package de.metas.invoicecandidate.spi;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.CurrencyId;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.model.InterfaceWrapperHelper;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import static de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.CandidatesAutoCreateMode.CREATE_CANDIDATES;

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

/**
 * Implementors of this class have the job to create and invalidate {@link I_C_Invoice_Candidate} records.
 * <p>
 * <b>IMPORTANT:</b> They need be registered in the {@link I_C_ILCandHandler} table. Only then the system will create model interceptors to make sure they are invoked at the right times.
 * <p>
 * To get an instance of this interface, use {@link IInvoiceCandidateHandlerBL} methods.
 * <p>
 * Registered implementations will instantiated and their methods will be called by metasfresh.
 * <p>
 * NOTE: because the API will create a new instance each time a handler is needed, it's safe to have status/field variables.
 * NOTE to future devs: It might be tempting to try and add a generic type parameter; i just failed miserably when it came to {@link #expandRequest(InvoiceCandidateGenerateRequest)} and to extending adjacent classes and services.
 *
 * @see IInvoiceCandidateHandlerBL
 */
public interface IInvoiceCandidateHandler
{

	/**
	 * Which action shall be performed when an invoice candidate invalidation was requested
	 */
	enum OnInvalidateForModelAction
	{
		REVALIDATE,

		/**
		 * We will still revalidate invalid ICs, but if a referenced model changes, then {@link IInvoiceCandidateHandler#invalidateCandidatesFor(Object)} will not be called, but the model will be
		 * scheduled for (re-)creation via {@link IInvoiceCandidateHandler#createCandidatesFor(InvoiceCandidateGenerateRequest)}.
		 */
		RECREATE_ASYNC
	}

	enum CandidatesAutoCreateMode
	{
		DONT,

		/** Enqueues the respective model (e.g. order or contract) for invoice creation. */
		CREATE_CANDIDATES,

		/** Like {@link #CREATE_CANDIDATES}, but waits for the candidates to be created and then directly enqueues them for invoice creation. */
		CREATE_CANDIDATES_AND_INVOICES;

		public boolean isDoSomething()
		{
			return this != DONT;
		}
	}

	/**
	 * @return which action shall be performed when an invoice candidate invalidation was requested.
	 * default: {@link OnInvalidateForModelAction#REVALIDATE}.
	 */
	default OnInvalidateForModelAction getOnInvalidateForModelAction()
	{
		return OnInvalidateForModelAction.REVALIDATE;
	}

	/**
	 * Checks if this handler, <b>in general</b>, can create invoice candidates automatically. Returns {@link CandidatesAutoCreateMode#CREATE_CANDIDATES} by default.
	 * <p>
	 * <b>The handler gives a "general" response on startup-time!</b>
	 * When the business logic has to create individual invoice candidates, it will call {@link #getSpecificCandidatesAutoCreateMode(Object)}.
	 *
	 * @return what shall be done for {@link #getSourceTable()}. This default impl returns {@link CandidatesAutoCreateMode#CREATE_CANDIDATES}.
	 */
	default CandidatesAutoCreateMode getGeneralCandidatesAutoCreateMode()
	{
		return CREATE_CANDIDATES;
	}

	/**
	 * Checks if this handler, <b>in particular</b>, can create invoice candidates for the given {@code model}.
	 * <p>
	 * Invoice candidate handlers usually need to implement this method,
	 * because they need to make sure that the given {@code model} does not have an invoice candidate yet, before they can return {@code true}.
	 * <p>
	 * Note that this method only matters if {@link #getGeneralCandidatesAutoCreateMode()} did not return {@link CandidatesAutoCreateMode#DONT} when the system started.
	 *
	 * @return whether the invoice candidates shall be automatically generated for the given particular model.
	 */
	CandidatesAutoCreateMode getSpecificCandidatesAutoCreateMode(Object model);

	/**
	 * Returns {@code AFTER_COMPLETE} by default.
	 *
	 * @return {@link DocTimingType} when to create the missing invoice candidates automatically; shall never return null.
	 */
	default DocTimingType getAutomaticallyCreateMissingCandidatesDocTiming()
	{
		return DocTimingType.AFTER_COMPLETE;
	}

	/**
	 * Retrieves all models which are eligible for invoicing but they have no invoice candidates.
	 *
	 * @param limit advises how many models shall be retrieved. Note that this is an advise which could be respected or not by current implementations.
	 */
	Iterator<?> retrieveAllModelsWithMissingCandidates(@NonNull QueryLimit limit);

	/**
	 * Called by API to expand an initial invoice candidate generate request.
	 * <p>
	 * Usually this method will return exactly the request which was provided as parameter, but there are some cases when an handler cannot generate missing candidates for a given model but the
	 * handler can advice which handlers can do that and which models shall be used.
	 * <p>
	 * An example would be the M_InOut handler which will expand the initial request to M_InOutLine requests.
	 *
	 * @param request initial request
	 * @return actual requests to be used. never returns null
	 */
	default List<InvoiceCandidateGenerateRequest> expandRequest(@NonNull final InvoiceCandidateGenerateRequest request)
	{
		return ImmutableList.of(request);
	}

	/**
	 * Gets the model to be used when invoice candidate generation is scheduled.
	 * <p>
	 * E.g. for an a {@code M_InOutLine} model, this will probably be the model's {@code M_InOut} record.
	 *
	 * @param model (of {@link #getSourceTable()} type)
	 * @return model to be used for IC generation scheduling.
	 */
	default Object getModelForInvoiceCandidateGenerateScheduling(final Object model)
	{
		return model;
	}

