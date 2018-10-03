package de.metas.ui.web.pickingV2.packageable;

import org.adempiere.warehouse.WarehouseTypeId;

import com.google.common.base.Joiner;

import de.metas.order.OrderId;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value(staticConstructor = "of")
public class PackageableRowId
{
	@NonNull
	OrderId orderId;
	WarehouseTypeId warehouseTypeId;

	@Getter(lazy = true)
	DocumentId documentId = createDocumentId();

	private DocumentId createDocumentId()
	{
		final String documentIdStr = Joiner.on("_")
				.skipNulls()
				.join(orderId.getRepoId(),
						warehouseTypeId != null ? warehouseTypeId.getRepoId() : null);
		return DocumentId.of(documentIdStr);
	}
}
