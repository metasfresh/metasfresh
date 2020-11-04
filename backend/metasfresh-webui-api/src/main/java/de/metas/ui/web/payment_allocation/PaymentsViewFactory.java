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

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import de.metas.common.util.time.SystemTime;
import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.IMsgBL;
import de.metas.payment.PaymentId;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.process.RelatedProcessDescriptor.DisplayPlace;
import de.metas.ui.web.payment_allocation.process.InvoicesView_AddAdditionalInvoice;
import de.metas.ui.web.payment_allocation.process.PaymentsView_AddAdditionalPayment;
import de.metas.ui.web.payment_allocation.process.PaymentsView_Allocate;
import de.metas.ui.web.payment_allocation.process.PaymentsView_AllocateAndDiscount;
import de.metas.ui.web.payment_allocation.process.PaymentsView_AllocateAndWriteOff;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.DefaultViewsRepositoryStorage;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewFactory;
import de.metas.ui.web.view.IViewsIndexStorage;
import de.metas.ui.web.view.IViewsRepository;
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

@ViewFactory(windowId = PaymentsViewFactory.WINDOW_ID_String)
public class PaymentsViewFactory implements IViewFactory, IViewsIndexStorage
{
	static final String WINDOW_ID_String = "540759"; // FIXME: HARDCODED
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_String);

	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final PaymentAndInvoiceRowsRepo rowsRepo;
	private final DefaultViewsRepositoryStorage views = new DefaultViewsRepositoryStorage(Duration.ofHours(1));

	public static final String PARAMETER_TYPE_SET_OF_PAYMENT_IDS = "SET_OF_PAYMENT_IDS";
	public static final String PARAMETER_TYPE_BPARTNER_ID = "BPARTNER_ID";

	public PaymentsViewFactory(
			@NonNull final PaymentAndInvoiceRowsRepo rowsRepo)
	{
		this.rowsRepo = rowsRepo;
	}

	@Override
	public void setViewsRepository(final IViewsRepository viewsRepository)
	{
		// nothing
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
	public PaymentsView createView(final @NonNull CreateViewRequest request)
	{
		final ViewId viewId = request.getViewId();
		viewId.assertWindowId(WINDOW_ID);

		final BPartnerId bPartnerId = request.getParameterAs(PARAMETER_TYPE_BPARTNER_ID, BPartnerId.class);
		final Set<PaymentId> paymentIds = request.getParameterAsSet(PARAMETER_TYPE_SET_OF_PAYMENT_IDS, PaymentId.class);

		final PaymentAndInvoiceRows paymentAndInvoiceRows;

		if (bPartnerId != null)
		{
			paymentAndInvoiceRows = rowsRepo.getByBPartnerId(bPartnerId);
		}
		else if (paymentIds != null && !paymentIds.isEmpty())
		{
			paymentAndInvoiceRows = rowsRepo.getByPaymentIds(paymentIds);
		}
		else
		{
			// if no payments exist, we allow the window to open with no records.
			// The user can manually add Payments and Invoices, then reconcile them.
			final ZonedDateTime evaluationDate = SystemTime.asZonedDateTime();

			paymentAndInvoiceRows = PaymentAndInvoiceRows.builder()
					.invoiceRows(
							InvoiceRows.builder()
									.repository(rowsRepo)
									.evaluationDate(evaluationDate)
									.initialRows(ImmutableList.of())
									.build())
					.paymentRows(
							PaymentRows.builder()
									.repository(rowsRepo)
									.evaluationDate(evaluationDate)
									.initialRows(ImmutableList.of())
									.build())
					.build();
		}

		return PaymentsView.builder()
				.paymentViewId(viewId)
				.rows(paymentAndInvoiceRows)
				.paymentsProcesses(getPaymentRelatedProcessDescriptors())
				.invoicesProcesses(getInvoiceRelatedProcessDescriptors())
				.build();
	}

	private List<RelatedProcessDescriptor> getPaymentRelatedProcessDescriptors()
	{
		return ImmutableList.of(
				createProcessDescriptor(10, PaymentsView_Allocate.class),
				createProcessDescriptor(20, PaymentsView_AllocateAndDiscount.class),
				createProcessDescriptor(30, PaymentsView_AllocateAndWriteOff.class),
				createProcessDescriptor(40, PaymentsView_AddAdditionalPayment.class));
	}

	private List<RelatedProcessDescriptor> getInvoiceRelatedProcessDescriptors()
	{
		return ImmutableList.of(
				createProcessDescriptor(10, InvoicesView_AddAdditionalInvoice.class));
	}

	protected final RelatedProcessDescriptor createProcessDescriptor(final int sortNo, @NonNull final Class<?> processClass)
	{
		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClass(processClass);
		if (processId == null)
		{
			throw new AdempiereException("No processId found for " + processClass);
		}

		return RelatedProcessDescriptor.builder()
				.processId(processId)
				.anyTable().anyWindow()
				.displayPlace(DisplayPlace.ViewQuickActions)
				.sortNo(sortNo)
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
		views.put(view);
	}

	@Nullable
	@Override
	public PaymentsView getByIdOrNull(final ViewId viewId)
	{
		return PaymentsView.cast(views.getByIdOrNull(viewId));
	}

	@Override
	public void closeById(final ViewId viewId, final ViewCloseAction closeAction)
	{
		views.closeById(viewId, closeAction);
	}

	@Override
	public Stream<IView> streamAllViews()
	{
		return views.streamAllViews();
	}

	@Override
	public void invalidateView(final ViewId viewId)
	{
		views.invalidateView(viewId);
	}
}
