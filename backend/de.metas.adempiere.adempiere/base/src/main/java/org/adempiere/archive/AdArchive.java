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
<<<<<<< HEAD
=======
import org.springframework.core.io.ByteArrayResource;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

import java.io.ByteArrayInputStream;

@Value
public class AdArchive
{
<<<<<<< HEAD
	private ArchiveId id;
	private byte[] archiveData;
=======
	@NonNull ArchiveId id;
	byte[] archiveData;
	@NonNull String contentType;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	@Builder(toBuilder = true)
	@JsonCreator
	private AdArchive(
			@JsonProperty("id") @NonNull final ArchiveId id,
<<<<<<< HEAD
			@JsonProperty("archiveData") @NonNull final byte[] archiveData)
	{
		this.id = id;
		this.archiveData = archiveData;
=======
			@JsonProperty("archiveData") final byte[] archiveData,
			@JsonProperty("contentType") @NonNull final String contentType)
	{
		this.id = id;
		this.archiveData = archiveData;
		this.contentType = contentType;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public ByteArrayInputStream getArchiveStream()
	{
		return new ByteArrayInputStream(archiveData);
	}
<<<<<<< HEAD
=======

	public ByteArrayResource getArchiveDataAsResource() {return new ByteArrayResource(archiveData);}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
