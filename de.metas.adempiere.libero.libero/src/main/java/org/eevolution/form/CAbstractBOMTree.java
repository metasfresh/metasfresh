/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved. *
 * Contributor(s): Victor Perez www.e-evolution.com *
 *****************************************************************************/

package org.eevolution.form;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JEditorPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.compiere.apps.form.FormFrame;
import org.compiere.grid.ed.VLookup;
import org.compiere.model.I_M_Product;
import org.compiere.model.MColumn;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.form.action.PopupAction;
import org.eevolution.form.action.ZoomMenuAction;
import org.eevolution.form.bom.BOMTreeFactory;
import org.eevolution.form.bom.BOMTreeModel;
import org.eevolution.form.bom.action.ChangeASIAction;
import org.eevolution.form.bom.action.CreateRfQAction;
import org.eevolution.form.bom.action.DeleteBOMAction;
import org.eevolution.form.bom.action.MergeBOMAction;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.reasoner.StorageReasoner;
import org.eevolution.model.wrapper.BOMLineWrapper;
import org.eevolution.model.wrapper.BOMWrapper;
import org.eevolution.tools.swing.SwingTool;
import org.eevolution.tools.worker.SingleWorker;

import de.metas.i18n.Msg;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.product.IProductDAO;
import de.metas.util.Services;

/**
 * @author Gunther Hoppe, tranSIT GmbH Ilmenau/Germany
 * @version 1.0, October 14th 2005
 */
@SuppressWarnings("all") // tsa: to many warnings in a code that we don't use. Suppress all to reduce noise.
public abstract class CAbstractBOMTree extends CAbstractForm implements PropertyChangeListener
{

	class TreeHandler extends MouseInputAdapter implements TreeSelectionListener, KeyListener
	{

		@Override
		public void mouseClicked(MouseEvent e)
		{

			if (model.getTree().getPathForLocation(e.getX(), e.getY()) == null)
			{

				return;
			}

			SwingTool.setCursorsFromChild(e.getComponent(), true);

			final MouseEvent evt = e;
			worker = new SingleWorker()
			{

				@Override
				protected Object doIt()
				{

					handleTreeEvent(evt);
					return null;
				}
			};

			worker.start();
		}

		@Override
		public void mouseMoved(MouseEvent e)
		{

			// m_tree.setToolTipText(msg.getToolTipText(e));
		}

		@Override
		public void keyPressed(KeyEvent e)
		{
		}

		@Override
		public void keyReleased(KeyEvent e)
		{

			if (e.getKeyCode() == 38 || e.getKeyCode() == 40)
			{

				TreePath path = model.getTree().getSelectionModel().getSelectionPath();

				String text = model.getBOMMessenger().getToolTipText(path);
				if (text != null)
				{

					nodeDescription.setText(text);
				}
			}
		}

		@Override
		public void keyTyped(KeyEvent e)
		{
		}

		@Override
		public void valueChanged(TreeSelectionEvent event)
		{
		}
	}

	private void handleTreeEvent(MouseEvent e)
	{

		String text = model.getBOMMessenger().getToolTipText(e);
		if (text != null)
		{

			nodeDescription.setText(text);
		}

		if (e.getButton() == MouseEvent.BUTTON3)
		{

			model.getTree().setSelectionPath(model.getTree().getPathForLocation(e.getX(), e.getY()));

			DefaultMutableTreeNode node = (DefaultMutableTreeNode)model.getTree().getSelectionPath().getLastPathComponent();

			if (node.getUserObject() instanceof BOMLineWrapper)
			{

				popupBOMLine.show(e.getComponent(), e.getX(), e.getY());
			}
			else if (node.getUserObject() instanceof BOMWrapper)
			{

				popupBOM.show(e.getComponent(), e.getX(), e.getY());
			}
			else if (node.getUserObject() instanceof I_M_Product)
			{

				popupRoot.show(e.getComponent(), e.getX(), e.getY());
			}
			else if (node.getUserObject() instanceof I_PP_Order)
			{
				popupRoot.show(e.getComponent(), e.getX(), e.getY());
			}
		}

		SwingTool.setCursorsFromChild(e.getComponent(), false);
	}

