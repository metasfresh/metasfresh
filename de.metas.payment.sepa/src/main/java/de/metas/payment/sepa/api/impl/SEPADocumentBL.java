package de.metas.payment.sepa.api.impl;

/*
 * #%L
 * de.metas.payment.sepa
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.X_C_BPartner;
import org.compiere.util.Util;

import de.metas.adempiere.service.IBPartnerOrgBL;
import de.metas.i18n.IMsgBL;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.sepa.api.ISEPABankAccountBL;
import de.metas.payment.sepa.api.ISEPADocument;
import de.metas.payment.sepa.api.ISEPADocumentBL;
import de.metas.payment.sepa.api.ISEPADocumentDAO;
import de.metas.payment.sepa.interfaces.I_C_BP_BankAccount;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.payment.sepa.sepamarshaller.impl.SEPACustomerCTIMarshaler_Pain_001_001_03_CH_02;

public class SEPADocumentBL implements ISEPADocumentBL
{

	private static final String MSG_CANT_ACCESS_DEFAULT_PATH = "de.metas.payment.sepa.api.impl.SEPADocumentBL.CantAccessDefaultPath";
	private static final String MSG_FILE_NOT_SAVED = "de.metas.payment.sepa.api.impl.SEPADocumentBL.FileNotSaved";
	private static final String MSG_GOING_TO_CREATE_FILE = "de.metas.payment.sepa.api.impl.SEPADocumentBL.GoingToCreateFile";

	private static final String CFG_DEFAULT_PATH = "de.metas.payment.sepa.api.impl.SEPADocumentBL.marshalXMLCreditFile.defaultPath";

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

			if (!ignorePaymentRule && doc.getC_BPartner_ID() > 0 && !Services.get(ISEPADocumentBL.class).isSEPAEligible(ctx, doc.getC_BPartner_ID(), trxName))
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

		header.setIBAN(sepaBankAccount.getIBAN());
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
		if(paymentDate == null)
		{
			return SystemTime.asTimestamp();
		}
		return paymentDate;
	}

	@Override
	public boolean isSEPAEligible(final Properties ctx, final int bPartnerId, final String trxName)
	{
		final I_C_BPartner bPartner = InterfaceWrapperHelper.create(ctx, bPartnerId, I_C_BPartner.class, trxName);

		return X_C_BPartner.PAYMENTRULE_DirectDebit.equals(Services.get(IPaymentBL.class).getPaymentRuleForBPartner(bPartner));
	}

	@Override
	public void updateBparter(final I_SEPA_Export_Line line)
	{
		// Placeholder, no functionality needed yet.
	}

	@Override
	public void marshalXMLCreditFile(final String fileName, final I_SEPA_Export sepaExport, final ILoggable log)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(sepaExport);

		try
		{
			final FileOutputStream out = new FileOutputStream(fileName, false);
			try
			{
				// marshaler based on xsd from the https://validation.iso-payments.ch download section
				final SEPACustomerCTIMarshaler_Pain_001_001_03_CH_02 marshaler = new SEPACustomerCTIMarshaler_Pain_001_001_03_CH_02();
				marshaler.marshal(sepaExport, out);

				out.flush();
			}
			finally
			{
				Util.close(out);
			}
		}
		catch (final Exception e)
		{
			throw new AdempiereException(ExceptionUtils.getRootCauseMessage(e), e);
		}

		// task 08267: verify that the file actually exists!
		final File f = new File(fileName);
		if (!f.exists() || f.isDirectory())
		{
			final String msg = msgBL.getMsg(ctx, MSG_FILE_NOT_SAVED, new Object[] { fileName });
			/* @formatter:off */ if (log != null) { log.addLog(msg); } /* @formatter:on */
			throw new AdempiereException(msg);
		}
		// task 08267: be a bit more transparent about wtf we do
		/* @formatter:off */ if (log != null) {	log.addLog(msgBL.getMsg(ctx, MSG_GOING_TO_CREATE_FILE, new Object[] { fileName })); } /* @formatter:on */

	}

	@Override
	public String createDefaultSepaExportFileName(final Properties ctx, final String fileNamePrefix, final ILoggable log)
	{
		final String defaultPath = Services.get(ISysConfigBL.class).getValue(CFG_DEFAULT_PATH);
		Check.errorIf(Check.isEmpty(defaultPath, true), "Missing AD_Sysconfig record for {}" + CFG_DEFAULT_PATH);

		// make sure the directory exists and show a nice user-friendly message if not, so that the user can turn to the local admin (e.g. if a windows share is unavailable)
		final File f = new File(defaultPath);
		if (!f.exists() || !f.isDirectory())
		{
			final IMsgBL msgBL = Services.get(IMsgBL.class);
			final String msg = msgBL.getMsg(ctx, MSG_CANT_ACCESS_DEFAULT_PATH, new Object[] { CFG_DEFAULT_PATH, defaultPath });
			/* @formatter:off */ if (log != null) { log.addLog(msg); } /* @formatter:on */
			throw new AdempiereException(msg);
		}

		return defaultPath + File.separator + fileNamePrefix.replaceAll("\\W+", "") + ".xml"; // note: using File.separator because we also want to show the filename in user messages etc
	}
}
