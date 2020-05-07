package de.metas.contracts.refund;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Repository;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.contracts.refund.RefundConfig.RefundBase;
import de.metas.contracts.refund.RefundConfig.RefundConfigBuilder;
import de.metas.contracts.refund.RefundConfig.RefundInvoiceType;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.invoice.InvoiceSchedule;
import de.metas.invoice.service.InvoiceScheduleRepository;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
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
public class RefundConfigRepository
{
	@VisibleForTesting
	@Getter
	private final InvoiceScheduleRepository invoiceScheduleRepository;

	public RefundConfigRepository(@NonNull final InvoiceScheduleRepository invoiceScheduleRepository)
	{
		this.invoiceScheduleRepository = invoiceScheduleRepository;
	}

	public boolean hasRefundConfig(@NonNull final ConditionsId conditionsId)
	{
		return createRefundConfigQuery(conditionsId)
				.anyMatch();
	}

	public List<RefundConfig> getByQuery(@NonNull final RefundConfigQuery query)
	{
		final IQueryBuilder<I_C_Flatrate_RefundConfig> builder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Flatrate_RefundConfig.class)
				.addOnlyActiveRecordsFilter();

		if (query.isOnlyIfUsedInProfitCalculation())
		{
			builder.addEqualsFilter(I_C_Flatrate_RefundConfig.COLUMN_IsUseInProfitCalculation, true);
		}

		if (query.getConditionsId() != null)
		{
			builder.addEqualsFilter(I_C_Flatrate_RefundConfig.COLUMN_C_Flatrate_Conditions_ID, query.getConditionsId());
		}

		if (query.getProductId() != null)
		{
			builder.addInArrayFilter(
					I_C_Flatrate_RefundConfig.COLUMN_M_Product_ID,
					null,
					query.getProductId());
		}

		if (query.getMinQty() != null)
		{
			builder.addCompareFilter(I_C_Flatrate_RefundConfig.COLUMN_MinQty, Operator.LESS_OR_EQUAL, query.getMinQty());
		}

		final List<I_C_Flatrate_RefundConfig> recordCandidates = builder.create().list();

		final ImmutableListMultimap<Boolean, I_C_Flatrate_RefundConfig> hasProduct2Records = Multimaps.index(recordCandidates, record -> record.getM_Product_ID() > 0);
		final ImmutableList<I_C_Flatrate_RefundConfig> recordsWithProduct = hasProduct2Records.get(true);

		if (!recordsWithProduct.isEmpty())
		{
			return processResultRecordList(query, recordsWithProduct);
		}

