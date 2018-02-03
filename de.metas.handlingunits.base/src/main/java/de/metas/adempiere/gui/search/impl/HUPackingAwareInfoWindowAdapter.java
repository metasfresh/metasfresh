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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.apps.search.IInfoSimple;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.product.IProductPA;

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
	public I_M_Product getM_Product()
	{
		final int productId = getM_Product_ID();
		if (productId <= 0)
		{
			return null;
		}

		// NOTE: we assume M_Product is cached
		final I_M_Product product = InterfaceWrapperHelper.create(Env.getCtx(), productId, I_M_Product.class, ITrx.TRXNAME_None);
		return product;
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
	public void setM_HU_PI_Item_Product(final I_M_HU_PI_Item_Product huPiItemProduct)
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
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		final int piItemProductId = getM_HU_PI_Item_Product_ID();
		if (piItemProductId <= 0)
		{
			return null;
		}
		return retrieveM_HU_PI_Item_ProductById(piItemProductId);
	}

	// NOTE: commented @Cached out because is no longer applied anyways (not a service)
	// @Cached
	/* package */I_M_HU_PI_Item_Product retrieveM_HU_PI_Item_ProductById(final int huPiItemProductId)
	{
		if (huPiItemProductId <= 0)
		{
			return null;
		}
		final I_M_HU_PI_Item_Product huPiItemProduct = Services.get(IHUPIItemProductDAO.class).retrieveForId(Env.getCtx(), huPiItemProductId);
		return huPiItemProduct;
	}

	@Override
	public BigDecimal getQtyPacks()
	{
		final BigDecimal qty = infoWindow.getValue(rowIndexModel, IHUPackingAware.COLUMNNAME_QtyPacks);
		return qty;
	}

	@Override
	public void setQtyPacks(final BigDecimal qtyPacks)
	{
		infoWindow.setValueByColumnName(IHUPackingAware.COLUMNNAME_QtyPacks, rowIndexModel, qtyPacks);
	}

	@Override
	public I_C_UOM getC_UOM()
	{
		return getC_UOM(getM_Product_ID());
	}

	// NOTE: commented @Cached out because is no longer applied anyways (not a service)
	// @Cached
	/* package */I_C_UOM getC_UOM(final int productId)
	{
		return Services.get(IProductPA.class).retrieveProductUOM(Env.getCtx(), productId);
	}

	@Override
	public void setC_UOM(final I_C_UOM uom)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setC_BPartner(final I_C_BPartner partner)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public I_C_BPartner getC_BPartner()
	{
		final KeyNamePair bpartnerKNP = infoWindow.getValue(rowIndexModel, IHUPackingAware.COLUMNNAME_C_BPartner_ID);
		if (bpartnerKNP == null || bpartnerKNP.getKey() <= 0)
		{
			return null;
		}

		return retrieveC_BPartnerById(bpartnerKNP.getKey());
	}

	// NOTE: commented @Cached out because is no longer applied anyways (not a service)
	// @Cached
	/* package */I_C_BPartner retrieveC_BPartnerById(final int partnerID)
	{
		if (partnerID <= 0)
		{
			return null;
		}
		final I_C_BPartner partner = InterfaceWrapperHelper.create(Env.getCtx(), partnerID, I_C_BPartner.class, ITrx.TRXNAME_None);

		return partner;
	}

	// DateOrdered does not exist yet in Info Windows

	@Override
	public void setDateOrdered(final Timestamp dateOrdered)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Timestamp getDateOrdered()
	{
		throw new UnsupportedOperationException();
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
