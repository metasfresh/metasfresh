package de.metas.manufacturing.order.weighting.run;

import com.google.common.collect.Range;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.lang.SeqNo;
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
	private final SeqNo lineNo;
	@NonNull private Quantity weight;
	@Nullable private final String description;
	@Getter private boolean isToleranceExceeded;

	@Builder
	private PPOrderWeightingRunCheck(
			final @NonNull PPOrderWeightingRunCheckId id,
			final @NonNull SeqNo lineNo,
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

		this.weight = Quantitys.of(this.weight.toBigDecimal(), uomId);
	}
}
