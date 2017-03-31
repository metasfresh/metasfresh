package de.metas.ui.web.process.descriptor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.metas.process.JavaProcess;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;

/*
 * #%L
 * metasfresh-webui-api
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

/**
 * Annotate {@link JavaProcess} methods which are responsible for providing lookup values for a given parameter.
 * In this case, the default parameter's lookup (as defined in application dictionary) won't be used.
 *
 * The annotated method shall have following characteristics:
 * <ul>
 * <li>return type shall ALWAYS be {@link LookupValuesList}
 * <li>parameters (if any) can be: {@link LookupDataSourceContext}
 * </ul>
 *
 *
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface ProcessParamLookupValuesProvider
{
	/** parameter name for whom we will provide the lookup values */
	String parameterName();

	/** list of parameter names on which the lookup values fetching depends on */
	String[] dependsOn();

	/** true if we will provide {@link IntegerLookupValue}s, else {@link StringLookupValue}s are assumed */
	boolean numericKey();
}
