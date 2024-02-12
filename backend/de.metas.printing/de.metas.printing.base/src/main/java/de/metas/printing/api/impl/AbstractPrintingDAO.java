package de.metas.printing.api.impl;

import de.metas.logging.LogManager;
import de.metas.printing.HardwarePrinterId;
import de.metas.printing.LogicalPrinterId;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_AD_Print_Clients;
import de.metas.printing.model.I_AD_Printer;
import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_PrinterHW_Calibration;
import de.metas.printing.model.I_AD_PrinterHW_MediaSize;
import de.metas.printing.model.I_AD_PrinterHW_MediaTray;
import de.metas.printing.model.I_AD_PrinterRouting;
import de.metas.printing.model.I_AD_PrinterTray_Matching;
import de.metas.printing.model.I_AD_Printer_Config;
import de.metas.printing.model.I_AD_Printer_Matching;
import de.metas.printing.model.I_AD_Printer_Tray;
import de.metas.printing.model.I_C_PrintPackageData;
import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Print_Job_Detail;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.model.I_C_Print_Package;
import de.metas.printing.model.I_C_Print_PackageInfo;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.model.I_C_Printing_Queue_Recipient;
import de.metas.printing.model.X_AD_PrinterHW;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceId;
import de.metas.workplace.WorkplaceService;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Archive;
import org.slf4j.Logger;
import org.slf4j.MDC;

import javax.annotation.Nullable;
import javax.print.attribute.standard.MediaSize;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

public abstract class AbstractPrintingDAO implements IPrintingDAO
{
	private final static Logger logger = LogManager.getLogger(AbstractPrintingDAO.class);

	/**
	 * Flag used to indicate if a change on {@link I_C_Printing_Queue_Recipient} shall NOT automatically trigger an update to {@link I_C_Printing_Queue#setPrintingQueueAggregationKey(String)}
	 */
	private static final ModelDynAttributeAccessor<I_C_Printing_Queue_Recipient, Boolean> DYNATTR_DisableAggregationKeyUpdate = new ModelDynAttributeAccessor<>("DisableAggregationKeyUpdate", Boolean.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public final Iterator<I_C_Print_Job_Line> retrievePrintJobLines(final I_C_Print_Job job)
	{
		return retrievePrintJobLines(job, SEQNO_First, SEQNO_Last);
	}

	@Override
	public final Iterator<I_C_Print_Job_Line> retrievePrintJobLines(final I_C_Print_Job job, final int fromSeqNo, final int toSeqNo)
	{
		Check.assume(fromSeqNo == SEQNO_First || fromSeqNo > 0, "Valid fromSeqNo: {}", fromSeqNo);
		Check.assume(toSeqNo == SEQNO_Last || fromSeqNo > 0, "Valid toSeqNo: {}", toSeqNo);
		if (fromSeqNo != SEQNO_First && toSeqNo != SEQNO_Last)
		{
			Check.assume(fromSeqNo <= toSeqNo, "fromSeqNo({}) < toSeqNo({})", fromSeqNo, toSeqNo);
		}
		return retrievePrintJobLines0(job, fromSeqNo, toSeqNo);
	}

	protected abstract Iterator<I_C_Print_Job_Line> retrievePrintJobLines0(final I_C_Print_Job job, final int fromSeqNo, final int toSeqNo);

	@Override
	public final I_C_Print_Job_Line retrievePrintJobLine(final I_C_Print_Job job, final int seqNo)
	{
		final int seqNoReal = resolveSeqNo(job, seqNo);
		Check.assume(seqNoReal > 0, "seqNo > 0");

		final Iterator<I_C_Print_Job_Line> lines = retrievePrintJobLines0(job, seqNoReal, seqNoReal);
		if (!lines.hasNext())
		{
			throw new AdempiereException("@NotFound@ @C_Print_Job_Line_ID@ (@SeqNo@:" + seqNoReal + ")");
		}

		final I_C_Print_Job_Line line = lines.next();
		if (lines.hasNext())
		{
			// shall not happen
			throw new AdempiereException("More then one line found for seqNo=" + seqNoReal + ": " + lines);
		}

		return line;
	}

	protected abstract int resolveSeqNo(final I_C_Print_Job job, final int seqNo);

	@Override
	public final Iterator<I_C_Print_Job_Line> retrievePrintJobLines(final I_C_Print_Job_Instructions jobInstructions)
	{
		final I_C_Print_Job job = jobInstructions.getC_Print_Job();
		final int fromSeqNo = jobInstructions.getC_PrintJob_Line_From().getSeqNo();
		final int toSeqNo = jobInstructions.getC_PrintJob_Line_To().getSeqNo();

		return retrievePrintJobLines(job, fromSeqNo, toSeqNo);
	}

	@Override
	public final List<I_C_Print_Job_Detail> retrievePrintJobDetails(final I_C_Print_Job_Line jobLine)
	{
		final List<I_C_Print_Job_Detail> details = retrievePrintJobDetailsIfAny(jobLine);

		Check.errorIf(details.isEmpty(), "Couldn't retrieve print job details for {}", jobLine);

		return details;
	}

	@Override
	public final List<I_C_Print_Job_Detail> retrievePrintJobDetailsIfAny(final I_C_Print_Job_Line jobLine)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_C_Print_Job_Detail> queryBuilder = queryBL.createQueryBuilder(I_C_Print_Job_Detail.class, jobLine)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Print_Job_Detail.COLUMNNAME_C_Print_Job_Line_ID, jobLine.getC_Print_Job_Line_ID());

