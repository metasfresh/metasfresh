package de.metas.ui.web.upload;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.FileUtils;
import org.compiere.model.I_AD_Image;
import org.compiere.model.MImage;
import org.compiere.util.Env;
import org.compiere.util.MimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.ImmutableMap;

import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.cache.ETag;
import de.metas.ui.web.cache.ETagAware;
import de.metas.ui.web.cache.ETagResponseEntityBuilder;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@RestController
@RequestMapping(value = ImageRestController.ENDPOINT)
public class ImageRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/image";

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");

	@Autowired
	private UserSession userSession;

	private JSONOptions newJSONOptions()
	{
		return JSONOptions.builder(userSession).build();
	}

	@PostMapping
	public int uploadImage(@RequestParam("file") final MultipartFile file) throws IOException
	{
		userSession.assertLoggedIn();

		final String name = file.getOriginalFilename();
		final byte[] data = file.getBytes();
		final String contentType = file.getContentType();
		final String filenameNorm = normalizeUploadFilename(name, contentType);

		final MImage adImage = new MImage(Env.getCtx(), 0, ITrx.TRXNAME_None);
		adImage.setName(filenameNorm);
		adImage.setBinaryData(data);
		// TODO: introduce adImage.setTemporary(true);
		InterfaceWrapperHelper.save(adImage);

		return adImage.getAD_Image_ID();
	}

	private static final String normalizeUploadFilename(final String name, final String contentType)
	{
		final String fileExtension = MimeType.getExtensionByType(contentType);

		final String nameNormalized;
		if (Check.isEmpty(name, true)
				|| "blob".equals(name) // HARDCODED: this happens when the image is taken from webcam
		)
		{
			nameNormalized = DATE_FORMAT.format(LocalDateTime.now());
		}
		else
		{
			nameNormalized = name.trim();
		}

		return FileUtils.changeFileExtension(nameNormalized, fileExtension);
	}

	@GetMapping("/{imageId}")
	@ResponseBody
	public ResponseEntity<byte[]> getImage(@PathVariable final int imageId,
			@RequestParam(name = "maxWidth", required = false, defaultValue = "-1") final int maxWidth,
			@RequestParam(name = "maxHeight", required = false, defaultValue = "-1") final int maxHeight,
			final WebRequest request)
	{
		userSession.assertLoggedIn();

		return ETagResponseEntityBuilder.ofETagAware(request, getWebuiImage(imageId, maxWidth, maxHeight))
				.includeLanguageInETag()
				.cacheMaxAge(userSession.getHttpCacheMaxAge())
				.jsonOptions(() -> newJSONOptions())
				.toResponseEntity((responseBuilder, webuiImage) -> responseBuilder
						.contentType(MediaType.parseMediaType(webuiImage.getContentType()))
						.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + webuiImage.getImageName() + "\"")
						.body(webuiImage.getImageData()));
	}

	private WebuiImage getWebuiImage(final int imageId, final int maxWidth, final int maxHeight)
	{
		if (imageId <= 0)
		{
			throw new IllegalArgumentException("Invalid image id");
		}

		final MImage adImage = MImage.get(Env.getCtx(), imageId);
		if (adImage == null || adImage.getAD_Image_ID() <= 0)
		{
			throw new EntityNotFoundException("Image id not found: " + imageId);
		}

		final boolean hasAccess = userSession.getUserRolePermissions().canView(adImage.getAD_Client_ID(), adImage.getAD_Org_ID(), I_AD_Image.Table_ID, adImage.getAD_Image_ID());
		if (!hasAccess)
		{
			throw new EntityNotFoundException("Image id not found: " + imageId);
		}

		return WebuiImage.of(adImage, maxWidth, maxHeight);
	}

	private static final class WebuiImage implements ETagAware
	{
		public static final WebuiImage of(final MImage adImage, final int maxWidth, final int maxHeight)
		{
			return new WebuiImage(adImage, maxWidth, maxHeight);
		}

		private final MImage adImage;
		private final int maxWidth;
		private final int maxHeight;
		private final ETag etag;

		private WebuiImage(@NonNull final MImage adImage, final int maxWidth, final int maxHeight)
		{
			this.adImage = adImage;
			this.maxWidth = maxWidth > 0 ? maxWidth : 0;
			this.maxHeight = maxHeight > 0 ? maxHeight : 0;

			etag = ETag.of(adImage.getUpdated().getTime(), ImmutableMap.<String, String> builder()
					.put("maxWidth", String.valueOf(maxWidth))
					.put("maxHeight", String.valueOf(maxHeight))
					.put("imageId", String.valueOf(adImage.getAD_Image_ID()))
					.build());
		}

		@Override
		public ETag getETag()
		{
			return etag;
		}

		public String getImageName()
		{
			return adImage.getName();
		}

		public String getContentType()
		{
			return adImage.getContentType();
		}

		public byte[] getImageData()
		{
			return adImage.getScaledImageData(maxWidth, maxHeight);
		}
	}

}
