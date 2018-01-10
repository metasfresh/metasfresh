package de.metas.shipper.gateway.go.async;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.util.Env;
import org.compiere.util.MimeType;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.printing.model.I_AD_Archive;
import de.metas.shipper.gateway.api.model.DeliveryOrder;
import de.metas.shipper.gateway.api.model.PackageLabel;
import de.metas.shipper.gateway.api.model.PackageLabels;
import de.metas.shipper.gateway.go.GOClient;
import de.metas.shipper.gateway.go.GOClientFactory;
import de.metas.shipper.gateway.go.GODeliveryOrderRepository;

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

public class GODeliveryOrderWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	public static void enqueueOnTrxCommit(final int deliveryOrderRepoId)
	{
		Check.assume(deliveryOrderRepoId > 0, "deliveryOrderRepoId > 0");
		Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(GODeliveryOrderWorkpackageProcessor.class)
				.newBlock()
				.newWorkpackage()
				.setUserInChargeId(Env.getAD_User_ID())
				.bindToThreadInheritedTrx()
				.parameters()
				.setParameter(PARAM_DeliveryOrderRepoId, deliveryOrderRepoId)
				.end()
				.build();
	}

	private static final String PARAM_DeliveryOrderRepoId = "DeliveryOrderRepoId";

	// Services
	private final IArchiveStorageFactory archiveStorageFactory = Services.get(IArchiveStorageFactory.class);
	private final GODeliveryOrderRepository deliveryOrderRepo;
	private final GOClientFactory goClientFactory;

	public GODeliveryOrderWorkpackageProcessor()
	{
		deliveryOrderRepo = Adempiere.getBean(GODeliveryOrderRepository.class);
		goClientFactory = Adempiere.getBean(GOClientFactory.class);
	}

	@Override
	public boolean isRunInTransaction()
	{
		return false;
	}

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage_NOTUSED, final String localTrxName_NOTUSED)
	{
		DeliveryOrder deliveryOrder = retrieveDeliveryOrder();

		final GOClient goClient = goClientFactory.newGOClientForShipperId(deliveryOrder.getShipperId());

		deliveryOrder = goClient.completeDeliveryOrder(deliveryOrder);
		deliveryOrderRepo.save(deliveryOrder);

		final List<PackageLabels> packageLabelsList = goClient.getPackageLabelsList(deliveryOrder);
		printLabels(deliveryOrder, packageLabelsList);

		return Result.SUCCESS;
	}

	private void printLabels(DeliveryOrder deliveryOrder, List<PackageLabels> packageLabels)
	{
		packageLabels.stream()
				.map(PackageLabels::getDefaultPackageLabel)
				.forEach(packageLabel -> printLabel(deliveryOrder, packageLabel));
	}

	private void printLabel(DeliveryOrder deliveryOrder, PackageLabel packageLabel)
	{
		final String fileExtWithDot = MimeType.getExtensionByType(packageLabel.getContentType());
		final String fileName = packageLabel.getType().toString() + fileExtWithDot;
		final byte[] labelData = packageLabel.getLabelData();

		final Properties ctx = Env.getCtx();
		final IArchiveStorage archiveStorage = archiveStorageFactory.getArchiveStorage(ctx);
		final I_AD_Archive archive = InterfaceWrapperHelper.create(archiveStorage.newArchive(ctx, ITrx.TRXNAME_ThreadInherited), I_AD_Archive.class);

		final TableRecordReference deliveryOrderRef = deliveryOrderRepo.toTableRecordReference(deliveryOrder);
		archive.setAD_Table_ID(deliveryOrderRef.getAD_Table_ID());
		archive.setRecord_ID(deliveryOrderRef.getRecord_ID());
		archive.setC_BPartner_ID(deliveryOrder.getDeliveryAddress().getBpartnerId());
		// archive.setAD_Org_ID(); // TODO: do we need to orgId too?
		archive.setName(fileName);
		archiveStorage.setBinaryData(archive, labelData);
		archive.setIsReport(false);
		archive.setIsDirectEnqueue(true);
		archive.setIsCreatePrintJob(true);
		InterfaceWrapperHelper.save(archive);
	}

	private DeliveryOrder retrieveDeliveryOrder()
	{
		final int deliveryOrderRepoId = getDeliveryOrderRepoId();
		return deliveryOrderRepo.getByRepoId(deliveryOrderRepoId);
	}

	public int getDeliveryOrderRepoId()
	{
		return getParameters().getParameterAsInt(PARAM_DeliveryOrderRepoId);
	}
}
