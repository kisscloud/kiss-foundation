package filter;

import entity.Guest;
import org.apache.commons.lang3.StringUtils;
import utils.GuestUtil;
import utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GuestFilter implements InnerFilter{

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, InnerFilterChain filterChain) {

        String token = request.getHeader("X-Access-Token");

        if (!StringUtils.isEmpty(token)) {
            Integer accountId = JwtUtil.getUserId(token);
            String userName = JwtUtil.getUserName(token);
            String name = JwtUtil.getName(token);
            Guest operator = new Guest();
            operator.setId(accountId);
            operator.setUsername(userName);
            operator.setName(name);
            GuestUtil.setGuest(operator);
        }

        filterChain.doFilter(request,response,filterChain);
    }
}
