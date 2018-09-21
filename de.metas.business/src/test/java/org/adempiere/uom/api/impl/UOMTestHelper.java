package org.adempiere.uom.api.impl;

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
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.IUOMConversionContext;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.model.I_M_Product;

import de.metas.util.Services;

public class UOMTestHelper
{
	private final Properties ctx;

	public UOMTestHelper(final Properties ctx)
	{
		super();
		this.ctx = ctx;
	}

	public I_M_Product createProduct(final String name, final I_C_UOM uom)
	{
		final I_M_Product product = InterfaceWrapperHelper.create(ctx, I_M_Product.class, ITrx.TRXNAME_None);
		product.setValue(name);
		product.setName(name);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		InterfaceWrapperHelper.save(product);
		return product;
	}

	public I_C_UOM createUOM(final int precision)
	{
		final I_C_UOM uom = InterfaceWrapperHelper.create(ctx, I_C_UOM.class, ITrx.TRXNAME_None);
		uom.setStdPrecision(precision);
		return uom;
	}

	public I_C_UOM createUOM(final String name, final int stdPrecision)
	{
		final int costingPrecision = 0;
		return createUOM(name, stdPrecision, costingPrecision);
	}

	public I_C_UOM createUOM(final String name, final int stdPrecision, final int costingPrecision)
	{
		final I_C_UOM uom = InterfaceWrapperHelper.create(ctx, I_C_UOM.class, ITrx.TRXNAME_None);
		uom.setName(name);
		uom.setUOMSymbol(name);
		uom.setX12DE355(name);
		uom.setStdPrecision(stdPrecision);
		uom.setCostingPrecision(costingPrecision);

		InterfaceWrapperHelper.save(uom);

		return uom;
	}

	public I_C_UOM createUOM(final String name, final int stdPrecision, final int costingPrecision, final String X12DE355)
	{
		final I_C_UOM uom = InterfaceWrapperHelper.create(ctx, I_C_UOM.class, ITrx.TRXNAME_None);
		uom.setName(name);
		uom.setStdPrecision(stdPrecision);
		uom.setCostingPrecision(costingPrecision);
		uom.setX12DE355(X12DE355);
		uom.setUOMSymbol(X12DE355);

		InterfaceWrapperHelper.save(uom);

		return uom;
	}

	public I_C_UOM_Conversion createUOMConversion(
			final I_M_Product product,
			final I_C_UOM uomFrom,
			final I_C_UOM uomTo,
			final BigDecimal multiplyRate,
			final BigDecimal divideRate)
	{
		final int productId = product == null ? -1 : product.getM_Product_ID();
		return createUOMConversion(productId, uomFrom, uomTo, multiplyRate, divideRate);
	}

	public I_C_UOM_Conversion createUOMConversion(
			final int productId,
			final I_C_UOM uomFrom,
			final I_C_UOM uomTo,
			final BigDecimal multiplyRate,
			final BigDecimal divideRate)
	{
		final I_C_UOM_Conversion conversion = InterfaceWrapperHelper.create(ctx, I_C_UOM_Conversion.class, ITrx.TRXNAME_None);

		conversion.setM_Product_ID(productId);
		conversion.setC_UOM_ID(uomFrom.getC_UOM_ID());
		conversion.setC_UOM_To_ID(uomTo.getC_UOM_ID());
		conversion.setMultiplyRate(multiplyRate);
		conversion.setDivideRate(divideRate);

		InterfaceWrapperHelper.save(conversion, ITrx.TRXNAME_None);

		return conversion;
	}

	public IUOMConversionContext createUOMConversionContext(final I_M_Product product)
	{
		return Services.get(IUOMConversionBL.class).createConversionContext(product);
	}
}
