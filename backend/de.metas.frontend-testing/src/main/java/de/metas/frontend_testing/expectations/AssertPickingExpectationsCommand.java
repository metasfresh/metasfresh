package de.metas.frontend_testing.expectations;

import com.google.common.base.Stopwatch;
import com.google.common.base.Supplier;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.frontend_testing.expectations.request.JsonPickingExpectation;
import de.metas.frontend_testing.expectations.request.JsonShipmentScheduleExpectation;
import de.metas.frontend_testing.expectations.request.JsonShipmentScheduleQtyPickedExpectation;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.inout.InOutLineId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static de.metas.frontend_testing.expectations.assertions.Assertions.assertThat;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softly;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softlyPutContext;

@Builder
class AssertPickingExpectationsCommand
{
	@NonNull private static final Logger logger = LogManager.getLogger(AssertPickingExpectationsCommand.class);
	@NonNull private final AssertExpectationsCommandServices services;
	@NonNull private final MasterdataContext context;

	@NonNull private final Map<String, JsonPickingExpectation> expectations;

	private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(60);

	void execute() throws Exception
	{
		for (final Map.Entry<String, JsonPickingExpectation> expectationEntry : expectations.entrySet())
		{
			final String matcherStr = expectationEntry.getKey();
			final JsonPickingExpectation expectation = expectationEntry.getValue();
			assertPicking(matcherStr, expectation);
		}
	}

	private void assertPicking(@NonNull final String matcherStr, @NonNull final JsonPickingExpectation pickingExpectation) throws Exception
	{
		final PickingJobId pickingJobId = getPickingJobIdByMatcherString(matcherStr);
		final PickingJob pickingJob = services.getPickingJobById(pickingJobId);

		if (pickingExpectation.getShipmentSchedules() != null)
		{
			final Set<ShipmentScheduleId> shipmentScheduleIdSet = pickingJob.getScheduleIds().getShipmentScheduleIds();
			waitShipmentSchedulesValid(shipmentScheduleIdSet);

			final Collection<I_M_ShipmentSchedule> shipmentSchedules = services.getShipmentSchedulesByIds(shipmentScheduleIdSet);
			assertShipmentSchedules(pickingExpectation.getShipmentSchedules(), shipmentSchedules);
		}
	}

	private void waitShipmentSchedulesValid(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds) throws InterruptedException
	{
		final Supplier<Boolean> noRecomputeRecords = () -> services.isAllValid(shipmentScheduleIds);

		final Stopwatch stopwatch = Stopwatch.createStarted();
		// using do-while because otherwise the first check could be done before invalidation
		do
		{
			logger.info("Waiting for shipment schedules to become valid: {}", shipmentScheduleIds);
			//noinspection BusyWait
			Thread.sleep(1000);
		} while((!noRecomputeRecords.get() && stopwatch.elapsed().compareTo(DEFAULT_TIMEOUT) < 0));

		if (!noRecomputeRecords.get())
		{
			throw new AdempiereException(
					"Shipment schedules not valid after " + stopwatch + ": " + shipmentScheduleIds);
		}

		logger.info("Shipment schedules are now valid after {}", stopwatch);
	}

	private PickingJobId getPickingJobIdByMatcherString(@NotNull final String matcherStr)
	{
		final Identifier pickingJobIdentifier = Identifier.ofString(matcherStr);
		return context.getOptionalId(pickingJobIdentifier, PickingJobId.class)
				.orElseGet(() -> pickingJobIdentifier.toId(PickingJobId.class));
	}

	private void assertShipmentSchedules(
			@NonNull final Map<String, JsonShipmentScheduleExpectation> expectations,
			@NonNull final Collection<I_M_ShipmentSchedule> actuals) throws Exception
	{
		final ArrayListMultimap<ProductId, I_M_ShipmentSchedule> actualsByProductId = indexByProductId(actuals);

		for (final Map.Entry<String, JsonShipmentScheduleExpectation> expectationEntry : expectations.entrySet())
		{
			final Identifier productIdentifier = Identifier.ofString(expectationEntry.getKey());
			final JsonShipmentScheduleExpectation expectation = expectationEntry.getValue();
			final ProductId productId = context.getId(productIdentifier, ProductId.class);

			final List<I_M_ShipmentSchedule> actualsForExpectation = actualsByProductId.removeAll(productId);
			if (actualsForExpectation.isEmpty())
			{
				throw new AdempiereException("No shipment schedule found for product " + productId);
			}

			assertShipmentSchedule(expectation, actualsForExpectation);
		}

		if (!actualsByProductId.isEmpty())
		{
			throw new AdempiereException("Following storages were not expected: " + actualsByProductId.values());
		}
	}

