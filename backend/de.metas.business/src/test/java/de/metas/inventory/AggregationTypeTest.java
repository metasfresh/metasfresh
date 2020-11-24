package de.metas.inventory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import de.metas.document.DocBaseAndSubType;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class AggregationTypeTest
{
	@Test
	public void test_getByDocType()
	{
		Stream.of(AggregationType.values())
				.forEach(this::test_getByDocType);
	}

	private void test_getByDocType(final AggregationType type)
	{
		final DocBaseAndSubType docBaseAndSubType = type.getDocBaseAndSubType();
		assertThat(AggregationType.getByDocTypeOrNull(docBaseAndSubType)).isSameAs(type);

		InventoryDocSubType.ofCode(docBaseAndSubType.getDocSubType());
	}
}
