/*
 * #%L
 * de.metas.business
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

package de.metas.product.process;

import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;

import java.util.Date;

public class M_ProductPrice_CreateProductPrices extends JavaProcess
{

	private static final String PARAM_VALID_FROM = "ValidFrom";
	@Param(parameterName = M_ProductPrice_CreateProductPrices.PARAM_VALID_FROM, mandatory = true)
	private Date p_Date;

	private final IProductBL productBL = Services.get(IProductBL.class);

	@Override
	protected String doIt() throws Exception
	{
		ProductId productId = ProductId.ofRepoId(this.getProcessInfo().getRecord_ID());
		productBL.createProductPrices(productId, p_Date);

		return MSG_OK;
	}
}
