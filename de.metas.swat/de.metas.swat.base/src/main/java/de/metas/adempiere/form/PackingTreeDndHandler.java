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


import java.awt.Dimension;
import java.awt.Point;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29'>(2009 0022 G61)</a>"
 */
final class PackingTreeDndHandler extends DropTargetAdapter
{

	private static final Logger logger = LogManager.getLogger(PackingTreeDndHandler.class);

	private final JTree tree;

	private final Set<IPackingTreeListener> packingTreeListeners = new HashSet<IPackingTreeListener>();

	public PackingTreeDndHandler(final JTree tree)
	{
		this.tree = tree;
	}

	public void addCommissiontreeListener(final IPackingTreeListener l)
	{

		packingTreeListeners.add(l);
	}

	/**
	 * Figure out what the action should do with the tree and whether it is legal. If it was, and we need an additional
	 * quantity parameter, display a pop-up with a text field. Finally notify the registered
	 * {@link IPackingTreeListener} instances.
	 * 
	 */
	public void drop(final DropTargetDropEvent dtde)
	{

		final TreePath selectionPath = tree.getSelectionPath();
		final TreePath sourcePath = selectionPath.getParentPath();

		final DefaultMutableTreeNode movingNode = (DefaultMutableTreeNode)selectionPath.getLastPathComponent();

		final Point dropLocation = dtde.getLocation();
		final TreePath targetPath = tree.getClosestPathForLocation(dropLocation.x, dropLocation.y);

		final DefaultMutableTreeNode oldParent = (DefaultMutableTreeNode)sourcePath.getLastPathComponent();
		final DefaultMutableTreeNode newParent = (DefaultMutableTreeNode)targetPath.getLastPathComponent();

		final PackingTreeModel treeModel = (PackingTreeModel)tree.getModel();

		final Point position = dtde.getLocation();

		if (logger.isDebugEnabled())
		{
			logger.debug("movingNode=" + movingNode + ", oldParent=" + oldParent + ", newParent=" + newParent);
		}

		if (oldParent == treeModel.getAvailableBins())
		{

			if (newParent == treeModel.getUsedBins())
			{

				final QtyEnteredHandler handler = new QtyEnteredHandler()
				{
					@Override
					public void fire(final BigDecimal qty)
					{
						fireUsedBinAdded(movingNode, qty.intValue());
						finishDrop(dtde, true);
					}
				};
				showPopup(position, handler, 1);
			}

		}
		else if (oldParent == treeModel.getUsedBins())
		{

			if (newParent == treeModel.getAvailableBins())
			{

				fireUsedBinRemoved(movingNode);
				finishDrop(dtde, true);
			}

		}
		else if (oldParent == treeModel.getUnPackedItems())
		{

			if (newParent.getUserObject() instanceof UsedBin)
			{

				final QtyEnteredHandler handler = new QtyEnteredHandler()
				{
					@Override
					public void fire(final BigDecimal qty)
					{
						firePackedItemAdded(movingNode, newParent, qty);
						finishDrop(dtde, true);
					}
				};
				showPopup(position, handler, 1);
			}
		}

		else if (oldParent.getUserObject() instanceof UsedBin)
		{

			if (newParent == treeModel.getUnPackedItems())
			{

				final LegacyPackingItem pi = (LegacyPackingItem)movingNode.getUserObject();

				final QtyEnteredHandler handler = new QtyEnteredHandler()
				{
					@Override
					public void fire(final BigDecimal qty)
					{
						firePackedItemRemoved(movingNode, oldParent, qty);
						finishDrop(dtde, true);
					}
				};
				showPopup(position, handler, pi.getQtySum().intValue());

			}
			else if (newParent.getUserObject() instanceof UsedBin)
			{

				final LegacyPackingItem pi = (LegacyPackingItem)movingNode.getUserObject();

				final QtyEnteredHandler handler = new QtyEnteredHandler()
				{
					@Override
					public void fire(final BigDecimal qty)
					{
						firePackedItemMoved(movingNode, oldParent, newParent, qty);
						finishDrop(dtde, true);
					}
				};
				showPopup(position, handler, pi.getQtySum().intValue());
			}
		}
		finishDrop(dtde, false);
	}

	private void showPopup(final Point position, final QtyEnteredHandler handler, final int suggestion)
	{

		final JPanel panel = new JPanel();

		final JLabel label = new JLabel(Msg.translate(Env.getCtx(), "Qty") + ": ");
		panel.add(label);

		final JTextField textField = new JTextField(Integer.toString(suggestion));
		textField.setPreferredSize(new Dimension(50, textField.getPreferredSize().height));
		panel.add(textField);
		textField.addActionListener(handler);

		final JPopupMenu popup = new JPopupMenu();
		handler.setPopup(popup);
		popup.add(panel);
		popup.show(tree, position.x, position.x);

		textField.requestFocus();
	}

	private void finishDrop(final DropTargetDropEvent dtde, final boolean success)
	{

		if (success)
		{
			dtde.acceptDrop(dtde.getDropAction());
			dtde.dropComplete(true);
		}
		else
		{
			dtde.rejectDrop();
			dtde.dropComplete(false);
		}
	}

	private void fireUsedBinAdded(DefaultMutableTreeNode availbinNode, int qty)
	{

		for (final IPackingTreeListener l : packingTreeListeners)
		{
			l.usedBinAdded(availbinNode, qty);
		}
	}

	private void fireUsedBinRemoved(DefaultMutableTreeNode usedBinNode)
	{

		for (final IPackingTreeListener l : packingTreeListeners)
		{
			l.usedBinRemoved(usedBinNode);
		}
	}

	private void firePackedItemRemoved(DefaultMutableTreeNode packedItemNode, DefaultMutableTreeNode oldUsedBin,
			final BigDecimal qty)
	{

		for (final IPackingTreeListener l : packingTreeListeners)
		{
			l.packedItemRemoved(packedItemNode, oldUsedBin, qty);
		}
	}

	private void firePackedItemAdded(DefaultMutableTreeNode packedItemNode, DefaultMutableTreeNode newUsedBin,
			final BigDecimal qty)
	{

		for (final IPackingTreeListener l : packingTreeListeners)
		{
			l.packedItemAdded(packedItemNode, newUsedBin, qty);
		}
	}

	private void firePackedItemMoved(DefaultMutableTreeNode packedItemNode, DefaultMutableTreeNode oldUsedBin,
			DefaultMutableTreeNode newUsedBin, BigDecimal qty)
	{

		for (final IPackingTreeListener l : packingTreeListeners)
		{
			l.packedItemMoved(packedItemNode, oldUsedBin, newUsedBin, qty);
		}
	}

	/**
	 * Action listener for the pop-up's text field
	 * 
	 * @author ts
	 * 
	 */
	private static abstract class QtyEnteredHandler implements ActionListener
	{

		private JPopupMenu popup;

		private void setPopup(JPopupMenu popup)
		{

			this.popup = popup;
		}

		public void actionPerformed(final ActionEvent e)
		{

			final JTextField textField = (JTextField)e.getSource();
			final String text = textField.getText();
			try
			{

				final int qty = Integer.parseInt(text);
				fire(new BigDecimal(qty));
				popup.setVisible(false);

			}
			catch (NumberFormatException ex)
			{
				logger.info("Invalid input: " + text);
				textField.setText("");
			}
		}

		/**
		 * Implementors notify the registered {@link IPackingTreeListener}s by invoking the appropriate fire* method.
		 * 
		 * @param qty
		 *            the quantity parameter we got from the text field.
		 */
		abstract void fire(BigDecimal qty);
	}
}
