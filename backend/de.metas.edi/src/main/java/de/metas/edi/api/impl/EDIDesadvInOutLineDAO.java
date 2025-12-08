/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.edi.api.impl;

import de.metas.edi.api.DesadvInOutLine;
import de.metas.edi.api.EDIDesadvLineId;
import de.metas.esb.edi.model.I_EDI_DesadvLine_InOutLine;
import de.metas.inout.InOutLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class EDIDesadvInOutLineDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public Optional<DesadvInOutLine> getByInOutLineId(@NonNull final InOutLineId shipmentLineId)
	{
		return getRecordByInOutLineIdIfExists(shipmentLineId)
				.map(EDIDesadvInOutLineDAO::ofRecord);
	}

	public void save(@NonNull final DesadvInOutLine desadvInOutLine)
	{
		final I_EDI_DesadvLine_InOutLine record = getRecordByInOutLineIdIfExists(desadvInOutLine.getShipmentLineId())
				.orElseGet(() -> InterfaceWrapperHelper.newInstance(I_EDI_DesadvLine_InOutLine.class));

		record.setAD_Org_ID(desadvInOutLine.getOrgId().getRepoId());
		record.setM_InOutLine_ID(desadvInOutLine.getShipmentLineId().getRepoId());
		record.setEDI_DesadvLine_ID(desadvInOutLine.getDesadvLineId().getRepoId());

		record.setM_Product_ID(desadvInOutLine.getProductId().getRepoId());
		record.setQtyDeliveredInStockingUOM(desadvInOutLine.getQtyDeliveredInStockingUOM().toBigDecimal());
		record.setDesadvLineTotalQtyDelivered(desadvInOutLine.getDesadvTotalQtyDeliveredInStockingUOM().toBigDecimal());

		record.setC_UOM_ID(desadvInOutLine.getQtyDeliveredInUOM().getUomId().getRepoId());
		record.setQtyDeliveredInUOM(desadvInOutLine.getQtyDeliveredInUOM().toBigDecimal());

		if (desadvInOutLine.getQtyDeliveredInInvoiceUOM() != null)
		{
			record.setC_UOM_Invoice_ID(desadvInOutLine.getQtyDeliveredInInvoiceUOM().getUomId().getRepoId());
			record.setQtyDeliveredInInvoiceUOM(desadvInOutLine.getQtyDeliveredInInvoiceUOM().toBigDecimal());
		}

		if (desadvInOutLine.getQtyEnteredInBPartnerUOM() != null)
		{
			record.setC_UOM_BPartner_ID(desadvInOutLine.getQtyEnteredInBPartnerUOM().getUomId().getRepoId());
			record.setQtyEnteredInBPartnerUOM(desadvInOutLine.getQtyEnteredInBPartnerUOM().toBigDecimal());
		}

		InterfaceWrapperHelper.save(record);
	}

	public void delete(@NonNull final DesadvInOutLine desadvInOutLine)
	{
		final I_EDI_DesadvLine_InOutLine desadvLineInOutLine = getRecordByInOutLineIdIfExists(desadvInOutLine.getShipmentLineId())
				.orElseThrow(() -> new AdempiereException("No EDI_DesadvLine_InOutLine found for M_InOutLine_ID = " + desadvInOutLine.getShipmentLineId().getRepoId()));
		InterfaceWrapperHelper.delete(desadvLineInOutLine);
	}

	@NonNull
	private Optional<I_EDI_DesadvLine_InOutLine> getRecordByInOutLineIdIfExists(@NonNull final InOutLineId shipmentLineId)
	{
		return queryBL.createQueryBuilder(I_EDI_DesadvLine_InOutLine.class)
				.addEqualsFilter(I_EDI_DesadvLine_InOutLine.COLUMNNAME_M_InOutLine_ID, shipmentLineId)
				.create()
				.firstOnlyOptional(I_EDI_DesadvLine_InOutLine.class);
	}

	@NonNull
	private static DesadvInOutLine ofRecord(@NonNull final I_EDI_DesadvLine_InOutLine record)
	{
		final ProductId productId = ProductId.ofRepoId(record.getM_Product_ID());

		return DesadvInOutLine.builder()
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.desadvLineId(EDIDesadvLineId.ofRepoId(record.getEDI_DesadvLine_ID()))
				.shipmentLineId(InOutLineId.ofRepoId(record.getM_InOutLine_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.qtyEnteredInBPartnerUOM(Optional.of(record.getC_UOM_BPartner_ID())
												 .map(UomId::ofRepoIdOrNull)
												 .map(bpartnerUOMId -> Quantitys.of(record.getQtyEnteredInBPartnerUOM(), bpartnerUOMId))
												 .orElse(null))
				.qtyDeliveredInStockingUOM(StockQtyAndUOMQtys
												   .ofQtyInStockUOM(record.getQtyDeliveredInStockingUOM(), productId)
												   .getStockQty())
				.qtyDeliveredInUOM(Quantitys.of(record.getQtyDeliveredInUOM(), UomId.ofRepoId(record.getC_UOM_ID())))
				.qtyDeliveredInInvoiceUOM(Optional.of(record.getC_UOM_Invoice_ID())
												  .map(UomId::ofRepoIdOrNull)
												  .map(invoiceUOMId -> Quantitys.of(record.getQtyDeliveredInInvoiceUOM(), invoiceUOMId))
												  .orElse(null))
				.desadvTotalQtyDeliveredInStockingUOM(StockQtyAndUOMQtys
															  .ofQtyInStockUOM(record.getDesadvLineTotalQtyDelivered(), productId)
															  .getStockQty())
				.build();
	}
}
