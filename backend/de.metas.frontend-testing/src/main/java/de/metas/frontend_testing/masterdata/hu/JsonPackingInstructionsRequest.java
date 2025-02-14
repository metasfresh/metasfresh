package de.metas.frontend_testing.masterdata.hu;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonPackingInstructionsRequest
{
	@NonNull Identifier tu;
	@NonNull Identifier product;
	@NonNull BigDecimal qtyCUsPerTU;
	
	@Nullable Identifier lu;
	int qtyTUsPerLU;
}
