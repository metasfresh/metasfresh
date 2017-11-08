package de.metas.contracts.impl;

import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.StartupListener;
import de.metas.adempiere.model.I_AD_User;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.impl.FlatrateTermDataFactory.ProductAndPricingSystem;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.inout.invoicecandidate.InOutLinesWithMissingInvoiceCandidate;
import lombok.NonNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, InOutLinesWithMissingInvoiceCandidate.class})
public class ExtendContractTest extends AbstractFlatrateTermTest
{

	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	final private static Timestamp startDate = TimeUtil.parseTimestamp("2017-09-10");

	@Before
	public void before()
	{
		helper.setupModuleInterceptors_Contracts_Full();
	}

	@Test
	public void extendContractWithAutoRenewOnYes_test()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(true);

		flatrateBL.extendContract(contract, true, true, null, null);

		assertFlatrateTerm(contract);
		assertPartnerData(contract);
	}

	@Test
	public void extendContractWithAutoRenewOnNo_test()
	{
		final I_C_Flatrate_Term contract = prepareContractForTest(false);

		flatrateBL.extendContract(contract, true, true, null, null);
		save(contract);

		assertFlatrateTerm(contract);
		assertPartnerData(contract);
	}

	private I_C_Flatrate_Term prepareContractForTest(final boolean isAutoRenew)
	{
		prepareBPartner();
		final ProductAndPricingSystem productAndPricingSystem = createProductAndPricingSystem();
		createProductAcct(productAndPricingSystem);
		final I_C_Flatrate_Conditions conditions = createFlatrateConditions(productAndPricingSystem, isAutoRenew);
		final I_C_Flatrate_Term contract = createFlatrateTerm(conditions, productAndPricingSystem);
		return contract;
	}

	private ProductAndPricingSystem createProductAndPricingSystem()
	{
		return FlatrateTermDataFactory.productAndPricingNew()
				.productValue("01")
				.productName("testProduct")
				.country(getCountry())
				.isTaxInclcuded(false)
				.validFrom(startDate)
				.build();
	}

	private void createProductAcct(@NonNull final ProductAndPricingSystem productAndPricingSystem)
	{
		final I_M_Product product = productAndPricingSystem.getProduct();

		FlatrateTermDataFactory.productAcctNew()
				.product(product)
				.acctSchema(getAcctSchema())
				.build();
	}

	private I_C_Flatrate_Conditions createFlatrateConditions(@NonNull final ProductAndPricingSystem productAndPricingSystem, final boolean isAutoRenew)
	{
		return FlatrateTermDataFactory.flatrateConditionsNew()
				.name("Abo")
				.calendar(getCalendar())
				.pricingSystem(productAndPricingSystem.getPricingSystem())
				.invoiceRule(X_C_Flatrate_Conditions.INVOICERULE_Sofort)
				.typeConditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription)
				.isAutoRenew(isAutoRenew)
				.build();
	}

	private I_C_Flatrate_Term createFlatrateTerm(@NonNull final I_C_Flatrate_Conditions conditions, @NonNull final ProductAndPricingSystem productAndPricingSystem)
	{
		final I_M_Product product = productAndPricingSystem.getProduct();

		final I_C_Flatrate_Term contract = flatrateBL.createTerm(
				helper.getContextProvider(),
				getBpartner(),
				conditions,
				startDate,
				null,
				product,
				false);

		final I_C_BPartner_Location bpLocation = getBpLocation();
		final I_AD_User user = getUser();

		contract.setBill_Location(bpLocation);
		contract.setBill_User(user);
		contract.setDropShip_BPartner(getBpartner());
		contract.setDropShip_Location(bpLocation);
		contract.setDropShip_User(user);
		contract.setPriceActual(BigDecimal.valueOf(2));
		contract.setPlannedQtyPerUnit(BigDecimal.ONE);
		contract.setMasterStartDate(startDate);
		contract.setM_Product(product);
		contract.setIsTaxIncluded(true);
		save(contract);

		return contract;
	}

	private void assertFlatrateTerm(@NonNull final I_C_Flatrate_Term currentflatrateTerm)
	{
		final I_C_Flatrate_Term nextflatrateTerm = currentflatrateTerm.getC_FlatrateTerm_Next();
		assertThat(nextflatrateTerm).isNotNull();
		assertThat(currentflatrateTerm.getC_Flatrate_Conditions()).isEqualTo(nextflatrateTerm.getC_Flatrate_Conditions());
		assertThat(currentflatrateTerm.getPlannedQtyPerUnit()).isEqualTo(nextflatrateTerm.getPlannedQtyPerUnit());

		final Timestamp startDateNewContract = TimeUtil.addDays(currentflatrateTerm.getEndDate(), 1);
		assertThat(nextflatrateTerm.getStartDate()).isEqualTo(startDateNewContract);

		assertThat(currentflatrateTerm.getMasterStartDate()).isEqualTo(nextflatrateTerm.getMasterStartDate());
		assertThat(currentflatrateTerm.getMasterEndDate()).isEqualTo(nextflatrateTerm.getMasterEndDate());
		assertThat(nextflatrateTerm.getMasterStartDate()).isNotNull();
		assertThat(currentflatrateTerm.isAutoRenew()).isEqualTo(nextflatrateTerm.isAutoRenew());
		if (currentflatrateTerm.isAutoRenew())
		{
			assertThat(currentflatrateTerm.getMasterEndDate()).isNull();
			assertThat(nextflatrateTerm.getMasterEndDate()).isNull();
		}
		else
		{
			assertThat(currentflatrateTerm.getMasterEndDate()).isNotNull();
			assertThat(nextflatrateTerm.getMasterEndDate()).isNotNull();

			final Timestamp expectedMasterEndDate = TimeUtil.addDays(TimeUtil.addYears(startDate, 2), -1);
			assertThat(currentflatrateTerm.getMasterEndDate()).isEqualTo(expectedMasterEndDate);
			assertThat(nextflatrateTerm.getMasterEndDate()).isEqualTo(expectedMasterEndDate);
		}
	}

	private void assertPartnerData(@NonNull final I_C_Flatrate_Term currentflatrateTerm)
	{
		final I_C_Flatrate_Term nextflatrateTerm = currentflatrateTerm.getC_FlatrateTerm_Next();

		assertThat(currentflatrateTerm.getBill_BPartner()).isEqualTo(nextflatrateTerm.getBill_BPartner());
		assertThat(currentflatrateTerm.getBill_Location()).isEqualTo(nextflatrateTerm.getBill_Location());
		assertThat(currentflatrateTerm.getBill_User()).isEqualTo(nextflatrateTerm.getBill_User());

		assertThat(currentflatrateTerm.getDropShip_BPartner()).isEqualTo(nextflatrateTerm.getDropShip_BPartner());
		assertThat(currentflatrateTerm.getDropShip_Location()).isEqualTo(nextflatrateTerm.getDropShip_Location());
		assertThat(currentflatrateTerm.getDropShip_User()).isEqualTo(nextflatrateTerm.getDropShip_User());
	}
}
