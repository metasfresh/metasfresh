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
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
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
import de.metas.bpartner.BPartnerId;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.lang.Percent;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.conditions.BreakValueType;
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
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;

public class PricingConditionsRepository implements IPricingConditionsRepository
{
	private final CCache<PricingConditionsId, PricingConditions> //
	pricingConditionsById = CCache
			.<PricingConditionsId, PricingConditions> newCache(
					I_M_DiscountSchema.Table_Name,
					10,
					CCache.EXPIREMINUTES_Never)
			.addResetForTableName(I_M_DiscountSchemaBreak.Table_Name);

	@Override
	public PricingConditions getPricingConditionsById(@NonNull final PricingConditionsId pricingConditionsId)
	{
		return pricingConditionsById.getOrLoad(pricingConditionsId, this::retrievePricingConditionsById);
	}

	@Override
	public Collection<PricingConditions> getPricingConditionsByIds(final Collection<PricingConditionsId> pricingConditionIds)
	{
		return pricingConditionsById.getAllOrLoad(pricingConditionIds, this::retrievePricingConditionsByIds);
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
		final BreakValueType breakValueType;
		AttributeId breakAttributeId = null;
		if (discountType == PricingConditionsDiscountType.BREAKS)
		{
			breakValueType = BreakValueType.forCode(discountSchemaRecord.getBreakValueType());
			if (breakValueType == BreakValueType.ATTRIBUTE)
			{
				breakAttributeId = AttributeId.ofRepoId(discountSchemaRecord.getBreakValue_Attribute_ID());
			}

			breaks = schemaBreakRecords.stream()
					.filter(I_M_DiscountSchemaBreak::isActive)
					.filter(I_M_DiscountSchemaBreak::isValid)
					.map(schemaBreakRecord -> toPricingConditionsBreak(schemaBreakRecord))
					.collect(ImmutableList.toImmutableList());
		}
		else
		{
			breakValueType = null;
			breaks = ImmutableList.of();
		}

		return PricingConditions.builder()
				.id(PricingConditionsId.ofDiscountSchemaId(discountSchemaRecord.getM_DiscountSchema_ID()))
				.discountType(discountType)
				.bpartnerFlatDiscount(discountSchemaRecord.isBPartnerFlatDiscount())
				.flatDiscount(Percent.of(discountSchemaRecord.getFlatDiscount()))
				.breakValueType(breakValueType)
				.breakAttributeId(breakAttributeId)
				.breaks(breaks)
				.build();
	}

	public static PricingConditionsBreak toPricingConditionsBreak(@NonNull final I_M_DiscountSchemaBreak schemaBreakRecord)
	{
		final int discountSchemaBreakId = schemaBreakRecord.getM_DiscountSchemaBreak_ID();
		final PricingConditionsBreakId id = discountSchemaBreakId > 0 ? PricingConditionsBreakId.of(schemaBreakRecord.getM_DiscountSchema_ID(), discountSchemaBreakId) : null;

		final PaymentTermId paymentTermIdOrNull = PaymentTermId.ofRepoIdOrNull(schemaBreakRecord.getC_PaymentTerm_ID());

		final Percent paymentDiscount = Percent.ofNullable(schemaBreakRecord.getPaymentDiscount());

		final PaymentTermService paymentTermService = Adempiere.getBean(PaymentTermService.class);
		final PaymentTermId derivedPaymentTermId = paymentTermService.getOrCreateDerivedPaymentTerm(
				paymentTermIdOrNull,
				paymentDiscount);

		return PricingConditionsBreak.builder()
				.id(id)
				.matchCriteria(toPricingConditionsBreakMatchCriteria(schemaBreakRecord))
				.seqNo(schemaBreakRecord.getSeqNo())
				//
				.priceOverride(toPriceOverride(schemaBreakRecord))
				//
				.bpartnerFlatDiscount(schemaBreakRecord.isBPartnerFlatDiscount())
				.discount(Percent.of(schemaBreakRecord.getBreakDiscount()))
				//
				.paymentTermIdOrNull(paymentTermIdOrNull)
				.paymentDiscountOverrideOrNull(paymentDiscount)
				.derivedPaymentTermIdOrNull(derivedPaymentTermId)
				//
				.qualityDiscountPercentage(schemaBreakRecord.getQualityIssuePercentage())
				//
				//
				.dateCreated(TimeUtil.asLocalDateTime(schemaBreakRecord.getCreated()))
				.hasChanges(false)
				.build();
	}

	@VisibleForTesting
	static PricingConditionsBreakMatchCriteria toPricingConditionsBreakMatchCriteria(final I_M_DiscountSchemaBreak schemaBreakRecord)
	{
		return PricingConditionsBreakMatchCriteria.builder()
				.breakValue(schemaBreakRecord.getBreakValue())
				.productId(ProductId.ofRepoIdOrNull(schemaBreakRecord.getM_Product_ID()))
				.productCategoryId(ProductCategoryId.ofRepoIdOrNull(schemaBreakRecord.getM_Product_Category_ID()))
				.productManufacturerId(BPartnerId.ofRepoIdOrNull(schemaBreakRecord.getManufacturer_ID()))
				.attributeValueId(AttributeValueId.ofRepoIdOrNull(schemaBreakRecord.getM_AttributeValue_ID()))
				.build();
	}

