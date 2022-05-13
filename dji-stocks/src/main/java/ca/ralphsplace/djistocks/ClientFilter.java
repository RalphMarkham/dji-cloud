package ca.ralphsplace.djistocks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ClientFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(ClientFilter.class);

    @Override
    public  void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String clientId = req.getHeader("X-Client_Id");
        if (clientId == null || clientId.trim().isEmpty()) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            LOG.info("Logging Request  {} : {}", req.getMethod(), req.getRequestURI());
            filterChain.doFilter(request,response);
        }
    }
}
