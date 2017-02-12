/**
 * 
 */
package de.metas.picking.terminal;

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


import java.awt.Color;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.tree.DefaultMutableTreeNode;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.model.I_M_PackagingTree;
import org.compiere.model.I_M_PackagingTreeItem;
import org.compiere.model.I_M_PackagingTreeItemSched;
import org.compiere.model.PackingTreeBL;
import org.compiere.model.X_M_PackageTree;
import org.compiere.model.X_M_PackagingTree;
import org.compiere.model.X_M_PackagingTreeItem;
import org.compiere.model.X_M_PackagingTreeItemSched;
import org.compiere.util.DB;
import org.compiere.util.TrxRunnable;

import de.metas.adempiere.form.AvailableBins;
import de.metas.adempiere.form.PackingDetailsMd;
import de.metas.adempiere.form.LegacyPackingItem;
import de.metas.adempiere.form.PackingTreeModel;
import de.metas.adempiere.form.UsedBin;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.terminal.form.swing.AbstractPackageTerminalPanel;

/**
 * @author cg
 * 
 */
public class Utils
{
	
	final static public boolean disableSavePickingTree = Services.get(ISysConfigBL.class).getBooleanValue("DisableSavePickingTree", false); 
	
	public enum PackingStates
	{
		packed(Color.GREEN),
		partiallypacked(Color.YELLOW),
		unpacked(Color.RED),
		overlapped(new Color(255, 165, 79)),
		ready(new Color(34, 139, 34)),
		open(new Color(255, 102, 0)),
		closed(new Color(0, 153, 51)); // dark green

		private Color stateColor;

		PackingStates(Color color)
		{
			this.stateColor = color;
		}

		public Color getColor()
		{
			return stateColor;
		}
	};

	public static boolean savePackingTree(final AbstractPackageTerminalPanel parentPanel)
	{
		return savePackingTree(parentPanel, false, false);
	}
	
