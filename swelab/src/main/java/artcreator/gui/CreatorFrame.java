package artcreator.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import artcreator.creator.CreatorFactory;
import artcreator.creator.impl.CreatorImpl;
import artcreator.creator.impl.Settings;
import artcreator.creator.port.Creator;
import artcreator.statemachine.StateMachineFactory;
import artcreator.statemachine.port.Observer;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.Subject;

public class CreatorFrame extends JFrame implements Observer {

    private Creator creator = CreatorFactory.FACTORY.creator();
    private Subject subject = StateMachineFactory.FACTORY.subject();
    private Controller controller;
    
    private static final int WIDTH = 600;
    private static final int HEIGHT = 500;

    private JButton btn = new JButton("Hello SWE");
    private JPanel panel = new JPanel();
	private JLabel leftImagePlaceHolder;
	private JLabel rightImagePlaceHolder;
	private JTextField distanceToothpickTextField;
	private JTextField numberToothpicksTextField;
	private Settings settings = new Settings();
	
    public CreatorFrame() {
        super("ArtCreator");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.subject.attach(this);
        this.controller = new Controller(this, subject, creator);

        this.controller = new StartingScreenController(this, subject, creator);

        /* build view */
        this.btn.addActionListener(this.controller);
        this.panel.add(this.btn);
        this.getContentPane().add(this.panel);

        CreatorImpl impl = new CreatorImpl(null, null);
        createStartScreenView();
    }

    public void update(State newState) {
        System.out.println("Neuer State: " + newState);

        if (newState.equals(State.S.IMAGE_IMPORTED)) {
            createImportImageView();
        } else if(newState.equals(State.S.SETTINGS)) {
        	createSettingsView();
        } else if(newState.equals(State.S.SETTINGS_DONE)) {
        	createTemplateView();
        }
    }

    private void createStartScreenView() {
        clearContent();

        this.controller = new StartingScreenController(this, subject, creator);
        this.btn.setText("Bild importieren");
        this.btn.addActionListener(this.controller);
        this.panel.add(this.btn);
        this.getContentPane().add(this.panel);

        repaintView();
    }

    private void clearContent() {
        this.getContentPane().removeAll();
        this.panel.removeAll();
    }

    private void repaintView() {
        this.panel.revalidate();
        this.panel.repaint();
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }

    private void createImportImageView() {
        clearContent();

        this.panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JLabel bildCount = new JLabel("1. Bildanzahl festlegen");
        this.panel.add(bildCount);

//        JRadioButton singleImage = new JRadioButton("Ein Bild");
        JRadioButton twoImages = new JRadioButton("Zwei Bilder");
        ButtonGroup group = new ButtonGroup();
//        group.add(singleImage);
        group.add(twoImages);
//        this.panel.add(singleImage);
        this.panel.add(twoImages);
        twoImages.setSelected(true);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        this.panel.add(separator);

        JLabel importImages = new JLabel("2. Bild festlegen");
        this.panel.add(importImages);

        JLabel leftImage = new JLabel("Linksbild");
        this.panel.add(leftImage);
        leftImagePlaceHolder = new JLabel();
        this.panel.add(leftImagePlaceHolder);
        ImportImageController importController = new ImportImageController(this, subject, creator);
        importController.setImageLabel(leftImagePlaceHolder); // Übergibt das Label an den Controller
        JButton leftButton = new JButton("Linkes Bild Importieren");
        leftButton.addActionListener(importController);
        this.panel.add(leftButton);
        
        JLabel rightImage = new JLabel("Rechtsbild");
        this.panel.add(rightImage);
        rightImagePlaceHolder = new JLabel();
        this.panel.add(rightImagePlaceHolder);
        importController.setImageLabel(rightImagePlaceHolder); // Übergibt das Label an den Controller
        JButton rightButton = new JButton("Rechtes Bild Importieren");
        rightButton.addActionListener(importController);
        this.panel.add(rightButton);
        
        SettingsController settingsController = new SettingsController(this, subject, creator);
        JButton settingsButton = new JButton("Einstellungen neu setzen");
        settingsButton.addActionListener(settingsController);
        this.panel.add(settingsButton);
        
		CreatorImpl impl = new CreatorImpl(null, null);
		impl.importImage(creator.getLeftImageFilePath(), creator.getRightImageFilePath());

        this.getContentPane().add(this.panel);

        repaintView();
    }
    
