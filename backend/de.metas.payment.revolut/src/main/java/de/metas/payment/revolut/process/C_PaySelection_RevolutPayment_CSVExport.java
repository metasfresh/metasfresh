/*
 * #%L
 * de.metas.payment.revolut
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

package de.metas.payment.revolut.process;

import de.metas.banking.PaySelectionId;
import de.metas.banking.payment.IPaySelectionDAO;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.AdMessageKey;
import de.metas.payment.revolut.PaySelectionService;
import de.metas.payment.revolut.RevolutExportService;
import de.metas.payment.revolut.model.RevolutPaymentExport;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.report.ReportResultData;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.X_C_PaySelection;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class C_PaySelection_RevolutPayment_CSVExport extends JavaProcess implements IProcessPrecondition
{
	private final IPaySelectionDAO paySelectionDAO = Services.get(IPaySelectionDAO.class);

	private static final AdMessageKey REVOLUT_AVAILABLE_ONLY_FOR_CREDIT_TRANSFER_ERROR = AdMessageKey.of("C_PaySelection_RevolutAvailableOnlyForCreditTransfer");

	@Override
	@NonNull
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		else if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		else
		{
			final I_C_PaySelection paySelection = paySelectionDAO.getById(PaySelectionId.ofRepoId(context.getSingleSelectedRecordId()))
					.orElseThrow(() -> AdempiereException.newWithPlainMessage("No paySelection found for selected record")
							.appendParametersToMessage()
							.setParameter("recordId", context.getSingleSelectedRecordId()));
			if(!DocStatus.ofNullableCodeOrUnknown(paySelection.getDocStatus()).isCompletedOrClosed())
			{
				return ProcessPreconditionsResolution.rejectWithInternalReason("C_PaySelection_ID=" + paySelection.getC_PaySelection_ID() + " needs to be completed or closed");
			}
			if (!paySelection.getPaySelectionTrxType().equals(X_C_PaySelection.PAYSELECTIONTRXTYPE_CreditTransfer))
			{
				return ProcessPreconditionsResolution.rejectWithInternalReason(msgBL.getTranslatableMsgText(REVOLUT_AVAILABLE_ONLY_FOR_CREDIT_TRANSFER_ERROR));
			}
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final RevolutExportService revolutExportService = SpringContextHolder.instance.getBean(RevolutExportService.class);
		final PaySelectionService paySelectionService = SpringContextHolder.instance.getBean(PaySelectionService.class);

		final I_C_PaySelection paySelection = paySelectionDAO.getById(PaySelectionId.ofRepoId(getRecord_ID()))
				.orElseThrow(() -> new AdempiereException("No paySelection found for selected record")
						.appendParametersToMessage()
						.setParameter("recordId", getRecord_ID()));

		final List<RevolutPaymentExport> revolutPaymentExportList = paySelectionService.computeRevolutPaymentExportList(paySelection);

		final List<RevolutPaymentExport> savedRevolutPaymentExportList = revolutExportService.saveAll(revolutPaymentExportList);

		final PaySelectionId paySelectionId = PaySelectionId.ofRepoId(paySelection.getC_PaySelection_ID());
		final ReportResultData reportResultData = revolutExportService.exportToCsv(paySelectionId, savedRevolutPaymentExportList);

		paySelection.setLastRevolutExport(Timestamp.from(Instant.now()));
		paySelection.setLastRevolutExportBy_ID(getAD_User_ID());

		paySelectionDAO.save(paySelection);

		getProcessInfo().getResult().setReportData(reportResultData);

		return MSG_OK;
	}
}
