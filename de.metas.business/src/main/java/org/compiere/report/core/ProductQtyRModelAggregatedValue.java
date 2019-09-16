package org.compiere.report.core;

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
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.I_M_Product;
import org.compiere.util.KeyNamePair;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.util.Services;

public class ProductQtyRModelAggregatedValue extends AbstractRModelAggregatedValue
{
	private static final transient Logger log = LogManager.getLogger(ProductQtyRModelAggregatedValue.class);

	private static final String COLUMNNAME_M_Product_ID = I_Fact_Acct.COLUMNNAME_M_Product_ID;
	private static final String COLUMNNAME_C_UOM_ID = I_Fact_Acct.COLUMNNAME_C_UOM_ID;

	// services
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	private I_M_Product product = null;
	private UOMConversionContext uomConversionCtx;
	private I_C_UOM uom;
	private BigDecimal qty;
	private boolean valid = true;

	@Override
	public void reset()
	{
		product = null;
		uomConversionCtx = null;
		uom = null;
		qty = null;
		valid = true;
	}

	@Override
	public void add(final RModelCalculationContext calculationCtx, final List<Object> row, final Object columnValue)
	{
		final IRModelMetadata metadata = calculationCtx.getMetadata();

		final BigDecimal qtyToAdd = toBigDecimalOrZero(columnValue);

		final int rowProductId = getM_Product_ID(metadata, row);
		if (rowProductId <= 0)
		{
			return;
		}

		final int rowUomId = getC_UOM_ID(metadata, row);
		if (rowUomId <= 0)
		{
			return;
		}

		add(rowProductId, rowUomId, qtyToAdd);
	}

	private void add(final int rowProductId, final int rowUomId, final BigDecimal rowQty)
	{
		// If this aggregation is no longer valid => do nothing
		if (!valid)
		{
			return;
		}

		final boolean isInitialized = product != null;
		if (!isInitialized)
		{
			uomConversionCtx = UOMConversionContext.of(rowProductId);
			uom = productBL.getStockUOM(rowProductId);
			qty = BigDecimal.ZERO;
		}
		//
		// Check if it's compatible
		else if (product.getM_Product_ID() != rowProductId)
		{
			valid = false;
			return;
		}

		final I_C_UOM rowUOM = InterfaceWrapperHelper.create(getCtx(), rowUomId, I_C_UOM.class, ITrx.TRXNAME_None);
		final BigDecimal rowQtyInBaseUOM;
		try
		{
			rowQtyInBaseUOM = uomConversionBL.convertQty(uomConversionCtx, rowQty, rowUOM, uom);
		}
		catch (final Exception e)
		{
			log.warn(e.getLocalizedMessage());

			valid = false;
			return;
		}
		qty = qty.add(rowQtyInBaseUOM);
	}

	@Override
	public Object getAggregatedValue(final RModelCalculationContext calculationCtx, final List<Object> groupRow)
	{
		if (!valid)
		{
			return null;
		}

		if (!isGroupBy(calculationCtx, COLUMNNAME_M_Product_ID))
		{
			return null;
		}

		final IRModelMetadata metadata = calculationCtx.getMetadata();

		final int groupProductId = getM_Product_ID(metadata, groupRow);
		if (groupProductId <= 0)
		{
			return null;
		}
		else if (product == null)
		{
			return BigDecimal.ZERO;
		}
		else if (product.getM_Product_ID() != groupProductId)
		{
			// shall not happen
			return null;
		}

		//
		// Update UOM column
		// FIXME: dirty hack
		{
			final KeyNamePair uomKNP = new KeyNamePair(uom.getC_UOM_ID(), uom.getName());
			setRowValueOrNull(metadata, groupRow, COLUMNNAME_C_UOM_ID, uomKNP);
		}

		return qty;
	}

	private final int getM_Product_ID(final IRModelMetadata metadata, final List<Object> row)
	{
		final KeyNamePair productKNP = getRowValueOrNull(metadata, row, COLUMNNAME_M_Product_ID, KeyNamePair.class);
		if (productKNP == null)
		{
			return -1;
		}
		return productKNP.getKey();
	}

	private final int getC_UOM_ID(final IRModelMetadata metadata, final List<Object> row)
	{
		final KeyNamePair uomKNP = getRowValueOrNull(metadata, row, COLUMNNAME_C_UOM_ID, KeyNamePair.class);
		if (uomKNP == null)
		{
			return -1;
		}
		return uomKNP.getKey();
	}

}
