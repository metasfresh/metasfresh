package de.metas.invoicecandidate;

/*
 * #%L
 * de.metas.swat.base
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
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Tax;
import org.compiere.model.X_C_Order;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.service.IOrderLineBL;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.model.I_C_BPartner;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;

/**
 * {@link I_C_Invoice_Candidate} builder to be used ONLY for testing.
 *
 * @author tsa
 *
 */
public class C_Invoice_Candidate_Builder
{
	private final AbstractICTestSupport test;

	private String instanceName;
	private int billBPartnerId;
	private int priceEntered;
	private BigDecimal priceEntered_Override;
	private BigDecimal qty;
	private int discount;
	private boolean isManual = false;
	private Boolean isSOTrx;
	private Boolean allowConsolidateInvoice;
	private String orderDocNo;
	private String orderLineDescription;
	private I_C_Tax tax;
	private String invoiceRule;
	private boolean invoiceRuleSet;
	private String invoiceRule_Override;
	private boolean invoiceRule_OverrideSet;
	private String poReference;
	private Timestamp dateAcct;
	private BigDecimal qualityDiscountPercent_Override;

	private int M_PriceList_Version_ID;
	private int M_PricingSystem_ID;

	public C_Invoice_Candidate_Builder(final AbstractICTestSupport test)
	{
		super();
		this.test = test;
	}

	public List<I_C_Invoice_Candidate> buildAsSigletonList()
	{
		final I_C_Invoice_Candidate ic = build();
		return Collections.singletonList(ic);
	}

	public I_C_Invoice_Candidate build()
	{
		final Properties ctx = Env.getCtx();
		final String trxName = ITrx.TRXNAME_ThreadInherited; // Services.get(ITrxManager.class).createTrxName("createInvoiceCandidate");

		//
		// Configure the BPartner
		final I_C_BPartner billPartner;
		{
			billPartner = InterfaceWrapperHelper.create(ctx, I_C_BPartner.class, trxName);
			billPartner.setC_BPartner_ID(billBPartnerId);

			// FIXME: this is actually a fucked up, not intuitive workaround
			// billPartner.setAllowConsolidateInvoice(false);
			if (!allowConsolidateInvoice)
			{
				billPartner.setPO_Invoice_Aggregation(test.defaultHeaderAggregation_NotConsolidated);
				billPartner.setSO_Invoice_Aggregation(test.defaultHeaderAggregation_NotConsolidated);
			}
			InterfaceWrapperHelper.save(billPartner);
		}

		final I_C_Invoice_Candidate ic = InterfaceWrapperHelper.create(ctx, I_C_Invoice_Candidate.class, trxName);
		POJOWrapper.setInstanceName(ic, instanceName);
		ic.setAD_Org_ID(1);

		//
		// Dates
		ic.setDateAcct(dateAcct);

		// InvoiceRule
		ic.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_Sofort);
		ic.setInvoiceRule_Override(ic.getInvoiceRule());
		// ic.setInvoiceRule_Effective(ic.getInvoiceRule()); // virtual column, not used
		if (invoiceRuleSet)
		{
			ic.setInvoiceRule(invoiceRule);
		}
		if (invoiceRule_OverrideSet)
		{
			ic.setInvoiceRule_Override(invoiceRule_Override);
		}

		ic.setBill_BPartner(billPartner);
		ic.setAD_User_InCharge_ID(-1); // nobody, aka null
		ic.setM_Product(test.product("1", -1));
		ic.setC_Currency(test.currencyConversionBL.getBaseCurrency(ctx));
		ic.setDiscount(BigDecimal.valueOf(discount));
		ic.setQtyOrdered(qty);
		ic.setQtyToInvoice(Env.ZERO); // to be computed
		ic.setQtyToInvoice_Override(null); // no override
		ic.setC_ILCandHandler(test.plainHandler);
		ic.setIsManual(isManual);
		ic.setPriceEntered(BigDecimal.valueOf(priceEntered));
		ic.setPriceEntered_Override(priceEntered_Override);
		// ic.setAllowConsolidateInvoice(allowConsolidateInvoice);

		Check.errorIf(isSOTrx == null, "this builder={} needs isSOTrx to be set before it is able to build an IC", this); // avoid autoboxing-NPE
		ic.setIsSOTrx(isSOTrx);

