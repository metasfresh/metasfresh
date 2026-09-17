package de.metas.distribution.ddorder.replenishment.related_documents;

import com.google.common.collect.ImmutableList;
import de.metas.document.references.related_documents.IRelatedDocumentsProvider;
import de.metas.document.references.related_documents.IZoomSource;
import de.metas.document.references.related_documents.RelatedDocumentsCandidate;
import de.metas.document.references.related_documents.RelatedDocumentsCandidateGroup;
import de.metas.document.references.related_documents.RelatedDocumentsId;
import de.metas.document.references.related_documents.RelatedDocumentsQuerySuppliers;
import de.metas.document.references.related_documents.RelatedDocumentsTargetWindow;
import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Services;
import de.metas.util.lang.Priority;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.compiere.model.MQuery;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Surfaces EVERY delivery contributing to a distribution order as its related shipment schedules, resolved through
 * {@code DD_OrderLine_PickingJobSchedule} — a consolidated order has no single owning shipment schedule to point at.
 */
@Component
public class DDOrderShipmentScheduleRelatedDocumentsProvider implements IRelatedDocumentsProvider
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADWindowDAO windowDAO = Services.get(IADWindowDAO.class);

	@Override
	public List<RelatedDocumentsCandidateGroup> retrieveRelatedDocumentsCandidates(
			@NonNull final IZoomSource fromDocument,
			@Nullable final AdWindowId targetWindowId)
	{
		final String sourceTableName = fromDocument.getTableName();
		if (!fromDocument.isSingleKeyRecord()
				|| !(I_DD_Order.Table_Name.equals(sourceTableName) || I_DD_OrderLine.Table_Name.equals(sourceTableName)))
		{
			return ImmutableList.of();
		}

		final AdWindowId shipmentScheduleWindowId = RecordWindowFinder.findAdWindowId(I_M_ShipmentSchedule.Table_Name).orElse(null);
		if (shipmentScheduleWindowId == null
				|| (targetWindowId != null && !AdWindowId.equals(targetWindowId, shipmentScheduleWindowId)))
		{
			return ImmutableList.of();
		}

		final String sqlWhereClause = sqlShipmentSchedulesOfDDOrder(sourceTableName, fromDocument.getRecord_ID());

		final MQuery query = new MQuery(I_M_ShipmentSchedule.Table_Name);
		query.addRestriction(sqlWhereClause);

		return ImmutableList.of(RelatedDocumentsCandidateGroup.of(
				RelatedDocumentsCandidate.builder()
						.id(RelatedDocumentsId.ofString(I_M_ShipmentSchedule.Table_Name))
						.internalName(I_M_ShipmentSchedule.Table_Name)
						.targetWindow(RelatedDocumentsTargetWindow.ofAdWindowId(shipmentScheduleWindowId))
						.priority(Priority.HIGHEST)
						.querySupplier(RelatedDocumentsQuerySuppliers.ofQuery(query))
						.windowCaption(windowDAO.retrieveWindowName(shipmentScheduleWindowId))
						.documentsCountSupplier(permissions -> countShipmentSchedules(sqlWhereClause))
						.build()));
	}

	private static String sqlShipmentSchedulesOfDDOrder(@NonNull final String sourceTableName, final int recordId)
	{
		final String contributorFilter = I_DD_Order.Table_Name.equals(sourceTableName)
				? " JOIN DD_OrderLine l ON l.DD_OrderLine_ID=a.DD_OrderLine_ID WHERE l.DD_Order_ID=" + recordId
				: " WHERE a.DD_OrderLine_ID=" + recordId;

		return "M_ShipmentSchedule_ID IN ("
				+ " SELECT s.M_ShipmentSchedule_ID FROM M_Picking_Job_Schedule s"
				+ " JOIN DD_OrderLine_PickingJobSchedule a ON a.M_Picking_Job_Schedule_ID=s.M_Picking_Job_Schedule_ID AND a.IsActive='Y'"
				+ contributorFilter
				+ ")";
	}

	private int countShipmentSchedules(@NonNull final String sqlWhereClause)
	{
		return queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
				.filter(TypedSqlQueryFilter.of(sqlWhereClause))
				.create()
				.count();
	}
}
