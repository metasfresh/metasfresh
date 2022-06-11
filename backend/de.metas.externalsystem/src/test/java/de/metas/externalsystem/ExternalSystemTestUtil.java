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

package de.metas.externalsystem;

import de.metas.common.util.CoalesceUtil;
import de.metas.externalsystem.model.I_ExternalSystem_Config;
import de.metas.externalsystem.model.I_ExternalSystem_Config_GRSSignum;
import lombok.Builder;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@UtilityClass
public class ExternalSystemTestUtil
{
	@NonNull
	@Builder(builderMethodName = "createI_ExternalSystem_ConfigBuilder", builderClassName = "I_ExternalSystem_ConfigBuilder")
	public I_ExternalSystem_Config createI_ExternalSystem_Config(
			@NonNull final String type,
			@Nullable final Boolean active,
			final int customParentConfigId)
	{
		final Boolean isActive = CoalesceUtil.coalesceNotNull(active, Boolean.TRUE);

		final I_ExternalSystem_Config record = newInstance(I_ExternalSystem_Config.class);
		record.setName("name");
		record.setType(type);
		record.setIsActive(isActive);
		record.setWriteAudit(true);
		record.setAuditFileFolder("auditFileFolder");

		if (customParentConfigId > 0)
		{
			record.setExternalSystem_Config_ID(customParentConfigId);
		}

		saveRecord(record);

		return record;
	}

	@NonNull
	@Builder(builderMethodName = "createGrsConfigBuilder", builderClassName = "grsConfigBuilder")
	private I_ExternalSystem_Config_GRSSignum createGrsConfig(
			final int externalSystemConfigId,
			@NonNull final String value,
			final boolean syncBPartnersToRestEndpoint,
			final boolean syncHUsOnMaterialReceipt,
			final boolean syncHUsOnProductionReceipt)
	{
		final Boolean isSyncBPartnersToRestEndpoint = CoalesceUtil.coalesceNotNull(syncBPartnersToRestEndpoint, Boolean.FALSE);
		final Boolean isSyncHUsOnMaterialReceipt = CoalesceUtil.coalesceNotNull(syncHUsOnMaterialReceipt, Boolean.FALSE);
		final Boolean isSyncHUsOnProductionReceipt = CoalesceUtil.coalesceNotNull(syncHUsOnProductionReceipt, Boolean.FALSE);

		final I_ExternalSystem_Config_GRSSignum childRecord = newInstance(I_ExternalSystem_Config_GRSSignum.class);
		childRecord.setBaseURL("baseUrl");
		childRecord.setExternalSystem_Config_ID(externalSystemConfigId);
		childRecord.setExternalSystemValue(value);
		childRecord.setCamelHttpResourceAuthKey("authKey");
		childRecord.setTenantId("tenantId");
		childRecord.setAuthToken("authToken");
		childRecord.setIsSyncBPartnersToRestEndpoint(isSyncBPartnersToRestEndpoint);
		childRecord.setIsSyncHUsOnMaterialReceipt(isSyncHUsOnMaterialReceipt);
		childRecord.setIsSyncHUsOnProductionReceipt(isSyncHUsOnProductionReceipt);
		saveRecord(childRecord);

		return childRecord;
	}
}
