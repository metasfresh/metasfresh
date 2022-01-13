/*
 * #%L
 * de.metas.business
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

package de.metas.banking.invoice_auto_allocation;

import com.google.common.collect.ImmutableList;
import de.metas.banking.BankAccountId;
import de.metas.cache.CCache;
import de.metas.document.DocTypeId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_BP_BankAccount_InvoiceAutoAllocateRule;
import org.springframework.stereotype.Repository;

@Repository
public class BankAccountInvoiceAutoAllocRulesRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, BankAccountInvoiceAutoAllocRules> cache = CCache.<Integer, BankAccountInvoiceAutoAllocRules>builder()
			.tableName(I_C_BP_BankAccount_InvoiceAutoAllocateRule.Table_Name)
			.build();

	public BankAccountInvoiceAutoAllocRules getRules()
	{
		return cache.getOrLoad(0, this::retrieveRules);
	}

	private BankAccountInvoiceAutoAllocRules retrieveRules()
	{
		final ImmutableList<BankAccountInvoiceAutoAllocRule> rules = queryBL
				.createQueryBuilder(I_C_BP_BankAccount_InvoiceAutoAllocateRule.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(BankAccountInvoiceAutoAllocRulesRepository::toBankAccountInvoiceAutoAllocRule)
				.collect(ImmutableList.toImmutableList());

		return BankAccountInvoiceAutoAllocRules.ofRulesList(rules);
	}

	private static BankAccountInvoiceAutoAllocRule toBankAccountInvoiceAutoAllocRule(
			@NonNull final I_C_BP_BankAccount_InvoiceAutoAllocateRule record)
	{
		return BankAccountInvoiceAutoAllocRule.builder()
				.bankAccountId(BankAccountId.ofRepoId(record.getC_BP_BankAccount_ID()))
				.invoiceDocTypeId(DocTypeId.ofRepoId(record.getC_DocTypeInvoice_ID()))
				.build();
	}
}
