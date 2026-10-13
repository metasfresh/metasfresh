package de.metas.contracts.compensationGroup.contract;

import de.metas.bpartner.BPartnerId;
import de.metas.product.ProductId;
import de.metas.util.lang.Percent;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2026 metas GmbH
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

class ContractServiceFeeTest
{
	private static final ProductId FEE_PRODUCT_ID = ProductId.ofRepoId(201);
	private static final BPartnerId SERVICE_COMPANY_ID = BPartnerId.ofRepoId(111);

	@Nested
	class of
	{
		@Test
		void zeroPercent_isTheZeroFee()
		{
			assertThat(ContractServiceFee.of(Percent.ZERO, FEE_PRODUCT_ID, SERVICE_COMPANY_ID)).isSameAs(ContractServiceFee.ZERO);
		}

		@Test
		void positivePercent_carriesProductAndServiceCompany()
		{
			final ContractServiceFee fee = ContractServiceFee.of(Percent.of(2), FEE_PRODUCT_ID, SERVICE_COMPANY_ID);

			assertThat(fee.isGreaterThanZero()).isTrue();
			assertThat(fee.getFeeProductId()).isEqualTo(FEE_PRODUCT_ID);
			assertThat(fee.getServiceCompanyBPartnerId()).isEqualTo(SERVICE_COMPANY_ID);
		}
	}
}
