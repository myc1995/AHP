package com.myc.ahpserver.common;

public class PostData {
    private double[] ahpInfoData;
    private int[] dataStructure;
    private int dataLength;

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    public double[] getAhpInfoData() {
        return ahpInfoData;
    }

    public void setAhpInfoData(double[] ahpInfoData) {
        this.ahpInfoData = ahpInfoData;
    }

    public int[] getDataStructure() {
        return dataStructure;
    }

    public void setDataStructure(int[] dataStructure) {
        this.dataStructure = dataStructure;
    }
}
