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
import org.adempiere.util.ISingletonService;
import org.adempiere.util.lang.IContextAware;
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
	 * This method will update the corresponding {@link I_M_AttributeValue} record.
	 * 
	 * When changing the implementation, please make sure that the model interceptors' <code>ifColumnsChanged</code> are in sync.
	 *
	 * @param materialTracking
	 */
	void createOrUpdateMaterialTrackingAttributeValue(I_M_Material_Tracking materialTracking);

	/**
	 * Sets the given material tracking to the given ASI.
	 *
	 * @param asi may be <code>null</code> <b>IF</b> also the given <code>materialTracking</code> is <code>null</code>. In that case, the method does nothing.
	 * @param materialTracking can be <code>null</code> if the material tracking shall be un-set from the given ASI.
	 */
	void setM_Material_Tracking(I_M_AttributeSetInstance asi, I_M_Material_Tracking materialTracking);

	/**
	 * Creates a new ASI with a <code>M_Material_Tracking</code> attribute instance and sets its value to the given <code>materialTracking</code>.
	 * If the given documentLine already has an ASI, that asi is copied. Otherwise a new ASI is created using the given documentLine's <code>M_Product</code>.
	 * <p>
	 * <b>IMPORTANT:</b> the method does <b>not save</b> the given <code>documentLine</code> after having changed its ASI-ID.
	 * 
	 * @param documentLine must be not <code>null</code> and convertible to {@link org.adempiere.mm.attributes.api.IAttributeSetInstanceAware IAttributeSetInstanceAware} via
	 *            {@link org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService IAttributeSetInstanceAwareFactoryService}
	 * @param materialTracking may be <code>null</code>, but even then, the new ASI will have an (empty) <code>M_Material_Tracking_ID</code> attribute.
	 */
	void createOrUpdateMaterialTrackingASI(Object documentLine, I_M_Material_Tracking materialTracking);

	/**
	 * Gets material tracking from given ASI
	 *
	 * @param asi
	 * @return
	 */
	I_M_Material_Tracking getMaterialTrackingOrNull(I_M_AttributeSetInstance asi);

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