	public static boolean savePackingTree(final AbstractPackageTerminalPanel parentPanel, final boolean processed, final boolean forceSaving)
	{
		if (disableSavePickingTree && !forceSaving) // cg : task 05659 Picking Terminal: Disable Persistency
		{
			return false;
		}
		
		// save tree
		Services.get(ITrxManager.class).run(new TrxRunnable()
		{
			public void run(String trxName)
			{
				final ITerminalContext terminalCtx = parentPanel.getTerminalContext();
				
				final PackingDetailsMd model = (PackingDetailsMd)parentPanel.getModel();
				final PackingTreeModel treeModel = model.getPackingTreeModel();
				final int warehouseDestId = treeModel.getM_Warehouse_Dest_Id();
				final Properties ctx = terminalCtx.getCtx();
				final int userId = terminalCtx.getAD_User_ID();
				X_M_PackageTree packageT = null;
				
				X_M_PackagingTree tree = new X_M_PackagingTree(ctx, 0, trxName);
				tree.setC_BPartner_ID(treeModel.getBp_id());
				tree.set_ValueOfColumn(X_M_PackagingTree.COLUMNNAME_CreatedBy, userId);
				tree.setM_Warehouse_Dest_ID(warehouseDestId);
				tree.saveEx();
				tree.setProcessed(processed);
				treeModel.setSavedTree(tree);

				// save non items
				X_M_PackagingTreeItem nonItem = new X_M_PackagingTreeItem(ctx, 0, trxName);
				nonItem.setType(X_M_PackagingTreeItem.TYPE_NonItem);
				nonItem.setM_PackagingTree_ID(tree.get_ID());
				nonItem.setQty(BigDecimal.ONE);
				nonItem.saveEx();
				for (I_M_ShipmentSchedule sched : model.getNonItems())
				{
					X_M_PackagingTreeItemSched itemSched = new X_M_PackagingTreeItemSched(ctx, 0, trxName);
					itemSched.setM_ShipmentSchedule_ID(sched.getM_ShipmentSchedule_ID());
					itemSched.setQty(BigDecimal.ONE);
					itemSched.setM_PackagingTreeItem_ID(nonItem.getM_PackagingTreeItem_ID());
					itemSched.saveEx();
				}

				@SuppressWarnings("unchecked")
				Enumeration<DefaultMutableTreeNode> available = treeModel.getAvailableBins().children();
				while (available.hasMoreElements())
				{
					DefaultMutableTreeNode currentChild = available.nextElement();

					// get boxes
					Object userObj = currentChild.getUserObject();
					if (userObj instanceof AvailableBins)
					{
						// save container
						X_M_PackagingTreeItem box = new X_M_PackagingTreeItem(ctx, 0, trxName);
						AvailableBins bin = (AvailableBins)userObj;
						box.setM_PackagingTree_ID(tree.get_ID());
						box.setM_Product_ID(bin.getPc().getM_Product_ID());
						box.setQty(BigDecimal.valueOf(bin.getQtyAvail()));
						box.setType(X_M_PackagingTreeItem.TYPE_AvailableBox);
						box.setM_PackagingContainer_ID(bin.getPc().getM_PackagingContainer_ID());
						box.saveEx();
					}
				}

				@SuppressWarnings("unchecked")
				Enumeration<DefaultMutableTreeNode> enumer = treeModel.getUsedBins().children();
				while (enumer.hasMoreElements())
				{
					DefaultMutableTreeNode currentChild = enumer.nextElement();

					// get boxes
					Object userObj = currentChild.getUserObject();
					X_M_PackagingTreeItem box = new X_M_PackagingTreeItem(ctx, 0, trxName);
					if (userObj instanceof UsedBin)
					{
						// save container
						UsedBin bin = (UsedBin)userObj;
						box.setM_PackagingTree_ID(tree.get_ID());
						box.setM_Product_ID(bin.getPackagingContainer().getM_Product_ID());
						box.setQty(BigDecimal.ONE);
						box.setType(X_M_PackagingTreeItem.TYPE_Box);
						box.setM_PackagingContainer_ID(bin.getPackagingContainer().getM_PackagingContainer_ID());
						BoxKey bk = ((BoxLayout)parentPanel.getProductKeysPanel().getPickingSlotsKeyLayout()).getBoxKey(currentChild);
						if (bk != null)
						{
							String status = null;
							if (PackingStates.partiallypacked.name().equals(bk.getStatus().getName()))
							{							
								status = X_M_PackagingTreeItem.STATUS_PartiallyPacked;
							}
							else if (PackingStates.packed.name().equals(bk.getStatus().getName()))
							{
								status = X_M_PackagingTreeItem.STATUS_Packed;
							}
							else if (PackingStates.unpacked.name().equals(bk.getStatus().getName()))
							{
								status = X_M_PackagingTreeItem.STATUS_UnPacked;
							}
							else if (PackingStates.ready.name().equals(bk.getStatus().getName()))
							{
								status = X_M_PackagingTreeItem.STATUS_Ready;
							}
							else if (PackingStates.open.name().equals(bk.getStatus().getName()))
							{
								status = X_M_PackagingTreeItem.STATUS_Open;
							}

							box.setStatus(status);
							
							//
							// try to find an existent package
							packageT =  PackingTreeBL.retrieveVirtualPackage(ctx, bin.getM_Package_ID(), trxName);
							if (packageT != null)
							{
								box.setM_PackageTree_ID(packageT.getM_PackageTree_ID());							
							}
							
						}
						box.saveEx();
						
						// for eac 
					}

					// get packing items
					@SuppressWarnings("unchecked")
					Enumeration<DefaultMutableTreeNode> enumProd = currentChild.children();
					while (enumProd.hasMoreElements())
					{
						DefaultMutableTreeNode child = enumProd.nextElement();

						// create products per box
						Object obj = child.getUserObject();
						if (obj instanceof LegacyPackingItem)
						{
							final LegacyPackingItem item = (LegacyPackingItem)obj;
							// save items
							X_M_PackagingTreeItem itemTree = new X_M_PackagingTreeItem(ctx, 0, trxName);
							itemTree.setM_PackagingTree_ID(tree.get_ID());
							itemTree.setGroupID(item.getGroupingKey());
							itemTree.setRef_M_PackagingTreeItem_ID(box.get_ID());
							itemTree.setM_Product_ID(item.getProductId());
							itemTree.setQty(item.getQtySum());
							itemTree.setWeight(item.computeWeight());
							itemTree.setType(X_M_PackagingTreeItem.TYPE_PackedItem);
							if (packageT != null && PackingTreeBL.isPacked(itemTree.getM_Product_ID(), packageT.getM_Package_ID(), item.getQtySum())  )		
							{
								itemTree.setM_PackageTree_ID(packageT.getM_PackageTree_ID());
							}
							itemTree.saveEx();
							//
							for (I_M_ShipmentSchedule sched : item.getShipmentSchedules())
							{
								X_M_PackagingTreeItemSched itemSched = new X_M_PackagingTreeItemSched(ctx, 0, trxName);
								itemSched.setM_ShipmentSchedule_ID(sched.getM_ShipmentSchedule_ID());
								itemSched.setQty(item.getQtyForSched(sched));
								itemSched.setM_PackagingTreeItem_ID(itemTree.getM_PackagingTreeItem_ID());
								itemSched.saveEx();
							}
						}
					}
				}

				// save unpacked items
				@SuppressWarnings("unchecked")
				Enumeration<DefaultMutableTreeNode> avail = treeModel.getUnPackedItems().children();
				while (avail.hasMoreElements())
				{
					DefaultMutableTreeNode currentChild = avail.nextElement();

					// get boxes
					Object userObj = currentChild.getUserObject();
					if (userObj instanceof LegacyPackingItem)
					{
						final LegacyPackingItem item = (LegacyPackingItem)userObj;
						X_M_PackagingTreeItem unpacked = new X_M_PackagingTreeItem(ctx, 0, trxName);
						unpacked.setM_PackagingTree_ID(tree.get_ID());
						unpacked.setM_Product_ID(item.getProductId());
						unpacked.setQty(item.getQtySum());
						unpacked.setWeight(item.computeWeight());
						unpacked.setType(X_M_PackagingTreeItem.TYPE_UnPackedItem);
						unpacked.setStatus("UP");
						unpacked.setGroupID(item.getGroupingKey());
						unpacked.saveEx();
						//
						for (I_M_ShipmentSchedule sched : item.getShipmentSchedules())
						{
							X_M_PackagingTreeItemSched itemSched = new X_M_PackagingTreeItemSched(ctx, 0, trxName);
							itemSched.setM_ShipmentSchedule_ID(sched.getM_ShipmentSchedule_ID());
							itemSched.setQty(item.getQtyForSched(sched));
							itemSched.setM_PackagingTreeItem_ID(unpacked.getM_PackagingTreeItem_ID());
							itemSched.saveEx();
						}
					}
				}

			}
		});

		return true;
	}
	
