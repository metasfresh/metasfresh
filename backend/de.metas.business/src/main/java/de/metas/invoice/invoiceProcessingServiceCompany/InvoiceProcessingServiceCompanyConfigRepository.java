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

package de.metas.invoice.invoiceProcessingServiceCompany;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.document.DocTypeId;
import de.metas.product.ProductId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_InvoiceProcessingServiceCompany;
import org.compiere.model.I_InvoiceProcessingServiceCompany_BPartnerAssignment;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Optional;

@Repository
public class InvoiceProcessingServiceCompanyConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, InvoiceProcessingServiceCompanyConfigMap> configsMapCache =
			CCache.<Integer, InvoiceProcessingServiceCompanyConfigMap>builder()
					.tableName(I_InvoiceProcessingServiceCompany.Table_Name)
					.additionalTableNameToResetFor(I_InvoiceProcessingServiceCompany_BPartnerAssignment.Table_Name)
					.build();

	public Optional<InvoiceProcessingServiceCompanyConfig> getByCustomerId(@NonNull final BPartnerId customerId, @NonNull final ZonedDateTime evaluationDate)
	{
		final InvoiceProcessingServiceCompanyConfigMap config = configsMapCache.getOrLoad(0, this::retrieveAllCompanyConfigs);
		return config.getByCustomerIdAndDate(customerId, evaluationDate);
	}

	@NonNull
	private InvoiceProcessingServiceCompanyConfigMap retrieveAllCompanyConfigs()
	{
		final ImmutableList<InvoiceProcessingServiceCompanyConfig> collect = queryBL.createQueryBuilder(I_InvoiceProcessingServiceCompany.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.iterateAndStream()
				.map(this::fromPO)
				.collect(GuavaCollectors.toImmutableList());
		return new InvoiceProcessingServiceCompanyConfigMap(collect);
	}

	private InvoiceProcessingServiceCompanyConfig fromPO(@NonNull final I_InvoiceProcessingServiceCompany record)
	{
		final ImmutableList<InvoiceProcessingServiceCompanyConfigBPartnerDetails> partnerDetails = retrieveAllBPartnerDetails(record);

		return InvoiceProcessingServiceCompanyConfig.builder()
				.serviceCompanyBPartnerId(BPartnerId.ofRepoId(record.getServiceCompany_BPartner_ID()))
				.serviceInvoiceDocTypeId(DocTypeId.ofRepoId(record.getServiceInvoice_DocType_ID()))
				.serviceFeeProductId(ProductId.ofRepoId(record.getServiceFee_Product_ID()))
				.validFrom(TimeUtil.asZonedDateTime(record.getValidFrom()))
				.bpartnerDetails(partnerDetails)
				.build();
	}

	private ImmutableList<InvoiceProcessingServiceCompanyConfigBPartnerDetails> retrieveAllBPartnerDetails(@NonNull final I_InvoiceProcessingServiceCompany company)
	{
		return queryBL
				.createQueryBuilder(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_InvoiceProcessingServiceCompany_BPartnerAssignment.COLUMN_InvoiceProcessingServiceCompany_ID, company.getInvoiceProcessingServiceCompany_ID())
				.create()
				.iterateAndStream()
				.map(recordBP -> InvoiceProcessingServiceCompanyConfigBPartnerDetails.builder()
						.bpartnerId(BPartnerId.ofRepoId(recordBP.getC_BPartner_ID()))
						.percent(Percent.of(recordBP.getFeePercentageOfGrandTotal()))
						.build()
				)
				.collect(GuavaCollectors.toImmutableList());
	}
}
