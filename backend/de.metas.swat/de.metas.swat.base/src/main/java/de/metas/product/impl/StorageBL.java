package de.metas.product.impl;

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
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Locator;
import org.compiere.model.MStorage;

import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.product.IStorageBL;
import de.metas.product.async.spi.impl.M_Storage_Add;

public class StorageBL implements IStorageBL
{
	private static final String CFG_M_STORAGE_DISABLED = "de.metas.product.M_Storage_Disabled";

	@Override
	public void addQtyOrdered(final Properties ctx,
			final I_M_Locator locator,
			final int productId,
			final int attributeSetInstanceId,
			final BigDecimal diffQtyOrdered,
			final String trxName)
	{
		if (isMStorageDisabled())
		{
			return;
		}

		final boolean ok = MStorage.add(ctx,
				locator.getM_Warehouse_ID(),
				locator.getM_Locator_ID(),
				productId, // M_Product_ID,
				attributeSetInstanceId, // M_AttributeSetInstance_ID,
				attributeSetInstanceId, // reservationAttributeSetInstance_ID,
				BigDecimal.ZERO, // diffQtyOnHand,
				BigDecimal.ZERO, // diffQtyReserved,
				diffQtyOrdered,  // diffQtyOrdered
				trxName);

		if (!ok)
		{
			throw new AdempiereException();
		}
	}

	@Override
	public void addQtyReserved(final Properties ctx,
			final I_M_Locator locator,
			final int productId,
			final int attributeSetInstanceId,
			final BigDecimal diffQtyReserved,
			final String trxName)
	{
		if (isMStorageDisabled())
		{
			return;
		}

		final boolean ok = MStorage.add(ctx,
				locator.getM_Warehouse_ID(),
				locator.getM_Locator_ID(),
				productId, // M_Product_ID,
				attributeSetInstanceId, // M_AttributeSetInstance_ID,
				attributeSetInstanceId, // reservationAttributeSetInstance_ID,
				BigDecimal.ZERO, // diffQtyOnHand,
				diffQtyReserved, // diffQtyReserved,
				BigDecimal.ZERO, // diffQtyOrdered
				trxName);

		if (!ok)
		{
			throw new AdempiereException();
		}
	}

	@Override
	public void addAsync(final Properties ctx,
			final int M_Warehouse_ID,
			final int M_Locator_ID,
			final int M_Product_ID,
			final int M_AttributeSetInstance_ID,
			final int reservationAttributeSetInstance_ID,
			final BigDecimal diffQtyOnHand,
			final BigDecimal diffQtyReserved,
			final BigDecimal diffQtyOrdered,
			final String trxName)
	{
		if (isMStorageDisabled())
		{
			return;
		}

		// @formatter:off
		Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, M_Storage_Add.class)
			.newBlock()
				.newWorkpackage()
				.bindToTrxName(trxName)
				.parameters()
					.setParameter(M_Storage_Add.WP_PARAM_M_Warehouse_ID, M_Warehouse_ID)
					.setParameter(M_Storage_Add.WP_PARAM_M_Locator_ID, M_Locator_ID)
					.setParameter(M_Storage_Add.WP_PARAM_M_Product_ID, M_Product_ID)
					.setParameter(M_Storage_Add.WP_PARAM_M_AttributeSetInstance_ID, M_AttributeSetInstance_ID)
					.setParameter(M_Storage_Add.WP_PARAM_reservationAttributeSetInstance_ID, reservationAttributeSetInstance_ID)
					.setParameter(M_Storage_Add.WP_PARAM_diffQtyOnHand, diffQtyOnHand)
					.setParameter(M_Storage_Add.WP_PARAM_diffQtyReserved, diffQtyReserved)
					.setParameter(M_Storage_Add.WP_PARAM_diffQtyOrdered, diffQtyOrdered)
				.end()
				.build(); // @formatter:on
	}

	@Override
	public boolean add(
			final Properties ctx,
			final int M_Warehouse_ID,
			final int M_Locator_ID,
			final int M_Product_ID,
			final int M_AttributeSetInstance_ID,
			final int reservationAttributeSetInstance_ID,
			final BigDecimal diffQtyOnHand,
			final BigDecimal diffQtyReserved,
			final BigDecimal diffQtyOrdered,
			final String trxName)
	{
		if (isMStorageDisabled())
		{
			return true;
		}

		return MStorage.add(ctx,
				M_Warehouse_ID,
				M_Locator_ID,
				M_Product_ID,
				M_AttributeSetInstance_ID,
				reservationAttributeSetInstance_ID,
				diffQtyOnHand,
				diffQtyReserved,
				diffQtyOrdered,
				trxName);
	}

	private boolean isMStorageDisabled()
	{
		return Services.get(ISysConfigBL.class).getBooleanValue(CFG_M_STORAGE_DISABLED, false);
	}
}
