/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.material.event.commons;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static de.metas.common.util.CoalesceUtil.coalesceNotNull;

/**
 * Makes sense together with a {@link MaterialDescriptor}.
 * The quantities are in the respective product's stock UOM.
 */
@Value
public class MinMaxDescriptor
{
	public static final MinMaxDescriptor ZERO = new MinMaxDescriptor(BigDecimal.ZERO, BigDecimal.ZERO);

	BigDecimal min;

	BigDecimal max;

	boolean highPriority;

	private MinMaxDescriptor(
			@JsonProperty("min") @Nullable final BigDecimal min,
			@JsonProperty("max") @Nullable final BigDecimal max)
	{
		this(min, max, false);
	}

	@JsonCreator
	@Builder
	private MinMaxDescriptor(
			@JsonProperty("min") @Nullable final BigDecimal min,
			@JsonProperty("max") @Nullable final BigDecimal max,
			@JsonProperty("highPriority") final @Nullable Boolean highPriority)
	{
		this.min = coalesceNotNull(min, BigDecimal.ZERO);
		this.max = coalesceNotNull(max, this.min);
		Check.errorIf(this.min.compareTo(this.max) > 0, "Minimum={} maybe not be bigger than maximum={}", this.min, this.max);
		this.highPriority = coalesceNotNull(highPriority, false);
	}

	@JsonIgnore
	public boolean isZero() {return min.signum() == 0 && max.signum() == 0;}

	@Nullable
	@JsonIgnore
	public MinMaxDescriptor toNullIfZero() {return isZero() ? null : this;}
}
