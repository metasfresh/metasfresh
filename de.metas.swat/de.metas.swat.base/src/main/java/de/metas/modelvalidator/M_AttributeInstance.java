package de.metas.modelvalidator;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_M_Attribute;

import de.metas.util.Services;

@Validator(I_M_AttributeInstance.class)
public class M_AttributeInstance
{
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void updateASIDescription(final I_M_AttributeInstance instance)
	{
		final I_M_AttributeSetInstance asi = instance.getM_AttributeSetInstance();
		if (null != asi)
		{
			Services.get(IAttributeSetInstanceBL.class).setDescription(asi);
			InterfaceWrapperHelper.save(asi);
		}
	}

	/**
	 * In case {@link I_M_AttributeInstance#COLUMNNAME_Value} column changed and we deal with an List attribute, search and set corresponding attribute value record
	 *
	 * @param ai
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE } //
			, ifColumnsChanged = I_M_AttributeInstance.COLUMNNAME_Value)
	public void updateAttributeValueIfList(final I_M_AttributeInstance ai)
	{
		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);

		final I_M_Attribute attribute = attributesRepo.getAttributeById(ai.getM_Attribute_ID());

		//
		// Skip it if attribute value type is not of type List
		final String valueType = attribute.getAttributeValueType();
		if (!X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(valueType))
		{
			return;
		}

		//
		// Search for M_AttributeValue and set M_Attribute.M_AttributeValue_ID
		final String value = ai.getValue();
		final AttributeListValue attributeValue = attributesRepo.retrieveAttributeValueOrNull(attribute, value);
		ai.setM_AttributeValue_ID(attributeValue != null ? attributeValue.getId().getRepoId() : -1);
	}
}
