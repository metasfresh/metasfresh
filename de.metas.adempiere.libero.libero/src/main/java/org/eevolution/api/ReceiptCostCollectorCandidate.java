package org.eevolution.api;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
import java.time.LocalDateTime;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.product.ProductId;
import de.metas.util.time.SystemTime;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

/**
 * Instances of this class can be passed to {@link IPPCostCollectorBL#createReceipt(IReceiptCostCollectorCandidate)} to have it generate and process a receipt. <br>
 * <p>
 * Note that in the context of a "co-product", a receipt is a negative issue (but that should not bother the user).
 *
 */
@Value
@Builder(toBuilder = true)
public class ReceiptCostCollectorCandidate
{
	I_PP_Order PP_Order;

	/** manufacturing order's BOM Line if this is a co/by-product receipt; <code>null</code> otherwise */
	I_PP_Order_BOMLine PP_Order_BOMLine;

	@NonNull
	@Default
	LocalDateTime movementDate = SystemTime.asLocalDateTime();

	ProductId productId;
	@NonNull
	I_C_UOM C_UOM;

	@NonNull
	@Default
	BigDecimal qtyToReceive = BigDecimal.ZERO;

	@NonNull
	BigDecimal qtyScrap;
	@NonNull
	BigDecimal qtyReject;
	LocatorId locatorId;
	AttributeSetInstanceId attributeSetInstanceId;

	public static class ReceiptCostCollectorCandidateBuilder
	{
		private ReceiptCostCollectorCandidateBuilder()
		{
			movementDate(SystemTime.asLocalDateTime());
			qtyToReceive(BigDecimal.ZERO);
			qtyScrap(BigDecimal.ZERO);
			qtyReject(BigDecimal.ZERO);
			locatorId(null);
			attributeSetInstanceId(AttributeSetInstanceId.NONE);
		}
	}
}
