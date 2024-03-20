/*
 * #%L
 * de.metas.business
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

package de.metas.remittanceadvice.interceptor;

import de.metas.document.IDocTypeDAO;
import de.metas.i18n.BooleanWithReason;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.logging.LogManager;
import de.metas.remittanceadvice.RemittanceAdvice;
import de.metas.remittanceadvice.RemittanceAdviceId;
import de.metas.remittanceadvice.RemittanceAdviceLine;
import de.metas.remittanceadvice.RemittanceAdviceLineId;
import de.metas.remittanceadvice.RemittanceAdviceRepository;
import de.metas.remittanceadvice.RemittanceAdviceService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_RemittanceAdvice_Line;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Interceptor(I_C_RemittanceAdvice_Line.class)
@Callout(I_C_RemittanceAdvice_Line.class)
@Component
public class C_RemittanceAdvice_Line
{
	private static final Logger log = LogManager.getLogger(C_RemittanceAdvice_Line.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	final RemittanceAdviceRepository remittanceAdviceRepo;
	final RemittanceAdviceService remittanceAdviceService;

	public C_RemittanceAdvice_Line(final RemittanceAdviceRepository remittanceAdviceRepo, final RemittanceAdviceService remittanceAdviceService)
	{
		this.remittanceAdviceRepo = remittanceAdviceRepo;
		this.remittanceAdviceService = remittanceAdviceService;
	}


	@Init
	public void registerCallout()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_C_RemittanceAdvice_Line.COLUMNNAME_ServiceFeeAmount)
	public void updateFromDocType(final I_C_RemittanceAdvice_Line record, final ICalloutField field)
	{
		final RemittanceAdviceId remittanceAdviceId = RemittanceAdviceId.ofRepoId(record.getC_RemittanceAdvice_ID());

		final RemittanceAdvice remittanceAdvice = remittanceAdviceRepo.getRemittanceAdvice(remittanceAdviceId);

		if (remittanceAdvice.getServiceFeeCurrencyId() == null)
		{
			throw new AdempiereException("Missing C_RemittanceAdvice.C_ServiceFeeAmount_Currency_ID");
		}
	}


	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW },
			ifColumnsChanged = { I_C_RemittanceAdvice_Line.COLUMNNAME_C_Invoice_ID,
					I_C_RemittanceAdvice_Line.COLUMNNAME_RemittanceAmt,
					I_C_RemittanceAdvice_Line.COLUMNNAME_PaymentDiscountAmt,
					I_C_RemittanceAdvice_Line.COLUMNNAME_ServiceFeeAmount })
	public void resolveRemittanceAdviceLine(@NonNull final I_C_RemittanceAdvice_Line record)
	{
		final RemittanceAdviceId remittanceAdviceId = RemittanceAdviceId.ofRepoId(record.getC_RemittanceAdvice_ID());
		final RemittanceAdvice remittanceAdvice = remittanceAdviceRepo.getRemittanceAdvice(remittanceAdviceId);

		final RemittanceAdviceLineId remittanceAdviceLineId = RemittanceAdviceLineId.ofRepoId(record.getC_RemittanceAdvice_Line_ID());

		final RemittanceAdviceLine remittanceAdviceLine = remittanceAdvice.getLine(remittanceAdviceLineId)
				.orElseThrow(() -> new AdempiereException("No line found under RemittanceAdviceId: {} with lineId: {}")
						.appendParametersToMessage()
						.setParameter("RemittanceAdviceId", remittanceAdviceId)
						.setParameter("RemittanceAdviceLineId", remittanceAdviceLineId));

		if (record.getC_Invoice_ID() > 0)
		{
			remittanceAdviceService.resolveRemittanceAdviceLine(remittanceAdvice, remittanceAdviceLine);
		}
		else
		{
			remittanceAdviceLine.removeInvoice();
		}

		final boolean readOnlyCurrencyFlagChanged = remittanceAdvice.recomputeCurrenciesReadOnlyFlag();

		if (readOnlyCurrencyFlagChanged)
		{
			remittanceAdviceRepo.updateRemittanceAdvice(remittanceAdvice);
		}
		else //update only the changed line
		{
			remittanceAdviceRepo.updateRemittanceAdviceLine(remittanceAdviceLine);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_DELETE },
			ifColumnsChanged = { I_C_RemittanceAdvice_Line.COLUMNNAME_RemittanceAmt,
					I_C_RemittanceAdvice_Line.COLUMNNAME_PaymentDiscountAmt,
					I_C_RemittanceAdvice_Line.COLUMNNAME_ServiceFeeAmount })
	public void computeRemittanceSums(@NonNull final I_C_RemittanceAdvice_Line record)
	{
		final RemittanceAdviceId remittanceAdviceId = RemittanceAdviceId.ofRepoId(record.getC_RemittanceAdvice_ID());

		final RemittanceAdvice remittanceAdvice = remittanceAdviceRepo.getRemittanceAdvice(remittanceAdviceId);
		final BooleanWithReason computationDone = remittanceAdvice.recomputeSumsFromLines();

		if (computationDone.isFalse())
		{
			log.warn("*** WARN computeRemittanceSums: remittance sums were not recomputed due to: {}", computationDone.getReason());
		}
		else
		{
			remittanceAdviceRepo.updateRemittanceAdvice(remittanceAdvice);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW },
			ifColumnsChanged = {I_C_RemittanceAdvice_Line.COLUMNNAME_C_BPartner_ID, I_C_RemittanceAdvice_Line.COLUMNNAME_Bill_BPartner_ID})
	public void validateRemittanceAdviceLineBPartner(@NonNull final I_C_RemittanceAdvice_Line record)
	{
		final RemittanceAdviceLine remittanceAdviceLine = getLineOrFail(record);

		remittanceAdviceLine.validateBPartner();

		remittanceAdviceRepo.updateRemittanceAdviceLine(remittanceAdviceLine);

	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW },
			ifColumnsChanged = I_C_RemittanceAdvice_Line.COLUMNNAME_ExternalInvoiceDocBaseType)
	public void validateRemittanceAdviceLineInvoiceDocType(@NonNull final I_C_RemittanceAdvice_Line record)
	{
		final RemittanceAdviceLine remittanceAdviceLine = getLineOrFail(record);

		if (remittanceAdviceLine.getInvoiceId() != null)
		{
			final I_C_Invoice lineResolvedInvoice = invoiceDAO.getByIdInTrx(remittanceAdviceLine.getInvoiceId());
			final I_C_DocType invoiceDocType = docTypeDAO.getRecordById(lineResolvedInvoice.getC_DocTypeTarget_ID());

			remittanceAdviceLine.validateInvoiceDocBaseType(invoiceDocType.getDocBaseType());

			remittanceAdviceRepo.updateRemittanceAdviceLine(remittanceAdviceLine);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW },
			ifColumnsChanged = { I_C_RemittanceAdvice_Line.COLUMNNAME_IsLineAcknowledged,
			I_C_RemittanceAdvice_Line.COLUMNNAME_IsInvoiceResolved,
			I_C_RemittanceAdvice_Line.COLUMNNAME_IsAmountValid})
	public void recomputeIsDocumentAcknowledged(@NonNull final I_C_RemittanceAdvice_Line record)
	{
		final RemittanceAdviceId remittanceAdviceId = RemittanceAdviceId.ofRepoId(record.getC_RemittanceAdvice_ID());
		final RemittanceAdvice remittanceAdvice = remittanceAdviceRepo.getRemittanceAdvice(remittanceAdviceId);

		final boolean hasAcknowledgedStatusChanged = remittanceAdvice.recomputeIsDocumentAcknowledged();

		if (hasAcknowledgedStatusChanged)
		{
			remittanceAdviceRepo.updateRemittanceAdvice(remittanceAdvice);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_DELETE })
	public void recomputeReadOnlyCurrenciesFlagOnDelete(@NonNull final I_C_RemittanceAdvice_Line record)
	{
		final RemittanceAdviceId remittanceAdviceId = RemittanceAdviceId.ofRepoId(record.getC_RemittanceAdvice_ID());
		final RemittanceAdvice remittanceAdvice = remittanceAdviceRepo.getRemittanceAdvice(remittanceAdviceId);

		final boolean readOnlyCurrenciesFlagChanged = remittanceAdvice.recomputeCurrenciesReadOnlyFlag();

		if (readOnlyCurrenciesFlagChanged)
		{
			remittanceAdviceRepo.updateRemittanceAdvice(remittanceAdvice);
		}
	}

	@NonNull
	private RemittanceAdviceLine getLineOrFail(@NonNull final I_C_RemittanceAdvice_Line record)
	{
		final RemittanceAdviceId remittanceAdviceId = RemittanceAdviceId.ofRepoId(record.getC_RemittanceAdvice_ID());
		final RemittanceAdvice remittanceAdvice = remittanceAdviceRepo.getRemittanceAdvice(remittanceAdviceId);

		final RemittanceAdviceLineId remittanceAdviceLineId = RemittanceAdviceLineId.ofRepoId(record.getC_RemittanceAdvice_Line_ID());

		return remittanceAdvice.getLine(remittanceAdviceLineId)
				.orElseThrow(() -> new AdempiereException("No line found under RemittanceAdviceId: {} with lineId: {}")
						.appendParametersToMessage()
						.setParameter("RemittanceAdviceId", remittanceAdviceId)
						.setParameter("RemittanceAdviceLineId", remittanceAdviceLineId));
	}
}
