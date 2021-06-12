/*
 * #%L
 * de-metas-camel-alberta-camelroutes
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

package de.metas.camel.externalsystems.alberta.institutions;

import de.metas.camel.externalsystems.alberta.common.AlbertaConnectionDetails;
import de.metas.common.bpartner.v2.request.alberta.JsonBPartnerRole;
import io.swagger.client.api.DoctorApi;
import io.swagger.client.api.HospitalApi;
import io.swagger.client.api.NursingHomeApi;
import io.swagger.client.api.NursingServiceApi;
import io.swagger.client.api.PayerApi;
import io.swagger.client.api.PharmacyApi;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetInstitutionsRouteContext
{
	@NonNull
	String orgCode;

	@NonNull
	AlbertaConnectionDetails albertaConnectionDetails;

	@NonNull
	String albertaResourceId;

	@NonNull
	JsonBPartnerRole role;

	@NonNull
	DoctorApi doctorApi;

	@NonNull
	HospitalApi hospitalApi;

	@NonNull
	NursingHomeApi nursingHomeApi;

	@NonNull
	NursingServiceApi nursingServiceApi;

	@NonNull
	PayerApi payerApi;

	@NonNull
	PharmacyApi pharmacyApi;
}
