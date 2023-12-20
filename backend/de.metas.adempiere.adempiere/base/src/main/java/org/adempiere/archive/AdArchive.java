/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package org.adempiere.archive;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.core.io.ByteArrayResource;

import java.io.ByteArrayInputStream;

@Value
public class AdArchive
{
	@NonNull ArchiveId id;
	byte[] archiveData;
	@NonNull String contentType;

	@Builder(toBuilder = true)
	@JsonCreator
	private AdArchive(
			@JsonProperty("id") @NonNull final ArchiveId id,
			@JsonProperty("archiveData") final byte[] archiveData,
			@JsonProperty("contentType") @NonNull final String contentType)
	{
		this.id = id;
		this.archiveData = archiveData;
		this.contentType = contentType;
	}

	public ByteArrayInputStream getArchiveStream()
	{
		return new ByteArrayInputStream(archiveData);
	}

	public ByteArrayResource getArchiveDataAsResource() {return new ByteArrayResource(archiveData);}
}
