/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.impexp;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_ImpFormat;
import org.compiere.model.I_AD_ImpFormat_Row;

import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Copy Import Format (lines)
 * 
 * @author Jorg Janke
 * @version $Id: CopyImportFormat.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class CopyImportFormat extends JavaProcess
{
	private ImpFormatId from_AD_ImpFormat_ID;
	private ImpFormatId to_AD_ImpFormat_ID;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParameters())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
			{

			}
			else if ("AD_ImpFormat_ID".equals(name))
			{
				from_AD_ImpFormat_ID = ImpFormatId.ofRepoId(para.getParameterAsInt());
			}
			else
			{
				log.error("prepare - Unknown Parameter: " + name);
			}
		}
		to_AD_ImpFormat_ID = ImpFormatId.ofRepoId(getRecord_ID());
	}	// prepare

	@Override
	protected String doIt()
	{
		final I_AD_ImpFormat from = retrieveImpFormatRecord(from_AD_ImpFormat_ID);
		final I_AD_ImpFormat target = retrieveImpFormatRecord(to_AD_ImpFormat_ID);
		if (from.getAD_Table_ID() != target.getAD_Table_ID())
		{
			throw new AdempiereException("From-To do Not have same Format Table");
		}

		final List<I_AD_ImpFormat_Row> fromRows = retrieveRows(from_AD_ImpFormat_ID);
		for (final I_AD_ImpFormat_Row row : fromRows)
		{
			copyRow(row, target);
		}

		String msg = "#" + fromRows.size();
		if (!from.getFormatType().equals(target.getFormatType()))
		{
			return msg + " - Note: Format Type different!";
		}
		return msg;
	}	// doIt

	private I_AD_ImpFormat retrieveImpFormatRecord(@NonNull final ImpFormatId id)
	{
		final I_AD_ImpFormat from = load(id, I_AD_ImpFormat.class);
		Check.assumeNotNull(from, "Parameter from is not null");
		return from;
	}

	private List<I_AD_ImpFormat_Row> retrieveRows(@NonNull final ImpFormatId impFormatId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_ImpFormat_Row.class)
				.addEqualsFilter(I_AD_ImpFormat_Row.COLUMN_AD_ImpFormat_ID, impFormatId)
				// .addOnlyActiveRecordsFilter() // ALL!
				.orderBy(I_AD_ImpFormat_Row.COLUMN_SeqNo)
				.create()
				.list();
	}

	private void copyRow(I_AD_ImpFormat_Row fromRow, I_AD_ImpFormat target)
	{
		final I_AD_ImpFormat_Row toRow = newInstance(I_AD_ImpFormat_Row.class);
		InterfaceWrapperHelper.copyValues(fromRow, toRow);
		toRow.setAD_Org_ID(fromRow.getAD_Org_ID()); // usually "*"
		toRow.setAD_ImpFormat_ID(target.getAD_ImpFormat_ID());
		saveRecord(toRow);
	}

}	// CopyImportFormat
