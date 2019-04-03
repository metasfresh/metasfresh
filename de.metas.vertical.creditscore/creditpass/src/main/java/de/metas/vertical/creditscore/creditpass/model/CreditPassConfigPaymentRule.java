package de.metas.vertical.creditscore.creditpass.model;

/*
 * #%L
 * de.metas.vertical.creditscore.creditpass.model
 *
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

import de.metas.money.CurrencyId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CreditPassConfigPaymentRule
{

	@NonNull
	private String paymentRule;

	@NonNull
	private int purchaseType;

	private BigDecimal requestPrice;

	private CurrencyId requestPriceCurrency;

	@NonNull
	private CreditPassConfigPaymentRuleId paymentRuleId;

	private List<CreditPassConfigPRFallback> creditPassConfigPRFallbacks;

}
