package de.metas.payment.sepa.sepamarshaller.impl;

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

import java.util.Iterator;
import java.util.Properties;

import org.adempiere.util.Services;
import org.adempiere.util.collections.ConvertIteratorWrapper;
import org.adempiere.util.collections.Converter;

import de.metas.payment.sepa.api.ISEPADocument;
import de.metas.payment.sepa.api.ISEPADocumentSourceQuery;
import de.metas.payment.sepa.api.ISEPAExportLineSourceDAO;
import de.metas.payment.sepa.api.impl.SEPADocument;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.payment.sepa.sepamarshaller.ISEPADocumentSource;

public class SEPAExportLineDocumentSource implements ISEPADocumentSource
{

	@Override
	public Iterator<ISEPADocument> iterate(final Properties ctx, final ISEPADocumentSourceQuery query, final String trxName)
	{
		final Iterator<I_SEPA_Export_Line> candIterator = Services.get(ISEPAExportLineSourceDAO.class).retrieveRetrySEPAExportLines(ctx, query.getAD_Org_ID(), trxName);

		return new ConvertIteratorWrapper<ISEPADocument, I_SEPA_Export_Line>(candIterator, new Converter<ISEPADocument, I_SEPA_Export_Line>()
		{
			@Override
			public ISEPADocument convert(final I_SEPA_Export_Line value)
			{
				return createSEPADocument(value);
			}
		});
	}

	private ISEPADocument createSEPADocument(final I_SEPA_Export_Line value)
	{
		final ISEPADocument sepaDocument = new SEPADocument(
				value.getAD_Client_ID(),
				value.getAD_Org_ID(),
				value.getAmt(),
				value.getDescription(),
				value.getIBAN(),
				value.getSwiftCode(),
				I_SEPA_Export_Line.Table_Name,
				value.getSEPA_MandateRefNo(),
				value.getSEPA_Export_Line_ID(),
				value.getC_BPartner_ID());
		return sepaDocument;
	}

	@Override
	public String getSourceTableName()
	{
		return I_SEPA_Export_Line.Table_Name;
	}

}
