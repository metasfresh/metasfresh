package de.metas.handlingunits.attributes.sscc18;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Attribute;

public interface ISSCC18CodeDAO extends ISingletonService
{
	AttributeId retrieveSSCC18AttributeId();
	
	I_M_Attribute retrieveSSCC18Attribute();
}
