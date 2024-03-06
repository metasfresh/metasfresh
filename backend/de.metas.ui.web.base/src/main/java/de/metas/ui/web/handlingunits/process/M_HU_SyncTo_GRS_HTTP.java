/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.handlingunits.process;

import com.google.common.collect.ImmutableSet;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.export.hu.ExportHUToExternalSystemService;
import de.metas.externalsystem.grssignum.ExportHUToGRSService;
import de.metas.externalsystem.grssignum.ExternalSystemGRSSignumConfigId;
import de.metas.externalsystem.model.I_ExternalSystem_Config_GRSSignum;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.process.Param;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.util.Services;
import org.compiere.SpringContextHolder;

import java.util.Objects;
import java.util.Set;

public class M_HU_SyncTo_GRS_HTTP extends M_HU_SyncTo_ExternalSystem
{
	private final ExportHUToGRSService exportHUToGRSService = SpringContextHolder.instance.getBean(ExportHUToGRSService.class);

	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	private static final String PARAM_EXTERNAL_SYSTEM_CONFIG_GRSSIGNUM_ID = I_ExternalSystem_Config_GRSSignum.COLUMNNAME_ExternalSystem_Config_GRSSignum_ID;
	@Param(parameterName = PARAM_EXTERNAL_SYSTEM_CONFIG_GRSSIGNUM_ID)
	private int externalSystemConfigGRSSignumId;

	@Override
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.GRSSignum;
	}

	@Override
	protected Set<I_M_HU> getHUsToExport()
	{
		return streamSelectedRows()
				.map(HUEditorRow::cast)
				.filter(Objects::nonNull)
				.map(HUEditorRow::getHuId)
				.filter(Objects::nonNull)
				.map(handlingUnitsBL::getTopLevelParent)
				.collect(ImmutableSet.toImmutableSet());
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
	protected ExportHUToExternalSystemService getExportToHUExternalSystem()
	{
		return exportHUToGRSService;
	}
}
