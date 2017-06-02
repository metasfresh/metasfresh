package de.metas.inoutcandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.google.common.annotations.VisibleForTesting;

import de.metas.inoutcandidate.api.IPackageable;
import lombok.Builder;
import lombok.Value;

/**
 * Plain {@link IPackageable} implementation
 *
 * @author tsa
 *
 */
@VisibleForTesting
@Value
@Builder
public class Packageable implements IPackageable
{
	private int bpartnerLocationId;

	private int shipmentScheduleId;

	private BigDecimal qtyToDeliver;

	private int bpartnerId;
	private String bpartnerValue;
	private String bpartnerName;

	private String bpartnerLocationName;

	private String bpartnerAddress;

	private int warehouseId;
	private String warehouseName;

	private String deliveryVia;

	private int shipperId;
	private String shipperName;

	private boolean displayed;

	private String documentNo;

	private Timestamp deliveryDate;
	private Timestamp preparationDate;

	private String freightCostRule;

	private int productId;
	private String productName;

	private int orderId;
	private String docSubType;
}
