package de.metas.handlingunits.inout.impl;

import static org.adempiere.model.InterfaceWrapperHelper.getCtx;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mm.attributes.api.ILotNumberBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.adempiere.service.IWarehouseDAO;
import de.metas.handlingunits.ddorder.api.IHUDDOrderBL;
import de.metas.handlingunits.inout.IInOutDDOrderBL;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.api.IInOutMovementBL;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.X_M_ReceiptSchedule;
import de.metas.product.IProductLotNumberLockDAO;
import de.metas.product.model.I_M_Product_LotNumber_Lock;
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

public class OnReceiptCompleteHandler
{

	public static final OnReceiptCompleteHandler newInstance()
	{
		return new OnReceiptCompleteHandler();
	}

	private I_M_InOut receipt;

	public void setReceipt(@NonNull final I_M_InOut receipt)
	{
		this.receipt = receipt;
	}

	private List<I_M_InOutLine> linesToBlock;
	private List<I_M_InOutLine> linesToDD_Order;
	private List<de.metas.inout.model.I_M_InOutLine> linesToMove;

	final IInOutDAO inoutDAO = Services.get(IInOutDAO.class);
	final IInOutDDOrderBL ddOrderBL = Services.get(IInOutDDOrderBL.class);
	final IInOutBL inoutBL = Services.get(IInOutBL.class);
	final ILotNumberBL lotNoBL = Services.get(ILotNumberBL.class);
	final IProductLotNumberLockDAO productLotNumberLockDAO = Services.get(IProductLotNumberLockDAO.class);
	final IHUDDOrderBL huDDOrderBL = Services.get(IHUDDOrderBL.class);
	final IInOutDDOrderBL inoutDDOrderBL = Services.get(IInOutDDOrderBL.class);
	final IInOutMovementBL inoutMovementBL = Services.get(IInOutMovementBL.class);

	final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	final ITrxManager trxManager = Services.get(ITrxManager.class);

	public void onReceiptComplete()
	{
		Check.assume(!receipt.isSOTrx(), "InOut shall be a receipt: {}", receipt);
		Check.assume(!inoutBL.isReversal(receipt), "InOut shall not be a reversal", receipt);

		splitLines();

		if (!Check.isEmpty(linesToBlock))
		{
			huDDOrderBL.createBlockDDOrderForReceiptLines(getContext(), linesToBlock);
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

	private void splitLines()
	{
		final List<I_M_InOutLine> linesAll = inoutDAO.retrieveLines(receipt, I_M_InOutLine.class);
		for (final I_M_InOutLine inOutLine : linesAll)
		{

			if (inOutLine.isPackagingMaterial())
			{
				// nothing to do. Skip this line
				continue;
			}

			if (hasLockedLotNo(inOutLine))
			{
				linesToBlock.add(inOutLine);
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

	private Properties getContext()
	{
		return getCtx(receipt);
	}

	public boolean hasLockedLotNo(final I_M_InOutLine inOutLine)
	{
		final int productId = inOutLine.getM_Product_ID();

		final String lotNumberAttributeValue = lotNoBL.getLotNumberAttributeValue(inOutLine.getM_AttributeSetInstance());

		if (Check.isEmpty(lotNumberAttributeValue))
		{
			// if the attribute is not present or set it automatically means the lotno is not blocked, either
			return false;
		}

		final I_M_Product_LotNumber_Lock lotNumberLock = productLotNumberLockDAO.retrieveLotNumberLock(productId, lotNumberAttributeValue);

		return lotNumberLock != null;
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

}
