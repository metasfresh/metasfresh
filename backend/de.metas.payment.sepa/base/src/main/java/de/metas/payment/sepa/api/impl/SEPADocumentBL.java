package de.metas.payment.sepa.api.impl;

import de.metas.banking.api.BankAccountService;
import de.metas.banking.api.BankRepository;
import de.metas.common.util.time.SystemTime;
import de.metas.payment.sepa.api.ISEPADocumentBL;
import de.metas.payment.sepa.api.ISEPADocumentDAO;
import de.metas.payment.sepa.api.SEPACreditTransferXML;
import de.metas.payment.sepa.api.SEPAExportContext;
import de.metas.payment.sepa.api.SEPAProtocol;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.payment.sepa.sepamarshaller.impl.SEPACustomerDirectDebitMarshaler_Pain_008_003_02;
import de.metas.payment.sepa.sepamarshaller.impl.SEPAMarshaler;
import de.metas.payment.sepa.sepamarshaller.impl.SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02;
import de.metas.payment.sepa.sepamarshaller.impl.SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03;
import de.metas.util.FileUtil;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_PaySelection;
import org.compiere.util.MimeType;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class SEPADocumentBL implements ISEPADocumentBL
{
	@NonNull private final BankAccountService bankAccountService = SpringContextHolder.instance.getBean(BankAccountService.class);
	@NonNull private final ISEPADocumentDAO sepaDocumentDAO = Services.get(ISEPADocumentDAO.class);

	@Override
	public I_SEPA_Export createSEPAExportFromPaySelection(@NonNull final I_C_PaySelection from, final boolean isGroupTransactions, @Nullable final SEPAProtocol chosenCreditTransferProtocol)
	{
		return new CreateSEPAExportFromPaySelectionCommand(from, isGroupTransactions, chosenCreditTransferProtocol).run();
	}

	@Override
	public Date getDueDate(final I_SEPA_Export_Line line)
	{
		// NOTE: for now, return SystemTime + 1
		// return TimeUtil.addDays(line.getSEPA_Export().getPaymentDate(), 1);

		// ts: unrelated: don'T add another day, because it turn our that it makes the creditors get their money one day after they expected it
		final Timestamp paymentDate = line.getSEPA_Export().getPaymentDate();
		if (paymentDate == null)
		{
			return SystemTime.asTimestamp();
		}
		return paymentDate;
	}

	@Override
	public List<I_SEPA_Export_Line> retrieveLines(@NonNull final I_SEPA_Export sepaExport)
	{
		return sepaDocumentDAO.retrieveLines(sepaExport);
	}

	@Override
	public SEPACreditTransferXML exportCreditTransferXML(@NonNull final I_SEPA_Export sepaExport, @NonNull final SEPAExportContext exportContext)
	{
		final SEPAProtocol protocol = SEPAProtocol.ofCode(sepaExport.getSEPA_Protocol());

		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final SEPAMarshaler marshaler = newSEPAMarshaler(protocol, exportContext);
		try
		{
			marshaler.marshal(sepaExport, out);
		}
		catch (final RuntimeException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
		return SEPACreditTransferXML.builder()
				.filename(FileUtil.stripIllegalCharacters(sepaExport.getDocumentNo()) + ".xml")
				.contentType(MimeType.TYPE_XML)
				.content(out.toByteArray())
				.warnings(marshaler.getWarnings())
				.build();
	}

	private SEPAMarshaler newSEPAMarshaler(@NonNull final SEPAProtocol protocol, @NonNull final SEPAExportContext exportContext)
	{
		if (SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02.equals(protocol))
		{
			return new SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02(getBankRepository(), exportContext, bankAccountService);
		}
		else if (SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_09_CH_03.equals(protocol))
		{
			return new SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03(getBankRepository(), exportContext, bankAccountService);
		}
		else if (SEPAProtocol.DIRECT_DEBIT_PAIN_008_003_02.equals(protocol))
		{
			return new SEPACustomerDirectDebitMarshaler_Pain_008_003_02(bankAccountService);
		}
		else
		{
			throw new AdempiereException("Unknown SEPA protocol: " + protocol);
		}
	}

	/**
	 * Looked up on demand rather than held as a field, because only the credit transfer marshallers need it.
	 */
	private static BankRepository getBankRepository()
	{
		return SpringContextHolder.instance.getBean(BankRepository.class);
	}
}
