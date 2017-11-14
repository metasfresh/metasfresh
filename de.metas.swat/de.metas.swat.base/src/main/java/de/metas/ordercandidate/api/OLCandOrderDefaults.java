package de.metas.ordercandidate.api;

import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
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
@Builder
public class OLCandOrderDefaults
{
	public static final OLCandOrderDefaults NULL = builder().build();
	
	private final int docTypeTargetId;
	
	private final String deliveryRule;
	private final String deliveryViaRule;
	private final int shipperId;
	private final int warehouseId;
	private final String freightCostRule;

	private final String paymentRule;
	private final int paymentTermId;

	private final String invoiceRule;
	private final int pricingSystemId;
}
