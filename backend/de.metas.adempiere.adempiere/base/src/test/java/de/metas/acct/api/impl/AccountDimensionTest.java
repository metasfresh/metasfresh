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
import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AccountDimension;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SnapshotExtension.class)
public class AccountDimensionTest
{
    private static final int ZERO = 0;
    private static final int NULL_REPO_ID = -1;

    private static final ImmutableSet<AcctSegmentType> INT_SEGMENTS = ImmutableSet.of(
            AcctSegmentType.Client,
            AcctSegmentType.Organization,
            AcctSegmentType.SubAccount,
            AcctSegmentType.Product,
            AcctSegmentType.BPartner,
            AcctSegmentType.OrgTrx,
            AcctSegmentType.LocationFrom,
            AcctSegmentType.LocationTo,
            AcctSegmentType.Project,
            AcctSegmentType.Campaign,
            AcctSegmentType.Activity,
            AcctSegmentType.UserList1,
            AcctSegmentType.UserList2,
            AcctSegmentType.UserElement1,
            AcctSegmentType.UserElement2,
            AcctSegmentType.SalesOrder,
            AcctSegmentType.SectionCode,
            AcctSegmentType.HarvestingCalendar,
            AcctSegmentType.HarvestingYear,
            AcctSegmentType.SalesRegion,
            AcctSegmentType.Account
    );

    private static final ImmutableSet<AcctSegmentType> NULLABLE_SEGMENTS = ImmutableSet.of(

            AcctSegmentType.UserElementString1,
            AcctSegmentType.UserElementString2,
            AcctSegmentType.UserElementString3,
            AcctSegmentType.UserElementString4,
            AcctSegmentType.UserElementString5,
            AcctSegmentType.UserElementString6,
            AcctSegmentType.UserElementString7,
            AcctSegmentType.UserElementNumber1,
            AcctSegmentType.UserElementNumber2,
            AcctSegmentType.UserElementDate1,
            AcctSegmentType.UserElementDate2
    );

    private static final ImmutableSet<AcctSegmentType> ALL_SEGMENTS = ImmutableSet.copyOf(AcctSegmentType.values());



    @SuppressWarnings("unused")
    private Expect expect;

