package de.metas.handlingunits.report.labels;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.process.AdProcessId;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class HULabelPrintProcessesList
{
	private final ImmutableMap<AdProcessId, HULabelPrintProcess> byId;

	private HULabelPrintProcessesList(final List<HULabelPrintProcess> list)
	{
		this.byId = Maps.uniqueIndex(list, HULabelPrintProcess::getProcessId);
	}

	public static Collector<HULabelPrintProcess, ?, HULabelPrintProcessesList> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(HULabelPrintProcessesList::new);
	}

	public Stream<HULabelPrintProcess> stream()
	{
		return byId.values().stream();
	}

	public boolean containsProcessId(@NonNull final AdProcessId adProcessId)
	{
		return byId.containsKey(adProcessId);
	}
}
