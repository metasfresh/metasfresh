package org.compiere.swing;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.event.TableModelEvent;

import org.adempiere.util.comparator.ComparatorChain;
import org.compiere.util.MSort;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * {@link CTable}'s row sorter which sorts the rows directly on model level (i.e. not on view level).
 * 
 * @author tsa
 *
 */
public class CTableModelRowSorter
{
	/** Logger */
	private static final transient Logger log = LogManager.getLogger(CTableModelRowSorter.class.getName());

	private final CTable table;

	private boolean enabled = true;

	/** state variable to indicate sorting in progress **/
	protected boolean sorting = false;

	/**
	 * Contains initial sort order configuration. We're assuming that it's already ordered externally.
	 * 
	 * Mapping: Model column index to Ascending(true)/Descending(false).
	 */
	private LinkedHashMap<Integer, Boolean> _initialSortIndexes2Direction = null;

	/**
	 * Contains user-configurable sort order configuration.
	 * 
	 * Mapping: Model column index to Ascending(true)/Descending(false).
	 */
	private final LinkedHashMap<Integer, Boolean> sortIndexes2Direction = new LinkedHashMap<>();

	private boolean _clearFiltersAfterRefresh = true; // preserve old BL

	public CTableModelRowSorter(final CTable table)
	{
		super();
		this.table = table;
	}

	/** @return if model level row sorting is enabled */
	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public void setClearFiltersAfterRefresh(final boolean clearFiltersAfterRefresh)
	{
		_clearFiltersAfterRefresh = clearFiltersAfterRefresh;
	}

	private boolean isClearFiltersAfterRefresh()
	{
		return _clearFiltersAfterRefresh;
	}

	/** @return last entry of given <code>map</code> or null if map is empty */
	private static <K, V> Entry<K, V> getLastElementOrNull(final LinkedHashMap<K, V> map)
	{
		if (map == null)
		{
			return null;
		}

		final Iterator<Entry<K, V>> iterator = map.entrySet().iterator();
		Entry<K, V> result = null;
		while (iterator.hasNext())
		{
			result = iterator.next();
		}
		return result;
	}

	/**
	 * @param modelColumnIndex
	 * 
	 * @return true if it will be sorted ASCending, false otherwise
	 */
	protected final boolean addToSortColumns(final int modelColumnIndex)
	{
		final boolean removeOtherColumns = false;
		return addToSortColumns(modelColumnIndex, removeOtherColumns);
	}

	protected final boolean addToSortColumnsAndRemoveOtherColumns(final int modelColumnIndex)
	{
		final boolean removeOtherColumns = true;
		return addToSortColumns(modelColumnIndex, removeOtherColumns);
	}

	private final boolean addToSortColumns(final int modelColumnIndex, final boolean removeOtherColumns)
	{
		Boolean sortAscending = sortIndexes2Direction.remove(modelColumnIndex);
		if (removeOtherColumns)
		{
			sortIndexes2Direction.clear();
		}
		if (sortAscending == null)
		{
			sortAscending = true;
		}
		else
		{
			sortAscending = !sortAscending;
		}
		sortIndexes2Direction.put(modelColumnIndex, sortAscending);

		return sortAscending;
	}

	protected final void setColumnSortOrder(final int modelColumnIndex, final SortOrder sortOrder)
	{
		sortIndexes2Direction.remove(modelColumnIndex);
		if (sortOrder == SortOrder.ASCENDING)
		{
			sortIndexes2Direction.put(modelColumnIndex, true);
		}
		else if (sortOrder == SortOrder.DESCENDING)
		{
			sortIndexes2Direction.put(modelColumnIndex, false);
		}
		else if (sortOrder == SortOrder.UNSORTED)
		{
			// just removed it's fine
		}
		else
		{
			// shall not happen
		}
	}

	public boolean isSortAscending(final int modelColumnIndex)
	{
		final Boolean isSortAscending = sortIndexes2Direction.get(modelColumnIndex);
		if (isSortAscending == null)
		{
			return true; // usually ASC is default
		}
		return isSortAscending;
	}

