package de.metas.frontend_testing.masterdata.sysconfig;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonSetSysconfigsResponse
{
	/** Previous values of every sysconfig row the call wrote; restore them via {@code restoreSysconfigs}. */
	@Nullable @JsonInclude(JsonInclude.Include.NON_EMPTY) List<JsonSysconfig> previousSysconfigs;
}
