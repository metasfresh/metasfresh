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

import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;

import javax.annotation.Nullable;

/**
 * Thrown when no Plant was found for given Warehouse
 *
 * @author Teo Sarca
 */
public class NoPlantForWarehouseException extends AdempiereException
{
	public NoPlantForWarehouseException(@Nullable final OrgId adOrgId, @Nullable final WarehouseId warehouseId, @Nullable final ProductId productId)
	{
		super(buildMsg(adOrgId, warehouseId, productId));
	}

	private static String buildMsg(@Nullable final OrgId adOrgId, @Nullable final WarehouseId warehouseId, @Nullable final ProductId productId)
	{
		final StringBuilder sb = new StringBuilder("@NoPlantForWarehouseException@");

		if (warehouseId != null)
		{
			final String warehouseName = Services.get(IWarehouseDAO.class).getWarehouseName(warehouseId);
			sb.append("\n @M_Warehouse_ID@ : ").append(warehouseName);
		}
		if (adOrgId != null)
		{
			sb.append("\n @AD_Org_ID@: ").append(adOrgId);
		}
		if (productId != null)
		{
			sb.append("\n @M_Product_ID@: ").append(productId);
		}

		return sb.toString();
	}
}
