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

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.settings.ModularContractTypeId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.lock.api.LockOwner;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
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
	@Nullable Boolean processed;
	@Nullable Boolean billable;
	@Nullable ComputingMethodType computingMethodType;
	@Nullable InvoiceCandidateId invoiceCandidateId;
	@Nullable LockOwner lockOwner;
	@Nullable @Singular Set<ModularContractLogEntryId> entryIds;
	
	@NonNull
	public static ModularContractLogQuery ofEntryIds(@NonNull final Set<ModularContractLogEntryId> entryIds)
	{
		return ModularContractLogQuery.builder()
				.entryIds(entryIds)
				.build();
	}
}
