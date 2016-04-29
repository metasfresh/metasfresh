package de.metas.ui.web.vaadin.window.prototype.order.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.service.impl.LookupDAO.SQLNamePairIterator;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;

import de.metas.ui.web.vaadin.window.descriptor.DataFieldLookupDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyName;
import de.metas.ui.web.vaadin.window.prototype.order.WindowConstants;
import de.metas.ui.web.vaadin.window.prototype.order.editor.LookupValue;

/*
 * #%L
 * metasfresh-webui
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class LookupPropertyValue implements PropertyValue
{
	private final PropertyName propertyName;
	private final DataFieldLookupDescriptor sqlLookupDescriptor;

	private final List<LookupValue> lookupValues = ImmutableList.of();

	LookupPropertyValue(final PropertyDescriptor descriptor)
	{
		super();
		final PropertyName propertyName = descriptor.getPropertyName();
		this.propertyName = WindowConstants.lookupValuesName(propertyName);

		this.sqlLookupDescriptor = descriptor.getSqlLookupDescriptor();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("name", propertyName)
				.toString();
	}

	@Override
	public PropertyName getName()
	{
		return propertyName;
	}

	@Override
	public Set<PropertyName> getDependsOnPropertyNames()
	{
		return ImmutableSet.of();
	}

	@Override
	public void onDependentPropertyValueChanged(final PropertyValueCollection values, final PropertyName changedPropertyName)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Object getValue()
	{
		final IStringExpression sqlForFetchingExpression = sqlLookupDescriptor.getSqlForFetchingExpression();
		final boolean numericKey = sqlLookupDescriptor.isNumericKey();
		final int entityTypeIndex = sqlLookupDescriptor.getEntityTypeIndex();

		final Evaluatee evalCtx = createEvaluationContext();
		final String sqlForFetching = sqlForFetchingExpression.evaluate(evalCtx, OnVariableNotFound.Fail);

		try (final SQLNamePairIterator data = new SQLNamePairIterator(sqlForFetching, numericKey, entityTypeIndex))
		{
			final List<LookupValue> items = data.fetchAll()
					.stream()
					.map(namePair -> toLookupValue(namePair))
					.collect(Collectors.toList());
			return items;
		}

		// // TODO
		// final Date now = new Date();
		// final List<LookupValue> lookupValues = new ArrayList<>();
		// for (int i = 1; i <= 10; i++)
		// {
		// lookupValues.add(LookupValue.of(i, "" + propertyName + " - " + now));
		// }
		//
		// return Suppliers.ofInstance(lookupValues);
	}

	private static final LookupValue toLookupValue(final NamePair namePair)
	{
		if (namePair == null)
		{
			return null;
		}
		else if (namePair instanceof ValueNamePair)
		{
			final ValueNamePair vnp = (ValueNamePair)namePair;
			return LookupValue.of(vnp.getValue(), vnp.getName());
		}
		else if (namePair instanceof KeyNamePair)
		{
			final KeyNamePair knp = (KeyNamePair)namePair;
			return LookupValue.of(knp.getKey(), knp.getName());
		}
		else
		{
			// shall not happen
			throw new IllegalArgumentException("Unknown namePair: " + namePair + " (" + namePair.getClass() + ")");
		}
	}

	@Override
	public Optional<String> getValueAsString()
	{
		return Optional.absent();
	}

	@Override
	public void setValue(final Object value)
	{
	}

	public Object getInitialValue()
	{
		return null;
	}

	@Override
	public Map<PropertyName, PropertyValue> getChildPropertyValues()
	{
		return ImmutableMap.of();
	}

	@Override
	public String getComposedValuePartName()
	{
		return null;
	}

	@Override
	public boolean isChanged()
	{
		return false;
	}

	private Evaluatee createEvaluationContext()
	{
		final String sqlFilter = "'%'"; // TODO
		final int sqlFetchLimit = 100; // TODO
		final String sqlValidationRule = "1=1"; // TODO

		final Map<String, Object> map = new HashMap<>();
		map.put(DataFieldLookupDescriptor.SQL_PARAM_FilterSql.toStringWithoutMarkers(), sqlFilter);
		map.put(DataFieldLookupDescriptor.SQL_PARAM_Limit.toStringWithoutMarkers(), sqlFetchLimit);
		map.put(DataFieldLookupDescriptor.SQL_PARAM_ValidationRuleSql.toStringWithoutMarkers(), sqlValidationRule);

		return Evaluatees.ofMap(map);
	}

}
