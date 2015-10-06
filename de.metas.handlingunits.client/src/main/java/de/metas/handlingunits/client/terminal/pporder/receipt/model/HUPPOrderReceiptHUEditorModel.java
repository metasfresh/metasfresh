package de.metas.handlingunits.client.terminal.pporder.receipt.model;

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


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.api.IPPOrderBOMBL;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.lutuconfig.model.CUKey;
import de.metas.handlingunits.ddorder.api.IHUDDOrderBL;
import de.metas.handlingunits.document.impl.NullHUDocumentLineFinder;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;

/**
 * HUEditor which displays all HUs for Manufacturing Order's Warehouse and BPartner.
 *
 * User can do further editing and then move the HUs forward through DD Order Line (if any).
 *
 * @author tsa
 *
 */
public class HUPPOrderReceiptHUEditorModel extends HUEditorModel
{
	//
	// Services
	// private final IDDOrderBL ddOrderBL = Services.get(IDDOrderBL.class);
	private final IDDOrderDAO ddOrderDAO = Services.get(IDDOrderDAO.class);
	private final IHUDDOrderBL huDDOrderBL = Services.get(IHUDDOrderBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	//
	// Parameters
	private final I_PP_Order ppOrder;

	private final List<I_PP_Order_BOMLine> ppOrderBOMLines;

	/**
	 * This indicates the selected cuKey (in case there is any)
	 */
	private final CUKey cuKey;

	/**
	 * Initialize the PPOrder and the pp order bom lines
	 *
	 * @param terminalContext
	 * @param ppOrder
	 * @param ppOrderBOMLines
	 * @param loadHUs in case it's true, also load all the already existing HUs from the locator
	 */
	public HUPPOrderReceiptHUEditorModel(final ITerminalContext terminalContext,
			final I_PP_Order ppOrder,
			final List<I_PP_Order_BOMLine> ppOrderBOMLines,
			final CUKey cuKey,
			final boolean loadHUs)
	{
		super(terminalContext);

		Check.assumeNotNull(ppOrder, "ppOrder not null");
		this.ppOrder = ppOrder;

		this.ppOrderBOMLines = ppOrderBOMLines;

		// The selected cuKey shall be propagated for further processing

		this.cuKey = cuKey;

		// In case the existing HUs shall not be loaded in the model, do nothing
		if (!loadHUs)
		{
			return;
		}
		//
		// Add finished goods product
		final Set<Integer> productIds = new HashSet<Integer>();
		productIds.add(ppOrder.getM_Product_ID());

		//
		// Add co/by-products from BOM lines
		final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
		for (final I_PP_Order_BOMLine ppOrderBOMLine : ppOrderBOMLines)
		{
			if (!ppOrderBOMBL.isReceipt(ppOrderBOMLine))
			{
				continue;
			}

			productIds.add(ppOrderBOMLine.getM_Product_ID());
		}

		//
		// Load All HUs for manufacturing order's Warehouse and BPartner
		final int bpartnerId = ppOrder.getC_BPartner_ID();
		final IHUQueryBuilder husQuery = handlingUnitsDAO.createHUQueryBuilder()
				.setContext(terminalContext)
				.setOnlyTopLevelHUs()
				.addOnlyInWarehouseId(ppOrder.getM_Warehouse_ID())
				.addOnlyInBPartnerId(bpartnerId <= 0 ? null : bpartnerId) // in case BP is not set, search for HUs without BPartner
				.addOnlyWithProductIds(productIds)
				.setHUStatus(X_M_HU.HUSTATUS_Active) // only active ones
		//
		;

		setRootHUKey(husQuery);
	}

	/**
	 * Creates a root {@link IHUKey} which contains given HUs and set it to this model
	 *
	 * @param husQuery
	 */
	private void setRootHUKey(final IHUQueryBuilder husQuery)
	{
		final ITerminalContext terminalContext = getTerminalContext();
		final IHUKeyFactory keyFactory = terminalContext.getService(IHUKeyFactory.class);
		final IHUKey rootHUKey = keyFactory.createRootKey(husQuery, NullHUDocumentLineFinder.instance);
		setRootHUKey(rootHUKey);
	}

	public I_PP_Order getPP_Order()
	{
		return ppOrder;
	}

	public List<I_PP_Order_BOMLine> getOrderBomLines()
	{
		return ppOrderBOMLines;
	}

	public CUKey getCUKey()
	{
		return cuKey;
	}

	public void doMoveForwardSelectedHUs()
	{
		final I_PP_Order ppOrder = getPP_Order();
		final I_M_Product product = ppOrder.getM_Product();

		final List<IHUProductStorage> huProductStorages = getSelectedHUProductStorages(product);
		if (huProductStorages.isEmpty())
		{
			// Nothing selected. Ignore it silently
			return;
		}

		final List<I_DD_OrderLine> forwardDDOrderLines = ddOrderDAO.retrieveForwardDDOrderLinesQuery(ppOrder)
				.create()
				.list();

		if (forwardDDOrderLines.isEmpty())
		{
			// No Forward DD Order Line found
			// NOTE: per Mark's request, don't show any error, just ignore it
			return;
		}

		huDDOrderBL.createMovements(forwardDDOrderLines, huProductStorages);
	}
}
