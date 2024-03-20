/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2022 metas GmbH
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

import de.metas.edi.api.EDIDesadvId;
import de.metas.edi.api.EDIDesadvLineId;
import de.metas.edi.api.EDIDesadvPackItemId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.generichumodel.PackagingCodeId;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Value
@Builder
public class CreateEDIDesadvPackRequest
{
	@NonNull
	OrgId orgId;

	@NonNull
	EDIDesadvId ediDesadvId;

	@NonNull
	String sscc18;

	@NonNull
	Boolean isManualIpaSSCC;

	@Nullable
	HuId huId;

	@Nullable
	PackagingCodeId huPackagingCodeLUID;

	@Nullable
	String gtinLUPackingMaterial;

	@NonNull
	@Singular
	List<CreateEDIDesadvPackItemRequest> createEDIDesadvPackItemRequests;

	@Value
	@Builder
	public static class CreateEDIDesadvPackItemRequest
	{
		@Nullable
		EDIDesadvPackItemId ediDesadvPackItemId;

		@NonNull
		EDIDesadvLineId ediDesadvLineId;

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
}
