package de.metas.order;

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.util.NumberUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.model.InterfaceWrapperHelper;

import javax.annotation.Nullable;

/**
 * Reads the packing instruction of an order line. The column belongs to the handling units module, so it is read by name.
 */
@UtilityClass
public class OrderLinePackingInstructions
{
	private static final String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";

	/**
	 * @param orderLineRecord the order line; the value that is currently set on the record counts, so it may be not yet saved
	 */
	@Nullable
	public static HUPIItemProductId extractHUPIItemProductId(@NonNull final Object orderLineRecord)
	{
		return HUPIItemProductId.ofRepoIdOrNull(NumberUtils.asInt(InterfaceWrapperHelper.getValueOrNull(orderLineRecord, COLUMNNAME_M_HU_PI_Item_Product_ID), 0));
	}
}
