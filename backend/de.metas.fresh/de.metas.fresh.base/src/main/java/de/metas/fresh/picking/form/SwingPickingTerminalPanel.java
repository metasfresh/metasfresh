/**
 *
 */
package de.metas.fresh.picking.form;

import java.awt.BorderLayout;
import java.awt.CardLayout;

/*
 * #%L
 * de.metas.fresh.base
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
import java.awt.Container;
import java.awt.Frame;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.adempiere.util.lang.IPair;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.apps.form.FormFrame;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IConfirmPanel;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.IKeyLayoutSelectionModel;
import de.metas.adempiere.form.terminal.ITerminalBasePanel;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.ITerminalKeyListener;
import de.metas.adempiere.form.terminal.ITerminalKeyPanel;
import de.metas.adempiere.form.terminal.ITerminalLabel;
import de.metas.adempiere.form.terminal.ITerminalLoginDialog;
import de.metas.adempiere.form.terminal.ITerminalScrollPane;
import de.metas.adempiere.form.terminal.ITerminalTextField;
import de.metas.adempiere.form.terminal.ITerminalTextField.KeyboardDisplayMode;
import de.metas.adempiere.form.terminal.ITerminalTextPane;
import de.metas.adempiere.form.terminal.POSKeyLayout;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.TerminalKeyListenerAdapter;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.context.ITerminalContextReferences;
import de.metas.adempiere.form.terminal.context.TerminalContextFactory;
import de.metas.adempiere.form.terminal.swing.SwingTerminalFactory;
import de.metas.bpartner.BPartnerId;
import de.metas.fresh.picking.BPartnerKey;
import de.metas.fresh.picking.BPartnerKeyLayout;
import de.metas.fresh.picking.DeliveryDateKey;
import de.metas.fresh.picking.DeliveryDateKeyLayout;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.picking.terminal.form.swing.IPickingTerminalPanel;
import de.metas.util.Check;
import de.metas.util.Services;

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
 */
public class SwingPickingTerminalPanel implements ITerminalBasePanel, IPickingTerminalPanel
{
	public static SwingPickingTerminalPanel cast(final ITerminalBasePanel panel)
	{
		return (SwingPickingTerminalPanel)panel;
	}

	private static final Logger logger = LogManager.getLogger(SwingPickingTerminalPanel.class);

	private static final String TITLE_PACKAGE_TERMINAL = "PackageTerminal";

	private static final String CARDNAME_WAREHOUSE_PICKING = "WAREHOUSE_PICKING";
	private static final String CARDNAME_RESULT = "RESULT";

	private final IPair<ITerminalContext, ITerminalContextReferences> terminalContextAndRefs;
	private SwingPickingOKPanel pickingOKPanel;

	private boolean _disposed = false;

	private final IContainer panel;
	private final CardLayout cardLayout;

	private FormFrame frame;
	private ITerminalKeyPanel warehousePanel;
	private ITerminalTextPane resultTextPane;
	private IConfirmPanel confirmPanel;
	private final String ACTION_Exit = "Logout";

	private BPartnerKeyLayout bpartnerKeyLayout;
	private ITerminalKeyPanel bpartnerPanel;
	private DeliveryDateKeyLayout deliveryDateKeyLayout;
	private ITerminalKeyPanel deliveryDateKeyPanel;
	// private VLookup productSearchField;
	private ITerminalTextField barcodeSearchField;

	private final ITerminalKeyListener refreshLinesTerminalKeyListener = new TerminalKeyListenerAdapter()
	{
		@Override
		public void keyReturned(final ITerminalKey key)
		{
			refreshLines();
		}
	};

