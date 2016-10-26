package de.metas.handlingunits.client.terminal.select.model;

/*
 * #%L
 * de.metas.handlingunits.client
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

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.beans.WeakPropertyChangeSupport;
import org.adempiere.util.collections.Predicate;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Warehouse;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.terminal.IDisposable;
import de.metas.adempiere.form.terminal.IKeyLayoutSelectionModel;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.TerminalKeyListenerAdapter;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.context.ITerminalContextReferences;
import de.metas.adempiere.form.terminal.table.ITerminalTableModel;
import de.metas.adempiere.form.terminal.table.ITerminalTableModel.SelectionMode;
import de.metas.adempiere.form.terminal.table.ITerminalTableModelListener;
import de.metas.adempiere.form.terminal.table.TerminalTableModel;
import de.metas.adempiere.form.terminal.table.swing.TerminalTableModelListenerAdapter;
import de.metas.adempiere.service.IPOSAccessBL;
import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.select.api.IPOSFiltering;
import de.metas.handlingunits.client.terminal.select.api.IPOSTableRow;
import de.metas.handlingunits.model.I_M_HU;

public abstract class AbstractHUSelectModel implements IDisposable
{
	// Services
	private final IPOSAccessBL posAccessBL = Services.get(IPOSAccessBL.class);

	private static final String MSG_ErrorNoDocumentLineSelected = "de.metas.handlingunits.client.ErrorNoDocumentLineSelected";
	public static final String MSG_ErrorNoHandlingUnitFoundForSelection = "de.metas.handlingunits.client.ErrorNoHandlingUnitFoundForSelection";
	private static final String MSG_ErrorNoWarehouseSelected = "de.metas.handlingunits.client.ErrorNoWarehouseSelected";
	public static final String MSG_ErrorNoBPartnerSelected = "de.metas.handlingunits.client.ErrorNoBPartnerSelected";

	public static final String PROPERTY_Lines = "Lines";

	private final ITerminalContext _terminalContext;
	private final IPOSFiltering service;

	private final WarehouseKeyLayout warehouseKeyLayout;

	private final VendorKeyLayout vendorKeyLayout;
	private boolean displayVendorKeys = true;
	private boolean displayCloseLinesButton = false;
	private boolean displayPhotoShootButton = false;

	private IQueryFilter<I_M_HU> additionalFilter = null;

	private ITerminalTableModel<IPOSTableRow> rowsTableModel;
	private ITerminalTableModelListener rowTableModelListener;
	private boolean allowNoRowsSelection = false;

	private final WeakPropertyChangeSupport pcs;

	/**
	 * All lines
	 */
	private List<IPOSTableRow> _linesAll = null;

	/**
	 * Lines which are filtered and displayed to user
	 */
	private List<IPOSTableRow> _lines = Collections.emptyList();

	private boolean disposed = false;

	/**
	 * Creates the filtering predicate for which lines are displayed.
	 *
	 * This needs to be specific for each implementations, as the displayed rows depend on different columns.
	 *
	 * @return
	 */
	protected abstract Predicate<IPOSTableRow> getRowsFilter();

	public AbstractHUSelectModel(final ITerminalContext terminalContext)
	{
		super();

		Check.assumeNotNull(terminalContext, "terminalContext not null");
		_terminalContext = terminalContext;
		pcs = terminalContext.createPropertyChangeSupport(this);

		service = _terminalContext.getService(IPOSFiltering.class);

		//
		// Configure Warehouse Key Layout
		warehouseKeyLayout = new WarehouseKeyLayout(_terminalContext);
		warehouseKeyLayout.getKeyLayoutSelectionModel().setAllowKeySelection(true);
		warehouseKeyLayout.addTerminalKeyListener(new TerminalKeyListenerAdapter()
		{
			@Override
			public void keyReturned(final ITerminalKey key)
			{
				final WarehouseKey warehouseKey = (WarehouseKey)key;
				onWarehouseKeyPressed(warehouseKey);
				refreshLines(true); // forceRefresh=true
			}
		});

		//
		// Configure vendorKeyLayout selectionModel
		vendorKeyLayout = new VendorKeyLayout(_terminalContext);
		{
			final IKeyLayoutSelectionModel vendorKeyLayoutSelectionModel = vendorKeyLayout.getKeyLayoutSelectionModel();
			vendorKeyLayoutSelectionModel.setAllowKeySelection(true);
			vendorKeyLayoutSelectionModel.setToggleableSelection(true);
		}
		vendorKeyLayout.addTerminalKeyListener(new TerminalKeyListenerAdapter()
		{
			@Override
			public void keyReturned(final ITerminalKey key)
			{
				final VendorKey vendorKey = (VendorKey)key;
				onVendorKeyPressed(vendorKey);
			}
		});

		//
		// Configure Rows Model
		rowsTableModel = new TerminalTableModel<IPOSTableRow>(terminalContext, service.getTableRowType());
		rowsTableModel.setEditable(true);
		rowsTableModel.setSelectionMode(SelectionMode.MULTIPLE);

		rowTableModelListener = new TerminalTableModelListenerAdapter()
		{
			@Override
			public void fireSelectionChanged()
			{
				setActionButtonsEnabled();
			}
		};
		rowsTableModel.addTerminalTableModelListener(rowTableModelListener);

		//
		// Add this model to disposables, to make sure it will be destroyed ASAP, when it's not needed
		// NOTE: this is not really mandatory, because it will be destroyed by GC when the time comes...
		terminalContext.addToDisposableComponents(this);
	}

	protected final void setAllowNoRowsSelection(final boolean allowNoRowsSelection)
	{
		this.allowNoRowsSelection = allowNoRowsSelection;
	}

	@OverridingMethodsMustInvokeSuper
	protected IPOSFiltering getService()
	{
		return service;
	}

	abstract protected void onWarehouseKeyPressed(final WarehouseKey key);

	protected void onVendorKeyPressed(final VendorKey key)
	{
		refreshLines(false); // forceRefresh=false
	}

	/**
	 * Refresh current lines
	 *
	 * @param forceRefresh if <code>true</code> then always refresh (even if is not needed)
	 */
	public void refreshLines(final boolean forceRefresh)
	{
		Services.get(IClientUI.class).invoke()
				.setLongOperation(true)
				.setParentComponentByWindowNo(getTerminalContext().getWindowNo())
				.setRunnable(new Runnable()
				{
					@Override
					public void run()
					{
						refreshLines0(forceRefresh);
					}
				})
				.invoke();
	}

	private void refreshLines0(final boolean forceRefresh)
	{
		final int warehouseId = getM_Warehouse_ID();

		//
		// Do we need to refresh all lines?
		if (warehouseId > 0 && (_linesAll == null || forceRefresh))
		{
			_linesAll = service.retrieveTableRows(getCtx(), warehouseId);
		}

		//
		// Filter all lines to get current lines
		if (_linesAll == null)
		{
			_lines = Collections.emptyList();
		}
		else
		{
			_lines = Collections.unmodifiableList(service.filter(_linesAll, getRowsFilter()));
		}

		//
		// Load POS Keys from Lines
		{
			// Update BPartner Key Layout:
			final List<I_C_BPartner> bpartners = service.getBPartners(_lines);
			vendorKeyLayout.createAndSetKeysFromBPartners(bpartners);

			// Update other custom key layouts
			loadKeysFromLines(_lines);
		}

		//
		// Get old selected rows (to be reselected if possible)
		final Set<IPOSTableRow> oldSelectedRows = new HashSet<IPOSTableRow>(rowsTableModel.getSelectedRows()); // duplicate old one because it will be cleared

		//
		// Set Lines to model
		rowsTableModel.setRows(_lines);

		//
		// Reselect old rows if possible
		final List<IPOSTableRow> newSelectedRows = _lines.stream()
				.filter(value -> oldSelectedRows.contains(value))
				.collect(Collectors.toList());

		rowsTableModel.setSelectedRows(newSelectedRows);
	}

	/**
	 * Load custom Key Layouts from given lines.
	 *
	 * This method is called during {@link #refreshLines(boolean)}.
	 *
	 * @param lines
	 */
	protected abstract void loadKeysFromLines(final List<IPOSTableRow> lines);

	public final ITerminalTableModel<IPOSTableRow> getRowsModel()
	{
		return rowsTableModel;
	}

	/**
	 *
	 * @return all currently displayed rows
	 */
	public final List<IPOSTableRow> getRows()
	{
		return _lines;
	}

	/**
	 *
	 * @return selected rows
	 */
	public final <T extends IPOSTableRow> Set<T> getRowsSelected()
	{
		final Set<IPOSTableRow> rows = rowsTableModel.getSelectedRows();
		final Set<T> rowsCasted = castRows(rows);
		return rowsCasted;
	}

	/**
	 * Sets selected rows.
	 *
	 * To be used mainly for testing.
	 *
	 * @param rowsSelected
	 */
	public final <T extends IPOSTableRow> void setRowsSelected(final Collection<T> rowsSelected)
	{
		rowsTableModel.setSelectedRows(new ArrayList<IPOSTableRow>(rowsSelected));
	}

	/**
	 *
	 * @return selected row or null
	 * @throws AdempiereException if more than one row is selected
	 */
	public final <T extends IPOSTableRow> T getRowSelected()
	{
		final Set<T> rows = getRowsSelected();
		if (rows.isEmpty())
		{
			return null;
		}
		else if (rows.size() > 1)
		{
			throw new AdempiereException("Only one row was exepected to be selected: " + rows);
		}
		else
		{
			final T row = rows.iterator().next();
			return row;
		}
	}

	/**
	 * Cast given <code>rows</code> to service's table row type (see {@link IPOSFiltering#getTableRowType()}).
	 *
	 * @param rows
	 * @return rows casted to service's actual row type
	 */
	protected final <T extends IPOSTableRow> Set<T> castRows(final Collection<IPOSTableRow> rows)
	{
		if (rows.isEmpty())
		{
			return Collections.emptySet();
		}

		final Class<T> type = service.getTableRowType();

		final Set<T> rowsCasted = new HashSet<T>();
		for (final IPOSTableRow row : rows)
		{
			final T rowCasted = type.cast(row);
			rowsCasted.add(rowCasted);
		}

		return rowsCasted;
	}

	/**
	 *
	 * @return Selected M_Warehouse_ID or "-1"
	 */
	@OverridingMethodsMustInvokeSuper
	public int getM_Warehouse_ID()
	{
		final WarehouseKey warehouseKey = warehouseKeyLayout.getKeyLayoutSelectionModel().getSelectedKeyOrNull(WarehouseKey.class);
		if (warehouseKey == null)
		{
			return -1;
		}
		return warehouseKey.getM_Warehouse_ID();
	}

	/**
	 * @param failIfNotSelected
	 * @return Selected M_Warehouse_ID or -1 if <code>failIfNotSelected</code> is <code>false</code>
	 * @throws AdempiereException if there is no selected warehouse and <code>failIfNotSelected</code> is <code>true</code>
	 */
	public final int getM_Warehouse_ID(final boolean failIfNotSelected)
	{
		final int warehouseId = getM_Warehouse_ID();
		if (failIfNotSelected && warehouseId <= 0)
		{
			throw new AdempiereException("@" + MSG_ErrorNoWarehouseSelected + "@");
		}

		return warehouseId;
	}

	/**
	 * @return selected warehouse
	 * @throws AdempiereException if there is no selected warehouse
	 */
	public final org.adempiere.warehouse.model.I_M_Warehouse getM_Warehouse()
	{
		final boolean failIfNotSelected = true;
		final int warehouseId = getM_Warehouse_ID(failIfNotSelected);
		final org.adempiere.warehouse.model.I_M_Warehouse warehouse = InterfaceWrapperHelper.create(getCtx(), warehouseId, org.adempiere.warehouse.model.I_M_Warehouse.class, ITrx.TRXNAME_None);
		return warehouse;
	}

	/**
	 * @return C_BPartner_ID of BPartner Key that is currently pressed or <code>-1</code>
	 */
	@OverridingMethodsMustInvokeSuper
	// NOTE: allow override only for testing purposes
	public int getC_BPartner_ID()
	{
		final VendorKey vendorKey = vendorKeyLayout.getKeyLayoutSelectionModel().getSelectedKeyOrNull(VendorKey.class);
		if (vendorKey == null)
		{
			return -1;
		}
		return vendorKey.getC_BPartner_ID();
	}

	/**
	 * @param failIfNotSelected
	 * @return C_BPartner_ID of Vendor Key that is currently pressed
	 * @throws AdempiereException if there is no selected partner and <code>failIfNotSelected</code> is <code>true</code>
	 */
	public final int getC_BPartner_ID(final boolean failIfNotSelected)
	{
		final int partnerId = getC_BPartner_ID();
		if (failIfNotSelected && partnerId <= 0)
		{
			throw new AdempiereException("@" + MSG_ErrorNoBPartnerSelected + "@");
		}

		return partnerId;
	}

	/**
	 * @return selected partner
	 * @throws AdempiereException if there is no selected partner
	 */
	public final de.metas.interfaces.I_C_BPartner getC_BPartner()
	{
		final boolean failIfNotSelected = true;
		final int partnerId = getC_BPartner_ID(failIfNotSelected);
		final de.metas.interfaces.I_C_BPartner partner = InterfaceWrapperHelper.create(getCtx(), partnerId, de.metas.interfaces.I_C_BPartner.class, ITrx.TRXNAME_None);
		return partner;
	}

	/**
	 * @return terminal context; never returns <code>null</code>
	 */
	public final ITerminalContext getTerminalContext()
	{
		return _terminalContext;
	}

	public final Properties getCtx()
	{
		return getTerminalContext().getCtx();
	}

	public final WarehouseKeyLayout getWarehouseKeyLayout()
	{
		return warehouseKeyLayout;
	}

	public final VendorKeyLayout getVendorKeyLayout()
	{
		return vendorKeyLayout;
	}

	/**
	 *
	 * @return true if the Vendor Keys are displayed, false otherwise
	 */
	public final boolean isDisplayVendorKeys()
	{
		return displayVendorKeys;
	}

	/**
	 * true if you want the Vendor Keys to be displayed, false otherwise
	 *
	 * @param displayVendorKeys
	 */
	public final void setDisplayVendorKeys(final boolean displayVendorKeys)
	{
		this.displayVendorKeys = displayVendorKeys;
	}

	/**
	 * Adds additional filters for HUs.
	 *
	 * @param filter
	 */
	public final void setAdditionalHUFilter(final IQueryFilter<I_M_HU> filter)
	{
		additionalFilter = filter;
	}

	/**
	 * Returns the additional HU filter. This is normally set from the Frame (for different POSs).
	 *
	 * @return
	 */
	public final IQueryFilter<I_M_HU> getAdditionalHUFilter()
	{
		return additionalFilter;
	}

	public final void addPropertyChangeListener(final PropertyChangeListener listener)
	{
		pcs.removePropertyChangeListener(listener); // make sure we are not adding it twice
		pcs.addPropertyChangeListener(listener);
	}

	public final void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener)
	{
		pcs.removePropertyChangeListener(propertyName, listener); // make sure we are not adding it twice
		pcs.addPropertyChangeListener(propertyName, listener);
	}

	protected final void firePropertyChange(final String propertyName, final Object oldValue, final Object newValue)
	{
		if (pcs != null)
		{
			pcs.firePropertyChange(propertyName, oldValue, newValue);
		}
	}

	/**
	 * Edit Handling Units associated with selected rows
	 *
	 * @param editorCallback UI Editor; it returns true if user actually edited something (i.e. he/she did not cancel)
	 */
	public final void doProcessSelectedLines(final IHUEditorCallback<HUEditorModel> editorCallback)
	{
		try
		{
			if (HUConstants.is_07277_createHUsInMemory())
			{
				POJOLookupMap.destroyThreadLocalStorage();  // make sure we are creating a new one
				final POJOLookupMap memDatabase = POJOLookupMap.createThreadLocalStorage();
				memDatabase.addAppliesIncludeOnlyTablenamePrefix("M_HU");
				memDatabase.addAppliesExcludeTablenamePrefix("M_HU_PI");
			}

			doProcessSelectedLines0(editorCallback);
		}
		finally
		{
			if (HUConstants.is_07277_createHUsInMemory())
			{
				// TODO: save in memory objects back to database
				POJOLookupMap.destroyThreadLocalStorage();
			}
		}
	}

	private final void doProcessSelectedLines0(final IHUEditorCallback<HUEditorModel> editorCallback)
	{
		Check.assumeNotNull(editorCallback, "editorCallback not null");

		//
		// Get selected rows
		final Set<IPOSTableRow> selectedRows = getRowsSelected();
		if (!allowNoRowsSelection && selectedRows.isEmpty())
		{
			// there is no need to open the HUEditor at this point because ATM we can't do anything without document lines
			throw new AdempiereException("@" + AbstractHUSelectModel.MSG_ErrorNoDocumentLineSelected + "@");
		}

		try (final ITerminalContextReferences references = getTerminalContext().newReferences())
		{
			//
			// Create HU Editor Model
			final HUEditorModel huEditorModel = createHUEditorModel(selectedRows, editorCallback);
			if (huEditorModel == null)
			{
				// NOTE: we consider that "createHUEditorModel" explicitly said that we shall do nothing
				return;
			}

			//
			// Call the actual UI Editor and wait for it's answer
			final boolean edited = editorCallback.editHUs(huEditorModel);

			// Make sure everything that was in the cache it's flushed. We do this because:
			// * the processing is happending outside of our HUKeys (in most of the cases)
			// * we want to have fresh data from this point on!
			if (HUConstants.is08793_HUSelectModel_ResetCacheBeforeProcess())
			{
				getTerminalContext().getService(IHUKeyFactory.class).clearCache();
			}

			//
			// If user actually edited something, try processing selected rows/lines
			if (edited)
			{
				processRows(selectedRows, huEditorModel);
			}
		}
		catch (Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}

		//
		// Do not refresh the lines here, because this code is run within "AbstractHUSelect*Panel*.doProcessSelectedLines()"
		// and that panel's doProcessSelectedLines() method is running within its own terminal references that are destroyed after that panel's doProcessSelectedLines() finished.
		// This means, that *if* refreshLines() creates any buttons and stuffs, they will be disposed really soon
		// refreshLines(true);

	}

	protected void processRows(final Set<IPOSTableRow> selectedRows, final HUEditorModel huEditorModel)
	{
		final IPOSFiltering service = getService();
		final Properties ctx = getCtx();

		final Set<I_M_HU> selectedHUs = huEditorModel.getSelectedHUs();
		service.processRows(ctx, selectedRows, selectedHUs);
	}

	/**
	 * Creates and configures {@link HUEditorModel} to be used when {@link #doProcessSelectedLines(IHUEditorCallback)}.
	 *
	 * NOTE: never call this method directly.
	 *
	 * @param rows
	 * @param editorCallback
	 * @return created editor model or null if it does not apply on current status
	 */
	protected abstract HUEditorModel createHUEditorModel(final Collection<IPOSTableRow> rows, IHUEditorCallback<HUEditorModel> editorCallback);

	/**
	 * Gets underlying object from current selected row.
	 *
	 * If more then one rows are selected, the first one will be considered only.
	 *
	 * If there are no rows selected, null will be returned.
	 *
	 * @return current selected model (persistent object)
	 */
	public final Object getSelectedObject()
	{
		return getSelectedObject(Object.class);
	}

	public final <T> T getSelectedObject(final Class<T> clazz)
	{
		final Set<IPOSTableRow> rows = getRowsSelected();
		if (rows.isEmpty())
		{
			return null;
		}

		final IPOSTableRow rowFirst = rows.iterator().next();
		return getReferencedObject(rowFirst, clazz);
	}

	protected final <T> T getReferencedObject(final IPOSTableRow row, final Class<T> clazz)
	{
		if (row == null)
		{
			return null;
		}

		final Object referencedObject = service.getReferencedObject(row);
		final T model = InterfaceWrapperHelper.create(referencedObject, clazz);
		return model;
	}

	protected final void load()
	{
		// 06472 : We take the warehouses from the filtering service and keep only the ones allowed in the POS profile.
		final List<I_M_Warehouse> warehousesAll = service.retrieveWarehouses(getCtx());
		final List<I_M_Warehouse> warehouses = posAccessBL.filterWarehousesByProfile(getCtx(), warehousesAll);
		Check.assumeNotEmpty(warehouses, "At least one warehouse shall be found");
		warehouseKeyLayout.createAndSetKeysFromWarehouses(warehouses);
	}

	protected final void setLayoutsEnabled(final boolean enabled)
	{
		warehouseKeyLayout.setEnabledKeys(enabled);
		vendorKeyLayout.setEnabledKeys(enabled);
		rowsTableModel.setEditable(enabled);
	}

	protected final void setSingleWarehouse(final I_M_Warehouse warehouse)
	{
		final WarehouseKey newWarehouseKey = new WarehouseKey(getTerminalContext(), warehouse);
		warehouseKeyLayout.resetKeys();
		final ArrayList<ITerminalKey> listModel = new ArrayList<ITerminalKey>(1);
		listModel.add(newWarehouseKey);
		warehouseKeyLayout.setKeys(listModel);
		warehouseKeyLayout.setSelectedKey(newWarehouseKey);
		refreshLines(true);
	}

	protected void setActionButtonsEnabled()
	{
		// By default, do nothing;
	}

	@Override
	protected void finalize() throws Throwable
	{
		dispose();
	};

	@Override
	@OverridingMethodsMustInvokeSuper
	public void dispose()
	{
		_lines = Collections.emptyList();
		_linesAll = Collections.emptyList();

		if (rowsTableModel != null)
		{
			if (rowTableModelListener != null)
			{
				rowsTableModel.removeTerminalTableModelListener(rowTableModelListener);
				rowTableModelListener = null;
			}
		}
		disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed;
	}

	/**
	 * Shall we display the "CloseLine" button?
	 *
	 * NOTE: to be overridden by implementors. By default returns <code>false</code> (for backward compatibility).
	 *
	 * @return
	 */
	public boolean isDisplayCloseLinesButton()
	{
		return displayCloseLinesButton;
	}

	/**
	 * Sets if we shall display the "CloseLine" button.
	 *
	 * @param displayCloseLinesButton
	 */
	protected void setDisplayCloseLinesButton(final boolean displayCloseLinesButton)
	{
		this.displayCloseLinesButton = displayCloseLinesButton;
	}

	/**
	 * Closes selected lines
	 */
	public final void doCloseLines()
	{
		final IPOSFiltering service = getService();

		final Set<IPOSTableRow> selectedRows = getRowsSelected();
		service.closeRows(selectedRows);
		refreshLines(true);
	}

	public boolean isDisplayPhotoShootButton()
	{
		return displayPhotoShootButton;
	}

	protected void setDisplayPhotoShootButton(final boolean displayPhotoShootButton)
	{
		this.displayPhotoShootButton = displayPhotoShootButton;
	}
}
