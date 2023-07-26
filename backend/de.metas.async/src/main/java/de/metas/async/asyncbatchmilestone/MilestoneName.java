/*
 * #%L
 * de.metas.async
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

package de.metas.async.asyncbatchmilestone;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.async.model.X_C_Async_Batch_Milestone;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Arrays;

@AllArgsConstructor
public enum MilestoneName implements ReferenceListAwareEnum
{
	SALES_ORDER_CREATION(X_C_Async_Batch_Milestone.NAME_Auftragserstellung),
	SHIPMENT_CREATION(X_C_Async_Batch_Milestone.NAME_Lieferungserstellung),
	INVOICE_CREATION(X_C_Async_Batch_Milestone.NAME_Rechnungserstellung),
	;

	@Getter
	String code;

	@JsonCreator
	public static MilestoneName ofCode(@NonNull final String code)
	{
		final MilestoneName name = namesByCode.get(code);
		if (name == null)
		{
			throw new AdempiereException("No " + MilestoneName.class + " found for code: " + code);
		}
		return name;
	}

	private static final ImmutableMap<String, MilestoneName> namesByCode = Maps.uniqueIndex(Arrays.asList(values()), MilestoneName::getCode);
}
