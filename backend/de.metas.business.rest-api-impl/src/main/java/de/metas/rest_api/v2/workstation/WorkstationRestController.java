package de.metas.rest_api.v2.workstation;

import de.metas.Profiles;
import de.metas.material.planning.Resource;
import de.metas.material.planning.ResourceService;
import de.metas.product.ResourceId;
import de.metas.resource.UserWorkstationService;
import de.metas.resource.qrcode.ResourceQRCode;
import de.metas.user.UserId;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.workplace.WorkplaceId;
import de.metas.workplace.WorkplaceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping(value = { MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/workstation" })
@RestController
@Profile(Profiles.PROFILE_App)
public class WorkstationRestController
{
	@NonNull private final ResourceService resourceService;
	@NonNull private final UserWorkstationService userWorkstationService;
	@NonNull private final WorkplaceService workplaceService;

	@NonNull
	private Resource getWorkstationById(final ResourceId workstationId)
	{
		final Resource workstation = resourceService.getById(workstationId);
		assertWorkstation(workstation);
		return workstation;
	}

	private static void assertWorkstation(final Resource workstation)
	{
		if (!workstation.isWorkstation())
		{
			throw new AdempiereException("Not a workstation QR Code");
		}
	}

	private JsonWorkstation toJson(final Resource workstation)
	{
		final ResourceId workstationId = workstation.getResourceId();
		final WorkplaceId workplaceId = workstation.getWorkplaceId();
		final String workplaceName = workplaceId != null ? workplaceService.getById(workplaceId).getName() : null;

		return JsonWorkstation.builder()
				.id(workstationId)
				.name(workstation.getName())
				.qrCode(workstation.toQrCode().toGlobalQRCodeJsonString())
				.workplaceName(workplaceName)
				.isUserAssigned(userWorkstationService.isUserAssigned(Env.getLoggedUserId(), workstationId))
				.build();
	}

	@GetMapping
	public JsonWorkstationSettings getStatus()
	{
		return JsonWorkstationSettings.builder()
				.assignedWorkstation(userWorkstationService.getUserWorkstationId(Env.getLoggedUserId())
						.map(resourceService::getById)
						.map(this::toJson)
						.orElse(null))
				.build();
	}

	@PostMapping("/assign")
	public JsonWorkstation assign(@RequestBody @NonNull final JsonAssignWorkstationRequest request)
	{
		final Resource workstation = getWorkstationById(request.getWorkstationIdEffective());

		final UserId loggedUserId = Env.getLoggedUserId();
		userWorkstationService.assign(loggedUserId, workstation.getResourceId());

		Optional.ofNullable(workstation.getWorkplaceId())
				.ifPresent(workplaceId -> workplaceService.assignWorkplace(loggedUserId, workplaceId));

		return toJson(workstation);
	}

	@PostMapping("/byQRCode")
	public JsonWorkstation getWorkstationByQRCode(@RequestBody @NonNull final JsonGetWorkstationByQRCodeRequest request)
	{
		final ResourceQRCode qrCode = ResourceQRCode.ofGlobalQRCodeJsonString(request.getQrCode());
		final Resource workstation = getWorkstationById(qrCode.getResourceId());
		return toJson(workstation);
	}
}
