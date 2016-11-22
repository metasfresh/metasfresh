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


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.VetoableChangeListener;
import java.math.BigDecimal;
import java.util.TooManyListenersException;

import javax.swing.BorderFactory;
import javax.swing.DropMode;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.I_M_PackagingContainer;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.form.FormFrame;
import org.compiere.grid.ed.VLocator;
import org.compiere.grid.ed.VLookup;
import org.compiere.grid.ed.VNumber;
import org.compiere.grid.ed.VString;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.Lookup;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.compiere.util.Env;

import de.metas.adempiere.service.IPackagingBL;

/**
 * GUI to configure the actual packaging (which goods go into which package)
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29'>(2009_0022_G61)</a>"
 */
public class PackingDetailsV extends CDialog implements ActionListener, PropertyChangeListener
{
	public static final String BLANK = "BLANK";

	private static final String MSG_LOCATOR = I_M_Locator.COLUMNNAME_M_Locator_ID;

	private static final String MSG_OVERVIEW = "packing.Overview";

	public static final String MSG_PACKAGE = "packing.PackagesOnHand";

	public static final String MSG_PRODUKT = I_M_Product.COLUMNNAME_M_Product_ID;

	public static final String MSG_QTY = "Qty";

	public static final String MSG_OL_PROD_DESC = "packing.olProductDesc";

	private static final String MSG_RECOMPUTE = "packing.Recompute";

	private static final String MSG_SHIPPER = I_M_Shipper.COLUMNNAME_M_Shipper_ID;

	public static final String MSG_VOLUME = I_M_Product.COLUMNNAME_Volume;

	private static final String MSG_VOLUME_MAX = I_M_PackagingContainer.COLUMNNAME_MaxVolume;

	private static final String MSG_VOLUME_PERCENT = "packing.VolumeFilledPercent";

	public static final String MSG_WEIGHT = I_M_Product.COLUMNNAME_Weight;

	private static final String MSG_WEIGHT_MAX = I_M_PackagingContainer.COLUMNNAME_MaxWeight;

	private static final String MSG_WEIGHT_PERCENT = "packing.WeightFilledPercent";

	private static final String MSG_WINDOW_TITLE = "packing.Details";

	public static final String PACK_ITEM = "PACK_ITEM";

	/**
	 * 
	 */
	private static final long serialVersionUID = -5329680535343231328L;

	public static final String USED_BIN = "USED_BIN";

	public static final String USED_BINS_SUM = "USED_BINS_SUM";

	private static final int WIDTH_RIGHT_PANEL = 400;

	private static final int WIDTH_TREE_PANEL = 400;

	private final CPanel centerPanel = new CPanel();

	private final ConfirmPanel confirmPanel = ConfirmPanel.builder()
			.withCancelButton(true)
			.withResetButton(true)
			.build();

	private final CardLayout detailsLayout = new CardLayout();

	private final VLocator fILocation = new VLocator();

	private final CTextField fIProduct = new CTextField();

	// private final VString fIOlComment = new VString();

	private final VString fIOlProdDesc = new VString();

	private final VNumber fIQty = new VNumber();

	private final VNumber fIVolume = new VNumber();

	private final VNumber fIWeight = new VNumber();

	private final VString fPProduct = new VString();
	private final VNumber fPVolume = new VNumber();
	private final VNumber fPVolumeFilledPercent = new VNumber();

	private final VNumber fPVolumeMax = new VNumber();

	private final VNumber fPWeight = new VNumber();
	private final VNumber fPWeightFilledPercent = new VNumber();
	private final VNumber fPWeightMax = new VNumber();

	private VLookup fShipper = null;
	private JTextArea fUsedBinsSum = new JTextArea();

	private final CPanel mainPanel = new CPanel();

	private final PackingTreeDndHandler packingTreeDndHandler;

	/**
	 * contains GUI elements related to the currently selected package line.
	 */
	private final CPanel rightLowerPanel = new CPanel();

