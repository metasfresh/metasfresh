package de.metas.handlingunits.allocation.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.ClearanceStatusInfo;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.quantity.Capacity;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedHashMap;

@Builder
public class TULoader
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	@NonNull private final IHUContext huContext;
	@NonNull private final I_M_HU_PI tuPI;
	@NonNull private final Capacity capacity;

	private final LinkedHashMap<TULoaderInstanceKey, TULoaderInstance> tuInstances = new LinkedHashMap<>();

	public void addCUs(@NonNull final Collection<I_M_HU> cuHUs)
	{
		cuHUs.forEach(this::addCU);
	}

	public void addCU(@NonNull final I_M_HU cuHU)
	{
		assertCU(cuHU);

		final TULoaderInstanceKey tuInstanceKey = extractTULoaderInstanceKeyFromCU(cuHU);
		final TULoaderInstance tuInstance = tuInstances.computeIfAbsent(tuInstanceKey, this::newTULoaderInstance);
		tuInstance.addCU(cuHU);
	}

	public void closeCurrentTUs()
	{
		tuInstances.values().forEach(TULoaderInstance::closeCurrentTU);
	}

	private static void assertCU(final @NonNull I_M_HU cuHU)
	{
		if (!HuPackingInstructionsVersionId.ofRepoId(cuHU.getM_HU_PI_Version_ID()).isVirtual())
		{
			throw new AdempiereException("Expected to be CU: " + cuHU);
		}
	}

	private TULoaderInstanceKey extractTULoaderInstanceKeyFromCU(final I_M_HU cuHU)
	{
		return TULoaderInstanceKey.builder()
				.bpartnerId(IHandlingUnitsBL.extractBPartnerIdOrNull(cuHU))
				.bpartnerLocationRepoId(cuHU.getC_BPartner_Location_ID())
				.locatorId(warehouseDAO.getLocatorIdByRepoIdOrNull(cuHU.getM_Locator_ID()))
				.huStatus(cuHU.getHUStatus())
				.clearanceStatusInfo(ClearanceStatusInfo.ofHU(cuHU))
				.build();
	}

	private TULoaderInstance newTULoaderInstance(@NonNull final TULoaderInstanceKey key)
	{
		return TULoaderInstance.builder()
				.huContext(huContext)
				.tuPI(tuPI)
				.capacity(capacity)
				.bpartnerId(key.getBpartnerId())
				.bpartnerLocationRepoId(key.getBpartnerLocationRepoId())
				.locatorId(key.getLocatorId())
				.huStatus(key.getHuStatus())
				.clearanceStatusInfo(key.getClearanceStatusInfo())
				.build();
	}

	//
	//
	//

	@Value
	private static class TULoaderInstanceKey
	{
		@Nullable BPartnerId bpartnerId;
		int bpartnerLocationRepoId;
		@Nullable LocatorId locatorId;
		@Nullable String huStatus;
		@Nullable ClearanceStatusInfo clearanceStatusInfo;

		@Builder
		private TULoaderInstanceKey(
				@Nullable final BPartnerId bpartnerId,
				final int bpartnerLocationRepoId,
				@Nullable final LocatorId locatorId,
				@Nullable final String huStatus,
				@Nullable final ClearanceStatusInfo clearanceStatusInfo)
		{
			this.bpartnerId = bpartnerId;
			this.bpartnerLocationRepoId = bpartnerLocationRepoId > 0 ? bpartnerLocationRepoId : -1;
			this.locatorId = locatorId;
			this.huStatus = StringUtils.trimBlankToNull(huStatus);
			this.clearanceStatusInfo = clearanceStatusInfo;
		}
	}

}
