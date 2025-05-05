package de.metas.customs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.inout.InOutAndLineId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Customs_Invoice;
import org.compiere.model.I_C_Customs_Invoice_Line;
import org.compiere.model.I_M_InOutLine_To_C_Customs_Invoice_Line;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.deleteAll;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.business
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
public class CustomsInvoiceRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	I_C_Customs_Invoice getByIdInTrx(final CustomsInvoiceId id)
	{
		return load(id, I_C_Customs_Invoice.class);
	}

	I_C_Customs_Invoice_Line getLineByIdInTrx(final CustomsInvoiceLineId id)
	{
		return load(id, I_C_Customs_Invoice_Line.class);
	}

	public DocTypeId retrieveCustomsInvoiceDocTypeId()
	{
		final DocTypeQuery docTypeQuery = DocTypeQuery.builder()
				.docBaseType(DocBaseType.CustomsInvoice)
				.docSubType(DocTypeQuery.DOCSUBTYPE_Any)
				.adClientId(Env.getClientId().getRepoId())
				.adOrgId(Env.getOrgId().getRepoId())
				.build();

		return docTypeDAO.getDocTypeIdOrNull(docTypeQuery);
	}

	I_C_Customs_Invoice save(@NonNull final CustomsInvoice customsInvoice)
	{
		final I_C_Customs_Invoice customsInvoiceRecord;
		final HashMap<CustomsInvoiceLineId, I_C_Customs_Invoice_Line> existingLineRecords;

		if (customsInvoice.getId() == null)
		{
			customsInvoiceRecord = newInstance(I_C_Customs_Invoice.class);
			existingLineRecords = new HashMap<>();
		}
		else
		{
			customsInvoiceRecord = load(customsInvoice.getId(), I_C_Customs_Invoice.class);
			existingLineRecords = retrieveLineRecords(customsInvoice.getId())
					.stream()
					.collect(GuavaCollectors.toHashMapByKey(lineRecord -> CustomsInvoiceLineId.ofRepoId(lineRecord.getC_Customs_Invoice_ID(), lineRecord.getC_Customs_Invoice_Line_ID())));
		}

		customsInvoiceRecord.setDocumentNo(customsInvoice.getDocumentNo());

		final OrgId orgId = customsInvoice.getOrgId();
		customsInvoiceRecord.setAD_Org_ID(orgId.getRepoId());

		final BPartnerLocationId bpartnerAndLocation = customsInvoice.getBpartnerAndLocationId();
		final BPartnerId bpartnerId = bpartnerAndLocation.getBpartnerId();

		customsInvoiceRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		customsInvoiceRecord.setC_BPartner_Location_ID(bpartnerAndLocation.getRepoId());
		customsInvoiceRecord.setAD_User_ID(UserId.toRepoId(customsInvoice.getUserId()));

		final DocTypeId docTypeId = customsInvoice.getDocTypeId();
		customsInvoiceRecord.setC_DocType_ID(docTypeId.getRepoId());

		customsInvoiceRecord.setDateInvoiced(TimeUtil.asTimestamp(customsInvoice.getInvoiceDate()));

		customsInvoiceRecord.setDocAction(customsInvoice.getDocAction());
		customsInvoiceRecord.setDocStatus(customsInvoice.getDocStatus().getCode());

		customsInvoiceRecord.setC_Currency_ID(customsInvoice.getCurrencyId().getRepoId());

		customsInvoiceRecord.setBPartnerAddress(customsInvoice.getBpartnerAddress());

		saveRecord(customsInvoiceRecord);

		final CustomsInvoiceId customsInvoiceId = CustomsInvoiceId.ofRepoId(customsInvoiceRecord.getC_Customs_Invoice_ID());
		customsInvoice.setId(customsInvoiceId);
		customsInvoice.setCreatedBy(UserId.ofRepoId(customsInvoiceRecord.getCreatedBy()));
		customsInvoice.setLastUpdatedBy(UserId.ofRepoId(customsInvoiceRecord.getUpdatedBy()));

		for (final CustomsInvoiceLine line : customsInvoice.getLines())
		{
			final I_C_Customs_Invoice_Line existingLineRecord = existingLineRecords.remove(line.getId());
			saveLine(line, customsInvoiceId, existingLineRecord);
		}

		//
		// Delete remaining lines
		deleteAll(existingLineRecords.values());

		return customsInvoiceRecord;
	}

	@NonNull
	public CustomsInvoice retrieveById(@NonNull final CustomsInvoiceId customsInvoiceId)
	{
		final I_C_Customs_Invoice customsInvoicePO = load(customsInvoiceId, I_C_Customs_Invoice.class);

		final CurrencyId currencyId = CurrencyId.ofRepoId(customsInvoicePO.getC_Currency_ID());
		final ImmutableListMultimap<CustomsInvoiceLineId, CustomsInvoiceLineAlloc> allocs = retrieveAllocations(customsInvoiceId);
		final ImmutableList<CustomsInvoiceLine> lines = retrieveLines(customsInvoiceId, currencyId, allocs);

		return CustomsInvoice.builder()
				.id(customsInvoiceId)
				.createdBy(UserId.ofRepoId(customsInvoicePO.getCreatedBy()))
				.lastUpdatedBy(UserId.ofRepoId(customsInvoicePO.getUpdatedBy()))
				.documentNo(customsInvoicePO.getDocumentNo())
				.orgId(OrgId.ofRepoId(customsInvoicePO.getAD_Org_ID()))
				.bpartnerAndLocationId(BPartnerLocationId.ofRepoId(customsInvoicePO.getC_BPartner_ID(), customsInvoicePO.getC_BPartner_Location_ID()))
				.bpartnerAddress(customsInvoicePO.getBPartnerAddress())
				.userId(UserId.ofRepoId(customsInvoicePO.getAD_User_ID()))
				.currencyId(currencyId)
				.docTypeId(DocTypeId.ofRepoId(customsInvoicePO.getC_DocType_ID()))
				.invoiceDate(TimeUtil.asLocalDate(customsInvoicePO.getDateInvoiced()))
				.docAction(customsInvoicePO.getDocAction())
				.docStatus(DocStatus.ofNullableCode(customsInvoicePO.getDocStatus()))
				.lines(lines)
				.build();
	}

	private ImmutableList<CustomsInvoiceLine> retrieveLines(
			@NonNull final CustomsInvoiceId id,
			@NonNull final CurrencyId currencyId,
			@NonNull final ImmutableListMultimap<CustomsInvoiceLineId, CustomsInvoiceLineAlloc> allocs)
	{

		final List<I_C_Customs_Invoice_Line> lines = retrieveLineRecords(id);

		return lines.stream()
				.map(line -> toCustomsInvoiceLine(line, id, currencyId, allocs))
				.collect(GuavaCollectors.toImmutableList());
	}

	@NonNull
	private CustomsInvoiceLine toCustomsInvoiceLine(
			@NonNull final I_C_Customs_Invoice_Line record,
			@NonNull final CustomsInvoiceId customsInvoiceId,
			@NonNull final CurrencyId currencyId,
			@NonNull final ImmutableListMultimap<CustomsInvoiceLineId, CustomsInvoiceLineAlloc> allocs)
	{
		final CustomsInvoiceLineId id = CustomsInvoiceLineId.ofRepoId(customsInvoiceId, record.getC_Customs_Invoice_Line_ID());
		final Quantity qty = Quantitys.of(record.getInvoicedQty(), UomId.ofRepoId(record.getC_UOM_ID()));
		final Money lineNetAmt = Money.of(record.getLineNetAmt(), currencyId);

		return CustomsInvoiceLine.builder()
				.id(id)
				.lineNo(record.getLineNo())
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.quantity(qty)
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.lineNetAmt(lineNetAmt)
				.allocations(new ArrayList<>(allocs.get(id)))
				.build();
	}

	private List<I_C_Customs_Invoice_Line> retrieveLineRecords(@NonNull final CustomsInvoiceId customsInvoiceId)
	{
		return queryBL
				.createQueryBuilder(I_C_Customs_Invoice_Line.class)
				.addEqualsFilter(I_C_Customs_Invoice_Line.COLUMN_C_Customs_Invoice_ID, customsInvoiceId)
				.create()
				.list();
	}

	private void saveLine(
			@NonNull final CustomsInvoiceLine line,
			@NonNull final CustomsInvoiceId customsInvoiceId,
			final I_C_Customs_Invoice_Line existingRecord)
	{
		final I_C_Customs_Invoice_Line record;
		if (existingRecord == null)
		{
			record = newInstance(I_C_Customs_Invoice_Line.class);
		}
		else
		{
			record = existingRecord;
		}

		record.setC_Customs_Invoice_ID(customsInvoiceId.getRepoId());

		record.setLineNo(line.getLineNo());

		final OrgId orgId = line.getOrgId();
		record.setAD_Org_ID(orgId.getRepoId());

		final Quantity quantity = line.getQuantity();
		record.setInvoicedQty(quantity.toBigDecimal());
		record.setC_UOM_ID(quantity.getUomId().getRepoId());

		final Money lineNetAmt = line.getLineNetAmt();
		record.setLineNetAmt(lineNetAmt.toBigDecimal());

		final ProductId productId = line.getProductId();
		record.setM_Product_ID(productId.getRepoId());

		saveRecord(record);

		line.setId(CustomsInvoiceLineId.ofRepoId(record.getC_Customs_Invoice_ID(), record.getC_Customs_Invoice_Line_ID()));

		saveLineAllocations(line);
	}

	public CustomsInvoice updateDocActionAndStatus(@NonNull final CustomsInvoice customsInvoice)
	{
		final I_C_Customs_Invoice customsInvoiceRecord = load(customsInvoice.getId(), I_C_Customs_Invoice.class);

		final String docAction = customsInvoiceRecord.getDocAction();
		final DocStatus docStatus = DocStatus.ofCode(customsInvoiceRecord.getDocStatus());

		return customsInvoice.toBuilder()
				.docAction(docAction)
				.docStatus(docStatus)
				.build();
	}

	public Set<ProductId> retrieveProductIdsWithNoCustomsTariff(final CustomsInvoiceId customsInvoiceId)
	{
		final List<I_C_Customs_Invoice_Line> lineRecords = retrieveLineRecords(customsInvoiceId);

		return lineRecords.stream()
				.filter(this::hasNoCustomsTariff)
				.map(line -> ProductId.ofRepoId(line.getM_Product_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	private boolean hasNoCustomsTariff(final I_C_Customs_Invoice_Line line)
	{
		final I_M_Product product = productDAO.getById(line.getM_Product_ID());

		return product.getM_CustomsTariff_ID() <= 0;
	}

	private void saveLineAllocations(final CustomsInvoiceLine line)
	{
		final HashMap<InOutAndLineId, I_M_InOutLine_To_C_Customs_Invoice_Line> existingRecords = retrieveAllocationRecords(line.getId())
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(allocRecord -> extractInOutAndLineId(allocRecord)));

		for (final CustomsInvoiceLineAlloc alloc : line.getAllocations())
		{
			I_M_InOutLine_To_C_Customs_Invoice_Line record = existingRecords.remove(alloc.getInoutAndLineId());
			if (record == null)
			{
				record = newInstance(I_M_InOutLine_To_C_Customs_Invoice_Line.class);
			}

			updateAllocationRecord(record, alloc, line.getId(), line.getProductId());
			saveRecord(record);
		}

		deleteAll(existingRecords.values());
	}

	private static void updateAllocationRecord(
			@NonNull final I_M_InOutLine_To_C_Customs_Invoice_Line record,
			@NonNull final CustomsInvoiceLineAlloc alloc,
			@NonNull final CustomsInvoiceLineId customsInvoiceLineId,
			@NonNull final ProductId productId)
	{
		record.setM_InOut_ID(alloc.getInoutAndLineId().getInOutId().getRepoId());
		record.setM_InOutLine_ID(alloc.getInoutAndLineId().getInOutLineId().getRepoId());

		record.setC_Customs_Invoice_ID(customsInvoiceLineId.getCustomsInvoiceId().getRepoId());
		record.setC_Customs_Invoice_Line_ID(customsInvoiceLineId.getRepoId());

		record.setM_Product_ID(productId.getRepoId());

		record.setC_UOM_ID(alloc.getQuantityInPriceUOM().getUomId().getRepoId());
		record.setMovementQty(alloc.getQuantityInPriceUOM().toBigDecimal());

		record.setPriceActual(alloc.getPrice().toBigDecimal());
		record.setC_Currency_ID(alloc.getPrice().getCurrencyId().getRepoId());

		saveRecord(record);
	}

	private List<I_M_InOutLine_To_C_Customs_Invoice_Line> retrieveAllocationRecords(final CustomsInvoiceLineId customsInvoiceLineId)
	{
		return queryBL.createQueryBuilder(I_M_InOutLine_To_C_Customs_Invoice_Line.class)
				.addEqualsFilter(I_M_InOutLine_To_C_Customs_Invoice_Line.COLUMN_C_Customs_Invoice_Line_ID, customsInvoiceLineId)
				.orderBy(I_M_InOutLine_To_C_Customs_Invoice_Line.COLUMN_M_InOutLine_To_C_Customs_Invoice_Line_ID)
				.create()
				.list();
	}

	private static InOutAndLineId extractInOutAndLineId(final I_M_InOutLine_To_C_Customs_Invoice_Line record)
	{
		return InOutAndLineId.ofRepoId(record.getM_InOut_ID(), record.getM_InOutLine_ID());
	}

	private ImmutableListMultimap<CustomsInvoiceLineId, CustomsInvoiceLineAlloc> retrieveAllocations(final CustomsInvoiceId customsInvoiceId)
	{
		return queryBL.createQueryBuilder(I_M_InOutLine_To_C_Customs_Invoice_Line.class)
				.addEqualsFilter(I_M_InOutLine_To_C_Customs_Invoice_Line.COLUMN_C_Customs_Invoice_ID, customsInvoiceId)
				.orderBy(I_M_InOutLine_To_C_Customs_Invoice_Line.COLUMN_M_InOutLine_To_C_Customs_Invoice_Line_ID)
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						record -> extractCustomsInvoiceLineId(record),
						record -> toCustomsInvoiceLineAlloc(record)));
	}

	private CustomsInvoiceLineAlloc toCustomsInvoiceLineAlloc(final I_M_InOutLine_To_C_Customs_Invoice_Line record)
	{
		return CustomsInvoiceLineAlloc.builder()
				.inoutAndLineId(extractInOutAndLineId(record))
				.price(Money.of(record.getPriceActual(), CurrencyId.ofRepoId(record.getC_Currency_ID())))
				.quantityInPriceUOM(Quantity.of(record.getMovementQty(), uomDAO.getById(record.getC_UOM_ID())))
				.build();
	}

	private static CustomsInvoiceLineId extractCustomsInvoiceLineId(final I_M_InOutLine_To_C_Customs_Invoice_Line record)
	{
		return CustomsInvoiceLineId.ofRepoId(record.getC_Customs_Invoice_ID(), record.getC_Customs_Invoice_Line_ID());
	}



}