	private final JTree tree;

	/**
	 * Opens a model dialog.
	 * 
	 * @param parent
	 */
	public PackingDetailsV(final FormFrame parent, final PackingDetailsMd model)
	{
		super(parent, true);

		this.setTitle(Services.get(IMsgBL.class).translate(Env.getCtx(), MSG_WINDOW_TITLE));

		// add a property change listener to react to changed model properties
		model.addPropertyChangeListener(this);

		final PackingTreeModel packingTreeModel = model.getPackingTreeModel();
		
		tree = new JTree(packingTreeModel);
		tree.setRootVisible(false);
		tree.setCellRenderer(new PackingTreeCelRenderer());

		tree.setDragEnabled(true);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setDropMode(DropMode.ON);

		packingTreeDndHandler = new PackingTreeDndHandler(tree);
		DropTarget dropTarget = new DropTarget();
		try
		{
			dropTarget.addDropTargetListener(packingTreeDndHandler);
		}
		catch (TooManyListenersException e)
		{
			throw new AdempiereException(e);
		}
		dropTarget.setComponent(tree);
		tree.setDropTarget(dropTarget);

		// add a tree model listener to expand newly added nodes.
		packingTreeModel.addTreeModelListener(new TreeModelListener()
		{
			@Override
			public void treeNodesChanged(TreeModelEvent e)
			{ /* Nothing to do */
			}

			@Override
			public void treeNodesInserted(final TreeModelEvent e)
			{
				// note: the invokeLate is absolutely necessary. Otherwise the
				// tree would be corrupted.
				SwingUtilities.invokeLater(new Runnable()
				{
					@Override
					public void run()
					{
						showNode(e.getTreePath());
					}
				});
			}

			@Override
			public void treeNodesRemoved(TreeModelEvent e)
			{
			}

			@Override
			public void treeStructureChanged(TreeModelEvent e)
			{
			}
		});

		expandTree(false);
		expandTree(true);

		dynInit(model);
		jbInit();

		getRootPane().setDefaultButton(confirmPanel.getOKButton());

		setMinimumSize(new Dimension(WIDTH_TREE_PANEL + WIDTH_RIGHT_PANEL, 400));
	}

	/**
	 * Listeners are notified when a button of the window's confirm panel is pressed.
	 * 
	 * @param l
	 */
	public void setConfirmPanelListener(final ActionListener l)
	{
		confirmPanel.setActionListener(l);
	}

	public void addPackingTreeListener(final IPackingTreeListener l)
	{
		packingTreeDndHandler.addCommissiontreeListener(l);
	}

	public void addRecomputeListener(final ActionListener l)
	{
		confirmPanel.getResetButton().addActionListener(l);
	}

	public void addShipperListener(final ActionListener l)
	{
		fShipper.addActionListener(l);
	}

	public void addPLWeightChangedListener(final VetoableChangeListener l)
	{
		fIWeight.addVetoableChangeListener(l);
	}

	/**
	 * Listeners are notified when the window's tree selection state changes.
	 * 
	 * @param l
	 */
	public void addTreeSelectionListener(final TreeSelectionListener l)
	{
		tree.addTreeSelectionListener(l);
	}

	private void dynInit(final PackingDetailsMd model)
	{

		final IPackagingBL packagingBL = Services.get(IPackagingBL.class);

		final Lookup shipperLookup = packagingBL.createShipperLookup();

		fShipper =
				new VLookup(I_M_Shipper.COLUMNNAME_M_Shipper_ID, true, false, true, shipperLookup);

		fShipper.setPreferredSize(new Dimension(200, 25));

		if (model.selectedShipperId > 0)
		{
			fShipper.setValue(model.selectedShipperId);
		}
		fShipper.setEnabled(model.useShipper);
		fShipper.setReadWrite(model.useShipper);
	}

	/**
	 * Expand or collapse the complete tree.
	 * 
	 * @param expand
	 */
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

