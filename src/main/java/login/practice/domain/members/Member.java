package login.practice.domain.members;

import lombok.Data;

@Data
public class Member {
    private Long id;
    private String loginId;
    private String password;
    private String name;

    public Member(Builder builder) {
        this.loginId = builder.loginId;
        this.password = builder.password;
        this.name = builder.name;
    }

    public static class Builder {
        private String loginId;
        private String password;
        private String name;

        public Builder setLoginId(String loginId) {
            this.loginId = loginId;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Member build() {
            return new Member(this);
        }

    }

}
