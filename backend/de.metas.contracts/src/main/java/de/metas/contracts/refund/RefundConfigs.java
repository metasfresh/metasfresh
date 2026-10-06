package de.metas.contracts.refund;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.i18n.AdMessageKey;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static de.metas.util.collections.CollectionUtils.extractSingleElement;
import static de.metas.util.collections.CollectionUtils.hasDifferentValues;
import static de.metas.util.collections.CollectionUtils.singleElement;

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

@UtilityClass
public class RefundConfigs
{
	private static final AdMessageKey MSG_REFUND_CONFIG_SAME_REFUND_INVOICE_TYPE = AdMessageKey.of("de.metas.constracts.refund.C_Flatrate_RefundConfig_SameRefundInvoiceType");
	private static final AdMessageKey MSG_REFUND_CONFIG_SAME_INVOICE_SCHEDULE = AdMessageKey.of("de.metas.constracts.refund.C_Flatrate_RefundConfig_SameInvoiceSchedule");
	private static final AdMessageKey MSG_REFUND_CONFIG_SAME_REFUND_MODE = AdMessageKey.of("de.metas.constracts.refund.C_Flatrate_RefundConfig_SameRefundMode");
	private static final AdMessageKey MSG_REFUND_CONFIG_SAME_REFUND_BASE = AdMessageKey.of("de.metas.constracts.refund.C_Flatrate_RefundConfig_SameRefundBase");
	static final AdMessageKey MSG_REFUND_CONFIG_SAME_BONUS_PRODUCT = AdMessageKey.of("de.metas.constracts.refund.C_Flatrate_RefundConfig_SameBonusProduct");
	static final AdMessageKey MSG_REFUND_CONFIG_SAME_BONUS_RECIPIENT = AdMessageKey.of("de.metas.constracts.refund.C_Flatrate_RefundConfig_SameBonusRecipient");
	public static final AdMessageKey MSG_REFUND_CONFIG_BONUS_PRODUCT_REQUIRED = AdMessageKey.of("de.metas.constracts.refund.C_Flatrate_RefundConfig_BonusProductRequired");

	public ImmutableList<RefundConfig> sortByMinQtyAsc(@NonNull final List<RefundConfig> refundConfigs)
	{
		// we need to look at the lowest minQty first, in order to "fill" it; only the "biggest" config is does not have the next config's minQty as ceiling
		final ImmutableList<RefundConfig> sortedConfigs = refundConfigs
				.stream()
				.sorted(Comparator.comparing(RefundConfig::getMinQty))
				.collect(ImmutableList.toImmutableList());
		return sortedConfigs;
	}

	public ImmutableList<RefundConfig> sortByMinQtyDesc(@NonNull final List<RefundConfig> refundConfigs)
	{
		final ImmutableList<RefundConfig> sortedConfigs = refundConfigs
				.stream()
				.sorted(Comparator.comparing(RefundConfig::getMinQty).reversed())
				.collect(ImmutableList.toImmutableList());
		return sortedConfigs;
	}

	public RefundConfig largestMinQty(@NonNull final List<RefundConfig> refundConfigs)
	{
		Check.assumeNotEmpty(refundConfigs, "The given refundConfigs may not be empty");

		return refundConfigs
				.stream()
				.max(Comparator.comparing(RefundConfig::getMinQty))
				.get();
	}

	public RefundConfig smallestMinQty(@NonNull final List<RefundConfig> refundConfigs)
	{
		Check.assumeNotEmpty(refundConfigs, "The given refundConfigs may not be empty");

		return refundConfigs
				.stream()
				.min(Comparator.comparing(RefundConfig::getMinQty))
				.get();
	}

	/**
	 * The refund line is booked on the bonus product, or else on the config's product. A config that has neither would book it on whatever product was sold, with the wrong accounts and tax.
	 */
	public void assertRefundProductIsKnown(@NonNull final RefundConfig refundConfig)
	{
		if (refundConfig.getProductId() == null && refundConfig.getBonusProductId() == null)
		{
			throw new AdempiereException(MSG_REFUND_CONFIG_BONUS_PRODUCT_REQUIRED).markAsUserValidationError();
		}
	}

	public BonusRecipient extractBonusRecipient(@NonNull final List<RefundConfig> refundConfigs)
	{
		return extractSingleElement(refundConfigs, RefundConfig::getBonusRecipient);
	}

