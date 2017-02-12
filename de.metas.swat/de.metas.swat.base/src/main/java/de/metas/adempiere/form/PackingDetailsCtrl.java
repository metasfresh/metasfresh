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


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.I_M_PackagingContainer;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.apps.ADialogDialog;
import org.compiere.apps.ConfirmPanel;
import org.compiere.grid.ed.VComboBox;
import org.compiere.grid.ed.VNumber;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.exception.NoContainerException;
import de.metas.adempiere.model.I_M_Product;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29'>(2009_0022_G61)</a>"
 */
public class PackingDetailsCtrl
{
	private static final Logger logger = LogManager.getLogger(PackingDetailsCtrl.class);

	private PackingDetailsV view;

	private final PackingDetailsMd model;

	public PackingDetailsCtrl(final PackingDetailsMd model)
	{
		this.model = model;
	}

	public void removePack(final DefaultMutableTreeNode packNode)
	{
		final List<DefaultMutableTreeNode> nodes = new ArrayList<DefaultMutableTreeNode>();

		for (int i = 0; i < packNode.getChildCount(); i++)
		{
			nodes.add((DefaultMutableTreeNode)packNode.getChildAt(i));
		}

		final PackingTreeModel packingTreeModel = model.getPackingTreeModel();
		packingTreeModel.removeNodeFromParent(packNode);

		final DefaultMutableTreeNode unallocated = packingTreeModel.getUnPackedItems();
		for (final DefaultMutableTreeNode node : nodes)
		{
			packingTreeModel.insertNodeInto(node, unallocated, unallocated.getChildCount());
		}
	}

	public void packBins(final Properties ctx)
	{
		final PackingTreeModel packingTreeModel = model.getPackingTreeModel();
		model.packer.pack(ctx, packingTreeModel, ITrx.TRXNAME_None);
	}

