package de.metas.handlingunits.attributes.sscc18;

import org.adempiere.mm.attributes.AttributeId;
import org.compiere.model.I_M_Attribute;

import de.metas.util.ISingletonService;

public interface ISSCC18CodeDAO extends ISingletonService
{
	AttributeId retrieveSSCC18AttributeId();
	
	I_M_Attribute retrieveSSCC18Attribute();
}
