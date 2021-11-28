package de.metas.mobileui.inventory_disposal;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HUBarcode;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.product.IProductBL;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.workflow.rest_api.service.WorkflowStartRequest;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class HUDisposalJobService
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	private final ConcurrentHashMap<HuId, HUDisposalJob> startedJobs = new ConcurrentHashMap<>();

	public HUDisposalJob createJob(final WorkflowStartRequest request)
	{
		return startedJobs.compute(
				extractHuId(request),
				(huId, existingJob) -> {
					if (existingJob != null)
					{
						throw new AdempiereException("Job already started");
					}
					return createJobEffective(request);
				});
	}

	@NonNull
	private static HuId extractHuId(final WorkflowStartRequest request)
	{
		final String huBarcodeString = Objects.requireNonNull(request.getWfParameters().getParameterAsString("startByBarcode"));
		return HUBarcode.ofBarcodeString(huBarcodeString)
				.toHuId();
	}

	private HUDisposalJob createJobEffective(final WorkflowStartRequest request)
	{
		final HuId huId = extractHuId(request);
		final I_M_HU hu = handlingUnitsBL.getById(huId);
		if (hu == null)
		{
			throw new AdempiereException("HU not found");
		}
		if (handlingUnitsBL.isDestroyed(hu))
		{
			throw new AdempiereException("HU is destroyed");
		}

		// TODO: lock the HU?

		return HUDisposalJob.builder()
				.huId(huId)
				.huBarcode(HUBarcode.ofHuId(huId))
				.huDisplayName(handlingUnitsBL.getDisplayName(hu))
				.invokerId(request.getInvokerId())
				.build();
	}

	public HUDisposalJob getJobById(final HuId huId)
	{
		final HUDisposalJob job = startedJobs.get(huId);
		if (job == null)
		{
			throw new AdempiereException("No job found");
		}
		return job;
	}

	public List<HUDisposalJob> getJobsByInvokerId(@NonNull final UserId invokerId)
	{
		return startedJobs.values()
				.stream()
				.filter(job -> UserId.equals(job.getInvokerId(), invokerId))
				.collect(ImmutableList.toImmutableList());
	}

	public HUDisposalJob completeDisposal(final HUDisposalJob huDisposalJob)
	{
		startedJobs.remove(huDisposalJob.getHuId());

		// TODO
		return huDisposalJob;
	}

	public void abort(final HuId huId, final UserId callerId)
	{
		startedJobs.remove(huId);
		// TODO: check if caller matchers
		// TODO release if needed
	}

	public List<HUStorageInfo> getStorageInfo(final HuId huId)
	{
		final I_M_HU hu = handlingUnitsBL.getById(huId);
		return handlingUnitsBL.getStorageFactory()
				.getStorage(hu)
				.streamProductStorages()
				.map(this::toHUStorageInfo)
				.collect(ImmutableList.toImmutableList());
	}

	private HUStorageInfo toHUStorageInfo(final IHUProductStorage productStorage)
	{
		return HUStorageInfo.builder()
				.productName(productBL.getProductNameTrl(productStorage.getProductId()))
				.qty(productStorage.getQty())
				.build();
	}
}
