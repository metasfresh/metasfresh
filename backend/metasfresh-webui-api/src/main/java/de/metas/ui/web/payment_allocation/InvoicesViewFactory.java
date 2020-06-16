/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.payment_allocation;

import java.util.stream.Stream;

import de.metas.i18n.IMsgBL;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.IViewsIndexStorage;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Services;
import lombok.NonNull;

import javax.annotation.Nullable;

@ViewFactory(windowId = InvoicesViewFactory.WINDOW_ID_String)
public class InvoicesViewFactory implements IViewFactory, IViewsIndexStorage
{
	static final String WINDOW_ID_String = "invoicesToAllocate"; // FIXME: HARDCODED
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_String);

	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final PaymentsViewFactory paymentsViewFactory;

	public InvoicesViewFactory(@NonNull final PaymentsViewFactory paymentsViewFactory)
	{
		this.paymentsViewFactory = paymentsViewFactory;
	}

	@Override
	public void setViewsRepository(@NonNull final IViewsRepository viewsRepository)
	{
		// nothing
	}

	@Override
	public ViewLayout getViewLayout(final WindowId windowId, final JSONViewDataType viewDataType, final ViewProfileId profileId)
	{
		return ViewLayout.builder()
				.setWindowId(WINDOW_ID)
				.setCaption(msgBL.translatable("InvoicesToAllocate"))
				.setAllowOpeningRowDetails(false)
				.addElementsFromViewRowClass(InvoiceRow.class, viewDataType)
				.build();
	}

	@Override
	public InvoicesView createView(final @NonNull CreateViewRequest request)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public WindowId getWindowId()
	{
		return WINDOW_ID;
	}

	@Override
	public void put(final IView view)
	{
		throw new UnsupportedOperationException();
	}

	@Nullable
	@Override
	public InvoicesView getByIdOrNull(final ViewId invoicesViewId)
	{
		final ViewId paymentsViewId = toPaymentsViewId(invoicesViewId);
		final PaymentsView paymentsView = paymentsViewFactory.getByIdOrNull(paymentsViewId);
		return paymentsView != null
				? paymentsView.getInvoicesView()
				: null;
	}

	private static ViewId toPaymentsViewId(final ViewId invoicesViewId)
	{
		return invoicesViewId.withWindowId(PaymentsViewFactory.WINDOW_ID);
	}

	@Override
	public void closeById(final ViewId viewId, final ViewCloseAction closeAction)
	{
	}

	@Override
	public Stream<IView> streamAllViews()
	{
		return paymentsViewFactory.streamAllViews()
				.map(PaymentsView::cast)
				.map(PaymentsView::getInvoicesView);
	}

	@Override
	public void invalidateView(final ViewId invoicesViewId)
	{
		final InvoicesView invoicesView = getByIdOrNull(invoicesViewId);
		if (invoicesView != null)
		{
			invoicesView.invalidateAll();
		}
	}
}
