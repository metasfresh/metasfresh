package de.metas.materialtracking;

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


import java.util.Properties;

import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.model.IContextAware;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeValue;

import de.metas.materialtracking.model.I_M_Material_Tracking;

/**
 * Models the relation between {@link I_M_Material_Tracking} and {@link I_M_Attribute}.
 *
 * @author tsa
 *
 */
public interface IMaterialTrackingAttributeBL extends ISingletonService
{
	/**
	 * Gets attribute which is configured to store the M_Material_Tracking_ID.
	 *
	 * @param ctx
	 * @return attribute; never return null
	 */
	I_M_Attribute getMaterialTrackingAttribute(Properties ctx);

	/**
	 * Called by API when {@link I_M_Material_Tracking} record is changed.
	 *
	 * This method will update coresponding {@link I_M_AttributeValue} record.
	 *
	 * @param materialTracking
	 */
	void createOrUpdateMaterialTrackingAttributeValue(I_M_Material_Tracking materialTracking);

	/**
	 * Sets given material tracking to given ASI.
	 *
	 * @param asi
	 * @param materialTracking
	 */
	void setM_Material_Tracking(I_M_AttributeSetInstance asi, I_M_Material_Tracking materialTracking);

	/**
	 * Gets material tracking from given ASI
	 *
	 * @param asi
	 * @return
	 */
	I_M_Material_Tracking getMaterialTracking(I_M_AttributeSetInstance asi);

	I_M_Material_Tracking getMaterialTracking(IContextAware context, IAttributeSet attributeSet);

	String getMaterialTrackingIdStr(I_M_Material_Tracking materialTracking);

	/**
	 * Check if the given ASI has a material tracking attribute. Note that this attribute's value can still be <code>null</code>, even if this method returns <code>true</code>.
	 * 
	 * @param asi the asi to check
	 * @return <code>true</code> if the given asi's attribute set contains the <code>M_Attribute</code> with <code>value=M_Material_Tracking_ID.</code>
	 */
	boolean hasMaterialTrackingAttribute(I_M_AttributeSetInstance asi);

	/** @return true if material tracking attribute exists and it's set */
	boolean isMaterialTrackingSet(IContextAware context, IAttributeSet attributeSet);
}
