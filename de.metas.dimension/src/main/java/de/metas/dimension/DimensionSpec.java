package de.metas.dimension;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.Multimap;

import de.metas.dimension.model.I_DIM_Dimension_Spec;
import de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute;
import de.metas.dimension.model.I_DIM_Dimension_Spec_AttributeValue;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.material.event.commons.AttributesKey;
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
			+ "#" + I_M_AttributeValue.Table_Name;

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
	 * @param asi
	 * @param dimensionSpec
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
			final I_M_AttributeInstance ai = asiBL.getCreateAttributeInstance(newASI, attrToValue.getKey());

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
	 * @param asi
	 * @param dimensionSpec
	 * @return
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
		final I_M_AttributeInstance attributeInstance = attributeDAO.retrieveAttributeInstance(asi, attribute.getM_Attribute_ID());
		if (attributeInstance == null)
		{
			return DimensionConstants.DIM_EMPTY;
		}

		final I_M_AttributeValue attributeValue = attributeInstance.getM_AttributeValue();
		if (attributeValue != null)
		{
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
				.getOrLoad(dimensionSpecRecord.getDIM_Dimension_Spec_ID(), () -> retrieveAttributesForDimensionSpec(dimensionSpecRecord));
	}

	private static List<I_M_Attribute> retrieveAttributesForDimensionSpec(@NonNull final I_DIM_Dimension_Spec dimensionSpecRecord)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_DIM_Dimension_Spec_Attribute.class)
				.addEqualsFilter(I_DIM_Dimension_Spec_Attribute.COLUMNNAME_DIM_Dimension_Spec_ID,
						dimensionSpecRecord.getDIM_Dimension_Spec_ID())
				.addOnlyActiveRecordsFilter()
				.andCollect(I_DIM_Dimension_Spec_Attribute.COLUMN_M_Attribute_ID, I_M_Attribute.class)

				// important to get a correct AttributesKey *if* we used these attributes for that purpose
				.orderBy().addColumn(I_M_Attribute.COLUMN_M_Attribute_ID).endOrderBy()
				.create()
				.list(I_M_Attribute.class);
	}

	public List<DimensionSpecGroup> retrieveGroups()
	{
		return dimentsionSpecIdToGroups
				.getOrLoad(dimensionSpecRecord.getDIM_Dimension_Spec_ID(), () -> retrieveGroupsFordimensionSpec(dimensionSpecRecord));
	}

	private static List<DimensionSpecGroup> retrieveGroupsFordimensionSpec(
			@NonNull final I_DIM_Dimension_Spec dimensionSpecRecord)
	{

		final List<I_DIM_Dimension_Spec_Attribute> attrs = Services.get(IQueryBL.class).createQueryBuilder(I_DIM_Dimension_Spec_Attribute.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DIM_Dimension_Spec_Attribute.COLUMN_DIM_Dimension_Spec_ID, dimensionSpecRecord.getDIM_Dimension_Spec_ID())
				.orderBy().addColumn(I_DIM_Dimension_Spec_Attribute.COLUMN_M_Attribute_ID).endOrderBy() // important to get a correct AttributesKey
				.create()
				.list();

		final Multimap<String, Integer> groupName2AttributeValueIds = mapGroupNamesToAttributeValues(attrs);

		final Builder<DimensionSpecGroup> groupList = ImmutableList.builder();
		if (dimensionSpecRecord.isIncludeEmpty())
		{
			groupList.add(DimensionSpecGroup.EMPTY_GROUP);
		}
		if(dimensionSpecRecord.isIncludeOtherGroup())
		{
			groupList.add(DimensionSpecGroup.OTHER_GROUP);
		}

		sortAndAddMapEntriesToList(groupName2AttributeValueIds, groupList);

		return groupList.build();
	}

	private static Multimap<String, Integer> mapGroupNamesToAttributeValues(
			@NonNull final List<I_DIM_Dimension_Spec_Attribute> attrs)
	{
		final Multimap<String, Integer> groupName2AttributeValues = ArrayListMultimap.create();

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
			@NonNull final I_DIM_Dimension_Spec_Attribute dimenstionspecAttribute,
			@NonNull final Multimap<String, Integer> groupName2AttributeValues)
	{
		final List<I_M_AttributeValue> attributeValues = Services.get(IAttributeDAO.class)
				.retrieveAttributeValues(dimenstionspecAttribute.getM_Attribute());
		for (final I_M_AttributeValue attributeValue : attributeValues)
		{
			final String groupName = dimenstionspecAttribute.isValueAggregate()
					? dimenstionspecAttribute.getValueAggregateName()
					: attributeValue.getName();
			groupName2AttributeValues.put(groupName, attributeValue.getM_AttributeValue_ID());
		}
	}

	private static void addIndividualAttributeValuestoMap(
			@NonNull final I_DIM_Dimension_Spec_Attribute dimensionSpecAttribute,
			@NonNull final Multimap<String, Integer> groupName2AttributeValues)
	{
		final List<I_DIM_Dimension_Spec_AttributeValue> attrValues = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DIM_Dimension_Spec_AttributeValue.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(
						I_DIM_Dimension_Spec_AttributeValue.COLUMN_DIM_Dimension_Spec_Attribute_ID,
						dimensionSpecAttribute.getDIM_Dimension_Spec_Attribute_ID())
				.create()
				.list();

		for (final I_DIM_Dimension_Spec_AttributeValue attrValue : attrValues)
		{
			final String groupName = dimensionSpecAttribute.isValueAggregate()
					? dimensionSpecAttribute.getValueAggregateName()
					: attrValue.getM_AttributeValue().getName();
			groupName2AttributeValues.put(groupName, attrValue.getM_AttributeValue_ID());
		}
	}

	private static void sortAndAddMapEntriesToList(
			@NonNull final Multimap<String, Integer> groupNameToAttributeValueIds,
			@NonNull final Builder<DimensionSpecGroup> list)
	{
		groupNameToAttributeValueIds.asMap().entrySet().stream()
				.sorted(Comparator.comparing(Entry::getKey))
				.forEach(entry -> {

					final ITranslatableString groupName = ITranslatableString.constant(entry.getKey());
					final AttributesKey attributesKey = AttributesKey.ofAttributeValueIds(entry.getValue());

					final DimensionSpecGroup newGroup = new DimensionSpecGroup(
							groupName,
							attributesKey);
					list.add(newGroup);
				});
	}

}