    @Test
    public void testAccountDimension_setNullValues()
    {
        final AccountDimension accountDimension = AccountDimension.builder()
                .setAD_Client_ID(NULL_REPO_ID)
                .setAD_Org_ID(NULL_REPO_ID)
                .setC_ElementValue_ID(null)
                .setC_SubAcct_ID(NULL_REPO_ID)
                .setM_Product_ID(NULL_REPO_ID)
                .setC_BPartner_ID(NULL_REPO_ID)
                .setAD_OrgTrx_ID(NULL_REPO_ID)
                .setC_LocFrom_ID(NULL_REPO_ID)
                .setC_LocTo_ID(NULL_REPO_ID)
                .setC_SalesRegion_ID(null)
                .setC_Project_ID(NULL_REPO_ID)
                .setC_Campaign_ID(NULL_REPO_ID)
                .setC_Activity_ID(NULL_REPO_ID)
                .setUser1_ID(NULL_REPO_ID)
                .setUser2_ID(NULL_REPO_ID)
                .setUserElement1_ID(NULL_REPO_ID)
                .setUserElement2_ID(NULL_REPO_ID)
                .setUserElementNumber1(null)
                .setUserElementNumber2(null)
                .setUserElementString1(null)
                .setUserElementString2(null)
                .setUserElementString3(null)
                .setUserElementString4(null)
                .setUserElementString5(null)
                .setUserElementString6(null)
                .setUserElementString7(null)
                .setSalesOrderId(NULL_REPO_ID)
                .setM_SectionCode_ID(NULL_REPO_ID)
                .setC_Harvesting_Calendar_ID(NULL_REPO_ID)
                .setHarvesting_Year_ID(NULL_REPO_ID)
                .setUserElementDate1(null)
                .setUserElementDate2(null)
                .build();

        expect.serializer("orderedJson").toMatchSnapshot(accountDimension);

        Assertions.assertThat(accountDimension.getAD_Client_ID()).isEqualTo(NULL_REPO_ID);
        Assertions.assertThat(accountDimension.getAD_Org_ID()).isEqualTo(NULL_REPO_ID);
        Assertions.assertThat(accountDimension.getC_ElementValue_ID()).isEqualTo(NULL_REPO_ID);
        Assertions.assertThat(accountDimension.getC_SubAcct_ID()).isEqualTo(NULL_REPO_ID);
        Assertions.assertThat(accountDimension.getM_Product_ID()).isEqualTo(NULL_REPO_ID);
        Assertions.assertThat(accountDimension.getC_BPartner_ID()).isEqualTo(NULL_REPO_ID);
        Assertions.assertThat(accountDimension.getAD_OrgTrx_ID()).isEqualTo(NULL_REPO_ID);
        Assertions.assertThat(accountDimension.getC_LocFrom_ID()).isEqualTo(NULL_REPO_ID);
        Assertions.assertThat(accountDimension.getC_LocTo_ID()).isEqualTo(NULL_REPO_ID);
        Assertions.assertThat(accountDimension.getC_SalesRegion_ID()).isEqualTo(NULL_REPO_ID);
        Assertions.assertThat(accountDimension.getC_Project_ID()).isEqualTo(NULL_REPO_ID);
        Assertions.assertThat(accountDimension.getC_Campaign_ID()).isEqualTo(NULL_REPO_ID);
        Assertions.assertThat(accountDimension.getC_Activity_ID()).isEqualTo(NULL_REPO_ID);
        Assertions.assertThat(accountDimension.getUser1_ID()).isEqualTo(NULL_REPO_ID);
        Assertions.assertThat(accountDimension.getUser2_ID()).isEqualTo(NULL_REPO_ID);
        Assertions.assertThat(accountDimension.getUserElement1_ID()).isEqualTo(NULL_REPO_ID);
        Assertions.assertThat(accountDimension.getUserElement2_ID()).isEqualTo(NULL_REPO_ID);
        Assertions.assertThat(accountDimension.getUserElementNumber1()).isNull();
        Assertions.assertThat(accountDimension.getUserElementNumber2()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString1()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString2()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString3()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString4()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString5()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString6()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString7()).isNull();
        Assertions.assertThat(accountDimension.getSalesOrderId()).isEqualTo(NULL_REPO_ID);
        Assertions.assertThat(accountDimension.getM_SectionCode_ID()).isEqualTo(NULL_REPO_ID);
        Assertions.assertThat(accountDimension.getC_Harvesting_Calendar_ID()).isEqualTo(NULL_REPO_ID);
        Assertions.assertThat(accountDimension.getHarvesting_Year_ID()).isEqualTo(NULL_REPO_ID);
        Assertions.assertThat(accountDimension.getUserElementDate1()).isNull();
        Assertions.assertThat(accountDimension.getUserElementDate2()).isNull();

        ALL_SEGMENTS.forEach(acctSegmentType -> assertSegmentValueSet(accountDimension, acctSegmentType));
        NULLABLE_SEGMENTS.forEach(acctSegmentType -> assertSegmentValueEmptyString(accountDimension, acctSegmentType));
        INT_SEGMENTS.forEach(acctSegmentType -> assertSegmentValueNullRepoId(accountDimension, acctSegmentType));
    }

    private void assertSegmentValueSet(final AccountDimension accountDimension, final AcctSegmentType acctSegmentType)
    {
        Assertions.assertThat(accountDimension.isSegmentValueSet(acctSegmentType)).as(String.valueOf(acctSegmentType)).isTrue();
    }

    private void assertSegmentValueNullRepoId(final AccountDimension accountDimension, final AcctSegmentType acctSegmentType)
    {
        Assertions.assertThat(accountDimension.getSegmentValue(acctSegmentType)).as(String.valueOf(acctSegmentType)).isEqualTo(NULL_REPO_ID);
    }

