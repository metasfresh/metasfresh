package org.adempiere.mm.attributes.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeExcludeBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.LegacyAdapters;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeSetExclude;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.MAttribute;
import org.compiere.model.MAttributeSet;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.lang.SOTrx;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

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
 *
 */
public final class ASIEditingInfo
{
	public static final ASIEditingInfo of(
			final ProductId productId,
			final AttributeSetInstanceId attributeSetInstanceId,
			final String callerTableName, final int callerColumnId,
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

	public static final ASIEditingInfo readonlyASI(final AttributeSetInstanceId attributeSetInstanceId)
	{
		return builder()
				.type(WindowType.StrictASIAttributes)
				.soTrx(SOTrx.SALES)
				.attributeSetInstanceId(attributeSetInstanceId)
				.build();
	}

	public static final ASIEditingInfo processParameterASI(final AttributeSetInstanceId attributeSetInstanceId)
	{
		return builder()
				.type(WindowType.ProcessParameter)
				.soTrx(SOTrx.SALES)
				.attributeSetInstanceId(attributeSetInstanceId)
				.build();
	}

	// Parameters
	private final WindowType _type;
	private final ProductId _productId;
	private final AttributeSetInstanceId _attributeSetInstanceId;
	private final String _callerTableName;
	private final int _calledColumnId;
	private final SOTrx _soTrx;

	// Deducted values
	private final I_M_AttributeSet _attributeSet;
	private ImmutableList<MAttribute> _availableAttributes;
	private MAttributeSetInstance _attributeSetInstance;
	private final boolean _allowSelectExistingASI;
	private final boolean isLotEnabled;
	private final boolean isSerNoEnabled;
	private final boolean isGuaranteeDateEnabled;

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
		_attributeSet = retrieveM_AttributeSet();
		if (attributeSetInstanceId == null || attributeSetInstanceId.isNone())
		{
			_attributeSetInstance = null;
		}
		else
		{
			final I_M_AttributeSetInstance asi = InterfaceWrapperHelper.loadOutOfTrx(attributeSetInstanceId, I_M_AttributeSetInstance.class);
			_attributeSetInstance = LegacyAdapters.convertToPO(asi);
		}

		//
		// Flags
		_allowSelectExistingASI = _type == WindowType.Regular;

		isLotEnabled = _type == WindowType.Regular
				&& _attributeSet != null && _attributeSet.isLot();
		isSerNoEnabled = _type == WindowType.Regular
				&& _attributeSet != null && _attributeSet.isSerNo();
		// isGuaranteeDateEnabled:
		// We are displaying it if we deal with a pure product ASI (i.e. user is not editing the ASI from product window),
		// and if:
		// * the attribute set requires a GuaranteeDate
		// * or if the ASI has a GuaranteeDate already set
		isGuaranteeDateEnabled = _type == WindowType.Regular
				&& (_attributeSet != null && _attributeSet.isGuaranteeDate() || _attributeSetInstance != null && _attributeSetInstance.getGuaranteeDate() != null);
	}

	private static WindowType extractType(String callerTableName, final int callerColumnId)
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

	private ProductId getProductId()
	{
		return _productId;
	}

	private I_M_Product getM_Product()
	{
		final ProductId productId = getProductId();
		if (productId == null)
		{
			return null;
		}
		return Services.get(IProductDAO.class).getById(productId);
	}

	public AttributeSetInstanceId getAttributeSetInstanceId()
	{
		return _attributeSetInstanceId;
	}

	public MAttributeSetInstance getM_AttributeSetInstance()
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

	public boolean isSOTrx()
	{
		return _soTrx.toBoolean();
	}

	@Nullable
	public I_M_AttributeSet getM_AttributeSet()
	{
		return _attributeSet;
	}

	public AttributeSetId getAttributeSetId()
	{
		final I_M_AttributeSet attributeSet = getM_AttributeSet();
		return attributeSet != null ? AttributeSetId.ofRepoId(attributeSet.getM_AttributeSet_ID()) : AttributeSetId.NONE;
	}

