package de.metas.invoicecandidate.api.impl;

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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.CLoggerLoggable;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.compiere.model.MTable;
import org.compiere.util.CLogger;
import org.compiere.util.TrxRunnable;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerDAO;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.workflow.api.IWFExecutionFactory;

public class InvoiceCandidateHandlerBL implements IInvoiceCandidateHandlerBL
{
	private static final Object NO_MODEL = new Object()
	{
		@Override
		public String toString()
		{
			return getClass().getName();
		}
	};

	private static final transient CLogger logger = CLogger.getCLogger(InvoiceCandidateHandlerBL.class);

	@Override
	public List<IInvoiceCandidateHandler> retrieveImplementationsForTable(final Properties ctx, final String tableName)
	{
		final List<IInvoiceCandidateHandler> result = new ArrayList<IInvoiceCandidateHandler>();

		for (final I_C_ILCandHandler record : Services.get(IInvoiceCandidateHandlerDAO.class).retrieveForTable(ctx, tableName))
		{
			result.add(mkInstance(record));
		}
		return result;
	}

	@Override
	public void evalClassName(final I_C_ILCandHandler ilCandGenerator)
	{
		if (Check.isEmpty(ilCandGenerator.getClassname()))
		{
			ilCandGenerator.setTableName(null);
			return;
		}

		final IInvoiceCandidateHandler handlerClass = mkInstance(ilCandGenerator);

		ilCandGenerator.setTableName(handlerClass.getSourceTable());
		ilCandGenerator.setIs_AD_User_InCharge_UI_Setting(handlerClass.isUserInChargeUserEditable());

		return;
	}

	@Override
	public IInvoiceCandidateHandler mkInstance(final I_C_ILCandHandler handlerRecord)
	{
		final IInvoiceCandidateHandler instance = Util.getInstance(IInvoiceCandidateHandler.class, handlerRecord.getClassname());
		instance.setHandlerRecord(handlerRecord);

		return instance;
	}

	@Override
	public void createMissingCandidates(final Properties ctx, final ILoggable loggable)
	{
		Services.get(ITrxManager.class).run(new TrxRunnable()
		{
			@Override
			public void run(final String trxName) throws Exception
			{
				createInvoiceCandidates(ctx, Services.get(IInvoiceCandidateHandlerDAO.class).retrieveAll(ctx), InvoiceCandidateHandlerBL.NO_MODEL, loggable, trxName);
			}
		});
	}

	@Override
	public List<I_C_Invoice_Candidate> createMissingCandidatesFor(final String tableName, final Object model)
	{
		Check.assumeNotNull(model, "model is not null");

		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final String trxName = InterfaceWrapperHelper.getTrxName(model);

		final List<I_C_ILCandHandler> icCandHandlers = Services.get(IInvoiceCandidateHandlerDAO.class).retrieveForTable(ctx, tableName);
		final CLoggerLoggable loggable = new CLoggerLoggable(InvoiceCandidateHandlerBL.logger, Level.INFO);
		return createInvoiceCandidates(ctx, icCandHandlers, model, loggable, trxName);
	}

