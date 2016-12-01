package de.metas.ui.web.pattribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.util.CCache;
import org.compiere.util.Env;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext.Builder;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFetcher;

/*
 * #%L
 * metasfresh-webui-api
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

public final class ASILookupDescriptor implements LookupDescriptor, LookupDataSourceFetcher
{
	public static final ASILookupDescriptor of(final I_M_Attribute attribute)
	{
		return new ASILookupDescriptor(attribute);
	}

	private static final CCache<Integer, AttributeValuesMap> attributeValuesMapByAttributeId = CCache.newLRUCache(I_M_AttributeValue.Table_Name, 100, 60);

	private final int attributeId;

	private ASILookupDescriptor(final I_M_Attribute attribute)
	{
		super();
		attributeId = attribute.getM_Attribute_ID();
	}
	
	@Override
	public LookupDataSourceFetcher getLookupDataSourceFetcher()
	{
		return this;
	}

	@Override
	public boolean isHighVolume()
	{
		return false;
	}

	@Override
	public LookupSource getLookupSourceType()
	{
		return LookupSource.list;
	}

	@Override
	public boolean hasParameters()
	{
		return false;
	}

	@Override
	public boolean isNumericKey()
	{
		return false;
	}

	@Override
	public Set<String> getDependsOnFieldNames()
	{
		return ImmutableSet.of();
	}

	@Override
	public String getCachePrefix()
	{
		return I_M_AttributeValue.Table_Name;
	}
	
	public AttributeValue getAttributeValue(final LookupValue lookupValue)
	{
		if(lookupValue == null)
		{
			return null;
		}
		
		return getAttributeValuesMap().getAttributeValueByValue(lookupValue.getId());
	}

	private final AttributeValuesMap getAttributeValuesMap()
	{
		return attributeValuesMapByAttributeId.getOrLoad(attributeId, () -> retrieveAttributeValuesMap(attributeId));
	}

	private AttributeValuesMap retrieveAttributeValuesMap(final int attributeId)
	{
		final I_M_Attribute attribute = InterfaceWrapperHelper.create(Env.getCtx(), attributeId, I_M_Attribute.class, ITrx.TRXNAME_None); // shall be fetched from cache
		final List<I_M_AttributeValue> attributeValues = Services.get(IAttributeDAO.class).retrieveAttributeValues(attribute);
		return new AttributeValuesMap(attributeValues);
	}

	@Override
	public Builder newContextForFetchingById(final Object id)
	{
		return LookupDataSourceContext.builder(I_M_AttributeValue.Table_Name).putFilterById(id);
	}

	@Override
	public LookupValue retrieveLookupValueById(final LookupDataSourceContext evalCtx)
	{
		final Object id = evalCtx.getIdToFilter();
		final LookupValue lookupValue = getAttributeValuesMap().getLookupValueById(id);
		if (lookupValue == null)
		{
			return LOOKUPVALUE_NULL;
		}
		return lookupValue;
	}

	@Override
	public Builder newContextForFetchingList()
	{
		return LookupDataSourceContext.builder(I_M_AttributeValue.Table_Name);
	}

	@Override
	public LookupValuesList retrieveEntities(final LookupDataSourceContext evalCtx)
	{
		return getAttributeValuesMap().toLookupValuesList();
	}

	private static final class AttributeValuesMap
	{

		private final LookupValuesList lookupValues;
		private final ImmutableMap<Object, AttributeValue> attributeValuesByValue;

		public AttributeValuesMap(final List<I_M_AttributeValue> attributeValueRecords)
		{
			super();

			final List<LookupValue> lookupValues = new ArrayList<>();
			final ImmutableMap.Builder<Object, AttributeValue> attributeValuesByValue = ImmutableMap.builder();

			for (final I_M_AttributeValue attributeValueRecord : attributeValueRecords)
			{
				final AttributeValue attributeValue = new AttributeValue(attributeValueRecord);
				attributeValuesByValue.put(attributeValue.getValue(), attributeValue);
				lookupValues.add(attributeValue.getLookupValue());
			}

			this.lookupValues = LookupValuesList.of(lookupValues);
			this.attributeValuesByValue = attributeValuesByValue.build();
		}

		public LookupValue getLookupValueById(final Object id)
		{
			return lookupValues.getById(id);
		}
		
		public AttributeValue getAttributeValueByValue(final Object value)
		{
			return attributeValuesByValue.get(value);
		}

		public LookupValuesList toLookupValuesList()
		{
			return lookupValues;
		}

	}

	public static final class AttributeValue
	{
		private final int M_AttributeValue_ID;
		private final LookupValue lookupValue;

		private AttributeValue(final I_M_AttributeValue attributeValue)
		{
			super();

			M_AttributeValue_ID = attributeValue.getM_AttributeValue_ID();
			lookupValue = StringLookupValue.of(attributeValue.getValue(), attributeValue.getName());
		}

		public Object getValue()
		{
			return lookupValue.getId();
		}
		
		public String getValueAsString()
		{
			return lookupValue.getIdAsString();
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("M_AttributeValue_ID", M_AttributeValue_ID)
					.add("lookupValue", lookupValue)
					.toString();
		}

		public int getM_AttributeValue_ID()
		{
			return M_AttributeValue_ID;
		}

		public LookupValue getLookupValue()
		{
			return lookupValue;
		}
	}
}
