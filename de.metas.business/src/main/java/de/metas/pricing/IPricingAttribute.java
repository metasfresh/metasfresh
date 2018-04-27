package de.metas.pricing;

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


import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;

/**
 * Contains information about a particular pricing relevant attribute and its value. <br>
 * A list of instances can be obtained from {@link IPricingResult#getPricingAttributes()}, if there were attributes relevant for the price calculation.
 *
 */
public interface IPricingAttribute
{
	/**
	 * 
	 * @return never <code>null</code>
	 */
	I_M_Attribute getAttribute();

	/**
	 * Might be <code>null</code>
	 * 
	 * @return
	 */
	I_M_AttributeValue getAttributeValue();
}
