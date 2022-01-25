package study.ecommerce.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionManager {

    public SessionMemberDto getMember(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (SessionMemberDto) session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
