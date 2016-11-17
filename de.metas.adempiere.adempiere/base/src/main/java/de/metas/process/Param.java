package de.metas.process;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.adempiere.exceptions.FillMandatoryException;

import de.metas.process.processtools.AD_Process_Para_UpdateFromAnnotations;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * Annotate a class field in order to be automatically loaded right before {@link JavaProcess#prepare()} method is called.
 * The annotated field may be <code>private</code>, but not <code>final</code>.<br>
 * Note that we still need <code>AD_Process_Param</code> records in the database.
 * <p>
 * The annotation can be used to avoid having to implement {@link JavaProcess#prepare()}, but it's also OK to have both,
 * e.g. if there are additional things to be done prior to the {@link JavaProcess#doIt()}.
 *
 * @see AD_Process_Para_UpdateFromAnnotations
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Param
{
	/** process parameter name to be used when binding */
	String parameterName();

	/** if true, when the parameter will be loaded an {@link FillMandatoryException} will be thrown if the value fetched from process parameters is null */
	boolean mandatory() default false;

	/** if <code>true</code>, then it will be assumed that the parameter is question is a range and that the annotated field shall contain the range's <code>_To</code> value. */
	boolean parameterTo() default false;
}
