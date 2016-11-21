package de.metas.process;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Used to annotate a process implementation, to provide more instructions. <br>
 * Notes:
 * <ul>
 * <li>you still need to add a record to <code>AD_Process</code></li>
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Process
{
	/**
	 * true if a current record needs to be selected when this process is called from gear/window.
	 *
	 * The default value is <code>true</code>, for backward compatibility.
	 */
	boolean requiresCurrentRecordWhenCalledFromGear() default true;

	/** true if this process shall be always executed on client side */
	boolean clientOnly() default false;
}
