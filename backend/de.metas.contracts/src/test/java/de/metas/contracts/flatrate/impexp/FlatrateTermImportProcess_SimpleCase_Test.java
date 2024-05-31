package de.metas.contracts.flatrate.impexp;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.contracts.impl.AbstractFlatrateTermTest;
import de.metas.contracts.impl.FlatrateTermDataFactory;
import de.metas.contracts.inoutcandidate.ShipmentScheduleSubscriptionReferenceProvider;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_I_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.contracts.modular.ModularContractComputingMethodHandlerRegistry;
import de.metas.contracts.modular.ModularContractPriceRepository;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.computing.ComputingMethodService;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.log.status.ModularLogCreateStatusRepository;
import de.metas.contracts.modular.log.status.ModularLogCreateStatusService;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.contracts.modular.workpackage.ProcessModularLogsEnqueuer;
import de.metas.greeting.GreetingRepository;
import de.metas.impexp.format.ImportTableDescriptorRepository;
import de.metas.impexp.processing.DBFunctionsRepository;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.invoice.detail.InvoiceCandidateWithDetailsRepository;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.monitoring.adapter.NoopPerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

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

public class FlatrateTermImportProcess_SimpleCase_Test extends AbstractFlatrateTermTest
{
	private IInvoiceCandDAO iinvoiceCandDAO;
	private IShipmentScheduleHandlerBL inOutCandHandlerBL;

	@Override
	protected void afterInit()
	{
		helper.setupModuleInterceptors_Contracts_Full();

		//
		inOutCandHandlerBL = Services.get(IShipmentScheduleHandlerBL.class);

		iinvoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		final BPartnerBL bpartnerBL = new BPartnerBL(new UserRepository());
		Services.registerService(IBPartnerBL.class, bpartnerBL);

		SpringContextHolder.registerJUnitBean(new ShipmentScheduleSubscriptionReferenceProvider());
		SpringContextHolder.registerJUnitBean(new GreetingRepository());
		SpringContextHolder.registerJUnitBean(PerformanceMonitoringService.class, NoopPerformanceMonitoringService.INSTANCE);
		SpringContextHolder.registerJUnitBean(IBPartnerBL.class, bpartnerBL);

		SpringContextHolder.registerJUnitBean(new DBFunctionsRepository());
		SpringContextHolder.registerJUnitBean(new ImportTableDescriptorRepository());
		SpringContextHolder.registerJUnitBean(new ProductTaxCategoryService(new ProductTaxCategoryRepository()));

		SpringContextHolder.registerJUnitBean(new ModularContractLogDAO());
		SpringContextHolder.registerJUnitBean(new ModularContractSettingsDAO());

		SpringContextHolder.registerJUnitBean(new ModularContractComputingMethodHandlerRegistry(ImmutableList.of()));
		SpringContextHolder.registerJUnitBean(new ProcessModularLogsEnqueuer(new ModularLogCreateStatusService(new ModularLogCreateStatusRepository())));
		final ModularContractLogService contractLogService = new ModularContractLogService(new ModularContractLogDAO(), new InvoiceCandidateWithDetailsRepository());
		SpringContextHolder.registerJUnitBean(new ComputingMethodService(contractLogService));
		SpringContextHolder.registerJUnitBean(new ModularContractPriceRepository());
		SpringContextHolder.registerJUnitBean(new ModularContractService(new ModularContractComputingMethodHandlerRegistry(ImmutableList.of()),
				new ModularContractSettingsDAO(),
				new ProcessModularLogsEnqueuer(new ModularLogCreateStatusService(new ModularLogCreateStatusRepository())),
				new ComputingMethodService(contractLogService),
				new ModularContractPriceRepository()));
	}

	@Test
	public void testImportActiveFlatrateTerms()
	{
		final Timestamp startDate = TimeUtil.parseTimestamp("2017-09-10");
		final Timestamp masterEndDate = TimeUtil.addDays(startDate, 90);
		final Timestamp expectedEndDate = TimeUtil.parseTimestamp("2018-09-09");

		final int bpartnerId = prepareBPartner();

		final FlatrateTermDataFactory.ProductAndPricingSystem productAndPricingSystem = FlatrateTermDataFactory
				.productAndPricingNew()
				.productValue("01")
				.productName("testProduct")
				.currencyId(getCurrencyId())
				.country(getCountry())
				.isTaxInclcuded(false)
				.validFrom(startDate)
				.build();

		final I_M_Product product = productAndPricingSystem.getProduct();

		FlatrateTermDataFactory.productAcctNew()
				.product(product)
				.acctSchemaId(getAcctSchemaId())
				.build();

		final I_C_Flatrate_Conditions conditions = FlatrateTermDataFactory.flatrateConditionsNew()
				.name("Abo")
				.calendar(getCalendar())
				.pricingSystem(productAndPricingSystem.getPricingSystem())
				.invoiceRule(X_C_Flatrate_Conditions.INVOICERULE_Immediate)
				.typeConditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription)
				.onFlatrateTermExtend(X_C_Flatrate_Conditions.ONFLATRATETERMEXTEND_CalculatePrice)
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
		importProcess.importRecord(new Mutable<>(), iflatrateTerm, true /* isInsertOnly */);

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
				.currencyId(getCurrencyId())
				.country(getCountry())
				.isTaxInclcuded(true)
				.validFrom(startDate)
				.build();

