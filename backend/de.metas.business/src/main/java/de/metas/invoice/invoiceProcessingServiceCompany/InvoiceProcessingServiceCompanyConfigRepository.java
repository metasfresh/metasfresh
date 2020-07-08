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
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.document.DocTypeId;
import de.metas.product.ProductId;
import de.metas.util.GuavaCollectors;
import de.metas.util.ImmutableMapEntry;
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
import java.util.function.Function;

@Repository
public class InvoiceProcessingServiceCompanyConfigRepository
{
	private final CCache<Integer, InvoiceProcessingServiceCompanyConfigMap> configMapCache =
			CCache.<Integer, InvoiceProcessingServiceCompanyConfigMap>builder()
					.tableName(I_InvoiceProcessingServiceCompany.Table_Name)
					.additionalTableNameToResetFor(I_InvoiceProcessingServiceCompany_BPartnerAssignment.Table_Name)
					.build();

	public Optional<InvoiceProcessingServiceCompanyConfig> getByCustomerId(@NonNull final BPartnerId customerId, @NonNull final ZonedDateTime evaluationDate)
	{
		final InvoiceProcessingServiceCompanyConfigMap configMap = configMapCache.getOrLoad(0, InvoiceProcessingServiceCompanyConfigRepository::retrieveAllCompanyConfigs);
		return configMap.getByCustomerIdAndDate(customerId, evaluationDate);
	}

	public Optional<InvoiceProcessingServiceCompanyConfig> getByPaymentBPartnerAndValidFromDate(@NonNull final BPartnerId serviceCompanyBPartnerId, @NonNull final ZonedDateTime evaluationDate)
	{
		final InvoiceProcessingServiceCompanyConfigMap configMap = configMapCache.getOrLoad(0, InvoiceProcessingServiceCompanyConfigRepository::retrieveAllCompanyConfigs);
		return configMap.getByServiceCompanyBPartnerIdAndDateIncludingInvalidDates(serviceCompanyBPartnerId, evaluationDate);
	}

	@NonNull
	static private InvoiceProcessingServiceCompanyConfigMap retrieveAllCompanyConfigs()
	{
		final ImmutableListMultimap<InvoiceProcessingServiceCompanyConfigId, InvoiceProcessingServiceCompanyConfigBPartnerDetails> bpartnerDetailsByCompanyConfig = retrieveAllBPartnerDetailsMappedByCompany();

		final ImmutableList<InvoiceProcessingServiceCompanyConfig> companyConfigs =
				Services.get(IQueryBL.class)
						.createQueryBuilder(I_InvoiceProcessingServiceCompany.class)
						.addOnlyActiveRecordsFilter()
						.create()
						.iterateAndStream()
						.map(toCompanyConfig(bpartnerDetailsByCompanyConfig))
						.collect(GuavaCollectors.toImmutableList());
		return new InvoiceProcessingServiceCompanyConfigMap(companyConfigs);
	}

	@NonNull
	private static Function<I_InvoiceProcessingServiceCompany, InvoiceProcessingServiceCompanyConfig> toCompanyConfig(
			final ImmutableListMultimap<InvoiceProcessingServiceCompanyConfigId, InvoiceProcessingServiceCompanyConfigBPartnerDetails> bpartnerDetailsByCompanyConfig)
	{
		return record -> {
			final InvoiceProcessingServiceCompanyConfigId companyConfigId = InvoiceProcessingServiceCompanyConfigId.ofRepoId(record.getInvoiceProcessingServiceCompany_ID());
			final ImmutableMap<BPartnerId, InvoiceProcessingServiceCompanyConfigBPartnerDetails> partnerDetails = getBPartnerDetailsForCompany(bpartnerDetailsByCompanyConfig, companyConfigId);

			return InvoiceProcessingServiceCompanyConfig.builder()
					.serviceCompanyBPartnerId(BPartnerId.ofRepoId(record.getServiceCompany_BPartner_ID()))
					.serviceInvoiceDocTypeId(DocTypeId.ofRepoId(record.getServiceInvoice_DocType_ID()))
					.serviceFeeProductId(ProductId.ofRepoId(record.getServiceFee_Product_ID()))
					.validFrom(TimeUtil.asZonedDateTime(record.getValidFrom()))
					.bpartnerDetails(partnerDetails)
					.build();
		};
	}

	static private ImmutableMap<BPartnerId, InvoiceProcessingServiceCompanyConfigBPartnerDetails> getBPartnerDetailsForCompany(
			@NonNull final ImmutableListMultimap<InvoiceProcessingServiceCompanyConfigId, InvoiceProcessingServiceCompanyConfigBPartnerDetails> bpartnerDetailsByCompanyConfig,
			@NonNull final InvoiceProcessingServiceCompanyConfigId companyConfigId)
	{
		return bpartnerDetailsByCompanyConfig.get(companyConfigId)
				.stream()
				.collect(GuavaCollectors.toImmutableMapByKey(InvoiceProcessingServiceCompanyConfigBPartnerDetails::getBpartnerId));
	}

	private static ImmutableListMultimap<InvoiceProcessingServiceCompanyConfigId, InvoiceProcessingServiceCompanyConfigBPartnerDetails> retrieveAllBPartnerDetailsMappedByCompany()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.iterateAndStream()
				.map(bpartnerDetailsToMapEntry())
				.collect(GuavaCollectors.toImmutableListMultimap());
	}

	@NonNull
	private static Function<I_InvoiceProcessingServiceCompany_BPartnerAssignment, ImmutableMapEntry<InvoiceProcessingServiceCompanyConfigId, InvoiceProcessingServiceCompanyConfigBPartnerDetails>> bpartnerDetailsToMapEntry()
	{
		return recordBP -> {
			final InvoiceProcessingServiceCompanyConfigBPartnerDetails partnerDetails = InvoiceProcessingServiceCompanyConfigBPartnerDetails.builder()
					.bpartnerId(BPartnerId.ofRepoId(recordBP.getC_BPartner_ID()))
					.percent(Percent.of(recordBP.getFeePercentageOfGrandTotal()))
					.build();

			final InvoiceProcessingServiceCompanyConfigId companyConfigId = InvoiceProcessingServiceCompanyConfigId.ofRepoId(recordBP.getInvoiceProcessingServiceCompany_ID());
			return ImmutableMapEntry.of(companyConfigId, partnerDetails);
		};
	}
}
