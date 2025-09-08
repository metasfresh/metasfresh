package de.metas.manufacturing.workflows_api.activity_handlers.issue.json;

import de.metas.product.IssuingToleranceSpec;
import de.metas.product.IssuingToleranceValueType;
import de.metas.quantity.Quantity;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonQtyToleranceSpec
{
	@Nullable BigDecimal percentage;
	@Nullable BigDecimal qty;
	@Nullable String uom;

	@Nullable
	public static JsonQtyToleranceSpec ofNullable(@Nullable IssuingToleranceSpec spec)
	{
		if (spec == null)
		{
			return null;
		}

		final IssuingToleranceValueType valueType = spec.getValueType();
		if (valueType == IssuingToleranceValueType.PERCENTAGE)
		{
			final Percent percent = spec.getPercent();
			return builder().percentage(percent.toBigDecimal()).build();
		}
		else if (valueType == IssuingToleranceValueType.QUANTITY)
		{
			final Quantity qty = spec.getQty();
			return builder().qty(qty.toBigDecimal()).uom(qty.getUOMSymbol()).build();
		}
		else
		{
			throw new AdempiereException("Unknown valueType: " + valueType);
		}
	}
}
