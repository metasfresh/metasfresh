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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.banking.BankAccountId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BP_BankAccount_DirectDebitMandate;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Repository Tables: C_BP_BankAccount_DirectDebitMandate
 * <p>
 * Deliberately has no cache: the SEPA export reads once per run, and every run writes {@code DateLastUsed} to this table
 * (which would reset a table-bound cache); the one-off check must see the current {@code DateLastUsed}.
 */
@Repository
public class DirectDebitMandateRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	public static DirectDebitMandateRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return SpringContextHolder.getBeanOrSupply(DirectDebitMandateRepository.class, DirectDebitMandateRepository::new);
	}

	/**
	 * Loads, with ONE query, the active mandates of all given bank accounts (e.g. of a whole pay selection).
	 * <p>
	 * Only mandates of the export org and of org 0 are considered; use {@link DirectDebitMandatesMap#getActiveMandate(BankAccountId)}
	 * to pick the export org's mandate first, falling back to the org-0 one.
	 * The DB unique index guarantees at most one active mandate per bank account and org.
	 */
	public DirectDebitMandatesMap getActiveMandates(
			@NonNull final Set<BankAccountId> bankAccountIds,
			@NonNull final OrgId exportOrgId)
	{
		if (bankAccountIds.isEmpty())
		{
			return DirectDebitMandatesMap.EMPTY;
		}

		final List<DirectDebitMandate> mandates = queryBL.createQueryBuilder(I_C_BP_BankAccount_DirectDebitMandate.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_C_BP_BankAccount_ID, bankAccountIds)
				.addInArrayFilter(I_C_BP_BankAccount_DirectDebitMandate.COLUMNNAME_AD_Org_ID, ImmutableSet.of(exportOrgId, OrgId.ANY))
				.create()
				.stream()
				.map(this::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return DirectDebitMandatesMap.ofList(mandates, exportOrgId);
	}

	/**
	 * Records that the mandate was used for a collection on {@code collectionDate}.
	 * <p>
	 * {@code DateLastUsed} only ever moves forward: it is set only if it is null or before {@code collectionDate}
	 * (compared as dates in the mandate record's org time zone); otherwise nothing is saved.
	 */
	public void updateDateLastUsed(
			@NonNull final DirectDebitMandateId mandateId,
			@NonNull final LocalDate collectionDate)
	{
		final I_C_BP_BankAccount_DirectDebitMandate record = load(mandateId, I_C_BP_BankAccount_DirectDebitMandate.class);
		final ZoneId orgZoneId = orgDAO.getTimeZone(OrgId.ofRepoId(record.getAD_Org_ID()));

		final LocalDate dateLastUsed = TimeUtil.asLocalDate(record.getDateLastUsed(), orgZoneId);
		if (dateLastUsed != null && !dateLastUsed.isBefore(collectionDate))
		{
			return;
		}

		record.setDateLastUsed(TimeUtil.asTimestamp(collectionDate, orgZoneId));
		saveRecord(record);
	}

	private DirectDebitMandate fromRecord(@NonNull final I_C_BP_BankAccount_DirectDebitMandate record)
	{
		final OrgId orgId = OrgId.ofRepoId(record.getAD_Org_ID());
		final ZoneId orgZoneId = orgDAO.getTimeZone(orgId);

		return DirectDebitMandate.builder()
				.id(DirectDebitMandateId.ofRepoId(record.getC_BP_BankAccount_DirectDebitMandate_ID()))
				.bankAccountId(BankAccountId.ofRepoId(record.getC_BP_BankAccount_ID()))
				.orgId(orgId)
				.type(DirectDebitMandateType.ofCode(record.getMandateType()))
				.reference(record.getMandateReference())
				.mandateDate(TimeUtil.asLocalDate(record.getMandateDate(), orgZoneId))
				.recurring(record.isRecurring())
				.dateLastUsed(TimeUtil.asLocalDate(record.getDateLastUsed(), orgZoneId))
				.build();
	}
}
