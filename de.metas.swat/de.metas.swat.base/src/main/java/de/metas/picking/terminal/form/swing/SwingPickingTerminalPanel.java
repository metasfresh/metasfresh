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
import java.awt.CardLayout;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JFrame;

import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.apps.form.FormFrame;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.form.PackingMd;
import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IConfirmPanel;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.ITerminalKeyPanel;
import de.metas.adempiere.form.terminal.ITerminalScrollPane;
import de.metas.adempiere.form.terminal.ITerminalTextPane;
import de.metas.adempiere.form.terminal.POSKeyLayout;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.TerminalKeyListenerAdapter;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.swing.SwingTerminalFactory;
import de.metas.logging.LogManager;
import de.metas.picking.terminal.PickingOKPanel;
import de.metas.picking.terminal.PickingTerminalPanel;

/**
 * Picking First Window Panel.
 *
 * Contains:
 * <ul>
 * <li>Warehouse Keys
 * <li>maybe other filtering keys (that will be added from extending classes)
 * <li>actual picking panel ( {@link SwingPickingOKPanel} )
 * <li>confirm panel (bottom)
 * </ul>
 *
 * @author cg
 *
 */
public class SwingPickingTerminalPanel extends PickingTerminalPanel
{
	protected final transient Logger log = LogManager.getLogger(getClass());

	public static final String CARDNAME_WAREHOUSE_PICKING = "WAREHOUSE_PICKING";
	public static final String CARDNAME_RESULT = "RESULT";

	private final IContainer panel;
	private final CardLayout cardLayout;

	private FormFrame frame;
	private ITerminalKeyPanel warehousePanel;
	public ITerminalTextPane resultTextPane;
	private IConfirmPanel confirmPanel;
	protected final String ACTION_Exit = "Logout";

	public SwingPickingTerminalPanel()
	{
		super();

		final IContainer panel = getTerminalFactory().createContainer();

		final Container panelSwing = SwingTerminalFactory.getUI(panel);
		cardLayout = new CardLayout();
		panelSwing.setLayout(cardLayout);

		this.panel = panel;
	}

	public void init(int windowNo, FormFrame frame)
	{
		final ITerminalContext tc = getTerminalContext();
		tc.setWindowNo(windowNo);

		frame.setJMenuBar(null);
		setFrame(frame);
		try
		{
			doLogin();

			// If user choose to quit from Login panel, then we need to stop right away
			if (isDisposed())
			{
				return;
			}

			initComponents();
			initLayout();

			this.frame.getContentPane().add(SwingTerminalFactory.getUI(panel), BorderLayout.CENTER);

			//
			// Init default values, set default focus etc
			// NOTE: running this method after adding the panel to frame because else the focus requests won't be set
			initDefaults();
		}
		catch (final Exception e)
		{
			log.warn("init", e);
			final ITerminalFactory factory = getTerminalFactory();
			factory.showWarning(this, ITerminalFactory.TITLE_ERROR, new TerminalException(e));

			dispose();
			if (this.frame != null)
			{
				this.frame.dispose();
			}
			return;
		}
	}

	@Override
	public void dispose()
	{
		if (frame != null)
		{
			frame.dispose();
			frame = null;
		}

		super.dispose();
	}

	protected PickingOKPanel createPickingOKPanel()
	{
		final SwingPickingOKPanel pickingPanel = new SwingPickingOKPanel(this);
		return pickingPanel;
	}

	protected ITerminalKeyPanel getWarehouseKeyPanel()
	{
		return warehousePanel;
	}

	/**
	 * Creates and configures UI components.
	 */
	protected void initComponents() throws Exception
	{
		final String frameTitle = Services.get(IMsgBL.class).getMsg(Env.getCtx(), TITLE_PACKAGE_TERMINAL);
		frame.setTitle(frameTitle);

		//
		// Card 1: Warehouse Picking
		{
			//
			final IKeyLayout warehouseKeyLayout = createWarehouseKeyLayout();
			warehousePanel = getTerminalFactory().createTerminalKeyPanel(warehouseKeyLayout, new WarehouseKeyListener());
			warehousePanel.setAllowKeySelection(true);

			//
			final PickingOKPanel pickingOKPanel = createPickingOKPanel();
			setPickingOKPanel(pickingOKPanel);
		}

		//
		// card 2
		{
			resultTextPane = getTerminalFactory().createTextPane("");
			//
			confirmPanel = getTerminalFactory().createConfirmPanel(true, "");
			confirmPanel.addButton(ACTION_Exit);
			confirmPanel.addListener(new ConfirmPanelListener());
		}
	}

	/**
	 * Creates and configures the Warehouse Key Layout
	 *
	 * @return warehouse key layout
	 */
	protected IKeyLayout createWarehouseKeyLayout()
	{
		final IKeyLayout warehouseKeyLayout = new POSKeyLayout(getTerminalContext(), 540003); // TODO hard coded "Warehouse Groups"
		warehouseKeyLayout.setRows(2);
		return warehouseKeyLayout;
	}

	/**
	 * Setup UI layout (i.e. add components to panels)
	 */
	private void initLayout()
	{
		//
		// Card 1: Warehouse Picking
		initLayout_WarehousePicking();

		//
		// card 2
		initLayout_Result();

		// show card
		showCard(CARDNAME_WAREHOUSE_PICKING);
	}

