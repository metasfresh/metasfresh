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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.api.IInOutCandidateBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.spi.impl.IQtyAndQuality;
import de.metas.inoutcandidate.spi.impl.MutableQtyAndQuality;
import de.metas.inoutcandidate.spi.impl.QualityNoticesCollection;

public class InOutCandidateBL implements IInOutCandidateBL
{
	@Override
	public InOutGenerateResult createEmptyInOutGenerateResult(final boolean storeReceipts)
	{
		return new DefaultInOutGenerateResult(storeReceipts);
	}

	@Override
	public IQtyAndQuality getQtyAndQuality(final org.compiere.model.I_M_InOutLine inoutLine0)
	{
		I_M_InOutLine inoutLine = InterfaceWrapperHelper.create(inoutLine0, I_M_InOutLine.class);

		// note: QtyEnetered and MovementQty are currently the same, but that's just because we are in the habit of setting M_InOutLine.C_UOM to the respective prodcuts stocking UOM.
		// therefore, we need to use MovementQty, unless we decide to add the UOM to this game, too (but currently i don't see the point).
		final BigDecimal qtyMoved = inoutLine.getMovementQty();

		final BigDecimal qtyMovedWithIssues;
		if (inoutLine.isInDispute())
		{
			qtyMovedWithIssues = qtyMoved;
		}
		else
		{
			qtyMovedWithIssues = BigDecimal.ZERO;
		}

		final MutableQtyAndQuality qtys = new MutableQtyAndQuality();
		qtys.addQtyAndQtyWithIssues(qtyMoved, qtyMovedWithIssues);
		qtys.addQualityNotices(QualityNoticesCollection.valueOfQualityNoticesString(inoutLine.getQualityNote()));

		return qtys;
	}

}