	public CAbstractBOMTree()
	{

		super();
	}

	private SingleWorker worker;
	private BOMTreeModel model;
	private CPanel northPanel;
	private VLookup lookup;
	private JSplitPane contentPane;
	private CPanel southPanel;
	private JEditorPane nodeDescription;

	protected JPopupMenu popupRoot;
	protected JPopupMenu popupBOM;
	protected JPopupMenu popupBOMLine;
	protected StorageReasoner reasoner;

	protected abstract String type();

	protected String idColumn()
	{

		return type() + "_ID";
	}

	@Override
	public void init(int WindowNo, FormFrame frame)
	{

		super.init(WindowNo, frame);

		reasoner = new StorageReasoner();

		try
		{

			preInit();
			jbInit();

			getWindow().getContentPane().add(this, BorderLayout.CENTER);
		}
		catch (Exception ex)
		{

			ex.printStackTrace();
		}
	}

	private void preInit()
	{

		northPanel = new CPanel();
		southPanel = new CPanel();
		contentPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		nodeDescription = new JEditorPane("text/html", "");
		nodeDescription.setOpaque(false);
		nodeDescription.setEditable(false);

		String columnName = null;
		int columnId = -1;
		if (BOMWrapper.BOM_TYPE_PRODUCT.equals(type()))
		{

			columnName = I_M_Product.COLUMNNAME_M_Product_ID;
			columnId = MColumn.getColumn_ID(I_M_Product.Table_Name, columnName);
		}
		else if (BOMWrapper.BOM_TYPE_ORDER.equals(type()))
		{

			columnName = I_PP_Order.COLUMNNAME_PP_Order_ID;
			columnId = MColumn.getColumn_ID(I_PP_Order.Table_Name, columnName);

		}

		MLookup lm = MLookupFactory.get(Env.getCtx(), getWindowNo(), 0, columnId, DisplayType.Search);
		lookup = new VLookup(columnName, false, false, true, lm)
		{

			@Override
			public void setValue(Object obj)
			{

				super.setValue(obj);
				dispatchPropertyChange();
			};
		};
	}

	private void jbInit()
	{

		CLabel label = null;
		if (BOMWrapper.BOM_TYPE_PRODUCT.equals(type()))
		{

			label = new CLabel(Msg.translate(Env.getCtx(), I_M_Product.COLUMNNAME_M_Product_ID));
		}
		else if (BOMWrapper.BOM_TYPE_ORDER.equals(type()))
		{

			label = new CLabel(Msg.translate(Env.getCtx(), I_PP_Order.COLUMNNAME_PP_Order_ID));
		}
		label.setLabelFor(lookup);

		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		northPanel.add(label, null);
		northPanel.add(lookup, null);
		southPanel.setLayout(new BorderLayout());

		JScrollPane sp = new JScrollPane(nodeDescription);
		sp.setBorder(null);
		contentPane.add(sp, JSplitPane.RIGHT);

		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(1000, 600));
		this.add(northPanel, BorderLayout.NORTH);
		this.add(contentPane, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
	}

	@Override
	public void dispose()
	{

		super.dispose();

		if (worker != null)
		{

			worker.stop();
		}
		worker = null;

		if (lookup != null)
		{

			lookup.dispose();
		}
		lookup = null;

		northPanel = null;
		contentPane = null;
		southPanel = null;
		nodeDescription = null;
		popupRoot = null;
		popupBOM = null;
		popupBOMLine = null;
		reasoner = null;
	}