	private static ArrayListMultimap<ProductId, I_M_ShipmentSchedule> indexByProductId(final @NotNull Collection<I_M_ShipmentSchedule> actuals)
	{
		return actuals.stream()
				.sorted(Comparator.comparingInt(I_M_ShipmentSchedule::getM_Product_ID)
						.thenComparing(I_M_ShipmentSchedule::getC_Order_ID)
						.thenComparing(I_M_ShipmentSchedule::getC_OrderLine_ID))
				.collect(GuavaCollectors.toArrayListMultimapByKey(AssertPickingExpectationsCommand::extractProductId));
	}

	@NotNull
	private static ProductId extractProductId(final I_M_ShipmentSchedule shipmentSchedule)
	{
		return ProductId.ofRepoId(shipmentSchedule.getM_Product_ID());
	}

	private void assertShipmentSchedule(
			@NonNull final JsonShipmentScheduleExpectation expectation,
			@NonNull final List<I_M_ShipmentSchedule> actuals) throws Exception
	{
		if (expectation.getIsScheduledForPicking() != null
				|| expectation.getQtyScheduledForPicking() != null
				|| expectation.getQtyScheduledForPickingOfProcessed() != null)
		{
			assertThat(actuals).isNotEmpty();
			actuals.forEach(actual -> softly(() -> {
				softlyPutContext("shipmentSchedule", actual);

				if (expectation.getIsScheduledForPicking() != null)
				{
					assertThat(actual.isScheduledForPicking()).isEqualTo(expectation.getIsScheduledForPicking());
				}
				if (expectation.getQtyScheduledForPicking() != null)
				{
					assertThat(actual.getQtyScheduledForPicking()).isEqualTo(expectation.getQtyScheduledForPicking());
				}
				if (expectation.getQtyScheduledForPickingOfProcessed() != null)
				{
					assertThat(actual.getQtyScheduledForPickingOfProcessed()).isEqualTo(expectation.getQtyScheduledForPickingOfProcessed());
				}
			}));
		}

		if (expectation.getQtyPicked() != null)
		{
			final ImmutableMap<ShipmentScheduleId, I_M_ShipmentSchedule> actualsById = Maps.uniqueIndex(actuals, actual -> ShipmentScheduleId.ofRepoId(actual.getM_ShipmentSchedule_ID()));
			final List<I_M_ShipmentSchedule_QtyPicked> actualQtyPickedRecords = services.getShipmentScheduleQtyPickedRecords(actualsById.keySet());
			assertQtyPickedList(expectation.getQtyPicked(), actualQtyPickedRecords, actualsById);
		}

		if (expectation.getIsClosed() != null)
		{
			assertThat(actuals).isNotEmpty();
			waitShipmentSchedulesClosed(actuals, expectation.getIsClosed());
			actuals.forEach(actual -> softly(() -> {
				softlyPutContext("shipmentSchedule", actual);
				assertThat(actual.isClosed()).as("IsClosed").isEqualTo(expectation.getIsClosed());
			}));
		}
	}

	private void waitShipmentSchedulesClosed(
			@NonNull final List<I_M_ShipmentSchedule> actuals,
			final boolean expectedClosed) throws InterruptedException
	{
		if (!expectedClosed)
		{
			return; // no need to wait if we expect them NOT closed
		}

		final ArrayList<I_M_ShipmentSchedule> notYetClosed = new ArrayList<>();
		for (final I_M_ShipmentSchedule actual : actuals)
		{
			if (!actual.isClosed())
			{
				notYetClosed.add(actual);
			}
		}

		if (notYetClosed.isEmpty())
		{
			return;
		}

		final Stopwatch stopwatch = Stopwatch.createStarted();
		while (!notYetClosed.isEmpty() && stopwatch.elapsed().compareTo(DEFAULT_TIMEOUT) < 0)
		{
			logger.info("Waiting for {}/{} shipment schedules to be closed", notYetClosed.size(), actuals.size());
			//noinspection BusyWait
			Thread.sleep(1000);

			InterfaceWrapperHelper.refreshAll(notYetClosed);
			notYetClosed.removeIf(I_M_ShipmentSchedule::isClosed);
		}
		stopwatch.stop();

		if (!notYetClosed.isEmpty())
		{
			throw new AdempiereException("Not all shipment schedules were closed after " + stopwatch + ": " + notYetClosed);
		}

		// Refresh all actuals so subsequent assertions see updated state
		InterfaceWrapperHelper.refreshAll(actuals);
	}

	private void assertQtyPickedList(
			@NonNull final List<JsonShipmentScheduleQtyPickedExpectation> expectations,
			@NonNull final List<I_M_ShipmentSchedule_QtyPicked> actuals,
			final ImmutableMap<ShipmentScheduleId, I_M_ShipmentSchedule> shipmentSchedulesById) throws Exception
	{
		assertThat(actuals).as("qty picked records").hasSameSize(expectations);

		waitQtyPickedListProcessed(expectations, actuals);

		for (int i = 0, size = expectations.size(); i < size; i++)
		{
			final JsonShipmentScheduleQtyPickedExpectation expectation = expectations.get(i);
			final I_M_ShipmentSchedule_QtyPicked actual = actuals.get(i);
			final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(actual.getM_ShipmentSchedule_ID());
			final I_M_ShipmentSchedule shipmentSchedule = shipmentSchedulesById.get(shipmentScheduleId);
			assertQtyPicked(expectation, actual, shipmentSchedule);
		}
	}

