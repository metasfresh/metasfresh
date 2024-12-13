/*
 * #%L
 * de-metas-camel-leichundmehl
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.networking.tcp.TCPConnectionDetails;
import de.metas.common.util.Check;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Jacksonized
@Builder
public class DestinationDetails
{
	@Nullable
	@JsonProperty("tcpConnectionDetails")
	TCPConnectionDetails tcpConnectionDetails;

	/**
	 * If not null, then don't send the resulting PLU-file to a Leich+Melh machine, but store it on disk.
	 */
	@Nullable
	@JsonProperty("pluFileServerFolder")
	String pluFileServerFolder;

	private DestinationDetails(
			@Nullable final TCPConnectionDetails tcpConnectionDetails,
			@Nullable final String pluFileServerFolder)
	{
		Check.errorIf(tcpConnectionDetails == null && Check.isBlank(pluFileServerFolder),
					  "At least one of tcpConnectionDetails and pluFileServerFolder needs to be specified");
		
		this.tcpConnectionDetails = tcpConnectionDetails;
		this.pluFileServerFolder = pluFileServerFolder;
	}

	@NonNull
	public TCPConnectionDetails getConnectionDetailsNotNull()
	{
		return Check.assumeNotNull(tcpConnectionDetails, "tcpConnectionDetails are expected to be non-null");
	}

	@NonNull
	public String getPluFileServerFolderNotBlank()
	{
		return Check.assumeNotEmpty(pluFileServerFolder, "pluFileServerFolder is expected to be not blank");
	}

	public boolean isStoreFileOnDisk()
	{
		return Check.isNotBlank(pluFileServerFolder);
	}
}
