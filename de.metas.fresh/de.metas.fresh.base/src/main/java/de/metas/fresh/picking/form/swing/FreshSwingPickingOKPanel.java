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


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.ListSelectionModel;

import org.adempiere.ui.api.IWindowBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.apps.form.FormFrame;
import org.compiere.minigrid.IMiniTable;
import org.compiere.minigrid.MiniTable;
import org.compiere.util.Util.ArrayKey;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.IPackingDetailsModel;
import de.metas.adempiere.form.IPackingItem;
import de.metas.adempiere.form.terminal.IConfirmPanel;
import de.metas.adempiere.form.terminal.ITerminalTable;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.fresh.picking.FreshPackingDetailsMd;
import de.metas.fresh.picking.form.FreshPackingItemHelper;
import de.metas.fresh.picking.form.FreshPackingMd;
import de.metas.fresh.picking.form.FreshPackingMdLinesComparator;
import de.metas.fresh.picking.form.FreshSwingPackageTerminal;
import de.metas.fresh.picking.form.FreshSwingPickingMiniTableColorProvider;
import de.metas.handlingunits.client.terminal.ddorder.form.DDOrderHUSelectForm;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.terminal.form.swing.AbstractPackageTerminal;
import de.metas.picking.terminal.form.swing.SwingPickingOKPanel;
import de.metas.picking.terminal.form.swing.SwingPickingTerminalPanel;

/**
 * Picking First Window Panel (fresh)
 * 
 * @author cg
 * 
 */
public class FreshSwingPickingOKPanel extends SwingPickingOKPanel
{
	/**
	 * Existing AD_Message (created in 2003) with EntityType='D'. German message text is "Fehler".
	 */
	// task 05582
	private static final String MSG_ERRORS = "Errors";

	// task 05582
	private static final String ACTION_QuickInv = "FreshSwingPickingOKPanel_QuickInv";

	// task fresh_05763
	private static final String ACTION_ShipperTransportation = "FreshSwingPickingOKPanel_ShipperTransportation";

	// task fresh_06982
	private static final String ACTION_DDOrder = "FreshSwingPickingOKPanel_DDOrder";

	public FreshSwingPickingOKPanel(final SwingPickingTerminalPanel basePanel)
	{
		super(basePanel);

		final FreshPropertyChangeListener propertyChangelistener = new FreshPropertyChangeListener();
		// task 05582: add button to open window "Eigenverbrauch (metas)"
		getConfirmPanel().addButton(ACTION_QuickInv);
		// task fresh_05763: add button to open window "Speditionsauftrag"
		getConfirmPanel().addButton(ACTION_ShipperTransportation);
		// task fresh_06982: add button to open window "Bereitstellung (POS)"
		getConfirmPanel().addButton(ACTION_DDOrder);
		getConfirmPanel().addListener(propertyChangelistener);
	}

	@Override
	public void configureMiniTable(final IMiniTable miniTable)
	{
		super.configureMiniTable(miniTable);
		miniTable.setColorProvider(new FreshSwingPickingMiniTableColorProvider(this));
	}

	@Override
	protected FreshPackingMd createPackingModel(final ITerminalTable lines)
	{
		final ITerminalContext terminalContext = getTerminalContext();
		final FreshPackingMd model = new FreshPackingMd(terminalContext);
		model.setMiniTable(lines);

		model.setTableRowKeysComparator(new FreshPackingMdLinesComparator(model));

		return model;
	}

	@Override
	public void setSelectionModel(final IMiniTable miniTable)
	{
		if (miniTable instanceof MiniTable)
		{
			final MiniTable mTable = (MiniTable)miniTable;
			mTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		}
	}

