package de.metas.material.event.pporder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.document.engine.DocStatus;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

/*
 * #%L
 * metasfresh-material-planning
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
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class PPOrder
{
	/**
	 * Can contain the {@code PP_Order_ID} of the production order document this is all about, but also note that there might not yet exist one.
	 */
	int ppOrderId;

	DocStatus docStatus;

	PPOrderData ppOrderData;

	/**
	 * Attention, might be {@code null}.
	 */
	List<PPOrderLine> lines;

	@JsonCreator
	@Builder(toBuilder = true)
	public PPOrder(
			@JsonProperty("ppOrderId") final int ppOrderId,
			@JsonProperty("docStatus") @Nullable final DocStatus docStatus,
			@JsonProperty("ppOrderData") @NonNull final PPOrderData ppOrderData,
			@JsonProperty("lines") @Singular final List<PPOrderLine> lines)
	{
		this.ppOrderId = ppOrderId;
		this.docStatus = docStatus;
		this.ppOrderData = ppOrderData;
		this.lines = lines;
	}
}
