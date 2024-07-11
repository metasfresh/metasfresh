/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.externalsystem.leichmehl;

import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.IExternalSystemChildConfig;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class ExternalSystemLeichMehlConfig implements IExternalSystemChildConfig
{
	@NonNull
	ExternalSystemLeichMehlConfigId id;

	@NonNull
	ExternalSystemParentConfigId parentId;

	@NonNull
	String value;

	@NonNull
	String productBaseFolderName;

	@NonNull
	Integer tcpPort;

	@NonNull
	String tcpHost;

	boolean pluFileExportAuditEnabled;

	@Builder
	public ExternalSystemLeichMehlConfig(
			@NonNull final ExternalSystemLeichMehlConfigId id,
			@NonNull final ExternalSystemParentConfigId parentId,
			@NonNull final String value,
			@NonNull final String productBaseFolderName,
			@NonNull final Integer tcpPort,
			@NonNull final String tcpHost,
			final boolean pluFileExportAuditEnabled)
	{
		this.id = id;
		this.parentId = parentId;
		this.value = value;
		this.productBaseFolderName = productBaseFolderName;
		this.tcpPort = tcpPort;
		this.tcpHost = tcpHost;
		this.pluFileExportAuditEnabled = pluFileExportAuditEnabled;
	}

	public static ExternalSystemLeichMehlConfig cast(@NonNull final IExternalSystemChildConfig childConfig)
	{
		return (ExternalSystemLeichMehlConfig)childConfig;
	}
}
