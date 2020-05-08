package com.ljz.diagnostic_system.model;

/**
 * 这个继承太烂了（后来自己看这段代码都看懵了，记一下
 * 基类中的id属性在基类中的意思是作物的id，在子类中的意思是病害的id
 * 本来Disease和Crop完全不是IS-A关系，不应该使用继承，要使用组合
 * 但是Mybatis的组合对象映射好像要配置resultMap，比较麻烦
 */
public class Disease extends Crop{
    private String image;
    private String features;
    private String information;
    private String crop;
    private String cropID;
    private String parts;

    public String getCropID() {
        return cropID;
    }

    public void setCropID(String cropID) {
        this.cropID = cropID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getParts() {
        return parts;
    }

    public void setParts(String parts) {
        this.parts = parts;
    }

    @Override
    public String toString() {
        return "Disease{" +
                "image='" + image + '\'' +
                ", features='" + features + '\'' +
                ", information='" + information + '\'' +
                ", crop='" + crop + '\'' +
                ", cropID='" + cropID + '\'' +
                ", parts='" + parts + '\'' +
                '}';
    }
}
