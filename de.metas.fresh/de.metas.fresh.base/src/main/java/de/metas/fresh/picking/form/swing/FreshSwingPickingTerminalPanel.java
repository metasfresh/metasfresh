/**
 * 
 */
package de.metas.fresh.picking.form.swing;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.adempiere.util.Check;
import org.compiere.minigrid.IMiniTable;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.ITableRowSearchSelectionMatcher;
import de.metas.adempiere.form.NullTableRowSearchSelectionMatcher;
import de.metas.adempiere.form.PackingMd;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.IKeyLayoutSelectionModel;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.ITerminalKeyListener;
import de.metas.adempiere.form.terminal.ITerminalKeyPanel;
import de.metas.adempiere.form.terminal.ITerminalLabel;
import de.metas.adempiere.form.terminal.ITerminalTextField;
import de.metas.adempiere.form.terminal.TerminalKeyListenerAdapter;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.fresh.picking.BPartnerKey;
import de.metas.fresh.picking.BPartnerKeyLayout;
import de.metas.fresh.picking.DeliveryDateKey;
import de.metas.fresh.picking.DeliveryDateKeyLayout;
import de.metas.fresh.picking.form.BarcodeHUTableRowSearchSelectionMatcher;
import de.metas.fresh.picking.form.ProductTableRowSearchSelectionMatcher;
import de.metas.fresh.picking.form.SSCC18HUTableRowSearchSelectionMatcher;
import de.metas.picking.terminal.PickingOKPanel;
import de.metas.picking.terminal.form.swing.SwingPickingTerminalPanel;

/**
 * Picking First Window Panel.
 * 
 * @author cg
 * 
 */
public class FreshSwingPickingTerminalPanel extends SwingPickingTerminalPanel
{
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
		public void propertyChange(PropertyChangeEvent evt)
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

	public FreshSwingPickingTerminalPanel()
	{
		super();
	}

	@Override
	public final PickingOKPanel createPickingOKPanel()
	{
		return new FreshSwingPickingOKPanel(this);
	}

	@Override
	protected final void initComponents() throws Exception
	{
		super.initComponents();

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
			barcodeSearchField.setShowKeyboardButton(false);
			barcodeSearchField.setKeyLayout(null); // no keyboard
			barcodeSearchField.addListener(barcodeSearchFieldListener);
			// initComponents_ProductSearch();
		}

