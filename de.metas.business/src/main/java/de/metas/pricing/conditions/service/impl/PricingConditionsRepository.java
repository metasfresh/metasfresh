package de.metas.pricing.conditions.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.copy;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

import java.math.BigDecimal;
import java.util.Collection;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.compiere.model.X_M_DiscountSchemaBreak;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.lang.Percent;
import de.metas.pricing.conditions.PriceOverride;
import de.metas.pricing.conditions.PriceOverrideType;
import de.metas.pricing.conditions.PricingConditions;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreakId;
import de.metas.pricing.conditions.PricingConditionsBreakMatchCriteria;
import de.metas.pricing.conditions.PricingConditionsDiscountType;
import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.pricing.conditions.service.IPricingConditionsRepository;
import de.metas.pricing.conditions.service.PricingConditionsBreakChangeRequest;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import lombok.NonNull;

public class PricingConditionsRepository implements IPricingConditionsRepository
{
	private final CCache<PricingConditionsId, PricingConditions> //
	pricingConditionsById = CCache.<PricingConditionsId, PricingConditions> newCache(I_M_DiscountSchema.Table_Name, 10, CCache.EXPIREMINUTES_Never)
			.addResetForTableName(I_M_DiscountSchemaBreak.Table_Name);

	@Override
	public PricingConditions getPricingConditionsById(final int discountSchemaId)
	{
		return getPricingConditionsById(PricingConditionsId.ofDiscountSchemaId(discountSchemaId));
	}

	@Override
	public PricingConditions getPricingConditionsById(@NonNull final PricingConditionsId pricingConditionsId)
	{
		return pricingConditionsById.getOrLoad(pricingConditionsId, this::retrievePricingConditionsById);
	}

	@Override
	public Collection<PricingConditions> getPricingConditionsByIds(final Collection<Integer> discountSchemaIds)
	{
		final Collection<PricingConditionsId> ids = PricingConditionsId.ofDiscountSchemaIds(discountSchemaIds);
		return pricingConditionsById.getAllOrLoad(ids, this::retrievePricingConditionsByIds);
	}

	@VisibleForTesting
	PricingConditions retrievePricingConditionsById(@NonNull final PricingConditionsId id)
	{
		final int discountSchemaId = id.getDiscountSchemaId();
		final I_M_DiscountSchema discountSchemaRecord = loadOutOfTrx(discountSchemaId, I_M_DiscountSchema.class);
		final List<I_M_DiscountSchemaBreak> schemaBreakRecords = streamSchemaBreakRecords(Env.getCtx(), ImmutableList.of(discountSchemaId), ITrx.TRXNAME_None)
				.collect(ImmutableList.toImmutableList());
		return toPricingConditions(discountSchemaRecord, schemaBreakRecords);
	}

	private Map<PricingConditionsId, PricingConditions> retrievePricingConditionsByIds(final Collection<PricingConditionsId> ids)
	{
		if (ids.isEmpty())
		{
			return ImmutableMap.of();
		}

		final Set<Integer> discountSchemaIds = PricingConditionsId.toDiscountSchemaIds(ids);

		final ListMultimap<Integer, I_M_DiscountSchemaBreak> schemaBreakRecords = streamSchemaBreakRecords(Env.getCtx(), discountSchemaIds, ITrx.TRXNAME_None)
				.collect(GuavaCollectors.toImmutableListMultimap(I_M_DiscountSchemaBreak::getM_DiscountSchema_ID));

		return Services.get(IQueryBL.class).createQueryBuilderOutOfTrx(I_M_DiscountSchema.class)
				.addInArrayFilter(I_M_DiscountSchema.COLUMNNAME_M_DiscountSchema_ID, discountSchemaIds)
				.create()
				.stream()
				.map(discountSchema -> toPricingConditions(discountSchema, schemaBreakRecords.get(discountSchema.getM_DiscountSchema_ID())))
				.collect(GuavaCollectors.toImmutableMapByKey(PricingConditions::getId));
	}

