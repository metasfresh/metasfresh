package de.metas.handlingunits.materialtracking;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_BOMLine;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.util.ISingletonService;

public interface IHUPPOrderMaterialTrackingBL extends ISingletonService
{
	/**
	 * Cross link the manufacturing order and the <code>materialTracking</code>
	 * 
	 * @param ppOrderBOMLine
	 * @param materialTracking
	 */
	void linkPPOrderToMaterialTracking(I_PP_Order_BOMLine ppOrderBOMLine, I_M_Material_Tracking materialTracking);

	/**
	 * Extracts HU's material tracking if any
	 * 
	 * @param huContext
	 * @param hu
	 * @return material tracking or null
	 */
	I_M_Material_Tracking extractMaterialTrackingIfAny(IHUContext huContext, I_M_HU hu);

}
