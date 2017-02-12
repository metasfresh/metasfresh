package de.metas.adempiere.service.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import static org.adempiere.model.I_M_PackagingContainer.COLUMNNAME_M_PackagingContainer_ID;
import static org.adempiere.model.I_M_PackagingContainer.Table_ID;
import static org.adempiere.util.CustomColNames.M_Package_PACKAGE_WEIGHT;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.tree.DefaultMutableTreeNode;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.inout.service.IInOutPA;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.Lookup;
import org.compiere.model.MColumn;
import org.compiere.model.MInOutLine;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MPackage;
import org.compiere.model.MPackageLine;
import org.compiere.model.MTable;
import org.compiere.model.PackingTreeBL;
import org.compiere.model.X_M_PackageTree;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;

import de.metas.adempiere.form.LegacyPackingItem;
import de.metas.adempiere.form.UsedBin;
import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.service.IPackagingBL;
import de.metas.document.engine.IDocActionBL;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.IOrderPA;
import de.metas.picking.terminal.Utils;
import de.metas.product.IProductBL;
import de.metas.shipping.interfaces.I_M_Package;
import de.metas.shipping.util.Constants;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29'>(2009_0022_G61)</a>"
 */
public class PackagingBL implements IPackagingBL
{

	@Override
	public Lookup createPackgagingContainerLookup()
	{
		final MColumn c = MTable.get(Env.getCtx(), Table_ID).getColumn(COLUMNNAME_M_PackagingContainer_ID);

		return createLookup(c);
	}

	@Override
	public Lookup createShipperLookup()
	{
		final MColumn c =
				MTable.get(Env.getCtx(), I_M_Shipper.Table_ID).getColumn(I_M_Shipper.COLUMNNAME_M_Shipper_ID);

		return createLookup(c);
	}
	
