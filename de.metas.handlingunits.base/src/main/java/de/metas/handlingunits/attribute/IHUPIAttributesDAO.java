package de.metas.handlingunits.attribute;

/*
 * #%L
 * de.metas.handlingunits.base
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


import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Version;

public interface IHUPIAttributesDAO extends ISingletonService
{
	/**
	 * Retrieve all direct attributes, including those who are not active for current PI Version.
	 *
	 * @param version
	 * @return list of attributes
	 * @see #retrieveDirectPIAttributes(I_M_HU_PI_Version)
	 */
	List<I_M_HU_PI_Attribute> retrieveDirectPIAttributes(I_M_HU_PI huPI);

	/**
	 * Retrieve all direct attributes, including those who are not active.
	 *
	 * Direct attributes all those attributes which are assigned directly to this version (excluding other inherited attrbutes, e.g. from template PI)
	 *
	 * @param version
	 * @return list of attributes
	 */
	List<I_M_HU_PI_Attribute> retrieveDirectPIAttributes(I_M_HU_PI_Version version);

	/**
	 * Retrieve available PI Attributes for given PI Version.
	 *
	 * Mainly, returned list consists of
	 * <ul>
	 * <li>direct attributes - PI Attributes which are directly defined on given <code>version</code>
	 * <li>attributes from template (i.e. NoPI) - PI attributes which are assigned to NoPI (see {@link IHandlingUnitsDAO#retrieveNoPI(java.util.Properties)})
	 * </ul>
	 *
	 * <br>
	 * NOTE: all attributes will be returned out of transaction no matter what transaction <code>version</code> has (this is because we want to make use of caching)
	 *
	 * @param version
	 * @return available PI attributes
	 */
	List<I_M_HU_PI_Attribute> retrievePIAttributes(I_M_HU_PI_Version version);
	
	List<I_M_HU_PI_Attribute> retrievePIAttributes(Properties ctx, int M_HU_PI_Version_ID);

	/**
	 * Retrieve available PI Attributes assigned to current version of given <code>huPI</code>.
	 *
	 * For more informations, i strongly suggest to read documentation of {@link #retrievePIAttributes(I_M_HU_PI_Version)}.
	 *
	 * @param huPI
	 * @return available PI attributes
	 */
	List<I_M_HU_PI_Attribute> retrievePIAttributes(I_M_HU_PI huPI);
	

	/**
	 *
	 * @param huPIAttribute
	 * @return
	 * 		<ul>
	 *         <li>true if this attribute was defined by the standard template
	 *         <li>false if this attribute is a customization on a particular element (e.g.HU, ASI etc)
	 *         </ul>
	 * @task FRESH-578 #275
	 */
	boolean isTemplateAttribute(I_M_HU_PI_Attribute huPIAttribute);

}
