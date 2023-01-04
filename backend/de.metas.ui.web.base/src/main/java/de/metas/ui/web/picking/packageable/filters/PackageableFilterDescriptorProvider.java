/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.picking.packageable.filters;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.i18n.IMsgBL;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.json.JSONDocumentFilter;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.window.datatypes.PanelLayoutType;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;

import java.util.Collection;
import java.util.Optional;

public class PackageableFilterDescriptorProvider implements DocumentFilterDescriptorsProvider
{
	// services
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final ProductBarcodeFilterServicesFacade services;
	private final ImmutableList<ProductBarcodeFilterDataFactory> productDataFactories;

	public static final String ProductBarcode_FilterId = "ProductBarcodeFilter";
	private static final String PARAM_Barcode = "Barcode";
	private static final String PARAM_Data = "$data";

	private final ImmutableMap<String, DocumentFilterDescriptor> filtersById;

	public PackageableFilterDescriptorProvider(@NonNull final ProductBarcodeFilterServicesFacade services)
	{
		this.services = services;
		this.productDataFactories = ImmutableList.of(
				// Check if given barcode is a product UPC or M_Product.Value
				new UPCProductBarcodeFilterDataFactory(),
				// Check if given barcode is a SSCC18 of an existing HU
				new SSCC18ProductBarcodeFilterDataFactory(),
				// Check if given barcode is an HU internal barcode
				new HUBarcodeFilterDataFactory());

		final DocumentFilterDescriptor productBarcodeFilterDescriptor = createProductBarcodeFilterDescriptor();
		filtersById = ImmutableMap.of(productBarcodeFilterDescriptor.getFilterId(), productBarcodeFilterDescriptor);
	}

	private DocumentFilterDescriptor createProductBarcodeFilterDescriptor()
	{
		return DocumentFilterDescriptor.builder()
				.setFilterId(ProductBarcode_FilterId)
				.setFrequentUsed(true)
				.setParametersLayoutType(PanelLayoutType.SingleOverlayField)
				.addParameter(DocumentFilterParamDescriptor.builder()
									  .fieldName(PARAM_Barcode)
									  .displayName(msgBL.translatable("Barcode"))
									  .mandatory(true)
									  .widgetType(DocumentFieldWidgetType.Text)
							  // .barcodeScannerType(BarcodeScannerType.QRCode)
				)
				.build();
	}

	@Override
	public Collection<DocumentFilterDescriptor> getAll()
	{
		return filtersById.values();
	}

	@Override
	public DocumentFilterDescriptor getByFilterIdOrNull(final String filterId)
	{
		return filtersById.get(filterId);
	}

	@Override
	public DocumentFilter unwrap(final @NonNull JSONDocumentFilter jsonFilter)
	{
		if (ProductBarcode_FilterId.equals(jsonFilter.getFilterId()))
		{
			return unwrapProductBarcodeFilter(jsonFilter);
		}
		else
		{
			return DocumentFilterDescriptorsProvider.super.unwrap(jsonFilter);
		}
	}

	private DocumentFilter unwrapProductBarcodeFilter(@NonNull final JSONDocumentFilter jsonFilter)
	{
		final DocumentFilter filter = DocumentFilterDescriptorsProvider.super.unwrap(jsonFilter);
		final String barcode = filter.getParameterValueAsString(PARAM_Barcode);

		if (barcode == null || Check.isBlank(barcode))
		{
			return filter;
		}

		final ProductBarcodeFilterData data = createData(barcode).orElse(null);
		if (data != null)
		{
			return filter.toBuilder()
					.addInternalParameter(DocumentFilterParam.ofNameEqualsValue(PARAM_Data, data))
					.build();
		}
		else
		{
			return filter;
		}
	}

	public final Optional<ProductBarcodeFilterData> createData(@NonNull final String barcode)
	{
		//
		// Pick the first matcher
		for (final ProductBarcodeFilterDataFactory factory : productDataFactories)
		{
			final Optional<ProductBarcodeFilterData> data = factory.createData(services, barcode, ClientId.METASFRESH);
			if (data.isPresent())
			{
				return data;
			}
		}

		return Optional.empty();
	}

	private ImmutableList<ProductBarcodeFilterDataFactory> getProductBarcodeFilterDataFactories()
	{
		return ImmutableList.of(
				// Check if given barcode is a product UPC or M_Product.Value
				new UPCProductBarcodeFilterDataFactory(),
				// Check if given barcode is a SSCC18 of an existing HU
				new SSCC18ProductBarcodeFilterDataFactory(),
				// Check if given barcode is an HU internal barcode
				new HUBarcodeFilterDataFactory());
	}

	public static Optional<ProductBarcodeFilterData> extractProductBarcodeFilterData(@NonNull final DocumentFilter filter)
	{
		if (!ProductBarcode_FilterId.equals(filter.getFilterId()))
		{
			throw new AdempiereException("Invalid filterId " + filter.getFilterId() + ". Expected: " + ProductBarcode_FilterId);
		}

		return Optional.ofNullable(filter.getParameterValueAs(PARAM_Data));
	}

	public static Optional<ProductBarcodeFilterData> extractProductBarcodeFilterData(@NonNull final IView view)
	{
		return view.getFilters()
				.stream()
				.filter(filter -> ProductBarcode_FilterId.equals(filter.getFilterId()))
				.findFirst()
				.flatMap(PackageableFilterDescriptorProvider::extractProductBarcodeFilterData);
	}
}
