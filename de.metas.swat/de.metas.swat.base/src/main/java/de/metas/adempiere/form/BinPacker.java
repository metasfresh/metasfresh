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
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;

import org.adempiere.model.I_M_PackagingContainer;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import de.metas.adempiere.exception.NoContainerException;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29'>(2009_0022_G61)</a>"
 */
public class BinPacker implements IBinPacker
{

	private static final Logger logger = LogManager.getLogger(BinPacker.class);

	public void pack(final Properties ctx, final PackingTreeModel model, final String trxName)
	{
		final DefaultMutableTreeNode unallocRoot = model.getUnPackedItems();
		final DefaultMutableTreeNode availBinRoot = model.getAvailableBins();

		final List<DefaultMutableTreeNode> unallocNodes = mkSortedListBigToSmall(unallocRoot);
		final List<DefaultMutableTreeNode> availBins = mkSortedListBigToSmall(availBinRoot);
		final List<DefaultMutableTreeNode> usedBins = new ArrayList<DefaultMutableTreeNode>();

		while (!unallocNodes.isEmpty())
		{
			// make another bin available for using
			addBinForUsage(ctx, unallocNodes, availBins, usedBins, model, trxName);

			// try to put all our unallocated items into the currently available bins
			alloc(unallocNodes, usedBins, model, trxName);
		}
	}
	
	/**
	 * Sums up the volume and weight of all items still unpacked, selects a bin from <code>availBins</code> and updates
	 * <code>model</code> accordingly.
	 * 
	 * If there is no bin large enough for the summed up weight and volume, the largest bin available is added.
	 * 
	 * @param unallocNodes
	 * @param availBins
	 * @param usedBins
	 * @param model
	 * @param trxName
	 */
	private void addBinForUsage(
			final Properties ctx,
			final List<DefaultMutableTreeNode> unallocNodes,
			final List<DefaultMutableTreeNode> availBins,
			final List<DefaultMutableTreeNode> usedBins,
			final PackingTreeModel model, final String trxName)
	{

		logger
				.debug("Computing the overall volume and weight we still need to cover");

		BigDecimal unallocVolumeSum = BigDecimal.ZERO;
		BigDecimal unallocWeightSum = BigDecimal.ZERO;
		BigDecimal unallocVolumeMax = BigDecimal.ZERO;
		BigDecimal unallocWeightMax = BigDecimal.ZERO;

		for (final DefaultMutableTreeNode currentNode : unallocNodes)
		{

			final LegacyPackingItem pi = getPI(currentNode);
			final BigDecimal volSingle = pi.retrieveVolumeSingle(trxName);
			final BigDecimal weightSingle = pi.retrieveWeightSingle(trxName);
			unallocVolumeSum = unallocVolumeSum.add(pi.getQtySum().multiply(
					volSingle));
			unallocWeightSum = unallocWeightSum.add(pi.getQtySum().multiply(
					weightSingle));

			if (unallocVolumeMax.compareTo(volSingle) < 0)
			{
				unallocVolumeMax = volSingle;
			}
			if (unallocWeightMax.compareTo(weightSingle) < 0)
			{
				unallocWeightMax = weightSingle;
			}
		}
		logger.debug("Still required: volume-sum=" + unallocVolumeSum
				+ "; volume-max=" + unallocVolumeMax + "; weight-sum:"
				+ unallocWeightSum + "; weight-max=" + unallocWeightMax);

		removeUnavailableBins(availBins, unallocVolumeMax, unallocWeightMax);

		final DefaultMutableTreeNode nexBinToUseNode = findBinNode(availBins,
				unallocVolumeSum, unallocWeightSum);
		final AvailableBins foundBin = getBin(nexBinToUseNode);

		foundBin.setQtyAvail(foundBin.getQtyAvail() - 1);

		final I_M_PackagingContainer foundPC = foundBin.getPc();

		final UsedBin newPack = new UsedBin(ctx, foundPC, trxName);
		logger.info("Adding " + newPack);

		final DefaultMutableTreeNode newPackNode = new DefaultMutableTreeNode(
				newPack);
		usedBins.add(newPackNode);
		model.insertNodeInto(newPackNode, model.getUsedBins(), model
				.getUsedBins().getChildCount());
	}

