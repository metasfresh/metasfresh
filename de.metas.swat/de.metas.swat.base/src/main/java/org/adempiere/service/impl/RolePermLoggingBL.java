package org.adempiere.service.impl;

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

public class RolePermLoggingBL implements IRolePermLoggingBL
{

	@Override
	public void logWindowAccess(int AD_Role_ID, int id, Boolean access)
	{
		MRolePermRequest.logWindowAccess(AD_Role_ID, id, access);
	}

	@Override
	public void logWindowAccess(int AD_Role_ID, int id, Boolean access, String description)
	{
		MRolePermRequest.logWindowAccess(AD_Role_ID, id, access, description);
	}

	@Override
	public void logFormAccess(int AD_Role_ID, int id, Boolean access)
	{
		MRolePermRequest.logFormAccess(AD_Role_ID, id, access);
	}

	@Override
	public void logProcessAccess(int AD_Role_ID, int id, Boolean access)
	{
		MRolePermRequest.logProcessAccess(AD_Role_ID, id, access);
	}

	@Override
	public void logTaskAccess(int AD_Role_ID, int id, Boolean access)
	{
		MRolePermRequest.logTaskAccess(AD_Role_ID, id, access);
	}

	@Override
	public void logWorkflowAccess(int AD_Role_ID, int id, Boolean access)
	{
		MRolePermRequest.logWorkflowAccess(AD_Role_ID, id, access);
	}

	@Override
	public void logDocActionAccess(int AD_Role_ID, DocTypeId docTypeId, String docAction, Boolean access)
	{
		MRolePermRequest.logDocActionAccess(AD_Role_ID, docTypeId, docAction, access);
	}

}
