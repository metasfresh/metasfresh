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

package de.metas.externalsystem.sap;

import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.sap.source.SAPContentSourceLocalFile;
import de.metas.externalsystem.sap.source.SAPContentSourceSFTP;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class ExternalSystemSAPConfig implements IExternalSystemChildConfig
{
	@NonNull
	ExternalSystemSAPConfigId id;

	@NonNull
	ExternalSystemParentConfigId parentId;

	@NonNull
	String value;

	@Nullable
	SAPContentSourceSFTP contentSourceSFTP;

	@Nullable
	SAPContentSourceLocalFile contentSourceLocalFile;

	boolean checkDescriptionForMaterialType;

	@Builder
	public ExternalSystemSAPConfig(
			@NonNull final ExternalSystemSAPConfigId id,
			@NonNull final ExternalSystemParentConfigId parentId,
			@NonNull final String value,
			@Nullable final SAPContentSourceSFTP contentSourceSFTP,
			@Nullable final SAPContentSourceLocalFile contentSourceLocalFile,
			final boolean checkDescriptionForMaterialType)
	{
		this.id = id;
		this.parentId = parentId;
		this.value = value;
		this.contentSourceSFTP = contentSourceSFTP;
		this.contentSourceLocalFile = contentSourceLocalFile;
		this.checkDescriptionForMaterialType = checkDescriptionForMaterialType;
	}

	@NonNull
	public static ExternalSystemSAPConfig cast(@NonNull final IExternalSystemChildConfig childCondig)
	{
		return (ExternalSystemSAPConfig)childCondig;
	}
}
