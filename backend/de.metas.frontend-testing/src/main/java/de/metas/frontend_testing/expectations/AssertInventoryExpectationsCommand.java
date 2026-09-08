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

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

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

			final Collection<I_M_InventoryLine> inventoryLines = services.getInventoryLinesByHUId(huId);
			softlyPutContext("inventoryLines", inventoryLines);

			assertInventory(inventoryLines, expectation);
		});
	}

	private void assertInventory(@NonNull final Collection<I_M_InventoryLine> inventoryLines, @NonNull final JsonInventoryExpectation expectation)
	{
		if (expectation.getIsExists() != null)
		{
			assertThat(!inventoryLines.isEmpty()).as("Inventory document exists").isEqualTo(expectation.getIsExists());
		}

		if (expectation.getDocStatus() != null || expectation.getDescription() != null)
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

			if (expectation.getDescription() != null)
			{
				assertThat(inventory.getDescription()).as("Description").isEqualTo(expectation.getDescription());
			}
		}
	}

	private I_M_Inventory getLatestInventory(@NonNull final Collection<I_M_InventoryLine> inventoryLines)
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
