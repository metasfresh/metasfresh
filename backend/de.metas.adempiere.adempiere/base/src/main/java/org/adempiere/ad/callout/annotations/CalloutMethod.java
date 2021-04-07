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

/**
 * Indicates that the annotated callout method shall be triggered when given columns were changed.
 * 
 * Annotated method shall have following signature:
 * 
 * <pre>
 * public void myCallout(I_C_MyModel model, ICalloutField field)
 * </pre>
 * 
 * where
 * <ul>
 * <li>model - is the model on which this callout applies (i.e. shall be the same class that you have used in {@link Callout} annotation)
 * <li>field - is the field on which this callout was triggered (useful to get the ColumnName etc)
 * </ul>
 * 
 * @author tsa
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface CalloutMethod
{
	/**
	 * Column names on which this method shall be bound
	 */
	String[] columnNames();

	/**
	 * Skip calling this callout if we are copying (with details).
	 */
	boolean skipIfCopying() default false;

	/**
	 * Skip calling this callout if it is called via another callout.
	 * Use case: you want a callout to do stuff only if invoked *directly* by a user (or business logic).
	 */
	boolean skipIfIndirectlyCalled() default false;
}
