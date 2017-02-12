package de.metas.adempiere.form;

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


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_PackagingTree;
import org.compiere.model.MPackageLine;
import org.compiere.model.PackagingTreeItemComparable;
import org.compiere.model.PackingTreeBL;
import org.compiere.model.X_M_PackagingTreeItem;
import org.compiere.model.X_M_PackagingTreeItemSched;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_M_Product;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.shipping.interfaces.I_M_Package;


/**
 *
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29'>(2009_0022_G61)</a>"
 */
public class PackingTreeModel extends DefaultTreeModel
{
	/**
	 *
	 */
	private static final long serialVersionUID = 447858978512397688L;

	private final Logger logger = LogManager.getLogger(getClass());

	private final DefaultMutableTreeNode nodeUnpackedItemsParent;

	private final DefaultMutableTreeNode nodeUsedBinsParent;

	private final DefaultMutableTreeNode nodeAvaiableBinsParent;

	private Collection<LegacyPackingItem> unpackedItems;

	private Collection<AvailableBins> availableBins;

	private DefaultMutableTreeNode selectedNode;

	private ArrayList<DefaultMutableTreeNode> lockedNodes = new ArrayList<DefaultMutableTreeNode>();

	private I_M_PackagingTree savedTree = null;

	public I_M_PackagingTree getSavedTree()
	{
		return savedTree;
	}

	public void setSavedTree(I_M_PackagingTree savedTree)
	{
		this.savedTree = savedTree;
	}

	private boolean isGroupingByWarehouseDest;

	private int C_BPartner_Location_ID;

	public int getC_BPartner_Location_ID()
	{
		return C_BPartner_Location_ID;
	}

	public void setC_BPartner_Location_ID(int c_BPartner_Location_ID)
	{
		C_BPartner_Location_ID = c_BPartner_Location_ID;
	}

	private int M_Warehouse_Dest_Id;

	public int getM_Warehouse_Dest_Id()
	{
		return M_Warehouse_Dest_Id;
	}

	public void setM_Warehouse_Dest_Id(int m_Warehouse_Dest_Id)
	{
		M_Warehouse_Dest_Id = m_Warehouse_Dest_Id;
	}

	private int bp_id;

	public int getBp_id()
	{
		return bp_id;
	}

	public void setBp_id(int bpId)
	{
		bp_id = bpId;
	}




	public PackingTreeModel(final Collection<IPackingItem> unallocatedItems, final Collection<AvailableBins> availableBins, final int C_BPartner_ID, final int C_BPartner_Location_ID, final int M_Warehouse_Dest_ID, final boolean isGroupingByWarehouseDest)
	{
		super(new DefaultMutableTreeNode());

		this.isGroupingByWarehouseDest = isGroupingByWarehouseDest;

		// create deep copies of the input data to allow a reset
		final ArrayList<LegacyPackingItem> itemCopies = new ArrayList<LegacyPackingItem>(unallocatedItems.size());
		for (final IPackingItem item : unallocatedItems)
		{
			itemCopies.add(new LegacyPackingItem(item));
		}

		final ArrayList<AvailableBins> binsCopies = new ArrayList<AvailableBins>(availableBins.size());
		for (final AvailableBins bins : availableBins)
		{
			binsCopies.add(new AvailableBins(bins));
		}

		this.unpackedItems = Collections.unmodifiableList(itemCopies);
		this.availableBins = Collections.unmodifiableList(binsCopies);

		final DefaultMutableTreeNode root = (DefaultMutableTreeNode)getRoot();

		nodeUsedBinsParent = new DefaultMutableTreeNode("Kommisioniert");
		root.add(nodeUsedBinsParent);

		nodeUnpackedItemsParent = new DefaultMutableTreeNode("Offen");
		root.add(nodeUnpackedItemsParent);

		nodeAvaiableBinsParent = new DefaultMutableTreeNode("Verpackungen");
		root.add(nodeAvaiableBinsParent);

		// set partner and order id
		setBp_id(C_BPartner_ID);
		setC_BPartner_Location_ID(C_BPartner_Location_ID);
		setM_Warehouse_Dest_Id(M_Warehouse_Dest_ID);

		initTree(isGroupingByWarehouseDest);
	}


