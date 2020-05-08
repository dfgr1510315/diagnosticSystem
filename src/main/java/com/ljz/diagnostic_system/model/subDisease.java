package com.ljz.diagnostic_system.model;

public class subDisease {

    private Disease disease;

    private double similarity;

    public subDisease(Disease disease, double similarity) {
        this.disease = disease;
        this.similarity = similarity;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }
}
