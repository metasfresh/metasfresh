package de.metas.handlingunits.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;

import com.google.common.collect.ImmutableList;

import de.metas.dimension.DimensionSpec;
import de.metas.dimension.IDimensionspecDAO;
import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

/**
 * {@link HUQueryBuilder} attributes related filtering
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@EqualsAndHashCode
@ToString(of = { "onlyAttributes", "barcode" })
final class HUQueryBuilder_Attributes
{
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	private final HashMap<AttributeId, HUAttributeQueryFilterVO> onlyAttributes;
	private boolean allowSql = true;
	private String barcode;

	public HUQueryBuilder_Attributes()
	{
		onlyAttributes = new HashMap<>();
		barcode = null;
	}

	private HUQueryBuilder_Attributes(final HUQueryBuilder_Attributes from)
	{
		onlyAttributes = deepCopy(from.onlyAttributes);
		barcode = from.barcode;
		allowSql = from.allowSql;
	}

	public HUQueryBuilder_Attributes copy()
	{
		return new HUQueryBuilder_Attributes(this);
	}

	private static HashMap<AttributeId, HUAttributeQueryFilterVO> deepCopy(final HashMap<AttributeId, HUAttributeQueryFilterVO> map)
	{
		final HashMap<AttributeId, HUAttributeQueryFilterVO> copy = new HashMap<>(map.size());

		for (final Map.Entry<AttributeId, HUAttributeQueryFilterVO> e : map.entrySet())
		{
			final AttributeId attributeId = e.getKey();
			final HUAttributeQueryFilterVO attributeFilterVO = e.getValue();
			final HUAttributeQueryFilterVO attributeFilterVOCopy = attributeFilterVO == null ? null : attributeFilterVO.copy();
			copy.put(attributeId, attributeFilterVOCopy);
		}

		return copy;
	}

	public ICompositeQueryFilter<I_M_HU> createQueryFilter()
	{
		final ICompositeQueryFilter<I_M_HU> filters = queryBL.createCompositeQueryFilter(I_M_HU.class);

		final ICompositeQueryFilter<I_M_HU> onlyAttributesFilter = createQueryFilter_OnlyAttributes();
		if (onlyAttributesFilter != null && !onlyAttributesFilter.isEmpty())
		{
			filters.addFilter(onlyAttributesFilter);
		}

		final ICompositeQueryFilter<I_M_HU> barcodeFilter = createQueryFilter_Barcode();
		if (barcodeFilter != null && !barcodeFilter.isEmpty())
		{
			filters.addFilter(barcodeFilter);
		}

		return filters;
	}

	private ICompositeQueryFilter<I_M_HU> createQueryFilter_OnlyAttributes()
	{
		if (onlyAttributes.isEmpty())
		{
			return null;
		}

		final ICompositeQueryFilter<I_M_HU> filters = queryBL.createCompositeQueryFilter(I_M_HU.class);

		// Iterate attribute filters and add a restriction for each of them
		// because each of them needs to be individually valid
		for (final HUAttributeQueryFilterVO attributeFilterVO : onlyAttributes.values())
		{
			attributeFilterVO.appendQueryFilterTo(filters);
		}

		return filters;
	}

	private ICompositeQueryFilter<I_M_HU> createQueryFilter_Barcode()
	{
		if (Check.isEmpty(barcode, true))
		{
			return null;
		}

		final ICompositeQueryFilter<I_M_HU> filters = queryBL.createCompositeQueryFilter(I_M_HU.class);

		final Collection<HUAttributeQueryFilterVO> barcodeQueryFilterVOs = createBarcodeHUAttributeQueryFilterVOs();
		if (!barcodeQueryFilterVOs.isEmpty())
		{
			final ICompositeQueryFilter<I_M_HU> barcodeFilter = queryBL.createCompositeQueryFilter(I_M_HU.class);

			// an HU will be barcode-identified either if it has barcode attributes or value with the value inserted as barcode
			barcodeFilter.setJoinOr();
			barcodeFilter.addEqualsFilter(I_M_HU.COLUMN_Value, barcode.trim());

			for (final HUAttributeQueryFilterVO attributeFilterVO : barcodeQueryFilterVOs)
			{
				attributeFilterVO.appendQueryFilterTo(barcodeFilter);
			}

			filters.addFilter(barcodeFilter);
		}
		// task #827 filter by hu value, as before
		else
		{
			filters.addEqualsFilter(I_M_HU.COLUMN_Value, barcode.trim());
		}

		return filters;
	}

	public String getAttributesSummary()
	{
		if (onlyAttributes.isEmpty())
		{
			return "";
		}

		final StringBuilder sb = new StringBuilder();
		for (final HUAttributeQueryFilterVO attributeFilterVO : onlyAttributes.values())
		{
			final String attributeSummary = attributeFilterVO.getSummary();
			if (Check.isEmpty(attributeSummary, true))
			{
				continue;
			}
			if (sb.length() > 0)
			{
				sb.append("\n");
			}
			sb.append(attributeSummary);
		}
		return sb.toString();
	}

	public void addOnlyWithAttribute(final I_M_Attribute attribute, final Object value)
	{
		final HUAttributeQueryFilterVO attributeFilterVO = getOrCreateAttributeFilterVO(attribute, HUAttributeQueryFilterVO.ATTRIBUTEVALUETYPE_Unknown);
		attributeFilterVO.addValue(value);
	}

	public void addOnlyWithAttribute(final AttributeCode attributeCode, final Object value)
	{
		final I_M_Attribute attribute = Services.get(IAttributeDAO.class).retrieveAttributeByValue(attributeCode, I_M_Attribute.class);
		addOnlyWithAttribute(attribute, value);
	}

	public void addOnlyWithAttribute(final AttributeId attributeId, final Object value)
	{
		final I_M_Attribute attribute = Services.get(IAttributeDAO.class).getAttributeById(attributeId);
		addOnlyWithAttribute(attribute, value);
	}

	public void addOnlyWithAttributeInList(final I_M_Attribute attribute, final String attributeValueType, @NonNull final List<? extends Object> values)
	{
		Check.assumeNotNull(values, "values not null");
		final HUAttributeQueryFilterVO attributeFilterVO = getOrCreateAttributeFilterVO(attribute, attributeValueType);
		attributeFilterVO.addValues(values);
	}

	public void addOnlyWithAttributeInList(final AttributeCode attributeCode, final Object... values)
	{
		final I_M_Attribute attribute = Services.get(IAttributeDAO.class).retrieveAttributeByValue(attributeCode);
		final List<Object> valuesAsList = Arrays.asList(values);
		addOnlyWithAttributeInList(attribute, HUAttributeQueryFilterVO.ATTRIBUTEVALUETYPE_Unknown, valuesAsList);
	}

	public void addOnlyWithAttributeNotNull(final AttributeCode attributeCode)
	{
		final I_M_Attribute attribute = Services.get(IAttributeDAO.class).retrieveAttributeByValue(attributeCode);
		getOrCreateAttributeFilterVO(attribute, HUAttributeQueryFilterVO.ATTRIBUTEVALUETYPE_Unknown)
				.setMatchingType(HUAttributeQueryFilterVO.AttributeValueMatchingType.NotNull);
	}

	public void addOnlyWithAttributeMissingOrNull(final AttributeCode attributeCode)
	{
		final I_M_Attribute attribute = Services.get(IAttributeDAO.class).retrieveAttributeByValue(attributeCode);
		getOrCreateAttributeFilterVO(attribute, HUAttributeQueryFilterVO.ATTRIBUTEVALUETYPE_Unknown)
				.setMatchingType(HUAttributeQueryFilterVO.AttributeValueMatchingType.MissingOrNull);
	}

	public void addOnlyWithAttributes(ImmutableAttributeSet attributeSet)
	{
		for (final I_M_Attribute attribute : attributeSet.getAttributes())
		{
			final Object value = attributeSet.getValue(attribute);
			addOnlyWithAttribute(attribute, value);
		}
	}

	private HUAttributeQueryFilterVO getOrCreateAttributeFilterVO(final I_M_Attribute attribute, final String attributeValueType)
	{
		return getOrCreateAttributeFilterVO(onlyAttributes, attribute, attributeValueType);
	}

	private HUAttributeQueryFilterVO getOrCreateAttributeFilterVO(
			@NonNull final Map<AttributeId, HUAttributeQueryFilterVO> targetMap,
			@NonNull final I_M_Attribute attribute,
			final String attributeValueType)
	{
		final HUAttributeQueryFilterVO attributeFilterVO = targetMap.computeIfAbsent(
				AttributeId.ofRepoId(attribute.getM_Attribute_ID()),
				k -> new HUAttributeQueryFilterVO(attribute, attributeValueType));

		if (attributeValueType != HUAttributeQueryFilterVO.ATTRIBUTEVALUETYPE_Unknown)
		{
			attributeFilterVO.assertAttributeValueType(attributeValueType);
		}

		return attributeFilterVO;
	}

	public boolean matches(@NonNull final IAttributeSet attributes)
	{
		// If there is no attribute restrictions we can accept this "attributes" right away
		if (onlyAttributes == null || onlyAttributes.isEmpty())
		{
			return true;
		}

		for (final HUAttributeQueryFilterVO attributeFilter : onlyAttributes.values())
		{
			if (!attributeFilter.matches(attributes))
			{
				return false;
			}
		}

		return true;
	}

	public void setBarcode(final String barcode)
	{
		this.barcode = barcode != null ? barcode.trim() : null;
	}

	private Collection<HUAttributeQueryFilterVO> createBarcodeHUAttributeQueryFilterVOs()
	{
		if (Check.isEmpty(barcode, true))
		{
			return ImmutableList.of();
		}

		final HashMap<AttributeId, HUAttributeQueryFilterVO> filterVOs = new HashMap<>();
		for (final I_M_Attribute attribute : getBarcodeAttributes())
		{
			final HUAttributeQueryFilterVO barcodeAttributeFilterVO = getOrCreateAttributeFilterVO(
					filterVOs,
					attribute,
					HUAttributeQueryFilterVO.ATTRIBUTEVALUETYPE_Unknown);
			barcodeAttributeFilterVO.addValue(barcode);
		}

		return filterVOs.values();
	}

	private List<I_M_Attribute> getBarcodeAttributes()
	{
		final IDimensionspecDAO dimensionSpecsRepo = Services.get(IDimensionspecDAO.class);
		final DimensionSpec barcodeDimSpec = dimensionSpecsRepo.retrieveForInternalNameOrNull(HUConstants.DIM_Barcode_Attributes);
		if (barcodeDimSpec == null)
		{
			return ImmutableList.of(); // no barcode dimension spec. Nothing to do
		}

		return barcodeDimSpec.retrieveAttributes()
				.stream()
				// Barcode must be a String attribute. In the database, this is forced by a validation rule
				.filter(attribute -> attribute.getAttributeValueType().equals(X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40))
				.collect(ImmutableList.toImmutableList());
	}

	public void setAllowSql(final boolean allowSql)
	{
		this.allowSql = allowSql;
	}
}
