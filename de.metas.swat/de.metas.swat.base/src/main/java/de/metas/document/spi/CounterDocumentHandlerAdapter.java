package de.metas.document.spi;

import org.adempiere.bpartner.service.IBPartnerAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.MDocTypeCounter;
import org.compiere.model.MOrg;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public abstract class CounterDocumentHandlerAdapter implements ICounterDocHandler
{

	private static final transient Logger logger = LogManager.getLogger(CounterDocumentHandlerAdapter.class);

	/**
	 * @return <code>true</code> if
	 *         <ul>
	 *         <li>the given document is not a counter document by itself
	 *         <li>the given document's <code>C_DocType</code> has <code>IsCreateCounter=Y</code>.
	 *         <li>the given document's BPartner is an org-BPartner
	 *         <li>there is a counter-doctype assigned to the given document's doc type and counter-org
	 *         </ul>
	 */
	@Override
	public boolean isCreateCounterDocument(IDocument document)
	{
		if (isCounterDocument(document))
		{
			return false;
		}

		// Document Type
		if (retrieveCounterDocTypeOrNull(document) == null)
		{
			return false;
		}

		if (retrieveCounterPartnerOrNull(document) == null)
		{
			return false;
		}

		final IBPartnerAware bpartnerAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(document, IBPartnerAware.class);
		if (bpartnerAware.getC_BPartner().getAD_OrgBP_ID() <= 0)
		{
			// document's BPartner is not linked to any (counter-)org
			return false;
		}

		return true;
	}

	protected final I_C_DocType retrieveCounterDocTypeOrNull(IDocument document)
	{
		final IDocumentBL docActionBL = Services.get(IDocumentBL.class);

		final I_C_DocType docType = docActionBL.getDocTypeOrNull(document);
		if (docType == null)
		{
			return null;
		}
		if (!docType.isCreateCounter())
		{
			return null;
		}

		int C_DocTypeTarget_ID = 0;
		MDocTypeCounter counterDT = MDocTypeCounter.getCounterDocType(document.getCtx(), docType.getC_DocType_ID());
		if (counterDT != null)
		{
			logger.debug(counterDT.toString());
			if (counterDT.isCreateCounter() && counterDT.isValid())
			{
				return counterDT.getCounter_C_DocType();
			}
		}
		else
		// indirect
		{
			C_DocTypeTarget_ID = MDocTypeCounter.getCounterDocType_ID(document.getCtx(), docType.getC_DocType_ID());
			logger.debug("Indirect C_DocTypeTarget_ID=" + C_DocTypeTarget_ID);
			if (C_DocTypeTarget_ID > 0)
			{
				return InterfaceWrapperHelper.create(document.getCtx(), C_DocTypeTarget_ID, I_C_DocType.class, document.get_TrxName());
			}
		}
		return null;
	}

	protected final I_C_BPartner retrieveCounterPartnerOrNull(IDocument document)
	{
		// our own org's bpartner would be the the counter document's C_BPartner.
		MOrg org = MOrg.get(document.getCtx(), document.getAD_Org_ID());
		int counterC_BPartner_ID = org.getLinkedC_BPartner_ID(document.get_TrxName());
		if (counterC_BPartner_ID > 0)
		{
			return InterfaceWrapperHelper.create(document.getCtx(), counterC_BPartner_ID, I_C_BPartner.class, document.get_TrxName());
		}
		return null;
	}

	protected final I_AD_Org retrieveCounterOrgOrNull(IDocument document)
	{
		final IBPartnerAware bpartnerAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(document, IBPartnerAware.class);
		if (bpartnerAware.getC_BPartner_ID() > 0 && bpartnerAware.getC_BPartner().getAD_OrgBP_ID() > 0)
		{
			return InterfaceWrapperHelper.create(document.getCtx(), bpartnerAware.getC_BPartner().getAD_OrgBP_ID(), I_AD_Org.class, document.get_TrxName());
		}

		// document's BPartner is not linked to any (counter-)org
		return null;
	}

}
