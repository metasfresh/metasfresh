package de.metas.materialtracking.ch.lagerkonf.impl;

/*
 * #%L
 * de.metas.materialtracking
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.adempiere.uom.api.IUOMDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;

import de.metas.currency.ICurrencyDAO;
import de.metas.materialtracking.qualityBasedInvoicing.IInvoicingItem;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityBasedInvoicingBL;
import de.metas.product.IProductPA;

public class HardCodedQualityBasedConfig extends AbstractQualityBasedConfig
{

	// Constants
	public static final String CURRENCY_ISO = "CHF";
	public final static String M_PRODUCT_SCRAP_VALUE = "MT_Scrap_Erdbesatz";
	public final static String C_UOM_SCRAP_X12DE355 = "KGM";
	public final static String M_PRODUCT_WITHHOLDING_VALUE = "MT_Witholding_Akonto";
	public final static String M_PRODUCT_REGULAR_PP_ORDER_VALUE = "MT_RegularPPOrder";
	public final static String C_UOM_FEE_X12DE355 = "KGM";
	public final static String M_PRODUCT_SORTING_OUT_FEE_VALUE = "P000360"; // TODO: cleanup MT_Fee_Futterkarotten
	public final static String M_PRODUCT_BASICLINE_FEE_VALUE = "MT_Fee_BasicLine";
	public final static String M_PRODUCT_PROMOTION_FEE_VALUE = "MT_Fee_Promotion";

	// Services
	private final IProductPA productPA = Services.get(IProductPA.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final IQualityBasedInvoicingBL qualityBasedInvoicingBL = Services.get(IQualityBasedInvoicingBL.class);

	//
	private final Map<Integer, BigDecimal> month2qualityAdjustment;
	private final SortedMap<BigDecimal, BigDecimal> feeProductPercentage2fee;
	private final I_M_Product scrapProduct;
	private final I_C_UOM scrapUOM;

	private Timestamp validToDate;

	private static int overallNumberOfInvoicings = 2; // default/old behavior
	private static boolean qualityAdjustmentsActive = true; // default/old behavior

	/* package */HardCodedQualityBasedConfig(final IContextAware ctxAware)
	{
		super(ctxAware);

		month2qualityAdjustment = new HashMap<Integer, BigDecimal>();
		month2qualityAdjustment.put(0, new BigDecimal("0.02")); // 0 => January
		month2qualityAdjustment.put(1, new BigDecimal("0.02"));
		month2qualityAdjustment.put(2, new BigDecimal("0.03"));
		month2qualityAdjustment.put(3, new BigDecimal("0.04"));
		month2qualityAdjustment.put(4, new BigDecimal("0.04")); // 4 => May

		// 5 => June; setting to 0.05 so we have something to invoice, in the case of "incomplete auslagerung"
		// because usually, material trackings go until may 30th
		month2qualityAdjustment.put(5, new BigDecimal("0.05"));

		month2qualityAdjustment.put(6, new BigDecimal("0.00"));
		month2qualityAdjustment.put(7, new BigDecimal("-0.03"));
		month2qualityAdjustment.put(8, new BigDecimal("-0.03"));
		month2qualityAdjustment.put(9, new BigDecimal("-0.03"));
		month2qualityAdjustment.put(10, new BigDecimal("-0.02"));
		month2qualityAdjustment.put(11, new BigDecimal("0.00")); // 11 => December

		feeProductPercentage2fee = new TreeMap<BigDecimal, BigDecimal>();
		feeProductPercentage2fee.put(new BigDecimal("0"), new BigDecimal("0.00"));
		feeProductPercentage2fee.put(new BigDecimal("15"), new BigDecimal("0.01"));
		feeProductPercentage2fee.put(new BigDecimal("20"), new BigDecimal("0.02"));
		feeProductPercentage2fee.put(new BigDecimal("25"), new BigDecimal("0.03"));
		feeProductPercentage2fee.put(new BigDecimal("30"), new BigDecimal("0.04"));
		feeProductPercentage2fee.put(new BigDecimal("35"), new BigDecimal("0.05"));
		feeProductPercentage2fee.put(new BigDecimal("40"), new BigDecimal("0.06"));

		//
		// Scrap Config
		scrapProduct = productPA.retrieveProduct(ctxAware.getCtx(),
				M_PRODUCT_SCRAP_VALUE,
				true, // throwExIfProductNotFound
				ctxAware.getTrxName());
		scrapUOM = uomDAO.retrieveByX12DE355(ctxAware.getCtx(), C_UOM_SCRAP_X12DE355);
		Check.assumeNotNull(scrapUOM, "scrapUOM not null");
	}

	@Override
	public I_M_Product getScrapProduct()
	{
		return scrapProduct;
	}

	@Override
	public I_C_UOM getScrapUOM()
	{
		return scrapUOM;
	}

	@Override
	public IInvoicingItem getWithholdingProduct()
	{
		final IContextAware ctxAware = getContext();
		final I_M_Product withholdingProduct = productPA.retrieveProduct(ctxAware.getCtx(),
				M_PRODUCT_WITHHOLDING_VALUE,
				true, // throwExIfProductNotFound
				ctxAware.getTrxName());
		final I_C_UOM withholdingUOM = uomDAO.retrieveByX12DE355(ctxAware.getCtx(), C_UOM_FEE_X12DE355);

		return qualityBasedInvoicingBL.createPlainInvoicingItem(
				withholdingProduct,
				BigDecimal.ONE,
				withholdingUOM);
	}

	@Override
	public BigDecimal getWithholdingPercent()
	{
		final BigDecimal withholdingPercent = new BigDecimal("50");
		return withholdingPercent;
	}

	@Override
	public List<IInvoicingItem> getAdditionalFeeProducts()
	{
		final IContextAware ctxAware = getContext();

		final boolean throwExIfProductNotFound = true;

		final ArrayList<IInvoicingItem> result = new ArrayList<IInvoicingItem>();

		result.add(qualityBasedInvoicingBL.createPlainInvoicingItem(
				productPA.retrieveProduct(ctxAware.getCtx(), M_PRODUCT_BASICLINE_FEE_VALUE, throwExIfProductNotFound, ctxAware.getTrxName()),
				BigDecimal.ONE,
				uomDAO.retrieveByX12DE355(ctxAware.getCtx(), C_UOM_FEE_X12DE355)));

		result.add(qualityBasedInvoicingBL.createPlainInvoicingItem(
				productPA.retrieveProduct(ctxAware.getCtx(), M_PRODUCT_PROMOTION_FEE_VALUE, throwExIfProductNotFound, ctxAware.getTrxName()),
				BigDecimal.ONE,
				uomDAO.retrieveByX12DE355(ctxAware.getCtx(), C_UOM_FEE_X12DE355)));
		return result;
	}

	@Override
	public BigDecimal getQualityAdjustmentForMonthOrNull(final int month)
	{
		if (!qualityAdjustmentsActive)
		{
			return null;
		}
		return month2qualityAdjustment.get(month);
	}

	@Override
	public final BigDecimal getQualityAdjustmentForDateOrNull(final Date date)
	{
		final int month = TimeUtil.asCalendar(date).get(Calendar.MONTH);
		return getQualityAdjustmentForMonthOrNull(month);
	}

	/**
	 * @return zero if the given percentage is below 10, <code>0.06</code> otherwise
	 */
	@Override
	public BigDecimal getScrapProcessingFeeForPercentage(final BigDecimal percentage)
	{
		if (percentage.compareTo(new BigDecimal("10.00")) < 0)
		{
			return BigDecimal.ZERO;
		}
		else
		{
			return new BigDecimal("0.06");
		}
	}

	@Override
	public BigDecimal getScrapPercentageTreshold()
	{
		return new BigDecimal("10.00");
	}

	@Override
	public boolean isFeeForProducedMaterial(final I_M_Product product)
	{
		return M_PRODUCT_SORTING_OUT_FEE_VALUE.equals(product.getValue());
	}

	@Override
	public BigDecimal getFeeForProducedMaterial(final I_M_Product m_Product, final BigDecimal percentage)
	{
		final List<BigDecimal> percentages = new ArrayList<BigDecimal>(feeProductPercentage2fee.keySet());

		// iterating from first to 2nd-last
		for (int i = 0; i < percentages.size() - 1; i++)
		{
			final BigDecimal currentPercentage = percentages.get(i);
			final BigDecimal nextPercentage = percentages.get(i + 1);

			if (currentPercentage.compareTo(percentage) <= 0
					&& nextPercentage.compareTo(percentage) > 0)
			{
				// found it: 'percentage' is in the interval that starts with 'currentPercentage'
				return feeProductPercentage2fee.get(currentPercentage);
			}
		}

		final BigDecimal lastInterval = percentages.get(percentages.size() - 1);
		return feeProductPercentage2fee.get(lastInterval);
	}

	@Override
	public I_C_Currency getCurrency()
	{
		final IContextAware ctxAware = getContext();
		return currencyDAO.retrieveCurrencyByISOCode(ctxAware.getCtx(), CURRENCY_ISO);
	}

	public static void setOverallNumberOfInvoicings(final int overallNumberOfInvoicings)
	{
		HardCodedQualityBasedConfig.overallNumberOfInvoicings = overallNumberOfInvoicings;
	}

	@Override
	public int getOverallNumberOfInvoicings()
	{
		return overallNumberOfInvoicings;
	}

	public static void setQualityAdjustmentActive(final boolean qualityAdjustmentOn)
	{
		HardCodedQualityBasedConfig.qualityAdjustmentsActive = qualityAdjustmentOn;
	}

	@Override
	public I_M_Product getRegularPPOrderProduct()
	{
		final IContextAware ctxAware = getContext();
		final I_M_Product regularPPOrderProduct = productPA.retrieveProduct(ctxAware.getCtx(),
				M_PRODUCT_REGULAR_PP_ORDER_VALUE,
				true, // throwExIfProductNotFound
				ctxAware.getTrxName());

		return regularPPOrderProduct;
	}

	/**
	 * @return the date that was set with {@link #setValidToDate(Timestamp)}, or falls back to "now plus 2 months". Never returns <code>null</code>.
	 */
	@Override
	public Timestamp getValidToDate()
	{
		if (validToDate == null)
		{
			return TimeUtil.addMonths(SystemTime.asDate(), 2);
		}
		return validToDate;
	}

	public void setValidToDate(Timestamp validToDate)
	{
		this.validToDate = validToDate;
	}
}
