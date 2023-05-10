package de.metas.requisition;

import de.metas.lang.SOTrx;
import de.metas.organization.OrgId;
import org.compiere.model.I_M_Requisition;
import org.compiere.model.I_M_RequisitionLine;
import org.compiere.model.MCharge;
import org.compiere.model.MProductPricing;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/*
 * #%L
 * de.metas.business
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

@Service
public class RequisitionService
{
	public BigDecimal computePrice(final I_M_Requisition requisition, final I_M_RequisitionLine line)
	{
		if (line.getC_Charge_ID() > 0)
		{
			final MCharge charge = MCharge.get(Env.getCtx(), line.getC_Charge_ID());
			return charge.getChargeAmt();
		}
		else if (line.getM_Product_ID() > 0)
		{
			final int priceListId = requisition.getM_PriceList_ID();
			if (priceListId <= 0)
			{
				// TODO: metas: we already changed the whole pricing engine, so for now we accept to not have a price here
				// throw new AdempiereException("PriceList unknown!");
				return BigDecimal.ZERO;
			}
			else
			{
				final MProductPricing pp = new MProductPricing(
						OrgId.ofRepoId(requisition.getAD_Org_ID()),
						line.getM_Product_ID(),
						line.getC_BPartner_ID(),
						null,
						line.getQty(),
						SOTrx.PURCHASE.toBoolean());
				pp.setM_PriceList_ID(priceListId);
				// pp.setPriceDate(getDateOrdered());
				//
				return pp.getPriceStd();
			}
		}
		else
		{
			return null;
		}
	}

	public void updateLineNetAmt(final I_M_RequisitionLine line)
	{
		BigDecimal lineNetAmt = line.getQty().multiply(line.getPriceActual());
		line.setLineNetAmt(lineNetAmt);
	}
}