	public SortOrder getSortOrder(final int modelColumnIndex)
	{
		final Boolean isSortAscending = sortIndexes2Direction.get(modelColumnIndex);
		return getSortOrderOfBooleanAscendingFlag(isSortAscending);
	}

	private static final SortOrder getSortOrderOfBooleanAscendingFlag(final Boolean isSortAscending)
	{
		if (isSortAscending == null)
		{
			return SortOrder.UNSORTED;
		}
		else if (isSortAscending)
		{
			return SortOrder.ASCENDING;
		}
		else
		{
			return SortOrder.DESCENDING;
		}
	}

	private static final Boolean getSortAscendingFlagOfSortOrder(final SortOrder sortOrder)
	{
		if (sortOrder == null)
		{
			return null;
		}
		else if (sortOrder == SortOrder.ASCENDING)
		{
			return true;
		}
		else if (sortOrder == SortOrder.DESCENDING)
		{
			return false;
		}
		else if (sortOrder == SortOrder.UNSORTED)
		{
			return null;
		}
		else
		{
			throw new IllegalArgumentException("Unknown sortOrder: " + sortOrder);
		}
	}

	public List<SortKey> getSortKeys()
	{
		if (sortIndexes2Direction.isEmpty())
		{
			return Collections.emptyList();
		}

		final List<SortKey> sortKeys = new ArrayList<>(sortIndexes2Direction.size());
		for (final Map.Entry<Integer, Boolean> e : sortIndexes2Direction.entrySet())
		{
			final int modelColumnIndex = e.getKey();
			final SortOrder sortOrder = getSortOrderOfBooleanAscendingFlag(e.getValue());
			final SortKey sortKey = new SortKey(modelColumnIndex, sortOrder);
			sortKeys.add(sortKey);
		}

		return sortKeys;
	}

	public final void setSortKeys(final List<? extends SortKey> sortKeys)
	{
		sortIndexes2Direction.clear();
		if (sortKeys == null || sortKeys.isEmpty())
		{
			return;
		}

		for (final SortKey sortKey : sortKeys)
		{
			final int modelColumnIndex = sortKey.getColumn();
			final Boolean isSortAscending = getSortAscendingFlagOfSortOrder(sortKey.getSortOrder());
			if (isSortAscending == null)
			{
				continue;
			}

			sortIndexes2Direction.put(modelColumnIndex, isSortAscending);
		}
	}

	/**
	 * @return column index of last column which was sorted
	 */
	public int getSortColumn()
	{
		final Entry<Integer, Boolean> lastElement = getLastElementOrNull(sortIndexes2Direction);
		if (lastElement == null)
		{
			return -1;
		}
		return lastElement.getKey();
	}

	/**
	 * @return boolean (last sorted column)
	 */
	public boolean isSortAscending()
	{
		final Entry<Integer, Boolean> lastElement = getLastElementOrNull(sortIndexes2Direction);
		if (lastElement == null)
		{
			return true; // usually ASC is default
		}
		return lastElement.getValue();
	}

	/**
	 * Clear sort criteria that were set by the user and invoke {@link #sort()}.
	 * 
	 * This causes the "initial" sort settings to be restored and the sorting to be done according to those.
	 */
	public final void clearSortCriteria()
	{
		sortIndexes2Direction.clear();
		sort();
	}

	/**
	 * Contains custom comparators which are available to be added externally for column indexes.
	 */
	final Map<Integer, List<Comparator<Object>>> _modelIndex2AdditionalSortIndexComparators = new LinkedHashMap<>();

	/**
	 * Specifies the given additional <code>comparator</code> for the column identified by the given <code>modelColumnIndex</code>. The given comparator takes precedence over the "general" sort order.
	 * To make sure there is no comparator on the given <code>modelColumnIndex</code>, call this method with <code>comparator == null</code>
	 * 
	 * Note that internally there is support for a multitude of comparators for each column index. those comparators would be chained. However, there is currently no use case, and also no
	 * reset/cleanup code for those comparator chains.
	 * 
	 * @param modelColumnIndex
	 * @param comparator may be <code>null</code>
	 */
	public final void setSortIndexComparator(final int modelColumnIndex, final Comparator<Object> comparator)
	{
		if (comparator == null)
		{
			_modelIndex2AdditionalSortIndexComparators.remove(modelColumnIndex);
		}
		else
		{
			_modelIndex2AdditionalSortIndexComparators.put(modelColumnIndex, Collections.singletonList(comparator));
		}
	}

