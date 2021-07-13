package de.metas.report.server;

import java.io.File;
import java.util.List;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.process.ProcessInfoParameter;
import de.metas.report.PrintFormatId;
import de.metas.report.jasper.JasperClassLoader;
import de.metas.report.jasper.JasperCompileClassLoader;
import de.metas.report.util.DevelopmentWorkspaceJasperDirectoriesFinder;
import de.metas.util.Check;
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

public abstract class AbstractReportEngine implements IReportEngine
{
	private static final Logger logger = LogManager.getLogger(AbstractReportEngine.class);
	private final IDeveloperModeBL developerModeBL = Services.get(IDeveloperModeBL.class);

	private static final String SYSCONFIG_ReportsDirs = "reportsDirs";
	private static final String PARAM_AD_PRINTFORMAT_ID = "AD_PrintFormat_ID";

	protected ClassLoader createReportClassLoader(final ReportContext reportContext)
	{
		final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

		final ClassLoader parentClassLoader;
		if (developerModeBL.isEnabled())
		{
			parentClassLoader = JasperCompileClassLoader.builder()
					.parentClassLoader(contextClassLoader)
					.additionalResourceDirNames(getConfiguredReportsDirs())
					.additionalResourceDirNames(getDevelopmentWorkspaceReportsDirs())
					.build();
			logger.info("Using compile class loader: {}", parentClassLoader);
		}
		else
		{
			parentClassLoader = contextClassLoader;
		}

		
		final OrgId adOrgId = reportContext.getOrgId();
		final JasperClassLoader jasperLoader = new JasperClassLoader(adOrgId, parentClassLoader, getPrintFormatIdOrNull(reportContext));
		logger.debug("Created jasper loader: {}", jasperLoader);
		return jasperLoader;
	}

	private PrintFormatId getPrintFormatIdOrNull(final ReportContext reportContext)
	{
		for (final ProcessInfoParameter param : reportContext.getProcessInfoParameters())
		{
			final String parameterName = param.getParameterName();
			if (PARAM_AD_PRINTFORMAT_ID.equals(parameterName))
			{
				return PrintFormatId.ofRepoIdOrNull(param.getParameterAsInt());		
			}
		}
		
		return null;
	}

	private List<File> getDevelopmentWorkspaceReportsDirs()
	{
		final File developmentWorkspaceDir = developerModeBL.getDevelopmentWorkspaceDir().orElse(null);
		if (developmentWorkspaceDir != null)
		{
			return DevelopmentWorkspaceJasperDirectoriesFinder.getReportsDirectoriesForWorkspace(developmentWorkspaceDir);
		}
		else
		{
			logger.warn("No development workspace directory configured. Not considering workspace reports directories");
			return ImmutableList.of();
		}
	}

	private List<File> getConfiguredReportsDirs()
	{
		final String reportsDirs = Services.get(ISysConfigBL.class).getValue(SYSCONFIG_ReportsDirs);
		if (Check.isEmpty(reportsDirs, true))
		{
			return ImmutableList.of();
		}

		return Splitter.on(",")
				.omitEmptyStrings()
				.splitToList(reportsDirs.trim())
				.stream()
				.map(File::new)
				.collect(ImmutableList.toImmutableList());
	}
}
