import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.io.*;

public class GUI extends JFrame implements ComponentListener, ActionListener {
    private JMenuBar mb;
    private JMenu x;
    private JMenuItem m1, m2, m3,m4;
    private JTextArea ta1;
    private JScrollPane sp1;
    private JButton btnLimpiar,btnTokens;
    private ImageIcon image;


    public GUI(){
        super("Cicada");
        Interfaz();
        Escuchadores();
    }

    public void Interfaz(){
        //cosas genericas de la interfaz
        setSize(1280,720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        //instanciar los componentes de la interfaz
        image = new ImageIcon("cicada.png");

        mb = new JMenuBar();
        x = new JMenu("Menu");

        m1 = new JMenuItem("Abrir");
        m2 = new JMenuItem("Guardar");
        m3 = new JMenuItem("Gramatica");
        m4 = new JMenuItem("abrir");

        ta1 = new JTextArea();
        sp1 = new JScrollPane(ta1);

        btnLimpiar = new JButton("limpiar");
        btnTokens = new JButton("Tokens");

        x.add(m1);
        x.add(m2);
        x.add(m3);
        x.add(m4);

        mb.add(x);
        setJMenuBar(mb);
        setIconImage(image.getImage());
        setVisible(true);
    }

    public void Escuchadores(){
        this.addComponentListener(this);
        m1.addActionListener(this);
        m2.addActionListener(this);
        m3.addActionListener(this);
        btnLimpiar.addActionListener(this);
        btnTokens.addActionListener(this);
    }

    public void abrirArchivo(){
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files","txt");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                // leer el archivo seleccionado y agregar el texto al TextArea
                BufferedReader br = new BufferedReader(new FileReader(selectedFile));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    content.append(line).append("\n");
                }
                br.close();
                ta1.setText(content.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void guardarArchivo(){
        JFileChooser fileChooser = new JFileChooser();
        // Set up a file filter if needed
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                //extraer el texto del JTextArea
                String textToSave = ta1.getText();
                // Escribir el texto al archivo
                FileWriter fileWriter = new FileWriter(selectedFile);
                fileWriter.write(textToSave);
                fileWriter.close();
                JOptionPane.showMessageDialog(null, "El archivo se ha guardado exitosamente.");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error al guardar el archivo.");
            }
        }
    }

    public void abrirGramatica() {
        String nombreArchivo = "gramatica.txt";

        try {
            File archivo = new File(nombreArchivo);

            if (archivo.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(archivo));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    content.append(line).append("\n");
                }
                br.close();
                ta1.setText(content.toString());
            } else {
                JOptionPane.showMessageDialog(this, "El archivo no existe.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al abrir el archivo: " + e.getMessage());
        }
    }

//darles una posicion y tamaño relativos a los componentes
    @Override
    public void componentResized(ComponentEvent e) {
        int w = this.getWidth();
        int h = this.getHeight();
        //posicion del textArea
        int textAreaX = (int)(w*0.03);
        int textAreaY = (int)(h*0.05);
        //tamaño del textArea
        int textAreaWidth = (int) (w * .40);
        int textAreaHeight = (int) (h * .70);
        //darle las medidas al scrollpane/textarea
        sp1.setBounds(textAreaX,textAreaY,textAreaWidth,textAreaHeight);

        //posicion del boton limpiar
        int btnLimpiarX = (int)(w*0.03);
        int btnLimpiarY = textAreaY + textAreaHeight +5;
        //tamaño del boton limpiar
        int btnLimpiarWidth = (int) (w * .07);
        int btnLimpiarHeight = (int) (h * .03);
        //darle las medidas al btnLimpiar
        btnLimpiar.setBounds(btnLimpiarX,btnLimpiarY,btnLimpiarWidth,btnLimpiarHeight);

        //posicion del boton tokens
        int btnTokensX = (textAreaX + textAreaWidth)- btnLimpiarWidth;
        int btnTokensY = textAreaY + textAreaHeight +5;
        //tamaño del boton tokens
        int btnTokensWidth = (int) (w * .07);
        int btnTokensHeight = (int) (h * .03);
        //darle las medidas al btn tokens
        btnTokens.setBounds(btnTokensX,btnTokensY,btnTokensWidth,btnTokensHeight);

        add(sp1);
        add(btnLimpiar);
        add(btnTokens);
        validate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == m1){
            abrirArchivo();
            return;
        }
        if (e.getSource() == m2){
            guardarArchivo();
            return;
        }
        if (e.getSource() == m3){
            abrirGramatica();
            return;
        }
        if (e.getSource() == btnLimpiar){
            ta1.setText("");
            return;
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    //main para ejecutar el programa
    public static void main(String[] args){
        new GUI();
    }


}
