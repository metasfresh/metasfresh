/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved. *
 * Contributor(s): victor.perez@e-evolution.com http://www.e-evolution.com *
 * Teo Sarca, www.arhipac.ro *
 *****************************************************************************/

package org.adempiere.model.engines;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Storage;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MStorage;
import org.compiere.model.MTransaction;
import org.compiere.model.X_AD_Client;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Cost_CollectorMA;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.IStorageBL;
import de.metas.product.ProductId;
import de.metas.util.Services;

/**
 * Storage Engine
 *
 * @author victor.perez@e-evolution.com http://www.e-evolution.com
 * @author Teo Sarca, www.arhipac.ro
 */
public class StorageEngine
{

	/** Logger */
	protected static transient Logger log = LogManager.getLogger(StorageEngine.class);

	public static void createTrasaction(
			final I_PP_Cost_Collector cc,
			final String MovementType,
			final Timestamp MovementDate,
			BigDecimal Qty,
			final boolean isReversal,
			final int M_Warehouse_ID,
			final int o_M_AttributeSetInstance_ID,
			final int o_M_Warehouse_ID,
			final boolean isSOTrx)
	{
		final int productId = cc.getM_Product_ID();
		// Incoming Trx
		final boolean incomingTrx = MovementType.charAt(1) == '+';	// V+ Vendor Receipt

		if (productId > 0 && Services.get(IProductBL.class).isStocked(productId))
		{
			// Ignore the Material Policy when is Reverse Correction
			if (!isReversal)
			{
				checkMaterialPolicy(cc, MovementType, MovementDate, M_Warehouse_ID);
			}

			// Reservation ASI
			final int reservationAttributeSetInstance_ID = o_M_AttributeSetInstance_ID;
			//
			final IStorageBL storageBL = Services.get(IStorageBL.class);
			if (cc.getM_AttributeSetInstance_ID() == 0)
			{
				for (final I_PP_Cost_CollectorMA ma : getMA(cc))
				{
					BigDecimal QtyMA = ma.getMovementQty();
					if (!incomingTrx)
					{
						QtyMA = QtyMA.negate();
					}

					// Update Storage - see also VMatch.createMatchRecord
					if (!storageBL.add(Env.getCtx(),
							M_Warehouse_ID,
							cc.getM_Locator_ID(),
							cc.getM_Product_ID(),
							ma.getM_AttributeSetInstance_ID(), reservationAttributeSetInstance_ID,
							QtyMA,
							BigDecimal.ZERO,
							BigDecimal.ZERO,
							ITrx.TRXNAME_ThreadInherited))
					{
						// won't happen because MStorage.add() either succeeds and returns true, or throws an exception
						throw new LiberoException("Cannot correct Inventory (Material allocation)");
					}
					create(cc,
							MovementType,
							MovementDate,

							// #gh489: M_Storage is a legacy and currently doesn't really work.
							// In this case, its use of M_AttributeSetInstance_ID (which is forwarded from storage to 'ma') introduces a coupling between random documents.
							// this coupling is a big problem, so we don't forward the ASI-ID to the M_Transaction
							0, // ma.getM_AttributeSetInstance_ID(),

							QtyMA);
				}
			}
			// sLine.getM_AttributeSetInstance_ID() != 0
			// if (mtrx == null)
			else
			{

				if (!incomingTrx)
				{
					Qty = Qty.negate();
				}

				// Fallback: Update Storage - see also VMatch.createMatchRecord
				if (!storageBL.add(Env.getCtx(), M_Warehouse_ID,
						cc.getM_Locator_ID(),
						cc.getM_Product_ID(),
						cc.getM_AttributeSetInstance_ID(), reservationAttributeSetInstance_ID,
						Qty, BigDecimal.ZERO, BigDecimal.ZERO, ITrx.TRXNAME_ThreadInherited))
				{
					// won't happen because MStorage.add() either succeeds and returns true, or throws an exception
					throw new LiberoException("Cannot correct Inventory (Material allocation)");
				}
				create(cc, MovementType, MovementDate, cc.getM_AttributeSetInstance_ID(), Qty);
			}
		}	// stock movement
	}

