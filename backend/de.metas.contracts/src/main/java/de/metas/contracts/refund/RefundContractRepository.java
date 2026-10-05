package de.metas.contracts.refund;

import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Supplier;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.PlainContextAware;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util.ArrayKey;
import org.springframework.stereotype.Repository;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.refund.RefundContract.RefundContractBuilder;
import de.metas.document.engine.IDocument;
import de.metas.money.Money;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.Percent;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Repository
public class RefundContractRepository
{
	private static final CCache<ArrayKey, ImmutableList<FlatrateTermId>> CACHE = //
			CCache.<ArrayKey, ImmutableList<FlatrateTermId>> newCache(
					I_C_Flatrate_Term.Table_Name + "#by"
							+ I_C_Flatrate_Term.COLUMNNAME_Type_Conditions + "#"
							+ I_C_Flatrate_Term.COLUMNNAME_DocStatus + "#"
							+ I_C_Flatrate_Term.COLUMNNAME_StartDate + "#"
							+ I_C_Flatrate_Term.COLUMNNAME_EndDate + "#"
							+ I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID + "#"
							+ I_C_Flatrate_Term.COLUMNNAME_M_Product_ID,
					0,
					CCache.EXPIREMINUTES_Never);

	@VisibleForTesting
	@Getter
	private final RefundConfigRepository refundConfigRepository;

	public RefundContractRepository(@NonNull final RefundConfigRepository refundConfigRepository)
	{
		this.refundConfigRepository = refundConfigRepository;
	}

	/**
	 * @return the ids of all refund terms that match the query; a term with the queried product comes before a term without product.
	 */
	public ImmutableList<FlatrateTermId> getIdsByQuery(@NonNull final RefundContractQuery query)
	{
		final Timestamp invoicableFromTimestamp = TimeUtil.asTimestamp(query.getDate());
		final int billPartnerId = query.getBPartnerId().getRepoId();
		final int productId = query.getProductId().getRepoId();

		final ArrayKey key = ArrayKey.of(invoicableFromTimestamp, billPartnerId, productId);
		return CACHE.getOrLoad(
				key,
				() -> retrieveIdsForCache(invoicableFromTimestamp, billPartnerId, productId));
	}

