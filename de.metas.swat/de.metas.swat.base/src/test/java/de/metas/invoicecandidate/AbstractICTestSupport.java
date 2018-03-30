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

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_InvoiceCandidate_InOutLine;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.model.MPriceList;
import org.compiere.model.MPricingSystem;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_C_Order;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;
import org.compiere.util.TrxRunnableAdapter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

import de.metas.aggregation.api.IAggregationFactory;
import de.metas.aggregation.api.IAggregationKeyBuilder;
import de.metas.aggregation.model.C_Aggregation_Attribute_Builder;
import de.metas.aggregation.model.C_Aggregation_Builder;
import de.metas.aggregation.model.I_C_Aggregation;
import de.metas.aggregation.model.X_C_Aggregation;
import de.metas.aggregation.model.X_C_AggregationItem;
import de.metas.aggregation.model.X_C_Aggregation_Attribute;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.impl.PlainCurrencyBL;
import de.metas.document.engine.IDocument;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.agg.key.impl.ICHeaderAggregationKeyBuilder_OLD;
import de.metas.invoicecandidate.agg.key.impl.ICLineAggregationKeyBuilder_OLD;
import de.metas.invoicecandidate.api.IAggregationDAO;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.impl.AggregationKeyEvaluationContext;
import de.metas.invoicecandidate.api.impl.HeaderAggregationKeyBuilder;
import de.metas.invoicecandidate.api.impl.PlainAggregationDAO;
import de.metas.invoicecandidate.api.impl.PlainInvoiceCandDAO;
import de.metas.invoicecandidate.api.impl.PlainInvoicingParams;
import de.metas.invoicecandidate.expectations.InvoiceCandidateExpectation;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Recompute;
import de.metas.invoicecandidate.modelvalidator.C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IAggregator;
import de.metas.invoicecandidate.spi.impl.PlainInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.impl.aggregator.standard.DefaultAggregator;
import de.metas.testsupport.AbstractTestSupport;

public abstract class AbstractICTestSupport extends AbstractTestSupport
{
	// services
	protected PlainCurrencyBL currencyConversionBL;
	protected IInvoiceCandBL invoiceCandBL;

	protected I_C_ILCandHandler plainHandler;

	protected I_C_Aggregation defaultHeaderAggregation;
	protected I_C_Aggregation defaultHeaderAggregation_NotConsolidated;

	/**
	 * Default Invoice Candidate Line Aggregator Definition
	 */
	private I_C_Invoice_Candidate_Agg defaultLineAgg;
	protected IAggregationKeyBuilder<I_C_Invoice_Candidate> headerAggKeyBuilder;

	protected I_C_BPartner_Location bpLoc;

	//
	// Taxes
	protected I_C_Tax tax_Default;
	protected I_C_Tax tax_NotFound;

	/**
	 * Currently used for both {@link #priceListVersion_SO} and {@link #priceListVersion_PO}.
	 */
	public final Timestamp plvDate = TimeUtil.getDay(2015, 01, 15);

	protected I_M_PricingSystem pricingSystem_SO;
	protected I_M_PriceList_Version priceListVersion_SO;

	protected I_M_PricingSystem pricingSystem_PO;
	protected I_M_PriceList_Version priceListVersion_PO;

	// task 07442
	protected I_AD_Org org;
	protected I_M_Product product;
	protected I_C_Activity activity;

	private I_C_Country country_DE;

	/**
	 * Don't access directly, use {@link #getInvoiceCandidateValidator()}
	 */
	private C_Invoice_Candidate invoiceCandidateValidator = null;
	private boolean modelInterceptorsRegistered = false;

