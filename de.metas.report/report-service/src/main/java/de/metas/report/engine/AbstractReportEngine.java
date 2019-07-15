package de.metas.report.engine;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.compiere.util.Env;

import de.metas.adempiere.report.jasper.JasperClassLoader;
import de.metas.adempiere.report.jasper.JasperCompileClassLoader;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public abstract class AbstractReportEngine implements IReportEngine
{
	protected ClassLoader createReportClassLoader(final ReportContext reportContext)
	{
		
		final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

		final ClassLoader parentClassLoader;
		final boolean useJasperCompileClassLoader = Services.get(IDeveloperModeBL.class).isEnabled();
		if (useJasperCompileClassLoader)
		{
			parentClassLoader = new JasperCompileClassLoader(contextClassLoader);
		}
		else
		{
			parentClassLoader = contextClassLoader;
		}

		final OrgId adOrgId = Env.getOrgId(reportContext.getCtx());
		final JasperClassLoader jasperLoader = new JasperClassLoader(adOrgId, parentClassLoader);
		return jasperLoader;
	}

}