    private void assertSegmentValueEmptyString(final AccountDimension accountDimension, final AcctSegmentType acctSegmentType)
    {
        Assertions.assertThat(accountDimension.getSegmentValue(acctSegmentType)).as(String.valueOf(acctSegmentType)).isEqualTo("");
    }

    @Test
    public void testAccountDimension_valuesNotSet()
    {
        final AccountDimension accountDimension = AccountDimension.NULL;
        ALL_SEGMENTS.forEach(acctSegmentType -> assertSegmentValueNotSet(accountDimension, acctSegmentType));
        ALL_SEGMENTS.forEach(acctSegmentType -> assertSegmentValueNull(accountDimension, acctSegmentType));
    }

    private void assertSegmentValueNotSet(final AccountDimension accountDimension, final AcctSegmentType acctSegmentType)
    {
        Assertions.assertThat(accountDimension.isSegmentValueSet(acctSegmentType)).as(String.valueOf(acctSegmentType)).isFalse();
    }

    private void assertSegmentValueNull(final AccountDimension accountDimension, final AcctSegmentType acctSegmentType)
    {
        Assertions.assertThat(accountDimension.getSegmentValue(acctSegmentType)).as(String.valueOf(acctSegmentType)).isNull();
    }

    @Test
    public void testAccountDimension_defaultValues()
    {
        final AccountDimension accountDimension = AccountDimension.NULL;

        Assertions.assertThat(accountDimension.getAD_Client_ID()).isEqualTo(ZERO);
        Assertions.assertThat(accountDimension.getAD_Org_ID()).isEqualTo(ZERO);
        Assertions.assertThat(accountDimension.getC_ElementValue_ID()).isEqualTo(ZERO);
        Assertions.assertThat(accountDimension.getC_SubAcct_ID()).isEqualTo(ZERO);
        Assertions.assertThat(accountDimension.getM_Product_ID()).isEqualTo(ZERO);
        Assertions.assertThat(accountDimension.getC_BPartner_ID()).isEqualTo(ZERO);
        Assertions.assertThat(accountDimension.getAD_OrgTrx_ID()).isEqualTo(ZERO);
        Assertions.assertThat(accountDimension.getC_LocFrom_ID()).isEqualTo(ZERO);
        Assertions.assertThat(accountDimension.getC_LocTo_ID()).isEqualTo(ZERO);
        Assertions.assertThat(accountDimension.getC_SalesRegion_ID()).isEqualTo(ZERO);
        Assertions.assertThat(accountDimension.getC_Project_ID()).isEqualTo(ZERO);
        Assertions.assertThat(accountDimension.getC_Campaign_ID()).isEqualTo(ZERO);
        Assertions.assertThat(accountDimension.getC_Activity_ID()).isEqualTo(ZERO);
        Assertions.assertThat(accountDimension.getUser1_ID()).isEqualTo(ZERO);
        Assertions.assertThat(accountDimension.getUser2_ID()).isEqualTo(ZERO);
        Assertions.assertThat(accountDimension.getUserElement1_ID()).isEqualTo(ZERO);
        Assertions.assertThat(accountDimension.getUserElement2_ID()).isEqualTo(ZERO);
        Assertions.assertThat(accountDimension.getUserElementNumber1()).isNull();
        Assertions.assertThat(accountDimension.getUserElementNumber2()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString1()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString2()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString3()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString4()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString5()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString6()).isNull();
        Assertions.assertThat(accountDimension.getUserElementString7()).isNull();
        Assertions.assertThat(accountDimension.getSalesOrderId()).isEqualTo(ZERO);
        Assertions.assertThat(accountDimension.getM_SectionCode_ID()).isEqualTo(ZERO);
        Assertions.assertThat(accountDimension.getC_Harvesting_Calendar_ID()).isEqualTo(ZERO);
        Assertions.assertThat(accountDimension.getHarvesting_Year_ID()).isEqualTo(ZERO);
        Assertions.assertThat(accountDimension.getUserElementDate1()).isNull();
        Assertions.assertThat(accountDimension.getUserElementDate2()).isNull();
    }
}
