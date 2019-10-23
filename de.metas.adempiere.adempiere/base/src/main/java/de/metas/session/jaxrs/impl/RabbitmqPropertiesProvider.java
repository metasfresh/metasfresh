package de.metas.session.jaxrs.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.metas.session.jaxrs.StatusServiceResult;
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
@Getter
public class RabbitmqPropertiesProvider
{
	@Value("${spring.swingui.rabbitmq.host:" + StatusServiceResult.RABBITMQ_USE_APPSERVER_HOSTNAME + "}")
	private String rabbitmqHost;

	@Value("${spring.swingui.rabbitmq.port:5672}")
	private String rabbitmqPort;

	@Value("${spring.rabbitmq.username:guest}")
	private String rabbitmqUsername;

	@Value("${spring.rabbitmq.password:guest}")
	private String rabbitmqPassword;
}
