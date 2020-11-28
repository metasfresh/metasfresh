package de.metas.materialtracking.model.validator;

/*
 * #%L
 * de.metas.materialtracking
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

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.model.ModelValidator;

import de.metas.materialtracking.IMaterialTrackingAttributeBL;
import de.metas.materialtracking.model.I_M_InOutLine;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.util.Services;

@Interceptor(I_M_InOutLine.class)
public class M_InOutLine
{
	/**
	 * Makes sure that <b>if</b> the given <code>iol</code> has an ASI with a <code>M_Material_Tracking_ID</code> attribute, then that attribute-instance's value is also referenced from the iol's
	 * {@link I_M_InOutLine#COLUMNNAME_M_Material_Tracking_ID M_Material_Tracking_ID} column. Note that if the AIS has a nuln tracking, than this method sets the column to null.
	 *
	 * @param iol
	 */
	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_M_InOutLine.COLUMNNAME_M_AttributeSetInstance_ID })
	public void updateMaterialTrackingIDfromASI(final I_M_InOutLine iol)
	{
		final IMaterialTrackingAttributeBL materialTrackingAttributeBL = Services.get(IMaterialTrackingAttributeBL.class);

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(iol.getM_AttributeSetInstance_ID());

		if (!materialTrackingAttributeBL.hasMaterialTrackingAttribute(asiId))
		{
			return; // nothing to sync, the line's asi has no M_Material_Tracking attrib
		}

		final I_M_Material_Tracking materialTracking = materialTrackingAttributeBL.getMaterialTrackingOrNull(asiId);

		final int m_Material_Tracking_ID = materialTracking == null ? 0 : materialTracking.getM_Material_Tracking_ID();
		if (iol.getM_Material_Tracking_ID() != m_Material_Tracking_ID)
		{
			iol.setM_Material_Tracking(materialTracking);
		}
	}
}
