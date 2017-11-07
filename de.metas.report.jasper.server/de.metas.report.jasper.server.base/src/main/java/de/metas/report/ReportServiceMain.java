package de.metas.report;

import org.adempiere.util.StringUtils;
import org.compiere.Adempiere.RunMode;
import org.compiere.util.Ini;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Profile;

import de.metas.adempiere.report.jasper.JasperConstants;

/*
 * #%L
 * de.metas.adempiere.adempiere.serverRoot.base
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


@SpringBootApplication(scanBasePackages =
	{ "de.metas.report", "de.metas.adempiere.report.jasper" })
@ServletComponentScan(value =
	{ "de.metas.adempiere.report.jasper.servlet" })
@Profile(JasperConstants.PROFILE_JasperServer)
public class ReportServiceMain
{
	/**
	 * By default, we run in headless mode. But using this system property, we can also run with headless=false.
	 * The only known use of that is that metasfresh can open the initial license & connection dialog to store the initial properties file.
	 */
	public static final String SYSTEM_PROPERTY_HEADLESS = "app-server-run-headless";

	public static void main(final String[] args)
	{
		// important because in Ini, there is a org.springframework.context.annotation.Condition that userwise wouldn't e.g. let the jasper servlet start
		Ini.setRunMode(RunMode.BACKEND);

		final String headless = System.getProperty(SYSTEM_PROPERTY_HEADLESS, Boolean.toString(true));

		new SpringApplicationBuilder(ReportServiceMain.class)
				.headless(StringUtils.toBoolean(headless)) // we need headless=false for initial connection setup popup (if any), usually this only applies on dev workstations.
				.web(true)
				.profiles(JasperConstants.PROFILE_JasperServer)
				.run(args);
	}
}
