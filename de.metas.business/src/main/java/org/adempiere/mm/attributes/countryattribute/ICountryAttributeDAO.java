package org.adempiere.mm.attributes.countryattribute;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import org.compiere.model.I_C_Country;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;

import de.metas.util.ISingletonService;

public interface ICountryAttributeDAO extends ISingletonService
{
	/**
	 * Gets M_Attribute_ID for storing the country.
	 * 
	 * @param adClientId
	 * @param adOrgId
	 * @return M_Attribute_ID or -1
	 */
	AttributeId retrieveCountryAttributeId(final int adClientId, final int adOrgId);

	/**
	 * Gets the attribute value related to a country. The country attribute is set in Sysconfig de.metas.swat.CountryAttribute. If we don't find an attribute value, behavior is described by Sysconfig
	 * de.metas.swat.CountryAttributeAction.
	 * 
	 * Part of task 05839.
	 * 
	 * @param ctx
	 * @param country
	 * @return
	 */
	I_M_AttributeValue retrieveAttributeValue(Properties ctx, I_C_Country country);

	I_M_Attribute retrieveCountryAttribute(Properties ctx);

}
