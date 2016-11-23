/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering;

import util.Point;
import com.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author user
 */
public class Clustering {
    
    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        System.out.println();
        ArrayList<Point> trainingPointData = new ArrayList<>();
        Point P = new Point(Float.valueOf(0), Float.valueOf(0));
        CSVReader reader = new CSVReader(new FileReader("data/point.csv"));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            ArrayList<Float> instance = new ArrayList<>();
            for (String aNextLine : nextLine) {
                instance.add(Float.parseFloat(aNextLine));
            }
            P.setAxis(instance.get(0));
            P.setOrdinate(instance.get(1));
            trainingPointData.add(new Point(P.getAxis(), P.getOrdinate()));
        }
        MyAgnes myAgnes = new MyAgnes();
        System.out.println("SINGLE LINKAGE");
        myAgnes.buildSingleLinkageClusterer(trainingPointData);
        System.out.println();
        System.out.println("COMPLETE LINKAGE");
        myAgnes.buildCompleteLinkageClusterer(trainingPointData);
    }
}