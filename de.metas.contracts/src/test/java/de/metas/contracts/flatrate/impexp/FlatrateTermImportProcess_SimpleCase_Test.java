package de.metas.contracts.flatrate.impexp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.contracts.impl.AbstractFlatrateTermTest;
import de.metas.contracts.impl.FlatrateTermDataFactory;
import de.metas.contracts.inoutcandidate.ShipmentScheduleSubscriptionReferenceProvider;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_I_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.inout.invoicecandidate.InOutLinesWithMissingInvoiceCandidate;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.GroupTemplateRepository;
import de.metas.order.compensationGroup.OrderGroupCompensationChangesHandler;
import de.metas.order.compensationGroup.OrderGroupRepository;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class,

		// note: we need to bring in theses classes because of setupModuleInterceptors_Contracts_Full()
		InOutLinesWithMissingInvoiceCandidate.class,
		ShipmentScheduleSubscriptionReferenceProvider.class,
		OrderGroupRepository.class,
		OrderGroupCompensationChangesHandler.class,
		GroupTemplateRepository.class,
		GroupCompensationLineCreateRequestFactory.class })
public class FlatrateTermImportProcess_SimpleCase_Test extends AbstractFlatrateTermTest
{
	private final transient IInvoiceCandDAO iinvoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final transient IShipmentScheduleHandlerBL inOutCandHandlerBL = Services.get(IShipmentScheduleHandlerBL.class);

	@Before
	public void before()
	{
		helper.setupModuleInterceptors_Contracts_Full();
	}

	@Test
	public void testImportActiveFlatrateTerms()
	{
		final Timestamp startDate = TimeUtil.parseTimestamp("2017-09-10");
		final Timestamp masterEndDate = TimeUtil.addDays(startDate, 90);
		final Timestamp expectedEndDate = TimeUtil.parseTimestamp("2018-09-09");

		final int bpartnerId = prepareBPartner();

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
				.extensionType(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne)
				.build();

		final I_I_Flatrate_Term iflatrateTerm = IFlatrateTermFactory.builder()
				.ctx(helper.getCtx())
				.bartnerId(bpartnerId)
				.dropShipBPartnerId(bpartnerId)
				.productId(product.getM_Product_ID())
				.flatrateConditionsId(conditions.getC_Flatrate_Conditions_ID())
				.price(BigDecimal.valueOf(10))
				.qty(BigDecimal.valueOf(1))
				.startDate(startDate)
				.masterStartDate(startDate)
				.masterEndDate(masterEndDate)
				.build();

		final FlatrateTermImportProcess importProcess = new FlatrateTermImportProcess();
		importProcess.setCtx(helper.getCtx());
		importProcess.importRecord(new Mutable<>(), iflatrateTerm);

		final I_C_Flatrate_Term flatrateTerm = iflatrateTerm.getC_Flatrate_Term();
		assertThat(flatrateTerm).isNotNull();

		assertPartnerData(iflatrateTerm);

		assertThat(flatrateTerm.getStartDate()).isEqualTo(iflatrateTerm.getStartDate());
		assertThat(flatrateTerm.getEndDate()).isEqualTo(expectedEndDate);
		assertThat(flatrateTerm.getMasterStartDate()).isEqualTo(startDate);
		assertThat(flatrateTerm.getMasterEndDate()).isEqualTo(masterEndDate);
		assertThat(flatrateTerm.isCloseInvoiceCandidate()).isFalse();

		assertInvoiceCandidate(flatrateTerm);

		assertShipmentSchedules(flatrateTerm, true);
	}

