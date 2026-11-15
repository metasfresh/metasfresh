package de.metas.frontend_testing.expectations;

import com.google.common.annotations.VisibleForTesting;
import de.metas.frontend_testing.expectations.request.JsonInventoryCandidateExpectation;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_Inventory_Candidate;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import lombok.Builder;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.frontend_testing.expectations.assertions.Assertions.assertThat;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softly;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softlyPutContext;

/** The key of each expectation is an HU: a masterdata identifier, or the HU QR code. */
@Builder
class AssertInventoryCandidateExpectationsCommand
{
	@NonNull private final AssertExpectationsCommandServices services;
	@NonNull private final MasterdataContext context;
	@NonNull private final Map<String, JsonInventoryCandidateExpectation> expectations;

	void execute()
	{
		expectations.forEach(this::assertCandidates);
	}

	private void assertCandidates(@NonNull final String huMatcherStr, @NonNull final JsonInventoryCandidateExpectation expectation)
	{
		softly(() -> {
			softlyPutContext("huMatcherStr", huMatcherStr);
			softlyPutContext("expectation", expectation);

			final HuId huId = getHUIdByMatcherString(huMatcherStr);
			softlyPutContext("huId", context.describeId(huId));

			assertCandidates(services.getInventoryCandidatesByHUId(huId), expectation);
		});
	}

	@VisibleForTesting
	static void assertCandidates(@NonNull final List<I_M_Inventory_Candidate> candidates, @NonNull final JsonInventoryCandidateExpectation expectation)
	{
		if (expectation.getCount() != null)
		{
			assertThat(candidates.size()).as("Number of M_Inventory_Candidate rows").isEqualTo(expectation.getCount());
		}
		if (expectation.getQtyToDispose() != null)
		{
			final BigDecimal qtyToDispose = candidates.stream()
					.map(I_M_Inventory_Candidate::getQtyToDispose)
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			assertThat(qtyToDispose).as("M_Inventory_Candidate.QtyToDispose").isEqualByComparingTo(expectation.getQtyToDispose());
		}
		if (expectation.getIsWholeHU() != null)
		{
			final boolean allMatch = !candidates.isEmpty()
					&& candidates.stream().allMatch(candidate -> candidate.isWholeHU() == expectation.getIsWholeHU());
			assertThat(allMatch).as("M_Inventory_Candidate.IsWholeHU is " + expectation.getIsWholeHU() + " on every row (and there is a row)").isEqualTo(true);
		}
	}

	private HuId getHUIdByMatcherString(@NonNull final String matcherStr)
	{
		final HuId huId = context.getOptionalId(Identifier.ofString(matcherStr), HuId.class).orElse(null);
		if (huId != null)
		{
			return huId;
		}

		return services.getHuIdByQRCode(HUQRCode.fromGlobalQRCodeJsonString(matcherStr));
	}
}
