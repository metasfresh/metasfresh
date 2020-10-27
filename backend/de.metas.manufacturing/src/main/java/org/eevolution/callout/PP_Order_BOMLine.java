package org.eevolution.callout;

import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.compiere.model.I_C_UOM;
import org.eevolution.model.I_PP_Order_BOMLine;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
 * Manufacturing order BOM Line callout
 *
 * @author metas-dev <dev@metasfresh.com>
 * @author based on initial version developed by Victor Perez, Teo Sarca under ADempiere project
 */
@Callout(I_PP_Order_BOMLine.class)
public class PP_Order_BOMLine
{
	private final IPPOrderBOMBL orderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	/**
	 * Calculates and sets QtyRequired from QtyEntered and C_UOM_ID
	 */
	@CalloutMethod(columnNames = {
			I_PP_Order_BOMLine.COLUMNNAME_M_Product_ID,
			I_PP_Order_BOMLine.COLUMNNAME_QtyEntered,
			I_PP_Order_BOMLine.COLUMNNAME_C_UOM_ID })
	private void updateQtyRequired(final I_PP_Order_BOMLine bomLine)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(bomLine.getM_Product_ID());
		if (productId == null)
		{
			return;
		}

		final UomId uomId = UomId.ofRepoIdOrNull(bomLine.getC_UOM_ID());
		if (uomId == null)
		{
			return;
		}

		final I_C_UOM uom = uomDAO.getById(uomId);
		final Quantity qtyEntered = Quantity.of(bomLine.getQtyEntered(), uom);

		orderBOMBL.setQtyRequiredToIssueOrReceive(bomLine, qtyEntered);
	}
}
