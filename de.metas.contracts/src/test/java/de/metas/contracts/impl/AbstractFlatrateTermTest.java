package de.metas.contracts.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_CountryArea;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Year;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.BeforeClass;

import de.metas.acct.api.AcctSchemaId;
import de.metas.adempiere.model.I_AD_User;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.flatrate.interfaces.I_C_DocType;
import de.metas.contracts.impl.FlatrateTermDataFactory.ProductAndPricingSystem;
import de.metas.contracts.model.I_C_Contract_Change;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Contract_Change;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.order.model.I_C_Order;
import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.location.ICountryAreaBL;
import de.metas.money.CurrencyId;
import de.metas.product.ProductAndCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;

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
 *
 */
public abstract class AbstractFlatrateTermTest
{
	private final transient IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);

	private final String sequence = "@BP@ @CON@ @A1@ @A2@ @A3@ @A4@ @P@ @C@ @CO@";
	protected final static BigDecimal QTY_ONE = BigDecimal.ONE;
	protected final static BigDecimal PRICE_TEN = BigDecimal.TEN;

	public FlatrateTermTestHelper helper;

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
	private I_AD_User user;

	@BeforeClass
	public final static void staticInit()
	{
		POJOWrapper.setDefaultStrictValues(false);
	}

	@Before
	public final void init()
	{
		AdempiereTestHelper.get().init();

		setupMasterData();
		initialize();

		Services.registerService(IShipmentScheduleBL.class, ShipmentScheduleBL.newInstanceForUnitTesting());
	}

	protected void initialize()
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
		warehouse.setAD_Org(helper.getOrg());
		save(warehouse);
	}

	private void createDocType()
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setAD_Org(helper.getOrg());
		docType.setDocSubType(I_C_DocType.DocSubType_Abonnement);
		docType.setDocBaseType(I_C_DocType.DocBaseType_CustomerContract);
		save(docType);
	}

	private void createCountryAndCountryArea()
	{
		country = newInstance(I_C_Country.class);
		country.setAD_Org(helper.getOrg());
		country.setAD_Language("de_DE");
		country.setCountryCode("DE");
		country.setDisplaySequence(sequence);
		country.setDisplaySequenceLocal(sequence);
		country.setCaptureSequence(sequence);
		save(country);

		final I_C_CountryArea countryArea = newInstance(I_C_CountryArea.class, country);
		countryArea.setValue(ICountryAreaBL.COUNTRYAREAKEY_EU);
		save(countryArea);
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
				.invoiceRule(X_C_Flatrate_Conditions.INVOICERULE_Sofort)
				.typeConditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription)
				.onFlatrateTermExtend(X_C_Flatrate_Conditions.ONFLATRATETERMEXTEND_CalculatePrice)
				.isCreateNoInvoice(false)
				.extensionType(extensionType)
				.build();
	}

	protected I_C_Flatrate_Term createFlatrateTerm(
			@NonNull final I_C_Flatrate_Conditions conditions,
			@NonNull final ProductAndCategoryId productAndCategoryId,
			@NonNull final Timestamp startDate)
	{
		final I_C_OrderLine orderLine = createOrderAndOrderLine(conditions, productAndCategoryId.getProductId());

		final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
		final I_C_Flatrate_Term contract = flatrateBL.createTerm(
				helper.getContextProvider(),
				getBpartner(),
				conditions,
				startDate,
				null,
				productAndCategoryId,
				false);

		final I_C_BPartner_Location bpLocation = getBpLocation();
		final I_AD_User user = getUser();

		contract.setBill_Location(bpLocation);
		contract.setBill_User(user);
		contract.setDropShip_BPartner(getBpartner());
		contract.setDropShip_Location(bpLocation);
		contract.setDropShip_User(user);
		contract.setPriceActual(PRICE_TEN);
		contract.setPlannedQtyPerUnit(QTY_ONE);
		contract.setMasterStartDate(startDate);
		contract.setM_Product_ID(productAndCategoryId.getProductId().getRepoId());
		contract.setIsTaxIncluded(true);
		contract.setC_OrderLine_Term(orderLine);
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

	protected I_C_Contract_Change createContractChange(@NonNull final I_C_Flatrate_Conditions flatrateConditions)
	{
		final I_C_Contract_Change contractChange = newInstance(I_C_Contract_Change.class);
		contractChange.setAction(X_C_Contract_Change.ACTION_Statuswechsel);
		contractChange.setC_Flatrate_Transition(flatrateConditions.getC_Flatrate_Transition());
		contractChange.setC_Flatrate_Conditions(flatrateConditions);
		contractChange.setContractStatus(X_C_Contract_Change.CONTRACTSTATUS_Gekuendigt);
		contractChange.setDeadLine(1);
		contractChange.setDeadLineUnit(X_C_Contract_Change.DEADLINEUNIT_MonatE);
		save(contractChange);
		return contractChange;
	}
}
