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
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxSavepoint;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.processing.exception.ProcessingException;
import org.adempiere.processing.service.IProcessingService;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.PO;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;
import org.slf4j.Logger;

import de.metas.allocation.api.IAllocationDAO;
import de.metas.commission.interfaces.I_C_Invoice;
import de.metas.commission.interfaces.I_C_Order;
import de.metas.commission.model.MCAdvComDoc;
import de.metas.commission.model.MCAdvComRelevantPOType;
import de.metas.commission.model.MCAdvCommissionFactCand;
import de.metas.commission.model.MCAdvCommissionRelevantPO;
import de.metas.commission.service.IComRelevantPoBL;
import de.metas.commission.service.ICommissionFactCandBL;
import de.metas.commission.service.ICommissionFactCandDAO;
import de.metas.logging.LogManager;
import de.metas.prepayorder.service.IPrepayOrderBL;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Provisionsberechnung_%282009_0023_G106%29'>(2009 0023 G106)</a>"
 */
public class WriteCommissionAccount extends SvrProcess
{
	private static final String ALSO_HANDLE_PROCESS_IMMEDIATELY = "AlsoHandleTypesWithProcessNow";

	private static final Logger logger = LogManager.getLogger(WriteCommissionAccount.class);

	private boolean alsoHandleProcessNow = false;

	@Override
	protected String doIt() throws Exception
	{
		final int oldClientId = Env.getAD_Client_ID(getCtx());
		try
		{
			Env.setContext(getCtx(), "#AD_Client_ID", getProcessInfo().getAD_Client_ID());

			final long startTime = SystemTime.millis();
			int counter = 0;

			MCAdvCommissionFactCand candidate = Services.get(ICommissionFactCandDAO.class).retrieveNext(getCtx(), get_TrxName());

			final ITrx trx = Trx.get(get_TrxName(), false);

			// savePoint is used to commit or revert all changes made for one candidate by the different commission
			// types.
			ITrxSavepoint savePoint;

			while (null != candidate)
			{
				// create a savePoint so we can go back if the evaluation of candidate with one types fails
				savePoint = trx.createTrxSavepoint("savePoint");

				final boolean evaluationOK = processCandidate(candidate, savePoint, trx);

				if (evaluationOK)
				{
					WriteCommissionAccount.logger.info("Sucessfully evaluated " + candidate);
					if (!candidate.isImmediateProcessingDone())
					{
						candidate.setIsImmediateProcessingDone(true);
					}
					candidate.setIsSubsequentProcessingDone(true);
					candidate.saveEx();

					trx.releaseSavepoint(savePoint);
					trx.commit(true);
				}
				else
				{
					WriteCommissionAccount.logger.info("Error evaluating " + candidate);
				}

				// get the next candidate from the queue
				candidate = Services.get(ICommissionFactCandDAO.class).retrieveNext(getCtx(), get_TrxName());
				counter++;
			}

			final String successMsg = "Processed " + counter + " candidates in " + TimeUtil.formatElapsed(System.currentTimeMillis() - startTime);
			addLog(successMsg);
			return "@Success@ - " + successMsg;
		}
		finally
		{
			Env.setContext(getCtx(), "#AD_Client_ID", oldClientId);
		}
	}

