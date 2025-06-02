/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.log;

import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;

import javax.annotation.Nullable;
import java.time.Instant;

/**
 * API communication informations
 */
@Value
@Builder
public class SecurPharmLog
{
	boolean error;

	//
	// Request
	@NonNull
	String requestUrl;
	@NonNull
	HttpMethod requestMethod;
	@NonNull
	Instant requestTime;

	//
	// Response
	@NonNull
	Instant responseTime;
	HttpStatusCode responseCode;
	String responseData;

	@NonNull
	String clientTransactionId;
	String serverTransactionId;

	@Nullable
	@NonFinal
	@Setter
	SecurPharmLogId id;
}
