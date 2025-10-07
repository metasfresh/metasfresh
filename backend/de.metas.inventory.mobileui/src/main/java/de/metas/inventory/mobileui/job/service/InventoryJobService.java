package de.metas.inventory.mobileui.job.service;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryQuery;
import de.metas.inventory.mobileui.job.InventoryJob;
import de.metas.inventory.mobileui.job.InventoryJobId;
import de.metas.inventory.mobileui.job.qrcode.ResolveHUCommand;
import de.metas.inventory.mobileui.job.qrcode.ResolveHUCommand.ResolveHUCommandBuilder;
import de.metas.inventory.mobileui.job.qrcode.ResolveHURequest;
import de.metas.inventory.mobileui.job.qrcode.ResolveHUResponse;
import de.metas.inventory.mobileui.job.repository.InventoryJobLoaderAndSaver;
import de.metas.inventory.mobileui.launchers.InventoryJobReference;
import de.metas.inventory.mobileui.rest_api.json.JsonCountRequest;
import de.metas.inventory.mobileui.rest_api.json.JsonInventoryJob;
import de.metas.product.IProductBL;
import de.metas.uom.IUOMDAO;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverRequest;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverResult;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class InventoryJobService
{
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final InventoryService inventoryService;
	@NonNull private final LocatorScannedCodeResolverService locatorScannedCodeResolver;
	@NonNull private final HUQRCodesService huQRCodesService;

	@NotNull
	private InventoryJobLoaderAndSaver newLoaderAndSaver()
	{
		return InventoryJobLoaderAndSaver.builder()
				.uomDAO(uomDAO)
				.inventoryService(inventoryService)
				.build();
	}

	public Stream<InventoryJobReference> streamReferences(@NonNull final InventoryQuery query)
	{
		return newLoaderAndSaver().streamReferences(query);
	}

	public InventoryJob getJobById(final InventoryJobId jobId)
	{
		return newLoaderAndSaver().loadById(jobId);
	}

	public InventoryJob startJob(@NonNull final InventoryId inventoryId, @NonNull final UserId responsibleId)
	{
		if (!responsibleId.isRegularUser())
		{
			throw new AdempiereException("Only regular users can be assigned to an inventory");
		}

		return newLoaderAndSaver()
				.updateById(InventoryJobId.ofInventoryId(inventoryId), job -> job.assigningTo(responsibleId));
	}

	public InventoryJob assignJob(final InventoryJobId jobId, final UserId newResponsibleId)
	{
		return newLoaderAndSaver()
				.updateById(jobId, job -> job.reassigningTo(newResponsibleId));
	}

	public void abort(final InventoryJobId jobId, final UserId callerId)
	{
		throw new UnsupportedOperationException(); // TODO
	}

	public void abortAll(final UserId callerId)
	{
		// TODO
	}

	public InventoryJob complete(InventoryJob inventoryJob)
	{
		// TODO
		return inventoryJob;
	}

	public LocatorScannedCodeResolverResult resolveLocator(@NonNull final LocatorScannedCodeResolverRequest request)
	{
		return locatorScannedCodeResolver.resolve(request);
	}

	public ResolveHUResponse resolveHU(@NonNull final ResolveHURequest request)
	{
		return newHUScannedCodeResolveCommand()
				.scannedCode(request.getScannedCode())
				.job(request.getJob())
				.lineId(request.getLineId())
				.locatorId(request.getLocatorId())
				.build()
				.execute();
	}

	private ResolveHUCommandBuilder newHUScannedCodeResolveCommand()
	{
		return ResolveHUCommand.builder()
				.productBL(productBL)
				.handlingUnitsBL(handlingUnitsBL)
				.huQRCodesService(huQRCodesService);
	}

	public JsonInventoryJob count(@NonNull final JsonCountRequest request, final UserId callerId)
	{
		final InventoryJobId jobId = InventoryJobId.ofWFProcessId(request.getWfProcessId());
		final InventoryJob job = getJobById(jobId);
		job.assertHasAccess(callerId);

		return JsonInventoryJob.of(job);
	}
}
