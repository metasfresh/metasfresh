package de.metas.handlingunits.client.terminal.editor.model.impl;

/*
 * #%L
 * de.metas.handlingunits.client
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.Predicate;
import org.compiere.model.IQuery;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;

import de.metas.adempiere.form.terminal.AbstractPropertiesPanelModel;
import de.metas.adempiere.form.terminal.IPropertiesPanelModel;
import de.metas.adempiere.form.terminal.ITerminalLookup;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyChildrenFilter;
import de.metas.handlingunits.client.terminal.editor.model.filter.IHUKeyFilter;
import de.metas.handlingunits.model.I_C_POS_HUEditor_Filter;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.pos.IPOSHUEditorFilterDAO;
import de.metas.i18n.IMsgBL;

/**
 * Implementation of {@link IPropertiesPanelModel} which allows user to filter available HUs.
 * 
 * FIXME: WARNING: this class is not stable anymore. Before enabling it again in production, please make sure it works.
 * Mainly, please check
 * <ul>
 * <li> {@link #createHUKeyChildrenFilter()}
 * </ul>
 *
 * @author al
 */
public class HUFilterPropertiesModel extends AbstractPropertiesPanelModel
{
	// Services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IPOSHUEditorFilterDAO filterDAO = Services.get(IPOSHUEditorFilterDAO.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	private final Runnable _callback;

	private final Map<String, HUFilterModel> _columnNames2Filters = new LinkedHashMap<>();

	private IHUKey _huKey = null;

	private final Predicate<IHUKey> filtersAsPredicate = new Predicate<IHUKey>()
	{
		@Override
		public boolean evaluate(final IHUKey key)
		{
			return checkAcceptKey(key);
		}
	};

	private MoreHUKey _moreHUKeyAvailableToUse = null;

