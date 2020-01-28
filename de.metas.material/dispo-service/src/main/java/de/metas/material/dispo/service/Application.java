package de.metas.material.dispo.service;

import java.util.Collections;

import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.Adempiere;
import org.compiere.Adempiere.RunMode;
import org.compiere.model.ModelValidationEngine;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.JsonObjectMapperHolder;
import de.metas.MetasfreshBeanNameGenerator;
import de.metas.Profiles;
import de.metas.cache.CacheMgt;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Dist_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Prod_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Transaction_Detail;

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
		scanBasePackages = { "de.metas", "org.adempiere" }  //
)
@Profile(Application.PROFILE_MaterialDispo_Standalone)
public class Application
{
	static final String PROFILE_MaterialDispo_Standalone = Profiles.PROFILE_MaterialDispo + "-standalone";

	@Autowired
	private ApplicationContext applicationContext;

	public static void main(final String[] args)
	{
		try (final IAutoCloseable c = ModelValidationEngine.postponeInit())
		{
			Ini.setRunMode(RunMode.BACKEND);

			// as of right now, we are not interested in loading any model validator whatsoever within this service
			// therefore we don't e.g. have to deal with the async-processor. It just won't be started.
			ModelValidationEngine.setInitEntityTypes(Collections.emptyList());

			Adempiere.instance.startup(RunMode.BACKEND);

			new SpringApplicationBuilder(Application.class)
					.headless(true)
					.web(WebApplicationType.SERVLET)
					.profiles(Profiles.PROFILE_MaterialDispo, PROFILE_MaterialDispo_Standalone)
					.beanNameGenerator(new MetasfreshBeanNameGenerator())
					.run(args);
		}

		// now init the model validation engine
		ModelValidationEngine.get();

		enableCacheEventsForDispoTables();
	}

	private static void enableCacheEventsForDispoTables()
	{
		final CacheMgt cacheMgt = CacheMgt.get();

		cacheMgt.enableRemoteCacheInvalidationForTableName(I_MD_Candidate.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_MD_Candidate_Demand_Detail.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_MD_Candidate_Dist_Detail.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_MD_Candidate_Prod_Detail.Table_Name);
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_MD_Candidate_Transaction_Detail.Table_Name);
	}

	@Bean
	@Primary
	public ObjectMapper jsonObjectMapper()
	{
		return JsonObjectMapperHolder.sharedJsonObjectMapper();
	}

	@Bean(name = Adempiere.BEAN_NAME)
	@Profile("!test")
	public Adempiere adempiere()
	{
		// when this is done, Adempiere.getBean(...) is ready to use
		final Adempiere adempiere = Env.getSingleAdempiereInstance(applicationContext);
		return adempiere;
	}

}
