package de.metas.ui.web.upload;

import java.io.IOException;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Image;
import org.compiere.model.MImage;
import org.compiere.util.MimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.session.UserSession;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
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

	@PostMapping
	public int uploadImage(@RequestParam("file") final MultipartFile file) throws IOException
	{
		userSession.assertLoggedIn();

		final String name = file.getOriginalFilename();
		final byte[] data = file.getBytes();

		final MImage adImage = new MImage(userSession.getCtx(), 0, ITrx.TRXNAME_None);
		adImage.setName(name);
		adImage.setBinaryData(data);
		InterfaceWrapperHelper.save(adImage);

		return adImage.getAD_Image_ID();
	}

	@GetMapping("/{imageId}")
	@ResponseBody
	public ResponseEntity<byte[]> getImage(@PathVariable final int imageId)
	{
		userSession.assertLoggedIn();

		if (imageId <= 0)
		{
			throw new IllegalArgumentException("Invalid image id");
		}

		final MImage adImage = MImage.get(userSession.getCtx(), imageId);
		if (adImage == null || adImage.getAD_Image_ID() <= 0)
		{
			throw new EntityNotFoundException("Image id not found: " + imageId);
		}

		final boolean hasAccess = userSession.getUserRolePermissions().canView(adImage.getAD_Client_ID(), adImage.getAD_Org_ID(), I_AD_Image.Table_ID, adImage.getAD_Image_ID());
		if (!hasAccess)
		{
			throw new EntityNotFoundException("Image id not found: " + imageId);
		}

		final String imageName = adImage.getName();
		final byte[] imageData = adImage.getData();
		final String contentType = MimeType.getMimeType(imageName);

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + imageName + "\"");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		final ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(imageData, headers, HttpStatus.OK);
		return response;
	}

}
