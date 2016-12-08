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


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.tree.DefaultMutableTreeNode;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.apps.form.FormFrame;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.form.IPackingDetailsModel;
import de.metas.adempiere.form.PackingItemsMap;
import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalBasePanel;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.ITerminalKeyListener;
import de.metas.adempiere.form.terminal.ITerminalKeyPanel;
import de.metas.adempiere.form.terminal.ITerminalSplitPane;
import de.metas.adempiere.form.terminal.TerminalKeyListenerAdapter;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.swing.SwingTerminalFactory;
import de.metas.adempiere.form.terminal.swing.TerminalSplitPane;
import de.metas.logging.LogManager;
import de.metas.process.ProcessExecutor;
import net.miginfocom.swing.MigLayout;

/**
 * Packing window panel (second window)
 *
 * @author cg
 *
 */
public abstract class AbstractPackageTerminalPanel implements ITerminalBasePanel, PropertyChangeListener
{
	protected final transient Logger log = LogManager.getLogger(getClass());

	private final ITerminalContext tc;
	private final AbstractPackageTerminal parentPackageTerminal;

	/**
	 * {@link ITerminalKeyListener} implementation which forwards all events to {@link #keyPressed(ITerminalKey)}.
	 */
	protected final ITerminalKeyListener terminalKeyListener2keyPressed = new TerminalKeyListenerAdapter()
	{
		@Override
		public void keyReturned(ITerminalKey key)
		{
			keyPressed(key);
		}
	};

	protected final IContainer panel;
	protected final IContainer panelCenter;
	protected ITerminalSplitPane split;

	private FormFrame frame;
	private final SwingPackageBoxesItems productKeysPanel;
	private final AbstractPackageDataPanel pickingData;
	private final ITerminalKeyPanel packingMaterialsPanel;

	private PackingItemsMap packItems;
	private Map<Integer, DefaultMutableTreeNode> boxes;
	private List<DefaultMutableTreeNode> availBoxes;

	private final IPackingDetailsModel model;

	/**
	 * create Package Terminal with link to the parent - Package terminal
	 *
	 * @param parent
	 */
	public AbstractPackageTerminalPanel(final ITerminalContext terminalContext, final AbstractPackageTerminal parent)
	{
		super();

		Check.assumeNotNull(terminalContext, "terminalContext not null");
		this.tc = terminalContext;
		final ITerminalFactory factory = tc.getTerminalFactory();

		this.parentPackageTerminal = parent;
		this.model = parent.getPackingDetailsModel();

		this.packItems = parent.getPackingItems();
		this.boxes = parent.getBoxes();
		this.availBoxes = parent.getAvailableBoxes();
		this.panel = factory.createContainer();
		this.panelCenter = factory.createContainer();

		// NOTE: init order is important because of our fucked-up architecture. This order was tested on only.
		this.productKeysPanel = createProductKeysPanel();
		this.pickingData = createPackageDataPanel();
		this.packingMaterialsPanel = createPackingMaterialsKeyLayoutPanel();

		SwingTerminalFactory.getUI(panel).setLayout(new MigLayout("ins 5 5 5 5", // Layout Constraints
				"shrink 10, grow", // Column constraints
				"grow, shrink 10"));

		SwingTerminalFactory.getUI(panelCenter).setLayout(new MigLayout("ins 5 5 5 5", // Layout Constraints
				"[grow][shrink 10]", // Column constraints
				"[grow][shrink 10]"));

		terminalContext.addToDisposableComponents(this);
	}

	public List<DefaultMutableTreeNode> getAvailBoxes()
	{
		return availBoxes;
	}

	public PackingItemsMap getPackItems()
	{
		return packItems;
	}

	public void setPackItems(final PackingItemsMap items)
	{
		packItems = items;
	}

	public Map<Integer, DefaultMutableTreeNode> getBoxes()
	{
		return boxes;
	}

	/**
	 * get panel Package dat panel is the panel which contains infos about the product, partner, the left, middle panel
	 *
	 * @return
	 */
	public AbstractPackageDataPanel getPickingData()
	{
		return pickingData;
	}

	protected abstract AbstractPackageDataPanel createPackageDataPanel();

	public void setFrameVisible(boolean visible)
	{
		if (frame != null)
		{
			frame.setVisible(visible);
		}
		if (getParent().getPickingFrame() != null)
		{
			getParent().getPickingFrame().setVisible(visible);
		}
	}

	protected IKeyLayout getPackingMaterialsKeyLayout()
	{
		final ITerminalKeyPanel panel = getPackingMaterialsKeyLayoutPanel();
		if (panel == null)
		{
			return null;
		}
		return panel.getKeyLayout();
	}

	protected ITerminalKeyPanel getPackingMaterialsKeyLayoutPanel()
	{
		return packingMaterialsPanel;
	}

	private final ITerminalKeyPanel createPackingMaterialsKeyLayoutPanel()
	{
		final IKeyLayout packingMaterialsLayout = createPackingMaterialsKeyLayout();
		packingMaterialsLayout.setBasePanel(this);
		getTerminalContext().addToDisposableComponents(packingMaterialsLayout);

		final ITerminalKeyPanel packingMaterialsLayoutPanel = getTerminalFactory()
				.createTerminalKeyPanel(packingMaterialsLayout, terminalKeyListener2keyPressed);

		return packingMaterialsLayoutPanel;
	}

