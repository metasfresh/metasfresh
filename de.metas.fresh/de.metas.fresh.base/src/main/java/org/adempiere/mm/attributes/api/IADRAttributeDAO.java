package org.adempiere.mm.attributes.api;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.compiere.model.I_M_Attribute;

import de.metas.fresh.model.I_C_BPartner;
import de.metas.util.ISingletonService;

public interface IADRAttributeDAO extends ISingletonService
{
	/**
	 * Gets M_Attribute_ID for storing the ADR.
	 * 
	 * @param adClientId
	 * @param adOrgId
	 * 
	 * @return M_Attribute_ID or -1
	 */
	AttributeId retrieveADRAttributeId(final int adClientId, final int adOrgId);

	I_M_Attribute retrieveADRAttribute(org.compiere.model.I_C_BPartner bpartner);
	I_M_Attribute retrieveADRAttribute(Properties ctx);

	/**
	 * Gets the attribute value related to ADR. The ADR attribute is set in Sysconfig de.metas.swat.ADRAttribute. If we don't find an attribute value, behavior is described by Sysconfig
	 * de.metas.swat.AttributeAction.
	 * 
	 * @param ctx
	 * @param partner
	 * @param isSOTrx will return different values for sales and purchase transactions
	 * @return the value of ADR attribute (if exists) or null
	 */
	AttributeListValue retrieveADRAttributeValue(Properties ctx, I_C_BPartner partner, boolean isSOTrx);


}
