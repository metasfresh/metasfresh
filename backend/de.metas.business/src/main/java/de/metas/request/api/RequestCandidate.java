package de.metas.request.api;

import de.metas.bpartner.BPartnerId;
import de.metas.inout.QualityNoteId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.request.RequestTypeId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2019 metas GmbH
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
@Builder
public class RequestCandidate
{
	@NonNull
	RequestTypeId requestTypeId;

	@NonNull
	OrgId orgId;
	ProductId productId;
	BPartnerId partnerId;
	UserId userId;

	TableRecordReference recordRef;

	@NonNull
	ZonedDateTime dateDelivered;
	@NonNull
	String confidentialType;
	@NonNull
	String summary;

	@Nullable
	QualityNoteId qualityNoteId;
	@Nullable
	String performanceType;
}
