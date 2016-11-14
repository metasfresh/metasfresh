package de.metas.payment.sepa.process;

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


import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import de.metas.payment.sepa.api.ISEPADocumentBL;
import de.metas.payment.sepa.model.I_SEPA_Export;

/**
 * Export SEPA Customer Direct Debit XML (not used in vs82, but kept in case we extend the requirements).
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/Vs82_05680_SEPA_Marshaller_for_vs82_%28109872507874%29
 */
public class SEPA_Export_GenerateXML extends SvrProcess
{
	private int p_SEPA_Export_ID = -1;

	private final String PARAM_FileName = "FileName";
	private String p_Filename = null;

	private final ISEPADocumentBL sepaDocumentBL = Services.get(ISEPADocumentBL.class);

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			if (para.getParameter() == null)
			{
				continue;
			}
			else if (PARAM_FileName.equals(para.getParameterName()) && para.getParameter() != null)
			{
				p_Filename = para.getParameter().toString();
			}
		}
		if (I_SEPA_Export.Table_Name.equals(getTableName()))
		{
			this.p_SEPA_Export_ID = getRecord_ID();
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		if (p_SEPA_Export_ID <= 0)
		{
			throw new FillMandatoryException(I_SEPA_Export.COLUMNNAME_SEPA_Export_ID);
		}
		final I_SEPA_Export sepaDocument = InterfaceWrapperHelper.create(getCtx(), p_SEPA_Export_ID, I_SEPA_Export.class, get_TrxName());
		Check.assumeNotNull(sepaDocument, "sepaDocument not found");

		// 08267: allow the user to omit the file name from the dialog, and instead use a default
		if (Check.isEmpty(p_Filename, true))
		{
			p_Filename = sepaDocumentBL.createDefaultSepaExportFileName(getCtx(), sepaDocument.getDocumentNo(), this);
		}

		if (Check.isEmpty(p_Filename, true))
		{
			throw new FillMandatoryException(PARAM_FileName);
		}

		sepaDocumentBL.marshalXMLCreditFile(p_Filename, sepaDocument, this);

		return "OK";
	}
}