	private static void checkMaterialPolicy(
			final I_PP_Cost_Collector cc,
			final String MovementType,
			final Timestamp MovementDate,
			final int M_Warehouse_ID)
	{
		deleteMA(cc);

		final ProductId productId = ProductId.ofRepoId(cc.getM_Product_ID());

		// Incoming Trx
		final boolean incomingTrx = MovementType.charAt(1) == '+';	// V+ Vendor Receipt
		final String mmPolicy = Services.get(IProductBL.class).getMMPolicy(productId.getRepoId());

		// Attribute Set Instance
		// Create an Attribute Set Instance to any receipt FIFO/LIFO
		if (cc.getM_AttributeSetInstance_ID() == 0)
		{
			//
			// Incomming transaction
			// (we receive materials to our warehouse/locator)
			if (incomingTrx)
			{
				cc.setM_AttributeSetInstance_ID(0 /* asi.getM_AttributeSetInstance_ID() */);
				log.info("New ASI=" + cc);
				createMA(cc, cc.getM_AttributeSetInstance_ID(), cc.getMovementQty());
			}
			//
			// Outgoing transaction
			// (we ship materials from our warehouse/locator)
			else
			{
				final Timestamp minGuaranteeDate = MovementDate;
				final MStorage[] storages = MStorage.getWarehouse(
						Env.getCtx(),
						M_Warehouse_ID,
						productId.getRepoId(),
						cc.getM_AttributeSetInstance_ID(),
						minGuaranteeDate,
						X_AD_Client.MMPOLICY_FiFo.equals(mmPolicy),
						true,
						cc.getM_Locator_ID(),
						ITrx.TRXNAME_ThreadInherited);
				BigDecimal qtyToDeliver = cc.getMovementQty();
				for (final I_M_Storage storage : storages)
				{
					if (storage.getQtyOnHand().compareTo(qtyToDeliver) >= 0)
					{
						createMA(cc,
								0, // storage.getM_AttributeSetInstance_ID(),
								qtyToDeliver);
						qtyToDeliver = BigDecimal.ZERO;
					}
					else
					{
						createMA(cc,
								0, // storage.getM_AttributeSetInstance_ID(),
								storage.getQtyOnHand());
						qtyToDeliver = qtyToDeliver.subtract(storage.getQtyOnHand());
						log.debug("QtyToDeliver=" + qtyToDeliver);
					}

					if (qtyToDeliver.signum() == 0)
					{
						break;
					}
				}

				if (qtyToDeliver.signum() != 0)
				{
					// deliver using new asi
					final I_M_Product product = Services.get(IProductDAO.class).getById(productId);
					final MAttributeSetInstance asi = MAttributeSetInstance.create(Env.getCtx(), product, ITrx.TRXNAME_ThreadInherited);
					createMA(cc, asi.getM_AttributeSetInstance_ID(), qtyToDeliver);
				}
			}	// outgoing Trx
		}
		else
		{
			if (incomingTrx)
			{
				;
			}
			else
			{
				createMA(cc, cc.getM_AttributeSetInstance_ID(), cc.getMovementQty());
			}
		}
		saveRecord(cc);
	}

	private static int deleteMA(final I_PP_Cost_Collector cc)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_PP_Cost_CollectorMA.class)
				.addEqualsFilter(I_PP_Cost_CollectorMA.COLUMNNAME_PP_Cost_Collector_ID, cc.getPP_Cost_Collector_ID())
				.create()
				.deleteDirectly();
	}

	private static void create(
			final I_PP_Cost_Collector cc,
			final String MovementType,
			final Timestamp MovementDate,
			final int M_AttributeSetInstance_ID,
			final BigDecimal Qty)
	{

		final MTransaction mtrx = new MTransaction(
				Env.getCtx(),
				cc.getAD_Org_ID(),
				MovementType,
				cc.getM_Locator_ID(),
				cc.getM_Product_ID(),
				M_AttributeSetInstance_ID,
				Qty,
				MovementDate,
				ITrx.TRXNAME_ThreadInherited);
		mtrx.setPP_Cost_Collector_ID(cc.getPP_Cost_Collector_ID());
		saveRecord(mtrx);

		CostEngineFactory.newCostEngine().createOrUpdateCostDetail(cc, mtrx);
	}

	private static void createMA(final I_PP_Cost_Collector cc, final int M_AttributeSetInstance_ID, final BigDecimal MovementQty)
	{
		final I_PP_Cost_Collector ma = newInstance(I_PP_Cost_Collector.class);
		ma.setAD_Org_ID(cc.getAD_Org_ID());
		ma.setPP_Cost_Collector_ID(cc.getPP_Cost_Collector_ID());
		ma.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
		ma.setMovementQty(MovementQty);

		saveRecord(ma);
	}

	private static List<I_PP_Cost_CollectorMA> getMA(final I_PP_Cost_Collector cc)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_PP_Cost_CollectorMA.class)
				.addEqualsFilter(I_PP_Cost_CollectorMA.COLUMNNAME_PP_Cost_Collector_ID, cc.getPP_Cost_Collector_ID())
				.create()
				.list();
	}

	/**
	 * Set (default) Locator based on qty.
	 *
	 * @param Qty quantity Assumes Warehouse is set
	 */
	public static int getM_Locator_ID(
			final Properties ctx,
			final int M_Warehouse_ID,
			final int M_Product_ID, final int M_AttributeSetInstance_ID,
			final BigDecimal Qty,
			final String trxName)
	{
		// Get existing Location
		int M_Locator_ID = MStorage.getM_Locator_ID(M_Warehouse_ID,
				M_Product_ID, M_AttributeSetInstance_ID,
				Qty, trxName);
		// Get default Location
		if (M_Locator_ID <= 0)
		{
			M_Locator_ID = Services.get(IWarehouseBL.class).getDefaultLocatorId(WarehouseId.ofRepoId(M_Warehouse_ID)).getRepoId();
		}
		return M_Locator_ID;
	}	// setM_Locator_ID

}
