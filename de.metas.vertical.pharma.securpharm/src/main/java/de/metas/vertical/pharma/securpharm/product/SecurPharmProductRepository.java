package de.metas.vertical.pharma.securpharm.product;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Collection;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.cache.CCache;
import de.metas.handlingunits.HuId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.client.schema.JsonExpirationDate;
import de.metas.vertical.pharma.securpharm.client.schema.JsonProductPackageState;
import de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.securpharm
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Repository
public class SecurPharmProductRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<SecurPharmProductId, SecurPharmProduct> productDataCache = CCache.<SecurPharmProductId, SecurPharmProduct> builder()
			.tableName(I_M_Securpharm_Productdata_Result.Table_Name)
			.build();

	public void save(@NonNull final SecurPharmProduct product)
	{
		I_M_Securpharm_Productdata_Result record = null;
		if (product.getId() != null)
		{
			record = load(product.getId(), I_M_Securpharm_Productdata_Result.class);
		}
		if (record == null)
		{
			record = newInstance(I_M_Securpharm_Productdata_Result.class);
		}

		record.setIsError(product.isError());
		record.setLastResultCode(product.getResultCode());
		record.setLastResultMessage(product.getResultMessage());
		record.setM_HU_ID(product.getHuId().getRepoId());

		//
		// Product data
		final ProductDetails productDetails = product.getProductDetails();
		record.setExpirationDate(productDetails != null ? productDetails.getExpirationDate().toTimestamp() : null);
		record.setLotNumber(productDetails != null ? productDetails.getLot() : null);
		record.setProductCode(productDetails != null ? productDetails.getProductCode() : null);
		record.setProductCodeType(productDetails != null ? productDetails.getProductCodeType().name() : null);
		record.setSerialNumber(productDetails != null ? productDetails.getSerialNumber() : null);

		record.setActiveStatus(productDetails != null ? productDetails.getActiveStatus().toYesNoString() : null);
		record.setInactiveReason(productDetails != null ? productDetails.getInactiveReason() : null);

		record.setIsDecommissioned(productDetails != null ? productDetails.isDecommissioned() : false);
		record.setDecommissionedServerTransactionId(productDetails != null ? productDetails.getDecommissionedServerTransactionId() : null);

		saveRecord(record);
		final SecurPharmProductId productId = SecurPharmProductId.ofRepoId(record.getM_Securpharm_Productdata_Result_ID());
		product.setId(productId);
	}

	public SecurPharmProduct getProductById(@NonNull final SecurPharmProductId id)
	{
		final Collection<SecurPharmProduct> products = getProductsByIds(ImmutableList.of(id));
		if (products.isEmpty())
		{
			throw new AdempiereException("@NotFound@" + id);
		}
		else if (products.size() == 1)
		{
			return products.iterator().next();
		}
		else
		{
			// shall not happen
			throw new AdempiereException("@InternalError@ Got more results for " + id + ": " + products);
		}

	}

	public Collection<SecurPharmProduct> getProductsByIds(@NonNull final Collection<SecurPharmProductId> productDataResultIds)
	{
		if (productDataResultIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return productDataCache.getAllOrLoad(productDataResultIds, this::retrieveProductDataResultByIds);
	}

	private Map<SecurPharmProductId, SecurPharmProduct> retrieveProductDataResultByIds(@NonNull final Collection<SecurPharmProductId> productDataResultIds)
	{
		Check.assumeNotEmpty(productDataResultIds, "productDataResultIds is not empty");

		return queryBL
				.createQueryBuilder(I_M_Securpharm_Productdata_Result.class)
				.addInArrayFilter(I_M_Securpharm_Productdata_Result.COLUMNNAME_M_Securpharm_Productdata_Result_ID, productDataResultIds)
				.create()
				.stream()
				.map(record -> toProductDataResult(record))
				.collect(GuavaCollectors.toImmutableMapByKey(SecurPharmProduct::getId));
	}

	public Collection<SecurPharmProduct> getProductsByHuIds(@NonNull final Collection<HuId> huIds)
	{
		if (huIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableSet<SecurPharmProductId> ids = queryBL
				.createQueryBuilder(I_M_Securpharm_Productdata_Result.class)
				.addInArrayFilter(I_M_Securpharm_Productdata_Result.COLUMNNAME_M_HU_ID, huIds)
				.create()
				.listIds(SecurPharmProductId::ofRepoId);

		return getProductsByIds(ids);
	}

	private static SecurPharmProduct toProductDataResult(@NonNull final I_M_Securpharm_Productdata_Result record)
	{
		final boolean error = record.isError();

		return SecurPharmProduct.builder()
				.error(error)
				.resultCode(record.getLastResultCode())
				.resultMessage(record.getLastResultMessage())
				.productDetails(!error ? toProductDetails(record) : null)
				.huId(HuId.ofRepoId(record.getM_HU_ID()))
				.id(SecurPharmProductId.ofRepoId(record.getM_Securpharm_Productdata_Result_ID()))
				.build();
	}

	private static ProductDetails toProductDetails(final I_M_Securpharm_Productdata_Result record)
	{
		return ProductDetails.builder()
				.productCode(record.getProductCode())
				.productCodeType(ProductCodeType.ofCode(record.getProductCodeType()))
				//
				.lot(record.getLotNumber())
				.serialNumber(record.getSerialNumber())
				//
				.expirationDate(JsonExpirationDate.ofLocalDate(TimeUtil.asLocalDate(record.getExpirationDate())))
				//
				.activeStatus(JsonProductPackageState.ofYesNoString(record.getActiveStatus()))
				.inactiveReason(record.getInactiveReason())
				//
				.decommissioned(record.isDecommissioned())
				.decommissionedServerTransactionId(record.getDecommissionedServerTransactionId())
				//
				.build();
	}
}