	public PackingTreeModel(final Properties ctx, final I_M_PackagingTree savedTree, final Collection<AvailableBins> availableBins, final int C_BPartnerLocation_ID, final int M_Warehouse_Dest_ID, final boolean isGroupByWarehouseDest)
	{
		super(new DefaultMutableTreeNode());
		this.savedTree =  savedTree;

		this.isGroupingByWarehouseDest = isGroupByWarehouseDest;

		this.unpackedItems = getSavedUnpackedItems(savedTree);

		final ArrayList<AvailableBins> binsCopies = new ArrayList<AvailableBins>(availableBins.size());
		for (final AvailableBins bins : availableBins)
		{
			binsCopies.add(new AvailableBins(bins));
		}

		this.availableBins = Collections.unmodifiableList(binsCopies);

		final DefaultMutableTreeNode root = (DefaultMutableTreeNode)getRoot();

		nodeUsedBinsParent = new DefaultMutableTreeNode("Kommisioniert");
		root.add(nodeUsedBinsParent);

		nodeUnpackedItemsParent = new DefaultMutableTreeNode("Offen");
		root.add(nodeUnpackedItemsParent);

		nodeAvaiableBinsParent = new DefaultMutableTreeNode("Verpackungen");
		root.add(nodeAvaiableBinsParent);

		setBp_id(savedTree.getC_BPartner_ID());
		setC_BPartner_Location_ID(C_BPartnerLocation_ID);
		setM_Warehouse_Dest_Id(M_Warehouse_Dest_ID);

		initSavedTree(ctx, isGroupByWarehouseDest);
	}

	public Collection<AvailableBins> getSavedAvailableBins(final Properties ctx, I_M_PackagingTree  savedTree)
	{
		List<PackagingTreeItemComparable> avail = PackingTreeBL.getItems(savedTree.getM_PackagingTree_ID(), X_M_PackagingTreeItem.TYPE_AvailableBox);
		Collection<AvailableBins> avalaiableContainers =  new ArrayList<AvailableBins>();
		for (X_M_PackagingTreeItem item : avail)
		{
			avalaiableContainers.add(new AvailableBins(ctx, item.getM_PackagingContainer(), item.getQty().intValue(), null));
		}
		return avalaiableContainers;
	}

	public Collection<LegacyPackingItem> getSavedUnpackedItems(I_M_PackagingTree  savedTree)
	{
		List<PackagingTreeItemComparable> unPackedItems =  PackingTreeBL.getItems(savedTree.getM_PackagingTree_ID(), X_M_PackagingTreeItem.TYPE_UnPackedItem);

		final ArrayList<LegacyPackingItem> itemCopies = new ArrayList<LegacyPackingItem>(unPackedItems.size());
		for (X_M_PackagingTreeItem upck : unPackedItems)
		{
			List<X_M_PackagingTreeItemSched> schedItems = PackingTreeBL.getSchedforItem(upck.getM_PackagingTreeItem_ID());
			Map<I_M_ShipmentSchedule, BigDecimal> schedWithQty = new HashMap<I_M_ShipmentSchedule, BigDecimal>();
			for (X_M_PackagingTreeItemSched schedItem : schedItems)
			{
				final I_M_ShipmentSchedule sched = schedItem.getM_ShipmentSchedule();
				if (sched == null)
				{
					logger.warn("No schedule found found "+schedItem+" [SKIP]");
					continue;
				}
				schedWithQty = Collections.singletonMap(sched, schedItem.getQty());
			}
			if (schedWithQty.isEmpty())
			{
				return new ArrayList<LegacyPackingItem>();
			}
			LegacyPackingItem item = new LegacyPackingItem(schedWithQty, upck.getGroupID(), ITrx.TRXNAME_None);
			itemCopies.add(item);
		}

		return Collections.unmodifiableList(itemCopies);
	}

