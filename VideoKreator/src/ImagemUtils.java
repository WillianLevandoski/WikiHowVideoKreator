 
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
 
/**
 * This program demonstrates how to resize an image.
 *
 * @author www.codejava.net
 *
 */
public class ImagemUtils {
 
    /**
     * Resizes an image to a absolute width and height (the image may not be
     * proportional)
     * @param inputImagePath Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param scaledWidth absolute width in pixels
     * @param scaledHeight absolute height in pixels
     * @throws IOException
     */
    public static void resize(String inputImagePath,
            String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
 
        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());
 
        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
 
        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);
 
        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }
 
    /**
     * Resizes an image by a percentage of original size (proportional).
     * @param inputImagePath Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param percent a double number specifies percentage of the output image
     * over the input image.
     * @throws IOException
     */
    public static void resize(String inputImagePath,
            String outputImagePath, double percent) throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);
        resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
    }
 
    /**
     * Test resizing images
     */
    public static void maiNn(String[] args) { // Photo Resizer
        String inputImagePath = "D:/Photo/Puppy.jpg";
        String outputImagePath1 = "D:/Photo/Puppy_Fixed.jpg";
        String outputImagePath2 = "D:/Photo/Puppy_Smaller.jpg";
        String outputImagePath3 = "D:/Photo/Puppy_Bigger.jpg";
 
        try {
            // resize to a fixed width (not proportional)
            int scaledWidth = 1024;
            int scaledHeight = 768;
            ImagemUtils.resize(inputImagePath, outputImagePath1, scaledWidth, scaledHeight);
 
            // resize smaller by 50%
            double percent = 0.5;
            ImagemUtils.resize(inputImagePath, outputImagePath2, percent);
 
            // resize bigger by 50%
            percent = 1.5;
            ImagemUtils.resize(inputImagePath, outputImagePath3, percent);
 
        } catch (IOException ex) {
            System.out.println("Error resizing the image.");
            ex.printStackTrace();
        }
    }
  
    public static void main(String[] args) {

//	public static void addTextoImagem(BufferedImage image, String texto) {
		try {
			BufferedImage image = null;
			//image = ImageIO.read(new URL("https://scontent.fbfh3-2.fna.fbcdn.net/v/t1.0-9/24617_323745894403275_1350736487_n.jpg?_nc_cat=103&_nc_oc=AQmqH3RqBheae1y3E7MBmoDIvy_X4mHoEeKCA37Zkh3BYTErD4NNLtkS3NMd1vcPSMBjpR9ehcb7eURFXJG7PtcP&_nc_ht=scontent.fbfh3-2.fna&oh=fc0969b128a18c40fc8ca65c2a23d5e9&oe=5E408503"));
			image = ImageIO.read(new File("C:\\Users\\Hall\\Documents\\Desert.jpg"));
			Graphics g = image.getGraphics();
			g.setFont(g.getFont().deriveFont(30f));
			g.drawString("Hello World!", 100, 300);
			g.dispose();
			ImageIO.write(image, "png", new File("testee.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
}