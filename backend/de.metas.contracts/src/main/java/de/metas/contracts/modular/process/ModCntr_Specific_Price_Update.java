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

import de.metas.contracts.modular.ModCntrSpecificPrice;
import de.metas.contracts.modular.ModCntrSpecificPriceId;
import de.metas.contracts.modular.ModularContractPriceService;
import de.metas.contracts.modular.log.ModCntrLogPriceUpdateRequest;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.workpackage.ModularContractLogHandlerRegistry;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.uom.UomId;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

import java.math.BigDecimal;

public class ModCntr_Specific_Price_Update extends JavaProcess implements IProcessPrecondition
{
	@NonNull private final ModularContractLogService contractLogService = SpringContextHolder.instance.getBean(ModularContractLogService.class);
	@NonNull private final ModularContractPriceService modularContractPriceService = SpringContextHolder.instance.getBean(ModularContractPriceService.class);
	@NonNull private final ModularContractLogHandlerRegistry logHandlerRegistry = SpringContextHolder.instance.getBean(ModularContractLogHandlerRegistry.class);

	@Param(parameterName = "Price")
	private BigDecimal p_price;

	@Param(parameterName = "MinValue")
	private BigDecimal p_minValue;

	@Param(parameterName = "AsNewPrice")
	private Boolean p_asNewPrice;

	@Param(parameterName = "C_UOM_ID")
	private UomId p_C_UOM_ID;

	@Param(parameterName = "C_Currency_ID")
	private CurrencyId p_C_Currency_ID;

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
		final ModCntrSpecificPriceId contractPriceId = ModCntrSpecificPriceId.ofRepoId(getRecord_ID());

		ModCntrSpecificPrice newContractPrice;

		if (p_asNewPrice)
		{
			newContractPrice = modularContractPriceService.cloneById(contractPriceId, contractPrice -> contractPrice.toBuilder()
					.amount(Money.of(p_price, p_C_Currency_ID))
					.uomId(p_C_UOM_ID)
					.minValue(p_minValue)
					.build());
		}
		else
		{
			newContractPrice = modularContractPriceService.updateById(contractPriceId, contractPrice -> contractPrice.toBuilder()
					.amount(Money.of(p_price, p_C_Currency_ID))
					.uomId(p_C_UOM_ID)
					.minValue(p_minValue)
					.build());
		}

		contractLogService.updatePriceAndAmount(ModCntrLogPriceUpdateRequest.builder()
						.unitPrice(newContractPrice.getProductPrice())
						.flatrateTermId(newContractPrice.flatrateTermId())
						.modularContractModuleId(newContractPrice.modularContractModuleId())
						.build(),
				logHandlerRegistry);

		return MSG_OK;
	}

}