	private final PropertyChangeListener barcodeSearchFieldListener = new PropertyChangeListener()
	{

		@Override
		public void propertyChange(final PropertyChangeEvent evt)
		{
			final String eventName = evt.getPropertyName();
			if (ITerminalTextField.PROPERTY_ActionPerformed.equals(eventName)
					|| ITerminalTextField.PROPERTY_TextChanged.equals(eventName)
					|| ITerminalTextField.PROPERTY_FocusLost.equals(eventName))
			{
				final String barcode = barcodeSearchField.getValue();
				onBarcodeSearch(barcode);
			}
		}
	};

	public SwingPickingTerminalPanel()
	{
		terminalContextAndRefs = TerminalContextFactory.get().createContextAndRefs();
		getTerminalContext().addToDisposableComponents(this);

		final IContainer panel = getTerminalFactory().createContainer();

		final Container panelSwing = SwingTerminalFactory.getUI(panel);
		cardLayout = new CardLayout();
		panelSwing.setLayout(cardLayout);

		this.panel = panel;
	}

	@Override
	public void add(final IComponent component, final Object constraints)
	{
		panel.add(component, constraints);
	}

	@Override
	public void addAfter(final IComponent component, final IComponent componentBefore, final Object constraints)
	{
		panel.addAfter(component, componentBefore, constraints);
	}

	@Override
	public void remove(final IComponent component)
	{
		panel.remove(component);
	}

	@Override
	public void removeAll()
	{
		panel.removeAll();
	}

	@Override
	public ITerminalContext getTerminalContext()
	{
		return terminalContextAndRefs.getLeft();
	}

	@Override
	public ITerminalFactory getTerminalFactory()
	{
		return getTerminalContext().getTerminalFactory();
	}

	@Override
	public boolean isProcessed()
	{
		return false;
	}

	@Override
	public Properties getCtx()
	{
		return getTerminalContext().getCtx();
	}

	@Override
	public final void keyPressed(final ITerminalKey key)
	{
		// nothing to do
	}

	@Override
	public void logout()
	{
		doLogin();
	}

	private void doLogin()
	{
		while (true)
		{
			setFrameVisible(false);
			final ITerminalLoginDialog login = getTerminalFactory().createTerminalLoginDialog(this);

			if (getAD_User_ID() >= 0)
			{
				login.setAD_User_ID(getAD_User_ID());
			}

			login.activate();
			if (login.isExit())
			{
				dispose();
				return;
			}
			else if (login.isLogged())
			{
				setAD_User_ID(login.getAD_User_ID());
				setFrameVisible(true);
				return;
			}
		}
	}

	private void setAD_User_ID(final int AD_User_ID)
	{
		getTerminalContext().setAD_User_ID(AD_User_ID);
	}

	private int getAD_User_ID()
	{
		return getTerminalContext().getAD_User_ID();
	}

	@Override
	public final FormFrame getComponent()
	{
		return frame;
	}

	private final void setFrame(final Object frame)
	{
		this.frame = (FormFrame)frame;
		this.frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.frame.setResizable(false);
		this.frame.setJMenuBar(null);
	}

	private final void setFrameVisible(final boolean visible)
	{
		if (frame != null)
		{
			frame.setVisible(visible);
		}
		else
		{
			return;
		}

		final SwingPickingOKPanel pickingOKPanel = getPickingOKPanel();
		if (pickingOKPanel != null && pickingOKPanel.getPackageFrame() != null)
		{
			pickingOKPanel.getPackageFrame().setVisible(visible);
		}
	}

	@Override
	public void init(final int windowNo, final FormFrame frame)
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
			logger.warn("init", e);
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
	public final boolean isDisposed()
	{
		return _disposed;
	}

	@Override
	public void dispose()
	{
		if (frame != null)
		{
			frame.dispose();
			frame = null;
		}

		if (isDisposed())
		{
			// This method might be called by both the swing framework and ITerminalContext.
			// Therefore we need to make sure not to try and call deleteReferences() twice because the second time there will be an error.
			return;
		}

		// it's important to do this before calling deleteReferences(), because this instance itself was also added as a removable component.
		// so, deleteReferences() will also call this dispose() method, and we want to avoid a stack overflow error.
		// note: alternatively, we could also add a _disposing variable, like we do e.g. in AbstractHUSelectFrame.
		_disposed = true;

		getTerminalContext().deleteReferences(terminalContextAndRefs.getRight());

		final TerminalContextFactory terminalContextFactory = TerminalContextFactory.get();
		terminalContextFactory.destroy(getTerminalContext());
	}

