package de.metas.materialtracking.qualityBasedInvoicing.ic.spi.impl;

/*
 * #%L
 * de.metas.materialtracking
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

import de.metas.invoicecandidate.model.IIsInvoiceCandidateAware;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.Iterator;
import java.util.List;

/**
 * Creates invoice candidates for {@link I_PP_Order}s that reference a {@link I_M_Material_Tracking}.<br>
 * for this, it makes use of {@link PPOrder2InvoiceCandidatesProducer}
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PP_Order_MaterialTracking_Handler extends AbstractInvoiceCandidateHandler
{
	private final PP_Order_MaterialTracking_HandlerDAO dao = new PP_Order_MaterialTracking_HandlerDAO();

	@Override
	public String getSourceTable()
	{
		return org.eevolution.model.I_PP_Order.Table_Name;
	}

	@Override
	public final CandidatesAutoCreateMode getGeneralCandidatesAutoCreateMode()
	{
		return CandidatesAutoCreateMode.CREATE_CANDIDATES;
	}

	@Override
	public DocTimingType getAutomaticallyCreateMissingCandidatesDocTiming()
	{
		return DocTimingType.AFTER_CLOSE;
	}

	/**
	 * @return <code>true</code> always.
	 */
	@Override
	public final CandidatesAutoCreateMode getSpecificCandidatesAutoCreateMode(final Object model)
	{
		return CandidatesAutoCreateMode.CREATE_CANDIDATES;
	}

	private PPOrder2InvoiceCandidatesProducer createInvoiceCandidatesProducer()
	{
		return new PPOrder2InvoiceCandidatesProducer()
				.setC_ILCandHandler(getHandlerRecord())
				.setILCandHandlerInstance(this);
	}

	/**
	 * Creates <b>or updates</b> ICs for the given request.
	 */
	@Override
	public InvoiceCandidateGenerateResult createCandidatesFor(final InvoiceCandidateGenerateRequest request)
	{
		final I_PP_Order ppOrder = request.getModel(I_PP_Order.class);

		final PPOrder2InvoiceCandidatesProducer invoiceCandidatesProducer = createInvoiceCandidatesProducer();

		final List<de.metas.materialtracking.model.I_C_Invoice_Candidate> invoiceCandidates = invoiceCandidatesProducer.createInvoiceCandidates(ppOrder);

		final IIsInvoiceCandidateAware isInvoiceCandidateAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(ppOrder, IIsInvoiceCandidateAware.class);
		if (isInvoiceCandidateAware != null)
		{
			// we flag the record, no matter if we actually created an IC or not. This is fine for this handler
			isInvoiceCandidateAware.setIsInvoiceCandidate(true);
			InterfaceWrapperHelper.save(isInvoiceCandidateAware);
		}

		return InvoiceCandidateGenerateResult.of(this, invoiceCandidates);
	}

	@Override
	public void invalidateCandidatesFor(final Object model)
	{
		Check.errorIf(true, "Shall not be called, because we have getOnInvalidateForModelAction()=RECREATE_ASYNC; model: {}", model);
	}

	@Override
	public boolean isUserInChargeUserEditable()
	{
		return true;
	}

	/**
	 * @param limit how many quality orders to retrieve
	 * @return all PP_Orders which are suitable for invoices and there are no invoice candidates created yet.
	 */
	@Override
	public Iterator<I_PP_Order> retrieveAllModelsWithMissingCandidates(@NonNull final QueryLimit limit)
	{
		return dao.retrievePPOrdersWithMissingICs(limit);
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void setOrderedData(final I_C_Invoice_Candidate ic)
	{
		// nothing to do; the value won't change
	}

	/**
	 * <ul>
	 * <li>QtyDelivered := QtyOrdered
	 * <li>DeliveryDate := DateOrdered
	 * <li>M_InOut_ID: untouched
	 * </ul>
	 *
	 * @see IInvoiceCandidateHandler#setDeliveredData(I_C_Invoice_Candidate)
	 */
	@Override
	public void setDeliveredData(final I_C_Invoice_Candidate ic)
	{
		ic.setQtyDelivered(ic.getQtyOrdered()); // when changing this, make sure to threat ProductType.Service specially
		ic.setQtyDeliveredInUOM(ic.getQtyEntered());
		ic.setDeliveryDate(ic.getDateOrdered());
	}

	@Override
	public void setBPartnerData(final I_C_Invoice_Candidate ic)
	{
		// nothing to do; the value won't change
	}

	/**
	 * @return {@link OnInvalidateForModelAction#RECREATE_ASYNC}.
	 */
	@Override
	public final OnInvalidateForModelAction getOnInvalidateForModelAction()
	{
		return OnInvalidateForModelAction.RECREATE_ASYNC;
	}

	/* package */boolean isInvoiceable(final Object model)
	{
		final I_PP_Order ppOrder = InterfaceWrapperHelper.create(model, I_PP_Order.class);
		return dao.isInvoiceable(ppOrder);
	}

}