	public String getM_AttributeSet_Name()
	{
		final I_M_AttributeSet attributeSet = getM_AttributeSet();
		return attributeSet == null ? "" : attributeSet.getName();
	}

	public String getM_AttributeSet_Description()
	{
		final I_M_AttributeSet attributeSet = getM_AttributeSet();
		return attributeSet == null ? "" : attributeSet.getDescription();
	}

	private I_M_AttributeSet retrieveM_AttributeSet()
	{
		final WindowType type = getWindowType();

		final I_M_AttributeSet attributeSet;
		switch (type)
		{
			case Regular:
			{
				attributeSet = getProductAttributeSet();
				// Product has no Instance Attributes
				if (attributeSet != null && !attributeSet.isInstanceAttribute())
				{
					throw new AdempiereException("@PAttributeNoInstanceAttribute@");
				}
				break;
			}
			case ProductWindow:
			{
				attributeSet = getProductAttributeSet();
				break;
			}
			case ProcessParameter:
			{
				final I_M_AttributeSet productAttributeSet = getProductAttributeSet();
				if (productAttributeSet != null)
				{
					attributeSet = productAttributeSet;
					break;
				}

				final MAttributeSetInstance asi = getM_AttributeSetInstance();
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
				final MAttributeSetInstance asi = getM_AttributeSetInstance();
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

	private I_M_AttributeSet getAttributeSet(final I_M_AttributeSetInstance asi)
	{
		if (asi == null)
		{
			return null;
		}

		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);

		final AttributeSetId attributeSetId = AttributeSetId.ofRepoIdOrNone(asi.getM_AttributeSet_ID());
		return attributesRepo.getAttributeSetById(attributeSetId);
	}

	private I_M_AttributeSet getProductAttributeSet()
	{
		final I_M_Product product = getM_Product();
		if (product == null)
		{
			return null;
		}
		return Services.get(IProductBL.class).getM_AttributeSet(product);
	}

	public boolean isLotEnabled()
	{
		return isLotEnabled;
	}

	public boolean isSerNoEnabled()
	{
		return isSerNoEnabled;
	}

	public boolean isGuaranteeDateEnabled()
	{
		return isGuaranteeDateEnabled;
	}

	public boolean isExcludedAttributeSet()
	{
		final I_M_AttributeSet attributeSet = getM_AttributeSet();
		final boolean attributeSetExists = attributeSet != null && attributeSet.getM_AttributeSet_ID() > 0;

		//
		// Exclude if it was configured to be excluded
		if (attributeSetExists)
		{
			final IAttributeExcludeBL excludeBL = Services.get(IAttributeExcludeBL.class);
			final I_M_AttributeSetExclude asExclude = excludeBL.getAttributeSetExclude(attributeSet, getCallerColumnId(), isSOTrx());
			final boolean exclude = asExclude != null && excludeBL.isFullExclude(asExclude);
			if (exclude)
			{
				return true; // exclude
			}
		}

		//
		// If no attributeSet, find out a default per each window type
		if (!attributeSetExists)
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

	public List<MAttribute> getAvailableAttributes()
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
				.map(I_M_Attribute::getM_Attribute_ID)
				.map(AttributeId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private ImmutableList<MAttribute> retrieveAvailableAttributes()
	{
		final WindowType type = getWindowType();
		final MAttributeSet attributeSet = LegacyAdapters.convertToPO(getM_AttributeSet());
		final int callerColumnId = getCallerColumnId();
		final boolean isSOTrx = isSOTrx();

		final Stream<MAttribute> attributes;
		switch (type)
		{
			case Regular:
			{
				attributes = retrieveAvailableAttributeSetAndInstanceAttributes(attributeSet, getAttributeSetInstanceId())
						.stream();
				break;
			}
			case ProductWindow:
			{
				Check.assumeNotNull(attributeSet, "Parameter attributeSet is not null");
				attributes = Stream.of(attributeSet.getMAttributes(false)); // non-instance attributes
				break;
			}
			case ProcessParameter:
			{
				// All attributes
				attributes = Services.get(IQueryBL.class)
						.createQueryBuilder(MAttribute.class, Env.getCtx(), ITrx.TRXNAME_None)
						.addOnlyActiveRecordsFilter()
						.addOnlyContextClient()
						//
						.orderBy()
						.addColumn(I_M_Attribute.COLUMNNAME_Name)
						.addColumn(I_M_Attribute.COLUMNNAME_M_Attribute_ID)
						.endOrderBy()
						//
						.create()
						.stream(MAttribute.class);
				break;
			}
			case Pricing:
			{
				attributes = Services.get(IQueryBL.class)
						.createQueryBuilder(MAttribute.class, Env.getCtx(), ITrx.TRXNAME_None)
						.addOnlyActiveRecordsFilter()
						.addOnlyContextClient()
						.addEqualsFilter(I_M_Attribute.COLUMNNAME_IsPricingRelevant, true)
						.addEqualsFilter(I_M_Attribute.COLUMNNAME_AttributeValueType, X_M_Attribute.ATTRIBUTEVALUETYPE_List) // atm only list attributes are supported, see IPricingAttribute
						//
						.orderBy()
						.addColumn(I_M_Attribute.COLUMNNAME_Name)
						.addColumn(I_M_Attribute.COLUMNNAME_M_Attribute_ID)
						.endOrderBy()
						//
						.create()
						.stream(MAttribute.class);
				break;
			}
			case StrictASIAttributes:
			{
				attributes = retrieveAvailableAttributeSetAndInstanceAttributes(attributeSet, getAttributeSetInstanceId())
						.stream();
				break;
			}
			default:
			{
				return ImmutableList.of();
			}
		}

		final IAttributeExcludeBL attributeExcludeBL = Services.get(IAttributeExcludeBL.class);

		return attributes
				.filter(attribute -> attributeSet == null || !attributeExcludeBL.isExcludedAttribute(attribute, attributeSet, callerColumnId, isSOTrx))
				.collect(GuavaCollectors.toImmutableList());
	}

	/**
	 * 
	 * @param attributeSet
	 * @param attributeSetInstanceId
	 * @return list of available attributeSet's instance attributes, merged with the attributes which are currently present in our ASI (even if they are not present in attribute set)
	 */
	private static final List<MAttribute> retrieveAvailableAttributeSetAndInstanceAttributes(
			@Nullable final MAttributeSet attributeSet,
			final AttributeSetInstanceId attributeSetInstanceId)
	{
		final LinkedHashMap<Integer, MAttribute> attributes = new LinkedHashMap<>(); // preserve the order

		//
		// Retrieve attribute set's instance attributes,
		// and index them by M_Attribute_ID
		if (attributeSet != null)
		{
			Stream.of(attributeSet.getMAttributes(true))
					.forEach(attribute -> attributes.put(attribute.getM_Attribute_ID(), attribute));
		}

		//
		// If we have an ASI then fetch the attributes from ASI which are missing in attributeSet
		// and add them to our "attributes" index.
		if(AttributeSetInstanceId.isRegular(attributeSetInstanceId))
		{
			Services.get(IQueryBL.class)
					.createQueryBuilderOutOfTrx(I_M_AttributeInstance.class)
					.addEqualsFilter(I_M_AttributeInstance.COLUMN_M_AttributeSetInstance_ID, attributeSetInstanceId)
					//
					.andCollect(I_M_AttributeInstance.COLUMN_M_Attribute_ID)
					.addNotInArrayFilter(I_M_Attribute.COLUMN_M_Attribute_ID, attributes.keySet()) // skip already loaded attributes
					.orderBy()
					.addColumn(I_M_Attribute.COLUMN_Name)
					.addColumn(I_M_Attribute.COLUMN_M_Attribute_ID)
					.endOrderBy()
					//
					.create()
					.stream(MAttribute.class)
					.forEach(attribute -> attributes.put(attribute.getM_Attribute_ID(), attribute));
		}

		//
		return ImmutableList.copyOf(attributes.values());
	}

	public static enum WindowType
	{
		Regular, ProductWindow, ProcessParameter, Pricing, StrictASIAttributes
	}
}
