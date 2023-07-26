/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2020 metas GmbH
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

package org.eevolution.api.impl;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.IProductCostingBL;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.compiere.model.I_M_Product;

public class MockedProductCostingBL implements IProductCostingBL
{
	@NonNull
	final CostingLevel defaultCostingLevel;
	@NonNull
	final CostingMethod defaultCostingMethod;

	public MockedProductCostingBL(
			@NonNull final CostingLevel defaultCostingLevel,
			@NonNull final CostingMethod defaultCostingMethod)
	{
		this.defaultCostingLevel = defaultCostingLevel;
		this.defaultCostingMethod = defaultCostingMethod;
	}

	@Override
	public @NonNull CostingLevel getCostingLevel(
			final ProductId productId,
			final AcctSchemaId acctSchemaId)
	{
		return defaultCostingLevel;
	}

	@Override
	public @NonNull CostingLevel getCostingLevel(
			final ProductId productId,
			final AcctSchema acctSchema)
	{
		return defaultCostingLevel;
	}

	@Override
	public @NonNull CostingLevel getCostingLevel(
			final I_M_Product product,
			final AcctSchema acctSchema)
	{
		return defaultCostingLevel;
	}

	@Override
	public @NonNull CostingMethod getCostingMethod(
			final I_M_Product product,
			final AcctSchema acctSchema)
	{
		return defaultCostingMethod;
	}

	@Override
	public @NonNull CostingMethod getCostingMethod(
			final ProductId productId,
			final AcctSchema acctSchema)
	{
		return defaultCostingMethod;
	}

	@Override
	public @NonNull CostingMethod getCostingMethod(
			final ProductId productId,
			final AcctSchemaId acctSchemaId)
	{
		return defaultCostingMethod;
	}
}
