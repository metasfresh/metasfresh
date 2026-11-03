package de.metas.dimension.model.validator;

import de.metas.dimension.IDimensionspecDAO;
import de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.ModelValidator;

@Interceptor(I_DIM_Dimension_Spec_Attribute.class)
@Callout(I_DIM_Dimension_Spec_Attribute.class)
public class DIM_Dimension_Spec_Attribute
{
	@Init
	public void registerCallout()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void validate(final I_DIM_Dimension_Spec_Attribute specAttr)
	{
		Services.get(IDimensionspecDAO.class).deleteAllSpecAttributeValues(specAttr);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_DIM_Dimension_Spec_Attribute.COLUMNNAME_M_Attribute_ID)
	@CalloutMethod(columnNames = I_DIM_Dimension_Spec_Attribute.COLUMNNAME_M_Attribute_ID)
	public void setAttributerValueType(final I_DIM_Dimension_Spec_Attribute specAttr)
	{
		final AttributeId attributeId = AttributeId.ofRepoIdOrNull(specAttr.getM_Attribute_ID());
		if (attributeId == null)
		{
			specAttr.setAttributeValueType(null);
		}

		else
		{
			final I_M_Attribute attribute = Services.get(IAttributeDAO.class).getAttributeRecordById(attributeId);
			final String attributeValueType = attribute.getAttributeValueType();

			specAttr.setAttributeValueType(attributeValueType);
		}
	}
}