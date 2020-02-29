package de.metas.payment.sepa.api.impl;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.apache.commons.lang3.StringUtils;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_PaySelection;
import org.compiere.util.MimeType;

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.payment.PaymentRule;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.sepa.api.ISEPABankAccountBL;
import de.metas.payment.sepa.api.ISEPADocument;
import de.metas.payment.sepa.api.ISEPADocumentBL;
import de.metas.payment.sepa.api.ISEPADocumentDAO;
import de.metas.payment.sepa.api.SEPACreditTransferXML;
import de.metas.payment.sepa.interfaces.I_C_BP_BankAccount;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.payment.sepa.sepamarshaller.impl.SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02;
import de.metas.util.Check;
import de.metas.util.FileUtils;
import de.metas.util.Services;
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
	public List<I_SEPA_Export> createSEPAExports(final Properties ctx, final Iterator<ISEPADocument> iterator, final String trxName, final boolean ignorePaymentRule)
	{
		if (!iterator.hasNext())
		{
			// No payment lines. Nothing to do.
			return null;
		}

		final List<I_SEPA_Export> exports = new ArrayList<>();

		while (iterator.hasNext())
		{
			final ISEPADocument doc = iterator.next();
			final I_SEPA_Export_Line line = InterfaceWrapperHelper.create(ctx, I_SEPA_Export_Line.class, trxName);
			if (doc == null)
			{
				// TODO: What do we do about this?
				continue;
			}

			if (!ignorePaymentRule && doc.getC_BPartner_ID() > 0 && !isSEPAEligible(doc.getC_BPartner_ID()))
			{
				// We ignore documents from BPartners that aren't eligible for SEPA direct debit.
				continue;
			}
			final I_SEPA_Export export = getCreateHeader(ctx, doc.getAD_Org_ID(), exports, trxName);

			line.setErrorMsg("");
			line.setAD_Org_ID(doc.getAD_Org_ID());
			line.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(doc.getTableName()));
			line.setRecord_ID(doc.getRecordId());
			line.setAmt(doc.getAmt());
			line.setDescription(doc.getDescription());
			line.setIBAN(doc.getIBAN());
			line.setSEPA_MandateRefNo(doc.getMandateRefNo());
			line.setSwiftCode(doc.getSwiftCode());
			line.setC_BPartner_ID(doc.getC_BPartner_ID());
			line.setSEPA_Export(export);

			// TODO: Change to AD_Message.
			if (null == doc.getAmt())
			{
				line.setIsError(true);
				line.setErrorMsg("No ammount.");
			}

			if (null == doc.getIBAN())
			{
				line.setIsError(true);
				line.setErrorMsg(line.getErrorMsg() + " No IBAN.");
			}

			if (null == doc.getSwiftCode())
			{
				line.setIsError(true);
				line.setErrorMsg(line.getErrorMsg() + " No BIC.");
			}

			if (null == doc.getMandateRefNo())
			{
				line.setIsError(true);
				line.setErrorMsg(line.getErrorMsg() + " No SEPA creditor identifier.");
			}

			InterfaceWrapperHelper.save(line);

			// vs82 05761: Treat the case of retrying failed SEPA export lines.
			if (I_SEPA_Export_Line.Table_Name.equals(doc.getTableName()))
			{
				final I_SEPA_Export_Line originalLine = InterfaceWrapperHelper.create(ctx, doc.getRecordId(), I_SEPA_Export_Line.class, trxName);
				originalLine.setSEPA_Export_Line_Retry_ID(line.getSEPA_Export_Line_ID());
				InterfaceWrapperHelper.save(originalLine);
			}
		}
		return exports;
	}

	private I_SEPA_Export getCreateHeader(final Properties ctx, final int adOrgId, final List<I_SEPA_Export> exports, final String trxName)
	{
		for (final I_SEPA_Export export : exports)
		{
			if (export.getAD_Org_ID() == adOrgId)
			{
				return export;
			}
		}
		final I_AD_Org org = InterfaceWrapperHelper.create(ctx, adOrgId, I_AD_Org.class, trxName);
		final I_SEPA_Export header = InterfaceWrapperHelper.create(ctx, I_SEPA_Export.class, trxName);
		final I_C_BPartner orgBP = Services.get(IBPartnerOrgBL.class).retrieveLinkedBPartner(org);
		final I_C_BP_BankAccount sepaBankAccount = Services.get(ISEPADocumentDAO.class).retrieveSEPABankAccount(orgBP);
		Check.assumeNotNull(sepaBankAccount, "No bank account for BPartner {}", orgBP);

		final String swiftCode = Services.get(ISEPABankAccountBL.class).getSwiftCode(sepaBankAccount);

		final String IBAN = sepaBankAccount.getIBAN();
		header.setIBAN(StringUtils.deleteWhitespace(IBAN));
		header.setSwiftCode(swiftCode);
		header.setAD_Org_ID(adOrgId);
		header.setDescription(null); // TODO: Add description.
		header.setProcessed(false);
		header.setPaymentDate(SystemTime.asDayTimestamp()); // TODO: Is this correct?
		header.setSEPA_CreditorIdentifier(sepaBankAccount.getSEPA_CreditorIdentifier());

		InterfaceWrapperHelper.save(header);
		exports.add(header);
		return header;
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

	private boolean isSEPAEligible(final int bpartnerId)
	{
		final I_C_BPartner bpartner = Services.get(IBPartnerDAO.class).getById(bpartnerId);
		final PaymentRule paymentRule = Services.get(IPaymentBL.class).getPaymentRuleForBPartner(bpartner);
		return paymentRule.isDirectDebit();
	}

	@Override
	public void updateBparter(final I_SEPA_Export_Line line)
	{
		// Placeholder, no functionality needed yet.
	}

	@Override
	public SEPACreditTransferXML exportCreditTransferXML(@NonNull final I_SEPA_Export sepaExport)
	{
		final ByteArrayOutputStream out = new ByteArrayOutputStream();

		final SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02 marshaler = new SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02();
		marshaler.marshal(sepaExport, out);

		return SEPACreditTransferXML.builder()
				.filename(FileUtils.stripIllegalCharacters(sepaExport.getDocumentNo()) + ".xml")
				.contentType(MimeType.TYPE_XML)
				.content(out.toByteArray())
				.build();
	}
}
