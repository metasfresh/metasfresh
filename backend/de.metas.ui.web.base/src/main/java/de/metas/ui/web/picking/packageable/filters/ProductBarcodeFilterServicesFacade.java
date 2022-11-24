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
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.edi.EDIProductLookup;
import de.metas.handlingunits.edi.EDIProductLookupService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.PlainContextAware;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Service
public class ProductBarcodeFilterServicesFacade
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final EDIProductLookupService ediProductLookupService;
	private final HUQRCodesService huQRCodesService;

	public ProductBarcodeFilterServicesFacade(
			@NonNull final EDIProductLookupService ediProductLookupService,
			@NonNull final HUQRCodesService huQRCodesService)
	{
		this.ediProductLookupService = ediProductLookupService;
		this.huQRCodesService = huQRCodesService;
	}

	public ICompositeQueryFilter<I_M_Packageable_V> createCompositeQueryFilter()
	{
		return queryBL.createCompositeQueryFilter(I_M_Packageable_V.class);
	}

	public SqlAndParams toSqlAndParams(@NonNull final IQueryFilter<I_M_Packageable_V> filter)
	{
		final ISqlQueryFilter sqlQueryFilter = ISqlQueryFilter.cast(filter);
		final String sql = sqlQueryFilter.getSql();
		final List<Object> sqlParams = sqlQueryFilter.getSqlParams(Env.getCtx());
		return SqlAndParams.of(sql, sqlParams);
	}

	public ImmutableList<EDIProductLookup> getEDIProductLookupByUPC(@NonNull final String upc)
	{
		return ediProductLookupService.lookupByUPC(upc, true);
	}

	public Optional<ProductId> getProductIdByBarcode(@NonNull final String barcode, @NonNull final ClientId clientId)
	{
		return productDAO.getProductIdByBarcode(barcode, clientId);
	}

	public Optional<I_M_HU> getHUByBarcode(@NonNull final String barcodeStringParam)
	{
		final String barcodeString = StringUtils.trimBlankToNull(barcodeStringParam);
		if (barcodeString == null)
		{
			// shall not happen
			throw new AdempiereException("No barcode provided");
		}

		final GlobalQRCode globalQRCode = GlobalQRCode.parse(barcodeString).orNullIfError();
		if(globalQRCode != null)
		{
			final HUQRCode huQRCode = HUQRCode.fromGlobalQRCode(globalQRCode);
			return huQRCodesService.getHuIdByQRCodeIfExists(huQRCode)
					.map(handlingUnitsDAO::getById);
		}
		else
		{
			final I_M_HU hu = handlingUnitsDAO.createHUQueryBuilder()
					.setHUStatus(X_M_HU.HUSTATUS_Active)
					.setOnlyWithBarcode(barcodeString)
					.firstOnly();
			return Optional.ofNullable(hu);
		}
	}

	public Optional<I_M_HU> getHUBySSCC18(@NonNull final String sscc18)
	{
		final I_M_HU hu = handlingUnitsDAO.createHUQueryBuilder()
				.setHUStatus(X_M_HU.HUSTATUS_Active)
				// match SSCC18 attribute
				.addOnlyWithAttribute(HUAttributeConstants.ATTR_SSCC18_Value, sscc18)
				// only HU's with BPartner set shall be considered (06821)
				.setOnlyIfAssignedToBPartner(true)
				.firstOnly();
		return Optional.ofNullable(hu);
	}

	public Optional<ProductBarcodeFilterData> createDataFromHU(
			final @Nullable String barcode,
			final @Nullable I_M_HU hu)
	{
		if (hu == null)
		{
			return Optional.empty();
		}

		final ImmutableSet<ProductId> productIds = extractProductIds(hu);
		if (productIds.isEmpty())
		{
			return Optional.empty();
		}

		final ICompositeQueryFilter<I_M_Packageable_V> filter = queryBL.createCompositeQueryFilter(I_M_Packageable_V.class)
				.addInArrayFilter(I_M_Packageable_V.COLUMNNAME_M_Product_ID, productIds);

		final WarehouseId warehouseId = IHandlingUnitsBL.extractWarehouseIdOrNull(hu);
		if (warehouseId != null)
		{
			filter.addInArrayFilter(I_M_Packageable_V.COLUMNNAME_M_Warehouse_ID, null, warehouseId);
		}

		final BPartnerId bpartnerId = IHandlingUnitsBL.extractBPartnerIdOrNull(hu);
		if (bpartnerId != null)
		{
			filter.addEqualsFilter(I_M_Packageable_V.COLUMNNAME_C_BPartner_Customer_ID, bpartnerId);
		}

		return Optional.of(ProductBarcodeFilterData.builder()
								   .barcode(barcode)
								   .huId(HuId.ofRepoId(hu.getM_HU_ID()))
								   .sqlWhereClause(toSqlAndParams(filter))
								   .build());
	}

	private ImmutableSet<ProductId> extractProductIds(@NonNull final I_M_HU hu)
	{
		final IMutableHUContext huContext = handlingUnitsBL.createMutableHUContext(PlainContextAware.newWithThreadInheritedTrx());
		return huContext.getHUStorageFactory()
				.getStorage(hu)
				.getProductStorages()
				.stream()
				.map(IHUProductStorage::getProductId)
				.collect(ImmutableSet.toImmutableSet());
	}
}
