package com.myc.ahpserver.service;

import com.myc.ahpserver.common.PostData;
import org.springframework.stereotype.Service;
import com.myc.ahpserver.common.ResultData;

import java.util.LinkedList;
import java.util.List;

@Service
public class AhpService {
    /**
     * Random Consistency Index
     */
    protected static double RI[] = {0.0, 0.0, 0.52, 0.89, 1.12, 1.26, 1.36, 1.41, 1.46, 1.49};

    public List<ResultData> handleAhpRequest(PostData postData) {
        List<Double[]> list2d = handleData(postData);
        List<Double[][]> list = fill2DArray(list2d, postData.getDataStructure());
        return calculateAhp(list, postData.getDataStructure());
    }

    public List<Double[]> handleData(PostData postData) {
        int[] dataStructure = postData.getDataStructure();
        double[] ahpInfoData = postData.getAhpInfoData();
        List<Double[]> list = new LinkedList<Double[]>();
        int flag = 0;
        for (int i = 0; i < dataStructure.length; i++) {//计算四个树的AHP，每次循环拿到每个树的二维数组
            int step = (dataStructure[i] - 1) + ((dataStructure[i] - 1) * ((dataStructure[i] - 1) - 1)) / 2;
            Double[] arr = new Double[step];
            for (int index = 0; index < step; index++) {
                arr[index] = ahpInfoData[flag + index];//要用的matrix[i][j]
            }
            flag += step;
            list.add(arr);//arr现在拿到了每个树的权值数据，接下来构造二维数组，然后计算ahp
        }
        return list;
    }

    public List<Double[][]> fill2DArray(List<Double[]> list, int[] dataStructure) {
        List<Double[][]> list2D = new LinkedList<Double[][]>();
        for (int i = 0; i < list.size(); i++) {
            Double[] arr = list.get(i);
            Double[][] matrix = new Double[dataStructure[i]][dataStructure[i]];
            for (int j = 0; j < dataStructure[i]; j++) {
                matrix[j][j] = 1.0;
            }
            int flag = 0;
            for (int m = 0; m < dataStructure[i] - 1; m++) {
                for (int n = m + 1; n < dataStructure[i]; n++) {
                    matrix[m][n] = arr[flag];
                    flag++;
                }
            }
            for (int p = dataStructure[i] - 1; p >= 0; p--) {
                for (int q = dataStructure[i] - 1; q >= 0; q--) {
                    matrix[p][q] = 1 / matrix[q][p];
                }
            }
            list2D.add(matrix);
        }
        return list2D;
    }

    public List<ResultData> calculateAhp(List<Double[][]> list2D, int[] dataStructure) {
        List<ResultData> ahpResult = new LinkedList<ResultData>();
        for (int i = 0; i < list2D.size(); i++) {
            boolean res = ahp(list2D.get(i), dataStructure[i]);
            if (!res) {
                ResultData resultData = new ResultData();
                resultData.setData(i + 1 + "");
                ahpResult.add(resultData);
            }
            System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
        }
        return ahpResult;
    }

    public boolean ahp(Double[][] array, int v) {
        Double[] column = new Double[v];
        for (int j = 0; j < v; j++) {
            for (int i = 0; i < v; i++) {
                if (column[j] != null) {
                    column[j] = column[j] + array[i][j];
                } else {
                    column[j] = array[i][j];
                }
            }
        }
        //矩阵归一化
        Double[][] matrixColumn = new Double[v][v];
        for (int j = 0; j < v; j++) {
            for (int i = 0; i < v; i++) {
                matrixColumn[i][j] = array[i][j] / column[j];
            }
        }
        //获得行数组
        Double[] line = new Double[v];
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                if (line[i] != null) {
                    line[i] = line[i] + matrixColumn[i][j];
                } else {
                    line[i] = matrixColumn[i][j];
                }
            }

        }
        //行归一化获得特征向量
        Double[] w = new Double[v];
        Double sum = 0.0;
        for (int i = 0; i < v; i++) {
            sum = sum + line[i];
        }
        for (int i = 0; i < v; i++) {
            w[i] = line[i] / sum;                    //特征向量
        }
        Double[] bw = new Double[v];
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                if (bw[i] != null) {
                    bw[i] = bw[i] + array[i][j] * w[j];
                } else {
                    bw[i] = array[i][j] * w[j];
                }
            }
        }
        Double sumR = 0.0;                        //最大特征跟R
        for (int i = 0; i < v; i++) {
            sumR = sumR + bw[i] / (v * w[i]);
        }
        Double ci = (sumR - v) / (v - 1);                //矩阵一致性指标
        System.out.println("计算出的矩阵一致性指标" + ci + "\n");
        Double cr = ci / RI[v - 1];
        System.out.println("CR=" + cr);//随机一致性比率 1.24为6阶矩阵的平均一致性指标
        if (cr >= 0.1) {
            System.out.println("CR大于0.1，权重设置不合理");
            return false;
        } else {
            //输出特征向量
            System.out.println("CR小于0.1，权重设置合理");
            for (int i = 0; i < v; i++) {
                System.out.println("特征" + i + "的权重：" + w[i]);
            }
            return true;
        }
    }
}
