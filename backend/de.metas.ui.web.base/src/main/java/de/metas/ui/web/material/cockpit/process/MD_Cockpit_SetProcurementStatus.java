/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.material.cockpit.process;

import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.X_MD_Cockpit;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ISysConfigBL;

import java.util.Set;

import static de.metas.ui.web.material.cockpit.MaterialCockpitRow.SYSCFG_PREFIX;
import static de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.SYSCFG_Layout;
import static de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper.SYSCFG_DISPLAYED_SUFFIX;

public class MD_Cockpit_SetProcurementStatus extends MaterialCockpitViewBasedProcess
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private static final String PARAM_PROCUREMENT_STATUS = X_MD_Cockpit.COLUMNNAME_ProcurementStatus;
	@Param(parameterName = PARAM_PROCUREMENT_STATUS)
	String procurementStatus;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No MaterialCockpitrows are selected");
		}

		final Set<Integer> cockpitRowIds = getSelectedCockpitRecordIdsRecursively();
		if (cockpitRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("The selected rows are just dummys with all-zero");
		}

		final String commaSeparatedFieldNames = sysConfigBL.getValue(SYSCFG_Layout, (String)null);
		if(Check.isNotBlank(commaSeparatedFieldNames)
				&& !commaSeparatedFieldNames.contains(PARAM_PROCUREMENT_STATUS))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Column Procurement Status isn't displayed");
		}

		final boolean paramIsDisplayed = sysConfigBL.getBooleanValue(SYSCFG_PREFIX + "." + PARAM_PROCUREMENT_STATUS + SYSCFG_DISPLAYED_SUFFIX, false);
		if(Check.isBlank(commaSeparatedFieldNames) && !paramIsDisplayed)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Column Procurement Status isn't displayed");
		}

		return ProcessPreconditionsResolution.accept();
	}
	@Override
	protected String doIt() throws Exception
	{
		final Set<Integer> cockpitRowIds = getSelectedCockpitRecordIdsRecursively();

		final ICompositeQueryUpdater<I_MD_Cockpit> updater = queryBL.createCompositeQueryUpdater(I_MD_Cockpit.class)
						.addSetColumnValue(X_MD_Cockpit.COLUMNNAME_ProcurementStatus, procurementStatus);

		queryBL.createQueryBuilder(I_MD_Cockpit.class)
				.addInArrayFilter(X_MD_Cockpit.COLUMNNAME_MD_Cockpit_ID, cockpitRowIds)
				.create()
				.updateDirectly(updater);

		invalidateView();

		return MSG_OK;
	}


}
