package de.metas.invoice.invoiceProcessingServiceCompany;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.product.ProductId;
import de.metas.util.lang.Percent;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class InvoiceProcessingServiceCompanyConfigMapTest
{
	private static final ZonedDateTime DATE = ZonedDateTime.parse("2020-06-01T00:00:00+02:00");
	private static final ZonedDateTime EARLIER = ZonedDateTime.parse("2020-01-01T00:00:00+01:00");
	private static final ZonedDateTime LATER = ZonedDateTime.parse("2020-03-01T00:00:00+01:00");

	private static final BPartnerId SERVICE_COMPANY_1 = BPartnerId.ofRepoId(1);
	private static final BPartnerId SERVICE_COMPANY_2 = BPartnerId.ofRepoId(2);
	private static final BPartnerId CUSTOMER_OF_COMPANY_1 = BPartnerId.ofRepoId(101);
	private static final BPartnerId CUSTOMER_OF_COMPANY_2 = BPartnerId.ofRepoId(102);

	private static InvoiceProcessingServiceCompanyConfig config(
			final BPartnerId serviceCompanyId,
			final ZonedDateTime validFrom,
			final BPartnerId customerId)
	{
		return InvoiceProcessingServiceCompanyConfig.builder()
				.serviceCompanyBPartnerId(serviceCompanyId)
				.serviceInvoiceDocTypeId(DocTypeId.ofRepoId(1))
				.serviceFeeProductId(ProductId.ofRepoId(1))
				.validFrom(validFrom)
				.bpartnerDetails(ImmutableMultimap.of(customerId, InvoiceProcessingServiceCompanyConfigBPartnerDetails.builder()
						.bpartnerId(customerId)
						.percent(Percent.of(2))
						.build()))
				.build();
	}

	@Test
	void getByCustomerIdAndDate_customerOfNonLastServiceCompany_isFound()
	{
		final InvoiceProcessingServiceCompanyConfig config1 = config(SERVICE_COMPANY_1, EARLIER, CUSTOMER_OF_COMPANY_1);
		final InvoiceProcessingServiceCompanyConfig config2 = config(SERVICE_COMPANY_2, EARLIER, CUSTOMER_OF_COMPANY_2);
		final InvoiceProcessingServiceCompanyConfigMap map = new InvoiceProcessingServiceCompanyConfigMap(ImmutableList.of(config1, config2));

		assertThat(map.getByCustomerIdAndDate(CUSTOMER_OF_COMPANY_1, DATE)).contains(config1);
		assertThat(map.getByCustomerIdAndDate(CUSTOMER_OF_COMPANY_2, DATE)).contains(config2);
	}

	@Test
	void getByCustomerIdAndDate_unknownCustomer_isEmpty()
	{
		final InvoiceProcessingServiceCompanyConfigMap map = new InvoiceProcessingServiceCompanyConfigMap(ImmutableList.of(
				config(SERVICE_COMPANY_1, EARLIER, CUSTOMER_OF_COMPANY_1),
				config(SERVICE_COMPANY_2, EARLIER, CUSTOMER_OF_COMPANY_2)));

		assertThat(map.getByCustomerIdAndDate(BPartnerId.ofRepoId(999), DATE)).isEmpty();
	}

	@Test
	void getByCustomerIdAndDate_newerConfigOfSameCompanyWithoutCustomer_supersedesOlderOne()
	{
		final InvoiceProcessingServiceCompanyConfigMap map = new InvoiceProcessingServiceCompanyConfigMap(ImmutableList.of(
				config(SERVICE_COMPANY_1, EARLIER, CUSTOMER_OF_COMPANY_1),
				config(SERVICE_COMPANY_1, LATER, BPartnerId.ofRepoId(103)),
				config(SERVICE_COMPANY_2, EARLIER, CUSTOMER_OF_COMPANY_2)));

		final Optional<InvoiceProcessingServiceCompanyConfig> result = map.getByCustomerIdAndDate(CUSTOMER_OF_COMPANY_1, DATE);
		assertThat(result).isEmpty();
	}
}
