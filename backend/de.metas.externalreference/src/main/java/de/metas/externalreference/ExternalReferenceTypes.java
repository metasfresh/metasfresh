/*
 * #%L
 * de.metas.externalreference
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

package de.metas.externalreference;

import de.metas.externalreference.allergen.AllergenExternalReferenceType;
import de.metas.externalreference.bankaccount.BPBankAccountType;
import de.metas.externalreference.bpartner.BPartnerExternalReferenceType;
import de.metas.externalreference.bpartnerlocation.BPLocationExternalReferenceType;
import de.metas.externalreference.greeting.GreetingExternalReferenceType;
import de.metas.externalreference.pricelist.PriceListExternalReferenceType;
import de.metas.externalreference.pricelist.PriceListVersionExternalReferenceType;
import de.metas.externalreference.product.ProductExternalReferenceType;
import de.metas.externalreference.productcategory.ProductCategoryExternalReferenceType;
import de.metas.externalreference.productprice.ProductPriceExternalReferenceType;
import de.metas.externalreference.shipper.ShipperExternalReferenceType;
import de.metas.externalreference.uom.UOMExternalReferenceType;
import de.metas.externalreference.warehouse.WarehouseExternalReferenceType;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ExternalReferenceTypes
{
	private final Map<String, IExternalReferenceType> typesByCode = new HashMap<>();

	public ExternalReferenceTypes()
	{
		registerType(NullExternalReferenceType.NULL);
		registerType(BPartnerExternalReferenceType.BPARTNER);
		registerType(BPLocationExternalReferenceType.BPARTNER_LOCATION);
		registerType(BPartnerExternalReferenceType.BPARTNER_VALUE);
		registerType(ProductExternalReferenceType.PRODUCT);
		registerType(ProductCategoryExternalReferenceType.PRODUCT_CATEGORY);
		registerType(ExternalUserReferenceType.USER_ID);
		registerType(PriceListExternalReferenceType.PRICE_LIST);
		registerType(PriceListVersionExternalReferenceType.PRICE_LIST_VERSION);
		registerType(WarehouseExternalReferenceType.WAREHOUSE);
		registerType(ProductPriceExternalReferenceType.PRODUCT_PRICE);
		registerType(ShipperExternalReferenceType.SHIPPER);
		registerType(UOMExternalReferenceType.UOM);
		registerType(AllergenExternalReferenceType.ALLERGEN);
		registerType(BPBankAccountType.BPBankAccount);
		registerType(GreetingExternalReferenceType.GREETING);
	}

	public void registerType(@NonNull final IExternalReferenceType type)
	{
		typesByCode.put(type.getCode(), type);
	}

	@NonNull
	public Optional<IExternalReferenceType> ofCode(@NonNull final String code)
	{
		final IExternalReferenceType externalReferenceType = typesByCode.get(code);
		return Optional.ofNullable(externalReferenceType);
	}
}
