package de.metas.inoutcandidate.modelvalidator;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.ModelValidator;

import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ImmutableShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ShipmentScheduleAttributeSegment;
import de.metas.util.Services;

@Validator(I_M_AttributeInstance.class)
public class M_AttributeInstance
{
	/**
	 * Fire {@link IShipmentScheduleSegment} changed when an {@link I_M_AttributeInstance} is changed.
	 *
	 * NOTE: there is no point to trigger this on AFTER_NEW because then an segment change will be fired on other level.
	 *
	 * @param ai attribute instance
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = {
			I_M_AttributeInstance.COLUMNNAME_IsActive,
			I_M_AttributeInstance.COLUMNNAME_M_Attribute_ID,
			I_M_AttributeInstance.COLUMNNAME_Value,
			I_M_AttributeInstance.COLUMNNAME_ValueNumber
	})
	public void fireAttributeChangeForAllProductsPartnersAndLocators(final I_M_AttributeInstance ai)
	{
		final ShipmentScheduleAttributeSegment attributeSegment = ShipmentScheduleAttributeSegment.of(
				AttributeSetInstanceId.ofRepoId(ai.getM_AttributeSetInstance_ID()),
				AttributeId.ofRepoId(ai.getM_Attribute_ID()));

		final ImmutableShipmentScheduleSegment storageSegment = ImmutableShipmentScheduleSegment.builder()
				.attributeSegment(attributeSegment)
				.build();

		Services.get(IShipmentScheduleInvalidateBL.class).notifySegmentChanged(storageSegment);
	}
}
