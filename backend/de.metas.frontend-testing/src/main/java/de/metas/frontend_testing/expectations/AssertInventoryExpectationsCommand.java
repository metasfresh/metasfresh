package de.metas.frontend_testing.expectations;

import de.metas.frontend_testing.expectations.request.JsonInventoryExpectation;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.inventory.InventoryId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Inventory;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static de.metas.frontend_testing.expectations.assertions.Assertions.assertThat;
import static de.metas.frontend_testing.expectations.assertions.Assertions.fail;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softly;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softlyPutContext;

@Builder
class AssertInventoryExpectationsCommand
{
	@NonNull private final AssertExpectationsCommandServices services;
	@NonNull private final MasterdataContext context;
	@NonNull final Map<String, JsonInventoryExpectation> expectations;

	void execute()
	{
		expectations.forEach(this::assertInventory);
	}

	private void assertInventory(@NonNull final String huMatcherStr, @NonNull final JsonInventoryExpectation expectation)
	{
		softly(() -> {
			softlyPutContext("huMatcherStr", huMatcherStr);
			softlyPutContext("expectation", expectation);

			final HuId huId = getHUIdByMatcherString(huMatcherStr);
			softlyPutContext("huId", context.describeId(huId));

			final List<I_M_InventoryLine> inventoryLines = services.getInventoryLinesByHUId(huId);
			softlyPutContext("inventoryLines", inventoryLines);

			assertInventory(inventoryLines, expectation);
		});
	}

	private void assertInventory(@NonNull final List<I_M_InventoryLine> inventoryLines, @NonNull final JsonInventoryExpectation expectation)
	{
		if (expectation.getIsExists() != null)
		{
			// When a description is also given, scope the existence check to inventories carrying that
			// description ("is there an inventory whose description equals the given text"), rather than
			// "does ANY inventory reference this HU" — lets a spec assert e.g. "no write-off inventory
			// exists" on an HU that already carries an unrelated (seed/weight-confirm) inventory, which
			// would otherwise always make the unscoped check true regardless of the write-off.
			final boolean exists = expectation.getDescription() != null
					? inventoryLines.stream().map(this::getInventoryOf).anyMatch(inventory -> Objects.equals(inventory.getDescription(), expectation.getDescription()))
					: !inventoryLines.isEmpty();
			assertThat(exists).as(existsAssertionLabel(expectation)).isEqualTo(expectation.getIsExists());
		}

		// description-equals-latest-inventory's-description is the pre-existing behaviour, kept only when
		// isExists isn't also asserted (isExists present switches description into the scoped-existence role above).
		final boolean assertDescriptionOnLatest = expectation.getDescription() != null && expectation.getIsExists() == null;

		if (expectation.getDocStatus() != null || assertDescriptionOnLatest)
		{
			if (inventoryLines.isEmpty())
			{
				fail("Expected an inventory document to assert docStatus/description on, but none was found");
				return;
			}

			final I_M_Inventory inventory = getLatestInventory(inventoryLines);

			if (expectation.getDocStatus() != null)
			{
				assertThat(inventory.getDocStatus()).as("DocStatus").isEqualTo(expectation.getDocStatus());
			}

			if (assertDescriptionOnLatest)
			{
				assertThat(inventory.getDescription()).as("Description").isEqualTo(expectation.getDescription());
			}
		}
	}

	private static String existsAssertionLabel(@NonNull final JsonInventoryExpectation expectation)
	{
		return expectation.getDescription() != null
				? "Inventory document with description '" + expectation.getDescription() + "' exists"
				: "Inventory document exists";
	}

	private I_M_Inventory getInventoryOf(@NonNull final I_M_InventoryLine inventoryLine)
	{
		return services.getInventoryById(InventoryId.ofRepoId(inventoryLine.getM_Inventory_ID()));
	}

	private I_M_Inventory getLatestInventory(@NonNull final List<I_M_InventoryLine> inventoryLines)
	{
		final I_M_InventoryLine latestLine = inventoryLines.stream()
				.max(Comparator.comparing(I_M_InventoryLine::getM_InventoryLine_ID))
				.orElseThrow(() -> new AdempiereException("inventoryLines is not empty")); // guarded by caller

		return services.getInventoryById(InventoryId.ofRepoId(latestLine.getM_Inventory_ID()));
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