	/**
	 * Note: the result is added to the terminal context's disposable components by the caller.
	 *
	 * @return
	 */
	protected abstract IKeyLayout createPackingMaterialsKeyLayout();

	/**
	 * retrieve the panel which contains the boxes and the products
	 *
	 * @return
	 */
	public SwingPackageBoxesItems getProductKeysPanel()
	{
		return productKeysPanel;
	}

	protected abstract SwingPackageBoxesItems createProductKeysPanel();

	/**
	 * @return Packing window (second window)
	 */
	public AbstractPackageTerminal getParent()
	{
		return parentPackageTerminal;
	}

	private void updateBoxes()
	{
		packItems = parentPackageTerminal.getPackingItems();
		boxes = parentPackageTerminal.getBoxes();
		availBoxes = parentPackageTerminal.getAvailableBoxes();
	}

	public IPackingDetailsModel getModel()
	{
		return model;
	}

	public void init(final int windowNo, final FormFrame frame)
	{
		this.frame = frame;
		frame.setMinimumSize(new Dimension(1024, 760));
		frame.setMaximumSize(new Dimension(1024, 760));	// cg: maximum size should be 1024x768 : see task 03520
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setJMenuBar(null);

		frame.setTitle(Services.get(IMsgBL.class).getMsg(getTerminalContext().getCtx(), "PackageTerminal"));

		try
		{
			dynInit();
			frame.getContentPane().add(SwingTerminalFactory.getUI(panel), BorderLayout.CENTER);
		}
		catch (Exception e)
		{
			log.warn("init", e);
			dispose();
			return;
		}
	}

	@Override
	public final boolean isDisposed()
	{
		return disposing || disposed;
	}

	@Override
	public final void dispose()
	{
		if (disposing || disposed)
		{
			return;
		}

		disposing = true;
		try
		{
			if (frame != null)
			{
				frame.dispose();
				frame = null;
			}
		}
		finally
		{
			disposing = false;
			disposed = true;
		}
	}

	private boolean disposing = false;
	private boolean disposed = false;

	protected abstract void dynInit();

	private void refreshPackingMaterialsKeyLayout()
	{
		IKeyLayout newKartonLayout = getPackingMaterialsKeyLayout();
		ITerminalKeyPanel newKartoPanel = getPackingMaterialsKeyLayoutPanel();
		//
		newKartonLayout.resetKeys();
		//
		newKartoPanel.removeLayout(newKartonLayout.getId());
		newKartoPanel.addPOSKeyLayout(newKartonLayout);
		newKartoPanel.showPOSKeyKayout(newKartonLayout);
	}

	@Override
	public Properties getCtx()
	{
		return Env.getCtx();
	}

	@Override
	public boolean isProcessed()
	{
		return false;
	}

	@Override
	public void logout()
	{
		getParent().getPickingOKPanel().logout();
	}

	@Override
	public void updateInfo()
	{
		// nothing to do
	}

	@Override
	public Object getComponent()
	{
		return frame;
	}

	@Override
	public ITerminalFactory getTerminalFactory()
	{
		return tc.getTerminalFactory();
	}

	@Override
	public void add(IComponent component, Object constraints)
	{
		SwingTerminalFactory.addChild(panel, component, constraints);
	}

	@Override
	public void addAfter(IComponent component, IComponent componentBefore, Object constraints)
	{
		SwingTerminalFactory.addChildAfter(panel, component, componentBefore, constraints);
	}

	@Override
	public void remove(IComponent component)
	{
		SwingTerminalFactory.removeChild(panel, component);
	}

	@Override
	public void removeAll()
	{
		panel.removeAll();
	}

	@Override
	abstract public void keyPressed(ITerminalKey key);

	/**
	 * reset new kartons, boxes, products
	 *
	 * @param resetNewKartons
	 * @param resetBoxes
	 * @param resetProducts
	 */
	public void refresh(boolean resetNewKartons, boolean resetBoxes, boolean resetProducts)
	{
		getParent().createBoxes(model);
		updateBoxes();
		productKeysPanel.refresh(resetProducts, resetBoxes);

		if (resetNewKartons)
		{
			refreshPackingMaterialsKeyLayout();
		}
	}

	@Override
	public ITerminalContext getTerminalContext()
	{
		return this.tc;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		if (evt.getPropertyName().equals("dividerLocation") && !evt.getNewValue().equals(new Integer(1)))
		{
			if (evt.getNewValue().equals(new Integer(0)))
			{
				((TerminalSplitPane)split).setDividerLocation(0);
			}
			else
			{
				((TerminalSplitPane)split).setDividerLocation(0.25);
			}
		}
	}

	public ProcessExecutor processPackingDetails()
	{
		// TODO: drop it - https://github.com/metasfresh/metasfresh/issues/456
		// NOTE assume this is not called
		throw new UnsupportedOperationException();
//
//		final SwingPickingOKPanel pickingOKPanel = (SwingPickingOKPanel)getParent().getPickingOKPanel();
//		return pickingOKPanel.invokeProcess(this.model);
	}
}
