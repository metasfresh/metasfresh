package de.metas.payment.esr.api.impl;

/*
 * #%L
 * de.metas.payment.esr
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


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IOrgDAO;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.api.IESRLineMatcher;
import de.metas.payment.esr.api.IESRLineHandlersService;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.model.X_ESR_ImportLine;

public abstract class AbstractESRLineMatcher implements IESRLineMatcher
{
	protected final transient Logger logger = LogManager.getLogger(getClass());
	protected final IESRLineHandlersService esrMatchingListener = Services.get(IESRLineHandlersService.class);
	
	/**
	 * Method to remove the left zeros from a string.
	 * 
	 * @param value
	 * @return the initial String if it's made of only zeros; the string without the left zeros otherwise.
	 */
	protected final String removeLeftZeros(final String value)
	{
		final int size = value.length();
		int counter;
		for (counter = 0; counter < size; counter++)
		{
			if (value.charAt(counter) != '0')
			{
				break;
			}
		}
		if (counter == size)
		{
			return value;
		}
		else
		{
			return value.substring(counter, size);
		}
	}

	/**
	 * 
	 * @param dateStr date string in format yyMMdd
	 * @return
	 */
	protected final Timestamp extractTimestampFromString(final String dateStr) throws ParseException
	{
		final DateFormat df = new SimpleDateFormat("yyMMdd");
		final Date date = df.parse(dateStr);
		return TimeUtil.asTimestamp(date);
	}

	protected String replaceNonDigitCharsWithZero(String amountStringWithPosibleSpaces)
	{
		final int size = amountStringWithPosibleSpaces.length();

		StringBuilder stringWithZeros = new StringBuilder();

		for (int i = 0; i < size; i++)
		{
			final char currentChar = amountStringWithPosibleSpaces.charAt(i);

			if (!Character.isDigit(currentChar))
			{
				stringWithZeros.append('0');
			}
			else
			{
				stringWithZeros.append(currentChar);
			}
		}

		return stringWithZeros.toString();
	}

	protected void setValuesFromInvoice(final I_ESR_ImportLine importLine, final I_C_Invoice invoice)
	{
		importLine.setC_BPartner_ID(invoice.getC_BPartner_ID()); // 04582: no need to load the whole bpartner when we just need the ID
		importLine.setOrg_ID(invoice.getAD_Org_ID());

		InterfaceWrapperHelper.save(importLine);
	}

	protected void setValuesFromESRString(final I_ESR_ImportLine importLine)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(importLine);

		final String esrImportLineText = importLine.getESRLineText();
		// The reference number of the ESR Import line
		final String completeEsrReferenceNumberStr = esrImportLineText.substring(12, 39);

		// Organization value

		final String orgValue = completeEsrReferenceNumberStr.substring(7, 10);
		importLine.setSektionNo(orgValue);

		// Org ID
		final I_AD_Org organization = Services.get(IOrgDAO.class).retrieveOrganizationByValue(ctx, orgValue);
		if (organization != null)
		{
			importLine.setOrg(organization);
			importLine.setESR_Document_Status(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched);
		}

		// BPartner value (without initial zeros)
		final int bPartnerId;

		// removed "final" because the value of bpValue could be changed
		String bpValue = removeLeftZeros(completeEsrReferenceNumberStr.substring(10, 18));

		// Get BPartner id
		I_C_BPartner bPartner = Services.get(IBPartnerDAO.class).retrieveBPartnerByValue(ctx, bpValue);

		// task 05799: Fall back:
		// If the partner was not found using the value with all the left zeros removed
		// and the value does not have the maximum length we add a 0 at the beginning of the string and try again
		if (bPartner == null && bpValue.length() < 8)
		{
			final String bpValueleftZero = '0' + bpValue;
			final I_C_BPartner bPartnerLeftZero = Services.get(IBPartnerDAO.class).retrieveBPartnerByValue(ctx, bpValueleftZero);

			if (bPartnerLeftZero != null)
			{
				bpValue = bpValueleftZero;
				bPartner = bPartnerLeftZero;
			}
		}

		importLine.setBPartner_Value(bpValue);

		if (bPartner != null)
		{
			// check organization
			// we should not allow matching form other org
			boolean match = esrMatchingListener.applyESRMatchingBPartner(bPartner, importLine);
			if (match)
			{
				bPartnerId = bPartner.getC_BPartner_ID();
				importLine.setC_BPartner(bPartner);
				importLine.setESR_Document_Status(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched);
			}
			else
			{
				importLine.setC_BPartner(null);
				bPartnerId = -1;
			}
		}
		else
		{
			importLine.setC_BPartner(null);
			bPartnerId = -1;
		}

		// Document number (e.g. of the invoice, without initial zeros)

		final String documentNo = removeLeftZeros(completeEsrReferenceNumberStr.substring(18, 26));
		importLine.setESR_DocumentNo(documentNo);

		I_C_Invoice invoice = importLine.getC_Invoice();

		if (invoice != null)
		{
			final I_C_BPartner invoicePartner = invoice.getC_BPartner();
			final String invoiceDocumentNo = invoice.getDocumentNo();
			// final I_AD_Org invoiceOrg = InterfaceWrapperHelper.create(ctx, invoice.getAD_Org_ID(), I_AD_Org.class, trxName);

			boolean match = esrMatchingListener.applyESRMatchingBPartnerOfTheInvoice(invoice, importLine);
			if (match)
			{
				if (!invoicePartner.getValue().equals(bpValue))
				{
					// task: 05799 also try with bpValue + one '0'
					final String bpValueleftZero = '0' + bpValue;

					if (invoicePartner.getValue().endsWith(bpValueleftZero))
					{
						// if we have a match with bpValueleftZero, then we need to make sure that 'importLine' references invoice's partner
						// (the one with the leading '0')
						importLine.setBPartner_Value(bpValueleftZero);
						importLine.setC_BPartner(invoicePartner);
					}
					//task 09861
					//Make sure the bpartners with values bigger than 1000 are correctly handled.
					// For this, check if the invoice's bp value doesn't end with the string bpvalue as it is
					else if(invoicePartner.getValue().endsWith(bpValue))
					{
						importLine.setBPartner_Value(bpValue);
						importLine.setC_BPartner(invoicePartner);
					}
					else
					{
						Services.get(IESRImportBL.class).addErrorMsg(importLine,
								Services.get(IMsgBL.class).getMsg(ctx, ERR_UNFIT_BPARTNER_VALUES, new Object[] {
										invoicePartner.getValue(),
										bpValue
								}));
					}
				}

				if (!invoiceDocumentNo.equals(documentNo))
				{
					Services.get(IESRImportBL.class).addErrorMsg(importLine,
							Services.get(IMsgBL.class).getMsg(ctx, ERR_UNFIT_DOCUMENT_NOS, new Object[] {
									invoiceDocumentNo,
									documentNo
							}));
				}

			}

		}
		// if (importLine.getC_Invoice() == null)
		else
		{
			// Try to get the invoice via bpartner and document no
			final I_C_Invoice invoiceFallback = Services.get(IInvoiceDAO.class).retrieveInvoiceByInvoiceNoAndBPartnerID(ctx, documentNo, bPartnerId);
			if (invoiceFallback != null)
			{
				// check the org: should not match with invoices from other orgs
				if (invoiceFallback.getAD_Org_ID() != importLine.getAD_Org_ID())
				{
					Services.get(IESRImportBL.class).addErrorMsg(importLine,
							Services.get(IMsgBL.class).getMsg(ctx, ESR_UNFIT_INVOICE_ORG));
				}
				else
				{
					Services.get(IESRImportBL.class).setInvoice(importLine, invoiceFallback);
				}
			}
		}
		InterfaceWrapperHelper.save(importLine);
	}
}
