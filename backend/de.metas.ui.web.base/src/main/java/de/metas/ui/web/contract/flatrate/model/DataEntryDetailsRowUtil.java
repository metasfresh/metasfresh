/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.ui.web.contract.flatrate.model;

import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryDetail;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryDetailId;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DataEntryDetailsRowUtil
{
	public DocumentId createDocumentId(@NonNull final FlatrateDataEntryDetail detail)
	{
		return createDocumentId(detail.getIdNonNull());
	}

	public DocumentId createDocumentId(@NonNull final FlatrateDataEntryDetailId detailId)
	{
		return DocumentId.of(detailId);
	}
}