	public static String mkTruncatedstring(final String text, final int length)
	{
		final String[] bits = text.split("\\\\n");
		final StringBuilder newtxt =  new StringBuilder();
		for (final String s : bits)
		{
			if (newtxt.length() > 0)
			{
				newtxt.append("<br>");
			}
			
			if (s.length() > length)
			{
				newtxt.append(s.substring(0, length-3).concat("..."));
			}
			else
			{
				newtxt.append(s);
			}
		}
		
		if (newtxt.length() == 0)
		{
			return text;
		}
		return newtxt.toString();
	}
	
	private static final String buttonSize = "h 80:80:80, w 80:80:80,";

	public static String getButtonSize()
	{
		return buttonSize;
	}
	
	public static void deletePackingTree(final AbstractPackageTerminalPanel packageTerminalPanel)
	{
		// delete tree
		Services.get(ITrxManager.class).run(new TrxRunnable()
		{
			public void run(String trxName)
			{
				final PackingDetailsMd model = (PackingDetailsMd)packageTerminalPanel.getModel();
				final PackingTreeModel treeModel = model.getPackingTreeModel();

				final I_M_PackagingTree packagingTree = treeModel.getSavedTree();
				if (packagingTree == null)
				{
					throw new AdempiereException("We don't have a tree to delete!");
				}

				// delete schedule items
				final String deleteItemScheds = " DELETE FROM " + I_M_PackagingTreeItemSched.Table_Name
						+ " WHERE EXISTS (SELECT 1 FROM " + I_M_PackagingTreeItem.Table_Name + " WHERE " + I_M_PackagingTreeItem.COLUMNNAME_M_PackagingTree_ID + " = ?)";
				DB.executeUpdateEx(deleteItemScheds, new Object[] { packagingTree.getM_PackagingTree_ID() }, trxName);

				// delete packaging tree items
				final String deleteItems = " DELETE FROM " + I_M_PackagingTreeItem.Table_Name + " WHERE " + I_M_PackagingTreeItem.COLUMNNAME_M_PackagingTree_ID + " = ? ";
				DB.executeUpdateEx(deleteItems, new Object[] { packagingTree.getM_PackagingTree_ID() }, trxName);

				// delete packaging tree
				final String deletePackagingTree = " DELETE FROM " + I_M_PackagingTree.Table_Name + " WHERE " + I_M_PackagingTree.COLUMNNAME_M_PackagingTree_ID + " = ? ";
				DB.executeUpdateEx(deletePackagingTree, new Object[] { packagingTree.getM_PackagingTree_ID() }, trxName);

			}
		});
	}
}
