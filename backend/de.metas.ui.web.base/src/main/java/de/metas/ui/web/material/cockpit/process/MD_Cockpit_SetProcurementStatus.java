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

import de.metas.cache.CacheMgt;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Product;

import java.util.Set;

import static de.metas.ui.web.material.cockpit.MaterialCockpitRow.SYSCFG_PREFIX;
import static de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.SYSCFG_Layout;
import static de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper.SYSCFG_DISABLED;
import static de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper.SYSCFG_DISPLAYED_SUFFIX;

public class MD_Cockpit_SetProcurementStatus extends MaterialCockpitViewBasedProcess
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private static final String PARAM_PROCUREMENT_STATUS = X_M_Product.COLUMNNAME_ProcurementStatus;
	@Param(parameterName = PARAM_PROCUREMENT_STATUS)
	String procurementStatus;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No MaterialCockpitRows are selected");
		}

		final String commaSeparatedFieldNames = sysConfigBL.getValue(SYSCFG_Layout, (String)null);
		final String commaSeparatedFieldNamesNorm = StringUtils.trimBlankToNull(commaSeparatedFieldNames);
		if (Check.isNotBlank(commaSeparatedFieldNamesNorm)
				&& !commaSeparatedFieldNamesNorm.equals(SYSCFG_DISABLED)
				&& !commaSeparatedFieldNamesNorm.contains(PARAM_PROCUREMENT_STATUS))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Column Procurement Status isn't displayed");
		}

		final boolean paramIsDisplayed = sysConfigBL.getBooleanValue(SYSCFG_PREFIX + "." + PARAM_PROCUREMENT_STATUS + SYSCFG_DISPLAYED_SUFFIX, false);
		if (Check.isBlank(commaSeparatedFieldNamesNorm) && !paramIsDisplayed)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Column Procurement Status isn't displayed");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final Set<ProductId> productIds = getSelectedProductIdsRecursively();

		final ICompositeQueryUpdater<I_M_Product> updater = queryBL.createCompositeQueryUpdater(I_M_Product.class)
				.addSetColumnValue(X_M_Product.COLUMNNAME_ProcurementStatus, procurementStatus);

		queryBL.createQueryBuilder(I_M_Product.class)
				.addInArrayFilter(I_M_Product.COLUMNNAME_M_Product_ID, productIds)
				.create()
				.updateDirectly(updater);

		productIds.forEach(this::cacheResetProduct);
		invalidateView();
		invalidateParentView();

		return MSG_OK;
	}

	private void cacheResetProduct(final ProductId productId)
	{
		CacheMgt.get().reset(I_M_Product.Table_Name, productId.getRepoId());
	}

}
