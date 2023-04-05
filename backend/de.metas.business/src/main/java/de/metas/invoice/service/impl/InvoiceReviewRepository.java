/*
 * #%L
 * de.metas.business
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

package de.metas.invoice.service.impl;

import de.metas.invoice.InvoiceId;
import de.metas.invoice.review.InvoiceReview;
import de.metas.invoice.review.InvoiceReviewId;
import de.metas.organization.OrgId;
import de.metas.po.CustomColumnService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice_Review;
import org.springframework.stereotype.Repository;

@Repository
public class InvoiceReviewRepository
{
	IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CustomColumnService customColumnService;

	public InvoiceReviewRepository(final CustomColumnService customColumnService)
	{
		this.customColumnService = customColumnService;
	}

	public InvoiceReview getOrCreateByInvoiceId(@NonNull final InvoiceId invoiceId, @NonNull final OrgId orgId)
	{
		return queryBL.createQueryBuilder(I_C_Invoice_Review.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Review.COLUMNNAME_C_Invoice_ID, invoiceId)
				.create()
				.firstOnlyOptional()
				.map(this::fromDB)
				.orElseGet(() -> this.create(invoiceId, orgId));
	}

	private InvoiceReview create(@NonNull final InvoiceId invoiceId, @NonNull final OrgId orgId)
	{
		return InvoiceReview.builder()
				.invoiceId(invoiceId)
				.orgId(orgId)
				.build();
	}

	private InvoiceReview fromDB(@NonNull final I_C_Invoice_Review review)
	{
		return InvoiceReview.builder()
				.id(InvoiceReviewId.ofRepoId(review.getC_Invoice_Review_ID()))
				.invoiceId(InvoiceId.ofRepoId(review.getC_Invoice_ID()))
				.orgId(OrgId.ofRepoId(review.getAD_Org_ID()))
				.extendedProps(customColumnService.getCustomColumnsAsMap(InterfaceWrapperHelper.getPO(review)))
				.build();
	}

	public InvoiceReviewId save(@NonNull final InvoiceReview invoiceReview)
	{
		final I_C_Invoice_Review review = InterfaceWrapperHelper.loadOrNew(invoiceReview.getId(), I_C_Invoice_Review.class);
		review.setC_Invoice_ID(invoiceReview.getInvoiceId().getRepoId());
		review.setAD_Org_ID(invoiceReview.getOrgId().getRepoId());
		customColumnService.setCustomColumns(InterfaceWrapperHelper.getPO(review), invoiceReview.getExtendedProps());
		InterfaceWrapperHelper.save(review);

		return InvoiceReviewId.ofRepoId(review.getC_Invoice_Review_ID());
	}

}
