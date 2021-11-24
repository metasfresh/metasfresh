/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.order.attachmenteditor;

import de.metas.attachments.AttachmentEntryRepository;
import de.metas.attachments.AttachmentEntryService;
import de.metas.i18n.IMsgBL;
import de.metas.order.OrderId;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.vertical.healthcare.alberta.bpartner.patient.AlbertaPatientRepository;
import de.metas.vertical.healthcare.alberta.prescription.dao.AlbertaPrescriptionRequestDAO;
import lombok.NonNull;
import org.compiere.model.I_C_Order;

import static de.metas.ui.web.order.attachmenteditor.process.C_Order_AttachmentView_Launcher.PARAM_PURCHASE_ORDER_ID;

@ViewFactory(windowId = OrderAttachmentViewFactory.OrderAttachmentView_String)
public class OrderAttachmentViewFactory implements IViewFactory
{
	public static final String OrderAttachmentView_String = "OrderAttachmentView";
	public static final WindowId WINDOWID = WindowId.fromJson(OrderAttachmentView_String);

	private final AttachmentEntryService attachmentEntryService;
	private final OrderAttachmentRowsRepository rowsRepo;

	public OrderAttachmentViewFactory(
			@NonNull final AttachmentEntryService attachmentEntryService,
			@NonNull final AlbertaPrescriptionRequestDAO albertaPrescriptionRequestDAO,
			@NonNull final AttachmentEntryRepository attachmentEntryRepository,
			@NonNull final AlbertaPatientRepository albertaPatientRepository)
	{
		this.attachmentEntryService = attachmentEntryService;
		this.rowsRepo = OrderAttachmentRowsRepository.builder()
				.albertaPrescriptionRequestDAO(albertaPrescriptionRequestDAO)
				.attachmentEntryRepository(attachmentEntryRepository)
				.albertaPatientRepository(albertaPatientRepository)
				.build();
	}

	@Override
	public IView createView(@NonNull final CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();
		viewId.assertWindowId(WINDOWID);

		final OrderId orderId = request.getParameterAs(PARAM_PURCHASE_ORDER_ID, OrderId.class);

		Check.assumeNotNull(orderId, PARAM_PURCHASE_ORDER_ID + " is mandatory!");

		final OrderAttachmentRows rows = rowsRepo.getByPurchaseOrderId(orderId);

		return OrderAttachmentView.builder()
				.attachmentEntryService(attachmentEntryService)
				.viewId(viewId)
				.rows(rows)
				.build();
	}

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType, final ViewProfileId profileId)
	{
		return ViewLayout.builder()
				.setWindowId(WINDOWID)
				.setCaption(Services.get(IMsgBL.class).translatable(I_C_Order.COLUMNNAME_C_Order_ID))
				.setAllowOpeningRowDetails(false)
				.allowViewCloseAction(ViewCloseAction.DONE)
				.addElementsFromViewRowClass(OrderAttachmentRow.class, viewDataType)
				.build();
	}
}
