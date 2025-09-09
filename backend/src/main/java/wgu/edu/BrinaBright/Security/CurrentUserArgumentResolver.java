package wgu.edu.BrinaBright.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import wgu.edu.BrinaBright.Entities.User;
import wgu.edu.BrinaBright.Repos.UserRepository;

@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private UserRepository userRepository;
    @Override public boolean supportsParameter(MethodParameter p) {
        return p.hasParameterAnnotation(CurrentUser.class) && p.getParameterType().equals(User.class);
    }
    @Override public Object resolveArgument(MethodParameter p, ModelAndViewContainer m,
                                            NativeWebRequest w, WebDataBinderFactory b) {

        var auth = SecurityContextHolder.getContext().getAuthentication();
        var email = auth.getName();
        return userRepository.findByEmail(email);
    }
}