		//
		// Selection changed listener
		getPickingOKPanel().getMiniTable().addPropertyChangeListener(IMiniTable.PROPERTY_SelectionChanged, new PropertyChangeListener()
		{
			@Override
			public void propertyChange(final PropertyChangeEvent evt)
			{
				onSelectedLinesChanged();
			}
		});
	}

	@Override
	protected IKeyLayout createWarehouseKeyLayout()
	{
		final IKeyLayout warehouseKeyLayout = super.createWarehouseKeyLayout();
		warehouseKeyLayout.setRows(1); // fresh_06250
		return warehouseKeyLayout;
	}

	@Override
	protected void initLayout_WarehousePicking()
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
		createPanel(container, getPickingOKPanel().getComponent(), "dock west, growx, wmin 620px, hmin 33%"); // i.e. shipment schedule lines
		createPanel(container, searchFieldsPanel, "dock east, growx, width 50%");

		add(container, SwingPickingTerminalPanel.CARDNAME_WAREHOUSE_PICKING, "dock north, growx");
	}

	@Override
	protected final void initDefaults()
	{
		super.initDefaults();

		// Set default focus to Product Search Field
		// NOTE: this method is called after window is build, so that's why is safe to call here request focus
		barcodeSearchField.requestFocus();

		resetProductSearchField();
	}

	private void onSelectedLinesChanged()
	{
		final PickingOKPanel pickingOKPanel = getPickingOKPanel();

		//
		// Update BPartner Keys
		{
			final Set<KeyNamePair> bpartners = pickingOKPanel.getSelectedBPartners();
			bpartnerKeyLayout.createAndSetKeysFromBPartnerKNPs(bpartners);
		}

		//
		// Update DeliveryDate Keys
		{
			final Set<Date> deliveryDates = pickingOKPanel.getSelectedDeliveryDates();
			deliveryDateKeyLayout.createAndSetKeysFromDates(deliveryDates);
		}
	}

	public int getSelectedWarehouseId()
	{
		final PickingOKPanel pickingOKPanel = getPickingOKPanel();
		final PackingMd model = pickingOKPanel.getModel();
		return model.getM_Warehouse_ID();
	}

	private List<Integer> getSelectedBPartnerIds()
	{
		final BPartnerKey bpartnerKey = bpartnerKeyLayout
				.getKeyLayoutSelectionModel()
				.getSelectedKeyOrNull(BPartnerKey.class);
		if (bpartnerKey == null)
		{
			return Collections.emptyList();
		}

		final int bpartnerId = bpartnerKey.getC_BPartner_ID();
		return Collections.singletonList(bpartnerId);
	}

	private void setSelectedBPartnerId(final int bpartnerId)
	{
		final BPartnerKey bpartnerKey = bpartnerKeyLayout.getKeyByBPartnerId(bpartnerId);
		final IKeyLayoutSelectionModel selectionModel = bpartnerKeyLayout.getKeyLayoutSelectionModel();
		selectionModel.setSelectedKey(bpartnerKey);
	}

	private Date getSelectedDeliveryDate()
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

	@Override
	protected final void resetFilters()
	{
		// When user presses the "Warehouse button" we shall reset all other filters
		// NOTE: "Warehouse button" shall be considered something like a master filters reset


		final PickingOKPanel pickingOKPanel = getPickingOKPanel();
		final PackingMd packingMd = pickingOKPanel.getModel();

		//
		// Reset selected BPartner key
		bpartnerKeyLayout.getKeyLayoutSelectionModel().onKeySelected(null);
		// FIXME: for some reason BPartnerIds is not set in model
		packingMd.setBPartnerIds(null);

		//
		// Reset selected DeliveryDate key
		deliveryDateKeyLayout.getKeyLayoutSelectionModel().onKeySelected(null);
		// FIXME: for some reason DeliveryDate is not set in model
		packingMd.setDeliveryDate(null);
		
		//
		// Reset table rows matcher (fresh_06821)
		packingMd.setTableRowSearchSelectionMatcher(NullTableRowSearchSelectionMatcher.instance);
	}

	@Override
	protected final void refreshLines()
	{
		final PickingOKPanel pickingOKPanel = getPickingOKPanel();
		final PackingMd model = pickingOKPanel.getModel();

		final List<Integer> bpartnerIds = getSelectedBPartnerIds();
		model.setBPartnerIds(bpartnerIds);

		final Date deliveryDate = getSelectedDeliveryDate();
		model.setDeliveryDate(deliveryDate);

		super.refreshLines();
	}

	private void setTableRowSearchSelectionMatcher(final ITableRowSearchSelectionMatcher matcherNew)
	{
		Check.assumeNotNull(matcherNew, "matcherNew not null");

		final PickingOKPanel pickingOKPanel = getPickingOKPanel();
		final PackingMd model = pickingOKPanel.getModel();

		final ITableRowSearchSelectionMatcher matcherOld = model.getTableRowSearchSelectionMatcher();

		model.setTableRowSearchSelectionMatcher(matcherNew);

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

	protected final void onBarcodeSearch(final String barcode)
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

	protected final void onBarcodeSearch0(final String barcode)
	{
		if (Check.isEmpty(barcode, true))
		{
			return;
		}

		final Properties ctx = getCtx();

		final int warehouseId = getSelectedWarehouseId();

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
				, NullTableRowSearchSelectionMatcher.instance
				);

		//
		// Pick the first matcher
		for (final ITableRowSearchSelectionMatcher matcher : matchers)
		{
			// Skip if matcher is valid
			if (!matcher.isValid())
			{
				continue;
			}

			//
			// We found a valid matcher => push it to model

			// Filter by BPartner if our matcher is BP specific
			final int matcherBPartnerId = matcher.getC_BPartner_ID();
			if (matcherBPartnerId > 0)
			{
				setSelectedBPartnerId(matcherBPartnerId);
			}
			// push our matcher to model
			setTableRowSearchSelectionMatcher(matcher);

			// stop here, after first valid matcher
			return;
		}
	}

	@Override
	protected void refreshLines(final ResetFilters resetFilters)
	{
		super.refreshLines(resetFilters);

		// Reset selection if filters were reset
		if (resetFilters == ResetFilters.Yes)
		{
			final PickingOKPanel pickingOKPanel = getPickingOKPanel();
			final PackingMd model = pickingOKPanel.getModel();
			model.setSelectedTableRowKeys(null); // force fire event
		}
	}

	@Override
	public void requestFocus()
	{
		if (barcodeSearchField != null)
		{
			barcodeSearchField.requestFocus();
		}
	}
}
