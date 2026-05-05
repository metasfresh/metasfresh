package de.metas.order.paymentschedule.core;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.AdMessageKey;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermBreak;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class OrderPaySchedule
{
	private static final AdMessageKey MSG_MultipleLCBreaksUnsupported = AdMessageKey.of("de.metas.invoice.proforma.MultipleLCBreaksUnsupported");

	@NonNull @Getter OrderId orderId;
	@NonNull private final ArrayList<OrderPayScheduleLine> lines;

	private OrderPaySchedule(@NonNull final OrderId orderId, @NonNull final List<OrderPayScheduleLine> lines)
	{
		this.orderId = orderId;
		this.lines = new ArrayList<>(lines);
	}

	public static OrderPaySchedule ofList(@NonNull final OrderId orderId, @NonNull final List<OrderPayScheduleLine> lines)
	{
		return new OrderPaySchedule(orderId, lines);
	}

	public static Collector<OrderPayScheduleLine, ?, Optional<OrderPaySchedule>> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator((lines) -> {
			if (lines.isEmpty())
			{
				return Optional.empty();
			}
			final OrderId orderId = lines.get(0).getOrderId();
			return Optional.of(new OrderPaySchedule(orderId, lines));
		});
	}

	@NonNull
	public ImmutableList<OrderPayScheduleLine> getLines()
	{
		return ImmutableList.copyOf(lines);
	}

	@NonNull
	public Stream<OrderPayScheduleLine> streamLines()
	{
		return lines.stream();
	}

	public OrderPayScheduleLine getLineById(@NonNull final OrderPayScheduleId payScheduleLineId)
	{
		return lines.stream()
				.filter(line -> OrderPayScheduleId.equals(line.getId(), payScheduleLineId))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("OrderPayScheduleLine not found for ID: " + payScheduleLineId));
	}

	public Stream<OrderPayScheduleLine> streamLinesByBreakId(@NonNull final PaymentTermBreakId breakId)
	{
		return lines.stream()
				.filter(line -> PaymentTermBreakId.equals(line.getPaymentTermBreakId(), breakId));
	}

	public void replaceLinesByBreakId(@NonNull final List<OrderPayScheduleLine> newLines)
	{
		@NonNull final PaymentTermBreakId termBreakId = newLines.stream()
				.map(OrderPayScheduleLine::getPaymentTermBreakId)
				.distinct()
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("More than one breakId in newLines: " + newLines)));

		int firstRemovedLineIdx = -1;
		for (int i = lines.size() - 1; i >= 0; i--)
		{
			if (PaymentTermBreakId.equals(lines.get(i).getPaymentTermBreakId(), termBreakId))
			{
				if (firstRemovedLineIdx < 0 || i < firstRemovedLineIdx)
				{
					firstRemovedLineIdx = i;
				}
				lines.remove(i);
			}
		}

		if (firstRemovedLineIdx < 0)
		{
			lines.addAll(newLines);
		}
		else
		{
			lines.addAll(firstRemovedLineIdx, newLines);
		}
	}

	public void updateStatusFromContext(final OrderSchedulingContext context)
	{
		final PaymentTerm paymentTerm = context.getPaymentTerm();

		for (final OrderPayScheduleLine line : lines)
		{
			if (line.getStatus().isPending())
			{
				final PaymentTermBreak termBreak = paymentTerm.getBreakById(line.getPaymentTermBreakId());
				final OrderPayScheduleLineContext dueDateAndStatus = context.computeLineContext(termBreak);
				line.applyAndProcess(dueDateAndStatus);
			}
		}
	}

	public void applyAndProcess(@NonNull final OrderPayScheduleId lineId, @NonNull final OrderPayScheduleLineContext context)
	{
		final OrderPayScheduleLine line = getLineById(lineId);
		line.applyAndProcess(context);
	}

	public void markAsPending(final OrderPayScheduleId lineId)
	{
		applyAndProcess(lineId, OrderPayScheduleLineContext.pending());
	}

	public Optional<OrderPayScheduleLine> getSingleLCLine()
	{
		final ImmutableList<OrderPayScheduleLine> lcLines = lines.stream()
				.filter(OrderPayScheduleLine::isLetterOfCreditDate)
				.collect(ImmutableList.toImmutableList());
		if (lcLines.isEmpty())
		{
			return Optional.empty();  // no LC break in payment term — no-op
		}
		else if (lcLines.size() > 1)
		{
			throw new AdempiereException(MSG_MultipleLCBreaksUnsupported, orderId);
		}
		else
		{
			return Optional.of(lcLines.get(0));
		}
	}
}
