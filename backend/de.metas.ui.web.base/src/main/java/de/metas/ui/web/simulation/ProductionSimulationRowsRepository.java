/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.ui.web.simulation;

import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.organization.IOrgDAO;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.productioncandidate.model.dao.PPOrderCandidateDAO;

public class ProductionSimulationRowsRepository
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;
	private final PPOrderCandidateDAO ppOrderCandidateDAO;

	private final LookupDataSource productLookup;
	private final LookupDataSource attributeSetInstanceLookup;
	private final LookupDataSource warehouseLookup;

	@Builder
	public ProductionSimulationRowsRepository(
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval,
			@NonNull final PPOrderCandidateDAO ppOrderCandidateDAO)
	{
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;
		this.ppOrderCandidateDAO = ppOrderCandidateDAO;

		productLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_M_Product.Table_Name);
		attributeSetInstanceLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_M_AttributeSetInstance.Table_Name);
		warehouseLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_M_Warehouse.Table_Name);
	}

	public ProductionSimulationRows getByOrderLineDescriptor(@NonNull final OrderLineDescriptor orderLineDescriptor)
	{
		return ProductionSimulationRowsLoader.builder()
				.candidateRepositoryRetrieval(candidateRepositoryRetrieval)
				.ppOrderCandidateDAO(ppOrderCandidateDAO)
				.productLookup(productLookup)
				.attributeSetInstanceLookup(attributeSetInstanceLookup)
				.warehouseLookup(warehouseLookup)
				.orderLineDescriptor(orderLineDescriptor)
				.orgDAO(orgDAO)
				.build()
				.load();
	}
}
