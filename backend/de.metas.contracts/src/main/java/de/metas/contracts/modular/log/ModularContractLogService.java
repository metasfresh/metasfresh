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
import de.metas.i18n.AdMessageKey;
import de.metas.order.OrderLineId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModularContractLogService
{
	private static final AdMessageKey MSG_ERROR_DOCUMENT_LINE_DELETION = AdMessageKey.of("documentLineDeletionErrorBecauseOfRelatedModuleContractLog");

	private final ModularContractLogDAO modularContractLogDAO;

	public ModularContractLogService(@NonNull final ModularContractLogDAO modularContractLogDAO)
	{
		this.modularContractLogDAO = modularContractLogDAO;
	}

	public void throwErrorIfLogExistsForDocumentLine(@NonNull final TableRecordReference tableRecordReference)
	{
		if (modularContractLogDAO.hasAnyModularLogs(tableRecordReference))
		{
			throw new AdempiereException(MSG_ERROR_DOCUMENT_LINE_DELETION);
		}
	}

	public void changeBillableStatus(
			@NonNull final ModularContractLogQuery query,
			final boolean isBillable)
	{
		modularContractLogDAO.changeBillableStatus(query, isBillable);
	}

	@NonNull
	public Optional<ModularContractLogEntry> getLastModularContractLog(
			@NonNull final FlatrateTermId modularFlatrateTermId,
			@NonNull final OrderLineId orderLineId)
	{
		return modularContractLogDAO.getLastModularContractLog(modularFlatrateTermId, orderLineId);
	}

	public void throwErrorIfProcessedLogsExistForRecord(
			@NonNull final TableRecordReference tableRecordReference,
			@NonNull final AdMessageKey errorMessage)
	{
		if (hasAnyProcessedLogs(tableRecordReference))
		{
			throw new AdempiereException(errorMessage);
		}
	}

	private boolean hasAnyProcessedLogs(@NonNull final TableRecordReference tableRecordReference)
	{
		final ModularContractLogQuery query = ModularContractLogQuery.builder()
				.referenceSet(TableRecordReferenceSet.of(tableRecordReference))
				.processed(true)
				.build();

		return modularContractLogDAO.anyMatch(query);
	}
}
