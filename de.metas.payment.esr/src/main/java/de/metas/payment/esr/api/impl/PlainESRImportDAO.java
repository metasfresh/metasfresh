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


import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Env;

import de.metas.document.refid.api.IReferenceNoDAO;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportLine;

public class PlainESRImportDAO extends AbstractESRImportDAO
{
	private final POJOLookupMap db = POJOLookupMap.get();

	public POJOLookupMap getDB()
	{
		return db;
	}

	public void dumpStatus()
	{
		db.dumpStatus();
	}

	@Override
	public List<I_ESR_ImportLine> retrieveLinesForTrxTypes(final I_ESR_Import esrImport, final List<String> esrTrxTypes)
	{
		final List<I_ESR_ImportLine> result = db.getRecords(I_ESR_ImportLine.class, new IQueryFilter<I_ESR_ImportLine>()
		{
			@Override
			public boolean accept(I_ESR_ImportLine pojo)
			{
				if (pojo.getESR_Import_ID() != esrImport.getESR_Import_ID())
				{
					return false;
				}
				if (esrTrxTypes != null && !esrTrxTypes.isEmpty()
						&& !esrTrxTypes.contains(pojo.getESRTrxType()))
				{
					return false;
				}
				return true;
			}
		});

		Collections.sort(result, esrImportLineDefaultComparator);

		return result;
	}

	@Override
	List<I_ESR_ImportLine> fetchLinesForInvoice(final I_ESR_Import esrImport, final I_C_Invoice invoice)
	{
		final List<I_ESR_ImportLine> result = db.getRecords(I_ESR_ImportLine.class, new IQueryFilter<I_ESR_ImportLine>()
		{
			@Override
			public boolean accept(I_ESR_ImportLine pojo)
			{
				if (pojo.getESR_Import_ID() != esrImport.getESR_Import_ID())
				{
					return false;
				}
				if (pojo.getC_Invoice_ID() != invoice.getC_Invoice_ID())
				{
					return false;
				}
				return true;
			}
		});

		Collections.sort(result, esrImportLineDefaultComparator);

		return result;
	}

	@Override
	protected I_C_ReferenceNo fetchESRInvoiceReferenceNumber(final Properties ctx, final String esrReferenceNumber)
	{
		final IReferenceNoDAO refNoDAO = Services.get(IReferenceNoDAO.class);
		final I_C_ReferenceNo_Type refNoType = refNoDAO.retrieveRefNoTypeByName(ctx, ESRConstants.DOCUMENT_REFID_ReferenceNo_Type_InvoiceReferenceNumber);

		final Pattern esrReferenceNumberPattern = Pattern.compile("^[0-9]{6}([0-9]*)[0-9]{1}$");

		return db.getFirstOnly(I_C_ReferenceNo.class, new IQueryFilter<I_C_ReferenceNo>()
		{

			@Override
			public boolean accept(I_C_ReferenceNo pojo)
			{
				if (pojo.getC_ReferenceNo_Type_ID() != refNoType.getC_ReferenceNo_Type_ID())
				{
					return false;
				}

				if (pojo.getAD_Client_ID() != 0 && pojo.getAD_Client_ID() != Env.getAD_Client_ID(ctx))
				{
					return false;
				}

				if (pojo.getAD_Org_ID() != 0 && pojo.getAD_Org_ID() != Env.getAD_Org_ID(ctx))
				{
					return false;
				}

				if (!pojo.isActive())
				{
					return false;
				}

				final String referenceNo = pojo.getReferenceNo();
				final Matcher esrReferenceNumberMatcher = esrReferenceNumberPattern.matcher(referenceNo);
				if (!esrReferenceNumberMatcher.find())
				{
					return false;
				}

				// System.out.println(esrReferenceNumber + "     ->     " + esrReferenceNumberMatcher.group(1));
				// if (!esrReferenceNumber.equals(esrReferenceNumberMatcher.group(1)))
				// {
				// return false;
				// }

				return true;
			}
		});
	}

	@Override
	public List<I_ESR_ImportLine> retrieveLines(final I_ESR_Import esrImport)
	{
		return db.getRecords(I_ESR_ImportLine.class, new IQueryFilter<I_ESR_ImportLine>()
		{
			@Override
			public boolean accept(I_ESR_ImportLine pojo)
			{
				if (pojo.getESR_Import_ID() != esrImport.getESR_Import_ID())
				{
					return false;
				}
				return true;
			}
		});
	}

	@Override
	public void deleteLines(I_ESR_Import esrImport)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<I_ESR_Import> retrieveESRImports(Properties ctx, int orgID)
	{
		// TODO: Implement a plain method for this
		throw new UnsupportedOperationException();
	}

	@Override
	public int countLines(I_ESR_Import esrImport, final Boolean processed)
	{

		final List<I_ESR_ImportLine> result = db.getRecords(I_ESR_ImportLine.class, new IQueryFilter<I_ESR_ImportLine>()
		{
			@Override
			public boolean accept(I_ESR_ImportLine pojo)
			{
				if (processed == null)
				{
					return true;
				}
				else if (processed)
				{
					if (!pojo.isProcessed())
					{
						return false;
					}
					return true;
				}
				else
				{
					if (pojo.isProcessed())
					{
						return false;
					}
					return true;
				}
			}
		});

		return result.size();
	}

	@Override
	public I_ESR_ImportLine fetchLineForESRLineText(final I_ESR_Import import1, final String esrImportLineText)
	{

		final List<I_ESR_ImportLine> result = db.getRecords(I_ESR_ImportLine.class, new IQueryFilter<I_ESR_ImportLine>()
		{
			@Override
			public boolean accept(I_ESR_ImportLine pojo)
			{
				if (pojo.getESR_Import_ID() != import1.getESR_Import_ID())
				{
					return false;
				}
				if (pojo.getESRLineText() != esrImportLineText)
				{
					return false;
				}
				return true;
			}
		});

		return result.get(0);
	}
}
