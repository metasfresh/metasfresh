package de.metas.frontend_testing.expectations;

import de.metas.frontend_testing.expectations.request.JsonPickingSlotExpectation;
import de.metas.frontend_testing.expectations.request.JsonPickingSlotQueueItemExpectation;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.slot.PickingSlotQueue;
import de.metas.handlingunits.picking.slot.PickingSlotQueueItem;
import de.metas.logging.LogManager;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static de.metas.frontend_testing.expectations.assertions.Assertions.assertThat;
import static de.metas.frontend_testing.expectations.assertions.Assertions.fail;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softly;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softlyPutContext;

@Builder
public class AssertPickingSlotExpectationsCommand
{
	@NonNull private static final Logger logger = LogManager.getLogger(AssertPickingExpectationsCommand.class);
	@NonNull private final AssertExpectationsCommandServices services;
	@NonNull private final MasterdataContext context;

	@NonNull private final Map<String, JsonPickingSlotExpectation> expectations;

	private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(60);

	void execute()
	{
		for (final Map.Entry<String, JsonPickingSlotExpectation> expectationEntry : expectations.entrySet())
		{
			final String matcherStr = expectationEntry.getKey();
			final JsonPickingSlotExpectation expectation = expectationEntry.getValue();
			assertPickingSlot(matcherStr, expectation);
		}
	}

	private void assertPickingSlot(@NonNull final String matcherStr, @NonNull final JsonPickingSlotExpectation expectation)
	{
		softly(() -> {
			softlyPutContext("matcherStr", matcherStr);
			softlyPutContext("expectation", expectation);

			final PickingSlotId pickingSlotId = getPickingSlotIdByMatcherString(matcherStr);
			softlyPutContext("pickingSlotId", pickingSlotId);

			if (expectation.getQueue() != null)
			{
				final PickingSlotQueue actual = services.getPickingSlotQueue(pickingSlotId);
				softlyPutContext("actual", actual);

				assertQueue(expectation.getQueue(), actual);
			}
		});
	}

	private PickingSlotId getPickingSlotIdByMatcherString(final @NonNull String matcherStr)
	{
		return PickingSlotQRCode.ofGlobalQRCodeJsonString(matcherStr).getPickingSlotId();
	}

	private void assertQueue(
			@NonNull final List<JsonPickingSlotQueueItemExpectation> expectations,
			@NonNull final PickingSlotQueue actual)
	{
		final LinkedHashMap<HuId, PickingSlotQueueItem> actualItems = CollectionUtils.uniqueLinkedHashMap(actual.getItems().stream(), PickingSlotQueueItem::getHuId);

		assertThat(actualItems).as("picking slot queue").hasSameSize(expectations);
		if (actualItems.size() != expectations.size())
		{
			return;
		}

		for (int i = 0; i < expectations.size(); i++)
		{
			softlyPutContext("index", i);

			final JsonPickingSlotQueueItemExpectation expectation = expectations.get(i);
			softlyPutContext("expectation", expectation);

			final HuId expectedHuId = context.getOptionalId(expectation.getHu(), HuId.class).orElse(null);
			if (expectedHuId != null)
			{
				softlyPutContext("expectedHuId", expectedHuId);
				final PickingSlotQueueItem actualItem = actualItems.remove(expectedHuId);
				if (actualItem == null)
				{
					fail("HU " + expectation.getHu() + "/" + expectedHuId + " not found in picking slot queue");
				}
			}
			else
			{
				final HuId actualHuId = actualItems.keySet().iterator().next();
				final PickingSlotQueueItem actualItem = actualItems.remove(actualHuId);
				softlyPutContext("actualItem", actualItem);
				context.putSameOrMissingId("M_HU_ID", expectation.getHu(), actualHuId, HuId.class);
			}
		}

	}

}
