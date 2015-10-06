package de.metas.adempiere.form.terminal.table.annotation;

/*
 * #%L
 * de.metas.swat.base
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

import de.metas.adempiere.form.terminal.table.ITerminalTable2;

/**
 * Used to add hints to java beans getter about how this column will be displayed by {@link ITerminalTable2}.
 * 
 * @author tsa
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface TableInfo
{
	String displayName() default "";

	boolean translate() default true;

	boolean hidden() default false;

	int seqNo() default Integer.MAX_VALUE;

	String lookupTableName() default "";

	String lookupColumnName() default "";

	/**
	 * @return optional prototype value used to calculate the column width
	 */
	String prototypeValue() default "";
}
