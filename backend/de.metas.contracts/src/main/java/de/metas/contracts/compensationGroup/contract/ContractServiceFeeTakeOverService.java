package de.metas.contracts.compensationGroup.contract;

import de.metas.bpartner.BPartnerId;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyService;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

/**
 * Resolves the customer's invoice-processing service-company fee percent.
 */
@Service
@RequiredArgsConstructor
public class ContractServiceFeeTakeOverService
{
	@NonNull private final InvoiceProcessingServiceCompanyService invoiceProcessingServiceCompanyService;

	/**
	 * @return the fee percent of the active assignment valid at {@code soDate}; {@link Percent#ZERO} if there is none. Never {@code null}.
	 * The doc type is deliberately {@code null}: only the assignment without a doc type is considered.
	 */
	@NonNull
	public Percent resolveServiceFeePercent(@NonNull final BPartnerId invoicePartnerId, @NonNull final ZonedDateTime soDate)
	{
		return resolveServiceFee(invoicePartnerId, soDate).getPercent();
	}

	/**
	 * @return the active assignment's fee (percent + the service-fee product that labels it) valid at {@code soDate};
	 * {@link ContractServiceFee#ZERO} if there is none. Never {@code null}.
	 * The doc type is deliberately {@code null}: only the assignment without a doc type is considered.
	 */
	@NonNull
	public ContractServiceFee resolveServiceFee(@NonNull final BPartnerId invoicePartnerId, @NonNull final ZonedDateTime soDate)
	{
		// doc type deliberately null: resolve the customer's doc-type-null default fee assignment (see method Javadoc)
		return invoiceProcessingServiceCompanyService.getByCustomerId(invoicePartnerId, soDate)
				.flatMap(config -> config.getFeePercentageOfGrandTotalByBpartner(invoicePartnerId, null)
						.map(percent -> ContractServiceFee.of(percent, config.getServiceFeeProductId())))
				.orElse(ContractServiceFee.ZERO);
	}
}
