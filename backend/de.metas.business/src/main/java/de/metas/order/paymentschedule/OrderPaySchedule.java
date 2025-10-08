package de.metas.order.paymentschedule;

import com.google.common.collect.ImmutableList;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;

@EqualsAndHashCode
@ToString
public class OrderPaySchedule
{
	@NonNull @Getter private final ImmutableList<OrderPayScheduleLine> lines;

	private OrderPaySchedule(@NonNull final List<OrderPayScheduleLine> lines)
	{
		Check.assumeNotEmpty(lines, "lines shall not be empty");
		this.lines = ImmutableList.copyOf(lines);
	}

	public static OrderPaySchedule ofList(@NonNull final List<OrderPayScheduleLine> lines)
	{
		return new OrderPaySchedule(lines);
	}

	public static Optional<OrderPaySchedule> optionalOfList(@NonNull final List<OrderPayScheduleLine> lines)
	{
		return lines.isEmpty() ? Optional.empty() : Optional.of(new OrderPaySchedule(lines));
	}

	public static Collector<OrderPayScheduleLine, ?, Optional<OrderPaySchedule>> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(OrderPaySchedule::optionalOfList);
	}

	public void updateById(final OrderPayScheduleId id, UnaryOperator<OrderPayScheduleLine> updater)
	{
		throw new UnsupportedOperationException(); // TODO
	}
}
