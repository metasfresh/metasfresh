package de.metas.inventory.mobileui.job.service;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryQuery;
import de.metas.inventory.mobileui.job.InventoryJob;
import de.metas.inventory.mobileui.job.InventoryJobId;
import de.metas.inventory.mobileui.job.qrcode.InventoryScannedCodeResolveCommand;
import de.metas.inventory.mobileui.job.qrcode.InventoryScannedCodeResolveCommand.InventoryScannedCodeResolveCommandBuilder;
import de.metas.inventory.mobileui.job.qrcode.ScannedCodeResolveRequest;
import de.metas.inventory.mobileui.job.qrcode.ScannedCodeResolveResponse;
import de.metas.inventory.mobileui.job.repository.InventoryJobLoaderAndSaver;
import de.metas.inventory.mobileui.launchers.InventoryJobReference;
import de.metas.product.IProductBL;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class InventoryJobService
{
	@NonNull final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull final HUQRCodesService huQRCodesService;

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
				.updateById(InventoryJobId.ofInventoryId(inventoryId), job -> {
					final UserId previousResponsibleId = job.getResponsibleId();
					if (previousResponsibleId != null && !UserId.equals(previousResponsibleId, responsibleId))
					{
						throw new AdempiereException("Inventory is already assigned");
					}
					return job.withResponsibleId(responsibleId);
				});
	}

	public InventoryJob assignJob(final InventoryJobId jobId, final UserId callerId)
	{
		return newLoaderAndSaver()
				.updateById(jobId, job -> job.withResponsibleId(callerId));
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

	public ScannedCodeResolveResponse resolveHU(@NonNull final ScannedCodeResolveRequest request)
	{
		return newScannedCodeResolveCommand(request)
				.job(request.getJob())
				.lineId(request.getLineId())
				.build()
				.execute();
	}

	private InventoryScannedCodeResolveCommandBuilder newScannedCodeResolveCommand(final @NotNull ScannedCodeResolveRequest request)
	{
		return InventoryScannedCodeResolveCommand.builder()
				.productBL(productBL)
				.handlingUnitsBL(handlingUnitsBL)
				.huQRCodesService(huQRCodesService)
				.scannedCode(request.getScannedCode());
	}
}
