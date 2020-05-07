package de.metas.handlingunits.client.terminal.ddorder.view;

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


import java.util.Set;

import org.eevolution.model.I_PP_Order;

import de.metas.adempiere.form.terminal.IConfirmPanel;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKeyPanel;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.ddorder.model.DDOrderHUSelectModel;
import de.metas.handlingunits.client.terminal.ddorder.model.IDDOrderTableRow;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.select.api.IPOSTableRow;
import de.metas.handlingunits.client.terminal.select.model.IHUEditorCallback;
import de.metas.handlingunits.client.terminal.select.view.AbstractHUSelectPanel;

public class DDOrderHUSelectPanel extends AbstractHUSelectPanel<DDOrderHUSelectModel>
{
	private ITerminalKeyPanel ddOrderPanel;

	private static final String MSG_CloseDDOrderLine = "CloseDDOrderLine?";

	public DDOrderHUSelectPanel(final ITerminalContext terminalContext)
	{
		super(terminalContext, new DDOrderHUSelectModel(terminalContext));

		initComponents();
		initLayout();
	}

	private void initComponents()
	{
		final ITerminalFactory factory = getTerminalFactory();

		final DDOrderHUSelectModel model = getModel();

		//
		// warehouse
		final IKeyLayout warehouseKeyLayout = model.getWarehouseKeyLayout();
		warehousePanel = factory.createTerminalKeyPanel(warehouseKeyLayout);

		//
		// vendors
		final IKeyLayout vendorKeyLayout = model.getVendorKeyLayout();
		bpartnersPanel = factory.createTerminalKeyPanel(vendorKeyLayout);

		//
		// dd orders
		final IKeyLayout purchaseOrderKeyLayout = model.getDDOrderKeyLayout();
		ddOrderPanel = factory.createTerminalKeyPanel(purchaseOrderKeyLayout);

		//
		// Lines table/panel
		linesTable = factory.createTerminalTable2(IPOSTableRow.class);
		linesTable.setModel(model.getRowsModel());
		// resultPanel = new HUDocumentSelectResultPanel(model);
	}

	@Override
	protected void doProcessSelectedLines(final DDOrderHUSelectModel model)
	{
		final IHUEditorCallback<HUEditorModel> editorCallback = new DDOrderHUEditorCallback(this);
		model.doProcessSelectedLines(editorCallback);
	}

	private void initLayout()
	{
		final IConfirmPanel confirmPanel = getConfirmPanel();

		createPanel(warehousePanel, "dock north, growx, hmin 15%");
		createPanel(bpartnersPanel, "dock north, growx, hmin 15%");
		createPanel(ddOrderPanel, "dock north, growx, hmin 15%");

		// createPanel(panel, resultPanel, "dock north, hmin 40%");
		createPanel(linesTable, "dock north, hmin 40%");
		createPanel(confirmPanel, "dock south, hmax 15%");
	}

	/**
	 * Used when manually opening the DD order panel for a particular order.
	 *
	 * @param pp_Order
	 */
	public void setContextManufacturingOrder(final I_PP_Order pp_Order)
	{
		final DDOrderHUSelectModel model = getModel();
		model.setContextManufacturingOrder(pp_Order);
	}

	public void setWarehouseOverrideId(final int warehouseOverrideId)
	{
		final DDOrderHUSelectModel model = getModel();
		model.setWarehouseOverrideId(warehouseOverrideId);
	}

	@Override
	protected final boolean beforeCloseLine()
	{
		final Set<IPOSTableRow> rows = getModel().getRowsSelected();

		boolean okToClose = true;
		for (final IPOSTableRow row : rows)
		{
			if (!(row instanceof IDDOrderTableRow))
			{
				// shall never happen
				continue;
			}

			final IDDOrderTableRow ddOrderTableRow = (IDDOrderTableRow)row;

			if (ddOrderTableRow.getQtyDelivered().compareTo(ddOrderTableRow.getQtyOrdered()) < 0)
			{
				okToClose = false;
				break;
			}
		}

		if (!okToClose)
		{
			final boolean closeHUEditor = getTerminalFactory().ask(this)
					.setAD_Message(MSG_CloseDDOrderLine)
					.setDefaultAnswer(false) // cancel by default, to prevent user mistakes
					.getAnswer();
			if (!closeHUEditor)
			{
				return false;
			}
		}

		return true;
	}
}
