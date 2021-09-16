/*
 * #%L
 * de.metas.swat.base
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

package de.metas.picking.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.NumberUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Optional;

@EqualsAndHashCode
public class PickingSlotBarcode
{
	@JsonCreator
	public static PickingSlotBarcode ofBarcodeString(@NonNull final String barcode)
	{
		if (!barcode.startsWith(PREFIX))
		{
			throw new AdempiereException("Invalid picking slot barcode: " + barcode);
		}

		final String pickingSlotIdStr = barcode.substring(PREFIX.length());
		final Integer pickingSlotIdInt = NumberUtils.asInteger(pickingSlotIdStr, null);
		if (pickingSlotIdInt == null)
		{
			throw new AdempiereException("Invalid picking slot barcode: " + barcode);
		}

		final PickingSlotId pickingSlotId = PickingSlotId.ofRepoIdOrNull(pickingSlotIdInt);
		if (pickingSlotId == null)
		{
			throw new AdempiereException("Invalid picking slot barcode: " + barcode);
		}

		return new PickingSlotBarcode(pickingSlotId);
	}

	public static Optional<PickingSlotBarcode> optionalOfBarcodeString(@Nullable final String barcode)
	{
		return barcode != null && !barcode.isEmpty()
				? Optional.of(ofBarcodeString(barcode))
				: Optional.empty();
	}

	public static PickingSlotBarcode ofPickingSlotId(@NonNull final PickingSlotId pickingSlotId)
	{
		return new PickingSlotBarcode(pickingSlotId);
	}

	private static final String PREFIX = "pickingSlot-";

	@Getter
	private final PickingSlotId pickingSlotId;

	private transient String stringRepresentation = null;

	private PickingSlotBarcode(@NonNull final PickingSlotId pickingSlotId) {this.pickingSlotId = pickingSlotId;}

	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	@JsonValue
	public String getAsString()
	{
		String stringRepresentation = this.stringRepresentation;
		if (stringRepresentation == null)
		{
			stringRepresentation = this.stringRepresentation = PREFIX + pickingSlotId.getRepoId();
		}
		return stringRepresentation;
	}
}
