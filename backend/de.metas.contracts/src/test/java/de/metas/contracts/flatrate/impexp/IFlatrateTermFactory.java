package de.metas.contracts.flatrate.impexp;

import de.metas.contracts.model.I_I_Flatrate_Term;
import lombok.Builder;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Fluent {@link I_I_Flatrate_Term} factory
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Builder
@Value
/* package */class IFlatrateTermFactory
{
	private final Properties ctx;
	private final int bartnerId;
	private final int dropShipBPartnerId;
	private final int flatrateConditionsId;
	private final int productId;
	private final BigDecimal price;
	private final BigDecimal qty;
	private final Timestamp startDate;
	private final Timestamp endDate;
	private final Timestamp masterStartDate;
	private final Timestamp masterEndDate;

	public static class IFlatrateTermFactoryBuilder
	{
		public I_I_Flatrate_Term build()
		{
			return createImportRecord();
		}

		private I_I_Flatrate_Term createImportRecord()
		{
			final I_I_Flatrate_Term iFlatrateTerm = InterfaceWrapperHelper.create(ctx, I_I_Flatrate_Term.class, ITrx.TRXNAME_None);
			iFlatrateTerm.setC_BPartner_ID(bartnerId);
			iFlatrateTerm.setDropShip_BPartner_ID(dropShipBPartnerId);
			iFlatrateTerm.setC_Flatrate_Conditions_ID(flatrateConditionsId);
			iFlatrateTerm.setM_Product_ID(productId);
			iFlatrateTerm.setPrice(price);
			iFlatrateTerm.setQty(qty);
			iFlatrateTerm.setStartDate(startDate);
			iFlatrateTerm.setEndDate(endDate);
			iFlatrateTerm.setMasterStartDate(masterStartDate);
			iFlatrateTerm.setMasterEndDate(masterEndDate);
			InterfaceWrapperHelper.save(iFlatrateTerm);
			return iFlatrateTerm;
		}

	}
}
