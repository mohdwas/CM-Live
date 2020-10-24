package com.e.cmlive.models;

public class RegisterModel {
    /*{
    "message": "User has been registered  successfully !!",
    "status": true,
    "user_detail": {
        "user_id": "1",
        "name": "ahmed",
        "email": "",
        "phone_number": "6395584895",
        "dob": "2020-12-10",
        "gender": "Male",
        "status": "1",
        "profile_image": "https://dev.nsglobalsystem.com/cmlive/image_gallery/user/blank.png"
    }
}*/

    private String message;
    private boolean status;
    private userDetailBean user_detail;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public userDetailBean getUser_detail() {
        return user_detail;
    }

    public void setUser_detail(userDetailBean user_detail) {
        this.user_detail = user_detail;
    }

    public class userDetailBean {

        private String user_id;
        private String name;
        private String email;
        private String phone_number;
        private String dob;
        private String gender;
        private String status;
        private String profile_image;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }
    }


}