	private static PricingConditions toPricingConditions(final I_M_DiscountSchema discountSchemaRecord, final List<I_M_DiscountSchemaBreak> schemaBreakRecords)
	{
		final PricingConditionsDiscountType discountType = PricingConditionsDiscountType.forCode(discountSchemaRecord.getDiscountType());
		final List<PricingConditionsBreak> breaks;
		if (discountType == PricingConditionsDiscountType.BREAKS)
		{
			breaks = schemaBreakRecords.stream()
					.filter(I_M_DiscountSchemaBreak::isActive)
					.filter(I_M_DiscountSchemaBreak::isValid)
					.map(schemaBreakRecord -> toPricingConditionsBreak(schemaBreakRecord))
					.collect(ImmutableList.toImmutableList());
		}
		else
		{
			breaks = ImmutableList.of();
		}

		return PricingConditions.builder()
				.id(PricingConditionsId.ofDiscountSchemaId(discountSchemaRecord.getM_DiscountSchema_ID()))
				.discountType(discountType)
				.bpartnerFlatDiscount(discountSchemaRecord.isBPartnerFlatDiscount())
				.flatDiscount(Percent.of(discountSchemaRecord.getFlatDiscount()))
				.quantityBased(discountSchemaRecord.isQuantityBased())
				.breaks(breaks)
				.build();
	}

	public static PricingConditionsBreak toPricingConditionsBreak(final I_M_DiscountSchemaBreak schemaBreakRecord)
	{
		return PricingConditionsBreak.builder()
				.id(PricingConditionsBreakId.of(schemaBreakRecord.getM_DiscountSchema_ID(), schemaBreakRecord.getM_DiscountSchemaBreak_ID()))
				.matchCriteria(toPricingConditionsBreakMatchCriteria(schemaBreakRecord))
				//
				.priceOverride(toPriceOverride(schemaBreakRecord))
				//
				.bpartnerFlatDiscount(schemaBreakRecord.isBPartnerFlatDiscount())
				.discount(Percent.of(schemaBreakRecord.getBreakDiscount()))
				.paymentTermId(schemaBreakRecord.getC_PaymentTerm_ID())
				//
				.qualityDiscountPercentage(schemaBreakRecord.getQualityIssuePercentage())
				//
				//
				.dateCreated(TimeUtil.asLocalDateTime(schemaBreakRecord.getCreated()))
				.build();
	}

	private static PricingConditionsBreakMatchCriteria toPricingConditionsBreakMatchCriteria(final I_M_DiscountSchemaBreak schemaBreakRecord)
	{
		return PricingConditionsBreakMatchCriteria.builder()
				.breakValue(schemaBreakRecord.getBreakValue())
				.productId(ProductId.ofRepoIdOrNull(schemaBreakRecord.getM_Product_ID()))
				.productCategoryId(ProductCategoryId.ofRepoIdOrNull(schemaBreakRecord.getM_Product_Category_ID()))
				.attributeValueId(schemaBreakRecord.getM_AttributeValue_ID())
				.build();
	}

	private static PriceOverride toPriceOverride(final I_M_DiscountSchemaBreak discountSchemaBreakRecord)
	{
		final boolean isPriceOverride = discountSchemaBreakRecord.isPriceOverride();
		final String priceBase = discountSchemaBreakRecord.getPriceBase();
		if (!isPriceOverride)
		{
			return PriceOverride.none();
		}
		else if (X_M_DiscountSchemaBreak.PRICEBASE_PricingSystem.equals(priceBase))
		{
			return PriceOverride.basePricingSystem(discountSchemaBreakRecord.getBase_PricingSystem_ID(), discountSchemaBreakRecord.getStd_AddAmt());
		}
		else if (X_M_DiscountSchemaBreak.PRICEBASE_Fixed.equals(priceBase))
		{
			return PriceOverride.fixedPrice(discountSchemaBreakRecord.getPriceStd());
		}
		else
		{
			throw new AdempiereException("Unknown PriceBase: " + priceBase);
		}
	}

