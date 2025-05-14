package org.adempiere.mm.attributes.spi.impl;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.cache.CCache;
import de.metas.cache.CCacheStats;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;

import javax.annotation.concurrent.Immutable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link IAttributeValuesProvider} which is fetching the attributes from {@link IAttributeDAO}.
 *
 * @author tsa
 */
public class DefaultAttributeValuesProvider implements IAttributeValuesProvider
{
	private static final String CACHE_PREFIX = IAttributeDAO.CACHEKEY_ATTRIBUTE_VALUE;
	private static final CCache<AttributeId, AttributeValuesMap> attributeId2values = CCache.newLRUCache(CACHE_PREFIX + "#AttributeValuesList#by#M_Attribute_ID", 100, 0);
	@NonNull private final IAttributeDAO attributeDAO;

	@NonNull private final I_M_Attribute attribute;
	@NonNull @Getter private final AttributeId attributeId;
	private transient Boolean _highVolume = null; // lazy

	private transient AttributeValuesMap _attributeValuesMap; // lazy
	private final transient Map<String, NamePair> attributeValuesNP_HighVolumeCache = new HashMap<>();

	public DefaultAttributeValuesProvider(
			@NonNull final IAttributeDAO attributeDAO,
			@NonNull final I_M_Attribute attribute)
	{
		this.attributeDAO = attributeDAO;
		this.attribute = attribute;
		this.attributeId = AttributeId.ofRepoId(attribute.getM_Attribute_ID());
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

	private AttributeValuesMap getAttributeValuesMap()
	{
		AttributeValuesMap attributeValuesMap = _attributeValuesMap;
		if (attributeValuesMap == null)
		{
			attributeValuesMap = _attributeValuesMap = attributeId2values.getOrLoad(attributeId, () -> retrieveAttributeValuesList(attribute));
		}
		return attributeValuesMap;
	}

	private AttributeValuesMap retrieveAttributeValuesList(final I_M_Attribute attribute)
	{
		final List<AttributeListValue> attributeValues = attributeDAO.retrieveAttributeValues(attribute);
		return new AttributeValuesMap(attribute, attributeValues);
	}

	private static ValueNamePair toValueNamePair(final AttributeListValue av)
	{
		final String value = av.getValue();
		final String name = av.getName();
		final String description = av.getDescription();
		return ValueNamePair.of(value, name, description);
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

	private static String normalizeValueKey(final Object valueKey)
	{
		return valueKey == null ? null : valueKey.toString();
	}

	@Override
	public NamePair getAttributeValueOrNull(final Evaluatee evalCtx_NOTUSED, final Object valueKey)
	{
		final String valueKeyNormalized = normalizeValueKey(valueKey);

		//
		{
			final NamePair vnp = getAttributeValuesMap().getValueByKeyOrNull(valueKeyNormalized);
			if (vnp != null)
			{
				return vnp;
			}
		}

		//
		if (isHighVolume())
		{
			final NamePair vnp = findValueDirectly(valueKeyNormalized);
			if (vnp != null)
			{
				return vnp;
			}
		}

		return null;
	}

	@Override
	public AttributeValueId getAttributeValueIdOrNull(final Object valueKey)
	{
		final String valueKeyNormalized = normalizeValueKey(valueKey);
		return getAttributeValuesMap().getAttributeValueId(valueKeyNormalized);
	}

	private NamePair findValueDirectly(final String valueKey)
	{
		return attributeValuesNP_HighVolumeCache.computeIfAbsent(valueKey, key -> {
			final AttributeListValue av = attributeDAO.retrieveAttributeValueOrNull(attribute, valueKey);
			return av == null ? null : toValueNamePair(av);
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
			_highVolume = attributeDAO.isHighVolumeValuesList(attribute);
		}
		return _highVolume;
	}

	@Immutable
	private static final class AttributeValuesMap
	{
		@Getter private final NamePair nullValue;
		private final ImmutableMap<String, NamePair> valuesByKey;
		private final ImmutableList<NamePair> valuesList;
		private final ImmutableMap<String, AttributeValueId> attributeValueIdByKey;

		private AttributeValuesMap(final I_M_Attribute attribute, final Collection<AttributeListValue> attributeValues)
		{
			final ImmutableMap.Builder<String, NamePair> valuesByKey = ImmutableMap.builder();
			final ImmutableMap.Builder<String, AttributeValueId> attributeValueIdByKey = ImmutableMap.builder();
			NamePair nullValue = null;

			for (final AttributeListValue av : attributeValues)
			{
				if (!av.isActive())
				{
					continue;
				}

				final ValueNamePair vnp = toValueNamePair(av);
				valuesByKey.put(vnp.getValue(), vnp);

				attributeValueIdByKey.put(vnp.getValue(), av.getId());

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
			this.valuesList = ImmutableList.copyOf(this.valuesByKey.values());

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

		public AttributeValueId getAttributeValueId(final String valueKey)
		{
			final AttributeValueId attributeValueId = attributeValueIdByKey.get(valueKey);
			if (attributeValueId == null)
			{
				throw new AdempiereException("No M_AttributeValue_ID found for '" + valueKey + "'");
			}
			return attributeValueId;
		}
	}
}
