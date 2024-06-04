/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular.log;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_ModCntr_Log;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.settings.ModularContractModuleId;
import de.metas.contracts.modular.settings.ModularContractTypeId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.lock.api.LockOwner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import javax.annotation.Nullable;
import java.util.Set;

@Value
@Builder
public class ModularContractLogQuery
{
	@Nullable TableRecordReferenceSet referenceSet;
	@Nullable LogEntryContractType contractType;
	@Nullable FlatrateTermId flatrateTermId;
	@Nullable ModularContractTypeId modularContractTypeId;
	@Nullable ModularContractModuleId contractModuleId;
	@Nullable Boolean processed;
	@Nullable Boolean billable;
	@Nullable InvoiceCandidateId invoiceCandidateId;
	@Nullable LockOwner lockOwner;
	@Nullable InvoicingGroupId invoicingGroupId;
	@Nullable LogEntryDocumentType logEntryDocumentType;
	boolean onlyIfAmountIsSet;
	@NonNull @Singular ImmutableSet<ModularContractLogEntryId> entryIds;
	@NonNull @Singular ImmutableList<OrderBy> orderBys;
	@NonNull @Singular ImmutableSet<ComputingMethodType> computingMethodTypes;
	@Builder.Default
	boolean isComputingMethodTypeActive = true;
	@NonNull @Singular ImmutableSet<AdTableId> excludedReferencedTableIds;
	
	@NonNull
	public static ModularContractLogQuery ofEntryIds(@NonNull final Set<ModularContractLogEntryId> entryIds)
	{
		return ModularContractLogQuery.builder()
				.entryIds(entryIds)
				.build();
	}

	@Getter
	@AllArgsConstructor
	public enum OrderBy
	{
		TRANSACTION_DATE_ASC(I_ModCntr_Log.COLUMNNAME_DateTrx, true),
		;

		final String columnName;
		final boolean ascending;
	}
}
