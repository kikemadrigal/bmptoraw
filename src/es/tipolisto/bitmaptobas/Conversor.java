package es.tipolisto.bitmaptobas;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.WriteAbortedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

public class Conversor {
	
	
	private String nombreArchivoAConvertir;
	private String nombreArchivoConvertido;

	File fileArchivoConvertido;
	public Conversor(String nombre) {
		nombreArchivoAConvertir=nombre;
		nombreArchivoConvertido=nombreArchivoAConvertir.substring(0,nombreArchivoAConvertir.length()-3)+"-tobas.txt";
		fileArchivoConvertido=new File(nombreArchivoConvertido);
		if(fileArchivoConvertido.exists()) {
			fileArchivoConvertido.delete();
			fileArchivoConvertido=new File(nombreArchivoConvertido);
		}

	}

	
	public boolean convertir() throws Exception{
		boolean exsito=false;
	    BufferedImage img;
	    FileOutputStream fos;
		try {
			img = ImageIO.read(new File(nombreArchivoAConvertir));
			int width = img.getRaster().getWidth();
			int height = img.getRaster().getHeight();
			byte[] data = ((DataBufferByte)img.getRaster().getDataBuffer()).getData();
			String filename = (new StringTokenizer(nombreArchivoAConvertir, ".")).nextToken();
			fos = new FileOutputStream(filename + "_" + width + "x" + height + ".raw");
		    fos.write(data);
		    fos.close();
		    Path path = Paths.get(nombreArchivoAConvertir, new String[0]);
		    byte[] inputData = Files.readAllBytes(path);
		    byte[] outputBuffer = new byte[32];
		    int bi = 0;
		    for (int i = 0; i < 16; i++) {
		      byte R = (byte)(((inputData[54 + i * 4 + 2] & 0xFF) >> 5 & 0xFF) << 4 & 0xFF);
		      byte B = (byte)((inputData[54 + i * 4 + 0] & 0xFF) >> 5 & 0xFF & 0xFF);
		      byte RB = (byte)(R | B);
		      byte G = (byte)((inputData[54 + i * 4 + 1] & 0xFF) >> 5 & 0xFF);
		      outputBuffer[bi++] = RB;
		      outputBuffer[bi++] = G;
		    } 
		    fos = new FileOutputStream(filename + ".pal");
		    fos.write(outputBuffer);
		    fos.close();
		    exsito=true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return exsito;
	}

}
