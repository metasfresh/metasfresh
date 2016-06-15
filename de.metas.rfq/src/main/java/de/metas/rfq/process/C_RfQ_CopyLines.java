package de.metas.rfq.process;

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.process.SvrProcess;

import de.metas.process.Param;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
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

	@Param(parameterName = "C_RfQ_ID")
	private final int p_From_RfQ_ID = 0;

	@Override
	protected String doIt()
	{
		final Properties ctx = getCtx();

		//
		final I_C_RfQ to = getRecord(I_C_RfQ.class);
		final I_C_RfQ from = InterfaceWrapperHelper.create(ctx, p_From_RfQ_ID, I_C_RfQ.class, ITrx.TRXNAME_ThreadInherited);
		Check.assumeNotNull(from, "from not null");

		// Copy Lines
		int countLines = 0;
		for (final I_C_RfQLine lineFrom : rfqDAO.retrieveLines(from))
		{
			final I_C_RfQLine newLine = InterfaceWrapperHelper.create(ctx, I_C_RfQLine.class, ITrx.TRXNAME_ThreadInherited);
			newLine.setAD_Org_ID(to.getAD_Org_ID());
			newLine.setC_RfQ(to);
			newLine.setLine(lineFrom.getLine());
			newLine.setDescription(lineFrom.getDescription());
			newLine.setHelp(lineFrom.getHelp());
			newLine.setM_Product_ID(lineFrom.getM_Product_ID());
			newLine.setM_AttributeSetInstance_ID(lineFrom.getM_AttributeSetInstance_ID());
			newLine.setDeliveryDays(lineFrom.getDeliveryDays());
			InterfaceWrapperHelper.save(newLine);

			// Copy Qtys
			for (final I_C_RfQLineQty lineQtyFrom : rfqDAO.retrieveLineQtys(lineFrom))
			{
				final I_C_RfQLineQty newQty = InterfaceWrapperHelper.create(ctx, I_C_RfQLineQty.class, ITrx.TRXNAME_ThreadInherited);
				newQty.setAD_Org_ID(newLine.getAD_Org_ID());
				newQty.setC_RfQLine(newLine);
				newQty.setC_UOM_ID(lineQtyFrom.getC_UOM_ID());
				newQty.setQty(lineQtyFrom.getQty());
				newQty.setIsOfferQty(lineQtyFrom.isOfferQty());
				newQty.setIsPurchaseQty(lineQtyFrom.isPurchaseQty());
				newQty.setMargin(lineQtyFrom.getMargin());
				InterfaceWrapperHelper.save(newQty);
			}
			countLines++;
		}

		//
		return "@C_RfQLine_ID@ #" + countLines;
	}
}
