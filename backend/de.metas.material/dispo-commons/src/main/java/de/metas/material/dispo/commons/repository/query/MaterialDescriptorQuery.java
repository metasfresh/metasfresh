package de.metas.material.dispo.commons.repository.query;

import de.metas.common.util.CoalesceUtil;
import de.metas.material.dispo.commons.repository.DateAndSeqNo;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseQuery;
import de.metas.material.dispo.commons.repository.atp.BPartnerClassifier;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-material-dispo-commons
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
public class MaterialDescriptorQuery
{
	/**
	 * Note: this operator only makes a difference if the given customerId is > 0.
	 * If it's less than zero, the records with customerId == null (i.e. "none") are selected anyways.
	 */
	public enum CustomerIdOperator
	{
		/** This is the default; query for records with the given customer id where zero means "none" and null means "any" */
		GIVEN_ID_ONLY,

		/** Like {@link #GIVEN_ID_ONLY}, but also include records that have no customerId */
		GIVEN_ID_OR_NULL
	}

	public static MaterialDescriptorQuery forDescriptor(@NonNull final MaterialDescriptor materialDescriptor)
	{
		final DateAndSeqNo atTime = null;
		return forDescriptor(materialDescriptor, atTime);
	}

	public static MaterialDescriptorQuery forDescriptor(
			@NonNull final MaterialDescriptor materialDescriptor,
			@Nullable final DateAndSeqNo atTime)
	{
		return MaterialDescriptorQuery
				.builder()
				.warehouseId(materialDescriptor.getWarehouseId())
				.productId(materialDescriptor.getProductId())
				.storageAttributesKey(materialDescriptor.getStorageAttributesKey())
				.customer(BPartnerClassifier.specificOrAny(materialDescriptor.getCustomerId()))
				.customerIdOperator(CustomerIdOperator.GIVEN_ID_ONLY)
				.atTime(atTime)
				.build();
	}

	public static MaterialDescriptorQuery forDescriptor(
			@NonNull final MaterialDescriptor materialDescriptor,
			@Nullable final DateAndSeqNo timeRangeStart,
			@Nullable final DateAndSeqNo timeRangeEnd)
	{
		Check.errorIf(timeRangeStart == null && timeRangeEnd == null, "At least one of timeRangeStart or timeRangeEnd need to be not-null");

		return MaterialDescriptorQuery
				.builder()
				.warehouseId(materialDescriptor.getWarehouseId())
				.productId(materialDescriptor.getProductId())
				.storageAttributesKey(materialDescriptor.getStorageAttributesKey())
				.customer(BPartnerClassifier.specificOrAny(materialDescriptor.getCustomerId()))
				.customerIdOperator(CustomerIdOperator.GIVEN_ID_ONLY)
				.timeRangeStart(timeRangeStart)
				.timeRangeEnd(timeRangeEnd)
				.build();
	}

	WarehouseId warehouseId;
	int productId;
	AttributesKey storageAttributesKey;

	BPartnerClassifier customer;
	CustomerIdOperator customerIdOperator;

	DateAndSeqNo atTime;

	DateAndSeqNo timeRangeStart;

	DateAndSeqNo timeRangeEnd;

	@Builder(toBuilder = true)
	private MaterialDescriptorQuery(
			final WarehouseId warehouseId,
			final int productId,
			final AttributesKey storageAttributesKey,
			final BPartnerClassifier customer,
			final CustomerIdOperator customerIdOperator,
			final DateAndSeqNo atTime,
			final DateAndSeqNo timeRangeStart,
			final DateAndSeqNo timeRangeEnd)
	{
		this.warehouseId = warehouseId;

		this.productId = productId;
		this.storageAttributesKey = storageAttributesKey != null
				? storageAttributesKey
				: AttributesKey.ALL;

		this.customerIdOperator = CoalesceUtil.coalesce(customerIdOperator, CustomerIdOperator.GIVEN_ID_ONLY);
		this.customer = customer != null ? customer : BPartnerClassifier.any();

		if (atTime != null)
		{
			Check.errorIf(timeRangeStart != null, "If atTime != null, then timeRangeStart needs to be null");
			Check.errorIf(timeRangeEnd != null, "If atTime != null, then timeRangeEnd needs to be null");
			Check.errorIf(atTime.getOperator() != null, "The instance given as atTime needs to have no operator");
		}
		if (timeRangeStart != null)
		{
			Check.errorIf(atTime != null, "If timeRangeStart != null, then atTime needs to be null");
			Check.errorIf(timeRangeStart.getOperator() == null, "If timeRangeStart is given, then its operator may not be null");
		}
		if (timeRangeEnd != null)
		{
			Check.errorIf(atTime != null, "If timeRangeEnd != null, then atTime needs to be null");
			Check.errorIf(timeRangeEnd.getOperator() == null, "If timeRangeEnd is given, then its operator may not be null");
		}
		this.atTime = atTime;
		this.timeRangeStart = timeRangeStart;
		this.timeRangeEnd = timeRangeEnd;

	}
}
