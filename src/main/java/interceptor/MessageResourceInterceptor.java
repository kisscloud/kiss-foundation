package interceptor;

import annotation.I18n;
import locale.MessageResource;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import utils.ThreadLocalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MessageResourceInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse rep, Object handler, ModelAndView modelAndView) {

        if (null != req.getAttribute(MessageResource.I18N_ATTRIBUTE)) {
            return;
        }

        HandlerMethod method = null;

        if (handler instanceof HandlerMethod) {
            method = (HandlerMethod) handler;
        }

        if (null == method) {
            return;
        }

        I18n i18nMethod = method.getMethodAnnotation(I18n.class);

        if (null != i18nMethod) {
            ThreadLocalUtil.setString(MessageResource.I18N_ATTRIBUTE, i18nMethod.value());
            return;
        }

        I18n i18nController = method.getBeanType().getAnnotation(I18n.class);

        if (null != i18nController) {
            ThreadLocalUtil.setString(MessageResource.I18N_ATTRIBUTE, i18nController.value());
            return;
        }

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
        ThreadLocalUtil.removeMap();
        return true;
    }
}
