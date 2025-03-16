package dyukov.vladimir.jirastub;

import org.springframework.http.HttpHeaders;

public class JiraStubHeaders extends HttpHeaders {
    JiraStubHeaders() {
        super();
        this.add("Server", "nginx/1.20.1");
        this.add("Content-Type", "application/json;charset=UTF-8");
        this.add("X-AREQUESTID", "582x2388286x1");
        this.add("Referrer-Policy", "strict-origin-when-cross-origin");
        this.add("X-XSS-Protection", "1; mode=block");
        this.add("X-Content-Type-Options", "nosniff");
        this.add("X-Frame-Options", "SAMEORIGIN");
        this.add("Content-Security-Policy", "sandbox");
        this.add("Strict-Transport-Security", "max-age=31536000");
        this.add("Set-Cookie", "JSESSIONID=1131A6172ED22EAE5F9DCE3FF3723C7B; Path=/; Secure; HttpOnly");
        this.add("X-Seraph-LoginReason", "OK");
        this.add("Set-Cookie", "atlassian.xsrf.token=B2UA-2NV2-5DBH-LD1W_c2c658854797860b33a043cb90d9876e1b2fa0eb_lin; Path=/; Secure");
        this.add("X-ASESSIONID", "duhm2u");
        this.add("X-AUSERNAME", "TEST_USER");
        this.add("Cache-Control", "no-cache, no-store, no-transform");
        this.add("X-Robots-Tag", "noindex, follow");
    }
}
