package de.metas.adempiere.ait.test.annotation;

/*
 * #%L
 * de.metas.adempiere.ait
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.event.EventType;
import de.metas.adempiere.ait.test.IntegrationTestListeners;
import de.metas.adempiere.ait.test.IntegrationTestSuite;

/**
 * Annotation is used for integration test methods to be called on certain test events.
 * NOTE that the test suite shall be run with {@link IntegrationTestSuite} and {@link IntegrationTestListeners} shall be declared.
 * 
 * @author ts
 * 
 * @see IntegrationTestSuite
 * @see AIntegrationTestDriver
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface ITEventListener
{
	/**
	 * @return the TestEventTypes that the method should be called on
	 */
	public EventType[] eventTypes();

	/**
	 * @return the integration test driver that the method listens to
	 */
	public Class<? extends AIntegrationTestDriver> driver();

	/**
	 * @return (optional) the names of the tasks to which the test relates  
	 */
	public String[] tasks() default {};

	public String desc() default "";
}
