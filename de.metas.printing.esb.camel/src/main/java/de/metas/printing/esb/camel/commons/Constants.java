package de.metas.printing.esb.camel.commons;

/*
 * #%L
 * de.metas.printing.esb.camel
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

	/**
	 * Notes:
	 * <ul>
	 * <li>"rsServer" is defined in beans.xml. There we also define the json marshaler and unmarshaler.</li>
	 * <li>loggingFeatureEnabled: can be set to true in the properties. If true, it logs exactly what goes in and what goes out.</li>
	 * <li>synchronous=true: let the cxf endpoint wait for the result from metasfresh. Let it <b>not</b> return HTTP-code 204 if there was no result after 30 seconds.<br>
	 * Also see See https://metasfresh.atlassian.net/browse/FRESH-17</li>
	 * </ul>
	 */
	public static final String EP_CXF_RS = "cxfrs://bean://rsServer?loggingFeatureEnabled={{cxf.rs.loggingFeatureEnabled}}&synchronous=true";

	public static final String EP_PRINT_PACKAGE_DATA_ARCHIVE = "{{archive.to.printing_client}}";

	private Constants()
	{
		super();
	}
}
