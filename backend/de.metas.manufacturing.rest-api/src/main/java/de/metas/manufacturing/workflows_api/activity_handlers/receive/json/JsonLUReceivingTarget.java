package de.metas.manufacturing.workflows_api.activity_handlers.receive.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JsonLUReceivingTarget
{
	@Nullable JsonNewLUTarget newLU;
	@Nullable JsonHUQRCodeTarget existingLU;

	@Builder
	@Jacksonized
	private JsonLUReceivingTarget(
			@Nullable final JsonNewLUTarget newLU,
			@Nullable final JsonHUQRCodeTarget existingLU)
	{
		Check.assumeSingleNonNull("One and only of the `newLU` or `existingLU` shall be specified.", newLU, existingLU);

		this.newLU = newLU;
		this.existingLU = existingLU;
	}
}
