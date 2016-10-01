package de.metas.handlingunits.client.terminal.aggregate.view;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.AEnv;
import org.compiere.model.I_M_InOut;
import org.compiere.model.MQuery;
import org.compiere.model.X_M_InOut;

import de.metas.adempiere.beans.impl.UILoadingPropertyChangeListener;
import de.metas.adempiere.form.terminal.IConfirmPanel;
import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.handlingunits.client.terminal.aggregate.model.AggregateHUEditorModel;
import de.metas.handlingunits.client.terminal.aggregate.model.AggregateHUSelectModel;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.editor.view.HUEditorPanel;
import de.metas.handlingunits.client.terminal.inventory.model.InventoryHUSelectModel;
import de.metas.handlingunits.client.terminal.inventory.view.InventoryHUSelectPanel;
import de.metas.handlingunits.model.I_M_PickingSlot;

/**
 * Verdichtung (POS) HU Select View (first window)
 *
 * @author tsa
 *
 */
public class AggregateHUSelectPanel extends InventoryHUSelectPanel<AggregateHUSelectModel>
{
	private static final String ACTION_CreateShipment = "de.metas.handlingunits.client.terminal.aggregate.view.AggregateHUSelectPanel#CreateShipment";
	private static final String ACTION_CloseOpenPickedHUs = "Close_HU";
	private static final String ACTION_ShowDraftedShipments = "de.metas.handlingunits.client.terminal.aggregate.view.AggregateHUSelectPanel#ShowDraftedShipments";

	private static final String MSG_NoOpenHUsToClose = "de.metas.handlingunits.client.terminal.aggregate.view.AggregateHUSelectPanel#NoOpenHUsToClose";
	private static final String MSG_ConfirmCloseOpenHUs = "de.metas.handlingunits.client.terminal.aggregate.view.AggregateHUSelectPanel#ConfirmCloseOpenHUs";

	public AggregateHUSelectPanel(final AggregateHUSelectModel model)
	{
		super(model);
	}

	@Override
	protected void initComponents()
	{
		super.initComponents();

		final IConfirmPanel confirmPanel = getConfirmPanel();
		final ITerminalButton bCreateShipment = confirmPanel.addButton(ACTION_CreateShipment);
		bCreateShipment.addListener(new UILoadingPropertyChangeListener(getComponent())
		{
			@Override
			public void propertyChange0(final PropertyChangeEvent evt)
			{
				doCreateShipment();
			}
		});

		//
		// 07834: Close open picked HUs
		final ITerminalButton bCloseOpenPickedHUs = confirmPanel.addButton(ACTION_CloseOpenPickedHUs);
		bCloseOpenPickedHUs.addListener(new UILoadingPropertyChangeListener(getComponent())
		{
			@Override
			public void propertyChange0(final PropertyChangeEvent evt)
			{
				doCloseOpenPickedHUs();
			}
		});

		//
		// 07560: Display Drafted/Prepared shipments
		final ITerminalButton bShowDraftedShipments = confirmPanel.addButton(ACTION_ShowDraftedShipments);
		bShowDraftedShipments.addListener(new UILoadingPropertyChangeListener(getComponent())
		{
			@Override
			public void propertyChange0(final PropertyChangeEvent evt)
			{
				doShowDraftedShipments();
			}
		});
	}

	@Override
	protected void initLayout()
	{
		//
		// Warehouse Keys Panel
		{
			createPanel(warehousePanel, "dock north, growx, hmin 20%");
		}

		//
		// BPartner Keys Panel (if displayed)
		final InventoryHUSelectModel model = getModel();
		if (model.isDisplayVendorKeys())
		{
			createPanel(bpartnersPanel, "dock center, growx, hmin 20%");
		}

		//
		// Confirmation panel (i.e. bottom buttons)
		{
			final IConfirmPanel confirmPanel = getConfirmPanel();
			createPanel(confirmPanel, "dock south, hmax 50px");
		}

		//
		// BPartner Location Keys Panel (if displayed)
		if (model.isDisplayVendorKeys() && bpartnerLocationsPanel != null)
		{
			createPanel(bpartnerLocationsPanel, "dock south, growx, hmin 20%");
		}
	}

