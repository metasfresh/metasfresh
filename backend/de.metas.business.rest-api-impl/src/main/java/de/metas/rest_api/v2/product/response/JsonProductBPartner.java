/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.product.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import de.metas.bpartner.BPartnerId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JsonProductBPartner
{
	@Schema(type = "java.lang.Integer",
			description = "This translates to `C_BPartner_ID`.")
	@NonNull
	BPartnerId bpartnerId;

	String productNo;
	String productName;
	String productDescription;
	String productCategory;

	String ean;

	boolean vendor;
	boolean currentVendor;
	boolean customer;

	int leadTimeInDays;
}
