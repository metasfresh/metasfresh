package org.adempiere.util.lang.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

public class TableRecordReferenceSetTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void getTableNames_empty()
	{
		assertThat(TableRecordReferenceSet.EMPTY.getTableNames()).isEmpty();
	}

	@Test
	public void getTableNames_singleTable()
	{
		final TableRecordReferenceSet set = TableRecordReferenceSet.of(TableRecordReference.of("C_Invoice", 1));
		assertThat(set.getTableNames()).containsExactly("C_Invoice");
	}

	@Test
	public void getTableNames_multipleTables()
	{
		final TableRecordReferenceSet set = TableRecordReferenceSet.of(ImmutableList.of(
				TableRecordReference.of("C_Invoice", 1),
				TableRecordReference.of("C_Order", 2)));

		assertThat(set.getTableNames()).containsExactlyInAnyOrder("C_Invoice", "C_Order");
	}

	@Test
	public void getSingleTableName_empty()
	{
		assertThatThrownBy(TableRecordReferenceSet.EMPTY::getSingleTableName)
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(String.valueOf(TableRecordReferenceSet.EMPTY.getTableNames()));
	}

	@Test
	public void getSingleTableName_singleTable()
	{
		final TableRecordReferenceSet set = TableRecordReferenceSet.of(TableRecordReference.of("C_Invoice", 1));
		assertThat(set.getSingleTableName()).isEqualTo("C_Invoice");
	}

	@Test
	public void getSingleTableName_multipleTables()
	{
		final TableRecordReferenceSet set = TableRecordReferenceSet.of(ImmutableList.of(
				TableRecordReference.of("C_Invoice", 1),
				TableRecordReference.of("C_Order", 2)));

		assertThatThrownBy(set::getSingleTableName)
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(String.valueOf(set.getTableNames()));
	}
}
