package de.metas.testsupport;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.assertj.core.api.Assertions;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * Use this import statement:
 *
 * <pre>
 * import static de.metas.testsupport.MetasfreshAssertions.*;
 * </pre>
 *
 * To access both the assertJ assertions and the metasfresh ones.<br>
 * Thanks to http://joel-costigliola.github.io/assertj/assertj-core-custom-assertions.html
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh/issues/3006
 *
 */
@SuppressWarnings("rawtypes")
public class MetasfreshAssertions extends Assertions
{
	public static CompositeQueryFilterAssert assertThat(@Nullable final ICompositeQueryFilter actual)
	{
		return new CompositeQueryFilterAssert(actual);
	}

	public static QueryFilterAssert assertThat(@Nullable final IQueryFilter actual)
	{
		return new QueryFilterAssert(actual);
	}

	public static ModelAssert assertThatModel(@Nullable final Object actual)
	{
		return new ModelAssert(actual);
	}
}
