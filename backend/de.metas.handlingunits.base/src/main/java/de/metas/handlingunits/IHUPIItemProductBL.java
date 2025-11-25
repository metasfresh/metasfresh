package de.metas.handlingunits;

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

import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.ISingletonService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

public interface IHUPIItemProductBL extends ISingletonService
{
	HUPIItemProduct getById(@NonNull HUPIItemProductId id);

	I_M_HU_PI_Item_Product getRecordById(HUPIItemProductId id);

	List<I_M_HU_PI_Item_Product> getCompatibleItemDefProducts(I_M_HU_PI_Version version, I_M_Product product);

	/**
	 * @return <code>true</code> if product is available in the version (or IsAllowAnyProduct), false otherwise
	 */
	boolean isCompatibleProduct(I_M_HU_PI_Version version, I_M_Product product);

	void deleteForItem(I_M_HU_PI_Item packingInstructionsItem);

	/**
	 * Returns <code>true</code> if the given <code>piip</code> is the "virtual" one, i.e. the one referencing the virtual packing instruction.
	 */
	boolean isVirtualHUPIItemProduct(I_M_HU_PI_Item_Product piip);

	boolean isInfiniteCapacity(HUPIItemProductId id);

	/**
	 * @return builder used to create the display name
	 */
	IHUPIItemProductDisplayNameBuilder buildDisplayName();

	/**
	 * Builds and set Name and Description field.
	 * <p>
	 * Name will be build using {@link IHUPIItemProductDisplayNameBuilder#buildItemProductDisplayName()} via {@link #buildDisplayName()}.
	 *
	 * @see #buildDisplayName()
	 */
	void setNameAndDescription(I_M_HU_PI_Item_Product itemProduct);

	ITranslatableString getDisplayName(HUPIItemProductId piItemProductId);

	@Nullable
	I_M_HU_PI_Item_Product getDefaultForProduct(@NonNull ProductId productId, @NonNull ZonedDateTime dateTime);

	static I_C_UOM extractUOMOrNull(@NonNull final I_M_HU_PI_Item_Product itemProduct)
	{
		final UomId uomId = extractUomIdOrNull(itemProduct);
		
		final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
		return uomId != null ? uomDAO.getById(uomId) : null;
	}

	static @Nullable UomId extractUomIdOrNull(final @NotNull I_M_HU_PI_Item_Product itemProduct)
	{
		return UomId.ofRepoIdOrNull(itemProduct.getC_UOM_ID());
	}

	static I_C_UOM extractUOM(@NonNull final I_M_HU_PI_Item_Product itemProduct)
	{
		final I_C_UOM uom = extractUOMOrNull(itemProduct);
		if (uom == null)
		{
			throw new AdempiereException("Cannot determine UOM of " + itemProduct.getName());
		}
		return uom;
	}

	I_M_HU_PI_Item_Product extractHUPIItemProduct(final I_C_Order order, final I_C_OrderLine orderLine);

	int getRequiredLUCount(@NonNull Quantity qty, I_M_HU_LUTU_Configuration lutuConfigurationInStockUOM);

	static StockQtyAndUOMQty getMaxQtyCUsPerLU(final @NonNull StockQtyAndUOMQty qty, final I_M_HU_LUTU_Configuration lutuConfigurationInStockUOM, final ProductId productId)
	{
		final StockQtyAndUOMQty maxQtyCUsPerLU;
		if (lutuConfigurationInStockUOM.isInfiniteQtyTU() || lutuConfigurationInStockUOM.isInfiniteQtyCU())
		{
			maxQtyCUsPerLU = StockQtyAndUOMQtys.createConvert(
					qty.getStockQty(),
					productId,
					qty.getUOMQtyNotNull().getUomId());
		}
		else
		{
			maxQtyCUsPerLU = StockQtyAndUOMQtys.createConvert(
					lutuConfigurationInStockUOM.getQtyCUsPerTU().multiply(lutuConfigurationInStockUOM.getQtyTU()),
					productId,
					qty.getUOMQtyNotNull().getUomId());
		}
		return maxQtyCUsPerLU;
	}

	static Quantity getQtyCUsPerTUInStockUOM(final @NonNull I_C_OrderLine orderLineRecord, final @NonNull Quantity stockQty, final I_M_HU_LUTU_Configuration lutuConfigurationInStockUOM)
	{
		final Quantity qtyCUsPerTUInStockUOM;
		if (orderLineRecord.getQtyItemCapacity().signum() > 0)
		{
			// we use the capacity which the goods were ordered in
			qtyCUsPerTUInStockUOM = Quantitys.of(orderLineRecord.getQtyItemCapacity(), stockQty.getUomId());
		}
		else if (!lutuConfigurationInStockUOM.isInfiniteQtyCU())
		{
			// we make an educated guess, based on the packing-instruction's information
			qtyCUsPerTUInStockUOM = Quantitys.of(lutuConfigurationInStockUOM.getQtyCUsPerTU(), stockQty.getUomId());
		}
		else
		{
			// we just don't have the info. So we assume that everything was put into one TU
			qtyCUsPerTUInStockUOM = stockQty;
		}
		return qtyCUsPerTUInStockUOM;
	}
}
