package de.metas.shipment;

import java.time.LocalDate;

import org.adempiere.service.OrgId;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocTypeId;
import de.metas.inout.InOutId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
@Value
@Builder
public class ShipmentDeclaration
{

	ShipmentDeclarationId shipmentDeclarationId;

	@NonNull
	OrgId orgId;

	@NonNull
	InOutId shipmentId;

	@NonNull
	BPartnerLocationId bpartnerAndLocationId;

	@NonNull
	DocTypeId docTypeId;

	@NonNull
	LocalDate shipmentDate;


	@Getter(AccessLevel.PRIVATE)
	ImmutableList<ShipmentDeclarationLineId> lines;


}
