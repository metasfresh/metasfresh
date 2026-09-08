package de.metas.frontend_testing.expectations;

import de.metas.frontend_testing.expectations.request.JsonMovementExpectation;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HuId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;

import java.util.Map;

import static de.metas.frontend_testing.expectations.assertions.Assertions.assertThat;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softly;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softlyPutContext;

@Builder
class AssertMovementExpectationsCommand
{
	@NonNull private final AssertExpectationsCommandServices services;
	@NonNull private final MasterdataContext context;
	@NonNull final Map<String, JsonMovementExpectation> expectations;

	void execute()
	{
		expectations.forEach(this::assertMovement);
	}

	private void assertMovement(@NonNull final String matcherStr, @NonNull final JsonMovementExpectation expectation)
	{
		softly(() -> {
			softlyPutContext("matcherStr", matcherStr);
			softlyPutContext("expectation", expectation);

			final ProductId productId = resolveProductId(matcherStr);
			softlyPutContext("productId", context.describeId(productId));

			final WarehouseId fromWarehouseId = context.getId(Identifier.ofString(expectation.getFromWarehouse()), WarehouseId.class);
			final WarehouseId toWarehouseId = context.getId(Identifier.ofString(expectation.getToWarehouse()), WarehouseId.class);

			if (expectation.getIsExists() != null)
			{
				final boolean exists = services.hasCompletedMovementLine(productId, fromWarehouseId, toWarehouseId);
				assertThat(exists)
						.as("Completed movement of " + context.describeId(productId)
								+ " from " + expectation.getFromWarehouse() + " to " + expectation.getToWarehouse() + " exists")
						.isEqualTo(expectation.getIsExists());
			}
		});
	}

	/**
	 * Resolves the expectation's key to a product: directly, if it is a product identifier, else via
	 * an HU identifier's FIRST packing material product.
	 */
	private ProductId resolveProductId(@NonNull final String matcherStr)
	{
		final Identifier identifier = Identifier.ofString(matcherStr);

		final ProductId productId = context.getOptionalId(identifier, ProductId.class).orElse(null);
		if (productId != null)
		{
			return productId;
		}

		final HuId huId = context.getId(identifier, HuId.class);
		return services.getPackingMaterialProductId(huId);
	}
}
