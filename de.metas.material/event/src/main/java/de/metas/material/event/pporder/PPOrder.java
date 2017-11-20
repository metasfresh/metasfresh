package de.metas.material.event.pporder;

import static de.metas.material.event.MaterialEventUtils.checkIdGreaterThanZero;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

import org.compiere.model.I_S_Resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.material.event.commons.ProductDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.experimental.Wither;

/*
 * #%L
 * metasfresh-material-planning
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
@Wither
public class PPOrder
{
	int orgId;

	/**
	 * The {@link I_S_Resource#getS_Resource_ID()} of the plant, as specified by the respective product planning record.
	 */
	int plantId;

	int warehouseId;

	int productPlanningId;

	@NonNull
	ProductDescriptor productDescriptor;

	int uomId;

	/**
	 * In a build-to-order scenario, this is the ID of the order line which this all is about.
	 */
	int orderLineId;

	/**
	 * Can contain the {@code PP_Order_ID} of the production order document this is all about, but also note that there might not yet exist one.
	 */
	int ppOrderId;

	String docStatus;

	/**
	 * This is usually the respective supply candidates' date value.
	 */
	@NonNull
	Date datePromised;

	/**
	 * This is usually the respective demand candiates' date value.
	 */
	@NonNull
	Date dateStartSchedule;

	@NonNull
	BigDecimal quantity;

	/**
	 * If {@code true}, then this event advises the recipient to directly request an actual PP_Order to be created.
	 */
	boolean advisedToCreatePPOrder;

	/**
	 * Attention, might be {@code null}.
	 */
	@Singular
	List<PPOrderLine> lines;

	@JsonCreator
	@Builder
	public PPOrder(
			@JsonProperty("orgId") final int orgId,
			@JsonProperty("plantId") final int plantId,
			@JsonProperty("warehouseId") final int warehouseId,
			@JsonProperty("productPlanningId") final int productPlanningId,
			@JsonProperty("productDescriptor") @NonNull final ProductDescriptor productDescriptor,
			@JsonProperty("uomId") final int uomId,
			@JsonProperty("orderLineId") final int orderLineId,
			@JsonProperty("ppOrderId") final int ppOrderId,
			@JsonProperty("docStatus") @Nullable final String docStatus,
			@JsonProperty("datePromised") @NonNull final Date datePromised,
			@JsonProperty("dateStartSchedule") @NonNull final Date dateStartSchedule,
			@JsonProperty("quantity") @NonNull final BigDecimal quantity,
			@JsonProperty("advisedToCreatePPOrder") final boolean advisedToCreatePPOrder,
			@JsonProperty("lines") @Singular final List<PPOrderLine> lines)
	{
		this.orgId = checkIdGreaterThanZero("orgId", orgId);
		this.plantId = checkIdGreaterThanZero("plantId", plantId);
		this.warehouseId = checkIdGreaterThanZero("warehouseId", warehouseId);
		this.productPlanningId = productPlanningId; // ok to be not set

		productDescriptor.asssertCompleteness();
		this.productDescriptor = productDescriptor;

		this.uomId = checkIdGreaterThanZero("uomId", uomId);
		this.orderLineId = orderLineId;
		this.ppOrderId = ppOrderId;
		this.docStatus = docStatus;
		this.datePromised = datePromised;
		this.dateStartSchedule = dateStartSchedule;
		this.quantity = quantity;
		this.advisedToCreatePPOrder = advisedToCreatePPOrder;
		this.lines = lines;
	}
}
