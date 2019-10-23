package org.adempiere.ad.callout.annotations;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;

/**
 * Classes annotated as callout may be registered with an instance of {@link IProgramaticCalloutProvider}.
 * The system will then invoke their methods that are annotated with {@link CalloutMethod}.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Callout
{
	/** Level on which a call is considered "recursive" */
	enum RecursionAvoidanceLevel
	{
		/**
		 * Consider as recursive call a call which is about a method of the same class
		 */
		CalloutClass,
		/**
		 * Consider as recursive call a call which is about a method which is already in call trace
		 */
		CalloutMethod,
		/**
		 * Consider as recursive call a call which is about a field(column name) which is already in call trace
		 */
		CalloutField,
	}

	/**
	 * Interface model class on which this Callout will be bound
	 *
	 * NOTE: class name shall be the same as the callout name (metas naming conventions)
	 */
	public Class<?> value();

	/**
	 * Configures on which level the callout execution recursion shall be avoided.
	 */
	public RecursionAvoidanceLevel recursionAvoidanceLevel() default RecursionAvoidanceLevel.CalloutClass;

}
