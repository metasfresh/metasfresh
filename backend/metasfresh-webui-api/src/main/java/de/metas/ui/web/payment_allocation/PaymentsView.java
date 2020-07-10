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

import com.google.common.collect.ImmutableList;
import de.metas.i18n.TranslatableStrings;
import de.metas.payment.PaymentId;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewRowOverrides;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;

public class PaymentsView extends AbstractCustomView<PaymentRow> implements IViewRowOverrides
{
	@Nullable
	public static PaymentsView cast(@Nullable final IView view)
	{
		return (PaymentsView)view;
	}

	private final ImmutableList<RelatedProcessDescriptor> processes;

	@Getter
	private final InvoicesView invoicesView;

	@Builder
	private PaymentsView(
			@NonNull final ViewId paymentViewId,
			final PaymentAndInvoiceRows rows,
			@Nullable final List<RelatedProcessDescriptor> paymentsProcesses,
			@Nullable final List<RelatedProcessDescriptor> invoicesProcesses)
	{
		super(paymentViewId,
				TranslatableStrings.empty(),
				rows.getPaymentRows(),
				NullDocumentFilterDescriptorsProvider.instance);

		invoicesView = InvoicesView.builder()
				.viewId(paymentViewId.withWindowId(InvoicesViewFactory.WINDOW_ID))
				.rows(rows.getInvoiceRows())
				.processes(invoicesProcesses)
				.build();

		this.processes = paymentsProcesses != null ? ImmutableList.copyOf(paymentsProcesses) : ImmutableList.of();
	}

	@Override
	public String getTableNameOrNull(final DocumentId documentId)
	{
		return null;
	}

	@Override
	public ViewId getIncludedViewId(final IViewRow row)
	{
		return invoicesView.getViewId();
	}

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
	{
		return processes;
	}

	@Override
	protected PaymentRows getRowsData()
	{
		return PaymentRows.cast(super.getRowsData());
	}

	public void addPayment(@NonNull final PaymentId paymentId)
	{
		final PaymentRows paymentRows = getRowsData();
		paymentRows.addPayment(paymentId);
	}
}
