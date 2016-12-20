package org.adempiere.mm.attributes.spi.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.concurrent.Immutable;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.CCache;
import org.compiere.util.CCache.CCacheStats;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * Implementation of {@link IAttributeValuesProvider} which is fetching the attributes from {@link I_M_AttributeValue}.
 *
 * @author tsa
 *
 */
public class DefaultAttributeValuesProvider implements IAttributeValuesProvider
{
	private final I_M_Attribute attribute;
	private transient Boolean _highVolume = null; // lazy

	private static final String CACHE_PREFIX = I_M_AttributeValue.Table_Name;
	private static final transient CCache<Integer, AttributeValuesMap> attributeId2values = CCache.newLRUCache(CACHE_PREFIX + "#AttributeValuesList#by#M_Attribute_ID", 100, 0);

	private transient AttributeValuesMap _attributeValuesMap; // lazy
	private final transient Map<String, NamePair> attributeValuesNP_HighVolumeCache = new HashMap<>();


	public DefaultAttributeValuesProvider(final I_M_Attribute attribute)
	{
		super();
		Check.assumeNotNull(attribute, "Parameter attribute is not null");
		this.attribute = attribute;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("attribute", attribute)
				.toString();
	}

	@Override
	public String getCachePrefix()
	{
		return CACHE_PREFIX;
	}
	
	@Override
	public List<CCacheStats> getCacheStats()
	{
		return ImmutableList.of(attributeId2values.stats());
	}

	@Override
	public String getAttributeValueType()
	{
		return X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40;
	}

	private final AttributeValuesMap getAttributeValuesMap()
	{
		if (_attributeValuesMap == null)
		{
			_attributeValuesMap = attributeId2values.getOrLoad(attribute.getM_Attribute_ID(), () -> retrieveAttributeValuesList(attribute));
		}
		return _attributeValuesMap;
	}

	private static final AttributeValuesMap retrieveAttributeValuesList(final I_M_Attribute attribute)
	{
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
		final List<I_M_AttributeValue> attributeValues = attributeDAO.retrieveAttributeValues(attribute);

		return new AttributeValuesMap(attribute, attributeValues);
	}

	private static ValueNamePair createNamePair(final I_M_AttributeValue av)
	{
		final String value = av.getValue();
		final String name = av.getName();
		final ValueNamePair vnp = new ValueNamePair(value, name);
		return vnp;
	}

	@Override
	public boolean isAllowAnyValue()
	{
		// only the fixed set of values is allowed
		return false;
	}

	@Override
	public Evaluatee prepareContext(final IAttributeSet attributeSet)
	{
		return Evaluatees.empty();
	}

	@Override
	public List<? extends NamePair> getAvailableValues(final Evaluatee evalCtx_NOTUSED)
	{
		return getAttributeValuesMap().getValues();
	}

	@Override
	public NamePair getAttributeValueOrNull(final Evaluatee evalCtx_NOTUSED, final String valueKey)
	{
		//
		{
			final NamePair vnp = getAttributeValuesMap().getValueByKeyOrNull(valueKey);
			if (vnp != null)
			{
				return vnp;
			}
		}

		//
		if (isHighVolume())
		{
			final NamePair vnp = findValueDirectly(valueKey);
			if (vnp != null)
			{
				return vnp;
			}
		}

		return null;
	}

	@Override
	public int getM_AttributeValue_ID(final String valueKey)
	{
		final Integer attributeValueId = getAttributeValuesMap().getM_AttributeValue_ID(valueKey);
		return attributeValueId != null ? attributeValueId : -1;
	}

	private final NamePair findValueDirectly(final String valueKey)
	{
		return attributeValuesNP_HighVolumeCache.computeIfAbsent(valueKey, key -> {
			final I_M_AttributeValue av = Services.get(IAttributeDAO.class).retrieveAttributeValueOrNull(attribute, valueKey);
			return av == null ? null : createNamePair(av);
		});
	}

	@Override
	public NamePair getNullValue()
	{
		return getAttributeValuesMap().getNullValue();
	}

	@Override
	public boolean isHighVolume()
	{
		if (_highVolume == null)
		{
			_highVolume = Services.get(IAttributeDAO.class).isHighVolumeValuesList(attribute);
		}
		return _highVolume;
	}

	@Immutable
	private static final class AttributeValuesMap
	{
		private final NamePair nullValue;
		private final ImmutableMap<String, NamePair> valuesByKey;
		private final ImmutableList<NamePair> valuesList;
		private final ImmutableMap<String, Integer> attributeValueIdByKey;

		private AttributeValuesMap(final I_M_Attribute attribute, final Collection<I_M_AttributeValue> attributeValues)
		{
			super();

			final ImmutableMap.Builder<String, NamePair> valuesByKey = ImmutableMap.builder();
			final ImmutableMap.Builder<String, Integer> attributeValueIdByKey = ImmutableMap.builder();
			NamePair nullValue = null;

			for (final I_M_AttributeValue av : attributeValues)
			{
				if (!av.isActive())
				{
					continue;
				}

				final ValueNamePair vnp = createNamePair(av);
				valuesByKey.put(vnp.getValue(), vnp);

				attributeValueIdByKey.put(vnp.getValue(), av.getM_AttributeValue_ID());

				//
				// Null placeholder value (if defined)
				if (av.isNullFieldValue())
				{
					Check.assumeNull(nullValue, "Only one null value shall be defined for {}, but we found: {}, {}",
							attribute.getName(), nullValue, av);
					nullValue = vnp;
				}
			}

			this.valuesByKey = valuesByKey.build();
			valuesList = ImmutableList.copyOf(this.valuesByKey.values());

			this.attributeValueIdByKey = attributeValueIdByKey.build();

			this.nullValue = nullValue;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("values", valuesList)
					.add("nullValue", nullValue)
					.toString();
		}

		public List<NamePair> getValues()
		{
			return valuesList;
		}

		public NamePair getValueByKeyOrNull(final String key)
		{
			return valuesByKey.get(key);
		}

		public int getM_AttributeValue_ID(final String valueKey)
		{
			return attributeValueIdByKey.get(valueKey);
		}

		public NamePair getNullValue()
		{
			return nullValue;
		}
	}
}
