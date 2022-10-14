/*
 * #%L
 * de-metas-camel-sap
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

package de.metas.camel.externalsystems.sap;

public interface SAPConstants
{
	String ROUTE_PROPERTY_SAP_ROUTE_CONTEXT = "SAPRouteContext";

	String SAP_SYSTEM_NAME = "SAP";

	String SFTP_MOVE_FOLDER_NAME = "sftp.move.folder.name";
	String SFTP_MOVE_FAILED_FOLDER_NAME = "sftp.move.failed.folder.name";
	String SFTP_REQUEST_DELAY = "sftp.request.freq.time";
}
