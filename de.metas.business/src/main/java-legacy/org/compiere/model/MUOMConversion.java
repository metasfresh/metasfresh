/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.uom.api.IUOMConversionBL;
import org.compiere.util.Env;

import de.metas.util.Services;

/**
 * Unit of Measure Conversion Model
 *
 * @author Jorg Janke
 * @version $Id: MUOMConversion.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MUOMConversion extends X_C_UOM_Conversion
{
	private static final String SYSCONFIG_ProductUOMConversionUOMValidate = "ProductUOMConversionUOMValidate";
	private static final String SYSCONFIG_ProductUOMConversionRateValidate = "ProductUOMConversionRateValidate";
	private static final String MSG_ProductUOMConversionUOMError = "ProductUOMConversionUOMError";
	private static final String MSG_ProductUOMConversionRateError = "ProductUOMConversionRateError";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8449239579085422641L;

	@Deprecated
	static public BigDecimal convert(Properties ctx, int C_UOM_ID, int C_UOM_To_ID, BigDecimal qty)
	{
		final I_C_UOM uomFrom = MUOM.get(ctx, C_UOM_ID);

		final I_C_UOM uomTo = MUOM.get(ctx, C_UOM_To_ID);

		// return convert(ctx, uomFrom, uomTo, qty);

		return Services.get(IUOMConversionBL.class).convert(ctx, uomFrom, uomTo, qty);
	}

	/**
	 * Convert qty to target UOM and round.
	 * 
	 * @param ctx context
	 * @param C_UOM_ID from UOM
	 * @param qty qty
	 * @return minutes - 0 if not found
	 */
	static public int convertToMinutes(Properties ctx,
			int C_UOM_ID, BigDecimal qty)
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

	/**
	 * Calculate End Date based on start date and qty
	 * 
	 * @param ctx context
	 * @param startDate date
	 * @param C_UOM_ID UOM
	 * @param qty qty
	 * @return end date
	 */
	static public Timestamp getEndDate(Properties ctx, Timestamp startDate, int C_UOM_ID, BigDecimal qty)
	{
		GregorianCalendar endDate = new GregorianCalendar();
		endDate.setTime(startDate);
		//
		int minutes = MUOMConversion.convertToMinutes(ctx, C_UOM_ID, qty);
		endDate.add(Calendar.MINUTE, minutes);
		//
		Timestamp retValue = new Timestamp(endDate.getTimeInMillis());
		// log.info( "TimeUtil.getEndDate", "Start=" + startDate
		// + ", Qty=" + qty + ", End=" + retValue);
		return retValue;
	}	// startDate

	@Deprecated
	public static BigDecimal deriveRate(Properties ctx,
			int C_UOM_ID, int C_UOM_To_ID)
	{
		final I_C_UOM uomFrom = MUOM.get(ctx, C_UOM_ID);
		final I_C_UOM uomTo = MUOM.get(ctx, C_UOM_To_ID);

		final BigDecimal deriveRate = Services.get(IUOMConversionBL.class).deriveRate(ctx, uomFrom, uomTo);

		return deriveRate;
	}

	@Deprecated
	public static BigDecimal convert(int C_UOM_From_ID, int C_UOM_To_ID, BigDecimal qty, boolean StdPrecision)
	{
		final Properties ctx = Env.getCtx();
		final I_C_UOM uomFrom = MUOM.get(ctx, C_UOM_From_ID);
		final I_C_UOM uomTo = MUOM.get(ctx, C_UOM_To_ID);
		// return convert(ctx, uomFrom, uomTo, qty, StdPrecision);
		return Services.get(IUOMConversionBL.class).convert(ctx, uomFrom, uomTo, qty, StdPrecision);
	}

	@Deprecated
	static public BigDecimal convertFromProductUOM(
			final Properties ctx,
			final int M_Product_ID,
			final int C_UOM_Dest_ID,
			final BigDecimal qtyToConvert)
	{
		final MProduct product = MProduct.get(ctx, M_Product_ID);
		final I_C_UOM uomDest = MUOM.get(ctx, C_UOM_Dest_ID);
		// return convertFromProductUOM(ctx, product, uomDest, qtyToConvert);

		final BigDecimal qtyConv = Services.get(IUOMConversionBL.class).convertFromProductUOM(ctx, product, uomDest, qtyToConvert);

		return qtyConv;
	}

	@Deprecated
	static public BigDecimal convertToProductUOM(
			final Properties ctx,
			final int M_Product_ID,
			final int C_UOM_Source_ID,
			final BigDecimal qtyToConvert)
	{
		final I_M_Product product = MProduct.get(ctx, M_Product_ID);
		final I_C_UOM uomSource = MUOM.get(ctx, C_UOM_Source_ID);

		final BigDecimal qtyConvToProductUOM = Services.get(IUOMConversionBL.class).convertToProductUOM(ctx, product, uomSource, qtyToConvert);
		return qtyConvToProductUOM;
	}

	/**************************************************************************
	 * Default Constructor
	 *
	 * @param ctx context
	 * @param C_UOM_Conversion_ID id
	 * @param trxName transaction
	 */
	public MUOMConversion(Properties ctx, int C_UOM_Conversion_ID, String trxName)
	{
		super(ctx, C_UOM_Conversion_ID, trxName);
	}	// MUOMConversion

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MUOMConversion(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MUOMConversion

	/**
	 * Before Save
	 *
	 * @param newRecord new
	 * @return true if can be saved
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		// From - To is the same
		if (getC_UOM_ID() == getC_UOM_To_ID())
		{
			throw new AdempiereException("@C_UOM_ID@ = @C_UOM_To_ID@");
		}
		// Nothing to convert
		if (getMultiplyRate().compareTo(Env.ZERO) <= 0)
		{
			throw new AdempiereException("@MultiplyRate@ <= 0");
		}

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		// Enforce Product UOM
		if (sysConfigBL.getBooleanValue(SYSCONFIG_ProductUOMConversionUOMValidate, true))
		{
			if (getM_Product_ID() != 0
					&& (newRecord || is_ValueChanged(I_C_UOM_Conversion.COLUMNNAME_M_Product_ID)))
			{
				final I_M_Product product = MProduct.get(getCtx(), getM_Product_ID());
				if (product.getC_UOM_ID() != getC_UOM_ID())
				{
					MUOM uom = MUOM.get(getCtx(), product.getC_UOM_ID());
					throw new AdempiereException("@" + MSG_ProductUOMConversionUOMError + "@ " + uom.getName());
				}
			}
		}

		// The Product UoM needs to be the smallest UoM - Multiplier must be < 0; Divider must be > 0
		if (sysConfigBL.getBooleanValue(SYSCONFIG_ProductUOMConversionRateValidate, true))
		{
			if (getM_Product_ID() != 0 && getDivideRate().compareTo(Env.ONE) < 0)
			{
				throw new AdempiereException("@" + MSG_ProductUOMConversionRateError + "@");
			}
		}

		return true;
	}	// beforeSave

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MUOMConversion[");
		sb.append(get_ID()).append("-C_UOM_ID=").append(getC_UOM_ID())
				.append(",C_UOM_To_ID=").append(getC_UOM_To_ID())
				.append(",M_Product_ID=").append(getM_Product_ID())
				.append("-Multiply=").append(getMultiplyRate())
				.append("/Divide=").append(getDivideRate())
				.append("]");
		return sb.toString();
	}	// toString

}	// UOMConversion