    private void createSettingsView() {
        clearContent();

        this.panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Titel
        JLabel titleLabel = new JLabel("Einstellungen für die Vorlage setzen");
        this.panel.add(titleLabel);

        // Anzahl Zahnstocher
        JLabel numberToothpickLabel = new JLabel("Anzahl Zahnstocher");
        this.panel.add(numberToothpickLabel);

        numberToothpicksTextField = new JTextField();
        this.panel.add(numberToothpicksTextField);

        // Abstand zwischen Zahnstocher
        JLabel distanceToothpickLabel = new JLabel("Abstand zwischen Zahnstocher");
        this.panel.add(distanceToothpickLabel);

        distanceToothpickTextField = new JTextField();
        JLabel abstandEinheitLabel = new JLabel("mm");
        JPanel abstandPanel = new JPanel();
        abstandPanel.add(distanceToothpickTextField);
        abstandPanel.add(abstandEinheitLabel);
        this.panel.add(abstandPanel);

        // Format Auswahl
        JLabel formatLabel = new JLabel("Format");
        this.panel.add(formatLabel);

        String[] formats = {"1:1"};
        JComboBox<String> formatComboBox = new JComboBox<>(formats);
        this.panel.add(formatComboBox);

        // Farbpalette setzen
        JLabel farbpaletteLabel = new JLabel("Farbpalette");
        this.panel.add(farbpaletteLabel);

        JButton farbpaletteButton = new JButton("Farbpalette setzen");
        this.panel.add(farbpaletteButton);

        // Einstellungen als Profil speichern Button
        JButton saveProfileButton = new JButton("Einstellungen als Profil speichern");
        this.panel.add(saveProfileButton);

        // Weiter Button
        SaveSettingsController saveSettingsController = new SaveSettingsController(this, subject, creator);
        JButton continueButton = new JButton("Weiter");
        this.panel.add(continueButton);
        continueButton.addActionListener(saveSettingsController);

        this.getContentPane().add(this.panel);

        repaintView();
    }
    
    private void createTemplateView() {
        clearContent();
        
        // Setze Layout
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        
        // Bildauswahl
        JLabel bildAuswahlLabel = new JLabel("Bildauswahl");
        bildAuswahlLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(bildAuswahlLabel);
        
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        JLabel leftImageLabel = new JLabel("Linksbild");
        JLabel rightImageLabel = new JLabel("Rechtsbild");
        
        JLabel leftImage = getLeftImagePlaceHolder();
        JLabel rightImage = getRightImagePlaceHolder();
        
        imagePanel.add(leftImageLabel);
        imagePanel.add(leftImage);
        imagePanel.add(rightImageLabel);
        imagePanel.add(rightImage);
        
        panel.add(imagePanel);
        
        // Einstellungen
        JLabel settingsLabel = new JLabel("Einstellungen");
        settingsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(settingsLabel);
        
        JPanel settingsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        
        // Anzahl Zahnstocher
        settingsPanel.add(new JLabel("Anzahl Zahnstocher"));
        JLabel toothpickCountField = new JLabel(String.valueOf(settings.getNumToothpicks()));
        settingsPanel.add(toothpickCountField);
        
        // Abstand zwischen Zahnstocher
        settingsPanel.add(new JLabel("Abstand zwischen Zahnstocher"));
        JPanel spacingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel spacingField = new JLabel(String.valueOf(settings.getToothpickDistance()));
        System.out.println(settings.getToothpickDistance());
        spacingPanel.add(spacingField);
        spacingPanel.add(new JLabel("mm"));
        settingsPanel.add(spacingPanel);
        
        // Format
        settingsPanel.add(new JLabel("Format"));
        JLabel aspectRatioLabel = new JLabel("1:1");
        settingsPanel.add(aspectRatioLabel);
        
        // Farbpalette
        settingsPanel.add(new JLabel("Farbpalette"));
        JLabel colorPaletteLabel = new JLabel("TBD"); // Placeholder
        settingsPanel.add(colorPaletteLabel);
        
        panel.add(settingsPanel);
        
        // Generate Button
        JButton generateButton = new JButton("Vorlage generieren");
        generateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        generateButton.addActionListener(controller); // Assuming controller handles button actions
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing
        panel.add(generateButton);
        
        // Füge das Panel zum Content Pane hinzu
        getContentPane().add(panel);
        
        repaintView();
    }
    
    public void setSettingsToothpicksDistance(float distance) {
    	settings.setToothpickDistance(distance);
    }
    
    public void setSettingsToothpicksNumber(int number) {
    	settings.setToothpickDistance(number);
    }
    
    public Settings getSettings() {
    	return settings;
    }
    
    public float getDistanceToothpicks() {
    	return  Float.parseFloat(distanceToothpickTextField.getText());
    }
    
    public int getNumberToothpicks() {
    	return Integer.parseInt(numberToothpicksTextField.getText());
    }
        
    public JLabel getLeftImagePlaceHolder() {
    	return leftImagePlaceHolder;
    }
    
    public JLabel getRightImagePlaceHolder() {
    	return rightImagePlaceHolder;
    }
}