	private void updateUsedBinPanel(final Properties ctx, final DefaultMutableTreeNode packNode)
	{
		final UsedBin pack = (UsedBin)packNode.getUserObject();

		final I_M_Product product = pack.retrieveProduct(ctx, null);
		final I_M_PackagingContainer container = pack.getPackagingContainer();

		final BigDecimal maxWeight = container.getMaxWeight();
		final BigDecimal maxVolume = container.getMaxVolume();

		model.setPackProd(product.getValue() + " (" + product.getName() + ")");
		model.setPackMaxWeight(maxWeight == null ? BigDecimal.ZERO : maxWeight);
		model.setPackMaxVolume(maxVolume == null ? BigDecimal.ZERO : maxVolume);

		final BigDecimal usedVol = PackingTreeModel.getUsedVolume(packNode, null);
		final BigDecimal usedWeight = PackingTreeModel.getUsedWeight(packNode, null);

		model.setPackWeight(usedWeight);

		if (usedVol.signum() <= 0 || maxVolume.signum() <= 0)
		{
			model.setPackVolumeFilledPercent(BigDecimal.ZERO);
		}
		else
		{
			model.setPackVolumeFilledPercent(
					usedVol.divide(maxVolume, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
		}
		if (usedWeight.signum() <= 0 || maxWeight.signum() <= 0)
		{
			model.setPackWeightFilledPercent(BigDecimal.ZERO);
		}
		else
		{
			model.setPackWeightFilledPercent(
					usedWeight.divide(maxWeight, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
		}
		view.showDetailsTab(PackingDetailsV.USED_BIN);
	}

	private void updatePackingItemPanel(final DefaultMutableTreeNode piNode)
	{
		final LegacyPackingItem pi = (LegacyPackingItem)piNode.getUserObject();

		final I_M_Product product = pi.getM_Product();

		model.setPiLocation("???");

		model.setPiProd(product.getValue() + " (" + product.getName() + ")");
		model.setPiQty(pi.getQtySum());

		model.setPiVolume(pi.getQtySum().multiply(product.getVolume()));

		// if the product has no weight info, make the field editable
		final BigDecimal productWeight = product.getWeight();
		final boolean productHasNoWeightInfo = productWeight == null || productWeight.signum() == 0;

		model.setPiWeightEditable(productHasNoWeightInfo);
		model.setPiWeight(pi.computeWeight());

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(pi.getShipmentSchedules().iterator().next().getC_OrderLine(), I_C_OrderLine.class);
		if (ol.isIndividualDescription())
		{
			model.setPiOlProdDesc(ol.getProductDescription());
		}
		else
		{
			model.setPiOlProdDesc("");
		}
		// if (Util.isEmpty(ol.getDescription()))
		// {
		// model.setPiOlDescription(ol.getDescription());
		// }
		// else
		// {
		// model.setPiOlDescription("");
		// }
		view.showDetailsTab(PackingDetailsV.PACK_ITEM);
	}

	private void updateUsedBinsSumPanel(final Properties ctx, final DefaultMutableTreeNode node)
	{
		final String s = model.getPackingTreeModel().mkUsedBinsSum(ctx, ITrx.TRXNAME_None);

		view.setUsedBinSummary(s);
		view.showDetailsTab(PackingDetailsV.USED_BINS_SUM);
	}

	/**
	 * Makes the give view this controler's view. Also adds this controler's listers to the view.
	 * 
	 * @param view
	 */
	public void setView(final Properties ctx, final PackingDetailsV view)
	{
		if (this.view != null)
		{
			throw new IllegalStateException("There is already a view set: " + view);
		}
		this.view = view;
		view.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				cancel();
			}
		});

		view.addTreeSelectionListener(new TreeSelectionListener()
		{
			@Override
			public void valueChanged(final TreeSelectionEvent e)
			{
				final DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)e.getPath().getLastPathComponent();
				final PackingTreeModel packingTreeModel = model.getPackingTreeModel();
				packingTreeModel.setSelectedNode(selectedNode);

				// if (e.getOldLeadSelectionPath() != null)
				// {
				// // make sure that the current weight is stored (required if the given product has no weight info)
				// final DefaultMutableTreeNode unselectedNode =
				// (DefaultMutableTreeNode)e.getOldLeadSelectionPath().getLastPathComponent();
				// if (unselectedUserObj instanceof PackingItem)
				// {
				// final PackingItem unselectedPi = (PackingItem)unselectedUserObj;
				// if (model.getPiQty().signum() != 0)
				// {
				// unselectedPi.setWeightSingle(model.getPiWeight().divide(model.getPiQty(), RoundingMode.HALF_UP));
				// }
				// else
				// {
				// unselectedPi.setWeightSingle(BigDecimal.ZERO);
				// }
				// }
				// }

				final Object selectedUserObj = selectedNode.getUserObject();

				if (selectedNode == packingTreeModel.getUsedBins())
				{
					updateUsedBinsSumPanel(ctx, selectedNode);
				}
				else if (selectedUserObj instanceof UsedBin)
				{
					updateUsedBinPanel(ctx, selectedNode);
				}
				else if (selectedUserObj instanceof LegacyPackingItem)
				{
					updatePackingItemPanel(selectedNode);
				}
				else
				{
					view.showDetailsTab(PackingDetailsV.BLANK);
				}
			}
		});

		view.addPackingTreeListener(new IPackingTreeListener()
		{
			@Override
			public void usedBinRemoved(DefaultMutableTreeNode usedBinNode)
			{
				model.getPackingTreeModel().removeUsedBin(ctx, usedBinNode);
			}

			@Override
			public void usedBinAdded(DefaultMutableTreeNode availbinNode, int qty)
			{
				model.getPackingTreeModel().addUsedBins(ctx, availbinNode, qty);
			}

			@Override
			public void packedItemRemoved(
					DefaultMutableTreeNode packedItemNode,
					DefaultMutableTreeNode oldUsedBin,
					BigDecimal qty)
			{
				model.getPackingTreeModel().removePackedItem(ctx, packedItemNode, oldUsedBin, qty, true);
			}

			@Override
			public void packedItemAdded(
					DefaultMutableTreeNode packedItemNode,
					DefaultMutableTreeNode newUsedBin,
					BigDecimal qty)
			{

				model.getPackingTreeModel().movePackItem(packedItemNode, newUsedBin, qty);
			}

			@Override
			public void packedItemMoved(
					DefaultMutableTreeNode itemNode,
					DefaultMutableTreeNode oldUsedBin,
					DefaultMutableTreeNode newUsedBin,
					BigDecimal qty)
			{
				model.getPackingTreeModel().movePackItem(itemNode, newUsedBin, qty);
			}
		});

		view.setConfirmPanelListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				if (ConfirmPanel.A_OK.equals(e.getActionCommand()))
				{
					if (model.selectedShipperId <= 0)
					{
						model.setValidState(PackingDetailsMd.STATE_INVALID);
					}
					view.dispose();
				}
				else if (ConfirmPanel.A_CANCEL.equals(e.getActionCommand()))
				{
					cancel();
				}
			}
		});

		view.addShipperListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				final VComboBox fShipper = (VComboBox)e.getSource();
				final Integer shipperId = (Integer)fShipper.getValue();
				model.selectedShipperId = (shipperId == null ? 0 : shipperId);

				validateModel();
			}
		});

		view.addPLWeightChangedListener(new VetoableChangeListener()
		{
			@Override
			public void vetoableChange(final PropertyChangeEvent evt) throws PropertyVetoException
			{
				final VNumber weightField = (VNumber)evt.getSource();
				final BigDecimal weight = (BigDecimal)weightField.getValue();
				final Object selectedUserObj = model.getPackingTreeModel().getSelectedNode().getUserObject();
				assert selectedUserObj instanceof LegacyPackingItem;

				final LegacyPackingItem pi = (LegacyPackingItem)selectedUserObj;
				if (model.getPiQty().signum() != 0)
				{
					pi.setWeightSingle(weight.divide(model.getPiQty(), RoundingMode.HALF_UP));
				}
				else
				{
					pi.setWeightSingle(BigDecimal.ZERO);
				}
				model.setPiWeight(weight);
				validateModel();
			}
		});

		view.addRecomputeListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				recompute(ctx);
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public void validateModel()
	{
		final boolean shipperOk = !model.useShipper || model.selectedShipperId > 0;
		boolean weightsOk = true;
		final Enumeration<DefaultMutableTreeNode> enu = model.getPackingTreeModel().getUsedBins().depthFirstEnumeration();
		while (enu.hasMoreElements())
		{
			final DefaultMutableTreeNode node = enu.nextElement();
			final Object userObj = node.getUserObject();
			if (userObj instanceof LegacyPackingItem)
			{
				final LegacyPackingItem pi = (LegacyPackingItem)userObj;
				if (pi.computeWeight().signum() <= 0)
				{
					weightsOk = false;
					break;
				}
			}
		}

		view.setConfirmButtonEnabled(shipperOk && weightsOk);
	}

	public void recompute(final Properties ctx)
	{
		final PackingTreeModel packingTreeModel = model.getPackingTreeModel();
		
		packingTreeModel.reset(ctx);
		try
		{
			model.packer.pack(ctx, packingTreeModel, null);
		}
		catch (NoContainerException e)
		{
			logger.error(e.toString());
			new ADialogDialog(view, e.getMessage(), e.toString(), JOptionPane.ERROR_MESSAGE).getReturnCode();

		}
		finally
		{
			view.expandTree(false);
			view.expandTree(true);
		}
	}

	void cancel()
	{
		model.setValidState(PackingDetailsMd.STATE_INVALID);
		view.dispose();
		
		Services.get(IShipmentSchedulePA.class).deleteUnprocessedLocksForShipmentRun(0, Env.getAD_User_ID(Env.getCtx()), null);
	}
}
