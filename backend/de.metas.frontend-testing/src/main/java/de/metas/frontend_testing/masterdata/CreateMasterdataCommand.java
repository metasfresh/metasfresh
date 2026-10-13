package de.metas.frontend_testing.masterdata;

import de.metas.frontend_testing.masterdata.sysconfig.SysconfigCommand;
import de.metas.frontend_testing.masterdata.bpartner.CreateBPartnerCommand;
import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerResponse;
import de.metas.frontend_testing.masterdata.externalreference.CreateExternalReferenceCommand;
import de.metas.frontend_testing.masterdata.externalreference.JsonCreateExternalReferenceRequest;
import lombok.Builder;
import lombok.NonNull;

import java.util.LinkedHashMap;
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
				.sysConfigBL(services.sysConfigBL)
				.sysconfigs(request.getSysconfigs())
				.build()
				.execute();

		final Map<String, JsonCreateBPartnerResponse> bpartners = createBPartners();
		createExternalReferences();

		return JsonCreateMasterdataResponse.builder()
				.context(context.toJson())
				.previousSysconfigs(previousSysconfigs)
				.bpartners(bpartners)
				.build();
	}

	private Map<String, JsonCreateBPartnerResponse> createBPartners()
	{
		final Map<String, JsonCreateBPartnerResponse> responses = new LinkedHashMap<>();
		if (request.getBpartners() != null)
		{
			request.getBpartners().forEach((identifier, bpartnerRequest) -> responses.put(identifier, CreateBPartnerCommand.builder()
					.queryBL(services.queryBL)
					.bpGroupDAO(services.bpGroupDAO)
					.countryDAO(services.countryDAO)
					.locationDAO(services.locationDAO)
					.context(context)
					.identifier(Identifier.ofString(identifier))
					.request(bpartnerRequest)
					.build()
					.execute()));
		}
		return responses;
	}

	private void createExternalReferences()
	{
		if (request.getExternalReferences() == null)
		{
			return;
		}

		for (final JsonCreateExternalReferenceRequest externalReferenceRequest : request.getExternalReferences())
		{
			CreateExternalReferenceCommand.builder()
					.externalReferenceRepository(services.externalReferenceRepository)
					.externalReferenceTypes(services.externalReferenceTypes)
					.externalSystems(services.externalSystems)
					.context(context)
					.request(externalReferenceRequest)
					.build()
					.execute();
		}
	}
}
