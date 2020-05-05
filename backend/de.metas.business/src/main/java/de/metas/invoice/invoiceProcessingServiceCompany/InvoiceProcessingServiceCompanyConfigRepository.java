package de.metas.invoice.invoiceProcessingServiceCompany;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.Map;
import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_InvoiceProcessingServiceCompany;
import org.compiere.model.I_InvoiceProcessingServiceCompany_BPartnerAssignment;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.document.DocTypeId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

@Repository
public class InvoiceProcessingServiceCompanyConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<InvoiceProcessingServiceCompanyConfigId, InvoiceProcessingServiceCompanyConfig> //
	configsById = CCache.<InvoiceProcessingServiceCompanyConfigId, InvoiceProcessingServiceCompanyConfig> builder()
			.tableName(I_InvoiceProcessingServiceCompany.Table_Name)
			.build();

	private final CCache<Integer, CustomerToConfigAssignmentMap> //
	customerToConfigAssignmentsCache = CCache.<Integer, CustomerToConfigAssignmentMap> builder()
			.tableName(I_InvoiceProcessingServiceCompany_BPartnerAssignment.Table_Name)
			.build();

	public Optional<InvoiceProcessingServiceCompanyConfig> getByCustomerId(@NonNull final BPartnerId customerId)
	{
		return getConfigIdByCustomerId(customerId)
				.map(this::getById)
				.filter(InvoiceProcessingServiceCompanyConfig::isActive);
	}

	private Optional<InvoiceProcessingServiceCompanyConfigId> getConfigIdByCustomerId(@NonNull final BPartnerId customerId)
	{
		final CustomerToConfigAssignmentMap customerToConfigAssignmentMap = customerToConfigAssignmentsCache.getOrLoad(0, this::retrieveCustomerToConfigAssignmentMap);
		return customerToConfigAssignmentMap.getConfigIdByCustomerId(customerId);
	}

	private InvoiceProcessingServiceCompanyConfig getById(@NonNull final InvoiceProcessingServiceCompanyConfigId configId)
	{
		return configsById.getOrLoad(configId, this::retrieveById);
	}

	private InvoiceProcessingServiceCompanyConfig retrieveById(@NonNull final InvoiceProcessingServiceCompanyConfigId configId)
	{
		final I_InvoiceProcessingServiceCompany record = loadOutOfTrx(configId, I_InvoiceProcessingServiceCompany.class);

		return InvoiceProcessingServiceCompanyConfig.builder()
				.active(record.isActive())
				.serviceCompanyBPartnerId(BPartnerId.ofRepoId(record.getServiceCompany_BPartner_ID()))
				.serviceInvoiceDocTypeId(DocTypeId.ofRepoId(record.getServiceInvoice_DocType_ID()))
				.serviceFeeProductId(ProductId.ofRepoId(record.getServiceFee_Product_ID()))
				.feePercentageOfGrandTotal(Percent.of(record.getFeePercentageOfGrandTotal()))
				.build();
	}

	private CustomerToConfigAssignmentMap retrieveCustomerToConfigAssignmentMap()
	{
		final ImmutableMap<BPartnerId, InvoiceProcessingServiceCompanyConfigId> configIdsByCustomerId = queryBL
				.createQueryBuilder(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						record -> BPartnerId.ofRepoId(record.getC_BPartner_ID()),
						record -> InvoiceProcessingServiceCompanyConfigId.ofRepoId(record.getInvoiceProcessingServiceCompany_ID())));

		return new CustomerToConfigAssignmentMap(configIdsByCustomerId);
	}

	//
	//
	//

	private static class CustomerToConfigAssignmentMap
	{
		private final ImmutableMap<BPartnerId, InvoiceProcessingServiceCompanyConfigId> configIdsByCustomerId;

		private CustomerToConfigAssignmentMap(@NonNull final Map<BPartnerId, InvoiceProcessingServiceCompanyConfigId> configIdsByCustomerId)
		{
			this.configIdsByCustomerId = ImmutableMap.copyOf(configIdsByCustomerId);
		}

		public Optional<InvoiceProcessingServiceCompanyConfigId> getConfigIdByCustomerId(@NonNull final BPartnerId customerId)
		{
			return Optional.ofNullable(configIdsByCustomerId.get(customerId));
		}
	}
}
