package artcreator.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
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
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import artcreator.creator.CreatorFactory;
import artcreator.creator.impl.CreatorImpl;
import artcreator.creator.impl.Settings;
import artcreator.creator.impl.Template;
import artcreator.creator.port.Creator;
import artcreator.statemachine.StateMachineFactory;
import artcreator.statemachine.port.Observer;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.Subject;

public class CreatorFrame extends JFrame implements Observer {

	private String leftFilePath;
	private String rightFilePath;

	private Creator creator = CreatorFactory.FACTORY.creator();
	private Subject subject = StateMachineFactory.FACTORY.subject();
	private Controller controller;

	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	private JButton btn = new JButton("Hello SWE");
	private JPanel panel = new JPanel();
	private JLabel leftImagePlaceHolder;
	private JLabel rightImagePlaceHolder;
	private JTextField distanceToothpickTextField;
	private JTextField numberToothpicksTextField;
	private Settings settings = new Settings();
	private BufferedImage[] importedImages;

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
		} else if (newState.equals(State.S.SETTINGS)) {
			createSettingsView();
		} else if (newState.equals(State.S.SETTINGS_DONE)) {
			createTemplateView();
		} else if (newState.equals(State.S.TEMPLATE_GENERATED)) {
			createGenerateTemplateView();
		}
		;
	}

	private void createStartScreenView() {
		clearContent();

		this.controller = new StartingScreenController(this, subject, creator);
		this.btn.setText("Bild importieren");
		this.btn.addActionListener(this.controller);

		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 10, 10, 10);
		mainPanel.add(this.btn, gbc);

		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		// this.panel.add(this.btn);
		// this.getContentPane().add(this.panel);

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

		this.panel.setLayout(new BorderLayout());

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.panel.add(contentPanel, BorderLayout.CENTER);

		// Step 1: Bildanzahl festlegen
		JPanel bildCountPanel = new JPanel();
		bildCountPanel.setLayout(new BoxLayout(bildCountPanel, BoxLayout.Y_AXIS));
		bildCountPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		bildCountPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		JLabel bildCount = new JLabel("1. Bildanzahl festlegen");
		bildCount.setAlignmentX(Component.LEFT_ALIGNMENT);
		bildCountPanel.add(bildCount);

		JRadioButton twoImages = new JRadioButton("Zwei Bilder");
		twoImages.setAlignmentX(Component.LEFT_ALIGNMENT);
		ButtonGroup group = new ButtonGroup();
		group.add(twoImages);
		bildCountPanel.add(twoImages);
		twoImages.setSelected(true);

		contentPanel.add(bildCountPanel);

		// Separator
		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
		contentPanel.add(separator);

		// Step 2: Bild festlegen
		JPanel importImagesPanel = new JPanel();
		importImagesPanel.setLayout(new GridLayout(2, 2, 10, 10));
		importImagesPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		importImagesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel importImages = new JLabel("2. Bild festlegen");
		importImages.setAlignmentX(Component.LEFT_ALIGNMENT);
		contentPanel.add(importImages);

		JLabel leftImage = new JLabel("Linksbild");
		leftImage.setAlignmentX(Component.LEFT_ALIGNMENT);
		importImagesPanel.add(leftImage);
		JLabel rightImage = new JLabel("Rechtsbild");
		rightImage.setAlignmentX(Component.LEFT_ALIGNMENT);
		importImagesPanel.add(rightImage);

		leftImagePlaceHolder = new JLabel();
		leftImagePlaceHolder.setPreferredSize(new Dimension(200, 200));
		leftImagePlaceHolder.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		importImagesPanel.add(leftImagePlaceHolder);

		rightImagePlaceHolder = new JLabel();
		rightImagePlaceHolder.setPreferredSize(new Dimension(200, 200));
		rightImagePlaceHolder.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		importImagesPanel.add(rightImagePlaceHolder);

		contentPanel.add(importImagesPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2, 10, 10));
		buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		ImportImageController importController = new ImportImageController(this, subject, creator);

		JButton leftButton = new JButton("Linkes Bild Importieren");
		leftButton.setPreferredSize(new Dimension(200, 30));
		leftButton.addActionListener(importController);
		buttonPanel.add(leftButton);

		JButton rightButton = new JButton("Rechtes Bild Importieren");
		rightButton.setPreferredSize(new Dimension(200, 30));
		rightButton.addActionListener(importController);
		buttonPanel.add(rightButton);

		contentPanel.add(buttonPanel);

		JPanel settingsPanel = new JPanel();
		settingsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		SettingsController settingsController = new SettingsController(this, subject, creator);
		JButton settingsButton = new JButton("Einstellungen neu setzen");
		settingsButton.addActionListener(settingsController);
		settingsPanel.add(settingsButton);

		JButton profileButton = new JButton("Profil verwenden");
		profileButton.addActionListener(e -> {
		});
		settingsPanel.add(profileButton);

		this.panel.add(settingsPanel, BorderLayout.SOUTH);

		CreatorImpl impl = new CreatorImpl(null, null);
		importedImages = impl.importImage(creator.getLeftImageFilePath(), creator.getRightImageFilePath());

		this.getContentPane().add(this.panel);

		repaintView();
	}

	private void createSettingsView() {
		clearContent();

		CreatorImpl impl = new CreatorImpl(null, null);
		this.panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		System.out.println("Left file path: " + leftFilePath + ", Right file path" + rightFilePath);
		importedImages = impl.importImage(leftFilePath, rightFilePath);

		this.panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;

		JLabel titleLabel = new JLabel("Einstellungen für die Vorlage setzen");
		gbc.gridwidth = 2;
		panel.add(titleLabel, gbc);
		gbc.gridwidth = 1;
		gbc.gridy++;

		JLabel numberToothpickLabel = new JLabel("Anzahl Zahnstocher");
		panel.add(numberToothpickLabel, gbc);
		gbc.gridx = 1;
		numberToothpicksTextField = new JTextField(20);
		panel.add(numberToothpicksTextField, gbc);
		gbc.gridx = 0;
		gbc.gridy++;

		JLabel distanceToothpickLabel = new JLabel("Abstand zwischen Zahnstocher");
		panel.add(distanceToothpickLabel, gbc);
		gbc.gridx = 1;
		JPanel abstandPanel = new JPanel(new BorderLayout());
		distanceToothpickTextField = new JTextField(20);
		abstandPanel.add(distanceToothpickTextField, BorderLayout.CENTER);
		JLabel abstandEinheitLabel = new JLabel("mm");
		abstandPanel.add(abstandEinheitLabel, BorderLayout.EAST);
		panel.add(abstandPanel, gbc);
		gbc.gridx = 0;
		gbc.gridy++;

		JLabel formatLabel = new JLabel("Format");
		panel.add(formatLabel, gbc);
		gbc.gridx = 1;
		String[] formats = { "1:1" };
		JComboBox<String> formatComboBox = new JComboBox<>(formats);
		panel.add(formatComboBox, gbc);
		gbc.gridx = 0;
		gbc.gridy++;

		ChooseColorPaletteController chooseColorPaletteController = new ChooseColorPaletteController(this, subject,
				creator);
		JLabel farbpaletteLabel = new JLabel("Farbpalette");
		panel.add(farbpaletteLabel, gbc);
		gbc.gridx = 1;
		JButton farbpaletteButton = new JButton("Farbpalette setzen");
		panel.add(farbpaletteButton, gbc);
		gbc.gridx = 0;
		gbc.gridy++;
		farbpaletteButton.addActionListener(chooseColorPaletteController);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.weighty = 1.0;
		panel.add(Box.createVerticalGlue(), gbc);
		gbc.weighty = 0;

		JButton saveProfileButton = new JButton("Einstellungen als Profil speichern");
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy += 2;
		panel.add(saveProfileButton, gbc);

		SaveSettingsController saveSettingsController = new SaveSettingsController(this, subject, creator);
		JButton continueButton = new JButton("Weiter");
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.EAST;
		continueButton.addActionListener(saveSettingsController);
		panel.add(continueButton, gbc);

		this.getContentPane().add(this.panel);
		repaintView();
	}

	private void createTemplateView() {
		clearContent();

		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;

		JLabel bildAuswahlLabel = new JLabel("Bildauswahl");
		panel.add(bildAuswahlLabel, gbc);

		gbc.gridy++;
		JPanel imagePanel = new JPanel(new GridBagLayout());
		GridBagConstraints imgGbc = new GridBagConstraints();
		imgGbc.insets = new Insets(10, 10, 10, 10);
		imgGbc.gridx = 0;
		imgGbc.gridy = 0;
		imgGbc.anchor = GridBagConstraints.CENTER;

		JLabel leftImageLabel = new JLabel("Linksbild");
		imagePanel.add(leftImageLabel, imgGbc);
		imgGbc.gridy++;
		JLabel leftImage = getLeftImagePlaceHolder();
		imagePanel.add(leftImage, imgGbc);
		leftImage.setPreferredSize(new Dimension(200, 200));

		imgGbc.gridx++;
		imgGbc.gridy = 0;
		JLabel rightImageLabel = new JLabel("Rechtsbild");
		imagePanel.add(rightImageLabel, imgGbc);
		imgGbc.gridy++;
		JLabel rightImage = getRightImagePlaceHolder();
		imagePanel.add(rightImage, imgGbc);
		rightImage.setPreferredSize(new Dimension(200, 200));

		gbc.gridwidth = 2;
		panel.add(imagePanel, gbc);
		gbc.gridwidth = 1;
		gbc.gridy++;

		JLabel settingsLabel = new JLabel("Einstellungen");
		panel.add(settingsLabel, gbc);

		gbc.gridy++;
		JPanel settingsPanel = new JPanel(new GridBagLayout());
		GridBagConstraints settingsGbc = new GridBagConstraints();
		settingsGbc.insets = new Insets(10, 10, 10, 10);
		settingsGbc.gridx = 0;
		settingsGbc.gridy = 0;
		settingsGbc.fill = GridBagConstraints.HORIZONTAL;

		settingsPanel.add(new JLabel("Anzahl Zahnstocher"), settingsGbc);
		settingsGbc.gridx++;
		JLabel toothpickCountField = new JLabel(String.valueOf(settings.getNumToothpicks()));
		settingsPanel.add(toothpickCountField, settingsGbc);

		settingsGbc.gridx = 0;
		settingsGbc.gridy++;
		settingsPanel.add(new JLabel("Abstand zwischen Zahnstocher"), settingsGbc);
		settingsGbc.gridx++;
		JPanel spacingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel spacingField = new JLabel(String.valueOf(settings.getToothpickDistance()));
		spacingPanel.add(spacingField);
		spacingPanel.add(new JLabel("mm"));
		settingsPanel.add(spacingPanel, settingsGbc);

		settingsGbc.gridx = 0;
		settingsGbc.gridy++;
		settingsPanel.add(new JLabel("Format"), settingsGbc);
		settingsGbc.gridx++;
		JLabel aspectRatioLabel = new JLabel("1:1");
		settingsPanel.add(aspectRatioLabel, settingsGbc);

		settingsGbc.gridx = 0;
		settingsGbc.gridy++;
		settingsPanel.add(new JLabel("Farbpalette"), settingsGbc);
		settingsGbc.gridx++;
		JLabel colorPaletteLabel = new JLabel("TBD"); // Placeholder
		settingsPanel.add(colorPaletteLabel, settingsGbc);

		gbc.gridwidth = 2;
		panel.add(settingsPanel, gbc);
		gbc.gridwidth = 1;
		gbc.gridy++;

		JButton generateButton = new JButton("Vorlage generieren");
		generateButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		GenerateTemplateController generateTemplateController = new GenerateTemplateController(this, subject, creator);
		generateButton.addActionListener(generateTemplateController); // Assuming controller handles button actions
		gbc.gridy++;
		panel.add(generateButton, gbc);

		getContentPane().add(panel);

		repaintView();
	}

	private void createGenerateTemplateView() {
		clearContent();
		
		int prefImgSize = 600;

		CreatorImpl creatorImpl = new CreatorImpl(null, null);
		
		Template template = creatorImpl.generateTemplate(importedImages[0], importedImages[1], settings);

		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;

		JLabel bildAuswahlLabel = new JLabel("Vorlage");
		panel.add(bildAuswahlLabel, gbc);

		gbc.gridy++;
		JPanel imagePanel = new JPanel(new GridBagLayout());
		GridBagConstraints imgGbc = new GridBagConstraints();
		imgGbc.insets = new Insets(10, 10, 10, 10);
		imgGbc.gridx = 0;
		imgGbc.gridy = 0;
		imgGbc.anchor = GridBagConstraints.CENTER;

		
		Image scaledLeftImage = template.getLeftImage().getScaledInstance(prefImgSize, prefImgSize, Image.SCALE_SMOOTH);
		Image scaledRightImage = template.getRightImage().getScaledInstance(prefImgSize, prefImgSize, Image.SCALE_SMOOTH);
		
		JLabel leftImageLabel = new JLabel("Linksbild");
		imagePanel.add(leftImageLabel, imgGbc);
		imgGbc.gridy++;
		JLabel leftImage = new JLabel();
		leftImageLabel.setIcon(new ImageIcon(scaledLeftImage));
		imagePanel.add(leftImage, imgGbc);
		leftImage.setPreferredSize(new Dimension(prefImgSize, prefImgSize));
		

		imgGbc.gridx++;
		imgGbc.gridy = 0;
		JLabel rightImageLabel = new JLabel("Rechtsbild");
		rightImageLabel.setIcon(new ImageIcon(scaledRightImage));
		imagePanel.add(rightImageLabel, imgGbc);
		imgGbc.gridy++;
		JLabel rightImage = new JLabel();
		imagePanel.add(rightImage, imgGbc);
		rightImage.setPreferredSize(new Dimension(prefImgSize, prefImgSize));
		
		panel.add(imagePanel, gbc);

		getContentPane().add(panel);

		repaintView();
	}

	public void createColorPaletteView() {
		// Create a new JFrame for the popup window
		JFrame popupFrame = new JFrame("Popup Window");

		// Set layout to BorderLayout
		popupFrame.setLayout(new BorderLayout());
		popupFrame.setSize(600, 600);

		// Create a DefaultTableModel for the table
		DefaultTableModel tableModel = new DefaultTableModel(new Object[] { "Color ID", "Color Code" }, 0);
		JTable table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);

		// Create and add the text field and button panel to the center
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		JTextField textField = new JTextField(20);
		JButton actionButton = new JButton("Add Entry");

		// Create an instance of AddColorController
		AddColorController controller = new AddColorController(this, subject, creator, textField, actionButton,
				tableModel);

		centerPanel.add(textField);
		centerPanel.add(actionButton);

		// Create a panel to hold the center components and the table
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(centerPanel, BorderLayout.NORTH);
		mainPanel.add(scrollPane, BorderLayout.CENTER);

		// Add the main panel to the content panel
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(mainPanel, BorderLayout.CENTER);

		// Create buttons for bottom left and bottom right
