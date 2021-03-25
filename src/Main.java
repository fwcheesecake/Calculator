import components.Colors;
import components.FancyButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;
import java.io.InputStream;

public class Main extends JFrame implements ActionListener, ComponentListener {
    private final JTextField expresionTextField;
    private final JTextField resultTextfield;

    private final JPanel buttonsPanel;

    private final String[] buttonsText = {
            "\uF106",   "C",    "\uF359",   "\uF069",
            "7",        "8",    "9",        "\uF529",
            "4",        "5",    "6",        "\uF068",
            "1",        "2",    "3",        "\uF067",
            "\uF541",   "0",    ".",        "\uF52C"};
    private final FancyButton[] buttons = new FancyButton[20];

    private Font fontAwesome, fontDisplay;

    Main() throws IOException, FontFormatException {
        InputStream awesomeFontIS = getClass().getResourceAsStream("/fonts/Font Awesome 5 Free-Solid-900.otf");
        fontAwesome = Font.createFont(Font.TRUETYPE_FONT, awesomeFontIS);
        fontAwesome = fontAwesome.deriveFont(Font.PLAIN, 25);

        InputStream displayFontIS = getClass().getResourceAsStream("/fonts/Calculator.ttf");
        fontDisplay = Font.createFont(Font.TRUETYPE_FONT, displayFontIS);
        fontDisplay = fontDisplay.deriveFont(Font.PLAIN, 25);

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        expresionTextField = new JTextField();
        expresionTextField.setPreferredSize(new Dimension(400, 60));
        expresionTextField.setBorder(null);
        expresionTextField.setFont(fontDisplay);
        getContentPane().add(expresionTextField);

        resultTextfield = new JTextField();
        resultTextfield.setPreferredSize(new Dimension(400, 60));
        resultTextfield.setFont(fontDisplay);
        resultTextfield.setBorder(null);
        resultTextfield.setEditable(false);
        resultTextfield.setHorizontalAlignment(JTextField.TRAILING);
        getContentPane().add(resultTextfield);

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setPreferredSize(new Dimension(400, 480));
        getContentPane().add(buttonsPanel);

        for(int i = 0; i < 20; i++) {
            buttons[i] = new FancyButton(buttonsText[i]);
            buttons[i].setBackground(Colors.NON_DIGIT_BUTTON);
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setBorderPainted(false);
            buttons[i].setFont(fontAwesome);
            buttons[i].setVerticalAlignment(JButton.CENTER);
            buttons[i].setFocusPainted(false);
            buttons[i].addActionListener(this);
            buttonsPanel.add(buttons[i]);
        }
        getContentPane().add(buttonsPanel);

        int w = 400, h = 600;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((int) (screen.getWidth() - w) / 2, (int) (screen.getHeight() - h) / 2, w, h);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        addComponentListener(this);
    }

    private  void changeFontSize() {
        for(int i = 0; i < 20; i++)
            buttons[i].setFont(fontAwesome);
        expresionTextField.setFont(fontDisplay);
        resultTextfield.setFont(fontDisplay);
    }

    public static void main(String[] args) {
        try {
            Main mainFrame = new Main();
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buttons[0])
            expresionTextField.setText(expresionTextField.getText() + "^");
        else if(e.getSource() == buttons[1]) {
            expresionTextField.setText("");
            resultTextfield.setText("");
        }
        else if(e.getSource() == buttons[2])
            expresionTextField.setText(expresionTextField.getText().substring(0, expresionTextField.getText().length() - 1));
        else if(e.getSource() == buttons[3])
            expresionTextField.setText(expresionTextField.getText() + "*");
        else if(e.getSource() == buttons[4])
            expresionTextField.setText(expresionTextField.getText() + "7");
        else if(e.getSource() == buttons[5])
            expresionTextField.setText(expresionTextField.getText() + "8");
        else if(e.getSource() == buttons[6])
            expresionTextField.setText(expresionTextField.getText() + "9");
        else if(e.getSource() == buttons[7])
            expresionTextField.setText(expresionTextField.getText() + "/");
        else if(e.getSource() == buttons[8])
            expresionTextField.setText(expresionTextField.getText() + "4");
        else if(e.getSource() == buttons[9])
            expresionTextField.setText(expresionTextField.getText() + "5");
        else if(e.getSource() == buttons[10])
            expresionTextField.setText(expresionTextField.getText() + "6");
        else if(e.getSource() == buttons[11])
            expresionTextField.setText(expresionTextField.getText() + "-");
        else if(e.getSource() == buttons[12])
            expresionTextField.setText(expresionTextField.getText() + "1");
        else if(e.getSource() == buttons[13])
            expresionTextField.setText(expresionTextField.getText() + "2");
        else if(e.getSource() == buttons[14])
            expresionTextField.setText(expresionTextField.getText() + "3");
        else if(e.getSource() == buttons[15])
            expresionTextField.setText(expresionTextField.getText() + "+");
        else if(e.getSource() == buttons[16])
            expresionTextField.setText(expresionTextField.getText() + "%");
        else if(e.getSource() == buttons[17])
            expresionTextField.setText(expresionTextField.getText() + "0");
        else if(e.getSource() == buttons[18])
            expresionTextField.setText(expresionTextField.getText() + ".");
        //else if(e.getSource() == buttons[19]) {
        else {
            String result = expresionTextField.getText();
            result = Calculus.infixToPostfix(result);
            resultTextfield.setText(Calculus.doOperations(result));
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        int m = Math.min(getWidth(), getHeight());
        float size = (float) (m * 0.0625);
        fontDisplay = fontDisplay.deriveFont(1, size);
        fontAwesome = fontAwesome.deriveFont(1, size);
        changeFontSize();
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
}
