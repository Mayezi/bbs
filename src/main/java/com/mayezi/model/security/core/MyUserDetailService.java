package com.mayezi.model.security.core;


        import com.mayezi.model.security.entity.Permission;
        import com.mayezi.model.security.service.PermissionService;
        import com.mayezi.model.user.entity.User;
        import com.mayezi.model.user.service.UserService;
        import org.apache.log4j.Logger;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.core.GrantedAuthority;
        import org.springframework.security.core.authority.SimpleGrantedAuthority;
        import org.springframework.security.core.userdetails.UserDetails;
        import org.springframework.security.core.userdetails.UserDetailsService;
        import org.springframework.security.core.userdetails.UsernameNotFoundException;
        import org.springframework.stereotype.Service;

        import java.util.ArrayList;
        import java.util.List;

@Service
public class MyUserDetailService implements UserDetailsService {

    Logger log = Logger.getLogger(MyUserDetailService.class);


    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            log.info("User:" + username + " not exists.");
            throw new UsernameNotFoundException("User not found");
        }
        List<Permission> permissions = permissionService.findByAdminUserId(user.getId());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Permission permission : permissions) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
            grantedAuthorities.add(grantedAuthority);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

}
