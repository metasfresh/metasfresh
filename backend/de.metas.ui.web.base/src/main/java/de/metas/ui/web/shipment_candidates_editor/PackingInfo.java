package de.metas.ui.web.shipment_candidates_editor;

import java.math.BigDecimal;
import java.math.RoundingMode;

import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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
final class PackingInfo
{
	public static final PackingInfo NONE = new PackingInfo();

	@NonNull
	BigDecimal qtyCUsPerTU;

	@NonNull
	String description;

	@Builder
	public PackingInfo(
			@NonNull final BigDecimal qtyCUsPerTU,
			final String description)
	{
		Check.assumeGreaterThanZero(qtyCUsPerTU, "qtyCUsPerTU");

		this.qtyCUsPerTU = qtyCUsPerTU;
		this.description = description != null ? description.trim() : "";
	}

	/** NO Packing constructor */
	private PackingInfo()
	{
		this.description = "";
		this.qtyCUsPerTU = BigDecimal.ONE;
	}

	public boolean isNone()
	{
		return NONE.equals(this);
	}

	public BigDecimal computeQtyUserEnteredByQtyCUs(@NonNull final Quantity qtyCUs)
	{
		return computeQtyUserEnteredByQtyCUs(qtyCUs.toBigDecimal());
	}

	public BigDecimal computeQtyUserEnteredByQtyCUs(@NonNull final BigDecimal qtyCUs)
	{
		return isNone()
				? qtyCUs
				: computeQtyTUsByQtyCUs(qtyCUs);
	}

	public BigDecimal computeQtyCUsByQtyUserEntered(@NonNull final BigDecimal qtyUserEntered)
	{
		return isNone()
				? qtyUserEntered
				: computeQtyCUsByQtyTUs(qtyUserEntered);
	}

	private BigDecimal computeQtyTUsByQtyCUs(final BigDecimal qtyCUs)
	{
		return qtyCUs.divide(qtyCUsPerTU, 0, RoundingMode.UP);
	}

	private BigDecimal computeQtyCUsByQtyTUs(final BigDecimal qtyTUs)
	{
		return qtyTUs.multiply(qtyCUsPerTU);
	}
}
