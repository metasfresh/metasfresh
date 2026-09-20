package de.metas.frontend_testing.expectations;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
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
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

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

		// Resolve THE ONE record every check below (isExists, docStatus, qtyBook/qtyCount) runs against,
		// up front: when a description is given that is the inventory whose description matches — never
		// "any inventory referencing the HU" / "the latest one", which can silently pass against an
		// unrelated (e.g. seed/weight-confirm) inventory that merely happens to be newest.
		final Inventory selectedInventory = selectInventory(inventoryLines, description, this::getInventoryOf);
		final Inventory describedInventory = description != null ? selectedInventory : null;

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
			// guarantee with the isExists/description checks above); otherwise selectInventory falls back
			// to the latest inventory referencing the HU (pre-existing docStatus-only behaviour).
			if (expectation.getDocStatus() != null)
			{
				if (selectedInventory == null)
				{
					fail("Expected an inventory document with description '" + description + "' to assert docStatus on, but none was found");
					return;
				}
				assertThat(selectedInventory.getDocStatus().getCode()).as("DocStatus").isEqualTo(expectation.getDocStatus());
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

		if (expectation.getQtyBook() != null || expectation.getQtyCount() != null)
		{
			// Same record as every check above: the DESCRIBED inventory when a description was given,
			// else the latest one referencing the HU.
			assertQtysOfSelectedInventory(selectedInventory, inventoryLines, description, expectation);
		}
	}

	/**
	 * The ONE inventory document an expectation is asserted against: the one whose description matches
	 * {@code description} when a description is given, else the latest one referencing the HU
	 * ({@code null} when there is no such document).
	 * <p>
	 * Kept separate from the assertions because picking "the latest" where "the described one" was meant
	 * is silent: the harness's own seed inventory also references the HU, so a regressed selection would
	 * assert the seed's qtys/docStatus and pass against a broken write-off.
	 */
	@VisibleForTesting
	@Nullable
	static Inventory selectInventory(
			@NonNull final List<I_M_InventoryLine> inventoryLines,
			@Nullable final String description,
			@NonNull final Function<I_M_InventoryLine, Inventory> inventoryLoader)
	{
		if (description != null)
		{
			return inventoryLines.stream()
					.map(inventoryLoader)
					.filter(inventory -> Objects.equals(inventory.getDescription(), description))
					.findFirst()
					.orElse(null);
		}

		return inventoryLines.stream()
				.max(Comparator.comparing(I_M_InventoryLine::getM_InventoryLine_ID))
				.map(inventoryLoader)
				.orElse(null);
	}

	@VisibleForTesting
	static void assertQtysOfSelectedInventory(
			@Nullable final Inventory selectedInventory,
			@NonNull final List<I_M_InventoryLine> inventoryLines,
			@Nullable final String description,
			@NonNull final JsonInventoryExpectation expectation)
	{
		if (selectedInventory == null)
		{
			fail("Expected an inventory document"
					+ (description != null ? " with description '" + description + "'" : "")
					+ " to assert qtyBook/qtyCount on, but none was found");
			return;
		}

		assertQtys(inventoryLines, selectedInventory.getId(), expectation);
	}

	/**
	 * Asserts the expectation's {@code qtyBook}/{@code qtyCount} against the HU's lines of {@code inventoryId},
	 * summed because one inventory document may carry several lines for the same HU.
	 */
	@VisibleForTesting
	static void assertQtys(
			@NonNull final List<I_M_InventoryLine> inventoryLines,
			@NonNull final InventoryId inventoryId,
			@NonNull final JsonInventoryExpectation expectation)
	{
		final List<I_M_InventoryLine> linesOfInventory = inventoryLines.stream()
				.filter(line -> line.getM_Inventory_ID() == inventoryId.getRepoId())
				.collect(ImmutableList.toImmutableList());

		if (expectation.getQtyBook() != null)
		{
			assertThat(sum(linesOfInventory, I_M_InventoryLine::getQtyBook)).as("QtyBook").isEqualByComparingTo(expectation.getQtyBook());
		}
		if (expectation.getQtyCount() != null)
		{
			assertThat(sum(linesOfInventory, I_M_InventoryLine::getQtyCount)).as("QtyCount").isEqualByComparingTo(expectation.getQtyCount());
		}
	}

	private static BigDecimal sum(
			@NonNull final List<I_M_InventoryLine> inventoryLines,
			@NonNull final Function<I_M_InventoryLine, BigDecimal> qtyGetter)
	{
		return inventoryLines.stream()
				.map(qtyGetter)
				.map(qty -> qty != null ? qty : BigDecimal.ZERO)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
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
		final Inventory latestInventory = selectInventory(inventoryLines, null, this::getInventoryOf);
		if (latestInventory == null)
		{
			throw new AdempiereException("inventoryLines is not empty"); // guarded by caller
		}
		return latestInventory;
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
