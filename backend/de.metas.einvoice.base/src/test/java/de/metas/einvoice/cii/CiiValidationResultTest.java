package de.metas.einvoice.cii;

import de.metas.einvoice.cii.CiiValidationResult.FailedAssertion;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CiiValidationResultTest
{
	@Test
	void getFatalAndErrorDetails_includesRuleIdMessageLocation_excludesWarning_nullSafe()
	{
		final CiiValidationResult result = CiiValidationResult.builder()
				.failedAssertion(FailedAssertion.builder()
						.ruleId("BR-DE-15")
						.message("Buyer reference missing")
						.location("/rsm:CrossIndustryInvoice/Agreement")
						.severity("fatal_error")
						.build())
				.failedAssertion(FailedAssertion.builder()
						.ruleId("BR-6")
						.message("Seller name missing")
						.location("/rsm:CrossIndustryInvoice/Seller")
						.severity("error")
						.build())
				.failedAssertion(FailedAssertion.builder()
						.ruleId("BR-W-1")
						.message("Just a warning text")
						.location("/warning/location")
						.severity("warning")
						.build())
				.failedAssertion(FailedAssertion.builder()
						.message("Message only")
						.severity("fatal_error")
						.build())
				.build();

		final String details = result.getFatalAndErrorDetails();

		assertThat(details)
				.contains("BR-DE-15 — Buyer reference missing (/rsm:CrossIndustryInvoice/Agreement)")
				.contains("BR-6 — Seller name missing (/rsm:CrossIndustryInvoice/Seller)")
				.contains("Message only")
				.doesNotContain("BR-W-1")
				.doesNotContain("Just a warning text")
				.doesNotContain("/warning/location")
				.doesNotContain("null");
		assertThat(details.split("\n")).hasSize(3);
	}
}
