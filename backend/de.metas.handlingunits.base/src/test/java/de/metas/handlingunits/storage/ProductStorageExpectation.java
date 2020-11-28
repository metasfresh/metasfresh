package de.metas.handlingunits.storage;

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

import org.hamcrest.Matchers;
import org.junit.Assert;

/**
 * {@link IProductStorage} expectation
 *
 * @author tsa
 *
 */
public class ProductStorageExpectation
{
	private BigDecimal qtyCapacity;
	private BigDecimal qty;
	private BigDecimal qtyFree;

	public ProductStorageExpectation()
	{
		super();
	}

	public ProductStorageExpectation assertExpected(final IProductStorage productStorage)
	{
		final String message = null;
		return assertExpected(message, productStorage);
	}

	public ProductStorageExpectation assertExpected(final String message, final IProductStorage productStorage)
	{
		final String prefix = (message == null ? "" : message)
				+ "\nProduct storage: " + productStorage
				+ "\n\nInvalid: ";

		Assert.assertNotNull(prefix + "productStorage is null", productStorage);

		if (qtyCapacity != null)
		{
			Assert.assertThat(prefix + "QtyCapacity",
					productStorage.getQtyCapacity(),
					Matchers.comparesEqualTo(qtyCapacity));
		}
		if (qty != null)
		{
			Assert.assertThat(prefix + "Qty",
					productStorage.getQty().toBigDecimal(),
					Matchers.comparesEqualTo(qty));
		}

		if (qtyFree != null)
		{
			Assert.assertThat(prefix + "QtyFree",
					productStorage.getQtyFree(),
					Matchers.comparesEqualTo(qtyFree));
		}
		else
		{
			Assert.assertThat(prefix + "QtyFree = QtyCapacity - Qty",
					productStorage.getQtyFree(),
					Matchers.comparesEqualTo(productStorage.getQtyCapacity().subtract(productStorage.getQty().toBigDecimal())));
		}

		return this;
	}

	public ProductStorageExpectation qtyCapacity(final BigDecimal qtyCapacity)
	{
		this.qtyCapacity = qtyCapacity;
		return this;
	}

	public ProductStorageExpectation qty(final BigDecimal qty)
	{
		this.qty = qty;
		return this;
	}

	public ProductStorageExpectation qtyFree(final BigDecimal qtyFree)
	{
		this.qtyFree = qtyFree;
		return this;
	}
}
