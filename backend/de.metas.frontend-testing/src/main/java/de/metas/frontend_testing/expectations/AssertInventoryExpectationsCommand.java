package de.metas.frontend_testing.expectations;

import de.metas.frontend_testing.expectations.request.JsonInventoryExpectation;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.inventory.InventoryId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.HashMap;
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

	@NonNull private final HashMap<InventoryId, Inventory> inventoriesCache = new HashMap<>();

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
		final String description = expectation.getDescription();

		// When a description is given, resolve THAT SPECIFIC inventory up front (the one whose
		// description matches) so every check below (isExists, docStatus) uses the SAME record —
		// never "any inventory referencing the HU" / "the latest one", which can silently pass
		// against an unrelated (e.g. seed/weight-confirm) inventory that merely happens to be newest.
		final Inventory describedInventory = description != null
				? inventoryLines.stream().map(this::getInventoryOf).filter(inventory -> Objects.equals(inventory.getDescription(), description)).findFirst().orElse(null)
				: null;

		if (expectation.getIsExists() != null)
		{
			final boolean exists = description != null ? describedInventory != null : !inventoryLines.isEmpty();
			assertThat(exists).as(existsAssertionLabel(expectation)).isEqualTo(expectation.getIsExists());
		}

		// description-equals-latest-inventory's-description is the pre-existing behaviour, kept only when
		// isExists isn't also asserted (isExists present switches description into the scoped-existence role above).
		final boolean assertDescriptionOnLatest = description != null && expectation.getIsExists() == null;

		if (expectation.getDocStatus() != null || assertDescriptionOnLatest)
		{
			if (inventoryLines.isEmpty())
			{
				fail("Expected an inventory document to assert docStatus/description on, but none was found");
				return;
			}

			// docStatus is asserted on the DESCRIBED inventory when a description was given (same-record
			// guarantee with the isExists/description checks above); otherwise fall back to the latest
			// inventory referencing the HU (pre-existing behaviour for a docStatus-only expectation).
			final Inventory inventory = description != null ? describedInventory : getLatestInventory(inventoryLines);

			if (expectation.getDocStatus() != null)
			{
				if (inventory == null)
				{
					fail("Expected an inventory document with description '" + description + "' to assert docStatus on, but none was found");
					return;
				}
				assertThat(inventory.getDocStatus().getCode()).as("DocStatus").isEqualTo(expectation.getDocStatus());
			}

			if (assertDescriptionOnLatest)
			{
				assertThat(getLatestInventory(inventoryLines).getDescription()).as("Description").isEqualTo(description);
			}
		}

		if (expectation.getCount() != null)
		{
			final long actualCount = countMatchingInventories(inventoryLines, description);
			assertThat(actualCount).as(countAssertionLabel(expectation)).isEqualTo((long)expectation.getCount());
		}
	}

	/**
	 * Number of DISTINCT inventory documents referencing the HU, matching {@code description} when given
	 * (else every one of them). Distinct by {@code M_Inventory_ID} because a single inventory document can
	 * carry more than one line for the same HU.
	 */
	private long countMatchingInventories(@NonNull final List<I_M_InventoryLine> inventoryLines, @Nullable final String description)
	{
		return inventoryLines.stream()
				.map(this::getInventoryOf)
				.filter(inventory -> description == null || Objects.equals(inventory.getDescription(), description))
				.map(Inventory::getId)
				.distinct()
				.count();
	}

	private static String countAssertionLabel(@NonNull final JsonInventoryExpectation expectation)
	{
		return expectation.getDescription() != null
				? "Number of inventory documents with description '" + expectation.getDescription() + "'"
				: "Number of inventory documents";
	}

	private static String existsAssertionLabel(@NonNull final JsonInventoryExpectation expectation)
	{
		return expectation.getDescription() != null
				? "Inventory document with description '" + expectation.getDescription() + "' exists"
				: "Inventory document exists";
	}

	/** Cached per distinct inventory id: several lines of the same document are inspected per assertion, and each miss loads the whole {@code Inventory} aggregate. */
	private Inventory getInventoryOf(@NonNull final I_M_InventoryLine inventoryLine)
	{
		return getInventoryById(InventoryId.ofRepoId(inventoryLine.getM_Inventory_ID()));
	}

	private Inventory getInventoryById(@NonNull final InventoryId inventoryId)
	{
		return inventoriesCache.computeIfAbsent(inventoryId, services::getInventoryById);
	}

	private Inventory getLatestInventory(@NonNull final List<I_M_InventoryLine> inventoryLines)
	{
		final I_M_InventoryLine latestLine = inventoryLines.stream()
				.max(Comparator.comparing(I_M_InventoryLine::getM_InventoryLine_ID))
				.orElseThrow(() -> new AdempiereException("inventoryLines is not empty")); // guarded by caller

		return getInventoryById(InventoryId.ofRepoId(latestLine.getM_Inventory_ID()));
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
