package de.metas.invoicecandidate.facet.impl;

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


import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;

import org.adempiere.facet.IFacetCollector;
import org.adempiere.facet.impl.CompositeFacetCollector;
import org.adempiere.util.Check;
import org.compiere.util.CLogger;

import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.invoicecandidate.facet.IInvoiceCandidateFacetCollectorFactory;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

public class InvoiceCandidateFacetCollectorFactory implements IInvoiceCandidateFacetCollectorFactory
{
	// services
	private static final transient CLogger logger = InvoiceCandidate_Constants.getLogger();

	private final CopyOnWriteArrayList<Class<? extends IFacetCollector<I_C_Invoice_Candidate>>> facetCollectorClasses = new CopyOnWriteArrayList<>();

	public InvoiceCandidateFacetCollectorFactory()
	{
		super();

		//
		// Register default collector classes
		// NOTE: the order in which they are registered, it's the order in which the facet categories will be displayed to user.
		registerFacetCollectorClass(C_Invoice_Candidate_BillBPartner_FacetCollector.class);
		registerFacetCollectorClass(C_Invoice_Candidate_Order_FacetCollector.class);
		registerFacetCollectorClass(C_Invoice_Candidate_InOut_FacetCollector.class);
		registerFacetCollectorClass(C_Invoice_Candidate_DeliveryDate_FacetCollector.class);
		registerFacetCollectorClass(C_Invoice_Candidate_Misc_FacetCollector.class);
	}

	@Override
	public InvoiceCandidateFacetCollectorFactory registerFacetCollectorClass(final Class<? extends IFacetCollector<I_C_Invoice_Candidate>> facetCollectorClass)
	{
		Check.assumeNotNull(facetCollectorClass, "facetCollectorClass not null");
		facetCollectorClasses.addIfAbsent(facetCollectorClass);

		return this;
	}

	@Override
	public IFacetCollector<I_C_Invoice_Candidate> createFacetCollectors()
	{
		final CompositeFacetCollector<I_C_Invoice_Candidate> collectors = new CompositeFacetCollector<>();
		for (final Class<? extends IFacetCollector<I_C_Invoice_Candidate>> collectorClass : facetCollectorClasses)
		{
			try
			{
				final IFacetCollector<I_C_Invoice_Candidate> collector = collectorClass.newInstance();
				collectors.addFacetCollector(collector);
			}
			catch (final Exception e)
			{
				logger.log(Level.WARNING, "Failed to instantiate collector " + collectorClass + ". Skip it.", e);
			}
		}

		if (!collectors.hasCollectors())
		{
			throw new IllegalStateException("No valid facet collector classes were defined");
		}

		return collectors;
	}
}
