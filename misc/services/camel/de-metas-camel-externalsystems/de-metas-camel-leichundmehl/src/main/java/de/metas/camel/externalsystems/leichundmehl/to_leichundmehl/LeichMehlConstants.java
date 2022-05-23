/*
 * #%L
 * de-metas-camel-leichundmehl
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl;

public interface LeichMehlConstants
{
	String ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT = "ExportPPOrderRouteContext";

	String HEADER_FTP_PORT = "FTPPort";
	String HEADER_FTP_HOST = "FTPHost";
	String HEADER_FTP_USERNAME = "FTPUsername";
	String HEADER_FTP_PASSWORD = "FTPPassword";
	String HEADER_FTP_DIRECTORY = "FTPDirectory";
	String HEADER_FTP_FILENAME = "FTPFilename";
}
