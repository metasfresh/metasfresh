package de.metas.handlingunits.impl;

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

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHUPIItemProductDisplayNameBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.i18n.ITranslatableString;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HUPIItemProductBL implements IHUPIItemProductBL
{
	@NonNull private final IHUPIItemProductDAO huPIItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);

	@Override
	public HUPIItemProduct getById(@NonNull final HUPIItemProductId id) {return huPIItemProductDAO.getById(id);}

	@Override
	public I_M_HU_PI_Item_Product getRecordById(@NonNull final HUPIItemProductId id)
	{
		return huPIItemProductDAO.getRecordById(id);
	}

	@Override
	public List<I_M_HU_PI_Item_Product> getCompatibleItemDefProducts(
			@NonNull final I_M_HU_PI_Version version,
			@NonNull final I_M_Product product)
	{
		final List<I_M_HU_PI_Item_Product> result = new ArrayList<>();

		final BPartnerId bpartnerId = null;
		final List<I_M_HU_PI_Item> versionPIItems = handlingUnitsDAO.retrievePIItems(version, bpartnerId);

		final List<I_M_HU_PI_Item> itemDefs = getNestedMaterialPIItems(versionPIItems);
		for (final I_M_HU_PI_Item itemDef : itemDefs)
		{
			Check.assume(X_M_HU_PI_Item.ITEMTYPE_Material.equals(itemDef.getItemType()), "{} item type is Material", itemDef);

			final I_M_HU_PI_Item_Product itemProduct = huPIItemProductDAO
					.retrievePIMaterialItemProduct(itemDef, product, SystemTime.asZonedDateTime());
			if (itemProduct != null && itemProduct.getM_HU_PI_Item_Product_ID() > 0)
			{
				result.add(itemProduct);
			}
		}

		return result;
	}

	private List<I_M_HU_PI_Item> getNestedMaterialPIItems(final List<I_M_HU_PI_Item> itemDefs)
	{
		if (itemDefs == null || itemDefs.isEmpty())
		{
			return Collections.emptyList();
		}

		final List<I_M_HU_PI_Item> nestedItemDefinitions = new ArrayList<>();
		for (final I_M_HU_PI_Item itemDef : itemDefs)
		{
			final String itemType = itemDef.getItemType();
			if (X_M_HU_PI_Item.ITEMTYPE_Material.equals(itemType))
			{
				nestedItemDefinitions.add(itemDef);
			}
			else if (X_M_HU_PI_Item.ITEMTYPE_HandlingUnit.equals(itemType))
			{
				// get nested items for included HU PI
				final I_M_HU_PI includedPI = handlingUnitsDAO.getIncludedPI(itemDef);
				final List<I_M_HU_PI_Item> nestedItems = handlingUnitsDAO.retrievePIItems(includedPI, null);

				// recursively get nested items
				final List<I_M_HU_PI_Item> filteredAndNestedItems = getNestedMaterialPIItems(nestedItems);
				nestedItemDefinitions.addAll(filteredAndNestedItems);
			}
		}
		return nestedItemDefinitions;
	}

	@Override
	public boolean isCompatibleProduct(final I_M_HU_PI_Version version, final I_M_Product product)
	{
		final List<I_M_HU_PI_Item_Product> compatiblePIDefProducts = getCompatibleItemDefProducts(version, product);
		return compatiblePIDefProducts != null && !compatiblePIDefProducts.isEmpty();
	}

	@Override
	public boolean isVirtualHUPIItemProduct(final I_M_HU_PI_Item_Product piip)
	{
		return HUPIItemProductId.isVirtualHU(piip.getM_HU_PI_Item_Product_ID());
	}

	@Override
	public boolean isInfiniteCapacity(@NonNull final HUPIItemProductId id)
	{
		if (id.isVirtualHU())
		{
			return true;
		}

		final I_M_HU_PI_Item_Product piip = getRecordById(id);
		if (piip == null)
		{
			return true;
		}

		return piip.isInfiniteCapacity();
	}

	@Override
	public void deleteForItem(final I_M_HU_PI_Item packingInstructionsItem)
	{
		final List<I_M_HU_PI_Item_Product> products = huPIItemProductDAO.retrievePIMaterialItemProducts(packingInstructionsItem);

		for (final I_M_HU_PI_Item_Product product : products)
		{
			InterfaceWrapperHelper.delete(product);
		}
	}

	@Override
	public void setNameAndDescription(final I_M_HU_PI_Item_Product itemProduct)
	{
		//
		// Build itemProduct's name from scratch
		final String nameBuilt = buildDisplayName()
				.setM_HU_PI_Item_Product(itemProduct)
				.buildItemProductDisplayName(); // build it from scratch

		// Set it as Name and Description
		itemProduct.setName(nameBuilt);
		itemProduct.setDescription(nameBuilt);
	}

	@Override
	public IHUPIItemProductDisplayNameBuilder buildDisplayName()
	{
		return new HUPIItemProductDisplayNameBuilder();
	}

	@Override
	public ITranslatableString getDisplayName(@NonNull final HUPIItemProductId piItemProductId)
	{
		return huPIItemProductDAO.getById(piItemProductId).getName();
	}

	@Nullable
	public I_M_HU_PI_Item_Product getDefaultForProduct(@NonNull final ProductId productId, @NonNull final ZonedDateTime dateTime)
	{
		return huPIItemProductDAO.retrieveDefaultForProduct(productId, dateTime);
	}

	@NonNull
	public I_M_HU_PI_Item_Product extractHUPIItemProduct(final I_C_Order order, final I_C_OrderLine orderLine)
	{
		final HUPIItemProductId hupiItemProductId = HUPIItemProductId.ofRepoIdOrNull(orderLine.getM_HU_PI_Item_Product_ID());
		if (hupiItemProductId != null)
		{
			return huPIItemProductDAO.getRecordById(hupiItemProductId);
		}
		else
		{
			final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());
			final BPartnerId buyerBPartnerId = BPartnerId.ofRepoId(order.getC_BPartner_ID());
			final ZoneId timeZone = orgDAO.getTimeZone(OrgId.ofRepoId(order.getAD_Org_ID()));

			return huPIItemProductDAO.retrieveMaterialItemProduct(
					productId,
					buyerBPartnerId,
					TimeUtil.asZonedDateTime(order.getDateOrdered(), timeZone),
					X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit, true/* allowInfiniteCapacity */);
		}
	}

	@Override
	public int getRequiredLUCount(final @NonNull Quantity qty, final I_M_HU_LUTU_Configuration lutuConfigurationInStockUOM)
	{
		if (lutuConfigurationInStockUOM.isInfiniteQtyTU() || lutuConfigurationInStockUOM.isInfiniteQtyCU())
		{
			return 1;
		}
		else
		{
			// Note need to use the StockQty because lutuConfigurationInStockUOM is also in stock-UOM.
			// And in the case of catchweight, it's very important to *not* make metasfresh convert quantites using the UOM-conversion
			return lutuConfigurationFactory.calculateQtyLUForTotalQtyCUs(
					lutuConfigurationInStockUOM,
					qty);
		}
	}

}
