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

package de.metas.acct.api.impl;

import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AcctSchemaId;
import de.metas.organization.OrgId;
import org.adempiere.service.ClientId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountDimensionTest
{
	private static final AcctSchemaId C_AcctSchema_ID1 = AcctSchemaId.ofRepoId(1);
	private static final ElementValueId C_ElementValue_ID1 = ElementValueId.ofRepoId(1);

	private static AccountDimension.Builder newDimension()
	{
		return AccountDimension.builder()
				.setAcctSchemaId(C_AcctSchema_ID1)
				.setAD_Client_ID(ClientId.METASFRESH.getRepoId())
				.setAD_Org_ID(OrgId.MAIN.getRepoId())
				.setC_ElementValue_ID(C_ElementValue_ID1.getRepoId());
	}

	private static void assertUserElementStringsAreNull(final AccountDimension accountDimension)
	{
		assertThat(accountDimension.getUserElementString1()).isNull();
		assertThat(accountDimension.getUserElementString2()).isNull();
		assertThat(accountDimension.getUserElementString3()).isNull();
		assertThat(accountDimension.getUserElementString4()).isNull();
		assertThat(accountDimension.getUserElementString5()).isNull();
		assertThat(accountDimension.getUserElementString6()).isNull();
		assertThat(accountDimension.getUserElementString7()).isNull();
	}

	@Test
	public void setNullValues()
	{
		final AccountDimension accountDimension = newDimension()
				.setUserElementString1(null)
				.setUserElementString2(null)
				.setUserElementString3(null)
				.setUserElementString4(null)
				.setUserElementString5(null)
				.setUserElementString6(null)
				.setUserElementString7(null)
				.build();

		assertUserElementStringsAreNull(accountDimension);
		assertThat(accountDimension.isSegmentValueSet(AcctSegmentType.UserElementString1)).isFalse();
	}

	@Test
	public void setBlankValues()
	{
		final AccountDimension accountDimension = newDimension()
				.setUserElementString1("")
				.setUserElementString2(" ")
				.build();

		assertUserElementStringsAreNull(accountDimension);
	}

	@Test
	public void valuesNotSet()
	{
		assertUserElementStringsAreNull(newDimension().build());
	}

	@Test
	public void valueSet()
	{
		final AccountDimension accountDimension = newDimension()
				.setUserElementString1(" abc ")
				.build();

		assertThat(accountDimension.getUserElementString1()).isEqualTo("abc");
		assertThat(accountDimension.getUserElementString2()).isNull();
	}
}
