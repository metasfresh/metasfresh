package org.eevolution.api.impl;

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
import java.sql.Timestamp;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.api.IReceiptCostCollectorCandidate;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder(toBuilder = true)
public class ReceiptCostCollectorCandidate implements IReceiptCostCollectorCandidate
{
	private final I_PP_Order PP_Order;
	private final I_PP_Order_BOMLine PP_Order_BOMLine;

	@NonNull
	@Default
	private Timestamp movementDate = SystemTime.asTimestamp();

	private I_M_Product M_Product;
	@NonNull
	private I_C_UOM C_UOM;

	@NonNull
	@Default
	private BigDecimal qtyToReceive = BigDecimal.ZERO;

	@NonNull
	private final BigDecimal qtyScrap;
	@NonNull
	private final BigDecimal qtyReject;
	private final int M_Locator_ID;
	private final int M_AttributeSetInstance_ID;

	public static class ReceiptCostCollectorCandidateBuilder
	{
		private ReceiptCostCollectorCandidateBuilder()
		{
			movementDate(SystemTime.asTimestamp());
			qtyToReceive(BigDecimal.ZERO);
			qtyScrap(BigDecimal.ZERO);
			qtyReject(BigDecimal.ZERO);
			M_Locator_ID(-1);
			M_AttributeSetInstance_ID(IAttributeDAO.M_AttributeSetInstance_ID_None);
		}
	}
}
