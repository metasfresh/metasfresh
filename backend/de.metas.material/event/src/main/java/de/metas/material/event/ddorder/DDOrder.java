package de.metas.material.event.ddorder;

import java.time.Instant;
import java.util.List;

import org.compiere.model.I_S_Resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

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
@Value
public class DDOrder
{

	/**
	 * {@code AD_Org_ID} of the <b>receiving</b> organization.
	 */
	OrgId orgId;

	/**
	 * The {@link I_S_Resource#getS_Resource_ID()} of the plant, as specified by the respective product planning record.
	 */
	int plantId;

	int productPlanningId;

	Instant datePromised;

	int shipperId;

	@Singular
	List<DDOrderLine> lines;

	int ddOrderId;

	String docStatus;

	/**
	 * Not persisted in the {@code DD_Order} data record, but
	 * when material-dispo posts {@link DDOrderRequestedEvent}, it contains a group-ID,
	 * and the respective {@link DDOrderCreatedEvent} contains the same group-ID.
	 */
	MaterialDispoGroupId materialDispoGroupId;

	@JsonCreator
	@Builder
	private DDOrder(
			@JsonProperty("orgId") @NonNull final OrgId orgId,
			@JsonProperty("plantId") final int plantId,
			@JsonProperty("productPlanningId") final int productPlanningId,
			@JsonProperty("datePromised") @NonNull final Instant datePromised,
			@JsonProperty("shipperId") final int shipperId,
			@JsonProperty("lines") @Singular final List<DDOrderLine> lines,
			@JsonProperty("ddOrderId") final int ddOrderId,
			@JsonProperty("docStatus") final String docStatus,
			@JsonProperty("materialDispoGroupId") final MaterialDispoGroupId materialDispoGroupId)
	{
		this.orgId = orgId;
		
		// these two might be zero, if the DDOrder was created manually
		this.plantId = plantId;
		this.productPlanningId = productPlanningId;

		this.datePromised = datePromised;
		this.shipperId = shipperId;
		this.lines = lines;
		this.ddOrderId = ddOrderId;
		this.docStatus = docStatus;
		this.materialDispoGroupId = materialDispoGroupId;
	}
}
