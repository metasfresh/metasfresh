/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 2008 SC ARHIPAC SERVICE SRL. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 *****************************************************************************/
package de.metas.material.planning.exception;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;

/**
 * Thrown when no Plant was found for given Warehouse
 *
 * @author Teo Sarca
 */
public class NoPlantForWarehouseException extends MrpException
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1573931274773612201L;

	public NoPlantForWarehouseException(final int adOrgId, final int warehouseId, final int productId)
	{
		super(buildMsg(adOrgId, warehouseId, productId));
	}

	private static String buildMsg(final int adOrgId, final int warehouseId, final int productId)
	{
		// TODO: vpj-cd create the msg for error
		final StringBuilder sb = new StringBuilder("@NoPlantForWarehouseException@");

		if (warehouseId > 0)
		{
			final I_M_Warehouse warehouse = InterfaceWrapperHelper.create(Env.getCtx(), warehouseId, I_M_Warehouse.class, ITrx.TRXNAME_None);
			final String warehouseName = warehouse == null ? String.valueOf(warehouseId) : warehouse.getName();
			sb.append("\n @M_Warehouse_ID@ : " + warehouseName);
		}
		if (adOrgId > 0)
		{
			sb.append("\n @AD_Org_ID@: " + adOrgId);
		}
		if (productId > 0)
		{
			sb.append("\n @M_Product_ID@: " + productId);
		}

		return sb.toString();
	}
}
