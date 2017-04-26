package de.metas.material.planning;

import org.adempiere.util.Services;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.metas.material.planning.impl.MRPContextFactory;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2017 metas GmbH
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
@Configuration
public class MaterialPlanningConfiguration
{

	@Bean
	public ProductPlanningBL productPlanningBL()
	{
		return new ProductPlanningBL();
	}

	/**
	 * Creates a the {@link MRPContextFactory} registers it to {@link Services}.
	 * 
	 * @return
	 */
	@Bean
	public MRPContextFactory mrpContextFactory()
	{
		final MRPContextFactory serviceImpl = new MRPContextFactory();
		Services.registerService(IMRPContextFactory.class, serviceImpl);
		return serviceImpl;
	}

}
