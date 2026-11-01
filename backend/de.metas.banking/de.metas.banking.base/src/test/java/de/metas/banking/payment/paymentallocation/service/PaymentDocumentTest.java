package de.metas.banking.payment.paymentallocation.service;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingContext;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentCurrencyContext;
import de.metas.payment.PaymentDirection;
import de.metas.payment.PaymentId;
import org.adempiere.service.ClientId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentDocumentTest
{
	private static final ZoneId ZONE = ZoneId.of("Europe/Berlin");

	@BeforeEach
	void beforeEach()
	{
		SystemTime.setFixedTimeSource(ZonedDateTime.of(2026, 10, 8, 12, 0, 0, 0, ZONE));
	}

	@AfterEach
	void afterEach()
	{
		SystemTime.resetTimeSource();
	}

	@Test
	void toInvoiceProcessingContext_usesPaymentBPartnerAndStartOfDateTrx()
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(102);
		final PaymentDocument paymentDocument = PaymentDocument.builder()
				.paymentId(PaymentId.ofRepoId(1))
				.bpartnerId(BPartnerId.ofRepoId(7))
				.paymentDirection(PaymentDirection.INBOUND)
				.openAmt(Money.of(100, currencyId))
				.amountToAllocate(Money.of(100, currencyId))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(ClientId.ofRepoId(1000000), OrgId.ofRepoId(1)))
				.dateTrx(LocalDate.parse("2026-03-15"))
				.dateAcct(LocalDate.parse("2026-03-20"))
				.paymentCurrencyContext(PaymentCurrencyContext.NONE)
				.build();

		final InvoiceProcessingContext context = paymentDocument.toInvoiceProcessingContext();

		assertThat(context).isEqualTo(InvoiceProcessingContext.of(
				BPartnerId.ofRepoId(7),
				LocalDate.parse("2026-03-15").atStartOfDay(SystemTime.zoneId())));
	}
}
