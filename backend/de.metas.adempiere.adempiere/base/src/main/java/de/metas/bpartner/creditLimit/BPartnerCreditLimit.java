/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.bpartner.creditLimit;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.OrgMappingId;
import de.metas.money.Money;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;
import lombok.Value;
import lombok.With;
import lombok.experimental.NonFinal;
import org.adempiere.ad.table.RecordChangeLog;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@JsonPropertyOrder(alphabetic = true/* we want the serialized json to be less flaky in our snapshot files */)
public class BPartnerCreditLimit
{
	public static final String ID = "id";
	public static final String BPARTNER_ID = "bpartnerId";
	public static final String CREDIT_LIMIT_TYPE_ID = "creditLimitTypeId";
	public static final String AMOUNT = "amount";
	public static final String DATE_FROM = "dateFrom";
	public static final String ACTIVE = "active";
	public static final String PROCESSED = "processed";

	@Nullable
	@NonFinal
	@Setter
	BPartnerCreditLimitId id;

	@Nullable
	BPartnerId bPartnerId;

	@NonNull
	CreditLimitTypeId creditLimitTypeId;

	@NonNull
	Money amount;

	@Nullable
	Instant dateFrom;

	@Nullable
	UserId approvedBy;

	@Nullable
	@With
	RecordChangeLog changeLog;

	@Nullable
	@NonFinal
	@Setter
	OrgMappingId orgMappingId;

	boolean active;

	boolean processed;

	@Builder(toBuilder = true)
	private BPartnerCreditLimit(
			@Nullable final BPartnerCreditLimitId id,
			@Nullable final BPartnerId bPartnerId,
			@NonNull final CreditLimitTypeId creditLimitTypeId,
			@NonNull final Money amount,
			@Nullable final Instant dateFrom,
			@Nullable final UserId approvedBy,
			@Nullable final RecordChangeLog changeLog,
			@Nullable final OrgMappingId orgMappingId,
			final boolean active,
			final boolean processed)
	{
		this.id = id;
		this.bPartnerId = bPartnerId;
		this.creditLimitTypeId = creditLimitTypeId;
		this.amount = amount;
		this.dateFrom = dateFrom;
		this.approvedBy = approvedBy;
		this.changeLog = changeLog;
		this.orgMappingId = orgMappingId;
		this.active = active;
		this.processed = processed;
	}
}
