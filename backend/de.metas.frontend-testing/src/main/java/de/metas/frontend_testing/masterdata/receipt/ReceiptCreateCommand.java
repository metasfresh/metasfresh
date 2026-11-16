package de.metas.frontend_testing.masterdata.receipt;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.model.I_M_ReceiptSchedule_Alloc;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleDAO;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL.CreateReceiptsParameters;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.api.impl.ReceiptMovementDateRule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.logging.LogManager;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import de.metas.interfaces.I_C_OrderLine;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Builder
public class ReceiptCreateCommand
{
	@NonNull private static final Logger logger = LogManager.getLogger(ReceiptCreateCommand.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);
	@NonNull private final IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	@NonNull private final IHUReceiptScheduleDAO huReceiptScheduleDAO = Services.get(IHUReceiptScheduleDAO.class);

	private static final Duration RECEIPT_SCHEDULE_POLL_TIMEOUT = Duration.ofSeconds(60);
	private static final Duration RECEIPT_SCHEDULE_POLL_SLEEP = Duration.ofMillis(1000);

	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final MasterdataContext context;
	@NonNull private final JsonReceiptCreateRequest request;

	public JsonReceiptCreateResponse execute()
	{
		return trxManager.callInThreadInheritedTrx(this::execute0);
	}

	private JsonReceiptCreateResponse execute0()
	{
		final OrderId orderId = context.getId(request.getPurchaseOrder(), OrderId.class);

		// 1. Poll for receipt schedules (they are created after order completion)
		final List<I_M_ReceiptSchedule> receiptSchedules = pollForReceiptSchedules(orderId);
		if (receiptSchedules.isEmpty())
		{
			throw new AdempiereException("No receipt schedules found for purchase order " + orderId);
		}

		// 2. Plan the HUs to be received; without them processReceiptSchedules has nothing to receive and creates no receipt
		generateHUsIfMissing(receiptSchedules);

		// The receipt processing replaces the allocations of the planned HUs, so the received HU is looked up before it
		final HuId receivedTopLevelHuId = retrieveReceivedTopLevelHuId(receiptSchedules);

		// 3. Generate receipts from receipt schedules
		final InOutGenerateResult result = huReceiptScheduleBL.processReceiptSchedules(CreateReceiptsParameters.builder()
				.ctx(Env.getCtx())
				.selectedHuIds(null) // process all HUs
				.commitEachReceiptIndividually(false)
				.printReceiptLabels(false)
				.receiptSchedules(receiptSchedules)
				.movementDateRule(ReceiptMovementDateRule.CURRENT_DATE)
				.build());

		final List<I_M_InOut> receipts = result.getInOuts();
		if (receipts.isEmpty())
		{
			throw new AdempiereException("No receipts were created for purchase order " + orderId);
		}

		registerReceivedHU(receivedTopLevelHuId);

		final I_M_InOut receipt = receipts.get(0);
		return JsonReceiptCreateResponse.builder()
				.id(String.valueOf(receipt.getM_InOut_ID()))
				.documentNo(receipt.getDocumentNo())
				.build();
	}

	private void generateHUsIfMissing(@NonNull final List<I_M_ReceiptSchedule> receiptSchedules)
	{
		for (final I_M_ReceiptSchedule receiptSchedule : receiptSchedules)
		{
			// generateHUsIfNeeded does not return early when HUs exist, so check here to not receive twice the quantity
			if (huReceiptScheduleDAO.retrieveAllHandlingUnitAllocations(receiptSchedule, ITrx.TRXNAME_ThreadInherited).isEmpty())
			{
				huReceiptScheduleBL.generateHUsIfNeeded(InterfaceWrapperHelper.create(receiptSchedule, de.metas.handlingunits.model.I_M_ReceiptSchedule.class), Env.getCtx());
			}
		}
	}

