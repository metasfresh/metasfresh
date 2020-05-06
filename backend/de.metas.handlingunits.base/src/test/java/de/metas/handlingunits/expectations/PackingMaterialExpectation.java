package de.metas.handlingunits.expectations;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;

import org.adempiere.util.test.ErrorMessage;
import org.compiere.model.I_M_Product;

public class PackingMaterialExpectation<ParentExpectationType> extends AbstractHUExpectation<ParentExpectationType>
{
	private I_M_Product product = null;
	private BigDecimal qty = null;

	public PackingMaterialExpectation()
	{
		super();
	}

	public PackingMaterialExpectation(final ParentExpectationType parentExpectation)
	{
		super(parentExpectation);
	}

	public PackingMaterialExpectation<ParentExpectationType> assertExpected(final I_M_Product product, final BigDecimal qty)
	{
		return assertExpected(newErrorMessage(), product, qty);
	}

	public PackingMaterialExpectation<ParentExpectationType> assertExpected(final ErrorMessage message, final I_M_Product product, final BigDecimal qty)
	{
		final ErrorMessage messageToUse = derive(message);

		assertModelEquals(messageToUse, this.product, product);
		assertEquals(messageToUse, this.qty, qty);
		return this;
	}

	public PackingMaterialExpectation<ParentExpectationType> product(final I_M_Product product)
	{
		this.product = product;
		return this;
	}

	public int getM_Product_ID()
	{
		return product == null ? -1 : product.getM_Product_ID();
	}

	public PackingMaterialExpectation<ParentExpectationType> qty(final BigDecimal qty)
	{
		this.qty = qty;
		return this;
	}

	public PackingMaterialExpectation<ParentExpectationType> qty(final int qty)
	{
		return qty(new BigDecimal(qty));
	}
}
