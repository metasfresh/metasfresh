package de.metas.storage.spi.hu.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IHUStatusBL;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.storage.IStorageQuery;
import de.metas.storage.IStorageRecord;
import de.metas.storage.spi.hu.IHUStorageBL;

/**
 *
 * A HU-based IStorageQuery implementation.
 * <p>
 * <b>IMPORTANT</b> this implementation will ignore HUs that are located in a <code>M_Locator</code> with {@link I_M_Locator#COLUMNNAME_IsAfterPickingLocator IsAfterPickingLocator} <code>='Y'</code>,
 * because HUs on such a locator are actually bound to be shipped in the very nearest future and are considered to be not "there" for normal storage stuff any more.
 *
 */
/* package */class HUStorageQuery implements IStorageQuery
{
	public static final HUStorageQuery cast(final IStorageQuery storageQuery)
	{
		Check.assumeInstanceOf(storageQuery, HUStorageQuery.class, "storageQuery");
		final HUStorageQuery huStorageQuery = (HUStorageQuery)storageQuery;
		return huStorageQuery;
	}

	// services
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	private final IHUQueryBuilder huQueryBuilder;
	private Set<Integer> _availableAttributeIds;
	private final Set<Integer> _productIds = new HashSet<>();
	private final transient List<I_M_Product> _products = new ArrayList<>(); // needed only for summary info
	private final transient List<I_C_BPartner> _bpartners = new ArrayList<>(); // needed only for summary info
	private final transient List<I_M_Warehouse> _warehouses = new ArrayList<>(); // needed only for summary info

	/* package */ HUStorageQuery()
	{
		super();

		huQueryBuilder = handlingUnitsDAO.createHUQueryBuilder();

		//
		// BPartner: we will accept without BP or with the BPs which is specified in query
		huQueryBuilder.setOnlyIfAssignedToBPartner(false);
		huQueryBuilder.addOnlyInBPartnerId(null); // accept no BPartner

		// consider only VHUs
		huQueryBuilder.setOnlyTopLevelHUs(false);
		huQueryBuilder.addPIVersionToInclude(handlingUnitsDAO.getVirtual_HU_PI_ID());

		// consider only those HUs which are Active or Picked. Those are about QtyOnHand.
		final List<String> huStatusesQtyOnHand = Services.get(IHUStatusBL.class).getQtyOnHandStatuses();
		for (final String huStatus : huStatusesQtyOnHand)
		{
			huQueryBuilder.addHUStatusToInclude(huStatus);
		}

		// by default, consider only HUs which are *not* in an after-picking locator (08123)
		huQueryBuilder.setExcludeAfterPickingLocator(true);
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(huQueryBuilder)
				// .append(_availableAttributeIds) // nop, because that one is retrieved & cached
				.append(_productIds)
				.toHashcode();
	}

	@Override
	public boolean equals(final Object otherObj)
	{
		if (this == otherObj)
		{
			return true;
		}
		final HUStorageQuery other = EqualsBuilder.getOther(this, otherObj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(huQueryBuilder, other.huQueryBuilder)
				// .append(_availableAttributeIds) // nop, because that one is retrieved & cached
				.append(_productIds, other._productIds)
				.isEqual();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public String getSummary()
	{
		final StringBuilder summary = new StringBuilder();

		//
		// Product names
		final Set<String> productNames = new HashSet<>();
		if (!_products.isEmpty())
		{
			for (final I_M_Product product : _products)
			{
				final String productName = product.getName();
				productNames.add(productName);
			}
		}
		final String productNamesStr = ListUtils.toString(productNames, ", ");
		//
		summary.append("\n");
		summary.append("@M_Product_ID@: ").append(Check.isEmpty(productNamesStr, true) ? "*" : productNamesStr);

		//
		// Warehouse names
		final Set<String> warehouseNames = new HashSet<>();
		if (!_warehouses.isEmpty())
		{
			for (final I_M_Warehouse warehouse : _warehouses)
			{
				final String warehouseName = warehouse.getName();
				warehouseNames.add(warehouseName);
			}
		}
		final String warehouseNamesStr = ListUtils.toString(warehouseNames, ", ");
		//
		summary.append("\n");
		summary.append("@M_Warehouse_ID@: ").append(Check.isEmpty(warehouseNamesStr, true) ? "*" : warehouseNamesStr);

		//
		// BPartner names
		final Set<String> bpartnerNames = new HashSet<>();
		if (!_bpartners.isEmpty())
		{
			for (final I_C_BPartner bpartner : _bpartners)
			{
				final String bpartnerName = bpartner.getName();
				bpartnerNames.add(bpartnerName);
			}
		}
		final String bpartnerNamesStr = ListUtils.toString(bpartnerNames, ", ");
		//
		summary.append("\n");
		summary.append("@C_BPartner_ID@: ").append(Check.isEmpty(bpartnerNamesStr, true) ? "*" : bpartnerNamesStr);

		//
		// Attributes
		final String attributesStr = huQueryBuilder.getAttributesSummary();
		//
		if (!Check.isEmpty(attributesStr, true))
		{
			summary.append("\n");
			summary.append(attributesStr);
		}

		return summary.toString();
	}

	public final IHUQueryBuilder createHUQueryBuilder()
	{
		return huQueryBuilder.copy();
	}

	public final IQueryBuilder<I_M_HU_Storage> createQueryBuilder_for_M_HU_Storages(final IContextAware context)
	{
		final IQueryBuilder<I_M_HU_Storage> huStorageQueryBuilder = huQueryBuilder.copy()
				.setContext(context)
				.createQueryBuilder()
				.andCollectChildren(I_M_HU_Storage.COLUMN_M_HU_ID, I_M_HU_Storage.class);

		huStorageQueryBuilder.addNotEqualsFilter(I_M_HU_Storage.COLUMN_Qty, BigDecimal.ZERO);

		// Filter by in scope M_Product_IDs
		if (!_productIds.isEmpty())
		{
			huStorageQueryBuilder.addInArrayFilter(I_M_HU_Storage.COLUMN_M_Product_ID, _productIds);
		}

		huStorageQueryBuilder.orderBy()
				.addColumn(I_M_HU_Storage.COLUMNNAME_M_HU_ID);

		return huStorageQueryBuilder;
	}

	@Override
	public boolean matches(final IStorageRecord storageRecord)
	{
		if (storageRecord == null)
		{
			return false;
		}

		//
		// Check if Product matches
		final Set<Integer> queryProductIds = getProductIds();
		if (!queryProductIds.isEmpty())
		{
			final I_M_Product recordProduct = storageRecord.getProduct();
			final int recordProductId = recordProduct.getM_Product_ID();
			if (!queryProductIds.contains(recordProductId))
			{
				return false;
			}
		}

		//
		// Make sure we are not dealing with an After-Picking locator (08123)
		final I_M_Locator recordLocator = InterfaceWrapperHelper.create(storageRecord.getLocator(), I_M_Locator.class);
		if (recordLocator.isAfterPickingLocator())
		{
			return false;
		}

		//
		// Check if Warehouse/Locator matches
		final Set<Integer> queryWarehouseIds = getWarehouseIds();
		if (!queryWarehouseIds.isEmpty())
		{
			final int recordWarehouseId = recordLocator.getM_Warehouse_ID();
			if (!queryWarehouseIds.contains(recordWarehouseId))
			{
				return false;
			}
		}

		//
		// Check if BPartner matches
		final Set<Integer> queryBPartnerIds = getBPartnerIds();
		if (!queryBPartnerIds.isEmpty())
		{
			final I_C_BPartner bpartner = storageRecord.getC_BPartner();
			Integer bpartnerId = bpartner == null ? null : bpartner.getC_BPartner_ID();
			bpartnerId = bpartnerId != null && bpartnerId <= 0 ? null : bpartnerId; // make sure if is <=0 then to use NULL which means ANY
			if (!queryBPartnerIds.contains(bpartnerId))
			{
				return false;
			}
		}

		//
		// Check if required attributes are matching
		final IAttributeSet recordAttributes = storageRecord.getAttributes();
		if (!huQueryBuilder.matches(recordAttributes))
		{
			return false;
		}

		return true;
	}

	@Override
	public IStorageQuery addProduct(final I_M_Product product)
	{
		Check.assumeNotNull(product, "Product not null");
		final int productId = product.getM_Product_ID();
		if (!_productIds.add(productId))
		{
			return this;
		}

		_products.add(product);

		return this;
	}

	private final Set<Integer> getProductIds()
	{
		return _productIds;
	}

	@Override
	public IStorageQuery addPartner(final I_C_BPartner bpartner)
	{
		final Integer bpartnerId;
		if (bpartner == null)
		{
			bpartnerId = null;
		}
		else
		{
			bpartnerId = bpartner.getC_BPartner_ID();
		}
		huQueryBuilder.addOnlyInBPartnerId(bpartnerId);
		_bpartners.add(bpartner);
		return this;
	}

	private final Set<Integer> getBPartnerIds()
	{
		return huQueryBuilder.getOnlyInBPartnerIds();
	}

	@Override
	public IStorageQuery addWarehouse(final I_M_Warehouse warehouse)
	{
		Check.assumeNotNull(warehouse, "warehouse not null");
		final int warehouseId = warehouse.getM_Warehouse_ID();
		huQueryBuilder.addOnlyInWarehouseId(warehouseId);
		_warehouses.add(warehouse);
		return this;
	}

	private final Set<Integer> getWarehouseIds()
	{
		return huQueryBuilder.getOnlyInWarehouseIds();
	}

	/**
	 * Adds a filter for the given attribute, <b>if</b> it is relevant according to {@link IHUStorageBL#getAvailableAttributeIds(java.util.Properties)}.
	 */
	@Override
	public IStorageQuery addAttribute(final I_M_Attribute attribute, final String attributeValueType, final Object attributeValue)
	{
		Check.assumeNotNull(attribute, "attribute not null");

		// Skip null values because in this case user filled nothing => so we accept any value
		if (attributeValue == null)
		{
			return this;
		}

		//
		// Make sure given attribute available to be used by our HU Storage implementations.
		// If we would filter by those attributes we would get NO result.
		final int attributeId = attribute.getM_Attribute_ID();
		final Set<Integer> availableAttributeIds = getAvailableAttributeIds();
		if (!availableAttributeIds.contains(attributeId))
		{
			return this;
		}

		//
		// Add attribute query restrictions
		final List<Object> attributeValues = Arrays.asList(attributeValue);
		huQueryBuilder.addOnlyWithAttributeInList(attribute, attributeValueType, attributeValues);

		return this;
	}

	@Override
	public IStorageQuery addAttributes(final IAttributeSet attributeSet)
	{
		Check.assumeNotNull(attributeSet, "attributeSet not null");
		for (final I_M_Attribute attribute : attributeSet.getAttributes())
		{
			// Skip attributes which were newly generated just to have an complete attribute set,
			// because it makes no sense to filter by those and because it could be that we will get no result.
			// Case: ASIAttributeStorge is generating new AIs for those attributes which are defined in M_HU_PI_Attribute but which were missing in given ASI.
			// If we would filter by those too, it's a big chance that we would get no result.
			if (attributeSet.isNew(attribute))
			{
				continue;
			}

			final Object value = attributeSet.getValue(attribute);

			final String attributeValueType = attributeSet.getAttributeValueType(attribute);
			addAttribute(attribute, attributeValueType, value);
		}

		return this;
	}

	private final Set<Integer> getAvailableAttributeIds()
	{
		if (_availableAttributeIds == null)
		{
			final IHUStorageBL huStorageBL = Services.get(IHUStorageBL.class);
			_availableAttributeIds = huStorageBL.getAvailableAttributeIds(Env.getCtx());
		}
		return _availableAttributeIds;
	}

	@Override
	public IStorageQuery setExcludeAfterPickingLocator(final boolean excludeAfterPickingLocator)
	{
		huQueryBuilder.setExcludeAfterPickingLocator(excludeAfterPickingLocator);
		return this;
	}
}
