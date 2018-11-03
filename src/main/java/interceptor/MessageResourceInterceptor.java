package interceptor;



import annotation.I18n;
import locale.MessageResource;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import utils.ThreadLocalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zbw
 * @create 2018/4/4 17:58
 */
public class MessageResourceInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse rep, Object handler, ModelAndView modelAndView) {

        // 在方法中设置i18路径
        if (null != req.getAttribute(MessageResource.I18N_ATTRIBUTE)) {
            return;
        }

        //判断是否HandlerMethod
        HandlerMethod method = null;

        if (handler instanceof HandlerMethod) {
            method = (HandlerMethod) handler;
        }

        if (null == method) {
            return;
        }

        // 在method注解了i18
        I18n i18nMethod = method.getMethodAnnotation(I18n.class);

        if (null != i18nMethod) {
            ThreadLocalUtil.setString(MessageResource.I18N_ATTRIBUTE, i18nMethod.value());
            return;
        }

        // 在Controller上注解了i18
        I18n i18nController = method.getBeanType().getAnnotation(I18n.class);

        if (null != i18nController) {
            ThreadLocalUtil.setString(MessageResource.I18N_ATTRIBUTE, i18nController.value());
            return;
        }

        // 根据Controller名字设置i18
        String controller = method.getBeanType().getName();
        int index = controller.lastIndexOf(".");

        if (index != -1) {
            controller = controller.substring(index + 1, controller.length());
        }

        index = controller.toUpperCase().indexOf("CONTROLLER");

        if (index != -1) {
            controller = controller.substring(0, index);
        }

        ThreadLocalUtil.setString(MessageResource.I18N_ATTRIBUTE, controller);
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse rep, Object handler) {
        // 在跳转到该方法先清除request中的国际化信息
        ThreadLocalUtil.removeMap();
        return true;
    }
}
