package de.metas.printing.api.impl;

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
// NOPMD by ts on 20.03.13 07:58

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.impl.POJOQuery;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.comparator.ComparatorChain;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Archive;
import org.compiere.util.Env;

import de.metas.lock.api.ILockManager;
import de.metas.printing.api.IPrintPackageBL;
import de.metas.printing.api.IPrintingQueueQuery;
import de.metas.printing.model.I_AD_Printer;
import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_PrinterHW_Calibration;
import de.metas.printing.model.I_AD_PrinterHW_MediaSize;
import de.metas.printing.model.I_AD_PrinterHW_MediaTray;
import de.metas.printing.model.I_AD_PrinterTray_Matching;
import de.metas.printing.model.I_AD_Printer_Matching;
import de.metas.printing.model.I_AD_Printer_Tray;
import de.metas.printing.model.I_C_PrintPackageData;
import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.model.I_C_Print_Package;
import de.metas.printing.model.I_C_Print_PackageInfo;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.model.X_C_Print_Job_Instructions;

public class PlainPrintingDAO extends AbstractPrintingDAO
{
	private final POJOLookupMap lookupMap = POJOLookupMap.get();

	public POJOLookupMap getLookupMap()
	{
		return lookupMap;
	}

	public <T> T newInstance(final Properties ctx, final Class<T> clazz, final String trxName)
	{
		return lookupMap.newInstance(ctx, clazz);
	}

	public void save(final Object object)
	{
		lookupMap.save(object);
	}

	@Override
	protected Iterator<I_C_Print_Job_Line> retrievePrintJobLines0(final I_C_Print_Job job, final int fromSeqNo, final int toSeqNo)
	{
		final List<I_C_Print_Job_Line> result = lookupMap.getRecords(I_C_Print_Job_Line.class, pojo -> {
			if (pojo.getC_Print_Job_ID() != job.getC_Print_Job_ID())
			{
				return false;
			}
			if (fromSeqNo != SEQNO_First && pojo.getSeqNo() < fromSeqNo)
			{
				return false;
			}
			if (toSeqNo != SEQNO_Last && pojo.getSeqNo() > toSeqNo)
			{
				return false;
			}

			return true;
		});

		Collections.sort(result, (o1, o2) -> o1.getSeqNo() - o2.getSeqNo());

		return result.iterator();
	}

	@Override
	protected int resolveSeqNo(final I_C_Print_Job job, final int seqNo)
	{
		if (seqNo > 0)
		{
			return seqNo;
		}

		int min = -1;
		int max = -1;
		for (final I_C_Print_Job_Line line : IteratorUtils.asIterable(retrievePrintJobLines0(job, SEQNO_First, SEQNO_Last)))
		{
			if (min == -1 || min > line.getSeqNo())
			{
				min = line.getSeqNo();
			}
			if (max == -1 || max < line.getSeqNo())
			{
				max = line.getSeqNo();
			}
		}

		if (seqNo == SEQNO_First)
		{
			return min;
		}
		else if (seqNo == SEQNO_Last)
		{
			return max;
		}
		else
		{
			return seqNo;
		}
	}

	@Override
	public I_AD_PrinterTray_Matching retrievePrinterTrayMatchingOrNull(final I_AD_Printer_Matching matching, final int AD_Printer_Tray_ID)
	{
		return lookupMap.getFirstOnly(I_AD_PrinterTray_Matching.class, pojo -> pojo.getAD_Printer_Matching_ID() == matching.getAD_Printer_Matching_ID()
				&& pojo.getAD_Printer_Tray_ID() == AD_Printer_Tray_ID);
	}

	@Override
	public List<I_AD_PrinterTray_Matching> retrievePrinterTrayMatchings(final I_AD_Printer_Matching matching)
	{
		return lookupMap.getRecords(I_AD_PrinterTray_Matching.class, pojo -> {
			if (!Check.equals(pojo.getAD_Printer_Matching_ID(), matching.getAD_Printer_Matching_ID()))
			{
				return false;
			}

			return true;
		});
	}

