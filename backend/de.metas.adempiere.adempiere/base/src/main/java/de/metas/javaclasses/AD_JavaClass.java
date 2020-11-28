/**
 *
 */
package de.metas.javaclasses;

import de.metas.javaclasses.model.I_AD_JavaClass;

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
 * This annotation can be used to further annutate a class that is supposed to be handled in the context of {@link I_AD_JavaClass}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public @interface AD_JavaClass
{

	/**
	 * Set to true, if a class shall be ignored by the framework and not be found and registered by {@link IJavaClassTypeBL#updateClassRecordsList(de.metas.javaclasses.model.I_AD_JavaClass_Type)}.
	 *
	 * @return
	 */
	boolean ignore() default false;

}
