package de.metas.dimension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import de.metas.common.util.pair.IPair;
import de.metas.common.util.pair.ImmutablePair;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;

import de.metas.cache.CCache;
import de.metas.dimension.model.I_DIM_Dimension_Spec;
import de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute;
import de.metas.dimension.model.I_DIM_Dimension_Spec_AttributeValue;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.AttributesKeyPart;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.dimension
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class DimensionSpec
{
	public static DimensionSpec ofRecord(@NonNull final I_DIM_Dimension_Spec dimensionSpecRecord)
	{
		Check.errorIf(dimensionSpecRecord.getDIM_Dimension_Spec_ID() <= 0,
				"Parameter dimensionSpecRecord needs to have ID > 0, i.e. it needs to be already stored in DB; dimensionSpecRecord={}",
				dimensionSpecRecord);
		return new DimensionSpec(dimensionSpecRecord);
	}

	private static final String GROUPS_CACHE_NAME = "DimensionSpec#retrieveGroups"
			+ "#" + I_DIM_Dimension_Spec.Table_Name
			+ "#" + I_DIM_Dimension_Spec_Attribute.Table_Name
			+ "#" + I_DIM_Dimension_Spec_AttributeValue.Table_Name
			+ "#" + IAttributeDAO.CACHEKEY_ATTRIBUTE_VALUE;

	private static final transient CCache<Integer, List<DimensionSpecGroup>> dimentsionSpecIdToGroups = CCache.newCache(
			GROUPS_CACHE_NAME,
			10,
			CCache.EXPIREMINUTES_Never);

	private static final String ATTRIBUTES_CACHE_NAME = "DimensionSpec#retrieveAttributes"
			+ "#" + I_DIM_Dimension_Spec.Table_Name
			+ "#" + I_DIM_Dimension_Spec_Attribute.Table_Name;

	private static final transient CCache<Integer, List<I_M_Attribute>> dimentsionSpecIdToAttributes = CCache.newCache(
			ATTRIBUTES_CACHE_NAME,
			10,
			CCache.EXPIREMINUTES_Never);

	private final I_DIM_Dimension_Spec dimensionSpecRecord;

	private DimensionSpec(final I_DIM_Dimension_Spec dimensionSpecRecord)
	{
		this.dimensionSpecRecord = dimensionSpecRecord;
	}

	/**
	 * Create a new {@link I_M_AttributeSetInstance} containing instances for relevant attributes in dimensionSpec and values from the given asi.<br>
	 * In other words, create a "projection" of the given asi, with respect to the given dimensionSpec.
	 *
	 * @return the new ASI if at least one of the relevant attribute/value couple in the given ASI, null otherwise
	 *
	 * @deprecated this method does not correctly handle dimensions with multiple M_AttributeValue_IDs in one group and is also only used by an oboslete feature.
	 */
	@Deprecated
	public I_M_AttributeSetInstance createASIForDimensionSpec(final I_M_AttributeSetInstance asi)
	{
		final IAttributeSetInstanceBL asiBL = Services.get(IAttributeSetInstanceBL.class);

		final List<KeyNamePair> attrToValues = retrieveAttritbuteIdToDisplayValue(asi);

		if (attrToValues.isEmpty())
		{
			// no relevant attribute was found. null asi
			return null;
		}

		final I_M_AttributeSetInstance newASI = InterfaceWrapperHelper.newInstance(I_M_AttributeSetInstance.class, asi);
		InterfaceWrapperHelper.save(newASI);

		for (final KeyNamePair attrToValue : attrToValues)
		{

			final AttributeId attributeId = AttributeId.ofRepoId(attrToValue.getKey());
			final AttributeSetInstanceId newASIId = AttributeSetInstanceId.ofRepoId(newASI.getM_AttributeSetInstance_ID());
			final I_M_AttributeInstance ai = asiBL.getCreateAttributeInstance(newASIId, attributeId);

			String attrValue = attrToValue.getName();

			if (DimensionConstants.DIM_EMPTY.equals(attrValue))
			{
				attrValue = Services.get(IMsgBL.class).getMsg(Env.getCtx(), DimensionConstants.MSG_NoneOrEmpty, true);
			}
			ai.setValue(attrValue);

			InterfaceWrapperHelper.save(ai);
		}
		return newASI;
	}

	/**
	 * Create {@link KeyNamePair}s of attribute IDs and values taken from the given <code>asi</code> that are relevant for the given dimensionSpec.
	 * In case of <code>null</code> asi or attributes not found or attributes with non relevant values, their values will be set to {@link DimensionConstants#DIM_EMPTY}.
	 *
	 * @deprecated this method does not correctly handle dimensions with multiple M_AttributeValue_IDs in one group and is also only used by an oboslete feature.
	 */
	@Deprecated
	public List<KeyNamePair> retrieveAttritbuteIdToDisplayValue(@Nullable final I_M_AttributeSetInstance asi)
	{

		final List<I_M_Attribute> attributes = retrieveAttributes();

		final ImmutableList.Builder<KeyNamePair> attributeIdToValues = ImmutableList.builder();

		for (final I_M_Attribute attribute : attributes)
		{
			final String displayNameOrNull = retrieveDisplayValueForAttribute(asi, attribute);
			if (displayNameOrNull != null)
			{
				attributeIdToValues.add(new KeyNamePair(attribute.getM_Attribute_ID(), displayNameOrNull));
			}
		}

		return attributeIdToValues.build();
	}

	/**
	 * @deprecated this method does not correctly handle dimensions with multiple M_AttributeValue_IDs in one group and is also only used by an obsolete feature.
	 */
	@Deprecated
	private String retrieveDisplayValueForAttribute(final I_M_AttributeSetInstance asi, final I_M_Attribute attribute)
	{
		final PlainContextAware ctxAware = PlainContextAware.newOutOfTrx();

		String attrValue = retrieveAttributeValueFromASI(attribute, asi);

		if (DimensionConstants.DIM_EMPTY.equals(attrValue))
		{
			// replace DIM_EMPTY with the text from MSG_NoneOrEmpty
			attrValue = Services.get(IMsgBL.class).getMsg(ctxAware.getCtx(), DimensionConstants.MSG_NoneOrEmpty, true);
		}
		final IDimensionspecDAO dimSpecDAO = Services.get(IDimensionspecDAO.class);
		final List<String> valueForGroup = dimSpecDAO.retrieveAttributeValueForGroup(dimensionSpecRecord.getInternalName(), attrValue, ctxAware);

		if (!valueForGroup.isEmpty())
		{
			return valueForGroup.get(0);
		}

		// fallback, in case the groupname was not found
		else if (dimensionSpecRecord.isIncludeEmpty())
		{
			return DimensionConstants.DIM_EMPTY;
		}

		return null;
	}

	/**
	 * @deprecated this method does not correctly handle dimensions with multiple M_AttributeValue_IDs in one group and is also only used by an obsolete feature.
	 */
	@Deprecated
	private static String retrieveAttributeValueFromASI(
			@NonNull final I_M_Attribute attribute,
			@Nullable final I_M_AttributeSetInstance asi)
	{
		if (asi == null
				|| asi.getM_AttributeSetInstance_ID() == AttributeConstants.M_AttributeSetInstance_ID_None)
		{
			return DimensionConstants.DIM_EMPTY;
		}

		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

		final AttributeId attributeId = AttributeId.ofRepoId(attribute.getM_Attribute_ID());
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID());
		final I_M_AttributeInstance attributeInstance = attributeDAO.retrieveAttributeInstance(asiId, attributeId);
		if (attributeInstance == null)
		{
			return DimensionConstants.DIM_EMPTY;
		}

		final AttributeValueId attributeValueId = AttributeValueId.ofRepoIdOrNull(attributeInstance.getM_AttributeValue_ID());
		if (attributeValueId != null)
		{
			final AttributeListValue attributeValue = attributeDAO.retrieveAttributeValueOrNull(attributeId, attributeValueId);

			final String nameOrValue = !Check.isEmpty(attributeValue.getName())
					? attributeValue.getName()
					: attributeValue.getValue();
			if (!Check.isEmpty(nameOrValue))
			{
				return nameOrValue;
			}
		}

		final String value = attributeInstance.getValue();
		if (value != null)
		{
			return value;
		}

		final BigDecimal valueNumber = attributeInstance.getValueNumber();

		if (valueNumber != null)
		{
			return valueNumber.toString();
		}

		return DimensionConstants.DIM_EMPTY;
	}

	public List<I_M_Attribute> retrieveAttributes()
	{
		return dimentsionSpecIdToAttributes
				.getOrLoad(dimensionSpecRecord.getDIM_Dimension_Spec_ID(), this::retrieveAttributesForDimensionSpec);
	}

	private List<I_M_Attribute> retrieveAttributesForDimensionSpec(final int dimensionSpecRecordId)
	{
		final ImmutableSet<AttributeId> attributeIds = retrieveAttributeIdsForDimensionSpec(dimensionSpecRecordId);
		return Services.get(IAttributeDAO.class).getAttributesByIds(attributeIds);
	}

	private ImmutableSet<AttributeId> retrieveAttributeIdsForDimensionSpec(final int dimensionSpecRecordId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_DIM_Dimension_Spec_Attribute.class)
				.addEqualsFilter(I_DIM_Dimension_Spec_Attribute.COLUMNNAME_DIM_Dimension_Spec_ID, dimensionSpecRecordId)
				.addOnlyActiveRecordsFilter()
				.create()
				.listDistinct(I_DIM_Dimension_Spec_Attribute.COLUMNNAME_M_Attribute_ID, Integer.class)
				.stream()
				.map(AttributeId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public List<DimensionSpecGroup> retrieveGroups()
	{
		return dimentsionSpecIdToGroups
				.getOrLoad(dimensionSpecRecord.getDIM_Dimension_Spec_ID(), () -> retrieveGroupsFordimensionSpec(dimensionSpecRecord));
	}

	private static List<DimensionSpecGroup> retrieveGroupsFordimensionSpec(
			@NonNull final I_DIM_Dimension_Spec dimensionSpecRecord)
	{

		final List<I_DIM_Dimension_Spec_Attribute> attrs = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DIM_Dimension_Spec_Attribute.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DIM_Dimension_Spec_Attribute.COLUMN_DIM_Dimension_Spec_ID, dimensionSpecRecord.getDIM_Dimension_Spec_ID())
				.orderBy(I_DIM_Dimension_Spec_Attribute.COLUMNNAME_M_Attribute_ID) // important to get a correct AttributesKey
				.create()
				.list();

		final Multimap<IPair<String, AttributeId>, AttributeValueId> groupName2AttributeValueIds = mapGroupNamesToAttributeValues(attrs);

		final Builder<DimensionSpecGroup> groupList = ImmutableList.builder();
		if (dimensionSpecRecord.isIncludeEmpty())
		{
			groupList.add(DimensionSpecGroup.EMPTY_GROUP);
		}
		if (dimensionSpecRecord.isIncludeOtherGroup())
		{
			groupList.add(DimensionSpecGroup.OTHER_GROUP);
		}

		sortAndAddMapEntriesToList(groupName2AttributeValueIds, groupList);

		return groupList.build();
	}

	private static Multimap<IPair<String, AttributeId>, AttributeValueId> mapGroupNamesToAttributeValues(
			@NonNull final List<I_DIM_Dimension_Spec_Attribute> attrs)
	{
		final Multimap<IPair<String, AttributeId>, AttributeValueId> groupName2AttributeValues = ArrayListMultimap.create();

		for (final I_DIM_Dimension_Spec_Attribute dsa : attrs)
		{
			if (dsa.isIncludeAllAttributeValues())
			{
				addAllAttributeValuesToMap(dsa, groupName2AttributeValues);
			}
			else
			{
				addIndividualAttributeValuestoMap(dsa, groupName2AttributeValues);
			}
		}
		return groupName2AttributeValues;
	}

	private static void addAllAttributeValuesToMap(
			@NonNull final I_DIM_Dimension_Spec_Attribute dimensionspecAttribute,
			@NonNull final Multimap<IPair<String, AttributeId>, AttributeValueId> groupName2AttributeValues)
	{
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

		final AttributeId attributeId = AttributeId.ofRepoId(dimensionspecAttribute.getM_Attribute_ID());
		final List<AttributeListValue> attributeValues = attributeDAO.retrieveAttributeValuesByAttributeId(attributeId);

		for (final AttributeListValue attributeValue : attributeValues)
		{
			final String groupName = dimensionspecAttribute.isValueAggregate()
					? dimensionspecAttribute.getValueAggregateName()
					: attributeValue.getName();

			final ImmutablePair<String, AttributeId> key = ImmutablePair.of(groupName, attributeId);
			groupName2AttributeValues.put(key, attributeValue.getId());
		}
	}

	private static void addIndividualAttributeValuestoMap(
			@NonNull final I_DIM_Dimension_Spec_Attribute dimensionSpecAttribute,
			@NonNull final Multimap<IPair<String, AttributeId>, AttributeValueId> groupName2AttributeValues)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final List<I_DIM_Dimension_Spec_AttributeValue> attrValues = queryBL
				.createQueryBuilder(I_DIM_Dimension_Spec_AttributeValue.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(
						I_DIM_Dimension_Spec_AttributeValue.COLUMN_DIM_Dimension_Spec_Attribute_ID,
						dimensionSpecAttribute.getDIM_Dimension_Spec_Attribute_ID())
				.create()
				.list();

		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
		final AttributeId attributeId = AttributeId.ofRepoId(dimensionSpecAttribute.getM_Attribute_ID());

		for (final I_DIM_Dimension_Spec_AttributeValue attrValue : attrValues)
		{
			final AttributeValueId attributeValueId = AttributeValueId.ofRepoId(attrValue.getM_AttributeValue_ID());

			final String groupName;
			if (dimensionSpecAttribute.isValueAggregate())
			{
				groupName = dimensionSpecAttribute.getValueAggregateName();
			}
			else
			{
				final AttributeListValue attributeListValue = attributesRepo.retrieveAttributeValueOrNull(attributeId, attributeValueId);
				groupName = attributeListValue.getName();
			}

			final ImmutablePair<String, AttributeId> key = ImmutablePair.of(groupName, attributeId);
			groupName2AttributeValues.put(key, attributeValueId);
		}
	}

	private static void sortAndAddMapEntriesToList(
			@NonNull final Multimap<IPair<String, AttributeId>, AttributeValueId> groupNameToAttributeValueIds,
			@NonNull final Builder<DimensionSpecGroup> list)
	{
		final Collection<Entry<IPair<String, AttributeId>, Collection<AttributeValueId>>> //
		entrySet = groupNameToAttributeValueIds.asMap().entrySet();

		final ArrayList<DimensionSpecGroup> newGroups = new ArrayList<>();
		for (final Entry<IPair<String, AttributeId>, Collection<AttributeValueId>> entry : entrySet)
		{
			final String groupName = entry.getKey().getLeft();
			final ITranslatableString groupNameTrl = TranslatableStrings.constant(groupName);
			final Optional<AttributeId> groupAttributeId = Optional.ofNullable(entry.getKey().getRight());
			final AttributesKey attributesKey = createAttributesKey(entry.getValue());

			final DimensionSpecGroup newGroup = new DimensionSpecGroup(
					() -> groupNameTrl,
					attributesKey,
					groupAttributeId);
			newGroups.add(newGroup);
		}
		
		newGroups.sort(Comparator.comparing(DimensionSpecGroup::getAttributesKey));
		list.addAll(newGroups);
	}

	private static AttributesKey createAttributesKey(final Collection<AttributeValueId> values)
	{
		return AttributesKey.ofParts(
				values.stream()
						.map(AttributesKeyPart::ofAttributeValueId)
						.collect(ImmutableSet.toImmutableSet()));
	}
}
