package de.metas.contracts.subscription.order.restart;

import java.util.List;

import de.metas.common.util.time.SystemTime;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ITableRecordReference;
import org.springframework.stereotype.Component;

import de.metas.contracts.IContractChangeBL;
import de.metas.contracts.IContractChangeBL.ContractChangeParameters;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.order.voidorderandrelateddocs.VoidOrderAndRelatedDocsHandler;
import de.metas.order.voidorderandrelateddocs.VoidOrderAndRelatedDocsRequest;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
public class VoidSubscriptionRelatedToOrderHandler implements VoidOrderAndRelatedDocsHandler
{
	@Override
	public RecordsToHandleKey getRecordsToHandleKey()
	{
		return RecordsToHandleKey.of(I_C_Flatrate_Term.Table_Name);
	}

	@Override
	public void handleOrderVoided(@NonNull final VoidOrderAndRelatedDocsRequest request)
	{
		final ContractChangeParameters contractChangeParameters = ContractChangeParameters.builder()
				.changeDate(SystemTime.asTimestamp())
				.isCloseInvoiceCandidate(true)
				.terminationReason(X_C_Flatrate_Term.TERMINATIONREASON_IncorrectlyRecorded)
				.isCreditOpenInvoices(false) // leave all existing invoices alone! there is another handler to deal with them, open or not
				.action(IContractChangeBL.ChangeTerm_ACTION_VoidSingleContract)
				.build();

		final IPair<RecordsToHandleKey, List<ITableRecordReference>> recordsToHandle = request.getRecordsToHandle();

		final IContractChangeBL contractChangeBL = Services.get(IContractChangeBL.class);
		for (final ITableRecordReference currentRecordToHandle : recordsToHandle.getRight())
		{
			// note: that we need to (re)load the flatrate term that we void before their respective successor was voided.
			// if we loaded them all at once before voiding their successor,, they still had C_FlatrateTerm_Next_ID > 0
			final I_C_Flatrate_Term currentTerm = currentRecordToHandle.getModel(I_C_Flatrate_Term.class);

			contractChangeBL.cancelContract(currentTerm, contractChangeParameters);
		}
	}
}
