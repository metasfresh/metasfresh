package org.adempiere.mm.attributes.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.metas.lang.SOTrx;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetDescriptor;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.Attribute;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeExcludeBL;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.compiere.model.I_M_AttributeSetExclude;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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

/**
 * Helper class used to provide informations to the ASI editor.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public final class ASIEditingInfo
{
	public static ASIEditingInfo of(
			final ProductId productId,
			final AttributeSetInstanceId attributeSetInstanceId,
			final String callerTableName,
			final int callerColumnId,
			final SOTrx soTrx)
	{
		return builder()
				.productId(productId)
				.attributeSetInstanceId(attributeSetInstanceId)
				.callerTableName(callerTableName)
				.callerColumnId(callerColumnId)
				.soTrx(soTrx)
				.build();
	}

	// services
	private final IAttributeSetInstanceBL asiBL = Services.get(IAttributeSetInstanceBL.class);
	private final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
	private final IAttributeExcludeBL attributeExcludeBL = Services.get(IAttributeExcludeBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	// Parameters
	public enum WindowType
	{
		Regular, ProductWindow, ProcessParameter, Pricing, StrictASIAttributes
	}

	private final WindowType _type;
	private final ProductId _productId;
	private final AttributeSetInstanceId _attributeSetInstanceId;
	private final String _callerTableName;
	private final int _calledColumnId;
	private final SOTrx _soTrx;

	// Deducted values
	private final AttributeSetDescriptor _attributeSet;
	private ImmutableList<Attribute> _availableAttributes;

	@Nullable
	private final I_M_AttributeSetInstance _attributeSetInstance;

	private final boolean _allowSelectExistingASI;

	@Builder
	private ASIEditingInfo(
			final WindowType type,
			final ProductId productId,
			final AttributeSetInstanceId attributeSetInstanceId,
			final String callerTableName,
			final int callerColumnId,
			@NonNull final SOTrx soTrx)
	{
		// Parameters, must be set first
		_type = type != null ? type : extractType(callerTableName, callerColumnId);
		_productId = productId;
		_attributeSetInstanceId = attributeSetInstanceId;
		_callerTableName = callerTableName;
		_calledColumnId = callerColumnId;
		_soTrx = soTrx;

		// Deducted values, we assume params are set
		_attributeSet = retrieveAttributeSetOrNull();
		if (attributeSetInstanceId == null || attributeSetInstanceId.isNone())
		{
			_attributeSetInstance = null;
		}
		else
		{
			_attributeSetInstance = asiBL.getById(attributeSetInstanceId);
		}

		//
		// Flags
		_allowSelectExistingASI = _type == WindowType.Regular;

	}

	private static WindowType extractType(final String callerTableName, final int callerColumnId)
	{
		if (I_M_Product.Table_Name.equals(callerTableName)) // FIXME HARDCODED: M_Product.M_AttributeSetInstance_ID's AD_Column_ID = 8418
		{
			return WindowType.ProductWindow;
		}
		else if (I_M_ProductPrice.Table_Name.equals(callerTableName)) // FIXME HARDCODED: M_ProductPrice.M_AttributeSetInstance_ID's AD_Column_ID = 556075
		{
			return WindowType.Pricing;
		}
		else if (Check.isEmpty(callerTableName, true))
		{
			return WindowType.ProcessParameter;
		}
		else
		{
			return WindowType.Regular;
		}
	}

	public boolean isAllowSelectExistingASI()
	{
		return _allowSelectExistingASI;
	}

	public WindowType getWindowType()
	{
		return _type;
	}

	public ProductId getProductId()
	{
		return _productId;
	}

	public AttributeSetInstanceId getAttributeSetInstanceId()
	{
		return _attributeSetInstanceId;
	}

	@Nullable
	public I_M_AttributeSetInstance getM_AttributeSetInstance()
	{
		return _attributeSetInstance;
	}

	public String getCallerTableName()
	{
		return _callerTableName;
	}

	public int getCallerColumnId()
	{
		return _calledColumnId;
	}

	public SOTrx getSOTrx()
	{
		return _soTrx;
	}

	public boolean isSOTrx()
	{
		return getSOTrx().toBoolean();
	}

	@Nullable
	private AttributeSetDescriptor getM_AttributeSet()
	{
		return _attributeSet;
	}

	@NonNull
	public AttributeSetId getAttributeSetId()
	{
		final AttributeSetDescriptor attributeSet = getM_AttributeSet();
		return attributeSet != null ? attributeSet.getAttributeSetId() : AttributeSetId.NONE;
	}

	@NonNull
	public String getM_AttributeSet_Name()
	{
		final AttributeSetDescriptor attributeSet = getM_AttributeSet();
		return attributeSet == null ? "" : attributeSet.getName();
	}

	@Nullable
	public String getM_AttributeSet_Description()
	{
		final AttributeSetDescriptor attributeSet = getM_AttributeSet();
		return attributeSet == null ? "" : attributeSet.getDescription();
	}

	private AttributeSetDescriptor retrieveAttributeSetOrNull()
	{
		final WindowType windowType = getWindowType();

		final AttributeSetDescriptor attributeSet;
		switch (windowType)
		{
			case Regular:
			{
				attributeSet = retrieveProductAttributeSetOrNull();
				// Product has no Instance Attributes
				if (attributeSet != null && !attributeSet.isInstanceAttribute())
				{
					throw new AdempiereException("@PAttributeNoInstanceAttribute@");
				}
				break;
			}
			case ProductWindow:
			{
				attributeSet = retrieveProductMasterDataSchema();
				break;
			}
			case ProcessParameter:
			{
				final AttributeSetDescriptor productAttributeSet = retrieveProductAttributeSetOrNull();
				if (productAttributeSet != null)
				{
					attributeSet = productAttributeSet;
					break;
				}

				final I_M_AttributeSetInstance asi = getM_AttributeSetInstance();
				attributeSet = getAttributeSet(asi);
				break;
			}
			case Pricing:
			{
				attributeSet = null;
				break;
			}
			case StrictASIAttributes:
			{
				final I_M_AttributeSetInstance asi = getM_AttributeSetInstance();
				attributeSet = getAttributeSet(asi);
				break;
			}
			default:
			{
				attributeSet = null;
				break;
			}
		}

		return attributeSet;
	}

	@Nullable
	private AttributeSetDescriptor getAttributeSet(final I_M_AttributeSetInstance asi)
	{
		if (asi == null)
		{
			return null;
		}

		final AttributeSetId attributeSetId = AttributeSetId.ofRepoIdOrNone(asi.getM_AttributeSet_ID());
		return attributeSetId.isNone() ? null : attributesRepo.getAttributeSetDescriptorById(attributeSetId);
	}

	@Nullable
	private AttributeSetDescriptor retrieveProductAttributeSetOrNull()
	{
		final ProductId productId = getProductId();
		if (productId == null)
		{
			return null;
		}

		return productBL.getAttributeSetOrNull(productId);
	}

	@Nullable
	private AttributeSetDescriptor retrieveProductMasterDataSchema()
	{
		final ProductId productId = getProductId();
		if (productId == null)
		{
			return null;
		}

		return productBL.getProductMasterDataSchemaOrNull(productId);
	}

	public boolean isExcludedAttributeSet()
	{
		final AttributeSetId attributeSetId = getAttributeSetId();

		//
		// Exclude if it was configured to be excluded
		if (!attributeSetId.isNone())
		{
			final I_M_AttributeSetExclude asExclude = attributeExcludeBL.getAttributeSetExclude(attributeSetId, getCallerColumnId(), getSOTrx());
			final boolean exclude = asExclude != null && attributeExcludeBL.isFullExclude(asExclude);
			if (exclude)
			{
				return true; // exclude
			}
		}

		//
		// If no attributeSet, find out a default per each window type
		if (attributeSetId.isNone())
		{
			// Product window requires a valid attributeSet.
			final WindowType type = getWindowType();
			if (type == WindowType.ProductWindow)
			{
				return true; // exclude
			}
			else if (type == WindowType.Regular)
			{
				final boolean asiExists = AttributeSetInstanceId.isRegular(getAttributeSetInstanceId());
				if (!asiExists)
				{
					return true; // exclude
				}
			}
		}

		//
		// Fallback
		return false; // don't exclude
	}

	public List<Attribute> getAvailableAttributes()
	{
		if (_availableAttributes == null)
		{
			_availableAttributes = retrieveAvailableAttributes();
		}
		return _availableAttributes;
	}

	public Set<AttributeId> getAvailableAttributeIds()
	{
		return getAvailableAttributes()
				.stream()
				.map(Attribute::getAttributeId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private ImmutableList<Attribute> retrieveAvailableAttributes()
	{
		final WindowType type = getWindowType();
		final AttributeSetInstanceId attributeSetInstanceId = getAttributeSetInstanceId();
		final AttributeSetId attributeSetId = getAttributeSetId();
		final SOTrx soTrx = getSOTrx();
		final int callerColumnId = getCallerColumnId();

		final Stream<Attribute> attributes;
		switch (type)
		{
			case Regular:
			{
				attributes = retrieveAvailableAttributeSetAndInstanceAttributes(attributeSetId, attributeSetInstanceId)
						.stream();
				break;
			}
			case ProductWindow:
			{
				attributes = attributesRepo
						.getAttributesByAttributeSetId(attributeSetId)
						.stream();
				break;
			}
			case ProcessParameter:
			{
				// All attributes
				attributes = attributesRepo.getAllAttributes()
						.stream()
						.sorted(Attribute.ORDER_BY_DisplayName);
				break;
			}
			case Pricing:
			{
				attributes = attributesRepo.getAllAttributes()
						.stream()
						.sorted(Attribute.ORDER_BY_DisplayName)
						.filter(this::isPricingRelevantAttribute);
				break;
			}
			case StrictASIAttributes:
			{
				attributes = retrieveAvailableAttributeSetAndInstanceAttributes(attributeSetId, attributeSetInstanceId)
						.stream();
				break;
			}
			default:
			{
				return ImmutableList.of();
			}
		}

		return attributes
				.filter(attribute -> attributeSetId.isNone() || !attributeExcludeBL.isExcludedAttribute(attribute, attributeSetId, callerColumnId, soTrx))
				.collect(ImmutableList.toImmutableList());
	}

	private boolean isPricingRelevantAttribute(final Attribute attribute)
	{
		return attribute.isPricingRelevant() && attribute.getValueType().isList();
	}

	/**
	 * @return list of available attributeSet's instance attributes, merged with the attributes which are currently present in our ASI (even if they are not present in attribute set)
	 */
	private List<Attribute> retrieveAvailableAttributeSetAndInstanceAttributes(
			@Nullable final AttributeSetId attributeSetId,
			@Nullable final AttributeSetInstanceId attributeSetInstanceId)
	{
		final LinkedHashMap<AttributeId, Attribute> attributes = new LinkedHashMap<>(); // preserve the order

		//
		// Retrieve attribute set's instance attributes,
		// and index them by M_Attribute_ID
		if (attributeSetId != null && !attributeSetId.isNone())
		{
			attributesRepo.getAttributesByAttributeSetId(attributeSetId)
					.stream()
					.filter(Attribute::isInstanceAttribute)
					.forEach(attribute -> attributes.put(attribute.getAttributeId(), attribute));
		}

		//
		// If we have an ASI then fetch the attributes from ASI which are missing in attributeSet
		// and add them to our "attributes" index.
		if (AttributeSetInstanceId.isRegular(attributeSetInstanceId))
		{
			final Set<AttributeId> alreadyLoadedAttributeIds = attributes.keySet();
			final Set<AttributeId> asiAttributeIds = asiBL.getAttributeIdsByAttributeSetInstanceId(attributeSetInstanceId);
			final Set<AttributeId> attributeIdsToLoad = Sets.difference(asiAttributeIds, alreadyLoadedAttributeIds);

			attributesRepo.getAttributesByIds(attributeIdsToLoad)
					.forEach(attribute -> attributes.put(attribute.getAttributeId(), attribute));
		}

		//
		return ImmutableList.copyOf(attributes.values());
	}
}
