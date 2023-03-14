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

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocTypeId;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.sap.export.SAPExportAcctConfig;
import de.metas.externalsystem.sap.source.SAPContentSourceLocalFile;
import de.metas.externalsystem.sap.source.SAPContentSourceSFTP;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

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

	@Nullable
	String baseURL;

	@Nullable
	String postAcctDocumentsPath;

	@Nullable
	String signedVersion;

	@Nullable
	String signedPermissions;

	@Nullable
	String signature;

	@Nullable
	String apiVersion;

	@NonNull
	ImmutableList<SAPExportAcctConfig> exportAcctConfigList;

	@Builder
	public ExternalSystemSAPConfig(
			@NonNull final ExternalSystemSAPConfigId id,
			@NonNull final ExternalSystemParentConfigId parentId,
			@NonNull final String value,
			@Nullable final SAPContentSourceSFTP contentSourceSFTP,
			@Nullable final SAPContentSourceLocalFile contentSourceLocalFile,
			final boolean checkDescriptionForMaterialType,
			@Nullable final ImmutableList<SAPExportAcctConfig> exportAcctConfigList,
			@Nullable final String baseURL,
			@Nullable final String postAcctDocumentsPath,
			@Nullable final String signedVersion,
			@Nullable final String signedPermissions,
			@Nullable final String signature,
			@Nullable final String apiVersion)
	{
		this.id = id;
		this.parentId = parentId;
		this.value = value;
		this.contentSourceSFTP = contentSourceSFTP;
		this.contentSourceLocalFile = contentSourceLocalFile;
		this.checkDescriptionForMaterialType = checkDescriptionForMaterialType;
		this.exportAcctConfigList = CoalesceUtil.coalesceNotNull(exportAcctConfigList, ImmutableList.of());
		this.postAcctDocumentsPath = postAcctDocumentsPath;
		this.signedVersion = signedVersion;
		this.signedPermissions = signedPermissions;
		this.signature = signature;
		this.baseURL = baseURL;
		this.apiVersion = apiVersion;
	}

	@NonNull
	public static ExternalSystemSAPConfig cast(@NonNull final IExternalSystemChildConfig childCondig)
	{
		return (ExternalSystemSAPConfig)childCondig;
	}

	@NonNull
	public Optional<SAPExportAcctConfig> getExportConfigFor(@Nullable final DocTypeId docTypeId)
	{
		if (docTypeId == null)
		{
			return Optional.empty();
		}

		return exportAcctConfigList.stream()
				.filter(config -> config.getDocTypeId().equals(docTypeId))
				.findFirst();
	}

	public boolean isExportEnabledForDocType(@Nullable final DocTypeId docTypeId)
	{
		if (Check.isBlank(baseURL)
				|| Check.isBlank(signature)
				|| Check.isBlank(signedVersion)
				|| Check.isBlank(signedPermissions)
				|| Check.isBlank(postAcctDocumentsPath)
				|| Check.isBlank(apiVersion))
		{
			return false;
		}

		return getExportConfigFor(docTypeId).isPresent();
	}
}
