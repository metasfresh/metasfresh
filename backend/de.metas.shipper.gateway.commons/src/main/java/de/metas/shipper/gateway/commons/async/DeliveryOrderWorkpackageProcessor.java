package de.metas.shipper.gateway.commons.async;

import de.metas.async.AsyncBatchId;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.common.util.CoalesceUtil;
import de.metas.printing.model.I_AD_Archive;
import de.metas.shipper.gateway.commons.ShipperGatewayServicesRegistry;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.DeliveryOrderService;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.PackageLabel;
import de.metas.shipper.gateway.spi.model.PackageLabels;
import de.metas.shipping.ShipperGatewayId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.compiere.util.MimeType;

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
			@NonNull final DeliveryOrderId deliveryOrderRepoId,
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
				.setParameter(PARAM_DeliveryOrderRepoId, deliveryOrderRepoId)
				.setParameter(PARAM_ShipperGatewayId, shipperGatewayId.toJson())
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
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage_NOTUSED, final String localTrxName_NOTUSED)
	{
		@NonNull final ShipperGatewayId shipperGatewayId = getShipperGatewayId();
		final DeliveryOrderService deliveryOrderService = shipperRegistry.getDeliveryOrderService(shipperGatewayId);

		final DeliveryOrder draftedDeliveryOrder = deliveryOrderService.getByRepoId(getDeliveryOrderRepoId());

		final ShipperGatewayClient client = deliveryOrderService.newClientForShipperId(draftedDeliveryOrder.getShipperId());

		final DeliveryOrder completedDeliveryOrder = client.completeDeliveryOrder(draftedDeliveryOrder);
		deliveryOrderService.save(completedDeliveryOrder);

		final List<PackageLabels> packageLabelsList = client.getPackageLabelsList(completedDeliveryOrder);

		final ITableRecordReference deliveryOrderRef = deliveryOrderService.toTableRecordReference(completedDeliveryOrder);
		printLabels(completedDeliveryOrder, deliveryOrderRef, packageLabelsList);

		return Result.SUCCESS;
	}

	private @NonNull ShipperGatewayId getShipperGatewayId()
	{
		return ShipperGatewayId.ofString(Check.assumeNotNull(
				getParameters().getParameterAsString(PARAM_ShipperGatewayId),
				"Parameter {} is set", PARAM_ShipperGatewayId
		));
	}

	public DeliveryOrderId getDeliveryOrderRepoId()
	{
		final int repoId = getParameters().getParameterAsInt(PARAM_DeliveryOrderRepoId, -1);
		return DeliveryOrderId.ofRepoId(repoId);
	}

	public void printLabels(
			@NonNull final DeliveryOrder deliveryOrder,
			@NonNull final ITableRecordReference deliveryOrderRef,
			@NonNull final List<PackageLabels> packageLabels)
	{
		for (final PackageLabels packageLabel : packageLabels)
		{
			final PackageLabel defaultPackageLabel = packageLabel.getDefaultPackageLabel();
			printLabel(deliveryOrder, deliveryOrderRef, defaultPackageLabel);
		}
	}

	private void printLabel(
			final DeliveryOrder deliveryOrder,
			final ITableRecordReference deliveryOrderRef,
			final PackageLabel packageLabel)
	{
		final IArchiveStorageFactory archiveStorageFactory = Services.get(IArchiveStorageFactory.class);

		final String fileExtWithDot = MimeType.getExtensionByType(packageLabel.getContentType());
		final String fileName = CoalesceUtil.firstNotEmptyTrimmed(packageLabel.getFileName(), packageLabel.getType().toString()) + fileExtWithDot;
		final byte[] labelData = packageLabel.getLabelData();

		final Properties ctx = Env.getCtx();
		final IArchiveStorage archiveStorage = archiveStorageFactory.getArchiveStorage(ctx);
		final I_AD_Archive archive = InterfaceWrapperHelper.create(archiveStorage.newArchive(ctx, ITrx.TRXNAME_ThreadInherited), I_AD_Archive.class);

		archive.setAD_Table_ID(deliveryOrderRef.getAD_Table_ID());
		archive.setRecord_ID(deliveryOrderRef.getRecord_ID());
		archive.setC_BPartner_ID(deliveryOrder.getDeliveryAddress().getBpartnerId());
		// archive.setAD_Org_ID(); // TODO: do we need to orgId too?
		archive.setName(fileName);
		archiveStorage.setBinaryData(archive, labelData);
		archive.setIsReport(false);
		archive.setIsDirectEnqueue(true);
		archive.setIsDirectProcessQueueItem(true);

		archive.setC_Async_Batch_ID(AsyncBatchId.toRepoId(getAsyncBatchIdOrNull()));

		InterfaceWrapperHelper.save(archive);
	}
}
