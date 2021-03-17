/*
 * #%L
 * procurement-webui-backend
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.procurement.webui.rest.rfq;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Value
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class JsonRfq
{
	@NonNull
	String rfqId;

	@NonNull
	String productName;
	@NonNull
	String packingInfo;

	@NonNull LocalDate dateStart;
	@NonNull LocalDate dateEnd;
	@NonNull LocalDate dateClose;

	@NonNull
	String qtyRequested;

	@NonNull
	String qtyPromised;

	@NonNull
	BigDecimal price;
	@NonNull
	String priceRendered;

	@NonNull
	@Singular
	List<JsonRfqQty> quantities;

	boolean confirmedByUser;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable
	@With
	Long countUnconfirmed;
}
