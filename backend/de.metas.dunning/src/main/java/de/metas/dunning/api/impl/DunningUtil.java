package de.metas.dunning.api.impl;

/*
 * #%L
 * de.metas.dunning
 * %%
 * Copyright (C) 2015 metas GmbH
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

<<<<<<< HEAD
import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.service.ClientId;
import org.compiere.util.TimeUtil;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.currency.ICurrencyBL;
import de.metas.dunning.api.IDunningUtil;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
<<<<<<< HEAD
=======
import lombok.NonNull;
import org.adempiere.service.ClientId;

import java.math.BigDecimal;
import java.sql.Timestamp;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

public class DunningUtil implements IDunningUtil
{
	@Override
	public BigDecimal currencyConvert(
			BigDecimal Amt,
			int CurFrom_ID,
			int CurTo_ID,
<<<<<<< HEAD
			Timestamp ConvDate,
=======
			@NonNull Timestamp ConvDate,
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			CurrencyConversionTypeId conversionTypeId,
			int AD_Client_ID,
			int AD_Org_ID)
	{
		return Services.get(ICurrencyBL.class).convert(
				Amt,
				CurrencyId.ofRepoId(CurFrom_ID),
				CurrencyId.ofRepoId(CurTo_ID),
<<<<<<< HEAD
				TimeUtil.asLocalDate(ConvDate),
=======
				ConvDate.toInstant(),
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				conversionTypeId,
				ClientId.ofRepoId(AD_Client_ID),
				OrgId.ofRepoId(AD_Org_ID));
	}

	@Override
	public CurrencyConversionTypeId getDefaultCurrencyConvertionTypeId()
	{
		return null;
	}
}
