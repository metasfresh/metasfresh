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

package de.metas.ui.web.bankstatement_reconciliation;

import java.util.stream.Stream;

import de.metas.i18n.TranslatableStrings;
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
import de.metas.util.Check;
import lombok.NonNull;

import javax.annotation.Nullable;

@ViewFactory(windowId = PaymentsToReconcileViewFactory.WINDOW_ID_String)
public class PaymentsToReconcileViewFactory implements IViewFactory, IViewsIndexStorage
{
	static final String WINDOW_ID_String = "paymentsToReconcile";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_String);

	private final BankStatementReconciliationViewFactory banksStatementReconciliationViewFactory;

	public PaymentsToReconcileViewFactory(
			@NonNull final BankStatementReconciliationViewFactory banksStatementReconciliationViewFactory)
	{
		this.banksStatementReconciliationViewFactory = banksStatementReconciliationViewFactory;
	}

	@Override
	public void setViewsRepository(@NonNull final IViewsRepository viewsRepository)
	{
		// nothing
	}

	@Override
	public IView createView(final @NonNull CreateViewRequest request)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public ViewLayout getViewLayout(
			final WindowId windowId,
			final JSONViewDataType viewDataType,
			final ViewProfileId profileId)
	{
		Check.assumeEquals(windowId, WINDOW_ID, "windowId");

		return ViewLayout.builder()
				.setWindowId(WINDOW_ID)
				.setCaption(TranslatableStrings.empty())
				.setAllowOpeningRowDetails(false)
				.addElementsFromViewRowClass(PaymentToReconcileRow.class, viewDataType)
				.build();
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
	public PaymentsToReconcileView getByIdOrNull(@NonNull final ViewId paymentsToReconcileViewId)
	{
		final ViewId bankStatementReconciliationViewId = toBankStatementReconciliationViewId(paymentsToReconcileViewId);
		final BankStatementReconciliationView bankStatementReconciliationView = banksStatementReconciliationViewFactory.getByIdOrNull(bankStatementReconciliationViewId);
		return bankStatementReconciliationView != null
				? bankStatementReconciliationView.getPaymentsToReconcileView()
				: null;
	}

	private static ViewId toBankStatementReconciliationViewId(@NonNull final ViewId paymentsToReconcileViewId)
	{
		return paymentsToReconcileViewId.withWindowId(BankStatementReconciliationViewFactory.WINDOW_ID);
	}

	@Override
	public void closeById(final ViewId viewId, final ViewCloseAction closeAction)
	{
		// nothing
	}

	@Override
	public Stream<IView> streamAllViews()
	{
		return banksStatementReconciliationViewFactory.streamAllViews()
				.map(BankStatementReconciliationView::cast)
				.map(BankStatementReconciliationView::getPaymentsToReconcileView);
	}

	@Override
	public void invalidateView(final ViewId paymentsToReconcileViewId)
	{
		final PaymentsToReconcileView paymentsToReconcileView = getByIdOrNull(paymentsToReconcileViewId);
		if (paymentsToReconcileView != null)
		{
			paymentsToReconcileView.invalidateAll();
		}
	}
}
