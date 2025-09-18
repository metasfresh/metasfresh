package de.metas.frontend_testing.masterdata.hu;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonCreateHURequest
{
	@Nullable Identifier product;
	@Nullable Identifier warehouse;
	@Nullable BigDecimal qty;
	@Nullable Identifier packingInstructions;
	@Nullable BigDecimal weightNet;
	@Nullable String lotNo;
	@Nullable String bestBeforeDate;
}