	public void setConfirmButtonEnabled(final boolean enabled)
	{
		confirmPanel.getOKButton().setEnabled(enabled);
	}

	/**
	 * Static Init
	 * 
	 */
	private void jbInit()
	{
		final JScrollPane treePane = new JScrollPane();
		treePane.getViewport().add(tree, null);
		treePane.setMinimumSize(new Dimension(WIDTH_TREE_PANEL, 400));

		final CPanel packItemPanel = setupPackingItemPanel();
		final CPanel usedBinPanel = setupUsedBinPanel();
		final CPanel usedBinsSumPanel = setupUsedBinsSumPanel();

		rightLowerPanel.setLayout(detailsLayout);
		rightLowerPanel.setBorder(BorderFactory.createEtchedBorder());
		rightLowerPanel.add(packItemPanel, PACK_ITEM);
		rightLowerPanel.add(usedBinPanel, USED_BIN);
		rightLowerPanel.add(usedBinsSumPanel, USED_BINS_SUM);
		rightLowerPanel.add(new CPanel(), BLANK);

		detailsLayout.show(rightLowerPanel, BLANK);

		// detailsTabbedPane.setBorder(BorderFactory.createEtchedBorder());

		final CPanel rightUpperPanel = new CPanel();
		rightUpperPanel.setLayout(new GridBagLayout());
		rightUpperPanel.setBorder(BorderFactory.createEtchedBorder());

		final GridBagConstraints gbc =
				new GridBagConstraints(0, 0, 1, 1, 0, 0,
						GridBagConstraints.EAST,
						GridBagConstraints.NONE,
						new Insets(5, 0, 0, 5),
						0, 0);

		final CLabel lShipper = new CLabel(Services.get(IMsgBL.class).translate(Env.getCtx(), MSG_SHIPPER));
		lShipper.setLabelFor(fShipper);
		rightUpperPanel.add(lShipper, gbc);

		gbc.gridx = 1;
		rightUpperPanel.add(fShipper, gbc);

		final JSplitPane rightPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, rightUpperPanel, rightLowerPanel);
		rightPanel.setMinimumSize(new Dimension(WIDTH_RIGHT_PANEL, 400));