	private void initSavedTree(final Properties ctx, final boolean isGroupByWarehouseDest)
	{
		for (final LegacyPackingItem currentItem : this.unpackedItems)
		{
			final DefaultMutableTreeNode itemNode = new DefaultMutableTreeNode(new LegacyPackingItem(currentItem));

			insertNodeInto(itemNode, nodeUnpackedItemsParent, nodeUnpackedItemsParent.getChildCount());
		}

		final List<PackagingTreeItemComparable> boxes = PackingTreeBL.getItems(savedTree.getM_PackagingTree_ID(), X_M_PackagingTreeItem.TYPE_Box);
		// get also open boxes from other trees
		final List<PackagingTreeItemComparable> openBoxes =  PackingTreeBL.getOpenBoxes(getC_BPartner_Location_ID(),getM_Warehouse_Dest_Id(), isGroupByWarehouseDest);
		// add the open boxes to all boxes
		final List<PackagingTreeItemComparable> allBoxes  = new ArrayList<PackagingTreeItemComparable>();
		allBoxes.addAll(boxes);
		allBoxes.addAll(openBoxes);

		List<PackagingTreeItemComparable> finalList = new ArrayList<PackagingTreeItemComparable>();
		for (X_M_PackagingTreeItem pi : allBoxes)
		{
			PackagingTreeItemComparable pic = new PackagingTreeItemComparable(pi.getCtx(), pi.getM_PackagingTreeItem_ID(), pi.get_TrxName());

			// don't let saved config to overwrite the real packages
			if (pic.getM_PackageTree_ID() > 0)
			{
				I_M_Package openPackage = InterfaceWrapperHelper.create(pic.getM_PackageTree().getM_Package(), I_M_Package.class);

				List<MPackageLine> plines = MPackageLine.get(pi.getCtx(), openPackage.getM_Package_ID(), pi.get_TrxName());

				if (!plines.isEmpty() && plines.get(0).getM_InOutLine().getM_InOut().getC_BPartner_ID() != getBp_id() )
				{
					continue;
				}

				if (isGroupByWarehouseDest && openPackage.getM_Warehouse_Dest_ID() != getM_Warehouse_Dest_Id())
				{
					continue;
				}
			}
			if (!finalList.contains(pic))
			{
				finalList.add(pic);
			}
		}

		// products form containers
		List<PackagingTreeItemComparable> packedItems =  PackingTreeBL.getItems(savedTree.getM_PackagingTree_ID(), X_M_PackagingTreeItem.TYPE_PackedItem);
		List<X_M_PackagingTreeItem> notShippedItems = new ArrayList<X_M_PackagingTreeItem>();
		for (X_M_PackagingTreeItem pack : packedItems)
		{
			if (pack.getM_PackageTree_ID() > 0)
			{
				continue;

			}
			notShippedItems.add(pack);
		}

		int i = 0;
		for (X_M_PackagingTreeItem box : finalList)
		{
			UsedBin usedBin = new UsedBin(ctx, box.getM_PackagingContainer(), null);
			if (box.getStatus().equals(X_M_PackagingTreeItem.STATUS_Ready))
			{
				usedBin.setReady(true);
			}

			if (box.getM_PackageTree_ID() > 0)
			{
				final int M_Package_ID = box.getM_PackageTree().getM_Package_ID();
				if (M_Package_ID > 0)
				{
					usedBin.setM_Package_ID(box.getM_PackageTree().getM_Package_ID());
				}
			}


			final DefaultMutableTreeNode binNode = new DefaultMutableTreeNode(usedBin);
			insertNodeInto(binNode, nodeUsedBinsParent, i);
			i++;


			int j = 0;
			for (X_M_PackagingTreeItem pack : notShippedItems)
			{
				if (pack.getRef_M_PackagingTreeItem_ID() == box.getM_PackagingTreeItem_ID())
				{
					List<X_M_PackagingTreeItemSched> schedItems = PackingTreeBL.getSchedforItem(pack.getM_PackagingTreeItem_ID());
					Map<I_M_ShipmentSchedule, BigDecimal> schedWithQty = new HashMap<I_M_ShipmentSchedule, BigDecimal>();
					for (X_M_PackagingTreeItemSched schedItem : schedItems)
					{
						final I_M_ShipmentSchedule sched = schedItem.getM_ShipmentSchedule();
						if (sched == null)
							throw new AdempiereException();
						schedWithQty.put(sched, schedItem.getQty());
					}

					if (schedWithQty.isEmpty())
					{
						continue;
					}

					LegacyPackingItem item = new LegacyPackingItem(schedWithQty, null);
					item.setWeightSingle(pack.getWeight().divide(pack.getQty(), BigDecimal.ROUND_HALF_UP));
					final DefaultMutableTreeNode itemNode = new DefaultMutableTreeNode(item);
					insertNodeInto(itemNode, binNode, j);
					j++;
				}
			}
		}

		for (final AvailableBins currentBin : this.availableBins)
		{
			final DefaultMutableTreeNode binNode = new DefaultMutableTreeNode(new AvailableBins(currentBin));

			insertNodeInto(binNode, nodeAvaiableBinsParent, nodeAvaiableBinsParent.getChildCount());
		}
	}


