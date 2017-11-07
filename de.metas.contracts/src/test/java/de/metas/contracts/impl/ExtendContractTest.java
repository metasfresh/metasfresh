package de.metas.contracts.impl;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.StartupListener;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.inoutcandidate.ShipmentScheduleOrderDocForSubscriptionLine;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.inout.invoicecandidate.InOutLinesWithMissingInvoiceCandidate;
import de.metas.interfaces.I_C_BPartner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, InOutLinesWithMissingInvoiceCandidate.class, ShipmentScheduleOrderDocForSubscriptionLine.class })
public class ExtendContractTest extends AbstractFlatrateTermTest
{

	@Test
	public void extendContract_test()
	{

		final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

		final Timestamp startDate = TimeUtil.parseTimestamp("2017-09-10");

		final int bpartnerId = prepareBPartner();
		final I_C_BPartner bparner  = InterfaceWrapperHelper.create(helper.getCtx(), bpartnerId, I_C_BPartner.class, ITrx.TRXNAME_None);

		final FlatrateTermDataFactory.ProductAndPricingSystem productAndPricingSystem = FlatrateTermDataFactory.productAndPricingNew()
				.productValue("01")
				.productName("testProduct")
				.country(getCountry())
				.isTaxInclcuded(false)
				.validFrom(startDate)
				.build();

		final I_M_Product product = productAndPricingSystem.getProduct();

		FlatrateTermDataFactory.productAcctNew()
				.product(product)
				.acctSchema(getAcctSchema())
				.build();

		final I_C_Flatrate_Conditions conditions = FlatrateTermDataFactory.flatrateConditionsNew()
				.name("Abo")
				.calendar(getCalendar())
				.pricingSystem(productAndPricingSystem.getPricingSystem())
				.invoiceRule(X_C_Flatrate_Conditions.INVOICERULE_Sofort)
				.typeConditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription)
				.build();

		
		final I_C_Flatrate_Term contract = flatrateBL.createTerm(
				helper.getContextProvider(),
				bparner,
				conditions,
				startDate,
				null,
				product,
				false);
		
		contract.setPriceActual(BigDecimal.valueOf(2));
		contract.setPlannedQtyPerUnit(BigDecimal.ONE);
		contract.setMasterStartDate(startDate);
		contract.setIsTaxIncluded(true);
		save(contract);
		
		flatrateBL.extendContract(contract, true, true, null, null);

	}
}
