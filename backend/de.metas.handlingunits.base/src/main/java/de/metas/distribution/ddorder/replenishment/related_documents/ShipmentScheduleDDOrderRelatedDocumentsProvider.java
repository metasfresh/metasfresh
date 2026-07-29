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
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Surfaces the replenishment {@code DD_Order}s of a shipment schedule as its related documents, resolved through
 * {@code DD_OrderLine_PickingJobSchedule} because a consolidated order serves several shipment schedules.
 */
@Component
public class ShipmentScheduleDDOrderRelatedDocumentsProvider implements IRelatedDocumentsProvider
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADWindowDAO windowDAO = Services.get(IADWindowDAO.class);

	@Override
	public List<RelatedDocumentsCandidateGroup> retrieveRelatedDocumentsCandidates(
			@NonNull final IZoomSource fromDocument,
			@Nullable final AdWindowId targetWindowId)
	{
		if (!fromDocument.isSingleKeyRecord()
				|| !I_M_ShipmentSchedule.Table_Name.equals(fromDocument.getTableName()))
		{
			return ImmutableList.of();
		}

		final AdWindowId ddOrderWindowId = RecordWindowFinder.findAdWindowId(I_DD_Order.Table_Name).orElse(null);
		if (ddOrderWindowId == null
				|| (targetWindowId != null && !AdWindowId.equals(targetWindowId, ddOrderWindowId)))
		{
			return ImmutableList.of();
		}

		final String sqlWhereClause = sqlDDOrdersOfShipmentSchedule(fromDocument.getRecord_ID());

		final MQuery query = new MQuery(I_DD_Order.Table_Name);
		query.addRestriction(sqlWhereClause);

		return ImmutableList.of(RelatedDocumentsCandidateGroup.of(
				RelatedDocumentsCandidate.builder()
						.id(RelatedDocumentsId.ofString(I_DD_Order.Table_Name))
						.internalName(I_DD_Order.Table_Name)
						.targetWindow(RelatedDocumentsTargetWindow.ofAdWindowId(ddOrderWindowId))
						.priority(Priority.HIGHEST)
						.querySupplier(RelatedDocumentsQuerySuppliers.ofQuery(query))
						.windowCaption(windowDAO.retrieveWindowName(ddOrderWindowId))
						.documentsCountSupplier(permissions -> countDDOrders(sqlWhereClause))
						.build()));
	}

	private static String sqlDDOrdersOfShipmentSchedule(final int shipmentScheduleId)
	{
		return "DD_Order_ID IN ("
				+ " SELECT l.DD_Order_ID FROM DD_OrderLine l"
				+ " JOIN DD_OrderLine_PickingJobSchedule a ON a.DD_OrderLine_ID=l.DD_OrderLine_ID AND a.IsActive='Y'"
				+ " JOIN M_Picking_Job_Schedule s ON s.M_Picking_Job_Schedule_ID=a.M_Picking_Job_Schedule_ID"
				+ " WHERE s.M_ShipmentSchedule_ID=" + shipmentScheduleId
				+ ")";
	}

	private int countDDOrders(@NonNull final String sqlWhereClause)
	{
		return queryBL.createQueryBuilder(I_DD_Order.class)
				.filter(TypedSqlQueryFilter.of(sqlWhereClause))
				.create()
				.count();
	}
}
