package de.metas.inoutcandidate.process;

/*
 * #%L
 * de.metas.swat.base
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

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.ProcessInfoSelectionQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_InOut;

import de.metas.inoutcandidate.api.IInOutCandidateBL;
import de.metas.inoutcandidate.api.IInOutProducer;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * Takes {@link I_M_ReceiptSchedule}s from user's window selection and produces {@link I_M_InOut} receipts.
 * 
 * @author lc
 * 
 */
public class M_ReceiptSchedule_GenerateInOutFromSelection extends JavaProcess
{

	public static final String MSG_NO_RECEIPT_SCHEDULES_SELECTED = "ReceiptScheduleNotSelected";

	private IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);

	private final String PARAM_IsComplete = "IsComplete";
	private boolean p_IsComplete = true;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			if (PARAM_IsComplete.equals(para.getParameterName()))
			{
				p_IsComplete = para.getParameterAsBoolean();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		final Iterator<I_M_ReceiptSchedule> schedules = getReceiptSchedules();

		if (!schedules.hasNext())
		{
			throw new AdempiereException("@" + MSG_NO_RECEIPT_SCHEDULES_SELECTED + "@");
		}

		final InOutGenerateResult result = Services.get(IInOutCandidateBL.class).createEmptyInOutGenerateResult(false); // storeReceipts=false
		final IInOutProducer producer = receiptScheduleBL.createInOutProducer(result, p_IsComplete);

		receiptScheduleBL.generateInOuts(getCtx(), producer, schedules);

		return "@Created@ @M_InOut_ID@: #" + result.getInOutCount();
	}

	private Iterator<I_M_ReceiptSchedule> getReceiptSchedules()
	{
		final ProcessInfo processInfo = getProcessInfo();

		final IQueryBuilder<I_M_ReceiptSchedule> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_ReceiptSchedule.class, processInfo);
		//
		// Only not processed lines
		queryBuilder.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_Processed, false);

		//
		// From user selection
		queryBuilder.filter(new ProcessInfoSelectionQueryFilter<I_M_ReceiptSchedule>(processInfo));

		final IQuery<I_M_ReceiptSchedule> query = queryBuilder.create()
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setApplyAccessFilterRW(true);

		return Services.get(IReceiptScheduleDAO.class).retrieve(query);
	}
}