		final ImmutableList<I_C_Flatrate_RefundConfig> recordsWithoutProduct = hasProduct2Records.get(false);
		return processResultRecordList(query, recordsWithoutProduct);
	}

	private List<RefundConfig> processResultRecordList(
			@NonNull final RefundConfigQuery query,
			@NonNull final ImmutableList<I_C_Flatrate_RefundConfig> recordsWithProductId)
	{
		if (query.getMinQty() != null)
		{
			final Optional<I_C_Flatrate_RefundConfig> recordToReturn = recordsWithProductId
					.stream()
					.max(Comparator.comparing(I_C_Flatrate_RefundConfig::getMinQty));
			return recordToReturn.isPresent()
					? ImmutableList.of(ofRecordOrNull(recordToReturn.get()))
					: ImmutableList.of();
		}
		return recordsWithProductId.stream()
				.map(this::ofRecordOrNull)
				.collect(ImmutableList.toImmutableList());
	}

	public RefundConfig getById(@NonNull final RefundConfigId id)
	{
		final I_C_Flatrate_RefundConfig record = load(id, I_C_Flatrate_RefundConfig.class);

		return Check.assumeNotNull(
				ofRecordOrNull(record),
				"There needs to be a loadable RefundConfig for RefundConfigId={}; I_C_Flatrate_RefundConfig={}",
				id, record);
	}

	private IQuery<I_C_Flatrate_RefundConfig> createRefundConfigQuery(
			@NonNull final ConditionsId conditionsId)
	{
		return createRefundConfigQueryBuilder(conditionsId)
				.create();
	}

	private IQueryBuilder<I_C_Flatrate_RefundConfig> createRefundConfigQueryBuilder(
			@NonNull final ConditionsId conditionsId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Flatrate_RefundConfig.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_RefundConfig.COLUMN_C_Flatrate_Conditions_ID, conditionsId.getRepoId());
	}

	private RefundConfig ofRecordOrNull(@Nullable final I_C_Flatrate_RefundConfig record)
	{
		if (record == null)
		{
			return null;
		}
		return ofRecord(record);
	}

	public RefundConfig ofRecord(@NonNull final I_C_Flatrate_RefundConfig record)
	{
		final InvoiceSchedule invoiceSchedule = invoiceScheduleRepository.ofRecord(record.getC_InvoiceSchedule());

		final RefundConfigBuilder builder = RefundConfig
				.builder()
				.id(RefundConfigId.ofRepoId(record.getC_Flatrate_RefundConfig_ID()))
				.conditionsId(ConditionsId.ofRepoId(record.getC_Flatrate_Conditions_ID()))
				.refundInvoiceType(extractRefundInvoiceType(record))
				.invoiceSchedule(invoiceSchedule)
				.percent(Percent.of(record.getRefundPercent()))
				.amount(Money.ofOrNull(record.getRefundAmt(), CurrencyId.ofRepoIdOrNull(record.getC_Currency_ID())))
				.minQty(record.getMinQty())
				.refundBase(extractRefundBase(record))
				.productId(ProductId.ofRepoIdOrNull(record.getM_Product_ID()))
				.refundMode(extractRefundMode(record))
				.useInProfitCalculation(record.isUseInProfitCalculation());

		return builder.build();
	}

	private RefundInvoiceType extractRefundInvoiceType(@NonNull final I_C_Flatrate_RefundConfig record)
	{

		final String refundInvoiceType = record.getRefundInvoiceType();
		if (X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Creditmemo.equals(refundInvoiceType))
		{
			return RefundInvoiceType.CREDITMEMO;
		}
		else if (X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Invoice.equals(refundInvoiceType))
		{
			return RefundInvoiceType.INVOICE;
		}
		Check.fail(
				"The given C_Flatrate_RefundConfig has an unsupported refundInvoiceType={}; record={}",
				refundInvoiceType, record);
		return null;
	}

	private RefundMode extractRefundMode(@NonNull final I_C_Flatrate_RefundConfig record)
	{
		final String refundMode = record.getRefundMode();
		if (X_C_Flatrate_RefundConfig.REFUNDMODE_Tiered.equals(refundMode))
		{
			return RefundMode.APPLY_TO_EXCEEDING_QTY;
		}
		else if (X_C_Flatrate_RefundConfig.REFUNDMODE_Accumulated.equals(refundMode))
		{
			return RefundMode.APPLY_TO_ALL_QTIES;
		}
		Check.fail("Unsupported C_Flatrate_RefundConfig.RefundMode={}; record={}", refundMode, record);
		return null;
	}

	private RefundBase extractRefundBase(@NonNull final I_C_Flatrate_RefundConfig record)
	{
		final String refundBase = record.getRefundBase();
		if (X_C_Flatrate_RefundConfig.REFUNDBASE_Percentage.equals(refundBase))
		{
			return RefundBase.PERCENTAGE;
		}
		else if (X_C_Flatrate_RefundConfig.REFUNDBASE_Amount.equals(refundBase))
		{
			return RefundBase.AMOUNT_PER_UNIT;
		}
		Check.fail("Unsupported C_Flatrate_RefundConfig.RefundBase={}; record={}", refundBase, record);
		return null;
	}

	public RefundConfig save(@NonNull final RefundConfig refundConfig)
	{
		final I_C_Flatrate_RefundConfig configRecord;
		if (refundConfig.getId() == null)
		{
			configRecord = newInstance(I_C_Flatrate_RefundConfig.class);
		}
		else
		{
			configRecord = load(refundConfig.getId(), I_C_Flatrate_RefundConfig.class);
		}

		switch (refundConfig.getRefundBase())
		{
			case PERCENTAGE:
				configRecord.setRefundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Percentage);
				configRecord.setRefundPercent(refundConfig.getPercent().toBigDecimal());
				configRecord.setRefundAmt(null);
				break;
			case AMOUNT_PER_UNIT:
				configRecord.setRefundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Amount);
				configRecord.setRefundAmt(refundConfig.getAmount().toBigDecimal());
				configRecord.setC_Currency_ID(refundConfig.getAmount().getCurrencyId().getRepoId());
				configRecord.setRefundPercent(null);
				break;
			default:
				Check.fail("Unexpected refundbase={}", refundConfig.getRefundBase());
				break;
		}

		final InvoiceSchedule savedInvoiceSchedule = invoiceScheduleRepository.save(refundConfig.getInvoiceSchedule());
		configRecord.setC_InvoiceSchedule_ID(savedInvoiceSchedule.getId().getRepoId());

		configRecord.setC_Flatrate_Conditions_ID(refundConfig.getConditionsId().getRepoId());
		configRecord.setMinQty(refundConfig.getMinQty());

		configRecord.setM_Product_ID(ProductId.toRepoId(refundConfig.getProductId()));

		switch (refundConfig.getRefundInvoiceType())
		{
			case CREDITMEMO:
				configRecord.setRefundInvoiceType(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Creditmemo);
				break;
			case INVOICE:
				configRecord.setRefundInvoiceType(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Invoice);
				break;
			default:
				Check.fail("Unexpected refundInvoiceType={}", refundConfig.getRefundInvoiceType());
				break;
		}
		switch (refundConfig.getRefundMode())
		{
			case APPLY_TO_ALL_QTIES:
				configRecord.setRefundMode(X_C_Flatrate_RefundConfig.REFUNDMODE_Accumulated);
				break;
			case APPLY_TO_EXCEEDING_QTY:
				configRecord.setRefundMode(X_C_Flatrate_RefundConfig.REFUNDMODE_Tiered);
				break;
			default:
				Check.fail("Unexpected refundMode={}", refundConfig.getRefundMode());
				break;
		}
		saveRecord(configRecord);

		return refundConfig
				.toBuilder()
				.id(RefundConfigId.ofRepoId(configRecord.getC_Flatrate_RefundConfig_ID()))
				.build();
	}

	public List<RefundConfig> saveAll(@NonNull final List<RefundConfig> refundConfigs)
	{
		final ImmutableList.Builder<RefundConfig> result = ImmutableList.builder();

		for (final RefundConfig refundConfig : refundConfigs)
		{
			result.add(save(refundConfig));
		}
		return result.build();
	}
}