//        JButton removeColorButton = new JButton("Farbe entfernen");
//        JButton addColorButton = new JButton("Farbe hinzufuegen");
//
//        // Create a panel for the buttons with FlowLayout
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
//        buttonPanel.add(removeColorButton);

		JPanel buttonPanelRight = new JPanel();
		buttonPanelRight.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		// buttonPanelRight.add(addColorButton);

		// Create a panel to hold both button panels
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		// southPanel.add(buttonPanel, BorderLayout.WEST);
		southPanel.add(buttonPanelRight, BorderLayout.EAST);

		// Add the content and button panels to the popup frame
		popupFrame.add(contentPanel, BorderLayout.CENTER);
		popupFrame.add(southPanel, BorderLayout.SOUTH);

		// Center the popup window on the screen
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int x = (screenSize.width - popupFrame.getWidth()) / 2;
		int y = (screenSize.height - popupFrame.getHeight()) / 2;
		popupFrame.setLocation(x, y);

		// Set default close operation
		popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Make the popup window visible
		popupFrame.setVisible(true);
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
		return Float.parseFloat(distanceToothpickTextField.getText());
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

	public String getLeftFilePath() {
		return leftFilePath;
	}

	public void setLeftFilePath(String leftFilePath) {
		this.leftFilePath = leftFilePath;
	}

	public String getRightFilePath() {
		return rightFilePath;
	}

	public void setRightFilePath(String rightFilePath) {
		this.rightFilePath = rightFilePath;
	}

}