	private final List<Comparator<Object>> getConfiguredSortIndexComparators(final int modelColumnIndex)
	{
		final List<Comparator<Object>> additionalSortIndexComparators = _modelIndex2AdditionalSortIndexComparators.get(modelColumnIndex);
		if (additionalSortIndexComparators == null)
		{
			return Collections.emptyList();
		}
		return additionalSortIndexComparators;
	}

	private final Comparator<Object> getDefaultSortComparator(final int modelColumnIndex, final boolean ascending)
	{
		final MSort sort = new MSort(0, null);
		sort.setSortAsc(ascending);

		final Comparator<Object> defaultSortIndexComparator = new Comparator<Object>()
		{
			@Override
			@SuppressWarnings("rawtypes")
			public int compare(final Object o1, final Object o2)
			{
				final Object item1 = ((Vector)o1).get(modelColumnIndex);
				final Object item2 = ((Vector)o2).get(modelColumnIndex);
				return sort.compare(item1, item2);
			}
		};
		return defaultSortIndexComparator;
	}

	/**
	 * Add initial sorting as configured (see {@link #sortIndexes2Direction})<br>
	 * <br>
	 * To be overridden by implementing classes.
	 */
	private final void loadInitialSortIndexes()
	{
		if (_initialSortIndexes2Direction == null)
		{
			return; // not configured
		}

		for (final Entry<Integer, Boolean> initialSortIndex2Direction : _initialSortIndexes2Direction.entrySet())
		{
			final int initialSortIndex = initialSortIndex2Direction.getKey();
			sortIndexes2Direction.remove(initialSortIndex);

			final boolean initialDirection = initialSortIndex2Direction.getValue();
			sortIndexes2Direction.put(initialSortIndex, initialDirection);
		}
	}

	/**
	 * Set initial sort configuration. If the sort settings are cleared by {@link #clearSortCriteria()}, they will subsequently be reloaded from this map. Note that the map implementation must be
	 * pre-ordered to the desired form. Also note that we demand a linked map to make it clear that the map's keys' ordering is a important part of the sort specification.
	 *
	 * @param initialSortIndexes2Direction
	 */
	public final void setInitialSortIndexes(final LinkedHashMap<Integer, Boolean> initialSortIndexes2Direction)
	{
		_initialSortIndexes2Direction = initialSortIndexes2Direction;
	}

	/**
	 * Tells if there are actual sort index column to sort by (i.e. if there are sorting settings).
	 * 
	 * @return <code>true</code> is the internal <code>_initialSortIndexes2Direction</code> is <code>null</code> or empty.
	 * 
	 * @task 08416
	 */
	public final boolean isInitialSortIndexesEmpty()
	{
		return _initialSortIndexes2Direction == null || _initialSortIndexes2Direction.isEmpty();
	}

	/**
	 * Default: true (assume the user did nothing)
	 */
	private boolean _isReloadOriginalSorting = true;

	/**
	 * Mark that the user changed something, therefore persist his/her changes.
	 *
	 * @param reloadOriginalSorting
	 */
	public final void setReloadOriginalSorting(final boolean reloadOriginalSorting)
	{
		_isReloadOriginalSorting = reloadOriginalSorting;
	}

	private final boolean isReloadOriginalSorting()
	{
		return _isReloadOriginalSorting;
	}

	/**
	 * Sort this table according to its current settings. If there are no current settings, then they are loaded from this table's "initital" settings.
	 * 
	 * @see #setInitialSortIndexes(Map)
	 * @see #setSortIndexComparator(int, Comparator)
	 */
	public final void sort()
	{
		sort(-1);
	}

