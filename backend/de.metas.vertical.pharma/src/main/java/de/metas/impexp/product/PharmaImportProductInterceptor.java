/**
 *
 */
package de.metas.impexp.product;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.time.LocalDate;

import de.metas.common.util.time.SystemTime;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;

import de.metas.impexp.processing.IImportInterceptor;
import de.metas.impexp.processing.IImportProcess;
import de.metas.location.ICountryDAO;
import de.metas.pricing.service.ProductPriceCreateRequest;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.ITaxDAO.TaxCategoryQuery;
import de.metas.tax.api.ITaxDAO.TaxCategoryQuery.VATType;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.vertical.pharma.model.I_I_Product;
import de.metas.vertical.pharma.model.I_M_Product;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma
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
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PharmaImportProductInterceptor implements IImportInterceptor
{
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	public static final PharmaImportProductInterceptor instance = new PharmaImportProductInterceptor();
	private final LocalDate firstDayYear =  TimeUtil.asLocalDate(TimeUtil.getYearFirstDay(SystemTime.asDate()));

	private PharmaImportProductInterceptor()
	{

	}

	@Override
	public void onImport(IImportProcess<?> process, Object importModel, Object targetModel, int timing)
	{
		if (timing != IImportInterceptor.TIMING_AFTER_IMPORT)
		{
			return;
		}

		final I_I_Product iproduct = InterfaceWrapperHelper.create(importModel, I_I_Product.class);

		if (isPharmaProductImport(iproduct))
		{
			final I_M_Product product = InterfaceWrapperHelper.create(targetModel, I_M_Product.class);
			onPharmaProductImported(iproduct, product);
		}
	}

	private boolean isPharmaProductImport(final I_I_Product iproduct)
	{
		return !Check.isEmpty(iproduct.getPharmaProductCategory_Name(), true);
	}

	private void onPharmaProductImported(@NonNull final I_I_Product iproduct, @NonNull final I_M_Product product )
	{
		product.setIsPrescription(iproduct.isPrescription());
		product.setIsNarcotic(iproduct.isNarcotic());
		product.setIsColdChain(iproduct.isColdChain());
		product.setIsTFG(iproduct.isTFG());

		if (!Check.isEmpty(iproduct.getFAM_ZUB(), true))
		{
			product.setFAM_ZUB(iproduct.getFAM_ZUB());
		}

		if (iproduct.getM_DosageForm_ID() > 0)
		{
			product.setM_DosageForm_ID(iproduct.getM_DosageForm_ID());
		}

		if (iproduct.getM_Indication_ID() > 0)
		{
			product.setM_Indication_ID(iproduct.getM_Indication_ID());
		}

		if (iproduct.getM_PharmaProductCategory_ID() > 0)
		{
			product.setM_PharmaProductCategory_ID(iproduct.getM_PharmaProductCategory_ID());
		}
		save(product);

		importPrices(iproduct);
	}

	private void importPrices(@NonNull final I_I_Product importRecord)
	{
		createAPU(importRecord);
		createAEP(importRecord);
	}

	private void createAPU(@NonNull final I_I_Product importRecord)
	{
		final TaxCategoryQuery query = TaxCategoryQuery.builder()
				.type(VATType.RegularVAT)
				.countryId(Services.get(ICountryDAO.class).getDefaultCountryId())
				.build();


		final ProductPriceCreateRequest request = ProductPriceCreateRequest.builder()
				.price(importRecord.getA01APU())
				.priceListId(importRecord.getAPU_Price_List_ID())
				.productId(importRecord.getM_Product_ID())
				.validDate(firstDayYear)
				.taxCategoryId(taxDAO.findTaxCategoryId(query))
				.build();

		final ProductPriceImporter command = new ProductPriceImporter(request);
		command.createProductPrice_And_PriceListVersionIfNeeded();
	}

	private void createAEP(@NonNull final I_I_Product importRecord)
	{
		final TaxCategoryQuery query = TaxCategoryQuery.builder()
				.type(VATType.RegularVAT)
				.countryId(Services.get(ICountryDAO.class).getDefaultCountryId())
				.build();

		final ProductPriceCreateRequest request = ProductPriceCreateRequest.builder()
				.price(importRecord.getA01AEP())
				.priceListId(importRecord.getAEP_Price_List_ID())
				.productId(importRecord.getM_Product_ID())
				.validDate(firstDayYear)
				.taxCategoryId(taxDAO.findTaxCategoryId(query))
				.build();

		final ProductPriceImporter command = new ProductPriceImporter(request);
		command.createProductPrice_And_PriceListVersionIfNeeded();
	}
}
