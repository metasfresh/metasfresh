package de.metas.contracts.impl;

import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.ProductActivityProvider;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.contracts.FlatrateTermRequest.CreateFlatrateTermRequest;
import de.metas.contracts.IContractChangeBL;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.flatrate.interfaces.I_C_DocType;
import de.metas.contracts.impl.FlatrateTermDataFactory.ProductAndPricingSystem;
import de.metas.contracts.location.adapter.ContractDocumentLocationAdapterFactory;
import de.metas.contracts.model.I_C_Contract_Change;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Contract_Change;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.modular.ModularContractComputingMethodHandlerRegistry;
import de.metas.contracts.modular.ModularContractPriceRepository;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.computing.ComputingMethodService;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.log.status.ModularLogCreateStatusRepository;
import de.metas.contracts.modular.log.status.ModularLogCreateStatusService;
import de.metas.contracts.modular.settings.ModularContractSettingsBL;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.contracts.modular.workpackage.ProcessModularLogsEnqueuer;
import de.metas.contracts.order.model.I_C_Order;
import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.DocBaseType;
import de.metas.document.dimension.DimensionFactory;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.OrderLineDimensionFactory;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleUpdater;
import de.metas.invoice.detail.InvoiceCandidateWithDetailsRepository;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.document.dimension.InvoiceCandidateDimensionFactory;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.location.ICountryAreaBL;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.service.ProductScalePriceService;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.product.IProductActivityProvider;
import de.metas.product.ProductAndCategoryId;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_CountryArea;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_C_Year;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_Tax;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * @author metas-dev <dev@metasfresh.com>
 */
public abstract class AbstractFlatrateTermTest
{
	protected final static BigDecimal QTY_ONE = BigDecimal.ONE;
	protected final static BigDecimal PRICE_TEN = BigDecimal.TEN;
	private final static String SEQUENCE = "@BP@ @CON@ @A1@ @A2@ @A3@ @A4@ @P@ @C@ @CO@";
	private final transient IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
	public FlatrateTermTestHelper helper;

	protected IContractChangeBL contractChangeBL;

	@Getter
	private I_C_Calendar calendar;
	@Getter
	private AcctSchemaId acctSchemaId;
	@Getter
	private CurrencyId currencyId;
	@Getter
	private I_C_Country country;
	@Getter
	private I_C_BPartner bpartner;
	@Getter
	private I_C_BPartner_Location bpLocation;
	@Getter
	private org.compiere.model.I_AD_User user;
	private TaxCategoryId taxCategoryId;

	@BeforeAll
	public static void staticInit()
	{
		POJOWrapper.setDefaultStrictValues(false);
	}

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		setupMasterData();

		Services.registerService(IShipmentScheduleUpdater.class, ShipmentScheduleUpdater.newInstanceForUnitTesting());
		Services.registerService(IProductActivityProvider.class, ProductActivityProvider.createInstanceForUnitTesting());

		final List<DimensionFactory<?>> dimensionFactories = new ArrayList<>();
		dimensionFactories.add(new InvoiceCandidateDimensionFactory());
		dimensionFactories.add(new OrderLineDimensionFactory());
		final DimensionService dimensionService = new DimensionService(dimensionFactories);
		SpringContextHolder.registerJUnitBean(dimensionService);

		SpringContextHolder.registerJUnitBean(IBPartnerBL.class, new BPartnerBL(new UserRepository()));
		SpringContextHolder.registerJUnitBean(new ProductTaxCategoryService(new ProductTaxCategoryRepository()));
		SpringContextHolder.registerJUnitBean(ProductScalePriceService.newInstanceForUnitTesting());
		SpringContextHolder.registerJUnitBean(new ModularContractSettingsDAO());
		SpringContextHolder.registerJUnitBean(new ModularContractLogDAO());
		SpringContextHolder.registerJUnitBean(new ModularContractSettingsBL(new ModularContractSettingsDAO()));

