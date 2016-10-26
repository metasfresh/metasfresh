package de.metas;

import org.compiere.Adempiere;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/*
 * #%L
 * de.metas.endcustomer.mf15.swingui
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

/**
 * Swing application starter.
 * 
 * WARNING: please keep in sync with all other SwingUIApplications
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SpringBootApplication( //
		scanBasePackages = { "de.metas", "org.adempiere" } //
		, excludeName = "de.metas.ServerBoot" // exclude the ServerBoot, just in case it's on classpath when running (usually when started from eclipse)
)
public class SwingUIApplication
{
	@Autowired
	private ApplicationContext applicationContext;

	public static void main(String[] args)
	{
		new SpringApplicationBuilder(SwingUIApplication.class)
				.headless(false)
				// actually we would like to it to start actuator endpoints and register with the spring-boot admin server, BUT
				// we first need to solve the problem of running multiple clients on the same machine (they need to bind to differnt ports)
				// there might be resource/performance problems
				// at any rate, we have not yet a solution as to how to configure them
				.web(false)
				.run(args);
	}

	/**
	 * Starts the metasfresh swing client. This needs to be decomposed to make it return during startup.<br>
	 * Otherwise, the whole spring thing doesn't work properly.
	 *
	 * @return
	 */
	@Bean
	public Adempiere adempiere()
	{
		Adempiere.main(applicationContext);
		final Adempiere adempiere = Env.getSingleAdempiereInstance();
		return adempiere;
	}
}