		final I_M_Product product = productAndPricingSystem.getProduct();

		FlatrateTermDataFactory.productAcctNew()
				.product(product)
				.acctSchemaId(getAcctSchemaId())
				.build();

		final I_C_Flatrate_Conditions conditions = FlatrateTermDataFactory.flatrateConditionsNew()
				.name("Abos")
				.calendar(getCalendar())
				.pricingSystem(productAndPricingSystem.getPricingSystem())
				.invoiceRule(X_C_Flatrate_Conditions.INVOICERULE_Immediate)
				.typeConditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription)
				.onFlatrateTermExtend(X_C_Flatrate_Conditions.ONFLATRATETERMEXTEND_CalculatePrice)
				.extensionType(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne)
				.isCreateNoInvoice(false)
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
		importProcess.importRecord(new Mutable<>(), iflatrateTerm, true /* isInsertOnly */);

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

		final FlatrateTermDataFactory.ProductAndPricingSystem productAndPricingSystem = FlatrateTermDataFactory
				.productAndPricingNew()
				.productValue("01")
				.productName("testProduct")
				.currencyId(getCurrencyId())
				.country(getCountry())
				.isTaxInclcuded(false)
				.validFrom(startDate)
				.build();

		final I_M_Product product = productAndPricingSystem.getProduct();

		final I_C_Flatrate_Conditions conditions = FlatrateTermDataFactory.flatrateConditionsNew()
				.name("Abo")
				.calendar(getCalendar())
				.pricingSystem(productAndPricingSystem.getPricingSystem())
				.invoiceRule(X_C_Flatrate_Conditions.INVOICERULE_Immediate)
				.typeConditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription)
				.onFlatrateTermExtend(X_C_Flatrate_Conditions.ONFLATRATETERMEXTEND_CalculatePrice)
				.extensionType(X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne)
				.build();

		final I_I_Flatrate_Term iflatrateTerm = IFlatrateTermFactory.builder()
				.ctx(helper.getCtx())
				.bartnerId(bpartnerId)
				.dropShipBPartnerId(bpartnerId)
				.productId(product.getM_Product_ID())
				.flatrateConditionsId(conditions.getC_Flatrate_Conditions_ID())
				.price(BigDecimal.valueOf(10)) // which currency?!?
				.qty(BigDecimal.valueOf(1))
				.startDate(startDate)
				.endDate(endDate)
				.masterStartDate(startDate)
				.masterEndDate(masterEndDate)
				.build();

		SpringContextHolder.registerJUnitBean(new DBFunctionsRepository());
		SpringContextHolder.registerJUnitBean(new ImportTableDescriptorRepository());
		final FlatrateTermImportProcess importProcess = new FlatrateTermImportProcess();
		importProcess.setCtx(helper.getCtx());
		importProcess.importRecord(new Mutable<>(), iflatrateTerm, true /* isInsertOnly */);

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
		assertThat(candidates).isEmpty();

		assertShipmentSchedules(flatrateTerm, false);
	}

	private void assertPartnerData(final I_I_Flatrate_Term iflatrateTerm)
	{

		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

		final I_C_Flatrate_Term flatrateTerm = iflatrateTerm.getC_Flatrate_Term();

		final I_C_BPartner billBPartnerRecord = bpartnerDAO.getById(flatrateTerm.getBill_BPartner_ID());

		assertThat(billBPartnerRecord).isNotNull();
		assertThat(billBPartnerRecord).isEqualToComparingFieldByField(iflatrateTerm.getDropShip_BPartner());
		assertThat(flatrateTerm.getBill_Location_ID()).isGreaterThan(0);
		assertThat(flatrateTerm.getDropShip_BPartner_ID()).isGreaterThan(0);
		assertThat(flatrateTerm.getDropShip_BPartner_ID()).isEqualByComparingTo(iflatrateTerm.getDropShip_BPartner_ID());
		assertThat(flatrateTerm.getDropShip_Location_ID()).isGreaterThan(0);
		assertThat(flatrateTerm.getBill_User_ID()).isGreaterThan(0);
		assertThat(flatrateTerm.getDropShip_User_ID()).isGreaterThan(0);
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

		final List<I_C_Invoice_Candidate> candsForTerm = iinvoiceCandDAO.retrieveReferencing(TableRecordReference.of(flatrateTerm));
		assertThat(candsForTerm).hasSize(1);
	}

	private void assertShipmentSchedules(final I_C_Flatrate_Term flatrateTerm, final boolean isActiveFT)
	{
		final Set<ShipmentScheduleId> createdShipmentCandidateIds = inOutCandHandlerBL.createMissingCandidates(Env.getCtx());
		if (isActiveFT)
		{
			assertThat(createdShipmentCandidateIds).hasSize(1);
		}
		else
		{
			assertThat(createdShipmentCandidateIds).isEmpty();
		}

	}
}