	/**
	 * Sort Table
	 * 
	 * @param modelColumnIndex model column sort index
	 */
	protected void sort(final int modelColumnIndex)
	{
		int rows = table.getRowCount();
		if (rows <= 0)
		{
			return;
		}

		sorting = true;
		try
		// using try-finally to make sure the sorting flag is set to false again
		{
			if (sortIndexes2Direction.isEmpty())
			{
				loadInitialSortIndexes();
			}

			final int modelColumnIndexToUse;
			if (modelColumnIndex > 0)
			{
				modelColumnIndexToUse = modelColumnIndex;

				// other columns
				final boolean sortAscending = addToSortColumnsAndRemoveOtherColumns(modelColumnIndexToUse);

				//
				log.info("#" + modelColumnIndexToUse + " - rows=" + rows + ", asc=" + sortAscending);
			}
			else
			{
				final Entry<Integer, Boolean> lastElement = getLastElementOrNull(sortIndexes2Direction);
				if (lastElement != null)
				{
					modelColumnIndexToUse = lastElement.getKey();
				}
				else
				{
					modelColumnIndexToUse = 0;
				}
			}

			//
			// Retain selected row ID
			final int selRow = table.getSelectedRow();
			final int selCol = table.getKeyColumnIndex() == -1 ? 0 : table.getKeyColumnIndex();	// used to identify current row
			final Object selected;
			if (selRow >= 0)
			{
				selected = table.getValueAt(selRow, selCol);
			}
			else
			{
				selected = null;
			}

			// Prepare sorting
			final Vector<?> modelDataVector = table.getModelDataVector();
			for (final Entry<Integer, Boolean> columnIndexInfo : sortIndexes2Direction.entrySet())
			{
				final int columnIndex = columnIndexInfo.getKey();
				final boolean ascending = columnIndexInfo.getValue();

				final ComparatorChain<Object> modelIndexComparatorChain = new ComparatorChain<Object>();

				final List<Comparator<Object>> sortIndexComparators = getConfiguredSortIndexComparators(columnIndex);
				if (sortIndexComparators.isEmpty() || !isReloadOriginalSorting()) // apply default comparator only if it's user-defined or if we don't have configured sort comparators
				{
					//
					// Use default comparator
					final Comparator<Object> defaultSortComparator = getDefaultSortComparator(columnIndex, ascending);
					modelIndexComparatorChain.addComparator(defaultSortComparator);
				}
				else
				{
					//
					// Use configured comparators
					for (final Comparator<Object> sortIndexComparator : sortIndexComparators)
					{
						modelIndexComparatorChain.addComparator(sortIndexComparator);
					}
				}
				Collections.sort(modelDataVector, modelIndexComparatorChain);
			}

			//
			// No pre-defined sort indexes => apply default CTable sorting
			if (sortIndexes2Direction.isEmpty())
			{
				final Comparator<Object> defaultSortComparator = getDefaultSortComparator(modelColumnIndexToUse, true); // ascending
				Collections.sort(modelDataVector, defaultSortComparator);
			}

			// selection
			table.clearSelection();
			if (selected != null)
			{
				for (int r = 0; r < rows; r++)
				{
					if (selected.equals(table.getValueAt(r, selCol)))
					{
						table.setRowSelectionInterval(r, r);
						table.scrollRectToVisible(table.getCellRect(r, modelColumnIndexToUse, true)); // teo_sarca: bug fix [ 1585369 ]
						break;
					}
				}
			}   // selected != null
		}
		finally
		{
			sorting = false;
		}
	}   // sort

	final void tableChanged(final TableModelEvent e)
	{
		final boolean clearFiltersAfterRefresh = isClearFiltersAfterRefresh();
		if (e != null
				&& e.getFirstRow() == 0
				&& e.getLastRow() == Integer.MAX_VALUE
				&& e.getColumn() == TableModelEvent.ALL_COLUMNS
				&& e.getType() == TableModelEvent.UPDATE)
		{
			// metas: tsa: cancel editing; if we are not cancel the editing and we have a callout that is saving when e field is changed
			// and on grid refresh the order of the records change, the editor will be active using previous value but on a different row => HUGE bug
			table.stopEditor(false);

			if (clearFiltersAfterRefresh && !sorting)
			{
				// reset sort state after refresh
				sortIndexes2Direction.clear();
			}
		}
		else
		{
			if (clearFiltersAfterRefresh && table.getRowCount() == 0)
			{
				// reset sort state after clear
				sortIndexes2Direction.clear();
			}
		}
	}
}
