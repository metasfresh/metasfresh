package de.metas.frontend_testing.masterdata;

import de.metas.frontend_testing.masterdata.sysconfig.SysconfigCommand;
import de.metas.frontend_testing.masterdata.bpartner.CreateBPartnerCommand;
import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerResponse;
import de.metas.frontend_testing.masterdata.externalreference.CreateExternalReferenceCommand;
import de.metas.frontend_testing.masterdata.externalreference.JsonCreateExternalReferenceRequest;
import de.metas.frontend_testing.masterdata.inlinefilter.JsonSetupInlineFilterRequest;
import de.metas.frontend_testing.masterdata.inlinefilter.JsonSetupInlineFilterResponse;
import de.metas.frontend_testing.masterdata.inlinefilter.SetupInlineFilterCommand;
import lombok.Builder;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
		final List<JsonSetupInlineFilterResponse> inlineFilters = setupInlineFilters();

		return JsonCreateMasterdataResponse.builder()
				.context(context.toJson())
				.previousSysconfigs(previousSysconfigs)
				.bpartners(bpartners)
				.inlineFilters(inlineFilters)
				.build();
	}

	private Map<String, JsonCreateBPartnerResponse> createBPartners()
	{
		final Map<String, JsonCreateBPartnerResponse> responses = new LinkedHashMap<>();
		if (request.getBpartners() != null)
		{
			request.getBpartners().forEach((identifier, bpartnerRequest) -> responses.put(identifier, CreateBPartnerCommand.builder()
					.bpartnerDAO(services.bpartnerDAO)
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

	private List<JsonSetupInlineFilterResponse> setupInlineFilters()
	{
		final List<JsonSetupInlineFilterResponse> responses = new ArrayList<>();
		if (request.getInlineFilters() != null)
		{
			for (final JsonSetupInlineFilterRequest inlineFilterRequest : request.getInlineFilters())
			{
				responses.add(SetupInlineFilterCommand.builder()
						.repository(services.inlineFilterRepository)
						.request(inlineFilterRequest)
						.build()
						.execute());
			}
		}
		return responses;
	}
}
