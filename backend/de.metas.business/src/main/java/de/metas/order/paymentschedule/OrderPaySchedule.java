package de.metas.order.paymentschedule;

import com.google.common.collect.ImmutableList;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

@EqualsAndHashCode
@ToString
public class OrderPaySchedule
{
	@NonNull @Getter OrderId orderId;
	@NonNull @Getter private final ImmutableList<OrderPayScheduleLine> lines;

	private OrderPaySchedule(@NonNull final OrderId orderId, @NonNull final List<OrderPayScheduleLine> lines)
	{
		this.orderId = orderId;
		this.lines = ImmutableList.copyOf(lines);
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

	public OrderPayScheduleLine getLineByPaymentTermBreakId(@NonNull final PaymentTermBreakId paymentTermBreakId)
	{
		return lines.stream()
				.filter(line -> PaymentTermBreakId.equals(line.getPaymentTermBreakId(), paymentTermBreakId))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No line found for " + paymentTermBreakId));
	}

	public void updateStatusFromContext(final OrderSchedulingContext context)
	{
		final PaymentTerm paymentTerm = context.getPaymentTerm();

		for (final OrderPayScheduleLine line : lines)
		{
			if (line.getStatus().isPending())
			{
				final PaymentTermBreak termBreak = paymentTerm.getBreakById(line.getPaymentTermBreakId());
				final DueDateAndStatus dueDateAndStatus = context.computeDueDate(termBreak);
				line.applyAndProcess(dueDateAndStatus);
			}

		}
	}
}