	/**
	 * Layout for warehouse picking panel (Card 1).
	 *
	 * @see #CARDNAME_WAREHOUSE_PICKING
	 */
	protected void initLayout_WarehousePicking()
	{
		final IContainer container = getTerminalFactory().createContainer("fill, ins 0 0");
		createPanel(container, warehousePanel, "dock north, growx,hmin 30%");
		createPanel(container, getPickingOKPanel().getComponent(), "dock south, growx");
		add(container, CARDNAME_WAREHOUSE_PICKING, "dock north, growx");
	}

	/**
	 * Layout for result panel (Card 2).
	 *
	 * @see #CARDNAME_RESULT
	 */
	protected void initLayout_Result()
	{
		final ITerminalScrollPane textPaneScroll = getTerminalFactory().createScrollPane(resultTextPane);
		final IContainer container = getTerminalFactory().createListContainer();
		createPanel(container, textPaneScroll, "dock north, hmin 70%");
		createPanel(container, confirmPanel, "dock south, hmax 30%");
		//
		add(container, CARDNAME_RESULT, "dock north, , growx, growy");
	}

	/**
	 * Setup default component values (i.e. select default warehouse etc)
	 */
	protected void initDefaults()
	{
		//
		// Select by default first warehouse
		final List<ITerminalKey> warehouseKeys = warehousePanel.getKeyLayout().getKeys();
		if (!warehouseKeys.isEmpty())
		{
			final ITerminalKey warehouseKey = warehouseKeys.get(0);
			warehousePanel.fireKeyPressed(warehouseKey.getId());
		}
	}

	protected final IComponent createPanel(IContainer content, IComponent component, Object constraints)
	{
		content.add(component, constraints);

		ITerminalScrollPane scroll = getTerminalFactory().createScrollPane(content);
		scroll.setUnitIncrementVSB(16);
		IContainer card = getTerminalFactory().createContainer();
		card.add(scroll, "growx, growy");
		return card;
	}

	/**
	 * Displays given panel card.
	 *
	 * @param cardName card name (see CARDNAME_* constants)
	 */
	private final void showCard(final String cardName)
	{
		final Container panelComp = SwingTerminalFactory.getUI(panel);
		cardLayout.show(panelComp, cardName);
	}

	public void next()
	{
		final Container panelComp = SwingTerminalFactory.getUI(panel);
		cardLayout.next(panelComp);
	}

	@Override
	protected final void setFrame(final Object frame)
	{
		this.frame = (FormFrame)frame;
		this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.frame.setResizable(false);
		this.frame.setJMenuBar(null);
	}

	@Override
	public final void setFrameVisible(final boolean visible)
	{
		if (frame != null)
		{
			frame.setVisible(visible);
		}
		else
		{
			return;
		}

		final PickingOKPanel pickingOKPanel = getPickingOKPanel();
		if (pickingOKPanel != null)
		{
			if (pickingOKPanel.getPackageFrame() != null)
			{
				((FormFrame)pickingOKPanel.getPackageFrame()).setVisible(visible);
			}
		}
	}

	protected final void add(IComponent component, String name, Object constraints)
	{
		SwingTerminalFactory.addChild(panel, component, name, constraints);
	}

	@Override
	public final FormFrame getComponent()
	{
		return this.frame;
	}

	private class WarehouseKeyListener extends TerminalKeyListenerAdapter
	{

		@Override
		public void keyReturned(final ITerminalKey key)
		{
			if (I_M_Warehouse.Table_Name.equals(key.getTableName()))
			{
				final int warehouseId = key.getValue().getKey();
				final PickingOKPanel pickingOKPanel = getPickingOKPanel();
				pickingOKPanel.getModel().setM_Warehouse_ID(warehouseId);
				refreshLines(ResetFilters.Yes);
			}
		}
	}

	// panel listener for result
	private class ConfirmPanelListener implements PropertyChangeListener
	{
		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			if (!IConfirmPanel.PROP_Action.equals(evt.getPropertyName()))
			{
				return;
			}
			String action = String.valueOf(evt.getNewValue());
			if (IConfirmPanel.ACTION_OK.equals(action))
			{
				refreshLines();
				next();
			}
			else if (IConfirmPanel.ACTION_Cancel.equals(action))
			{
				frame.dispose();
			}
			else if (ACTION_Exit.equals(action))
			{
				// TODO
			}
		}
	}

	/**
	 * Reset filters on warehouse change (which we consider it as a master refresh/reset)
	 */
	protected void resetFilters()
	{
		// nothing at this level
	}

	protected void refreshLines()
	{
		final ResetFilters resetFilters = ResetFilters.No;
		refreshLines(resetFilters);
	}

	protected static enum ResetFilters
	{
		No,
		Yes,
		IfNoResult,
	}

	protected void refreshLines(final ResetFilters resetFilters)
	{
		if (resetFilters == ResetFilters.Yes)
		{
			resetFilters();
		}

		final PickingOKPanel pickingOKPanel = getPickingOKPanel();

		pickingOKPanel.refresh();

		if (resetFilters == ResetFilters.IfNoResult)
		{
			// If there is no result call this method again with resetFilters=Yes
			final PackingMd packingModel = pickingOKPanel.getModel();
			if (packingModel.getRowsCount() <= 0)
			{
				refreshLines(ResetFilters.Yes);
			}
		}
	}

	/**
	 * Method called when we activate the panel window's and we want to give focus to proper component
	 */
	public void requestFocus()
	{
		// nothing at this level
	}
}
