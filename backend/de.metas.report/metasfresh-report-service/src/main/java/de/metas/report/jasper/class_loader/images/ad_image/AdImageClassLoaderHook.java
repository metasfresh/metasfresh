package de.metas.report.jasper.class_loader.images.ad_image;

import de.metas.image.AdImageId;
import de.metas.logging.LogManager;
import de.metas.report.jasper.class_loader.images.ImageUtils;
import de.metas.util.FileUtil;
import lombok.NonNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class AdImageClassLoaderHook
{
	private static final transient Logger logger = LogManager.getLogger(AdImageClassLoaderHook.class);
	private static final String RESOURCENAME_PREFIX = "AD_Image-";

	@Nullable
	public URL getResourceURLOrNull(@NonNull final String resourceName)
	{
		final int idx = resourceName.indexOf(RESOURCENAME_PREFIX);
		if (idx < 0)
		{
			return null;
		}

		final String adImageIdAndExtension = resourceName.substring(idx + RESOURCENAME_PREFIX.length());

		final String adImageIdStr = FileUtil.getFileBaseName(adImageIdAndExtension);
		final AdImageId adImageId = AdImageId.ofNullableObject(adImageIdStr);
		if (adImageId == null)
		{
			return toURLOrNull(ImageUtils.getEmptyPNGFile());
		}

		final File imageFile = ImageUtils.createTempImageFile(adImageId)
				.orElseGet(ImageUtils::getEmptyPNGFile);

		return toURLOrNull(imageFile);

	}

	@Nullable
	private static URL toURLOrNull(@NonNull final File file)
	{
		try
		{
			return file.toURI().toURL();
		}
		catch (final MalformedURLException ex)
		{
			logger.warn("Failed converting the local file to URL: {}", file, ex);
			return null;
		}
	}
}
