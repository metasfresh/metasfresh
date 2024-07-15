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

import com.google.common.collect.ImmutableSet;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_ModCntr_Specific_Price;
import de.metas.contracts.modular.ModCntrSpecificPrice;
import de.metas.contracts.modular.ModCntrSpecificPriceId;
import de.metas.contracts.modular.ModularContractPriceService;
import de.metas.contracts.modular.log.ModCntrLogPriceUpdateRequest;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.workpackage.ModularContractLogHandlerRegistry;
import de.metas.i18n.AdMessageKey;
import de.metas.money.CurrencyId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import java.math.BigDecimal;
import java.util.Set;

public class ModCntr_Specific_Delete_Price_Selection extends JavaProcess implements IProcessPrecondition
{
	@NonNull private final ModularContractPriceService modularContractPriceService = SpringContextHolder.instance.getBean(ModularContractPriceService.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ModularContractLogService contractLogService = SpringContextHolder.instance.getBean(ModularContractLogService.class);
	@NonNull private final ModularContractLogHandlerRegistry logHandlerRegistry = SpringContextHolder.instance.getBean(ModularContractLogHandlerRegistry.class);

	private static final AdMessageKey ERROR_MSG_NO_FALLBACK_PRICE = AdMessageKey.of("Msg_No_Fallback_Price");

	@Param(parameterName = "M_Product_ID", mandatory = true)
	private ProductId p_M_Product_ID;

	@Param(parameterName = "Price", mandatory = true)
	private BigDecimal p_price;

	@Param(parameterName = "MinValue", mandatory = true)
	private BigDecimal p_minValue;

	@Param(parameterName = "C_UOM_ID", mandatory = true)
	private UomId p_C_UOM_ID;

	@Param(parameterName = "C_Currency_ID", mandatory = true)
	private CurrencyId p_C_Currency_ID;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!existsAtLeastOneScalePrice(context))
		{
			return ProcessPreconditionsResolution.reject();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		retrieveContractSpecificPricesFromSelection()
				.forEach(contractPriceId ->
				{
					if (!modularContractPriceService.existsSimilarContractSpecificScalePrice(contractPriceId))
					{
						throw new AdempiereException(ERROR_MSG_NO_FALLBACK_PRICE);
					}

					final ModCntrSpecificPrice contractPrice = modularContractPriceService.getById(contractPriceId);

					// delete price
					modularContractPriceService.deleteById(contractPriceId);

					// the update price by recomputing the price for logs
					// the given price is ingonred in UserElementNumberShipmentLineLog
					contractLogService.updatePriceAndAmount(ModCntrLogPriceUpdateRequest.builder()
									.unitPrice(contractPrice.getProductPrice())
									.flatrateTermId(contractPrice.flatrateTermId())
									.modularContractModuleId(contractPrice.modularContractModuleId())
									.build(),
							logHandlerRegistry);
				});
		return MSG_OK;
	}

	private ImmutableSet<ModCntrSpecificPriceId> retrieveContractSpecificPricesFromSelection()
	{
		return queryBL.createQueryBuilder(I_ModCntr_Specific_Price.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_M_Product_ID, p_M_Product_ID)
				.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_C_UOM_ID, p_C_UOM_ID)
				.addInArrayFilter(I_ModCntr_Specific_Price.COLUMNNAME_C_Flatrate_Term_ID, getSelectedContracts(getProcessInfo().getQueryFilterOrElseFalse()))
				.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_C_Currency_ID, p_C_Currency_ID)
				.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_MinValue, p_minValue)
				.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_Price, p_price)
				.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_IsScalePrice, true)
				.create()
				.listIds(ModCntrSpecificPriceId::ofRepoId);
	}

	private Set<FlatrateTermId> getSelectedContracts(final IQueryFilter<I_C_Flatrate_Term> processFilter)
	{
		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addFilter(processFilter)
				.create()
				.listIds(FlatrateTermId::ofRepoId);
	}

	private boolean existsAtLeastOneScalePrice(final @NonNull IProcessPreconditionsContext context)
	{
		// context.getQueryFilter()

		return queryBL.createQueryBuilder(I_ModCntr_Specific_Price.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_ModCntr_Specific_Price.COLUMNNAME_C_Flatrate_Term_ID, getSelectedContracts(context.getQueryFilter(I_C_Flatrate_Term.class)))
				.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_IsScalePrice, true)
				.create()
				.count() > 0;
	}

}
