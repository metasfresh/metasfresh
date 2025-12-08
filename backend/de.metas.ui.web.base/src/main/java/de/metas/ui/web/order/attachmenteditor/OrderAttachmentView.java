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

import de.metas.attachments.AttachmentEntryService;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;

public class OrderAttachmentView extends AbstractCustomView<OrderAttachmentRow> implements IEditableView
{
	private final AttachmentEntryService attachmentEntryService;

	@Builder
	public OrderAttachmentView(
			@NonNull final AttachmentEntryService attachmentEntryService,
			@NonNull final ViewId viewId,
			@Nullable final ITranslatableString description,
			@NonNull final OrderAttachmentRows rows)
	{
		super(viewId, description, rows, NullDocumentFilterDescriptorsProvider.instance);

		this.attachmentEntryService = attachmentEntryService;
	}

	@Nullable
	@Override
	public String getTableNameOrNull(@Nullable final DocumentId documentId)
	{
		return null;
	}

	@Override
	public void close(final ViewCloseAction closeAction)
	{
		if (closeAction.isDone())
		{
			saveChanges();
		}
	}

	@Override
	protected OrderAttachmentRows getRowsData()
	{
		return OrderAttachmentRows.cast(super.getRowsData());
	}

	private void saveChanges()
	{
		getRowsData().createAttachmentLinksRequestList()
				.ifPresent(userChanges -> userChanges.forEach(attachmentEntryService::handleAttachmentLinks));
	}
}
