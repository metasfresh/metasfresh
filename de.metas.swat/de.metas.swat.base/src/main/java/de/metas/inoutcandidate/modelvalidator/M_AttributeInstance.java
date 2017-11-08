package de.metas.inoutcandidate.modelvalidator;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.ModelValidator;

import de.metas.storage.IStorageListeners;
import de.metas.storage.IStorageSegment;
import de.metas.storage.impl.ImmutableStorageAttributeSegment;
import de.metas.storage.impl.ImmutableStorageSegment;

@Validator(I_M_AttributeInstance.class)
public class M_AttributeInstance
{
	/**
	 * Fire {@link IStorageSegment} changed when an {@link I_M_AttributeInstance} is changed.
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
		final ImmutableStorageAttributeSegment attributeSegment = new ImmutableStorageAttributeSegment(ai.getM_AttributeSetInstance_ID(), ai.getM_Attribute_ID());

		final ImmutableStorageSegment storageSegment = ImmutableStorageSegment.builder()
				.attributeSegment(attributeSegment)
				.build();

		Services.get(IStorageListeners.class).notifyStorageSegmentChanged(storageSegment);
	}
}
