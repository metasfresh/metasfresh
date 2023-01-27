package de.metas.bpartner.service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.impl.CreditStatus;
import de.metas.common.util.CoalesceUtil;
import de.metas.sectionCode.SectionCodeId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

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
	BigDecimal deliveryCreditUsed;
	CreditStatus soCreditStatus;
	CreditStatus deliveryCreditStatus;
	SectionCodeId sectionCodeId;

	@Builder
	public BPartnerStats(
			final int repoId,
			@NonNull final BPartnerId bpartnerId,
			@Nullable final SectionCodeId sectionCodeId,
			@Nullable final BigDecimal openItems,
			@Nullable final BigDecimal actualLifeTimeValue,
			@Nullable final BigDecimal soCreditUsed,
			@Nullable final BigDecimal deliveryCreditUsed,
			@Nullable final CreditStatus soCreditStatus,
			@Nullable final CreditStatus deliveryCreditStatus)
	{
		Check.assume(repoId > 0, "Given parameter repoId is > 0");

		this.repoId = repoId;
		this.bpartnerId = bpartnerId;
		this.sectionCodeId = sectionCodeId;
		this.openItems = CoalesceUtil.coalesceNotNull(openItems, ZERO);
		this.actualLifeTimeValue = CoalesceUtil.coalesceNotNull(actualLifeTimeValue, ZERO);
		this.soCreditUsed = CoalesceUtil.coalesceNotNull(soCreditUsed, ZERO);
		this.deliveryCreditUsed = CoalesceUtil.coalesceNotNull(deliveryCreditUsed, ZERO);
		this.soCreditStatus = CoalesceUtil.coalesceNotNull(soCreditStatus, CreditStatus.NoCreditCheck);
		this.deliveryCreditStatus = CoalesceUtil.coalesceNotNull(deliveryCreditStatus, CreditStatus.NoCreditCheck);
	}
}
