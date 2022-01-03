package learning.spring.security.demo.configuration;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

import static learning.spring.security.demo.configuration.ApplicationPermission.*;

public enum ApplicationRole {

    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(
            STUDENT_READ,
            STUDENT_WRITE,
            COURSE_READ,
            COURSE_WRITE
    )),
    ADMINTRAINEE(Sets.newHashSet(STUDENT_READ, COURSE_READ));

    private final Set<ApplicationPermission> permissions;

    ApplicationRole(Set<ApplicationPermission> permissions) {
        this.permissions = permissions;
    }
}
