/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular.invgroup.interceptor;

import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.contracts.model.I_ModCntr_InvoicingGroup;
import de.metas.contracts.model.I_ModCntr_InvoicingGroup_Product;
import de.metas.contracts.modular.invgroup.InvoicingGroup;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.invgroup.InvoicingGroupProductId;
import de.metas.i18n.AdMessageKey;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class ModCntrInvoicingGroupRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final static AdMessageKey MSG_PRODUCT_IN_ANOTHER_INVOICING_GROUP = AdMessageKey.of("de.metas.contracts.modular.invgroup.ProductInAnotherGroup");

	public void validateInvoicingGroupProductNoOverlap(
			@NonNull final ProductId productId,
			@Nullable final InvoicingGroupProductId excludeInvoicingGroupProductId,
			@NonNull final YearAndCalendarId yearAndCalendarId)
	{
		final IQueryBuilder<I_ModCntr_InvoicingGroup_Product> queryBuilder = queryBL.createQueryBuilder(I_ModCntr_InvoicingGroup_Product.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ModCntr_InvoicingGroup_Product.COLUMNNAME_M_Product_ID, productId);
		if (excludeInvoicingGroupProductId != null)
		{
			queryBuilder.addNotEqualsFilter(I_ModCntr_InvoicingGroup_Product.COLUMNNAME_ModCntr_InvoicingGroup_Product_ID, excludeInvoicingGroupProductId);
		}
		final boolean invoicingGroupsOverlapingForProduct = queryBuilder
				.andCollect(I_ModCntr_InvoicingGroup_Product.COLUMN_ModCntr_InvoicingGroup_ID)
				.addEqualsFilter(I_ModCntr_InvoicingGroup.COLUMNNAME_C_Harvesting_Calendar_ID, yearAndCalendarId.calendarId())
				.addEqualsFilter(I_ModCntr_InvoicingGroup.COLUMNNAME_Harvesting_Year_ID, yearAndCalendarId.yearId())
				.create()
				.anyMatch();
		if (invoicingGroupsOverlapingForProduct)
		{
			throw new AdempiereException(MSG_PRODUCT_IN_ANOTHER_INVOICING_GROUP);
		}
	}

	public Stream<I_ModCntr_InvoicingGroup_Product> streamInvoicingGroupProductsFor(@NonNull final InvoicingGroupId invoicingGroupId)
	{
		return queryBL.createQueryBuilder(I_ModCntr_InvoicingGroup_Product.class)
				.addEqualsFilter(I_ModCntr_InvoicingGroup_Product.COLUMNNAME_ModCntr_InvoicingGroup_ID, invoicingGroupId)
				.addOnlyActiveRecordsFilter()
				.stream();
	}

	@NonNull
	public Optional<ProductId> getInvoicingGroupProductFor(@NonNull final ProductId productId,@NonNull final YearAndCalendarId yearAndCalendarId)
	{
		return getInvoicingGroupRecordFor(productId, yearAndCalendarId)
				.map(group -> ProductId.ofRepoId(group.getGroup_Product_ID()));
	}

	@NonNull
	public Optional<InvoicingGroupId> getInvoicingGroupIdFor(
			@NonNull final ProductId productId,
			@NonNull final YearAndCalendarId yearAndCalendarId)
	{
		return getInvoicingGroupRecordFor(productId, yearAndCalendarId)
				.map(group -> InvoicingGroupId.ofRepoId(group.getModCntr_InvoicingGroup_ID()));
	}

	@NonNull
	private Optional<I_ModCntr_InvoicingGroup> getInvoicingGroupRecordFor(
			@NonNull final ProductId productId,
			@NonNull final YearAndCalendarId yearAndCalendarId)
	{
		return queryBL.createQueryBuilder(I_ModCntr_InvoicingGroup_Product.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ModCntr_InvoicingGroup_Product.COLUMNNAME_M_Product_ID, productId)
				.andCollect(I_ModCntr_InvoicingGroup_Product.COLUMN_ModCntr_InvoicingGroup_ID)
				.addEqualsFilter(I_ModCntr_InvoicingGroup.COLUMNNAME_C_Harvesting_Calendar_ID, yearAndCalendarId.calendarId())
				.addEqualsFilter(I_ModCntr_InvoicingGroup.COLUMNNAME_Harvesting_Year_ID, yearAndCalendarId.yearId())
				.create()
				.firstOnlyOptional();
	}

	public Stream<InvoicingGroup> streamAll()
	{
		return queryBL.createQueryBuilder(I_ModCntr_InvoicingGroup.class)
				//currently there's no tighter filter criteria to minimise the number of invoicing groups that are considered
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(this::fromDB);
	}

	@NonNull
	public InvoicingGroup getById(@NonNull final InvoicingGroupId invoicingGroupId)
	{
		final I_ModCntr_InvoicingGroup invoicingGroup = InterfaceWrapperHelper.load(invoicingGroupId, I_ModCntr_InvoicingGroup.class);
		return fromDB(invoicingGroup);
	}

	private InvoicingGroup fromDB(@NonNull final I_ModCntr_InvoicingGroup invoicingGroup)
	{
		return InvoicingGroup.builder()
				.id(InvoicingGroupId.ofRepoId(invoicingGroup.getModCntr_InvoicingGroup_ID()))
				.name(invoicingGroup.getName())
				.productId(ProductId.ofRepoIdOrNull(invoicingGroup.getGroup_Product_ID()))
				.yearId(YearId.ofRepoId(invoicingGroup.getHarvesting_Year_ID()))
				.amtToDistribute(Money.ofOrNull(invoicingGroup.getTotalInterest(), CurrencyId.ofRepoId(invoicingGroup.getC_Currency_ID())))
				.build();
	}
}
