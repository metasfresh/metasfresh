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

import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;

import de.metas.invoicecandidate.model.IIsInvoiceCandidateAware;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionHandlerDAO;

/**
 * Creates invoice candidates for {@link I_PP_Order}s which have DocType with BaseType <code>MOP</code> and a special subtype to indicate it's a quality order
 *
 * @author ts
 *
 */
public abstract class AbstractQualityInspectionHandler extends AbstractInvoiceCandidateHandler
{
	@Override
	public final boolean isCreateMissingCandidatesAutomatically()
	{
		return true;
	}

	/**
	 * @return <code>true</code> if the given model is invoicable according to {@link IQualityInspectionHandlerDAO#getInvoiceableOrderFilter()}.
	 */
	@Override
	public final boolean isCreateMissingCandidatesAutomatically(final Object model)
	{
		return true;
	}

	private final PPOrder2InvoiceCandidatesProducer createInvoiceCandidatesProducer()
	{
		final PPOrder2InvoiceCandidatesProducer invoiceCandidatesProducer = new PPOrder2InvoiceCandidatesProducer()
				.setC_ILCandHandler(getHandlerRecord())
				.setILCandHandlerInstance(this);

		return invoiceCandidatesProducer;
	}

	/**
	 * Creates <b>or updates</b> ICs for the given request.
	 */
	@Override
	public InvoiceCandidateGenerateResult createCandidatesFor(final InvoiceCandidateGenerateRequest request)
	{
		final Object model = request.getModel();

		final PPOrder2InvoiceCandidatesProducer invoiceCandidatesProducer = createInvoiceCandidatesProducer();

		final List<de.metas.materialtracking.model.I_C_Invoice_Candidate> invoiceCandidates =
				invoiceCandidatesProducer.createInvoiceCandidates(model);

		final IIsInvoiceCandidateAware isInvoiceCandidateAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(model, IIsInvoiceCandidateAware.class);
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
		Check.errorIf(true, "Shall not be called, because we have getOnInvalidateForModelAction()=RECREATE_ASYNC; model: {0}", model);
	}

	@Override
	public boolean isUserInChargeUserEditable()
	{
		return true;
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
		ic.setQtyDelivered(ic.getQtyOrdered());
		ic.setDeliveryDate(ic.getDateOrdered());
	}

	@Override
	public void setPriceActual(final I_C_Invoice_Candidate ic)
	{
		// nothing to do; the value won't change
	}

	@Override
	public void setBPartnerData(final I_C_Invoice_Candidate ic)
	{
		// nothing to do; the value won't change
	}

	@Override
	public void setC_UOM_ID(final I_C_Invoice_Candidate ic)
	{
		// nothing to do; the value won't change
	}

	@Override
	public void setPriceEntered(final I_C_Invoice_Candidate ic)
	{
		ic.setPriceEntered(ic.getPriceActual());
	}

	/**
	 *
	 * @returns {@link OnInvalidateForModelAction#RECREATE_ASYNC}.
	 */
	@Override
	public final OnInvalidateForModelAction getOnInvalidateForModelAction()
	{
		return OnInvalidateForModelAction.RECREATE_ASYNC;
	}

	/* package */abstract boolean isInvoiceable(Object model);

}
