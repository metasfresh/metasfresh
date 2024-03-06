package de.metas.manufacturing.job.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.PPOrderBOMLineId;

import javax.annotation.Nullable;
import java.util.Objects;

@EqualsAndHashCode
public class FinishedGoodsReceiveLineId
{
	public static final FinishedGoodsReceiveLineId FINISHED_GOODS = new FinishedGoodsReceiveLineId("finishedGoods");

	public static FinishedGoodsReceiveLineId ofCOProductBOMLineId(@NonNull final PPOrderBOMLineId coProductBOMLineId) {return new FinishedGoodsReceiveLineId(PREFIX_CO_PRODUCT + coProductBOMLineId.getRepoId());}

	@JsonCreator
	public static FinishedGoodsReceiveLineId ofString(@NonNull final String string)
	{
		if (string.equals(FINISHED_GOODS.string))
		{
			return FINISHED_GOODS;
		}
		else if (string.startsWith(PREFIX_CO_PRODUCT))
		{
			return new FinishedGoodsReceiveLineId(string);
		}
		else
		{
			throw new AdempiereException("Invalid ID: " + string);
		}
	}

	private static final String PREFIX_CO_PRODUCT = "coProduct-";

	private final String string;

	private FinishedGoodsReceiveLineId(@NonNull final String string)
	{
		this.string = string;
	}

	@Override
	@Deprecated
	public String toString() {return toJson();}

	@JsonValue
	public String toJson() {return string;}

	public static boolean equals(@Nullable final FinishedGoodsReceiveLineId id1, @Nullable final FinishedGoodsReceiveLineId id2) {return Objects.equals(id1, id2);}
}
