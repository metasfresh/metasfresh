package de.metas.handlingunits.callout;

import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.OrderLineHUPackingAware;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.order.api.IHUOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.model.InterfaceWrapperHelper;

@Callout(I_C_OrderLine.class)
public class C_OrderLine
{

	private final IHUPackingAwareBL packingAwareBL = Services.get(IHUPackingAwareBL.class);

	/**
	 * Task 06915: only updating on QtyEntered, but not on M_HU_PI_Item_Product_ID, because when the HU_PI_Item_Product changes, we want QtyEnteredTU to stay the same.
	 *
	 */
	@CalloutMethod(columnNames = { I_C_OrderLine.COLUMNNAME_QtyEntered })
	public void updateQtyTU(final I_C_OrderLine orderLine, final ICalloutField field)
	{
		final IHUPackingAware packingAware = new OrderLineHUPackingAware(orderLine);
		packingAwareBL.setQtyTU(packingAware);
		packingAwareBL.setQtyLUFromQtyTU(packingAware);
		packingAware.setQty(packingAware.getQty());
	}

	/**
	 * Task 06915: If QtyEnteredTU or M_HU_PI_Item_Product_ID change, then update QtyEntered (i.e. the CU qty).
	 *
	 */
	@CalloutMethod(columnNames = { I_C_OrderLine.COLUMNNAME_QtyEnteredTU, I_C_OrderLine.COLUMNNAME_M_HU_PI_Item_Product_ID })
	public void updateQtyCU(final I_C_OrderLine orderLine, final ICalloutField field)
	{
		final IHUPackingAware packingAware = new OrderLineHUPackingAware(orderLine);
		setQtuCUFromQtyTU(packingAware);
		packingAwareBL.setQtyLUFromQtyTU(packingAware);

		// Update lineNetAmt, because QtyEnteredCU changed : see task 06727
		Services.get(IOrderLineBL.class).updateLineNetAmtFromQtyEntered(orderLine);
	}

	private void setQtuCUFromQtyTU(final IHUPackingAware packingAware)
	{
		final int qtyPacks = packingAware.getQtyTU().intValue();
		packingAwareBL.setQtyCUFromQtyTU(packingAware, qtyPacks);
	}

	@CalloutMethod(columnNames = { I_C_OrderLine.COLUMNNAME_QtyLU, I_C_OrderLine.COLUMNNAME_M_LU_HU_PI_ID })
	public void updateQtyTUCU(final I_C_OrderLine orderLine, final ICalloutField field)
	{
		final IHUPackingAware packingAware = new OrderLineHUPackingAware(orderLine);
		packingAwareBL.setQtyTUFromQtyLU(packingAware);
		updateQtyCU(orderLine, field);
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
