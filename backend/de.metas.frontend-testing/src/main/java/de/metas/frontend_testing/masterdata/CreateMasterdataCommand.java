package de.metas.frontend_testing.masterdata;

import com.google.common.collect.ImmutableMap;
import de.metas.frontend_testing.masterdata.sysconfig.SysconfigCommand;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * @implNote This class is intentionally kept as a thin orchestrator: it routes to dedicated command classes
 * and must NOT contain business logic.
 */
@Builder
public class CreateMasterdataCommand
{
	@NonNull private final CreateMasterdataCommandSupportingServices services;
	@NonNull private final JsonCreateMasterdataRequest request;

	private final MasterdataContext context = new MasterdataContext();
	/**
	 * Previous values of every sysconfig changed by this request.
	 */
	private final LinkedHashMap<String, String> previousSysconfigs = new LinkedHashMap<>();

	public JsonCreateMasterdataResponse execute()
	{
		this.context.putFromJson(request.getContext());

		// Apply sysconfigs early (before any masterdata creation)
		previousSysconfigs.putAll(applySysconfigs());

		// IMPORTANT: the order is very important - masterdata that references other masterdata by identifier comes after it.

		return JsonCreateMasterdataResponse.builder()
				.context(context.toJson())
				.previousSysconfigs(previousSysconfigs.isEmpty() ? null : ImmutableMap.copyOf(previousSysconfigs))
				.build();
	}

	@NonNull
	private ImmutableMap<String, String> applySysconfigs()
	{
		@Nullable final Map<String, String> sysconfigs = request.getSysconfigs();
		if (sysconfigs == null || sysconfigs.isEmpty())
		{
			return ImmutableMap.of();
		}

		return SysconfigCommand.builder()
				.sysconfigs(sysconfigs)
				.build()
				.execute();
	}
}
