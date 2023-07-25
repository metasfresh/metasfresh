package org.adempiere.acct.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import de.metas.acct.Account;
import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.FactAcctQuery;
import de.metas.bpartner.BPartnerId;
import de.metas.document.dimension.Dimension;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.sales_region.SalesRegionId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.I_Fact_Acct;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface IFactAcctBL extends ISingletonService
{

	/**
	 * Gets/creates the account (i.e. {@link I_C_ValidCombination}) of given fact line.
	 */
	Account getAccount(I_Fact_Acct factAcct);

	static AccountDimension extractAccountDimension(final I_Fact_Acct fa)
	{
		return AccountDimension.builder()
				.setAcctSchemaId(AcctSchemaId.ofRepoId(fa.getC_AcctSchema_ID()))
				.setAD_Client_ID(fa.getAD_Client_ID())
				.setAD_Org_ID(fa.getAD_Org_ID())
				.setC_ElementValue_ID(fa.getAccount_ID())
				.setC_SubAcct_ID(fa.getC_SubAcct_ID())
				.setM_Product_ID(fa.getM_Product_ID())
				.setC_BPartner_ID(fa.getC_BPartner_ID())
				.setAD_OrgTrx_ID(fa.getAD_OrgTrx_ID())
				.setC_LocFrom_ID(fa.getC_LocFrom_ID())
				.setC_LocTo_ID(fa.getC_LocTo_ID())
				.setC_SalesRegion_ID(SalesRegionId.ofRepoIdOrNull(fa.getC_SalesRegion_ID()))
				.setC_Project_ID(fa.getC_Project_ID())
				.setC_Campaign_ID(fa.getC_Campaign_ID())
				.setC_Activity_ID(fa.getC_Activity_ID())
				.setSalesOrderId(fa.getC_OrderSO_ID())
				.setM_SectionCode_ID(fa.getM_SectionCode_ID())
				.setUser1_ID(fa.getUser1_ID())
				.setUser2_ID(fa.getUser2_ID())
				.setUserElement1_ID(fa.getUserElement1_ID())
				.setUserElement2_ID(fa.getUserElement2_ID())
				.setUserElementString1(fa.getUserElementString1())
				.setUserElementString2(fa.getUserElementString2())
				.setUserElementString3(fa.getUserElementString3())
				.setUserElementString4(fa.getUserElementString4())
				.setUserElementString5(fa.getUserElementString5())
				.setUserElementString6(fa.getUserElementString6())
				.setUserElementString7(fa.getUserElementString7())
				.build();
	}

	static Dimension extractDimension(final I_Fact_Acct record)
	{
		return Dimension.builder()
				.projectId(ProjectId.ofRepoIdOrNull(record.getC_Project_ID()))
				.campaignId(record.getC_Campaign_ID())
				.activityId(ActivityId.ofRepoIdOrNull(record.getC_Activity_ID()))
				.salesOrderId(OrderId.ofRepoIdOrNull(record.getC_OrderSO_ID()))
				.sectionCodeId(SectionCodeId.ofRepoIdOrNull(record.getM_SectionCode_ID()))
				.productId(ProductId.ofRepoIdOrNull(record.getM_Product_ID()))
				.bpartnerId2(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID()))
				.user1_ID(record.getUser1_ID())
				.user2_ID(record.getUser2_ID())
				.userElement1Id(record.getUserElement1_ID())
				.userElement2Id(record.getUserElement2_ID())
				.userElementString1(record.getUserElementString1())
				.userElementString2(record.getUserElementString2())
				.userElementString3(record.getUserElementString3())
				.userElementString4(record.getUserElementString4())
				.userElementString5(record.getUserElementString5())
				.userElementString6(record.getUserElementString6())
				.userElementString7(record.getUserElementString7())
				.build();
	}

	Optional<Money> getAcctBalance(@NonNull List<FactAcctQuery> queries);

	Stream<I_Fact_Acct> stream(FactAcctQuery query);
}
