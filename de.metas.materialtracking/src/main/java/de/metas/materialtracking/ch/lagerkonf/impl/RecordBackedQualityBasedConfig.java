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
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.materialtracking.ch.lagerkonf.IQualityInspLagerKonfDAO;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_QualityInsp_LagerKonf_AdditionalFee;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_QualityInsp_LagerKonf_Month_Adj;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_QualityInsp_LagerKonf_ProcessingFee;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_QualityInsp_LagerKonf_Version;
import de.metas.materialtracking.ch.lagerkonf.model.X_M_QualityInsp_LagerKonf_Month_Adj;
import de.metas.materialtracking.qualityBasedInvoicing.IInvoicingItem;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityBasedInvoicingBL;

/* package */class RecordBackedQualityBasedConfig extends AbstractQualityBasedConfig
{

	/**
	 * Note: the given <code>qualityInspLagerKonfVersion</code> is not referenced from this instance. Only its data is loaded into this POJO.
	 */
	// TODO: remove this code from the constructor..turn this class into a dump pojo and add a some loader-service-"stuff".
	public RecordBackedQualityBasedConfig(final I_M_QualityInsp_LagerKonf_Version qualityInspLagerKonfVersion)
	{
		super(InterfaceWrapperHelper.getContextAware(qualityInspLagerKonfVersion));
		final IContextAware ctxAware = getContext();

		currency = qualityInspLagerKonfVersion.getC_Currency();

		scrapUOM = qualityInspLagerKonfVersion.getC_UOM_Scrap();
		scrapProduct = qualityInspLagerKonfVersion.getM_Product_Scrap();
		scrapPercentageTreshold = qualityInspLagerKonfVersion.getPercentage_Scrap_Treshhold();
		scrapFee = qualityInspLagerKonfVersion.getScrap_Fee_Amt_Per_UOM();

		month2qualityAdjustment = new HashMap<Integer, BigDecimal>(12);

		final List<I_M_QualityInsp_LagerKonf_Month_Adj> adjustments = Services.get(IQualityInspLagerKonfDAO.class).retriveMonthAdjustments(qualityInspLagerKonfVersion);
		BigDecimal maximumFee = null;
		for (final I_M_QualityInsp_LagerKonf_Month_Adj adj : adjustments)
		{
			final int monthNumber;
			switch (adj.getQualityAdjustmentMonth())
			{
				case X_M_QualityInsp_LagerKonf_Month_Adj.QUALITYADJUSTMENTMONTH_Jan:
					monthNumber = 0;
					break;
				case X_M_QualityInsp_LagerKonf_Month_Adj.QUALITYADJUSTMENTMONTH_Feb:
					monthNumber = 1;
					break;
				case X_M_QualityInsp_LagerKonf_Month_Adj.QUALITYADJUSTMENTMONTH_Mar:
					monthNumber = 2;
					break;
				case X_M_QualityInsp_LagerKonf_Month_Adj.QUALITYADJUSTMENTMONTH_Apr:
					monthNumber = 3;
					break;
				case X_M_QualityInsp_LagerKonf_Month_Adj.QUALITYADJUSTMENTMONTH_May:
					monthNumber = 4;
					break;
				case X_M_QualityInsp_LagerKonf_Month_Adj.QUALITYADJUSTMENTMONTH_Jun:
					monthNumber = 5;
					break;
				case X_M_QualityInsp_LagerKonf_Month_Adj.QUALITYADJUSTMENTMONTH_Jul:
					monthNumber = 6;
					break;
				case X_M_QualityInsp_LagerKonf_Month_Adj.QUALITYADJUSTMENTMONTH_Aug:
					monthNumber = 7;
					break;
				case X_M_QualityInsp_LagerKonf_Month_Adj.QUALITYADJUSTMENTMONTH_Sep:
					monthNumber = 8;
					break;
				case X_M_QualityInsp_LagerKonf_Month_Adj.QUALITYADJUSTMENTMONTH_Oct:
					monthNumber = 9;
					break;
				case X_M_QualityInsp_LagerKonf_Month_Adj.QUALITYADJUSTMENTMONTH_Nov:
					monthNumber = 10;
					break;
				case X_M_QualityInsp_LagerKonf_Month_Adj.QUALITYADJUSTMENTMONTH_Dec:
					monthNumber = 11;
					break;
				default:
					// shouldn't happen as long as the AD and code are in sync
					Check.errorIf(true, "Unexpected M_QualityInsp_LagerKonf_Month_Adj.QualityAdjustmentMonth={}", adj.getQualityAdjustmentMonth());
					monthNumber = -1;
			}
			final BigDecimal fee = adj.getQualityAdj_Amt_Per_UOM();

			month2qualityAdjustment.put(monthNumber, fee);
			if (maximumFee == null || fee.compareTo(maximumFee) > 0)
			{
				maximumFee = fee;
			}
		}
		maximumQualityAdjustment = maximumFee;

		productWithProcessingFee = qualityInspLagerKonfVersion.getM_Product_ProcessingFee();
		regularPPOrderProduct = qualityInspLagerKonfVersion.getM_Product_RegularPPOrder();

		feeProductPercentage2fee = new TreeMap<BigDecimal, BigDecimal>();
		final List<I_M_QualityInsp_LagerKonf_ProcessingFee> processingFees = Services.get(IQualityInspLagerKonfDAO.class).retriveProcessingFees(qualityInspLagerKonfVersion);
		for (final I_M_QualityInsp_LagerKonf_ProcessingFee processingFee : processingFees)
		{
			feeProductPercentage2fee.put(processingFee.getPercentFrom(), processingFee.getProcessing_Fee_Amt_Per_UOM());
		}

		additionaFeeProducts = new ArrayList<IInvoicingItem>();
		final List<I_M_QualityInsp_LagerKonf_AdditionalFee> aditionalFees = Services.get(IQualityInspLagerKonfDAO.class).retriveAdditionalFees(qualityInspLagerKonfVersion);
		for (final I_M_QualityInsp_LagerKonf_AdditionalFee additionalFee : aditionalFees)
		{
			additionaFeeProducts.add(qualityBasedInvoicingBL.createPlainInvoicingItem(
					additionalFee.getM_Product(),
					BigDecimal.ONE,
					uomDAO.retrieveByX12DE355(ctxAware.getCtx(), C_UOM_FEE_X12DE355)));
		}

		numberOfInspections = qualityInspLagerKonfVersion.getNumberOfQualityInspections();

		witholdingProduct = qualityBasedInvoicingBL.createPlainInvoicingItem(
				qualityInspLagerKonfVersion.getM_Product_Witholding(),
				BigDecimal.ONE,
				uomDAO.retrieveByX12DE355(ctxAware.getCtx(), C_UOM_FEE_X12DE355));

		validToDate = qualityInspLagerKonfVersion.getValidTo();
	}

	// Constants
	private final static String C_UOM_FEE_X12DE355 = "PCE";

	// Services
	private final transient IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final transient IQualityBasedInvoicingBL qualityBasedInvoicingBL = Services.get(IQualityBasedInvoicingBL.class);

	private final Map<Integer, BigDecimal> month2qualityAdjustment;
	private final SortedMap<BigDecimal, BigDecimal> feeProductPercentage2fee;
	private final I_M_Product scrapProduct;
	private final I_M_Product productWithProcessingFee;

	// 08720
	private final I_M_Product regularPPOrderProduct;

	private final I_C_UOM scrapUOM;
	private final BigDecimal scrapPercentageTreshold;
	private final BigDecimal scrapFee;

	private final ArrayList<IInvoicingItem> additionaFeeProducts;

	private final IInvoicingItem witholdingProduct;

	private final int numberOfInspections;

	private final I_C_Currency currency;

	private final Timestamp validToDate;

	private final BigDecimal maximumQualityAdjustment;

	// public RecordBackedQualityBasedConfig(final IContextAware ctxAware)
	// {
	// this.ctxAware = ctxAware;

	// month2qualityAdjustment = new HashMap<Integer, BigDecimal>();
	// month2qualityAdjustment.put(0, new BigDecimal("0.02")); // 0 => January
	// month2qualityAdjustment.put(1, new BigDecimal("0.02"));
	// month2qualityAdjustment.put(2, new BigDecimal("0.03"));
	// month2qualityAdjustment.put(3, new BigDecimal("0.04"));
	// month2qualityAdjustment.put(4, new BigDecimal("0.04")); // 4 => May
	// month2qualityAdjustment.put(5, new BigDecimal("0.00"));
	// month2qualityAdjustment.put(6, new BigDecimal("0.00"));
	// month2qualityAdjustment.put(7, new BigDecimal("-0.03"));
	// month2qualityAdjustment.put(8, new BigDecimal("-0.03"));
	// month2qualityAdjustment.put(9, new BigDecimal("-0.03"));
	// month2qualityAdjustment.put(10, new BigDecimal("-0.02"));
	// month2qualityAdjustment.put(11, new BigDecimal("0.00")); // 11 => December
	//
	// feeProductPercentage2fee = new TreeMap<BigDecimal, BigDecimal>();
	// feeProductPercentage2fee.put(new BigDecimal("0"), new BigDecimal("0.00"));
	// feeProductPercentage2fee.put(new BigDecimal("15"), new BigDecimal("0.01"));
	// feeProductPercentage2fee.put(new BigDecimal("20"), new BigDecimal("0.02"));
	// feeProductPercentage2fee.put(new BigDecimal("25"), new BigDecimal("0.03"));
	// feeProductPercentage2fee.put(new BigDecimal("30"), new BigDecimal("0.04"));
	// feeProductPercentage2fee.put(new BigDecimal("35"), new BigDecimal("0.05"));
	// feeProductPercentage2fee.put(new BigDecimal("40"), new BigDecimal("0.06"));
	//
	// //
	// // Scrap Config
	// scrapProduct = productPA.retrieveProduct(ctxAware.getCtx(),
	// M_PRODUCT_SCRAP_VALUE,
	// true, // throwExIfProductNotFound
	// ctxAware.getTrxName());
	// scrapUOM = uomDAO.retrieveByX12DE355(ctxAware.getCtx(), C_UOM_SCRAP_X12DE355);
	// Check.assumeNotNull(scrapUOM, "scrapUOM not null");
	// }

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
		return witholdingProduct;
	}

	@Override
	public List<IInvoicingItem> getAdditionalFeeProducts()
	{
		return additionaFeeProducts;
	}

	@Override
	public BigDecimal getQualityAdjustmentForMonthOrNull(final int month)
	{
		return month2qualityAdjustment.get(month);
	}

	@Override
	public final BigDecimal getQualityAdjustmentForDateOrNull(final Date date)
	{
		if(date.after(getValidToDate()))
		{
			return maximumQualityAdjustment;
		}
		final int month = TimeUtil.asCalendar(date).get(Calendar.MONTH);
		return getQualityAdjustmentForMonthOrNull(month);
	}

	/**
	 * @return zero if the given percentage is below 10, <code>0.06</code> otherwise
	 */
	@Override
	public BigDecimal getScrapProcessingFeeForPercentage(final BigDecimal percentage)
	{
		if (percentage.compareTo(getScrapPercentageTreshold()) < 0)
		{
			return BigDecimal.ZERO;
		}
		else
		{
			return scrapFee;
		}
	}

	@Override
	public BigDecimal getScrapPercentageTreshold()
	{
		return scrapPercentageTreshold;
	}

	@Override
	public boolean isFeeForProducedMaterial(final I_M_Product m_Product)
	{
		return productWithProcessingFee.getM_Product_ID() == m_Product.getM_Product_ID()
				&& !feeProductPercentage2fee.isEmpty();
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
	public int getOverallNumberOfInvoicings()
	{
		return numberOfInspections;
	}

	@Override
	public BigDecimal getWithholdingPercent()
	{
		return Env.ONEHUNDRED.divide(BigDecimal.valueOf(numberOfInspections), 2, RoundingMode.HALF_UP);
	}

	@Override
	public I_C_Currency getCurrency()
	{
		return currency;
	}

	@Override
	public I_M_Product getRegularPPOrderProduct()
	{
		return regularPPOrderProduct;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public Timestamp getValidToDate()
	{
		return validToDate;
	}
}
