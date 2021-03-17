package org.adempiere.inout.util;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;

import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.BOMComponentType;

import de.metas.adempiere.model.I_M_Product;
import de.metas.material.event.commons.AttributesKey;
import org.eevolution.api.QtyCalculationsBOM;
import org.eevolution.api.QtyCalculationsBOMLine;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
final class ChristmasPackTestData
{
	@NonNull
	ShipmentScheduleAvailableStockDetail christmasPackStockDetail;
	@NonNull
	ShipmentScheduleAvailableStockDetail chocolateStockDetail;
	@NonNull
	ShipmentScheduleAvailableStockDetail socksStockDetail;

	@Builder
	private ChristmasPackTestData(
			final int chocolate_qtyPerPack,
			final int socks_qtyPerPack,
			//
			final int christmasPack_qtyOnHand,
			final int chocolate_qtyOnHand,
			final int socks_qtyOnHand)
	{
		final WarehouseId warehouseId = WarehouseId.ofRepoId(111);

		final I_C_UOM uomEach = uom("Each", 0);
		final ProductId christmasPackProductId = product("christmas pack", uomEach);
		final ProductId chocolateProductId = product("chocolate", uomEach);
		final ProductId socksProductId = product("socks", uomEach);

		final QtyCalculationsBOM pickingBOM = QtyCalculationsBOM.builder()
				.line(QtyCalculationsBOMLine.builder()
						.bomProductId(christmasPackProductId)
						.bomProductUOM(uomEach)
						//
						.componentType(BOMComponentType.Component)
						.productId(chocolateProductId)
						.qtyPercentage(false)
						.uom(uomEach)
						.qtyForOneFinishedGood(new BigDecimal(chocolate_qtyPerPack))
						//
						.build())
				.line(QtyCalculationsBOMLine.builder()
						.bomProductId(christmasPackProductId)
						.bomProductUOM(uomEach)
						//
						.componentType(BOMComponentType.Component)
						.productId(socksProductId)
						.qtyPercentage(false)
						.uom(uomEach)
						.qtyForOneFinishedGood(new BigDecimal(socks_qtyPerPack))
						//
						.build())
				.build();

		chocolateStockDetail = ShipmentScheduleAvailableStockDetail.builder()
				.productId(chocolateProductId)
				.warehouseId(warehouseId)
				.storageAttributesKey(AttributesKey.ALL)
				.qtyOnHand(new BigDecimal(chocolate_qtyOnHand))
				.build();
		socksStockDetail = ShipmentScheduleAvailableStockDetail.builder()
				.productId(socksProductId)
				.warehouseId(warehouseId)
				.storageAttributesKey(AttributesKey.ALL)
				.qtyOnHand(new BigDecimal(socks_qtyOnHand))
				.build();
		christmasPackStockDetail = ShipmentScheduleAvailableStockDetail.builder()
				.productId(christmasPackProductId)
				.warehouseId(warehouseId)
				.storageAttributesKey(AttributesKey.ALL)
				.qtyOnHand(new BigDecimal(christmasPack_qtyOnHand))
				//
				.pickingBOM(pickingBOM)
				.componentStockDetail(chocolateStockDetail)
				.componentStockDetail(socksStockDetail)
				//
				.build();
	}

	private static I_C_UOM uom(final String name, final int precision)
	{
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setName(name);
		uom.setUOMSymbol(name);
		uom.setStdPrecision(precision);
		saveRecord(uom);
		return uom;
	}

	private static ProductId product(final String name, final I_C_UOM stockingUOM)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue(name);
		product.setName(name);
		product.setC_UOM_ID(stockingUOM.getC_UOM_ID());
		saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

}
