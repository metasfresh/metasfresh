package de.metas.ui.web.payment_allocation;

import java.util.Set;

import de.metas.i18n.IMsgBL;
import de.metas.payment.PaymentId;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.IncludedViewLayout;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@ViewFactory(windowId = PaymentViewFactory.WINDOW_ID_String)
public class PaymentViewFactory implements IViewFactory
{
	static final String WINDOW_ID_String = "540759"; // FIXME: HARDCODED
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_String);

	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final PaymentAndInvoiceRowsRepo rowsRepo;

	public PaymentViewFactory(
			@NonNull final PaymentAndInvoiceRowsRepo rowsRepo)
	{
		this.rowsRepo = rowsRepo;
	}

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType, final ViewProfileId profileId)
	{
		return ViewLayout.builder()
				.setWindowId(WINDOW_ID)
				.setCaption(msgBL.translatable("PaymentAllocation"))
				.setAllowOpeningRowDetails(false)
				.allowViewCloseAction(ViewCloseAction.DONE)
				.setIncludedViewLayout(IncludedViewLayout.builder()
						.openOnSelect(true)
						.blurWhenOpen(false)
						.build())
				.addElementsFromViewRowClass(PaymentRow.class, viewDataType)
				.build();
	}

	@Override
	public PaymentsView createView(final CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();
		viewId.assertWindowId(WINDOW_ID);

		final Set<PaymentId> paymentIds = PaymentId.fromIntSet(request.getFilterOnlyIds());
		final PaymentAndInvoiceRows paymentAndInvoiceRows = rowsRepo.getByPaymentIds(paymentIds);

		return PaymentsView.builder()
				.paymentViewId(viewId)
				.rows(paymentAndInvoiceRows)
				.build();
	}

}
