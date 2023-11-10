package com.example.pokeindex;

public class Pokemon {
    private Name name;
    private Image image;

    // 添加的嵌套类以匹配JSON数据
    public static class Name {
        private String english;
        // 可以根据需要添加更多语言字段

        // Getters and Setters
        public String getEnglish() {
            return english;
        }

        public void setEnglish(String english) {
            this.english = english;
        }
    }

    public static class Image {
        private String thumbnail;
        // 其他图片链接可以按需要添加

        // Getters and Setters
        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }

    // Getters and Setters for Pokemon
    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
