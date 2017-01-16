/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): victor.perez@e-evolution.com http://www.e-evolution.com    *
 *                 Teo Sarca, www.arhipac.ro                                  *
 *****************************************************************************/

package org.adempiere.model.engines;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_M_Storage;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MClient;
import org.compiere.model.MProduct;
import org.compiere.model.MStorage;
import org.compiere.model.MTransaction;
import org.compiere.model.MWarehouse;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.eevolution.exceptions.LiberoException;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.product.IProductBL;

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
			IDocumentLine docLine,
			String MovementType,
			Timestamp MovementDate,
			BigDecimal Qty,
			boolean isReversal,
			int M_Warehouse_ID,
			int o_M_AttributeSetInstance_ID,
			int o_M_Warehouse_ID,
			boolean isSOTrx
			)
	{
		MProduct product = MProduct.get(docLine.getCtx(), docLine.getM_Product_ID());
		// Incoming Trx
		boolean incomingTrx = MovementType.charAt(1) == '+';	// V+ Vendor Receipt

		if (product != null && Services.get(IProductBL.class).isStocked(product))
		{
			// Ignore the Material Policy when is Reverse Correction
			if (!isReversal)
			{
				checkMaterialPolicy(docLine, MovementType, MovementDate, M_Warehouse_ID);
			}

			// Reservation ASI
			int reservationAttributeSetInstance_ID = o_M_AttributeSetInstance_ID;
			//
			if (docLine.getM_AttributeSetInstance_ID() == 0)
			{
				IInventoryAllocation mas[] = StorageEngine.getMA(docLine);
				for (int j = 0; j < mas.length; j++)
				{
					IInventoryAllocation ma = mas[j];
					BigDecimal QtyMA = ma.getMovementQty();
					if (!incomingTrx)	// C- Customer Shipment - V- Vendor Return
						QtyMA = QtyMA.negate();

					// Update Storage - see also VMatch.createMatchRecord
					if (!MStorage.add(docLine.getCtx(), M_Warehouse_ID,
							docLine.getM_Locator_ID(),
							docLine.getM_Product_ID(),
							ma.getM_AttributeSetInstance_ID(), reservationAttributeSetInstance_ID,
							QtyMA,
							BigDecimal.ZERO,
							BigDecimal.ZERO,
							docLine.get_TrxName()))
					{
						// won't happen because MStorage.add() either succeeds and returns true, or throws an exception
						throw new LiberoException("Cannot correct Inventory (Material allocation)");
					}
					create(docLine,
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

				if (!incomingTrx)	// C- Customer Shipment - V- Vendor Return
					Qty = Qty.negate();

				// Fallback: Update Storage - see also VMatch.createMatchRecord
				if (!MStorage.add(docLine.getCtx(), M_Warehouse_ID,
						docLine.getM_Locator_ID(),
						docLine.getM_Product_ID(),
						docLine.getM_AttributeSetInstance_ID(), reservationAttributeSetInstance_ID,
						Qty, BigDecimal.ZERO, BigDecimal.ZERO, docLine.get_TrxName()))
				{
					// won't happen because MStorage.add() either succeeds and returns true, or throws an exception
					throw new LiberoException("Cannot correct Inventory (Material allocation)");
				}
				create(docLine, MovementType, MovementDate, docLine.getM_AttributeSetInstance_ID(), Qty);
			}
		}	// stock movement
	}

	private static void checkMaterialPolicy(
			IDocumentLine line,
			String MovementType,
			Timestamp MovementDate,
			int M_Warehouse_ID)
	{
		deleteMA(line);

		// Incoming Trx
		final boolean incomingTrx = MovementType.charAt(1) == '+';	// V+ Vendor Receipt
		final MProduct product = MProduct.get(line.getCtx(), line.getM_Product_ID());
		final String mmPolicy = Services.get(IProductBL.class).getMMPolicy(product);

		// Need to have Location
		if (line.getM_Locator_ID() == 0)
		{
			// MWarehouse w = MWarehouse.get(getCtx(), getM_Warehouse_ID());
			// line.setM_Warehouse_ID(M_Warehouse_ID);
			// line.setM_Locator_ID(getM_Locator_ID(line.getCtx(),line.getM_Warehouse_ID(), line.getM_Product_ID(),line.getM_AttributeSetInstance_ID(), incomingTrx ? Env.ZERO : line.getMovementQty(),
			// line.get_TrxName()));
		}

		// Attribute Set Instance
		// Create an Attribute Set Instance to any receipt FIFO/LIFO
		if (line.getM_AttributeSetInstance_ID() == 0)
		{
			//
			// Incomming transaction
			// (we receive materials to our warehouse/locator)
			if (incomingTrx)
			{
				MAttributeSetInstance asi = null;
				// auto balance negative on hand
				MStorage[] storages = MStorage.getWarehouse(line.getCtx(), M_Warehouse_ID, line.getM_Product_ID(), 0,
						null, MClient.MMPOLICY_FiFo.equals(mmPolicy), false, line.getM_Locator_ID(), line.get_TrxName());
				for (final I_M_Storage storage : storages)
				{
					if (storage.getQtyOnHand().signum() < 0)
					{
						asi = new MAttributeSetInstance(line.getCtx(), storage.getM_AttributeSetInstance_ID(), line.get_TrxName());
						break;
					}
				}
				// always create asi so fifo/lifo work.
				if (asi == null)
				{
					asi = MAttributeSetInstance.create(line.getCtx(), product, line.get_TrxName());
				}
				line.setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID());
				log.info("New ASI=" + line);
				createMA(line, line.getM_AttributeSetInstance_ID(), line.getMovementQty());
			}
			//
			// Outgoing transaction
			// (we ship materials from our warehouse/locator)
			else
			{
				Timestamp minGuaranteeDate = MovementDate;
				MStorage[] storages = MStorage.getWarehouse(line.getCtx(), M_Warehouse_ID, line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
						minGuaranteeDate, MClient.MMPOLICY_FiFo.equals(mmPolicy), true, line.getM_Locator_ID(), line.get_TrxName());
				BigDecimal qtyToDeliver = line.getMovementQty();
				for (final I_M_Storage storage : storages)
				{
					if (storage.getQtyOnHand().compareTo(qtyToDeliver) >= 0)
					{
						createMA(line, storage.getM_AttributeSetInstance_ID(), qtyToDeliver);
						qtyToDeliver = BigDecimal.ZERO;
					}
					else
					{
						createMA(line, storage.getM_AttributeSetInstance_ID(), storage.getQtyOnHand());
						qtyToDeliver = qtyToDeliver.subtract(storage.getQtyOnHand());
						log.debug("QtyToDeliver=" + qtyToDeliver);
					}

					if (qtyToDeliver.signum() == 0)
						break;
				}

				if (qtyToDeliver.signum() != 0)
				{
					// deliver using new asi
					MAttributeSetInstance asi = MAttributeSetInstance.create(line.getCtx(), product, line.get_TrxName());
					createMA(line, asi.getM_AttributeSetInstance_ID(), qtyToDeliver);
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
				createMA(line, line.getM_AttributeSetInstance_ID(), line.getMovementQty());
			}
		}
		save(line);
	}

	private static String getTableNameMA(IDocumentLine model)
	{
		return model.get_TableName() + "MA";
	}

	private static int deleteMA(IDocumentLine model)
	{
		String sql = "DELETE FROM " + getTableNameMA(model) + " WHERE " + model.get_TableName() + "_ID=?";
		int no = DB.executeUpdateEx(sql, new Object[] { model.get_ID() }, model.get_TrxName());
		if (no > 0)
			log.info("Deleted old #" + no);
		return no;
	}

	private static void saveMA(IInventoryAllocation ma)
	{
		InterfaceWrapperHelper.save(ma);
	}

	private static void save(IDocumentLine line)
	{
		InterfaceWrapperHelper.save(line);
	}

	private static void create(IDocumentLine model,
			String MovementType,
			Timestamp MovementDate,
			int M_AttributeSetInstance_ID,
			BigDecimal Qty)
	{

		MTransaction mtrx = new MTransaction(
				model.getCtx(),
				model.getAD_Org_ID(),
				MovementType,
				model.getM_Locator_ID(),
				model.getM_Product_ID(),
				M_AttributeSetInstance_ID,
				Qty,
				MovementDate,
				model.get_TrxName());
		setReferenceLine_ID(mtrx, model);
		mtrx.saveEx();
		CostEngineFactory.getCostEngine(model.getAD_Client_ID()).createCostDetail(model, mtrx);
	}

	private static IInventoryAllocation createMA(IDocumentLine model, int M_AttributeSetInstance_ID, BigDecimal MovementQty)
	{
		final Properties ctx = model.getCtx();
		final String tableName = getTableNameMA(model);
		final String trxName = model.get_TrxName();

		final PO maPO = TableModelLoader.instance.newPO(ctx, tableName, trxName);
		final IInventoryAllocation ma = InterfaceWrapperHelper.create(maPO, IInventoryAllocation.class);
		ma.setAD_Org_ID(model.getAD_Org_ID());
		setReferenceLine_ID(maPO, model);
		ma.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
		ma.setMovementQty(MovementQty);

		saveMA(ma);
		log.debug("##: " + ma);

		return ma;
	}

	private static IInventoryAllocation[] getMA(IDocumentLine model)
	{
		final Properties ctx = model.getCtx();
		final String IDColumnName = model.get_TableName() + "_ID";
		final String tableName = getTableNameMA(model);
		final String trxName = model.get_TrxName();

		final String whereClause = IDColumnName + "=?";
		final List<PO> list = new Query(ctx, tableName, whereClause, trxName)
				.setParameters(new Object[] { model.get_ID() })
				.setOrderBy(IDColumnName)
				.list();

		final List<IInventoryAllocation> inventoryAllocationList = InterfaceWrapperHelper.createList(list, IInventoryAllocation.class);
		IInventoryAllocation[] arr = new IInventoryAllocation[list.size()];
		return inventoryAllocationList.toArray(arr);
	}

	private static void setReferenceLine_ID(PO model, IDocumentLine ref)
	{
		String refColumnName = ref.get_TableName() + "_ID";
		if (model.get_ColumnIndex(refColumnName) < 0)
		{
			throw new LiberoException("Invalid inventory document line " + ref);
		}
		model.set_ValueOfColumn(refColumnName, ref.get_ID());

	}

	/**
	 * Set (default) Locator based on qty.
	 *
	 * @param Qty quantity Assumes Warehouse is set
	 */
	public static int getM_Locator_ID(
			Properties ctx,
			int M_Warehouse_ID,
			int M_Product_ID, int M_AttributeSetInstance_ID,
			BigDecimal Qty,
			String trxName)
	{
		// Get existing Location
		int M_Locator_ID = MStorage.getM_Locator_ID(M_Warehouse_ID,
				M_Product_ID, M_AttributeSetInstance_ID,
				Qty, trxName);
		// Get default Location
		if (M_Locator_ID == 0)
		{
			MWarehouse wh = MWarehouse.get(ctx, M_Warehouse_ID);
			M_Locator_ID = Services.get(IWarehouseBL.class).getDefaultLocator(wh).getM_Locator_ID();
		}
		return M_Locator_ID;
	}	// setM_Locator_ID

}
