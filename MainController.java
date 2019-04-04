package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class MainController {
	private static FileChooser fileChooser = new FileChooser();
	private static BlackAndWhiteFilter bwFilter = new BlackAndWhiteFilter(127);
	public static BufferedImage selectedImage;
	public static BufferedImage binaryImage;
	public static BufferedImage birdImage;
	private static BirdFinder boxFilter = new BirdFinder(binaryImage);
	
	@FXML
	private MenuItem openButton;

	@FXML
	private MenuItem closeButton;

	@FXML
	private ImageView birdsOutlinedView;

	@FXML
	private ImageView originalImageView;

	@FXML
	private ImageView blackAndWhiteImageView;

	@FXML
	void onClose(ActionEvent event) {

	}

	@FXML
	void onOpen(ActionEvent event) {
		File imageFile = fileChooser.showOpenDialog(null);

		if (imageFile == null) {
			return;
		}

		try {
			selectedImage = ImageIO.read(imageFile);
			binaryImage = bwFilter.applyToImage(selectedImage);
			birdImage = boxFilter.outlineBirds(binaryImage);

			originalImageView.setImage(SwingFXUtils.toFXImage(selectedImage, null));
			blackAndWhiteImageView.setImage(SwingFXUtils.toFXImage(binaryImage, null));
			birdsOutlinedView.setImage(SwingFXUtils.toFXImage(birdImage, null));
		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}