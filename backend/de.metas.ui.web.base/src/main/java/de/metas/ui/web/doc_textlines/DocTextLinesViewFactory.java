package de.metas.ui.web.doc_textlines;

import com.google.common.collect.ImmutableList;
import de.metas.doctextline.DocTextLineDocumentRef;
import de.metas.doctextline.DocTextLineRepository;
import de.metas.i18n.ITranslatableString;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.process.RelatedProcessDescriptor.DisplayPlace;
import de.metas.ui.web.doc_textlines.process.WEBUI_DocTextLines_Delete;
import de.metas.ui.web.doc_textlines.process.WEBUI_DocTextLines_InsertAbove;
import de.metas.ui.web.doc_textlines.process.WEBUI_DocTextLines_MoveDown;
import de.metas.ui.web.doc_textlines.process.WEBUI_DocTextLines_MoveUp;
import de.metas.ui.web.doc_textlines.process.WEBUI_Order_DocTextLines_Launcher;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Product;

import java.util.List;

/**
 * Builds {@link DocTextLinesView} -- the merged list of one sales order's article lines (read-only) and text
 * lines (inline-editable). {@link WEBUI_Order_DocTextLines_Launcher} opens this view as a modal from the sales
 * order line tab; all four quick actions (insert-above, delete, move-up, move-down) are registered here via
 * {@link #getRelatedProcessDescriptors()}.
 * <p>
 * Shape copied from {@code shipment_candidates_editor}'s {@code ShipmentCandidatesViewFactory} (a plain
 * {@link IViewFactory}, no window-catalog registration needed for the window id itself) -- see that class for
 * the precedent.
 */
@ViewFactory(windowId = DocTextLinesViewFactory.WINDOW_ID_STRING)
public class DocTextLinesViewFactory implements IViewFactory
{
	static final String WINDOW_ID_STRING = "docTextLines";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	private static final String PARAM_RecordRef = "recordRef";

	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final DocTextLineRepository docTextLineRepository;
	private final LookupDataSource productsLookup;

	public DocTextLinesViewFactory(
			@NonNull final DocTextLineRepository docTextLineRepository,
			@NonNull final LookupDataSourceFactory lookupDataSourceFactory)
	{
		this.docTextLineRepository = docTextLineRepository;
		this.productsLookup = lookupDataSourceFactory.searchInTableLookup(I_M_Product.Table_Name);
	}

	/** Called by {@link WEBUI_Order_DocTextLines_Launcher} to build its {@link CreateViewRequest} from the order's {@link TableRecordReference}. */
	public CreateViewRequest createViewRequest(@NonNull final TableRecordReference recordRef)
	{
		return CreateViewRequest.builder(WINDOW_ID)
				.setParameter(PARAM_RecordRef, recordRef)
				.build();
	}

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType, final ViewProfileId profileId)
	{
		final ITranslatableString caption = Services.get(IADProcessDAO.class)
				.retrieveProcessNameByClassIfUnique(WEBUI_Order_DocTextLines_Launcher.class)
				.orElse(null);

		return ViewLayout.builder()
				.setWindowId(WINDOW_ID)
				.setCaption(caption)
				.setAllowOpeningRowDetails(false)
				.allowViewCloseAction(ViewCloseAction.CANCEL)
				.allowViewCloseAction(ViewCloseAction.DONE)
				.addElementsFromViewRowClass(DocTextLinesRow.class, viewDataType)
				.build();
	}

	@Override
	public DocTextLinesView createView(@NonNull final CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();
		viewId.assertWindowId(WINDOW_ID);

		final OrderId orderId = extractOrderId(request);

		final DocTextLinesRows rows = DocTextLinesRowsLoader.builder()
				.orderDAO(orderDAO)
				.docTextLineRepository(docTextLineRepository)
				.productsLookup(productsLookup)
				.orderId(orderId)
				.build()
				.load();

		return DocTextLinesView.builder()
				.viewId(viewId)
				.rows(rows)
				.documentRef(DocTextLineDocumentRef.ofOrderId(orderId))
				.processes(getRelatedProcessDescriptors())
				.build();
	}

	private static ImmutableList<RelatedProcessDescriptor> getRelatedProcessDescriptors()
	{
		return ImmutableList.of(
				createProcessDescriptor(WEBUI_DocTextLines_InsertAbove.class),
				createProcessDescriptor(WEBUI_DocTextLines_Delete.class),
				createProcessDescriptor(WEBUI_DocTextLines_MoveUp.class),
				createProcessDescriptor(WEBUI_DocTextLines_MoveDown.class));
	}

	private static RelatedProcessDescriptor createProcessDescriptor(@NonNull final Class<?> processClass)
	{
		final AdProcessId processId = Services.get(IADProcessDAO.class).retrieveProcessIdByClass(processClass);
		return RelatedProcessDescriptor.builder()
				.processId(processId)
				.anyTable().anyWindow()
				.displayPlace(DisplayPlace.ViewQuickActions)
				.build();
	}

	private static OrderId extractOrderId(@NonNull final CreateViewRequest request)
	{
		final TableRecordReference recordRef = request.getParameterAs(PARAM_RecordRef, TableRecordReference.class);
		recordRef.assertTableName(I_C_Order.Table_Name);
		return OrderId.ofRepoId(recordRef.getRecord_ID());
	}
}
