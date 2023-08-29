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

package de.metas.contracts.modular.workpackage.impl;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.contracts.modular.impl.MaterialReceiptLineModularContractHandler;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.workpackage.IModularContractLogHandler;
import de.metas.i18n.ExplainedOptional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class MaterialReceiptLineInterimLogHandler implements IModularContractLogHandler<I_M_InOutLine>
{
	private final MaterialReceiptLineHandlerHelper materialReceiptLineHandlerHelper;
	private final MaterialReceiptLineModularContractHandler contractHandler;

	@Override
	public LogAction getLogAction(@NonNull final HandleLogsRequest<I_M_InOutLine> request)
	{
		return switch (request.getModelAction())
				{
					case COMPLETED -> LogAction.CREATE;
					case REVERSED, REACTIVATED, VOIDED -> LogAction.REVERSE;
					default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
				};
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final CreateLogRequest<I_M_InOutLine> request)
	{
		return materialReceiptLineHandlerHelper.createLogEntryCreateRequest(request);
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull final HandleLogsRequest<I_M_InOutLine> handleLogsRequest, @NonNull final FlatrateTermId contractId)
	{
		return materialReceiptLineHandlerHelper.createLogEntryReverseRequest(handleLogsRequest, contractId);
	}

	@Override
	public @NonNull IModularContractTypeHandler<I_M_InOutLine> getModularContractTypeHandler()
	{
		return contractHandler;
	}
}
