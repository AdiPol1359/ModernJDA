package me.adipol.modernjda.util;

import lombok.experimental.UtilityClass;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.Member;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public class MemberUtil {

    public static boolean hasRole(Member member, String... roleId) {
        List<String> roles = Arrays.asList(roleId);

        return member.getRoles().stream().map(ISnowflake::getId).anyMatch(roles::contains);
    }
}