	/**
	 * This method does the actual invoice creation by calling the given <code>creatorRecords</code>. Note that each <code>creatorRecord</code> is called multiple times, until it returns the empty
	 * list. That way it is possible to for a creator to create only a limited number of invoice candidates at a time and thus avoid memory issues.
	 *
	 * @param ctx
	 * @param handlerRecords
	 * @param model may be <code>{@link #NO_MODEL}</code>. If it not {@link #NO_MODEL}, then the method only created missing candidates for the given model.
	 * @param trxName
	 * @return if model is <code>{@link #NO_MODEL}</code>, then we return the empty list. If not, then we return the created invoice candidates.
	 */
	private List<I_C_Invoice_Candidate> createInvoiceCandidates(
			final Properties ctx,
			final List<I_C_ILCandHandler> handlerRecords,
			final Object model,
			final ILoggable loggable,
			final String trxName)
	{
		final List<I_C_Invoice_Candidate> result = new ArrayList<I_C_Invoice_Candidate>();

		if (handlerRecords == null || handlerRecords.isEmpty())
		{
			logger.log(Level.WARNING, "No C_ILCandHandler were provided for '{0}'. Nothing to do.", model);
		}

		// services
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		for (final I_C_ILCandHandler handlerRecord : handlerRecords)
		{
			loggable.addLog("Invoking handler: " + handlerRecord.getName() + " (" + handlerRecord + ")");
			try
			{
				final IInvoiceCandidateHandler creatorImpl = mkInstance(handlerRecord);

				// HARDCODED BufferSize/Limit to be used when we are creating missing candidates
				final int bufferSize = 500;

				List<I_C_Invoice_Candidate> newCandidates = createCandidates(ctx, model, bufferSize, creatorImpl, trxName);
				final int candidatesCount = newCandidates.size();
				while (!newCandidates.isEmpty())
				{
					if (Util.same(model, InvoiceCandidateHandlerBL.NO_MODEL))
					{
						trxManager.commit(trxName);
					}
					else
					{
						result.addAll(newCandidates);
						break;
					}

					newCandidates = createCandidates(ctx, model, bufferSize, creatorImpl, trxName);
				}

				loggable.addLog("Handler " + handlerRecord.getName() + ": @Created@ #" + candidatesCount);
			}
			catch (final RuntimeException e)
			{
				// log the error, but go on
				final Throwable rootCause = ExceptionUtils.getRootCause(e);
				final String errmsg = "Caught " + (rootCause != null ? rootCause : e).getClass()
						+ " calling createMissingCandidates() method of handler " + handlerRecord.getName() + " (class " + handlerRecord.getClassname() + ") : "
						+ ExceptionUtils.getRootCauseMessage(e);
				loggable.addLog(errmsg);

				logger.log(Level.WARNING, errmsg, e);
				throw e; // rethrow. that ways, at least the trx is rolled back
			}

		}

		if (Util.same(model, InvoiceCandidateHandlerBL.NO_MODEL))
		{
			Check.assume(result.isEmpty(), "Internal error: result shall be empty");
			trxManager.commit(trxName);
		}

		return result;
	}

	/**
	 * Create candidates. If model is {@link #NO_MODEL} then all missing candidates will be created.
	 *
	 * @param ctx
	 * @param model if set to a value != {@link #NO_MODEL}, then only candidates for the given model are created,
	 * @param bufferSize used only when creating missing candidates. See limit parameter of {@link IInvoiceCandidateHandler#createMissingCandidates(Properties, int, String)}.
	 * @param invoiceCandiateHandler
	 * @param trxName
	 * @return created candidates
	 */
	private List<I_C_Invoice_Candidate> createCandidates(final Properties ctx, final Object model, final int bufferSize, final IInvoiceCandidateHandler invoiceCandiateHandler, final String trxName)
	{
		final List<I_C_Invoice_Candidate> newCandidates = new ArrayList<I_C_Invoice_Candidate>();

		if (Util.same(model, InvoiceCandidateHandlerBL.NO_MODEL))
		{
			newCandidates.addAll(invoiceCandiateHandler.createMissingCandidates(ctx, bufferSize, trxName));
		}
		else
		{
			newCandidates.addAll(invoiceCandiateHandler.createCandidatesFor(model));
		}

		updateDefaultsAndSave(invoiceCandiateHandler, newCandidates, model);

		return newCandidates;
	}

	/**
	 * Make sure all candidates are persisted to database. It also:
	 * <ul>
	 * <li>set AD_User_InChange_ID
	 * </ul>
	 *
	 * @param handler
	 * @param candidates
	 * @param model source model
	 */
	private void updateDefaultsAndSave(final IInvoiceCandidateHandler handler, final List<I_C_Invoice_Candidate> candidates, final Object model)
	{
		if (candidates == null || candidates.isEmpty())
		{
			return;
		}

		for (final I_C_Invoice_Candidate ic : candidates)
		{
			updateDefaultsAndSaveSingleCandidate(handler, ic, model);
		}

		invalidateNewCandidates(candidates);
	}

