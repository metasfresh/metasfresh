/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.handlingunits.qrcodes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public interface IHUQRCode
{
	String getAsString();

	@JsonIgnore
	default boolean isWeightRequired() {return false;}

	Optional<BigDecimal> getWeightInKg();

	Optional<LocalDate> getBestBeforeDate();

	default Optional<LocalDate> getProductionDate() {return Optional.empty();}

	Optional<String> getLotNumber();

	/** Expiration (use-by) date the code carries, if any. */
	@JsonIgnore
	default Optional<LocalDate> getExpirationDate() {return Optional.empty();}

	/**
	 * What this code says about the product it labels. Empty (the default): this kind of code never names a product.
	 * Present: a product label; the codes it carries (product number, GTIN, EAN-13) may each be absent.
	 */
	@JsonIgnore
	default Optional<ProductLabelCodes> getProductLabelCodes() {return Optional.empty();}
}
