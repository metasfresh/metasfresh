package de.metas.vertical.cables.webui.quickinput;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_OrderLine;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.model.I_PP_Product_BOM;

import de.metas.adempiere.callout.OrderFastInput;
import de.metas.adempiere.model.I_C_Order;
import de.metas.product.ProductId;
import de.metas.ui.web.quickinput.IQuickInputProcessor;
import de.metas.ui.web.quickinput.QuickInput;
import de.metas.ui.web.window.datatypes.DocumentId;

/*
 * #%L
 * metasfresh-webui-api
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

public class CableSalesOrderLineQuickInputProcessor implements IQuickInputProcessor
{
	@Override
	public DocumentId process(final QuickInput quickInput)
	{
		final I_C_Order order = quickInput.getRootDocumentAs(I_C_Order.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(order);

		final I_C_OrderLine newOrderLine = OrderFastInput.addOrderLine(ctx, order, orderLine -> updateOrderLine(orderLine, quickInput));
		final int newOrderLineId = newOrderLine.getC_OrderLine_ID();
		return DocumentId.of(newOrderLineId);
	}

	private final void updateOrderLine(final I_C_OrderLine newOrderLine, final QuickInput fromQuickInput)
	{
		final ICablesOrderLineQuickInput quickInputModel = fromQuickInput.getQuickInputDocumentAs(ICablesOrderLineQuickInput.class);
		final ProductId productId = getBOMProductId(quickInputModel);

		// TODO
		throw new UnsupportedOperationException("not implemented");
	}

	private ProductId getBOMProductId(ICablesOrderLineQuickInput quickInputModel)
	{
		final ProductId plugProduct1Id = ProductId.ofRepoId(quickInputModel.getPlug1_Product_ID());
		final ProductId plugProduct2Id = ProductId.ofRepoId(quickInputModel.getPlug2_Product_ID());
		final ProductId cableProductId = ProductId.ofRepoId(quickInputModel.getCable_Product_ID());

		final IProductBOMDAO productBOMsRepo = Services.get(IProductBOMDAO.class);
		final List<I_PP_Product_BOM> boms = productBOMsRepo.retrieveBOMsContainingExactProducts(plugProduct1Id.getRepoId(), cableProductId.getRepoId(), plugProduct2Id.getRepoId());
		if (boms.isEmpty())
		{
			throw new AdempiereException("@NotFound@ @PP_Product_BOM_ID@");
		}
		else if (boms.size() > 1)
		{
			final String bomValues = boms.stream().map(I_PP_Product_BOM::getValue).collect(Collectors.joining(", "));
			throw new AdempiereException("More than one BOMs found: " + bomValues);
		}

		final I_PP_Product_BOM bom = boms.get(0);
		return ProductId.ofRepoId(bom.getM_Product_ID());
	}

}
