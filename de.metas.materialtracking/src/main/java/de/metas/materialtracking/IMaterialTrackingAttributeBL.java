package de.metas.materialtracking;

import java.util.Optional;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;

import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.util.ISingletonService;

/**
 * Models the relation between {@link I_M_Material_Tracking} and {@link I_M_Attribute}.
 *
 * @author tsa
 *
 */
public interface IMaterialTrackingAttributeBL extends ISingletonService
{
	/**
	 * Gets attribute which is configured to store the {@code M_Material_Tracking_ID}.
	 *
	 * @return attributeId if the record exists and is activated; never return null.
	 */
	Optional<AttributeId> getMaterialTrackingAttributeId();

	/** @return the material tracking {@code M_Attribute} record; never return null, but fails if the record does not exist or is not active. */
	I_M_Attribute getMaterialTrackingAttribute();

	/**
	 * Called by API when {@link I_M_Material_Tracking} record is changed.
	 *
	 * This method will update the corresponding attribute value record.
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
