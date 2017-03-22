package de.metas.adempiere.ait.helper;

/*
 * #%L
 * de.metas.adempiere.ait
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


import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AcctSchema_Default;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.MBPartner;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MQuery;
import org.compiere.model.MTable;
import org.compiere.process.DocAction;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.ait.helper.AcctFactAssert.AcctDimension;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.currency.ICurrencyDAO;
import de.metas.document.IDocumentPA;
import de.metas.interfaces.I_C_BPartner;
import de.metas.logging.LogManager;

public class InvoiceHelper
{
	private final Logger logger = LogManager.getLogger(getClass());

	/**
	 * Automatically detect and set the price. Indicator to be used in {@link #addLine(String, int, int)} method.
	 */
	public static final int PriceAuto = -1;

	private final IHelper helper;

	private String currencyCode;
	private String countryCode;

	private String pricingSystemValue = IHelper.DEFAULT_PricingSystemValue;
	private String expectedCompleteStatus;
	private String docBaseType;
	private Boolean soTrx = Boolean.TRUE;
	private Boolean isTaxIncluded = Boolean.FALSE;

	private ArrayList<ProductPriceVO> lines = new ArrayList<ProductPriceVO>();

	private boolean gridTabLevel = false;

	InvoiceHelper(final IHelper helper)
	{
		this.helper = helper;
	}

	public I_C_Invoice quickCreateInvoice(String pricingSystemValue, String productValue, int priceList, int priceActual)
	{
		return this.setPricingSystemValue(pricingSystemValue)
				.addLine(productValue, priceList, priceActual)
				.setComplete(MInvoice.DOCSTATUS_Completed)
				.createInvoice();
	}

	public I_C_Invoice quickCreateInvoice(int productPriceList)
	{
		return this.addLine(IHelper.DEFAULT_ProductValue, productPriceList, -1)
				.setComplete(MInvoice.DOCSTATUS_Completed)
				.createInvoice();
	}

	public I_C_Invoice createInvoice()
	{
		final Properties ctx = helper.getCtx();
		final String trxName = helper.getTrxName();

		final I_C_BPartner bp = helper.mkBPartnerHelper().getC_BPartner(helper.getConfig());
		// BPOpenBalanceValidator.get().recordState(bp);

		final org.compiere.model.I_C_Currency currency = Services.get(ICurrencyDAO.class).retrieveCurrencyByISOCode(ctx, getCurrencyCode());

		final GridWindowHelper gridWindowHelper;
		final MInvoice invoicePO;
		final I_C_Invoice invoice;
		if (gridTabLevel)
		{
			gridWindowHelper = helper.mkGridWindowHelper();
			invoice = gridWindowHelper
					// Open Invoice window. For optimizations we try to open it empty
					.openTabForTable(I_C_Invoice.Table_Name, soTrx, MQuery.getNoRecordQuery(I_C_Invoice.Table_Name, false))
					.newRecord()
					.getGridTabInterface(I_C_Invoice.class);
			invoicePO = null;
		}
		else
		{
			gridWindowHelper = null;
			invoicePO = new MInvoice(ctx, 0, trxName);
			invoice = InterfaceWrapperHelper.create(invoicePO, I_C_Invoice.class);
		}

		invoice.setDateInvoiced(helper.getNow());
		invoice.setDateAcct(helper.getNow());
		invoice.setIsSOTrx(soTrx);
		invoice.setIsTaxIncluded(isTaxIncluded);

		if (docBaseType != null)
		{
			invoice.setC_DocTypeTarget_ID(
					Services.get(IDocumentPA.class).retriveDocTypeId(ctx, invoice.getAD_Org_ID(), docBaseType));
		}

		if (gridTabLevel)
		{
			invoice.setC_BPartner_ID(bp.getC_BPartner_ID());
			// C_BPartner_Location_ID, AD_User_ID, C_PaymentTerm_ID etc will be filled automatically by callouts
		}
		else
		{
			invoicePO.setBPartner((MBPartner)InterfaceWrapperHelper.getPO(bp));
		}

		//
		// Make sure we a created the PriceSystem/PriceList/Version
		final I_M_PriceList priceList = helper.getM_PriceList(pricingSystemValue, getCurrencyCode(), getCountryCode(), invoice.isSOTrx());
		invoice.setM_PriceList_ID(priceList.getM_PriceList_ID());

		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setDescription("Generated by " + this.toString() + " on " + (new Date()));

		// Mare sure other fields are set
		// not yet ported from 
		// if (invoice.getC_Payment_ID() <= 0)
		// Services.get(IInvoiceBL.class).setDefaultPaymentTerm(invoice);

		//
		// Save invoice
		if (gridTabLevel)
		{
			gridWindowHelper
					.validateLookups()
					.save();
		}
		else
		{
			InterfaceWrapperHelper.save(invoice);
		}
		assertTrue("C_Invoice_ID shall be set", invoice.getC_Invoice_ID() > 0);

		for (ProductPriceVO line : lines)
		{
			if (line.productValue != null)
			{
				helper.setProductPrice(priceList, line.productValue, line.priceList);
			}

			final I_C_InvoiceLine invoiceLine;
			if (gridTabLevel)
			{
				invoiceLine = gridWindowHelper
						.selectTab(I_C_InvoiceLine.Table_Name)
						.newRecord()
						.getGridTabInterface(I_C_InvoiceLine.class);
			}
			else
			{
				invoiceLine = InterfaceWrapperHelper.create(new MInvoiceLine(invoicePO), I_C_InvoiceLine.class);
			}

			if (line.productValue != null)
			{
				invoiceLine.setM_Product_ID(helper.getM_Product(line.productValue).getM_Product_ID());
			}
			boolean priceListWasSet = false;
			if (line.chargeValue != null)
			{
				invoiceLine.setC_Charge_ID(helper.getC_Charge(line.chargeValue, null).getC_Charge_ID());
				invoiceLine.setPriceEntered(line.priceActual);
				invoiceLine.setPriceList(line.priceList);
				priceListWasSet = true;
			}
			if (line.description != null)
			{
				invoiceLine.setDescription(line.description);
				invoiceLine.setPriceEntered(line.priceActual);
				invoiceLine.setPriceList(line.priceList);
				priceListWasSet = true;

				// make sure that we created also price list version
				helper.getM_PriceList_Version(pricingSystemValue, getCurrencyCode(), getCountryCode());
			}

			invoiceLine.setQtyEntered(line.qty);
			invoiceLine.setQtyInvoiced(invoiceLine.getQtyEntered());

			//
			// Save invoice line
			if (gridTabLevel)
			{
				gridWindowHelper
						.validateLookups()
						.save();
			}
			else
			{
				InterfaceWrapperHelper.save(invoiceLine);
			}
			assertTrue("C_InvoiceLine_ID shall be set", invoiceLine.getC_InvoiceLine_ID() > 0);

			//
			// Validate prices
			if (priceListWasSet)
			{
				assertThat("Price list not correct", invoiceLine.getPriceList(), comparesEqualTo(line.priceList));
			}
			if (line.priceActual != null && line.priceActual.compareTo(invoiceLine.getPriceActual()) != 0)
			{
				invoiceLine.setPriceActual(line.priceActual);
				if (line.priceUOM != null)
				{
					invoiceLine.setPrice_UOM_ID(line.priceUOM.getC_UOM_ID()); // 07090: when setting a priceActual, we also need to specify a PriceUOM
				}
// ts: commented out ad-hoc, to see if we relly need to explcitly set it. will try it out next time ;-)				
//				else
//				{
//					invoiceLine.setPrice_UOM(helper.getM_Product(line.productValue).getC_UOM());
//				}
				InterfaceWrapperHelper.save(invoiceLine);
				assertThat("Price actual not correct", invoiceLine.getPriceActual(), comparesEqualTo(line.priceActual));
			}
		}

		if (expectedCompleteStatus != null)
		{
			helper.process(invoice, DocAction.ACTION_Complete, expectedCompleteStatus);
			// BPOpenBalanceValidator.get().afterInvoiceComplete(invoicePO);

			doAccountingAssertions(invoice);
		}
		logger.info("Invoice: " + invoice + ", NetAmt=" + invoice.getTotalLines());

		return invoice;
	}

	private void doAccountingAssertions(I_C_Invoice invoicePO)
	{
		if (invoicePO.isSOTrx())
		{
			helper.doAccountingAssertions()
					.assertAcctAmounts(invoicePO.getGrandTotal(), Env.ZERO, new AcctDimension()
							.setAD_Table_ID(MTable.getTable_ID(I_C_Invoice.Table_Name))
							.setRecord_ID(invoicePO.getC_Invoice_ID())
							.setElementValue(I_C_AcctSchema_Default.COLUMNNAME_C_Receivable_Acct)
					);
		}
	}

	public InvoiceHelper addLine(
			final String productValue,
			final int priceList,
			final int priceActual)
	{
		BigDecimal priceListBD = new BigDecimal(priceList);
		BigDecimal priceActualBD = priceActual < 0 ? null : new BigDecimal(priceActual);
		addLine(productValue, priceListBD, priceActualBD);
		return this;
	}

	public InvoiceHelper addLine(
			final String productValue,
			final BigDecimal priceList,
			final BigDecimal priceActual)
	{
		ProductPriceVO line = new ProductPriceVO();
		line.lineType = ProductPriceVO.LineType.Product;
		line.productValue = productValue;
		line.priceList = priceList;
		line.priceActual = priceActual;
		lines.add(line);
		return this;
	}

	public InvoiceHelper addChargeLine(
			final String chargeValue,
			final int priceList,
			final int priceActual)
	{
		BigDecimal priceListBD = new BigDecimal(priceList);
		BigDecimal priceActualBD = priceActual < 0 ? null : new BigDecimal(priceActual);
		ProductPriceVO line = new ProductPriceVO();
		line.lineType = ProductPriceVO.LineType.Charge;
		line.chargeValue = chargeValue;
		line.priceList = priceListBD;
		line.priceActual = priceActualBD;
		lines.add(line);
		return this;
	}

	public InvoiceHelper addDescriptionLine(
			final String description,
			final int priceList,
			final int priceActual)
	{
		BigDecimal priceListBD = new BigDecimal(priceList);
		BigDecimal priceActualBD = priceActual < 0 ? null : new BigDecimal(priceActual);
		ProductPriceVO line = new ProductPriceVO();
		line.lineType = ProductPriceVO.LineType.Description;
		line.description = description;
		line.priceList = priceListBD;
		line.priceActual = priceActualBD;
		lines.add(line);
		return this;
	}

	/**
	 * Tells this helper to complete the order after creation.
	 * 
	 * @param expectedCompleteStatus the expected docstatus after the completion ('WP' or 'CO'). If <code>null</code>, the order is not completed.
	 * @return
	 */
	public InvoiceHelper setComplete(final String expectedCompleteStatus)
	{
		this.expectedCompleteStatus = expectedCompleteStatus;
		return this;
	}

	public InvoiceHelper setPricingSystemValue(final String pricingSystemValue)
	{
		this.pricingSystemValue = pricingSystemValue;
		return this;
	}

	public InvoiceHelper setCurrencyCode(final String currencyCode)
	{
		this.currencyCode = currencyCode;
		return this;
	}

	private String getCurrencyCode()
	{
		if (currencyCode == null)
		{
			return helper.getCurrencyCode();
		}
		return currencyCode;
	}

	public InvoiceHelper setCountryCode(final String countryCode)
	{
		this.countryCode = countryCode;
		return this;
	}

	private String getCountryCode()
	{
		if (countryCode == null)
		{
			return helper.getCountryCode();
		}
		return countryCode;
	}

	public InvoiceHelper setDocBaseType(final String docType)
	{
		this.docBaseType = docType;
		return this;
	}

	public InvoiceHelper setSOTrx(final Boolean soTrx)
	{
		this.soTrx = soTrx;
		return this;
	}

	public InvoiceHelper setIsTaxIncluded(final boolean isTaxIncluded)
	{
		this.isTaxIncluded = isTaxIncluded;
		return this;
	}

	/**
	 * Enable GridTab level mode. If enabled, document will be created using only GridTab
	 * 
	 * @param gridTabLevel
	 * @return
	 */
	public InvoiceHelper setGridTabLevel(boolean gridTabLevel)
	{
		this.gridTabLevel = gridTabLevel;
		return this;
	}

	@Override
	public String toString()
	{
		return getClass().getCanonicalName() + "["
				+ "gridTabLevel=" + gridTabLevel
				+ "]";
	}
}
