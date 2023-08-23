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

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.YearId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_ModCntr_Log;
import de.metas.contracts.modular.settings.ModularContractTypeId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.adempiere.warehouse.WarehouseId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.copyValues;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

@Repository
public class ModularContractLogDAO
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public ModularContractLogEntry getById(@NonNull final ModularContractLogEntryId id)
	{
		return fromRecord(load(ModularContractLogEntryId.toRepoId(id), I_ModCntr_Log.class));
	}

	public ModularContractLogEntryId create(@NonNull final LogEntryCreateRequest request)
	{
		final I_ModCntr_Log log = newInstance(I_ModCntr_Log.class);
		log.setC_Flatrate_Term_ID(FlatrateTermId.toRepoId(request.getContractId()));
		log.setM_Product_ID(ProductId.toRepoId(request.getProductId()));
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
		log.setDateTrx(request.getTransactionDate().toTimestamp(orgDAO::getTimeZone));

		final InvoiceCandidateId invoiceCandidateId = request.getInvoiceCandidateId();
		if (invoiceCandidateId != null)
		{
			log.setC_Invoice_Candidate_ID(invoiceCandidateId.getRepoId());
		}

		final Money amount = request.getAmount();
		if (amount != null)
		{
			log.setAmount(amount.toBigDecimal());
			log.setC_Currency_ID(amount.getCurrencyId().getRepoId());
		}

		final Quantity quantity = request.getQuantity();
		if (quantity != null)
		{
			log.setQty(quantity.toBigDecimal());
			log.setC_UOM_ID(quantity.getUomId().getRepoId());
		}
		log.setHarvesting_Year_ID(YearId.toRepoId(request.getYear()));
		log.setDescription(request.getDescription());
		log.setModCntr_Type_ID(ModularContractTypeId.toRepoId(request.getModularContractTypeId()));

		save(log);

		return ModularContractLogEntryId.ofRepoId(log.getModCntr_Log_ID());
	}

	@NonNull
	private ModularContractLogEntry fromRecord(@NonNull final I_ModCntr_Log record)
	{
		return ModularContractLogEntry.builder()
				.id(ModularContractLogEntryId.ofRepoId(record.getModCntr_Log_ID()))
				.contractId(FlatrateTermId.ofRepoIdOrNull(record.getC_Flatrate_Term_ID()))
				.productId(ProductId.ofRepoIdOrNull(record.getM_Product_ID()))
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
				.year(YearId.ofRepoId(record.getHarvesting_Year_ID()))
				.isBillable(record.isBillable())
				.build();
	}

	@NonNull
	public ModularContractLogEntryId reverse(@NonNull final LogEntryReverseRequest request)
	{
		final I_ModCntr_Log oldLog = lastRecord(ModularContractLogQuery.builder()
				.entryId(request.id())
				.flatrateTermId(request.flatrateTermId())
				.referenceSet(TableRecordReferenceSet.of(request.referencedModel()))
				.build())
				.orElseThrow(() -> new AdempiereException("No record found for " + request));

		final I_ModCntr_Log reversedLog = newInstance(I_ModCntr_Log.class);
		copyValues(oldLog, reversedLog);

		if (reversedLog.getQty() != null)
		{
			reversedLog.setQty(reversedLog.getQty().negate());
		}

		if (reversedLog.getAmount() != null)
		{
			reversedLog.setAmount(reversedLog.getAmount().negate());
		}

		if (request.description() != null)
		{
			reversedLog.setDescription(request.description());
		}

		save(reversedLog);

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
		toSqlQuery(query)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_ModCntr_Log.COLUMNNAME_IsBillable, isBillable)
				.setExecuteDirectly(true)
				.execute();
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

		if (query.getEntryId() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_ModCntr_Log_ID, query.getEntryId());
		}

		if (query.getFlatrateTermId() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_C_Flatrate_Term_ID, query.getFlatrateTermId());
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
				.orderByDescending(I_ModCntr_Log.COLUMN_Created)
				.orderByDescending(I_ModCntr_Log.COLUMNNAME_ModCntr_Log_ID)
				.firstOptional();
	}

	private static Quantity extractQty(@NonNull final I_ModCntr_Log record)
	{
		return Quantitys.create(record.getQty(), UomId.ofRepoId(record.getC_UOM_ID()));
	}
}
