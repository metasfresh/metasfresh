package de.metas.invoicecandidate.process.params;

import de.metas.forex.ForexContractId;
import de.metas.forex.process.utils.ForexContractParameters;
import de.metas.money.CurrencyId;
import org.adempiere.util.api.Params;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

class InvoicingParamsTest
{
	@Test
	void standardCase_toMap_fromParams()
	{
		final InvoicingParams invoicingParams = InvoicingParams.builder()
				.onlyApprovedForInvoicing(true)
				.consolidateApprovedICs(true)
				.ignoreInvoiceSchedule(true)
				//.storeInvoicesInResult(true) // don't set it because this field is not converted to params
				//.assumeOneInvoice(true)  // don't set it because this field is not converted to params
				.dateInvoiced(LocalDate.parse("2023-01-30"))
				.dateAcct(LocalDate.parse("2023-01-31"))
				.poReference("somePOReference")
				.check_NetAmtToInvoice(new BigDecimal("123.4567"))
				.updateLocationAndContactForInvoice(true)
				.completeInvoices(false) // use false because by default is true
				.forexContractParameters(ForexContractParameters.builder()
						.isFEC(true)
						.orderCurrencyId(CurrencyId.ofRepoId(1))
						.forexContractId(ForexContractId.ofRepoId(2))
						.fromCurrencyId(CurrencyId.ofRepoId(3))
						.toCurrencyId(CurrencyId.ofRepoId(4))
						.currencyRate(new BigDecimal("1.2345"))
						.build())
				.build();

		//noinspection unchecked
		final Map<String, Object> map = (Map<String, Object>)invoicingParams.toMap();

		final InvoicingParams invoicingParams2 = InvoicingParams.ofParams(Params.ofMap(map));

		Assertions.assertThat(invoicingParams2).usingRecursiveComparison().isEqualTo(invoicingParams);
		Assertions.assertThat(invoicingParams2).isEqualTo(invoicingParams);
	}
}
