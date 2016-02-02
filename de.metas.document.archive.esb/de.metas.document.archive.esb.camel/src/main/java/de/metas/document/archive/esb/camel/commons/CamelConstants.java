package de.metas.document.archive.esb.camel.commons;

/*
 * #%L
 * de.metas.document.archive.esb.camel
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


/**
 * Camel Constants.
 * 
 * Constants from here are a simple binding of constants defined in:
 * <ul>
 * <li>archive.properties
 * </ul>
 * 
 * @author tsa
 * 
 */
public final class CamelConstants
{
	private CamelConstants()
	{
		super();
	}

	/**
	 * REST HTTP endpoint
	 * 
	 * See archive.properties
	 */
	public static final String EP_REST_HTTP = "{{ep.cxf.rs}}";

	/**
	 * JMS endpoint URI for the topic where ADempiere listens for the transformation results.
	 * 
	 * See archive.properties
	 */
	public static final String EP_ADEMPIERE_TO = "{{ep.adempiere.to}}";

	/**
	 * Endpoint to be used for error handling and deadletter channel
	 */
	public static final String EP_ERRORS = "{{cxf.rs.error}}";
}
