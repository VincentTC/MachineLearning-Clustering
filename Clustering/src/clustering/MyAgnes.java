/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering;

import java.util.ArrayList;
import util.Point;
import util.DistanceMatrix;

/**
 *
 * @author Gerry
 */

public class MyAgnes {    
    
    // Single Linkage
    public void buildSingleLinkageClusterer(ArrayList<Point> data){
        ArrayList<ArrayList<Point>> clusters = new ArrayList<>();

        // Create clusters with each cluster containing one data point
        for (Point aData : data) {
            ArrayList<Point> cluster = new ArrayList<>();
            cluster.add(aData);
            clusters.add(cluster);
        }

        System.out.println(clusters.size() + " Clusters : " + clusters);

        for (;clusters.size() != 1;) {
            DistanceMatrix distanceMatrix = new DistanceMatrix(clusters);
            distanceMatrix.calculateSingleLinkage();

            distanceMatrix.printDistanceMatrix();

            int[] nearestCluster = distanceMatrix.minimumDistance();
            int clusterAIdx = nearestCluster[0];
            int clusterBIdx = nearestCluster[1];

            // Merge clusters
            clusters.get(clusterAIdx).addAll(clusters.get(clusterBIdx));
            clusters.remove(clusterBIdx);

            System.out.println();
            System.out.println(clusters.size() + " Clusters : " + clusters.toString().substring(1, clusters.toString().length()-1).replace("],", "]"));
        }
    }
    
    // Complete Linkage
    public void buildCompleteLinkageClusterer(ArrayList<Point> data){
        ArrayList<ArrayList<Point>> clusters = new ArrayList<>();

        // Create clusters with each cluster containing one data point
        for (Point aData : data) {
            ArrayList<Point> cluster = new ArrayList<>();
            cluster.add(aData);
            clusters.add(cluster);
        }

        System.out.println(clusters.size() + " Clusters : " + clusters);

        for (;clusters.size() != 1;) {
            DistanceMatrix distanceMatrix = new DistanceMatrix(clusters);
            distanceMatrix.calculateCompleteLinkage();

            distanceMatrix.printDistanceMatrix();

            int[] farCluster = distanceMatrix.maximumDistance();
            int clusterAIdx = farCluster[0];
            int clusterBIdx = farCluster[1];

            // Merge clusters
            clusters.get(clusterAIdx).addAll(clusters.get(clusterBIdx));
            clusters.remove(clusterBIdx);

            System.out.println();
            System.out.println(clusters.size() + " Clusters : " + clusters.toString().substring(1, clusters.toString().length()-1).replace("],", "]"));
        }
    }
}
