package dyukov.vladimir.jirastub.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class JiraStubFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;

        res.setHeader("Server", "nginx/1.20.1");
        res.setHeader("Content-Type", "application/json;charset=UTF-8");
        res.setHeader("X-AREQUESTID", "582x2388286x1");
        res.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");
        res.setHeader("X-XSS-Protection", "1; mode=block");
        res.setHeader("X-Content-Type-Options", "nosniff");
        res.setHeader("X-Frame-Options", "SAMEORIGIN");
        res.setHeader("Content-Security-Policy", "sandbox");
        res.setHeader("Strict-Transport-Security", "max-age=31536000");
        res.setHeader("Set-Cookie", "JSESSIONID=1131A6172ED22EAE5F9DCE3FF3723C7B; Path=/; Secure; HttpOnly");
        res.setHeader("X-Seraph-LoginReason", "OK");
        res.setHeader("Set-Cookie", "atlassian.xsrf.token=B2UA-2NV2-5DBH-LD1W_c2c658854797860b33a043cb90d9876e1b2fa0eb_lin; Path=/; Secure");
        res.setHeader("X-ASESSIONID", "du2m2u");
        res.setHeader("X-AUSERNAME", "TEST_USER");
        res.setHeader("Cache-Control", "no-cache, no-store, no-transform");
        res.setHeader("X-Robots-Tag", "noindex, follow");

        chain.doFilter(request, response);
    }
}
