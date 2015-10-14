package de.metas.printing.esb.camel.commons;

/*
 * #%L
 * Metas :: Components :: Request-Response Framework for Mass Printing (SMX-4.5.2)
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


import de.metas.printing.esb.camel.inout.cxf.jaxrs.PRTRestService;

public final class Constants
{
	/**
	 * JMS endpoint URI for the topic where ADempiere listens for the transformation results
	 * 
	 * @see http://fusesource.com/docs/router/2.8/component_ref/_IDU_ActiveMQ.html
	 */
	public static final String EP_JMS_TO_AD = "{{activemq.topic.to.adempiere.nondurable}}";
	/**
	 * @see #EP_JMS_TO_AD
	 */
	public static final String EP_JMS_FROM_AD = "{{activemq.topic.from.adempiere}}";

	public static final String EP_CXF_RS = "cxfrs://http://{{http.domain}}?resourceClasses=" + PRTRestService.class.getName();

	public static final String EP_PRINT_PACKAGE_DATA_ARCHIVE = "{{archive.to.printing_client}}";

	private Constants()
	{
		super();
	}
}
