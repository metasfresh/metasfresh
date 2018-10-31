package de.metas.vertical.pharma.vendor.gateway.msv3.config;

import java.net.URL;
import java.util.Objects;

import org.adempiere.exceptions.AdempiereException;

import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.ClientSoftwareId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * de.metas.vendor.gateway.msv3
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

@Value
@Builder(toBuilder = true)
@ToString(exclude = "authPassword")
public class MSV3ClientConfig
{
	public static final Version VERSION_1 = Version.builder()
			.id("1")
			.jaxbPackagesToScan(de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.ObjectFactory.class.getPackage().getName())
			.build();
	public static final Version VERSION_2 = Version.builder()
			.id("2")
			.jaxbPackagesToScan(de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.ObjectFactory.class.getPackage().getName())
			.build();

	@NonNull
	URL baseUrl;
	@NonNull
	String authUsername;
	@NonNull
	String authPassword;

	@Getter
	@NonNull
	BPartnerId bpartnerId;

	@NonNull
	@Default
	Version version = VERSION_2;
	
	@NonNull
	ClientSoftwareId clientSoftwareId;

	/** might be null, if the MSV3ClientConfig wasn't stored yet */
	@Getter
	MSV3ClientConfigId configId;

	public void assertVersion(@NonNull final String expectedVersion)
	{
		if (!Objects.equals(this.version.getId(), expectedVersion))
		{
			throw new AdempiereException("Configuration does not have the expected version")
					.setParameter("config", this)
					.setParameter("expectedVersion", expectedVersion)
					.appendParametersToMessage();
		}
	}
}