	private void initTree( final boolean isGroupByWarehouseDest)
	{
		for (final LegacyPackingItem currentItem : this.unpackedItems)
		{
			final DefaultMutableTreeNode itemNode = new DefaultMutableTreeNode(new LegacyPackingItem(currentItem));

			insertNodeInto(itemNode, nodeUnpackedItemsParent, nodeUnpackedItemsParent.getChildCount());
		}

		for (final AvailableBins currentBin : this.availableBins)
		{
			final DefaultMutableTreeNode binNode = new DefaultMutableTreeNode(new AvailableBins(currentBin));

			insertNodeInto(binNode, nodeAvaiableBinsParent, nodeAvaiableBinsParent.getChildCount());
		}

		// add open bins
		// get also open boxes from other trees
		final List<PackagingTreeItemComparable> openBoxes =  PackingTreeBL.getOpenBoxes(getC_BPartner_Location_ID(), getM_Warehouse_Dest_Id(), isGroupByWarehouseDest);


		List<PackagingTreeItemComparable> finalList = new ArrayList<PackagingTreeItemComparable>();
		for (X_M_PackagingTreeItem pi : openBoxes)
		{

			PackagingTreeItemComparable pic = new PackagingTreeItemComparable(pi.getCtx(), pi.getM_PackagingTreeItem_ID(), pi.get_TrxName());
			// don't let saved config to overwrite the real packages
			if (pic.getM_PackageTree_ID() > 0)
			{
				I_M_Package openPackage = InterfaceWrapperHelper.create(pic.getM_PackageTree().getM_Package(), I_M_Package.class);

				List<MPackageLine> plines = MPackageLine.get(pi.getCtx(), openPackage.getM_Package_ID(), pi.get_TrxName());

				if (!plines.isEmpty() && plines.get(0).getM_InOutLine().getM_InOut().getC_BPartner_ID() != getBp_id() )
				{
					continue;
				}

				if (isGroupByWarehouseDest && openPackage.getM_Warehouse_Dest_ID() != getM_Warehouse_Dest_Id())
				{
					continue;
				}
			}

			if (!finalList.contains(pic))
			{
				finalList.add(pic);
			}
		}

		// first, add the used bin
		int i = 0;
		for (X_M_PackagingTreeItem box : finalList)
		{
			UsedBin usedBin = new UsedBin(Env.getCtx(), box.getM_PackagingContainer(), null);
			if (box.getStatus() != null && box.getStatus().equals(X_M_PackagingTreeItem.STATUS_Ready))
			{
				usedBin.setReady(true);
			}

			if (box.getM_PackageTree_ID() > 0)
			{
				final int M_Package_ID = box.getM_PackageTree().getM_Package_ID();
				if (M_Package_ID > 0)
				{
					usedBin.setM_Package_ID(box.getM_PackageTree().getM_Package_ID());
				}
			}

			final DefaultMutableTreeNode binNode = new DefaultMutableTreeNode(usedBin);
			insertNodeInto(binNode, nodeUsedBinsParent, i);
			i++;
		}


	}

	public DefaultMutableTreeNode getUsedBins()
	{
		return nodeUsedBinsParent;
	}

	public DefaultMutableTreeNode getUnPackedItems()
	{
		return nodeUnpackedItemsParent;
	}

