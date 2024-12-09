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
<<<<<<< HEAD
import de.metas.edi.api.EDIDesadvLineId;
import de.metas.edi.api.EDIDesadvPackItemId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.generichumodel.PackagingCodeId;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
=======
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.generichumodel.PackagingCodeId;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
<<<<<<< HEAD
import java.math.BigDecimal;
import java.sql.Timestamp;
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import java.util.List;

@Value
@Builder
public class CreateEDIDesadvPackRequest
{
	@NonNull
	OrgId orgId;

	@NonNull
	EDIDesadvId ediDesadvId;

<<<<<<< HEAD
	int line;
=======
	@NonNull
	Integer seqNo;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	
	@NonNull
	String sscc18;

<<<<<<< HEAD
	/** 
	 * true means the SSCC was just created on-the-fly. false means it's coming from a HU's SSCC-Attribute. 
=======
	/**
	 * true means the SSCC was just created on-the-fly. false means it's coming from a HU's SSCC-Attribute.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 */
	@NonNull
	Boolean isManualIpaSSCC;

	@Nullable
	HuId huId;

	@Nullable
<<<<<<< HEAD
	PackagingCodeId huPackagingCodeLUID;

	@Nullable
	String gtinLUPackingMaterial;
=======
	PackagingCodeId huPackagingCodeID;

	@Nullable
	String gtinPackingMaterial;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	@NonNull
	@Singular
	List<CreateEDIDesadvPackItemRequest> createEDIDesadvPackItemRequests;

<<<<<<< HEAD
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
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
