/**
 * 
 */
package de.metas.picking.terminal.form.swing;

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


import java.awt.Dimension;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.form.AvailableBins;
import de.metas.adempiere.form.IPackingItem;
import de.metas.adempiere.form.PackingDetailsMd;
import de.metas.adempiere.form.PackingItemsMap;
import de.metas.adempiere.form.PackingTreeModel;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.ITerminalKeyPanel;
import de.metas.adempiere.form.terminal.ITerminalLabel;
import de.metas.adempiere.form.terminal.ITerminalScrollPane;
import de.metas.adempiere.form.terminal.ITerminalTree;
import de.metas.adempiere.form.terminal.POSKeyLayout;
import de.metas.adempiere.form.terminal.TerminalKeyPanel;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.swing.SwingTerminalFactory;
import de.metas.adempiere.form.terminal.swing.TerminalSplitPane;
import de.metas.adempiere.form.terminal.swing.TerminalTree;
import de.metas.adempiere.model.I_M_Product;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;
import de.metas.picking.terminal.BoxKey;
import de.metas.picking.terminal.NewKartonKey;
import de.metas.picking.terminal.NewKartonLayout;
import de.metas.picking.terminal.ProductKey;
import de.metas.picking.terminal.Utils.PackingStates;

/**
 * Packing window panel (second window)
 * 
 * @author cg
 * 
 */
public class SwingPackageTerminalPanel extends AbstractPackageTerminalPanel
{
	private static final String ERR_SWING_PACKAGE_TERMINAL_PANEL_NO_CONTAINER = "de.metas.commission.picking.form.swing.SwingPackageTerminalPanel.noContainer";

	protected final Logger log = LogManager.getLogger(getClass());

	private IKeyLayout deliveryKey;
	private ITerminalKeyPanel deliveryPanel;
	private ITerminalTree tree;

	public SwingPackageTerminalPanel(final ITerminalContext terminalContext, final AbstractPackageTerminal parent)
	{
		super(terminalContext, parent);
	}

	@Override
	protected AbstractPackageDataPanel createPackageDataPanel()
	{
		return new SwingPackageDataPanel(this);
	}

	@Override
	protected SwingPackageBoxesItems createProductKeysPanel()
	{
		return new SwingPackageBoxesItems(this);
	}

	@Override
	protected IKeyLayout createPackingMaterialsKeyLayout()
	{
		final NewKartonLayout kartonKeyLayout = new NewKartonLayout(getTerminalContext());
		kartonKeyLayout.setRows(1);
		return kartonKeyLayout;
	}

	@Override
	public PackingDetailsMd getModel()
	{
		return (PackingDetailsMd)super.getModel();
	}

