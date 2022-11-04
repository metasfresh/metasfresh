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

package de.metas.bpartner.service.creditlimit;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import de.metas.bpartner.OrgMappingId;
import de.metas.bpartner.creditLimit.BPartnerCreditLimitId;
import de.metas.bpartner.creditLimit.CreditLimitTypeId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.With;
import org.adempiere.ad.table.RecordChangeLog;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

@Setter
@Getter
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
	private BPartnerCreditLimitId id;

	@NonNull
	private final CreditLimitTypeId creditLimitTypeId;

	@NonNull
	private final BigDecimal amount;

	@Nullable
	private final Instant dateFrom;

	@Nullable
	@With
	private String bpartnerValue;

	@Nullable
	@With
	private RecordChangeLog changeLog;

	@Nullable
	private OrgMappingId orgMappingId;

	private boolean active;

	private boolean processed;

	@Builder(toBuilder = true)
	private BPartnerCreditLimit(
			@Nullable final BPartnerCreditLimitId id,
			@NonNull final CreditLimitTypeId creditLimitTypeId,
			@NonNull final BigDecimal amount,
			@Nullable final Instant dateFrom,
			@Nullable final String bpartnerValue,
			@Nullable final RecordChangeLog changeLog,
			@Nullable final OrgMappingId orgMappingId,
			final boolean active,
			final boolean processed)
	{
		this.id = id;
		this.creditLimitTypeId = creditLimitTypeId;
		this.amount = amount;
		this.dateFrom = dateFrom;
		this.bpartnerValue = bpartnerValue;
		this.changeLog = changeLog;
		this.orgMappingId = orgMappingId;
		this.active = active;
		this.processed = processed;
	}

	@NonNull
	public BPartnerCreditLimitId getIdNotNull()
	{
		if (this.id == null)
		{
			throw new AdempiereException("BPartnerCreditLimitId is missing!");
		}
		return this.id;
	}

	@NonNull
	public String getBPartnerValueNotNull()
	{
		if (this.bpartnerValue == null)
		{
			throw new AdempiereException("BPartnerValue is missing!");
		}
		return this.bpartnerValue;
	}
}
