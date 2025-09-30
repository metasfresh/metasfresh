/*
 * #%L
 * de-metas-common-delivery
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.common.delivery.v1.json.request;

import de.metas.common.delivery.v1.json.JsonPackageDimensions;
import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipping.mpackage.PackageId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class JsonDeliveryOrderLine
{
	@Nullable String id;
	@Nullable String content;
	@NonNull BigDecimal grossWeightKg;
	@NonNull JsonPackageDimensions packageDimensions;
	@NonNull String packageId;
	@Nullable String awb;
	@Nullable String trackingUrl;
	@Nullable byte[] labelPdfBase64;
	@NonNull List<JsonDeliveryOrderLineContents> contents;

}
