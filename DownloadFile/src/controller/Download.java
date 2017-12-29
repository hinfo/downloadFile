/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author henrique
 */
public class Download {

    /**
     * @param args the command line arguments
     */
    public static Boolean terminou = false;
    public static int count;

    /**
     * Metodo específico para download de arquivos do sia sus.
     *
     * @param stringUrl url contendo o arquivo
     * @param pathLocal diretorio local onde será salvo o arquivo
     */
    public static void downloadArquivo(String stringUrl, String pathLocal) {
        try {

            String nomeArquivoLocal = getNomeArquivo(stringUrl);
            System.out.println("Baixando o arquivo " + nomeArquivoLocal);
            URL url = new URL(stringUrl + nomeArquivoLocal);
            InputStream is = url.openStream();
            FileOutputStream fos = new FileOutputStream(pathLocal + nomeArquivoLocal);
            byte[] buffer = new byte[4096];
            int bytes = -1;
            count = 0;

//                  TelaPrincipal.progress.setMaximum(count);
            int kont = 0;
            while ((bytes = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytes);
                System.out.print(".");
                terminou = false;

            }
            is.close();
            fos.close();
            count = 0;
            terminou = true;
            System.out.println("Arquivo baixado");

        } catch (Exception e) {
            System.out.println("Erro ao realizar o download \n" + e);

        }
    }

    /**
     * Metodo generico para download de arquivos
     *
     * @param stringUrl url contendo o arquivo.
     * @param pathLocal diretorio de destino
     * @param nome nome do arquivo a ser salvo parametro opcional.
     */
    public static void downloadArquivo(String stringUrl, String pathLocal, String nome) {
        try {

            System.out.println("Baixando o arquivo ");
            URL url = new URL(stringUrl + nome);
            InputStream is = url.openStream();
            FileOutputStream fos = new FileOutputStream(pathLocal + nome);
            byte[] buffer = new byte[4096];
            int bytes = -1;
            count = 0;

            int kont = 0;
            while ((bytes = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytes);
                System.out.print(".");
                terminou = false;

            }
            is.close();
            fos.close();
            count = 0;
            terminou = true;
            System.out.println("Arquivo baixado");

        } catch (Exception e) {
            System.out.println("Erro ao realizar o download \n" + e);

        }

    }

    public static void downloadArquivo(String stringUrl) {
        try {

            URL url = new URL(stringUrl);
            int index = url.getFile().split("/").length;
            String nome = url.getFile().split("/")[index - 1];
            Long size = verificaTamnahoArquivo(url.openStream());
            InputStream is = url.openStream();


            FileOutputStream fos = new FileOutputStream(nome);
            byte[] buffer = new byte[is.available()];
            int bytes = -1;
            count = 0;

            int kont = 0;
            System.out.println("Baixando o arquivo " + nome);
//            System.out.println("Total de bytes:" + totalBytes);
            while ((bytes = is.read(buffer)) != -1) {
//                System.out.print("bytes: ");
//                System.out.println((bytes * totalBytes) / 100 + "%");
                fos.write(buffer, 0, bytes);

                terminou = false;
            }
            is.close();
            fos.close();
            count = 0;
            terminou = true;
//            Long size = fos.getChannel().size();
            System.out.printf("Arquivo baixado total %.2f kb", size / 1024.0);


        } catch (Exception e) {
            System.out.println("Erro ao realizar o download \n" + e);

        }

    }

    public static long verificaTamnahoArquivo(InputStream is) throws IOException {
        byte[] buffer = new byte[is.available()];
        int bytes = -1;
        long somaBytes = 0;
        while ((bytes = is.read(buffer)) != -1) {
            somaBytes += bytes;
        }
        is.close();
        return somaBytes;
    }
    /**
     * Metodo para identificar o nome do arquivo a ser baixado no site do sia
     * sus.
     *
     * @param urlSite
     * @return string com o nome
     * @throws MalformedURLException
     * @throws IOException
     */
    public static String getNomeArquivo(String urlSite) throws MalformedURLException, IOException {
        String nomeArquivo = null;
        int tamArq = 0;
        URL url = new URL(urlSite);
        URLConnection conn = url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

        String linha = null;
        //lista os arquivos 
        String[] aux = null;
        while ((linha = br.readLine()) != null) {
//            System.out.println(linha);
            if (linha.contains("BDSIA") && linha.contains("exe")) {
                aux = linha.split(" ");
                nomeArquivo = aux[17];
                tamArq = Integer.parseInt(aux[16]);

//                Geralmente o último arquivo é o mais atualizado
//                System.out.print("Nome: " + nomeArquivo);
//                System.out.print(" | data: " + aux[0]);
//                System.out.print(" | hora: " + aux[2]);
//                System.out.print(" | Tam: " + aux[16]);
//                System.out.println(" ");
            } else if (linha.contains("BPA") && linha.contains("exe")) {
                //Geralmente fica apenas o mais atualizado no diretório
                aux = linha.split(" ");
                nomeArquivo = aux[17];
                tamArq = Integer.parseInt(aux[16]);
            }
        }
        System.out.println("Tamanho do arquivo " + tamArq * 0.9);
        return nomeArquivo;
    }
}
