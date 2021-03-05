package de.metas.ui.web.pricing.process;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.I_M_Product;
import org.compiere.model.PO;

import de.metas.pricing.conditions.CopyDiscountSchemaBreaksRequest;
import de.metas.pricing.conditions.CopyDiscountSchemaBreaksRequest.Direction;
import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.pricing.conditions.service.IPricingConditionsRepository;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
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

public class M_DiscountSchemaBreak_CopyToSelectedSchema_Product extends JavaProcess implements IProcessPrecondition
{

	private final IPricingConditionsRepository pricingConditionsRepo = Services.get(IPricingConditionsRepository.class);

	final String PARAM_M_Product_ID = I_M_Product.COLUMNNAME_M_Product_ID;

	@Param(parameterName = I_M_DiscountSchema.COLUMNNAME_M_DiscountSchema_ID, mandatory = true)
	private int p_PricingConditionsId;

	@Param(parameterName = PARAM_M_Product_ID, mandatory = true)
	private int p_ProductId;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull IProcessPreconditionsContext context)
	{
		
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}
	@Override
	protected String doIt()
	{
		final List<PO> records =  getSelectedIncludedRecords(PO.class);
		final Set<ProductId> products = new HashSet<ProductId>();
		final Set<PricingConditionsId> pricingConditions = new HashSet<PricingConditionsId>();
		
		records.forEach(record -> {
			final Optional<Integer> productId = InterfaceWrapperHelper.getValue(record, I_M_DiscountSchemaBreak.COLUMNNAME_M_Product_ID);
			if (productId.isPresent())
			{
				products.add(ProductId.ofRepoId(productId.get()));	
			}
			
			//
			final Optional<Integer> pricingConditionsId = InterfaceWrapperHelper.getValue(record, I_M_DiscountSchemaBreak.COLUMNNAME_M_DiscountSchema_ID);
			if (pricingConditionsId.isPresent())
			{
				pricingConditions.add(PricingConditionsId.ofRepoId(pricingConditionsId.get()));	
			}
			
		});
		
		final ICompositeQueryFilter<I_M_DiscountSchemaBreak> queryFilter = Services.get(IQueryBL.class)
				.createCompositeQueryFilter(I_M_DiscountSchemaBreak.class)
				.setJoinAnd()
				.addInArrayFilter(I_M_DiscountSchemaBreak.COLUMNNAME_M_DiscountSchema_ID, pricingConditions)
				.addInArrayFilter(I_M_DiscountSchemaBreak.COLUMNNAME_M_Product_ID, products);

		

		final boolean allowCopyToSameSchema = true;

		final CopyDiscountSchemaBreaksRequest request = CopyDiscountSchemaBreaksRequest.builder()
				.filter(queryFilter)
				.pricingConditionsId(PricingConditionsId.ofRepoId(p_PricingConditionsId))
				.productId(ProductId.ofRepoId(p_ProductId))
				.allowCopyToSameSchema(allowCopyToSameSchema)
				.direction(Direction.TargetSource)
				.build();
		
		pricingConditionsRepo.copyDiscountSchemaBreaksWithProductId(request);

		return MSG_OK;
	}

}
