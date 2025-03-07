package de.metas.tax.api;

/*
 * #%L
 * de.metas.swat.base
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

import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.lang.SOTrx;
import de.metas.organization.OrgId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Tax;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ITaxBL extends ISingletonService
{
	Tax getTaxById(TaxId taxId);

	/**
	 * Do not attempt to retrieve the C_Tax for an order (i.e invoicing is done at a different time - 1 year - from the order)<br>
	 * Also note that packaging material receipts don't have an order line and if this one had, no IC would be created for it by this handler.<br>
	 * Instead, always rely on taxing BL to bind the tax to the invoice candidate
	 * <p>
	 * Try to rely on the tax category from the pricing result<br>
	 * 07739: If that's not available, then throw an exception; don't attempt to retrieve the German tax because that method proved to return a wrong result
	 */
	@NonNull
	TaxId getTaxNotNull(
			@Nullable Object model,
			@Nullable TaxCategoryId taxCategoryId,
			int productId,
			Timestamp shipDate,
			@NonNull OrgId orgId,
			@Nullable WarehouseId warehouseId,
			BPartnerLocationAndCaptureId shipBPartnerLocationId,
			SOTrx soTrx,
			@Nullable VatCodeId vatCodeId);

	/**
	 * @param taxIncluded if true tax is included in given amount
	 */
	CalculateTaxResult calculateTax(I_C_Tax tax, BigDecimal amount, boolean taxIncluded, int scale);

	/**
	 * @return tax amount (only actual tax amount, NOT reverse charge tax amt)
	 */
	BigDecimal calculateTaxAmt(I_C_Tax tax, BigDecimal amount, boolean taxIncluded, int scale);

	/**
	 * Sets the correct flags if given tax has {@link I_C_Tax#isWholeTax()} set.
	 */
	void setupIfIsWholeTax(final I_C_Tax tax);

	TaxCategoryId retrieveRegularTaxCategoryId();

	Optional<TaxCategoryId> getTaxCategoryIdByInternalName(String internalName);

	List<Tax> getChildTaxes(@NonNull TaxId taxId);

	Tax getDefaultTax(TaxCategoryId taxCategoryId);
	Optional<TaxCategoryId> getTaxCategoryIdByName(String name);
}
