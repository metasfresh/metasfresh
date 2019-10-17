package de.metas;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class MetasfreshBeanNameGenerator extends AnnotationBeanNameGenerator
{
	@Override
	protected String buildDefaultBeanName(@NonNull final BeanDefinition definition)
	{
		if (isMetasfreshPackage(definition))
		{
			return extractFullyQualifiedBeanClassName(definition);
		}

		// Fallback to standard way of naming beans
		// i.e. simple class name, first letter lower case
		return super.buildDefaultBeanName(definition);
	}

	private boolean isMetasfreshPackage(@NonNull final BeanDefinition definition)
	{
		final String beanClassName = definition.getBeanClassName();

		return beanClassName.startsWith("de.metas")
				|| beanClassName.startsWith("org.adempiere")
				|| beanClassName.startsWith("org.eevolution");
	}

	private static String extractFullyQualifiedBeanClassName(final BeanDefinition definition)
	{
		return definition.getBeanClassName();
	}
}
