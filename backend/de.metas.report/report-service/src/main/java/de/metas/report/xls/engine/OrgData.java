package de.metas.report.xls.engine;

import java.io.InputStream;
import java.util.Properties;

import org.compiere.util.Env;

import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.report.jasper.server.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class OrgData
{
	public static final OrgData ofAD_Org_ID(final Properties ctx)
	{
		final OrgId adOrgId = Env.getOrgId(ctx);
		return new OrgData(adOrgId);
	}

	private final OrgId adOrgId;
	private transient String orgName; // lazy

	private OrgData(final OrgId adOrgId)
	{
		this.adOrgId = adOrgId;
	}

	public String getName()
	{
		String orgName = this.orgName;
		if (orgName == null)
		{
			orgName = this.orgName = Services.get(IOrgDAO.class).retrieveOrgName(adOrgId);
		}
		return orgName;
	}

	public InputStream getLogo()
	{
		// TODO: refactor from de.metas.adempiere.report.jasper.OrgLogoLocalFileLoader
		throw new UnsupportedOperationException();
	}
}
