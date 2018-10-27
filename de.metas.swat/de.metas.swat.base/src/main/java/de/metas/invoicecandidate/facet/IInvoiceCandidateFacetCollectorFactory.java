package de.metas.invoicecandidate.facet;

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


import org.adempiere.facet.IFacetCollector;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.ISingletonService;

/**
 * Service responsible for registering and providing all {@link IFacetCollector}s to be used to collect facets from {@link I_C_Invoice_Candidate}s.
 * 
 * @author tsa
 *
 */
public interface IInvoiceCandidateFacetCollectorFactory extends ISingletonService
{
	/**
	 * Register a new {@link IFacetCollector} to be used when collecting facets for {@link I_C_Invoice_Candidate}s.
	 * 
	 * NOTE: the order in which they are registered, it's the order in which the facet categories will be displayed to user.
	 * 
	 * @param facetCollectorClass
	 */
	IInvoiceCandidateFacetCollectorFactory registerFacetCollectorClass(Class<? extends IFacetCollector<I_C_Invoice_Candidate>> facetCollectorClass);

	/**
	 * Create an aggregated/composite facet collector which contains all registered collectors.
	 * 
	 * @return an aggregate/composite of all registered collectors.
	 * @throws IllegalStateException if there were no valid collectors registered.
	 */
	IFacetCollector<I_C_Invoice_Candidate> createFacetCollectors();
}
