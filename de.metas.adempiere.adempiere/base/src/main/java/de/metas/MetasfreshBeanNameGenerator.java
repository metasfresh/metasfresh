package de.metas;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.core.type.AnnotationMetadata;

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
	private static final String CLASSNAME_Interceptor = org.adempiere.ad.modelvalidator.annotations.Interceptor.class.getName();
	private static final String CLASSNAME_Callout = org.adempiere.ad.callout.annotations.Callout.class.getName();

	@Override
	protected String buildDefaultBeanName(final BeanDefinition definition)
	{
		if (isInterceptor(definition))
		{
			// Because we have more than one model Interceptor with the same class name but in different packages,
			// it's better to name the bean after it's FQ name instead of just simple class name
			return extractFullyQualifiedBeanClassName(definition);
		}
		else if (isCallout(definition))
		{
			// Because we have more than one Callout with the same class name but in different packages,
			// it's better to name the bean after it's FQ name instead of just simple class name
			return extractFullyQualifiedBeanClassName(definition);
		}
		else
		{
			// Fallback to standard way of naming beans
			// i.e. simple class name, first letter lower case
			return super.buildDefaultBeanName(definition);
		}
	}

	private static boolean isInterceptor(final BeanDefinition definition)
	{
		return hasAnnotation(definition, CLASSNAME_Interceptor);
	}

	private static boolean isCallout(final BeanDefinition definition)
	{
		return hasAnnotation(definition, CLASSNAME_Callout);
	}

	private static boolean hasAnnotation(final BeanDefinition definition, final String annotationClassname)
	{
		if (definition instanceof AnnotatedBeanDefinition)
		{
			final AnnotatedBeanDefinition annotatedDef = (AnnotatedBeanDefinition)definition;
			final AnnotationMetadata amd = annotatedDef.getMetadata();
			return amd.hasAnnotation(annotationClassname);
		}
		else
		{
			return false;
		}
	}

	private static String extractFullyQualifiedBeanClassName(final BeanDefinition definition)
	{
		return definition.getBeanClassName();
	}
}
