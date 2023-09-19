/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contracts.modular.interim.invoice;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.invoicecandidate.FlatrateTerm_Handler;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.interim.bpartner.BPartnerInterimContract;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.ModularContractLogEntry;
import de.metas.contracts.modular.log.ModularContractLogQuery;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutLineId;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.NewInvoiceCandidate;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerDAO;
import de.metas.invoicecandidate.externallyreferenced.InvoiceCandidateRepository;
import de.metas.invoicecandidate.externallyreferenced.ManualCandidateService;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderDAO;
import de.metas.order.InvoiceRule;
import de.metas.order.OrderAndLineId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Repository
public class InterimInvoiceCandidateService
{
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IInvoiceCandidateHandlerDAO invoiceCandidateHandlerDAO = Services.get(IInvoiceCandidateHandlerDAO.class);
	private final ManualCandidateService manualCandidateService = SpringContextHolder.instance.getBean(ManualCandidateService.class);
	private final InvoiceCandidateRepository invoiceCandidateRepository = SpringContextHolder.instance.getBean(InvoiceCandidateRepository.class);
	private final ModularContractLogService modularContractLogService = SpringContextHolder.instance.getBean(ModularContractLogService.class);
	private final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository = SpringContextHolder.instance.getBean(ModCntrInvoicingGroupRepository.class);

	private DocTypeId interimInvoiceDocType;

	public ImmutableSet<InvoiceCandidateId> createInterimInvoiceCandidatesFor(@NonNull final I_C_Flatrate_Term flatrateTermRecord, @NonNull final BPartnerInterimContract bPartnerInterimContract)
	{
		final List<I_M_InOutLine> inOutLines = getUnprocessedInOutLines(flatrateTermRecord);
		if (inOutLines.isEmpty())
		{
			return ImmutableSet.of();
		}

		final OrderAndLineId orderAndLineId = OrderAndLineId.ofRepoIds(flatrateTermRecord.getC_Order_Term_ID(), flatrateTermRecord.getC_OrderLine_Term_ID());
		final I_C_OrderLine orderLine = orderDAO.getOrderLineById(orderAndLineId);
		final I_C_Order order = orderDAO.getById(orderAndLineId.getOrderId());
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(order.getBill_BPartner_ID());
		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());
		final UomId stockUOM = productBL.getStockUOMId(productId);
		final OrgId orgId = OrgId.ofRepoId(flatrateTermRecord.getAD_Org_ID());
		final ZoneId orgTimeZone = orgDAO.getTimeZone(orgId);
		final ProductId productIdToInvoice = modCntrInvoicingGroupRepository.getInvoicingGroupProductFor(productId)
				.orElse(productId);

		final NewInvoiceCandidate.NewInvoiceCandidateBuilder newInvoiceCandidateTemplate = NewInvoiceCandidate.builder()
				.orgId(orgId)
				.soTrx(SOTrx.PURCHASE)
				.invoiceDocTypeId(getInterimInvoiceDocType())
				.invoiceRule(InvoiceRule.Immediate)
				.harvestYearAndCalendarId(bPartnerInterimContract.getYearAndCalendarId())
				.productId(productIdToInvoice)
				.paymentTermId(PaymentTermId.ofRepoId(order.getC_PaymentTerm_ID()))
				.billPartnerInfo(BPartnerInfo.builder()
										 .bpartnerId(bpartnerId)
										 .bpartnerLocationId(BPartnerLocationId.ofRepoId(bpartnerId, order.getBill_Location_ID()))
										 .contactId(BPartnerContactId.ofRepoIdOrNull(bpartnerId, order.getBill_User_ID()))
										 .build())
				.invoicingUomId(stockUOM)
				//.qtyOrdered(stockDeliveredQty)
				//.qtyDelivered(stockDeliveredQty)
				.presetDateInvoiced(LocalDate.now(orgTimeZone))
				.dateOrdered(TimeUtil.asLocalDate(order.getDateOrdered(), orgTimeZone))
				.recordReference(TableRecordReference.of(flatrateTermRecord))
				.isInterimInvoice(true)
				.isManual(false)
				.handlerId(invoiceCandidateHandlerDAO.retrieveIdForClassOneOnly(FlatrateTerm_Handler.class))
				.flatrateTermId(FlatrateTermId.ofRepoId(flatrateTermRecord.getC_Flatrate_Term_ID()));

		final ImmutableSet.Builder<InvoiceCandidateId> invoiceCandidateSet = ImmutableSet.builder();

		inOutLines.forEach(
				inOutLine -> {
					final StockQtyAndUOMQty stockDeliveredQty = inOutBL.getStockQtyAndQtyInUOM(inOutLine);

					final NewInvoiceCandidate newInvoiceCandidate = newInvoiceCandidateTemplate
							.qtyOrdered(stockDeliveredQty)
							.qtyDelivered(stockDeliveredQty)
							.build();

					final InvoiceCandidateId invoiceCandidateId = invoiceCandidateRepository.save(manualCandidateService.createInvoiceCandidate(newInvoiceCandidate));
					invoiceCandidateSet.add(invoiceCandidateId);
					modularContractLogService.setICProcessed(
							ModularContractLogQuery.builder()
									.contractType(LogEntryContractType.INTERIM)
									.referenceSet(TableRecordReferenceSet.of(TableRecordReference.of(I_M_InOutLine.Table_Name, inOutLine.getM_InOutLine_ID())))
									.build(),
							invoiceCandidateId);
				});

		return invoiceCandidateSet.build();
	}

	@NonNull
	private List<I_M_InOutLine> getUnprocessedInOutLines(final @NonNull I_C_Flatrate_Term flatrateTermRecord)
	{
		final OrderAndLineId orderAndLineId = OrderAndLineId.ofRepoIds(flatrateTermRecord.getC_Order_Term_ID(), flatrateTermRecord.getC_OrderLine_Term_ID());
		final List<I_M_InOutLine> inOutLines = inOutDAO.retrieveInterimInvoiceableInOuts(orderAndLineId);
		final TableRecordReferenceSet tableRecordReferences = TableRecordReferenceSet.of(inOutLines.stream()
				.map(TableRecordReference::of)
				.collect(ImmutableSet.toImmutableSet()));
		final ModularContractLogQuery query = ModularContractLogQuery.builder()
				.referenceSet(tableRecordReferences)
				.flatrateTermId(FlatrateTermId.ofRepoId(flatrateTermRecord.getC_Flatrate_Term_ID()))
				.contractType(LogEntryContractType.INTERIM)
				.build();
		final ImmutableSet<InOutLineId> inOutLineIds = modularContractLogService.getModularContractLogEntries(query)
				.stream().filter(ModularContractLogEntry::isProcessed)
				.map(entry -> InOutLineId.ofRepoId(entry.getReferencedRecord().getRecord_ID()))
				.collect(ImmutableSet.toImmutableSet());
		return inOutLines.stream()
				.filter(line -> !inOutLineIds.contains(InOutLineId.ofRepoId(line.getM_InOutLine_ID())))
				.collect(ImmutableList.toImmutableList());
	}

	private DocTypeId getInterimInvoiceDocType()
	{
		if (interimInvoiceDocType == null)
		{
			interimInvoiceDocType = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
					.adClientId(Env.getAD_Client_ID())
					.docBaseType(DocBaseType.APInvoice)
					.docSubType(X_C_DocType.DOCSUBTYPE_DownPayment)
					.build());
		}
		return interimInvoiceDocType;
	}

}