	@BeforeClass
	public static final void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
	}

	@Before
	public final void initStuff()
	{
		AdempiereTestHelper.get().init();

		modelInterceptorsRegistered = false;
		// FIXME: make sure all tests based on this abstract test support model interceptors
		// registerModelInterceptors();

		final Properties ctx = Env.getCtx();
		Env.setContext(ctx, Env.CTXNAME_Date, SystemTime.asDayTimestamp());

		final String trxName = Trx.createTrxName();

		currencyConversionBL = (PlainCurrencyBL)Services.get(ICurrencyBL.class);

		plainHandler = InterfaceWrapperHelper.create(ctx, I_C_ILCandHandler.class, trxName);
		plainHandler.setClassname(PlainInvoiceCandidateHandler.class.getName());
		InterfaceWrapperHelper.save(plainHandler);

		//
		// Setup Header & Line aggregation
		{
			// Header
			config_InvoiceCand_HeaderAggregation();
			Services.get(IAggregationFactory.class).setDefaultAggregationKeyBuilder(
					I_C_Invoice_Candidate.class,
					X_C_Aggregation.AGGREGATIONUSAGELEVEL_Header,
					ICHeaderAggregationKeyBuilder_OLD.instance);
			headerAggKeyBuilder = new HeaderAggregationKeyBuilder();

			// Line
			config_InvoiceCand_LineAggregation(ctx, trxName);
			Services.get(IAggregationFactory.class).setDefaultAggregationKeyBuilder(
					I_C_Invoice_Candidate.class,
					X_C_Aggregation.AGGREGATIONUSAGELEVEL_Line,
					ICLineAggregationKeyBuilder_OLD.instance);
		}

		// Setup taxes
		config_Taxes();

		this.country_DE = InterfaceWrapperHelper.create(ctx, I_C_Country.class, trxName);
		country_DE.setAD_Language("de");
		InterfaceWrapperHelper.save(country_DE);

		config_StandardDocTypes();
		config_Pricing();

		final I_C_Location loc = InterfaceWrapperHelper.create(ctx, I_C_Location.class, trxName);
		loc.setC_Country_ID(country_DE.getC_Country_ID());
		InterfaceWrapperHelper.save(loc);
		bpLoc = InterfaceWrapperHelper.create(ctx, I_C_BPartner_Location.class, trxName);
		bpLoc.setC_Location_ID(loc.getC_Location_ID());
		InterfaceWrapperHelper.save(bpLoc);

		org = InterfaceWrapperHelper.create(ctx, I_AD_Org.class, trxName); // 07442
		product = InterfaceWrapperHelper.create(ctx, I_M_Product.class, trxName);
		activity = InterfaceWrapperHelper.create(ctx, I_C_Activity.class, trxName);

		//
		// Services
		invoiceCandBL = Services.get(IInvoiceCandBL.class);
	}

	protected void config_InvoiceCand_HeaderAggregation()
	{
		//@formatter:off
		defaultHeaderAggregation = new C_Aggregation_Builder()
			.setAD_Table_ID(I_C_Invoice_Candidate.Table_Name)
			.setIsDefault(true)
			.setAggregationUsageLevel(X_C_Aggregation.AGGREGATIONUSAGELEVEL_Header)
			.setName("Default")
			.newItem()
				.setType(X_C_AggregationItem.TYPE_Column)
				.setAD_Column(I_C_Invoice_Candidate.COLUMN_Bill_BPartner_ID)
				.end()
			.newItem()
				.setType(X_C_AggregationItem.TYPE_Column)
				.setAD_Column(I_C_Invoice_Candidate.COLUMN_Bill_Location_ID)
				.end()
			.newItem()
				.setType(X_C_AggregationItem.TYPE_Column)
				.setAD_Column(I_C_Invoice_Candidate.COLUMN_C_Currency_ID)
				.end()
			.newItem()
				.setType(X_C_AggregationItem.TYPE_Column)
				.setAD_Column(I_C_Invoice_Candidate.COLUMN_AD_Org_ID)
				.end()
			.newItem()
				.setType(X_C_AggregationItem.TYPE_Column)
				.setAD_Column(I_C_Invoice_Candidate.COLUMN_IsSOTrx)
				.end()
			.newItem()
				.setType(X_C_AggregationItem.TYPE_Column)
				.setAD_Column(I_C_Invoice_Candidate.COLUMN_IsTaxIncluded)
				.end()
			.build();
		//@formatter:on

		new C_Aggregation_Attribute_Builder()
				.setType(X_C_Aggregation_Attribute.TYPE_Attribute)
				.setCode(AggregationKeyEvaluationContext.ATTRIBUTE_CODE_AggregatePer_M_InOut_ID)
				.build();

		//
		// FIXME: the aggregation is wrong:
		// "just talked with mark: @teo: those unit tests were wrong..we should remove the InOut_ID from our aggregator and fix them
		// ..can you malbe add a fixme for now?"
		// [Tobi42 aka the 5th rider]
		//

		//@formatter:off
		defaultHeaderAggregation_NotConsolidated = new C_Aggregation_Builder()
			.setAD_Table_ID(I_C_Invoice_Candidate.Table_Name)
			.setIsDefault(false)
			.setAggregationUsageLevel(X_C_Aggregation.AGGREGATIONUSAGELEVEL_Header)
			.setName("Default_NotConsolidated")
			.newItem()
				.setType(X_C_AggregationItem.TYPE_IncludedAggregation)
				.setIncluded_Aggregation(defaultHeaderAggregation)
				.end()
			.newItem()
				.setType(X_C_AggregationItem.TYPE_Column)
				.setAD_Column(I_C_Invoice_Candidate.COLUMN_C_Order_ID)
				.end()
//			.newItem()
//				.setType(X_C_AggregationItem.TYPE_Attribute)
//				.setC_Aggregation_Attribute(attr_AggregatePer_M_InOut_ID)
//				//.setAD_Column(I_C_Invoice_Candidate.COLUMN_First_Ship_BPLocation_ID)
//				.end()
			.build();
		//@formatter:on
	}

	/**
	 * Configures {@link DefaultAggregator} to be the aggregator that is returned by invocations of {@link IAggregationDAO#retrieveAggregate(I_C_Invoice_Candidate)} throughout tests. <br>
	 * Override this method to test different {@link IAggregator}s.
	 *
	 * @param ctx
	 * @param trxName
	 */
	protected void config_InvoiceCand_LineAggregation(final Properties ctx, final String trxName)
	{
		//
		// Create Default Invoice Candidate Aggregator Definition
		defaultLineAgg = InterfaceWrapperHelper.create(ctx, I_C_Invoice_Candidate_Agg.class, trxName);
		defaultLineAgg.setAD_Org_ID(0);
		defaultLineAgg.setSeqNo(0);
		defaultLineAgg.setName("Default");
		defaultLineAgg.setClassname(DefaultAggregator.class.getName());
		defaultLineAgg.setIsActive(true);
		defaultLineAgg.setC_BPartner(null);
		defaultLineAgg.setM_ProductGroup(null);
		InterfaceWrapperHelper.save(defaultLineAgg);

		final PlainAggregationDAO aggregationDAO = (PlainAggregationDAO)Services.get(IAggregationDAO.class);
		aggregationDAO.setDefaultAgg(defaultLineAgg);
	}

	protected void config_Taxes()
	{
		final Properties ctx = Env.getCtx();
		final PlainContextAware context = new PlainContextAware(ctx);

		final I_C_TaxCategory taxCategory_None = InterfaceWrapperHelper.newInstance(I_C_TaxCategory.class, context);
		taxCategory_None.setC_TaxCategory_ID(100);
		taxCategory_None.setName("None");
		InterfaceWrapperHelper.save(taxCategory_None);

		this.tax_NotFound = InterfaceWrapperHelper.create(ctx, I_C_Tax.class, ITrx.TRXNAME_None);
		tax_NotFound.setC_Tax_ID(100);
		tax_NotFound.setC_TaxCategory(taxCategory_None);
		InterfaceWrapperHelper.save(tax_NotFound);

		final I_C_TaxCategory taxCategory_Default = InterfaceWrapperHelper.newInstance(I_C_TaxCategory.class, context);
		taxCategory_Default.setC_TaxCategory_ID(1000000);
		taxCategory_Default.setName("Default");
		InterfaceWrapperHelper.save(taxCategory_Default);

		this.tax_Default = InterfaceWrapperHelper.create(ctx, I_C_Tax.class, ITrx.TRXNAME_None);
		tax_Default.setC_Tax_ID(1000000);
		tax_Default.setC_TaxCategory(taxCategory_Default);
		InterfaceWrapperHelper.save(tax_Default);
	}

	protected void config_StandardDocTypes()
	{
		final Properties ctx = Env.getCtx();
		final PlainContextAware context = new PlainContextAware(ctx);

		final I_C_DocType docType_ARI = InterfaceWrapperHelper.newInstance(I_C_DocType.class, context);
		docType_ARI.setName("ARI");
		docType_ARI.setDocBaseType(X_C_DocType.DOCBASETYPE_ARInvoice);
		docType_ARI.setIsSOTrx(true);
		docType_ARI.setIsDefault(true);
		InterfaceWrapperHelper.save(docType_ARI);

		final I_C_DocType docType_API = InterfaceWrapperHelper.newInstance(I_C_DocType.class, context);
		docType_API.setName("API");
		docType_API.setDocBaseType(X_C_DocType.DOCBASETYPE_APInvoice);
		docType_API.setIsSOTrx(false);
		docType_API.setIsDefault(true);
		InterfaceWrapperHelper.save(docType_API);
	}

	protected void config_Pricing()
	{
		final Properties ctx = Env.getCtx();
		final PlainContextAware context = new PlainContextAware(ctx);

		//
		// create the "none" PS and PL
		final I_M_PricingSystem pricingSystem_None = InterfaceWrapperHelper.newInstance(I_M_PricingSystem.class, context);
		pricingSystem_None.setM_PricingSystem_ID(MPricingSystem.M_PricingSystem_ID_None);
		pricingSystem_None.setName("None");
		InterfaceWrapperHelper.save(pricingSystem_None);

		final I_M_PriceList priceList_None = InterfaceWrapperHelper.newInstance(I_M_PriceList.class, context);
		priceList_None.setM_PriceList_ID(MPriceList.M_PriceList_ID_None);
		priceList_None.setM_PricingSystem_ID(pricingSystem_None.getM_PricingSystem_ID());
		priceList_None.setName("None");
		priceList_None.setIsSOPriceList(true);
		priceList_None.setC_Country_ID(country_DE.getC_Country_ID());
		InterfaceWrapperHelper.save(priceList_None);

		final int currencyPrecision = currencyConversionBL.getBaseCurrency(ctx).getStdPrecision();

		//
		// create a sales PS and PLV
		pricingSystem_SO = InterfaceWrapperHelper.newInstance(I_M_PricingSystem.class);
		InterfaceWrapperHelper.save(pricingSystem_SO);

		final I_M_PriceList pl_so = InterfaceWrapperHelper.newInstance(I_M_PriceList.class);
		pl_so.setM_PricingSystem(pricingSystem_SO);
		pl_so.setIsSOPriceList(true);
		pl_so.setPricePrecision(currencyPrecision);
		InterfaceWrapperHelper.save(pl_so);

		priceListVersion_SO = InterfaceWrapperHelper.newInstance(I_M_PriceList_Version.class);
		priceListVersion_SO.setM_PriceList_ID(pl_so.getM_PriceList_ID());
		priceListVersion_SO.setValidFrom(plvDate);
		InterfaceWrapperHelper.save(priceListVersion_SO);

		//
		// create a purchase PS and PLV
		pricingSystem_PO = InterfaceWrapperHelper.newInstance(I_M_PricingSystem.class);
		InterfaceWrapperHelper.save(pricingSystem_PO);

		final I_M_PriceList pl_po = InterfaceWrapperHelper.newInstance(I_M_PriceList.class);
		pl_po.setM_PricingSystem(pricingSystem_PO);
		pl_po.setIsSOPriceList(false);
		pl_po.setPricePrecision(currencyPrecision);
		InterfaceWrapperHelper.save(pl_po);

		priceListVersion_PO = InterfaceWrapperHelper.newInstance(I_M_PriceList_Version.class);
		priceListVersion_PO.setM_PriceList_ID(pl_po.getM_PriceList_ID());
		priceListVersion_PO.setValidFrom(plvDate);
		InterfaceWrapperHelper.save(priceListVersion_PO);
	}

	protected final C_Invoice_Candidate_Builder createInvoiceCandidate()
	{
		return new C_Invoice_Candidate_Builder(this)
				// Set defaults (backward compatibility with existing tests)
				.setOrderDocNo("order1")
				.setOrderLineDescription("orderline1_1")
				.setDiscount(0)
				.setC_Tax(tax_Default);
	}

	/**
	 * Quick create an {@link I_C_Invoice_Candidate}.
	 *
	 * @see #createInvoiceCandidate()
	 */
	protected final I_C_Invoice_Candidate createInvoiceCandidate(final int billBPartnerId,
			final int priceEntered,
			final int qty,
			final boolean isManual,
			final boolean isSOTrx)
	{
		return createInvoiceCandidate()
				.setBillBPartnerId(billBPartnerId)
				.setPriceEntered(priceEntered)
				.setQty(qty)
				.setManual(isManual)
				.setSOTrx(isSOTrx)
				.build();
	}

	/**
	 * Quick create an {@link I_C_Invoice_Candidate}.
	 *
	 * @see #createInvoiceCandidate()
	 */
	protected final I_C_Invoice_Candidate createInvoiceCandidate(final int billBPartnerId,
			final int priceEntered,
			final int qty,
			final int discount,
			final boolean isManual,
			final boolean isSOTrx)
	{
		return createInvoiceCandidate()
				.setBillBPartnerId(billBPartnerId)
				.setPriceEntered(priceEntered)
				.setQty(qty)
				.setDiscount(discount)
				.setManual(isManual)
				.setSOTrx(isSOTrx)
				.build();
	}

	protected final I_M_InOut createInOut(final int bpartnerId, final int orderId, final String documentNo)
	{
		final I_M_InOut inOut = inOut(documentNo, I_M_InOut.class);
		inOut.setC_BPartner_ID(bpartnerId);
		inOut.setC_Order_ID(orderId);

		// gh #1566: inactive and reversed inouts will be ignored by IInvoiceCandDAO.retrieveICIOLAssociationsExclRE()
		inOut.setDocStatus(IDocument.STATUS_Completed);
		inOut.setIsActive(true);

		InterfaceWrapperHelper.save(inOut);
		return inOut;
	}

	protected final I_M_InOutLine createInvoiceCandidateInOutLine(
			final I_C_Invoice_Candidate ic,
			final I_M_InOut inOut,
			final BigDecimal qtyEntered,
			final String inOutLineDescription)
	{
		final boolean assumeNew = true;

		//
		// Create IOL
		final I_M_InOutLine inOutLine = inOutLine(inOutLineDescription, assumeNew, I_M_InOutLine.class);
		{
			inOutLine.setM_InOut_ID(inOut.getM_InOut_ID());
			inOutLine.setC_OrderLine_ID(ic.getC_OrderLine_ID());
			inOutLine.setQtyEntered(qtyEntered);
			inOutLine.setMovementQty(qtyEntered);
			InterfaceWrapperHelper.save(inOutLine);
		}

		//
		// Set orderLine delivered Qty
		{
			final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(ic.getC_OrderLine(), I_C_OrderLine.class);
			final BigDecimal qtyDeliveredOL = BigDecimal.ZERO.add(orderLine.getQtyDelivered()).add(qtyEntered);
			orderLine.setQtyDelivered(qtyDeliveredOL);

			InterfaceWrapperHelper.save(orderLine);
		}

		//
		// Set C_Invoice_Candidate deliveredQty
		{
			final BigDecimal qtyDeliveredIC = BigDecimal.ZERO.add(ic.getQtyDelivered()).add(qtyEntered);
			ic.setQtyDelivered(qtyDeliveredIC);

			InterfaceWrapperHelper.save(ic);
		}

		//
		// Bind IOL to IC
		invoiceCandidateInOutLine(ic, inOutLine);
		return inOutLine;
	}

	protected final I_C_InvoiceCandidate_InOutLine invoiceCandidateInOutLine(final I_C_Invoice_Candidate ic, final I_M_InOutLine inOutLine)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_C_InvoiceCandidate_InOutLine iciol = db.getFirstOnly(I_C_InvoiceCandidate_InOutLine.class, pojo -> pojo.getC_Invoice_Candidate_ID() == ic.getC_Invoice_Candidate_ID()
				&& pojo.getM_InOutLine_ID() == inOutLine.getM_InOutLine_ID());

		if (iciol == null)
		{
			iciol = db.newInstance(Env.getCtx(), I_C_InvoiceCandidate_InOutLine.class);
			iciol.setC_Invoice_Candidate_ID(ic.getC_Invoice_Candidate_ID());
			iciol.setM_InOutLine_ID(inOutLine.getM_InOutLine_ID());
			InterfaceWrapperHelper.save(iciol);
		}

		return iciol;
	}

	protected void completeInOut(final I_M_InOut inOut)
	{
		inOut.setProcessed(true);
		inOut.setDocStatus(X_C_Order.DOCSTATUS_Completed); // fake complete
		InterfaceWrapperHelper.save(inOut);
	}

	/**
	 * Updates all invalid invoice candidates
	 */
	protected final void updateInvalidCandidates()
	{
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

		final Properties ctx = Env.getCtx();
		final String trxName = Services.get(ITrxManager.class).createTrxName("UpdateInvalidCandidates");

		// NOTE: setting this flag to make sure that the model validators behave as they would when the server-side process was run. In particular, make sure
		// that the ICs are not invalidated (again) when they are changed, because that causes trouble with the "light" PlainInvoiceCandDAO recomputeMap
		// implementation.
		try (final IAutoCloseable updateInProgressCloseable = invoiceCandBL.setUpdateProcessInProgress())
		{
			invoiceCandBL.updateInvalid()
					.setContext(ctx, trxName)
					.setTaggedWithAnyTag()
					.update();

			//
			// Call model validator, because if is not called directly (would otherwise be called from within 'updateInvalid')
			if (!modelInterceptorsRegistered)
			{
				final List<I_C_Invoice_Candidate> allCandidates = POJOLookupMap.get().getRecords(I_C_Invoice_Candidate.class);
				Collections.sort(allCandidates, PlainInvoiceCandDAO.INVALID_CANDIDATES_ORDERING);

				for (final I_C_Invoice_Candidate ic : allCandidates)
				{
					if (ic.isProcessed())
					{
						continue;
					}
					getInvoiceCandidateValidator().updateNetAmtToInvoice(ic);
					InterfaceWrapperHelper.save(ic);
				}
			}

			//
			// Make sure there are NO invalid invoice candidates.
			final boolean existingInvalidCandidates = Services.get(IQueryBL.class)
					.createQueryBuilder(I_C_Invoice_Candidate_Recompute.class, ctx, trxName)
					.create()
					.match();
			Assert.assertEquals("Existing invalid invoice candidates", false, existingInvalidCandidates);
		}
	}

	/**
	 * Update given invalid invoice candidates.
	 *
	 * Also, please note:
	 * <ul>
	 * <li>this method is refreshing the invoice candidates after updating them (just to make sure we get the up2date result).
	 * <li>this method is <b>NOT</b> simulating the IC model interceptor like {@link #updateInvalidCandidates()} it does; it just assumes the model interceptor is there and running.
	 * </ul>
	 *
	 * @param invoiceCandidates
	 */
	protected void updateInvalid(final Iterable<I_C_Invoice_Candidate> invoiceCandidates)
	{
		final Properties ctx = Env.getCtx();

		//
		// Update invalid
		Services.get(ITrxManager.class).run(new TrxRunnableAdapter()
		{
			@Override
			public void run(String localTrxName) throws Exception
			{
				invoiceCandBL.updateInvalid()
						.setContext(ctx, localTrxName)
						.setTaggedWithAnyTag()
						.setOnlyC_Invoice_Candidates(invoiceCandidates)
						.update();
			}
		});

		//
		// Refresh invoice candidates
		for (I_C_Invoice_Candidate ic : invoiceCandidates)
		{
			InterfaceWrapperHelper.refresh(ic);
		}
	}

	/**
	 * Lazily initializes our {@link C_Invoice_Candidate} model validator/interceptor and returns it. The lazyness is required because the MV might make calls to {@link Services#get(Class)} before the
	 * actual testing starts and might lead to trouble when then {@link Services#clear()} is called prio to the tests (because then we might have then old reference beeing left in some classes).
	 *
	 * @return
	 */
	public final C_Invoice_Candidate getInvoiceCandidateValidator()
	{
		if (invoiceCandidateValidator == null)
		{
			invoiceCandidateValidator = new C_Invoice_Candidate();
		}
		return invoiceCandidateValidator;
	}

	public final boolean isModelInterceptorsRegistered()
	{
		return modelInterceptorsRegistered;
	}

	protected final void registerModelInterceptors()
	{
		if (modelInterceptorsRegistered)
		{
			// already registered
			return;
		}

		final IModelInterceptorRegistry modelInterceptorRegistry = Services.get(IModelInterceptorRegistry.class);
		modelInterceptorRegistry.addModelInterceptor(getInvoiceCandidateValidator());

		modelInterceptorsRegistered = true;
	}

	protected final InvoiceCandidateExpectation<Object> newInvoiceCandidateExpectation()
	{
		return InvoiceCandidateExpectation.newExpectation();
	}

	protected PlainInvoicingParams createDefaultInvoicingParams()
	{
		final PlainInvoicingParams invoicingParams = new PlainInvoicingParams();
		invoicingParams.setIgnoreInvoiceSchedule(true);
		invoicingParams.setConsolidateApprovedICs(false);
		return invoicingParams;
	}
}
