/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular.log;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.calendar.standard.YearId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_ModCntr_Log;
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.model.I_ModCntr_Type;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.settings.ModularContractModuleId;
import de.metas.contracts.modular.settings.ModularContractTypeId;
import de.metas.i18n.AdMessageKey;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.lang.SOTrx;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.LockOwner;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static de.metas.contracts.modular.log.LogEntryContractType.MODULAR_CONTRACT;
import static org.adempiere.model.InterfaceWrapperHelper.copyValues;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ModularContractLogDAO
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ILockManager lockManager = Services.get(ILockManager.class);

	private final static AdMessageKey ERR_MSG_ON_REVERSE_PROCESSED = AdMessageKey.of("de.metas.contracts.modular.reverseNotAllowedIfProcessed");

	public ModularContractLogEntry getById(@NonNull final ModularContractLogEntryId id)
	{
		return fromRecord(load(ModularContractLogEntryId.toRepoId(id), I_ModCntr_Log.class));
	}

	public ModularContractLogEntryId create(@NonNull final LogEntryCreateRequest request)
	{
		final I_ModCntr_Log log = newInstance(I_ModCntr_Log.class);
		log.setC_Flatrate_Term_ID(FlatrateTermId.toRepoId(request.getContractId()));
		log.setM_Product_ID(ProductId.toRepoId(request.getProductId()));
		log.setProductName(request.getProductName());
		log.setInitial_Product_ID(ProductId.toRepoId(request.getInitialProductId()));
		log.setAD_Table_ID(request.getReferencedRecord().getAD_Table_ID());
		log.setRecord_ID(request.getReferencedRecord().getRecord_ID());
		log.setCollectionPoint_BPartner_ID(BPartnerId.toRepoId(request.getCollectionPointBPartnerId()));
		log.setProducer_BPartner_ID(BPartnerId.toRepoId(request.getProducerBPartnerId()));
		log.setBill_BPartner_ID(BPartnerId.toRepoId(request.getInvoicingBPartnerId()));
		log.setM_Warehouse_ID(WarehouseId.toRepoId(request.getWarehouseId()));
		log.setModCntr_Log_DocumentType(request.getDocumentType().getCode());
		log.setContractType(request.getContractType().getCode());
		log.setIsSOTrx(request.getSoTrx().isSales());
		log.setProcessed(request.isProcessed());
		log.setIsBillable(request.isBillable());
		log.setDateTrx(request.getTransactionDate().toTimestamp(orgDAO::getTimeZone));

		final InvoiceCandidateId invoiceCandidateId = request.getInvoiceCandidateId();
		if (invoiceCandidateId != null)
		{
			log.setC_Invoice_Candidate_ID(invoiceCandidateId.getRepoId());
		}

		request.getAmount()
				.ifPresentOrElse(amount -> {
							log.setAmount(amount.toBigDecimal());
							log.setC_Currency_ID(amount.getCurrencyId().getRepoId());
						},
						() -> log.setAmount(null));

		request.getQuantity()
				.ifPresentOrElse(quantity -> {
							log.setQty(quantity.toBigDecimal());
							log.setC_UOM_ID(quantity.getUomId().getRepoId());
						},
						() -> log.setQty(null));

		log.setHarvesting_Year_ID(YearId.toRepoId(request.getYear()));
		log.setDescription(request.getDescription());
		log.setModCntr_Type_ID(ModularContractTypeId.toRepoId(request.getModularContractTypeId()));

		if (request.getSubEntryId() != null)
		{
			InterfaceWrapperHelper.setValue(log, request.getSubEntryId().getColumnName(), request.getSubEntryId().getId().getRepoId());
		}

		request.getPriceActual()
				.ifPresentOrElse(priceActual -> {
							log.setPriceActual(priceActual.toBigDecimal());
							log.setPrice_UOM_ID(priceActual.getUomId().getRepoId());
							log.setC_Currency_ID(priceActual.getCurrencyId().getRepoId());
						},
						() -> log.setPriceActual(null));

		log.setModCntr_Module_ID(request.getConfigModuleId().getRepoId());

		log.setModCntr_InvoicingGroup_ID(InvoicingGroupId.toRepoId(request.getInvoicingGroupId()));

		if (request.getStorageDays() != null)
		{
			log.setStorageDays(request.getStorageDays());
		}

		if (request.getUserElementNumber1() != null)
		{
			log.setUserElementNumber1(request.getUserElementNumber1());
		}

		if (request.getUserElementNumber2() != null)
		{
			log.setUserElementNumber2(request.getUserElementNumber2());
		}

		saveRecord(log);

		return ModularContractLogEntryId.ofRepoId(log.getModCntr_Log_ID());
	}

	@NonNull
	private ModularContractLogEntry fromRecord(@NonNull final I_ModCntr_Log record)
	{
		return ModularContractLogEntry.builder()
				.id(ModularContractLogEntryId.ofRepoId(record.getModCntr_Log_ID()))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(record.getAD_Client_ID(), record.getAD_Org_ID()))
				.contractId(FlatrateTermId.ofRepoIdOrNull(record.getC_Flatrate_Term_ID()))
				.productId(ProductId.ofRepoIdOrNull(record.getM_Product_ID()))
				.initialProductId(ProductId.ofRepoIdOrNull(record.getInitial_Product_ID()))
				.productName(record.getProductName())
				.modularContractTypeId(ModularContractTypeId.ofRepoIdOrNull(record.getModCntr_Type_ID()))
				.referencedRecord(TableRecordReference.of(record.getAD_Table_ID(), record.getRecord_ID()))
				.contractType(LogEntryContractType.ofCode(record.getContractType()))
				.collectionPointBPartnerId(BPartnerId.ofRepoIdOrNull(record.getCollectionPoint_BPartner_ID()))
				.producerBPartnerId(BPartnerId.ofRepoIdOrNull(record.getProducer_BPartner_ID()))
				.invoicingBPartnerId(BPartnerId.ofRepoIdOrNull(record.getBill_BPartner_ID()))
				.warehouseId(WarehouseId.ofRepoIdOrNull(record.getM_Warehouse_ID()))
				.documentType(LogEntryDocumentType.ofCode(record.getModCntr_Log_DocumentType()))
				.soTrx(SOTrx.ofBoolean(record.isSOTrx()))
				.processed(record.isProcessed())
				.quantity(Quantity.ofNullable(record.getQty(), uomDAO.getById(record.getC_UOM_ID())))
				.amount(Money.ofOrNull(record.getAmount(), CurrencyId.ofRepoIdOrNull(record.getC_Currency_ID())))
				.transactionDate(LocalDateAndOrgId.ofTimestamp(record.getDateTrx(), OrgId.ofRepoId(record.getAD_Org_ID()), orgDAO::getTimeZone))
				.storageDays(record.getStorageDays() >= 0 ? record.getStorageDays() : null)
				.userElementNumber1(InterfaceWrapperHelper.getValueAsBigDecimalOrNull(record, I_ModCntr_Log.COLUMNNAME_UserElementNumber1))
				.userElementNumber2(InterfaceWrapperHelper.getValueAsBigDecimalOrNull(record, I_ModCntr_Log.COLUMNNAME_UserElementNumber2))
				.year(YearId.ofRepoId(record.getHarvesting_Year_ID()))
				.isBillable(record.isBillable())
				.priceActual(extractPriceActual(record))
				.modularContractModuleId(ModularContractModuleId.ofRepoId(record.getModCntr_Module_ID()))
				.invoicingGroupId(InvoicingGroupId.ofRepoIdOrNull(record.getModCntr_InvoicingGroup_ID()))
				.build();
	}

	@NonNull
	public ModularContractLogEntryId reverse(@NonNull final LogEntryReverseRequest request)
	{
		final ModularContractLogQuery.ModularContractLogQueryBuilder queryBuilder = ModularContractLogQuery.builder()
				.flatrateTermId(request.flatrateTermId())
				.referenceSet(TableRecordReferenceSet.of(request.referencedModel()))
				.contractType(request.logEntryContractType())
				.contractModuleId(request.contractModuleId());

		if (request.id() != null)
		{
			queryBuilder.entryId(request.id());
		}

		final I_ModCntr_Log oldLog = lastRecord(queryBuilder.build())
				.orElseThrow(() -> new AdempiereException("No record found for " + request));

		if (oldLog.isProcessed())
		{
			throw new AdempiereException(ERR_MSG_ON_REVERSE_PROCESSED, oldLog);
		}

		oldLog.setIsBillable(false);
		saveRecord(oldLog);

		final I_ModCntr_Log reversedLog = newInstance(I_ModCntr_Log.class);
		copyValues(oldLog, reversedLog);

		if (reversedLog.getQty() != null)
		{
			reversedLog.setQty(reversedLog.getQty().negate());
		}

		if (reversedLog.getAmount() != null && reversedLog.getAmount().signum() != 0)
		{
			reversedLog.setAmount(reversedLog.getAmount().negate());
		}

		if (request.description() != null)
		{
			reversedLog.setDescription(request.description());
		}

		reversedLog.setIsBillable(false);
		saveRecord(reversedLog);

		return ModularContractLogEntryId.ofRepoId(reversedLog.getModCntr_Log_ID());
	}

	public boolean hasAnyModularLogs(@NonNull final TableRecordReference recordRef)
	{
		return toSqlQuery(ModularContractLogQuery.builder().referenceSet(TableRecordReferenceSet.of(recordRef)).build())
				.create()
				.anyMatch();
	}

	public void changeBillableStatus(
			@NonNull final ModularContractLogQuery query,
			final boolean isBillable)
	{
		final Optional<I_ModCntr_Log> logOptional = lastRecord(query);
		if (logOptional.isPresent())
		{
			final I_ModCntr_Log log = logOptional.get();
			log.setIsBillable(isBillable);
			saveRecord(log);

			CacheMgt.get().reset(CacheInvalidateMultiRequest.rootRecords(
					I_ModCntr_Log.Table_Name,
					ImmutableSet.of(ModularContractLogEntryId.ofRepoId(log.getModCntr_Log_ID()))));
		}
	}

	public boolean anyMatch(@NonNull final ModularContractLogQuery query)
	{
		return toSqlQuery(query)
				.create()
				.anyMatch();
	}

	public void delete(@NonNull final LogEntryDeleteRequest request)
	{
		final IQueryBuilder<I_ModCntr_Log> queryBuilder = queryBL.createQueryBuilder(I_ModCntr_Log.class)
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_AD_Table_ID, request.referencedModel().getAD_Table_ID())
				.addInArrayFilter(I_ModCntr_Log.COLUMNNAME_Record_ID, request.referencedModel().getRecord_ID())
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_C_Flatrate_Term_ID, request.flatrateTermId())
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_ContractType, request.logEntryContractType())
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_ModCntr_Module_ID, request.modularContractModuleId());

		if (request.subEntryId() != null)
		{
			queryBuilder.addEqualsFilter(request.subEntryId().getColumnName(), request.subEntryId().getId());
		}

		queryBuilder.create().delete(true);
	}

	private IQueryBuilder<I_ModCntr_Log> toSqlQuery(@NonNull final ModularContractLogQuery query)
	{
		final IQueryBuilder<I_ModCntr_Log> sqlQueryBuilder = queryBL.createQueryBuilder(I_ModCntr_Log.class)
				.addOnlyActiveRecordsFilter();

		final TableRecordReferenceSet referenceSet = query.getReferenceSet();
		if (referenceSet != null)
		{
			sqlQueryBuilder
					.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_AD_Table_ID, referenceSet.getSingleTableId())
					.addInArrayFilter(I_ModCntr_Log.COLUMNNAME_Record_ID, referenceSet.toIntSet());
		}

		if (query.getContractType() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_ContractType, query.getContractType().getCode());
		}

		if (!Check.isEmpty(query.getEntryIds()))
		{
			sqlQueryBuilder.addInArrayFilter(I_ModCntr_Log.COLUMNNAME_ModCntr_Log_ID, query.getEntryIds());
		}

		if (query.getFlatrateTermId() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_C_Flatrate_Term_ID, query.getFlatrateTermId());
		}

		if (query.getModularContractTypeId() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_ModCntr_Type_ID, query.getModularContractTypeId());
		}

		if (query.getContractModuleId() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_ModCntr_Module_ID, query.getContractModuleId());
		}

		if (query.getProcessed() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_Processed, query.getProcessed());
		}

		if (!query.getComputingMethodTypes().isEmpty())
		{
			final IQueryBuilder<I_ModCntr_Type> modCntrTypeBuilder = queryBL.createQueryBuilder(I_ModCntr_Type.class)
					.addInArrayFilter(I_ModCntr_Type.COLUMNNAME_ModularContractHandlerType, query.getComputingMethodTypes());
			if (query.isComputingMethodTypeActive())
			{
				modCntrTypeBuilder.addOnlyActiveRecordsFilter();
			}
			final IQuery<I_ModCntr_Module> moduleFilter = queryBL.createQueryBuilder(I_ModCntr_Module.class)
					.addOnlyActiveRecordsFilter()
					.addInSubQueryFilter(I_ModCntr_Module.COLUMNNAME_ModCntr_Type_ID, I_ModCntr_Type.COLUMNNAME_ModCntr_Type_ID, modCntrTypeBuilder.create())
					.create();

			sqlQueryBuilder.addInSubQueryFilter(I_ModCntr_Log.COLUMNNAME_ModCntr_Module_ID, I_ModCntr_Module.COLUMNNAME_ModCntr_Module_ID, moduleFilter);
		}
		if (query.getInvoiceCandidateId() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_C_Invoice_Candidate_ID, query.getInvoiceCandidateId());
		}

		if (query.getBillable() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_IsBillable, query.getBillable());
		}

		if (query.getLockOwner() != null)
		{
			sqlQueryBuilder.addFilter(lockManager.getLockedByFilter(I_ModCntr_Log.class, query.getLockOwner()));
		}

		if (query.getInvoicingGroupId() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_ModCntr_InvoicingGroup_ID, query.getInvoicingGroupId());
		}

		if (query.getLogEntryDocumentType() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_ModCntr_Log_DocumentType, query.getLogEntryDocumentType().getCode());
		}

		if (query.isOnlyIfAmountIsSet())
		{
			sqlQueryBuilder.addNotNull(I_ModCntr_Log.COLUMNNAME_C_Currency_ID);
			sqlQueryBuilder.addNotNull(I_ModCntr_Log.COLUMNNAME_Amount);
		}

		if (!query.getExcludedReferencedTableIds().isEmpty())
		{
			sqlQueryBuilder.addNotInArrayFilter(I_ModCntr_Log.COLUMNNAME_AD_Table_ID, query.getExcludedReferencedTableIds());
		}

		if (!query.getOrderBys().isEmpty())
		{
			for (final ModularContractLogQuery.OrderBy orderBy : query.getOrderBys())
			{
				if (orderBy.isAscending())
				{
					sqlQueryBuilder.orderBy(orderBy.getColumnName());
				}
				else
				{
					sqlQueryBuilder.orderByDescending(orderBy.getColumnName());
				}
			}
		}

		return sqlQueryBuilder;
	}

	@NonNull
	public Quantity retrieveQuantityFromExistingLog(final @NonNull ModularContractLogQuery query)
	{
		return lastRecord(query)
				.map(ModularContractLogDAO::extractQty)
				.orElseThrow(() -> new AdempiereException("No records found for " + query));
	}

	private Optional<I_ModCntr_Log> lastRecord(final @NonNull ModularContractLogQuery query)
	{
		return toSqlQuery(query)
				.orderByDescending(I_ModCntr_Log.COLUMNNAME_Created)
				.orderByDescending(I_ModCntr_Log.COLUMNNAME_ModCntr_Log_ID)
				.firstOptional();
	}

	private static Quantity extractQty(@NonNull final I_ModCntr_Log record)
	{
		return Quantitys.of(record.getQty(), UomId.ofRepoId(record.getC_UOM_ID()));
	}

	public Optional<ModularContractLogEntry> getLastModularContractLog(
			@NonNull final FlatrateTermId modularFlatrateTermId,
			@NonNull final OrderLineId orderLineId)
	{
		final TableRecordReferenceSet modularRecordReference = TableRecordReferenceSet.of(TableRecordReference.of(I_C_OrderLine.Table_Name, orderLineId));

		final ModularContractLogQuery query = ModularContractLogQuery.builder()
				.contractType(MODULAR_CONTRACT)
				.flatrateTermId(modularFlatrateTermId)
				.referenceSet(modularRecordReference)
				.build();
		final Optional<I_ModCntr_Log> modCntrLog = lastRecord(query);
		return modCntrLog.map(this::fromRecord);
	}

	@NonNull
	public ModularContractLogEntriesList getModularContractLogEntries(@NonNull final ModularContractLogQuery query)
	{
		return toSqlQuery(query)
				.stream()
				.map(this::fromRecord)
				.collect(ModularContractLogEntriesList.collect());
	}

	@Nullable
	public PInstanceId getModularContractLogEntrySelection(@NonNull final ModularContractLogQuery query)
	{
		return toSqlQuery(query).create().createSelection();
	}

	@NonNull
	public IQueryFilter<I_ModCntr_Log> getModularContractLogEntryFilter(@NonNull final ModularContractLogQuery query)
	{
		return queryBL.createCompositeQueryFilter(I_ModCntr_Log.class)
				.addInSubQueryFilter(I_ModCntr_Log.COLUMNNAME_ModCntr_Log_ID,
						I_ModCntr_Log.COLUMNNAME_ModCntr_Log_ID,
						toSqlQuery(query).create());
	}

	@NonNull
	public Stream<ModularContractLogEntry> streamModularContractLogEntries(@NonNull final ModularContractLogQuery query)
	{
		return toSqlQuery(query)
				.iterateAndStream()
				.map(this::fromRecord);
	}

	public void setICProcessed(@NonNull final ModularContractLogQuery query, @NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		final IQuery<I_ModCntr_Log> sqlQuery = toSqlQuery(query).create();
		sqlQuery.updateDirectly()
				.addSetColumnValue(I_ModCntr_Log.COLUMNNAME_C_Invoice_Candidate_ID, invoiceCandidateId)
				.addSetColumnValue(I_ModCntr_Log.COLUMNNAME_Processed, true)
				.setExecuteDirectly(true)
				.execute();
		if (sqlQuery.anyMatch())
		{
			CacheMgt.get().reset(CacheInvalidateMultiRequest.rootRecords(
					I_ModCntr_Log.Table_Name,
					sqlQuery.listIds(ModularContractLogEntryId::ofRepoId)));
		}
	}

	@NonNull
	public Iterator<I_ModCntr_Log> getLogsIteratorOrderedByRecordRef(@NonNull final IQueryFilter<I_ModCntr_Log> filter)
	{
		return queryBL.createQueryBuilder(I_ModCntr_Log.class)
				.filter(filter)
				.orderBy(I_ModCntr_Log.COLUMNNAME_AD_Table_ID)
				.orderBy(I_ModCntr_Log.COLUMNNAME_Record_ID)
				.create()
				//dev-note: we need a guaranteed iterator as at least in one of the usages we delete log entries while the iteration is ongoing
				.iterateWithGuaranteedIterator(I_ModCntr_Log.class);
	}

	@NonNull
	public ImmutableList<I_ModCntr_Log> list(@NonNull final ModularContractLogQuery query)
	{
		return toSqlQuery(query)
				.create()
				.listImmutable(I_ModCntr_Log.class);
	}

	@NonNull
	public ImmutableSet<FlatrateTermId> getModularContractIds(@NonNull final ModularContractLogQuery query)
	{
		return toSqlQuery(query)
				.create()
				.listDistinct(I_ModCntr_Log.COLUMNNAME_C_Flatrate_Term_ID, Integer.class)
				.stream()
				.filter(contractId -> contractId > 0)
				.map(FlatrateTermId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Nullable
	public PInstanceId getSelection(@NonNull final LockOwner lockOwner)
	{
		return queryBL.createQueryBuilder(I_ModCntr_Log.class)
				.addFilter(lockManager.getLockedByFilter(I_ModCntr_Log.class, lockOwner))
				.create()
				.createSelection();
	}

	@Nullable
	private static ProductPrice extractPriceActual(@NonNull final I_ModCntr_Log record)
	{
		final BigDecimal priceActual = record.getPriceActual();
		final UomId uomId = UomId.ofRepoIdOrNull(record.getPrice_UOM_ID());
		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(record.getC_Currency_ID());
		final ProductId productId = ProductId.ofRepoIdOrNull(record.getM_Product_ID());

		if (priceActual == null
				|| uomId == null
				|| currencyId == null
				|| productId == null)
		{
			return null;
		}

		return ProductPrice.builder()
				.uomId(uomId)
				.productId(productId)
				.money(Money.of(priceActual, currencyId))
				.build();
	}

	public void save(@NonNull final ModularContractLogEntriesList logList)
	{
		if (logList.isEmpty())
		{
			return;
		}

		final ImmutableMap<ModularContractLogEntryId, I_ModCntr_Log> recordsById = Maps.uniqueIndex(
				InterfaceWrapperHelper.loadByRepoIdAwares(logList.getIds(), I_ModCntr_Log.class),
				record -> ModularContractLogEntryId.ofRepoId(record.getModCntr_Log_ID())
		);

		for (final ModularContractLogEntry log : logList)
		{
			final I_ModCntr_Log record = recordsById.get(log.getId());
			updateRecord(record, log, orgDAO::getTimeZone);
			saveRecord(record);
		}
	}

	private static void updateRecord(
			@NonNull final I_ModCntr_Log record,
			@NonNull final ModularContractLogEntry from,
			@NonNull final Function<OrgId, ZoneId> getZoneIdByOrgId)
	{
		Optional.ofNullable(from.getContractId())
				.map(FlatrateTermId::getRepoId)
				.ifPresent(record::setC_Flatrate_Term_ID);
		Optional.ofNullable(from.getProductId())
				.map(ProductId::getRepoId)
				.ifPresent(record::setM_Product_ID);
		final TableRecordReference referencedRecord = from.getReferencedRecord();
		record.setAD_Table_ID(referencedRecord.getAD_Table_ID());
		record.setRecord_ID(referencedRecord.getRecord_ID());
		record.setContractType(from.getContractType().getCode());
		Optional.ofNullable(from.getCollectionPointBPartnerId())
				.map(BPartnerId::getRepoId)
				.ifPresent(record::setCollectionPoint_BPartner_ID);
		Optional.ofNullable(from.getProducerBPartnerId())
				.map(BPartnerId::getRepoId)
				.ifPresent(record::setProducer_BPartner_ID);
		Optional.ofNullable(from.getInvoicingBPartnerId())
				.map(BPartnerId::getRepoId)
				.ifPresent(record::setBill_BPartner_ID);
		Optional.ofNullable(from.getWarehouseId())
				.map(WarehouseId::getRepoId)
				.ifPresent(record::setM_Warehouse_ID);
		record.setModCntr_Log_DocumentType(from.getDocumentType().getCode());

		record.setProcessed(from.isProcessed());
		Optional.ofNullable(from.getQuantity())
				.ifPresent(qty -> {
					record.setQty(qty.toBigDecimal());
					record.setC_UOM_ID(qty.getUomId().getRepoId());
				});
		Optional.ofNullable(from.getAmount())
				.ifPresent(amt -> {
					record.setAmount(amt.toBigDecimal());
					record.setC_Currency_ID(amt.getCurrencyId().getRepoId());
				});
		record.setDateTrx(from.getTransactionDate().toTimestamp(getZoneIdByOrgId));
		Optional.ofNullable(from.getStorageDays())
				.ifPresentOrElse(record::setStorageDays, () -> record.setStorageDays(0));
		record.setHarvesting_Year_ID(from.getYear().getRepoId());
		record.setIsBillable(from.isBillable());
		Optional.ofNullable(from.getPriceActual())
				.ifPresent(price -> {
					record.setPriceActual(price.toBigDecimal());
					record.setPrice_UOM_ID(price.getUomId().getRepoId());
					record.setC_Currency_ID(price.getCurrencyId().getRepoId());
				});
		record.setModCntr_Module_ID(from.getModularContractModuleId().getRepoId());
	}

}
