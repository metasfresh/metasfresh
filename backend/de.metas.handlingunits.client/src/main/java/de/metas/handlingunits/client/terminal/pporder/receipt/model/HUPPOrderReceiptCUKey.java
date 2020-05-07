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


import java.math.BigDecimal;

import org.adempiere.util.Check;
import org.compiere.model.I_M_Product;
import org.eevolution.api.IReceiptCostCollectorCandidate;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.ILUTUConfigurationEditor;
import de.metas.handlingunits.client.terminal.lutuconfig.model.CUKey;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.pporder.api.IPPOrderReceiptHUProducer;

public class HUPPOrderReceiptCUKey extends CUKey
{
	private final ILUTUConfigurationEditor lutuConfigurationEditor;
	private final IReceiptCostCollectorCandidate receiptCostCollectorCandidate;

	public HUPPOrderReceiptCUKey(
			final ITerminalContext terminalContext,
			final ILUTUConfigurationEditor lutuConfigurationEditor,
			final IReceiptCostCollectorCandidate receiptCostCollectorCandidate,
			final BigDecimal qtyToReceiveTarget)
	{
		super(terminalContext, receiptCostCollectorCandidate.getM_Product());

		Check.assumeNotNull(lutuConfigurationEditor, "lutuConfigurationEditor not null");
		this.lutuConfigurationEditor = lutuConfigurationEditor;

		Check.assumeNotNull(receiptCostCollectorCandidate, "receiptCostCollectorCandidate not null");
		this.receiptCostCollectorCandidate = receiptCostCollectorCandidate;

		setTotalQtyCU(qtyToReceiveTarget);
	}

	@Override
	protected String createName()
	{
		final I_M_Product product = getM_Product();
		return product.getName();
	}

	public final ILUTUConfigurationEditor getLUTUConfigurationEditor()
	{
		return lutuConfigurationEditor;
	}

	@Override
	public final I_M_HU_LUTU_Configuration getDefaultLUTUConfiguration()
	{
		if (lutuConfigurationEditor == null)
		{
			return null;
		}
		return lutuConfigurationEditor.getEditingLUTUConfiguration();
	}

	public final IReceiptCostCollectorCandidate getReceiptCostCollectorCandidate()
	{
		return receiptCostCollectorCandidate;
	}
	
	public final IPPOrderReceiptHUProducer createReceiptCandidatesProducer()
	{
		final IReceiptCostCollectorCandidate receiptCostCollectorCandidate = getReceiptCostCollectorCandidate();
		
		final IPPOrderReceiptHUProducer producer;
		if (receiptCostCollectorCandidate.getPP_Order_BOMLine() != null)
		{
			producer = IPPOrderReceiptHUProducer.receiveByOrCoProduct(receiptCostCollectorCandidate.getPP_Order_BOMLine());
		}

		else
		{
			producer = IPPOrderReceiptHUProducer.receiveMainProduct(receiptCostCollectorCandidate.getPP_Order());
		}
		
		return producer;
	}
}