	public I_C_PrintPackageData getPrintPackageData(final I_C_Print_Package printPackage)
	{
		return lookupMap.getFirstOnly(I_C_PrintPackageData.class, pojo -> pojo.getC_Print_Package_ID() == printPackage.getC_Print_Package_ID());
	}

	public List<I_C_Print_PackageInfo> retrievePrintPackageInfo(final I_C_Print_Package printPackage)
	{
		return lookupMap.getRecords(I_C_Print_PackageInfo.class, pojo -> pojo.getC_Print_Package_ID() == printPackage.getC_Print_Package_ID());
	}

	@Override
	public I_C_Print_Job_Instructions retrieveAndLockNextPrintJobInstructions(final Properties ctx, final String trxName)
	{
		final String hostKey = Services.get(IPrintPackageBL.class).getHostKeyOrNull(ctx);

		final List<I_C_Print_Job_Instructions> result = lookupMap.getRecords(I_C_Print_Job_Instructions.class, pojo -> {
			if (!X_C_Print_Job_Instructions.STATUS_Pending.equals(pojo.getStatus()))
			{
				return false;
			}
			if (pojo.getAD_User_ToPrint_ID() != Env.getAD_User_ID(ctx))
			{
				return false;
			}
			if (Services.get(ILockManager.class).isLocked(pojo))
			{
				return false;
			}
			if (!Check.isEmpty(hostKey, true))
			{
				return pojo.getHostKey() == null
						|| hostKey.equals(pojo.getHostKey());
			}
			return true;
		});

		if (result.isEmpty())
		{
			return null;
		}

		final I_C_Print_Job_Instructions instructions = result.get(0);
		InterfaceWrapperHelper.setTrxName(instructions, trxName);

		final boolean locked = Services.get(ILockManager.class).lock(instructions);
		if (!locked)
		{
			throw new AdempiereException("Cannot lock " + instructions);
		}

		return instructions;
	}

	@Override
	public IQuery<I_C_Printing_Queue> createQuery(final Properties ctx, final IPrintingQueueQuery queueQuery, final String trxName)
	{
		if (queueQuery.getOnlyAD_PInstance_ID() > 0)
		{
			throw new UnsupportedOperationException("Calling with AD_PInstance_ID > 0 not supported");
		}
		if (queueQuery.getFilter() != null)
		{
			throw new UnsupportedOperationException("Query Filter not supported for " + queueQuery);
		}
		if (queueQuery.getModelFilter() != null)
		{
			throw new UnsupportedOperationException("Query Model Filter not supported for " + queueQuery);
		}

		final IQueryFilter<I_C_Printing_Queue> filter = pojo -> {
			if (!pojo.isActive())
			{
				return false;
			}

			if (queueQuery.getIsPrinted() != null && queueQuery.getIsPrinted().booleanValue() != pojo.isProcessed())
			{
				return false;
			}
			if (queueQuery.getAD_Client_ID() >= 0 && queueQuery.getAD_Client_ID() != pojo.getAD_Client_ID())
			{
				return false;
			}
			if (queueQuery.getAD_Org_ID() >= 0 && queueQuery.getAD_Org_ID() != pojo.getAD_Org_ID())
			{
				return false;
			}
			if (queueQuery.getAD_User_ID() >= 0
					&& !InterfaceWrapperHelper.isNull(pojo, I_C_Printing_Queue.COLUMNNAME_AD_User_ID)
					&& queueQuery.getAD_User_ID() != pojo.getAD_User_ID())
			{
				return false;
			}
			if (queueQuery.getIgnoreC_Printing_Queue_ID() > 0
					&& queueQuery.getIgnoreC_Printing_Queue_ID() == pojo.getC_Printing_Queue_ID())
			{
				return false;
			}

			final I_AD_Archive archive = pojo.getAD_Archive();
			if (queueQuery.getModelTableId() > 0 && queueQuery.getModelTableId() != archive.getAD_Table_ID())
			{
				return false;
			}
			if (queueQuery.getModelFromRecordId() > 0 && queueQuery.getModelFromRecordId() > archive.getRecord_ID())
			{
				return false;
			}
			if (queueQuery.getModelToRecordId() > 0 && queueQuery.getModelToRecordId() < archive.getRecord_ID())
			{
				return false;
			}

			return true;
		};

		final IQueryOrderBy orderBy = Services.get(IQueryBL.class).createQueryOrderByBuilder(I_C_Printing_Queue.class)
				.addColumn(I_C_Printing_Queue.COLUMNNAME_C_Printing_Queue_ID)
				.createQueryOrderBy();

		final POJOQuery<I_C_Printing_Queue> query = new POJOQuery<>(ctx,
				I_C_Printing_Queue.class,
				null,  // tableName=null => get it from the given model class
				trxName)
						.addFilter(filter)
						.setOrderBy(orderBy);

		return query;
	}

