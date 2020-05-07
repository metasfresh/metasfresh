/**
 *
 */
package de.metas.report.server;

/*
 * #%L
 * de.metas.report.server
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * @author kh
 *
 */
public final class ReportConstants
{
	public static final String SYSCONFIG_ReportsServerServlet = "de.metas.adempiere.report.jasper.JRServerServlet";
	private static final String SYSCONFIG_ReportsServerBaseUrl_DEFAULT = "http://localhost:8585/adempiereJasper";
	public static final String SYSCONFIG_ReportsServerServlet_DEFAULT = SYSCONFIG_ReportsServerBaseUrl_DEFAULT + "/ReportServlet";

	public static final String SYSCONFIG_BarcodeServlet = "de.metas.adempiere.report.barcode.BarcodeServlet";
	public static final String SYSCONFIG_BarcodeServlet_DEFAULT = SYSCONFIG_ReportsServerBaseUrl_DEFAULT + "/BarcodeServlet";

	// This needs to fit the Servlet mapping in the web.xml
	public static final String JRSERVERSERVLET_MGTSERVLET_SUFFIX = "/MgtServlet";
	// 03337: Actions for the reports management
	public static final String MGTSERVLET_PARAM_Action = "action";
	public static final String MGTSERVLET_ACTION_CacheReset = "cacheReset";

	/**
	 * Specifies an alternative SQL query to be used by the report.
	 *
	 * NOTE: this alternative query will be used only by reports which are supporting it.
	 * If the report does not support this parameter, it will be ignored.
	 */
	public static final String REPORT_PARAM_SQL_QUERY = "REPORT_SQL_QUERY";
	public static final String REPORT_PARAM_SQL_QUERY_AD_PInstance_ID_Placeholder = "@AD_PInstance_ID@";

	public static final String REPORT_PARAM_BARCODE_URL = "barcodeURL";

	public static final String REPORT_PARAM_REPORT_FORMAT = "ReportFormat";
}