	@Override
	public void dynInit()
	{
		// Create Sub Panels

		// the tree
		tree = new TerminalTree(getTerminalContext());
		((TerminalTree)tree).setModel(getModel().getPackingTreeModel());
		((TerminalTree)tree).expandTree(true);
		//
		getModel().addPropertyChangeListener(this);

		// partner data
		final ITerminalLabel partnerData = getTerminalFactory().createLabel(getParent().getPickingOKPanel().getLabelData(), false);

		// panel for delivery type
		deliveryKey = new POSKeyLayout(getTerminalContext(), 540002); // TODO HARDCODED
		deliveryKey.setRows(0);
		deliveryKey.addListener(this);
		deliveryPanel = getTerminalFactory().createTerminalKeyPanel(deliveryKey, terminalKeyListener2keyPressed);
		deliveryPanel.setAllowKeySelection(true);
		// select default delivery type
		if (getModel().selectedShipperId > 0)
		{
			final List<ITerminalKey> keys = deliveryKey.getKeys();
			for (ITerminalKey tk : keys)
			{
				int id = Integer.parseInt(tk.getValue().getID());
				if (getModel().selectedShipperId == id)
				{
					((TerminalKeyPanel)deliveryPanel).onKeySelected(tk);
				}
			}
		}
		else
		{
			final List<ITerminalKey> keys = deliveryKey.getKeys();
			for (ITerminalKey tk : keys)
			{
				getModel().selectedShipperId = Integer.parseInt(tk.getValue().getID());
				((TerminalKeyPanel)deliveryPanel).onKeySelected(tk);
				break;
			}
		}
		// add delivery, details and new box in one panel
		SwingTerminalFactory.addChild(panelCenter, partnerData, "dock north, wrap, wmin 120");
		SwingTerminalFactory.addChild(panelCenter, deliveryPanel, "dock north, wrap, wmin 120");
		SwingTerminalFactory.addChild(panelCenter, getPickingData(), "dock north, wrap, wmin 120");
		SwingTerminalFactory.addChild(panelCenter, getPackingMaterialsKeyLayoutPanel(), "dock north,wrap, wmin 120, gapleft 5, gapright 5");
		// add tree in a scroll panel
		ITerminalScrollPane scroll = getTerminalFactory().createScrollPane(tree);
		// add split panel
		split = getTerminalFactory().createSplitPane(TerminalSplitPane.HORIZONTAL_SPLIT,
				scroll.getComponent(), panelCenter.getComponent());
		SwingTerminalFactory.getUI(panelCenter).setMinimumSize(new Dimension(310, 100));
		((TerminalSplitPane)split).setContinuousLayout(true);
		((TerminalSplitPane)split).setEnabled(true);
		((TerminalSplitPane)split).setOneTouchExpandable(true);
		((TerminalSplitPane)split).setMinimumSize(new Dimension());
		((TerminalSplitPane)split).setDividerSize(10);
		((TerminalSplitPane)split).addPropertyChangeListener(this);
		// add components in main panel
		add(getProductKeysPanel(), "dock east, w 55%, hmin 500, grow");
		add(split, "dock center, push, w 45%, hmin 500, grow");
	}

	@Override
	public final void keyPressed(final ITerminalKey key)
	{
		if (isDisposed())
		{
			return;
		}

		final SwingPackageBoxesItems productsKeyPanel = getProductKeysPanel();

		final AbstractPackageDataPanel pickingData = getPickingData();
		pickingData.requestFocus();

		if (key instanceof ProductKey)
		{
			final ProductKey productKey = (ProductKey)key;
			// productsKeyPanel.setSelectedProduct(pk); // it is already selected
			pickingData.setReadOnly(false);
			updatePackingItemPanel(productKey.getPackingItem(), productKey.getUsedBin(), false);
			getProductKeysPanel().setEnableAddRemoveButtons(true);
			if (productKey.getBoxNo() == PackingItemsMap.KEY_UnpackedItems)
			{
				pickingData.setQtyFieldReadOnly(false, true, false); //plus, minus, field
			}
			else
			{
				pickingData.setQtyFieldReadOnly(true, false, false);// plus, minus, field
			}
			
			final ITerminalKeyPanel keypanel = productsKeyPanel.getProductsKeyLayoutPanel();
			keypanel.onKeySelected(productKey);
		}
		else if (key instanceof NewKartonKey)
		{
			if (!hasAvailableBins())
			{
				throw new AdempiereException("@" + ERR_SWING_PACKAGE_TERMINAL_PANEL_NO_CONTAINER + "@");
			}
			//
			NewKartonKey kk = (NewKartonKey)key;
			final PackingTreeModel packingTreeModel = getModel().getPackingTreeModel();
			packingTreeModel.addUsedBins(Env.getCtx(), kk.getNode(), 1);
			packingTreeModel.reload();
			((TerminalTree)tree).expandTree(true);
			//
			refresh(true, true, true);
			pickingData.getbSave().setEnabled(false);
		}
		else if (key instanceof BoxKey)
		{
			final BoxKey bk = (BoxKey)key;
			
			productsKeyPanel.getProductsKeyLayoutPanel().onKeySelected(null);
			//setSelectedProduct(null);
			
			productsKeyPanel.getProductsKeyLayout().setSelectedBox(bk);
			//
			pickingData.getbLock().setEnabled(bk.getNode().children().hasMoreElements());// empty boxes should not be possible to lock

			// empty boxes should not be possible to pack and also already packed packag
			boolean enableClose = bk.getNode().children().hasMoreElements() && !PackingStates.closed.name().equals(bk.getStatus().getName());
			pickingData.getbClose().setEnabled(enableClose);
			//
			((SwingPackageDataPanel)pickingData).setDataValues(bk.getName().toString(),
					null,
					bk.getContainer().getMaxVolume().toString(),
					bk.getContainer().getMaxWeight().toString(),
					false,
					bk.getContainer().getDescription());
			pickingData.setReadOnly(true);
			if (bk.getStatus().getName()
					.equals(PackingStates.ready.name()))
			{
				bk.setReady(true);
				pickingData.getbDelete().setEnabled(false);
			}
			else
			{
				pickingData.getbDelete().setEnabled(false);
			}
			refresh(false, false, true);
		}
		else
		{
			// should be delivery key
			// set delivery type
			getModel().selectedShipperId = Integer.parseInt(key.getValue().getID());
			pickingData.validateModel();
		}
	}

