/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_ModCntr_Specific_Price;
import de.metas.contracts.modular.settings.ModularContractModuleId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ModularContractPriceRepository
{
	final private IQueryBL queryBL = Services.get(IQueryBL.class);

	@Nullable
	public ModCntrSpecificPrice retrievePriceForProductAndContract(@NonNull final ModularContractModuleId modularContractModuleId, @NonNull final FlatrateTermId flatrateTermId)
	{
		return queryBL.createQueryBuilder(I_ModCntr_Specific_Price.class)
				.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_C_Flatrate_Term_ID, flatrateTermId)
				.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_ModCntr_Module_ID, modularContractModuleId)
				.addOnlyActiveRecordsFilter()
				.create()

				.firstOnlyOptional(I_ModCntr_Specific_Price.class)
				.map(ModularContractPriceRepository::fromDB)
				.orElse(null);
	}

	public boolean isSpecificPricesExistforFlatrateTermId(@NonNull final FlatrateTermId flatrateTermId)
	{
		return queryBL.createQueryBuilder(I_ModCntr_Specific_Price.class)
				.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_C_Flatrate_Term_ID, flatrateTermId)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
	}

	public void save(@NonNull final ModCntrSpecificPrice modCntrSpecificPrice)
	{
		final I_ModCntr_Specific_Price price = modCntrSpecificPrice.id() != null ? InterfaceWrapperHelper.load(modCntrSpecificPrice.id(), I_ModCntr_Specific_Price.class) : InterfaceWrapperHelper.newInstance(I_ModCntr_Specific_Price.class);
		price.setC_Flatrate_Term_ID(modCntrSpecificPrice.flatrateTermId().getRepoId());
		price.setM_Product_ID(modCntrSpecificPrice.productId().getRepoId());
		price.setC_TaxCategory_ID(modCntrSpecificPrice.taxCategoryId().getRepoId());
		price.setC_Currency_ID(modCntrSpecificPrice.amount().getCurrencyId().getRepoId());
		price.setPrice(modCntrSpecificPrice.amount().toBigDecimal());
		price.setModCntr_Module_ID(modCntrSpecificPrice.modularContractModuleId().getRepoId());
		price.setC_UOM_ID(modCntrSpecificPrice.uomId().getRepoId());

		saveRecord(price);
	}

	@NonNull
	private static ModCntrSpecificPrice fromDB(@NonNull final I_ModCntr_Specific_Price modCntrSpecificPrice)
	{
		return ModCntrSpecificPrice.builder()
				.id(ModCntrSpecificPriceId.ofRepoId(modCntrSpecificPrice.getModCntr_Specific_Price_ID()))
				.modularContractModuleId(ModularContractModuleId.ofRepoId(modCntrSpecificPrice.getModCntr_Module_ID()))
				.flatrateTermId(FlatrateTermId.ofRepoId(modCntrSpecificPrice.getC_Flatrate_Term_ID()))
				.taxCategoryId(TaxCategoryId.ofRepoId(modCntrSpecificPrice.getC_TaxCategory_ID()))
				.uomId(UomId.ofRepoId(modCntrSpecificPrice.getC_UOM_ID()))
				.productId(ProductId.ofRepoId(modCntrSpecificPrice.getM_Product_ID()))
				.amount(Money.of(modCntrSpecificPrice.getPrice(), CurrencyId.ofRepoId(modCntrSpecificPrice.getC_Currency_ID())))
				.build();
	}

}
