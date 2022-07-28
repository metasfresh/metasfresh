/*
 * #%L
 * de.metas.vertical.healthcare.alberta
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

package de.metas.vertical.healthcare.alberta.order;

import de.metas.bpartner.BPartnerId;
import de.metas.ordercandidate.api.OLCandId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class AlbertaOrderInfo
{
	@NonNull
	OLCandId olCandId;

	@NonNull
	OrgId orgId;

	@NonNull
	String externalId;

	@Nullable
	String rootId;

	@Nullable
	Instant creationDate;

	@Nullable
	LocalDate startDate;

	@Nullable
	LocalDate endDate;

	@Nullable
	BigDecimal dayOfDelivery;

	@Nullable
	LocalDate nextDelivery;

	@Nullable
	BPartnerId doctorBPartnerId;

	@Nullable
	BPartnerId pharmacyBPartnerId;

	@Nullable
	Boolean isInitialCare;

	@Nullable
	Boolean isSeriesOrder;

	@Nullable
	Boolean isArchived;

	@Nullable
	String annotation;

	@Nullable
	String deliveryInformation;

	@Nullable
	String deliveryNote;

	@Nullable
	Instant updated;

	@Nullable
	String therapy;

	@Nullable
	List<String> therapyTypes;

	@Nullable
	AlbertaOrderLineInfo orderLine;
}
