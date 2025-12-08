package de.metas.ui.web.upload;

import com.google.common.collect.ImmutableMap;
import de.metas.image.AdImage;
import de.metas.image.AdImageId;
import de.metas.organization.OrgId;
import de.metas.ui.web.cache.ETag;
import de.metas.ui.web.cache.ETagAware;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

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
	static WebuiImage of(final AdImage adImage, final int maxWidth, final int maxHeight)
	{
		return new WebuiImage(adImage, maxWidth, maxHeight);
	}

	private final AdImage adImage;
	private final int maxWidth;
	private final int maxHeight;
	private final ETag etag;

	private WebuiImage(@NonNull final AdImage adImage, final int maxWidth, final int maxHeight)
	{
		this.adImage = adImage;
		this.maxWidth = Math.max(maxWidth, 0);
		this.maxHeight = Math.max(maxHeight, 0);

		etag = ETag.of(adImage.getLastModified().toEpochMilli(), ImmutableMap.<String, String>builder()
				.put("maxWidth", String.valueOf(maxWidth))
				.put("maxHeight", String.valueOf(maxHeight))
				.put("imageId", String.valueOf(adImage.getId().getRepoId()))
				.build());
	}

	@Override
	public ETag getETag()
	{
		return etag;
	}

	public String getImageName()
	{
		return adImage.getFilename();
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
		return adImage.getClientAndOrgId().getClientId();
	}

	public OrgId getAdOrgId()
	{
		return adImage.getClientAndOrgId().getOrgId();
	}

	public AdImageId getAdImageId()
	{
		return adImage.getId();
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
