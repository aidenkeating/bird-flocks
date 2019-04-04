package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class MainController {
	private static FileChooser fileChooser = new FileChooser();
	private static BlackAndWhiteFilter bwFilter = new BlackAndWhiteFilter(127);
	private static BirdFinder boxFilter = new BirdFinder(1);
	
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
		Platform.exit();
	}

	@FXML
	void onOpen(ActionEvent event) {
		File imageFile = fileChooser.showOpenDialog(null);

		if (imageFile == null) {
			return;
		}

		try {
			BufferedImage selectedImage = ImageIO.read(imageFile);
			BufferedImage binaryImage = bwFilter.applyToImage(selectedImage);
			originalImageView.setImage(SwingFXUtils.toFXImage(selectedImage, null));
			blackAndWhiteImageView.setImage(SwingFXUtils.toFXImage(binaryImage, null));
			
			BufferedImage birdImage = boxFilter.outlineBirds(binaryImage, selectedImage);
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