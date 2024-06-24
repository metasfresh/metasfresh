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
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Value
@Builder
public class EDIDesadvPack
{
	@NonNull
	EDIDesadvPackId ediDesadvPackId;

	@NonNull
	EDIDesadvId ediDesadvId;

	@NonNull
	String sscc18;

	@NonNull
	Boolean isManualIpaSscc;

	@Nullable
	HuId huId;

	@Nullable
	PackagingCodeId huPackagingCodeLuId;

	@Nullable
	String gtinLuPackingMaterial;

	@NonNull
	@Singular
	List<EDIDesadvPackItem> ediDesadvPackItems;

	@NonNull
	public Integer getQtyTU()
	{
		return ediDesadvPackItems.stream()
				.map(EDIDesadvPackItem::getQtyTu)
				.filter(Objects::nonNull)
				.reduce(0, Integer::sum);
	}

	@NonNull
	public BigDecimal getQtyCUsPerTU()
	{
		return ediDesadvPackItems.stream()
				.map(EDIDesadvPackItem::getQtyCUsPerTU)
				.filter(Objects::nonNull)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@NonNull
	public BigDecimal getQtyCUsPerLU()
	{
		return ediDesadvPackItems.stream()
				.map(EDIDesadvPackItem::getQtyCUsPerLU)
				.filter(Objects::nonNull)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Value
	@Builder
	public static class EDIDesadvPackItem
	{
		@NonNull
		EDIDesadvPackItemId ediDesadvPackItemId;

		@NonNull
		EDIDesadvPackId ediDesadvPackId;

		@NonNull
		EDIDesadvLineId ediDesadvLineId;

		@NonNull
		BigDecimal movementQty;

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
		PackagingCodeId huPackagingCodeTuId;

		@Nullable
		String gtinTuPackingMaterial;
	}
}
