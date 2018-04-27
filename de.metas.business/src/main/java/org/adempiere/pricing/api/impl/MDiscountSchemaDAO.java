package org.adempiere.pricing.api.impl;

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
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IMDiscountSchemaDAO;
import org.adempiere.pricing.api.PricingConditions;
import org.adempiere.pricing.api.PricingConditionsBreak;
import org.adempiere.pricing.api.PricingConditionsBreak.PriceOverrideType;
import org.adempiere.pricing.api.PricingConditionsBreakChangeRequest;
import org.adempiere.pricing.api.PricingConditionsBreakMatchCriteria;
import org.adempiere.pricing.api.PricingConditionsDiscountType;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.compiere.model.X_M_DiscountSchemaBreak;
import org.compiere.util.CCache;
import org.compiere.util.Env;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import lombok.NonNull;

public class MDiscountSchemaDAO implements IMDiscountSchemaDAO
{
	private final CCache<Integer, PricingConditions> pricingConditionsById = CCache.<Integer, PricingConditions> newCache(I_M_DiscountSchema.Table_Name, 10, CCache.EXPIREMINUTES_Never)
			.addResetForTableName(I_M_DiscountSchemaBreak.Table_Name);

	@Override
	public PricingConditions getPricingConditionsById(final int discountSchemaId)
	{
		return pricingConditionsById.getOrLoad(discountSchemaId, () -> retrievePricingConditionsById(discountSchemaId));
	}

	@Override
	public Collection<PricingConditions> getPricingConditionsByIds(final Collection<Integer> discountSchemaIds)
	{
		return pricingConditionsById.getAllOrLoad(discountSchemaIds, this::retrievePricingConditionsByIds);
	}

	@VisibleForTesting
	PricingConditions retrievePricingConditionsById(final int discountSchemaId)
	{
		Check.assumeGreaterThanZero(discountSchemaId, "discountSchemaId");
		final I_M_DiscountSchema discountSchemaRecord = loadOutOfTrx(discountSchemaId, I_M_DiscountSchema.class);
		final List<I_M_DiscountSchemaBreak> schemaBreakRecords = streamSchemaBreakRecords(Env.getCtx(), ImmutableList.of(discountSchemaId), ITrx.TRXNAME_None)
				.collect(ImmutableList.toImmutableList());
		return toPricingConditions(discountSchemaRecord, schemaBreakRecords);
	}

