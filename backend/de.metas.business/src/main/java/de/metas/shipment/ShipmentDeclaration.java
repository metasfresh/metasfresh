package de.metas.shipment;

import java.time.LocalDate;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocTypeId;
import de.metas.inout.InOutId;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

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
@Data
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShipmentDeclaration
{
	@NonFinal
	ShipmentDeclarationId id;

	@NonNull
	String documentNo;

	@NonNull
	OrgId orgId;

	@NonNull
	InOutId shipmentId;

	@NonNull
	BPartnerLocationId bpartnerAndLocationId;

	@Nullable
	UserId userId;

	@NonNull
	DocTypeId docTypeId;

	@NonNull
	LocalDate shipmentDate;

	@NonNull
	String docAction;

	@NonNull
	String docStatus;

	@Nullable
	@NonFinal
	ShipmentDeclarationId baseShipmentDeclarationId;

	@Nullable
	@NonFinal
	ShipmentDeclarationId correctionShipmentDeclarationId;

	@NonNull
	ImmutableList<ShipmentDeclarationLine> lines;

	public void updateLineNos()
	{
		int nextLineNo = 10;
		for (ShipmentDeclarationLine line : lines)
		{
			line.setLineNo(nextLineNo);
			nextLineNo += 10;
		}
	}

	public ShipmentDeclaration copyToNew(
			@NonNull final DocTypeId newDocTypeId,
			@NonNull final String newDocAction)
	{
		final ImmutableList<ShipmentDeclarationLine> newLines = getLines()
				.stream()
				.map(ShipmentDeclarationLine::copyToNew)
				.collect(ImmutableList.toImmutableList());

		return toBuilder()
				.id(null)
				.docTypeId(newDocTypeId)
				.docAction(newDocAction)
				.lines(newLines)
				.build();
	}
}
