package de.metas.document.archive.esb.api;

/*
 * #%L
 * de.metas.document.archive.esb.api
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


public interface IArchiveEndpoint
{
	String PATH_Service = "/archive";

	String PARAM_SessionId = "sessionId";

	String OPERATION_GetArchiveData = "getArchiveData";
	String PATH_GetArchiveData = "/" + IArchiveEndpoint.OPERATION_GetArchiveData + "/{" + IArchiveEndpoint.PARAM_SessionId + "}";

	ArchiveGetDataResponse getArchiveData(ArchiveGetDataRequest request);

	String OPERATION_SetArchiveData = "setArchiveData";
	String PATH_SetArchiveData = "/" + IArchiveEndpoint.OPERATION_SetArchiveData + "/{" + IArchiveEndpoint.PARAM_SessionId + "}";

	ArchiveSetDataResponse setArchiveData(ArchiveSetDataRequest request);
}