		ic.setBill_Location(test.bpLoc);
		ic.setIsError(false); // just to avoid "refreshing changed models" exception from POJOWrapper
		ic.setProcessed(false); // just to avoid failing filters on Processed=N
		ic.setC_Tax(tax);
		ic.setIsTaxIncluded(false); // to avoid key builder to get "null" here
		// 07442: activity and tax

		ic.setPOReference(poReference);

		ic.setDateOrdered(TimeUtil.addDays(test.plvDate, 1)); // make sure that the system can find out PLVs

		{
			// set M_PricingSystem and M_PriceList_Version info
			if (M_PricingSystem_ID > 0)
			{
				ic.setM_PricingSystem_ID(M_PricingSystem_ID);
			}
			else
			{
				ic.setM_PricingSystem(isSOTrx ? test.pricingSystem_SO : test.pricingSystem_PO);
			}
			if (M_PriceList_Version_ID > 0)
			{
				ic.setM_PriceList_Version_ID(M_PriceList_Version_ID);
			}
			else
			{
				ic.setM_PriceList_Version(isSOTrx ? test.priceListVersion_SO : test.priceListVersion_PO);
			}
		}

		if (!isManual)
		{
			// create first order
			final I_C_BPartner bPartner = InterfaceWrapperHelper.create(test.bpartner("1"), I_C_BPartner.class);

			final I_C_Order order = test.order(orderDocNo);
			order.setC_BPartner_ID(bPartner.getC_BPartner_ID());
			InterfaceWrapperHelper.save(order);

			final I_C_OrderLine orderLine = test.orderLine(orderLineDescription, I_C_OrderLine.class);
			orderLine.setPriceEntered(BigDecimal.valueOf(priceEntered));
			orderLine.setDiscount(BigDecimal.valueOf(discount));
			final BigDecimal priceActual = Services.get(IOrderLineBL.class).subtractDiscount(BigDecimal.valueOf(priceEntered), BigDecimal.valueOf(discount), 2);

			orderLine.setPriceActual(priceActual);
			orderLine.setC_BPartner_ID(bPartner.getC_BPartner_ID());
			orderLine.setC_Order_ID(order.getC_Order_ID());
			orderLine.setProcessed(true); // because the order is also processed and in real life order.processed implies orderLine.processed
			InterfaceWrapperHelper.save(orderLine);

			order.setProcessed(true);
			order.setDocStatus(X_C_Order.DOCSTATUS_Completed); // fake complete
			InterfaceWrapperHelper.save(order);

			ic.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
			ic.setC_Order_ID(orderLine.getC_Order_ID());
			ic.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(org.compiere.model.I_C_OrderLine.Table_Name));
			ic.setRecord_ID(orderLine.getC_OrderLine_ID());
			ic.setPriceActual(priceActual);
			ic.setPrice_UOM_ID(orderLine.getPrice_UOM_ID()); // 07090 when we set PiceActual, we shall also set PriceUOM.
		}
		else
		{
			ic.setPriceActual(BigDecimal.valueOf(priceEntered));
		}

		//
		// Header Aggregation
		final String headerAggregationKey = test.headerAggKeyBuilder.buildKey(ic);
		ic.setHeaderAggregationKey_Calc(headerAggregationKey);
		ic.setHeaderAggregationKey(headerAggregationKey);

		//
		// Quality
		ic.setQualityDiscountPercent_Override(qualityDiscountPercent_Override);

		InterfaceWrapperHelper.save(ic);

		//
		// Call model validator because if is not called directly
		if (!test.isModelInterceptorsRegistered())
		{
			test.getInvoiceCandidateValidator().invalidateCandidates(ic);
		}

		//
		// Make sure values were correctly set:
		// Assert.assertEquals("Invalid IC's AllowConsolidateInvoice", allowConsolidateInvoice, ic.isAllowConsolidateInvoice());

		//
		// Return created invoice candidate
		return ic;
	}

	public C_Invoice_Candidate_Builder setBillBPartnerId(final int billBPartnerId)
	{
		this.billBPartnerId = billBPartnerId;
		return this;
	}

	public C_Invoice_Candidate_Builder setBillBPartner(final org.compiere.model.I_C_BPartner billBPartner)
	{
		this.billBPartnerId = billBPartner.getC_BPartner_ID();
		return this;
	}

	public C_Invoice_Candidate_Builder setPriceEntered(final int priceEntered)
	{
		this.priceEntered = priceEntered;
		return this;
	}

	public C_Invoice_Candidate_Builder setPriceEntered_Override(final Integer priceEntered_Override)
	{
		return setPriceEntered_Override(priceEntered_Override == null ? null : BigDecimal.valueOf(priceEntered_Override));
	}

	public C_Invoice_Candidate_Builder setPriceEntered_Override(final BigDecimal priceEntered_Override)
	{
		this.priceEntered_Override = priceEntered_Override;
		return this;
	}

	/**
	 * Specify the new IC's M_PricingSystem_ID.
	 * If not set,
	 * then either {@link AbstractICTestSupport#pricingSystem_PO} or {@link AbstractICTestSupport#pricingSystem_SO} are used,
	 * depending on {@link #isSOTrx}.
	 *
	 * @param M_PricingSystem_ID
	 * @return
	 */
	public C_Invoice_Candidate_Builder setM_PricingSystem_ID(final int M_PricingSystem_ID)
	{
		this.M_PricingSystem_ID = M_PricingSystem_ID;
		return this;
	}

	/**
	 * Specify the new IC's M_PriceList_Version_ID.
	 * If not set, then either {@link AbstractICTestSupport#priceListVersion_PO} or {@link AbstractICTestSupport#priceListVersion_SO} are used,
	 * depending on {@link #isSOTrx}.
	 * <p>
	 * Hint: when setting a customer PLV, make sure to use a date not after {@link AbstractICTestSupport#plvDate}, because currently, in {@link #build()}, we orientate the new IC's <code>DateOrdered</code> on the PLV-date.
	 *
	 * @param M_PriceList_Version_ID
	 * @return
	 */
	public C_Invoice_Candidate_Builder setM_PriceList_Version_ID(final int M_PriceList_Version_ID)
	{
		this.M_PriceList_Version_ID = M_PriceList_Version_ID;
		return this;
	}

	public C_Invoice_Candidate_Builder setQty(final int qty)
	{
		return setQty(BigDecimal.valueOf(qty));
	}

	public C_Invoice_Candidate_Builder setQty(final BigDecimal qty)
	{
		this.qty = qty;
		return this;
	}

	public C_Invoice_Candidate_Builder setDiscount(final int discount)
	{
		this.discount = discount;
		return this;
	}

	public C_Invoice_Candidate_Builder setManual(final boolean isManual)
	{
		this.isManual = isManual;
		return this;
	}

	public C_Invoice_Candidate_Builder setSOTrx(final boolean isSOTrx)
	{
		this.isSOTrx = isSOTrx;
		return this;
	}

	/**
	 * Configures the IC's BPartner to use/not use an consolitated header aggregation.
	 *
	 * @param allowConsolidateInvoice
	 */
	public C_Invoice_Candidate_Builder setAllowConsolidateInvoiceOnBPartner(final boolean allowConsolidateInvoice)
	{
		this.allowConsolidateInvoice = allowConsolidateInvoice;
		return this;
	}

	public C_Invoice_Candidate_Builder setOrderDocNo(final String orderDocNo)
	{
		this.orderDocNo = orderDocNo;
		return this;
	}

	public C_Invoice_Candidate_Builder setOrderLineDescription(final String orderLineDescription)
	{
		this.orderLineDescription = orderLineDescription;
		return this;
	}

	/**
	 * @param instanceName
	 * @return
	 * @see POJOWrapper#setInstanceName(Object, String)
	 */
	public C_Invoice_Candidate_Builder setInstanceName(final String instanceName)
	{
		this.instanceName = instanceName;
		return this;
	}

	public C_Invoice_Candidate_Builder setC_Tax(final I_C_Tax tax)
	{
		this.tax = tax;
		return this;
	}

	public C_Invoice_Candidate_Builder setInvoiceRule(String invoiceRule)
	{
		this.invoiceRule = invoiceRule;
		this.invoiceRuleSet = true;
		return this;
	}

	public C_Invoice_Candidate_Builder setInvoiceRule_Override(String invoiceRule_Override)
	{
		this.invoiceRule_Override = invoiceRule_Override;
		this.invoiceRule_OverrideSet = true;
		return this;
	}

	public C_Invoice_Candidate_Builder setPOReference(String poReference)
	{
		this.poReference = poReference;
		return this;
	}

	public C_Invoice_Candidate_Builder setDateAcct(Timestamp dateAcct)
	{
		this.dateAcct = dateAcct;
		return this;
	}

	public C_Invoice_Candidate_Builder setQualityDiscountPercent_Override(BigDecimal qualityDiscountPercent_Override)
	{
		this.qualityDiscountPercent_Override = qualityDiscountPercent_Override;
		return this;
	}
}
