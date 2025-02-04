package com.example.winterdeom.global.auth;

import com.example.winterdeom.domain.auth.repository.AuthRepository;
import com.example.winterdeom.domain.common.error.ErrorCode;
import com.example.winterdeom.domain.common.exception.NotFoundException;
import com.example.winterdeom.domain.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
@Slf4j
@AllArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationContext authenticationContext;
    private final AuthRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("prehandle");
        String accessToken = AuthenticationExtractor.extractTokenFromRequest(request);
        log.info("success accessToken");
        UUID userId = UUID.fromString(jwtTokenProvider.getPayload(accessToken));
        log.info("success to get payload: userId");
        User user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        log.info("success to get user");
        authenticationContext.setPrincipal(user);
        log.info("set user");
        return true;
    }
}
