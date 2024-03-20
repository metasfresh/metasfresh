package de.metas.invoicecandidate;

import static de.metas.util.Check.assumeNotNull;

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
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import de.metas.payment.paymentterm.PaymentTermId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Location;
// import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Tax;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.engine.DocStatus;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.model.I_C_BPartner;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.order.IOrderLineBL;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Tax;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static de.metas.util.Check.assumeNotNull;

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
	private OrgId orgId;
	private BPartnerId billBPartnerId;
	private PaymentTermId paymentTermId;
	private BPartnerLocationId billBPartnerLocationId;
	private int priceEntered;
	private BigDecimal priceEntered_Override;
	private UomId uomId;
	private BigDecimal qtyOrdered;
	private ProductId productId;
	private int discount;
	private boolean isManual = false;
	private Boolean isSOTrx;
	private String orderDocNo;
	private String orderLineDescription;
	private I_C_Tax tax;
	private String invoiceRule;
	private boolean invoiceRuleSet;
	private String invoiceRule_Override;
	private boolean invoiceRule_OverrideSet;
	private String poReference;
	private LocalDate dateAcct;
	private LocalDate dateInvoiced;
	private LocalDate dateToInvoice;
	private LocalDate presetDateInvoiced;
	private BigDecimal qualityDiscountPercent_Override;

	private int M_PriceList_Version_ID;
	private int M_PricingSystem_ID;
	private int asyncBatchId;

	public C_Invoice_Candidate_Builder(@NonNull final AbstractICTestSupport test)
	{
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
		
		final I_C_Invoice_Candidate ic = InterfaceWrapperHelper.create(ctx, I_C_Invoice_Candidate.class, trxName);
		POJOWrapper.setInstanceName(ic, instanceName);

		ic.setIsInEffect(true);
		ic.setAD_Org_ID(orgId.getRepoId());

		//
		// Dates
		ic.setDateAcct(TimeUtil.asTimestamp(dateAcct));
		ic.setPresetDateInvoiced(TimeUtil.asTimestamp(presetDateInvoiced));
		ic.setDateInvoiced(TimeUtil.asTimestamp(dateInvoiced));
		ic.setDateToInvoice(TimeUtil.asTimestamp(dateToInvoice));

		// InvoiceRule
		ic.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_Immediate);
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
		ic.setC_PaymentTerm_ID(PaymentTermId.toRepoId(paymentTermId));

		ic.setBill_BPartner_ID(billBPartnerId.getRepoId());

		{
			final I_C_Country country_DE = InterfaceWrapperHelper.create(ctx, I_C_Country.class, trxName);
			country_DE.setAD_Language("de");
			InterfaceWrapperHelper.save(country_DE);

			final I_C_Location loc = InterfaceWrapperHelper.create(ctx, I_C_Location.class, trxName);
			loc.setC_Country_ID(country_DE.getC_Country_ID());
			InterfaceWrapperHelper.save(loc);

			final I_C_BPartner_Location bpLoc = InterfaceWrapperHelper.create(ctx, I_C_BPartner_Location.class, trxName);
			bpLoc.setC_Location_ID(loc.getC_Location_ID());
			bpLoc.setC_BPartner_ID(billBPartnerId.getRepoId());
			if (billBPartnerLocationId != null)
			{
				Check.assumeEquals(billBPartnerLocationId.getBpartnerId(), billBPartnerId, "BP Location shall have the same BP: {}, {}", billBPartnerLocationId, billBPartnerId);
				bpLoc.setC_BPartner_Location_ID(billBPartnerLocationId.getRepoId());
			}
			InterfaceWrapperHelper.save(bpLoc);

			billBPartnerLocationId = BPartnerLocationId.ofRepoId(bpLoc.getC_BPartner_ID(), bpLoc.getC_BPartner_Location_ID());
		}

		ic.setBill_Location_ID(billBPartnerLocationId.getRepoId());

		final StockQtyAndUOMQty qtysOrdered = StockQtyAndUOMQtys.createWithUomQtyUsingConversion(
				assumeNotNull(qtyOrdered, "this builder needs qtyOrdered to be set before it is able to build an IC; this={}", this),
				productId,
				uomId);

		ic.setAD_User_InCharge_ID(-1); // nobody, aka null
		ic.setM_Product_ID(ProductId.toRepoId(productId));
		ic.setC_Currency_ID(test.currencyConversionBL.getBaseCurrency(ctx).getId().getRepoId());
		ic.setDiscount(BigDecimal.valueOf(discount));
		ic.setQtyOrdered(qtysOrdered.getStockQty().toBigDecimal());
		ic.setQtyEntered(qtysOrdered.getUOMQtyNotNull().toBigDecimal());
		ic.setQtyToInvoice(BigDecimal.ZERO); // to be computed
		ic.setQtyToInvoice_Override(null); // no override
		ic.setC_ILCandHandler(test.plainHandler);
		ic.setIsManual(isManual);
		ic.setPriceEntered(BigDecimal.valueOf(priceEntered));
		ic.setPriceEntered_Override(priceEntered_Override);
		ic.setC_UOM_ID(UomId.toRepoId(uomId));

		Check.errorIf(isSOTrx == null, "this builder needs isSOTrx to be set before it is able to build an IC; this={}", this); // avoid autoboxing-NPE
		ic.setIsSOTrx(isSOTrx);

		ic.setIsError(false); // just to avoid "refreshing changed models" exception from POJOWrapper
		ic.setProcessed(false); // just to avoid failing filters on Processed=N
		ic.setC_Tax_ID(tax == null ? -1 : tax.getC_Tax_ID());
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
				ic.setM_PricingSystem_ID(isSOTrx ? test.pricingSystem_SO.getM_PricingSystem_ID() : test.pricingSystem_PO.getM_PricingSystem_ID());
			}
			if (M_PriceList_Version_ID > 0)
			{
				ic.setM_PriceList_Version_ID(M_PriceList_Version_ID);
			}
			else
			{
				ic.setM_PriceList_Version_ID(isSOTrx ? test.priceListVersion_SO.getM_PriceList_Version_ID() : test.priceListVersion_PO.getM_PriceList_Version_ID());
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
			order.setDocStatus(DocStatus.Completed.getCode()); // fake complete
			InterfaceWrapperHelper.save(order);

			ic.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
			ic.setC_Order_ID(orderLine.getC_Order_ID());
			ic.setC_OrderSO_ID(orderLine.getC_OrderSO_ID());
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
			test.getInvoiceCandidateValidator().invalidateCandidatesAfterChange(ic);
		}

		//
		// Return created invoice candidate
		return ic;
	}

	/**
	 * Needs to be an existing partner's ID
	 */
	public C_Invoice_Candidate_Builder setBillBPartnerId(final int billBPartnerId)
	{
		return setBillBPartnerId(BPartnerId.ofRepoId(billBPartnerId));
	}

	/**
	 * Needs to be an existing partner's ID
	 */
	public C_Invoice_Candidate_Builder setBillBPartnerId(@NonNull final BPartnerId billBPartnerId)
	{
		this.billBPartnerId = billBPartnerId;
		return this;
	}

	public C_Invoice_Candidate_Builder setPaymentTermId(@NonNull final PaymentTermId paymentTermId)
	{
		this.paymentTermId = paymentTermId;
		return this;
	}

	public C_Invoice_Candidate_Builder setBillBPartner(final org.compiere.model.I_C_BPartner billBPartner)
	{
		return setBillBPartnerId(BPartnerId.ofRepoId(billBPartner.getC_BPartner_ID()));
	}

	public C_Invoice_Candidate_Builder setBillBPartnerAndLocationId(final BPartnerLocationId billBPartnerAndLocationId)
	{
		setBillBPartnerId(billBPartnerAndLocationId.getBpartnerId());
		billBPartnerLocationId = billBPartnerAndLocationId;
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

	public C_Invoice_Candidate_Builder setUomId(final UomId uomId)
	{
		this.uomId = uomId;
		return this;
	}

	/**
	 * Specify the new IC's M_PricingSystem_ID.
	 * If not set,
	 * then either {@link AbstractICTestSupport#pricingSystem_PO} or {@link AbstractICTestSupport#pricingSystem_SO} are used,
	 * depending on {@link #isSOTrx}.
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
	 */
	public C_Invoice_Candidate_Builder setM_PriceList_Version_ID(final int M_PriceList_Version_ID)
	{
		this.M_PriceList_Version_ID = M_PriceList_Version_ID;
		return this;
	}

	public C_Invoice_Candidate_Builder setQtyOrdered(final int qtyOrdered)
	{
		return setQtyOrdered(BigDecimal.valueOf(qtyOrdered));
	}

	public C_Invoice_Candidate_Builder setQtyOrdered(final BigDecimal qtyOrdered)
	{
		this.qtyOrdered = qtyOrdered;
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

	public C_Invoice_Candidate_Builder setProductId(final ProductId productId)
	{
		this.productId = productId;
		return this;
	}

	public C_Invoice_Candidate_Builder setInvoiceRule(final String invoiceRule)
	{
		this.invoiceRule = invoiceRule;
		invoiceRuleSet = true;
		return this;
	}

	public C_Invoice_Candidate_Builder setInvoiceRule_Override(final String invoiceRule_Override)
	{
		this.invoiceRule_Override = invoiceRule_Override;
		invoiceRule_OverrideSet = true;
		return this;
	}

	public C_Invoice_Candidate_Builder setPOReference(final String poReference)
	{
		this.poReference = poReference;
		return this;
	}

	public C_Invoice_Candidate_Builder setDateAcct(final LocalDate dateAcct)
	{
		this.dateAcct = dateAcct;
		return this;
	}

	public C_Invoice_Candidate_Builder setDateInvoiced(final LocalDate dateInvoiced)
	{
		this.dateInvoiced = dateInvoiced;
		return this;
	}

	public C_Invoice_Candidate_Builder setDateToInvoice(final LocalDate dateToInvoice)
	{
		this.dateToInvoice = dateToInvoice;
		return this;
	}

	public C_Invoice_Candidate_Builder setPresetDateInvoiced(final LocalDate presetDateInvoiced)
	{
		this.presetDateInvoiced = presetDateInvoiced;
		return this;
	}

	public C_Invoice_Candidate_Builder setQualityDiscountPercent_Override(final BigDecimal qualityDiscountPercent_Override)
	{
		this.qualityDiscountPercent_Override = qualityDiscountPercent_Override;
		return this;
	}

	public C_Invoice_Candidate_Builder setOrgId(@NonNull final OrgId orgId)
	{
		this.orgId = orgId;
		return this;
	}

	public C_Invoice_Candidate_Builder setAsyncBatchId(final int asyncBatchId)
	{
		this.asyncBatchId = asyncBatchId;
		return this;
	}
}
