/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.cucumber.stepdefs.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.C_BP_BankAccount_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.payment.sepa.C_BP_BankAccount_DirectDebitMandate_StepDefData;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.allocation.api.PaymentAllocationId;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.allocation.C_AllocationHdr_StepDefData;
import de.metas.cucumber.stepdefs.costing.M_CostRevaluation_StepDefData;
import de.metas.cucumber.stepdefs.dunning.C_DunningDoc_StepDefData;
import de.metas.cucumber.stepdefs.gljournal_sap.SAP_GLJournal_StepDefData;
import de.metas.cucumber.stepdefs.inventory.M_Inventory_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.match_inv.M_MatchInv_StepDefData;
import de.metas.cucumber.stepdefs.order.C_Order_MFGWarehouse_Report_StepDefData;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.payment.C_Payment_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Cost_Collector_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_ShipperTransportation_StepDefData;
import de.metas.dunning.DunningDocId;
import de.metas.inout.InOutId;
import de.metas.inventory.InventoryId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.matchinv.MatchInvId;
import de.metas.order.OrderId;
import de.metas.payment.PaymentId;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_M_CostRevaluation;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_MatchInv;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class IdentifiersResolver
{
	@NonNull private final C_Invoice_StepDefData invoiceTable;
	@NonNull private final C_Payment_StepDefData paymentTable;
	@NonNull private final C_AllocationHdr_StepDefData allocationTable;
	@NonNull private final M_MatchInv_StepDefData matchInvTable;
	@NonNull private final M_InOut_StepDefData inOutTable;
	@NonNull private final M_Inventory_StepDefData inventoryTable;
	@NonNull private final C_Order_StepDefData orderTable;
	@NonNull private final C_DunningDoc_StepDefData dunningDocTable;
	@NonNull private final PP_Cost_Collector_StepDefData ppCostCollectorTable;
	@NonNull private final M_CostRevaluation_StepDefData costRevaluationTable;
	@NonNull private final M_ShipperTransportation_StepDefData shipperTransportationTable;
	@NonNull private final PP_Order_StepDefData ppOrderTable;
	@NonNull private final C_Order_MFGWarehouse_Report_StepDefData checkupReportTable;
	@NonNull private final SAP_GLJournal_StepDefData sapGLJournalTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final C_BP_BankAccount_StepDefData bankAccountTable;
	@NonNull private final C_BP_BankAccount_DirectDebitMandate_StepDefData mandateTable;
	@NonNull private final ImmutableList<StepDefData<?>> stepDefDatas;

	public IdentifiersResolver(
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final C_Payment_StepDefData paymentTable,
			@NonNull final C_AllocationHdr_StepDefData allocationTable,
			@NonNull final M_MatchInv_StepDefData matchInvTable,
			@NonNull final M_InOut_StepDefData inOutTable,
			@NonNull final M_Inventory_StepDefData inventoryTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_DunningDoc_StepDefData dunningDocTable,
			@NonNull final PP_Cost_Collector_StepDefData ppCostCollectorTable,
			@NonNull final M_CostRevaluation_StepDefData costRevaluationTable,
			@NonNull final M_ShipperTransportation_StepDefData shipperTransportationTable,
			@NonNull final PP_Order_StepDefData ppOrderTable,
			@NonNull final C_Order_MFGWarehouse_Report_StepDefData checkupReportTable,
			@NonNull final SAP_GLJournal_StepDefData sapGLJournalTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final C_BP_BankAccount_StepDefData bankAccountTable,
			@NonNull final C_BP_BankAccount_DirectDebitMandate_StepDefData mandateTable)
	{
		this.invoiceTable = invoiceTable;
		this.paymentTable = paymentTable;
		this.allocationTable = allocationTable;
		this.matchInvTable = matchInvTable;
		this.inOutTable = inOutTable;
		this.inventoryTable = inventoryTable;
		this.orderTable = orderTable;
		this.dunningDocTable = dunningDocTable;
		this.ppCostCollectorTable = ppCostCollectorTable;
		this.costRevaluationTable = costRevaluationTable;
		this.shipperTransportationTable = shipperTransportationTable;
		this.ppOrderTable = ppOrderTable;
		this.checkupReportTable = checkupReportTable;
		this.sapGLJournalTable = sapGLJournalTable;
		this.bpartnerTable = bpartnerTable;
		this.bankAccountTable = bankAccountTable;
		this.mandateTable = mandateTable;
		// checkupReportTable is not a StepDefData, so it is not part of this list
		this.stepDefDatas = ImmutableList.of(invoiceTable, paymentTable, allocationTable, matchInvTable, inOutTable, inventoryTable, orderTable,
				dunningDocTable, ppCostCollectorTable, costRevaluationTable, shipperTransportationTable, ppOrderTable, sapGLJournalTable,
				bpartnerTable, bankAccountTable, mandateTable);
	}

	@NonNull
	public StepDefData<?> getStepDefDataByTableName(@NonNull final String tableName)
	{
		return stepDefDatas.stream()
				.filter(stepDefData -> tableName.equals(stepDefData.getTableName()))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No StepDefData registered for table " + tableName));
	}

	/**
	 * Registers the record referenced by {@code recordRef} under {@code identifier}, in the StepDefData that is responsible for the record's table.
	 */
	public void putRecord(@NonNull final StepDefDataIdentifier identifier, @NonNull final TableRecordReference recordRef)
	{
		getStepDefDataByTableName(recordRef.getTableName()).putRecord(identifier, recordRef);
	}

	@NonNull
	public ImmutableSet<TableRecordReference> getTableRecordReferencesOfCommaSeparatedIdentifiers(@Nullable final String commaSeparatedIdentifiers)
	{
		final String commaSeparatedIdentifiersNorm = StringUtils.trimBlankToNull(commaSeparatedIdentifiers);
		if (commaSeparatedIdentifiersNorm == null || commaSeparatedIdentifiersNorm.equals("-"))
		{
			return ImmutableSet.of();
		}

		return getTableRecordReferences(StepDefDataIdentifier.ofCommaSeparatedString(commaSeparatedIdentifiersNorm));
	}

	@NonNull
	public TableRecordReferenceSet getTableRecordReferenceSetOfCommaSeparatedIdentifiers(@Nullable final String commaSeparatedIdentifiers)
	{
		return TableRecordReferenceSet.of(getTableRecordReferencesOfCommaSeparatedIdentifiers(commaSeparatedIdentifiers));
	}

	@NonNull
	public ImmutableSet<TableRecordReference> getTableRecordReferences(@NonNull final List<StepDefDataIdentifier> identifiers)
	{
		return identifiers.stream()
				.map(this::getTableRecordReference)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	public TableRecordReference getTableRecordReference(@NonNull final StepDefDataIdentifier identifier)
	{
		final ArrayList<TableRecordReference> result = new ArrayList<>();
		invoiceTable.getIdOptional(identifier)
				.map(id -> TableRecordReference.of(I_C_Invoice.Table_Name, id))
				.ifPresent(result::add);
		paymentTable.getIdOptional(identifier)
				.map(id -> TableRecordReference.of(I_C_Payment.Table_Name, id))
				.ifPresent(result::add);
		allocationTable.getIdOptional(identifier)
				.map(id -> TableRecordReference.of(I_C_AllocationHdr.Table_Name, id))
				.ifPresent(result::add);
		matchInvTable.getIdOptional(identifier)
				.map(MatchInvId::toRecordRef)
				.ifPresent(result::add);
		inOutTable.getIdOptional(identifier)
				.map(InOutId::toRecordRef)
				.ifPresent(result::add);
		inventoryTable.getIdOptional(identifier)
				.map(id -> TableRecordReference.of(I_M_Inventory.Table_Name, id))
				.ifPresent(result::add);
		orderTable.getIdOptional(identifier)
				.map(OrderId::toRecordRef)
				.ifPresent(result::add);
		dunningDocTable.getIdOptional(identifier)
				.map(DunningDocId::toRecordRef)
				.ifPresent(result::add);
		ppCostCollectorTable.getOptional(identifier)
				.map(cc -> TableRecordReference.of(I_PP_Cost_Collector.Table_Name, cc.getPP_Cost_Collector_ID()))
				.ifPresent(result::add);
		costRevaluationTable.getIdOptional(identifier)
				.map(id -> TableRecordReference.of(I_M_CostRevaluation.Table_Name, id))
				.ifPresent(result::add);
		shipperTransportationTable.getIdOptional(identifier)
				.map(id -> TableRecordReference.of(I_M_ShipperTransportation.Table_Name, id))
				.ifPresent(result::add);
		ppOrderTable.getIdOptional(identifier)
				.map(id -> TableRecordReference.of(I_PP_Order.Table_Name, id))
				.ifPresent(result::add);
		checkupReportTable.getIdOptional(identifier)
				.map(id -> TableRecordReference.of(C_Order_MFGWarehouse_Report_StepDefData.TABLE_NAME, id))
				.ifPresent(result::add);
		sapGLJournalTable.getIdOptional(identifier)
				.map(id -> TableRecordReference.of(I_SAP_GLJournal.Table_Name, id))
				.ifPresent(result::add);
		bpartnerTable.getIdOptional(identifier)
				.map(id -> TableRecordReference.of(I_C_BPartner.Table_Name, id))
				.ifPresent(result::add);
		bankAccountTable.getIdOptional(identifier)
				.map(id -> TableRecordReference.of(I_C_BP_BankAccount.Table_Name, id.getRepoId()))
				.ifPresent(result::add);
		mandateTable.getOptional(identifier)
				.map(TableRecordReference::of)
				.ifPresent(result::add);

		if (result.isEmpty())
		{
			throw new AdempiereException("No known document found for identifier: " + identifier);
		}
		else if (result.size() > 1)
		{
			throw new AdempiereException("More than one document found for identifier " + identifier + ": " + result);
		}
		else
		{
			return result.get(0);
		}
	}

	public Optional<StepDefDataIdentifier> getIdentifier(final TableRecordReference documentRef)
	{
		final String tableName = documentRef.getTableName();
		final int recordId = documentRef.getRecord_ID();

		switch (tableName)
		{
			case I_C_Invoice.Table_Name:
				return invoiceTable.getFirstIdentifierById(InvoiceId.ofRepoId(recordId));
			case I_C_Payment.Table_Name:
				return paymentTable.getFirstIdentifierById(PaymentId.ofRepoId(recordId));
			case I_C_AllocationHdr.Table_Name:
				return allocationTable.getFirstIdentifierById(PaymentAllocationId.ofRepoId(recordId));
			case I_M_MatchInv.Table_Name:
				return matchInvTable.getFirstIdentifierById(MatchInvId.ofRepoId(recordId));
			case I_M_InOut.Table_Name:
				return inOutTable.getFirstIdentifierById(InOutId.ofRepoId(recordId));
			case I_M_Inventory.Table_Name:
				return inventoryTable.getFirstIdentifierById(InventoryId.ofRepoId(recordId));
			case I_SAP_GLJournal.Table_Name:
				return sapGLJournalTable.getFirstIdentifierById(SAPGLJournalId.ofRepoId(recordId));
			default:
				return Optional.empty();
		}
	}

	public TableRecordReferenceSet getAccountableDocumentRefs()
	{
		final HashSet<TableRecordReference> result = new HashSet<>();

		invoiceTable.streamIds()
				.map(id -> TableRecordReference.of(I_C_Invoice.Table_Name, id))
				.forEach(result::add);
		paymentTable.streamIds()
				.map(id -> TableRecordReference.of(I_C_Payment.Table_Name, id))
				.forEach(result::add);
		allocationTable.streamIds()
				.map(id -> TableRecordReference.of(I_C_AllocationHdr.Table_Name, id))
				.forEach(result::add);
		matchInvTable.streamIds()
				.map(MatchInvId::toRecordRef)
				.forEach(result::add);
		inOutTable.streamIds()
				.map(InOutId::toRecordRef)
				.forEach(result::add);
		sapGLJournalTable.streamIds()
				.map(id -> TableRecordReference.of(I_SAP_GLJournal.Table_Name, id))
				.forEach(result::add);

		return TableRecordReferenceSet.of(result);
	}
}
