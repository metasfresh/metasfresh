package de.metas.handlingunits.attributes.sscc18.impl;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.attribute.Constants;
import de.metas.handlingunits.attributes.sscc18.ISSCC18CodeDAO;
import de.metas.util.Services;

public class SSCC18CodeDAO implements ISSCC18CodeDAO
{
	@Override
	public AttributeId retrieveSSCC18AttributeId()
	{
		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
		return attributesRepo.retrieveAttributeIdByValueOrNull(Constants.ATTR_SSCC18_Value);
	}

	@Override
	public I_M_Attribute retrieveSSCC18Attribute()
	{
		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
		final AttributeId attributeId = retrieveSSCC18AttributeId();
		return attributesRepo.getAttributeById(attributeId);
	}
}
