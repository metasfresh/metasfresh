package de.metas.contracts.commission.commissioninstance.services;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.commission.model.I_C_Flatrate_Conditions;
import de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings;
import de.metas.contracts.commission.model.I_C_MediatedCommissionSettings;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.logging.LogManager;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

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
	private static final Logger logger = LogManager.getLogger(CommissionProductService.class);

	@NonNull
	public ProductId getCommissionProduct(@NonNull final ConditionsId conditionsId)
	{
		final I_C_Flatrate_Conditions conditionsRecord = InterfaceWrapperHelper.loadOutOfTrx(conditionsId, I_C_Flatrate_Conditions.class);
		final TypeConditions typeConditions = TypeConditions.ofCode(conditionsRecord.getType_Conditions());

		switch (typeConditions)
		{
			case COMMISSION:
				final I_C_HierarchyCommissionSettings commissionSettings = InterfaceWrapperHelper.loadOutOfTrx(conditionsRecord.getC_HierarchyCommissionSettings_ID(), I_C_HierarchyCommissionSettings.class);
				return ProductId.ofRepoId(commissionSettings.getCommission_Product_ID());
			case MEDIATED_COMMISSION:
				final I_C_MediatedCommissionSettings mediatedCommissionSettings = InterfaceWrapperHelper.loadOutOfTrx(conditionsRecord.getC_MediatedCommissionSettings_ID(), I_C_MediatedCommissionSettings.class);
				return ProductId.ofRepoId(mediatedCommissionSettings.getCommission_Product_ID());
			default:
				throw new AdempiereException("Unexpected typeConditions for C_Flatrate_Conditions_ID:" + conditionsId)
						.appendParametersToMessage()
						.setParameter("typeConditions", typeConditions);
		}
	}

	public boolean productPreventsCommissioning(@Nullable final ProductId productId)
	{
		if (productId == null)
		{
			return false; // no product also means nothing is prevented
		}

		final IProductDAO productDAO = Services.get(IProductDAO.class);
		final I_M_Product productRecord = productDAO.getById(productId);
		if (!productRecord.isCommissioned())
		{
			logger.debug("M_Product_ID={} of invoice candidate has Commissioned=false; -> not going to invoke commission system");
		}
		return !productRecord.isCommissioned();
	}
}
