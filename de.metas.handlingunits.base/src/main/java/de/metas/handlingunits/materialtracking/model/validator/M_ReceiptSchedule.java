package de.metas.handlingunits.materialtracking.model.validator;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.materialtracking.IHUMaterialTrackingBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.materialtracking.IMaterialTrackingAttributeBL;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.util.Services;

@Interceptor(I_M_ReceiptSchedule.class)
public class M_ReceiptSchedule
{
	/**
	 * Updates the M_Material_Tracking attribute of the assigned HUs which are still in the planning status.
	 *
	 * @param rs
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_M_ReceiptSchedule.COLUMNNAME_M_AttributeSetInstance_ID)
	public void onMaterialTrackingASIChange(final I_M_ReceiptSchedule rs)
	{
		final IMaterialTrackingAttributeBL materialTrackingAttributeBL = Services.get(IMaterialTrackingAttributeBL.class);
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);

		// get the old and current material tracking (if any) and check if there was a change
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(rs.getM_AttributeSetInstance_ID());
		final I_M_Material_Tracking materialTracking = materialTrackingAttributeBL.getMaterialTrackingOrNull(asiId); // might be null
		final int materialTrackingId = materialTracking == null ? -1 : materialTracking.getM_Material_Tracking_ID();

		final I_M_ReceiptSchedule rsOld = InterfaceWrapperHelper.createOld(rs, I_M_ReceiptSchedule.class);
		final AttributeSetInstanceId asiOldId = AttributeSetInstanceId.ofRepoIdOrNone(rsOld.getM_AttributeSetInstance_ID());
		final I_M_Material_Tracking materialTrackingOld = materialTrackingAttributeBL.getMaterialTrackingOrNull(asiOldId);
		final int materialTrackingOldId = materialTrackingOld == null ? -1 : materialTrackingOld.getM_Material_Tracking_ID();

		if (materialTrackingOldId == materialTrackingId)
		{
			return; // the M_Material_Tracking part of the ASI was not changed; nothing to do
		}

		// update the HUs that are still in the planning stage
		final List<I_M_HU> topLevelHUs = huAssignmentDAO.retrieveTopLevelHUsForModel(rs);
		for (final I_M_HU hu : topLevelHUs)
		{
			// we only want to update HUs that are still in the planning stage. For the others, this rs is not in charge anymore
			final IHUMaterialTrackingBL huMaterialTrackingBL = Services.get(IHUMaterialTrackingBL.class);
			huMaterialTrackingBL.updateHUAttributeRecursive(hu, materialTracking, X_M_HU.HUSTATUS_Planning);
		}
	}
}
