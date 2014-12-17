package org.ikovia;

import java.io.*;


public class Application {

    public static void main(String [] args){

        System.out.println("Application Started");

        Application app = new Application();

        File sampleFile = new File(app.getClass().getResource("/samples.txt").getFile());

        try {

            BufferedReader fileReader = new BufferedReader(new FileReader(sampleFile));

            String line;
            while((line = fileReader.readLine()) != null){

                System.out.println(line);
            }
        }
        catch(FileNotFoundException e){

            System.out.println(e.toString());
        }
        catch(IOException e){

            System.out.println(e.toString());
        }

    }
}
