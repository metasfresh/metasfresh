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
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.model.I_ModCntr_Specific_Price;
import de.metas.contracts.model.I_ModCntr_Type;
import de.metas.contracts.modular.computing.purchasecontract.averageonshippedqty.ContractSpecificScalePriceRequest;
import de.metas.contracts.modular.settings.ModularContractModuleId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.deleteRecord;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ModularContractPriceRepository
{
	final private IQueryBL queryBL = Services.get(IQueryBL.class);

	public ModCntrSpecificPrice getById(@NonNull final ModCntrSpecificPriceId id)
	{
		final I_ModCntr_Specific_Price record = getRecordById(id);
		return fromRecord(record);
	}

	private static I_ModCntr_Specific_Price getRecordById(final @NotNull ModCntrSpecificPriceId id)
	{
		return InterfaceWrapperHelper.load(id, I_ModCntr_Specific_Price.class);
	}

	@NonNull
	public ModCntrSpecificPrice retrievePriceForProductAndContract(@NonNull final ContractSpecificPriceRequest contractSpecificPriceRequest)
	{
		return retrieveOptionalPriceForProductAndContract(contractSpecificPriceRequest)
				.orElseThrow(() -> new AdempiereException("No Price found for Product and Contract !")
						.appendParametersToMessage()
						.setParameter("ModularContractModuleId", contractSpecificPriceRequest.getModularContractModuleId().getRepoId())
						.setParameter("ContractId", contractSpecificPriceRequest.getFlatrateTermId().getRepoId()));
	}

	public boolean isSpecificPricesExistForFlatrateTermId(@NonNull final FlatrateTermId flatrateTermId)
	{
		return queryBL.createQueryBuilder(I_ModCntr_Specific_Price.class)
				.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_C_Flatrate_Term_ID, flatrateTermId)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
	}

	public void saveAll(@NonNull final List<ModCntrSpecificPrice> modCntrSpecificPrices)
	{
		modCntrSpecificPrices.forEach(this::save);
	}

	public void save(@NonNull final ModCntrSpecificPrice modCntrSpecificPrice)
	{
		final I_ModCntr_Specific_Price record = modCntrSpecificPrice.id() != null
				? getRecordById(modCntrSpecificPrice.id())
				: InterfaceWrapperHelper.newInstance(I_ModCntr_Specific_Price.class);
		updateRecord(record, modCntrSpecificPrice);
		saveRecord(record);
	}

	private static void updateRecord(final I_ModCntr_Specific_Price record, final @NotNull ModCntrSpecificPrice from)
	{
		record.setC_Flatrate_Term_ID(from.flatrateTermId().getRepoId());
		record.setM_Product_ID(from.productId().getRepoId());
		record.setC_TaxCategory_ID(from.taxCategoryId().getRepoId());
		record.setC_Currency_ID(from.amount().getCurrencyId().getRepoId());
		record.setPrice(from.amount().toBigDecimal());
		record.setMinValue(from.minValue());
		record.setIsScalePrice(from.isScalePrice());
		record.setModCntr_Module_ID(from.modularContractModuleId().getRepoId());
		record.setC_UOM_ID(from.uomId().getRepoId());
		record.setSeqNo(from.seqNo().toInt());
	}

	@NonNull
	public Optional<ModCntrSpecificPrice> retrieveOptionalPriceForProductAndContract(
			@NonNull final ContractSpecificPriceRequest contractSpecificPriceRequest)
	{
		return getSpecificPriceQuery(contractSpecificPriceRequest)

				.firstOnlyOptional(I_ModCntr_Specific_Price.class)
				.map(ModularContractPriceRepository::fromRecord);
	}

	private IQuery<I_ModCntr_Specific_Price> getSpecificPriceQuery(final @NonNull ContractSpecificPriceRequest contractSpecificPriceRequest)
	{
		return queryBL.createQueryBuilder(I_ModCntr_Specific_Price.class)
				.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_C_Flatrate_Term_ID, contractSpecificPriceRequest.getFlatrateTermId())
				.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_ModCntr_Module_ID, contractSpecificPriceRequest.getModularContractModuleId())
				.addOnlyActiveRecordsFilter()
				.create();
	}

	public ModCntrSpecificPrice retrieveScalePriceForProductAndContract(@NonNull final ContractSpecificScalePriceRequest contractSpecificScalePriceRequest)
	{
		return retrieveOptionalScalePriceForProductAndContract(contractSpecificScalePriceRequest)
				.orElse(null);
	}

	@NonNull
	public Optional<ModCntrSpecificPrice> retrieveOptionalScalePriceForProductAndContract(@NonNull final ContractSpecificScalePriceRequest contractSpecificScalePriceRequest)
	{
		final ModularContractModuleId modularContractModuleId = contractSpecificScalePriceRequest.getModularContractModuleId();
		final FlatrateTermId flatrateTermId = contractSpecificScalePriceRequest.getFlatrateTermId();
		final String columnName = contractSpecificScalePriceRequest.getColumn().getColumnName();
		final BigDecimal value = contractSpecificScalePriceRequest.getValue();

		final IQuery<I_ModCntr_Type> types = queryBL.createQueryBuilder(I_ModCntr_Type.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ModCntr_Type.COLUMNNAME_ColumnName, columnName)
				.create();

		final IQuery<I_ModCntr_Module> modules = queryBL.createQueryBuilder(I_ModCntr_Module.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter()
				.matchingColumnNames(I_ModCntr_Module.COLUMNNAME_ModCntr_Type_ID, I_ModCntr_Type.COLUMNNAME_ModCntr_Type_ID)
				.subQuery(types)
				.end()
				.create();

		return queryBL.createQueryBuilder(I_ModCntr_Specific_Price.class)
				.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_C_Flatrate_Term_ID, flatrateTermId)
				.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_ModCntr_Module_ID, modularContractModuleId)
				.addEqualsFilter(I_ModCntr_Specific_Price.COLUMNNAME_IsScalePrice, true)
				.addInSubQueryFilter()
				.matchingColumnNames(I_ModCntr_Specific_Price.COLUMNNAME_ModCntr_Module_ID, I_ModCntr_Module.COLUMNNAME_ModCntr_Module_ID)
				.subQuery(modules)
				.end()
				.addCompareFilter(I_ModCntr_Specific_Price.COLUMNNAME_MinValue, CompareQueryFilter.Operator.LESS_OR_EQUAL, value)
				.addOnlyActiveRecordsFilter()
				.orderByDescending(I_ModCntr_Specific_Price.COLUMNNAME_MinValue)
				.create()
				.firstOptional(I_ModCntr_Specific_Price.class)
				.map(ModularContractPriceRepository::fromRecord);
	}

	@NonNull
	private static ModCntrSpecificPrice fromRecord(@NonNull final I_ModCntr_Specific_Price modCntrSpecificPrice)
	{
		return ModCntrSpecificPrice.builder()
				.id(ModCntrSpecificPriceId.ofRepoId(modCntrSpecificPrice.getModCntr_Specific_Price_ID()))
				.modularContractModuleId(ModularContractModuleId.ofRepoId(modCntrSpecificPrice.getModCntr_Module_ID()))
				.flatrateTermId(FlatrateTermId.ofRepoId(modCntrSpecificPrice.getC_Flatrate_Term_ID()))
				.taxCategoryId(TaxCategoryId.ofRepoId(modCntrSpecificPrice.getC_TaxCategory_ID()))
				.uomId(UomId.ofRepoId(modCntrSpecificPrice.getC_UOM_ID()))
				.minValue(modCntrSpecificPrice.getMinValue())
				.isScalePrice(modCntrSpecificPrice.isScalePrice())
				.productId(ProductId.ofRepoId(modCntrSpecificPrice.getM_Product_ID()))
				.amount(Money.of(modCntrSpecificPrice.getPrice(), CurrencyId.ofRepoId(modCntrSpecificPrice.getC_Currency_ID())))
				.seqNo(SeqNo.ofInt(modCntrSpecificPrice.getSeqNo()))
				.build();
	}

	public ModCntrSpecificPrice updateById(@NonNull final ModCntrSpecificPriceId id, @NonNull final UnaryOperator<ModCntrSpecificPrice> mapper)
	{
		final I_ModCntr_Specific_Price record = getRecordById(id);

		final ModCntrSpecificPrice price = fromRecord(record);
		final ModCntrSpecificPrice priceChanged = mapper.apply(price);
		updateRecord(record, priceChanged);
		saveRecord(record);

		return priceChanged;
	}

	public ModCntrSpecificPrice cloneById(@NonNull final ModCntrSpecificPriceId id, @NonNull final UnaryOperator<ModCntrSpecificPrice> mapper)
	{
		final I_ModCntr_Specific_Price record = getRecordById(id);
		final ModCntrSpecificPrice templatePrice = fromRecord(record);

		final ModCntrSpecificPrice newModCntrSpecificPrice = ModCntrSpecificPrice.builder()
				.flatrateTermId(templatePrice.flatrateTermId())
				.modularContractModuleId(templatePrice.modularContractModuleId())
				.taxCategoryId(templatePrice.taxCategoryId())
				.uomId(templatePrice.uomId())
				.amount(templatePrice.amount())
				.productId(templatePrice.productId())
				.seqNo(templatePrice.seqNo())
				.isScalePrice(templatePrice.isScalePrice())
				.minValue(templatePrice.minValue())
				.build();

		final ModCntrSpecificPrice newModCntrSpecificPriceChanged = mapper.apply(newModCntrSpecificPrice);
		save(newModCntrSpecificPriceChanged);

		return newModCntrSpecificPriceChanged;
	}

	public void deleteById(@NonNull final ModCntrSpecificPriceId id)
	{
		final I_ModCntr_Specific_Price record = load(id, I_ModCntr_Specific_Price.class);
		deleteRecord(record);
	}

	public Optional<TaxCategoryId> retrieveOptionalContractSpecificTaxCategory(final ContractSpecificPriceRequest contractSpecificPriceRequest)
	{
		final Set<TaxCategoryId> distinctTaxCategories = getSpecificPriceQuery(contractSpecificPriceRequest)
				.stream()
				.map(I_ModCntr_Specific_Price::getC_TaxCategory_ID)
				.map(TaxCategoryId::ofRepoId)
				.collect(Collectors.toSet());
		return distinctTaxCategories.size() == 1 ? distinctTaxCategories.stream().findFirst() : Optional.empty();
	}
}
