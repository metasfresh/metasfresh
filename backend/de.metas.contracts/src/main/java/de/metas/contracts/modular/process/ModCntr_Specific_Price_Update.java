/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular.process;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_ModCntr_Specific_Price;
import de.metas.contracts.modular.log.ModCntrLogPriceUpdateRequest;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.settings.ModularContractModuleId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.uom.UomId;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class ModCntr_Specific_Price_Update extends JavaProcess implements IProcessPrecondition
{
	private final ModularContractLogService contractLogService = SpringContextHolder.instance.getBean(ModularContractLogService.class);

	@Param(parameterName = "Price")
	private BigDecimal p_price;

	@Param(parameterName = "C_UOM_ID")
	private int p_C_UOM_ID;

	@Param(parameterName = "C_Currency_ID")
	private int p_C_Currency_ID;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final I_ModCntr_Specific_Price specificPrice = getProcessInfo().getRecord(I_ModCntr_Specific_Price.class);
		specificPrice.setPrice(p_price);
		specificPrice.setC_UOM_ID(p_C_UOM_ID);
		specificPrice.setC_Currency_ID(p_C_Currency_ID);
		saveRecord(specificPrice);

		updateModCntrLogPrices(specificPrice);

		return MSG_OK;
	}

	private void updateModCntrLogPrices(final I_ModCntr_Specific_Price specificPrice)
	{
		final ProductPrice productUnitPrice = ProductPrice.builder()
				.productId(ProductId.ofRepoId(specificPrice.getM_Product_ID()))
				.money(Money.of(specificPrice.getPrice(), CurrencyId.ofRepoId(specificPrice.getC_Currency_ID())))
				.uomId(UomId.ofRepoId(specificPrice.getC_UOM_ID()))
				.build();
		contractLogService.updatePriceAndAmount(ModCntrLogPriceUpdateRequest.builder()
				.unitPrice(productUnitPrice)
				.flatrateTermId(FlatrateTermId.ofRepoId(specificPrice.getC_Flatrate_Term_ID()))
				.modularContractModuleId(ModularContractModuleId.ofRepoId(specificPrice.getModCntr_Module_ID()))
				.build());
	}
}
