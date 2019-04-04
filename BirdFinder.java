package application;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BirdFinder {
	private static int BLACK = Color.BLACK.getRGB();
	private int noiseReduction;

	public BirdFinder(int noiseReduction) {
		this.noiseReduction = noiseReduction;
	}

	public BufferedImage outlineBirds(BufferedImage binaryImage, BufferedImage toOutline) {
		UnionFind uf = new UnionFind(toOutline.getWidth() * toOutline.getHeight());
		Graphics toOutlineGraphics = toOutline.getGraphics();
		int iw = binaryImage.getWidth();
		
		for (int y = 0; y < binaryImage.getHeight(); y++) {
			for (int x = 0; x < binaryImage.getWidth(); x++) {
				if (binaryImage.getRGB(x, y) != BLACK) {
					continue;
				}
				int pixelId = getPixelId(iw, x, y);
				if (x > 0 && binaryImage.getRGB(x - 1, y) == BLACK) {
					uf.union(pixelId, getPixelId(iw, x - 1, y));
				}
				if (y > 0 && binaryImage.getRGB(x, y - 1) == BLACK) {
					uf.union(pixelId, getPixelId(iw, x, y - 1));
				}
				if (y < binaryImage.getHeight() - 1 && binaryImage.getRGB(x, y + 1) == BLACK) {
					uf.union(pixelId, getPixelId(iw, x, y + 1));
				}
				if (x < binaryImage.getWidth() - 1 && binaryImage.getRGB(x + 1, y) == BLACK) {
					uf.union(pixelId, getPixelId(iw, x + 1, y));
				}
				if (x > 0 && y > 0 && binaryImage.getRGB(x - 1, y - 1) == BLACK) {
					uf.union(pixelId, getPixelId(iw, x - 1, y - 1));
				}
				if (x > 0 && y < binaryImage.getHeight() - 1 && binaryImage.getRGB(x - 1, y + 1) == BLACK) {
					uf.union(pixelId, getPixelId(iw, x - 1, y + 1));
				}
				if (x < binaryImage.getWidth() - 1 && y < binaryImage.getHeight() - 1
						&& binaryImage.getRGB(x + 1, y + 1) == BLACK) {
					uf.union(pixelId, getPixelId(iw, x + 1, y + 1));
				}
				if (x < binaryImage.getWidth() - 1 && y > 0 && binaryImage.getRGB(x + 1, y - 1) == BLACK) {
					uf.union(pixelId, getPixelId(iw, x + 1, y - 1));
				}
			}
		}

		Set<Integer> roots = uf.getRoots(this.noiseReduction);
		Iterator<Integer> rootIter = roots.iterator();
		while (rootIter.hasNext()) {
			int root = rootIter.next();
			List<Integer> nodes = uf.getNodes(root);

			int leftmostX = -1;
			int rightmostX = -1;
			int topmostY = -1;
			int bottommostY = -1;
			
			for (int i = 0; i < nodes.size(); i++) {
				int nodeX = (int) nodes.get(i) % binaryImage.getWidth();
				int nodeY = (int) nodes.get(i) / binaryImage.getWidth();

				if (leftmostX == -1 || nodeX < leftmostX) {
					leftmostX = nodeX;
				}
				if (rightmostX == -1 || nodeX > rightmostX) {
					rightmostX = nodeX;
				}
				if (topmostY == -1 || nodeY < topmostY) {
					topmostY = nodeY;
				}
				if (bottommostY == -1 || nodeY > bottommostY) {
					bottommostY = nodeY;
				}
			}
			int height = bottommostY - topmostY;
			int width = rightmostX - leftmostX;
			toOutlineGraphics.setColor(Color.MAGENTA);
			toOutlineGraphics.drawRect(leftmostX, topmostY, width, height);
		}
		toOutlineGraphics.dispose();
		return toOutline;
	}

	private int getPixelId(int imageWidth, int x, int y) {
		return (imageWidth * y) + x;
	}

}