		queryBuilder.orderBy()
				.addColumn(I_C_Print_Job_Detail.COLUMNNAME_C_Print_Job_Detail_ID);

		return queryBuilder
				.create()
				.list(I_C_Print_Job_Detail.class);
	}

	@Override
	public final void removeMediaSizes(final List<I_AD_PrinterHW_MediaSize> sizes)
	{
		for (final I_AD_PrinterHW_MediaSize si : sizes)
		{
			InterfaceWrapperHelper.delete(si);
		}
	}

	@Override
	public final void removeCalibrations(final List<I_AD_PrinterHW_Calibration> calibrations)
	{
		for (final I_AD_PrinterHW_Calibration cal : calibrations)
		{
			InterfaceWrapperHelper.delete(cal);
		}
	}

	@Override
	public final void removeMediaTrays(final List<I_AD_PrinterHW_MediaTray> trays)
	{
		for (final I_AD_PrinterHW_MediaTray tr : trays)
		{
			InterfaceWrapperHelper.delete(tr);
		}
	}

	@Nullable
	@Override
	public final I_AD_Printer_Config retrievePrinterConfig(@Nullable final String hostKey, @Nullable final UserId userToPrintId)
	{
		final WorkplaceService workplaceService = SpringContextHolder.instance.getBean(WorkplaceService.class);
		final WorkplaceId workplaceId = (userToPrintId == null) ? null : workplaceService.getWorkplaceByUserId(userToPrintId)
				.map(Workplace::getId)
				.orElse(null);
		
		return retrievePrinterConfig(hostKey, userToPrintId, workplaceId);
	}

	@Nullable
	@Override
	public final I_AD_Printer_Config retrievePrinterConfig(@Nullable final String hostKey, @Nullable final UserId userToPrintId, @Nullable final WorkplaceId workplaceId)
	{
		try (final MDC.MDCCloseable ignore = MDC.putCloseable("hostKey", hostKey);
				final MDC.MDCCloseable ignore2 = MDC.putCloseable("userToPrintId", Integer.toString(UserId.toRepoId(userToPrintId))))
		{
			final IQueryBuilder<I_AD_Printer_Config> queryBuilder = queryBL
					.createQueryBuilderOutOfTrx(I_AD_Printer_Config.class)
					.addOnlyActiveRecordsFilter();
			if (Check.isNotBlank(hostKey))
			{
				logger.debug("retrievePrinterConfig - will filter by ConfigHostKey IN ({}, NULL)", hostKey);
				queryBuilder.addInArrayFilter(I_AD_Printer_Config.COLUMN_ConfigHostKey, hostKey, null); // allow "less specific" configs without hostkey
			}
			else if (workplaceId == null)
			{
				Check.errorIf(userToPrintId == null, "If the 'hostKey' param is empty, then the 'userToPrintId has to be > 0");
			}
			queryBuilder.orderBy(I_AD_Printer_Config.COLUMNNAME_ConfigHostKey); // prefer records with hostkey set

			if (workplaceId != null)
			{
				queryBuilder.addEqualsFilter(I_AD_Printer_Config.COLUMNNAME_C_Workplace_ID, workplaceId);
			}
			else
			{
				if (userToPrintId != null)
				{
					logger.debug("retrievePrinterConfig - will filter by AD_User_PrinterMatchingConfig_ID={}", UserId.toRepoId(userToPrintId));
					queryBuilder.addEqualsFilter(I_AD_Printer_Config.COLUMNNAME_AD_User_PrinterMatchingConfig_ID, userToPrintId);
				}
				else
				{
				logger.debug("retrievePrinterConfig - hostKey={} - userToPrintId is null -> order by AD_User_PrinterMatchingConfig_ID to prefer records with user set", hostKey);
					Check.errorIf(Check.isBlank(hostKey), "If the 'userToPrintId' param is empty, then the 'hostKey has to be not-blank");
					queryBuilder.orderBy(I_AD_Printer_Config.COLUMNNAME_AD_User_PrinterMatchingConfig_ID); // prefer records with userId set
				}
			}

			return queryBuilder
					.orderBy(I_AD_Printer_Config.COLUMNNAME_ConfigHostKey)
					.create()
					.first();
		}
	}

	@Override
	public final I_AD_Printer_Matching retrievePrinterMatching(final String hostKey, final UserId userToPrintId, final I_AD_PrinterRouting routing)
	{
		final I_AD_Printer_Matching matching = retrievePrinterMatchingOrNull(hostKey, userToPrintId, InterfaceWrapperHelper.create(routing.getAD_Printer(), I_AD_Printer.class));
		if (matching == null)
		{
			throw new AdempiereException("Couldn't retrieve printer matching").appendParametersToMessage()
					.setParameter("hostKey", hostKey)
					.setParameter("AD_User_PrinterMatchingConfig_ID", userToPrintId == null ? "<null>" : UserId.toRepoId(userToPrintId))
					.setParameter("AD_PrinterRouting", routing);
		}

		return matching;
	}

	@Nullable
	@Override
	public final I_AD_Printer_Matching retrievePrinterMatchingOrNull(
			@Nullable final String hostKey,
			@Nullable final UserId userToPrintId,
			@NonNull final de.metas.adempiere.model.I_AD_Printer printer)
	{
		final I_AD_Printer_Config printerConfigRecord = retrievePrinterConfig(hostKey, userToPrintId);
		if (printerConfigRecord == null)
		{
			return null;
		}
		return queryBL.createQueryBuilder(I_AD_Printer_Matching.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Printer_Matching.COLUMNNAME_AD_Printer_Config_ID, printerConfigRecord.getAD_Printer_Config_ID())
				.addEqualsFilter(I_AD_Printer_Matching.COLUMN_AD_Printer_ID, printer.getAD_Printer_ID())
				.create()
				.firstOnly(I_AD_Printer_Matching.class);
	}

	@Override
	public final List<I_AD_Printer_Matching> retrievePrinterMatchings(final I_AD_PrinterHW printerHW)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Printer_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Printer_Config.COLUMN_ConfigHostKey, printerHW.getHostKey())
				.andCollectChildren(I_AD_Printer_Matching.COLUMN_AD_Printer_Config_ID)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Printer_Matching.COLUMNNAME_AD_PrinterHW_ID, printerHW.getAD_PrinterHW_ID())
				.orderBy()
				.addColumnAscending(I_AD_Printer_Matching.COLUMNNAME_AD_Printer_Matching_ID).endOrderBy()
				.create()
				.list();
	}

	@Nullable
	@Override
	public final I_AD_PrinterTray_Matching retrievePrinterTrayMatching(
			final I_AD_Printer_Matching printerMatchingRecord, final I_AD_PrinterRouting routing,
			final boolean throwExIfMissing)
	{
		final I_AD_PrinterTray_Matching trayMatching = retrievePrinterTrayMatchingOrNull(printerMatchingRecord, routing.getAD_Printer_Tray_ID());
		if (trayMatching == null && throwExIfMissing)
		{
			throw new AdempiereException("Couldn't retrieve printer tray matching for printer " + routing.getAD_Printer_ID());
		}

		return trayMatching;
	}


	@Nullable
	@Override
	public final I_AD_PrinterTray_Matching retrievePrinterTrayMatchingOrNull(
			@NonNull final I_AD_Printer_Matching matching,
			final int AD_Printer_Tray_ID)
	{
		return queryBL.createQueryBuilder(I_AD_PrinterTray_Matching.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_PrinterTray_Matching.COLUMNNAME_AD_Printer_Matching_ID, matching.getAD_Printer_Matching_ID())
				.addEqualsFilter(I_AD_PrinterTray_Matching.COLUMNNAME_AD_Printer_Tray_ID, AD_Printer_Tray_ID)
				.create()
				.firstOnly(I_AD_PrinterTray_Matching.class);
	}

	@Override
	public final List<UserId> retrievePrintingQueueRecipientIDs(@NonNull final I_C_Printing_Queue printingQueue)
	{
		final List<Map<String, Object>> listDistinct = retrievePrintingQueueRecipientsQuery(printingQueue)
				.addOnlyActiveRecordsFilter()
				.create()
				.listDistinct(I_C_Printing_Queue_Recipient.COLUMNNAME_AD_User_ToPrint_ID);
		final List<UserId> result = new ArrayList<>();
		for (final Map<String, Object> distinct : listDistinct)
		{
			final UserId userId = UserId.ofRepoIdOrNull((Integer)distinct.get(I_C_Printing_Queue_Recipient.COLUMNNAME_AD_User_ToPrint_ID));
			if (userId != null)
			{
				result.add(userId);
			}
		}
		return result;
	}

	private IQueryBuilder<I_C_Printing_Queue_Recipient> retrievePrintingQueueRecipientsQuery(final I_C_Printing_Queue item)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_C_Printing_Queue_Recipient.class, item)
				// .addOnlyActiveRecordsFilter() // we must retrieve ALL; the caller shall decide
				.addEqualsFilter(I_C_Printing_Queue_Recipient.COLUMN_C_Printing_Queue_ID, item.getC_Printing_Queue_ID())
				//
				.orderBy()
				.addColumn(I_C_Printing_Queue_Recipient.COLUMNNAME_C_Printing_Queue_Recipient_ID)
				.endOrderBy();

	}

	@Override
	public final List<I_C_Printing_Queue_Recipient> retrieveAllPrintingQueueRecipients(final I_C_Printing_Queue item)
	{
		return retrievePrintingQueueRecipientsQuery(item)
				.create()
				.list(I_C_Printing_Queue_Recipient.class);
	}

	@Override
	public final void deletePrintingQueueRecipients(final I_C_Printing_Queue item)
	{
		for (final I_C_Printing_Queue_Recipient recipient : retrieveAllPrintingQueueRecipients(item))
		{
			setDisableAggregationKeyUpdate(recipient);
			InterfaceWrapperHelper.delete(recipient);
		}
	}

	@Override
	public final boolean isUpdatePrintingQueueAggregationKey(final I_C_Printing_Queue_Recipient recipient)
	{
		final Boolean disabled = DYNATTR_DisableAggregationKeyUpdate.getValue(recipient);
		return disabled == null || !disabled;
	}

	@Override
	public final void setDisableAggregationKeyUpdate(final I_C_Printing_Queue_Recipient recipient)
	{
		DYNATTR_DisableAggregationKeyUpdate.setValue(recipient, true);
	}

	@Nullable
	@Override
	public final I_AD_Print_Clients retrievePrintClientsEntry(final Properties ctx, final String hostKey)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Print_Clients.class, ctx, ITrx.TRXNAME_None)
				// .addOnlyContextClient(ctx) // task 08022: AD_Print_Clients always have AD_Client_ID=0
				.addEqualsFilter(I_AD_Print_Clients.COLUMNNAME_HostKey, hostKey)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_AD_Print_Clients.class);
	}

	@Nullable
	@Override
	public final I_AD_PrinterHW_MediaSize retrieveMediaSize(
			@NonNull final I_AD_PrinterHW hwPrinter,
			@NonNull final MediaSize mediaSize,
			final boolean createIfNotExists)
	{
		final String mediaSizeName = mediaSize.getMediaSizeName().toString();
		final I_AD_PrinterHW_MediaSize result = Services.get(IQueryBL.class).createQueryBuilder(I_AD_PrinterHW_MediaSize.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_PrinterHW_MediaSize.COLUMN_AD_PrinterHW_ID, hwPrinter.getAD_PrinterHW_ID())
				.addEqualsFilter(I_AD_PrinterHW_MediaSize.COLUMN_Name, mediaSizeName)
				.create()
				.firstOnly(I_AD_PrinterHW_MediaSize.class);

		if (result == null && createIfNotExists)
		{
			// task 08458
			final I_AD_PrinterHW_MediaSize newInstance = InterfaceWrapperHelper.newInstance(I_AD_PrinterHW_MediaSize.class, hwPrinter);
			newInstance.setAD_PrinterHW(hwPrinter);
			newInstance.setName(mediaSizeName);
			InterfaceWrapperHelper.save(newInstance);

			return newInstance;
		}

		Check.errorUnless(result != null, "Missing AD_PrinterHW_MediaSize with name {} for printer {}",
						  mediaSizeName,
						  hwPrinter.getName());
		return result;
	}

	@Override
	public final I_C_Printing_Queue retrievePrintingQueue(@NonNull final I_AD_Archive archive)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_C_Printing_Queue.class, archive)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Printing_Queue.COLUMN_AD_Archive_ID, archive.getAD_Archive_ID())
				.orderBy()
				.addColumnDescending(I_C_Printing_Queue.COLUMNNAME_Created)
				.addColumnDescending(I_C_Printing_Queue.COLUMNNAME_C_Printing_Queue_ID) // also order by ID in case created is not unique
				.endOrderBy()
				.create()
				.first();
	}

	@Override
	public final List<I_C_Print_PackageInfo> retrievePrintPackageInfos(@NonNull final I_C_Print_Package printPackage)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Print_PackageInfo.class, printPackage)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Print_PackageInfo.COLUMN_C_Print_Package_ID, printPackage.getC_Print_Package_ID())
				.orderBy().addColumn(I_C_Print_PackageInfo.COLUMNNAME_AD_PrinterHW_ID).endOrderBy()
				.create()
				.list();
	}

	@Override
	public final I_C_PrintPackageData retrievePrintPackageData(@NonNull final I_C_Print_Package printPackage)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_PrintPackageData.class, printPackage)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PrintPackageData.COLUMN_C_Print_Package_ID, printPackage.getC_Print_Package_ID())
				.orderBy()
				.addColumn(I_C_PrintPackageData.COLUMNNAME_Created)
				.addColumn(I_C_PrintPackageData.COLUMNNAME_C_PrintPackageData_ID)
				.endOrderBy()
				.create()
				.first(); // note: right now IDK why it's first an not firstOnly
	}

	@Nullable
	@Override
	public final I_AD_PrinterHW retrieveAttachToPrintPackagePrinter(final Properties ctx, final String hostkey, final String trxName)
	{
		final IQuery<I_AD_Printer_Config> queryConfig = queryBL.createQueryBuilder(I_AD_Printer_Config.class)
				.addEqualsFilter(I_AD_Printer_Config.COLUMN_ConfigHostKey, hostkey)
				.create();

		final IQuery<I_AD_Printer_Matching> queryMatchings = queryBL.createQueryBuilder(I_AD_Printer_Matching.class)
				.addInSubQueryFilter(I_AD_Printer_Matching.COLUMNNAME_AD_Printer_Config_ID, I_AD_Printer_Config.COLUMNNAME_AD_Printer_Config_ID, queryConfig)
				.create();

		return queryBL.createQueryBuilder(I_AD_PrinterHW.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_PrinterHW.COLUMNNAME_OutputType, X_AD_PrinterHW.OUTPUTTYPE_Attach)
				.addInArrayFilter(I_AD_PrinterHW.COLUMN_HostKey, hostkey, null)
				.addInSubQueryFilter(I_AD_Printer_Matching.COLUMNNAME_AD_PrinterHW_ID, I_AD_PrinterHW.COLUMNNAME_AD_PrinterHW_ID, queryMatchings)
				.orderBy()
				.addColumnDescending(I_AD_PrinterHW.COLUMNNAME_HostKey).endOrderBy()
				.create()
				.first(I_AD_PrinterHW.class);
	}

	@Override
	public List<I_AD_Printer_Tray> retrieveTrays(final LogicalPrinterId printerId)
	{
		return queryBL.createQueryBuilder(I_AD_Printer_Tray.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Printer_Tray.COLUMNNAME_AD_Printer_ID, printerId)
				.create()
				.list();
	}

	@Override
	public I_AD_PrinterHW retrieveHardwarePrinter(@NonNull final HardwarePrinterId hardwarePrinterId)
	{
		return InterfaceWrapperHelper.loadOutOfTrx(hardwarePrinterId, I_AD_PrinterHW.class);
	}

	@Override
	public List<I_AD_PrinterHW_MediaTray> retrieveMediaTrays(@NonNull final HardwarePrinterId hardwarePrinterId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_PrinterHW_MediaTray.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_PrinterHW_MediaTray.COLUMNNAME_AD_PrinterHW_ID, hardwarePrinterId)
				.create()
				.list();
	}
}
