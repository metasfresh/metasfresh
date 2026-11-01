package de.metas.frontend_testing.masterdata.sysconfig;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Previous values of the sysconfigs a {@code setSysconfigs} / {@code setSystemSysconfigs} call wrote, so that the caller can restore them;
 * same semantics as the equally named fields of {@code JsonCreateMasterdataResponse}.
 */
@Value
@Builder
@Jacksonized
public class JsonSetSysconfigsResponse
{
	/** {@code null} value = there was no value; restore via {@code setSysconfigs}. */
	@Nullable @JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.ALWAYS) Map<String, String> previousSysconfigs;

	/** {@code null} value = there was no SYSTEM row; restore via {@code setSystemSysconfigs}. */
	@Nullable @JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.ALWAYS) Map<String, String> previousSystemSysconfigs;
}
