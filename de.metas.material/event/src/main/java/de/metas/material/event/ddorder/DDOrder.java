package de.metas.material.event.ddorder;

import java.util.Date;
import java.util.List;

import org.compiere.model.I_S_Resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;

/*
 * #%L
 * metasfresh-mrp
 * %%
 * Copyright (C) 2017 metas GmbH
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
@AllArgsConstructor // used by jackson when it deserializes a string
final public class DDOrder
{

	/**
	 * {@code AD_Org_ID} of the <b>receiving</b> organization.
	 */
	@NonNull
	private final Integer orgId;

	/**
	 * The {@link I_S_Resource#getS_Resource_ID()} of the plant, as specified by the respective product planning record.
	 */
	@NonNull
	private final Integer plantId;

	@NonNull
	private final Integer productPlanningId;

	@NonNull
	private final Date datePromised;

	@NonNull
	private final Integer shipperId;

	/**
	 * If {@code true}, then this event advises the recipient to directly request an actual DD_Order to be created.
	 */
	private final boolean createDDrder;

	@Singular
	private final List<DDOrderLine> lines;

	private final int ddOrderId;

	private final String docStatus;
}