	public RefundMode extractRefundMode(@NonNull final List<RefundConfig> refundConfigs)
	{
		final RefundMode refundMode = extractSingleElement(
				refundConfigs,
				RefundConfig::getRefundMode);
		return refundMode;
	}

	/**
	 * @return the product that the refund line is booked on: the configs' bonus product, or else their product.
	 *         {@code null} if the configs have neither, e.g. because their base is a product category.
	 * @throws RuntimeException if the configs have more than one bonus product, or more than one product
	 */
	@Nullable
	public ProductId extractRefundProductId(@NonNull final List<RefundConfig> refundConfigs)
	{
		final ProductId bonusProductId = extractSingleNonNullOrNull(refundConfigs, RefundConfig::getBonusProductId);
		if (bonusProductId != null)
		{
			return bonusProductId;
		}
		return extractSingleNonNullOrNull(refundConfigs, RefundConfig::getProductId);
	}

	@Nullable
	private ProductId extractSingleNonNullOrNull(
			@NonNull final List<RefundConfig> refundConfigs,
			@NonNull final Function<RefundConfig, ProductId> productIdExtractor)
	{
		final ImmutableSet<ProductId> productIds = refundConfigs.stream()
				.map(productIdExtractor)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		if (productIds.isEmpty())
		{
			return null;
		}
		return singleElement(productIds);
	}

	public void assertValid(@NonNull final List<RefundConfig> refundConfigs)
	{
		Check.assumeNotEmpty(refundConfigs, "refundConfigs");

		if (hasDifferentValues(refundConfigs, RefundConfig::getRefundBase))
		{
			Loggables.addLog("The given refundConfigs need to all have the same RefundBase; refundConfigs={}", refundConfigs);

			throw new AdempiereException(MSG_REFUND_CONFIG_SAME_REFUND_BASE).markAsUserValidationError();
		}
		if (hasDifferentValues(refundConfigs, RefundConfig::getRefundMode))
		{
			Loggables.addLog("The given refundConfigs need to all have the same RefundMode; refundConfigs={}", refundConfigs);

			throw new AdempiereException(MSG_REFUND_CONFIG_SAME_REFUND_MODE).markAsUserValidationError();
		}

		// the refund of a contract is issued to one partner
		if (hasDifferentValues(refundConfigs, RefundConfig::getBonusRecipient))
		{
			Loggables.addLog("The given refundConfigs need to all have the same BonusRecipient; refundConfigs={}", refundConfigs);

			throw new AdempiereException(MSG_REFUND_CONFIG_SAME_BONUS_RECIPIENT).markAsUserValidationError();
		}

		// the refund line is booked on one product. Different products per config are fine though: the term's product selects the configs.
		final long distinctBonusProducts = refundConfigs.stream().map(RefundConfig::getBonusProductId).filter(Objects::nonNull).distinct().count();
		if (distinctBonusProducts > 1)
		{
			Loggables.addLog("The given refundConfigs need to all have the same bonus product; refundConfigs={}", refundConfigs);

			throw new AdempiereException(MSG_REFUND_CONFIG_SAME_BONUS_PRODUCT).markAsUserValidationError();
		}

		if (RefundMode.APPLY_TO_ALL_QTIES.equals(extractRefundMode(refundConfigs)))
		{
			// we have one IC with different configs, so those configs need to have the consistent settings
			if (hasDifferentValues(refundConfigs, RefundConfig::getInvoiceSchedule))
			{
				Loggables.addLog(
						"Because refundMode={}, all the given refundConfigs need to all have the same invoiceSchedule; refundConfigs={}",
						RefundMode.APPLY_TO_ALL_QTIES, refundConfigs);

				throw new AdempiereException(MSG_REFUND_CONFIG_SAME_INVOICE_SCHEDULE).markAsUserValidationError();
			}
			if (hasDifferentValues(refundConfigs, RefundConfig::getRefundInvoiceType))
			{
				Loggables.addLog(
						"Because refundMode={}, all the given refundConfigs need to all have the same refundInvoiceType; refundConfigs={}",
						RefundMode.APPLY_TO_ALL_QTIES, refundConfigs);

				throw new AdempiereException(MSG_REFUND_CONFIG_SAME_REFUND_INVOICE_TYPE).markAsUserValidationError();
			}
		}
	}
}
