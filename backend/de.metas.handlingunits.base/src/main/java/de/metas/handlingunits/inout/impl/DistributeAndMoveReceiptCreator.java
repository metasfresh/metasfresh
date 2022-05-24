package de.metas.handlingunits.inout.impl;

import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.QuarantineInOutLine;
import de.metas.handlingunits.inout.IInOutDDOrderBL;
import de.metas.handlingunits.inout.impl.DistributeAndMoveReceiptCreator.Result.ResultBuilder;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.api.IInOutMovementBL;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.X_M_ReceiptSchedule;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.product.LotNumberQuarantine;
import de.metas.product.LotNumberQuarantineRepository;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;
import org.adempiere.mm.attributes.api.ILotNumberBL;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Movement;
import org.eevolution.model.I_DD_Order;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

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

@ToString(doNotUseGetters = true)
@Service
public class DistributeAndMoveReceiptCreator
{
	// services
	private final transient IInOutDAO inoutDAO = Services.get(IInOutDAO.class);
	private final transient IInOutBL inoutBL = Services.get(IInOutBL.class);

	private final transient IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);

	private final transient ILotNumberBL lotNoBL = Services.get(ILotNumberBL.class);
	private final transient LotNumberQuarantineRepository lotNumberQuarantineRepository;

	private final transient DDOrderService ddOrderService;
	private final transient IInOutDDOrderBL inoutDDOrderBL = Services.get(IInOutDDOrderBL.class);
	private final transient IInOutMovementBL inoutMovementBL = Services.get(IInOutMovementBL.class);

	public DistributeAndMoveReceiptCreator(
			@NonNull final LotNumberQuarantineRepository lotNumberQuarantineRepository,
			@NonNull final DDOrderService ddOrderService)
	{
		this.lotNumberQuarantineRepository = lotNumberQuarantineRepository;
		this.ddOrderService = ddOrderService;
	}

	public Result createDocumentsFor(
			@NonNull final I_M_InOut receipt,
			@Nullable final LocatorId locatorToId)
	{
		Check.assume(!receipt.isSOTrx(), "InOut shall be a receipt: {}", receipt);
		Check.assume(!inoutBL.isReversal(receipt), "InOut shall not be a reversal", receipt);

		final PartitionedInOutLineRecords partitionedLines = partitionLines(receipt);

		final ResultBuilder result = Result.builder();

		if (!Check.isEmpty(partitionedLines.getLinesToQuarantine()))
		{
			result.quarantineDDOrders(ddOrderService.createQuarantineDDOrderForReceiptLines(partitionedLines.getLinesToQuarantine()));
			setInvoiceCandsInDispute(partitionedLines.getLinesToQuarantine());
		}

		for (final I_M_InOutLine lineToDDOrder : partitionedLines.getLinesToDD_Order())
		{
			final Optional<I_DD_Order> ddOrder = inoutDDOrderBL.createDDOrderForInOutLine(lineToDDOrder, locatorToId);
			ddOrder.ifPresent(result::ddOrder);
		}

		if (!Check.isEmpty(partitionedLines.getLinesToMove()))
		{
			final List<de.metas.interfaces.I_M_Movement> //
			movements = inoutMovementBL.generateMovementFromReceiptLines(partitionedLines.getLinesToMove(), locatorToId);

			result.movements(movements);
		}

		return result.build();
	}

	@lombok.Value
	@lombok.Builder
	public static class Result
	{
		@Singular
		List<I_DD_Order> ddOrders;

		@Singular
		List<I_M_Movement> movements;

		@Singular
		List<I_DD_Order> quarantineDDOrders;
	}

	private PartitionedInOutLineRecords partitionLines(@NonNull final I_M_InOut receipt)
	{
		final de.metas.handlingunits.inout.impl.DistributeAndMoveReceiptCreator.PartitionedInOutLineRecords.PartitionedInOutLineRecordsBuilder //
		builder = PartitionedInOutLineRecords.builder();

		final List<I_M_InOutLine> linesAll = inoutDAO.retrieveLines(receipt, I_M_InOutLine.class);
		for (final I_M_InOutLine inOutLine : linesAll)
		{
			if (inOutLine.isPackagingMaterial())
			{
				continue; // nothing to do. Skip this line
			}

			final LotNumberQuarantine quarantineLotNo = getQuarantineLotNoOrNull(inOutLine);
			if (quarantineLotNo != null)
			{
				final QuarantineInOutLine quarantineInOutLine = new QuarantineInOutLine(inOutLine, quarantineLotNo);
				builder.lineToQuarantine(quarantineInOutLine);
			}
			else if (isCreateDDOrder(inOutLine))
			{
				builder.lineToDD_Order(inOutLine);
			}
			else
			{
				builder.lineToMove(inOutLine);
			}
		}
		return builder.build();
	}

	@Value
	@Builder
	private static class PartitionedInOutLineRecords
	{
		@Singular("lineToQuarantine")
		List<QuarantineInOutLine> linesToQuarantine;

		@Singular("lineToDD_Order")
		List<I_M_InOutLine> linesToDD_Order;

		@Singular("lineToMove")
		List<org.compiere.model.I_M_InOutLine> linesToMove;
	}

	private void setInvoiceCandsInDispute(final List<QuarantineInOutLine> lines)
	{
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

		lines.stream()
				.map(QuarantineInOutLine::getInOutLine)
				.forEach(invoiceCandBL::markInvoiceCandInDisputeForReceiptLine);
	}

	private LotNumberQuarantine getQuarantineLotNoOrNull(final I_M_InOutLine inOutLine)
	{
		final ProductId productId = ProductId.ofRepoId(inOutLine.getM_Product_ID());

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

		return lotNumberQuarantineRepository.getByProductIdAndLot(productId, lotNumberAttributeValue);
	}

	private boolean isCreateDDOrder(@NonNull final I_M_InOutLine inOutLine)
	{
		final List<I_M_ReceiptSchedule> rsForInOutLine = receiptScheduleDAO.retrieveRsForInOutLine(inOutLine);

		for (final I_M_ReceiptSchedule resceiptSchedule : rsForInOutLine)
		{
			final boolean createDistributionOrder = X_M_ReceiptSchedule.ONMATERIALRECEIPTWITHDESTWAREHOUSE_CreateDistributionOrder
					.equals(resceiptSchedule.getOnMaterialReceiptWithDestWarehouse());

			if (createDistributionOrder)
			{
				return true;
			}
		}
		return false;
	}
}
