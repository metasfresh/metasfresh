package de.metas.ui.web.upload;

import java.io.IOException;

import org.compiere.model.I_AD_Image;
import org.springframework.beans.factory.annotation.Autowired;
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

import de.metas.ui.web.cache.ETagResponseEntityBuilder;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.json.JSONOptions;

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

	@Autowired
	private UserSession userSession;

	@Autowired
	private WebuiImageService imageService;

	private JSONOptions newJSONOptions()
	{
		return JSONOptions.of(userSession);
	}

	@PostMapping
	public WebuiImageId uploadImage(@RequestParam("file") final MultipartFile file) throws IOException
	{
		userSession.assertLoggedIn();
		return imageService.uploadImage(file);
	}

	@GetMapping("/{imageId}")
	@ResponseBody
	public ResponseEntity<byte[]> getImage(
			@PathVariable("imageId") final int imageIdInt,
			@RequestParam(name = "maxWidth", required = false, defaultValue = "-1") final int maxWidth,
			@RequestParam(name = "maxHeight", required = false, defaultValue = "-1") final int maxHeight,
			final WebRequest request)
	{
		userSession.assertLoggedIn();

		final WebuiImageId imageId = WebuiImageId.ofRepoIdOrNull(imageIdInt);
		return ETagResponseEntityBuilder.ofETagAware(request, getWebuiImage(imageId, maxWidth, maxHeight))
				.includeLanguageInETag()
				.cacheMaxAge(userSession.getHttpCacheMaxAge())
				.jsonOptions(() -> newJSONOptions())
				.toResponseEntity((responseBuilder, webuiImage) -> webuiImage.toResponseEntity(responseBuilder));
	}

	private WebuiImage getWebuiImage(final WebuiImageId imageId, final int maxWidth, final int maxHeight)
	{
		final WebuiImage image = imageService.getWebuiImage(imageId, maxWidth, maxHeight);
		assertUserHasAccess(image);
		return image;
	}

	private void assertUserHasAccess(final WebuiImage image)
	{
		final boolean hasAccess = userSession.getUserRolePermissions().canView(image.getAdClientId(), image.getAdOrgId(), I_AD_Image.Table_ID, image.getAdImageId());
		if (!hasAccess)
		{
			throw new EntityNotFoundException("No access to image: " + image.getAdImageId());
		}
	}
}
