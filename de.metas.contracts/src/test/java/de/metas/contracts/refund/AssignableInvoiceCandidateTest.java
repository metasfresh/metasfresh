package de.metas.contracts.refund;

import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.contracts.refund.AssignableInvoiceCandidate.SplitResult;
import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;

/*
 * #%L
 * de.metas.contracts
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

public class AssignableInvoiceCandidateTest
{
	private static final BigDecimal TWENTY = new BigDecimal("20");
	private static final BigDecimal THIRTY = new BigDecimal("30");
	private RefundTestTools refundTestTools;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		refundTestTools = RefundTestTools.newInstance();
	}

	@Test
	public void splitQuantity()
	{
		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(1, 2);

		final AssignableInvoiceCandidate candidate = AssignableInvoiceCandidate.builder()
				//.bpartnerId(BPartnerId.ofRepoId(20))
				.bpartnerLocationId(billBPartnerAndLocationId)
				.invoiceableFrom(LocalDate.now())
				.money(Money.of(TEN, refundTestTools.getCurrencyId()))
				.precision(2)
				.productId(ProductId.ofRepoId(30))
				.quantity(Quantity.of(THIRTY, refundTestTools.getUomRecord()))
				.build();

		// invoke the method under test
		final SplitResult result = candidate.splitQuantity(TEN);

		assertThat(result.getRemainder()).isNotNull();
		assertThat(result.getRemainder().getQuantity().toBigDecimal()).isEqualByComparingTo(TWENTY);
		assertThat(result.getRemainder().getMoney().toBigDecimal()).isEqualByComparingTo("6.67"); // 2/3 of the original's quantity
		assertThat(result.getRemainder().getId()).isEqualTo(candidate.getId());

		assertThat(result.getNewCandidate()).isNotNull();
		assertThat(result.getNewCandidate().getQuantity().toBigDecimal()).isEqualByComparingTo(TEN);
		assertThat(result.getNewCandidate().getMoney().toBigDecimal()).isEqualByComparingTo("3.33"); // 1/3 of the original's quantity
		assertThat(result.getNewCandidate().getId()).isEqualTo(candidate.getId());
	}
}
