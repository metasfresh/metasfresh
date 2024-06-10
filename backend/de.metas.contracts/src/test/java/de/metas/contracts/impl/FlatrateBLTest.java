package de.metas.contracts.impl;

import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.flatrate.dataEntry.invoice.FlatrateDataEntryHandler;
import de.metas.contracts.location.adapter.ContractDocumentLocationAdapterFactory;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Data;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.contracts.model.I_C_Flatrate_Matching;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_DataEntry;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.settings.ModularContractSettingsBL;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.lang.SOTrx;
import de.metas.organization.OrgId;
import de.metas.pricing.rules.MockedPricingRule;
import de.metas.product.IProductActivityProvider;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.VatCodeId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.pricing.model.I_C_PricingRule;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_Year;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product_Category;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

public class FlatrateBLTest extends ContractsTestBase
{

	I_C_Flatrate_Transition transition = null;
	I_C_Flatrate_Term term = null;

	I_C_BPartner partner = null;
	I_M_Product product = null;
	I_M_Product_Category productCategory = null;
	I_C_Flatrate_Conditions conditions1 = null;
	I_C_Flatrate_Conditions conditions2 = null;
	I_C_Flatrate_Term overlappingTerm = null;

	// periods:
	I_C_Period period1 = null;
	I_C_Period period2 = null;
	I_C_Period period3 = null;
	I_C_Period period4 = null;
	I_C_Period period5 = null;
	I_C_Period period6 = null;

	private OrgId orgId;
	private ActivityId activityId;

	protected IProductActivityProvider productActivityProvider;
	protected ITaxBL taxBL;

	@Override
	protected void init()
	{
		MockedPricingRule.INSTANCE.reset();
		final I_C_PricingRule pricingRule = newInstance(I_C_PricingRule.class);
		pricingRule.setSeqNo(10);
		pricingRule.setClassname(MockedPricingRule.class.getName());
		pricingRule.setName(pricingRule.getClassname());
		save(pricingRule);

		final I_C_ILCandHandler handler = newInstance(I_C_ILCandHandler.class);
		handler.setClassname(FlatrateDataEntryHandler.class.getName());
		save(handler);

		productActivityProvider = Mockito.mock(IProductActivityProvider.class);
		taxBL = Mockito.mock(ITaxBL.class);

		SpringContextHolder.registerJUnitBean(IBPartnerBL.class, new BPartnerBL(new UserRepository()));
		SpringContextHolder.registerJUnitBean(new ModularContractSettingsDAO());
		SpringContextHolder.registerJUnitBean(new ModularContractLogDAO());
		SpringContextHolder.registerJUnitBean(new ModularContractSettingsBL(new ModularContractSettingsDAO()));
	}

	@BeforeEach
	public void before()
	{
		orgId = AdempiereTestHelper.createOrgWithTimeZone();

		final I_C_Activity activity = newInstance(I_C_Activity.class);
		save(activity);
		activityId = ActivityId.ofRepoId(activity.getC_Activity_ID());
	}

	private Timestamp day(final int year, final int month, final int day)
	{
		return TimeUtil.getDay(year, month, day);
	}

	@Test
	public void beforeCompleteDataEntry_test()
	{
		final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

		final I_C_Flatrate_Transition flatrateTransition = newInstance(I_C_Flatrate_Transition.class);
		flatrateTransition.setDeliveryInterval(1);
		flatrateTransition.setDeliveryIntervalUnit(X_C_Flatrate_Transition.DELIVERYINTERVALUNIT_JahrE);
		save(flatrateTransition);

		final I_M_PricingSystem pricingSystem = newInstance(I_M_PricingSystem.class);
		save(pricingSystem);

		final I_C_Flatrate_Conditions flatrateConditions = newInstance(I_C_Flatrate_Conditions.class);
		flatrateConditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_HoldingFee);
		flatrateConditions.setM_PricingSystem_ID(pricingSystem.getM_PricingSystem_ID());
		save(flatrateConditions);

		final I_C_Country country = newInstance(I_C_Country.class);
		save(country);

		final I_C_Location location = newInstance(I_C_Location.class);
		location.setC_Country_ID(country.getC_Country_ID());
		save(location);

		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setName("bpartner1");
		save(bpartner);

		final I_C_BPartner_Location bpLocation = newInstance(I_C_BPartner_Location.class);
		bpLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpLocation.setName("Location1");
		bpLocation.setC_Location_ID(location.getC_Location_ID());
		save(bpLocation);

