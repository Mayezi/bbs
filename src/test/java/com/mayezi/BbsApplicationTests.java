package com.mayezi;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BbsApplicationTests {

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("9527"));
    }

}
