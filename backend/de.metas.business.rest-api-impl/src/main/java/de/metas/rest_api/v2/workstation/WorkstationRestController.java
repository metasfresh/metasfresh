package de.metas.rest_api.v2.workstation;

import de.metas.Profiles;
import de.metas.material.planning.IResourceDAO;
import de.metas.product.ResourceId;
import de.metas.resource.UserWorkstationService;
import de.metas.resource.qrcode.ResourceQRCode;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.workplace.WorkplaceId;
import de.metas.workplace.WorkplaceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_S_Resource;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RequiredArgsConstructor
@RequestMapping(value = { MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/workstation" })
@RestController
@Profile(Profiles.PROFILE_App)
public class WorkstationRestController
{
	@NonNull private final IResourceDAO resourceDAO = Services.get(IResourceDAO.class);
	@NonNull private final UserWorkstationService userWorkstationService;
	@NonNull private final WorkplaceService workplaceService;

	@NonNull
	private static ResourceId extractWorkstationId(final I_S_Resource workstation)
	{
		return ResourceId.ofRepoId(workstation.getS_Resource_ID());
	}

	private I_S_Resource getWorkstationById(final ResourceId workstationId)
	{
		final I_S_Resource workstation = resourceDAO.getById(workstationId);
		assertWorkstation(workstation);
		return workstation;
	}

	private static void assertWorkstation(final I_S_Resource workstation)
	{
		if (!isWorkstation(workstation))
		{
			throw new AdempiereException("Not a workstation QR Code");
		}
	}

	private static boolean isWorkstation(final I_S_Resource resource)
	{
		return resource.isManufacturingResource()
				&& Objects.equals(resource.getManufacturingResourceType(), X_S_Resource.MANUFACTURINGRESOURCETYPE_WorkStation);
	}

	private JsonWorkstation toJson(final I_S_Resource workstation)
	{
		final ResourceId workstationId = extractWorkstationId(workstation);
		final WorkplaceId workplaceId = WorkplaceId.ofRepoIdOrNull(workstation.getC_Workplace_ID());
		final String workplaceName = workplaceId != null ? workplaceService.getById(workplaceId).getName() : null;

		return JsonWorkstation.builder()
				.id(workstationId)
				.name(workstation.getName())
				.qrCode(ResourceQRCode.ofResource(workstation).toGlobalQRCodeJsonString())
				.workplaceName(workplaceName)
				.isUserAssigned(userWorkstationService.isUserAssigned(Env.getLoggedUserId(), workstationId))
				.build();
	}

	@GetMapping
	public JsonWorkstationSettings getStatus()
	{
		return JsonWorkstationSettings.builder()
				.assignedWorkstation(userWorkstationService.getUserWorkstationId(Env.getLoggedUserId())
						.map(resourceDAO::getById)
						.map(this::toJson)
						.orElse(null))
				.build();
	}

	@PostMapping("/assign")
	public JsonWorkstation assign(@RequestBody @NonNull final JsonAssignWorkstationRequest request)
	{
		final I_S_Resource workstation = getWorkstationById(request.getWorkstationIdEffective());

		final UserId loggedUserId = Env.getLoggedUserId();
		userWorkstationService.assign(loggedUserId, extractWorkstationId(workstation));

		WorkplaceId.optionalOfRepoId(workstation.getC_Workplace_ID())
				.ifPresent(workplaceId -> workplaceService.assignWorkplace(loggedUserId, workplaceId));

		return toJson(workstation);
	}

	@PostMapping("/byQRCode")
	public JsonWorkstation getWorkstationByQRCode(@RequestBody @NonNull final JsonGetWorkstationByQRCodeRequest request)
	{
		final ResourceQRCode qrCode = ResourceQRCode.ofGlobalQRCodeJsonString(request.getQrCode());
		final I_S_Resource workstation = getWorkstationById(qrCode.getResourceId());
		return toJson(workstation);
	}
}
