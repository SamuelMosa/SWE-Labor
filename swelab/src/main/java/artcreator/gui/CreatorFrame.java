package artcreator.gui;

import java.awt.image.BufferedImage;
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
        JLabel anzahlLabel = new JLabel("Anzahl Zahnstocher");
        this.panel.add(anzahlLabel);

        JTextField anzahlTextField = new JTextField();
        this.panel.add(anzahlTextField);

        // Abstand zwischen Zahnstocher
        JLabel abstandLabel = new JLabel("Abstand zwischen Zahnstocher");
        this.panel.add(abstandLabel);

        JTextField abstandTextField = new JTextField();
        JLabel abstandEinheitLabel = new JLabel("mm");
        JPanel abstandPanel = new JPanel();
        abstandPanel.add(abstandTextField);
        abstandPanel.add(abstandEinheitLabel);
        this.panel.add(abstandPanel);

        // Format Auswahl
        JLabel formatLabel = new JLabel("Format");
        this.panel.add(formatLabel);

        String[] formats = {"16:9", "4:3", "1:1"};
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
        JButton continueButton = new JButton("Weiter");
        this.panel.add(continueButton);

        this.getContentPane().add(this.panel);

        repaintView();
    }
    
    public JLabel getLeftImagePlaceHolder() {
    	return leftImagePlaceHolder;
    }
    
    public JLabel getRightImagePlaceHolder() {
    	return rightImagePlaceHolder;
    }
}
