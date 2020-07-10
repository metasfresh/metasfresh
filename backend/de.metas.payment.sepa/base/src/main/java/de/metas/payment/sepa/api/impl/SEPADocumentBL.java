package de.metas.payment.sepa.api.impl;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.Date;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_PaySelection;
import org.compiere.util.MimeType;

import de.metas.banking.api.BankRepository;
import de.metas.payment.sepa.api.ISEPADocumentBL;
import de.metas.payment.sepa.api.SEPACreditTransferXML;
import de.metas.payment.sepa.api.SEPAProtocol;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.payment.sepa.sepamarshaller.impl.SEPACustomerDirectDebitMarshaler_Pain_008_003_02;
import de.metas.payment.sepa.sepamarshaller.impl.SEPAMarshaler;
import de.metas.payment.sepa.sepamarshaller.impl.SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02;
import de.metas.util.FileUtil;
import de.metas.util.time.SystemTime;
import lombok.NonNull;

public class SEPADocumentBL implements ISEPADocumentBL
{
	@Override
	public I_SEPA_Export createSEPAExportFromPaySelection(final I_C_PaySelection from)
	{
		return new CreateSEPAExportFromPaySelectionCommand(from).run();
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
	public SEPACreditTransferXML exportCreditTransferXML(@NonNull final I_SEPA_Export sepaExport)
	{
		final SEPAProtocol protocol = SEPAProtocol.ofCode(sepaExport.getSEPA_Protocol());

		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final SEPAMarshaler marshaler = newSEPAMarshaler(protocol);
		marshaler.marshal(sepaExport, out);

		return SEPACreditTransferXML.builder()
				.filename(FileUtil.stripIllegalCharacters(sepaExport.getDocumentNo()) + ".xml")
				.contentType(MimeType.TYPE_XML)
				.content(out.toByteArray())
				.build();
	}

	private SEPAMarshaler newSEPAMarshaler(@NonNull final SEPAProtocol protocol)
	{
		if (SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02.equals(protocol))
		{
			final BankRepository bankRepository = SpringContextHolder.instance.getBean(BankRepository.class);
			return new SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02(bankRepository);
		}
		else if (SEPAProtocol.DIRECT_DEBIT_PAIN_008_003_02.equals(protocol))
		{
			return new SEPACustomerDirectDebitMarshaler_Pain_008_003_02();
		}
		else
		{
			throw new AdempiereException("Unknown SEPA protocol: " + protocol);
		}
	}
}
