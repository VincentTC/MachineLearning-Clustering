/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 *
 * @author Vincent
 */
public class myKMeans {
    private double [][] data;
    private int [] label;
    private int [] withLabel;
    private double [][] _centroids;
    private int nRows;
    private int nDimension;
    private int nClusters;

    public myKMeans(String fileName, String labelname){
        BufferedReader reader;
        CSVHelper csv = new CSVHelper();
        ArrayList<String> values; 
        try 
        {
            reader = new BufferedReader(new FileReader(fileName));
            nRows = 1;
            values = csv.parseLine(reader);
            nDimension = values.size();
            while(reader.readLine()!=null)
                nRows++;
            reader.close();
            System.out.println("Jumlah row = " + nRows + ", Jumlah dimension = " + nDimension);

            data = new double[nRows][];
            for (int i=0; i<nRows; i++)
                data[i] = new double[nDimension];

            reader = new BufferedReader(new FileReader(fileName));
            int nrow = 0;
            while ((values = csv.parseLine(reader)) != null){
                double [] dv = new double[values.size()];
                for (int i=0; i< values.size(); i++){
                    dv[i] = Double.parseDouble(values.get(i));
                }
                data[nrow] = dv;
                nrow ++;
            }      
            reader.close();
            System.out.println("Loaded data");

            if (labelname != null){
                reader = new BufferedReader(new FileReader(labelname));
                withLabel = new int[nRows];
                int c = 0;
                while ((values = csv.parseLine(reader)) != null){
                  withLabel[c] = Integer.parseInt(values.get(0)); 
                }
                reader.close();
                System.out.println("loaded labels");
            } 
        }
        catch(Exception e) 
        {
          System.out.println(e);
          System.exit(0); 
        }
    }

    public void clustering(int numClusters, int epoch, double [][] centroids) {
        nClusters = numClusters;
        if (centroids != null)
            _centroids = centroids;
        else {
            _centroids = new double[nClusters][];

            ArrayList idx = new ArrayList();
            for (int i=0; i<numClusters; i++){
                int c;
                do {
                    c = (int) (Math.random()*nRows);
                } while(idx.contains(c));
                idx.add(c);

                _centroids[i] = new double[nDimension];
                for (int j=0; j<nDimension; j++)
                    _centroids[i][j] = data[c][j];
            }
        }

        double [][] c1 = _centroids;
        double threshold = 0.001;
        int round = 0;

        while (true){
            _centroids = c1;

            //assign record to the closest centroid
            label = new int[nRows];
            for (int i=0; i<nRows; i++){
                label[i] = closest(data[i]);
            }

            c1 = updateCentroids();
            round ++;
            if ((epoch > 0 && round >= epoch) || converge(_centroids, c1, threshold))
                break;
        }

        System.out.println("Clustering converges at " + round + " epoch");
    }

    private int closest(double [] v) {
        double minDistance = dist(v, _centroids[0]);
        int label = 0;
        for (int i=1; i<nClusters; i++){
            double t = dist(v, _centroids[i]);
            if (minDistance > t){
                minDistance = t;
                label = i;
            }
        }
        return label;
    }

    private double dist(double [] v1, double [] v2){
        double sum = 0;
        for (int i=0; i<nDimension; i++){
            double d = v1[i]-v2[i];
            sum += d*d;
        }
        return Math.sqrt(sum);
    }

    private double [][] updateCentroids(){
        // initialize centroids and set to 0
        double [][] newc = new double [nClusters][]; //new centroids 
        int [] counts = new int[nClusters]; // sizes of the clusters

        for (int i=0; i<nClusters; i++){
            counts[i] = 0;
            newc[i] = new double [nDimension];
            for (int j=0; j<nDimension; j++)
                newc[i][j] = 0;
        }


        for (int i=0; i<nRows; i++){
            int cn = label[i]; // the cluster membership id for record i
            for (int j=0; j<nDimension; j++){
                newc[cn][j] += data[i][j]; // update that centroid by adding the member data record
            }
            counts[cn]++;
        }

        for (int i=0; i< nClusters; i++){
            for (int j=0; j<nDimension; j++){
                newc[i][j] /= counts[i];
            }
        } 

        return newc;
    }

    private boolean converge(double [][] c1, double [][] c2, double threshold){
        double maxv = 0;
        for (int i=0; i< nClusters; i++){
            double d = dist(c1[i], c2[i]);
            if (maxv < d)
                maxv = d;
        } 

        if (maxv < threshold)
            return true;
        else
            return false;
    }
    public double[][] getCentroids() {
        return _centroids;
    }

    public int [] getLabel() {
        return label;
    }

    public int getNRows() {
        return nRows;
    }

    public void printResults(){
        System.out.println("Centroids:");
        for (int i=0; i<nClusters; i++){
            for(int j=0; j<nDimension; j++){
                if (j == 0)
                    System.out.print("[");
                System.out.print(_centroids[i][j]);
                if (j < nDimension-1)
                    System.out.print(", ");
                else
                    System.out.print("] ");
            }
            System.out.println();
        }
        System.out.println("Result:");
        for (int i=0; i<nClusters; i++){
            System.out.print("Cluster-" + (i+1) + " : ");
            for (int j=0; j<nRows; j++){
                for (int k=0; k<nDimension; k++){
                    if (label[j] == i){
                        if (k == 0)
                            System.out.print("[");
                        System.out.print(data[j][k]);
                        if (k < nDimension-1)
                            System.out.print(", ");
                        else
                            System.out.print("] ");
                    }
                }
            }
            System.out.println("");
        }
    }


    public static void main( String[] astrArgs ) {
        myKMeans KM = new myKMeans("data/point.csv", null);
        KM.clustering(3, 10, null); // 2 clusters, maximum 10 iterations
        KM.printResults();
    }
}
