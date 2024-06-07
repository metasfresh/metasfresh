package de.metas.ui.web.material.cockpit;

import de.metas.common.util.CoalesceUtil;
import de.metas.dimension.DimensionSpec;
import de.metas.dimension.IDimensionspecDAO;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.Env;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public final class MaterialCockpitUtil
{
	public static final String WINDOWID_MaterialCockpitView_String = "540376";
	public static final WindowId WINDOWID_MaterialCockpitView = WindowId.fromJson(WINDOWID_MaterialCockpitView_String);

	public static final String WINDOWID_MaterialCockpit_Detail_String = "540395";

	public static final String WINDOW_MaterialCockpit_StockDetail_String = "540457";
	public static final WindowId WINDOW_MaterialCockpit_StockDetailView = WindowId.of(Integer.parseInt(WINDOW_MaterialCockpit_StockDetail_String));

	public static final String SYSCONFIG_DIM_SPEC_INTERNAL_NAME = "de.metas.ui.web.material.cockpit.DIM_Dimension_Spec.InternalName";
	public static final String DEFAULT_DIM_SPEC_INTERNAL_NAME = "Material_Cockpit_Default_Spec";

	public static final String SYSCONFIG_DETAILS_ROW_AGGREGATION = "de.metas.ui.web.material.cockpit.DetailsRowAggregation";

	private static final String SYSCFG_I_QtyDemand_QtySupply_V_ACTIVE = "de.metas.ui.web.material.cockpit.I_QtyDemand_QtySupply_V.IsActive";

	public static final String DONT_FILTER = "DONT_FILTER";
	public static final String NON_EMPTY = "NON_EMPTY";

	private MaterialCockpitUtil()
	{
	}

	public static DimensionSpec retrieveDimensionSpec()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final IDimensionspecDAO dimensionspecDAO = Services.get(IDimensionspecDAO.class);

		final String dimSpecName = sysConfigBL.getValue(
				SYSCONFIG_DIM_SPEC_INTERNAL_NAME,
				DEFAULT_DIM_SPEC_INTERNAL_NAME,
				Env.getAD_Client_ID(),
				Env.getAD_Org_ID(Env.getCtx()));

		final DimensionSpec dimensionSpec = dimensionspecDAO.retrieveForInternalNameOrNull(CoalesceUtil.firstNotEmptyTrimmed(
				dimSpecName,
				DEFAULT_DIM_SPEC_INTERNAL_NAME));

		return Check.assumeNotNull(dimensionSpec, "Unable to load DIM_Dimension_Spec record with InternalName={}", dimSpecName);
	}

	public static boolean isI_QtyDemand_QtySupply_VActive()
	{
		return Services.get(ISysConfigBL.class).getBooleanValue(SYSCFG_I_QtyDemand_QtySupply_V_ACTIVE, true);
	}
}
