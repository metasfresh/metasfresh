package de.metas.handlingunits.rest_api.createfromlabel;

import com.google.common.collect.ImmutableMap;
import de.metas.handlingunits.qrcodes.gs1.GS1HUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

/** The values a label carries (lot, best-before, production date, weight), read from the parsed code. Shared by the proposal and the create command. */
@Value
@Builder
class LabelValues
{
	@Nullable String lotNo;
	@Nullable LocalDate bestBeforeDate;
	@Nullable LocalDate productionDate;
	@Nullable BigDecimal weightKg;

	@NonNull
	static LabelValues of(@NonNull final IHUQRCode code)
	{
		// a GS1 label's use-by date (17) is stored as the best-before date (working default)
		final LocalDate bestBeforeDate = code.getBestBeforeDate()
				.orElseGet(() -> code instanceof GS1HUQRCode ? ((GS1HUQRCode)code).getExpirationDate().orElse(null) : null);

		return builder()
				.lotNo(code.getLotNumber().orElse(null))
				.bestBeforeDate(bestBeforeDate)
				.productionDate(code.getProductionDate().orElse(null))
				.weightKg(code.getWeightInKg().orElse(null))
				.build();
	}

	/** the attribute values the label carries, in display order (lot, best-before, production date) */
	@NonNull
	ImmutableMap<AttributeCode, Object> getAttributeValues()
	{
		final ImmutableMap.Builder<AttributeCode, Object> result = ImmutableMap.builder();
		if (lotNo != null)
		{
			result.put(AttributeConstants.ATTR_LotNumber, lotNo);
		}
		if (bestBeforeDate != null)
		{
			result.put(AttributeConstants.ATTR_BestBeforeDate, bestBeforeDate);
		}
		if (productionDate != null)
		{
			result.put(AttributeConstants.ProductionDate, productionDate);
		}
		return result.build();
	}

	/**
	 * {@link #getAttributeValues()} without the attributes this installation does not define: the preview does not show them and the
	 * booking does not store them (one rule for both steps, so OK never fails on a value the preview left out).
	 */
	@NonNull
	ImmutableMap<AttributeCode, Object> getAttributeValuesDefinedHere(@NonNull final IAttributeDAO attributeDAO)
	{
		return getAttributeValues().entrySet().stream()
				.filter(entry -> attributeDAO.getAttributeDisplayNameByValue(entry.getKey()).isPresent())
				.collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));
	}
}
