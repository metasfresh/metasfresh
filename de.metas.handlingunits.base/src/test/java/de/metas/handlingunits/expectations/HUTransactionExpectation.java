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

import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.IReference;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.Assert;

import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;

public class HUTransactionExpectation<ParentExpectationType> extends AbstractHUExpectation<ParentExpectationType>
{

	private I_M_HU _hu;
	private I_M_Product _product;
	private BigDecimal _qty;
	private I_C_UOM _uom;
	private IReference<I_M_HU> _vhu;
	private Object _referencedModel;

	public HUTransactionExpectation()
	{
		this(null);
	}

	public HUTransactionExpectation(final ParentExpectationType parentExpectation)
	{
		super(parentExpectation);
	}

	@Override
	public String toString()
	{
		return "HUTransactionExpectation ["
				+ "\n product=" + _product
				+ "\n qty=" + _qty
				+ "\n uom=" + _uom
				+ "\n hu=" + _hu
				+ "\n vhu=" + _vhu
				+ "\n referencedModel=" + _referencedModel
				+ "]";
	}

	public HUTransactionExpectation<ParentExpectationType> assertExpected(final IHUTransactionCandidate transaction)
	{
		final String message = "";
		return assertExpected(message, transaction);
	}

	public HUTransactionExpectation<ParentExpectationType> assertExpected(final String message, final IHUTransactionCandidate transaction)
	{
		final String prefix = (message == null ? "" : message)
				+ "\nHU Transaction: " + transaction
				+ "\n"
				+ "\nInvalid ";

		Assert.assertNotNull(message + "HU Transaction not null", transaction);

		if (_hu != null)
		{
			assertModelEquals(prefix + "HU", _hu, transaction.getM_HU());
		}
		if (_product != null)
		{
			assertModelEquals(prefix + "Product", _product, transaction.getProduct());
		}
		if (_qty != null)
		{
			assertEquals(prefix + "Qty", _qty, transaction.getQuantity().getQty());
		}
		if (_uom != null)
		{
			assertModelEquals(prefix + "UOM", _uom, transaction.getQuantity().getUOM());
		}
		if (_vhu != null)
		{
			final I_M_HU_Item actual_vhuItem = transaction.getVHU_Item();
			final I_M_HU actual_vhu = actual_vhuItem == null ? null : actual_vhuItem.getM_HU();
			assertModelEquals(prefix + "VHU", _vhu.getValue(), actual_vhu);
		}
		if (_referencedModel != null)
		{
			assertModelEquals(prefix + "ReferencedModel", _referencedModel, transaction.getReferencedModel());
		}

		return this;
	}

	public HUTransactionExpectation<ParentExpectationType> hu(final I_M_HU hu)
	{
		this._hu = hu;
		return this;
	}

	public HUTransactionExpectation<ParentExpectationType> product(final I_M_Product product)
	{
		this._product = product;
		return this;
	}

	public HUTransactionExpectation<ParentExpectationType> qty(final BigDecimal qty)
	{
		this._qty = qty;
		return this;
	}

	public HUTransactionExpectation<ParentExpectationType> qty(final String qtyStr)
	{
		return qty(new BigDecimal(qtyStr));
	}

	public HUTransactionExpectation<ParentExpectationType> qty(final int qty)
	{
		return qty(new BigDecimal(qty));
	}

	public HUTransactionExpectation<ParentExpectationType> uom(final I_C_UOM uom)
	{
		this._uom = uom;
		return this;
	}

	public HUTransactionExpectation<ParentExpectationType> virtualHU(final I_M_HU vhu)
	{
		final IMutable<I_M_HU> vhuRef = new Mutable<>(vhu);
		return virtualHU(vhuRef);
	}

	public HUTransactionExpectation<ParentExpectationType> virtualHU(final IReference<I_M_HU> vhu)
	{
		this._vhu = vhu;
		return this;
	}

	public HUTransactionExpectation<ParentExpectationType> referencedModel(final Object referencedModel)
	{
		this._referencedModel = referencedModel;
		return this;
	}

}
