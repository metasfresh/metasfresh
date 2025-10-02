package de.metas.inventory.mobileui.job;

import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryQuery;
import de.metas.inventory.mobileui.job.repository.InventoryJobLoaderAndSaver;
import de.metas.inventory.mobileui.launchers.InventoryJobReference;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Inventory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class InventoryJobService
{
	@NonNull private final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);

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

	public InventoryJob startJob(final InventoryId inventoryId, final UserId responsibleId)
	{
		if (!responsibleId.isRegularUser())
		{
			throw new AdempiereException("Only regular users can be assigned to an inventory");
		}

		final I_M_Inventory inventoryRecord = inventoryDAO.getById(inventoryId);

		final UserId previousResponsibleId = UserId.ofRepoIdOrNullIfSystem(inventoryRecord.getAD_User_Responsible_ID());
		if (previousResponsibleId != null && !UserId.equals(previousResponsibleId, responsibleId))
		{
			throw new AdempiereException("Inventory is already assigned");
		}

		inventoryRecord.setAD_User_Responsible_ID(responsibleId.getRepoId());
		inventoryDAO.save(inventoryRecord);

		return newLoaderAndSaver()
				.addToCache(inventoryRecord)
				.loadById(InventoryJobId.ofRepoId(inventoryRecord.getM_Inventory_ID()));
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
}
