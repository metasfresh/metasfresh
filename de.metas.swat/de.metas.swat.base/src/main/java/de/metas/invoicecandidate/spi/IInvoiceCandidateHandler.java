package de.metas.invoicecandidate.spi;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

/**
 * Implementors of this class have the job to create and invalidate {@link I_C_Invoice_Candidate} records.
 *
 * They can be registered in {@link I_C_ILCandHandler} table.
 *
 * To get an instance of this interface, use {@link IInvoiceCandidateHandlerBL} methods.
 *
 * Registered implementations will instantiated and their {@link #createMissingCandidates(Properties, String)} method will be called by API.
 *
 * NOTE: because the API will create a new instance each time a handler is needed, it's safe to have status/field variables.
 *
 * @author ts
 *
 * @see IInvoiceCandidateHandlerBL
 */
public interface IInvoiceCandidateHandler
{
	/** Which action shall be performed when an invoice candidate invalidation was requested */
	public enum OnInvalidateForModelAction
	{
		REVALIDATE,

		/**
		 * We will still revalidate invalid ICs, but if a referenced model changes, then {@link IInvoiceCandidateHandler#invalidateCandidatesFor(Object)} will not be called, but the model will be
		 * scheduled for (re-)creation via {@link IInvoiceCandidateHandler#createCandidatesFor(InvoiceCandidateGenerateRequest)}.
		 */
		RECREATE_ASYNC
	}

	/**
	 * @return which action shall be performed when an invoice candidate invalidation was requested
	 */
	OnInvalidateForModelAction getOnInvalidateForModelAction();

	/**
	 * Checks if this handler, in general, can create invoice candidates automatically.
	 *
	 * This is a preliminary condition, and when the business logic has to create invoice candidates automatically, first it will call {@link #isCreateMissingCandidatesAutomatically(Object)}.
	 *
	 * @return true if the invoice candidates shall be automatically generated for {@link #getSourceTable()}.
	 */
	boolean isCreateMissingCandidatesAutomatically();

	/**
	 * Checks if this handler can generate invoice candidates for given model.
	 *
	 * @param model
	 * @return true if the invoice candidates shall be automatically generated for given model.
	 */
	boolean isCreateMissingCandidatesAutomatically(Object model);

	/**
	 * @return {@link DocTimingType} when to create the missing invoice candidates automatically; shall never return null.
	 */
	DocTimingType getAutomaticallyCreateMissingCandidatesDocTiming();

	/**
	 * Retrieves all models which are eligible for invoicing but they have no invoice candidates.
	 *
	 * @param ctx
	 * @param limit how many models shall be retrieved. Note that, at this moment, this is a recommendation which could be respected or not by current implementations.
	 * @param trxName
	 * @return models
	 */
	Iterator<? extends Object> retrieveAllModelsWithMissingCandidates(Properties ctx, int limit, String trxName);

	/**
	 * Called by API to expand an initial invoice candidate generate request.
	 *
	 * Usually this method will return exactly the request which was provided as parameter, but there are some cases when an handler cannot generate missing candidates for a given model but the
	 * handler can advice which handlers can do that and which models shall be used.
	 *
	 * An example would be the M_InOut handler which will expand the initial request to M_InOutLine requests.
	 *
	 * @param request initial request
	 * @return actual requests to be used. never returns null
	 */
	List<InvoiceCandidateGenerateRequest> expandRequest(InvoiceCandidateGenerateRequest request);

	/**
	 * Gets the model to be used when invoice candidate generation is scheduled.
	 *
	 * @param model (of {@link #getSourceTable()} type)
	 * @return model to be used for IC generation scheduling.
	 */
	Object getModelForInvoiceCandidateGenerateScheduling(Object model);

	/**
	 * Creates missing candidates for the given model.
	 *
	 * SPI-implementors can assume that this method is only called with objects that
	 * <ul>
	 * <li>can be handled by {@link InterfaceWrapperHelper} and
	 * <li>belong to the table that is returned by {@link #getSourceTable()}
	 *
	 * Note: usually this method should be called from {@link IInvoiceCandidateHandlerBL} only, because SPI-implementors are not expected to set the new candidates' <code>C_ILCandGenerator_ID</code>
	 * columns.
	 *
	 * @param request
	 * @return result containing invoice candidates that were created
	 */
	InvoiceCandidateGenerateResult createCandidatesFor(InvoiceCandidateGenerateRequest request);

	/**
	 * Invalidates invoice candidates for the given model.
	 *
	 * SPI-implementors can assume that this method is only called with objects that
	 * <ul>
	 * <li>can be handled by {@link InterfaceWrapperHelper} and
	 * <li>belong to the table that is returned by {@link #getSourceTable()}
	 *
	 * @param model
	 */
	void invalidateCandidatesFor(Object model);

	String getSourceTable();

	void setHandlerRecord(I_C_ILCandHandler record);

	I_C_ILCandHandler getHandlerRecord();

	/**
	 * Returns the id of the user to notify in case of problems. A value below zero means "no user".
	 *
	 * @return
	 */
	int getAD_User_InCharge_ID(I_C_Invoice_Candidate ic);

	boolean isUserInChargeUserEditable();

	/**
	 * Method responsible for setting {@link I_C_Invoice_Candidate#setNetAmtToInvoice(java.math.BigDecimal)}.
	 *
	 * @param ic
	 */
	void setNetAmtToInvoice(I_C_Invoice_Candidate ic);

	/**
	 * Method responsible for setting
	 * <ul>
	 * <li>C_Order_ID
	 * <li>DateOrderd
	 * <li>QtyOrdered
	 * </ul>
	 * of the given invoice candidate.
	 * <p>
	 * Implementors can assume that this method is called before {@link #setDeliveredData(I_C_Invoice_Candidate)}.
	 *
	 * @param ic
	 */
	void setOrderedData(I_C_Invoice_Candidate ic);

	/**
	 * Method responsible for setting
	 * <ul>
	 * <li>M_InOut_ID
	 * <li>DeliveryDate
	 * <li>QtyDelivered
	 * </ul>
	 * of the given invoice candidate.
	 * <p>
	 * Implementors can assume that when this method is called then {@link #setOrderedData(I_C_Invoice_Candidate)} was already called.
	 *
	 * @param ic
	 */
	void setDeliveredData(I_C_Invoice_Candidate ic);

	/**
	 * Method responsible for setting
	 * <ul>
	 * <li>PriceActual
	 * <li>Price_UOM_ID
	 * <li>IsTaxIncluded
	 * </ul>
	 * of the given invoice candidate.
	 *
	 * @param ic
	 *
	 */
	void setPriceActual(I_C_Invoice_Candidate ic);

	/**
	 * * Method responsible for setting
	 * <ul>
	 * <li>Bill_BPartner_ID
	 * <li>Bill_Location_ID
	 * <li>Bill_User_ID
	 * </ul>
	 * of the given invoice candidate.
	 *
	 * @param ic
	 */
	void setBPartnerData(I_C_Invoice_Candidate ic);

	/**
	 * Method sets inherited C_UOM_ID opon IC creation
	 *
	 * @param ic
	 */
	void setC_UOM_ID(I_C_Invoice_Candidate ic);

	void setPriceEntered(I_C_Invoice_Candidate ic);
}
