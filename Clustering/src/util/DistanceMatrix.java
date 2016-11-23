/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import util.Point;

/**
 *
 * @author Gerry
 */
public class DistanceMatrix {
    private final Float[][] distanceMatrix;
    private final ArrayList<ArrayList<Point>> clusters;
    
    public DistanceMatrix(ArrayList<ArrayList<Point>> clusters){
        this.clusters = clusters;
        distanceMatrix = new Float[clusters.size()][clusters.size()];
    }
    
    public float euclideanDistance(Point P1, Point P2){
        return (float) Math.sqrt(Math.pow((P1.getAxis() - P2.getAxis()), 2) + Math.pow((P1.getOrdinate() - P2.getOrdinate()), 2));
    }
    
    public DistanceMatrix calculateSingleLinkage(){
        for(int i=0; i<clusters.size(); i++){
            for(int j=i; j<clusters.size(); j++){
                if(i == j){
                    distanceMatrix[i][j] = Float.valueOf(0);
                } else {
                    ArrayList<Point> firstCluster = clusters.get(i);
                    ArrayList<Point> secondCluster = clusters.get(j);
                    Float minimumDistance = Float.MAX_VALUE;
                    
                    for(Point firstPoint: firstCluster){
                        for(Point secondPoint: secondCluster){
                            minimumDistance = Math.min(minimumDistance, euclideanDistance(firstPoint, secondPoint));
                        }
                    }
                    distanceMatrix[i][j] = minimumDistance;
                    distanceMatrix[j][i] = minimumDistance;
                }
            }
        }
        return this;
    }
    
    public DistanceMatrix calculateCompleteLinkage(){
        Float maximumDistance = Float.MIN_VALUE;
        
        for(int i=0; i<clusters.size(); i++){
            for(int j=i; j<clusters.size(); j++){
                if(i == j){
                    distanceMatrix[i][j] = Float.valueOf(0);
                } else {
                    ArrayList<Point> firstCluster = clusters.get(i);
                    ArrayList<Point> secondCluster = clusters.get(j);
                    
                    for(Point firstPoint: firstCluster){
                        for(Point secondPoint: secondCluster){
                            maximumDistance = Math.min(maximumDistance, euclideanDistance(firstPoint, secondPoint));
                        }
                    }
                    distanceMatrix[i][j] = maximumDistance;
                    distanceMatrix[j][i] = maximumDistance;
                }
            }
        }
        return this;
    }
    
    public int[] getNearestCluster(){
        Float minimumDistance = Float.MAX_VALUE;
        int firstCluster = -1;
        int secondCluster = -1;
        
        for(int i=0; i<distanceMatrix.length; i++){
            for(int j=i+1; j<distanceMatrix.length; j++){
                if(distanceMatrix[i][j] < minimumDistance){
                    minimumDistance = distanceMatrix[i][j];
                    firstCluster = i;
                    secondCluster = j;
                }
            }
        }
        return new int[]{firstCluster, secondCluster};
    }
    
    public void printDistanceMatrix() {
        String format = "%-20s";
        
        System.out.println("****************************");
        for (Float[] aDistanceMatrix : distanceMatrix) {
            for (Float anADistanceMatrix : aDistanceMatrix) {
                System.out.format(format, anADistanceMatrix);
            }
            System.out.println();
        }
        System.out.println("****************************");
    }

}