	@Test
	public void testImportActiveFlatrateTermsWithTaxIncluded()
	{
		final Timestamp startDate = TimeUtil.parseTimestamp("2017-09-10");
		final Timestamp masterEndDate = TimeUtil.addDays(startDate, 90);
		final Timestamp expectedEndDate = TimeUtil.parseTimestamp("2018-09-09");

		final int bpartnerId = prepareBPartner();

		final FlatrateTermDataFactory.ProductAndPricingSystem productAndPricingSystem = FlatrateTermDataFactory.productAndPricingNew()
				.productValue("02")
				.productName("testProduct")
				.country(getCountry())
				.isTaxInclcuded(true)
				.validFrom(startDate)
				.build();

		final I_M_Product product = productAndPricingSystem.getProduct();

		FlatrateTermDataFactory.productAcctNew()
				.product(product)
				.acctSchema(getAcctSchema())
				.build();

		final I_C_Flatrate_Conditions conditions = FlatrateTermDataFactory.flatrateConditionsNew()
				.name("Abos")
				.calendar(getCalendar())
				.pricingSystem(productAndPricingSystem.getPricingSystem())
				.invoiceRule(X_C_Flatrate_Conditions.INVOICERULE_Sofort)
				.typeConditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription)
				.extensionType(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne)
				.build();

		final I_I_Flatrate_Term iflatrateTerm = IFlatrateTermFactory.builder()
				.ctx(helper.getCtx())
				.bartnerId(bpartnerId)
				.dropShipBPartnerId(bpartnerId)
				.productId(product.getM_Product_ID())
				.flatrateConditionsId(conditions.getC_Flatrate_Conditions_ID())
				.price(BigDecimal.valueOf(10))
				.qty(BigDecimal.valueOf(1))
				.startDate(startDate)
				.masterStartDate(startDate)
				.masterEndDate(masterEndDate)
				.build();

		final FlatrateTermImportProcess importProcess = new FlatrateTermImportProcess();
		importProcess.setCtx(helper.getCtx());
		importProcess.importRecord(new Mutable<>(), iflatrateTerm);

		final I_C_Flatrate_Term flatrateTerm = iflatrateTerm.getC_Flatrate_Term();
		assertThat(flatrateTerm).isNotNull();

		assertPartnerData(iflatrateTerm);

		assertThat(flatrateTerm.getStartDate()).isEqualTo(iflatrateTerm.getStartDate());
		assertThat(flatrateTerm.getEndDate()).isEqualTo(expectedEndDate);
		assertThat(flatrateTerm.getMasterStartDate()).isEqualTo(startDate);
		assertThat(flatrateTerm.getMasterEndDate()).isEqualTo(masterEndDate);
		assertThat(flatrateTerm.isCloseInvoiceCandidate()).isFalse();

		assertInvoiceCandidate(flatrateTerm);

