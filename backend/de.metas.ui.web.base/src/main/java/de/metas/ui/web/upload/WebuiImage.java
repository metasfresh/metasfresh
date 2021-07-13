package de.metas.ui.web.upload;

import org.adempiere.service.ClientId;
import org.compiere.model.MImage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.google.common.collect.ImmutableMap;

import de.metas.organization.OrgId;
import de.metas.ui.web.cache.ETag;
import de.metas.ui.web.cache.ETagAware;
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

public final class WebuiImage implements ETagAware
{
	static final WebuiImage of(final MImage adImage, final int maxWidth, final int maxHeight)
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

	public ClientId getAdClientId()
	{
		return ClientId.ofRepoId(adImage.getAD_Client_ID());
	}

	public OrgId getAdOrgId()
	{
		return OrgId.ofRepoId(adImage.getAD_Org_ID());
	}

	public int getAdImageId()
	{
		return adImage.getAD_Image_ID();
	}

	public ResponseEntity<byte[]> toResponseEntity()
	{
		return toResponseEntity(ResponseEntity.status(HttpStatus.OK));
	}

	public ResponseEntity<byte[]> toResponseEntity(@NonNull final ResponseEntity.BodyBuilder responseBuilder)
	{
		return responseBuilder
				.contentType(MediaType.parseMediaType(getContentType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + getImageName() + "\"")
				.body(getImageData());
	}
}