	@Nullable
	private HuId retrieveReceivedTopLevelHuId(@NonNull final List<I_M_ReceiptSchedule> receiptSchedules)
	{
		final Identifier receivedHUIdentifier = request.getReceivedHU();
		if (receivedHUIdentifier == null)
		{
			return null;
		}

		final Set<HuId> topLevelHuIds = receiptSchedules.stream()
				.flatMap(receiptSchedule -> huReceiptScheduleDAO.retrieveAllHandlingUnitAllocations(receiptSchedule, ITrx.TRXNAME_ThreadInherited).stream())
				.filter(I_M_ReceiptSchedule_Alloc::isActive)
				.map(ReceiptCreateCommand::extractTopLevelHuId)
				.collect(ImmutableSet.toImmutableSet());

		// more than one means the spec would silently refer to an arbitrary one of the received HUs
		if (topLevelHuIds.size() != 1)
		{
			throw new AdempiereException("receivedHU `" + receivedHUIdentifier + "` needs exactly one received top-level HU but found " + topLevelHuIds);
		}

		return topLevelHuIds.iterator().next();
	}

	private void registerReceivedHU(@Nullable final HuId receivedTopLevelHuId)
	{
		if (receivedTopLevelHuId == null)
		{
			return;
		}

		context.putIdentifier(request.getReceivedHU(), receivedTopLevelHuId);

		// A real receipt prints the HU labels, i.e. the received HU and every HU in it carry a QR code; a manufacturing
		// job that uses the HU as source HU needs that code to list it (it fails with "No QRCode was generated").
		final ImmutableSet.Builder<HuId> huIds = ImmutableSet.builder();
		collectHuAndIncludedHuIds(handlingUnitsBL.getById(receivedTopLevelHuId), huIds);
		huQRCodesService.generateForExistingHUs(huIds.build());
	}

	private void collectHuAndIncludedHuIds(@NonNull final I_M_HU hu, @NonNull final ImmutableSet.Builder<HuId> collector)
	{
		collector.add(HuId.ofRepoId(hu.getM_HU_ID()));
		for (final I_M_HU includedHU : handlingUnitsBL.retrieveIncludedHUs(hu))
		{
			collectHuAndIncludedHuIds(includedHU, collector);
		}
	}

	@NonNull
	private static HuId extractTopLevelHuId(@NonNull final I_M_ReceiptSchedule_Alloc alloc)
	{
		final int huRepoId = alloc.getM_LU_HU_ID() > 0 ? alloc.getM_LU_HU_ID()
				: alloc.getM_TU_HU_ID() > 0 ? alloc.getM_TU_HU_ID()
				: alloc.getVHU_ID();
		return HuId.ofRepoId(huRepoId);
	}

	private List<I_M_ReceiptSchedule> pollForReceiptSchedules(final OrderId orderId)
	{
		// Get order line IDs for this purchase order
		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(orderId);
		if (orderLines.isEmpty())
		{
			throw new AdempiereException("No order lines found for purchase order " + orderId);
		}

		final Stopwatch stopwatch = Stopwatch.createStarted();

		while (true)
		{
			// Retrieve receipt schedules by looking up each order line
			final List<I_M_ReceiptSchedule> allSchedules = new ArrayList<>();
			for (final I_C_OrderLine orderLine : orderLines)
			{
				final I_M_ReceiptSchedule schedule = receiptScheduleDAO.retrieveForRecord(orderLine);
				if (schedule != null)
				{
					allSchedules.add(schedule);
				}
			}

			if (!allSchedules.isEmpty())
			{
				logger.info("Found {} receipt schedules for purchase order {} after {}", allSchedules.size(), orderId, stopwatch);
				return allSchedules;
			}

			if (stopwatch.elapsed().compareTo(RECEIPT_SCHEDULE_POLL_TIMEOUT) > 0)
			{
				throw new AdempiereException("Timeout waiting for receipt schedules for purchase order " + orderId + ". Waited " + stopwatch.elapsed());
			}

			logger.info("No receipt schedules yet for purchase order {}. Sleeping... (elapsed: {})", orderId, stopwatch);
			sleep(RECEIPT_SCHEDULE_POLL_SLEEP);
		}
	}

	private static void sleep(@NonNull final Duration duration)
	{
		try
		{
			Thread.sleep(duration.toMillis());
		}
		catch (final InterruptedException e)
		{
			throw new AdempiereException(e);
		}
	}
}
