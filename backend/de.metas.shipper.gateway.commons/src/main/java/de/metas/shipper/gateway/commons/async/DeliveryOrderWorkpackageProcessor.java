package de.metas.shipper.gateway.commons.async;

import de.metas.async.AsyncBatchId;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.common.util.CoalesceUtil;
import de.metas.printing.model.I_AD_Archive;
import de.metas.shipper.gateway.api.ShipperGatewayId;
import de.metas.shipper.gateway.commons.ShipperGatewayServicesRegistry;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.DeliveryOrderService;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.PackageLabel;
import de.metas.shipper.gateway.spi.model.PackageLabels;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.compiere.util.MimeType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Properties;

/*
 * #%L
 * de.metas.shipper.gateway.go
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

public class DeliveryOrderWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	public static void enqueueOnTrxCommit(
			@NonNull final DeliveryOrderId deliveryOrderId,
			@NonNull final ShipperGatewayId shipperGatewayId,
			@Nullable final AsyncBatchId asyncBatchId)
	{
		final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);

		workPackageQueueFactory
				.getQueueForEnqueuing(DeliveryOrderWorkpackageProcessor.class)
				.newWorkPackage()
				.setC_Async_Batch_ID(asyncBatchId)
				.setUserInChargeId(Env.getLoggedUserIdIfExists().orElse(null))
				.bindToThreadInheritedTrx()
				.parameters()
				.setParameter(PARAM_DeliveryOrderRepoId, deliveryOrderId.getRepoId())
				.setParameter(PARAM_ShipperGatewayId, shipperGatewayId.getAsString())
				.end()
				.buildAndEnqueue();
	}

	private static final String PARAM_DeliveryOrderRepoId = "DeliveryOrderRepoId";
	private static final String PARAM_ShipperGatewayId = "ShipperGatewayId";

	// Services
	private final ShipperGatewayServicesRegistry shipperRegistry = SpringContextHolder.instance.getBean(ShipperGatewayServicesRegistry.class);
	
	@Override
	public boolean isRunInTransaction()
	{
		return false;
	}

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName_NOTUSED)
	{
		final DeliveryOrder draftedDeliveryOrder = retrieveDeliveryOrder();

		final ShipperGatewayId shipperGatewayId = getShipperGatewayId();

		final ShipperGatewayClient client = shipperRegistry
				.getClientFactory(shipperGatewayId)
				.newClientForShipperId(draftedDeliveryOrder.getShipperId());

		final DeliveryOrderService deliveryOrderRepo = //
				shipperRegistry.getDeliveryOrderService(shipperGatewayId);

		final DeliveryOrder completedDeliveryOrder = client.completeDeliveryOrder(draftedDeliveryOrder);
		deliveryOrderRepo.save(completedDeliveryOrder);

		final List<PackageLabels> packageLabelsList = client.getPackageLabelsList(completedDeliveryOrder);
		final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoIdOrNull(workPackage.getC_Async_Batch_ID());

		printLabels(completedDeliveryOrder, packageLabelsList, deliveryOrderRepo, asyncBatchId);

		return Result.SUCCESS;
	}

	private DeliveryOrder retrieveDeliveryOrder()
	{
		final ShipperGatewayId shipperGatewayId = getShipperGatewayId();
		final DeliveryOrderService deliveryOrderService = shipperRegistry.getDeliveryOrderService(shipperGatewayId);

		final DeliveryOrderId deliveryOrderRepoId = getDeliveryOrderId();
		return deliveryOrderService.getById(deliveryOrderRepoId);
	}

	@NotNull
	private ShipperGatewayId getShipperGatewayId()
	{
		final String shipperGatewayId = getParameters().getParameterAsString(PARAM_ShipperGatewayId);
		if (shipperGatewayId == null)
		{
			throw new AdempiereException("No " + PARAM_ShipperGatewayId + " parameter found in workpackage parameters.");
		}
		return ShipperGatewayId.ofString(shipperGatewayId);
	}

	@NonNull
	private DeliveryOrderId getDeliveryOrderId()
	{
		final DeliveryOrderId deliveryOrderId = getParameters().getParameterAsId(PARAM_DeliveryOrderRepoId, DeliveryOrderId.class);
		if (deliveryOrderId == null)
		{
			throw new AdempiereException("No " + PARAM_DeliveryOrderRepoId + " parameter found in workpackage parameters.");
		}
		return deliveryOrderId;
	}

	public void printLabels(
			@NonNull final DeliveryOrder deliveryOrder,
			@NonNull final List<PackageLabels> packageLabels,
			@NonNull final DeliveryOrderService deliveryOrderRepo,
			@Nullable final AsyncBatchId asyncBatchId)
	{
		for (final PackageLabels packageLabel : packageLabels)
		{
			final PackageLabel defaultPackageLabel = packageLabel.getDefaultPackageLabel();
			printLabel(deliveryOrder, defaultPackageLabel, deliveryOrderRepo, asyncBatchId);
		}
	}

	private void printLabel(
			final DeliveryOrder deliveryOrder,
			final PackageLabel packageLabel,
			@NonNull final DeliveryOrderService deliveryOrderRepo,
			@Nullable final AsyncBatchId asyncBatchId)
	{
		final IArchiveStorageFactory archiveStorageFactory = Services.get(IArchiveStorageFactory.class);

		final String fileExtWithDot = MimeType.getExtensionByType(packageLabel.getContentType());
		final String fileName = CoalesceUtil.firstNotEmptyTrimmed(packageLabel.getFileName(), packageLabel.getType().toString()) + fileExtWithDot;
		final byte[] labelData = packageLabel.getLabelData();

		final Properties ctx = Env.getCtx();
		final IArchiveStorage archiveStorage = archiveStorageFactory.getArchiveStorage(ctx);
		final I_AD_Archive archive = InterfaceWrapperHelper.create(archiveStorage.newArchive(ctx, ITrx.TRXNAME_ThreadInherited), I_AD_Archive.class);

		final ITableRecordReference deliveryOrderRef = deliveryOrderRepo.toTableRecordReference(deliveryOrder);
		archive.setAD_Table_ID(deliveryOrderRef.getAD_Table_ID());
		archive.setRecord_ID(deliveryOrderRef.getRecord_ID());
		archive.setC_BPartner_ID(deliveryOrder.getDeliveryAddress().getBpartnerId());
		// archive.setAD_Org_ID(); // TODO: do we need to orgId too?
		archive.setName(fileName);
		archiveStorage.setBinaryData(archive, labelData);
		archive.setIsReport(false);
		archive.setIsDirectEnqueue(true);
		archive.setIsDirectProcessQueueItem(true);

		archive.setC_Async_Batch_ID(AsyncBatchId.toRepoId(asyncBatchId));

		InterfaceWrapperHelper.save(archive);
	}
}
