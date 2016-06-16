package de.metas.rfq.process;

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.process.SvrProcess;

import de.metas.process.Param;
import de.metas.rfq.IRfqBL;
import de.metas.rfq.IRfqDAO;
import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQLine;
import de.metas.rfq.model.I_C_RfQLineQty;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * Copy {@link I_C_RfQLine}s from a given {@link I_C_RfQ}.
 *
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public class C_RfQ_CopyLines extends SvrProcess
{
	// services
	private final transient IRfqDAO rfqDAO = Services.get(IRfqDAO.class);
	private final transient IRfqBL rfqBL = Services.get(IRfqBL.class);

	@Param(parameterName = "C_RfQ_ID")
	private final int p_From_RfQ_ID = 0;

	@Override
	protected String doIt()
	{
		final Properties ctx = getCtx();

		//
		final I_C_RfQ to = getRecord(I_C_RfQ.class);
		rfqBL.assertDraft(to);

		final I_C_RfQ from = InterfaceWrapperHelper.create(ctx, p_From_RfQ_ID, I_C_RfQ.class, ITrx.TRXNAME_ThreadInherited);
		Check.assumeNotNull(from, "from not null");

		// Copy Lines
		int countLines = 0;
		for (final I_C_RfQLine lineFrom : rfqDAO.retrieveLines(from))
		{
			final I_C_RfQLine newLine = copyLineFrom(to, lineFrom);

			// Copy Qtys
			for (final I_C_RfQLineQty lineQtyFrom : rfqDAO.retrieveLineQtys(lineFrom))
			{
				copyLineQtyFrom(newLine, lineQtyFrom);
			}
			countLines++;
		}

		//
		return "@C_RfQLine_ID@ #" + countLines;
	}

	private I_C_RfQLine copyLineFrom(final I_C_RfQ to, final I_C_RfQLine lineFrom)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(to);

		final I_C_RfQLine lineTo = InterfaceWrapperHelper.create(ctx, I_C_RfQLine.class, ITrx.TRXNAME_ThreadInherited);
		lineTo.setAD_Org_ID(to.getAD_Org_ID());
		lineTo.setC_RfQ(to);
		lineTo.setLine(lineFrom.getLine());
		lineTo.setDescription(lineFrom.getDescription());
		lineTo.setHelp(lineFrom.getHelp());
		lineTo.setM_Product_ID(lineFrom.getM_Product_ID());
		lineTo.setM_AttributeSetInstance_ID(lineFrom.getM_AttributeSetInstance_ID());
		lineTo.setDeliveryDays(lineFrom.getDeliveryDays());

		InterfaceWrapperHelper.save(lineTo);
		return lineTo;
	}

	private I_C_RfQLineQty copyLineQtyFrom(final I_C_RfQLine lineTo, final I_C_RfQLineQty lineQtyFrom)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(lineTo);

		final I_C_RfQLineQty lineQtyTo = InterfaceWrapperHelper.create(ctx, I_C_RfQLineQty.class, ITrx.TRXNAME_ThreadInherited);
		lineQtyTo.setAD_Org_ID(lineTo.getAD_Org_ID());
		lineQtyTo.setC_RfQLine(lineTo);
		lineQtyTo.setC_UOM_ID(lineQtyFrom.getC_UOM_ID());
		lineQtyTo.setQty(lineQtyFrom.getQty());
		lineQtyTo.setIsOfferQty(lineQtyFrom.isOfferQty());
		lineQtyTo.setIsPurchaseQty(lineQtyFrom.isPurchaseQty());
		lineQtyTo.setMargin(lineQtyFrom.getMargin());

		InterfaceWrapperHelper.save(lineQtyTo);
		return lineQtyTo;
	}

}
