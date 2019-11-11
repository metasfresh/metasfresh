package de.metas.handlingunits.client.terminal.pporder.receipt.model;

import org.compiere.model.I_M_Product;
import org.eevolution.api.ReceiptCostCollectorCandidate;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.ILUTUConfigurationEditor;
import de.metas.handlingunits.client.terminal.lutuconfig.model.CUKey;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.pporder.api.IPPOrderReceiptHUProducer;
import de.metas.quantity.Quantity;
import de.metas.util.Check;

public class HUPPOrderReceiptCUKey extends CUKey
{
	private final ILUTUConfigurationEditor lutuConfigurationEditor;
	private final ReceiptCostCollectorCandidate receiptCostCollectorCandidate;

	public HUPPOrderReceiptCUKey(
			final ITerminalContext terminalContext,
			final ILUTUConfigurationEditor lutuConfigurationEditor,
			final ReceiptCostCollectorCandidate receiptCostCollectorCandidate,
			final Quantity qtyToReceiveTarget)
	{
		super(terminalContext, receiptCostCollectorCandidate.getProductId());

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

	public final ReceiptCostCollectorCandidate getReceiptCostCollectorCandidate()
	{
		return receiptCostCollectorCandidate;
	}
	
	public final IPPOrderReceiptHUProducer createReceiptCandidatesProducer()
	{
		final ReceiptCostCollectorCandidate receiptCostCollectorCandidate = getReceiptCostCollectorCandidate();
		
		final IPPOrderReceiptHUProducer producer;
		if (receiptCostCollectorCandidate.getOrderBOMLine() != null)
		{
			producer = IPPOrderReceiptHUProducer.receivingByOrCoProduct(receiptCostCollectorCandidate.getOrderBOMLine());
		}

		else
		{
			producer = IPPOrderReceiptHUProducer.receivingMainProduct(receiptCostCollectorCandidate.getOrder());
		}
		
		return producer;
	}
}
