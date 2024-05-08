package de.metas.bpartner.service;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.compiere.model.X_C_BPartner_Stats;

import de.metas.bpartner.BPartnerId;
import de.metas.util.Check;
import de.metas.common.util.CoalesceUtil;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
public class BPartnerStats
{
	int repoId;

	BPartnerId bpartnerId;

	BigDecimal openItems;
	BigDecimal actualLifeTimeValue;
	BigDecimal soCreditUsed;
	String soCreditStatus;

	@Builder
	public BPartnerStats(
			final int repoId,
			@NonNull final BPartnerId bpartnerId,
			@Nullable final BigDecimal openItems,
			@Nullable final BigDecimal actualLifeTimeValue,
			@Nullable final BigDecimal soCreditUsed,
			@Nullable final String soCreditStatus)
	{
		Check.assume(repoId > 0, "Given parameter repoId is > 0");

		this.repoId = repoId;
		this.bpartnerId = bpartnerId;
		this.openItems = CoalesceUtil.coalesce(openItems, ZERO);
		this.actualLifeTimeValue = CoalesceUtil.coalesce(actualLifeTimeValue, ZERO);
		this.soCreditUsed = CoalesceUtil.coalesce(soCreditUsed, ZERO);
		this.soCreditStatus = CoalesceUtil.coalesce(soCreditStatus, X_C_BPartner_Stats.SOCREDITSTATUS_NoCreditCheck);
	}

	public BigDecimal getSOCreditUsed()
	{
		return soCreditUsed;
	}

	public String getSOCreditStatus()
	{
		return soCreditStatus;
	}
}
