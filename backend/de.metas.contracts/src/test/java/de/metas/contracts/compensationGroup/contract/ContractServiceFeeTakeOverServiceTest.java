package de.metas.contracts.compensationGroup.contract;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocTypeId;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyConfigRepository;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyService;
import de.metas.money.MoneyService;
import de.metas.util.lang.Percent;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_InvoiceProcessingServiceCompany;
import org.compiere.model.I_InvoiceProcessingServiceCompany_BPartnerAssignment;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class ContractServiceFeeTakeOverServiceTest
{
	private static final ZoneId ZONE = ZoneId.of("UTC");
	private static final BPartnerId CUSTOMER_ID = BPartnerId.ofRepoId(2);
	private static final BPartnerId OTHER_CUSTOMER_ID = BPartnerId.ofRepoId(3);
	private static final ZonedDateTime SO_DATE = LocalDate.parse("2026-05-10").atStartOfDay(ZONE);

	private ContractServiceFeeTakeOverService service;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final CurrencyRepository currencyRepo = new CurrencyRepository();
		SpringContextHolder.registerJUnitBean(currencyRepo);

		service = new ContractServiceFeeTakeOverService(new InvoiceProcessingServiceCompanyService(
				new InvoiceProcessingServiceCompanyConfigRepository(),
				new MoneyService(currencyRepo)));
	}

	@Test
	void activeAssignmentValidAtDate_returnsItsPercent()
	{
		createConfig(CUSTOMER_ID, "2.60", true, LocalDate.parse("2026-01-01"));

		assertThat(service.resolveServiceFeePercent(CUSTOMER_ID, SO_DATE)).isEqualTo(Percent.of(new BigDecimal("2.60")));
	}

	@Test
	void inactiveAssignment_returnsZero()
	{
		createConfig(CUSTOMER_ID, "2.60", false, LocalDate.parse("2026-01-01"));

		assertThat(service.resolveServiceFeePercent(CUSTOMER_ID, SO_DATE)).isEqualTo(Percent.ZERO);
	}

	@Test
	void configValidFromAfterDate_returnsZero()
	{
		createConfig(CUSTOMER_ID, "2.60", true, LocalDate.parse("2026-06-01"));

		assertThat(service.resolveServiceFeePercent(CUSTOMER_ID, SO_DATE)).isEqualTo(Percent.ZERO);
	}

	@Test
	void noAssignmentForCustomer_returnsZero()
	{
		assertThat(service.resolveServiceFeePercent(CUSTOMER_ID, SO_DATE)).isEqualTo(Percent.ZERO);
	}

	@Test
	void usesTheInvoicePartnerPassedIn()
	{
		// the lookup picks the latest valid config, so both customers must hang on the same config
		final I_InvoiceProcessingServiceCompany config = createConfigRecord(LocalDate.parse("2026-01-01"));
		createAssignment(config, OTHER_CUSTOMER_ID, "1.00", true);
		createAssignment(config, CUSTOMER_ID, "2.60", true);

		assertThat(service.resolveServiceFeePercent(CUSTOMER_ID, SO_DATE)).isEqualTo(Percent.of(new BigDecimal("2.60")));
		assertThat(service.resolveServiceFeePercent(OTHER_CUSTOMER_ID, SO_DATE)).isEqualTo(Percent.of(new BigDecimal("1.00")));
	}

	@Test
	void onlyDocTypeScopedAssignment_returnsZero()
	{
		// the take-over resolves the doc-type-null default; an assignment scoped to a doc type must not match
		final I_InvoiceProcessingServiceCompany config = createConfigRecord(LocalDate.parse("2026-01-01"));
		createAssignment(config, CUSTOMER_ID, "2.60", true, DocTypeId.ofRepoId(444));

		assertThat(service.resolveServiceFeePercent(CUSTOMER_ID, SO_DATE)).isEqualTo(Percent.ZERO);
	}

	@Test
	void docTypeScopedAndNullDocTypeAssignments_returnsTheNullDocTypePercent()
	{
		// a doc-type-scoped row must not shadow the doc-type-null default the take-over reconciles against
		final I_InvoiceProcessingServiceCompany config = createConfigRecord(LocalDate.parse("2026-01-01"));
		createAssignment(config, CUSTOMER_ID, "9.99", true, DocTypeId.ofRepoId(444));
		createAssignment(config, CUSTOMER_ID, "2.60", true, null);

		assertThat(service.resolveServiceFeePercent(CUSTOMER_ID, SO_DATE)).isEqualTo(Percent.of(new BigDecimal("2.60")));
	}

	private static void createConfig(final BPartnerId customerId, final String percent, final boolean assignmentActive, final LocalDate validFrom)
	{
		final I_InvoiceProcessingServiceCompany config = createConfigRecord(validFrom);
		createAssignment(config, customerId, percent, assignmentActive);
	}

	private static I_InvoiceProcessingServiceCompany createConfigRecord(final LocalDate validFrom)
	{
		final I_InvoiceProcessingServiceCompany config = newInstance(I_InvoiceProcessingServiceCompany.class);
		config.setIsActive(true);
		config.setServiceCompany_BPartner_ID(111);
		config.setServiceInvoice_DocType_ID(222);
		config.setServiceFee_Product_ID(333);
		config.setValidFrom(TimeUtil.asTimestamp(validFrom.atStartOfDay(ZONE)));
		saveRecord(config);
		return config;
	}

	private static void createAssignment(final I_InvoiceProcessingServiceCompany config, final BPartnerId customerId, final String percent, final boolean active)
	{
		createAssignment(config, customerId, percent, active, null);
	}

	private static void createAssignment(final I_InvoiceProcessingServiceCompany config, final BPartnerId customerId, final String percent, final boolean active, final DocTypeId docTypeId)
	{
		final I_InvoiceProcessingServiceCompany_BPartnerAssignment assignment = newInstance(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class);
		assignment.setIsActive(active);
		assignment.setInvoiceProcessingServiceCompany_ID(config.getInvoiceProcessingServiceCompany_ID());
		assignment.setC_BPartner_ID(customerId.getRepoId());
		assignment.setFeePercentageOfGrandTotal(new BigDecimal(percent));
		if (docTypeId != null)
		{
			assignment.setC_DocType_ID(docTypeId.getRepoId());
		}
		saveRecord(assignment);
	}
}
