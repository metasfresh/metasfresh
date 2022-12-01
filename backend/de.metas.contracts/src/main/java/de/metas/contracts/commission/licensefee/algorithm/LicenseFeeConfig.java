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

package de.metas.contracts.commission.licensefee.algorithm;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionType;
import de.metas.contracts.commission.licensefee.model.LicenseFeeSettingsId;
import de.metas.contracts.commission.licensefee.model.LicenseFeeSettingsLineId;
import de.metas.product.ProductId;
import de.metas.util.lang.Percent;
import io.micrometer.core.lang.Nullable;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.Optional;

@Value
public class LicenseFeeConfig implements CommissionConfig
{
	@NonNull
	LicenseFeeContract licenseFeeContract;

	@NonNull
	ProductId commissionProductId;

	@NonNull
	Percent commissionPercent;

	@NonNull
	Integer pointsPrecision;

	@NonNull
	LicenseFeeSettingsLineId licenseFeeSettingsLineId;

	@Builder
	public LicenseFeeConfig(
			@NonNull final LicenseFeeContract licenseFeeContract,
			@NonNull final ProductId commissionProductId,
			@NonNull final Percent commissionPercent,
			@NonNull final Integer pointsPrecision,
			@NonNull final LicenseFeeSettingsLineId licenseFeeSettingsLineId)
	{
		this.licenseFeeContract = licenseFeeContract;
		this.commissionProductId = commissionProductId;
		this.commissionPercent = commissionPercent;
		this.pointsPrecision = pointsPrecision;
		this.licenseFeeSettingsLineId = licenseFeeSettingsLineId;
	}

	public static boolean isInstance(@NonNull final CommissionConfig config)
	{
		return config instanceof LicenseFeeConfig;
	}

	@NonNull
	public static LicenseFeeConfig cast(@NonNull final CommissionConfig config)
	{
		return castOrEmpty(config)
				.orElseThrow(() -> new AdempiereException("Cannot cast the given commission config to LicenseFeeConfig")
						.appendParametersToMessage()
						.setParameter("config", config));
	}

	@NonNull
	public static Optional<LicenseFeeConfig> castOrEmpty(@NonNull final CommissionConfig commissionConfig)
	{
		if (isInstance(commissionConfig))
		{
			return Optional.of((LicenseFeeConfig)commissionConfig);
		}
		return Optional.empty();
	}

	@Override
	public CommissionType getCommissionType()
	{
		return CommissionType.LICENSE_FEE_COMMISSION;
	}

	@Override
	@Nullable
	public CommissionContract getContractFor(@NonNull final BPartnerId contractualBPartnerId)
	{
		if (licenseFeeContract.getContractOwnerBPartnerId().equals(contractualBPartnerId))
		{
			return licenseFeeContract;
		}
		return null;
	}

	@NonNull
	public LicenseFeeSettingsId getId()
	{
		return licenseFeeSettingsLineId.getLicenseFeeSettingsId();
	}
}
