/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.handlingunits.shipmentschedule.process;

import com.google.common.collect.ImmutableSet;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.inoutcandidate.ShipmentScheduleQtyPickedId;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import java.util.Set;

public class M_ShipmentSchedule_Unpick extends JavaProcess implements IProcessPrecondition
{
	private final static AdMessageKey DELIVERED_HU_CANNOT_BE_UNPICKED = AdMessageKey.of("de.metas.handlingunits.shipmentschedule.process.Delivered_HUs_Cannot_Be_Unpicked");

	private final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private final PickingCandidateService pickingCandidateService = SpringContextHolder.instance.getBean(PickingCandidateService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		final ImmutableSet<ShipmentScheduleQtyPickedId> selectedLineIds = getSelectedQtyPickedLines(context);
		if (selectedLineIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (isDeliveredLineSelected(selectedLineIds))
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(DELIVERED_HU_CANNOT_BE_UNPICKED));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final ImmutableSet<ShipmentScheduleQtyPickedId> selectedLineIds = getSelectedIncludedRecordIds(I_M_ShipmentSchedule_QtyPicked.class)
				.stream()
				.map(ShipmentScheduleQtyPickedId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		shipmentScheduleAllocDAO.retrieveQtyPickedRecordsByIds(selectedLineIds, I_M_ShipmentSchedule_QtyPicked.class)
				.forEach(this::unpickHu);

		return MSG_OK;
	}

	private void unpickHu(@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPickedRecord)
	{
		final HuId luId = HuId.ofRepoIdOrNull(shipmentScheduleQtyPickedRecord.getM_LU_HU_ID());
		final HuId tuId = HuId.ofRepoIdOrNull(shipmentScheduleQtyPickedRecord.getM_TU_HU_ID());
		final HuId cuId = HuId.ofRepoIdOrNull(shipmentScheduleQtyPickedRecord.getVHU_ID());

		final HuId highestLevelHuId = CoalesceUtil.coalesce(luId, tuId, cuId);
		if (highestLevelHuId == null)
		{
			throw new AdempiereException("No HU was picked!");
		}

		pickingCandidateService.unprocessAndRestoreSourceHUsByHUId(highestLevelHuId);
		pickingCandidateService.removeHUFromPickingSlot(highestLevelHuId);
	}

	private boolean isDeliveredLineSelected(@NonNull final Set<ShipmentScheduleQtyPickedId> lineIds)
	{
		return shipmentScheduleAllocDAO.retrieveQtyPickedRecordsByIds(lineIds, I_M_ShipmentSchedule_QtyPicked.class)
				.stream()
				.map(I_M_ShipmentSchedule_QtyPicked::getM_InOutLine_ID)
				.anyMatch(inOutLineId -> inOutLineId > 0);
	}

	@NonNull
	private ImmutableSet<ShipmentScheduleQtyPickedId> getSelectedQtyPickedLines(final @NonNull IProcessPreconditionsContext context)
	{
		return context
				.getSelectedIncludedRecords()
				.stream()
				.map(reference -> reference.getIdAssumingTableName(I_M_ShipmentSchedule_QtyPicked.Table_Name, ShipmentScheduleQtyPickedId::ofRepoId))
				.collect(ImmutableSet.toImmutableSet());
	}
}
