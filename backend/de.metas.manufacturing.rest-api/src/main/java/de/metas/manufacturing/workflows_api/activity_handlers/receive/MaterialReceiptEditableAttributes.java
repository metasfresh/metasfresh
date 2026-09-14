package de.metas.manufacturing.workflows_api.activity_handlers.receive;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.manufacturing.config.MobileUIManufacturingConfig;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.api.Attribute;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Single source of truth for the manufacturing-receipt <b>generic editable-attribute allow-list</b>:
 * the config's editable-attribute codes ∩ the received product's instance-level {@code M_AttributeSet}.
 * <p>
 * Used by BOTH:
 * <ul>
 *   <li>{@link MaterialReceiptActivityHandler#buildEditableAttributes} — what the mobile UI OFFERS at receipt, and</li>
 *   <li>{@link de.metas.manufacturing.job.service.ManufacturingJobService#receiveGoods} — the fail-loud guard that
 *       REJECTS a receive whose generic {@code attributes[]} map carries a code not on that allow-list.</li>
 * </ul>
 * Extracting it here keeps the OFFER and the GUARD provably identical (no drift): a code the UI never offers can
 * never be silently accepted at receipt.
 */
@Component
public class MaterialReceiptEditableAttributes
{
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	/**
	 * The generic editable-attribute list for the given product: the config's editable-attribute codes
	 * ({@link MobileUIManufacturingConfig#getEditableAttributeCodesInOrder()}, already ordered), restricted to the
	 * product's {@code M_AttributeSet} and to instance-level attributes only, in that config order. Empty when the
	 * config lists nothing or the product's attribute set is {@code None} / carries none of the configured codes.
	 */
	@NonNull
	public List<Attribute> getEditableAttributes(
			@NonNull final ProductId productId,
			@NonNull final MobileUIManufacturingConfig config)
	{
		final ImmutableList<AttributeCode> configuredCodes = config.getEditableAttributeCodesInOrder();
		if (configuredCodes.isEmpty())
		{
			return ImmutableList.of();
		}

		final AttributeSetId attributeSetId = productBL.getAttributeSetId(productId);
		if (attributeSetId.isNone())
		{
			return ImmutableList.of();
		}

		final ImmutableMap<AttributeCode, Attribute> instanceAttributesByCode = attributeDAO
				.retrieveAttributes(attributeSetId, /* isInstanceAttribute */true)
				.stream()
				.collect(ImmutableMap.toImmutableMap(Attribute::getAttributeCode, attribute -> attribute));

		final ImmutableList.Builder<Attribute> result = ImmutableList.builder();
		for (final AttributeCode code : configuredCodes)
		{
			final Attribute attribute = instanceAttributesByCode.get(code);
			if (attribute != null)
			{
				result.add(attribute);
			}
		}
		return result.build();
	}

	/**
	 * The allow-list as a set of codes — the same resolution as {@link #getEditableAttributes}, projected to codes.
	 */
	@NonNull
	public ImmutableSet<AttributeCode> getEditableAttributeCodes(
			@NonNull final ProductId productId,
			@NonNull final MobileUIManufacturingConfig config)
	{
		return getEditableAttributes(productId, config)
				.stream()
				.map(Attribute::getAttributeCode)
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * Fail-loud guard (CHANGE 1, generic channel only): rejects the receive if the submitted generic-attribute map
	 * carries any code that is NOT on the editable allow-list for the received product. The dedicated scan fields
	 * ({@code lotNo}/{@code bestBeforeDate}/{@code productionDate}) are never routed through here — a scanned label
	 * value is always accepted. Special codes submitted via the generic map (Lot-Nummer / HU_BestBeforeDate /
	 * ProductionDate) pass exactly when they are configured editable AND instance attributes of the product.
	 */
	public void assertOnlyEditableAttributesSubmitted(
			@NonNull final ProductId productId,
			@NonNull final MobileUIManufacturingConfig config,
			@NonNull final Set<AttributeCode> submittedGenericAttributeCodes)
	{
		if (submittedGenericAttributeCodes.isEmpty())
		{
			return;
		}

		final ImmutableSet<AttributeCode> editableCodes = getEditableAttributeCodes(productId, config);
		for (final AttributeCode submittedCode : submittedGenericAttributeCodes)
		{
			if (!editableCodes.contains(submittedCode))
			{
				throw new AdempiereException("Attribute is not editable at manufacturing receipt for this product")
						.setParameter("attributeCode", submittedCode)
						.setParameter("productId", productId)
						.setParameter("editableAttributeCodes", editableCodes)
						.markAsUserValidationError();
			}
		}
	}

	/**
	 * Fail-loud guard (CHANGE 2, dual-channel): rejects the receive when the same producer-managed code is supplied
	 * through BOTH channels for the same value — a non-blank dedicated field
	 * ({@code lotNo}/{@code bestBeforeDate}/{@code productionDate}) AND a non-blank entry for that code in the generic
	 * {@code attributes[]} map. This replaces the old silent "generic-map-wins" coalesce for the conflict case: a real
	 * conflict now never reaches that coalesce. A blank generic value is not a conflict (the coalesce would fall back
	 * to the dedicated field deterministically), so it is not rejected.
	 */
	public static void assertNoDualChannelConflict(
			@Nullable final String lotNo,
			@Nullable final String bestBeforeDate,
			@Nullable final String productionDate,
			@NonNull final Map<AttributeCode, String> genericAttributes)
	{
		assertNoDualChannelConflict(lotNo, AttributeConstants.ATTR_LotNumber, genericAttributes);
		assertNoDualChannelConflict(bestBeforeDate, AttributeConstants.ATTR_BestBeforeDate, genericAttributes);
		assertNoDualChannelConflict(productionDate, AttributeConstants.ProductionDate, genericAttributes);
	}

	private static void assertNoDualChannelConflict(
			@Nullable final String dedicatedValue,
			@NonNull final AttributeCode code,
			@NonNull final Map<AttributeCode, String> genericAttributes)
	{
		final String dedicated = StringUtils.trimBlankToNull(dedicatedValue);
		if (dedicated == null)
		{
			return;
		}

		final String generic = StringUtils.trimBlankToNull(genericAttributes.get(code));
		if (generic == null)
		{
			return;
		}

		throw new AdempiereException("The same attribute was submitted in both the dedicated field and the generic attributes")
				.setParameter("attributeCode", code)
				.setParameter("dedicatedValue", dedicated)
				.setParameter("genericValue", generic)
				.markAsUserValidationError();
	}
}