	private final boolean checkAcceptKey(final IHUKey key)
	{
		if (key instanceof MoreHUKey)
		{
			_moreHUKeyAvailableToUse = (MoreHUKey)key;
			return true; // will be added at the end
		}

		for (final HUFilterModel filterModel : _columnNames2Filters.values())
		{
			final Object value = filterModel.getValue();
			final IHUKeyFilter filter = filterModel.getHUKeyFilter();
			if (!filter.accept(key, value))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Reset empty status, then extract next keys
	 *
	 * @param key
	 */
	private final void extractNextKeys(final IHUKey key)
	{
		if (_moreHUKeyAvailableToUse == null)
		{
			return;
		}
		_moreHUKeyAvailableToUse.resetEmptyStatus(); // empty status needs to be reset for forced retrieval upon changed filter

		//
		// MoreHU key needs to be added because it's parent is checked and used
		//

		//
		// Only add it to parent if not already there
		if (!key.getChildren().contains(_moreHUKeyAvailableToUse))
		{
			key.addChild(_moreHUKeyAvailableToUse);
		}
		final boolean hasMoreKeys = _moreHUKeyAvailableToUse.extractNextKeys();

		//
		// Make sure it's added at the end (if there could be more children)
		key.removeChild(_moreHUKeyAvailableToUse);
		if (hasMoreKeys)
		{
			key.addChild(_moreHUKeyAvailableToUse);
		}
		_callback.run(); // execute callback
	}

	private final IHUKeyChildrenFilter createHUKeyChildrenFilter()
	{
		// FIXME: here it will be much more safer to create a new instance instace of using inner classes!!!
		final Predicate<IHUKey> predicate = filtersAsPredicate;
		final IQuery<I_M_HU> query = createChildrenHUSubQuery();
		return new HUKeyChildrenFilter(predicate, query);
	}

	private IQuery<I_M_HU> createChildrenHUSubQuery()
	{
		final ITerminalContext contextProvider = getTerminalContext();

		final IQueryBuilder<I_M_HU> queryBuilder = queryBL.createQueryBuilder(I_M_HU.class, contextProvider);

		for (final HUFilterModel filterModel : _columnNames2Filters.values())
		{
			final Object value = filterModel.getValue();
			final IHUKeyFilter filter = filterModel.getHUKeyFilter();
			final IQuery<I_M_HU> subQueryFilter = filter.getSubQueryFilter(contextProvider, value);
			if (subQueryFilter != null)
			{
				queryBuilder.addInSubQueryFilter(I_M_HU.COLUMN_M_HU_ID, I_M_HU.COLUMN_M_HU_ID, subQueryFilter);
			}
		}
		return queryBuilder.create();
	}

	public HUFilterPropertiesModel(final ITerminalContext terminalContext, final Runnable callback)
	{
		super(terminalContext);

		Check.assumeNotNull(callback, "callback not null");
		_callback = callback;

		final List<I_C_POS_HUEditor_Filter> filters = filterDAO.retrieveFilters(terminalContext);
		for (final I_C_POS_HUEditor_Filter filter : filters)
		{
			final HUFilterModel huFilter = new HUFilterModel(filter);
			_columnNames2Filters.put(filter.getColumnName(), huFilter);
		}
	}

	private final HUFilterModel getHUFilter(final String propertyName)
	{
		final HUFilterModel huFilter = _columnNames2Filters.get(propertyName);
		Check.assumeNotNull(huFilter, "{} contains {}", _columnNames2Filters, propertyName);
		return huFilter;
	}

	private final IHUKeyFilter getHUKeyFilter(final String propertyName)
	{
		final HUFilterModel huFilter = getHUFilter(propertyName);
		return huFilter.getHUKeyFilter();
	}

	private final IHUKey getHUKey()
	{
		return _huKey;
	}

	public final void setHUKey(final IHUKey huKey)
	{
		Check.assumeNotNull(huKey, "huKey not null");

		// Remove filters from previous key
		if (_huKey != null)
		{
			_huKey.setChildrenFilter(null);
		}

		_huKey = huKey;

		// Add filter to the new key
		_huKey.setChildrenFilter(createHUKeyChildrenFilter());
	}

	@Override
	public List<String> getPropertyNames()
	{
		final Set<String> columnNames = _columnNames2Filters.keySet();
		return new ArrayList<String>(columnNames);
	}

	@Override
	public String getPropertyDisplayName(final String propertyName)
	{
		getHUFilter(propertyName); // validate

		final Properties ctx = getTerminalContext().getCtx();
		final String propertyDisplayName = msgBL.translate(ctx, propertyName);
		return propertyDisplayName;
	}

	@Override
	public int getDisplayType(final String propertyName)
	{
		return getHUFilter(propertyName).getDisplayType();
	}

	@Override
	public Object getPropertyValue(final String propertyName)
	{
		final HUFilterModel huFilter = getHUFilter(propertyName);
		return huFilter.getValue();
	}

	@Override
	public List<? extends NamePair> getPropertyAvailableValues(final String propertyName)
	{
		final IHUKeyFilter huKeyFilter = getHUKeyFilter(propertyName);

		final Set<NamePair> availableValues = new TreeSet<>();
		final IHUKey key = getHUKey();

		final List<IHUKey> children = key.getChildren();
		for (final IHUKey child : children)
		{
			final List<? extends NamePair> childAvailableValues = getPropertyAvailableValuesForChild(child, huKeyFilter);
			if (childAvailableValues == null || childAvailableValues.isEmpty())
			{
				continue;
			}
			availableValues.addAll(childAvailableValues);
		}

		final List<NamePair> availableValuesList = new ArrayList<>(availableValues);
		availableValuesList.remove(KeyNamePair.EMPTY);
		availableValuesList.add(0, KeyNamePair.EMPTY);
		return availableValuesList;
	}

	private List<? extends NamePair> getPropertyAvailableValuesForChild(final IHUKey child, final IHUKeyFilter huKeyFilter)
	{
		final List<NamePair> childAvailableValues = new ArrayList<>();
		if (child instanceof MoreHUKey)
		{
			_moreHUKeyAvailableToUse = (MoreHUKey)child;
		}

		if (_moreHUKeyAvailableToUse != null) // there are still more HUs in here & button was initialized
		{
			final List<? extends NamePair> availableValuesFromQueryBuilder = huKeyFilter.getPropertyAvailableValues(_moreHUKeyAvailableToUse.getQueryBuilder());
			childAvailableValues.addAll(availableValuesFromQueryBuilder);
		}

		final List<? extends NamePair> availableValuesFromChild = huKeyFilter.getPropertyAvailableValues(child);
		childAvailableValues.addAll(availableValuesFromChild); // fallback to existing (if no MoreHU button exists - i.e all were loaded)

		return childAvailableValues;
	}

	@Override
	public ITerminalLookup getPropertyLookup(final String propertyName)
	{
		final IHUKeyFilter huKeyFilter = getHUKeyFilter(propertyName);
		return huKeyFilter.getPropertyLookup(getHUKey());
	}

	@Override
	public void setPropertyValue(final String propertyName, final Object value)
	{
		//
		// Update filter model
		final HUFilterModel huFilter = getHUFilter(propertyName);
		huFilter.setValue(value);

		//
		// Execute filtering
		final IHUKey key = getHUKey();
		key.setChildrenFilter(createHUKeyChildrenFilter());

		//
		// Load first set of filtered keys
		extractNextKeys(key);
	}

	@Override
	public boolean isEditable(final String propertyName)
	{
		return true; // filters are always editable (for now)
	}

	@Override
	public void commitEdit()
	{
		fireContentChanged();
	}
}
