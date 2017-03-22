package de.metas.commission.process;

/*
 * #%L
 * de.metas.commission.base
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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_Period;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MPeriod;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.eevolution.model.I_HR_Period;
import org.eevolution.model.I_HR_Process;
import org.eevolution.model.MHRMovement;
import org.eevolution.model.MHRPeriod;
import org.eevolution.model.MHRProcess;
import org.eevolution.model.X_HR_Process;

import de.metas.commission.exception.CommissionException;
import de.metas.commission.interfaces.I_C_InvoiceLine;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_AdvCommissionPayroll;
import de.metas.commission.service.ICommissionConditionDAO;
import de.metas.logging.MetasfreshLastError;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * 
 * @author ts
 * @see "<a href="http://dewiki908/mediawiki/index.php/US691:_Provisionsberechnung_08-2010_%282010090110000033%29 ">US691: Provisionsberechnung 08-2010 (2010090110000033)</a>"
 */
public class InvoiceCommission extends JavaProcess
{

	private static final String MSG_MISSING_HR_PERIOD_1P = "Missing_HR_Period_1P";

	private static final String MSG_HR_PROCESSING_ERROR_1P = "HR_Process_Error_1P";

	private I_C_Period period;

	private Timestamp dateAcct;

	@Override
	protected String doIt() throws Exception
	{
		int invoiceCount = 0;
		int processCount = 0;

		final MHRPeriod hrPeriod = getHRPeriod(period);
		if (hrPeriod == null)
		{
			throw new CommissionException(InvoiceCommission.MSG_MISSING_HR_PERIOD_1P, new Object[] { period.getName() });
		}

		//
		// iterate commission contracts and find all combinations of HR_Payroll_ID and C_Doctype_Payroll_ID
		final Map<Integer, Set<Integer>> payrollId2DocTypeId = new HashMap<Integer, Set<Integer>>();

		for (final I_C_AdvCommissionCondition cond : Services.get(ICommissionConditionDAO.class).retrieve(getCtx(), Env.getAD_Org_ID(getCtx()), get_TrxName()))
		{
			final int payrollId = cond.getHR_Payroll_ID();
			final int docTypeId = cond.getC_Doctype_Payroll_ID();

			Set<Integer> docTypeIds = payrollId2DocTypeId.get(payrollId);
			if (docTypeIds == null)
			{
				docTypeIds = new HashSet<Integer>();
				payrollId2DocTypeId.put(payrollId, docTypeIds);
			}
			docTypeIds.add(docTypeId);
		}

		//
		// create and process a payroll process for every combination of HR_Payroll_ID and C_Doctype_Payroll_ID
		for (final int payrollId : payrollId2DocTypeId.keySet())
		{
			for (final int docTypeId : payrollId2DocTypeId.get(payrollId))
			{
				final MHRProcess hrProcess = new MHRProcess(getCtx(), 0, get_TrxName());
				hrProcess.setDateAcct(dateAcct);
				hrProcess.setHR_Period_ID(hrPeriod.get_ID());
				hrProcess.setHR_Payroll_ID(payrollId);
				hrProcess.setC_DocTypeTarget_ID(docTypeId);

				final String name = mkHrProcessName(docTypeId);

				hrProcess.setName(name);
				hrProcess.saveEx();
				processCount++;
				if (hrProcess.processIt(X_HR_Process.DOCACTION_Complete))
				{
					hrProcess.saveEx();
					final String msgProcess = "@Created@: @" + I_HR_Process.COLUMNNAME_HR_Process_ID + "@ " + hrProcess.getDocumentNo();
					addLog(0, SystemTime.asTimestamp(), new BigDecimal(invoiceCount + processCount), msgProcess);

					for (final MInvoice invoice : retrieveInvoices(hrProcess))
					{
						invoiceCount++;

						final String msgInvoice = "@Created@: @" + org.compiere.model.I_C_Invoice.COLUMNNAME_C_Invoice_ID + "@ " + invoice.getDocumentNo();
						addLog(0, SystemTime.asTimestamp(), new BigDecimal(invoiceCount + processCount), msgInvoice);
					}
				}
				else
				{
					final String msgError = Msg.getMsg(getCtx(), InvoiceCommission.MSG_HR_PROCESSING_ERROR_1P, new Object[] { hrProcess.getDocumentNo() }) + " " + MetasfreshLastError.retrieveError();
					addLog(0, SystemTime.asTimestamp(), new BigDecimal(invoiceCount + processCount), msgError);
				}
			}
		}
		final String msgReturn = "@Created@ " + (processCount + invoiceCount);
		return msgReturn;
	}

