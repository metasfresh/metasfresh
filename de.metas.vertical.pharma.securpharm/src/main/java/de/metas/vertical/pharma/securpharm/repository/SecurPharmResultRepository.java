/*
 *
 *  * #%L
 *  * %%
 *  * Copyright (C) <current year> metas GmbH
 *  * %%
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as
 *  * published by the Free Software Foundation, either version 2 of the
 *  * License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public
 *  * License along with this program. If not, see
 *  * <http://www.gnu.org/licenses/gpl-2.0.html>.
 *  * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.repository;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.InventoryId;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.inventory.IInventoryDAO;
import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.model.*;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

@Repository
public class SecurPharmResultRepository
{

	public SecurPharmProductDataResult createResult(@NonNull final SecurPharmProductDataResult productDataResult)
	{
		final I_M_Securpharm_Productdata_Result productDataResultRecord = newInstance(I_M_Securpharm_Productdata_Result.class);

		final ProductData productData = productDataResult.getProductData();
		final SecurPharmRequestLogData logData = productDataResult.getRequestLogData();

		if (productData != null)
		{
			productDataResultRecord.setExpirationDate(TimeUtil.asTimestamp(productData.getExpirationDate()));
			productDataResultRecord.sethasActiveStatus(productData.isActive());
			productDataResultRecord.setInactiveReason(productData.getInactiveReason());
			productDataResultRecord.setLotNumber(productData.getLot());
			productDataResultRecord.setProductCode(productData.getProductCode());
			productDataResultRecord.setProductCodeType(productData.getProductCodeType().name());
			productDataResultRecord.setSerialNumber(productData.getSerialNumber());
		}
		productDataResultRecord.setM_HU_ID(productDataResult.getHuId().getRepoId());
		productDataResultRecord.setIsError(productDataResultRecord.isError());
		productDataResultRecord.setRequestUrl(logData.getRequestUrl());
		productDataResultRecord.setRequestStartTime(TimeUtil.asTimestamp(logData.getRequestTime()));
		productDataResultRecord.setRequestEndTime(TimeUtil.asTimestamp(logData.getResponseTime()));
		productDataResultRecord.setTransactionIDClient(logData.getClientTransactionID());
		productDataResultRecord.setTransactionIDServer(logData.getServerTransactionID());

		save(productDataResultRecord);
		productDataResult.setResultId(SecurPharmProductDataResultId.ofRepoId(productDataResultRecord.getM_Securpharm_Productdata_Result_ID()));
		return productDataResult;
	}

	public SecurPharmActionResult createResult(@NonNull final SecurPharmActionResult securPharmActionResult)
	{
		final I_M_Securpharm_Action_Result actionResultRecord = newInstance(I_M_Securpharm_Action_Result.class);

		actionResultRecord.setAction(securPharmActionResult.getAction().name());
		actionResultRecord.setIsError(securPharmActionResult.isError());

		actionResultRecord.setM_Inventory_ID(securPharmActionResult.getInventoryId().getRepoId());
		actionResultRecord.setM_Securpharm_Productdata_Result_ID(securPharmActionResult.getProductDataResult().getResultId().getRepoId());
		final SecurPharmRequestLogData logData = securPharmActionResult.getRequestLogData();
		actionResultRecord.setRequestUrl(logData.getRequestUrl());
		actionResultRecord.setRequestStartTime(TimeUtil.asTimestamp(logData.getRequestTime()));
		actionResultRecord.setRequestEndTime(TimeUtil.asTimestamp(logData.getResponseTime()));
		actionResultRecord.setTransactionIDClient(logData.getClientTransactionID());
		actionResultRecord.setTransactionIDServer(logData.getServerTransactionID());

		save(actionResultRecord);
		securPharmActionResult.setResultId(SecurPharmActionResultId.ofRepoId(actionResultRecord.getM_Securpharm_Action_Result_ID()));
		return securPharmActionResult;
	}

	public SecurPharmActionResult getActionResultByInventoryId(@NonNull final InventoryId inventoryId, @NonNull final DecommissionAction action)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final Optional<SecurPharmActionResult> securPharmResult = queryBL
				.createQueryBuilder(I_M_Securpharm_Action_Result.class)
				.addEqualsFilter(I_M_Securpharm_Action_Result.COLUMNNAME_M_Inventory_ID, inventoryId.getRepoId())
				.addEqualsFilter(I_M_Securpharm_Action_Result.COLUMNNAME_Action, action.name())
				.orderByDescending(I_M_Securpharm_Action_Result.COLUMNNAME_RequestStartTime)
				.create().stream().findFirst().map(actionResultRecord -> ofRecord(inventoryId, actionResultRecord)).get();

		if (securPharmResult.isPresent())
		{
			final SecurPharmActionResult actionResult = securPharmResult.get();
			final int productDataId = actionResult.getProductDataResult().getResultId().getRepoId();
			queryBL
					.createQueryBuilder(I_M_Securpharm_Productdata_Result.class)
					.addEqualsFilter(I_M_Securpharm_Productdata_Result.COLUMNNAME_M_Securpharm_Productdata_Result_ID, productDataId)
					.create().stream().findFirst().map(productResultRecord -> {
				final SecurPharmProductDataResult result = actionResult.getProductDataResult();
				return ofRecord(productResultRecord, result);
			});
			return actionResult;
		}
		return null;
	}

	private Optional<SecurPharmActionResult> ofRecord(@NonNull InventoryId inventoryId, I_M_Securpharm_Action_Result actionResult)
	{
		final SecurPharmActionResult securPharmActionResult = new SecurPharmActionResult();
		securPharmActionResult.setResultId(SecurPharmActionResultId.ofRepoId(actionResult.getM_Securpharm_Action_Result_ID()));
		securPharmActionResult.setAction(DecommissionAction.valueOf(actionResult.getAction()));
		final SecurPharmRequestLogData logData = SecurPharmRequestLogData.builder()
				.requestTime(TimeUtil.asLocalDateTime(actionResult.getRequestStartTime()))
				.responseTime(TimeUtil.asLocalDateTime(actionResult.getRequestEndTime()))
				.requestUrl(actionResult.getRequestUrl())
				.clientTransactionID(actionResult.getTransactionIDClient())
				.clientTransactionID(actionResult.getTransactionIDServer())
				.build();
		securPharmActionResult.setRequestLogData(logData);
		securPharmActionResult.setError(actionResult.isError());
		securPharmActionResult.setInventoryId(inventoryId);
		final SecurPharmProductDataResult productDataResult = new SecurPharmProductDataResult();
		productDataResult.setResultId(SecurPharmProductDataResultId.ofRepoId(actionResult.getM_Securpharm_Productdata_Result_ID()));
		securPharmActionResult.setProductDataResult(productDataResult);
		return Optional.of(securPharmActionResult);
	}

	private Optional<SecurPharmProductDataResult> ofRecord(@NonNull final I_M_Securpharm_Productdata_Result productResult, @NonNull final SecurPharmProductDataResult result)
	{
		result.setHuId(HuId.ofRepoId(productResult.getM_HU_ID()));
		result.setError(result.isError());
		final SecurPharmRequestLogData logData = SecurPharmRequestLogData.builder()
				.requestTime(TimeUtil.asLocalDateTime(productResult.getRequestStartTime()))
				.responseTime(TimeUtil.asLocalDateTime(productResult.getRequestEndTime()))
				.requestUrl(productResult.getRequestUrl())
				.clientTransactionID(productResult.getTransactionIDClient())
				.clientTransactionID(productResult.getTransactionIDServer())
				.build();
		result.setRequestLogData(logData);
		final ProductData productData = ProductData.builder()
				.isActive(productResult.isActive())
				.expirationDate(TimeUtil.asLocalDate(productResult.getExpirationDate()))
				.inactiveReason(productResult.getInactiveReason())
				.lot(productResult.getLotNumber())
				.productCode(productResult.getProductCode())
				.productCodeType(ProductCodeType.valueOf(productResult.getProductCodeType()))
				.serialNumber(productResult.getSerialNumber())
				.build();
		result.setProductData(productData);
		return Optional.of(result);
	}

	public SecurPharmProductDataResult getProductResultByInventoryId(@NonNull final InventoryId inventoryId)
	{
		final Optional<HuId> huId = Services.get(IInventoryDAO.class)
				.retrieveLinesForInventoryId(inventoryId.getRepoId(), I_M_InventoryLine.class)
				.stream().findFirst().map(invLine -> HuId.ofRepoId(invLine.getM_HU_ID()));
		if (!huId.isPresent())
		{
			return null;
		}
		else
		{
			final IQueryBL queryBL = Services.get(IQueryBL.class);
			final SecurPharmProductDataResult productDataResult = queryBL
					.createQueryBuilder(I_M_Securpharm_Productdata_Result.class)
					.addEqualsFilter(I_M_Securpharm_Productdata_Result.COLUMNNAME_M_HU_ID, huId.get().getRepoId())
					.create().stream().findFirst().map(productResult -> {
						final SecurPharmProductDataResult result = new SecurPharmProductDataResult();
						return ofRecord(productResult, result);
					}).get().orElse(null);
			return productDataResult;
		}
	}

}
