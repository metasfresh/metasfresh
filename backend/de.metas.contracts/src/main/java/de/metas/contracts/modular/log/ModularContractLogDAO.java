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
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_ModCntr_Log;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public class ModularContractLogDAO
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public ModularContractLogEntry get(@NonNull final ModularContractLogEntryId id)
	{
		return fromDB(InterfaceWrapperHelper.load(ModularContractLogEntryId.toRepoId(id), I_ModCntr_Log.class));
	}

	public ModularContractLogEntryId create(@NonNull final LogEntryCreateRequest request)
	{
		final I_ModCntr_Log log = fromLogEntryCreateRequest(request);
		InterfaceWrapperHelper.save(log);
		return ModularContractLogEntryId.ofRepoId(log.getModCntr_Log_ID());
	}

	private I_ModCntr_Log fromLogEntryCreateRequest(final LogEntryCreateRequest request)
	{
		final I_ModCntr_Log log = InterfaceWrapperHelper.newInstance(I_ModCntr_Log.class);
		log.setC_Flatrate_Term_ID(FlatrateTermId.toRepoId(request.getContractId()));
		log.setM_Product_ID(ProductId.toRepoId(request.getProductId()));
		log.setAD_Table_ID(request.getReferencedRecord().getAD_Table_ID());
		log.setRecord_ID(request.getReferencedRecord().getRecord_ID());
		log.setCollectionPoint_BPartner_ID(BPartnerId.toRepoId(request.getCollectionPointBPartnerId()));
		log.setBill_BPartner_ID(BPartnerId.toRepoId(request.getInvoicingBPartnerId()));
		log.setProducer_BPartner_ID(BPartnerId.toRepoId(request.getProducerBPartnerId()));
		log.setM_Warehouse_ID(WarehouseId.toRepoId(request.getCollectionPoint()));
		log.setModCntr_Log_DocumentType(request.getDocumentType().getCode());
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

		return log;
	}

	private ModularContractLogEntry fromDB(final I_ModCntr_Log log)
	{
		return ModularContractLogEntry.builder()
				.id(ModularContractLogEntryId.ofRepoId(log.getModCntr_Log_ID()))
				.contractId(FlatrateTermId.ofRepoId(log.getC_Flatrate_Term_ID()))
				.productId(ProductId.ofRepoId(log.getM_Product_ID()))
				.referencedRecord(TableRecordReference.of(log.getAD_Table_ID(), log.getRecord_ID()))
				.collectionPointBPartnerId(BPartnerId.ofRepoId(log.getCollectionPoint_BPartner_ID())) // TODO check if this is mandatory, I think not
				.producerBPartnerId(BPartnerId.ofRepoIdOrNull(log.getProducer_BPartner_ID())) // TODO make ModCntr_Log.Producer_BPartner_ID
				.invoicingBPartnerId(BPartnerId.ofRepoIdOrNull(log.getBill_BPartner_ID()))// TODO make ModCntr_Log.Bill_BPartner_ID
				.collectionPoint(WarehouseId.ofRepoId(log.getM_Warehouse_ID()))
				.documentType(LogEntryDocumentType.ofCode(log.getModCntr_Log_DocumentType())) // TODO make ModCntr_Log.ModCntr_Log_DocumentType mandatory in DB
				.soTrx(SOTrx.ofBoolean(log.isSOTrx()))
				.processed(log.isProcessed())
				.quantity(Quantity.ofOrNull(log.getQty(), uomDAO.getById(log.getC_UOM_ID())))
				.amount(Money.ofOrNull(log.getAmount(), CurrencyId.ofRepoIdOrNull(log.getC_Currency_ID()))) // TODO make ModCntr_Log.Amount & ModCntr_Log.C_Currency_ID optional in DB
				.transactionDate(LocalDateAndOrgId.ofTimestamp(log.getDateTrx(), OrgId.ofRepoId(log.getAD_Org_ID()), orgDAO::getTimeZone)) // TODO make ModCntr_Log.TrxDate mandatory in DB
				.build();
	}

	@NonNull
	public ModularContractLogEntryId reverse(@NonNull final LogEntryReverseRequest logEntryReverseRequest)
	{
		final I_ModCntr_Log oldLog = InterfaceWrapperHelper.load(logEntryReverseRequest.id().getRepoId(), I_ModCntr_Log.class);

		final I_ModCntr_Log reversedLog = InterfaceWrapperHelper.newInstance(I_ModCntr_Log.class);
		InterfaceWrapperHelper.copyValues(oldLog, reversedLog);
		if (reversedLog.getQty() != null)
		{
			reversedLog.setQty(reversedLog.getQty().negate());
		}
		InterfaceWrapperHelper.save(reversedLog);

		return ModularContractLogEntryId.ofRepoId(reversedLog.getModCntr_Log_ID());
	}

	/**
	 * @return the log entry that was just deleted; might be helpful for testing.
	 */
	@Nullable
	public ModularContractLogEntry delete(final LogEntryDeleteRequest logEntryDeleteRequest)
	{
		final TableRecordReference referencedModel = logEntryDeleteRequest.getReferencedModel();
		final I_ModCntr_Log recordToDelete = queryBL.createQueryBuilder(I_ModCntr_Log.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_AD_Table_ID, referencedModel.getAdTableId())
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_Record_ID, referencedModel.getRecord_ID())
				.orderBy(I_ModCntr_Log.COLUMNNAME_Created)
				.create()
				.first();
		if (recordToDelete != null)
		{
			final ModularContractLogEntry logEntry = fromDB(recordToDelete);
			InterfaceWrapperHelper.delete(recordToDelete);
			return logEntry;
		}
		return null;
	}
}
