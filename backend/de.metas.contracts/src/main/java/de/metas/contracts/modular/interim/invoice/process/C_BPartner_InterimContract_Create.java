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

import de.metas.contracts.FlatrateTermRequest.ModularFlatrateTermQuery;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.modular.interim.bpartner.BPartnerInterimContract;
import de.metas.contracts.modular.interim.bpartner.BPartnerInterimContractId;
import de.metas.contracts.modular.interim.bpartner.BPartnerInterimContractService;
import de.metas.contracts.modular.interim.invoice.service.IInterimInvoiceFlatrateTermBL;
import de.metas.contracts.modular.interim.invoice.service.IInterimInvoiceFlatrateTermDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.lang.SOTrx;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicBoolean;

public class C_BPartner_InterimContract_Create extends JavaProcess implements IProcessPrecondition
{
	@Param(mandatory = true, parameterName = "DateFrom")
	private Timestamp p_DateFrom;
	@Param(mandatory = true, parameterName = "DateTo")
	private Timestamp p_DateTo;

	private final IInterimInvoiceFlatrateTermDAO interimInvoiceFlatrateTermDAO = Services.get(IInterimInvoiceFlatrateTermDAO.class);
	private final IInterimInvoiceFlatrateTermBL interimInvoiceFlatrateTermBL = Services.get(IInterimInvoiceFlatrateTermBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final BPartnerInterimContractService bPartnerInterimContractService = SpringContextHolder.instance.getBean(BPartnerInterimContractService.class);

	private final static AdMessageKey MSG_REJECTION_NO_INTERIM_CONTRACT = AdMessageKey.of("de.metas.contracts.modular.interim.notActiveRejection");

	private final static AdMessageKey ERROR_NO_MODULAR_CONTRACTS_FOUND = AdMessageKey.of("de.metas.contracts.modular.interim.noModularContractsFound");

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
				.typeConditions(TypeConditions.MODULAR_CONTRACT)
				.dateFrom(p_DateFrom)
				.dateTo(p_DateTo)
				.build();

		final AtomicBoolean isEmpty = new AtomicBoolean(true);
		flatrateBL.streamModularFlatrateTermsByQuery(modularFlatrateTermQuery)
				.forEach(flatrateTermRecord -> {
					isEmpty.set(false);
					interimInvoiceFlatrateTermBL.create(flatrateTermRecord, p_DateFrom, p_DateTo);
				});
		if (isEmpty.get())
		{
			throw new AdempiereException(ERROR_NO_MODULAR_CONTRACTS_FOUND);
		}

		return MSG_OK;
	}

}
