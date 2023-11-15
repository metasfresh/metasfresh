package de.metas.deliveryplanning.related_documents;

import com.google.common.collect.ImmutableList;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningReceiptInfo;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.deliveryplanning.DeliveryPlanningShipmentInfo;
import de.metas.document.references.related_documents.IRelatedDocumentsProvider;
import de.metas.document.references.related_documents.IZoomSource;
import de.metas.document.references.related_documents.RelatedDocumentsCandidate;
import de.metas.document.references.related_documents.RelatedDocumentsCandidateGroup;
import de.metas.document.references.related_documents.RelatedDocumentsId;
import de.metas.document.references.related_documents.RelatedDocumentsQuerySuppliers;
import de.metas.document.references.related_documents.RelatedDocumentsTargetWindow;
import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.inout.InOutId;
import de.metas.order.OrderId;
import de.metas.util.Services;
import de.metas.util.lang.Priority;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_InOut;
import org.compiere.model.MQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DeliveryPlanningRelatedDocumentsProvider implements IRelatedDocumentsProvider
{
	private final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);
	private final DeliveryPlanningService deliveryPlanningService;

	public DeliveryPlanningRelatedDocumentsProvider(
			@NonNull final DeliveryPlanningService deliveryPlanningService)
	{
		this.deliveryPlanningService = deliveryPlanningService;
	}

	@Override
	public List<RelatedDocumentsCandidateGroup> retrieveRelatedDocumentsCandidates(
			@NonNull final IZoomSource fromDocument,
			@Nullable final AdWindowId targetWindowId)
	{
		if (I_M_Delivery_Planning.Table_Name.equals(fromDocument.getTableName()))
		{
			final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(fromDocument.getRecord_ID());
			return getForDeliveryPlanning(deliveryPlanningId, targetWindowId);

		}
		else
		{
			return ImmutableList.of();
		}
	}

	private List<RelatedDocumentsCandidateGroup> getForDeliveryPlanning(
			@NonNull final DeliveryPlanningId deliveryPlanningId,
			@Nullable final AdWindowId expectedWindowId)
	{
		return deliveryPlanningService.getShipmentOrReceiptInfo(
				deliveryPlanningId,
				receiptInfo -> getForReceiptInfo(receiptInfo, expectedWindowId),
				shipmentInfo -> getForShipmentInfo(shipmentInfo, expectedWindowId)
		);
	}

	private List<RelatedDocumentsCandidateGroup> getForReceiptInfo(
			final @NonNull DeliveryPlanningReceiptInfo receiptInfo,
			final @Nullable AdWindowId expectedWindowId)
	{
		final ArrayList<RelatedDocumentsCandidateGroup> result = new ArrayList<>();

		Optional.ofNullable(receiptInfo.getPurchaseOrderId())
				.flatMap(purchaseOrderId -> toRelatedDocumentsCandidate(purchaseOrderId, expectedWindowId))
				.ifPresent(result::add);

		Optional.ofNullable(receiptInfo.getReceiptId())
				.flatMap(receiptId -> toRelatedDocumentsCandidate(receiptId, expectedWindowId))
				.ifPresent(result::add);

		return result;
	}

	private List<RelatedDocumentsCandidateGroup> getForShipmentInfo(
			final @NonNull DeliveryPlanningShipmentInfo shipmentInfo,
			final @Nullable AdWindowId expectedWindowId)
	{
		final ArrayList<RelatedDocumentsCandidateGroup> result = new ArrayList<>();

		Optional.ofNullable(shipmentInfo.getSalesOrderId())
				.flatMap(salesOrderId -> toRelatedDocumentsCandidate(salesOrderId, expectedWindowId))
				.ifPresent(result::add);

		Optional.ofNullable(shipmentInfo.getShipmentId())
				.flatMap(shipmentId -> toRelatedDocumentsCandidate(shipmentId, expectedWindowId))
				.ifPresent(result::add);

		return result;
	}

	private Optional<RelatedDocumentsCandidateGroup> toRelatedDocumentsCandidate(@NonNull final OrderId orderId, @Nullable final AdWindowId expectedWindowId)
	{
		final AdWindowId windowId = getWindowId(I_C_Order.Table_Name, orderId, expectedWindowId);
		if (windowId == null)
		{
			return Optional.empty();
		}

		final MQuery query = new MQuery(I_C_Order.Table_Name);
		query.addRestriction(I_C_Order.COLUMNNAME_C_Order_ID, MQuery.Operator.EQUAL, orderId);

		return Optional.of(
				RelatedDocumentsCandidateGroup.of(
						RelatedDocumentsCandidate.builder()
								.id(RelatedDocumentsId.ofString(I_C_Order.Table_Name))
								.internalName(I_C_Order.Table_Name)
								.targetWindow(RelatedDocumentsTargetWindow.ofAdWindowId(windowId))
								.priority(Priority.HIGHEST)
								.querySupplier(RelatedDocumentsQuerySuppliers.ofQuery(query))
								.windowCaption(adWindowDAO.retrieveWindowName(windowId))
								.documentsCountSupplier(permissions -> 1)
								.build()
				)
		);
	}

	private Optional<RelatedDocumentsCandidateGroup> toRelatedDocumentsCandidate(@NonNull final InOutId inoutId, @Nullable final AdWindowId expectedWindowId)
	{
		final AdWindowId windowId = getWindowId(I_M_InOut.Table_Name, inoutId, expectedWindowId);
		if (windowId == null)
		{
			return Optional.empty();
		}

		final MQuery query = new MQuery(I_M_InOut.Table_Name);
		query.addRestriction(I_M_InOut.COLUMNNAME_M_InOut_ID, MQuery.Operator.EQUAL, inoutId);

		return Optional.of(
				RelatedDocumentsCandidateGroup.of(
						RelatedDocumentsCandidate.builder()
								.id(RelatedDocumentsId.ofString(I_M_InOut.Table_Name))
								.internalName(I_M_InOut.Table_Name)
								.targetWindow(RelatedDocumentsTargetWindow.ofAdWindowId(windowId))
								.priority(Priority.HIGHEST)
								.querySupplier(RelatedDocumentsQuerySuppliers.ofQuery(query))
								.windowCaption(adWindowDAO.retrieveWindowName(windowId))
								.documentsCountSupplier(permissions -> 1)
								.build()
				)
		);
	}

	@Nullable
	private AdWindowId getWindowId(
			@NonNull final String tableName,
			@NonNull final RepoIdAware id,
			@Nullable final AdWindowId expectedWindowId)
	{
		final AdWindowId windowId = RecordWindowFinder.newInstance(tableName, id.getRepoId())
				.checkRecordPresentInWindow()
				.findAdWindowId()
				.orElse(null);
		if (windowId == null)
		{
			return null;
		}

		if (expectedWindowId != null && !AdWindowId.equals(windowId, expectedWindowId))
		{
			return null;
		}

		return windowId;
	}
}
