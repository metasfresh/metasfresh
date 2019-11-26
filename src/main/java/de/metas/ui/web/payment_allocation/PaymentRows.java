package de.metas.ui.web.payment_allocation;

import java.util.List;
import java.util.Map;

import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
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

public class PaymentRows implements IRowsData<PaymentRow>
{
	private final ImmutableList<DocumentId> rowIds; // used to preserve the order
	private final ImmutableMap<DocumentId, PaymentRow> rowsById;

	@Builder
	private PaymentRows(
			@NonNull final List<PaymentRow> rows)
	{
		Check.assumeNotEmpty(rows, "rows is not empty");

		rowIds = rows.stream()
				.map(PaymentRow::getId)
				.collect(ImmutableList.toImmutableList());

		rowsById = Maps.uniqueIndex(rows, PaymentRow::getId);
	}

	@Override
	public Map<DocumentId, PaymentRow> getDocumentId2TopLevelRows()
	{
		return rowIds.stream()
				.map(rowsById::get)
				.collect(GuavaCollectors.toImmutableMapByKey(PaymentRow::getId));
	}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs)
	{
		return DocumentIdsSelection.EMPTY;
	}

	@Override
	public void invalidateAll()
	{
		// nothing
	}
}
