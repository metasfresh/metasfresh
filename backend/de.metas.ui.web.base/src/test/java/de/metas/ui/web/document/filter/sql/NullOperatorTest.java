/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.document.filter.sql;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class NullOperatorTest
{

	@Test
	void ofNotNullFlag()
	{
		Assertions.assertThat(NullOperator.ofNotNullFlag(null)).isSameAs(NullOperator.ANY);
		Assertions.assertThat(NullOperator.ofNotNullFlag(true)).isSameAs(NullOperator.IS_NOT_NULL);
		Assertions.assertThat(NullOperator.ofNotNullFlag(false)).isSameAs(NullOperator.IS_NULL);
	}

	@Test
	void negate()
	{
		Assertions.assertThat(NullOperator.ANY.negate()).isSameAs(NullOperator.ANY);
		Assertions.assertThat(NullOperator.IS_NULL.negate()).isSameAs(NullOperator.IS_NOT_NULL);
		Assertions.assertThat(NullOperator.IS_NOT_NULL.negate()).isSameAs(NullOperator.IS_NULL);
	}

	@Test
	void negateIf()
	{
		Assertions.assertThat(NullOperator.IS_NULL.negateIf(false)).isSameAs(NullOperator.IS_NULL);
		Assertions.assertThat(NullOperator.IS_NULL.negateIf(true)).isSameAs(NullOperator.IS_NOT_NULL);
	}
}