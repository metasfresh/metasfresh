package de.metas.report.jasper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;

import com.google.common.io.BaseEncoding;

import de.metas.logging.LogManager;
import lombok.NonNull;

final public class ImageFileLoader
{
	protected static final transient Logger logger = LogManager.getLogger(AttachmentImageFileLoader.class);

	private static final String emptyPNGBase64Encoded = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII="; // thanks to http://png-pixel.com/
	private transient File emptyPNGFile; // lazy

	
	public static ImageFileLoader newInstance()
	{
		return new ImageFileLoader();
	}

	private ImageFileLoader()
	{
	}

	
	public File getEmptyPNGFile()
	{
		File file = this.emptyPNGFile;
		if (file != null && !file.exists())
		{
			file = null;
		}

		if (file == null)
		{
			file = this.emptyPNGFile = createTempPNGFile("empty", BaseEncoding.base64().decode(emptyPNGBase64Encoded));
		}
		return file;
	}

	public File createTempPNGFile(@NonNull final String filenamePrefix, @NonNull byte[] content)
	{
		try
		{
			final File tempFile = File.createTempFile(filenamePrefix, ".png");
			try (final FileOutputStream out = new FileOutputStream(tempFile))
			{
				out.write(content);
				out.close();
			}

			return tempFile;
		}
		catch (IOException e)
		{
			logger.warn("Failed creating the logo temporary file", e);
			return null;
		}
	}
}