	@Override
	public I_AD_PrinterHW_Calibration retrieveCalibration(final I_AD_PrinterHW_MediaSize hwMediaSize, final I_AD_PrinterHW_MediaTray hwTray)
	{
		return lookupMap.getFirstOnly(I_AD_PrinterHW_Calibration.class, pojo -> Check.equals(pojo.getAD_PrinterHW_MediaSize(), hwMediaSize) && Check.equals(pojo.getAD_PrinterHW_MediaTray(), hwTray));
	}

	@Override
	public List<I_AD_PrinterHW_MediaSize> retrieveMediaSizes(final I_AD_PrinterHW printerHW)
	{
		return lookupMap.getRecords(I_AD_PrinterHW_MediaSize.class, pojo -> pojo.getAD_PrinterHW_ID() == printerHW.getAD_PrinterHW_ID());
	}

	@Override
	public List<I_AD_PrinterHW_Calibration> retrieveCalibrations(final Properties ctx, final int printerID, final String trxName)
	{
		return lookupMap.getRecords(I_AD_PrinterHW_Calibration.class, pojo -> pojo.getAD_PrinterHW_ID() == printerID);
	}

	@Override
	public List<I_AD_PrinterHW_MediaTray> retrieveMediaTrays(final I_AD_PrinterHW printerHW)
	{
		return lookupMap.getRecords(I_AD_PrinterHW_MediaTray.class, pojo -> pojo.getAD_PrinterHW_ID() == printerHW.getAD_PrinterHW_ID());
	}

	@Override
	public void runWithTrxName(final String trxName, final Runnable runnable)
	{
		// nothing special on this level because we are not dealing with trx constraints
		runnable.run();
	}

	public I_C_Print_Job_Instructions retrievePrintJobInstructionsForPrintJob(final I_C_Print_Job printJob)
	{
		return lookupMap.getFirstOnly(I_C_Print_Job_Instructions.class, pojo -> pojo.getC_Print_Job_ID() == printJob.getC_Print_Job_ID());
	}

	@Override
	public List<I_AD_Printer> retrievePrinters(final Properties ctx, final int adOrgId)
	{
		final List<I_AD_Printer> result = lookupMap.getRecords(I_AD_Printer.class, pojo -> {
			if (!pojo.isActive())
			{
				return false;
			}

			return pojo.getAD_Org_ID() == 0 || pojo.getAD_Org_ID() == adOrgId;
		});

		final Comparator<I_AD_Printer> cmpOrg = (o1, o2) -> o1.getAD_Org_ID() - o2.getAD_Org_ID();
		final Comparator<I_AD_Printer> cmpPrinterName = (o1, o2) -> o1.getPrinterName().compareTo(o2.getPrinterName());

		Collections.sort(result, new ComparatorChain<I_AD_Printer>()
				.addComparator(cmpOrg, true)
				.addComparator(cmpPrinterName));

		return result;
	}


	@Override
	public List<I_AD_Printer_Tray> retrieveTrays(final I_AD_Printer printer)
	{
		return lookupMap.getRecords(I_AD_Printer_Tray.class, pojo -> {

			if (!pojo.isActive())
			{
				return false;
			}

			if (pojo.getAD_Printer_ID() != printer.getAD_Printer_ID())
			{
				return false;
			}
			return true;
		});
	}
}
