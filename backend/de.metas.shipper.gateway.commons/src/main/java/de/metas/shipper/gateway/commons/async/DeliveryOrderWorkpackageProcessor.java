package de.metas.shipper.gateway.commons.async;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.Adempiere;
import org.compiere.util.Env;
import org.compiere.util.MimeType;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.printing.model.I_AD_Archive;
import de.metas.shipper.gateway.commons.ShipperGatewayServicesRegistry;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipper.gateway.spi.DeliveryOrderRepository;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.PackageLabel;
import de.metas.shipper.gateway.spi.model.PackageLabels;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.CoalesceUtil;
import lombok.NonNull;

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
			final int deliveryOrderRepoId,
			@NonNull final String shipperGatewayId)
	{
		Check.assume(deliveryOrderRepoId > 0, "deliveryOrderRepoId > 0");

		Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(DeliveryOrderWorkpackageProcessor.class)
				.newBlock()
				.newWorkpackage()
				.setUserInChargeId(Env.getLoggedUserIdIfExists().orElse(null))
				.bindToThreadInheritedTrx()
				.parameters()
				.setParameter(PARAM_DeliveryOrderRepoId, deliveryOrderRepoId)
				.setParameter(PARAM_ShipperGatewayId, shipperGatewayId)
				.end()
				.build();
	}

	private static final String PARAM_DeliveryOrderRepoId = "DeliveryOrderRepoId";
	private static final String PARAM_ShipperGatewayId = "ShipperGatewayId";
	// Services

	// private final GOClientFactory goClientFactory;

	private final ShipperGatewayServicesRegistry shipperRegistry;

	public DeliveryOrderWorkpackageProcessor()
	{
		shipperRegistry = Adempiere.getBean(ShipperGatewayServicesRegistry.class);
	}

	@Override
	public boolean isRunInTransaction()
	{
		return false;
	}

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage_NOTUSED, final String localTrxName_NOTUSED)
	{
		final DeliveryOrder draftedDeliveryOrder = retrieveDeliveryOrder();

		final String shipperGatewayId = getParameters().getParameterAsString(PARAM_ShipperGatewayId);

		final ShipperGatewayClient client = shipperRegistry
				.getClientFactory(shipperGatewayId)
				.newClientForShipperId(draftedDeliveryOrder.getShipperId());

		final DeliveryOrderRepository deliveryOrderRepo = //
				shipperRegistry.getDeliveryOrderRepository(shipperGatewayId);

		final DeliveryOrder completedDeliveryOrder = client.completeDeliveryOrder(draftedDeliveryOrder);
		deliveryOrderRepo.save(completedDeliveryOrder);

		final List<PackageLabels> packageLabelsList = client.getPackageLabelsList(completedDeliveryOrder);
		printLabels(completedDeliveryOrder, packageLabelsList, deliveryOrderRepo);

		return Result.SUCCESS;
	}

	private DeliveryOrder retrieveDeliveryOrder()
	{
		final String shipperGatewayId = getParameters().getParameterAsString(PARAM_ShipperGatewayId);
		final DeliveryOrderRepository deliveryOrderRepo = shipperRegistry.getDeliveryOrderRepository(shipperGatewayId);

		final DeliveryOrderId deliveryOrderRepoId = getDeliveryOrderRepoId();
		return deliveryOrderRepo.getByRepoId(deliveryOrderRepoId);
	}

	public DeliveryOrderId getDeliveryOrderRepoId()
	{
		final int repoId = getParameters().getParameterAsInt(PARAM_DeliveryOrderRepoId, -1);
		return DeliveryOrderId.ofRepoId(repoId);
	}

	public void printLabels(
			@NonNull final DeliveryOrder deliveryOrder,
			@NonNull final List<PackageLabels> packageLabels,
			final DeliveryOrderRepository deliveryOrderRepo)
	{
		packageLabels.stream()
				.map(PackageLabels::getDefaultPackageLabel)
				.forEach(packageLabel -> printLabel(deliveryOrder, packageLabel, deliveryOrderRepo));
	}

	private void printLabel(
			final DeliveryOrder deliveryOrder,
			final PackageLabel packageLabel,
			final DeliveryOrderRepository deliveryOrderRepo)
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
		InterfaceWrapperHelper.save(archive);
	}
}