	private void waitQtyPickedListProcessed(
			@NonNull final List<JsonShipmentScheduleQtyPickedExpectation> expectations,
			@NonNull final List<I_M_ShipmentSchedule_QtyPicked> actuals) throws InterruptedException
	{
		final int size = expectations.size();

		final ArrayList<I_M_ShipmentSchedule_QtyPicked> notProcessed = new ArrayList<>(size);
		for (int i = 0; i < size; i++)
		{
			final JsonShipmentScheduleQtyPickedExpectation expectation = expectations.get(i);
			final I_M_ShipmentSchedule_QtyPicked actual = actuals.get(i);
			if (expectation.getProcessed() != null && expectation.getProcessed() && !actual.isProcessed())
			{
				notProcessed.add(actual);
			}
		}
		if (notProcessed.isEmpty())
		{
			return;
		}

		final Stopwatch stopwatch = Stopwatch.createStarted();
		while (!notProcessed.isEmpty() && stopwatch.elapsed().compareTo(DEFAULT_TIMEOUT) < 0)
		{
			logger.info("Waiting for {}/{} qty picked records to be processed", notProcessed.size(), size);
			//noinspection BusyWait
			Thread.sleep(1000); // 1s

			InterfaceWrapperHelper.refreshAll(notProcessed);
			notProcessed.removeIf(I_M_ShipmentSchedule_QtyPicked::isProcessed);
		}
		stopwatch.stop();

		if (notProcessed.isEmpty())
		{
			logger.info("All {} qty picked records are now processed after {}", size, stopwatch);

		}
		else
		{
			throw new AdempiereException("Not all qty picked records were processed after " + stopwatch + ": " + notProcessed);
		}
	}

	private void assertQtyPicked(
			@NonNull final JsonShipmentScheduleQtyPickedExpectation expectation,
			@NonNull final I_M_ShipmentSchedule_QtyPicked qtyPickedRecord,
			@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		softly(() -> {
			softlyPutContext("expectation", expectation);
			softlyPutContext("qtyPickedRecord", qtyPickedRecord);
			softlyPutContext("shipmentSchedule", shipmentSchedule);

			if (expectation.getQtyPicked() != null || expectation.getCatchWeight() != null)
			{
				final StockQtyAndUOMQty actualQtyPicked = services.extractQtyPicked(qtyPickedRecord, extractProductId(shipmentSchedule));

				if (expectation.getQtyPicked() != null)
				{
					assertThat(actualQtyPicked.getStockQty()).as("qtyPicked").isEqualTo(expectation.getQtyPicked().toQuantity());
				}
				if (expectation.getCatchWeight() != null)
				{
					assertThat(actualQtyPicked.getUOMQtyNotNull()).as("catch weight").isEqualTo(expectation.getCatchWeight().toQuantity());
				}
			}

			if (expectation.getQtyLUs() != null)
			{
				assertThat(qtyPickedRecord.getQtyLU().intValueExact()).as("QtyLUs").isEqualTo(expectation.getQtyLUs());
			}
			if (expectation.getQtyTUs() != null)
			{
				assertThat(qtyPickedRecord.getQtyTU().intValueExact()).as("QtyTUs").isEqualTo(expectation.getQtyTUs().toInt());
			}

			if (expectation.getProcessed() != null)
			{
				assertThat(qtyPickedRecord.isProcessed()).as("Processed").isEqualTo(expectation.getProcessed());
			}

			if (expectation.getVhu() != null)
			{
				final HuId actualId = HuId.ofRepoIdOrNull(qtyPickedRecord.getVHU_ID());
				context.putSameOrMissingId("VHU_ID", expectation.getVhu(), actualId, HuId.class);
			}
			if (expectation.getTu() != null)
			{
				final HuId actualId = HuId.ofRepoIdOrNull(qtyPickedRecord.getM_TU_HU_ID());
				context.putSameOrMissingId("M_TU_HU_ID", expectation.getTu(), actualId, HuId.class);
			}
			if (expectation.getLu() != null)
			{
				final HuId actualId = HuId.ofRepoIdOrNull(qtyPickedRecord.getM_LU_HU_ID());
				context.putSameOrMissingId("M_LU_HU_ID", expectation.getLu(), actualId, HuId.class);
			}

			if (expectation.getShipmentLineId() != null)
			{
				final InOutLineId actualId = InOutLineId.ofRepoIdOrNull(qtyPickedRecord.getM_InOutLine_ID());
				context.putSameOrMissingId("M_InOutLine_ID", expectation.getShipmentLineId(), actualId, InOutLineId.class);
			}
		});
	}

}
