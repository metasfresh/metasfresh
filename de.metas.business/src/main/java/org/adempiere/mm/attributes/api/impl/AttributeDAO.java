package org.adempiere.mm.attributes.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.ValidationRuleQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeUse;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_AttributeValue_Mapping;
import org.compiere.model.X_M_Attribute;
import org.compiere.model.X_M_AttributeValue;

import com.google.common.collect.ImmutableMap;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.cache.annotations.CacheSkipIfNotNull;
import lombok.NonNull;

public class AttributeDAO implements IAttributeDAO
{
	@Override
	@Cached(cacheName = I_M_Attribute.Table_Name + "#by#" + I_M_Attribute.COLUMNNAME_M_Attribute_ID)
	public I_M_Attribute retrieveAttributeById(@CacheCtx final Properties ctx, final int attributeId)
	{
		return InterfaceWrapperHelper.create(ctx, attributeId, I_M_Attribute.class, ITrx.TRXNAME_None);
	}

	@Override
	@Cached(cacheName = I_M_Attribute.Table_Name + "#by#" + I_M_Attribute.COLUMNNAME_Value)
	public <T extends I_M_Attribute> T retrieveAttributeByValue(@CacheCtx final Properties ctx, final String value, final Class<T> clazz)
	{
		final IQueryBuilder<T> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(clazz, ctx, ITrx.TRXNAME_None);

		final ICompositeQueryFilter<T> filters = queryBuilder.getCompositeFilter();
		filters.addOnlyActiveRecordsFilter();
		filters.addEqualsFilter(I_M_Attribute.COLUMNNAME_Value, value);

		final T attribute = queryBuilder
				.create()
				.firstOnly(clazz);

		Check.errorIf(attribute == null, "There is no attribute defined for the value {}", value);

		return attribute;
	}

	@Override
	public List<I_M_AttributeValue> retrieveAttributeValues(final I_M_Attribute attribute)
	{
		final Map<String, I_M_AttributeValue> map = retrieveAttributeValuesMap(attribute);
		return new ArrayList<>(map.values());
	}

	@Override
	public I_M_AttributeValue retrieveAttributeValueOrNull(final I_M_Attribute attribute, final String value)
	{
		//
		// In case we are dealing with a high-volume attribute values set, we can not fetch all of them,
		// but better to go directly and query.
		if (isHighVolumeValuesList(attribute))
		{
			return Services.get(IQueryBL.class)
					.createQueryBuilder(I_M_AttributeValue.class, attribute)
					.addEqualsFilter(I_M_AttributeValue.COLUMN_M_Attribute_ID, attribute.getM_Attribute_ID())
					.addEqualsFilter(I_M_AttributeValue.COLUMN_Value, value)
					.create()
					.firstOnly(I_M_AttributeValue.class);
		}

		final Map<String, I_M_AttributeValue> map = retrieveAttributeValuesMap(attribute);

		//
		// search by Value
		final I_M_AttributeValue avDirect = map.get(value);

		if (avDirect != null)
		{
			return avDirect;
		}

		//
		// Nothing found so far, return null
		return null;
	}

	@Override
	public boolean isHighVolumeValuesList(final I_M_Attribute attribute)
	{
		Check.assumeNotNull(attribute, "attribute not null");

		if (!X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(attribute.getAttributeValueType()))
		{
			return false;
		}

		return attribute.isHighVolume();
	}

	@Override
	public List<I_M_AttributeInstance> retrieveAttributeInstances(final I_M_AttributeSetInstance attributeSetInstance)
	{
		if (attributeSetInstance == null)
		{
			return Collections.emptyList();
		}

		final IQueryBuilder<I_M_AttributeInstance> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_AttributeInstance.class, attributeSetInstance)
				.addEqualsFilter(I_M_AttributeInstance.COLUMNNAME_M_AttributeSetInstance_ID, attributeSetInstance.getM_AttributeSetInstance_ID());

		queryBuilder.orderBy()
				.addColumn(I_M_AttributeInstance.COLUMNNAME_M_Attribute_ID); // at least to have a predictable order

