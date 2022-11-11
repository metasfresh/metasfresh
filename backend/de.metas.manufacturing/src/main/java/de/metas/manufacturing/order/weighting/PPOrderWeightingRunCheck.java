package de.metas.manufacturing.order.weighting;

import com.google.common.collect.Range;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;

@Getter
@EqualsAndHashCode
@ToString
public class PPOrderWeightingRunCheck
{
	@NonNull private final PPOrderWeightingRunCheckId id;
	private final int lineNo;
	@NonNull private Quantity weight;
	@Nullable private final String description;
	@Getter private boolean isToleranceExceeded;

	@Builder
	private PPOrderWeightingRunCheck(
			final @NonNull PPOrderWeightingRunCheckId id,
			final int lineNo,
			final @NonNull Quantity weight,
			final @Nullable String description,
			final boolean isToleranceExceeded)
	{
		this.id = id;
		this.lineNo = lineNo;
		this.weight = weight;
		this.description = description;
		this.isToleranceExceeded = isToleranceExceeded;
	}

	void updateIsToleranceExceeded(final Range<Quantity> validRange)
	{
		this.isToleranceExceeded = !validRange.contains(weight);
	}

	public void setUomId(final UomId uomId)
	{
		if (UomId.equals(this.weight.getUomId(), uomId))
		{
			return;
		}

		this.weight = Quantitys.create(this.weight.toBigDecimal(), uomId);
	}
}
