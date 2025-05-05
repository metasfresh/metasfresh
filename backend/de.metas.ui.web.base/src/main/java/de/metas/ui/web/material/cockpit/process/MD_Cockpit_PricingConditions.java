package de.metas.ui.web.material.cockpit.process;

import java.util.Objects;
import java.util.Set;

import de.metas.product.ProductId;
import de.metas.util.lang.RepoIdAwares;
import org.compiere.model.I_M_Product;

import com.google.common.collect.ImmutableSet;

import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.material.cockpit.MaterialCockpitRow;
import de.metas.ui.web.order.pricingconditions.view.ProductPricingConditionsViewFactory;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class MD_Cockpit_PricingConditions extends MaterialCockpitViewBasedProcess
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final Set<ProductId> productIds = getProductIds();
		if (productIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		getResult().setRecordsToOpen(I_M_Product.Table_Name, RepoIdAwares.asRepoIdsSet(getProductIds()), ProductPricingConditionsViewFactory.WINDOW_ID_STRING);
		return MSG_OK;
	}

	private Set<ProductId> getProductIds()
	{
		return streamSelectedRows()
				.map(MaterialCockpitRow::getProductId)
				.filter(Objects::nonNull)
				.distinct()
				.limit(2)
				.collect(ImmutableSet.toImmutableSet());
	}

}