	/**
	 * 
	 * @param candidate
	 * @param savePoint if processing fails (i.e. if a Throwable is caught during processing), then the method will rollback to this savepoint
	 * @param trx
	 * @return
	 * @throws SQLException thrown only if trxRollback fails
	 */
	private boolean processCandidate(
			final MCAdvCommissionFactCand candidate,
			final ITrxSavepoint savePoint,
			final ITrx trx) throws SQLException
	{
		boolean evaluationOK = true;

		final IComRelevantPoBL relevantPoBL = Services.get(IComRelevantPoBL.class);

		try
		{
			relevantPoBL.recordIncident(candidate);

			if (!candidate.isInfo())
			{
				// get the 'relevantPO' definition that caused the po (e.g. order) to be recorded as a/
				// candidate.
				final MCAdvCommissionRelevantPO relevantPO = (MCAdvCommissionRelevantPO)candidate.getC_AdvCommissionRelevantPO();

				// get the different relPOTypes. Each one links the candidate to one commission type
				// Note: relPOTypes with isProcessImmediately()==true are returned first
				final List<MCAdvComRelevantPOType> relPOTypes = MCAdvComRelevantPOType.retrieveFor(relevantPO);

				for (final MCAdvComRelevantPOType relPOType : relPOTypes)
				{
					if (!evaluationOK)
					{
						// the evaluation of the last relPOType (i.e. the current relPOType's predecessor)
						// failed.
						break;
					}

					if (!relPOType.isProcessImmediately() /*
														 * the current relPOType is handled with subsequent processing
														 */
							|| candidate.isAlsoHandleTypesWithProcessNow() /*
																			 * the candidate wants us to handle types with immediate processing
																			 */
							|| alsoHandleProcessNow /*
													 * the process caller wants us to handle types with immediate processing
													 */
					)
					{
						if (relPOType.isProcessImmediately() && candidate.isImmediateProcessingDone())
						{
							// the current relPOType doesn't need to be invoked
							continue;
						}
						candidate.setCurrentRelPOType(relPOType); // setting the type only for the time of processing
						relevantPoBL.invokeCommissionType(candidate, false, getProcessInfo().getAD_PInstance_ID());
						candidate.setCurrentRelPOType(null);
					}
				}
			}

			createOrInactivateComDocRecords(candidate);
		}
		catch (final Throwable t)
		{
			final ProcessingException pe;
			if (t instanceof ProcessingException)
			{
				pe = (ProcessingException)t;
			}
			else
			{
				pe = new ProcessingException("Caught " + t + " while processing " + candidate, t, getProcessInfo().getAD_PInstance_ID());
			}

			// undo the evaluation for the current candidate
			trx.rollback(savePoint);

			// create an issue and store the error status
			Services.get(IProcessingService.class).handleProcessingException(
					candidate.getCtx(),
					candidate,
					pe,
					candidate.getTrxName());

			addLog(candidate.get_ID(), SystemTime.asTimestamp(), null, pe.getMessage());
			evaluationOK = false;
		}
		return evaluationOK;
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
			else if (name.equals(WriteCommissionAccount.ALSO_HANDLE_PROCESS_IMMEDIATELY))
			{
				alsoHandleProcessNow = ((String)para[i].getParameter()).equals("Y");
			}
		}
	}

	private void createOrInactivateComDocRecords(final MCAdvCommissionFactCand cand)
	{
		final PO po = Services.get(ICommissionFactCandBL.class).retrievePO(cand);

		if (InterfaceWrapperHelper.isInstanceOf(po, I_C_AllocationHdr.class))
		{
			final I_C_AllocationHdr allocHdr = InterfaceWrapperHelper.create(po, I_C_AllocationHdr.class);
			;
			for (final I_C_AllocationLine allocLine : Services.get(IAllocationDAO.class).retrieveLines(allocHdr))
			{
				createOrUpdateComDocIfRequired(allocLine);
			}
		}
	}

	static void createOrUpdateComDocIfRequired(final I_C_AllocationLine allocLine)
	{
		final boolean isPrepayOrder;

		if (allocLine.getC_Order_ID() > 0)
		{
			final I_C_Order order = InterfaceWrapperHelper.create(allocLine.getC_Order(), I_C_Order.class);

			final IPrepayOrderBL prepayOrderBL = Services.get(IPrepayOrderBL.class);
			isPrepayOrder = prepayOrderBL.isPrepayOrder(order);

			if (isPrepayOrder && isPaidOrder(order))
			{
				final MCAdvComDoc comDoc = MCAdvComDoc.createOrUpdate(order, allocLine);
				if (comDoc != null)
				{
					WriteCommissionAccount.logger.info("Created/Updated " + comDoc + " for " + order + " and " + allocLine);
				}
			}
			else
			{
				WriteCommissionAccount.logger.info("Deactivating C_AdvComDoc for " + order + " (if existing)");
				MCAdvComDoc.deactivateIfExist(order);
			}
		}
		else
		{
			isPrepayOrder = false;
		}

		if (allocLine.getC_Invoice_ID() > 0)
		{
			final I_C_Invoice invoice = InterfaceWrapperHelper.create(allocLine.getC_Invoice(), I_C_Invoice.class);
			if (!isPrepayOrder && isTriggerInvoice(invoice))
			{
				final MCAdvComDoc comDoc = MCAdvComDoc.createOrUpdate(invoice, allocLine);
				if (comDoc != null)
				{
					WriteCommissionAccount.logger.info("Created/Updated " + comDoc + " for " + invoice + " and " + allocLine);
				}
			}
			else
			{
				WriteCommissionAccount.logger.info("Deactivating C_AdvComDoc for " + invoice + " (if existing)");
				MCAdvComDoc.deactivateIfExist(invoice);
			}
		}
	}

	static boolean isTriggerInvoice(final I_C_Invoice invoice)
	{
		return invoice.isSOTrx() && invoice.isPaid();
	}

	static boolean isPaidOrder(final I_C_Order order)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(order);
		final String trxName = InterfaceWrapperHelper.getTrxName(order);

		final IPrepayOrderBL prepayOrderBL = Services.get(IPrepayOrderBL.class);
		final BigDecimal allocatedAmt = prepayOrderBL.retrieveAllocatedAmt(ctx, order.getC_Order_ID(), trxName);
		final boolean isPaid = allocatedAmt.compareTo(order.getGrandTotal()) >= 0;

		return isPaid;
	}

}
