package de.metas.material.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.Env;

import de.metas.bpartner.BPartnerId;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.AttributesKeyPart;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;

/*
 * #%L
 * metasfresh-material-event
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

public class EventTestHelper
{
	public static final Instant NOW = SystemTime.asInstant();
	public static final Instant BEFORE_BEFORE_NOW = NOW.minus(20, ChronoUnit.MINUTES);
	public static final Instant BEFORE_NOW = NOW.minus(10, ChronoUnit.MINUTES);
	public static final Instant AFTER_NOW = NOW.plus(10, ChronoUnit.MINUTES);

	/**
	 * This constant is zero because we don't control the client-id used by {@link POJOWrapper} when it creates a new instance.
	 * It could be done with {@link Env}, but it would add complexity..
	 */
	public static final ClientId CLIENT_ID = ClientId.SYSTEM;
	public static final OrgId ORG_ID = OrgId.ofRepoId(20);
	public static final ClientAndOrgId CLIENT_AND_ORG_ID = ClientAndOrgId.ofClientAndOrg(CLIENT_ID, ORG_ID);

	public static final int TRANSACTION_ID = 60;

	public static final WarehouseId WAREHOUSE_ID = WarehouseId.ofRepoId(51);

	public static final int SHIPMENT_SCHEDULE_ID = 21;

	public static final int PRODUCT_ID = 24;

	public static final BPartnerId BPARTNER_ID = BPartnerId.ofRepoId(25);

	public static final int ATTRIBUTE_SET_INSTANCE_ID = 28;

	public static final AttributesKey STORAGE_ATTRIBUTES_KEY = AttributesKey.ofString("1");

	public static SupplyRequiredDescriptor createSupplyRequiredDescriptor()
	{
		return createSupplyRequiredDescriptorWithProductId(PRODUCT_ID);
	}

	public static SupplyRequiredDescriptor createSupplyRequiredDescriptorWithProductId(final int productId)
	{
		return SupplyRequiredDescriptor.builder()
				.shipmentScheduleId(SHIPMENT_SCHEDULE_ID)
				.demandCandidateId(41)
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_AND_ORG_ID))
				.materialDescriptor(createMaterialDescriptorWithProductId(productId))
				.build();
	}

	public static MaterialDescriptor createMaterialDescriptor()
	{
		return createMaterialDescriptorWithProductId(PRODUCT_ID);
	}

	public static MaterialDescriptor createMaterialDescriptorWithProductId(final int productId)
	{
		return MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptorWithProductId(productId))
				.warehouseId(WAREHOUSE_ID)
				.customerId(BPARTNER_ID)
				.quantity(BigDecimal.TEN)
				.date(NOW)
				.build();
	}

	public static ProductDescriptor createProductDescriptor()
	{
		return createProductDescriptorWithProductId(PRODUCT_ID);
	}

	public static ProductDescriptor createProductDescriptorWithProductId(final int productId)
	{
		return ProductDescriptor.forProductAndAttributes(
				productId,
				STORAGE_ATTRIBUTES_KEY,
				ATTRIBUTE_SET_INSTANCE_ID);
	}

	public static ProductDescriptor createProductDescriptorWithOffSet(final int offset)
	{
		final AttributeValueId firstAttributeValueId = STORAGE_ATTRIBUTES_KEY.getParts().iterator().next().getAttributeValueId();
		final AttributeValueId newAttributeValueId = AttributeValueId.ofRepoId(firstAttributeValueId.getRepoId() + 1 + offset);

		return ProductDescriptor.forProductAndAttributes(
				PRODUCT_ID + offset,
				AttributesKey.ofParts(AttributesKeyPart.ofAttributeValueId(newAttributeValueId)),
				ATTRIBUTE_SET_INSTANCE_ID + offset);
	}
}
