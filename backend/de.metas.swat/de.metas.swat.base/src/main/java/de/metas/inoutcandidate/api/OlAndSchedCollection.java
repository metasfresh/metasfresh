package de.metas.inoutcandidate.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.invalidation.segments.ImmutableShipmentScheduleSegment;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import lombok.NonNull;
import org.slf4j.MDC;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class OlAndSchedCollection
{
	public static final OlAndSchedCollection EMPTY = new OlAndSchedCollection(ImmutableList.of());

	@NonNull private final ImmutableList<OlAndSched> list;

	private OlAndSchedCollection(@NonNull final ImmutableList<OlAndSched> list)
	{
		this.list = list;
	}

	public static OlAndSchedCollection ofList(final List<OlAndSched> list)
	{
		return list.isEmpty() ? EMPTY : new OlAndSchedCollection(ImmutableList.copyOf(list));
	}

	public boolean isEmpty() {return list.isEmpty();}

	public int size() {return list.size();}

	public void forEach(@NonNull final Consumer<OlAndSched> consumer)
	{
		list.forEach(olAndSched -> {
			try (final MDC.MDCCloseable ignored = ShipmentSchedulesMDC.putShipmentScheduleId(olAndSched.getShipmentScheduleId()))
			{
				consumer.accept(olAndSched);
			}
		});
	}

	public ImmutableSet<ShipmentScheduleId> getShipmentScheduleIds()
	{
		return list.stream().map(OlAndSched::getShipmentScheduleId).collect(ImmutableSet.toImmutableSet());
	}

	public List<I_M_ShipmentSchedule> getShipmentSchedules()
	{
		return list.stream().map(OlAndSched::getSched).collect(ImmutableList.toImmutableList());
	}

	public Set<ImmutableShipmentScheduleSegment> getShipmentScheduleSegments()
	{
		return list.stream().map(OlAndSched::getShipmentScheduleSegment).collect(ImmutableSet.toImmutableSet());
	}
}
