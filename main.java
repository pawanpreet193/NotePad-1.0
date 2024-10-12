package NotePad;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class NotepadApp extends JFrame implements ActionListener {

    // Text Area for the notepad
    JTextArea textArea;

    // Menu components
    JMenuBar menuBar;
    JMenu fileMenu, editMenu, formatMenu, viewMenu, styleMenu;
    JMenuItem newItem, openItem, saveItem, exitItem;
    JMenuItem cutItem, copyItem, pasteItem, undoItem, redoItem, fontItem, fontSizeItem, fontColorItem, backgroundColorItem, boldItem, italicItem, plainItem;
    JCheckBoxMenuItem darkModeItem;

    // For undo/redo functionality
    UndoManager undoManager;

    // Constructor to initialize Notepad
    public NotepadApp() {
        // Create the text area
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane);

        // Initialize the undo manager
        undoManager = new UndoManager();
        textArea.getDocument().addUndoableEditListener(undoManager);

        // Create the menu bar
        menuBar = new JMenuBar();

        // Create File menu
        fileMenu = new JMenu("File");
        newItem = new JMenuItem("New");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Create Edit menu
        editMenu = new JMenu("Edit");
        cutItem = new JMenuItem("Cut");
        copyItem = new JMenuItem("Copy");
        pasteItem = new JMenuItem("Paste");
        undoItem = new JMenuItem("Undo");
        redoItem = new JMenuItem("Redo");

        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.addSeparator();
        editMenu.add(undoItem);
        editMenu.add(redoItem);

        // Create Format menu
        formatMenu = new JMenu("Format");
        fontItem = new JMenuItem("Font");
        fontSizeItem = new JMenuItem("Font Size");
        fontColorItem = new JMenuItem("Font Color");
        backgroundColorItem = new JMenuItem("Background Color");

        formatMenu.add(fontItem);
        formatMenu.add(fontSizeItem);
        formatMenu.add(fontColorItem);
        formatMenu.add(backgroundColorItem);


        //Create Style menu
        styleMenu = new JMenu("Style");
        boldItem = new JMenuItem("Bold");
        plainItem = new JMenuItem("Plain");
        italicItem = new JMenuItem("Italic");

        styleMenu.add(boldItem);
        styleMenu.add(plainItem);
        styleMenu.add(italicItem);

        // Create View menu for Dark Mode
        viewMenu = new JMenu("View");
        darkModeItem = new JCheckBoxMenuItem("Dark Mode");

        viewMenu.add(darkModeItem);

        // Add menus to the menu bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(viewMenu);
        menuBar.add(styleMenu);

        //

        // Add menu bar to the frame
        setJMenuBar(menuBar);

        // Add action listeners to menu items
        newItem.addActionListener(this);
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);
        cutItem.addActionListener(this);
        copyItem.addActionListener(this);
        pasteItem.addActionListener(this);
        undoItem.addActionListener(this);
        redoItem.addActionListener(this);
        fontItem.addActionListener(this);
        fontSizeItem.addActionListener(this);
        fontColorItem.addActionListener(this);
        backgroundColorItem.addActionListener(this);
        darkModeItem.addActionListener(this);
        boldItem.addActionListener(this);
        italicItem.addActionListener(this);
        plainItem.addActionListener(this);

        // Frame settings
        setTitle("Notepad");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // Event handling for menu items
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "New":
                textArea.setText("");
                break;
            case "Open":
                openFile();
                break;
            case "Save":
                saveFile();
                break;
            case "Exit":
                System.exit(0);
                break;
            case "Cut":
                textArea.cut();
                break;
            case "Copy":
                textArea.copy();
                break;
            case "Paste":
                textArea.paste();
                break;
            case "Undo":
                if (undoManager.canUndo()) {
                    undoManager.undo();
                }
                break;
            case "Redo":
                if (undoManager.canRedo()) {
                    undoManager.redo();
                }
                break;
            case "Font":
                changeFont();
                break;
            case "Font Size":
                changeFontSize();
                break;
            case "Font Color":
                changeFontColor();
                break;
            case "Background Color":
                changeBackgroundColor();
                break;
            case "Bold":
                changeFontStyle(Font.BOLD);
                break;
            case "Italic":
                changeFontStyle(Font.ITALIC);
                break;
            case "Plain":
                changeFontStyle(Font.PLAIN);
                break;

        }

        // Handle Dark Mode toggle
        if (e.getSource() == darkModeItem) {
            if (darkModeItem.isSelected()) {
                enableDarkMode();
            } else {
                disableDarkMode();
            }
        }
    }

    // Method to open a file
    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                textArea.read(reader, null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Method to save a file
    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                textArea.write(writer);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Method to change the font of the text
    private void changeFont() {
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        String fontName = (String) JOptionPane.showInputDialog(this, "Choose a font", "Font", JOptionPane.PLAIN_MESSAGE, null, fonts, fonts[0]);
        if (fontName != null) {
            textArea.setFont(new Font(fontName, Font.PLAIN, textArea.getFont().getSize()));
        }
    }

    // Method to change font style
//    private void changeFontStyle(int style){
//        Font currentFont = textArea.getFont();
//        //textArea.setFont(new Font(currentFont.getFontName(),style, currentFont.getSize()));
//       //Font newFont = new Font(currentFont.getFontName(), style, currentFont.getSize());
//       //textArea.setFont(newFont);
//
//        if(style == Font.PLAIN){
//            textArea.setFont(new Font(currentFont.getFontName(), Font.PLAIN, currentFont.getSize()));
//        }
//
//        else{
//            int newStyle = style ;
//            textArea.setFont(new Font(currentFont.getFontName(), newStyle, currentFont.getSize()));
//        }
//    }

    // Method to change font style (bold, italic, plain)
    private void changeFontStyle(int style) {
        Font currentFont = textArea.getFont();
        int currentStyle = currentFont.getStyle();

        // Toggle between plain, bold, italic, and bold+italic
        if (style == Font.PLAIN) {
            textArea.setFont(new Font(currentFont.getFontName(), Font.PLAIN, currentFont.getSize()));
        } else if (style == Font.BOLD) {
            // If current style includes BOLD, remove it; otherwise, add it
            int newStyle = (currentStyle & Font.BOLD) == Font.BOLD ? (currentStyle & ~Font.BOLD) : (currentStyle | Font.BOLD);
            textArea.setFont(new Font(currentFont.getFontName(), newStyle, currentFont.getSize()));
        } else if (style == Font.ITALIC) {
            // If current style includes ITALIC, remove it; otherwise, add it
            int newStyle = (currentStyle & Font.ITALIC) == Font.ITALIC ? (currentStyle & ~Font.ITALIC) : (currentStyle | Font.ITALIC);
            textArea.setFont(new Font(currentFont.getFontName(), newStyle, currentFont.getSize()));
        }
    }


    // Method to change the font size of the text
    private void changeFontSize() {
        Integer[] sizes = new Integer[63];
        for (int i = 0, size = 10; i < sizes.length; i++, size++) {
            sizes[i] = size;
        }

        Integer fontSize = (Integer) JOptionPane.showInputDialog(this, "Choose a font size", "Font Size",
                JOptionPane.PLAIN_MESSAGE, null, sizes, textArea.getFont().getSize());

        if (fontSize != null) {
            textArea.setFont(new Font(textArea.getFont().getFontName(), Font.PLAIN, fontSize));
        }
    }

    // Method to change the font color of the text
    private void changeFontColor() {
        Color color = JColorChooser.showDialog(this, "Choose Font Color", textArea.getForeground());
        if (color != null) {
            textArea.setForeground(color);
        }
    }

    // Method to change the background color of the text area
    private void changeBackgroundColor() {
        Color color = JColorChooser.showDialog(this, "Choose Background Color", textArea.getBackground());
        if (color != null) {
            textArea.setBackground(color);
        }
    }

    // Method to enable dark mode
    private void enableDarkMode() {
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);
    }

    // Method to disable dark mode (return to light mode)
    private void disableDarkMode() {
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.BLACK);
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NotepadApp().setVisible(true);
        });
    }
}
