package de.metas.image;

import de.metas.organization.ClientAndOrgId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.MimeType;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;

@Value
@Builder
public class AdImage
{
	@NonNull AdImageId id;
	@NonNull Instant lastModified;

	@NonNull String contentType;
	@NonNull String filename;

	@Nullable byte[] data;

	@NonNull ClientAndOrgId clientAndOrgId;

	public byte[] getScaledImageData(final int maxWidth, final int maxHeight)
	{
		if (data == null || data.length == 0)
		{
			return null;
		}

		if (maxWidth <= 0 && maxHeight <= 0)
		{
			return data;
		}

		final BufferedImage image;
		try
		{
			image = ImageIO.read(new ByteArrayInputStream(data));
		}
		catch (IOException ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
		if (image == null)
		{
			return null;
		}

		final Dimension sizeOrig = new Dimension(image.getWidth(), image.getHeight());
		final Dimension sizeNew = computeScaledDimension(sizeOrig, new Dimension(maxWidth, maxHeight));
		if (sizeNew.equals(sizeOrig))
		{
			return data;
		}
		else
		{
			final BufferedImage resizedImage = new BufferedImage(sizeNew.width, sizeNew.height, BufferedImage.TYPE_INT_RGB);
			final Graphics2D g = resizedImage.createGraphics();

			g.setComposite(AlphaComposite.Src);
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			g.drawImage(image, 0, 0, sizeNew.width, sizeNew.height, null);
			g.dispose();

			final String imageFormatName = computeImageFormatNameByContentType(contentType);
			return toByteArray(resizedImage, imageFormatName);
		}
	}

	private static byte[] toByteArray(final BufferedImage image, final String formatName)
	{
		try
		{
			final ByteArrayOutputStream buf = new ByteArrayOutputStream();
			ImageIO.write(image, formatName, buf);
			return buf.toByteArray();
		}
		catch (IOException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	public static Dimension computeScaledDimension(final Dimension size, final Dimension maxSize)
	{
		// credits: http://stackoverflow.com/questions/10245220/java-image-resize-maintain-aspect-ratio

		final int widthOrig = size.width;
		final int heightOrig = size.height;
		final int widthMax = maxSize.width;
		final int heightMax = maxSize.height;

		int widthNew;
		int heightNew;

		// first check if we need to scale width
		if (widthMax > 0 && widthOrig > widthMax)
		{
			// scale width to fit
			widthNew = widthMax;
			// scale height to maintain aspect ratio
			heightNew = widthNew * heightOrig / widthOrig;
		}
		else
		{
			widthNew = widthOrig;
			heightNew = heightOrig;
		}

		// then check if we need to scale even with the new height
		if (heightMax > 0 && heightNew > heightMax)
		{
			// scale height to fit instead
			heightNew = heightMax;
			// scale width to maintain aspect ratio
			widthNew = heightNew * widthOrig / heightOrig;
		}

		return new Dimension(widthNew, heightNew);
	}

	private static String computeImageFormatNameByContentType(@NonNull final String contentType)
	{
		final String fileExtension = MimeType.getExtensionByTypeWithoutDot(contentType);
		if (Check.isEmpty(fileExtension))
		{
			return "png";
		}
		return fileExtension;
	}

}
