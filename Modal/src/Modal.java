

import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Murilo Augusto Castagnoli de Quadros
 * @since 2014
 * @email macquadros@gmail.com
 */
public class Modal {

    private String[] mascaras;
    private char placeholder;
    private JFrame referencia;

    public Modal() {
        mascaras = new String[0];
        placeholder = '_';
    }
    
    public Modal(JFrame referencia) {
       this();
       this.referencia = referencia;
    }

    public void mensagem(String titulo, String mensagem, int tipo) {
        JOptionPane.showMessageDialog(referencia, titulo, mensagem, tipo);
    }

    public void info(String titulo, String mensagem) {
        mensagem(mensagem, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public void aviso(String titulo, String mensagem) {
        mensagem(mensagem, titulo, JOptionPane.WARNING_MESSAGE);
    }

    public void erro(String titulo, String mensagem) {
        mensagem(mensagem, titulo, JOptionPane.ERROR_MESSAGE);
    }

    public int box(String titulo, Object[] campos) {
        return JOptionPane.showConfirmDialog(referencia, campos, titulo, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    }

    public String[] formulario(String titulo, String... campos) {
        JTextField[] fields = new JTextField[campos.length];
        Object[] objetos = new Object[campos.length * 2];
        int indice = 0;
        for (int i = 0; i < campos.length; i++) {
            if (i < mascaras.length) {
                if (mascaras[i] != null) {
                    fields[i] = new JFormattedTextField(getMascara(mascaras[i]));
                } else {
                    fields[i] = new JTextField();
                }
            } else {
                fields[i] = new JTextField();
            }
            objetos[indice] = campos[i].concat(" :");
            indice++;
            objetos[indice] = fields[i];
            indice++;
        }
        mascaras = new String[0];
        if (box(titulo, objetos) == JOptionPane.OK_OPTION) {
            for (int i = 0; i < campos.length; i++) {
                campos[i] = fields[i].getText();
            }
            return campos;
        }
        return null;
    }

    public void setMascaras(String... mascaras) {
        this.mascaras = mascaras;
    }

    public void setMascaraPlaceholderCharacter(char placeholder) {
        this.placeholder = placeholder;
    }
    
    private MaskFormatter getMascara(String formato) {
        MaskFormatter mascara = new MaskFormatter();
        try {
            mascara.setMask(formato);  
            mascara.setPlaceholderCharacter(placeholder);
        } catch (ParseException e) {
            //TODO LOG
        }
        return mascara;
    }
    
}
