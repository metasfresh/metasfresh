package de.metas.ui.web.upload;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MImage;
import org.compiere.util.Env;
import org.compiere.util.MimeType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.annotations.VisibleForTesting;

import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.util.FileUtil;
import de.metas.util.time.SystemTime;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
public class WebuiImageService
{
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");

	public WebuiImageId uploadImage(final MultipartFile file) throws IOException
	{
		final String name = file.getOriginalFilename();
		final byte[] data = file.getBytes();
		final String contentType = file.getContentType();
		final String filenameNorm = normalizeUploadFilename(name, contentType);

		final MImage adImage = new MImage(Env.getCtx(), 0, ITrx.TRXNAME_None);
		adImage.setName(filenameNorm);
		adImage.setBinaryData(data);
		// TODO: introduce adImage.setTemporary(true);
		InterfaceWrapperHelper.save(adImage);

		return WebuiImageId.ofRepoId(adImage.getAD_Image_ID());
	}

	@VisibleForTesting
	static final String normalizeUploadFilename(final String name, final String contentType)
	{
		final String fileExtension = MimeType.getExtensionByType(contentType);

		final String nameNormalized;
		if (Check.isEmpty(name, true)
				|| "blob".equals(name) // HARDCODED: this happens when the image is taken from webcam
		)
		{
			nameNormalized = DATE_FORMAT.format(SystemTime.asZonedDateTime());
		}
		else
		{
			nameNormalized = name.trim();
		}

		return FileUtil.changeFileExtension(nameNormalized, fileExtension);
	}

	public WebuiImage getWebuiImage(@NonNull final WebuiImageId imageId, final int maxWidth, final int maxHeight)
	{
		final MImage adImage = MImage.get(Env.getCtx(), imageId.getRepoId());
		if (adImage == null || adImage.getAD_Image_ID() <= 0)
		{
			throw new EntityNotFoundException("Image id not found: " + imageId);
		}

		return WebuiImage.of(adImage, maxWidth, maxHeight);
	}
}
