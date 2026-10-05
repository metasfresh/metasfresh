package de.metas.contracts.compensationGroup.contract;

import de.metas.bpartner.BPartnerId;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyService;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

/**
 * Resolves the customer's payment-service fee (e.g. Markant 2,15 %) that is added onto the take-over discount line.
 */
@Service
@RequiredArgsConstructor
public class ContractServiceFeeTakeOverService
{
	@NonNull private final InvoiceProcessingServiceCompanyService invoiceProcessingServiceCompanyService;

	/**
	 * @return the fee percent of the active assignment valid at {@code soDate}; {@link Percent#ZERO} if there is none. Never {@code null}.
	 * The doc type is deliberately {@code null}: the config reconciles against the doc-type-null default assignment.
	 */
	@NonNull
	public Percent resolveServiceFeePercent(@NonNull final BPartnerId invoicePartnerId, @NonNull final ZonedDateTime soDate)
	{
		return invoiceProcessingServiceCompanyService.getByCustomerId(invoicePartnerId, soDate)
				.map(config -> config.getFeePercentageOfGrandTotalByBpartner(invoicePartnerId, null).orElse(Percent.ZERO))
				.orElse(Percent.ZERO);
	}
}
