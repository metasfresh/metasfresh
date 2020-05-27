/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.quickinput.ddorderline;

import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.DDOrderLineHUPackingAware;
import de.metas.handlingunits.model.I_DD_OrderLine;
import de.metas.product.ProductId;
import de.metas.ui.web.quickinput.IQuickInputProcessor;
import de.metas.ui.web.quickinput.QuickInput;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.eevolution.model.I_DD_Order;

import java.math.BigDecimal;
import java.util.Set;

public class DDOrderLineQuickInputProcessor implements IQuickInputProcessor
{
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IHUPackingAwareBL huPackingAwareBL = Services.get(IHUPackingAwareBL .class);

	@Override
	public Set<DocumentId> process(final QuickInput quickInput)
	{
		final IDDOrderLineQuickInput ddOrderQuickInput = quickInput.getQuickInputDocumentAs(IDDOrderLineQuickInput.class);

		final ProductId productId = ProductId.ofRepoId(ddOrderQuickInput.getM_Product_ID());
		final int mHUPIProductID = ddOrderQuickInput.getM_HU_PI_Item_Product_ID();
		final BigDecimal qty = ddOrderQuickInput.getQty();

		final I_DD_Order ddOrder = quickInput.getRootDocumentAs(I_DD_Order.class);
		final I_DD_OrderLine ddOrderLine = InterfaceWrapperHelper.newInstance(I_DD_OrderLine.class, ddOrder);
		ddOrderLine.setIsInvoiced(false);
		ddOrderLine.setDD_Order_ID(ddOrder.getDD_Order_ID());

		ddOrderLine.setM_Product_ID(productId.getRepoId());
		ddOrderLine.setC_UOM_ID(ddOrderQuickInput.getM_Product().getC_UOM_ID());

		final WarehouseId warehouseIdFrom = WarehouseId.ofRepoId(ddOrder.getM_Warehouse_From_ID());
		final LocatorId locatorFromId = getDefaultLocatorID(warehouseIdFrom, "DD_Order.M_Warehouse_From_ID");
		ddOrderLine.setM_Locator_ID(locatorFromId.getRepoId());

		final WarehouseId warehouseToId = WarehouseId.ofRepoId(ddOrder.getM_Warehouse_To_ID());
		final LocatorId locatorToId = getDefaultLocatorID(warehouseToId, "DD_Order.M_Warehouse_To_ID");
		ddOrderLine.setM_LocatorTo_ID(locatorToId.getRepoId());

		if (mHUPIProductID > 0)
		{
			ddOrderLine.setM_HU_PI_Item_Product_ID(mHUPIProductID);
			ddOrderLine.setQtyEnteredTU(qty);

			final IHUPackingAware packingAware = new DDOrderLineHUPackingAware(ddOrderLine);

			huPackingAwareBL.setQtyCUFromQtyTU(packingAware, qty.intValue());
		}
		else
		{
			ddOrderLine.setQtyEntered(qty);
		}

		InterfaceWrapperHelper.saveRecord(ddOrderLine);

		final DocumentId documentId = DocumentId.of(ddOrderLine.getDD_OrderLine_ID());
		return ImmutableSet.of(documentId);
	}

	@NonNull
	private LocatorId getDefaultLocatorID(@NonNull final WarehouseId warehouseId, @NonNull final String warehouseIDColumn)
	{
		final LocatorId locatorId = warehouseBL.getDefaultLocatorId(warehouseId);

		if (locatorId == null)
		{
			throw new AdempiereException("There is no locator available for the selected warehouse!")
					.appendParametersToMessage()
					.setParameter(warehouseIDColumn, warehouseId);
		}

		return locatorId;
	}
}
