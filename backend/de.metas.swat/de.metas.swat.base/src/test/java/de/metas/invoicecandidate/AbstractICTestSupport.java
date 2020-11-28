package de.metas.invoicecandidate;

import static de.metas.util.Check.assumeGreaterThanZero;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
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
import de.metas.attachments.AttachmentEntryService;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.impl.PlainCurrencyBL;
import de.metas.document.engine.DocStatus;
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
import de.metas.invoicecandidate.compensationGroup.InvoiceCandidateGroupRepository;
import de.metas.invoicecandidate.expectations.InvoiceCandidateExpectation;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Recompute;
import de.metas.invoicecandidate.modelvalidator.C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IAggregator;
import de.metas.invoicecandidate.spi.impl.PlainInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.impl.aggregator.standard.DefaultAggregator;
import de.metas.location.CountryId;
import de.metas.notification.INotificationRepository;
import de.metas.notification.impl.NotificationRepository;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.organization.OrgId;
import de.metas.organization.StoreCreditCardNumberMode;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.testsupport.AbstractTestSupport;
import de.metas.uom.UomId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import lombok.Getter;

public class AbstractICTestSupport extends AbstractTestSupport
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
	protected ClientId clientId;
	protected OrgId orgId;

	@Getter
	protected ProductId productId;

	@Getter
	protected UomId uomId;
	protected ActivityId activityId;
	protected WarehouseId warehouseId;

	private CountryId countryId_DE;

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

		final I_AD_Client client = InterfaceWrapperHelper.newInstance(I_AD_Client.class);
		saveRecord(client);
		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Client_ID, client.getAD_Client_ID());
		clientId = ClientId.ofRepoId(client.getAD_Client_ID());

		modelInterceptorsRegistered = false;
		// FIXME: make sure all tests based on this abstract test support model interceptors
		// registerModelInterceptors();

		final Properties ctx = Env.getCtx();
		Env.setContext(ctx, Env.CTXNAME_Date, SystemTime.asDayTimestamp());

		// final String trxName = Trx.createTrxName();
		final String trxName = ITrx.TRXNAME_ThreadInherited;

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

		final I_C_Country country_DE = InterfaceWrapperHelper.create(ctx, I_C_Country.class, trxName);
		country_DE.setAD_Language("de");
		InterfaceWrapperHelper.save(country_DE);
		countryId_DE = CountryId.ofRepoId(country_DE.getC_Country_ID());

		config_StandardDocTypes();
		config_Pricing();

		final I_AD_Org orgRecord = InterfaceWrapperHelper.create(ctx, I_AD_Org.class, trxName); // 07442
		InterfaceWrapperHelper.save(orgRecord);
		orgId = OrgId.ofRepoId(orgRecord.getAD_Org_ID());

		final I_AD_OrgInfo orgInfoRecord = newInstance(I_AD_OrgInfo.class);
		orgInfoRecord.setAD_Org_ID(orgRecord.getAD_Org_ID());
		orgInfoRecord.setStoreCreditCardData(StoreCreditCardNumberMode.DONT_STORE.getCode());
		saveRecord(orgInfoRecord);

		final I_M_Warehouse warehouse = InterfaceWrapperHelper.create(ctx, I_M_Warehouse.class, trxName);
		warehouse.setAD_Org_ID(orgRecord.getAD_Org_ID());
		InterfaceWrapperHelper.save(warehouse);
		warehouseId = WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());

		final I_C_UOM stockUomRecord = InterfaceWrapperHelper.create(ctx, I_C_UOM.class, trxName);
		InterfaceWrapperHelper.save(stockUomRecord);

		final I_M_Product product = BusinessTestHelper.createProduct("product", stockUomRecord);
		productId = ProductId.ofRepoId(product.getM_Product_ID());

		final I_C_UOM uomRecord = InterfaceWrapperHelper.create(ctx, I_C_UOM.class, trxName);
		InterfaceWrapperHelper.save(uomRecord);
		uomId = UomId.ofRepoId(uomRecord.getC_UOM_ID());

		final I_C_UOM_Conversion uomConversionRecord = newInstance(I_C_UOM_Conversion.class);
		uomConversionRecord.setC_UOM_ID(stockUomRecord.getC_UOM_ID());
		uomConversionRecord.setC_UOM_To_ID(uomRecord.getC_UOM_ID());
		uomConversionRecord.setMultiplyRate(TEN);
		uomConversionRecord.setDivideRate(ONE.divide(TEN));
		saveRecord(uomConversionRecord);

		final I_C_Activity activity = InterfaceWrapperHelper.create(ctx, I_C_Activity.class, trxName);
		InterfaceWrapperHelper.save(activity);
		activityId = ActivityId.ofRepoId(activity.getC_Activity_ID());

		//
		// Services
		invoiceCandBL = Services.get(IInvoiceCandBL.class);

		final AttachmentEntryService attachmentEntryService = AttachmentEntryService.createInstanceForUnitTesting();

		Services.registerService(INotificationRepository.class, new NotificationRepository(attachmentEntryService));
		Services.registerService(IBPartnerBL.class, new BPartnerBL(new UserRepository()));

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
				.setAD_Column(I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID)
				.end()
			.newItem()
				.setType(X_C_AggregationItem.TYPE_Column)
				.setAD_Column(I_C_Invoice_Candidate.COLUMNNAME_Bill_Location_ID)
				.end()
			.newItem()
				.setType(X_C_AggregationItem.TYPE_Column)
				.setAD_Column(I_C_Invoice_Candidate.COLUMNNAME_C_Currency_ID)
				.end()
			.newItem()
				.setType(X_C_AggregationItem.TYPE_Column)
				.setAD_Column(I_C_Invoice_Candidate.COLUMNNAME_AD_Org_ID)
				.end()
			.newItem()
				.setType(X_C_AggregationItem.TYPE_Column)
				.setAD_Column(I_C_Invoice_Candidate.COLUMNNAME_IsSOTrx)
				.end()
			.newItem()
				.setType(X_C_AggregationItem.TYPE_Column)
				.setAD_Column(I_C_Invoice_Candidate.COLUMNNAME_IsTaxIncluded)
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
				.setAD_Column(I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID)
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
		defaultLineAgg.setC_BPartner_ID(0);
		defaultLineAgg.setM_ProductGroup(null);
		InterfaceWrapperHelper.save(defaultLineAgg);

		final PlainAggregationDAO aggregationDAO = (PlainAggregationDAO)Services.get(IAggregationDAO.class);
		aggregationDAO.setDefaultAgg(defaultLineAgg);
	}

	protected void config_Taxes()
	{
		final I_C_TaxCategory taxCategory_None = InterfaceWrapperHelper.newInstance(I_C_TaxCategory.class);
		taxCategory_None.setC_TaxCategory_ID(100);
		taxCategory_None.setName("None");
		InterfaceWrapperHelper.save(taxCategory_None);

		tax_NotFound = InterfaceWrapperHelper.newInstance(I_C_Tax.class);
		tax_NotFound.setC_Tax_ID(100);
		tax_NotFound.setC_TaxCategory(taxCategory_None);
		InterfaceWrapperHelper.save(tax_NotFound);

		final I_C_TaxCategory taxCategory_Default = InterfaceWrapperHelper.newInstance(I_C_TaxCategory.class);
		taxCategory_Default.setC_TaxCategory_ID(1000000);
		taxCategory_Default.setName("Default");
		InterfaceWrapperHelper.save(taxCategory_Default);

		tax_Default = InterfaceWrapperHelper.newInstance(I_C_Tax.class);
		tax_Default.setC_Tax_ID(1000000);
		tax_Default.setC_TaxCategory(taxCategory_Default);
		InterfaceWrapperHelper.save(tax_Default);
	}

	protected void config_StandardDocTypes()
	{
		final I_C_DocType docType_ARI = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docType_ARI.setName("ARI");
		docType_ARI.setDocBaseType(X_C_DocType.DOCBASETYPE_ARInvoice);
		docType_ARI.setIsSOTrx(true);
		docType_ARI.setIsDefault(true);
		InterfaceWrapperHelper.save(docType_ARI);

		final I_C_DocType docType_API = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docType_API.setName("API");
		docType_API.setDocBaseType(X_C_DocType.DOCBASETYPE_APInvoice);
		docType_API.setIsSOTrx(false);
		docType_API.setIsDefault(true);
		InterfaceWrapperHelper.save(docType_API);
	}

	protected void config_Pricing()
	{
		//
		// create the "none" PS and PL
		final I_M_PricingSystem pricingSystem_None = InterfaceWrapperHelper.newInstance(I_M_PricingSystem.class);
		pricingSystem_None.setM_PricingSystem_ID(IPriceListDAO.M_PricingSystem_ID_None);
		pricingSystem_None.setName("None");
		InterfaceWrapperHelper.save(pricingSystem_None);

		final Currency currency = currencyConversionBL.getBaseCurrency(Env.getCtx());

		final I_M_PriceList priceList_None = InterfaceWrapperHelper.newInstance(I_M_PriceList.class);
		priceList_None.setM_PriceList_ID(IPriceListDAO.M_PriceList_ID_None);
		priceList_None.setM_PricingSystem_ID(pricingSystem_None.getM_PricingSystem_ID());
		priceList_None.setName("None");
		priceList_None.setIsSOPriceList(true);
		priceList_None.setC_Country_ID(countryId_DE.getRepoId());
		priceList_None.setC_Currency_ID(currency.getId().getRepoId());
		InterfaceWrapperHelper.save(priceList_None);

		final CurrencyPrecision currencyPrecision = currency.getPrecision();

		//
		// create a sales PS and PLV
		pricingSystem_SO = InterfaceWrapperHelper.newInstance(I_M_PricingSystem.class);
		InterfaceWrapperHelper.save(pricingSystem_SO);

		final I_M_PriceList pl_so = InterfaceWrapperHelper.newInstance(I_M_PriceList.class);
		pl_so.setM_PricingSystem_ID(pricingSystem_SO.getM_PricingSystem_ID());
		pl_so.setIsSOPriceList(true);
		pl_so.setPricePrecision(currencyPrecision.toInt());
		pl_so.setC_Currency_ID(currency.getId().getRepoId());
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
		pl_po.setM_PricingSystem_ID(pricingSystem_PO.getM_PricingSystem_ID());
		pl_po.setIsSOPriceList(false);
		pl_po.setPricePrecision(currencyPrecision.toInt());
		pl_po.setC_Currency_ID(currency.getId().getRepoId());
		InterfaceWrapperHelper.save(pl_po);

		priceListVersion_PO = InterfaceWrapperHelper.newInstance(I_M_PriceList_Version.class);
		priceListVersion_PO.setM_PriceList_ID(pl_po.getM_PriceList_ID());
		priceListVersion_PO.setValidFrom(plvDate);
		InterfaceWrapperHelper.save(priceListVersion_PO);
	}

	public final C_Invoice_Candidate_Builder createInvoiceCandidate()
	{
		return new C_Invoice_Candidate_Builder(this)
				.setOrgId(orgId)
				// Set defaults (backward compatibility with existing tests)
				.setOrderDocNo("order1")
				.setOrderLineDescription("orderline1_1")
				.setProductId(productId)
				.setUomId(uomId)
				.setDiscount(0)
				.setC_Tax(tax_Default);
	}

	/**
	 * Quick create an {@link I_C_Invoice_Candidate}.
	 *
	 * @see #createInvoiceCandidate()
	 */
	public final I_C_Invoice_Candidate createInvoiceCandidate(final int billBPartnerId,
			final int priceEntered,
			final int qty,
			final boolean isManual,
			final boolean isSOTrx)
	{
		return createInvoiceCandidate()
				.setBillBPartnerId(billBPartnerId)
				.setPriceEntered(priceEntered)
				.setQtyOrdered(qty)
				.setManual(isManual)
				.setSOTrx(isSOTrx)
				.build();
	}

	/**
	 * Quick create an {@link I_C_Invoice_Candidate}.
	 *
	 * @see #createInvoiceCandidate()
	 */
	public final I_C_Invoice_Candidate createInvoiceCandidate(final int billBPartnerId,
			final int priceEntered,
			final int qty,
			final int discount,
			final boolean isManual,
			final boolean isSOTrx)
	{
		return createInvoiceCandidate()
				.setBillBPartnerId(billBPartnerId)
				.setPriceEntered(priceEntered)
				.setQtyOrdered(qty)
				.setDiscount(discount)
				.setManual(isManual)
				.setSOTrx(isSOTrx)
				.build();
	}

	public final I_M_InOut createInOut(final int bpartnerId, final int orderId, final String documentNo)
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
			final I_C_Invoice_Candidate icRecord,
			final I_M_InOut inOut,
			final StockQtyAndUOMQty qtysDelivered,
			final String inOutLineDescription)
	{
		final boolean assumeNew = true;

		//
		// Create IOL
		final I_M_InOutLine inOutLine = inOutLine(inOutLineDescription, assumeNew, I_M_InOutLine.class);
		{
			inOutLine.setM_Product_ID(assumeGreaterThanZero(icRecord.getM_Product_ID(), "icRecord.getM_Product_ID()"));
			inOutLine.setM_InOut_ID(inOut.getM_InOut_ID());
			inOutLine.setC_OrderLine_ID(icRecord.getC_OrderLine_ID());
			inOutLine.setQtyEntered(qtysDelivered.getUOMQtyNotNull().toBigDecimal());
			inOutLine.setC_UOM_ID(qtysDelivered.getUOMQtyNotNull().getUomId().getRepoId());
			inOutLine.setMovementQty(qtysDelivered.getStockQty().toBigDecimal());
			InterfaceWrapperHelper.save(inOutLine);
		}

		//
		// Set orderLine delivered Qty
		{
			final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(icRecord.getC_OrderLine(), I_C_OrderLine.class);
			final BigDecimal qtyDeliveredOL = orderLine.getQtyDelivered().add(qtysDelivered.getStockQty().toBigDecimal());
			orderLine.setQtyDelivered(qtyDeliveredOL);

			InterfaceWrapperHelper.save(orderLine);
		}

		//
		// Set C_Invoice_Candidate deliveredQty
		{
			final BigDecimal qtyDeliveredIC = icRecord.getQtyDelivered().add(qtysDelivered.getStockQty().toBigDecimal());
			icRecord.setQtyDelivered(qtyDeliveredIC);

			InterfaceWrapperHelper.save(icRecord);
		}

		//
		// Bind IOL to IC
		invoiceCandidateInOutLine(icRecord, inOutLine);
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
			invoiceCandBL.updateICIOLAssociationFromIOL(iciol, inOutLine);
			InterfaceWrapperHelper.save(iciol);
		}

		return iciol;
	}

	protected void completeInOut(final I_M_InOut inOut)
	{
		inOut.setProcessed(true);
		inOut.setDocStatus(DocStatus.Completed.getCode()); // fake complete
		InterfaceWrapperHelper.save(inOut);
	}

	/**
	 * Updates all invalid invoice candidates
	 */
	public final void updateInvalidCandidates()
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
					.anyMatch();
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
	 */
	public void updateInvalid(final Iterable<I_C_Invoice_Candidate> invoiceCandidates)
	{
		final Properties ctx = Env.getCtx();

		//
		// Update invalid
		Services.get(ITrxManager.class).runInNewTrx(new TrxRunnableAdapter()
		{
			@Override
			public void run(final String localTrxName) throws Exception
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
		for (final I_C_Invoice_Candidate ic : invoiceCandidates)
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
			final AttachmentEntryService attachmentEntryService = AttachmentEntryService.createInstanceForUnitTesting();
			final InvoiceCandidateGroupRepository groupsRepo = new InvoiceCandidateGroupRepository(new GroupCompensationLineCreateRequestFactory());

			invoiceCandidateValidator = new C_Invoice_Candidate(
					new InvoiceCandidateRecordService(),
					groupsRepo,
					attachmentEntryService);
		}
		return invoiceCandidateValidator;
	}

	public final boolean isModelInterceptorsRegistered()
	{
		return modelInterceptorsRegistered;
	}

	public final void registerModelInterceptors()
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

	public final InvoiceCandidateExpectation<Object> newInvoiceCandidateExpectation()
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
