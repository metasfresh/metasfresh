package de.metas.frontend_testing.masterdata.receipt;

import com.google.common.base.Stopwatch;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
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
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import de.metas.interfaces.I_C_OrderLine;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Builder
public class ReceiptCreateCommand
{
	@NonNull private static final Logger logger = LogManager.getLogger(ReceiptCreateCommand.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);
	@NonNull private final IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);

	private static final Duration RECEIPT_SCHEDULE_POLL_TIMEOUT = Duration.ofSeconds(60);
	private static final Duration RECEIPT_SCHEDULE_POLL_SLEEP = Duration.ofMillis(1000);

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

		// 2. Generate receipts from receipt schedules
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

		final I_M_InOut receipt = receipts.get(0);
		return JsonReceiptCreateResponse.builder()
				.id(String.valueOf(receipt.getM_InOut_ID()))
				.documentNo(receipt.getDocumentNo())
				.build();
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