	public final void expandTree(final boolean expand)
	{
		int row = 0;
		while (row < tree.getRowCount())
		{
			if (expand)
			{
				tree.expandRow(row);
			}
			else
			{
				tree.collapseRow(row);
			}
			row++;
		}
	}

	public void updatePackingItemPanel(final IPackingItem pi, final DefaultMutableTreeNode usedBin, boolean refresh)
	{
		if (pi.getShipmentSchedules().isEmpty())
		{
			return;
		}
		final I_M_Product product = pi.getM_Product();

		final BigDecimal productWeight = product.getWeight();
		// if the product has no weight info, make the field editable
		final boolean productHasNoWeightInfo = productWeight == null || productWeight.signum() == 0;
		final I_C_OrderLine ol = InterfaceWrapperHelper.create(pi.getShipmentSchedules().iterator().next().getC_OrderLine(), I_C_OrderLine.class);
		String desc;
		if (ol.isIndividualDescription())
		{
			desc = ol.getProductDescription();
		}
		else
		{
			desc = "";
		}

		final PackingDetailsMd model = getModel();
		model.setPiLocation("???");

		model.setPiProd(product.getValue() + " (" + product.getName() + ")");
		model.setPiQty(pi.getQtySum());

		model.setPiVolume(pi.getQtySum().multiply(product.getVolume()));

		model.setPiWeightEditable(productHasNoWeightInfo);
		model.setPiWeight(pi.computeWeight());

		if (ol.isIndividualDescription())
		{
			model.setPiOlProdDesc(ol.getProductDescription());
		}
		else
		{
			model.setPiOlProdDesc("");
		}

		final AbstractPackageDataPanel pickingData = getPickingData();
		if (refresh)
		{
			((SwingPackageDataPanel)pickingData).setDataValues(product.getValue() + " (" + product.getName() + ")",
					pi.getQtySum().toString(),
					pi.getQtySum().multiply(product.getVolume()).toString(),
					pi.computeWeight().toString(),
					productHasNoWeightInfo,
					desc);
		}
		else
		{
			((SwingPackageDataPanel)pickingData).setDataValues(pi, usedBin, product.getValue() + " (" + product.getName() + ")",
					pi.getQtySum().toString(),
					pi.getQtySum().multiply(product.getVolume()).toString(),
					pi.computeWeight().toString(),
					productHasNoWeightInfo,
					desc);
		}

	}

	@SuppressWarnings("unchecked")
	private boolean hasAvailableBins()
	{
		final PackingDetailsMd model = getModel();
		final DefaultMutableTreeNode availBins = model.getPackingTreeModel().getAvailableBins();
		model.getPackingTreeModel().reload(availBins);

		final Enumeration<DefaultMutableTreeNode> enAvailBins = availBins.children();
		boolean hasBins = false;
		while (enAvailBins.hasMoreElements())
		{
			final DefaultMutableTreeNode availBinsNode = enAvailBins.nextElement();

			final AvailableBins bins = (AvailableBins)availBinsNode.getUserObject();
			if (bins.getQtyAvail() > 0)
			{
				hasBins = true;
				break;
			}
		}
		return hasBins;
	}

	public ITerminalTree getTree()
	{
		return tree;
	}
}
