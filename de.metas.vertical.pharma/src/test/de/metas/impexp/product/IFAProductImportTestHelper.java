package de.metas.impexp.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.vertical.pharma.model.I_I_Pharma_Product;
import de.metas.vertical.pharma.model.I_M_Product;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.UtilityClass;

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
 * A collection of IFA product import test helpers.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
/* package */class IFAProductImportTestHelper
{

	public void assertIFAProductImported(final I_I_Pharma_Product ifaProduct)
	{
		final I_M_Product product = InterfaceWrapperHelper.create(ifaProduct.getM_Product(), I_M_Product.class);
		assertThat(product).isNotNull();
		assertThat(product.getValue()).isNotNull();
		assertThat(product.getValue()).isEqualTo(ifaProduct.getA00PZN());
		assertThat(product.getName()).isNotNull();
		assertThat(product.getName()).isEqualTo(ifaProduct.getA00PNAM());
		assertThat(product.getDescription()).isEqualTo(ifaProduct.getA00PBEZ());
		assertThat(product.getUPC()).isEqualTo(ifaProduct.getA00GTIN());
		assertThat(product.getPackageSize()).isEqualTo(ifaProduct.getA00PGMENG());
		assertThat(product.getC_UOM()).isNotNull();
		assertThat(product.getC_UOM().getX12DE355()).isEqualTo("PCE");
		assertThat(product.getPackage_UOM()).isNotNull();
		assertThat(product.getPackage_UOM().getX12DE355()).isEqualTo(ifaProduct.getPackage_UOM().getX12DE355());
	}
	
	@Builder
	@Value
	public class IFAFlags
	{
		final boolean isColdChain;
		final boolean isPrescription;
		final boolean isNarcotic;
		final boolean isTFG;
	}
	
	public void assertIFAProductFlags(final I_I_Pharma_Product ifaProduct, final @NonNull IFAFlags flags)
	{
		final I_M_Product product = InterfaceWrapperHelper.create(ifaProduct.getM_Product(), I_M_Product.class);
		assertThat(product).isNotNull();
		assertThat(product.isColdChain()).isEqualTo(flags.isColdChain());
		assertThat(product.isPrescription()).isEqualTo(flags.isPrescription());
		assertThat(product.isNarcotic()).isEqualTo(flags.isNarcotic());
		assertThat(product.isTFG()).isEqualTo(flags.isTFG());
	}

}
