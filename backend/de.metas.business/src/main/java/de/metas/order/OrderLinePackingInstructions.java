package de.metas.order;

import de.metas.handlingunits.HUPIItemProductId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderLine;

import javax.annotation.Nullable;

/**
 * Reads the packing instruction of an order line.
 */
@UtilityClass
public class OrderLinePackingInstructions
{
	/**
	 * @param orderLineRecord the order line; the value that is currently set on the record counts, so it may be not yet saved
	 */
	@Nullable
	public static HUPIItemProductId extractHUPIItemProductId(@NonNull final I_C_OrderLine orderLineRecord)
	{
		final de.metas.interfaces.I_C_OrderLine orderLineWithPackingInstruction = InterfaceWrapperHelper.create(orderLineRecord, de.metas.interfaces.I_C_OrderLine.class);
		return HUPIItemProductId.ofRepoIdOrNull(orderLineWithPackingInstruction.getM_HU_PI_Item_Product_ID());
	}
}
