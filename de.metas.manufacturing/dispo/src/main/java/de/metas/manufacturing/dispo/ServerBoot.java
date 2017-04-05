package de.metas.manufacturing.dispo;

import org.compiere.Adempiere;
import org.compiere.Adempiere.RunMode;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

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

/**
 * metasfresh server boot.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@SpringBootApplication( //
scanBasePackages = { "de.metas", "org.adempiere" },  //
excludeName = "de.metas.SwingUIApplication" // exclude the SwingUIApplication, just in case it's on classpath when running (usually when started from eclipse)
)
public class ServerBoot
{
	@Autowired
	private ApplicationContext applicationContext;

	public static void main(final String[] args)
	{
		// important because in Ini, there is a org.springframework.context.annotation.Condition that userwise wouldn't e.g. let the jasper servlet start
		Ini.setRunMode(RunMode.BACKEND);

		new SpringApplicationBuilder(ServerBoot.class)
				.headless(true)
				.web(true)
				.run(args);
	}

	

	@Bean
	public Adempiere adempiere()
	{
		final Adempiere adempiere = Env.getSingleAdempiereInstance();
		adempiere.setApplicationContext(applicationContext);
		adempiere.startup(RunMode.BACKEND);
		return adempiere;
	}

}