	@SuppressWarnings("unchecked")
	public static BigDecimal getUsedVolume(final DefaultMutableTreeNode packNode, final String trxName)
	{

		BigDecimal result = BigDecimal.ZERO;

		final Enumeration<DefaultMutableTreeNode> enu = packNode.children();
		while (enu.hasMoreElements())
		{

			final DefaultMutableTreeNode node = enu.nextElement();
			final LegacyPackingItem pl = (LegacyPackingItem)node.getUserObject();

			result = result.add(pl.retrieveVolumeSingle(trxName).multiply(pl.getQtySum()));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static BigDecimal getUsedWeight(final DefaultMutableTreeNode packNode, String trxName)
	{
		BigDecimal result = BigDecimal.ZERO;

		final Enumeration<DefaultMutableTreeNode> enu = packNode.children();
		while (enu.hasMoreElements())
		{
			final DefaultMutableTreeNode node = enu.nextElement();
			final LegacyPackingItem pl = (LegacyPackingItem)node.getUserObject();

			result = result.add(pl.retrieveWeightSingle(trxName).multiply(pl.getQtySum()));
		}
		return result;
	}

	public void addUnallocatedLine(final LegacyPackingItem pl)
	{
		final DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(pl);
		insertNodeInto(newNode, getUnPackedItems(), getUnPackedItems().getChildCount());
	}

	public DefaultMutableTreeNode getAvailableBins()
	{
		return nodeAvaiableBinsParent;
	}

	public DefaultMutableTreeNode getSelectedNode()
	{
		return selectedNode;
	}

	public void addLockedNode(DefaultMutableTreeNode lockedNode)
	{
		lockedNodes.add(lockedNode);
	}

	public void removeLockedNode(DefaultMutableTreeNode lockedNode)
	{
		lockedNodes.remove(lockedNode);
	}

	public void removeAllLockedNode()
	{
		lockedNodes =  new ArrayList<DefaultMutableTreeNode>();
	}

	public boolean isLockedNode(DefaultMutableTreeNode lockedNode)
	{
		return lockedNodes.contains(lockedNode);
	}

	public ArrayList<DefaultMutableTreeNode> fetchLockedNodes()
	{
		return lockedNodes;
	}

	public void setSelectedNode(DefaultMutableTreeNode selectedNode)
	{
		this.selectedNode = selectedNode;
	}

	public void reset(final Properties ctx)
	{
		nodeUsedBinsParent.removeAllChildren();
		this.nodeStructureChanged(nodeUsedBinsParent);

		nodeAvaiableBinsParent.removeAllChildren();
		this.nodeStructureChanged(nodeAvaiableBinsParent);

		nodeUnpackedItemsParent.removeAllChildren();
		this.nodeStructureChanged(nodeUnpackedItemsParent);

		if (savedTree != null)
		{
			this.unpackedItems = getSavedUnpackedItems(savedTree);
			this.availableBins = getSavedAvailableBins(ctx, savedTree);
			initSavedTree(ctx, isGroupingByWarehouseDest);
		}
		else
			initTree(isGroupingByWarehouseDest);
		reload();
	}

	@SuppressWarnings("unchecked")
	public String mkUsedBinsSum(final Properties ctx, final String trxName)
	{

		final Map<Integer, BigDecimal> id2ItemQty = new HashMap<Integer, BigDecimal>();
		final Map<Integer, I_M_Product> id2Item = new HashMap<Integer, I_M_Product>();

		final Map<Integer, BigDecimal> id2BinQty = new HashMap<Integer, BigDecimal>();
		final Map<Integer, I_M_Product> id2Bin = new HashMap<Integer, I_M_Product>();

		final Enumeration<DefaultMutableTreeNode> allChildren = getUsedBins().depthFirstEnumeration();

		while (allChildren.hasMoreElements())
		{
			final DefaultMutableTreeNode currentChild = allChildren.nextElement();
			final Object userObj = currentChild.getUserObject();

			if (userObj instanceof UsedBin)
			{

				final UsedBin usedBin = (UsedBin)userObj;
				final I_M_Product prod = usedBin.retrieveProduct(ctx, trxName);

				BigDecimal qty = id2BinQty.get(prod.getM_Product_ID());
				if (qty == null)
				{
					qty = BigDecimal.ZERO;
				}
				qty = qty.add(BigDecimal.ONE);
				id2BinQty.put(prod.getM_Product_ID(), qty);
				id2Bin.put(prod.getM_Product_ID(), prod);

			}
			else if (userObj instanceof LegacyPackingItem)
			{
				final LegacyPackingItem item = (LegacyPackingItem)userObj;
				final I_M_Product prod = item.getM_Product();

				BigDecimal qty = id2ItemQty.get(prod.getM_Product_ID());
				if (qty == null)
				{
					qty = BigDecimal.ZERO;
				}
				qty = qty.add(item.getQtySum());
				id2ItemQty.put(prod.getM_Product_ID(), qty);
				id2Item.put(prod.getM_Product_ID(), prod);
			}
		}

		final StringBuffer sb = new StringBuffer();
		for (final Integer id : id2ItemQty.keySet())
		{
			sb.append(id2ItemQty.get(id));
			sb.append(" x ");
			sb.append(id2Item.get(id).getName());
			sb.append("\n");
		}
		for (final Integer id : id2BinQty.keySet())
		{
			sb.append(id2BinQty.get(id));
			sb.append(" x ");
			sb.append(id2Bin.get(id).getName());
			sb.append("\n");
		}

		// truncate the last line break
		if (sb.length() > 0)
		{
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public void removeUsedBin(final Properties ctx, final DefaultMutableTreeNode usedBinToRemoveNode)
	{
		final Map<ArrayKey, Map<I_M_ShipmentSchedule, BigDecimal>> key2Scheds = new HashMap<ArrayKey, Map<I_M_ShipmentSchedule, BigDecimal>>();

		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

		final Enumeration<DefaultMutableTreeNode> enPackedItemsToRemove = usedBinToRemoveNode.children();

		// iterate the packed items of the bin to be removed
		// store the products, shipment schedules and qtys in 'prodId2Scheds' so they won't get lost
		final Set<DefaultMutableTreeNode> nodesToRemove = new HashSet<DefaultMutableTreeNode>();
		while (enPackedItemsToRemove.hasMoreElements())
		{
			final DefaultMutableTreeNode itemToRemoveNode = enPackedItemsToRemove.nextElement();
			final LegacyPackingItem itemToRemove = (LegacyPackingItem)itemToRemoveNode.getUserObject();

			final Collection<I_M_ShipmentSchedule> schedsToRemove = itemToRemove.getShipmentSchedules();

			// store all different scheds (they might be distributed over more
			// than one item) with their qty
			for (final I_M_ShipmentSchedule schedToRemove : schedsToRemove)
			{
				// Group all scheds by their grouping key.
				// Note: for normal products, there is only item node for each productId, but to div/misc products,
				// there is one per orderLineId.

				// #100 FRESH-435: in FreshPackingItem we rely on all scheds having the same effective C_BPartner_Location_ID, so we need to include that in the key
				final boolean includeBPartner = true;
				final ArrayKey key = shipmentScheduleBL.mkKeyForGrouping(schedToRemove, includeBPartner);
				Map<I_M_ShipmentSchedule, BigDecimal> scheds = key2Scheds.get(key);

				if (scheds == null)
				{
					scheds = new HashMap<I_M_ShipmentSchedule, BigDecimal>();
					key2Scheds.put(key, scheds);
				}

				final BigDecimal existingRemovedQty = scheds.get(schedToRemove);

				if (existingRemovedQty == null)
				{
					scheds.put(schedToRemove, itemToRemove.getQtyForSched(schedToRemove));
				}
				else
				{
					scheds.put(schedToRemove, existingRemovedQty.add(itemToRemove.getQtyForSched(schedToRemove)));
				}
			}
			nodesToRemove.add(itemToRemoveNode);
		}

		// now remove the actual tree nodes
		for (final DefaultMutableTreeNode nodeToRemove : nodesToRemove)
		{
			this.removeNodeFromParent(nodeToRemove);
		}

		// now iterate the existing unpacked items and check if one of the
		// scheds from unpackedOl2Sched belongs to one of them. In that case
		// there won't be the need to a new unpackedItem for that sched.
		final Enumeration<DefaultMutableTreeNode> enUnpackedItems = getUnPackedItems().children();
		while (enUnpackedItems.hasMoreElements())
		{
			final DefaultMutableTreeNode existingUnpackedItemNode = enUnpackedItems.nextElement();
			final LegacyPackingItem existingUnpackedItem = (LegacyPackingItem)existingUnpackedItemNode.getUserObject();

			final Map<I_M_ShipmentSchedule, BigDecimal> newlyUnpackedScheds =
					key2Scheds.remove(Util.mkKey(existingUnpackedItem.getProductId()));

			if (newlyUnpackedScheds == null)
			{
				continue;
			}

			// add the shipment schedules of the newly unpacked product (if they haven't been added yet)
			existingUnpackedItem.addSchedules(newlyUnpackedScheds);
		}

		final UsedBin usedBin = (UsedBin)usedBinToRemoveNode.getUserObject();

		// create new unpacked items for the products that are still in prodId2Scheds
		for (final ArrayKey productId : key2Scheds.keySet())
		{
			final Map<I_M_ShipmentSchedule, BigDecimal> sched2qty = key2Scheds.get(productId);

			final LegacyPackingItem newUnpackedItem = new LegacyPackingItem(sched2qty, usedBin.getTrxName());

			final DefaultMutableTreeNode newUnpackedItemNode = new DefaultMutableTreeNode(newUnpackedItem);
			insertNodeInto(newUnpackedItemNode, getUnPackedItems(), getUnPackedItems().getChildCount());
		}
		removeNodeFromParent(usedBinToRemoveNode);

		// add the bin we just removed to the available bins
		boolean foundAvailBins = false;

		final Enumeration<DefaultMutableTreeNode> enAvailBins = getAvailableBins().children();
		while (enAvailBins.hasMoreElements())
		{
			final DefaultMutableTreeNode availBinsNode = enAvailBins.nextElement();

			final AvailableBins availBins = (AvailableBins)availBinsNode.getUserObject();
			if (availBins.getPc().getM_PackagingContainer_ID() == usedBin.getPackagingContainer().getM_PackagingContainer_ID())
			{
				availBins.setQtyAvail(availBins.getQtyAvail() + 1);
				this.nodeChanged(availBinsNode);
				foundAvailBins = true;
				break;
			}
		}

		if (!foundAvailBins)
		{
			final AvailableBins newAvailBins =
					new AvailableBins(ctx, usedBin.getPackagingContainer(), 1, usedBin.getTrxName());

			final DefaultMutableTreeNode newAvailBinsNode = new DefaultMutableTreeNode(newAvailBins);

			insertNodeInto(newAvailBinsNode, getAvailableBins(), getAvailableBins().getChildCount());
		}
	}

	public void addUsedBins(final Properties ctx, final DefaultMutableTreeNode availableBinsNode, final int qty)
	{
		final AvailableBins availableBins = (AvailableBins)availableBinsNode.getUserObject();

		final int qtyToUse;
		if (availableBins.getQtyAvail() > qty)
		{
			qtyToUse = qty;
		}
		else
		{
			qtyToUse = availableBins.getQtyAvail();
		}

		availableBins.setQtyAvail(availableBins.getQtyAvail() - qtyToUse);

		for (int i = 0; i < qtyToUse; i++)
		{
			final UsedBin newUsedBin = new UsedBin(ctx, availableBins.getPc(), availableBins.getTrxName());
			final DefaultMutableTreeNode newUsedBinNode = new DefaultMutableTreeNode(newUsedBin);

			this.insertNodeInto(newUsedBinNode, getUsedBins(), getUsedBins().getChildCount());
		}
		this.nodeChanged(availableBinsNode);
	}

	/**
	 * Removes the given quantity from the node representing a packing item. Might remove the node as a whole.
	 *
	 * @param packingItemNode
	 * @param oldUsedBin
	 * @param qty
	 */
	public void removePackedItem(
			final Properties ctx,
			final DefaultMutableTreeNode packingItemNode,
			final DefaultMutableTreeNode oldUsedBin,
			final BigDecimal qty,
			final boolean deletedUsedBin)
	{
		final Map<I_M_ShipmentSchedule, BigDecimal> qtysToTransfer = subtractPackingItem(packingItemNode, qty);

		final LegacyPackingItem packingItem = (LegacyPackingItem)packingItemNode.getUserObject();

		final DefaultMutableTreeNode existingUnpackedItemNode = findUnpackedPackingItemNode(packingItem);

		if (existingUnpackedItemNode == null)
		{
			// need to create a new node under 'nodeUnpackedItemsParent'
			final LegacyPackingItem newUnpackedItem = new LegacyPackingItem(qtysToTransfer, packingItem.getTrxName());

			final DefaultMutableTreeNode newUnpackedItemNode = new DefaultMutableTreeNode(newUnpackedItem);
			this.insertNodeInto(newUnpackedItemNode, nodeUnpackedItemsParent, nodeUnpackedItemsParent.getChildCount());
		}
		else
		{
			// use an existing node under 'nodeUnpackedItemsParent'
			final LegacyPackingItem existingUnpackedItem = (LegacyPackingItem)existingUnpackedItemNode.getUserObject();
			existingUnpackedItem.addSchedules(qtysToTransfer);

			this.nodeChanged(existingUnpackedItemNode);
		}

		if (oldUsedBin.isLeaf() && deletedUsedBin)
		{
			this.removeUsedBin(ctx, oldUsedBin);
		}
	}

	private Map<I_M_ShipmentSchedule, BigDecimal> subtractPackingItem(
			final DefaultMutableTreeNode packingItemNode, final BigDecimal qty)
	{
		final LegacyPackingItem packingItem = (LegacyPackingItem)packingItemNode.getUserObject();

		final Map<I_M_ShipmentSchedule, BigDecimal> result = packingItem.subtract(qty);

		if (packingItem.getQtySum().signum() > 0)
		{
			this.nodeChanged(packingItemNode);
		}
		else
		{
			this.removeNodeFromParent(packingItemNode);
		}
		return result;
	}

	/**
	 * Adds a {@link LegacyPackingItem} to another node
	 *
	 * @param newUsedBinNode
	 * @param qtysToTransfer
	 * @param packingItem
	 */
	private void addPackingItem(
			final DefaultMutableTreeNode newUsedBinNode,
			final Map<I_M_ShipmentSchedule, BigDecimal> qtysToTransfer,
			final LegacyPackingItem packingItem)
	{
		final DefaultMutableTreeNode existingPiNode =
				findPackingItemNode(newUsedBinNode, packingItem);

		if (existingPiNode == null)
		{
			final LegacyPackingItem newPi = new LegacyPackingItem(qtysToTransfer, packingItem.getTrxName());

			final DefaultMutableTreeNode newPiNode = new DefaultMutableTreeNode(newPi);
			this.insertNodeInto(newPiNode, newUsedBinNode, newUsedBinNode.getChildCount());
		}
		else
		{
			final LegacyPackingItem existingPi = (LegacyPackingItem)existingPiNode.getUserObject();
			existingPi.addSchedules(qtysToTransfer);
			this.nodeChanged(existingPiNode);
		}
	}

	public void movePackItem(
			final DefaultMutableTreeNode itemNode,
			final DefaultMutableTreeNode newUsedBin,
			final BigDecimal qty)
	{
		final Map<I_M_ShipmentSchedule, BigDecimal> qtyToUse = subtractPackingItem(itemNode, qty);

		final LegacyPackingItem packingItem = (LegacyPackingItem)itemNode.getUserObject();

		addPackingItem(newUsedBin, qtyToUse, packingItem);
	}

	@Override
	public String toString()
	{
		return dumpTree((DefaultMutableTreeNode)root);
	}

	@SuppressWarnings("unchecked")
	public static String dumpTree(final DefaultMutableTreeNode root)
	{

		Enumeration<DefaultMutableTreeNode> en = root.preorderEnumeration();
		int count = 0;
		StringBuffer sb = new StringBuffer();

		while (en.hasMoreElements())
		{
			DefaultMutableTreeNode nd = en
					.nextElement();
			for (int i = 0; i < nd.getLevel(); i++)
			{
				sb.append("    ");
			}
			if (nd.getUserObject() != null)
			{
				sb.append(nd.getUserObject().toString());
			}
			else
			{
				sb.append("<null>");
			}
			sb.append("\n");
			count++;
		}
		sb.append("Count=" + count);

		return sb.toString();
	}

	public DefaultMutableTreeNode findUnpackedPackingItemNode(final LegacyPackingItem packingItemToMatch)
	{
		return findPackingItemNode(this.nodeUnpackedItemsParent, packingItemToMatch);
	}

	/**
	 * For the given <code>packingItemToMatch</code>, this method returns the first tree node that is a child of the
	 * given <code>usedBinNode</code> and to whose packingItem the shipment schedules of <code>packingItemToMatch</code>
	 * can be added.
	 *
	 * @param usedBinNode
	 * @param packingItemToMatch
	 * @return
	 * @see LegacyPackingItem#canAddSchedule(I_M_ShipmentSchedule)
	 */
	@SuppressWarnings("unchecked")
	public DefaultMutableTreeNode findPackingItemNode(
			final DefaultMutableTreeNode usedBinNode,
			final IPackingItem packingItemToMatch)
	{
		final Enumeration<DefaultMutableTreeNode> piNodeEnum = usedBinNode.children();

		while (piNodeEnum.hasMoreElements())
		{
			final DefaultMutableTreeNode packingItemNode = piNodeEnum.nextElement();

			final LegacyPackingItem packingItem = (LegacyPackingItem)packingItemNode.getUserObject();
			if (packingItem.getGroupingKey() == packingItemToMatch.getGroupingKey())
			{
				return packingItemNode;
			}
		}
		return null;
	}


	/**
	 * For the given <code>packingItemToMatch</code>, this method returns qty of products contained in one specific
	 * packages, <br>
	 * if the bin is specified; <br>
	 * if the bin is null, will return the total qty from all packages
	 *
	 * @param packingItemToMatch
	 * @param specificBin
	 *            may be <code>null</code>. If set, then the Qty of the given bin is returned
	 * @return qty
	 */
	@SuppressWarnings("unchecked")
	public BigDecimal getQtyPackingItem(final IPackingItem packingItemToMatch, final DefaultMutableTreeNode specificBin)
	{
		BigDecimal qty = BigDecimal.ZERO;
		final Enumeration<DefaultMutableTreeNode> enumUsedBins = getUsedBins().children();
		while (enumUsedBins.hasMoreElements())
		{
			final DefaultMutableTreeNode currentUsedBin = enumUsedBins.nextElement();
			final Enumeration<DefaultMutableTreeNode> piNodeEnum = currentUsedBin.children();

			while (piNodeEnum.hasMoreElements())
			{
				final DefaultMutableTreeNode packingItemNode = piNodeEnum.nextElement();

				final LegacyPackingItem packingItem = (LegacyPackingItem)packingItemNode.getUserObject();
				// is that packing item is closed, don't count
				if (packingItem.isClosed())
				{
					continue;
				}

				if (packingItem.getGroupingKey() == packingItemToMatch.getGroupingKey())
				{
					if (null != specificBin && specificBin.equals(currentUsedBin))
						// note: we don't need to search further, because no bin
						// can have more than one item with the same grouping key
					{
						return packingItem.getQtySum();
					}
					else if (specificBin == null)
					{
						qty =  qty.add(packingItem.getQtySum());
					}
				}
			}

		}
		return qty;
	}


	public class PackingTreeItemComparator implements Comparator<X_M_PackagingTreeItem>
	{

		@Override
		 public int compare(X_M_PackagingTreeItem pi1, X_M_PackagingTreeItem pi2)
		{
			// compare items
			return pi1.get_ID() - pi2.get_ID();
		}

	}
}
