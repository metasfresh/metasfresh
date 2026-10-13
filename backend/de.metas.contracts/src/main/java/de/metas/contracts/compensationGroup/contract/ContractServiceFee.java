package de.metas.contracts.compensationGroup.contract;

import de.metas.product.ProductId;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

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

/** The customer's payment-service fee: its percent and the product that labels it. {@link #ZERO} when there is no fee (no product). */
@Value
public class ContractServiceFee
{
	public static final ContractServiceFee ZERO = new ContractServiceFee(Percent.ZERO, null);

	@NonNull Percent percent;
	/** The configured service-fee product, used only to label the fee; {@code null} iff {@link #percent} is zero. */
	@Nullable ProductId feeProductId;

	public static ContractServiceFee of(@NonNull final Percent percent, @NonNull final ProductId feeProductId)
	{
		return new ContractServiceFee(percent, feeProductId);
	}

	private ContractServiceFee(@NonNull final Percent percent, @Nullable final ProductId feeProductId)
	{
		this.percent = percent;
		this.feeProductId = feeProductId;
	}

	public boolean isGreaterThanZero()
	{
		return percent.signum() > 0;
	}
}
