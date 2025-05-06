package de.metas.frontend_testing.expectations.request;

import de.metas.frontend_testing.masterdata.JsonCreateMasterdataResponse;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonExpectations
{
	@Nullable JsonCreateMasterdataResponse masterdata;

	@Nullable List<JsonPickingExpectation> pickings;
	@Nullable List<JsonHUExpectation> hus;
}
