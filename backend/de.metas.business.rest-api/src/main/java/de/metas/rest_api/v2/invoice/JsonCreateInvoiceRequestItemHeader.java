/*
 * #%L
 * de.metas.business.rest-api
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

package de.metas.rest_api.v2.invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.common.rest_api.v2.JsonDocTypeInfo;
import de.metas.common.rest_api.v2.SwaggerDocConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;
import java.util.Optional;

@Value
@Builder
@Jacksonized
public class JsonCreateInvoiceRequestItemHeader
{
	@NonNull
	String externalHeaderId;
	
	@NonNull
	String orgCode;
	
	@NonNull
	JsonDocTypeInfo invoiceDocType;

	@NonNull
	String poReference;

	@NonNull
	String soTrx;

	@NonNull
	String currencyCode;
	
	@NonNull
	String billPartnerIdentifier;

	@NonNull
	String billLocationIdentifier;
	
	@Nullable
	String billContactIdentifier;

	@Nullable
	String acctSchemaCode;

	@Nullable
	LocalDate dateInvoiced;

	@Nullable
	LocalDate dateAcct;

	@Nullable
	LocalDate dateOrdered;

	@Nullable
	BigDecimal grandTotal;

	@Nullable
	BigDecimal taxTotal;

	@ApiModelProperty(value = "Identifier of the `AD_InputDataSource` record that tells where this Invoice came from.\n" + SwaggerDocConstants.DATASOURCE_IDENTIFIER_DOC)
	@Nullable
	String dataSource;
	
	@Nullable
	Map<String, Object> extendedProps;

	@NonNull
	@JsonIgnore
	public Optional<Instant> getDateInvoicedAsInstant(@NonNull final ZoneId zoneId)
	{
		return Optional.ofNullable(dateInvoiced)
				.map(date -> date.atStartOfDay(zoneId).toInstant());
	}

	@NonNull
	@JsonIgnore
	public Optional<Instant> getDateAcctAsInstant(@NonNull final ZoneId zoneId)
	{
		return Optional.ofNullable(dateAcct)
				.map(date -> date.atStartOfDay(zoneId).toInstant());
	}

	@NonNull
	@JsonIgnore
	public Optional<Instant> getDateOrderedAsInstant(@NonNull final ZoneId zoneId)
	{
		return Optional.ofNullable(dateOrdered)
				.map(date -> date.atStartOfDay(zoneId).toInstant());
	}
}
