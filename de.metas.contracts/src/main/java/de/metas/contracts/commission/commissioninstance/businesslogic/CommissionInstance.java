package de.metas.contracts.commission.commissioninstance.businesslogic;

import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionTriggerData;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.SalesCommissionShare;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;

/*
 * #%L
 * de.metas.commission
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

/** one instance has a commission trigger and {@code n} commission shares (according to the sales rep hierarchy). */
@Data
public class CommissionInstance
{
	/** null if this instance was not (yet) persisted */
	private final CommissionInstanceId id;

	private CommissionTriggerData currentTriggerData;

	private final CommissionConfig config;

	/** Each share means that commission will be paid to some {@link Beneficiary} in accordance to some commission contract and hierarchy. */
	private final ImmutableList<SalesCommissionShare> shares;

	@JsonCreator
	@Builder(toBuilder = true)
	private CommissionInstance(
			@JsonProperty("id") @Nullable final CommissionInstanceId id,
			@JsonProperty("currentTriggerData") @NonNull final CommissionTriggerData currentTriggerData,
			@JsonProperty("config") @NonNull final CommissionConfig config,
			@JsonProperty("shares") @Singular final List<SalesCommissionShare> shares)
	{
		this.id = id;
		this.currentTriggerData = currentTriggerData;
		this.config = config;
		this.shares = ImmutableList.copyOf(shares);
	}
}
