package de.metas.session.jaxrs.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Getter;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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
 * Provides rabbitmq related spring properties to {@link StatusService}.
 * This service will probably become obsolete when we turn {@link StatusService} into a "real" spring-managed REST endpoint, because then we can simply inject the properties into it directly.
 */
@Service
public class RabbitmqPropertiesProvider
{
	@Value("${spring.rabbitmq.host}")
	@Getter
	private String rabbitmqHost;

	@Value("${spring.rabbitmq.port:5672}")
	@Getter
	private String rabbitmqPort;

	@Value("${spring.rabbitmq.username:metasfresh}")
	@Getter
	private String rabbitmqUsername;

	@Value("${spring.rabbitmq.password:metasfresh}")
	@Getter
	private String rabbitmqPassword;
}