	@Override
	public boolean isDisplayNonItemsEnabled(final Properties ctx)
	{
		final boolean displayNonItemsDefault = false;
		final boolean displayNonItems = Services.get(ISysConfigBL.class).getBooleanValue(
				Constants.SYSCONFIG_SHIPMENT_PACKAGE_NON_ITEMS,
				displayNonItemsDefault,
				Env.getAD_Client_ID(ctx),
				Env.getAD_Org_ID(ctx)
				);

		return displayNonItems;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<I_M_InOut, Collection<MPackage>> createInOutAndPackages(
			final Properties ctx,
			final DefaultMutableTreeNode root,
			final int shipperId,
			final Collection<I_M_ShipmentSchedule> nonItems,
			final String trxName)
	{
		final Map<I_M_InOut, Collection<MPackage>> result = new IdentityHashMap<I_M_InOut, Collection<MPackage>>();

		final boolean displayNonItems = isDisplayNonItemsEnabled(ctx);

		if (root.getChildCount() == 0)
		{
			// the packing tree has no nodes. But there might still be non-items that could still be shipped

			if (nonItems.isEmpty() || !displayNonItems)
			{
				// either there aren't any non-items or non-items are not shipped alone.
				return result;
			}
		}

		final IInOutPA inOutPA = Services.get(IInOutPA.class);
		final IOrderPA orderPA = Services.get(IOrderPA.class);

		final Enumeration<DefaultMutableTreeNode> packNodes = root.children();

		I_M_InOut inOut = null;

		final Map<Integer, I_M_InOutLine> olId2il = new HashMap<Integer, I_M_InOutLine>();

		while (packNodes.hasMoreElements())
		{
			final DefaultMutableTreeNode packNode = packNodes.nextElement();
			final UsedBin pack = (UsedBin)packNode.getUserObject();

			// one M_Package per packNode
			MPackage adPack = null;
			 // check if we have already an open package
			X_M_PackageTree virtualPackage  = PackingTreeBL.retrieveVirtualPackage(ctx, pack.getM_Package_ID(), trxName);

			final Enumeration<DefaultMutableTreeNode> packLineNodes = packNode.children();

			final Map<Integer, MPackageLine> schedId2packageLine = new HashMap<Integer, MPackageLine>();

			while (packLineNodes.hasMoreElements())
			{
				final LegacyPackingItem pi = (LegacyPackingItem)packLineNodes.nextElement().getUserObject();
				
				for (final I_M_ShipmentSchedule sched : pi.getShipmentSchedules())
				{
					final I_C_OrderLine ol = orderPA.retrieveOrderLine(sched.getC_OrderLine_ID(), trxName);
					final String sql = "SELECT fetch_Destination_Warehouse(CAST( (CASE WHEN " + de.metas.interfaces.I_C_OrderLine.COLUMNNAME_M_Warehouse_Dest_ID 
									 + " IS NULL THEN 0 ELSE " + de.metas.interfaces.I_C_OrderLine.COLUMNNAME_M_Warehouse_Dest_ID + " END) AS INTEGER)) "
									 + " FROM " +I_C_OrderLine.Table_Name 
									 + " WHERE " + I_C_OrderLine.COLUMNNAME_C_OrderLine_ID + " = ?";
					final int warehouseDest_ID = DB.getSQLValueEx(trxName, sql, ol.getC_OrderLine_ID());
					
					final I_C_Order order = InterfaceWrapperHelper.create(ol.getC_Order(), I_C_Order.class);
					final int bp_loc_id = sched.getC_BP_Location_Override_ID() > 0 ?  sched.getC_BP_Location_Override_ID() : ol.getC_BPartner_Location_ID();
					
					if (!Utils.disableSavePickingTree) // cg : task 05659 Picking Terminal: Disable Persistency
					{
						if (virtualPackage!= null)
						{
							adPack = (MPackage)virtualPackage.getM_Package();
							
						}
					}
					
					
					
					// don't pack again what has been packed and shipped already
					if (pi.isClosed())
					{
						continue;
					}
					
					if (!Utils.disableSavePickingTree) // cg : task 05659 Picking Terminal: Disable Persistency
					{
						// create the virtual package 
						if (virtualPackage == null)
						{
							virtualPackage = new X_M_PackageTree(ctx, 0, trxName);
							virtualPackage.setC_BPartner_Location_ID(bp_loc_id);
							virtualPackage.saveEx();
						}
					}
				
					if (inOut == null)
					{
						inOut = mkInOut(ctx, shipperId, sched, ol, order, trxName);
						result.put(inOut, new ArrayList<MPackage>());
					}

					// note: make sure that non-products may be on the InOut-Doc, but not
					// inside a package (even if they somehow ended up in the tree)
					final boolean isItem = Services.get(IProductBL.class).isItem(ol.getM_Product());

					if (adPack == null && isItem)
					{
						adPack = new MPackage(ctx, 0, trxName);
						adPack.setShipDate(null);
						
						// ts: we need the M_InOut reference for shipper transportation and DPD label
						adPack.setM_InOut_ID(inOut.getM_InOut_ID());  // cg:  task: 03521 : one package can have multiple inouts
						final I_M_Package pck = InterfaceWrapperHelper.create(adPack, I_M_Package.class);
						pck.setIsClosed(pack.isMarkedForPacking());
						//
						pck.setM_Shipper_ID(shipperId);
						pck.setM_PackagingContainer_ID(pack.getPackagingContainer().getM_PackagingContainer_ID());
						pck.setProcessed(pack.isMarkedForPacking());
						
						InterfaceWrapperHelper.save(pck);
						result.get(inOut).add(adPack);
					}
					
					if (warehouseDest_ID > 0)
					{
						final I_M_Package pck = InterfaceWrapperHelper.create(adPack, I_M_Package.class);
						pck.setM_Warehouse_Dest_ID(warehouseDest_ID);
						InterfaceWrapperHelper.save(pck);
					}
					
					if (!Utils.disableSavePickingTree) // cg : task 05659 Picking Terminal: Disable Persistency
					{
						// set the link btw virtual pck and real pck
						virtualPackage.setM_Package_ID(adPack.getM_Package_ID());
						final I_M_Package pck = InterfaceWrapperHelper.create(adPack, I_M_Package.class);
						virtualPackage.setIsClosed(pck.isClosed());
						virtualPackage.saveEx();
					}
					
					pack.setM_Package_ID(adPack.getM_Package_ID()); // need to make this link for be able to save the good tree

					I_M_InOutLine il = olId2il.get(sched.getC_OrderLine_ID());
					 if (il == null) 
					{
						il = InterfaceWrapperHelper.create(inOutPA.createNewLine(InterfaceWrapperHelper.create(inOut, I_M_InOut.class), trxName), I_M_InOutLine.class);
						il.setM_Product_ID(sched.getM_Product_ID());
						inOutPA.setLineOrderLine(il, ol, 0, BigDecimal.ZERO);

						olId2il.put(sched.getC_OrderLine_ID(), il);
					}

					inOutPA.setLineQty(il, il.getMovementQty().add(pi.getQtyForSched(sched)));
					InterfaceWrapperHelper.save(il);

					// note: multiple scheds might have the same package line
					MPackageLine adPackLine = schedId2packageLine.get(sched.getM_ShipmentSchedule_ID());
					if (adPackLine == null && isItem)
					{
						adPackLine = new MPackageLine(adPack);
						adPackLine.setM_InOutLine_ID(il.getM_InOutLine_ID());
						schedId2packageLine.put(sched.getM_ShipmentSchedule_ID(), adPackLine);
					}
					if (adPackLine != null)
					{
						adPackLine.setQty(adPackLine.getQty().add(pi.getQtyForSched(sched)));
						adPackLine.saveEx(trxName);
					}
				}

				if (adPack != null)
				{
					adPack.set_ValueOfColumn(M_Package_PACKAGE_WEIGHT, pi.computeWeight());
					I_M_Package pck = InterfaceWrapperHelper.create(adPack, I_M_Package.class);
					pck.setIsClosed(pack.isMarkedForPacking());
					//
					pck.setM_Shipper_ID(shipperId);
					pck.setM_PackagingContainer_ID(pack.getPackagingContainer().getM_PackagingContainer_ID());
					pck.setProcessed(pack.isMarkedForPacking());
					InterfaceWrapperHelper.save(pck);
					adPack.saveEx();
				}
			}
		}

		if (inOut != null || (displayNonItems && !nonItems.isEmpty()))
		{
			// create inOut lines for our non-items
			for (final I_M_ShipmentSchedule nonItem : nonItems)
			{
				final I_C_OrderLine ol = InterfaceWrapperHelper.create(nonItem.getC_OrderLine(), I_C_OrderLine.class);

				if (inOut == null)
				{
					final I_C_Order order = InterfaceWrapperHelper.create(ol.getC_Order(), I_C_Order.class);

					inOut = mkInOut(ctx, shipperId, nonItem, ol, order, trxName);
					result.put(inOut, new ArrayList<MPackage>());
				}

				final MInOutLine il = inOutPA.createNewLine(inOut, trxName);
				il.setM_Product_ID(nonItem.getM_Product_ID());
				il.setOrderLine(ol, 0, nonItem.getQtyToDeliver());
				il.setQty(nonItem.getQtyToDeliver());
				il.saveEx();
			}

			// metas: Neu-Nummerierung der InOut-Zeilen.
			inOutPA.renumberLinesWithoutComment(10, InterfaceWrapperHelper.create(inOut, I_M_InOut.class));

			final IDocActionBL docActionBL = Services.get(IDocActionBL.class);
			final boolean success = docActionBL.processIt(inOut, DocAction.ACTION_Complete);
			if (success)
			{
				InterfaceWrapperHelper.save(inOut);
			}
			else
			{
				throw new AdempiereException(docActionBL.getDocAction(inOut).getProcessMsg());
			}
		}
		return result;
	}

	private I_M_InOut mkInOut(
			final Properties ctx,
			final int shipperId,
			final I_M_ShipmentSchedule sched,
			final I_C_OrderLine ol,
			final I_C_Order order,
			final String trxName)
	{
		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
		
		final I_M_InOut inOut = shipmentScheduleBL.createInOut(ctx, order, Env.getContextAsDate(ctx, "#Date"), new OlAndSched(InterfaceWrapperHelper.create(ol, de.metas.interfaces.I_C_OrderLine.class), sched), trxName);
		inOut.setM_Shipper_ID(shipperId);

		// task 04949 execute the save in an unmanaged trx. This makes sure that the 'trxName' transaction is opened when the save takes place.
		// without it, we have log-messages such as
		// ===========> MInOut.save: Transaction closed or never opened (TrxRun_c2bddd0a-c85d-44e0-8e3a-8f57065f9922) => starting now --> MInOut[0-null,DocStatus=DR] [11]
		Services.get(ITrxManager.class).run(trxName, new TrxRunnable()
		{
			@Override
			public void run(final String trxName) throws Exception
			{
				InterfaceWrapperHelper.save(inOut);
			}
		}
				);

		return inOut;
	}

	private static Lookup createLookup(final I_AD_Column c)
	{
		try
		{
			final Lookup m_bundlesLookup = MLookupFactory.get(Env.getCtx(),
					-1, // WindowNo
					0, // Column_ID,
					DisplayType.Table, // AD_Reference_ID,
					c.getColumnName(), // ColumnName
					c.getAD_Reference_Value_ID(), // AD_Reference_Value_ID,
					false, // IsParent,
					IValidationRule.AD_Val_Rule_ID_Null // ValidationCode // 03271: no validation is required
					);
			return m_bundlesLookup;
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}
}
