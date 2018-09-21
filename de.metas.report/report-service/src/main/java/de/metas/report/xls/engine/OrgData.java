package de.metas.report.xls.engine;

import java.io.InputStream;
import java.util.Properties;

import org.adempiere.service.IOrgDAO;
import org.compiere.model.I_AD_Org;
import org.compiere.util.Env;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class OrgData
{
	public static final OrgData ofAD_Org_ID(final Properties ctx)
	{
		final int adOrgId = Env.getAD_Org_ID(ctx);
		return new OrgData(ctx, adOrgId);
	}

	// private static final transient CLogger logger = CLogger.getCLogger(OrgData.class);

	private final Properties ctx;
	private final int adOrgId;

	private final Supplier<I_AD_Org> orgSupplier = Suppliers.memoize(new Supplier<I_AD_Org>()
	{

		@Override
		public I_AD_Org get()
		{
			return Services.get(IOrgDAO.class).retrieveOrg(ctx, adOrgId);
		}
	});

	private OrgData(final Properties ctx, final int adOrgId)
	{
		this.ctx = ctx;
		this.adOrgId = adOrgId;
	}

	private final I_AD_Org getAD_Org()
	{
		return orgSupplier.get();
	}

	public String getName()
	{
		return getAD_Org().getName();
	}

	public InputStream getLogo()
	{
		// TODO: refactor from de.metas.adempiere.report.jasper.OrgLogoLocalFileLoader
		throw new UnsupportedOperationException();
	}
}