	private static PriceOverride toPriceOverride(@NonNull final I_M_DiscountSchemaBreak discountSchemaBreakRecord)
	{
		final String priceBase = discountSchemaBreakRecord.getPriceBase();

		if (Check.isEmpty(priceBase, true))
		{
			return PriceOverride.none();
		}
		else if (X_M_DiscountSchemaBreak.PRICEBASE_PricingSystem.equals(priceBase))
		{
			final PricingSystemId basePricingSystemId = PricingSystemId.ofRepoId(discountSchemaBreakRecord.getBase_PricingSystem_ID());
			final BigDecimal basePriceAddAmt = discountSchemaBreakRecord.getStd_AddAmt();

			return PriceOverride.basePricingSystem(basePricingSystemId, basePriceAddAmt);
		}
		else if (X_M_DiscountSchemaBreak.PRICEBASE_Fixed.equals(priceBase))
		{
			final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(discountSchemaBreakRecord.getC_Currency_ID());
			if (currencyId == null)
			{
				throw new AdempiereException(TranslatableStringBuilder
						.newInstance()
						.insertFirstADMessage("discountSchemaBreakRecord with M_DiscountSchemaBreak_ID={0} and M_DiscountSchema_ID={1} has PriceBase=F(ixed), but no C_Currency_ID!",
								discountSchemaBreakRecord.getM_DiscountSchemaBreak_ID(), discountSchemaBreakRecord.getM_DiscountSchema_ID())
						.build());
				// logger.warn("discountSchemaBreakRecord with M_DiscountSchemaBreak_ID={} and M_DiscountSchema_ID={} has PriceBase=F(ixed), but no C_Currency_ID! Returning PriceOverride.none()",
				// discountSchemaBreakRecord.getM_DiscountSchemaBreak_ID(), discountSchemaBreakRecord.getM_DiscountSchema_ID());
				// return PriceOverride.none();
			}
			final Money fixedPrice = Money.of(discountSchemaBreakRecord.getPriceStd(), currencyId);

			return PriceOverride.fixedPrice(fixedPrice);
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
			if (pricingConditionsId == null)
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
			schemaBreak.setBreakDiscount(request.getDiscount().getValue());
		}

		if (request.getPaymentTermId() != null)
		{
			final int paymentTermRepoId = PaymentTermId.getRepoId(request.getPaymentTermId().orElse(null));
			schemaBreak.setC_PaymentTerm_ID(paymentTermRepoId);
		}
		if (request.getPaymentDiscount() != null)
		{
			final BigDecimal paymentDiscountValue = request
					.getPaymentDiscount()
					.map(Percent::getValue)
					.orElse(null);
			schemaBreak.setPaymentDiscount(paymentDiscountValue);
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

	@VisibleForTesting
	static void updateSchemaBreakRecordFromRecordFromMatchCriteria(final I_M_DiscountSchemaBreak schemaBreak, final PricingConditionsBreakMatchCriteria matchCriteria)
	{
		if (matchCriteria == null)
		{
			return;
		}

		schemaBreak.setBreakValue(matchCriteria.getBreakValue());
		schemaBreak.setM_Product_ID(ProductId.toRepoId(matchCriteria.getProductId()));
		schemaBreak.setM_Product_Category_ID(ProductCategoryId.toRepoId(matchCriteria.getProductCategoryId()));
		schemaBreak.setManufacturer_ID(BPartnerId.toRepoIdOr(matchCriteria.getProductManufacturerId(), -1));
		schemaBreak.setM_AttributeValue_ID(AttributeValueId.toRepoId(matchCriteria.getAttributeValueId()));
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
			schemaBreak.setPriceBase(null);
			schemaBreak.setBase_PricingSystem_ID(-1);
			schemaBreak.setStd_AddAmt(BigDecimal.ZERO);

			schemaBreak.setPriceStd(null);
			schemaBreak.setC_Currency(null);
		}
		else if (priceType == PriceOverrideType.BASE_PRICING_SYSTEM)
		{
			schemaBreak.setPriceBase(X_M_DiscountSchemaBreak.PRICEBASE_PricingSystem);
			schemaBreak.setBase_PricingSystem_ID(price.getBasePricingSystemId().getRepoId());
			schemaBreak.setStd_AddAmt(price.getBasePriceAddAmt());

			schemaBreak.setPriceStd(null);
			schemaBreak.setC_Currency(null);
		}
		else if (priceType == PriceOverrideType.FIXED_PRICE)
		{
			schemaBreak.setPriceBase(X_M_DiscountSchemaBreak.PRICEBASE_Fixed);
			schemaBreak.setBase_PricingSystem_ID(-1);
			schemaBreak.setStd_AddAmt(BigDecimal.ZERO);

			final Money fixedPrice = price.getFixedPrice();
			schemaBreak.setPriceStd(fixedPrice.getValue());
			schemaBreak.setC_Currency_ID(fixedPrice.getCurrencyId().getRepoId());
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