	private void alloc(
			final List<DefaultMutableTreeNode> unallocNodes,
			final List<DefaultMutableTreeNode> usedBins,
			final PackingTreeModel model,
			final String trxName)
	{
		final Set<DefaultMutableTreeNode> packedNodes = new HashSet<DefaultMutableTreeNode>();

		sortUsedBins(usedBins, trxName);
		Iterator<DefaultMutableTreeNode> itUsedBinNodes = usedBins.iterator();

		boolean progressMade = false;

		for (final DefaultMutableTreeNode packedNode : unallocNodes)
		{
			if (!itUsedBinNodes.hasNext())
			{
				break;
			}

			final DefaultMutableTreeNode usedBinNode = itUsedBinNodes.next();
			final UsedBin usedPack = getPack(usedBinNode);

			// find out how much space we have left
			final BigDecimal maxVolume = usedPack.getPackagingContainer().getMaxVolume();
			final BigDecimal usedVolume = PackingTreeModel.getUsedVolume(usedBinNode, trxName);
			final BigDecimal freeVolume = maxVolume.subtract(usedVolume);

			final LegacyPackingItem unpackedPi = getPI(packedNode);
			final BigDecimal singleVol = unpackedPi.retrieveVolumeSingle(trxName);

			final BigDecimal binFreeQty;
			if (singleVol.signum() > 0)
			{
				binFreeQty = freeVolume.divide(singleVol, BigDecimal.ROUND_FLOOR).setScale(0, BigDecimal.ROUND_FLOOR);
			}
			else
			{
				binFreeQty = new BigDecimal(Long.MAX_VALUE);
			}
			if (binFreeQty.signum() <= 0)
			{
				// there is no space left in the bins that we are currently using
				break;
			}

			final BigDecimal requiredQty = unpackedPi.getQtySum();
			if (binFreeQty.compareTo(requiredQty) >= 0)
			{
				// the pl fits completely into our bin
				model.insertNodeInto(packedNode, usedBinNode, usedBinNode.getChildCount());
				packedNodes.add(packedNode);

				// sort the used bins again because the current bin might not be
				// the one with the biggest free volume anymore
				sortUsedBins(usedBins, trxName);
				itUsedBinNodes = usedBins.iterator();
			}
			else
			{
				// split the current item and move a part into a new item to be
				// located in our bin
				final Map<I_M_ShipmentSchedule, BigDecimal> qtysToTransfer = unpackedPi.subtract(binFreeQty);
				final LegacyPackingItem newPi = new LegacyPackingItem(qtysToTransfer, trxName);

				final DefaultMutableTreeNode newItemNode = new DefaultMutableTreeNode(newPi);
				model.insertNodeInto(newItemNode, usedBinNode, usedBinNode.getChildCount());
			}
			progressMade = true;
		}
		unallocNodes.removeAll(packedNodes);

		if (!progressMade)
		{
			throw new NoContainerException(false, false);
		}
	}

	/**
	 * 
	 * @param unallocRoot
	 * @return list with the biggest first
	 */
	private List<DefaultMutableTreeNode> mkSortedListBigToSmall(final DefaultMutableTreeNode unallocRoot)
	{
		final List<DefaultMutableTreeNode> unallocNodes = mkList(unallocRoot);
		sortList(unallocNodes, -1);
		return unallocNodes;
	}

	/**
	 * sort the used bins according to their free volume. The one with the biggest free volume comes first.
	 * 
	 * @param nodes
	 * @param trxName
	 */
	private void sortUsedBins(final List<DefaultMutableTreeNode> nodes, final String trxName)
	{
		Collections.sort(nodes, new Comparator<DefaultMutableTreeNode>()
		{
			public int compare(final DefaultMutableTreeNode o1, final DefaultMutableTreeNode o2)
			{
				final BigDecimal usedVol1 = PackingTreeModel.getUsedVolume(o1, trxName);
				final BigDecimal usedVol2 = PackingTreeModel.getUsedVolume(o2, trxName);
				final BigDecimal freeVol1 = getPack(o1).getPackagingContainer().getMaxVolume().subtract(usedVol1);
				final BigDecimal freeVol2 = getPack(o2).getPackagingContainer().getMaxVolume().subtract(usedVol2);

				return freeVol2.compareTo(freeVol1);
			}
		});
	}

