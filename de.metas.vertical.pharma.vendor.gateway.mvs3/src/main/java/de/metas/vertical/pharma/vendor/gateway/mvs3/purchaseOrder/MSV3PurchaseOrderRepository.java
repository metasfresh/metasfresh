package de.metas.vertical.pharma.vendor.gateway.mvs3.purchaseOrder;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.springframework.stereotype.Repository;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.mvs3
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

@Repository
public class MSV3PurchaseOrderRepository
{
	public void retrieveOrCreate(final int orderId)
	{
		final int supportId = DB.getNextID(Env.getCtx(), "MSV3_PurchaseOrder_SupportId", ITrx.TRXNAME_None);

	}


}
