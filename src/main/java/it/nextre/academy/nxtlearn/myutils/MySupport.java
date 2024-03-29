package it.nextre.academy.nxtlearn.myutils;

import it.nextre.academy.nxtlearn.config.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.*;
import java.text.DecimalFormat;

public class MySupport {

    /**
     * Clona un array di double
     * */
    public static double[] clonaArray(double[] arrayInput) {
        double[] arrayOutput = new double[arrayInput.length];
        for (int i = 0; i < arrayInput.length; i++) {
            arrayOutput[i]=arrayInput[i];
        }//end for
        return arrayOutput;
    }

    /**
     * Formatta un double a 2 cifre decimali
     * */
    public static String formattaDouble(double number){
         /* cattiva idea duplicare la logica
         DecimalFormat df= new DecimalFormat();
         df.setMaximumFractionDigits(2);
         //System.out.printf("%.2f",number);
         //System.out.println("");
         return df.format(number);
         */
        //meglio richiamare l'altro metodo
        return formattaDouble(number,2);
    }

    /**
     * Formatta un double a n cifre decimali
     * */
    public static String formattaDouble(double number, int precision){
        DecimalFormat df= new DecimalFormat();
        df.setMaximumFractionDigits(precision);
        //System.out.printf("%."+precision+"f",number);
        //System.out.println("");
        return df.format(number);
    }


    /**
     * Stampa una matrice di interi a schermo
     * @param matrice
     */
    public static void stampaMatriceInteri(int[][] matrice) {
        for (int riga = 0; riga < matrice.length; riga++) {
            //System.out.print("[");
            for (int colonna = 0; colonna < matrice[riga].length; colonna++) {
                //System.out.print(matrice[riga][colonna]+" ");
                System.out.printf("% 3d ",matrice[riga][colonna]);
                if (colonna!=matrice[riga].length-1){
                    //System.out.print("|");
                }
            }//end for
            //System.out.print("]");
            System.out.println("");
        }//end for
    }




    public boolean isPalindroma(String str){
        int i = 0;
        for (i=0; i<str.length()/2; i++){
            if(str.charAt(i)!=str.charAt(str.length()-1-i)){
                break;
            }
        }
        return i==str.length()/2;
    }

    private static String capitalize(String string){
        if (string != null && string.length()>0){
            String tmp = ""+string.charAt(0);
            return tmp.toUpperCase()+string.substring(1).toLowerCase();
        }
        return string;
    }


    private static int cont=0;
    public static void sfogliaPath(String s, boolean nascosti) {
        sfogliaPath(s, nascosti, null,null);
    }
    public static void sfogliaPath(String s, boolean nascosti,String ext) {
        sfogliaPath(s, nascosti, null, ext);
    }
    public static void sfogliaPath(String s, boolean nascosti,File src) {
        sfogliaPath(s, nascosti, src, null);
    }
    public static void sfogliaPath(String s, boolean nascosti, File src, String ext) {
        try {
            File file = new File(s);
            File[] files = file.listFiles()!=null?file.listFiles():new File[0];

            for (int i = 0; i < files.length; i++) {
                //skippo file/cartelle nascoste
                if (!nascosti && files[i].getName().startsWith("."))
                    continue;
                if (!nascosti && files[i].isHidden())
                    continue;
                if (ext!=null && !files[i].isDirectory() && !files[i].getName().toLowerCase().endsWith(ext.toLowerCase()))
                    continue;

                //vedo se devo scrivere su un file l'output
                boolean toFile=false;
                Writer writer=null;
                if (src!=null){
                    writer = new FileWriter(src,true);
                    toFile=true;
                }
                //vedo se devo scrivere il path completo o no
                boolean fullPath = ext!=null;

                if (files[i].isDirectory()){
                    File tmp = new File(files[i].toString());
                    File[] tmps = tmp.listFiles()!=null?tmp.listFiles():new File[0];
                    String out="";
                    if (fullPath)
                        out = "";//la directory, in fase di filtraggio, non va scritta su file
                    else
                        out = " ".repeat(cont)+"> "+files[i].getName() + " ["+tmps.length+"]";

                    if (toFile) {
                        if (out.trim().length()>0)
                        writer.write(out+"\n");
                        writer.flush();
                        writer.close();
                    }
                    else
                        System.out.println(out);

                    cont++;
                    sfogliaPath(files[i].toString(), nascosti,src,ext);
                    cont--;
                }else{
                    String out;
                    if (fullPath)
                        out = files[i].toString();
                    else
                        out = " ".repeat(cont)+"    "+files[i].getName();

                    if (toFile){
                        writer.write(out+"\n");
                        writer.flush();
                        writer.close();
                    } else
                        System.out.println(out);
                }
            }
        } catch (SecurityException e) {
            System.err.println(e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static CustomUserDetails getLoggedUser(){
        try {
            // todo capire se possibile fare di meglio
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof CustomUserDetails) {
                return ((CustomUserDetails) principal);
            } /*else {
                return principal;
            }*/
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

}//end class
