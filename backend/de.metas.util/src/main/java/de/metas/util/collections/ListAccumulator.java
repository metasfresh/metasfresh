package de.metas.util.collections;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.ToString;

import java.util.Collection;
import java.util.function.Consumer;

@ToString
public final class ListAccumulator<T>
{
	private ImmutableList.Builder<T> buffer = null;

	public synchronized void add(@NonNull final T item)
	{
		if (buffer == null)
		{
			buffer = ImmutableList.builder();
		}

		buffer.add(item);
	}

	public synchronized void addAll(@NonNull final Collection<T> items)
	{
		if (buffer == null)
		{
			buffer = ImmutableList.builder();
		}

		buffer.addAll(items);
	}

	public synchronized ImmutableList<T> getAndClear()
	{
		if (buffer == null)
		{
			return ImmutableList.of();
		}
		else
		{
			final ImmutableList<T> list = buffer.build();
			buffer = null;
			return list;
		}
	}

	public void flush(@NonNull final Consumer<ImmutableList<T>> consumer)
	{
		consumer.accept(getAndClear());
	}
}
