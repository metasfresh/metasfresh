package de.metas.ui.web.material.cockpit.rowfactory;

<<<<<<< HEAD
import java.math.BigDecimal;
import java.time.LocalDate;

import org.compiere.util.TimeUtil;

=======
import de.metas.material.cockpit.ProductWithDemandSupply;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.product.ProductId;
import lombok.NonNull;
import lombok.Value;
<<<<<<< HEAD
=======
import org.compiere.util.TimeUtil;

import java.time.LocalDate;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/*
 * #%L
 * metasfresh-webui-api
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

@Value
public class MainRowBucketId
{
<<<<<<< HEAD
=======
	@NonNull ProductId productId;
	@NonNull LocalDate date;

	private MainRowBucketId(
			@NonNull final ProductId productId,
			@NonNull final LocalDate date)
	{
		this.productId = productId;
		this.date = date;
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public static MainRowBucketId createInstanceForCockpitRecord(
			@NonNull final I_MD_Cockpit dataRecord)
	{
		return new MainRowBucketId(
				ProductId.ofRepoId(dataRecord.getM_Product_ID()),
				TimeUtil.asLocalDate(dataRecord.getDateGeneral()));
	}

	public static MainRowBucketId createInstanceForStockRecord(
			@NonNull final I_MD_Stock stockRecord,
			@NonNull final LocalDate date)
	{
		return new MainRowBucketId(
				ProductId.ofRepoId(stockRecord.getM_Product_ID()),
				date);
	}

<<<<<<< HEAD
=======
	@NonNull
	public static MainRowBucketId createInstanceForQuantitiesRecord(
			@NonNull final ProductWithDemandSupply qtyRecord,
			@NonNull final LocalDate date)
	{
		return new MainRowBucketId(
				qtyRecord.getProductId(),
				date);
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public static MainRowBucketId createPlainInstance(@NonNull final ProductId productId, @NonNull final LocalDate date)
	{
		return new MainRowBucketId(productId, date);
	}
<<<<<<< HEAD

	ProductId productId;
	LocalDate date;

	private BigDecimal pmmQtyPromised = BigDecimal.ZERO;

	private BigDecimal qtyReserved = BigDecimal.ZERO;

	private BigDecimal qtyOrdered = BigDecimal.ZERO;

	private BigDecimal qtyMaterialentnahme = BigDecimal.ZERO;

	private BigDecimal qtyMrp = BigDecimal.ZERO;

	private BigDecimal qtyPromised = BigDecimal.ZERO;

	private BigDecimal qtyOnHand = BigDecimal.ZERO;

	private MainRowBucketId(
			@NonNull final ProductId productId,
			@NonNull final LocalDate date)
	{
		this.productId = productId;
		this.date = date;
	}

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
