package de.metas.manufacturing.workflows_api.activity_handlers.receive.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import de.metas.common.util.CoalesceUtil;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JsonReceivingTarget
{
	@Nullable JsonNewLUTarget newLU;
	@Nullable JsonHUQRCodeTarget existingLU;

	@Builder
	@Jacksonized
	private JsonReceivingTarget(
			@Nullable final JsonNewLUTarget newLU,
			@Nullable final JsonHUQRCodeTarget existingLU)
	{
		if (CoalesceUtil.countNotNulls(newLU, existingLU) != 1)
		{
			throw new AdempiereException("One and only of the `newLU` or `existingLU` shall be specified.");
		}

		this.newLU = newLU;
		this.existingLU = existingLU;
	}
}
