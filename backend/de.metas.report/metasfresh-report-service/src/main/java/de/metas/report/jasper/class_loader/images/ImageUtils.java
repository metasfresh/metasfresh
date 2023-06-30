package de.metas.report.jasper.class_loader.images;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.io.BaseEncoding;
import de.metas.image.AdImageId;
import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Image;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@UtilityClass
public final class ImageUtils
{
	private static final Logger logger = LogManager.getLogger(ImageUtils.class);

	@VisibleForTesting
	public static final String emptyPNGBase64Encoded = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII="; // thanks to http://png-pixel.com/
	private static transient File emptyPNGFile; // lazy

	public static File getEmptyPNGFile()
	{
		File file = ImageUtils.emptyPNGFile;
		if (file == null
				|| !file.exists()
				|| !file.canRead())
		{
			file = ImageUtils.emptyPNGFile = createTempPNGFile("empty", BaseEncoding.base64().decode(emptyPNGBase64Encoded));
		}

		return file;
	}

	public static File createTempPNGFile(@NonNull final String filenamePrefix, byte[] content)
	{
		if (content == null || content.length <= 0)
		{
			return getEmptyPNGFile();
		}

		try
		{
			final File tempFile = File.createTempFile(filenamePrefix, ".png");
			try (final FileOutputStream out = new FileOutputStream(tempFile))
			{
				out.write(content);
			}

			return tempFile;
		}
		catch (IOException e)
		{
			throw new AdempiereException("Failed creating the PNG temporary file with prefix `" + filenamePrefix + "`", e);
		}
	}

	public static Optional<File> createTempImageFile(@NonNull final AdImageId adImageId)
	{
		try
		{
			final I_AD_Image adImage = InterfaceWrapperHelper.load(adImageId, I_AD_Image.class);
			if (adImage == null)
			{
				return Optional.empty();
			}

			final byte[] data = adImage.getBinaryData();
			if (data == null || data.length <= 0)
			{
				return Optional.empty();
			}

			return Optional.of(createTempPNGFile("AD_Image-" + adImageId.getRepoId() + "_", data));
		}
		catch (Exception ex)
		{
			logger.warn("Failed creating temp PNG file for {}. Returning blank file.", adImageId, ex);
			return Optional.of(getEmptyPNGFile());
		}
	}
}
