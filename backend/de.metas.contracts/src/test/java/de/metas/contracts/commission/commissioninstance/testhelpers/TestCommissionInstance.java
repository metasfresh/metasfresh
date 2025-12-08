package de.metas.contracts.commission.commissioninstance.testhelpers;

import com.google.common.collect.ImmutableMap;
import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstanceId;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerType;
import de.metas.contracts.commission.model.I_C_Commission_Instance;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.money.CurrencyId;
import de.metas.order.model.I_M_Product_Category;
import de.metas.organization.OrgId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
@Builder
public class TestCommissionInstance
{
	@NonNull
	String pointsBase_Forecasted;
	@NonNull
	String pointsBase_Invoiceable;
	@NonNull
	String pointsBase_Invoiced;

	@NonNull
	Long mostRecentTriggerTimestamp;

	@NonNull
	Timestamp triggerDocumentDate;

	@NonNull
	OrgId orgId;

	@Nullable
	InvoiceCandidateId invoiceCandidateId;

	@Nullable
	InvoiceAndLineId invoiceAndLineId;

	@NonNull
	CommissionTriggerType triggerType;

	@NonNull
	CurrencyId currencyId;

	@NonNull
	Quantity invoicedQty;

	/** If null, the instance's ordered product gets an on-the-fly created category */
	@Nullable
	ProductCategoryId existingSalesProductCategoryId;

	/** If null, the instance's BillPartner gets an on-the-fly created bpGroup */
	@Nullable
	BPGroupId existingCustomerGroupIdId;

	@Singular
	List<TestCommissionShare> commissionShareTestRecords;

	public CreateCommissionInstanceResult createCommissionData()
	{
		final I_M_Product salesProductRecord = newInstance(I_M_Product.class);
		if (existingSalesProductCategoryId != null)
		{
			salesProductRecord.setM_Product_Category_ID(existingSalesProductCategoryId.getRepoId());
		}
		else
		{
			final I_M_Product_Category productCategoryRecord = newInstance(I_M_Product_Category.class);
			saveRecord(productCategoryRecord);
			salesProductRecord.setM_Product_Category_ID(productCategoryRecord.getM_Product_Category_ID());
		}
		saveRecord(salesProductRecord);

		final I_C_BPartner customerRecord = newInstance(I_C_BPartner.class);
		if (existingCustomerGroupIdId != null)
		{
			customerRecord.setC_BP_Group_ID(existingCustomerGroupIdId.getRepoId());
		}
		else
		{
			final I_C_BP_Group bpGroupRecord = newInstance(I_C_BP_Group.class);
			saveRecord(bpGroupRecord);
			customerRecord.setC_BP_Group_ID(bpGroupRecord.getC_BP_Group_ID());
		}
		saveRecord(customerRecord);

		final I_C_Commission_Instance instanceRecord = newInstance(I_C_Commission_Instance.class);
		if (invoiceCandidateId != null)
		{
			instanceRecord.setC_Invoice_Candidate_ID(invoiceCandidateId.getRepoId());
		}
		if (invoiceAndLineId != null)
		{
			instanceRecord.setC_Invoice_ID(invoiceAndLineId.getInvoiceId().getRepoId());
			instanceRecord.setC_InvoiceLine_ID(invoiceAndLineId.getRepoId());
		}

		instanceRecord.setAD_Org_ID(orgId.getRepoId());
		instanceRecord.setCommissionTrigger_Type(triggerType.getCode());
		instanceRecord.setBill_BPartner_ID(customerRecord.getC_BPartner_ID());
		instanceRecord.setM_Product_Order_ID(salesProductRecord.getM_Product_ID());
		instanceRecord.setMostRecentTriggerTimestamp(Timestamp.from(Instant.ofEpochMilli(mostRecentTriggerTimestamp)));
		instanceRecord.setCommissionDate(triggerDocumentDate);
		instanceRecord.setPointsBase_Forecasted(new BigDecimal(pointsBase_Forecasted));
		instanceRecord.setPointsBase_Invoiceable(new BigDecimal(pointsBase_Invoiceable));
		instanceRecord.setPointsBase_Invoiced(new BigDecimal(pointsBase_Invoiced));
		instanceRecord.setC_Currency_ID(currencyId.getRepoId());
		instanceRecord.setQty(invoicedQty.toBigDecimal());
		instanceRecord.setC_UOM_ID(invoicedQty.getUOM().getC_UOM_ID());
		saveRecord(instanceRecord);

		final ImmutableMap.Builder<BPartnerId, Integer> bpartnerId2commissionShareId = ImmutableMap.builder();
		for (final TestCommissionShare commissionShareTestRecord : commissionShareTestRecords)
		{
			final int C_Commission_Share_ID = commissionShareTestRecord.createCommissionData(instanceRecord.getC_Commission_Instance_ID());
			bpartnerId2commissionShareId.put(commissionShareTestRecord.getSalesRepBPartnerId(), C_Commission_Share_ID);
		}
		return new CreateCommissionInstanceResult(
				ProductId.ofRepoId(salesProductRecord.getM_Product_ID()),
				BPartnerId.ofRepoId(customerRecord.getC_BPartner_ID()),
				CommissionInstanceId.ofRepoId(instanceRecord.getC_Commission_Instance_ID()),
				bpartnerId2commissionShareId.build());
	}

	@Value
	public static class CreateCommissionInstanceResult
	{

		@NonNull
		ProductId salesProductId;

		@NonNull
		BPartnerId customerBPartnerId;

		@NonNull
		CommissionInstanceId commissionInstanceId;

		ImmutableMap<BPartnerId, Integer> bpartnerId2commissionShareId;
	}

}
