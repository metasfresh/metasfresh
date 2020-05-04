package de.metas.costing.interceptors;

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.springframework.stereotype.Component;

import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.ICostDetailService;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costing.IProductCostingBL;
import de.metas.util.Services;
import lombok.NonNull;

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

@Component
public class CostingInterceptors extends AbstractModuleInterceptor
{
	private final ICostElementRepository costElementRepository;
	private final ICurrentCostsRepository currentCostsRepository;
	private final ICostDetailService costDetailsService;
	private final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);
	private final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);

	public CostingInterceptors(
			@NonNull final ICostElementRepository costElementRepository,
			@NonNull final ICurrentCostsRepository currentCostsRepository,
			@NonNull final ICostDetailService costDetailsService)
	{
		this.costElementRepository = costElementRepository;
		this.currentCostsRepository = currentCostsRepository;
		this.costDetailsService = costDetailsService;
	}

	@Override
	protected void registerInterceptors(@NonNull final IModelValidationEngine engine)
	{
		engine.addModelValidator(new de.metas.costing.interceptors.M_Cost(costElementRepository, productCostingBL));
		engine.addModelValidator(new de.metas.costing.interceptors.M_CostDetail());
		engine.addModelValidator(new de.metas.costing.interceptors.M_CostElement(acctSchemaDAO));
		engine.addModelValidator(new de.metas.costing.interceptors.M_CostType(acctSchemaDAO));
		engine.addModelValidator(new de.metas.costing.interceptors.M_Product_Category_Acct(costElementRepository));
		engine.addModelValidator(new de.metas.costing.interceptors.M_Product(currentCostsRepository, costDetailsService));
	}
}
