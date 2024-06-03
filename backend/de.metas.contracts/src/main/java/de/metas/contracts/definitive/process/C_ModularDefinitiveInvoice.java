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

package de.metas.contracts.definitive.process;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.definitive.notification.DefinitiveInvoiceUserNotificationsProducer;
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
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.SpringContextHolder;

import java.util.Collection;
import java.util.Collections;

public class C_ModularDefinitiveInvoice extends JavaProcess implements IProcessPrecondition
{
	private final static AdMessageKey ERROR_NOT_ALL_QTY_HAS_BEEN_SHIPPED = AdMessageKey.of("de.metas.contracts.definitiveinvoice.process.NotAllQtyShipped");
	private final ModularContractInvoiceEnqueuer modularContractInvoiceEnqueuer = SpringContextHolder.instance.getBean(ModularContractInvoiceEnqueuer.class);
	private final DefinitiveInvoiceUserNotificationsProducer userNotificationsProducer = DefinitiveInvoiceUserNotificationsProducer.newInstance();
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!flatrateBL.isDefinitiveInvoiceableModularContractExists(context.getQueryFilter(I_C_Flatrate_Term.class)))
		{
			return ProcessPreconditionsResolution.reject();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final UserId userInChargeId = getUserId();
		final Collection<FlatrateTermId> selectedContracts = getSelectedContracts();
		final boolean isSingleSelection = selectedContracts.size() == 1;

		final ImmutableSet<FlatrateTermId> contractsReadyForDefinitiveInvoice = flatrateBL
				.getReadyForDefinitiveInvoicingModularContractIds(getProcessInfo().getQueryFilterOrElseFalse());

		if (contractsReadyForDefinitiveInvoice.isEmpty() && isSingleSelection)
		{
			return msgBL.getMsg(ERROR_NOT_ALL_QTY_HAS_BEEN_SHIPPED, Collections.singletonList(getProcessInfo().getRecord(I_C_Flatrate_Term.class).getDocumentNo()));
		}

		selectedContracts.stream()
				.filter(contract -> !contractsReadyForDefinitiveInvoice.contains(contract))
				.forEach(contract -> userNotificationsProducer.notifyCannotBeGenerated(contract, userInChargeId));
		modularContractInvoiceEnqueuer.enqueueDefinitiveInvoice(contractsReadyForDefinitiveInvoice, userInChargeId, getInvoicingParams());

		return MSG_OK;
	}

	private Collection<FlatrateTermId> getSelectedContracts()
	{
		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addFilter(getProcessInfo().getQueryFilterOrElseFalse())
				.create()
				.listIds(FlatrateTermId::ofRepoId);
	}

	@NonNull
	private InvoicingParams getInvoicingParams()
	{
		return InvoicingParams.ofParams(getParameterAsIParams());
	}
}
