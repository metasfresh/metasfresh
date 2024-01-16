package de.metas.handlingunits.qrcodes.service;

import com.google.common.collect.ImmutableList;
import lombok.ToString;

import java.util.Iterator;
import java.util.UUID;
import java.util.function.Supplier;

@ToString
class MockedUniqueUUIDGenerator implements Supplier<UUID>
{
	private final Iterator<UUID> iterator;

	MockedUniqueUUIDGenerator(final UUID... uuids)
	{
		this.iterator = ImmutableList.copyOf(uuids).iterator();
	}

	@Override
	public UUID get()
	{
		return iterator.next();
	}
}