		final I_C_Flatrate_Term currentTerm = newInstance(I_C_Flatrate_Term.class);
		currentTerm.setAD_Org_ID(orgId.getRepoId());
		currentTerm.setStartDate(day(2013, 1, 1));
		currentTerm.setEndDate(day(2014, 7, 27));
		currentTerm.setC_Flatrate_Conditions(flatrateConditions);
		currentTerm.setM_PricingSystem_ID(pricingSystem.getM_PricingSystem_ID());

		final BPartnerLocationAndCaptureId bpartnerLocationId = BPartnerLocationAndCaptureId.ofRepoIdOrNull(bpartner.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID(), location.getC_Location_ID());

		ContractDocumentLocationAdapterFactory
				.billLocationAdapter(currentTerm)
				.setFrom(bpartnerLocationId);

		save(currentTerm);

		final I_M_PriceList priceList = newInstance(I_M_PriceList.class);
		priceList.setC_Currency_ID(10);
		priceList.setC_Country_ID(country.getC_Country_ID());
		priceList.setIsSOPriceList(true);
		priceList.setM_PricingSystem_ID(pricingSystem.getM_PricingSystem_ID());
		priceList.setIsActive(true);
		save(priceList);

		final I_M_PriceList_Version priceListVersion = newInstance(I_M_PriceList_Version.class);
		priceListVersion.setM_PriceList_ID(priceList.getM_PriceList_ID());
		priceListVersion.setValidFrom(day(2013, 1, 1));
		priceListVersion.setIsActive(true);
		save(priceListVersion);

		final I_C_UOM productUOM = newInstance(I_C_UOM.class);
		saveRecord(productUOM);

		final I_M_Product product = newInstance(I_M_Product.class);
		product.setM_Product_Category_ID(20);
		product.setC_UOM_ID(productUOM.getC_UOM_ID());
		save(product);

		final I_C_Period period = newInstance(I_C_Period.class);
		period.setStartDate(day(2013, 1, 1));
		save(period);

		final I_C_Flatrate_DataEntry dataEntry = newInstance(I_C_Flatrate_DataEntry.class);
		dataEntry.setAD_Org_ID(orgId.getRepoId());
		dataEntry.setC_Flatrate_Term(currentTerm);
		dataEntry.setType(X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased);
		dataEntry.setM_Product_DataEntry_ID(product.getM_Product_ID());
		dataEntry.setC_Period(period);
		save(dataEntry);

		setupTaxAndActivity(product, dataEntry, currentTerm);

