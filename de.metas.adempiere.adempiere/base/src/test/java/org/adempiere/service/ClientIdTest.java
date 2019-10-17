package org.adempiere.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.function.IntFunction;

import org.adempiere.exceptions.AdempiereException;
import org.junit.Test;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class ClientIdTest
{
	@Test
	public void test_ofRepoIdOrNull()
	{
		testStandardValues(ClientId::ofRepoIdOrNull);

		assertThat(ClientId.ofRepoIdOrNull(-1)).isNull();
		assertThat(ClientId.ofRepoIdOrNull(1234567).getRepoId()).isEqualTo(1234567);
	}

	@Test
	public void test_ofRepoId()
	{
		testStandardValues(ClientId::ofRepoId);

		assertThatThrownBy(() -> ClientId.ofRepoId(-1))
				.isInstanceOf(AdempiereException.class)
				.hasMessage("Invalid AD_Client_ID: -1");

		assertThat(ClientId.ofRepoId(1234567).getRepoId()).isEqualTo(1234567);
	}

	@Test
	public void test_ofRepoIdOrSystem()
	{
		testStandardValues(ClientId::ofRepoIdOrSystem);

		assertThat(ClientId.ofRepoIdOrSystem(-1)).isSameAs(ClientId.SYSTEM);
		assertThat(ClientId.ofRepoIdOrSystem(1234567).getRepoId()).isEqualTo(1234567);
	}

	private void testStandardValues(final IntFunction<ClientId> factory)
	{
		assertThat(factory.apply(ClientId.SYSTEM.getRepoId())).isSameAs(ClientId.SYSTEM);
		assertThat(factory.apply(ClientId.TRASH.getRepoId())).isSameAs(ClientId.TRASH);
		assertThat(factory.apply(ClientId.METASFRESH.getRepoId())).isSameAs(ClientId.METASFRESH);
	}

	@Test
	public void test_isSystem()
	{
		assertThat(ClientId.SYSTEM).returns(true, ClientId::isSystem);
		assertThat(ClientId.TRASH).returns(false, ClientId::isSystem);
		assertThat(ClientId.METASFRESH).returns(false, ClientId::isSystem);
		assertThat(ClientId.ofRepoId(1234567)).returns(false, ClientId::isSystem);
	}

	@Test
	public void test_isTrash()
	{
		assertThat(ClientId.SYSTEM).returns(false, ClientId::isTrash);
		assertThat(ClientId.TRASH).returns(true, ClientId::isTrash);
		assertThat(ClientId.METASFRESH).returns(false, ClientId::isTrash);
		assertThat(ClientId.ofRepoId(1234567)).returns(false, ClientId::isTrash);
	}

	@Test
	public void test_isRegular()
	{
		assertThat(ClientId.SYSTEM).returns(false, ClientId::isRegular);
		assertThat(ClientId.TRASH).returns(false, ClientId::isRegular);
		assertThat(ClientId.METASFRESH).returns(true, ClientId::isRegular);
		assertThat(ClientId.ofRepoId(1234567)).returns(true, ClientId::isRegular);
	}
}
