package de.metas.banking.payment;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.MPaySelection;
import org.compiere.model.MPaySelectionCheck;
import org.compiere.model.MPaySelectionLine;
import org.compiere.util.Env;

import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.document.engine.IDocument;
import de.metas.i18n.IMsgBL;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class PaySelectionDocumentHandler implements DocumentHandler
{

	private static I_C_PaySelection extractPaySelection(final DocumentTableFields docFields)
	{
		return InterfaceWrapperHelper.create(docFields, I_C_PaySelection.class);
	}

	@Override
	public String getSummary(DocumentTableFields docFields)
	{
		return extractPaySelection(docFields).getName();
	}

	@Override
	public String getDocumentInfo(DocumentTableFields docFields)
	{
		return getSummary(docFields);
	}

	@Override
	public int getDoc_User_ID(DocumentTableFields docFields)
	{
		return extractPaySelection(docFields).getCreatedBy();
	}

	@Override
	public int getC_Currency_ID(DocumentTableFields docFields)
	{
		return -1; // no default currency for pay selections
	}

	@Override
	public BigDecimal getApprovalAmt(DocumentTableFields docFields)
	{
		return BigDecimal.ZERO;
	}

	@Override
	public File createPDF(DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String prepareIt(DocumentTableFields docFields)
	{
		return IDocument.STATUS_InProgress;
	}

	@Override
	public String completeIt(DocumentTableFields docFields)
	{
		final I_C_PaySelection paySelection = extractPaySelection(docFields);

		final MPaySelection mPaySelection = new MPaySelection(Env.getCtx(), paySelection.getC_PaySelection_ID(), ITrx.TRXNAME_None);

		final MPaySelectionLine[] lines = mPaySelection.getLines(true);

		PaySelectionCheckProducer.newInstance()
				.createChecksAndUpdateLines(lines);

		paySelection.setProcessed(true);
		paySelection.setDocAction(IDocument.ACTION_ReActivate);
		return IDocument.STATUS_Completed;
	}

	@Override
	public void approveIt(DocumentTableFields docFields)
	{
		// nothing to do

	}

	@Override
	public void rejectIt(DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException("The action RejectIt is not implemented for PaySelection documents");

	}

	@Override
	public void voidIt(DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException("The action VoidIt is not implemented for PaySelection documents");
	}

	@Override
	public void closeIt(DocumentTableFields docFields)
	{

		final I_C_PaySelection paySelection = extractPaySelection(docFields);

		paySelection.setProcessed(true);
		paySelection.setDocAction(IDocument.ACTION_None);

	}

	@Override
	public void unCloseIt(DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException("The action UnCloseIt is not implemented for PaySelection documents");

	}

	@Override
	public void reverseCorrectIt(DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException("The action ReverseCorrectIt is not implemented for PaySelection documents");

	}

	@Override
	public void reverseAccrualIt(DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException("The action ReverseAccrual It is not implemented for PaySelection documents");

	}

	@Override
	public void reactivateIt(DocumentTableFields docFields)
	{
		final I_C_PaySelection paySelection = extractPaySelection(docFields);
		paySelection.setProcessed(false);
		paySelection.setDocAction(IDocument.ACTION_Complete);

	}

	private static final class PaySelectionCheckProducer
	{
		public static PaySelectionCheckProducer newInstance()
		{
			return new PaySelectionCheckProducer();
		}

		private ArrayList<MPaySelectionCheck> paySelectionChecks = new ArrayList<MPaySelectionCheck>();

		private PaySelectionCheckProducer()
		{
		}

		public void createChecksAndUpdateLines(final MPaySelectionLine[] lines)
		{
			for (final MPaySelectionLine line : lines)
			{
				if (!line.isActive() || line.isProcessed())
				{
					continue;
				}
				createCheckAndUpdateLine(line);
			}
		}

		private void createCheckAndUpdateLine(final MPaySelectionLine line)
		{
			//
			// Try to find one
			for (final MPaySelectionCheck check : paySelectionChecks)
			{
				// Add to existing
				if (check.getC_BPartner_ID() == line.getInvoice().getC_BPartner_ID())
				{
					check.addLine(line);
					InterfaceWrapperHelper.save(check);

					linkCheckAndMarkProcessed(line, check);
					return;
				}
			}

			//
			// Create new
			final String paymentRule = line.getPaymentRule();
			final MPaySelectionCheck check = new MPaySelectionCheck(line, paymentRule);
			if (!check.isValid())
			{
				String msg = Services.get(IMsgBL.class).getMsg(Env.getCtx(), MPaySelection.ERR_PaySelection_NoBPBankAccount);
				throw new AdempiereException(msg);
			}
			InterfaceWrapperHelper.save(check);

			linkCheckAndMarkProcessed(line, check);

			paySelectionChecks.add(check);
		}

		private void linkCheckAndMarkProcessed(final MPaySelectionLine line, final MPaySelectionCheck check)
		{
			line.setC_PaySelectionCheck_ID(check.getC_PaySelectionCheck_ID());
			line.setProcessed(true);
			InterfaceWrapperHelper.save(line);
		}

	}

}
