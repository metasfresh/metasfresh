package de.metas.dimension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import org.adempiere.ad.dao.IQueryBL;
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
import lombok.Value;

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
	 * message for non or empty attribute value
	 */
	public static final String MSG_NoneOrEmpty = "NoneOrEmpty";

	/**
	 * Create a new {@link I_M_AttributeSetInstance} containing instances for relevant attributes in dimensionSpec and values from the given asi.<br>
	 * In other words, create a "projection" of the given asi, with respect to the given dimensionSpec.
	 *
	 * @param asi
	 * @param dimensionSpec
	 * @return the new ASI if at least one of the relevant attribute/value couple in the given ASI, null otherwise
	 */
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
				attrValue = Services.get(IMsgBL.class).getMsg(Env.getCtx(), MSG_NoneOrEmpty, true);
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
	 */
	public List<KeyNamePair> retrieveAttritbuteIdToDisplayValue(final I_M_AttributeSetInstance asi)
	{
		final IDimensionspecDAO dimSpecDAO = Services.get(IDimensionspecDAO.class);

		final PlainContextAware ctxAware = PlainContextAware.newOutOfTrx();

		final List<I_M_Attribute> dimAttrs = retrieveAttributes();

		final List<KeyNamePair> attributeIdToValues = new ArrayList<>();

		for (final I_M_Attribute attribute : dimAttrs)
		{
			String attrValue = getAttrValueFromASI(attribute, asi);

			if (DimensionConstants.DIM_EMPTY.equals(attrValue))
			{
				// replace DIM_EMPTY with the text from MSG_NoneOrEmpty
				attrValue = Services.get(IMsgBL.class).getMsg(ctxAware.getCtx(), MSG_NoneOrEmpty, true);
			}
			final List<String> valueForGroup = dimSpecDAO.retrieveAttributeValueForGroup(dimensionSpecRecord.getInternalName(), attrValue, ctxAware);

			if (!valueForGroup.isEmpty())
			{
				attributeIdToValues.add(new KeyNamePair(attribute.getM_Attribute_ID(), valueForGroup.get(0)));
			}

			// fallback, in case the groupname was not found
			else if (dimensionSpecRecord.isIncludeEmpty())
			{
				attributeIdToValues.add(new KeyNamePair(attribute.getM_Attribute_ID(), DimensionConstants.DIM_EMPTY));
			}
		}

		return attributeIdToValues;
	}

	private static String getAttrValueFromASI(final I_M_Attribute attribute, final I_M_AttributeSetInstance asi)
	{
		final IAttributeDAO attrDAO = Services.get(IAttributeDAO.class);

		if (asi == null)
		{
			return DimensionConstants.DIM_EMPTY;
		}

		final String trxName = InterfaceWrapperHelper.getTrxName(asi);

		final I_M_AttributeInstance attributeInstance = attrDAO.retrieveAttributeInstance(asi, attribute.getM_Attribute_ID(), trxName);

		if (attributeInstance == null)
		{
			return DimensionConstants.DIM_EMPTY;
		}

		final I_M_AttributeValue attrValue = attributeInstance.getM_AttributeValue();

		if (attrValue != null)
		{
			final String value = attrValue.getName() == null ? attrValue.getValue() : attrValue.getName();

			if (value != null)
			{
				return value;
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
				.getOrLoad(dimensionSpecRecord.getDIM_Dimension_Spec_ID(), () -> retrieveGroups(dimensionSpecRecord));
	}

	private static List<DimensionSpecGroup> retrieveGroups(@NonNull final I_DIM_Dimension_Spec dimensionSpecRecord)
	{
		final Builder<DimensionSpecGroup> builder = ImmutableList.builder();
		if (dimensionSpecRecord.isIncludeEmpty())
		{
			builder.add(DimensionSpecGroup.EMPTY_GROUP);
		}

		final List<I_DIM_Dimension_Spec_Attribute> attrs = Services.get(IQueryBL.class).createQueryBuilder(I_DIM_Dimension_Spec_Attribute.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DIM_Dimension_Spec_Attribute.COLUMN_DIM_Dimension_Spec_ID, dimensionSpecRecord.getDIM_Dimension_Spec_ID())
				.orderBy().addColumn(I_DIM_Dimension_Spec_Attribute.COLUMN_M_Attribute_ID).endOrderBy() // important to get a correct AttributesKey
				.create()
				.list();

		final Multimap<String, Integer> groupName2AttributeValues = ArrayListMultimap.create();

		for (final I_DIM_Dimension_Spec_Attribute dsa : attrs)
		{
			if (dsa.isIncludeAllAttributeValues())
			{
				final List<I_M_AttributeValue> attributeValues = Services.get(IAttributeDAO.class)
						.retrieveAttributeValues(dsa.getM_Attribute());
				for (final I_M_AttributeValue attributeValue : attributeValues)
				{
					final String groupName = dsa.isValueAggregate() ? dsa.getValueAggregateName() : attributeValue.getName();
					groupName2AttributeValues.put(groupName, attributeValue.getM_AttributeValue_ID());
				}
			}
			else
			{
				final List<I_DIM_Dimension_Spec_AttributeValue> attrValues = Services.get(IQueryBL.class)
						.createQueryBuilder(I_DIM_Dimension_Spec_AttributeValue.class)
						.addOnlyActiveRecordsFilter()
						.addEqualsFilter(I_DIM_Dimension_Spec_AttributeValue.COLUMN_DIM_Dimension_Spec_Attribute_ID, dsa.getDIM_Dimension_Spec_Attribute_ID())
						.create()
						.list();
				for (final I_DIM_Dimension_Spec_AttributeValue attrValue : attrValues)
				{
					final String groupName = dsa.isValueAggregate() ? dsa.getValueAggregateName() : attrValue.getM_AttributeValue().getName();
					groupName2AttributeValues.put(groupName, attrValue.getM_AttributeValue_ID());
				}
			}
		}

		final IMsgBL msgBL = Services.get(IMsgBL.class);

		groupName2AttributeValues.asMap().entrySet().stream()
				.sorted(Comparator.comparing(Entry::getKey))
				.forEach(entry -> {

					final ITranslatableString groupName = msgBL.getTranslatableMsgText(entry.getKey());
					final boolean emptyGroup = false;

					builder.add(new DimensionSpecGroup(
							groupName,
							AttributesKey.ofAttributeValueIds(entry.getValue()),
							emptyGroup));
				});

		return builder.build();
	}

	@Value
	public static class DimensionSpecGroup
	{
		public static DimensionSpecGroup EMPTY_GROUP = new DimensionSpecGroup(
				Services.get(IMsgBL.class).getTranslatableMsgText(MSG_NoneOrEmpty),
				AttributesKey.NONE,
				true);

		@NonNull
		ITranslatableString groupName;

		/**
		 * These {@code M_AttributeValue_ID}s belong to this group. Maybe empty, often has just one element.<br>
		 * Might later be abstracted away so that other kind of data (e.g. "BPartners") could also be mapped to a dimension group.
		 */
		@NonNull
		AttributesKey attributesKey;

		boolean emptyGroup;
	}

}
