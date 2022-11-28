package io.company.artists.api.rest.v1.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.company.artists.api.lib.Error;
import io.company.artists.api.lib.RestError;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component("restAuthenticationEntryPoint")
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        RestError re = new RestError();
        re.setStatus(Integer.toUnsignedLong(HttpStatus.UNAUTHORIZED.value()));
        re.setMethod(request.getMethod());
        re.setRequestURI(request.getRequestURI());

        String queryString = request.getQueryString();
        if (queryString != null && !queryString.isEmpty()) {
            queryString = re.getRequestURI().concat("?").concat(queryString);
            re.setRequestQuery(queryString);
        }

        Error error = new Error();
        error.setTitle("Unauthorized");
        error.setMessage("Authentication credentials are required to the resource. " +
                "All requests must be authenticated.");
        re.addErrorsItem(error);


        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, re);
        responseStream.flush();
    }

}