	/**
	 * 
	 * @param unallocNodes
	 * @param factor
	 *            if factor is <0, the nodes are sorted in reverse order
	 */
	private void sortList(final List<DefaultMutableTreeNode> unallocNodes, final int factor)
	{
		Collections.sort(unallocNodes, new Comparator<DefaultMutableTreeNode>()
				{
					@SuppressWarnings("unchecked")
					public int compare(DefaultMutableTreeNode o1, DefaultMutableTreeNode o2)
					{
						final Comparable p1 = (Comparable)o1.getUserObject();
						final Comparable p2 = (Comparable)o2.getUserObject();
						return p1.compareTo(p2) * factor;
					}
				});
	}

	@SuppressWarnings("unchecked")
	private List<DefaultMutableTreeNode> mkList(
			final DefaultMutableTreeNode unallocRoot)
	{
		final List<DefaultMutableTreeNode> unallocNodes = new ArrayList<DefaultMutableTreeNode>(
				unallocRoot.getChildCount());

		final Enumeration<DefaultMutableTreeNode> unallocEnum = unallocRoot
				.children();
		while (unallocEnum.hasMoreElements())
		{
			unallocNodes.add(unallocEnum.nextElement());
		}
		return unallocNodes;
	}

	private LegacyPackingItem getPI(DefaultMutableTreeNode o2)
	{
		return (LegacyPackingItem)o2.getUserObject();
	}

	private UsedBin getPack(DefaultMutableTreeNode o2)
	{
		return (UsedBin)o2.getUserObject();
	}

	private AvailableBins getBin(DefaultMutableTreeNode n)
	{
		return (AvailableBins)n.getUserObject();
	}

	private void removeUnavailableBins(
			final List<DefaultMutableTreeNode> availBins,
			final BigDecimal requiredVolume, final BigDecimal requiredWeigth)
	{

		final Set<DefaultMutableTreeNode> nodesToRemove = new HashSet<DefaultMutableTreeNode>();

		boolean insufficientVolume = false;
		boolean insufficientWeight = false;

		for (final DefaultMutableTreeNode binNode : availBins)
		{

			final AvailableBins bins = getBin(binNode);

			if (bins.getQtyAvail() <= 0)
			{

				logger.info("Bins '" + bins + "' are no longer available");
				nodesToRemove.add(binNode);
			}
			if (bins.getPc().getMaxVolume().compareTo(requiredVolume) < 0)
			{

				logger.info("Payload volume of available bins '" + bins
						+ "' is too small");
				nodesToRemove.add(binNode);
			}
			if (bins.getPc().getMaxWeight().compareTo(requiredWeigth) < 0)
			{

				logger.info("Payload weight of available bins '" + bins
						+ "' is too small");
				nodesToRemove.add(binNode);
			}
		}

		availBins.removeAll(nodesToRemove);

		if (availBins.isEmpty())
		{
			throw new NoContainerException(insufficientVolume,
					insufficientWeight);
		}
	}

	/**
	 * 
	 * @param availBins
	 *            the bins to choose from. This method asserts that they are sorted in descending order.
	 * @param vol
	 * @param weight
	 * @return
	 */
	private DefaultMutableTreeNode findBinNode(
			final List<DefaultMutableTreeNode> availBins, final BigDecimal vol,
			final BigDecimal weight)
	{

		if (availBins.isEmpty())
		{
			throw new NoContainerException(false, false);
		}

		// check if everything fits into one bin, starting with the smallest one
		for (int i = availBins.size() - 1; i >= 0; i--)
		{

			final DefaultMutableTreeNode packNode = availBins.get(i);

			final AvailableBins bin = (AvailableBins)packNode.getUserObject();
			final I_M_PackagingContainer pc = bin.getPc();

			if (pc.getMaxVolume().compareTo(vol) >= 0
					&& pc.getMaxWeight().compareTo(weight) >= 0
					&& bin.getQtyAvail() > 0)
			{

				return packNode;
			}
		}

		// no bin is big enough, return the biggest one we got
		return availBins.get(0);
	}
}
