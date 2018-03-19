package de.metas;

import org.adempiere.util.IService;
import org.adempiere.util.Services;
import org.adempiere.util.Services.IServiceImplProvider;
import org.compiere.Adempiere;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.google.common.base.MoreObjects;

import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent>
{
	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event)
	{
		final ApplicationContext applicationContext = event.getApplicationContext();
		Adempiere.instance.setApplicationContext(applicationContext);

		// gh #427: allow service implementations to be managed by spring.
		Services.setExternalServiceImplProvider(new SpringApplicationContextAsServiceImplProvider(applicationContext));
	}

	private static final class SpringApplicationContextAsServiceImplProvider implements IServiceImplProvider
	{
		private final ApplicationContext applicationContext;

		private SpringApplicationContextAsServiceImplProvider(final ApplicationContext applicationContext)
		{
			this.applicationContext = applicationContext;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this).addValue(applicationContext).toString();
		}

		@Override
		public <T extends IService> T provideServiceImpl(@NonNull final Class<T> serviceClazz)
		{
			try
			{
				return applicationContext.getBean(serviceClazz);
			}
			catch (final NoUniqueBeanDefinitionException e)
			{
				// not ok; we have > 1 matching beans defined in the spring context. So far that always indicated some sort of mistake, so let's escalate.
				throw e;
			}
			catch (final NoSuchBeanDefinitionException e)
			{
				// ok; the bean is not in the spring context, so let's just return null
				return null;
			}
		}
	}
}
