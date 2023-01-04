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

package de.metas.contracts.commission.mediated.algorithm;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionType;
import de.metas.contracts.commission.mediated.model.MediatedCommissionSettingsId;
import de.metas.contracts.commission.mediated.model.MediatedCommissionSettingsLineId;
import de.metas.product.ProductId;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.Optional;

@Value
@Builder
public class MediatedCommissionConfig implements CommissionConfig
{
	@NonNull
	Percent commissionPercent;

	@NonNull
	ProductId commissionProductId;

	@NonNull
	Integer pointsPrecision;

	@NonNull
	MediatedCommissionContract mediatedCommissionContract;

	@NonNull
	MediatedCommissionSettingsLineId mediatedCommissionSettingsLineId;

	@NonNull
	public static MediatedCommissionConfig cast(@NonNull final CommissionConfig commissionConfig)
	{
		return castOrEmpty(commissionConfig)
				.orElseThrow(() -> new AdempiereException("Cannot cast the given config to MediatedCommissionConfig")
						.appendParametersToMessage()
						.setParameter("config", commissionConfig));
	}

	@NonNull
	public static Optional<MediatedCommissionConfig> castOrEmpty(@NonNull final CommissionConfig commissionConfig)
	{
		if (commissionConfig instanceof MediatedCommissionConfig)
		{
			return Optional.of((MediatedCommissionConfig)commissionConfig);
		}
		return Optional.empty();
	}

	public static boolean isInstance(@NonNull final CommissionConfig commissionConfig)
	{
		return commissionConfig instanceof MediatedCommissionConfig;
	}

	@Override
	@NonNull
	public CommissionType getCommissionType()
	{
		return CommissionType.MEDIATED_COMMISSION;
	}

	@Override
	public MediatedCommissionContract getContractFor(@NonNull final BPartnerId contractualBPartnerId)
	{
		if (contractualBPartnerId.equals(mediatedCommissionContract.getContractOwnerBPartnerId()))
		{
			return mediatedCommissionContract;
		}

		return null;
	}

	@Override
	@NonNull
	public ProductId getCommissionProductId()
	{
		return commissionProductId;
	}

	@NonNull
	public MediatedCommissionSettingsId getId()
	{
		return mediatedCommissionSettingsLineId.getMediatedCommissionSettingsId();
	}
}
