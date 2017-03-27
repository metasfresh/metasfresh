package de.metas.ui.web.config;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adempiere.util.Services;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import de.metas.hostkey.api.IHostKeyBL;
import de.metas.hostkey.spi.impl.HttpCookieHostKeyStorage;
import de.metas.ui.web.base.util.IHttpSessionProvider;

/*
 * #%L
 * metasfresh-webui-api
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

/**
 * Configures HostKey for webui
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh-webui-api/issues/264
 */
@Configuration
public class HostKeyConfig
{
	@PostConstruct
	public void setupHostKeyStorage()
	{
		Services.registerService(IHttpSessionProvider.class, new SpringHttpSessionProvider());

		Services.get(IHostKeyBL.class).setHostKeyStorage(new HttpCookieHostKeyStorage());
	}

	private static final class SpringHttpSessionProvider implements IHttpSessionProvider
	{
		@Override
		public HttpServletRequest getCurrentRequest()
		{
			return getRequestAttributes().getRequest();
		}

		@Override
		public HttpServletResponse getCurrentResponse()
		{
			return getRequestAttributes().getResponse();
		}

		private ServletRequestAttributes getRequestAttributes()
		{
			final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
			if (requestAttributes instanceof ServletRequestAttributes)
			{
				return (ServletRequestAttributes)requestAttributes;
			}
			else
			{
				throw new IllegalStateException("Not called in the context of an HTTP request (" + requestAttributes + ")");
			}
		}

	}
}
