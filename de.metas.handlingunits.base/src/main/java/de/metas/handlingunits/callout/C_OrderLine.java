package de.metas.handlingunits.callout;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.OrderLineHUPackingAware;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.order.api.IHUOrderBL;
import de.metas.order.IOrderLineBL;

@Callout(I_C_OrderLine.class)
public class C_OrderLine
{
	/**
	 * Task 06915: only updating on QtyEntered, but not on M_HU_PI_Item_Product_ID, because when the HU_PI_Item_Product changes, we want QtyEnteredTU to stay the same.
	 *
	 * @param orderLine
	 * @param field
	 */
	@CalloutMethod(columnNames = { I_C_OrderLine.COLUMNNAME_QtyEntered })
	public void updateQtyTU(final I_C_OrderLine orderLine, final ICalloutField field)
	{
		final IHUPackingAware packingAware = new OrderLineHUPackingAware(orderLine);
		Services.get(IHUPackingAwareBL.class).setQtyTU(packingAware);
		packingAware.setQty(packingAware.getQty());
	}

	/**
	 * Task 06915: If QtyEnteredTU or M_HU_PI_Item_Product_ID change, then update QtyEntered (i.e. the CU qty).
	 *
	 * @param orderLine
	 * @param field
	 */
	@CalloutMethod(columnNames = { I_C_OrderLine.COLUMNNAME_QtyEnteredTU, I_C_OrderLine.COLUMNNAME_M_HU_PI_Item_Product_ID })
	public void updateQtyCU(final I_C_OrderLine orderLine, final ICalloutField field)
	{
		final IHUPackingAware packingAware = new OrderLineHUPackingAware(orderLine);
		final Integer qtyPacks = packingAware.getQtyTU().intValue();
		Services.get(IHUPackingAwareBL.class).setQtyCUFromQtyTU(packingAware, qtyPacks);

		// Update lineNetAmt, because QtyEnteredCU changed : see task 06727
		Services.get(IOrderLineBL.class).updateLineNetAmt(orderLine, orderLine.getQtyEntered());
	}

	@CalloutMethod(columnNames = { I_C_OrderLine.COLUMNNAME_C_BPartner_ID
			, I_C_OrderLine.COLUMNNAME_QtyEntered
			, I_C_OrderLine.COLUMNNAME_M_HU_PI_Item_Product_ID
			, I_C_OrderLine.COLUMNNAME_M_Product_ID })
	public void onHURelevantChange(final I_C_OrderLine orderLine, final ICalloutField field)
	{
		final I_C_OrderLine ol = InterfaceWrapperHelper.create(orderLine, I_C_OrderLine.class);

		Services.get(IHUOrderBL.class).updateOrderLine(ol, field.getColumnName());
	}
}
