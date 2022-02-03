package de.metas.handlingunits.qrcodes.model;

import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuItemId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public final class HUOrAggregatedTUItemId
{
	@Nullable private final HuId huId;
	@Nullable private final HuItemId aggregatedTUItemId;

	private HUOrAggregatedTUItemId(
			@Nullable final HuId huId,
			@Nullable final HuItemId aggregatedTUItemId)
	{
		if (CoalesceUtil.countNotNulls(huId, aggregatedTUItemId) != 1)
		{
			throw new AdempiereException("huId and aggregateHUItemId are mutually exclusive");
		}

		this.huId = huId;
		this.aggregatedTUItemId = aggregatedTUItemId;
	}

	public static HUOrAggregatedTUItemId ofHuId(@NonNull final HuId huId)
	{
		return new HUOrAggregatedTUItemId(huId, null);
	}

	public static HUOrAggregatedTUItemId ofAggregatedTUItemId(@NonNull final HuItemId aggregateHUItemId)
	{
		return new HUOrAggregatedTUItemId(null, aggregateHUItemId);
	}

	public boolean isHuId() {return huId != null;}

	public HuId getHuId()
	{
		if (huId == null) {throw new AdempiereException("Not an HuId: " + this);}
		return huId;
	}

	public HuId getHuIdOrNull()
	{
		return huId;
	}

	public boolean isAggregatedTU() {return aggregatedTUItemId != null;}

	public HuItemId getAggregatedTUItemId()
	{
		if (aggregatedTUItemId == null) {throw new AdempiereException("Not an Aggregated HuId: " + this);}
		return aggregatedTUItemId;
	}

	public HuItemId getAggregateTUItemIdOrNull()
	{
		return aggregatedTUItemId;
	}
}
