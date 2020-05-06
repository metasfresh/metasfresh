package de.metas.shipment;

import javax.annotation.Nullable;

import de.metas.document.DocTypeId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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

@Value
public class ShipmentDeclarationConfig
{
	ShipmentDeclarationConfigId id;
	String name;
	int documentLinesNumber;
	DocTypeId docTypeId;
	DocTypeId docTypeCorrectionId;

	@Builder
	private ShipmentDeclarationConfig(
			@NonNull final ShipmentDeclarationConfigId id,
			@NonNull final String name,
			final int documentLinesNumber,
			@NonNull final DocTypeId docTypeId,
			@Nullable final DocTypeId docTypeCorrectionId)
	{
		Check.assume(documentLinesNumber > 0, "documentLinesNumber > 0");

		this.id = id;
		this.name = name;
		this.documentLinesNumber = documentLinesNumber;
		this.docTypeId = docTypeId;
		this.docTypeCorrectionId = docTypeCorrectionId;
	}
}
