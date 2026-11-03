package de.metas.frontend_testing.expectations;

import com.google.common.annotations.VisibleForTesting;
import de.metas.frontend_testing.expectations.request.JsonProductExpectation;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;

import java.util.Map;

import static de.metas.frontend_testing.expectations.assertions.Assertions.assertThat;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softly;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softlyPutContext;

/** The key of each expectation is a product identifier from the masterdata request. */
@Builder
class AssertProductExpectationsCommand
{
	@NonNull private final AssertExpectationsCommandServices services;
	@NonNull private final MasterdataContext context;
	@NonNull private final Map<String, JsonProductExpectation> expectations;

	void execute()
	{
		expectations.forEach(this::assertProduct);
	}

	private void assertProduct(@NonNull final String productMatcherStr, @NonNull final JsonProductExpectation expectation)
	{
		softly(() -> {
			softlyPutContext("productMatcherStr", productMatcherStr);
			softlyPutContext("expectation", expectation);

			final ProductId productId = context.getId(Identifier.ofString(productMatcherStr), ProductId.class);
			softlyPutContext("productId", context.describeId(productId));

			assertCounts(services.countActiveHUsByProductId(productId), services.countInventoryLinesByProductId(productId), expectation);
		});
	}

	@VisibleForTesting
	static void assertCounts(final int huCount, final int inventoryLineCount, @NonNull final JsonProductExpectation expectation)
	{
		if (expectation.getHuCount() != null)
		{
			assertThat(huCount).as("Number of HUs holding the product").isEqualTo(expectation.getHuCount());
		}
		if (expectation.getInventoryLineCount() != null)
		{
			assertThat(inventoryLineCount).as("Number of inventory lines of the product").isEqualTo(expectation.getInventoryLineCount());
		}
	}
}
