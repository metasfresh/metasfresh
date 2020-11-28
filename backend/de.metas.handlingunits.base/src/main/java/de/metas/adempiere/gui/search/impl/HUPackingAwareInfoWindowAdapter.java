package de.metas.adempiere.gui.search.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.search.IInfoSimple;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.product.IProductBL;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Wraps given Info Window grid record and makes it behave like an {@link IHUPackingAware}.
 *
 * @author tsa
 *
 */
/* package */class HUPackingAwareInfoWindowAdapter implements IHUPackingAware
{
	private final IInfoSimple infoWindow;
	private final int rowIndexModel;

	public HUPackingAwareInfoWindowAdapter(final IInfoSimple infoWindow, final int rowIndexModel)
	{
		super();

		Check.assumeNotNull(infoWindow, "infoWindow not null");
		this.infoWindow = infoWindow;

		Check.assume(rowIndexModel >= 0, "rowIndexModel >= 0");
		this.rowIndexModel = rowIndexModel;
	}

	@Override
	public int getM_Product_ID()
	{
		return infoWindow.getRecordId(rowIndexModel);
	}

	@Override
	public void setM_Product_ID(final int productId)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getM_AttributeSetInstance_ID()
	{
		final Object asiObj = infoWindow.getValue(rowIndexModel, COLUMNNAME_M_AttributeSetInstance_ID);
		if (asiObj == null)
		{
			return -1;
		}
		else if (asiObj instanceof KeyNamePair)
		{
			return ((KeyNamePair)asiObj).getKey();
		}
		else if (asiObj instanceof Number)
		{
			return ((Number)asiObj).intValue();
		}
		else
		{
			throw new AdempiereException("Unsupported value for " + COLUMNNAME_M_AttributeSetInstance_ID + ": " + asiObj);
		}
	}

	@Override
	public void setM_AttributeSetInstance_ID(final int M_AttributeSetInstance_ID)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setQty(final BigDecimal qty)
	{
		infoWindow.setValueByColumnName(IHUPackingAware.COLUMNNAME_Qty_CU, rowIndexModel, qty);
	}

	@Override
	public BigDecimal getQty()
	{
		final BigDecimal qty = infoWindow.getValue(rowIndexModel, IHUPackingAware.COLUMNNAME_Qty_CU);
		return qty;
	}

	@Override
	public void setM_HU_PI_Item_Product_ID(final int huPiItemProductId)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getM_HU_PI_Item_Product_ID()
	{
		final KeyNamePair huPiItemProductKNP = infoWindow.getValue(rowIndexModel, IHUPackingAware.COLUMNNAME_M_HU_PI_Item_Product_ID);
		if (huPiItemProductKNP == null)
		{
			return -1;
		}

		final int piItemProductId = huPiItemProductKNP.getKey();
		if (piItemProductId <= 0)
		{
			return -1;
		}

		return piItemProductId;
	}

	@Override
	public BigDecimal getQtyTU()
	{
		final BigDecimal qty = infoWindow.getValue(rowIndexModel, IHUPackingAware.COLUMNNAME_QtyPacks);
		return qty;
	}

	@Override
	public void setQtyTU(final BigDecimal qtyPacks)
	{
		infoWindow.setValueByColumnName(IHUPackingAware.COLUMNNAME_QtyPacks, rowIndexModel, qtyPacks);
	}

	@Override
	public int getC_UOM_ID()
	{
		return Services.get(IProductBL.class).getStockUOMId(getM_Product_ID()).getRepoId();
	}

	@Override
	public void setC_UOM_ID(final int uomId)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setC_BPartner_ID(final int partnerId)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int getC_BPartner_ID()
	{
		final KeyNamePair bpartnerKNP = infoWindow.getValue(rowIndexModel, IHUPackingAware.COLUMNNAME_C_BPartner_ID);
		return bpartnerKNP != null ? bpartnerKNP.getKey() : -1;
	}

	@Override
	public boolean isInDispute()
	{
		// there is no InDispute flag to be considered
		return false;
	}

	@Override
	public void setInDispute(final boolean inDispute)
	{
		throw new UnsupportedOperationException();
	}
}