		assertShipmentSchedules(flatrateTerm, true);
	}

	@Test
	public void testImportingInActiveFlatrateTerms()
	{
		final Timestamp startDate = TimeUtil.parseTimestamp("2017-03-10");
		final Timestamp endDate = TimeUtil.parseTimestamp("2017-09-09");
		final Timestamp masterEndDate = TimeUtil.addDays(startDate, 90);

		final int bpartnerId = prepareBPartner();

		final FlatrateTermDataFactory.ProductAndPricingSystem productAndPricingSystem = FlatrateTermDataFactory.productAndPricingNew()
				.productValue("01")
				.productName("testProduct")
				.country(getCountry())
				.isTaxInclcuded(false)
				.validFrom(startDate)
				.build();

		final I_M_Product product = productAndPricingSystem.getProduct();

		final I_C_Flatrate_Conditions conditions = FlatrateTermDataFactory.flatrateConditionsNew()
				.name("Abo")
				.calendar(getCalendar())
				.pricingSystem(productAndPricingSystem.getPricingSystem())
				.invoiceRule(X_C_Flatrate_Conditions.INVOICERULE_Sofort)
				.typeConditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription)
				.extensionType(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne)
				.build();

		final I_I_Flatrate_Term iflatrateTerm = IFlatrateTermFactory.builder()
				.ctx(helper.getCtx())
				.bartnerId(bpartnerId)
				.dropShipBPartnerId(bpartnerId)
				.productId(product.getM_Product_ID())
				.flatrateConditionsId(conditions.getC_Flatrate_Conditions_ID())
				.price(BigDecimal.valueOf(10))
				.qty(BigDecimal.valueOf(1))
				.startDate(startDate)
				.endDate(endDate)
				.masterStartDate(startDate)
				.masterEndDate(masterEndDate)
				.build();

		final FlatrateTermImportProcess importProcess = new FlatrateTermImportProcess();
		importProcess.setCtx(helper.getCtx());
		importProcess.importRecord(new Mutable<>(), iflatrateTerm);

		final I_C_Flatrate_Term flatrateTerm = iflatrateTerm.getC_Flatrate_Term();
		assertThat(flatrateTerm).isNotNull();

		assertPartnerData(iflatrateTerm);

		assertThat(flatrateTerm.getStartDate()).isEqualTo(iflatrateTerm.getStartDate());
		assertThat(flatrateTerm.getEndDate()).isEqualTo(endDate);
		assertThat(flatrateTerm.getMasterStartDate()).isEqualTo(startDate);
		assertThat(flatrateTerm.getMasterEndDate()).isEqualTo(masterEndDate);
		assertThat(flatrateTerm.getDocAction()).isEqualTo(X_C_Flatrate_Term.DOCACTION_None);
		assertThat(flatrateTerm.getContractStatus()).isEqualTo(X_C_Flatrate_Term.CONTRACTSTATUS_Quit);

		final List<I_C_Invoice_Candidate> candidates = createInvoiceCandidates(flatrateTerm);
		assertThat(candidates).hasSize(0);

		assertShipmentSchedules(flatrateTerm, false);
	}

	private void assertPartnerData(final I_I_Flatrate_Term iflatrateTerm)
	{
		final I_C_Flatrate_Term flatrateTerm = iflatrateTerm.getC_Flatrate_Term();
		assertThat(flatrateTerm.getBill_BPartner()).isNotNull();
		assertThat(flatrateTerm.getBill_BPartner()).isEqualToComparingFieldByField(iflatrateTerm.getDropShip_BPartner());
		assertThat(flatrateTerm.getBill_Location()).isNotNull();
		assertThat(flatrateTerm.getDropShip_BPartner()).isNotNull();
		assertThat(flatrateTerm.getDropShip_BPartner_ID()).isEqualByComparingTo(iflatrateTerm.getDropShip_BPartner_ID());
		assertThat(flatrateTerm.getDropShip_Location()).isNotNull();
		assertThat(flatrateTerm.getBill_User()).isNotNull();
		assertThat(flatrateTerm.getDropShip_User()).isNotNull();
	}

	private void assertInvoiceCandidate(final I_C_Flatrate_Term flatrateTerm)
	{
		final List<I_C_Invoice_Candidate> candidates = createInvoiceCandidates(flatrateTerm);

		assertThat(candidates).hasSize(1);

		final I_C_Invoice_Candidate invoiceCandidate = candidates.get(0);

		assertThat(invoiceCandidate.getM_Product_ID()).isEqualTo(flatrateTerm.getM_Product_ID());
		assertThat(invoiceCandidate.getRecord_ID()).isEqualByComparingTo(flatrateTerm.getC_Flatrate_Term_ID());
		assertThat(invoiceCandidate.getPriceActual()).isEqualByComparingTo(flatrateTerm.getPriceActual());
		assertThat(invoiceCandidate.isTaxIncluded()).isEqualTo(flatrateTerm.isTaxIncluded());

		final List<I_C_Invoice_Candidate> candsForTerm = iinvoiceCandDAO.retrieveReferencing(flatrateTerm);
		assertThat(candsForTerm.size(), equalTo(1));
	}

	private void assertShipmentSchedules(final I_C_Flatrate_Term flatrateTerm, final boolean isActiveFT)
	{
		final List<I_M_ShipmentSchedule> createdShipmentCands = createMissingShipmentSchedules(flatrateTerm);
		if (isActiveFT)
		{
			assertThat(createdShipmentCands).hasSize(1);
		}
		else
		{
			assertThat(createdShipmentCands).hasSize(0);
		}

	}

	private List<I_M_ShipmentSchedule> createMissingShipmentSchedules(final I_C_Flatrate_Term flatrateTerm)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(flatrateTerm);
		return inOutCandHandlerBL.createMissingCandidates(ctx, ITrx.TRXNAME_ThreadInherited);
	}

}