	private static ImmutableList<FlatrateTermId> retrieveIdsForCache(
			@NonNull final Timestamp invoicableFromTimestamp,
			final int billPartnerId,
			final int productId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Flatrate_Term.class, PlainContextAware.newOutOfTrx())
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Type_Conditions, X_C_Flatrate_Term.TYPE_CONDITIONS_Refund)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_DocStatus, X_C_Flatrate_Term.DOCSTATUS_Completed)
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_StartDate, Operator.LESS_OR_EQUAL, invoicableFromTimestamp)
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, Operator.GREATER_OR_EQUAL, invoicableFromTimestamp)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, billPartnerId)
				.addInArrayFilter(I_C_Flatrate_Term.COLUMNNAME_M_Product_ID, null, productId)
				.orderBy()
				.addColumn(I_C_Flatrate_Term.COLUMNNAME_M_Product_ID, IQueryOrderBy.Direction.Descending, IQueryOrderBy.Nulls.Last)
				.addColumn(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID)
				.endOrderBy()
				.create()
				.listIds(FlatrateTermId::ofRepoId);
	}

	/**
	 * @return all refund contracts that match the query. They are additive: every one of them applies.
	 *         A contract whose configs have a product category base only matches a product of that category or of one of its sub-categories.
	 *         A config with both a product and a category requires both: the term's product is the config's product, and the product has to be in the category.
	 */
	public ImmutableList<RefundContract> getByQuery(@NonNull final RefundContractQuery query)
	{
		// only needed (and looked up) if a contract has a category base
		final Supplier<ImmutableSet<ProductCategoryId>> productCategoryIdAndAncestors = Suppliers.memoize(() -> retrieveProductCategoryIdAndAncestors(query.getProductId()));

		return getIdsByQuery(query)
				.stream()
				.map(this::getById)
				.filter(contract -> contract.getRefundConfigs().stream().anyMatch(config -> isInBase(config, productCategoryIdAndAncestors)))
				.collect(ImmutableList.toImmutableList());
	}

	private static ImmutableSet<ProductCategoryId> retrieveProductCategoryIdAndAncestors(@NonNull final ProductId productId)
	{
		final IProductDAO productDAO = Services.get(IProductDAO.class);
		final ProductCategoryId productCategoryId = productDAO.retrieveProductCategoryByProductId(productId);
		return productCategoryId != null
				? productDAO.getProductCategoryIdAndAncestors(productCategoryId)
				: ImmutableSet.of();
	}

	private static boolean isInBase(@NonNull final RefundConfig config, @NonNull final Supplier<ImmutableSet<ProductCategoryId>> productCategoryIdAndAncestors)
	{
		return config.getProductCategoryId() == null || productCategoryIdAndAncestors.get().contains(config.getProductCategoryId());
	}

	public RefundContract getById(@NonNull final FlatrateTermId flatrateTermId)
	{
		final I_C_Flatrate_Term contractRecord = loadOutOfTrx(flatrateTermId.getRepoId(), I_C_Flatrate_Term.class);

		return ofRecord(contractRecord);
	}

	public RefundContract ofRecord(@NonNull final I_C_Flatrate_Term contractRecord)
	{
		final ConditionsId conditionsId = ConditionsId.ofRepoId(contractRecord.getC_Flatrate_Conditions_ID());

		final ProductId productId = ProductId.ofRepoIdOrNull(contractRecord.getM_Product_ID());

		final RefundConfigQuery query = RefundConfigQuery.builder()
				.conditionsId(conditionsId)
				.productId(productId)
				.build();

		final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoId(contractRecord.getC_Flatrate_Term_ID());
		final List<RefundConfig> refundConfigs = refundConfigRepository.getByQuery(query);

		Check.assumeNotEmpty(refundConfigs, // otherwise we should not have been called!
				"C_Flatrate_Conditions_ID={} needs to have at least one config for M_Product_ID={}; C_Flatrate_Term={}",
				contractRecord.getC_Flatrate_Conditions_ID(), contractRecord.getM_Product_ID(), contractRecord);

		final RefundContractBuilder contractBuilder = RefundContract
				.builder()
				.id(flatrateTermId)
				.bPartnerId(BPartnerId.ofRepoId(contractRecord.getBill_BPartner_ID()))
				.startDate(TimeUtil.asLocalDate(contractRecord.getStartDate()))
				.endDate(TimeUtil.asLocalDate(contractRecord.getEndDate()));

		final boolean hasZeroQtyConfig = refundConfigs.stream().anyMatch(config -> config.getMinQty().signum() <= 0);
		if (!hasZeroQtyConfig)
		{
			final RefundConfig template = refundConfigs.get(0);

			final RefundConfig zeroConfig = template
					.toBuilder()
					.id(null)
					.minQty(ZERO)
					.percent(Percent.ZERO)
					.amount(Money.toZeroOrNull(template.getAmount()))
					.build();
			contractBuilder.refundConfig(zeroConfig);
		}
		contractBuilder.refundConfigs(refundConfigs);

		return contractBuilder.build();
	}

	public RefundContract save(@NonNull final RefundContract contract)
	{
		final I_C_Flatrate_Term contractRecord;
		if (contract.getId() == null)
		{
			contractRecord = newInstance(I_C_Flatrate_Term.class);
		}
		else
		{
			contractRecord = load(contract.getId().getRepoId(), I_C_Flatrate_Term.class);
		}

		// contractRecord.setM_Product_ID(contract.getProductId().getRepoId());

		contractRecord.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Refund);
		contractRecord.setDocStatus(IDocument.STATUS_Completed);
		contractRecord.setStartDate(TimeUtil.asTimestamp(contract.getStartDate()));
		contractRecord.setEndDate(TimeUtil.asTimestamp(contract.getEndDate()));
		contractRecord.setBill_BPartner_ID(contract.getBPartnerId().getRepoId());
		final List<RefundConfig> refundConfigs = contract.getRefundConfigs();

		final ConditionsId conditionsId = CollectionUtils.extractSingleElement(refundConfigs, RefundConfig::getConditionsId);
		contractRecord.setC_Flatrate_Conditions_ID(conditionsId.getRepoId());

		final List<RefundConfig> savedConfigs = refundConfigRepository.saveAll(refundConfigs);

		saveRecord(contractRecord);

		return contract
				.toBuilder()
				.id(FlatrateTermId.ofRepoId(contractRecord.getC_Flatrate_Term_ID()))
				.clearRefundConfigs()
				.refundConfigs(savedConfigs)
				.build();
	}
}
