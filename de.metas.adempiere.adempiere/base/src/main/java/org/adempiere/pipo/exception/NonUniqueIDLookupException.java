/**
 * 
 */
package org.adempiere.pipo.exception;

/*
 * #%L
 * ADempiere ERP - Base
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


import org.adempiere.exceptions.AdempiereException;

/**
 * Throws when IDFinder methods have found more then one id (result) for search criteria
 * @author Teo Sarca, teo.sarca@gmail.com
 *
 */
public class NonUniqueIDLookupException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3523717056935105698L;

	public NonUniqueIDLookupException(String what)
	{
		super("Non Unique ID Lookup found for "+what);
	}
}
