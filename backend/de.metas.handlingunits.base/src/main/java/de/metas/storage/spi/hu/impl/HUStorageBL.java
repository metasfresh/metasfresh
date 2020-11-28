package de.metas.storage.spi.hu.impl;

import java.util.Set;

import org.adempiere.mm.attributes.AttributeId;

import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.attribute.IHUPIAttributesDAO;
import de.metas.handlingunits.attribute.PIAttributes;
import de.metas.storage.spi.hu.IHUStorageBL;
import de.metas.util.Services;

public class HUStorageBL implements IHUStorageBL
{
	@Override
	public Set<AttributeId> getAvailableAttributeIds()
	{
		final IHUPIAttributesDAO piAttributesDAO = Services.get(IHUPIAttributesDAO.class);
		final PIAttributes piAttributes = piAttributesDAO.retrievePIAttributes(HuPackingInstructionsVersionId.VIRTUAL);
		return piAttributes.getAttributeIds();
	}
}
