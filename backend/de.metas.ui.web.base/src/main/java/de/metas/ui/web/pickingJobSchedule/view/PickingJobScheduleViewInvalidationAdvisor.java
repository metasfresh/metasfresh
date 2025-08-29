package de.metas.ui.web.pickingJobSchedule.view;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.model.I_M_Picking_Job_Schedule;
import de.metas.handlingunits.picking.job_schedule.model.PickingJobScheduleId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.ui.web.view.DefaultView;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewInvalidationAdvisor;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PickingJobScheduleViewInvalidationAdvisor implements IViewInvalidationAdvisor
{
	@Override
	public WindowId getWindowId() {return PickingJobScheduleViewHelper.WINDOW_ID;}

	@Override
	public Set<DocumentId> findAffectedRowIds(final TableRecordReferenceSet recordRefs, final boolean watchedByFrontend, final IView view)
	{
		final DefaultView sqlView = DefaultView.cast(view);
		if (!watchedByFrontend)
		{
			sqlView.invalidateSelection();
			return ImmutableSet.of();
		}

		final HashSet<ShipmentScheduleId> shipmentScheduleIds = new HashSet<>();
		final HashSet<PickingJobScheduleId> pickingJobScheduleIds = new HashSet<>();
		for (final TableRecordReference recordRef : recordRefs)
		{
			if (recordRef.tableNameEqualsTo(I_M_ShipmentSchedule.Table_Name))
			{
				shipmentScheduleIds.add(ShipmentScheduleId.ofRepoId(recordRef.getRecord_ID()));
			}
			else if (recordRef.tableNameEqualsTo(I_M_Picking_Job_Schedule.Table_Name))
			{
				pickingJobScheduleIds.add(PickingJobScheduleId.ofRepoId(recordRef.getRecord_ID()));
			}
		}

		final HashSet<DocumentId> rowIds = new HashSet<>();
		rowIds.addAll(sqlView.retrieveRowIdsByPartialKeyFromSelection(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleIds));
		rowIds.addAll(sqlView.retrieveRowIdsByPartialKeyFromSourceTable(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleIds));
		rowIds.addAll(sqlView.retrieveRowIdsByPartialKeyFromSelection(I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID, pickingJobScheduleIds));
		rowIds.addAll(sqlView.retrieveRowIdsByPartialKeyFromSourceTable(I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID, pickingJobScheduleIds));
		return rowIds;
	}
}