	@Override
	protected AggregateHUSelectModel getHUSelectModel()
	{
		return (AggregateHUSelectModel)super.getHUSelectModel();
	}

	@Override
	protected HUEditorPanel createHUEditorPanelInstance(final HUEditorModel huEditorModel)
	{
		final AggregateHUEditorModel aggregateEditorModel = (AggregateHUEditorModel)huEditorModel;
		final AggregateHUEditorPanel editorPanel = new AggregateHUEditorPanel(aggregateEditorModel);
		return editorPanel;
	}

	private void doCreateShipment()
	{
		final AggregateHUSelectModel model = getHUSelectModel();
		model.doCreateShipment();
	}

	private void doCloseOpenPickedHUs()
	{
		final AggregateHUSelectModel model = getHUSelectModel();
		final Properties ctx = getTerminalContext().getCtx();

		final List<I_M_PickingSlot> pickingSlotsToFree = model.getPickingSlotsToFree();

		//
		// If there is no open picking slots, warn the user
		final int countOpenHUs = pickingSlotsToFree.size();
		if (countOpenHUs <= 0)
		{
			final AdempiereException ex = new AdempiereException(ctx, MSG_NoOpenHUsToClose, new Object[] {});
			getTerminalFactory().showWarning(this, ITerminalFactory.TITLE_ERROR, ex);
			return;
		}

		//
		// Ask the user if he/she really wants to close those open picking slots
		final String msgConfirmCloseOpenHUs = msgBL.getMsg(ctx, MSG_ConfirmCloseOpenHUs, new Object[] { countOpenHUs });
		final boolean closeHUs = getTerminalFactory()
				.ask(this)
				.setAD_Message(ITerminalFactory.TITLE_WARN)
				.setAdditionalMessage(msgConfirmCloseOpenHUs)
				.setDefaultAnswer(false) // cancel by default, to prevent user mistakes
				.getAnswer();
		if (!closeHUs)
		{
			return;
		}

		//
		// Close the picking slots.
		// Use the same picking slots the user agrees upon (i.e until he presses OK in the existing pool someone else might already load something)
		model.doCloseOpenPickedHUs(pickingSlotsToFree);
	}

	/**
	 * Display Drafted/Prepared shipments
	 *
	 * @task 07560
	 */
	private void doShowDraftedShipments()
	{
		final AggregateHUSelectModel model = getHUSelectModel();

		final MQuery draftedShipmentsQuery = new MQuery(I_M_InOut.Table_Name);
		draftedShipmentsQuery.addRestriction(I_M_InOut.COLUMNNAME_DocStatus, Operator.EQUAL.asMQueryOperator(), X_M_InOut.DOCSTATUS_Drafted); // filter DR (drafted, prepared) documents
		draftedShipmentsQuery.setForceSOTrx(true); // force open the SO window

		// Filter by BPartner
		final int bpartnerId = model.getC_BPartner_ID(true); // having a BPartner selected is mandatory
		draftedShipmentsQuery.addRestriction(I_M_InOut.COLUMNNAME_C_BPartner_ID, Operator.EQUAL.asMQueryOperator(), bpartnerId); // filter by BPartner

		// Filter by BPartner Location (optional)
		final int bpartnerLocationId = model.getC_BPartner_Location_ID();
		if (bpartnerLocationId > 0)
		{
			draftedShipmentsQuery.addRestriction(I_M_InOut.COLUMNNAME_C_BPartner_Location_ID, Operator.EQUAL.asMQueryOperator(), bpartnerLocationId);
		}

		//
		// Open the shipments window, filtered by our selection
		AEnv.zoom(draftedShipmentsQuery);
	}
}