		return queryBuilder.create()
				.list(I_M_AttributeInstance.class);
	}

	@Override
	public I_M_AttributeInstance retrieveAttributeInstance(final I_M_AttributeSetInstance attributeSetInstance, final int attributeId)
	{
		if (attributeSetInstance == null)
		{
			return null;
		}

		return Services.get(IQueryBL.class).createQueryBuilder(I_M_AttributeInstance.class)
				.addEqualsFilter(I_M_AttributeInstance.COLUMNNAME_M_AttributeSetInstance_ID, attributeSetInstance.getM_AttributeSetInstance_ID())
				.addEqualsFilter(I_M_AttributeInstance.COLUMNNAME_M_Attribute_ID, attributeId)
				.create()
				.firstOnly(I_M_AttributeInstance.class);
	}

	@Override
	public List<I_M_AttributeValue> retrieveFilteredAttributeValues(final I_M_Attribute attribute, final Boolean isSOTrx)
	{
		final List<I_M_AttributeValue> values = retrieveAttributeValues(attribute);
		if (null == isSOTrx)
		{
			// No isSOTrx. Retrieve all.
			return values;
		}

		final String availableTrxExpected = isSOTrx ? X_M_AttributeValue.AVAILABLETRX_SO : X_M_AttributeValue.AVAILABLETRX_PO;
		for (final Iterator<I_M_AttributeValue> it = values.iterator(); it.hasNext();)
		{
			final I_M_AttributeValue av = it.next();
			final String availableTrx = av.getAvailableTrx();

			if (availableTrx == null)
			{
				continue;
			}
			if (availableTrx.equals(availableTrxExpected))
			{
				continue;
			}

			it.remove();
		}

		return values;
	}

	/**
	 *
	 * @param attribute
	 * @return value to {@link I_M_AttributeValue} map
	 */
	private Map<String, I_M_AttributeValue> retrieveAttributeValuesMap(@NonNull final I_M_Attribute attribute)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(attribute);

		final int attributeId = attribute.getM_Attribute_ID();

		//
		// 07708: Apply AD_Val_Rule when filtering attributes for current context
		final ValidationRuleQueryFilter<I_M_AttributeValue> validationRuleQueryFilter;
		final int adValRuleId = attribute.getAD_Val_Rule_ID();
		if (adValRuleId > 0)
		{
			validationRuleQueryFilter = new ValidationRuleQueryFilter<>(attribute, adValRuleId);
		}
		else
		{
			validationRuleQueryFilter = null;
		}

		return retrieveAttributeValuesMap(ctx, attributeId, validationRuleQueryFilter);
	}

	@Cached(cacheName = I_M_AttributeValue.Table_Name
			+ "#by#" + I_M_AttributeValue.COLUMNNAME_M_Attribute_ID
			+ "#" + I_M_AttributeValue.COLUMNNAME_Value)
	/* package */Map<String, I_M_AttributeValue> retrieveAttributeValuesMap(
			@CacheCtx final Properties ctx,
			final int attributeId,
			// NOTE: we are caching this method only if we dont have a filter.
			// If we have a filter:
			// * that's mutable so it will fuck up our case
			// * in most of the cases, when we have an validation rule filter we are dealing with a huge amount of data which needs to be filtered (see Karoten ID example from)
			@CacheSkipIfNotNull final ValidationRuleQueryFilter<I_M_AttributeValue> validationRuleQueryFilter)
	{
		final IQueryBuilder<I_M_AttributeValue> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_AttributeValue.class, ctx, ITrx.TRXNAME_None);

		final ICompositeQueryFilter<I_M_AttributeValue> filters = queryBuilder.getCompositeFilter();
		filters.addOnlyActiveRecordsFilter();
		filters.addEqualsFilter(I_M_AttributeValue.COLUMNNAME_M_Attribute_ID, attributeId);

		if (validationRuleQueryFilter != null)
		{
			queryBuilder.filter(validationRuleQueryFilter);
		}

		// task 06897: order attributes by name
		queryBuilder.orderBy()
				.addColumn(I_M_Attribute.COLUMNNAME_Name);

		final List<I_M_AttributeValue> list = queryBuilder.create()
				.list(I_M_AttributeValue.class);

		if (list.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableMap.Builder<String, I_M_AttributeValue> value2attributeValues = ImmutableMap.builder();
		for (final I_M_AttributeValue av : list)
		{
			final String value = av.getValue();
			value2attributeValues.put(value, av);
		}

		return value2attributeValues.build();
	}

	@Override
	public Set<String> retrieveAttributeValueSubstitutes(final I_M_Attribute attribute, final String value)
	{
		final I_M_AttributeValue attributeValue = retrieveAttributeValueOrNull(attribute, value);
		if (attributeValue == null)
		{
			return Collections.emptySet();
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(attributeValue);
		final int attributeValueId = attributeValue.getM_AttributeValue_ID();

		return retrieveAttributeValueSubstitutes(ctx, attributeValueId);
	}

	@Cached(cacheName = I_M_AttributeValue_Mapping.Table_Name + "#by#" + I_M_AttributeValue_Mapping.COLUMNNAME_M_AttributeValue_To_ID + "#StringSet")
	Set<String> retrieveAttributeValueSubstitutes(@CacheCtx final Properties ctx, final int attributeValueId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final List<I_M_AttributeValue> attributeValueSubstitutes = queryBL.createQueryBuilder(I_M_AttributeValue_Mapping.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_AttributeValue_Mapping.COLUMN_M_AttributeValue_To_ID, attributeValueId)
				.andCollect(I_M_AttributeValue_Mapping.COLUMN_M_AttributeValue_ID, I_M_AttributeValue.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		final Set<String> substitutes = new HashSet<>();
		for (final I_M_AttributeValue attributeValueSubstitute : attributeValueSubstitutes)
		{
			final String substituteValue = attributeValueSubstitute.getValue();
			substitutes.add(substituteValue);
		}

		return substitutes;
	}

	@Override
	public List<I_M_Attribute> retrieveAttributes(final I_M_AttributeSet attributeSet, final boolean isInstanceAttribute)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_M_AttributeUse> attributeUseQuery = queryBL.createQueryBuilder(I_M_AttributeUse.class, attributeSet)
				.addEqualsFilter(I_M_AttributeUse.COLUMNNAME_M_AttributeSet_ID, attributeSet.getM_AttributeSet_ID())
				.create();
		final ICompositeQueryFilter<I_M_Attribute> compositeFilter = queryBL.createCompositeQueryFilter(I_M_Attribute.class);
		compositeFilter.addEqualsFilter(I_M_Attribute.COLUMNNAME_IsInstanceAttribute, isInstanceAttribute);
		compositeFilter.addInSubQueryFilter(I_M_Attribute.COLUMNNAME_M_Attribute_ID, I_M_AttributeUse.COLUMNNAME_M_Attribute_ID, attributeUseQuery);

		return queryBL.createQueryBuilder(I_M_Attribute.class, attributeSet)
				.filter(compositeFilter)
				.create()
				.list(I_M_Attribute.class);
	}

	@Override
	public boolean containsAttribute(final int attributeSetId, final int attributeId, final Object ctxProvider)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_AttributeUse.class, ctxProvider)
				.addEqualsFilter(I_M_AttributeUse.COLUMNNAME_M_AttributeSet_ID, attributeSetId)
				.addEqualsFilter(I_M_AttributeUse.COLUMNNAME_M_Attribute_ID, attributeId)
				.create()
				.match();
	}

	@Override
	public I_M_Attribute retrieveAttribute(final I_M_AttributeSet as, final int attributeId)
	{
		final I_M_AttributeUse attributeUse = Services.get(IQueryBL.class).createQueryBuilder(I_M_AttributeUse.class, as)
				.addEqualsFilter(I_M_AttributeUse.COLUMNNAME_M_AttributeSet_ID, as.getM_AttributeSet_ID())
				.addEqualsFilter(I_M_AttributeUse.COLUMNNAME_M_Attribute_ID, attributeId)
				.create()
				.firstOnly(I_M_AttributeUse.class);
		if (attributeUse == null)
		{
			return null;
		}

		final I_M_Attribute attribute = attributeUse.getM_Attribute();
		return attribute;
	}

	@Override
	public I_M_AttributeInstance createNewAttributeInstance(final Properties ctx, final I_M_AttributeSetInstance asi, final int attributeId, final String trxName)
	{
		final I_M_AttributeInstance ai = InterfaceWrapperHelper.create(ctx, I_M_AttributeInstance.class, trxName);
		ai.setAD_Org_ID(asi.getAD_Org_ID());
		ai.setM_AttributeSetInstance(asi);
		ai.setM_Attribute_ID(attributeId);

		return ai;
	}

	@Override
	@Cached(cacheName = I_M_AttributeSet.Table_Name + "#ID=0")
	public I_M_AttributeSet retrieveNoAttributeSet()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_AttributeSet.class)
				.addEqualsFilter(I_M_AttributeSet.COLUMNNAME_M_AttributeSet_ID, AttributeConstants.M_AttributeSet_ID_None)
				.create()
				.firstOnlyNotNull(I_M_AttributeSet.class);
	}

	@Override
	@Cached(cacheName = I_M_AttributeSetInstance.Table_Name + "#ID=0")
	public I_M_AttributeSetInstance retrieveNoAttributeSetInstance()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_AttributeSetInstance.class)
				.addEqualsFilter(I_M_AttributeSetInstance.COLUMNNAME_M_AttributeSetInstance_ID, AttributeConstants.M_AttributeSetInstance_ID_None)
				.create()
				.firstOnlyNotNull(I_M_AttributeSetInstance.class);
	}
}
