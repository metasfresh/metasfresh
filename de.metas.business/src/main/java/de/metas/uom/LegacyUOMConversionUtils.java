package de.metas.uom;

import java.math.BigDecimal;
import java.util.Properties;

import org.compiere.model.I_C_UOM;
import org.compiere.model.MUOM;
import org.compiere.util.Env;

import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

@UtilityClass
@Deprecated
public class LegacyUOMConversionUtils
{
	@Deprecated
	private static BigDecimal convert(Properties ctx, int C_UOM_ID, int C_UOM_To_ID, BigDecimal qty)
	{
		final I_C_UOM uomFrom = MUOM.get(ctx, C_UOM_ID);

		final I_C_UOM uomTo = MUOM.get(ctx, C_UOM_To_ID);

		// return convert(ctx, uomFrom, uomTo, qty);

		return Services.get(IUOMConversionBL.class).convert(uomFrom, uomTo, qty).orElse(null);
	}

	/**
	 * Convert qty to target UOM and round.
	 * 
	 * @param ctx context
	 * @param C_UOM_ID from UOM
	 * @param qty qty
	 * @return minutes - 0 if not found
	 */
	public static int convertToMinutes(Properties ctx, int C_UOM_ID, BigDecimal qty)
	{

		if (qty == null)
			return 0;
		int C_UOM_To_ID = MUOM.getMinute_UOM_ID(ctx);
		if (C_UOM_ID == C_UOM_To_ID)
			return qty.intValue();
		//
		BigDecimal result = convert(ctx, C_UOM_ID, C_UOM_To_ID, qty);
		if (result == null)
			return 0;
		return result.intValue();
	}	// convert

	@Deprecated
	public static BigDecimal convert(int C_UOM_From_ID, int C_UOM_To_ID, BigDecimal qty, boolean StdPrecision)
	{
		final Properties ctx = Env.getCtx();
		final I_C_UOM uomFrom = MUOM.get(ctx, C_UOM_From_ID);
		final I_C_UOM uomTo = MUOM.get(ctx, C_UOM_To_ID);
		// return convert(ctx, uomFrom, uomTo, qty, StdPrecision);
		return Services.get(IUOMConversionBL.class).convert(uomFrom, uomTo, qty, StdPrecision);
	}

	@Deprecated
	public static BigDecimal convertFromProductUOM(
			final Properties ctx,
			final int M_Product_ID,
			final int C_UOM_Dest_ID,
			final BigDecimal qtyToConvert)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(M_Product_ID);
		final I_C_UOM uomDest = MUOM.get(ctx, C_UOM_Dest_ID);

		final BigDecimal qtyConv = Services.get(IUOMConversionBL.class).convertFromProductUOM(productId, uomDest, qtyToConvert);

		return qtyConv;
	}

	@Deprecated
	public static BigDecimal convertToProductUOM(
			final Properties ctx,
			final int M_Product_ID,
			final int C_UOM_Source_ID,
			final BigDecimal qtyToConvert)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(M_Product_ID);
		final UomId fromUomId = UomId.ofRepoIdOrNull(C_UOM_Source_ID);

		return Services.get(IUOMConversionBL.class).convertToProductUOM(productId, qtyToConvert, fromUomId);
	}
}
