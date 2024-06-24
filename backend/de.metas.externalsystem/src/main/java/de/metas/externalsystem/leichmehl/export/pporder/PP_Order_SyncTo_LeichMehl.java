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

package de.metas.externalsystem.leichmehl.export.pporder;

import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.export.ExportToExternalSystemService;
import de.metas.externalsystem.export.pporder.PP_Order_SyncTo_ExternalSystem;
import de.metas.externalsystem.leichmehl.ExportPPOrderToLeichMehlService;
import de.metas.externalsystem.leichmehl.ExternalSystemLeichMehlConfigId;
import de.metas.externalsystem.model.I_ExternalSystem_Config_LeichMehl;
import de.metas.process.Param;
import org.compiere.SpringContextHolder;

public class PP_Order_SyncTo_LeichMehl extends PP_Order_SyncTo_ExternalSystem
{
	private final ExportPPOrderToLeichMehlService exportPPOrderToLeichMehlService = SpringContextHolder.instance.getBean(ExportPPOrderToLeichMehlService.class);

	private static final String PARAM_EXTERNAL_SYSTEM_CONFIG_LEICHMEHL_ID = I_ExternalSystem_Config_LeichMehl.COLUMNNAME_ExternalSystem_Config_LeichMehl_ID;
	@Param(parameterName = PARAM_EXTERNAL_SYSTEM_CONFIG_LEICHMEHL_ID)
	private int externalSystemConfigLeichMehlId;

	@Override
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.LeichUndMehl;
	}

	@Override
	protected IExternalSystemChildConfigId getExternalSystemChildConfigId()
	{
		return ExternalSystemLeichMehlConfigId.ofRepoId(externalSystemConfigLeichMehlId);
	}

	@Override
	protected String getExternalSystemParam()
	{
		return PARAM_EXTERNAL_SYSTEM_CONFIG_LEICHMEHL_ID;
	}

	@Override
	protected ExportToExternalSystemService getExportPPOrderToExternalSystem()
	{
		return exportPPOrderToLeichMehlService;
	}
}
