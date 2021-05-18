package de.metas.contracts.commission.commissioninstance.testhelpers;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import javax.annotation.Nullable;

import de.metas.organization.OrgId;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;

import de.metas.bpartner.BPGroupId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionSettingsLineId;
import de.metas.contracts.commission.model.I_C_CommissionSettingsLine;
import de.metas.product.ProductCategoryId;
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
public class TestCommissionConfigLine
{
	/** Not persisted in the C_CommissionSettingsLine; just needed for the case that a particular line shall to be accessed later in the test */
	@NonNull
	String name;

	@Nullable
	BPGroupId customerBGroupId;

	@Nullable
	ProductCategoryId salesProductCategoryId;

	@NonNull
	String percentOfBasePoints;

	@NonNull
	Integer seqNo;

	/** supposed to be invoked from {@link TestCommissionConfig}. */
	IPair<String, CommissionSettingsLineId> createConfigLineData(
			@NonNull final OrgId orgId,
			final int C_HierarchyCommissionSettings)
	{
		final I_C_CommissionSettingsLine settingsLineRecord = newInstance(I_C_CommissionSettingsLine.class);
		settingsLineRecord.setAD_Org_ID(OrgId.toRepoId(orgId));
		settingsLineRecord.setC_HierarchyCommissionSettings_ID(C_HierarchyCommissionSettings);
		settingsLineRecord.setSeqNo(seqNo);
		settingsLineRecord.setPercentOfBasePoints(new java.math.BigDecimal(percentOfBasePoints));
		settingsLineRecord.setCustomer_Group_ID(BPGroupId.toRepoId(customerBGroupId));
		settingsLineRecord.setM_Product_Category_ID(ProductCategoryId.toRepoId(salesProductCategoryId));

		saveRecord(settingsLineRecord);

		return ImmutablePair.of(name, CommissionSettingsLineId.ofRepoId(settingsLineRecord.getC_CommissionSettingsLine_ID()));
	}
}
