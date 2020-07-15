/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.pricing.trade_margin;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.model.I_C_Customer_Trade_Margin;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder
public class CustomerTradeMarginSettings
{
	@NonNull
	BPartnerId salesRepId;

	@Nullable
	BPartnerId customerId;

	@NonNull
	LocalDate validFrom;

	@NonNull
	BigDecimal marginPercent;

	public static CustomerTradeMarginSettings of(final I_C_Customer_Trade_Margin iC_CustomerTradeMargin)
	{
		return builder()
				.salesRepId( BPartnerId.ofRepoId( iC_CustomerTradeMargin.getC_BPartner_SalesRep_ID() ) )
				.customerId( BPartnerId.ofRepoIdOrNull( iC_CustomerTradeMargin.getC_BPartner_Customer_ID() ) )
				.validFrom( iC_CustomerTradeMargin.getValidFrom().toLocalDateTime().toLocalDate() )
				.marginPercent( iC_CustomerTradeMargin.getMargin_Percent() )
				.build();
	}

	public boolean applicableToAllCustomers()
	{
		return getCustomerId() == null;
	}

	public boolean applicableOnlyTo(@NonNull final BPartnerId customerId) {
		return BPartnerId.equals(getCustomerId(), customerId);
	}
}
