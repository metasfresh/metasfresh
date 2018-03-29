package de.metas.handlingunits.inout.impl;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.api.ILotNumberBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeSetInstance;

import de.metas.handlingunits.ddorder.api.IHUDDOrderBL;
import de.metas.handlingunits.ddorder.api.QuarantineInOutLine;
import de.metas.handlingunits.inout.IInOutDDOrderBL;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.api.IInOutMovementBL;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.X_M_ReceiptSchedule;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.product.model.I_M_Product_LotNumber_Lock;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class DistributeAndMoveReceiptHandler
{
	private final I_M_InOut receipt;

	private List<QuarantineInOutLine> linesToQuarantine = new ArrayList<>();
	private List<I_M_InOutLine> linesToDD_Order = new ArrayList<>();
	private List<de.metas.inout.model.I_M_InOutLine> linesToMove = new ArrayList<>();

	private final IInOutDAO inoutDAO = Services.get(IInOutDAO.class);
	private final IInOutBL inoutBL = Services.get(IInOutBL.class);
	private final ILotNumberBL lotNoBL = Services.get(ILotNumberBL.class);
	private final IHUDDOrderBL huDDOrderBL = Services.get(IHUDDOrderBL.class);
	private final IInOutDDOrderBL inoutDDOrderBL = Services.get(IInOutDDOrderBL.class);
	private final IInOutMovementBL inoutMovementBL = Services.get(IInOutMovementBL.class);
	
	@Builder
	private DistributeAndMoveReceiptHandler(@NonNull final I_M_InOut receipt)
	{
		Check.assume(!receipt.isSOTrx(), "InOut shall be a receipt: {}", receipt);
		Check.assume(!inoutBL.isReversal(receipt), "InOut shall not be a reversal", receipt);
		
		this.receipt = receipt;
	}

	public void process()
	{

		partitionLines();

		if (!Check.isEmpty(linesToQuarantine))
		{
			huDDOrderBL.createQuarantineDDOrderForReceiptLines(linesToQuarantine);

			setInvoiceCandsInDispute(linesToQuarantine);

		}

		if (!Check.isEmpty(linesToDD_Order))
		{
			for (final I_M_InOutLine lineToDDOrder : linesToDD_Order)
			{
				inoutDDOrderBL.createDDOrderForInOutLine(lineToDDOrder);
			}
		}
		if (!Check.isEmpty(linesToMove))
		{
			inoutMovementBL.generateMovementFromReceiptLines(linesToMove);
		}
	}

	private void setInvoiceCandsInDispute(final List<QuarantineInOutLine> lines)
	{
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

		lines.stream()
				.map(line -> line.getInOutLine())
				.forEach(receiptLine -> invoiceCandBL.markInvoiceCandInDisputeForReceiptLine(receiptLine));

	}

	private void partitionLines()
	{
		final List<I_M_InOutLine> linesAll = inoutDAO.retrieveLines(receipt, I_M_InOutLine.class);
		for (final I_M_InOutLine inOutLine : linesAll)
		{

			if (inOutLine.isPackagingMaterial())
			{
				// nothing to do. Skip this line
				continue;
			}

			final I_M_Product_LotNumber_Lock lockedLotNo = getLockedLotNoOrNull(inOutLine);
			if (lockedLotNo != null)
			{
				final QuarantineInOutLine quarantineInOutLine = new QuarantineInOutLine(inOutLine, lockedLotNo);
				linesToQuarantine.add(quarantineInOutLine);
			}
			else if (isCreateDDOrder(inOutLine))
			{
				linesToDD_Order.add(inOutLine);
				// ddOrderBL.createDDOrderForInOutLine(inOutLine);
			}
			else
			{
				linesToMove.add(inOutLine);
			}
		}

	}

	private I_M_Product_LotNumber_Lock getLockedLotNoOrNull(final I_M_InOutLine inOutLine)
	{
		final int productId = inOutLine.getM_Product_ID();

		final I_M_AttributeSetInstance receiptLineASI = inOutLine.getM_AttributeSetInstance();

		if (receiptLineASI == null)
		{
			// if it has no attributes set it means it has no lot number set either.
			return null;
		}

		final String lotNumberAttributeValue = lotNoBL.getLotNumberAttributeValueOrNull(receiptLineASI);

		if (Check.isEmpty(lotNumberAttributeValue))
		{
			// if the attribute is not present or set it automatically means the lotno is not to quarantine, either
			return null;
		}

		final I_M_Product_LotNumber_Lock lotNumberLock = retrieveLotNumberLock(productId, lotNumberAttributeValue);

		return lotNumberLock;
	}

	private I_M_Product_LotNumber_Lock retrieveLotNumberLock(final int productId, final String lotNo)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_Product_LotNumber_Lock.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_M_Product_LotNumber_Lock.COLUMN_M_Product_ID, productId)
				.addEqualsFilter(I_M_Product_LotNumber_Lock.COLUMNNAME_Lot, lotNo)
				.orderBy(I_M_Product_LotNumber_Lock.COLUMN_M_Product_ID)
				.create()
				.first();

	}

	private boolean isCreateDDOrder(final I_M_InOutLine inOutLine)
	{
		final List<I_M_ReceiptSchedule> rsForInOutLine = Services.get(IReceiptScheduleDAO.class).retrieveRsForInOutLine(inOutLine);

		for (final I_M_ReceiptSchedule rs : rsForInOutLine)
		{
			if (X_M_ReceiptSchedule.ONMATERIALRECEIPTWITHDESTWAREHOUSE_CreateDistributionOrder.equals(rs.getOnMaterialReceiptWithDestWarehouse()))
			{
				return true;
			}

		}
		return false;
	}

	
	public static class DistributeAndMoveReceiptHandlerBuilder
	{
		public void process()
		{
			build().process();
		}
	}
}
