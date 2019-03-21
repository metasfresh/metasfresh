package de.metas.uom.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import de.metas.product.ProductId;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;

public class UOMTestHelper
{
	private final Properties ctx;

	public UOMTestHelper()
	{
		this(Env.getCtx());
	}

	public UOMTestHelper(@NonNull final Properties ctx)
	{
		this.ctx = ctx;
	}

	public ProductId createProduct(final String name, final I_C_UOM uom)
	{
		return createProduct(name, UomId.ofRepoId(uom.getC_UOM_ID()));
	}

	public ProductId createProduct(final String name, final UomId uomId)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue(name);
		product.setName(name);
		product.setC_UOM_ID(uomId.getRepoId());
		saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
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

	public void createUOMConversion(
			final I_M_Product product,
			final I_C_UOM uomFrom,
			final I_C_UOM uomTo,
			final BigDecimal multiplyRate,
			final BigDecimal divideRate)
	{
		Services.get(IUOMConversionDAO.class).createUOMConversion(CreateUOMConversionRequest.builder()
				.productId(product == null ? null : ProductId.ofRepoId(product.getM_Product_ID()))
				.fromUomId(UomId.ofRepoId(uomFrom.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(uomTo.getC_UOM_ID()))
				.fromToMultiplier(multiplyRate)
				.toFromMultiplier(divideRate)
				.build());
	}

	public void createUOMConversion(
			final ProductId productId,
			final I_C_UOM uomFrom,
			final I_C_UOM uomTo,
			final BigDecimal multiplyRate,
			final BigDecimal divideRate)
	{
		createUOMConversion(CreateUOMConversionRequest.builder()
				.productId(productId)
				.fromUomId(UomId.ofRepoId(uomFrom.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(uomTo.getC_UOM_ID()))
				.fromToMultiplier(multiplyRate)
				.toFromMultiplier(divideRate)
				.build());
	}

	public void createUOMConversion(@NonNull final CreateUOMConversionRequest request)
	{
		Services.get(IUOMConversionDAO.class).createUOMConversion(request);
	}

}
