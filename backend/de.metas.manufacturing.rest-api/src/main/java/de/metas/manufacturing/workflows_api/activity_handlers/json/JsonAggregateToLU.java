package de.metas.manufacturing.workflows_api.activity_handlers.json;

import de.metas.common.util.CoalesceUtil;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
public class JsonAggregateToLU
{
	@Nullable JsonAggregateToNewLU newLU;
	@Nullable String existingLUBarcode;

	@Builder
	@Jacksonized
	private JsonAggregateToLU(
			@Nullable final JsonAggregateToNewLU newLU,
			@Nullable final String existingLUBarcode)
	{
		if (CoalesceUtil.countNotNulls(newLU, existingLUBarcode) != 1)
		{
			throw new AdempiereException("One and only of the `newLU` or `existingLUBarcode` shall be specified.");
		}

		this.newLU = newLU;
		this.existingLUBarcode = existingLUBarcode;
	}
}
