package de.metas.handlingunits.materialtracking;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_BOMLine;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.util.ISingletonService;

public interface IHUPPOrderMaterialTrackingBL extends ISingletonService
{
	/**
	 * Link the manufacturing order and the {@code materialTracking} via {@code M_Material_Tracking_Ref}.<br>
	 * If the respective {@code PP_Order} is also a quality inspection, then it also set {@code PP_Order.M_MaterialTracking_ID}.
	 * 
	 */
	void linkPPOrderToMaterialTracking(I_PP_Order_BOMLine ppOrderBOMLine, I_M_Material_Tracking materialTracking);

	/**
	 * Extracts HU's material tracking if any
	 * 
	 * @return material tracking or null
	 */
	I_M_Material_Tracking extractMaterialTrackingIfAny(IHUContext huContext, I_M_HU hu);

}
