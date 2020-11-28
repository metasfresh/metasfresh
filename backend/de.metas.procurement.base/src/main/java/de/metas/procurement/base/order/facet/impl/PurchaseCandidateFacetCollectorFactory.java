package de.metas.procurement.base.order.facet.impl;

import org.adempiere.facet.ClassBasedFacetCollectorFactory;
import org.adempiere.facet.IFacetCollector;
import org.slf4j.Logger;

import de.metas.procurement.base.ProcurementConstants;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;
import de.metas.procurement.base.order.facet.IPurchaseCandidateFacetCollectorFactory;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class PurchaseCandidateFacetCollectorFactory implements IPurchaseCandidateFacetCollectorFactory
{
	// services
	private static final transient Logger logger = ProcurementConstants.getLogger(PurchaseCandidateFacetCollectorFactory.class);

	private final ClassBasedFacetCollectorFactory<I_PMM_PurchaseCandidate> factory = new ClassBasedFacetCollectorFactory<I_PMM_PurchaseCandidate>()
			.setLogger(logger);

	public PurchaseCandidateFacetCollectorFactory()
	{
		super();

		//
		// Register default collector classes
		// NOTE: the order in which they are registered, it's the order in which the facet categories will be displayed to user.
		factory.registerFacetCollectorClasses(
				PMM_PurchaseCandidate_BPartner_FacetCollector.class
				, PMM_PurchaseCandidate_Product_FacetCollector.class
				, PMM_PurchaseCandidate_DatePromised_FacetCollector.class
				);
	}

	@Override
	public IFacetCollector<I_PMM_PurchaseCandidate> createFacetCollectors()
	{
		return factory.createFacetCollectors();
	}

}
