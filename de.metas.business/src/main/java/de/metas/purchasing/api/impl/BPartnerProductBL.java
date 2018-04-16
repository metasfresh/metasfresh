package de.metas.purchasing.api.impl;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import de.metas.i18n.IMsgBL;
import de.metas.interfaces.I_C_BPartner_Product;
import de.metas.purchasing.api.IBPartnerProductBL;
import de.metas.purchasing.api.IBPartnerProductDAO;

/*
 * #%L
 * de.metas.business
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

public class BPartnerProductBL implements IBPartnerProductBL
{
	@Override
	public void assertProductNotBanned(final int productId, final int bpartnerId)
	{

		final IBPartnerProductDAO bpProductDAO = Services.get(IBPartnerProductDAO.class);

		final I_C_BPartner_Product bannedProductForPartner = bpProductDAO.getBannedProductForPartner(
				productId,
				bpartnerId);

		if (bannedProductForPartner != null)
		{
			final I_C_BPartner partner = loadOutOfTrx(bpartnerId, I_C_BPartner.class);
			final String msg = Services.get(IMsgBL.class).getMsg(
					Env.getCtx(),
					IBPartnerProductDAO.MSG_ProductSalesBanError,
					new Object[] { partner.getName(), bannedProductForPartner.getSalesBanReason() });

			throw new AdempiereException(msg);
		}
	}
}