	private final void initComponents() throws Exception
	{
		final String frameTitle = Services.get(IMsgBL.class).getMsg(Env.getCtx(), TITLE_PACKAGE_TERMINAL);
		frame.setTitle(frameTitle);

		//
		// Card 1: Warehouse Picking
		{
			//
			final IKeyLayout warehouseKeyLayout = new POSKeyLayout(getTerminalContext(), 540003); // TODO hard coded "Warehouse Groups"
			warehouseKeyLayout.setRows(1); // fresh_06250
			warehousePanel = getTerminalFactory().createTerminalKeyPanel(warehouseKeyLayout, new WarehouseKeyListener());
			warehousePanel.setAllowKeySelection(true);

			//
			pickingOKPanel = new SwingPickingOKPanel(this);
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

		final ITerminalContext terminalContext = getTerminalContext();
		final ITerminalFactory terminalFactory = getTerminalFactory();

		//
		// BPartner panel
		{
			bpartnerKeyLayout = new BPartnerKeyLayout(terminalContext);
			bpartnerKeyLayout.setRows(2);
			bpartnerKeyLayout.setColumns(3);

			final IKeyLayoutSelectionModel bpartnerKeyLayoutSelection = bpartnerKeyLayout.getKeyLayoutSelectionModel();
			bpartnerKeyLayoutSelection.setAllowKeySelection(true);
			bpartnerKeyLayoutSelection.setAutoSelectIfOnlyOne(false);
			bpartnerKeyLayoutSelection.setToggleableSelection(true);

			bpartnerKeyLayout.addTerminalKeyListener(refreshLinesTerminalKeyListener);

			bpartnerPanel = terminalFactory.createTerminalKeyPanel(bpartnerKeyLayout);
		}

		//
		// Dates search panel
		{
			deliveryDateKeyLayout = new DeliveryDateKeyLayout(terminalContext);
			deliveryDateKeyLayout.setRows(1);
			deliveryDateKeyLayout.setColumns(10);

			final IKeyLayoutSelectionModel deliveryDateKeyLayoutSelection = deliveryDateKeyLayout.getKeyLayoutSelectionModel();
			deliveryDateKeyLayoutSelection.setAllowKeySelection(true);
			deliveryDateKeyLayoutSelection.setAutoSelectIfOnlyOne(false);
			deliveryDateKeyLayoutSelection.setToggleableSelection(true);

			deliveryDateKeyLayout.addTerminalKeyListener(refreshLinesTerminalKeyListener);

			deliveryDateKeyPanel = terminalFactory.createTerminalKeyPanel(deliveryDateKeyLayout);
			deliveryDateKeyPanel.setKeyFixedHeight("40px");
		}

		//
		// Product Search panel
		{
			barcodeSearchField = terminalFactory.createTerminalTextField("Barcode");
			barcodeSearchField.setKeyboardDisplayMode(KeyboardDisplayMode.NEVER);
			barcodeSearchField.setKeyLayout(null); // no keyboard
			barcodeSearchField.addListener(barcodeSearchFieldListener);
			// initComponents_ProductSearch();
		}

		//
		// Selection changed listener
		getPickingOKPanel().addLinesSelectionChangedListener(this::onSelectedLinesChanged);
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
	private void initLayout_WarehousePicking()
	{
		final ITerminalFactory terminalFactory = getTerminalFactory();

		//
		// Fields Search Panel
		final IContainer searchFieldsPanel = terminalFactory.createContainer("flowx, ins 0 0");
		{
			final ITerminalLabel barcodeSearchLabel = terminalFactory.createLabel("Barcode", true);
			((JComponent)barcodeSearchLabel.getComponent()).setBackground(Color.RED);

			searchFieldsPanel.add(barcodeSearchLabel, "");
			searchFieldsPanel.add(
					barcodeSearchField, // component
					"growx, wrap, width 300px" // constraints
			);

			//
			// Enable programatically Tab Key handling because there is NO other focusable component, so focus lost will not be triggered on TAB presses.
			// NOTE: this is a workaround introduced in task: http://dewiki908/mediawiki/index.php/08766_Kommissionierung_Barcode_TAB_%28108885949537%29
			// FIXME: figure it out how to solve this case without special handling at this level (idea: check the focus traversal policy and decide based on that)
			barcodeSearchField.enableHandleTabKeyAsFocusLost();
		}

		final IContainer container = terminalFactory.createContainer("fill, ins 0 0");
		createPanel(container, getWarehouseKeyPanel(), "dock north, growx, hmin 100px");
		createPanel(container, bpartnerPanel, "dock north, growx, hmin 200px, hmax 33%");
		createPanel(container, deliveryDateKeyPanel, "dock north, growx, hmin 60px");
		createPanel(container, getPickingOKPanel(), "dock west, growx, wmin 620px, hmin 33%"); // i.e. shipment schedule lines
		createPanel(container, searchFieldsPanel, "dock east, growx, width 50%");

		add(container, CARDNAME_WAREHOUSE_PICKING, "dock north, growx");
	}

	/**
	 * Layout for result panel (Card 2).
	 *
	 * @see #CARDNAME_RESULT
	 */
	private void initLayout_Result()
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
	private final void initDefaults()
	{
		//
		// Select by default first warehouse
		final List<ITerminalKey> warehouseKeys = warehousePanel.getKeyLayout().getKeys();
		if (!warehouseKeys.isEmpty())
		{
			final ITerminalKey warehouseKey = warehouseKeys.get(0);
			warehousePanel.fireKeyPressed(warehouseKey.getId());
		}

		// Set default focus to Product Search Field
		// NOTE: this method is called after window is build, so that's why is safe to call here request focus
		barcodeSearchField.requestFocus();

		resetProductSearchField();
	}

	private final IComponent createPanel(final IContainer content, final IComponent component, final Object constraints)
	{
		content.add(component, constraints);

		final ITerminalScrollPane scroll = getTerminalFactory().createScrollPane(content);
		scroll.setUnitIncrementVSB(16);
		final IContainer card = getTerminalFactory().createContainer();
		card.add(scroll, "growx, growy");
		return card;
	}

	private final void add(final IComponent component, final String name, final Object constraints)
	{
		SwingTerminalFactory.addChild(panel, component, name, constraints);
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

	private void onSelectedLinesChanged()
	{
		final SwingPickingOKPanel pickingOKPanel = getPickingOKPanel();

		//
		// Update BPartner Keys
		{
			final Set<KeyNamePair> bpartners = pickingOKPanel.getSelectedBPartners();
			bpartnerKeyLayout.createAndSetKeysFromBPartnerKNPs(bpartners);
		}

		//
		// Update DeliveryDate Keys
		{
			final Set<LocalDate> deliveryDates = pickingOKPanel.getSelectedDeliveryDates();
			deliveryDateKeyLayout.createAndSetKeysFromDates(deliveryDates);
		}
	}

	private final ITerminalKeyPanel getWarehouseKeyPanel()
	{
		return warehousePanel;
	}

	public final SwingPickingOKPanel getPickingOKPanel()
	{
		return pickingOKPanel;
	}

	public WarehouseId getSelectedWarehouseId()
	{
		return getPickingOKPanel().getWarehouseId();
	}

	private Set<BPartnerId> getSelectedBPartnerIds()
	{
		final BPartnerKey bpartnerKey = bpartnerKeyLayout
				.getKeyLayoutSelectionModel()
				.getSelectedKeyOrNull(BPartnerKey.class);
		if (bpartnerKey == null)
		{
			return ImmutableSet.of();
		}

		final BPartnerId bpartnerId = bpartnerKey.getBpartnerId();
		return bpartnerId != null ? ImmutableSet.of(bpartnerId) : ImmutableSet.of();
	}

	private void setSelectedBPartnerId(final BPartnerId bpartnerId)
	{
		final BPartnerKey bpartnerKey = bpartnerKeyLayout.getKeyByBPartnerId(bpartnerId);
		final IKeyLayoutSelectionModel selectionModel = bpartnerKeyLayout.getKeyLayoutSelectionModel();
		selectionModel.setSelectedKey(bpartnerKey);
	}

	private LocalDate getSelectedDeliveryDate()
	{
		final DeliveryDateKey deliveryDateKey = deliveryDateKeyLayout
				.getKeyLayoutSelectionModel()
				.getSelectedKeyOrNull(DeliveryDateKey.class);

		if (deliveryDateKey == null)
		{
			return null;
		}

		return deliveryDateKey.getDate();
	}

	/** Reset filters on warehouse change (which we consider it as a master refresh/reset) */
	private final void resetFilters()
	{
		// When user presses the "Warehouse button" we shall reset all other filters
		// NOTE: "Warehouse button" shall be considered something like a master filters reset

		final SwingPickingOKPanel pickingOKPanel = getPickingOKPanel();

		//
		// Reset selected BPartner key
		bpartnerKeyLayout.getKeyLayoutSelectionModel().onKeySelected(null);
		// FIXME: for some reason BPartnerIds is not set in model
		pickingOKPanel.setBPartnerIds(null);

		//
		// Reset selected DeliveryDate key
		deliveryDateKeyLayout.getKeyLayoutSelectionModel().onKeySelected(null);
		// FIXME: for some reason DeliveryDate is not set in model
		pickingOKPanel.setDeliveryDate(null);

		//
		// Reset table rows matcher (fresh_06821)
		pickingOKPanel.setTableRowSearchSelectionMatcher(NullTableRowSearchSelectionMatcher.instance);
	}

	private final void refreshLines()
	{
		final SwingPickingOKPanel pickingOKPanel = getPickingOKPanel();

		final Set<BPartnerId> bpartnerIds = getSelectedBPartnerIds();
		pickingOKPanel.setBPartnerIds(bpartnerIds);

		final LocalDate deliveryDate = getSelectedDeliveryDate();
		pickingOKPanel.setDeliveryDate(deliveryDate);

		refreshLines(ResetFilters.No);
	}

	private void setTableRowSearchSelectionMatcher(final ITableRowSearchSelectionMatcher matcherNew)
	{
		Check.assumeNotNull(matcherNew, "matcherNew not null");

		final SwingPickingOKPanel pickingOKPanel = getPickingOKPanel();

		final ITableRowSearchSelectionMatcher matcherOld = pickingOKPanel.getTableRowSearchSelectionMatcher();

		pickingOKPanel.setTableRowSearchSelectionMatcher(matcherNew);

		//
		// Check if new matcher and old matcher are functionally the same
		if (matcherNew.equalsOrNull(matcherOld))
		{
			return;
		}

		refreshLines();
	}

	private void resetProductSearchField()
	{
		barcodeSearchField.setValue(null);
		barcodeSearchField.requestFocus();
	}

	private boolean onBarcodeSearchRunning = false;

	private final void onBarcodeSearch(final String barcode)
	{
		if (onBarcodeSearchRunning)
		{
			return;
		}

		onBarcodeSearchRunning = true;
		try
		{
			//
			// Execute actual barcode search
			onBarcodeSearch0(barcode);

			//
			// Reset Product Search Field
			SwingUtilities.invokeLater(new Runnable()
			{
				@Override
				public void run()
				{
					resetProductSearchField();
				}
			});
		}
		finally
		{
			onBarcodeSearchRunning = false;
		}
	}

	private final void onBarcodeSearch0(final String barcode)
	{
		if (Check.isEmpty(barcode, true))
		{
			return;
		}

		final Properties ctx = getCtx();

		final int warehouseId = WarehouseId.toRepoId(getSelectedWarehouseId());

		//
		// Build a list of matchers that needs to be checked
		final List<ITableRowSearchSelectionMatcher> matchers = Arrays.<ITableRowSearchSelectionMatcher> asList(
				// Check if given barcode is a product UPC or M_Product.Value
				new ProductTableRowSearchSelectionMatcher(ctx, barcode)
				// Check if given barcode is a SSCC18 of an existing HU
				, new SSCC18HUTableRowSearchSelectionMatcher(ctx, barcode, warehouseId)
				// Check if given barcode is an HU internal barcode
				, new BarcodeHUTableRowSearchSelectionMatcher(ctx, barcode, warehouseId)
				// Fallback: null matcher
				, NullTableRowSearchSelectionMatcher.instance);

		//
		// Pick the first matcher
		for (final ITableRowSearchSelectionMatcher matcher : matchers)
		{
			// Skip if matcher is invalid
			if (!matcher.isValid())
			{
				continue;
			}

			//
			// We found a valid matcher => push it to model

			// Filter by BPartner if our matcher is BP specific
			final BPartnerId matcherBPartnerId = BPartnerId.ofRepoIdOrNull(matcher.getC_BPartner_ID());
			if (matcherBPartnerId != null)
			{
				setSelectedBPartnerId(matcherBPartnerId);
			}
			// push our matcher to model
			setTableRowSearchSelectionMatcher(matcher);

			// stop here, after first valid matcher
			return;
		}
	}

	static enum ResetFilters
	{
		No, Yes, IfNoResult,
	}

	public void refreshLines(final ResetFilters resetFilters)
	{
		if (resetFilters == ResetFilters.Yes)
		{
			resetFilters();
		}

		final SwingPickingOKPanel pickingOKPanel = getPickingOKPanel();
		pickingOKPanel.refreshLines();

		if (resetFilters == ResetFilters.IfNoResult)
		{
			// If there is no result call this method again with resetFilters=Yes
			if (pickingOKPanel.getRowsCount() <= 0)
			{
				refreshLines(ResetFilters.Yes);
			}
		}

		// Reset selection if filters were reset
		if (resetFilters == ResetFilters.Yes)
		{
			pickingOKPanel.setSelectedTableRowKeys(null); // force fire event
		}
	}

	/**
	 * Method called when we activate the panel window's and we want to give focus to proper component
	 */
	public void requestFocus()
	{
		if (barcodeSearchField != null)
		{
			barcodeSearchField.requestFocus();
		}
	}

	public void setResultHtml(final String resultHtml)
	{
		resultTextPane.setText(resultHtml);
	}

	// panel listener for result
	private class ConfirmPanelListener implements PropertyChangeListener
	{
		@Override
		public void propertyChange(final PropertyChangeEvent evt)
		{
			if (!IConfirmPanel.PROP_Action.equals(evt.getPropertyName()))
			{
				return;
			}
			final String action = String.valueOf(evt.getNewValue());
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

	private class WarehouseKeyListener extends TerminalKeyListenerAdapter
	{

		@Override
		public void keyReturned(final ITerminalKey key)
		{
			if (I_M_Warehouse.Table_Name.equals(key.getTableName()))
			{
				final WarehouseId warehouseId = WarehouseId.ofRepoId(key.getValue().getKey());
				final SwingPickingOKPanel pickingOKPanel = getPickingOKPanel();
				pickingOKPanel.setWarehouseId(warehouseId);
				refreshLines(ResetFilters.Yes);
			}
		}
	}

}
