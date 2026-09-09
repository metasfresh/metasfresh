package de.metas.einvoice.cii;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Result of an EN16931 Schematron validation run on a marshalled CII XML document.
 *
 * <p>A result is considered valid when there are no failed assertions of FATAL or ERROR severity.
 * Warnings (role="warning") do not make the result invalid.
 */
@Value
@Builder
public class CiiValidationResult
{
	/** All failed assertions from the SVRL output. */
	@NonNull
	@Singular
	List<FailedAssertion> failedAssertions;

	/**
	 * Returns {@code true} when no FATAL or ERROR level assertions failed.
	 * Informational / warning assertions are not considered failures.
	 */
	public boolean isValid()
	{
		return failedAssertions.stream().noneMatch(FailedAssertion::isFatalOrError);
	}

	/**
	 * Returns the rule IDs (BR-* codes) of all FATAL/ERROR-level failed assertions.
	 * Used for reporting mapper gaps in the valid-fixture test.
	 */
	@NonNull
	public List<String> getFatalAndErrorRuleIds()
	{
		return failedAssertions.stream()
				.filter(FailedAssertion::isFatalOrError)
				.map(FailedAssertion::getRuleId)
				.collect(Collectors.toList());
	}

	/**
	 * A single failed Schematron assertion from the SVRL output.
	 */
	@Value
	@Builder
	public static class FailedAssertion
	{
		/** EN16931 rule ID, e.g. "BR-6", "BR-CO-13". May be null for non-EN16931 rules. */
		String ruleId;

		/** Human-readable rule message, e.g. "[BR-6]-An Invoice shall contain the Seller name." */
		String message;

		/** XPath location of the failing element in the CII document. */
		String location;

		/** Severity: "fatal", "error", "warning". */
		@NonNull
		String severity;

		public boolean isFatalOrError()
		{
			// EErrorLevel IDs: "fatal_error" (from flag="fatal"), "error" (from flag="error")
			// See com.helger.commons.error.level.EErrorLevel and DefaultSVRLErrorLevelDeterminator
			return "fatal_error".equals(severity) || "error".equals(severity);
		}
	}
}
