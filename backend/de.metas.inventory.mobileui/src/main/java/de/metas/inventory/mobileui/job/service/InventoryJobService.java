package de.metas.inventory.mobileui.job.service;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryQuery;
import de.metas.inventory.mobileui.job.InventoryJob;
import de.metas.inventory.mobileui.job.InventoryJobId;
import de.metas.inventory.mobileui.job.InventoryJobLine;
import de.metas.inventory.mobileui.job.qrcode.InventoryHUScannedCodeResolveCommand;
import de.metas.inventory.mobileui.job.qrcode.InventoryHUScannedCodeResolveCommand.InventoryHUScannedCodeResolveCommandBuilder;
import de.metas.inventory.mobileui.job.qrcode.ScannedCodeResolveRequest;
import de.metas.inventory.mobileui.job.qrcode.ScannedCodeResolveResponse;
import de.metas.inventory.mobileui.job.repository.InventoryJobLoaderAndSaver;
import de.metas.inventory.mobileui.launchers.InventoryJobReference;
import de.metas.inventory.mobileui.rest_api.json.JsonCountRequest;
import de.metas.inventory.mobileui.rest_api.json.JsonInventoryJob;
import de.metas.product.IProductBL;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolveContext;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverResult;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class InventoryJobService
{
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final LocatorScannedCodeResolverService locatorScannedCodeResolver;
	@NonNull private final HUQRCodesService huQRCodesService;

	@NotNull
	private InventoryJobLoaderAndSaver newLoaderAndSaver() {return new InventoryJobLoaderAndSaver();}

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

	public LocatorScannedCodeResolverResult resolveLocator(@NonNull final ScannedCodeResolveRequest request)
	{
		Check.assumeNull(request.getLocatorId(), "locator shall not be provided");

		final List<InventoryJobLine> lines = request.getContextJobLines();

		return locatorScannedCodeResolver.resolve(
				request.getScannedCode(),
				LocatorScannedCodeResolveContext.builder()
						.eligibleLocatorIds(lines.stream().map(InventoryJobLine::getLocatorId).collect(ImmutableSet.toImmutableSet()))
						.build()
		);
	}

	public ScannedCodeResolveResponse resolveHU(@NonNull final ScannedCodeResolveRequest request)
	{
		return newHUScannedCodeResolveCommand(request)
				.job(request.getJob())
				.lineId(request.getLineId())
				.build()
				.execute();
	}

	private InventoryHUScannedCodeResolveCommandBuilder newHUScannedCodeResolveCommand(final @NotNull ScannedCodeResolveRequest request)
	{
		return InventoryHUScannedCodeResolveCommand.builder()
				.productBL(productBL)
				.handlingUnitsBL(handlingUnitsBL)
				.huQRCodesService(huQRCodesService)
				.scannedCode(request.getScannedCode());
	}

	public JsonInventoryJob count(@NonNull final JsonCountRequest request)
	{
		final InventoryJobId jobId = InventoryJobId.ofWFProcessId(request.getWfProcessId());
		final InventoryJob job = getJobById(jobId);

		return JsonInventoryJob.of(job);
	}
}
