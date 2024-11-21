/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.acct.api.impl;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AcctSchemaId;
import de.metas.organization.OrgId;
import org.adempiere.service.ClientId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SnapshotExtension.class)
public class AccountDimensionTest
{
    private static final AcctSchemaId C_AcctSchema_ID1 = AcctSchemaId.ofRepoId(1);
    private static final ElementValueId C_ElementValue_ID1 = ElementValueId.ofRepoId(1);

    @SuppressWarnings("unused")
    private Expect expect;

    @Test
    public void testAccountDimension_setNullValues()
    {
        final AccountDimension accountDimension = AccountDimension.builder()
                .setAcctSchemaId(C_AcctSchema_ID1)
                .setAD_Client_ID(ClientId.METASFRESH.getRepoId())
                .setAD_Org_ID(OrgId.MAIN.getRepoId())
                .setC_ElementValue_ID(C_ElementValue_ID1.getRepoId())
                .setUserElementString1(null)
                .setUserElementString2(null)
                .setUserElementString3(null)
                .setUserElementString4(null)
                .setUserElementString5(null)
                .setUserElementString6(null)
                .setUserElementString7(null)
                .setUserElementNumber1(null)
                .setUserElementNumber2(null)
                .build();

        Assertions.assertThat(accountDimension.getUserElementString1()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString2()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString3()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString4()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString5()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString6()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString7()).isNull();
        Assertions.assertThat(accountDimension.getUserElementNumber1()).isNull();
        Assertions.assertThat(accountDimension.getUserElementNumber2()).isNull();

        expect.serializer("orderedJson").toMatchSnapshot(accountDimension);
    }

    @Test
    public void testAccountDimension_valuesNotSet()
    {
        final AccountDimension accountDimension = AccountDimension.builder()
                .setAcctSchemaId(C_AcctSchema_ID1)
                .setAD_Client_ID(ClientId.METASFRESH.getRepoId())
                .setAD_Org_ID(OrgId.MAIN.getRepoId())
                .setC_ElementValue_ID(C_ElementValue_ID1.getRepoId())
                .build();

        Assertions.assertThat(accountDimension.getUserElementString1()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString2()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString3()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString4()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString5()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString6()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString7()).isNull();
        Assertions.assertThat(accountDimension.getUserElementNumber1()).isNull();
        Assertions.assertThat(accountDimension.getUserElementNumber2()).isNull();

        expect.serializer("orderedJson").toMatchSnapshot(accountDimension);
    }
}
