package code.uz.config;

import code.uz.dto.JwtDTO;
import code.uz.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final CustomUserDetailsService customUserDetailsService;


    // token exists tekshiradi try{} da token-ni kesib oladi va mavjuda bulsa doFilter() ga uzatvoradi...
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization"); // task.http-dagi
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String token = header.substring(7).trim();
            JwtDTO jwtDTO = JwtUtil.decode(token);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtDTO.getPhone());

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JwtException e) {
            throw new ServletException("Invalid token" + e.getMessage());
        }

        filterChain.doFilter(request, response);  // tekshirib mavjud bulgan token-ni olib controller ga uzatvoradi
    }


    // ma'lum bir url lar uchun, yani WHITE_LIST da bor bulgan url-larni filter qilib utirmaslik uchun method.
    // WHITE_LIST -> permitAll() url-lar.
    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        return Arrays
                .stream(SpringConfig.WHITE_LIST) // white_list-ni aylanib chiqadi
                .anyMatch(p -> antPathMatcher.match(p, request.getServletPath())); // match qiladi
    }
    // murojat qilinayotgan url agar white_list da bulsa filter qilma depmiz
}