		flatrateBL.beforeCompleteDataEntry(dataEntry);
	}

	private void setupTaxAndActivity(final I_M_Product product, final I_C_Flatrate_DataEntry dataEntry, final I_C_Flatrate_Term currentTerm)
	{
		Services.registerService(IProductActivityProvider.class, productActivityProvider);
		Services.registerService(ITaxBL.class, taxBL);

		Mockito.when(
						productActivityProvider.getActivityForAcct(
								clientId,
								orgId,
								ProductId.ofRepoId(product.getM_Product_ID())))
				.thenReturn(activityId);

		Mockito.when(taxBL.getTaxNotNull(
						any(I_C_Flatrate_Term.class),
						any(TaxCategoryId.class),
						anyInt(),
						any(Timestamp.class),
						any(OrgId.class),
						any(WarehouseId.class),
						any(BPartnerLocationAndCaptureId.class),
						any(SOTrx.class),
						any(VatCodeId.class)))
				.thenReturn(TaxId.ofRepoId(3));
	}

	@Test
	public void testUpdateNoticeDateAndEndDate_endsWithCalendarYear_CorrectData()
	{
		prepareForUpdateEndDate();

		Services.get(IFlatrateBL.class).updateNoticeDateAndEndDate(term);

		assertThat(term.getEndDate()).isEqualTo(day(2014, 12, 31));

		// term duration: 24 months
		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_MonatE);
		transition.setTermDuration(24);
		save(transition);

		Services.get(IFlatrateBL.class).updateNoticeDateAndEndDate(term);

		assertThat(term.getEndDate()).isEqualTo(day(2014, 12, 31));
	}

	@Test
	public void testUpdateNoticeDateAndEndDate_endsWithCalendarYear_IncorrectTermDuration()
	{
		prepareForUpdateEndDate();
		// term duration: 25 months
		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_MonatE);
		transition.setTermDuration(25);
		save(transition);

		assertThatThrownBy(() -> Services.get(IFlatrateBL.class).updateNoticeDateAndEndDate(term))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	public void testUpdateNoticeDateAndEndDate_endsWithCalendarYear_YearNotInCalendar()
	{
		prepareForUpdateEndDate();

		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_MonatE);
		transition.setTermDuration(48); // the year of now + 4 years is not in calendar
		save(transition);

		assertThatThrownBy(() -> Services.get(IFlatrateBL.class).updateNoticeDateAndEndDate(term))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	public void testUpdateNoticeDateAndEndDate_noEndsWithCalendarYear()
	{
		prepareForUpdateEndDate();

		transition.setEndsWithCalendarYear(false);

		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_MonatE);
		transition.setTermDuration(5);
		save(transition);

		Services.get(IFlatrateBL.class).updateNoticeDateAndEndDate(term);

		assertThat(term.getEndDate()).isEqualTo(day(2013, 8, 2));

		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_TagE);
		transition.setTermDuration(30);
		save(transition);

		Services.get(IFlatrateBL.class).updateNoticeDateAndEndDate(term);

		assertThat(term.getEndDate()).isEqualTo(day(2013, 4, 1));

		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_JahrE);
		transition.setTermDuration(2);
		save(transition);

		Services.get(IFlatrateBL.class).updateNoticeDateAndEndDate(term);

		assertThat(term.getEndDate()).isEqualTo(day(2015, 3, 2));

		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_WocheN);
		transition.setTermDuration(2);
		save(transition);

		Services.get(IFlatrateBL.class).updateNoticeDateAndEndDate(term);

		assertThat(term.getEndDate()).isEqualTo(day(2013, 3, 16));

	}

	private void prepareForUpdateEndDate()
	{
		final I_C_Calendar calendar = newInstance(I_C_Calendar.class);
		save(calendar);

		// first year
		final I_C_Year year1 = newInstance(I_C_Year.class);
		year1.setC_Calendar_ID(calendar.getC_Calendar_ID());
		save(year1);

		period1 = newInstance(I_C_Period.class);
		period1.setStartDate(day(2013, 1, 1));
		period1.setEndDate(day(2013, 5, 1));
		period1.setC_Year_ID(year1.getC_Year_ID());
		save(period1);

		period2 = newInstance(I_C_Period.class);
		period2.setStartDate(day(2013, 5, 2));
		period2.setEndDate(day(2013, 10, 1));
		period2.setC_Year_ID(year1.getC_Year_ID());
		save(period2);

		period3 = newInstance(I_C_Period.class);
		period3.setStartDate(day(2013, 10, 2));
		period3.setEndDate(day(2013, 12, 31));
		period3.setC_Year_ID(year1.getC_Year_ID());
		save(period3);

		// second year

		final I_C_Year year2 = newInstance(I_C_Year.class);
		year2.setC_Calendar_ID(calendar.getC_Calendar_ID());
		save(year2);

		period4 = newInstance(I_C_Period.class);
		period4.setStartDate(day(2014, 1, 1));
		period4.setEndDate(day(2014, 5, 1));
		period4.setC_Year_ID(year2.getC_Year_ID());
		save(period4);

		period5 = newInstance(I_C_Period.class);
		period5.setStartDate(day(2014, 5, 2));
		period5.setEndDate(day(2014, 10, 1));
		period5.setC_Year_ID(year2.getC_Year_ID());
		save(period5);

		period6 = newInstance(I_C_Period.class);
		period6.setStartDate(day(2014, 10, 2));
		period6.setEndDate(day(2014, 12, 31));
		period6.setC_Year_ID(year2.getC_Year_ID());
		save(period6);

		transition = newInstance(I_C_Flatrate_Transition.class);
		transition.setEndsWithCalendarYear(true);
		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_JahrE);
		transition.setTermDuration(2);
		transition.setTermOfNoticeUnit(X_C_Flatrate_Transition.TERMOFNOTICEUNIT_WocheN);
		transition.setTermOfNotice(2);
		transition.setC_Calendar_Contract(calendar);
		save(transition);

		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setC_Flatrate_Transition(transition);
		save(conditions);

		term = newInstance(I_C_Flatrate_Term.class);
		term.setStartDate(day(2013, 3, 3));
		term.setC_Flatrate_Conditions(conditions);
		save(term);
	}

	@Test
	public void testhasOverlappingTerms()
	{
		// calendar
		final I_C_Calendar calendar = newInstance(I_C_Calendar.class);
		save(calendar);

		// first year
		final I_C_Year year1 = newInstance(I_C_Year.class);
		year1.setC_Calendar_ID(calendar.getC_Calendar_ID());
		save(year1);

		// create transition
		transition = newInstance(I_C_Flatrate_Transition.class);
		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_MonatE);
		transition.setTermDuration(1);
		transition.setDeliveryIntervalUnit(X_C_Flatrate_Transition.DELIVERYINTERVALUNIT_WocheN);
		transition.setDeliveryInterval(1);
		transition.setTermOfNoticeUnit(X_C_Flatrate_Transition.TERMOFNOTICEUNIT_WocheN);
		transition.setTermOfNotice(1);
		transition.setC_Calendar_Contract(calendar);
		save(transition);

		// product category is mandatofy in matchings
		productCategory = newInstance(I_M_Product_Category.class);
		save(productCategory);

		// product
		product = newInstance(I_M_Product.class);
		product.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());
		save(product);

		// first conditions
		conditions1 = newInstance(I_C_Flatrate_Conditions.class);
		conditions1.setC_Flatrate_Transition(transition);
		save(conditions1);

		// matchings linked to the first conditions
		final I_C_Flatrate_Matching matching1 = newInstance(I_C_Flatrate_Matching.class);
		matching1.setSeqNo(10);
		matching1.setM_Product_Category_Matching_ID(productCategory.getM_Product_Category_ID());
		matching1.setC_Flatrate_Transition(transition);
		matching1.setC_Flatrate_Conditions(conditions1);
		save(matching1);

		// second conditions
		conditions2 = newInstance(I_C_Flatrate_Conditions.class);
		conditions2.setC_Flatrate_Transition(transition);
		save(conditions2);

		// matchings linked with the second conditions
		final I_C_Flatrate_Matching matching2 = newInstance(I_C_Flatrate_Matching.class);
		matching2.setSeqNo(10);
		matching2.setM_Product_Category_Matching_ID(productCategory.getM_Product_Category_ID());
		matching2.setM_Product_ID(product.getM_Product_ID());
		matching2.setC_Flatrate_Transition(transition);
		matching2.setC_Flatrate_Conditions(conditions2);
		save(matching2);

		// terms must be for the same partner
		partner = newInstance(I_C_BPartner.class);
		partner.setName("Partner1");
		save(partner);

		// terms must be for the same flatrate data
		final I_C_Flatrate_Data flatrateData = newInstance(I_C_Flatrate_Data.class);
		flatrateData.setC_BPartner_ID(partner.getC_BPartner_ID());
		save(flatrateData);

		// first term: first conditions, March 15 - April 14
		term = newInstance(I_C_Flatrate_Term.class);
		term.setBill_BPartner_ID(partner.getC_BPartner_ID());
		term.setStartDate(day(2017, 3, 15));
		term.setEndDate(day(2017, 4, 14));
		term.setC_Flatrate_Conditions(conditions1);
		term.setType_Conditions(TypeConditions.CALL_ORDER.getCode());
		term.setC_Flatrate_Data(flatrateData);
		term.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Completed);
		save(term);

		// second term: second conditions, March 14 - April 13 => Overlapping: March 15, April 14
		overlappingTerm = newInstance(I_C_Flatrate_Term.class);
		overlappingTerm.setBill_BPartner_ID(partner.getC_BPartner_ID());
		overlappingTerm.setStartDate(day(2017, 3, 14));
		overlappingTerm.setEndDate(day(2017, 4, 13));
		overlappingTerm.setC_Flatrate_Conditions(conditions2);
		overlappingTerm.setType_Conditions(TypeConditions.CALL_ORDER.getCode());
		overlappingTerm.setC_Flatrate_Data(flatrateData);
		overlappingTerm.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Completed);
		save(overlappingTerm);

		boolean hasOverlappingTerms = Services.get(IFlatrateBL.class).hasOverlappingTerms(term);
		assertThat(hasOverlappingTerms).isTrue();

		// test the other way around
		hasOverlappingTerms = Services.get(IFlatrateBL.class).hasOverlappingTerms(overlappingTerm);
		assertThat(hasOverlappingTerms).isTrue();
	}
}
