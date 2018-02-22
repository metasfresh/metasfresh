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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
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
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.IUOMConversionContext;
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

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.legacy.form.AvailableBins;
import de.metas.picking.legacy.form.IPackingItem;
import de.metas.picking.legacy.form.LegacyPackingItem;
import de.metas.picking.legacy.form.PackingDetailsMd;
import de.metas.picking.legacy.form.PackingTreeModel;
import de.metas.picking.legacy.form.UsedBin;
import de.metas.picking.terminal.form.swing.AbstractPackageTerminalPanel;
import de.metas.quantity.Quantity;
import lombok.NonNull;

/**
 * @author cg
 *
 */
public class Utils
{

	final static public boolean disableSavePickingTree = Services.get(ISysConfigBL.class).getBooleanValue("DisableSavePickingTree", false);

	public enum PackingStates
	{
		packed(Color.GREEN), partiallypacked(Color.YELLOW), unpacked(Color.RED), overlapped(new Color(255, 165, 79)), ready(new Color(34, 139, 34)), open(new Color(255, 102, 0)), closed(new Color(0, 153, 51)); // dark green

		private final Color stateColor;

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
		Services.get(ITrxManager.class).run((TrxRunnable)trxName -> {
			final ITerminalContext terminalCtx = parentPanel.getTerminalContext();

			final PackingDetailsMd model = (PackingDetailsMd)parentPanel.getModel();
			final PackingTreeModel treeModel = model.getPackingTreeModel();
			final int warehouseDestId = treeModel.getM_Warehouse_Dest_Id();
			final Properties ctx = terminalCtx.getCtx();
			final int userId = terminalCtx.getAD_User_ID();
			X_M_PackageTree packageT = null;

			final X_M_PackagingTree tree = new X_M_PackagingTree(ctx, 0, trxName);
			tree.setC_BPartner_ID(treeModel.getBp_id());
			tree.set_ValueOfColumn(X_M_PackagingTree.COLUMNNAME_CreatedBy, userId);
			tree.setM_Warehouse_Dest_ID(warehouseDestId);
			tree.saveEx();
			tree.setProcessed(processed);
			treeModel.setSavedTree(tree);

			// save non items
			final X_M_PackagingTreeItem nonItem = new X_M_PackagingTreeItem(ctx, 0, trxName);
			nonItem.setType(X_M_PackagingTreeItem.TYPE_NonItem);
			nonItem.setM_PackagingTree_ID(tree.get_ID());
			nonItem.setQty(BigDecimal.ONE);
			nonItem.saveEx();
			for (final I_M_ShipmentSchedule sched1 : model.getNonItems())
			{
				final X_M_PackagingTreeItemSched itemSched1 = new X_M_PackagingTreeItemSched(ctx, 0, trxName);
				itemSched1.setM_ShipmentSchedule_ID(sched1.getM_ShipmentSchedule_ID());
				itemSched1.setQty(BigDecimal.ONE);
				itemSched1.setM_PackagingTreeItem_ID(nonItem.getM_PackagingTreeItem_ID());
				itemSched1.saveEx();
			}

			@SuppressWarnings("unchecked")
			final Enumeration<DefaultMutableTreeNode> available = treeModel.getAvailableBins().children();
			while (available.hasMoreElements())
			{
				final DefaultMutableTreeNode currentChild1 = available.nextElement();

				// get boxes
				final Object userObj1 = currentChild1.getUserObject();
				if (userObj1 instanceof AvailableBins)
				{
					// save container
					final X_M_PackagingTreeItem box1 = new X_M_PackagingTreeItem(ctx, 0, trxName);
					final AvailableBins bin1 = (AvailableBins)userObj1;
					box1.setM_PackagingTree_ID(tree.get_ID());
					box1.setM_Product_ID(bin1.getPc().getM_Product_ID());
					box1.setQty(BigDecimal.valueOf(bin1.getQtyAvail()));
					box1.setType(X_M_PackagingTreeItem.TYPE_AvailableBox);
					box1.setM_PackagingContainer_ID(bin1.getPc().getM_PackagingContainer_ID());
					box1.saveEx();
				}
			}

			@SuppressWarnings("unchecked")
			final Enumeration<DefaultMutableTreeNode> enumer = treeModel.getUsedBins().children();
			while (enumer.hasMoreElements())
			{
				final DefaultMutableTreeNode currentChild2 = enumer.nextElement();

				// get boxes
				final Object userObj2 = currentChild2.getUserObject();
				final X_M_PackagingTreeItem box2 = new X_M_PackagingTreeItem(ctx, 0, trxName);
				if (userObj2 instanceof UsedBin)
				{
					// save container
					final UsedBin bin2 = (UsedBin)userObj2;
					box2.setM_PackagingTree_ID(tree.get_ID());
					box2.setM_Product_ID(bin2.getPackagingContainer().getM_Product_ID());
					box2.setQty(BigDecimal.ONE);
					box2.setType(X_M_PackagingTreeItem.TYPE_Box);
					box2.setM_PackagingContainer_ID(bin2.getPackagingContainer().getM_PackagingContainer_ID());
					final BoxKey bk = ((BoxLayout)parentPanel.getProductKeysPanel().getPickingSlotsKeyLayout()).getBoxKey(currentChild2);
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

						box2.setStatus(status);

						//
						// try to find an existent package
						packageT = PackingTreeBL.retrieveVirtualPackage(ctx, bin2.getM_Package_ID(), trxName);
						if (packageT != null)
						{
							box2.setM_PackageTree_ID(packageT.getM_PackageTree_ID());
						}

					}
					box2.saveEx();

					// for eac
				}

				// get packing items
				@SuppressWarnings("unchecked")
				final Enumeration<DefaultMutableTreeNode> enumProd = currentChild2.children();
				while (enumProd.hasMoreElements())
				{
					final DefaultMutableTreeNode child = enumProd.nextElement();

					// create products per box
					final Object obj = child.getUserObject();
					if (obj instanceof LegacyPackingItem)
					{
						final LegacyPackingItem item1 = (LegacyPackingItem)obj;
						// save items
						final X_M_PackagingTreeItem itemTree = new X_M_PackagingTreeItem(ctx, 0, trxName);
						itemTree.setM_PackagingTree_ID(tree.get_ID());
						itemTree.setGroupID(item1.getGroupingKey());
						itemTree.setRef_M_PackagingTreeItem_ID(box2.get_ID());
						itemTree.setM_Product_ID(item1.getProductId());
						itemTree.setQty(convertToItemUOM(item1, item1.getQtySum()));
						itemTree.setWeight(item1.computeWeightInProductUOM());
						itemTree.setType(X_M_PackagingTreeItem.TYPE_PackedItem);

						final boolean packed = PackingTreeBL.isPacked(
								itemTree.getM_Product_ID(),
								packageT.getM_Package_ID(),
								convertToItemUOM(item1, item1.getQtySum()));

						if (packageT != null && packed)
						{
							itemTree.setM_PackageTree_ID(packageT.getM_PackageTree_ID());
						}
						itemTree.saveEx();
						//
						for (final I_M_ShipmentSchedule sched2 : item1.getShipmentSchedules())
						{
							final X_M_PackagingTreeItemSched itemSched2 = new X_M_PackagingTreeItemSched(ctx, 0, trxName);
							itemSched2.setM_ShipmentSchedule_ID(sched2.getM_ShipmentSchedule_ID());
							itemSched2.setQty(convertToItemUOM(item1, item1.getQtyForSched(sched2)));
							itemSched2.setM_PackagingTreeItem_ID(itemTree.getM_PackagingTreeItem_ID());
							itemSched2.saveEx();
						}
					}
				}
			}

			// save unpacked items
			@SuppressWarnings("unchecked")
			final Enumeration<DefaultMutableTreeNode> avail = treeModel.getUnPackedItems().children();
			while (avail.hasMoreElements())
			{
				final DefaultMutableTreeNode currentChild3 = avail.nextElement();

				// get boxes
				final Object userObj3 = currentChild3.getUserObject();
				if (userObj3 instanceof LegacyPackingItem)
				{
					final LegacyPackingItem item2 = (LegacyPackingItem)userObj3;
					final X_M_PackagingTreeItem unpacked = new X_M_PackagingTreeItem(ctx, 0, trxName);
					unpacked.setM_PackagingTree_ID(tree.get_ID());
					unpacked.setM_Product_ID(item2.getProductId());
					unpacked.setQty(convertToItemUOM(item2, item2.getQtySum()));
					unpacked.setWeight(item2.computeWeightInProductUOM());
					unpacked.setType(X_M_PackagingTreeItem.TYPE_UnPackedItem);
					unpacked.setStatus("UP");
					unpacked.setGroupID(item2.getGroupingKey());
					unpacked.saveEx();
					//
					for (final I_M_ShipmentSchedule sched3 : item2.getShipmentSchedules())
					{
						final X_M_PackagingTreeItemSched itemSched3 = new X_M_PackagingTreeItemSched(ctx, 0, trxName);
						itemSched3.setM_ShipmentSchedule_ID(sched3.getM_ShipmentSchedule_ID());
						itemSched3.setQty(convertToItemUOM(item2, item2.getQtyForSched(sched3)));
						itemSched3.setM_PackagingTreeItem_ID(unpacked.getM_PackagingTreeItem_ID());
						itemSched3.saveEx();
					}
				}
			}

		});

		return true;
	}

	public static String mkTruncatedstring(final String text, final int length)
	{
		final String[] bits = text.split("\\\\n");
		final StringBuilder newtxt = new StringBuilder();
		for (final String s : bits)
		{
			if (newtxt.length() > 0)
			{
				newtxt.append("<br>");
			}

			if (s.length() > length)
			{
				newtxt.append(s.substring(0, length - 3).concat("..."));
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
		Services.get(ITrxManager.class).run((TrxRunnable)trxName -> {
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

		});
	}

	public static final BigDecimal convertToItemUOM(
			@NonNull final IPackingItem packingItem,
			@NonNull final Quantity quantity)
	{
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

		return uomConversionBL.convertQty(
				IUOMConversionContext.of(packingItem.getM_Product()),
				quantity.getQty(),
				quantity.getUOM(),
				packingItem.getC_UOM());
	}
}
