package de.metas.contracts.commission.commissioninstance.testhelpers;

import static de.metas.common.util.CoalesceUtil.coalesce;
import static de.metas.common.util.CoalesceUtil.coalesceSuppliers;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.time.LocalDate;

import javax.annotation.Nullable;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.TimeUtil;

import de.metas.contracts.commission.CommissionConstants;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.engine.IDocument;
import de.metas.product.ProductId;
import de.metas.util.time.SystemTime;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
public class TestCommissionContract
{
	/** Name of the sales rep. If no sales rep with this name exists, one is created on the fly */
	@NonNull
	String salesRepName;

	/** If not set, then use the sales rep's name also for the contract. */
	@Nullable
	String contractName;

	/** mostly used to construct a hierarchy with the contracts' BPartners. */
	@Nullable
	String parentSalesRepName;

	/** The flatrate term is created with start = date minus 10 days and end = date plus 10 days. */
	LocalDate date;

	private TestCommissionContract(
			@NonNull final String salesRepName,
			@Nullable final String contractName,
			@Nullable final String parentSalesRepName,
			@Nullable final LocalDate date)
	{
		this.salesRepName = salesRepName;
		this.contractName = contractName;
		this.parentSalesRepName = parentSalesRepName;
		this.date = coalesceSuppliers(() -> date, () -> SystemTime.asLocalDate());
	}

	/** Supposed to be invoked from {@link TestCommissionConfig}. */
	I_C_Flatrate_Term createContractData(
			@NonNull final Integer C_Flatrate_Conditions_ID,
			@NonNull final ProductId commissionProductId)
	{
		final String effectiveContractName = coalesce(contractName, salesRepName);

		final I_C_Flatrate_Term termRecord = newInstance(I_C_Flatrate_Term.class);
		POJOWrapper.setInstanceName(termRecord, effectiveContractName);

		final I_C_BPartner exitingBPartnerRecord = POJOLookupMap.get().getFirstOnly(I_C_BPartner.class, bpRecord -> salesRepName.equals(bpRecord.getName()));
		if (exitingBPartnerRecord == null)
		{
			final I_C_BPartner saleRepBPartnerRecord = newInstance(I_C_BPartner.class);
			POJOWrapper.setInstanceName(saleRepBPartnerRecord, salesRepName);
			saleRepBPartnerRecord.setName(salesRepName);
			saveRecord(saleRepBPartnerRecord);
			termRecord.setBill_BPartner_ID(saleRepBPartnerRecord.getC_BPartner_ID());
		}
		else
		{
			termRecord.setBill_BPartner_ID(exitingBPartnerRecord.getC_BPartner_ID());
		}
		termRecord.setC_Flatrate_Conditions_ID(C_Flatrate_Conditions_ID);
		termRecord.setNote("name=" + effectiveContractName + " (parentSalesRepName=" + parentSalesRepName + ")");
		termRecord.setDocStatus(IDocument.STATUS_Completed);
		termRecord.setType_Conditions(CommissionConstants.TYPE_CONDITIONS_COMMISSION);
		termRecord.setM_Product_ID(commissionProductId.getRepoId());
		termRecord.setStartDate(TimeUtil.asTimestamp(date.minusDays(10)));
		termRecord.setEndDate(TimeUtil.asTimestamp(date.plusDays(10)));

		saveRecord(termRecord);

		return termRecord;
	}
}
