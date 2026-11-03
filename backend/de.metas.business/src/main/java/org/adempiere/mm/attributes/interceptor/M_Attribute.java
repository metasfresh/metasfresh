package org.adempiere.mm.attributes.interceptor;

import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.mm.attributes.api.impl.AttributeDAO;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_M_Attribute.class)
public class M_Attribute
{
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(@NonNull final I_M_Attribute record)
	{
		AttributeDAO.fromRecord(record); // validate
	}

}
