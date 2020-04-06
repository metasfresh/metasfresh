package de.metas.inoutcandidate.modelvalidator;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ImmutableShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ShipmentScheduleAttributeSegment;
import de.metas.util.Services;

@Interceptor(I_M_AttributeInstance.class)
@Component
public class M_AttributeInstance
{
	private final IAttributesBL attributesBL = Services.get(IAttributesBL.class);

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
		final AttributeId attributeId = AttributeId.ofRepoId(ai.getM_Attribute_ID());
		if (!attributesBL.isStorageRelevant(attributeId))
		{
			return;
		}

		final ShipmentScheduleAttributeSegment attributeSegment = ShipmentScheduleAttributeSegment.ofAttributeId(attributeId);

		final ImmutableShipmentScheduleSegment storageSegment = ImmutableShipmentScheduleSegment.builder()
				.attribute(attributeSegment)
				.build();

		Services.get(IShipmentScheduleInvalidateBL.class).notifySegmentChanged(storageSegment);
	}
}
