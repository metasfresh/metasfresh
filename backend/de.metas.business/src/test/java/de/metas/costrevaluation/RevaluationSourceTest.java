package de.metas.costrevaluation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2024 metas GmbH
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

public class RevaluationSourceTest
{
	@Test
	public void ofCode_returns_matching_enum_value()
	{
		assertThat(RevaluationSource.ofCode("CopyFromCostElement")).isSameAs(RevaluationSource.CopyFromCostElement);
		assertThat(RevaluationSource.ofCode("Calculated")).isSameAs(RevaluationSource.Calculated);
	}

	@Test
	public void getCode_roundtrips()
	{
		for (final RevaluationSource source : RevaluationSource.values())
		{
			assertThat(RevaluationSource.ofCode(source.getCode())).isSameAs(source);
		}
	}

	@Test
	public void ofCode_throws_for_unknown_code()
	{
		assertThatThrownBy(() -> RevaluationSource.ofCode("SomeUnknownCode"))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("No RevaluationSource found for code");
	}

	@Test
	public void ofNullableCode()
	{
		assertThat(RevaluationSource.ofNullableCode(null)).isNull();
		assertThat(RevaluationSource.ofNullableCode("CopyFromCostElement")).isSameAs(RevaluationSource.CopyFromCostElement);
	}

	@Test
	public void toCodeOrNull()
	{
		assertThat(RevaluationSource.toCodeOrNull(null)).isNull();
		assertThat(RevaluationSource.toCodeOrNull(RevaluationSource.CopyFromCostElement)).isEqualTo("CopyFromCostElement");
	}

	@Test
	public void isCopyFromCostElement()
	{
		assertThat(RevaluationSource.CopyFromCostElement.isCopyFromCostElement()).isTrue();
		assertThat(RevaluationSource.Calculated.isCopyFromCostElement()).isFalse();
	}

	@Test
	public void equals_nullsafe()
	{
		assertThat(RevaluationSource.equals(null, null)).isTrue();
		assertThat(RevaluationSource.equals(RevaluationSource.Calculated, RevaluationSource.Calculated)).isTrue();
		assertThat(RevaluationSource.equals(RevaluationSource.Calculated, RevaluationSource.CopyFromCostElement)).isFalse();
	}
}
