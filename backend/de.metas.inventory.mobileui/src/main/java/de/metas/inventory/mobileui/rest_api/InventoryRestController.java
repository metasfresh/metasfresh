package de.metas.inventory.mobileui.rest_api;

import de.metas.Profiles;
import de.metas.inventory.mobileui.job.InventoryJob;
import de.metas.inventory.mobileui.job.InventoryJobId;
import de.metas.inventory.mobileui.job.qrcode.ScannedCodeResolveRequest;
import de.metas.inventory.mobileui.job.qrcode.ScannedCodeResolveResponse;
import de.metas.inventory.mobileui.job.service.InventoryJobService;
import de.metas.inventory.mobileui.rest_api.json.JsonResolveHUResponse;
import de.metas.inventory.mobileui.rest_api.json.JsonResolveLocatorResponse;
import de.metas.inventory.mobileui.rest_api.json.JsonResolveProductResponse;
import de.metas.inventory.mobileui.rest_api.json.JsonScannedCodeResolveRequest;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/mobile/inventory")
@RestController
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class InventoryRestController
{
	@NonNull private InventoryJobService jobService;

	@PostMapping("/resolveLocator")
	public JsonResolveLocatorResponse resolveLocator(@RequestBody @NonNull final JsonScannedCodeResolveRequest request)
	{
		final LocatorScannedCodeResolverResult result = jobService.resolveLocator(fromJson(request));
		return JsonResolveLocatorResponse.of(result);
	}

	@PostMapping("/resolveHU")
	public JsonResolveHUResponse resolveHU(@RequestBody @NonNull final JsonScannedCodeResolveRequest request)
	{
		final ScannedCodeResolveResponse response = jobService.resolveHU(fromJson(request));
		return JsonResolveHUResponse.of(response);
	}

	@PostMapping("/resolveProduct")
	public JsonResolveProductResponse resolveProduct(@RequestBody @NonNull final JsonScannedCodeResolveRequest request)
	{
		throw new UnsupportedOperationException(); // TODO
	}

	@NotNull
	private ScannedCodeResolveRequest fromJson(final @NotNull JsonScannedCodeResolveRequest request)
	{
		final InventoryJobId jobId = InventoryJobId.ofWFProcessId(request.getWfProcessId());
		final InventoryJob job = jobService.getJobById(jobId);
		return ScannedCodeResolveRequest.builder()
				.scannedCode(request.getScannedCode())
				.job(job)
				.lineId(request.getLineId())
				.locatorId(request.getLocatorQRCode() != null ? request.getLocatorQRCode().getLocatorId() : null)
				.build();
	}

}
