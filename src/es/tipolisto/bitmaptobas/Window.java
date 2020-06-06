package es.tipolisto.bitmaptobas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class Window extends JFrame {
	private JPanel contentPane;
	private File file;
	private JButton btnSeleccionaElArchivo, btnConvertir;
	private JLabel labelLocalizacion;




	/**
	 * Create the frame.
	 */
	public Window() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("MSX Murcia");
		contentPane = new JPanel();
		
		//Le asignamos al panel de la ventana un BorderLayout
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		inicializar();
		
		
		//Events
		btnSeleccionaElArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jFileChooser=new JFileChooser(System.getProperty("user.dir"));
				jFileChooser.setDialogTitle("Selecciona un archivo");
				int result=jFileChooser.showSaveDialog(null);
				if(result==JFileChooser.APPROVE_OPTION) {
					file=jFileChooser.getSelectedFile();
					labelLocalizacion.setText(file.getPath().toString());
				}
			}
		});
		
		btnConvertir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				convertir();
			}
		});
	}
	
	private void inicializar() {
		/***
		 * BorderLayout.NORTH
		 */
		JLabel lblAsmToBas = new JLabel("BMP TO RAW");
		lblAsmToBas.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblAsmToBas, BorderLayout.NORTH);

		/***
		 * BorderLayout.WEST
		 */
		
		/***
		 * BorderLayout.CENTER->JPanelCentral
		 */
		JPanel jPanelCentral=new JPanel();
		jPanelCentral.setLayout(new GridLayout(2,1));
		labelLocalizacion = new JLabel("Localizacion");
		jPanelCentral.add(labelLocalizacion);
		labelLocalizacion.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel jPanelCentralArriba=new JPanel();
		jPanelCentralArriba.setLayout(new FlowLayout());
		jPanelCentral.add(jPanelCentralArriba);
		
		btnSeleccionaElArchivo = new JButton("Selecciona el archivo");
		jPanelCentralArriba.add(btnSeleccionaElArchivo);
		btnConvertir = new JButton("Convertir");
		jPanelCentralArriba.add(btnConvertir);
		btnConvertir.setHorizontalAlignment(SwingConstants.CENTER);
		
		contentPane.add(jPanelCentral, BorderLayout.CENTER);
		/***
		 * Final JPanelCentral
		 */
		
		
		/**
		 * BorderLayout.SOUTH->JPanel abajo
		 */
		JPanel jPanelAbajo=new JPanel();
		jPanelAbajo.setLayout(new FlowLayout());
		contentPane.add(jPanelAbajo, BorderLayout.SOUTH);
		/**
		 * Final JPanelAbajo
		 */
		
	}
	
	
	
	
	private void convertir() {
		int contadorLinea=Constants.contadorLinea;
		boolean exito=false;
		String filePath="";
		if(file==null) {
			JOptionPane.showMessageDialog(null, "Tienes que seleccionar un archivo"); 
		}else if(!file.canRead()) {
			JOptionPane.showMessageDialog(null, "No tienes permisos para leer la imagen"); 
	    }else {
			filePath=file.getPath().toString();
			Conversor conversor=new Conversor(filePath);
			try {
				exito=conversor.convertir();
			}catch (Exception e) {
				JOptionPane.showMessageDialog(null, "No es un bmp indexado válido.");
			}
			
			if(exito)
				JOptionPane.showMessageDialog(null, "Convertido!!"); 

				 
		}
	}

}
