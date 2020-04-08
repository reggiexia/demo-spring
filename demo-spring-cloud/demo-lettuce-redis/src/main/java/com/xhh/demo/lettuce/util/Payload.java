package com.xhh.demo.lettuce.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Payload {
    private String user;
    private List<Privilege> privileges = new ArrayList<>();

    public Payload addResource(Privilege privilege) {
        this.privileges.add(privilege);
        return this;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class Privilege {
        private String type;
        private String resource;
        private String role_id;
    }
}
