/**
 * 
 */
package de.metas.adempiere.report.jasper;

/*
 * #%L
 * de.metas.report.jasper.commons
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
 * @author kh
 * 
 */
public final class JasperConstants
{

	public static final String SYSCONFIG_JRServerServlet = "de.metas.adempiere.report.jasper.JRServerServlet";
	public static final String SYSCONFIG_BarcodeServlet = "de.metas.adempiere.report.barcode.BarcodeServlet";

	public static final String SYSCONFIG_JRServerServer_DEFAULT = "http://localhost:8080/adempiereJasper";

	public static final String SYSCONFIG_JRServerServlet_DEFAULT = SYSCONFIG_JRServerServer_DEFAULT + "/ReportServlet";
	public static final String SYSCONFIG_BarcodeServlet_DEFAULT = SYSCONFIG_JRServerServer_DEFAULT + "/BarcodeServlet";

	// This needs to fit the Servlet mapping in the web.xml
	public static final String JRSERVERSERVLET_MGTSERVLET_SUFFIX = "/MgtServlet";

	// 03337: Actions for the Jasper management servlet.
	public static final String MGTSERVLET_PARAM_Action = "action";
	public static final String MGTSERVLET_ACTION_CacheReset = "cacheReset";

	/**
	 * Specifies an alternative SQL query to be used by the Jasper Report.
	 * 
	 * NOTE: this alternative query will be used only by jasper reports which are supporting it.
	 * If the jasper report does not support this parameter, it will be ignored.
	 */
	public static final String REPORT_PARAM_SQL_QUERY = "REPORT_SQL_QUERY";
	public static final String REPORT_PARAM_SQL_QUERY_AD_PInstance_ID_Placeholder = "@AD_PInstance_ID@";
}
