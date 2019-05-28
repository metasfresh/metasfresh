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

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.cache.CCache;
import de.metas.handlingunits.HuId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.model.DecommissionAction;
import de.metas.vertical.pharma.securpharm.model.DecommissionResponse;
import de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Action_Result;
import de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Log;
import de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Productdata_Result;
import de.metas.vertical.pharma.securpharm.model.ProductCodeType;
import de.metas.vertical.pharma.securpharm.model.ProductDetails;
import de.metas.vertical.pharma.securpharm.model.SecurPharmActionResultId;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProduct;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProductId;
import de.metas.vertical.pharma.securpharm.model.SecurPharmLog;
import de.metas.vertical.pharma.securpharm.model.SecurPharmLogId;
import de.metas.vertical.pharma.securpharm.model.UndoDecommissionResponse;
import de.metas.vertical.pharma.securpharm.model.schema.ExpirationDate;
import de.metas.vertical.pharma.securpharm.model.schema.ProductPackageState;
import lombok.NonNull;

@Repository
public class SecurPharmResultRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private CCache<SecurPharmProductId, SecurPharmProduct> productDataCache = CCache.<SecurPharmProductId, SecurPharmProduct> builder()
			.tableName(I_M_Securpharm_Productdata_Result.Table_Name)
			.build();

	public void save(
			@NonNull final SecurPharmProduct productDataResult,
			final Collection<SecurPharmLog> logs)
	{
		I_M_Securpharm_Productdata_Result record = null;
		if (productDataResult.getId() != null)
		{
			record = load(productDataResult.getId(), I_M_Securpharm_Productdata_Result.class);
		}
		if (record == null)
		{
			record = newInstance(I_M_Securpharm_Productdata_Result.class);
		}

		record.setIsError(productDataResult.isError());
		record.setM_HU_ID(productDataResult.getHuId().getRepoId());

		//
		// Product data
		final ProductDetails productDetails = productDataResult.getProductDetails();
		record.setExpirationDate(productDetails != null ? productDetails.getExpirationDate().toTimestamp() : null);
		record.setLotNumber(productDetails != null ? productDetails.getLot() : null);
		record.setProductCode(productDetails != null ? productDetails.getProductCode() : null);
		record.setProductCodeType(productDetails != null ? productDetails.getProductCodeType().name() : null);
		record.setSerialNumber(productDetails != null ? productDetails.getSerialNumber() : null);

		record.setActiveStatus(productDetails != null ? productDetails.getActiveStatus().toYesNoString() : null);
		record.setInactiveReason(productDetails != null ? productDetails.getInactiveReason() : null);

		record.setIsDecommissioned(productDetails.isDecommissioned());
		record.setDecommissionedServerTransactionId(productDetails.getDecommissionedServerTransactionId());

		saveRecord(record);
		final SecurPharmProductId productDataResultId = SecurPharmProductId.ofRepoId(record.getM_Securpharm_Productdata_Result_ID());
		productDataResult.setId(productDataResultId);

		saveLogs(logs,
				productDataResultId,
				(SecurPharmActionResultId)null);
	}

	private void saveLogs(
			final Collection<SecurPharmLog> logs,
			@Nullable final SecurPharmProductId productDataId,
			@Nullable final SecurPharmActionResultId actionResultId)
	{
		if (logs.isEmpty())
		{
			return;
		}

		final Set<SecurPharmLogId> existingLogIds = logs.stream()
				.map(SecurPharmLog::getId)
				.filter(Predicates.notNull())
				.collect(ImmutableSet.toImmutableSet());
		final ImmutableMap<SecurPharmLogId, I_M_Securpharm_Log> existingLogRecords = retrieveLogRecordsByIds(existingLogIds);

		for (final SecurPharmLog log : logs)
		{
			final I_M_Securpharm_Log existingLogRecord = existingLogRecords.get(log.getId());
			saveLog(log, existingLogRecord, productDataId, actionResultId);
		}
	}

	private ImmutableMap<SecurPharmLogId, I_M_Securpharm_Log> retrieveLogRecordsByIds(final Collection<SecurPharmLogId> ids)
	{
		if (ids.isEmpty())
		{
			return ImmutableMap.of();
		}

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Securpharm_Log.class)
				.addInArrayFilter(I_M_Securpharm_Log.COLUMN_M_Securpharm_Log_ID, ids)
				.create()
				.stream()
				.collect(GuavaCollectors.toImmutableMapByKey(record -> SecurPharmLogId.ofRepoId(record.getM_Securpharm_Log_ID())));

	}

	private void saveLog(
			@NonNull final SecurPharmLog log,
			@Nullable final I_M_Securpharm_Log existingLogRecord,
			@Nullable final SecurPharmProductId productDataId,
			@Nullable final SecurPharmActionResultId actionResultId)
	{
		final I_M_Securpharm_Log record;
		if (existingLogRecord != null)
		{
			record = existingLogRecord;
		}
		else
		{
			record = newInstance(I_M_Securpharm_Log.class);
		}

		record.setIsError(log.isError());

		//
		// Request
		record.setRequestUrl(log.getRequestUrl());
		record.setRequestMethod(log.getRequestMethod() != null ? log.getRequestMethod().name() : null);
		record.setRequestStartTime(TimeUtil.asTimestamp(log.getRequestTime()));

		//
		// Response
		record.setRequestEndTime(TimeUtil.asTimestamp(log.getResponseTime()));
		record.setResponseCode(log.getResponseCode() != null ? log.getResponseCode().value() : 0);

		//
		record.setTransactionIDClient(log.getClientTransactionId());
		record.setTransactionIDServer(log.getServerTransactionId());

		//
		// Links
		if (productDataId != null)
		{
			record.setM_Securpharm_Productdata_Result_ID(productDataId.getRepoId());
		}
		if (actionResultId != null)
		{
			record.setM_Securpharm_Action_Result_ID(actionResultId.getRepoId());
		}

		saveRecord(record);
		log.setId(SecurPharmLogId.ofRepoId(record.getM_Securpharm_Log_ID()));
	}

	public void save(
			@NonNull final DecommissionResponse response,
			final Collection<SecurPharmLog> logs)
	{
		final SecurPharmActionResultId responseId = saveActionResult(response);

		saveLogs(logs,
				response.getProductDataResultId(),
				responseId);
	}

	private SecurPharmActionResultId saveActionResult(final DecommissionResponse response)
	{
		final I_M_Securpharm_Action_Result record = newInstance(I_M_Securpharm_Action_Result.class);

		record.setIsError(response.isError());
		record.setAction(DecommissionAction.DESTROY.getCode());
		record.setM_Inventory_ID(response.getInventoryId().getRepoId());
		record.setM_Securpharm_Productdata_Result_ID(response.getProductDataResultId().getRepoId());
		record.setTransactionIDServer(response.getServerTransactionId());

		saveRecord(record);

		return SecurPharmActionResultId.ofRepoId(record.getM_Securpharm_Action_Result_ID());
	}

	public void save(
			@NonNull final UndoDecommissionResponse response,
			final Collection<SecurPharmLog> logs)
	{
		final SecurPharmActionResultId responseId = saveActionResult(response);

		saveLogs(logs,
				response.getProductDataResultId(),
				responseId);
	}

	private SecurPharmActionResultId saveActionResult(@NonNull final UndoDecommissionResponse response)
	{
		I_M_Securpharm_Action_Result record = newInstance(I_M_Securpharm_Action_Result.class);

		record.setIsError(response.isError());
		record.setAction(DecommissionAction.UNDO_DISPENSE.getCode());
		record.setM_Inventory_ID(response.getInventoryId().getRepoId());
		record.setM_Securpharm_Productdata_Result_ID(response.getProductDataResultId().getRepoId());
		record.setTransactionIDServer(response.getServerTransactionId());

		saveRecord(record);

		return SecurPharmActionResultId.ofRepoId(record.getM_Securpharm_Action_Result_ID());
	}

	public SecurPharmProduct getProductById(@NonNull final SecurPharmProductId id)
	{
		Collection<SecurPharmProduct> products = getProductsByIds(ImmutableList.of(id));
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

	public Collection<SecurPharmProduct> getProductDataResultByHuIds(@NonNull final Collection<HuId> huIds)
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
				.expirationDate(ExpirationDate.ofLocalDate(TimeUtil.asLocalDate(record.getExpirationDate())))
				//
				.activeStatus(ProductPackageState.ofYesNoString(record.getActiveStatus()))
				.inactiveReason(record.getInactiveReason())
				//
				.decommissioned(record.isDecommissioned())
				.decommissionedServerTransactionId(record.getDecommissionedServerTransactionId())
				//
				.build();
	}
}
