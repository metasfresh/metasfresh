/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.externalsystem.other.export.bpartner;

import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.export.ExportToExternalSystemService;
import de.metas.externalsystem.export.bpartner.C_BPartner_SyncTo_ExternalSystem;
import de.metas.externalsystem.model.I_ExternalSystem_Config;
import de.metas.externalsystem.other.ExternalSystemOtherConfig;
import de.metas.externalsystem.other.ExternalSystemOtherConfigId;
import de.metas.process.Param;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class C_BPartner_SyncTo_Other extends C_BPartner_SyncTo_ExternalSystem
{
	private final ExportBPartnerToOtherService exportBPartnerToOtherService = SpringContextHolder.instance.getBean(ExportBPartnerToOtherService.class);

	private static final String PARAM_EXTERNAL_SYSTEM_CONFIG_OTHER_ID = I_ExternalSystem_Config.COLUMNNAME_ExternalSystem_Config_ID;
	@Param(parameterName = PARAM_EXTERNAL_SYSTEM_CONFIG_OTHER_ID)
	private int externalSystemConfigOtherId;

	@Override
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.Other;
	}

	@Override
	protected IExternalSystemChildConfigId getExternalSystemChildConfigId()
	{
		return ExternalSystemOtherConfigId.ofExternalSystemParentConfigId(ExternalSystemParentConfigId.ofRepoId(externalSystemConfigOtherId));
	}

	@Override
	protected String getExternalSystemParam()
	{
		return PARAM_EXTERNAL_SYSTEM_CONFIG_OTHER_ID;
	}

	@Override
	protected ExportToExternalSystemService getExportToBPartnerExternalSystem()
	{
		return exportBPartnerToOtherService;
	}

	@Override
	protected boolean isExportAllowed(@NonNull final IExternalSystemChildConfig childConfig)
	{
		final ExternalSystemOtherConfig otherConfig = ExternalSystemOtherConfig.cast(childConfig);

		return otherConfig.isSyncBPartnerEnabled();
	}
}
