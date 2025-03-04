package de.metas.costing;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.methods.CostAmountType;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.compiere.model.I_M_CostDetail;

import javax.annotation.Nullable;
import java.time.Instant;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License; or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful;
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not; see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
@Builder
public class CostDetailQuery
{
	@Nullable AcctSchemaId acctSchemaId;
	@Nullable CostElementId costElementId;
	@Nullable CostingDocumentRef documentRef;
	@Nullable CostAmountType amtType;

	@Nullable ProductId productId;
	@Nullable AttributeSetInstanceId attributeSetInstanceId;

	@Nullable ClientId clientId;
	@Nullable OrgId orgId;

	@Nullable CostDetailId afterCostDetailId;

	@Nullable Range<Instant> dateAcctRage;

	@NonNull @Singular ImmutableList<OrderBy> orderBys;

	@Getter
	@AllArgsConstructor
	public enum OrderBy
	{
		ID_ASC(I_M_CostDetail.COLUMNNAME_M_CostDetail_ID, true),
		ID_DESC(I_M_CostDetail.COLUMNNAME_M_CostDetail_ID, false),
		DATE_ACCT_ASC(I_M_CostDetail.COLUMNNAME_DateAcct, true),
		DATE_ACCT_DESC(I_M_CostDetail.COLUMNNAME_DateAcct, false),
		;

		final String columnName;
		final boolean ascending;
	}

	public static CostDetailQueryBuilder builderFrom(@NonNull final CostSegmentAndElement costSegmentAndElement)
	{
		return builderFrom(costSegmentAndElement.toCostSegment())
				.costElementId(costSegmentAndElement.getCostElementId());
	}

	public static CostDetailQueryBuilder builderFrom(@NonNull final CostSegment costSegment)
	{
		return builder()
				.acctSchemaId(costSegment.getAcctSchemaId())
				.productId(costSegment.getProductId())
				.attributeSetInstanceId(costSegment.getAttributeSetInstanceId().asRegularOrNull())
				.clientId(costSegment.getClientId())
				.orgId(costSegment.getOrgId().asRegularOrNull());
	}

}