	private Map<Integer, PricingConditions> retrievePricingConditionsByIds(final Collection<Integer> discountSchemaIds)
	{
		if (discountSchemaIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ListMultimap<Integer, I_M_DiscountSchemaBreak> schemaBreakRecords = streamSchemaBreakRecords(Env.getCtx(), discountSchemaIds, ITrx.TRXNAME_None)
				.collect(GuavaCollectors.toImmutableListMultimap(I_M_DiscountSchemaBreak::getM_DiscountSchema_ID));

		return Services.get(IQueryBL.class).createQueryBuilderOutOfTrx(I_M_DiscountSchema.class)
				.addInArrayFilter(I_M_DiscountSchema.COLUMNNAME_M_DiscountSchema_ID, discountSchemaIds)
				.create()
				.stream()
				.map(discountSchema -> toPricingConditions(discountSchema, schemaBreakRecords.get(discountSchema.getM_DiscountSchema_ID())))
				.collect(GuavaCollectors.toImmutableMapByKey(PricingConditions::getDiscountSchemaId));
	}

	private static PricingConditions toPricingConditions(final I_M_DiscountSchema discountSchemaRecord, final List<I_M_DiscountSchemaBreak> schemaBreakRecords)
	{
		final List<PricingConditionsBreak> breaks = schemaBreakRecords.stream()
				.filter(I_M_DiscountSchemaBreak::isActive)
				.filter(I_M_DiscountSchemaBreak::isValid)
				.map(schemaBreakRecord -> toPricingConditionsBreak(schemaBreakRecord))
				.collect(ImmutableList.toImmutableList());

		return PricingConditions.builder()
				.discountSchemaId(discountSchemaRecord.getM_DiscountSchema_ID())
				.discountType(PricingConditionsDiscountType.forCode(discountSchemaRecord.getDiscountType()))
				.bpartnerFlatDiscount(discountSchemaRecord.isBPartnerFlatDiscount())
				.flatDiscount(discountSchemaRecord.getFlatDiscount())
				.quantityBased(discountSchemaRecord.isQuantityBased())
				.breaks(breaks)
				.build();
	}

	public static PricingConditionsBreak toPricingConditionsBreak(final I_M_DiscountSchemaBreak schemaBreakRecord)
	{
		return PricingConditionsBreak.builder()
				.discountSchemaId(schemaBreakRecord.getM_DiscountSchema_ID())
				.discountSchemaBreakId(schemaBreakRecord.getM_DiscountSchemaBreak_ID())
				.matchCriteria(PricingConditionsBreakMatchCriteria.builder()
						.breakValue(schemaBreakRecord.getBreakValue())
						.productId(schemaBreakRecord.getM_Product_ID())
						.productCategoryId(schemaBreakRecord.getM_Product_Category_ID())
						.attributeValueId(schemaBreakRecord.getM_AttributeValue_ID())
						.build())
				//
				.priceOverride(toPriceOverrideType(schemaBreakRecord.isPriceOverride(), schemaBreakRecord.getPriceBase()))
				.basePricingSystemId(schemaBreakRecord.getBase_PricingSystem_ID())
				.basePriceAddAmt(schemaBreakRecord.getStd_AddAmt())
				.fixedPrice(schemaBreakRecord.getPriceStd())
				//
				.bpartnerFlatDiscount(schemaBreakRecord.isBPartnerFlatDiscount())
				.discount(schemaBreakRecord.getBreakDiscount())
				//
				.qualityDiscountPercentage(schemaBreakRecord.getQualityIssuePercentage())
				//
				.paymentTermId(schemaBreakRecord.getC_PaymentTerm_ID())
				//
				.build();
	}

	private static PriceOverrideType toPriceOverrideType(final boolean isPriceOverride, final String priceBase)
	{
		if (!isPriceOverride)
		{
			return PriceOverrideType.NONE;
		}
		else if (X_M_DiscountSchemaBreak.PRICEBASE_PricingSystem.equals(priceBase))
		{
			return PriceOverrideType.BASE_PRICING_SYSTEM;
		}
		else if (X_M_DiscountSchemaBreak.PRICEBASE_Fixed.equals(priceBase))
		{
			return PriceOverrideType.FIXED_PRICED;
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
	public int changePricingConditionsBreak(@NonNull final PricingConditionsBreakChangeRequest request)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		return trxManager.call(ITrx.TRXNAME_ThreadInherited, () -> changePricingConditionsBreak0(request));
	}

	public int changePricingConditionsBreak0(@NonNull final PricingConditionsBreakChangeRequest request)
	{
		//
		final I_M_DiscountSchemaBreak schemaBreak;
		if (request.getDiscountSchemaBreakId() > 0)
		{
			schemaBreak = load(request.getDiscountSchemaBreakId(), I_M_DiscountSchemaBreak.class);
			if (schemaBreak.getM_DiscountSchema_ID() != request.getDiscountSchemaId())
			{
				throw new AdempiereException("" + request + " and " + schemaBreak + " does not have the same discount schema");
			}
		}
		else
		{
			schemaBreak = newInstance(I_M_DiscountSchemaBreak.class);
			schemaBreak.setM_DiscountSchema_ID(request.getDiscountSchemaId());
		}

		//
		if (request.getUpdateFromDiscountSchemaBreakId() > 0)
		{
			final I_M_DiscountSchemaBreak fromSchemaBreak = load(request.getUpdateFromDiscountSchemaBreakId(), I_M_DiscountSchemaBreak.class);
			copy().setFrom(fromSchemaBreak)
					.setTo(schemaBreak)
					.addTargetColumnNameToSkip(I_M_DiscountSchemaBreak.COLUMNNAME_AD_Org_ID)
					.addTargetColumnNameToSkip(I_M_DiscountSchemaBreak.COLUMNNAME_M_DiscountSchema_ID)
					.copy();
		}

		//
		// Prices
		final PriceOverrideType priceOverride = request.getPriceOverride();
		if (priceOverride == null)
		{
			// don't change the prices
		}
		else if (priceOverride == PriceOverrideType.NONE)
		{
			schemaBreak.setIsPriceOverride(false);
			schemaBreak.setPriceBase(null);
			schemaBreak.setBase_PricingSystem_ID(-1);
			schemaBreak.setStd_AddAmt(BigDecimal.ZERO);
			schemaBreak.setPriceStd(BigDecimal.ZERO);
		}
		else if (priceOverride == PriceOverrideType.BASE_PRICING_SYSTEM)
		{
			schemaBreak.setIsPriceOverride(true);
			schemaBreak.setPriceBase(X_M_DiscountSchemaBreak.PRICEBASE_PricingSystem);
			schemaBreak.setBase_PricingSystem_ID(request.getBasePricingSystemId());
			schemaBreak.setStd_AddAmt(request.getBasePriceAddAmt());
			schemaBreak.setPriceStd(BigDecimal.ZERO);
		}
		else if (priceOverride == PriceOverrideType.FIXED_PRICED)
		{
			schemaBreak.setIsPriceOverride(true);
			schemaBreak.setPriceBase(X_M_DiscountSchemaBreak.PRICEBASE_Fixed);
			schemaBreak.setBase_PricingSystem_ID(-1);
			schemaBreak.setStd_AddAmt(BigDecimal.ZERO);
			schemaBreak.setPriceStd(request.getFixedPrice());
		}
		else
		{
			throw new AdempiereException("Unknown price override: " + priceOverride);
		}

		//
		// Discount
		if (request.getDiscount() != null)
		{
			schemaBreak.setBreakDiscount(request.getDiscount());
		}
		if (request.getPaymentTermId() != null)
		{
			schemaBreak.setC_PaymentTerm_ID(request.getPaymentTermId());
		}

		InterfaceWrapperHelper.save(schemaBreak);

		if (!schemaBreak.isValid())
		{
			throw new AdempiereException(schemaBreak.getNotValidReason());
		}

		return schemaBreak.getM_DiscountSchemaBreak_ID();
	}
}