		SpringContextHolder.registerJUnitBean(new ModularContractLogService(new ModularContractLogDAO(), new InvoiceCandidateWithDetailsRepository()));
		SpringContextHolder.registerJUnitBean(new ModularContractService(new ModularContractComputingMethodHandlerRegistry(Collections.emptyList()),
				new ModularContractSettingsDAO(),
				new ProcessModularLogsEnqueuer(new ModularLogCreateStatusService(new ModularLogCreateStatusRepository())),
				new ComputingMethodService(new ModularContractLogService(new ModularContractLogDAO(), new InvoiceCandidateWithDetailsRepository())),
				new ModularContractPriceRepository()));

		contractChangeBL = Services.get(IContractChangeBL.class);

		afterInit();
	}

	protected void afterInit()
	{
	}

	protected FlatrateTermTestHelper createFlatrateTermTestHelper()
	{
		return new FlatrateTermTestHelper();
	}

	protected void setupMasterData()
	{
		helper = createFlatrateTermTestHelper();
		createCalendar();
		createAcctSchema();
		createWarehouse();
		createDocType();
		createCountryAndCountryArea();
		createTaxes();

		currencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
	}

	public I_C_Flatrate_Term prepareContractForTest(final String extensionType, final Timestamp startDate)
	{
		prepareBPartner();

		final ProductAndPricingSystem productAndPricingSystem = createProductAndPricingSystem(startDate);
		createProductAcct(productAndPricingSystem);

		final I_C_Flatrate_Conditions conditions = createFlatrateConditions(productAndPricingSystem, extensionType);
		createContractChange(conditions);

		return createFlatrateTerm(
				conditions,
				productAndPricingSystem.getProductAndCategoryId(),
				startDate);
	}

	public List<I_C_Invoice_Candidate> createInvoiceCandidates(final I_C_Flatrate_Term flatrateTerm)
	{
		return invoiceCandidateHandlerBL.createMissingCandidatesFor(flatrateTerm);
	}

	private void createCalendar()
	{
		calendar = newInstance(I_C_Calendar.class);
		save(calendar);

		final I_C_Year currentYear = newInstance(I_C_Year.class);
		currentYear.setC_Calendar_ID(calendar.getC_Calendar_ID());
		save(currentYear);

		for (int i = 1; i <= 12; i++)
		{
			final Timestamp startDate = TimeUtil.getDay(2017, i, 1);
			final Timestamp endDate = TimeUtil.getMonthLastDay(startDate);
			final I_C_Period period = newInstance(I_C_Period.class);
			period.setStartDate(startDate);
			period.setEndDate(endDate);
			period.setC_Year_ID(currentYear.getC_Year_ID());
			save(period);
		}

		final I_C_Year nextYear = newInstance(I_C_Year.class);
		nextYear.setC_Calendar_ID(calendar.getC_Calendar_ID());
		save(nextYear);

		for (int i = 1; i <= 12; i++)
		{
			final Timestamp startDate = TimeUtil.getDay(2018, i, 1);
			final Timestamp endDate = TimeUtil.getMonthLastDay(startDate);
			final I_C_Period period = newInstance(I_C_Period.class);
			period.setStartDate(startDate);
			period.setEndDate(endDate);
			period.setC_Year_ID(nextYear.getC_Year_ID());
			save(period);
		}

	}

	private void createAcctSchema()
	{
		// NOTE: there is no need for actual accounting schema.
		// A dummy ID is sufficient.
		acctSchemaId = AcctSchemaId.ofRepoId(1);

		// acctSchemaId = AcctSchemaTestHelper.newAcctSchema().build();
		// Services.registerService(IAcctSchemaDAO.class, new AcctSchemaDAO()
		// {
		// @Override
		// public AcctSchema getByCliendAndOrg(final ClientId clientId, final OrgId orgId)
		// {
		// return getById(acctSchemaId);
		// }
		// });
	}

	private void createWarehouse()
	{
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setName("WH");
		warehouse.setAD_Org_ID(helper.getOrg().getAD_Org_ID());
		save(warehouse);
	}

	private void createDocType()
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setAD_Org_ID(helper.getOrg().getAD_Org_ID());
		docType.setDocSubType(I_C_DocType.DocSubType_Abonnement);
		docType.setDocBaseType(DocBaseType.CustomerContract.getCode());
		save(docType);
	}

	private void createCountryAndCountryArea()
	{
		country = newInstance(I_C_Country.class);
		country.setAD_Org_ID(helper.getOrg().getAD_Org_ID());
		country.setAD_Language("de_DE");
		country.setCountryCode("DE");
		country.setDisplaySequence(SEQUENCE);
		country.setDisplaySequenceLocal(SEQUENCE);
		country.setCaptureSequence(SEQUENCE);
		save(country);

		final I_C_CountryArea countryArea = newInstance(I_C_CountryArea.class, country);
		countryArea.setValue(ICountryAreaBL.COUNTRYAREAKEY_EU);
		save(countryArea);
	}

	private void createTaxes()
	{
		final I_C_TaxCategory taxCategory = newInstance(I_C_TaxCategory.class);
		saveRecord(taxCategory);
		taxCategoryId = TaxCategoryId.ofRepoId(taxCategory.getC_TaxCategory_ID());

		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setC_TaxCategory_ID(taxCategory.getC_TaxCategory_ID());
		tax.setValidFrom(TimeUtil.asTimestamp(LocalDate.of(1970, Month.JANUARY, 1).atStartOfDay().atZone(ZoneId.systemDefault())));
		tax.setC_Country_ID(country.getC_Country_ID());
		tax.setTo_Country_ID(country.getC_Country_ID());
		tax.setTypeOfDestCountry(X_C_Tax.TYPEOFDESTCOUNTRY_Domestic);
		tax.setSOPOType(X_C_Tax.SOPOTYPE_SalesTax);
		saveRecord(tax);
	}

	protected int prepareBPartner()
	{
		bpartner = FlatrateTermDataFactory.bpartnerNew()
				.bpValue("G0022")
				.isCustomer(true)
				.build();

		bpLocation = FlatrateTermDataFactory.bpLocationNew()
				.bpartner(bpartner)
				.isBillTo_Default(true)
				.isShipTo_Default(true)
				.country(getCountry())
				.build();

		user = FlatrateTermDataFactory.userNew()
				.bpartner(bpartner)
				.isBillToContact_Default(true)
				.isShipToContact_Default(true)
				.firstName("FN")
				.lastName("LN")
				.build();

		return bpartner.getC_BPartner_ID();
	}

	protected ProductAndPricingSystem createProductAndPricingSystem(@NonNull final Timestamp startDate)
	{
		return FlatrateTermDataFactory.productAndPricingNew()
				.productValue("01")
				.productName("testProduct")
				.currencyId(getCurrencyId())
				.country(getCountry())
				.isTaxInclcuded(false)
				.validFrom(startDate)
				.build();
	}

	protected void createProductAcct(@NonNull final ProductAndPricingSystem productAndPricingSystem)
	{
		final I_M_Product product = productAndPricingSystem.getProduct();

		FlatrateTermDataFactory.productAcctNew()
				.product(product)
				.acctSchemaId(getAcctSchemaId())
				.build();
	}

	protected I_C_Flatrate_Conditions createFlatrateConditions(@NonNull final ProductAndPricingSystem productAndPricingSystem, final String extensionType)
	{
		return FlatrateTermDataFactory.flatrateConditionsNew()
				.name("Abo")
				.calendar(getCalendar())
				.pricingSystem(productAndPricingSystem.getPricingSystem())
				.invoiceRule(X_C_Flatrate_Conditions.INVOICERULE_Immediate)
				.typeConditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription)
				.onFlatrateTermExtend(X_C_Flatrate_Conditions.ONFLATRATETERMEXTEND_CalculatePrice)
				.isCreateNoInvoice(false)
				.extensionType(extensionType)
				.uomId(UomId.ofRepoId(productAndPricingSystem.getProduct().getC_UOM_ID()))
				.build();
	}

	protected I_C_Flatrate_Term createFlatrateTerm(
			@NonNull final I_C_Flatrate_Conditions conditions,
			@NonNull final ProductAndCategoryId productAndCategoryId,
			@NonNull final Timestamp startDate)
	{
		final I_C_OrderLine orderLine = createOrderAndOrderLine(conditions, productAndCategoryId.getProductId());

		final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

		final CreateFlatrateTermRequest createFlatrateTermRequest = CreateFlatrateTermRequest.builder()
				.orgId(OrgId.ofRepoId(orderLine.getAD_Org_ID()))
				.context(helper.getContextProvider())
				.bPartner(getBpartner())
				.conditions(conditions)
				.startDate(startDate)
				.productAndCategoryId(productAndCategoryId)
				.completeIt(false)
				.build();

		final I_C_Flatrate_Term contract = flatrateBL.createTerm(createFlatrateTermRequest);

		final I_C_BPartner_Location bpLocation = getBpLocation();
		final I_AD_User user = getUser();
		final BPartnerLocationAndCaptureId bpartnerLocationId = BPartnerLocationAndCaptureId.ofRepoIdOrNull(bpLocation.getC_BPartner_ID(),
				bpLocation.getC_BPartner_Location_ID(),
				bpLocation.getC_Location_ID());

		final BPartnerContactId bPartnerContactId = BPartnerContactId.ofRepoIdOrNull(user.getC_BPartner_ID(), user.getAD_User_ID());

		ContractDocumentLocationAdapterFactory
				.billLocationAdapter(contract)
				.setFrom(bpartnerLocationId, bPartnerContactId);

		ContractDocumentLocationAdapterFactory
				.dropShipLocationAdapter(contract)
				.setFrom(bpartnerLocationId, bPartnerContactId);

		contract.setPriceActual(PRICE_TEN);
		contract.setPlannedQtyPerUnit(QTY_ONE);
		contract.setMasterStartDate(startDate);
		contract.setM_Product_ID(productAndCategoryId.getProductId().getRepoId());
		contract.setC_TaxCategory_ID(taxCategoryId.getRepoId());
		contract.setIsTaxIncluded(true);
		contract.setC_OrderLine_Term_ID(orderLine.getC_OrderLine_ID());
		contract.setC_Order_Term_ID(orderLine.getC_Order_ID());
		save(contract);
		flatrateBL.complete(contract);

		return contract;
	}

	protected I_C_OrderLine createOrderAndOrderLine(
			@NonNull final I_C_Flatrate_Conditions conditions,
			@NonNull final ProductId productId)
	{
		final I_C_Order orderRecord = newInstance(I_C_Order.class);
		orderRecord.setContractStatus(I_C_Order.CONTRACTSTATUS_Active);
		saveRecord(orderRecord);

		final I_C_OrderLine orderLineRecord = newInstance(I_C_OrderLine.class);
		orderLineRecord.setC_Order(orderRecord);
		orderLineRecord.setC_Flatrate_Conditions(conditions);
		orderLineRecord.setM_Product_ID(productId.getRepoId());
		saveRecord(orderLineRecord);

		return orderLineRecord;
	}

	protected void createContractChange(@NonNull final I_C_Flatrate_Conditions flatrateConditions)
	{
		final I_C_Contract_Change contractChange = newInstance(I_C_Contract_Change.class);
		contractChange.setAction(X_C_Contract_Change.ACTION_Statuswechsel);
		contractChange.setC_Flatrate_Transition(flatrateConditions.getC_Flatrate_Transition());
		contractChange.setC_Flatrate_Conditions(flatrateConditions);
		contractChange.setContractStatus(X_C_Contract_Change.CONTRACTSTATUS_Gekuendigt);
		contractChange.setDeadLine(1);
		contractChange.setDeadLineUnit(X_C_Contract_Change.DEADLINEUNIT_MonatE);
		save(contractChange);
	}
}