	@VisibleForTesting
	/* package */ Stream<I_M_DiscountSchemaBreak> streamSchemaBreakRecords(final Properties ctx, final Collection<Integer> discountSchemaIds, final String trxName)
	{
		if (discountSchemaIds.isEmpty())
		{
			return Stream.empty();
		}

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_DiscountSchemaBreak.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_DiscountSchemaBreak.COLUMNNAME_M_DiscountSchema_ID, discountSchemaIds)
				.orderBy(I_M_DiscountSchemaBreak.COLUMNNAME_M_DiscountSchema_ID)
				.orderBy(I_M_DiscountSchemaBreak.COLUMNNAME_SeqNo)
				.orderBy(I_M_DiscountSchemaBreak.COLUMNNAME_M_DiscountSchemaBreak_ID)
				.create()
				.stream();
	}

	@VisibleForTesting
	/* package */ List<I_M_DiscountSchemaLine> retrieveLines(@CacheCtx final Properties ctx, final int discountSchemaId, @CacheTrx final String trxName)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_DiscountSchemaLine.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_DiscountSchemaLine.COLUMNNAME_M_DiscountSchema_ID, discountSchemaId)
				.orderBy(I_M_DiscountSchemaLine.COLUMNNAME_SeqNo)
				.orderBy(I_M_DiscountSchemaLine.COLUMNNAME_M_DiscountSchemaLine_ID)
				.create()
				.listImmutable(I_M_DiscountSchemaLine.class);
	}

	@Override
	public int resequence(final int discountSchemaId)
	{
		final int countLines = resequenceLines(discountSchemaId);
		final int countBreaks = resequenceBreaks(discountSchemaId);
		return countLines + countBreaks;
	}

	private int resequenceLines(final int discountSchemaId)
	{
		int countUpdated = 0;

		final List<I_M_DiscountSchemaLine> lines = retrieveLines(Env.getCtx(), discountSchemaId, ITrx.TRXNAME_ThreadInherited);
		int i = 0;
		for (final I_M_DiscountSchemaLine currentLine : lines)
		{
			final int currentSeq = (i + 1) * 10;
			if (currentSeq != currentLine.getSeqNo())
			{
				currentLine.setSeqNo(currentSeq);
				InterfaceWrapperHelper.save(currentLine);
				countUpdated++;
			}
			i++;
		}

		return countUpdated;
	}

	private int resequenceBreaks(final int discountSchemaId)
	{
		int countUpdated = 0;

		final List<I_M_DiscountSchemaBreak> breaks = streamSchemaBreakRecords(Env.getCtx(), ImmutableList.of(discountSchemaId), ITrx.TRXNAME_ThreadInherited)
				.collect(ImmutableList.toImmutableList());
		int i = 0;
		for (final I_M_DiscountSchemaBreak br : breaks)
		{
			final int currentSeq = (i + 1) * 10;
			if (currentSeq != br.getSeqNo())
			{
				br.setSeqNo(currentSeq);
				InterfaceWrapperHelper.save(br);
				countUpdated++;
			}
			i++;
		}

		return countUpdated;
	}

	@Override
	public PricingConditionsBreak changePricingConditionsBreak(@NonNull final PricingConditionsBreakChangeRequest request)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		return trxManager.call(ITrx.TRXNAME_ThreadInherited, () -> changePricingConditionsBreak0(request));
	}

	private PricingConditionsBreak changePricingConditionsBreak0(@NonNull final PricingConditionsBreakChangeRequest request)
	{
		final PricingConditionsId pricingConditionsId = request.getPricingConditionsId();

		//
		// Load/Create discount schema record
		final I_M_DiscountSchemaBreak schemaBreak;
		final PricingConditionsBreakId pricingConditionsBreakId = request.getPricingConditionsBreakId();
		if (pricingConditionsBreakId != null)
		{
			schemaBreak = load(pricingConditionsBreakId.getDiscountSchemaBreakId(), I_M_DiscountSchemaBreak.class);
			if (!pricingConditionsBreakId.matchingDiscountSchemaId(schemaBreak.getM_DiscountSchema_ID()))
			{
				throw new AdempiereException("" + request + " and " + schemaBreak + " does not have the same discount schema");
			}
		}
		else
		{
			if(pricingConditionsId == null)
			{
				throw new AdempiereException("Cannot create new break because no pricingConditionsId found: " + request);
			}
			final int discountSchemaId = pricingConditionsId.getDiscountSchemaId();

			schemaBreak = newInstance(I_M_DiscountSchemaBreak.class);
			schemaBreak.setM_DiscountSchema_ID(discountSchemaId);
			schemaBreak.setSeqNo(retrieveNextSeqNo(discountSchemaId));
			schemaBreak.setBreakValue(BigDecimal.ZERO);
		}

		//
		// Update
		updateSchemaBreakRecordFromRecordFromMatchCriteria(schemaBreak, request.getMatchCriteria());
		updateSchemaBreakRecordFromSourceScheamaBreakRecord(schemaBreak, request.getUpdateFromPricingConditionsBreakId());
		updateSchemaBreakRecordFromPrice(schemaBreak, request.getPrice());
		if (request.getDiscount() != null)
		{
			schemaBreak.setBreakDiscount(request.getDiscount().getValueAsBigDecimal());
		}
		if (request.getPaymentTermId() != null)
		{
			schemaBreak.setC_PaymentTerm_ID(request.getPaymentTermId());
		}

		//
		// Save & validate
		InterfaceWrapperHelper.save(schemaBreak);
		if (!schemaBreak.isValid())
		{
			throw new AdempiereException(schemaBreak.getNotValidReason());
		}
		return toPricingConditionsBreak(schemaBreak);
	}

	private void updateSchemaBreakRecordFromRecordFromMatchCriteria(final I_M_DiscountSchemaBreak schemaBreak, final PricingConditionsBreakMatchCriteria matchCriteria)
	{
		if (matchCriteria == null)
		{
			return;
		}

		schemaBreak.setBreakValue(matchCriteria.getBreakValue());
		schemaBreak.setM_Product_ID(ProductId.toRepoId(matchCriteria.getProductId()));
		schemaBreak.setM_Product_Category_ID(ProductCategoryId.toRepoId(matchCriteria.getProductCategoryId()));
		schemaBreak.setM_AttributeValue_ID(matchCriteria.getAttributeValueId());
	}

	private void updateSchemaBreakRecordFromSourceScheamaBreakRecord(final I_M_DiscountSchemaBreak schemaBreak, final PricingConditionsBreakId sourcePricingConditionsBreakId)
	{
		if (sourcePricingConditionsBreakId == null)
		{
			return;
		}

		final I_M_DiscountSchemaBreak fromSchemaBreak = load(sourcePricingConditionsBreakId.getDiscountSchemaBreakId(), I_M_DiscountSchemaBreak.class);
		copy().setFrom(fromSchemaBreak)
				.setTo(schemaBreak)
				.addTargetColumnNameToSkip(I_M_DiscountSchemaBreak.COLUMNNAME_AD_Org_ID)
				.addTargetColumnNameToSkip(I_M_DiscountSchemaBreak.COLUMNNAME_M_DiscountSchema_ID)
				.copy();
	}

	private void updateSchemaBreakRecordFromPrice(final I_M_DiscountSchemaBreak schemaBreak, final PriceOverride price)
	{
		if (price == null)
		{
			// don't change the prices
			return;
		}

		final PriceOverrideType priceType = price.getType();
		if (priceType == PriceOverrideType.NONE)
		{
			schemaBreak.setIsPriceOverride(false);
			schemaBreak.setPriceBase(null);
			schemaBreak.setBase_PricingSystem_ID(-1);
			schemaBreak.setStd_AddAmt(BigDecimal.ZERO);
			schemaBreak.setPriceStd(BigDecimal.ZERO);
		}
		else if (priceType == PriceOverrideType.BASE_PRICING_SYSTEM)
		{
			schemaBreak.setIsPriceOverride(true);
			schemaBreak.setPriceBase(X_M_DiscountSchemaBreak.PRICEBASE_PricingSystem);
			schemaBreak.setBase_PricingSystem_ID(price.getBasePricingSystemId());
			schemaBreak.setStd_AddAmt(price.getBasePriceAddAmt());
			schemaBreak.setPriceStd(BigDecimal.ZERO);
		}
		else if (priceType == PriceOverrideType.FIXED_PRICE)
		{
			schemaBreak.setIsPriceOverride(true);
			schemaBreak.setPriceBase(X_M_DiscountSchemaBreak.PRICEBASE_Fixed);
			schemaBreak.setBase_PricingSystem_ID(-1);
			schemaBreak.setStd_AddAmt(BigDecimal.ZERO);
			schemaBreak.setPriceStd(price.getFixedPrice());
		}
		else
		{
			throw new AdempiereException("Unknown price override: " + priceType);
		}
	}

	private int retrieveNextSeqNo(final int discountSchemaId)
	{
		final int lastSeqNo = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_DiscountSchemaBreak.class)
				.addEqualsFilter(I_M_DiscountSchemaBreak.COLUMN_M_DiscountSchema_ID, discountSchemaId)
				.create()
				.maxInt(I_M_DiscountSchemaBreak.COLUMNNAME_SeqNo);

		final int nextSeqNo = lastSeqNo / 10 * 10 + 10;
		return nextSeqNo;
	}
}