	private String mkHrProcessName(final int docTypeId)
	{
		final DateFormat fmt = DateFormat.getDateInstance(DateFormat.MEDIUM, Env.getLanguage(getCtx()).getLocale());
		final String docTypeName = MDocType.get(getCtx(), docTypeId).getName();

		final String name = period.getName() + " [" + docTypeName + ", " + fmt.format(dateAcct) + ", " + getProcessInfo().getAD_PInstance_ID() + "]";
		return name;
	}

	@Override
	protected void prepare()
	{
		final ProcessInfoParameter[] para = getParametersAsArray();

		for (int i = 0; i < para.length; i++)
		{
			final String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
			{
				;
			}
			else if (name.equals(I_C_AdvCommissionPayroll.COLUMNNAME_C_Period_ID))
			{

				final int periodId = ((BigDecimal)para[i].getParameter())
						.intValue();
				period = MPeriod.get(getCtx(), periodId);

			}
			else if (name.equals(I_C_Period.COLUMNNAME_C_Year_ID))
			{
				// nothing to do
			}
			else if (name.equals("DateAcct"))
			{
				dateAcct = (Timestamp)para[i].getParameter();
			}
			else
			{
				log.error("Unknown Parameter: " + name);
			}
		}

		if (dateAcct == null)
		{
			dateAcct = period.getEndDate();
		}

	}

	private MHRPeriod getHRPeriod(final I_C_Period period)
	{
		final String wc = I_HR_Period.COLUMNNAME_C_Period_ID + "=?";

		final MHRPeriod hrPeriod = new Query(getCtx(), I_HR_Period.Table_Name, wc, get_TrxName()).setParameters(period.getC_Period_ID()).setOnlyActiveRecords(true).firstOnly();

		return hrPeriod;
	}

	private List<MInvoice> retrieveInvoices(final MHRProcess hrProcess)
	{
		final String wc = org.eevolution.model.I_HR_Movement.COLUMNNAME_HR_Process_ID + "=?";

		final Collection<MHRMovement> movements =
				new Query(getCtx(), org.eevolution.model.I_HR_Movement.Table_Name, wc, get_TrxName())
						.setParameters(hrProcess.get_ID())
						.setOnlyActiveRecords(true)
						.setClient_ID()
						.list();

		final Set<Integer> invoiceIds = new HashSet<Integer>();

		for (final MHRMovement movement : movements)
		{
			final Collection<MInvoiceLine> ils =
					new Query(getCtx(), org.compiere.model.I_C_InvoiceLine.Table_Name, I_C_InvoiceLine.COLUMNNAME_HR_Movement_ID + "=?", get_TrxName())
							.setParameters(movement.get_ID())
							.setOnlyActiveRecords(true)
							.setClient_ID()
							.list();

			for (final MInvoiceLine il : ils)
			{
				invoiceIds.add(il.getC_Invoice_ID());
			}
		}

		final List<MInvoice> invoices = new ArrayList<MInvoice>(invoiceIds.size());
		for (final int invoiceId : invoiceIds)
		{
			// can't use MInvoice.get() because currently the invoice to load only exists in our trx
			invoices.add(new MInvoice(getCtx(), invoiceId, get_TrxName()));
		}

		final Comparator<MInvoice> cmp = new Comparator<MInvoice>()
		{
			@Override
			public int compare(final MInvoice i1, final MInvoice i2)
			{
				return i1.getDocumentNo().compareTo(i2.getDocumentNo());
			}
		};
		Collections.sort(invoices, cmp);

		return invoices;
	}
}
