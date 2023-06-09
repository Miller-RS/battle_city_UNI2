/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.battlecity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPServerThread50 extends Thread {

    private Socket client;
    private TCPServer50 tcpserver;
    private int clientID;
    private boolean running = false;
    public PrintWriter mOut;
    public PrintWriter mOut2;
    public BufferedReader in;
    private TCPServer50.OnMessageReceived messageListener = null;
    private String message;
    TCPServerThread50[] cli_amigos;
    String[][] campo;

    InputStream secuenciaDeEntrada;
    OutputStream secuenciaDeSalida;
    PrintWriter out;

    /*public String[][] campo = {{"-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-"},//28
                                    {"|"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," ","|"},
                                    {"|"," "," ","#"," "," ","#"," "," ","#"," "," "," "," "," "," ","#"," "," "," ","#","#"," "," ","#","#"," ","#","|"},
                                    {"|"," "," ","#"," "," ","#"," "," ","#","#"," "," "," "," "," ","#"," "," "," ","#","#"," "," "," "," "," "," ","|"},
                                    {"|"," "," ","#"," "," ","#"," "," ","#"," ","#"," "," "," "," ","#"," "," "," ","#","#"," "," "," "," "," "," ","|"},
                                    {"|"," "," ","#"," "," ","#"," "," ","#"," "," ","#"," "," "," ","#"," "," "," ","#","#"," "," "," "," "," "," ","|"},
                                    {"|"," "," ","#"," "," ","#"," "," ","#"," "," "," ","#"," "," ","#"," "," "," ","#","#"," "," ","#"," "," "," ","|"},
                                    {"|"," "," ","#"," "," ","#"," "," ","#"," "," "," "," ","#"," ","#"," "," "," ","#","#"," "," "," ","#"," "," ","|"},
                                    {"|"," "," ","#"," "," ","#"," "," ","#"," "," "," "," "," ","#","#"," "," "," ","#","#"," "," "," "," ","#"," ","|"},
                                    {"|"," "," ","#","#","#","#"," "," ","#"," "," "," "," "," "," ","#"," "," "," ","#","#"," "," "," "," "," ","#","|"},
                                    {"|"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," ","|"},
                                    {"|"," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," ","|"},
                                    {"-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-"}};//13 filas*/
    public TCPServerThread50(Socket client_, TCPServer50 tcpserver_, int clientID_, TCPServerThread50[] cli_ami_, String[][] campo) {
        this.client = client_;
        this.tcpserver = tcpserver_;
        this.clientID = clientID_;
        this.cli_amigos = cli_ami_;
        this.campo = campo;
    }

    public void trabajen(int cli) {
        mOut.println("TRABAJAMOS [" + cli + "]...");
    }

    public void run() {
        running = true;
        try {
            try {
                boolean soycontador = false;
                mOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
                System.out.println("TCP Server" + "C: Sent.");
                messageListener = tcpserver.getMessageListener();
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                //sendMessage("Megaman 8x");

                //enviar el campo de batalla a los clientes
                //transformamos el campo a bits para enviarlo
                /* ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(campo);
                byte[] bytes = baos.toByteArray();
                OutputStream os = client.getOutputStream();
                os.write(bytes);
                System.out.println("size bytes: "+bytes.length);
                System.out.println("campo size:"+ campo.length); */
                ////////////////////////////////////////////////////////
                for (int i = 0; i < campo.length; i++) {
                    for (int j = 0; j < campo[i].length; j++) {
                        sendMessage(campo[i][j]);
                    }
                }

                /* while (running) {
                   // message = in.readLine();
                    System.out.println("entra 1");
                    //recivimos el campo de batalla actualizado
                    InputStream inputStream = client.getInputStream();
                   // DataInputStream dataInputStream = new DataInputStream(inputStream);
                    //int matrixLength = dataInputStream.readInt();
                    System.out.println("entra 2");
                    byte[] bytes2 = new byte[2086];//size del campo de batalla
                     System.out.println("entra 3");
                    int count = inputStream.read(bytes2);
                     System.out.println("entra 4");
                    ByteArrayInputStream bais = new ByteArrayInputStream(bytes2, 0, count);
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    String[][] campoUpdate = (String[][]) ois.readObject();

                    campo = campoUpdate;
                    
                    System.out.println(campo[1][1]);
                   /* if (message != null && messageListener != null) {
                        messageListener.messageReceived(message);
                    }*/
                //  message = null;
                //}
                System.out.print(in.ready());
                System.out.print("cARACTER CENTRADO: " + '\u00B7');
                while (running) {

                    String mensaje = in.readLine();
                    System.out.println("mensaje enviado:  " + mensaje);
                    String[] palabras = mensaje.split(" ");

                    System.out.println("length de palabras: " + palabras.length);
                    if (palabras.length == 5) {

                        int i = Integer.parseInt(palabras[0]);
                        int j = Integer.parseInt(palabras[1]);

                        campo[i][j] = palabras[2];

                        int h = Integer.parseInt(palabras[3]);
                        int g = Integer.parseInt(palabras[4]);

                        campo[h][g] = " ";

                        for (int v = 0; v < 13; v++) {
                            for (int w = 0; w < 29; w++) {
                                System.out.print(campo[v][w] + " ");
                            }
                            System.out.println();
                        }
                        String[][] campoCopy = campo;
                        envioActualizacionTankes(campoCopy);

                    } else {

                        if (palabras.length == 2) {
                            int i = Integer.parseInt(palabras[0]);
                            int j = Integer.parseInt(palabras[1]);

//System.out.println("posiciones y valor: "+palabras[0]+palabras[1]+palabras[2]);
                            campo[i][j] = " ";

                            String[][] campoCopy = campo;
                            envioActualizacionTankes(campoCopy);
                            /*for (int k = 1; k < cli_amigos.length; k++) {
                                System.out.println("length amidgos " + cli_amigos.length);
                                System.out.println("bandera 1");
                                secuenciaDeSalida = cli_amigos[k].client.getOutputStream();
                                System.out.println("bandera 2");
                                out = new PrintWriter(secuenciaDeSalida, true);
                                System.out.println("bandera 3");

                                for (int v = 0; v < campo.length; v++) {
                                    for (int w = 0; w < campo[v].length; w++) {
                                        out.println(campo[v][w]);
                                    }
                                }

                                //out.println(palabras[0]+" "+palabras[1]+" "+palabras[2]);
                                System.out.println("bandera 4");
                            }*/
                        } else {

                            if (palabras[2].equals(".")) {
                                int i = Integer.parseInt(palabras[0]);
                                int j = Integer.parseInt(palabras[1]);

//System.out.println("posiciones y valor: "+palabras[0]+palabras[1]+palabras[2]);
                                campo[i][j] = palabras[2];

                                String[][] campoCopy = campo;

                                envioActualizacionTankes(campoCopy);

                                actualizarBalas(campoCopy, i, j, palabras[5]);
                            } else {
                                int i = Integer.parseInt(palabras[0]);
                                int j = Integer.parseInt(palabras[1]);

//System.out.println("posiciones y valor: "+palabras[0]+palabras[1]+palabras[2]);
                                campo[i][j] = palabras[2];

                                String[][] campoCopy = campo;

                                envioActualizacionTankes(campoCopy);
                            }
                            /*for (int k = 1; k < cli_amigos.length; k++) {
                                System.out.println("length amidgos " + cli_amigos.length);
                                System.out.println("bandera 1");
                                secuenciaDeSalida = cli_amigos[k].client.getOutputStream();
                                System.out.println("bandera 2");
                                out = new PrintWriter(secuenciaDeSalida, true);
                                System.out.println("bandera 3");

                                for (int v = 0; v < campo.length; v++) {
                                    for (int w = 0; w < campo[v].length; w++) {
                                        out.println(campo[v][w]);
                                    }
                                }

                                //out.println(palabras[0]+" "+palabras[1]+" "+palabras[2]);
                                System.out.println("bandera 4");
                            }*/
                        }

                    }

                    //System.out.println(campo[i][j]);
                    // }
                    // }
                    /* System.out.println("Recivio el campo actualizado");
                    for (int v = 0; v < 13; v++) {
                        for (int w = 0; w < 29; w++) {
                            System.out.print(campo[v][w] + " ");
                        }
                        System.out.println();
                    }*/
                    //InputStream secuenciaDeEntrada = client.getInputStream();
                    //OutputStream secuenciaDeSalida = client.getOutputStream();
                    //PrintWriter out = new PrintWriter(secuenciaDeSalida, true);
                    //out.println(palabras[0]+" "+palabras[1]+" "+palabras[2]);
                    /* for (int k = 1; k < cli_amigos.length; k++) {
                        System.out.println("length amidgos " + cli_amigos.length);
                        System.out.println("bandera 1");
                        secuenciaDeSalida = cli_amigos[k].client.getOutputStream();
                        System.out.println("bandera 2");
                        out = new PrintWriter(secuenciaDeSalida, true);
                        System.out.println("bandera 3");

                        for (int v = 0; v < campo.length; v++) {
                            for (int w = 0; w < campo[v].length; w++) {
                                out.println(campo[v][w]);
                            }
                        }

                        //out.println(palabras[0]+" "+palabras[1]+" "+palabras[2]);
                        System.out.println("bandera 4");
                    }*/
                    //}
                }

                System.out.println("RESPONSE FROM CLIENT" + "S: Received Message: '" + message + "'");
            } catch (Exception e) {
                System.out.println("TCP Server" + "S: Error" + e);
            } finally {
                client.close();
            }

        } catch (Exception e) {
            System.out.println("TCP Server" + "C: Error" + e);
        }
    }

    public void stopClient() {
        running = false;
    }

    public void sendMessage(String message) {//funcion de trabajo
        if (mOut != null && !mOut.checkError()) {
            mOut.println(message);
            mOut.flush();
        }
    }

    public void envioActualizacionTankes(String[][] x) {

        //out = new PrintWriter(secuenciaDeSalida, true);
        for (int k = 1; k < cli_amigos.length; k++) {
            System.out.println("length amidgos " + cli_amigos.length);
            System.out.println("bandera 1");
            try {
                secuenciaDeSalida = cli_amigos[k].client.getOutputStream();
            } catch (IOException ex) {
                Logger.getLogger(TCPServerThread50.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("bandera 2");
            out = new PrintWriter(secuenciaDeSalida, true);
            System.out.println("bandera 3");

            for (int v = 0; v < x.length; v++) {
                for (int w = 0; w < x[v].length; w++) {
                    out.println(x[v][w]);
                }
            }

            //out.println(palabras[0]+" "+palabras[1]+" "+palabras[2]);
            System.out.println("bandera 4");
        }

    }

    public void actualizarBalas(String[][] y, int i, int j, String tankeDirection) {

        System.out.println("Direccion tanke: " + tankeDirection);
        if (tankeDirection.equals("<")) {
            while (y[i][j - 1].equals(" ")) {
                System.out.println("Bandera 2 de disparo");
                j = j - 1;
                y[i][j] = ".";
                y[i][j + 1] = " ";
                //manejador.sendMessage(Integer.toString(row) + " " + Integer.toString(Column) + " " + mapa[row][Column]);

                envioActualizacionTankes(y);
                try {
                   
                    Thread.sleep(1200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TCPServerThread50.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Bandera 3 de disparo");

            }
            y[i][j] = " ";

            envioActualizacionTankes(y);
        }

        //////////////////////////
        if (tankeDirection.equals(">")) {
            while (y[i][j + 1].equals(" ")) {
                System.out.println("Bandera 2 de disparo");
                j = j + 1;
                y[i][j] = ".";
                y[i][j - 1] = " ";
                //manejador.sendMessage(Integer.toString(row) + " " + Integer.toString(Column) + " " + mapa[row][Column]);

                envioActualizacionTankes(y);
                 try {
                   
                    Thread.sleep(1200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TCPServerThread50.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Bandera 3 de disparo");
                System.out.println("Bandera 3 de disparo");

            }
            y[i][j] = " ";

            envioActualizacionTankes(y);
        }

        //////////////////
        if (tankeDirection.equals("v")) {
            while (y[i + 1][j].equals(" ")) {
                System.out.println("Bandera 2 de disparo");
                i = i + 1;
                y[i][j] = ".";
                y[i - 1][j] = " ";
                //manejador.sendMessage(Integer.toString(row) + " " + Integer.toString(Column) + " " + mapa[row][Column]);

                envioActualizacionTankes(y);
                 try {
                   
                    Thread.sleep(1200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TCPServerThread50.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Bandera 3 de disparo");
                System.out.println("Bandera 3 de disparo");

            }
            y[i][j] = " ";

            envioActualizacionTankes(y);
        }

        ///////////////
        if (tankeDirection.equals("^")) {
            while (y[i - 1][j].equals(" ")) {
                System.out.println("Bandera 2 de disparo");
                i = i - 1;
                y[i][j] = ".";
                y[i + 1][j] = " ";
                //manejador.sendMessage(Integer.toString(row) + " " + Integer.toString(Column) + " " + mapa[row][Column]);

                envioActualizacionTankes(y);
                 try {
                   
                    Thread.sleep(1200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TCPServerThread50.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Bandera 3 de disparo");
                System.out.println("Bandera 3 de disparo");

            }
            y[i][j] = " ";

            envioActualizacionTankes(y);
        }
    }

}