	private void handleActionEvent()
	{

		final int id = lookup.getValueAsInt();
		if (id <= 0)
		{
			return;
		}

		nodeDescription.setText("");

		Object model = null;
		if (BOMWrapper.BOM_TYPE_PRODUCT.equals(type()))
		{
			model = Services.get(IProductDAO.class).getById(id);
		}
		else if (BOMWrapper.BOM_TYPE_ORDER.equals(type()))
		{
			model = Services.get(IPPOrderDAO.class).getById(PPOrderId.ofRepoId(id));
		}

		final BOMTreeModel treeModel = BOMTreeFactory.get(type(), model, reasoner);

		configureTree();

		contentPane.add(new JScrollPane(treeModel.getTree()), JSplitPane.LEFT);
		contentPane.setDividerLocation(0.25d);
	}

	protected void configureTree()
	{

		model.getTree().addPropertyChangeListener(this);

		TreeHandler th = new TreeHandler();
		model.getTree().addMouseMotionListener(th);
		model.getTree().addMouseListener(th);
		model.getTree().addKeyListener(th);
		model.getTree().addTreeSelectionListener(th);

		popupRoot = new JPopupMenu();
		popupBOM = new JPopupMenu();
		popupBOMLine = new JPopupMenu();

		try
		{

			configurePopup(popupRoot, model.getTree(), I_M_Product.class);
			configurePopup(popupBOM, model.getTree(), BOMWrapper.class);
			configurePopup(popupBOMLine, model.getTree(), BOMLineWrapper.class);
		}
		catch (Exception e)
		{

			e.printStackTrace();
		}
	}

	protected void configurePopup(JPopupMenu popup, JTree tree, Class clazz) throws Exception
	{

		PopupAction action = null;

		// Context menu items for manufacturing order or product
		if (I_PP_Order.class.isAssignableFrom(clazz) || I_M_Product.class.isAssignableFrom(clazz))
		{

			action = new ZoomMenuAction(tree);
			popup.add(action);
		}
		// Context menu items for BOM header
		else if (BOMWrapper.class.equals(clazz))
		{

			// Delete, merge and zoom action only for product BOM type available
			if (BOMWrapper.BOM_TYPE_PRODUCT.equals(type()))
			{

				action = new ZoomMenuAction(tree);
				popup.add(action);

				action = new MergeBOMAction(tree);
				action.addPropertyChangeListener(this);
				popup.add(action);

				action = new DeleteBOMAction(tree);
				action.addPropertyChangeListener(this);
				popup.add(action);
			}

			// CreateRFQ action only for order BOM type available
			if (BOMWrapper.BOM_TYPE_ORDER.equals(type()))
			{

				action = new CreateRfQAction(tree, getWindow());
				action.addPropertyChangeListener(this);
				popup.add(action);
			}
		}
		// Context menu items for BOM line
		else if (BOMLineWrapper.class.equals(clazz))
		{

			// Delete action only for product BOM type available
			if (BOMWrapper.BOM_TYPE_PRODUCT.equals(type()))
			{

				action = new DeleteBOMAction(tree);
				action.addPropertyChangeListener(this);
				popup.add(action);
			}

			action = new ChangeASIAction(tree, getWindow());
			action.addPropertyChangeListener(this);
			popup.add(action);
		}
	}

	protected void dispatchPropertyChange()
	{

		PropertyChangeEvent evt = new PropertyChangeEvent(lookup, ChangeASIAction.COMMAND, null, null);
		propertyChange(evt);
	}

	@Override
	public void propertyChange(PropertyChangeEvent e)
	{

		if (MergeBOMAction.COMMAND.equals(e.getPropertyName())
				|| ChangeASIAction.COMMAND.equals(e.getPropertyName())
				|| DeleteBOMAction.COMMAND.equals(e.getPropertyName()))
		{

			SwingTool.setCursorsFromParent(getWindow(), true);
			SingleWorker worker = new SingleWorker()
			{

				@Override
				protected Object doIt()
				{

					handleActionEvent();
					SwingTool.setCursorsFromParent(getWindow(), false);
					return null;
				}
			};

			runWorker(worker);
		}
	}

	protected void runWorker(SingleWorker worker)
	{

		this.worker = worker;
		this.worker.start();
	}
}
