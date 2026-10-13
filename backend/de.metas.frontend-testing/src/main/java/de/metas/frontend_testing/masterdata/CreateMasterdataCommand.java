package de.metas.frontend_testing.masterdata;

import de.metas.frontend_testing.masterdata.sysconfig.SysconfigCommand;
import lombok.Builder;
import lombok.NonNull;

import java.util.Map;

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

	public JsonCreateMasterdataResponse execute()
	{
		context.putFromJson(request.getContext());

		final Map<String, String> previousSysconfigs = SysconfigCommand.builder()
				.sysconfigs(request.getSysconfigs())
				.build()
				.execute();

		return JsonCreateMasterdataResponse.builder()
				.context(context.toJson())
				.previousSysconfigs(previousSysconfigs)
				.build();
	}
}
