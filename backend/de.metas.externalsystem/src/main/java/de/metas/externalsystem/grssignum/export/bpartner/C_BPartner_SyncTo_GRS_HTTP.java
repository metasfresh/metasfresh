/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.grssignum.export.bpartner;

import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.export.ExportToExternalSystemService;
import de.metas.externalsystem.export.bpartner.C_BPartner_SyncTo_ExternalSystem;
import de.metas.externalsystem.grssignum.ExportBPartnerToGRSService;
import de.metas.externalsystem.grssignum.ExternalSystemGRSSignumConfigId;
import de.metas.externalsystem.model.I_ExternalSystem_Config_GRSSignum;
import de.metas.process.Param;
import org.compiere.SpringContextHolder;

public class C_BPartner_SyncTo_GRS_HTTP extends C_BPartner_SyncTo_ExternalSystem
{
	private final ExportBPartnerToGRSService exportToGRSService = SpringContextHolder.instance.getBean(ExportBPartnerToGRSService.class);

	private static final String PARAM_EXTERNAL_SYSTEM_CONFIG_GRSSIGNUM_ID = I_ExternalSystem_Config_GRSSignum.COLUMNNAME_ExternalSystem_Config_GRSSignum_ID;
	@Param(parameterName = PARAM_EXTERNAL_SYSTEM_CONFIG_GRSSIGNUM_ID)
	private int externalSystemConfigGRSSignumId;

	@Override
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.GRSSignum;
	}

	@Override
	protected IExternalSystemChildConfigId getExternalSystemChildConfigId()
	{
		return ExternalSystemGRSSignumConfigId.ofRepoId(externalSystemConfigGRSSignumId);
	}

	@Override
	protected String getExternalSystemParam()
	{
		return PARAM_EXTERNAL_SYSTEM_CONFIG_GRSSIGNUM_ID;
	}

	@Override
	protected ExportToExternalSystemService getExportToBPartnerExternalSystem()
	{
		return exportToGRSService;
	}
}
