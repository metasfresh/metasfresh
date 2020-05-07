package de.metas.material.dispo.commons.repository;

import java.util.Date;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.base.Preconditions;

import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MaterialDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
	public enum DateOperator
	{
		BEFORE, //
		BEFORE_OR_AT, //
		AT, //
		AT_OR_AFTER, //
		AFTER
	}

	public static MaterialDescriptorQuery forDescriptor(
			@NonNull final MaterialDescriptor materialDescriptor)
	{
		return new MaterialDescriptorQuery(
				materialDescriptor.getWarehouseId(),
				materialDescriptor.getProductId(),
				materialDescriptor.getStorageAttributesKey(),
				materialDescriptor.getBPartnerId(),
				materialDescriptor.getDate(),
				DateOperator.AT);
	}

	public static MaterialDescriptorQuery forDescriptor(
			@NonNull final MaterialDescriptor materialDescriptor,
			@NonNull final DateOperator dateOperator)
	{
		return new MaterialDescriptorQuery(
				materialDescriptor.getWarehouseId(),
				materialDescriptor.getProductId(),
				materialDescriptor.getStorageAttributesKey(),
				materialDescriptor.getBPartnerId(),
				materialDescriptor.getDate(),
				dateOperator);
	}

	/**
	 * This property specifies how to interpret the date.
	 */
	DateOperator dateOperator;

	int warehouseId;
	int productId;
	AttributesKey storageAttributesKey;
	int bPartnerId;
	Date date;

	@Builder
	private MaterialDescriptorQuery(
			final int warehouseId,
			final int productId,
			final AttributesKey storageAttributesKey,
			final Integer bPartnerId,
			final Date date,
			final DateOperator dateOperator)
	{
		this.warehouseId = warehouseId > 0 ? warehouseId : -1;

		this.productId = productId;
		this.storageAttributesKey = storageAttributesKey != null
				? storageAttributesKey
				: AttributesKey.ALL;

		if (bPartnerId == null)
		{
			this.bPartnerId = AvailableToPromiseQuery.BPARTNER_ID_ANY;
		}
		else if (bPartnerId == 0)
		{
			this.bPartnerId = AvailableToPromiseQuery.BPARTNER_ID_NONE;
		}
		else if (bPartnerId > 0 || bPartnerId == AvailableToPromiseQuery.BPARTNER_ID_ANY || bPartnerId == AvailableToPromiseQuery.BPARTNER_ID_NONE)
		{
			this.bPartnerId = bPartnerId;
		}
		else
		{
			throw new AdempiereException("Parameter bPartnerId has an invalid value=" + bPartnerId);
		}

		Preconditions.checkArgument(dateOperator == null || date != null,
				"Given date parameter may not be null because a not-null dateOperator=%s is given",
				dateOperator);
		this.date = date;
		this.dateOperator = dateOperator != null ? dateOperator : DateOperator.AT;
	}
}
