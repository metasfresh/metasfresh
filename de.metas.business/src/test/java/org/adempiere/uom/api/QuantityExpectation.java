package org.adempiere.uom.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.compiere.model.I_C_UOM;
import org.hamcrest.Matchers;
import org.junit.Assert;

import de.metas.quantity.Quantity;

public class QuantityExpectation
{
	private BigDecimal _qty;
	private boolean _qtySame;
	private I_C_UOM _uom;

	private BigDecimal _sourceQty;
	private boolean _sourceQtySame;
	private I_C_UOM _sourceUOM;
	private boolean _sourceSameAsCurrent;

	public QuantityExpectation()
	{
		super();
	}

	public QuantityExpectation assertExpected(final Quantity quantity)
	{
		final String message = null;
		return assertExpected(message, quantity);
	}

	public QuantityExpectation assertExpected(final String message, final Quantity quantity)
	{
		final String prefix = (message == null ? "" : message)
				+ "\n Quantity: " + quantity
				+ "\n Invalid: ";

		Assert.assertNotNull(prefix + "Quantity shall not be null", quantity);

		if (_qty != null)
		{
			assertSameOrEquals(prefix + "Qty", _qtySame, _qty, quantity.getQty());
		}
		if (_uom != null)
		{
			Assert.assertSame(prefix + "UOM", _uom, quantity.getUOM());
		}

		if (_sourceQty != null)
		{
			assertSameOrEquals(prefix + "Source Qty", _sourceQtySame, _sourceQty, quantity.getSourceQty());
		}
		if (_sourceUOM != null)
		{
			Assert.assertSame(prefix + "Source UOM", _sourceUOM, quantity.getSourceUOM());
		}

		if (_sourceSameAsCurrent)
		{
			assertSameOrEquals(prefix + "Source Qty (same as current)", true, quantity.getQty(), quantity.getSourceQty());
			Assert.assertSame(prefix + "Source UOM (same as current)", quantity.getUOM(), quantity.getSourceUOM());
		}

		return this;
	}

	public QuantityExpectation qty(final BigDecimal qty)
	{
		this._qty = qty;
		this._qtySame = false;
		return this;
	}

	public QuantityExpectation qty(final String qtyStr)
	{
		return qty(new BigDecimal(qtyStr));
	}

	public QuantityExpectation sameQty(final BigDecimal qty)
	{
		this._qty = qty;
		this._qtySame = true;
		return this;
	}

	public QuantityExpectation uom(final I_C_UOM uom)
	{
		this._uom = uom;
		return this;
	}

	public QuantityExpectation sourceQty(final BigDecimal sourceQty)
	{
		this._sourceQty = sourceQty;
		this._sourceQtySame = false;
		return this;
	}

	public QuantityExpectation sourceQty(final String sourceQtyStr)
	{
		return sourceQty(new BigDecimal(sourceQtyStr));
	}

	public QuantityExpectation sameSourceQty(final BigDecimal sourceQty)
	{
		this._sourceQty = sourceQty;
		this._sourceQtySame = true;
		return this;
	}

	public QuantityExpectation sourceUOM(final I_C_UOM sourceUOM)
	{
		this._sourceUOM = sourceUOM;
		return this;
	}

	public QuantityExpectation sourceSameAsCurrent()
	{
		this._sourceSameAsCurrent = true;
		return this;
	}

	private static void assertSameOrEquals(final String message, final boolean expectSame, final BigDecimal expected, final BigDecimal actual)
	{
		if (expectSame)
		{
			Assert.assertSame(message, expected, actual);
		}
		else
		{
			Assert.assertThat(message, actual, Matchers.comparesEqualTo(expected));
		}
	}

}
