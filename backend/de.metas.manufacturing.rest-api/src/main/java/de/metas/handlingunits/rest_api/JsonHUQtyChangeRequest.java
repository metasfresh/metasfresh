package de.metas.handlingunits.rest_api;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.metas.common.rest_api.v2.JsonQuantity;
import de.metas.handlingunits.HuId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.LocalDate;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class JsonHUQtyChangeRequest
{
	@NonNull String huQRCode;
	@Nullable HuId huId;
	@NonNull JsonQuantity qty;
	@Nullable String description;
	boolean splitOneIfAggregated;

	@Nullable String locatorQRCode;

	boolean setBestBeforeDate;
	@Nullable @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate bestBeforeDate;

	boolean setLotNo;
	@Nullable String lotNo;

	public JsonHUQtyChangeRequest withHuIdAndValidate(@NonNull final HuId huId)
	{
		if (this.huId != null)
		{
			if (!HuId.equals(this.huId, huId))
			{
				throw new AdempiereException("Overriding request huId with `" + huId + "` is not allowed for request " + this);
			}
			else
			{
				return this;
			}
		}
		else
		{
			return toBuilder().huId(huId).build();
		}
	}
}
