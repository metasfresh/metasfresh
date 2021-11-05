/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.commission.commissioninstance.businesslogic.margin;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionType;
import de.metas.contracts.pricing.trade_margin.CustomerTradeMarginId;
import de.metas.contracts.pricing.trade_margin.CustomerTradeMarginLineId;
import de.metas.product.ProductId;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.Optional;

@Value
public class MarginConfig implements CommissionConfig
{
	@NonNull
	ProductId commissionProductId;

	@NonNull
	Percent tradedPercent;

	@NonNull
	Integer pointsPrecision;

	@NonNull
	MarginContract marginContract;

	@NonNull
	CustomerTradeMarginLineId customerTradeMarginLineId;

	public static boolean isInstance(@NonNull final CommissionConfig config)
	{
		return config instanceof MarginConfig;
	}

	@NonNull
	public static MarginConfig cast(@NonNull final CommissionConfig config)
	{
		return castOrEmpty(config)
				.orElseThrow(() -> new AdempiereException("Cannot cast the given config to MarginConfig")
						.appendParametersToMessage()
						.setParameter("config", config));
	}

	@NonNull
	public static Optional<MarginConfig> castOrEmpty(@NonNull final CommissionConfig commissionConfig)
	{
		if (commissionConfig instanceof MarginConfig)
		{
			return Optional.of((MarginConfig)commissionConfig);
		}
		return Optional.empty();
	}

	@Builder
	MarginConfig(
			@JsonProperty("commissionProductId") @NonNull final ProductId commissionProductId,
			@JsonProperty("tradedPercent") @NonNull final Percent tradedPercent,
			@JsonProperty("pointsPrecision") @NonNull final Integer pointsPrecision,
			@JsonProperty("marginContract") @NonNull final MarginContract marginContract,
			@JsonProperty("customerTradeMarginLineId") @NonNull final CustomerTradeMarginLineId customerTradeMarginLineId)
	{
		this.commissionProductId = commissionProductId;
		this.tradedPercent = tradedPercent;
		this.pointsPrecision = pointsPrecision;
		this.marginContract = marginContract;
		this.customerTradeMarginLineId = customerTradeMarginLineId;
	}

	@Override
	public CommissionType getCommissionType()
	{
		return CommissionType.MARGIN_COMMISSION;
	}

	@Override
	public CommissionContract getContractFor(@NonNull final BPartnerId contractualBPartnerId)
	{
		if (marginContract.getContractOwnerBPartnerId().equals(contractualBPartnerId))
		{
			return marginContract;
		}

		return null;
	}

	@NonNull
	public CustomerTradeMarginId getId()
	{
		return customerTradeMarginLineId.getCustomerTradeMarginId();
	}
}
