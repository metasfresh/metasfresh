package de.metas.handlingunits.util;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.Check;
import lombok.ToString;

/**
 * Iterator implementation for {@link List} which also supports {@link #current()} value.
 * 
 * The biggest difference between this implementation and other list iterators is that here we are allowing adding new elements to the list while iterating.
 * 
 * WARNING: behavior is unexpected in case you are adding items before the cursor's position.
 * 
 * @author tsa
 */
@ToString
public final class HUListCursor
{
	private final ArrayList<I_M_HU> list = new ArrayList<>();

	private int currentIndex = -1;
	private I_M_HU currentItem = null;
	private boolean currentItemSet = false;

	/**
	 * Append item at the end of the underlying list.
	 */
	public void append(final I_M_HU item)
	{
		list.add(item);
	}

	public I_M_HU current()
	{
		Check.assume(currentItemSet, "has current item");
		return currentItem;
	}

	public void closeCurrent()
	{
		currentItemSet = false;
		currentItem = null;
	}

	public boolean hasCurrent()
	{
		return currentItemSet;
	}

	public boolean hasNext()
	{
		final int size = list.size();
		if (currentIndex < 0)
		{
			return size > 0;
		}
		else
		{
			return currentIndex + 1 < size;
		}

	}

	public I_M_HU next()
	{
		// Calculate the next index
		final int nextIndex;
		if (currentIndex < 0)
		{
			nextIndex = 0;
		}
		else
		{
			nextIndex = currentIndex + 1;
		}

		// Make sure the new index is valid
		final int size = list.size();
		if (nextIndex >= size)
		{
			throw new NoSuchElementException("index=" + nextIndex + ", size=" + size);
		}

		// Update status
		this.currentIndex = nextIndex;
		this.currentItem = list.get(nextIndex);
		this.currentItemSet = true;

		return currentItem;
	}
}