		final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePane, rightPanel);

		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		centerPanel.add(splitPane, BorderLayout.CENTER);

		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(centerPanel, BorderLayout.CENTER);

		confirmPanel.getResetButton().setText(Services.get(IMsgBL.class).translate(Env.getCtx(), MSG_RECOMPUTE));

		mainPanel.add(confirmPanel, BorderLayout.PAGE_END);

		getContentPane().add(mainPanel);
	}

	private void setupField(final CTextField field)
	{
		field.setPreferredSize(new Dimension(150, field.getPreferredSize().height));
		field.setEditable(false);
		field.setHorizontalAlignment(JTextField.RIGHT);
	}

	private CPanel setupPackingItemPanel()
	{
		final CPanel packItemPanel = new CPanel();
		packItemPanel.setBorder(BorderFactory.createEtchedBorder());
		packItemPanel.setLayout(new GridBagLayout());

		final GridBagConstraints gbc =
				new GridBagConstraints(0, 0, 1, 1, 0, 0,
						GridBagConstraints.EAST,
						GridBagConstraints.NONE,
						new Insets(5, 0, 0, 5),
						0, 0);

		final CLabel lProduct = new CLabel(Services.get(IMsgBL.class).translate(Env.getCtx(), MSG_PRODUKT));
		lProduct.setLabelFor(fIProduct);
		setupField(fIProduct);

		packItemPanel.add(lProduct, gbc);
		gbc.gridx = 1;
		packItemPanel.add(fIProduct, gbc);

		final CLabel lLocation = new CLabel(Services.get(IMsgBL.class).translate(Env.getCtx(), MSG_LOCATOR));
		lLocation.setLabelFor(fILocation);
		// gbc.gridx = 0;
		// gbc.gridy = 1;
		// packItemPanel.add(lLocation, gbc);
		// gbc.gridx = 1;
		// packItemPanel.add(fLLocation, gbc);

		final CLabel lWeight = new CLabel(Services.get(IMsgBL.class).translate(Env.getCtx(), MSG_WEIGHT));
		lWeight.setLabelFor(fIWeight);
		setupVNumber(fIWeight);

		gbc.gridx = 0;
		gbc.gridy = 1;
		packItemPanel.add(lWeight, gbc);
		gbc.gridx = 1;
		packItemPanel.add(fIWeight, gbc);

		final CLabel lVolume = new CLabel(Services.get(IMsgBL.class).translate(Env.getCtx(), MSG_VOLUME));
		lVolume.setLabelFor(fIVolume);
		setupVNumber(fIVolume);

		gbc.gridx = 0;
		gbc.gridy = 2;
		packItemPanel.add(lVolume, gbc);
		gbc.gridx = 1;
		packItemPanel.add(fIVolume, gbc);

		final CLabel lQty = new CLabel(Services.get(IMsgBL.class).translate(Env.getCtx(), MSG_QTY));
		lQty.setLabelFor(fIQty);
		setupVNumber(fIQty);

		gbc.gridx = 0;
		gbc.gridy = 3;
		packItemPanel.add(lQty, gbc);
		gbc.gridx = 1;
		packItemPanel.add(fIQty, gbc);

		// final CLabel lOlComment = new CLabel(Services.get(IMsgBL.class).translate(Env.getCtx(), MSG_OL_COMMENT));
		// lOlComment.setLabelFor(fIOlComment);
		// setupField(fIOlComment);
		//
		// gbc.gridx = 0;
		// gbc.gridy = 4;
		// packItemPanel.add(lOlComment, gbc);
		// gbc.gridx = 1;
		// packItemPanel.add(fIOlComment, gbc);

		final CLabel lOlProdDesc = new CLabel(Services.get(IMsgBL.class).translate(Env.getCtx(), MSG_OL_PROD_DESC));
		lOlProdDesc.setLabelFor(fIOlProdDesc);
		setupField(fIOlProdDesc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		packItemPanel.add(lOlProdDesc, gbc);
		gbc.gridx = 1;
		packItemPanel.add(fIOlProdDesc, gbc);

		return packItemPanel;
	}

	private void setupVNumber(final VNumber editor)
	{
		editor.setPreferredSize(new Dimension(150, fIWeight.getPreferredSize().height));
		editor.setReadWrite(false);
	}

	private CPanel setupUsedBinPanel()
	{
		final CPanel usedBinPanel = new CPanel();
		usedBinPanel.setLayout(new GridBagLayout());
		usedBinPanel.setBorder(BorderFactory.createEtchedBorder());

		final GridBagConstraints gbc =
				new GridBagConstraints(0, 0, 1, 1, 0, 0,
						GridBagConstraints.EAST,
						GridBagConstraints.NONE,
						new Insets(5, 0, 0, 5),
						0, 0);

		final CLabel lProduct = new CLabel(Services.get(IMsgBL.class).translate(Env.getCtx(), MSG_PACKAGE));
		lProduct.setLabelFor(fPProduct);
		setupField(fPProduct);
		usedBinPanel.add(lProduct, gbc);
		gbc.gridx = 1;
		usedBinPanel.add(fPProduct, gbc);

		final CLabel lWeight = new CLabel(Services.get(IMsgBL.class).translate(Env.getCtx(), MSG_WEIGHT));
		lWeight.setLabelFor(fPWeight);
		setupVNumber(fPWeight);
		gbc.gridx = 0;
		gbc.gridy = 1;
		usedBinPanel.add(lWeight, gbc);
		gbc.gridx = 1;
		usedBinPanel.add(fPWeight, gbc);

		final CLabel lWeightMax = new CLabel(Services.get(IMsgBL.class).translate(Env.getCtx(), MSG_WEIGHT_MAX));
		lWeightMax.setLabelFor(fPWeightMax);
		setupVNumber(fPWeightMax);
		gbc.gridx = 0;
		gbc.gridy = 2;
		usedBinPanel.add(lWeightMax, gbc);
		gbc.gridx = 1;
		usedBinPanel.add(fPWeightMax, gbc);

		final CLabel lWeightFilledPercent = new CLabel(Services.get(IMsgBL.class).translate(Env.getCtx(), MSG_WEIGHT_PERCENT));
		lWeightFilledPercent.setLabelFor(fPWeightFilledPercent);
		setupVNumber(fPWeightFilledPercent);
		gbc.gridx = 0;
		gbc.gridy = 3;
		usedBinPanel.add(lWeightFilledPercent, gbc);
		gbc.gridx = 1;
		usedBinPanel.add(fPWeightFilledPercent, gbc);

		final CLabel lVolume = new CLabel(Services.get(IMsgBL.class).translate(Env.getCtx(), MSG_VOLUME));
		lVolume.setLabelFor(fPVolume);
		setupVNumber(fPVolume);
		gbc.gridx = 0;
		gbc.gridy = 4;
		usedBinPanel.add(lVolume, gbc);
		gbc.gridx = 1;
		usedBinPanel.add(fPVolume, gbc);

		final CLabel lVolumeMax = new CLabel(Services.get(IMsgBL.class).translate(Env.getCtx(), MSG_VOLUME_MAX));
		lVolumeMax.setLabelFor(fPVolumeMax);
		setupVNumber(fPVolumeMax);
		gbc.gridx = 0;
		gbc.gridy = 5;
		usedBinPanel.add(lVolumeMax, gbc);
		gbc.gridx = 1;
		usedBinPanel.add(fPVolumeMax, gbc);

		final CLabel lVolumeFilledPercent = new CLabel(Services.get(IMsgBL.class).translate(Env.getCtx(), MSG_VOLUME_PERCENT));
		lVolumeFilledPercent.setLabelFor(fPVolumeFilledPercent);
		setupVNumber(fPVolumeFilledPercent);
		gbc.gridx = 0;
		gbc.gridy = 6;
		usedBinPanel.add(lVolumeFilledPercent, gbc);
		gbc.gridx = 1;
		usedBinPanel.add(fPVolumeFilledPercent, gbc);

		return usedBinPanel;
	}

	private CPanel setupUsedBinsSumPanel()
	{
		final CPanel usedBinsSumPanel = new CPanel();
		usedBinsSumPanel.setLayout(new GridBagLayout());
		usedBinsSumPanel.setBorder(BorderFactory.createEtchedBorder());

		final GridBagConstraints gbc =
				new GridBagConstraints(0, 0, 1, 1, 0, 0,
						GridBagConstraints.WEST,
						GridBagConstraints.NONE,
						new Insets(5, 0, 0, 5),
						0, 0);

		final CLabel lUsedBinsSum = new CLabel(Services.get(IMsgBL.class).translate(Env.getCtx(), MSG_OVERVIEW));
		lUsedBinsSum.setLabelFor(fUsedBinsSum);
		usedBinsSumPanel.add(lUsedBinsSum, gbc);

		final JScrollPane scrollPane =
				new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		fUsedBinsSum.setEditable(false);
		scrollPane.getViewport().add(fUsedBinsSum);
		gbc.gridy = 1;
		usedBinsSumPanel.add(scrollPane, gbc);

		return usedBinsSumPanel;
	}

	public void setUsedBinSummary(final String summary)
	{
		fUsedBinsSum.setText(summary);
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt)
	{
		if (PackingDetailsMd.PACK_VOLUME.equals(evt.getPropertyName()))
		{
			final BigDecimal newVal = (BigDecimal)evt.getNewValue();
			fPVolume.setValue(newVal.setScale(0, BigDecimal.ROUND_HALF_UP));
		}
		else if (PackingDetailsMd.PACK_VOLUME_MAX.equals(evt.getPropertyName()))
		{
			final BigDecimal newVal = (BigDecimal)evt.getNewValue();
			fPVolumeMax.setValue(newVal.setScale(0, BigDecimal.ROUND_HALF_UP));
		}
		else if (PackingDetailsMd.PACK_VOLUME_FILLED_PERCENT.equals(evt.getPropertyName()))
		{
			final BigDecimal newVal = (BigDecimal)evt.getNewValue();
			fPVolumeFilledPercent.setValue(newVal.setScale(1, BigDecimal.ROUND_HALF_UP));
		}
		else if (PackingDetailsMd.PACK_WEIGHT.equals(evt.getPropertyName()))
		{
			final BigDecimal newVal = (BigDecimal)evt.getNewValue();
			fPWeight.setValue(newVal.setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		else if (PackingDetailsMd.PACK_WEIGHT_MAX.equals(evt.getPropertyName()))
		{
			final BigDecimal newVal = (BigDecimal)evt.getNewValue();
			fPWeightMax.setValue(newVal.setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		else if (PackingDetailsMd.PACK_WEIGHT_FILLED_PERCENT.equals(evt.getPropertyName()))
		{
			final BigDecimal newVal = (BigDecimal)evt.getNewValue();
			fPWeightFilledPercent.setValue(newVal.setScale(1, BigDecimal.ROUND_HALF_UP));
		}
		else if (PackingDetailsMd.PACK_PROD.equals(evt.getPropertyName()))
		{
			fPProduct.setValue(evt.getNewValue());
		}
		else if (PackingDetailsMd.PI_LOCATION.equals(evt.getPropertyName()))
		{
			fILocation.setValue(evt.getNewValue());
		}
		else if (PackingDetailsMd.PI_PROD.equals(evt.getPropertyName()))
		{
			fIProduct.setValue(evt.getNewValue());
		}
		else if (PackingDetailsMd.PI_QTY.equals(evt.getPropertyName()))
		{
			final BigDecimal newVal = (BigDecimal)evt.getNewValue();
			fIQty.setValue(newVal.setScale(0, BigDecimal.ROUND_HALF_UP));
		}
		else if (PackingDetailsMd.PI_VOLUME.equals(evt.getPropertyName()))
		{
			final BigDecimal newVal = (BigDecimal)evt.getNewValue();
			fIVolume.setValue(newVal.setScale(0, BigDecimal.ROUND_HALF_UP));
		}
		else if (PackingDetailsMd.PI_WEIGHT.equals(evt.getPropertyName()))
		{
			final BigDecimal newVal = (BigDecimal)evt.getNewValue();
			if (!newVal.equals(fIWeight.getValue()))
			{
				// only set the value, if there is a change..
				fIWeight.setValue(newVal.setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			// indicate a problem if weight is 0
			fIWeight.setBackground(newVal.signum() <= 0);
		}
		else if (PackingDetailsMd.PI_WEIGHT_EDITABLE.equals(evt.getPropertyName()))
		{
			final Boolean newVal = (Boolean)evt.getNewValue();
			fIWeight.setReadWrite(newVal);
		}
		else if (PackingDetailsMd.PI_OL_PROD_DESC.equals(evt.getPropertyName()))
		{
			final String newVal = (String)evt.getNewValue();
			fIOlProdDesc.setValue(newVal);
		}
	}

	/**
	 * 
	 * @param tabIdx
	 *            one of {@link #TAB_IDX_PACK} or {@link #TAB_IDX_PL}.
	 */
	public void showDetailsTab(final String tabName)
	{
		detailsLayout.show(rightLowerPanel, tabName);
	}

	public void showNode(final TreePath newPath)
	{
		tree.expandPath(newPath);
	}
}
