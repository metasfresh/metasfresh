package de.metas.bpartner.service;

import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

import de.metas.common.util.time.SystemTime;
import org.junit.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerStatsBL.CalculateSOCreditStatusRequest;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class IBPartnerStatsBLTest
{

	@Test
	public void build_CalculateSOCreditStatusRequest()
	{
		final BPartnerStats stat = BPartnerStats.builder()
				.repoId(20)
				.bpartnerId(BPartnerId.ofRepoId(10))
				.build();

		final CalculateSOCreditStatusRequest calculateSOCreditStatusRequest = CalculateSOCreditStatusRequest.builder()
				.date(SystemTime.asTimestamp())
				.additionalAmt(null)
				.stat(stat)
				.build();

		assertThat(calculateSOCreditStatusRequest.getAdditionalAmt()).isEqualByComparingTo(ZERO);
	}

}
