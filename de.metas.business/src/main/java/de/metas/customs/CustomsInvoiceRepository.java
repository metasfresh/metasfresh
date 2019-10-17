package de.metas.customs;

import static org.adempiere.model.InterfaceWrapperHelper.deleteAll;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Customs_Invoice;
import org.compiere.model.I_C_Customs_Invoice_Line;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;

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
	I_C_Customs_Invoice getByIdInTrx(final CustomsInvoiceId id)
	{
		return load(id, I_C_Customs_Invoice.class);
	}

	public DocTypeId retrieveCustomsInvoiceDocTypeId()
	{
		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

		final DocTypeQuery docTypeQuery = DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_CustomsInvoice)
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

	private List<I_C_Customs_Invoice_Line> retrieveLineRecords(@NonNull final CustomsInvoiceId customsInvoiceId)
	{
		return Services.get(IQueryBL.class)
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

		record.setC_UOM_ID(line.getUomId().getRepoId());

		saveRecord(record);

		line.setId(CustomsInvoiceLineId.ofRepoId(record.getC_Customs_Invoice_ID(), record.getC_Customs_Invoice_Line_ID()));

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

	public void setCustomsInvoiceToShipment(
			@NonNull final InOutId shipmentId,
			@NonNull final CustomsInvoiceId customsInvoiceId)
	{
		final IInOutDAO inoutDAO = Services.get(IInOutDAO.class);

		final I_M_InOut shipmentRecord = inoutDAO.getById(shipmentId);

		shipmentRecord.setC_Customs_Invoice_ID(customsInvoiceId.getRepoId());

		shipmentRecord.setIsExportedToCustomsInvoice(true);

		saveRecord(shipmentRecord);
	}

	public void setCustomsInvoiceLineToShipmentLine(
			@NonNull final InOutAndLineId shipmentLine,
			@NonNull final CustomsInvoiceLineId customsInvoiceLineId)
	{
		final IInOutDAO inoutDAO = Services.get(IInOutDAO.class);

		final I_M_InOutLine shipmentLineRecord = inoutDAO.getLineById(shipmentLine.getInOutLineId());

		shipmentLineRecord.setC_Customs_Invoice_Line_ID(customsInvoiceLineId.getRepoId());

		saveRecord(shipmentLineRecord);

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
		final String customsTariff = line.getM_Product().getCustomsTariff();

		return Check.isEmpty(customsTariff);
	}

}
