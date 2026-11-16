/*
 * #%L
 * de.metas.business
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

package de.metas.banking.mandate;

import com.google.common.collect.ImmutableSet;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.organization.OrgId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_BankAccount_DirectDebitMandate;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class DirectDebitMandateRepositoryTest
{
	private static final BPartnerId BPARTNER = BPartnerId.ofRepoId(20);
	private static final BankAccountId BANK_ACCOUNT = BankAccountId.ofRepoId(10);

	private DirectDebitMandateRepository repository;
	private OrgId exportOrg;
	private OrgId otherOrg;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = DirectDebitMandateRepository.newInstanceForUnitTesting();
		exportOrg = AdempiereTestHelper.createOrgWithTimeZone("exportOrg");
		otherOrg = AdempiereTestHelper.createOrgWithTimeZone("otherOrg");
	}

	private I_C_BP_BankAccount_DirectDebitMandate mandateRecord(final OrgId orgId, final String reference)
	{
		final I_C_BP_BankAccount_DirectDebitMandate record = newInstance(I_C_BP_BankAccount_DirectDebitMandate.class);
		record.setAD_Org_ID(orgId.getRepoId());
		record.setC_BPartner_ID(BPARTNER.getRepoId());
		record.setC_BP_BankAccount_ID(BANK_ACCOUNT.getRepoId());
		record.setMandateType(DirectDebitMandateType.B2B.getCode());
		record.setMandateReference(reference);
		record.setMandateDate(TimeUtil.asTimestamp(LocalDate.of(2026, 1, 10)));
		record.setIsRecurring(true);
		saveRecord(record);
		return record;
	}

	private String getMandateReference(final OrgId exportOrgId)
	{
		return repository.getMandates(ImmutableSet.of(BANK_ACCOUNT), exportOrgId)
				.getMandate(BANK_ACCOUNT)
				.map(DirectDebitMandate::getReference)
				.orElse(null);
	}

	@Test
	void mandateOfAnotherOrgIsIgnored()
	{
		mandateRecord(otherOrg, "OTHER");
		mandateRecord(OrgId.ANY, "ANY");

		assertThat(getMandateReference(exportOrg)).isEqualTo("ANY");
	}

	@Test
	void exportOrgMandateWins()
	{
		mandateRecord(otherOrg, "OTHER");
		mandateRecord(OrgId.ANY, "ANY");
		mandateRecord(exportOrg, "EXPORT");

		assertThat(getMandateReference(exportOrg)).isEqualTo("EXPORT");
	}

	@Test
	void exportOrgMandateWins_insertedBeforeOrgAny()
	{
		mandateRecord(exportOrg, "EXPORT");
		mandateRecord(OrgId.ANY, "ANY");

		assertThat(getMandateReference(exportOrg)).isEqualTo("EXPORT");
	}

	@Test
	void exportFromOrgAny_findsOnlyTheOrgAnyMandate()
	{
		mandateRecord(otherOrg, "OTHER");
		mandateRecord(OrgId.ANY, "ANY");

		assertThat(getMandateReference(OrgId.ANY)).isEqualTo("ANY");
	}

	@Test
	void inactiveMandateIsIgnored()
	{
		mandateRecord(OrgId.ANY, "ANY");
		final I_C_BP_BankAccount_DirectDebitMandate inactive = mandateRecord(exportOrg, "INACTIVE");
		inactive.setIsActive(false);
		saveRecord(inactive);

		assertThat(getMandateReference(exportOrg)).isEqualTo("ANY");
	}
}
