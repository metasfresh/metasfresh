package org.adempiere.service.impl;

import org.adempiere.ad.element.api.AdWindowId;

/*
 * #%L
 * de.metas.swat.base
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


import org.adempiere.service.IRolePermLoggingBL;
import org.compiere.model.MRolePermRequest;

import de.metas.document.DocTypeId;
import de.metas.security.RoleId;

public class RolePermLoggingBL implements IRolePermLoggingBL
{

	@Override
	public void logWindowAccess(RoleId roleId, int id, Boolean access)
	{
		MRolePermRequest.logWindowAccess(roleId, id, access);
	}

	@Override
	public void logWindowAccess(RoleId roleId, AdWindowId id, Boolean access, String description)
	{
		MRolePermRequest.logWindowAccess(roleId, id, access, description);
	}

	@Override
	public void logFormAccess(RoleId roleId, int id, Boolean access)
	{
		MRolePermRequest.logFormAccess(roleId, id, access);
	}

	@Override
	public void logProcessAccess(RoleId roleId, int id, Boolean access)
	{
		MRolePermRequest.logProcessAccess(roleId, id, access);
	}

	@Override
	public void logTaskAccess(RoleId roleId, int id, Boolean access)
	{
		MRolePermRequest.logTaskAccess(roleId, id, access);
	}

	@Override
	public void logWorkflowAccess(RoleId roleId, int id, Boolean access)
	{
		MRolePermRequest.logWorkflowAccess(roleId, id, access);
	}

	@Override
	public void logDocActionAccess(RoleId roleId, DocTypeId docTypeId, String docAction, Boolean access)
	{
		MRolePermRequest.logDocActionAccess(roleId, docTypeId, docAction, access);
	}

}
