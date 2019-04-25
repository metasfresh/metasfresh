package de.metas.shipment;

import java.time.LocalDate;

import javax.annotation.Nullable;

import org.adempiere.service.OrgId;
import org.adempiere.user.UserId;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocTypeId;
import de.metas.inout.InOutId;
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
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShipmentDeclaration
{
	@NonFinal
	ShipmentDeclarationId id;

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
}