	/**
	 * Create missing candidates for the given model. No need to save them, that's already done by the caller.
	 * <p>
	 * SPI-implementors can assume that this method is only called with objects that
	 * <ul>
	 * <li>can be handled by {@link InterfaceWrapperHelper} and
	 * <li>belong to the table that is returned by {@link #getSourceTable()}
	 * <p>
	 * Note: usually this method should be called from {@link IInvoiceCandidateHandlerBL} only, because SPI-implementors are not expected to set the new candidates' <code>C_ILCandGenerator_ID</code>
	 * columns.
	 *
	 * @return result containing invoice candidates that were created
	 */
	InvoiceCandidateGenerateResult createCandidatesFor(InvoiceCandidateGenerateRequest request);

	/**
	 * Invalidates invoice candidates for the given model.
	 * <p>
	 * SPI-implementors can assume that this method is only called with objects that
	 * <ul>
	 * <li>can be handled by {@link InterfaceWrapperHelper} and
	 * <li>belong to the table that is returned by {@link #getSourceTable()}
	 */
	void invalidateCandidatesFor(Object model);

	String getSourceTable();

	void setHandlerRecord(I_C_ILCandHandler record);

	I_C_ILCandHandler getHandlerRecord();

	/**
	 * Returns the id of the user to notify in case of problems. A value below zero means "no user".
	 */
	default int getAD_User_InCharge_ID(final I_C_Invoice_Candidate ic)
	{
		return getHandlerRecord().getAD_User_InCharge_ID();
	}

	boolean isUserInChargeUserEditable();

	/**
	 * Set NetAmtToInvoice = PriceActual * QtyToInvoice - DiscountAmt, rounded to currency precision.<br>
	 * (i.e. method responsible for setting {@link I_C_Invoice_Candidate#setNetAmtToInvoice(java.math.BigDecimal)}).
	 */
	void setNetAmtToInvoice(I_C_Invoice_Candidate ic);

	/**
	 * Set the "full" open invoicable amount, no matter what the invoice rule is.
	 */
	void setLineNetAmt(I_C_Invoice_Candidate ic);

	/**
	 * Method responsible for setting
	 * <ul>
	 * <li>C_Order_ID
	 * <li>DateOrderd
	 * <li>QtyOrdered
	 * <li>QtyEntered
	 * <li>C_UOM_ID
	 * </ul>
	 * of the given invoice candidate.
	 * <p>
	 * Implementors can assume that this method is called before {@link #setDeliveredData(I_C_Invoice_Candidate)}.
	 */
	void setOrderedData(I_C_Invoice_Candidate ic);

	/**
	 * Method responsible for setting
	 * <ul>
	 * <li>{@code M_InOut_ID}
	 * <li>{@code DeliveryDate}
	 * <li>{@code QtyDelivered}
	 * <li>{@code QtyDeliveredInUOM}
	 * </ul>
	 * of the given invoice candidate.
	 * <p>
	 * Implementors can assume that when this method is called then {@link #setOrderedData(I_C_Invoice_Candidate)} was already called.
	 */
	void setDeliveredData(I_C_Invoice_Candidate ic);

	default PriceAndTax calculatePriceAndTax(final I_C_Invoice_Candidate ic)
	{
		return PriceAndTax.NONE;
	}

	default void setShipmentSchedule(final I_C_Invoice_Candidate ic) { /* do nothing */ };

	/**
	 * * Method responsible for setting
	 * <ul>
	 * <li>Bill_BPartner_ID
	 * <li>Bill_Location_ID
	 * <li>Bill_User_ID
	 * </ul>
	 * of the given invoice candidate.
	 */
	void setBPartnerData(I_C_Invoice_Candidate ic);

	default void setIsInEffect(final I_C_Invoice_Candidate ic)
	{
		ic.setIsInEffect(true);
	}

	default void setInvoiceScheduleAndDateToInvoice(@NonNull final I_C_Invoice_Candidate ic)
	{
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

		ic.setC_InvoiceSchedule_ID(bpartnerDAO.getById(ic.getBill_BPartner_ID()).getC_InvoiceSchedule_ID());
		invoiceCandBL.set_DateToInvoice_DefaultImpl(ic);
	}

	/**
	 * So that a handler is given the chance to do additional logic once the invoice candidates have been persisted.
	 */
	default void postSave(@NonNull final InvoiceCandidateGenerateResult result)
	{
		//do nothing
	}

	/**
	 * So that a handler is given the chance to do additional logic once the invoice candidates have been updated.
	 */
	default void postUpdate(@NonNull final I_C_Invoice_Candidate ic)
	{
		//do nothing
	}

	default void setWarehouseId(@NonNull final I_C_Invoice_Candidate ic) {}

	default void setHarvestingDetails(@NonNull final I_C_Invoice_Candidate ic) {}
	
	@NonNull
	default ImmutableList<Object> getRecordsToLock(@NonNull final Object model)
	{
		return ImmutableList.of(model);
	}

	/**
	 * Price and tax info calculation result.
	 * <p>
	 * All fields are optional and only those filled will be set back to invoice candidate.
	 */
	@lombok.Value
	@lombok.Builder
	class PriceAndTax
	{
		public static final PriceAndTax NONE = builder().build();

		PricingSystemId pricingSystemId;
		PriceListVersionId priceListVersionId;
		CurrencyId currencyId;

		BigDecimal priceEntered;
		BigDecimal priceActual;
		UomId priceUOMId;

		Percent discount;

		InvoicableQtyBasedOn invoicableQtyBasedOn;

		Boolean taxIncluded;
		TaxId taxId;
		TaxCategoryId taxCategoryId;

		BigDecimal compensationGroupBaseAmt;
	}

}
