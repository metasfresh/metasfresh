package de.metas.order.paymentschedule.referenced_docs.material_receipt;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.document.engine.DocStatus;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.lang.SOTrx;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderPayScheduleMaterialReceiptService
{
	@NonNull private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	@NonNull private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

	public MaterialReceiptCollection getByOrderId(@NonNull final OrderId orderId)
	{
		return getByOrderId(orderId, null);
	}

	public MaterialReceiptCollection getByOrderId(
			@NonNull final OrderId orderId,
			@Nullable final I_M_InOut completingReceipt)
	{
		return getByOrderId(orderId, completingReceipt, null);
	}

	/**
	 * Overload used by the AFTER_REVERSECORRECT / AFTER_REVERSEACCRUAL path to thread the
	 * in-memory reversed receipt down to the retrieval layer so it can be excluded from
	 * the receipts list.
	 *
	 * <p>Why this overload exists: {@code MInOut.reverseCorrectIt()} sets {@code Reversal_ID}
	 * on the in-memory original receipt at line 2208 but does NOT save it before
	 * AFTER_REVERSECORRECT fires at line 2262.  Any listener that re-reads the receipt from
	 * DB sees {@code Reversal_ID=0} + {@code DocStatus=CO} – i.e. it looks "still active" –
	 * even though we are inside the after-reverse callback.  We thread the in-memory reversed
	 * receipt down so the lookup excludes it by ID while reading all other receipts normally.
	 *
	 * <p>Sibling pattern to the {@code completingReceipt} include-override used during
	 * AFTER_COMPLETE (where the DB still shows {@code DocStatus=IP}).
	 *
	 * @param orderId         the order whose receipts are to be retrieved
	 * @param completingReceipt in-memory receipt currently completing (not yet CO in DB); may be {@code null}
	 * @param excludeReceipt  in-memory receipt currently being reversed (Reversal_ID not yet saved to DB);
	 *                        the receipt with this ID will be excluded from the result even if the DB still
	 *                        shows it as {@code DocStatus=CO} / {@code Reversal_ID=0}; may be {@code null}
	 */
	public MaterialReceiptCollection getByOrderId(
			@NonNull final OrderId orderId,
			@Nullable final I_M_InOut completingReceipt,
			@Nullable final I_M_InOut excludeReceipt)
	{
		final List<I_M_InOut> receiptRecords = retrieveReceiptRecords(orderId, completingReceipt, excludeReceipt);

		return fromRecords(receiptRecords, orderId);
	}

	private MaterialReceiptCollection fromRecords(
			@NonNull final List<I_M_InOut> receiptRecords,
			@NonNull final OrderId orderId)
	{
		if (receiptRecords.isEmpty()) {return MaterialReceiptCollection.EMPTY;}

		final ImmutableListMultimap<InOutId, I_M_InOutLine> lineRecords = Multimaps.index(
				inOutDAO.retrieveLinesForInOuts(receiptRecords),
				OrderPayScheduleMaterialReceiptService::extractInOutId
		);

		return receiptRecords
				.stream()
				.map(receiptRecord -> fromRecord(receiptRecord, lineRecords, orderId))
				.collect(MaterialReceiptCollection.collect());
	}

	private MaterialReceipt fromRecord(
			@NonNull final I_M_InOut receipt,
			@NonNull final ImmutableListMultimap<InOutId, I_M_InOutLine> lineRecords,
			@NonNull final OrderId orderId)
	{
		final InOutId inOutId = InOutId.ofRepoId(receipt.getM_InOut_ID());
		return MaterialReceipt.builder()
				.id(inOutId)
				.orderId(orderId)
				.movementDate(TimeUtil.asLocalDate(receipt.getMovementDate()))
				.lines(lineRecords.get(inOutId)
						.stream()
						.map(this::fromRecord)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private MaterialReceipt.Line fromRecord(final I_M_InOutLine receiptLine)
	{
		return MaterialReceipt.Line.builder()
				.id(InOutLineId.ofRepoId(receiptLine.getM_InOutLine_ID()))
				.movementQty(inOutBL.getMovementQty(receiptLine))
				.orderLineId(extractOrderAndLineId(receiptLine))
				.build();
	}

	@NonNull
	private static InOutId extractInOutId(final I_M_InOutLine line)
	{
		return InOutId.ofRepoId(line.getM_InOut_ID());
	}

	@Nullable
	private static OrderAndLineId extractOrderAndLineId(final I_M_InOutLine receiptLine)
	{
		return OrderAndLineId.ofRepoIdsOrNull(receiptLine.getC_Order_ID(), receiptLine.getC_OrderLine_ID());
	}

	@Nullable
	public static OrderId extractOrderIdOrNull(final @NotNull I_M_InOut inoutRecord)
	{
		return OrderId.ofRepoIdOrNull(inoutRecord.getC_Order_ID());
	}

	private @NotNull List<I_M_InOut> retrieveReceiptRecords(
			@NonNull final OrderId orderId,
			@Nullable final I_M_InOut completingReceipt0)
	{
		return retrieveReceiptRecords(orderId, completingReceipt0, null);
	}

	private @NotNull List<I_M_InOut> retrieveReceiptRecords(
			@NonNull final OrderId orderId,
			@Nullable final I_M_InOut completingReceipt0,
			@Nullable final I_M_InOut excludeReceipt)
	{
		final I_M_InOut completingReceiptEffective = isEligibleReceipt(completingReceipt0, false) ? completingReceipt0 : null;
		final int excludeReceiptId = excludeReceipt != null ? excludeReceipt.getM_InOut_ID() : -1;

		final ArrayList<I_M_InOut> result = new ArrayList<>();
		for (final I_M_InOut receiptRecord : inOutDAO.retrieveInOutsByOrderId(orderId))
		{
			// Skip the reversed receipt even though the DB still shows it as CO/Reversal_ID=0
			if (excludeReceiptId > 0 && receiptRecord.getM_InOut_ID() == excludeReceiptId)
			{
				continue;
			}

			if (completingReceiptEffective != null && completingReceiptEffective.getM_InOut_ID() == receiptRecord.getM_InOut_ID())
			{
				result.add(completingReceiptEffective);
			}
			else if (isEligibleReceipt(receiptRecord, true))
			{
				result.add(receiptRecord);
			}
		}

		return result;
	}

	private static boolean isEligibleReceipt(@Nullable final I_M_InOut receipt, boolean validateDocStatus)
	{
		if (receipt == null)
		{
			return false;
		}

		// Material receipt
		if (!isMaterialReceipt(receipt))
		{
			return false;
		}

		// not a reversal
		if (receipt.getReversal_ID() > 0)
		{
			return false;
		}

		if (validateDocStatus)
		{
			final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(receipt.getDocStatus());
			//noinspection RedundantIfStatement
			if (!docStatus.isCompletedOrClosed())
			{
				return false;
			}
		}

		return true;
	}

	public static boolean isMaterialReceipt(final @NonNull I_M_InOut receipt)
	{
		return SOTrx.ofBoolean(receipt.isSOTrx()).isPurchase();
	}
}