	private void updateDefaultsAndSaveSingleCandidate(final IInvoiceCandidateHandler handler, final I_C_Invoice_Candidate ic, final Object model)
	{
		Check.assumeNotNull(handler, "handler not null");

		// Make sure there is a link to creator/handler
		final I_C_ILCandHandler handlerDef = handler.getHandlerRecord();
		if (handlerDef != null)
		{
			ic.setC_ILCandHandler_ID(handlerDef.getC_ILCandHandler_ID());
		}

		// Make sure User InCharge is set
		if (ic.getAD_User_InCharge_ID() <= 0)
		{
			final int adUserInChargeId = handler.getAD_User_InCharge_ID(ic);
			ic.setAD_User_InCharge_ID(adUserInChargeId);
		}

		// Save it
		InterfaceWrapperHelper.save(ic);

		// task 05791: notify the system whenever this new ic references an existing PO
		if (ic.getAD_Table_ID() > 0 && ic.getRecord_ID() > 0)
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(ic, true); // useClientOrgFromModel == true
			final String trxName = InterfaceWrapperHelper.getTrxName(ic); // using the ic's transaction as opposed to ITrx.TRXNAME_None, because the PO referenced by ic might as well have been created
																			// within it

			Services.get(IWFExecutionFactory.class).notifyActivityPerformed( // 03745
					MTable.get(ctx, ic.getAD_Table_ID()).getPO(ic.getRecord_ID(), trxName),
					ic);
		}
	}

	private void invalidateNewCandidates(final List<I_C_Invoice_Candidate> candidates)
	{
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

		// group them by
		// * HeaderAggregationKey
		// * Bill_BPartner_ID
		// * AD_Table_ID
		// * Record_ID
		// add one representative for each combination to our map 'key2IC'
		final Map<ArrayKey, I_C_Invoice_Candidate> headerAndPartnerKey2IC = new HashMap<ArrayKey, I_C_Invoice_Candidate>();
		final Map<ArrayKey, I_C_Invoice_Candidate> referencedRecordKey2IC = new HashMap<ArrayKey, I_C_Invoice_Candidate>();
		for (final I_C_Invoice_Candidate ic : candidates)
		{
			final ArrayKey headerAndPartnerKey = Util.mkKey(ic.getHeaderAggregationKey(), ic.getBill_BPartner_ID());
			if (!headerAndPartnerKey2IC.containsKey(headerAndPartnerKey))
			{
				// we done have such a representative yet, now add it
				headerAndPartnerKey2IC.put(headerAndPartnerKey, ic);
			}

			final ArrayKey referencedRecordKey = Util.mkKey(ic.getAD_Table_ID(), ic.getRecord_ID());
			if (!referencedRecordKey2IC.containsKey(headerAndPartnerKey))
			{
				// we done have such a representative yet, now add it
				referencedRecordKey2IC.put(referencedRecordKey, ic);
			}
		}

		// now call the api for each representative, assuming that this way *all* candidates that need it are invalidated, but not each one a thousand time.
		for (final I_C_Invoice_Candidate ic : headerAndPartnerKey2IC.values())
		{
			invoiceCandBL.invalidateForPartnerIfInvoiceRuleDemandsIt(ic);
		}

		for (final I_C_Invoice_Candidate ic : referencedRecordKey2IC.values())
		{
			invoiceCandBL.invalidateUsingHandler(ic);
		}
	}

	/**
	 * Create an instance of {@link IInvoiceCandidateHandler} implementation that can handle given invoice candidate.
	 *
	 * @param ic
	 * @return handler instance
	 */
	private IInvoiceCandidateHandler createInvoiceCandidateHandler(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandidateHandlerDAO invoiceCandidateHandlerDAO = Services.get(IInvoiceCandidateHandlerDAO.class);
		final I_C_ILCandHandler handler = invoiceCandidateHandlerDAO.retrieveFor(ic);
		final IInvoiceCandidateHandler handlerInstance = mkInstance(handler);
		return handlerInstance;
	}

	@Override
	public void setNetAmtToInvoice(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandidateHandler handler = createInvoiceCandidateHandler(ic);
		handler.setNetAmtToInvoice(ic);
	}

	@Override
	public void setOrderedData(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandidateHandler handler = createInvoiceCandidateHandler(ic);
		handler.setOrderedData(ic);
	}

	@Override
	public void setDeliveredData(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandidateHandler handler = createInvoiceCandidateHandler(ic);
		handler.setDeliveredData(ic);
	}

	@Override
	public void invalidateCandidatesFor(final Object model)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final String tableName = InterfaceWrapperHelper.getTableName(model.getClass());

		for (final IInvoiceCandidateHandler handler : retrieveImplementationsForTable(ctx, tableName))
		{
			handler.invalidateCandidatesFor(model);
		}
	}

	@Override
	public void setPriceActual(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandidateHandler handler = createInvoiceCandidateHandler(ic);
		handler.setPriceActual(ic);
	}

	@Override
	public void setPriceEntered(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandidateHandler handler = createInvoiceCandidateHandler(ic);
		handler.setPriceEntered(ic);
	}

	@Override
	public void setBPartnerData(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandidateHandler handler = createInvoiceCandidateHandler(ic);
		handler.setBPartnerData(ic);
	}

	@Override
	public void setC_UOM_ID(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandidateHandler handler = createInvoiceCandidateHandler(ic);
		handler.setC_UOM_ID(ic);
	}
}