	@Override
	protected Collection<IPackingItem> createUnallocatedLines(final List<OlAndSched> olsAndScheds, final boolean displayNonItems)
	{
		final Collection<IPackingItem> unallocatedLines = new ArrayList<>();
		final Map<ArrayKey, IPackingItem> key2Sched = new HashMap<>();

		for (final OlAndSched oldAndSched : olsAndScheds)
		{
			final I_M_ShipmentSchedule sched = oldAndSched.getSched();
			if (sched.isDisplayed() || displayNonItems)
			{
				final BigDecimal qtyToDeliverTarget = Services.get(IShipmentScheduleEffectiveBL.class).getQtyToDeliver(sched);
				
				// task 08153: these code-lines are obsolete now, because the sched's qtyToDeliver(_Override) has the qtyPicked already factored in
				// final BigDecimal qtyPicked = shipmentScheduleAllocBL.getQtyPicked(sched);
				// final BigDecimal qtyToDeliver = qtyToDeliverTarget.subtract(qtyPicked == null ? BigDecimal.ZERO : qtyPicked);
				final Map<I_M_ShipmentSchedule, BigDecimal> schedWithQty = Collections.singletonMap(sched, qtyToDeliverTarget);

				final ArrayKey key = Services.get(IShipmentScheduleBL.class).mkKeyForGrouping(sched, true);

				IPackingItem item = key2Sched.get(key);
				if (item == null)
				{
					item = FreshPackingItemHelper.create(schedWithQty);
					assert item.getGroupingKey() == key.hashCode();

					key2Sched.put(key, item);
					unallocatedLines.add(item);
				}
				else
				{
					item.addSchedules(schedWithQty);
				}
			}
		}

		return unallocatedLines;
	}

	@Override
	protected AbstractPackageTerminal createPackingTerminal(final IPackingDetailsModel detailsModel)
	{
		Check.assumeInstanceOf(detailsModel, FreshPackingDetailsMd.class, "detailsModel");
		final FreshPackingDetailsMd packingDetailsModel = (FreshPackingDetailsMd)detailsModel;

		final FreshSwingPackageTerminal packageTerminal = new FreshSwingPackageTerminal(this, packingDetailsModel);
		return packageTerminal;
	}

	@Override
	public IPackingDetailsModel createPackingDetailsModel(
			final Properties ctx,
			final int[] rows_NOTUSED,
			final Collection<IPackingItem> unallocatedLines,
			final List<I_M_ShipmentSchedule> nonItemScheds)
	{
		if (unallocatedLines.isEmpty())
		{
			return null;
		}

		final IPackingDetailsModel detailsModel = new FreshPackingDetailsMd(getTerminalContext(), unallocatedLines, nonItemScheds);

		executePacking(detailsModel);
		return detailsModel;
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	private class FreshPropertyChangeListener implements PropertyChangeListener
	{
		@Override
		public void propertyChange(final PropertyChangeEvent evt)
		{
			if (!IConfirmPanel.PROP_Action.equals(evt.getPropertyName()))
			{
				return;
			}
			final String action = String.valueOf(evt.getNewValue());
			if (ACTION_QuickInv.equals(action))
			{
				// FIXME find a better solution..maybe by introducing column AD_Window.InternalName? 540205 is the AD_Window_ID of the "Eigenverbrauch (metas)" window
				openDynamicWindow(540205);
			}
			else if (ACTION_ShipperTransportation.equals(action))
			{
				// FIXME find a better solution..maybe by introducing column AD_Window.InternalName?
				openDynamicWindow(540020);
			}
			else if (ACTION_DDOrder.equals(action))
			{
				//
				// Simulate DDOrder HU editor callback
				final ITerminalContext terminalContext = getTerminalContext();

				final DDOrderHUSelectForm form = new DDOrderHUSelectForm();
				form.init(terminalContext.getWindowNo(), new FormFrame());

				//
				// Force Target WarehouseId the one which is selected in Kommissionier Terminal
				{
					final FreshSwingPickingTerminalPanel basePanel = getPickingTerminalPanel();
					final int selectedWarehouseId = basePanel.getSelectedWarehouseId();
					form.setWarehouseOverrideId(selectedWarehouseId);
				}

				form.show();
			}
		}

		/**
		 * FIXME find a better solution..maybe by introducing column AD_Window.InternalName?<br>
		 * <br>
		 * Open dynamic window
		 * 
		 * @param AD_Window_ID
		 */
		private void openDynamicWindow(final int AD_Window_ID)
		{
			final boolean success = Services.get(IWindowBL.class).openWindow(AD_Window_ID);
			if (!success)
			{
				final int windowNo = getPickingTerminalPanel().getTerminalContext().getWindowNo();
				Services.get(IClientUI.class).error(windowNo, MSG_ERRORS);
			}
		}
	}

	/**
	 * Casts {@link FreshSwingPickingTerminalPanel} to parent
	 */
	@Override
	public FreshSwingPickingTerminalPanel getPickingTerminalPanel()
	{
		final SwingPickingTerminalPanel pickingTerminalPanel = super.getPickingTerminalPanel();
		return (FreshSwingPickingTerminalPanel)pickingTerminalPanel;
	}
}
