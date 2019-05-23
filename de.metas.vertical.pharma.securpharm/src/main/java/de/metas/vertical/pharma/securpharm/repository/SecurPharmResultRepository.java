/*
 *
 * * #%L
 * * %%
 * * Copyright (C) <current year> metas GmbH
 * * %%
 * * This program is free software: you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as
 * * published by the Free Software Foundation, either version 2 of the
 * * License, or (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public
 * * License along with this program. If not, see
 * * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * * #L%
 *
 */

package de.metas.vertical.pharma.securpharm.repository;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.model.DecommissionAction;
import de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Action_Result;
import de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result;
import de.metas.vertical.pharma.securpharm.model.ProductCodeType;
import de.metas.vertical.pharma.securpharm.model.ProductData;
import de.metas.vertical.pharma.securpharm.model.SecurPharmActionResult;
import de.metas.vertical.pharma.securpharm.model.SecurPharmActionResultId;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProductDataResult;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProductDataResultId;
import de.metas.vertical.pharma.securpharm.model.SecurPharmRequestLogData;
import de.metas.vertical.pharma.securpharm.model.schema.ExpirationDate;
import lombok.NonNull;

@Repository
public class SecurPharmResultRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void saveNew(@NonNull final SecurPharmProductDataResult result)
	{
		Check.assumeNull(result.getId(), "productDataResult shall not be already saved: {}", result);

		final I_M_Securpharm_Productdata_Result record = newInstance(I_M_Securpharm_Productdata_Result.class);
		record.setM_HU_ID(result.getHuId().getRepoId());

		final ProductData productData = result.getProductData();
		if (productData != null)
		{
			record.setExpirationDate(TimeUtil.asTimestamp(productData.getExpirationDate()));
			record.sethasActiveStatus(productData.isActive());
			record.setInactiveReason(productData.getInactiveReason());
			record.setLotNumber(productData.getLot());
			record.setProductCode(productData.getProductCode());
			record.setProductCodeType(productData.getProductCodeType().name());
			record.setSerialNumber(productData.getSerialNumber());
		}

		final SecurPharmRequestLogData logData = result.getRequestLogData();
		record.setIsError(logData.isError());
		record.setRequestUrl(logData.getRequestUrl());
		record.setRequestStartTime(TimeUtil.asTimestamp(logData.getRequestTime()));
		record.setRequestEndTime(TimeUtil.asTimestamp(logData.getResponseTime()));
		record.setTransactionIDClient(logData.getClientTransactionId());
		record.setTransactionIDServer(logData.getServerTransactionId());

		saveRecord(record);
		result.setId(SecurPharmProductDataResultId.ofRepoId(record.getM_Securpharm_Productdata_Result_ID()));
	}

	public void saveNew(@NonNull final SecurPharmActionResult result)
	{
		Check.assumeNull(result.getId(), "productDataResult shall not be already saved: {}", result);

		final I_M_Securpharm_Action_Result record = newInstance(I_M_Securpharm_Action_Result.class);

		record.setAction(result.getAction().getCode());
		record.setM_Inventory_ID(result.getInventoryId().getRepoId());
		record.setM_Securpharm_Productdata_Result_ID(result.getProductDataResultId().getRepoId());

		final SecurPharmRequestLogData logData = result.getRequestLogData();
		record.setIsError(logData.isError());
		record.setRequestUrl(logData.getRequestUrl());
		record.setRequestStartTime(TimeUtil.asTimestamp(logData.getRequestTime()));
		record.setRequestEndTime(TimeUtil.asTimestamp(logData.getResponseTime()));
		record.setTransactionIDClient(logData.getClientTransactionId());
		record.setTransactionIDServer(logData.getServerTransactionId());

		saveRecord(record);
		result.setId(SecurPharmActionResultId.ofRepoId(record.getM_Securpharm_Action_Result_ID()));
	}

	public Optional<SecurPharmActionResult> getActionResultByInventoryId(
			@NonNull final InventoryId inventoryId,
			@NonNull final DecommissionAction action)
	{
		//
		// Fetch Product Action Result
		final I_M_Securpharm_Action_Result actionResultRecord = queryBL
				.createQueryBuilder(I_M_Securpharm_Action_Result.class)
				.addEqualsFilter(I_M_Securpharm_Action_Result.COLUMNNAME_M_Inventory_ID, inventoryId)
				.addEqualsFilter(I_M_Securpharm_Action_Result.COLUMNNAME_Action, action.getCode())
				.orderByDescending(I_M_Securpharm_Action_Result.COLUMNNAME_RequestStartTime)
				.create()
				.first();
		if (actionResultRecord == null)
		{
			return Optional.empty();
		}

		//
		// Retrieve Product Data
		final SecurPharmProductDataResultId productDataResultId = SecurPharmProductDataResultId.ofRepoId(actionResultRecord.getM_Securpharm_Productdata_Result_ID());
		final ProductData productData = getProductDataResultById(productDataResultId).getProductData();

		return Optional.of(toActionResult(
				actionResultRecord,
				productData,
				inventoryId));
	}

	public SecurPharmProductDataResult getProductDataResultById(@NonNull final SecurPharmProductDataResultId productDataResultId)
	{
		final I_M_Securpharm_Productdata_Result productResultRecord = queryBL
				.createQueryBuilder(I_M_Securpharm_Productdata_Result.class)
				.addEqualsFilter(I_M_Securpharm_Productdata_Result.COLUMNNAME_M_Securpharm_Productdata_Result_ID, productDataResultId)
				.create()
				.firstOnlyNotNull(I_M_Securpharm_Productdata_Result.class);

		return toProductDataResult(productResultRecord);
	}

	private static SecurPharmActionResult toActionResult(
			@NonNull final I_M_Securpharm_Action_Result record,
			@NonNull final ProductData productData,
			@NonNull final InventoryId inventoryId)
	{
		final SecurPharmProductDataResultId productDataResultId = SecurPharmProductDataResultId.ofRepoId(record.getM_Securpharm_Productdata_Result_ID());

		return SecurPharmActionResult.builder()
				.productDataResultId(productDataResultId)
				.productData(productData)
				.requestLogData(toRequestLogData(record))
				.action(DecommissionAction.ofCode(record.getAction()))
				.inventoryId(inventoryId)
				.id(SecurPharmActionResultId.ofRepoId(record.getM_Securpharm_Action_Result_ID()))
				.build();
	}

	private static SecurPharmRequestLogData toRequestLogData(final I_M_Securpharm_Action_Result record)
	{
		return SecurPharmRequestLogData.builder()
				.requestTime(TimeUtil.asInstant(record.getRequestStartTime()))
				.responseTime(TimeUtil.asInstant(record.getRequestEndTime()))
				.requestUrl(record.getRequestUrl())
				.clientTransactionId(record.getTransactionIDClient())
				.serverTransactionId(record.getTransactionIDServer())
				.error(record.isError())
				.build();
	}

	public Optional<SecurPharmProductDataResult> getProductDataResultByInventoryId(@NonNull final InventoryId inventoryId)
	{
		final IInventoryDAO inventoryRepo = Services.get(IInventoryDAO.class);

		// TODO: is this correct?! what if we have more HUs...
		final HuId huId = inventoryRepo
				.retrieveLinesForInventoryId(inventoryId, I_M_InventoryLine.class)
				.stream()
				.findFirst()
				.map(invLine -> HuId.ofRepoId(invLine.getM_HU_ID())) // TODO: handle multiple HUs
				.orElse(null);
		if (huId == null)
		{
			return Optional.empty();
		}

		return getProductDataResultByHuId(huId);
	}

	private Optional<SecurPharmProductDataResult> getProductDataResultByHuId(@NonNull final HuId huId)
	{
		final I_M_Securpharm_Productdata_Result record = queryBL
				.createQueryBuilder(I_M_Securpharm_Productdata_Result.class)
				.addEqualsFilter(I_M_Securpharm_Productdata_Result.COLUMNNAME_M_HU_ID, huId)
				.create()
				.first();
		if (record == null)
		{
			return Optional.empty();
		}

		final SecurPharmProductDataResult result = toProductDataResult(record);
		return Optional.of(result);
	}

	private static SecurPharmProductDataResult toProductDataResult(@NonNull final I_M_Securpharm_Productdata_Result record)
	{
		final SecurPharmRequestLogData logData = toRequestLogData(record);
		return SecurPharmProductDataResult.builder()
				.productData(!logData.isError() ? toProductData(record) : null)
				.requestLogData(logData)
				//
				.huId(HuId.ofRepoId(record.getM_HU_ID()))
				//
				.id(SecurPharmProductDataResultId.ofRepoId(record.getM_Securpharm_Productdata_Result_ID()))
				//
				.build();
	}

	private static SecurPharmRequestLogData toRequestLogData(final I_M_Securpharm_Productdata_Result record)
	{
		final SecurPharmRequestLogData logData = SecurPharmRequestLogData.builder()
				.requestTime(TimeUtil.asInstant(record.getRequestStartTime()))
				.responseTime(TimeUtil.asInstant(record.getRequestEndTime()))
				.requestUrl(record.getRequestUrl())
				.clientTransactionId(record.getTransactionIDClient())
				.serverTransactionId(record.getTransactionIDServer())
				.error(record.isError())
				.build();
		return logData;
	}

	private static ProductData toProductData(final I_M_Securpharm_Productdata_Result record)
	{
		return ProductData.builder()
				.active(record.isActive())
				.expirationDate(ExpirationDate.ofLocalDate(TimeUtil.asLocalDate(record.getExpirationDate())))
				.inactiveReason(record.getInactiveReason())
				.lot(record.getLotNumber())
				.productCode(record.getProductCode())
				.productCodeType(ProductCodeType.ofCode(record.getProductCodeType()))
				.serialNumber(record.getSerialNumber())
				.build();
	}
}
