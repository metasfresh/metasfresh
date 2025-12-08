/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.edi.api.impl.pack;

import de.metas.edi.api.EDIDesadvLineId;
import de.metas.edi.api.EDIDesadvPackItemId;
import de.metas.handlingunits.generichumodel.PackagingCodeId;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Value
@Builder
public class CreateEDIDesadvPackItemRequest
{
	/**
	 * Might be not-null when we use this class to add an item to an existing pack.
	 * Ignored if this instance is included with a {@link CreateEDIDesadvPackRequest}.
	 */
	@With
	@Nullable
	EDIDesadvPackId ediDesadvPackId;

	/**
	 * Looking at the code which uses this, the whole class is not just about creating, but also about updating pack-items!
	 */
	@Nullable
	EDIDesadvPackItemId ediDesadvPackItemId;

	@NonNull
	EDIDesadvLineId ediDesadvLineId;

	@NonNull
	Integer line;
	
	@NonNull
	BigDecimal movementQtyInStockUOM;

	@Nullable
	InOutId inOutId;

	@Nullable
	InOutLineId inOutLineId;

	@Nullable
	BigDecimal qtyItemCapacity;

	@Nullable
	Integer qtyTu;

	@Nullable
	BigDecimal qtyCUsPerTU;

	@Nullable
	BigDecimal qtyCUPerTUinInvoiceUOM;

	@Nullable
	BigDecimal qtyCUsPerLU;

	@Nullable
	BigDecimal qtyCUsPerLUinInvoiceUOM;

	@Nullable
	Timestamp bestBeforeDate;

	@Nullable
	String lotNumber;

	@Nullable
	PackagingCodeId huPackagingCodeTUID;

	@Nullable
	String gtinTUPackingMaterial;
}
