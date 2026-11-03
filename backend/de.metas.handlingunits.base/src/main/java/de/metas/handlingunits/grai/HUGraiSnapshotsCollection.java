package de.metas.handlingunits.grai;

import com.google.common.collect.ImmutableList;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collector;

public class HUGraiSnapshotsCollection implements Iterable<HUGraiSnapshot>
{
	public static final HUGraiSnapshotsCollection EMPTY = new HUGraiSnapshotsCollection(ImmutableList.of());

	@NonNull private final ImmutableList<HUGraiSnapshot> list;

	private HUGraiSnapshotsCollection(@NonNull final ImmutableList<HUGraiSnapshot> list)
	{
		this.list = list;
	}

	public static HUGraiSnapshotsCollection ofCollection(Collection<HUGraiSnapshot> collection)
	{
		return collection.isEmpty() ? EMPTY : new HUGraiSnapshotsCollection(ImmutableList.copyOf(collection));
	}

	public static Collector<HUGraiSnapshot, ?, HUGraiSnapshotsCollection> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(HUGraiSnapshotsCollection::ofCollection);
	}

	public boolean isEmpty() {return list.isEmpty();}

	@Override
	@NonNull
	public Iterator<HUGraiSnapshot> iterator() {return list.iterator();}

	public void assertAllGraisAssigned()
	{
		list.forEach(HUGraiSnapshot::assertAllGraisAssigned);
	}

	public int findMaxExistingDummyCounter(@NonNull final DummyGRAITemplate template)
	{
		int maxCounter = 0;

		for (final HUGraiSnapshot snapshot : list)
		{
			maxCounter = Math.max(maxCounter, snapshot.findMaxDummyCounter(template));
		}

		return maxCounter;
	}

}
