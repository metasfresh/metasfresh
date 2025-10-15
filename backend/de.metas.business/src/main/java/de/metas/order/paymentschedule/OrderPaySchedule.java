package de.metas.order.paymentschedule;

import com.google.common.collect.ImmutableList;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.util.Check;
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
	@NonNull @Getter OrderId orderid;
	@NonNull @Getter private final ImmutableList<OrderPayScheduleLine> lines;

	private OrderPaySchedule(@NonNull final OrderId orderid, @NonNull final List<OrderPayScheduleLine> lines)
	{
		this.orderid = orderid;
		Check.assumeNotEmpty(lines, "lines shall not be empty");
		this.lines = ImmutableList.copyOf(lines);
	}

	public static OrderPaySchedule ofList(@NonNull final OrderId orderid, @NonNull final List<OrderPayScheduleLine> lines)
	{
		return new OrderPaySchedule(orderid, lines);
	}

	public static Optional<OrderPaySchedule> optionalOfList(@NonNull final OrderId orderid, @NonNull final List<OrderPayScheduleLine> lines)
	{
		return lines.isEmpty() ? Optional.empty() : Optional.of(new OrderPaySchedule(orderid, lines));
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
}
