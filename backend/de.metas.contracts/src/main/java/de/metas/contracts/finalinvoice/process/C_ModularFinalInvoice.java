/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.finalinvoice.process;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.finalinvoice.workpackage.ModularContractInvoiceEnqueuer;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.i18n.AdMessageKey;
import de.metas.invoicecandidate.process.params.InvoicingParams;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class C_ModularFinalInvoice extends JavaProcess implements IProcessPrecondition
{
	private final static AdMessageKey NO_MODULAR_CONTRACT_SELECTED = AdMessageKey.of("de.metas.contracts.finalinvoice.process.NoModularContract");

	private final ModularContractInvoiceEnqueuer modularContractInvoiceEnqueuer = SpringContextHolder.instance.getBean(ModularContractInvoiceEnqueuer.class);

	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (!flatrateBL.isFinalInvoiceableModularContractExists(context.getQueryFilter(I_C_Flatrate_Term.class)))
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(NO_MODULAR_CONTRACT_SELECTED));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final UserId userInChargeId = getUserId();
		final ImmutableSet<FlatrateTermId> selectedModularContractIds = flatrateBL
				.getReadyForFinalInvoicingModularContractIds(getProcessInfo().getQueryFilterOrElseFalse());

		modularContractInvoiceEnqueuer.enqueueFinalInvoice(selectedModularContractIds, userInChargeId, getInvoicingParams());

		return MSG_OK;
	}

	@NonNull
	private InvoicingParams getInvoicingParams()
	{
		return InvoicingParams.ofParams(getParameterAsIParams());
	}
}
