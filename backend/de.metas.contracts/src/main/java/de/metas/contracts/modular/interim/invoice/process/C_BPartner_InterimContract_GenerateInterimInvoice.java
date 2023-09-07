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

package de.metas.contracts.modular.interim.invoice.process;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.FlatrateTermRequest.ModularFlatrateTermQuery;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.modular.interim.bpartner.BPartnerInterimContract;
import de.metas.contracts.modular.interim.bpartner.BPartnerInterimContractId;
import de.metas.contracts.modular.interim.bpartner.BPartnerInterimContractService;
import de.metas.contracts.modular.interim.invoice.InterimInvoiceCandidateService;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueueResult;
import de.metas.invoicecandidate.process.params.InvoicingParams;
import de.metas.lang.SOTrx;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;

import java.util.Set;

public class C_BPartner_InterimContract_GenerateInterimInvoice extends JavaProcess implements IProcessPrecondition
{
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

	private final BPartnerInterimContractService bPartnerInterimContractService = SpringContextHolder.instance.getBean(BPartnerInterimContractService.class);
	private final InterimInvoiceCandidateService interimInvoiceCandidateService = SpringContextHolder.instance.getBean(InterimInvoiceCandidateService.class);

	private final static AdMessageKey MSG_REJECTION_NO_INTERIM_CONTRACT = AdMessageKey.of("de.metas.contracts.modular.interim.notActiveRejection");

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final BPartnerInterimContractId bPartnerInterimContractId = BPartnerInterimContractId.ofRepoId(context.getSingleSelectedRecordId());
		final BPartnerInterimContract bPartnerInterimContract = bPartnerInterimContractService.getById(bPartnerInterimContractId);
		if (!bPartnerInterimContract.getIsInterimContract())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_REJECTION_NO_INTERIM_CONTRACT));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final BPartnerInterimContractId bPartnerInterimContractId = BPartnerInterimContractId.ofRepoId(getRecord_ID());
		final BPartnerInterimContract bPartnerInterimContract = bPartnerInterimContractService.getById(bPartnerInterimContractId);

		final ModularFlatrateTermQuery modularFlatrateTermQuery = ModularFlatrateTermQuery.builder()
				.bPartnerId(bPartnerInterimContract.getBPartnerId())
				.calendarId(bPartnerInterimContract.getYearAndCalendarId().calendarId())
				.yearId(bPartnerInterimContract.getYearAndCalendarId().yearId())
				.soTrx(SOTrx.PURCHASE)
				.typeConditions(TypeConditions.INTERIM_INVOICE)
				.build();

		final ImmutableSet<InvoiceCandidateId> invoiceCandidateIds = flatrateBL.streamModularFlatrateTermsByQuery(modularFlatrateTermQuery)
				.map(flatrateTermRecord -> interimInvoiceCandidateService.createInterimInvoiceCandidatesFor(flatrateTermRecord, bPartnerInterimContract))
				.flatMap(Set::stream)
				.collect(ImmutableSet.toImmutableSet());

		return prepareAndEnqeue(invoiceCandidateIds);
	}

	private String prepareAndEnqeue(@NonNull final ImmutableSet<InvoiceCandidateId> invoiceCandidateIds)
	{
		final PInstanceId invoiceCandidatesSelectionId = DB.createT_Selection(invoiceCandidateIds, getTrxName());

		final IInvoiceCandidateEnqueueResult enqueueResult = invoiceCandBL.enqueueForInvoicing()
				.setContext(getCtx())
				.setInvoicingParams(getInvoicingParams())
				.setFailIfNothingEnqueued(true) // If no workpackages were created, display error message that no selection was made (07666)
				//
				.prepareAndEnqueueSelection(invoiceCandidatesSelectionId);

		return enqueueResult.getSummaryTranslated(getCtx());
	}

	private InvoicingParams getInvoicingParams()
	{
		return InvoicingParams.ofParams(getParameterAsIParams());
	}

}
