package de.metas.contracts.commission.commissioninstance.services;

import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Service;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.commission.model.I_C_Flatrate_Conditions;
import de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings;
import de.metas.product.ProductId;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
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

@Service
public class CommissionProductService
{
	public ProductId getCommissionProduct(@NonNull final ConditionsId conditionsId)
	{
		final I_C_Flatrate_Conditions conditionsRecord = InterfaceWrapperHelper.loadOutOfTrx(conditionsId, I_C_Flatrate_Conditions.class);
		final I_C_HierarchyCommissionSettings commissionSettings = InterfaceWrapperHelper.loadOutOfTrx(conditionsRecord.getC_HierarchyCommissionSettings_ID(), I_C_HierarchyCommissionSettings.class);

		return ProductId.ofRepoId(commissionSettings.getCommission_Product_ID());
	}
}
