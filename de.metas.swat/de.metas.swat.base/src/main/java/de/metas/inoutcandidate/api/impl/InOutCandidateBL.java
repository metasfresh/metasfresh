package de.metas.inoutcandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.api.IInOutCandidateBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.spi.impl.ReceiptQty;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.inoutcandidate.spi.impl.QualityNoticesCollection;

public class InOutCandidateBL implements IInOutCandidateBL
{
	@Override
	public InOutGenerateResult createEmptyInOutGenerateResult(final boolean storeReceipts)
	{
		return new DefaultInOutGenerateResult(storeReceipts);
	}

	@Override
	public ReceiptQty getQtyAndQuality(final org.compiere.model.I_M_InOutLine inoutLine0)
	{
		I_M_InOutLine inoutLine = InterfaceWrapperHelper.create(inoutLine0, I_M_InOutLine.class);

		final ProductId productId = ProductId.ofRepoId(inoutLine.getM_Product_ID());

		// note: QtyEnetered and MovementQty are currently the same, but that's just because we are in the habit of setting M_InOutLine.C_UOM to the respective prodcuts stocking UOM.
		// therefore, we need to use MovementQty, unless we decide to add the UOM to this game, too (but currently i don't see the point).

		final BigDecimal qtyInUOM;
		final UomId uomId;
		if (InterfaceWrapperHelper.isNull(inoutLine, I_M_InOutLine.COLUMNNAME_QtyDeliveredCatch))
		{
			qtyInUOM = inoutLine.getQtyEntered();
			uomId = UomId.ofRepoId(inoutLine.getC_UOM_ID());
		}
		else
		{
			qtyInUOM = inoutLine.getQtyDeliveredCatch();
			uomId = UomId.ofRepoId(inoutLine.getCatch_UOM_ID());
		}

		final StockQtyAndUOMQty qtyMoved = StockQtyAndUOMQtys.create(inoutLine.getMovementQty(), productId, qtyInUOM, uomId);

		final StockQtyAndUOMQty qtyMovedWithIssues;
		if (inoutLine.isInDispute())
		{
			qtyMovedWithIssues = qtyMoved;
		}
		else
		{
			qtyMovedWithIssues = qtyMoved.toZero();
		}

		final ReceiptQty qtys = new ReceiptQty(productId, uomId);

		qtys.addQtyAndQtyWithIssues(qtyMoved, qtyMovedWithIssues);
		qtys.addQualityNotices(QualityNoticesCollection.valueOfQualityNoticesString(inoutLine.getQualityNote()));

		return qtys;
	}

}
