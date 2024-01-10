package de.metas.printing.api;

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

import de.metas.printing.HardwarePrinterId;
import de.metas.printing.LogicalPrinterId;
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
import de.metas.user.UserId;
import de.metas.util.ISingletonService;
import de.metas.workplace.WorkplaceId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Archive;

import javax.annotation.Nullable;
import javax.print.attribute.standard.MediaSize;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public interface IPrintingDAO extends ISingletonService
{
	/**
	 * {@link I_C_Print_Job_Line}'s first SeqNo place holder
	 */
	int SEQNO_First = -200;
	/**
	 * {@link I_C_Print_Job_Line}'s last SeqNo place holder
	 */
	int SEQNO_Last = -100;

	/**
	 * @see #retrievePrintJobLines(I_C_Print_Job, int, int)
	 */
	Iterator<I_C_Print_Job_Line> retrievePrintJobLines(I_C_Print_Job job);

	/**
	 * Retrieve {@link I_C_Print_Job_Line}s, ordered by SeqNo.
	 *
	 * @param fromSeqNo first seqno inclusive (or -1)
	 * @param toSeqNo last seqno inclusive (or -1)
	 */
	Iterator<I_C_Print_Job_Line> retrievePrintJobLines(I_C_Print_Job job, int fromSeqNo, int toSeqNo);

	Iterator<I_C_Print_Job_Line> retrievePrintJobLines(I_C_Print_Job_Instructions jobInstructions);

	I_C_Print_Job_Line retrievePrintJobLine(I_C_Print_Job job, int seqNo);

	/**
	 * Retrieve the print job details for the given job line. Assumes that there is at least one, never returns an empty list.
	 */
	List<I_C_Print_Job_Detail> retrievePrintJobDetails(I_C_Print_Job_Line jobLine);

	/**
	 * Retrieve the print job details for the given job line. If there are none, an empty list will be returned.
	 */
	List<I_C_Print_Job_Detail> retrievePrintJobDetailsIfAny(I_C_Print_Job_Line jobLine);

	/**
	 * @param printerMatchingRecord The "parent record" of the tray printerMatchingRecord that we are looking for
	 * @param routing the printer routing record that contains the logical printer and tray for which we search the hardware tray.
	 * @param throwExIfNull if <code>true</code> and there is no tray printerMatchingRecord, the method will throw an {@link AdempiereException}
	 * @return the tray printerMatchingRecord or <code>null</code> if there is none and <code>throwExIfNull</code> was <code>false</code>.
	 */
	I_AD_PrinterTray_Matching retrievePrinterTrayMatching(I_AD_Printer_Matching printerMatchingRecord, I_AD_PrinterRouting routing, boolean throwExIfNull);

	I_AD_PrinterTray_Matching retrievePrinterTrayMatchingOrNull(I_AD_Printer_Matching matching, int AD_Printer_Tray_ID);

	List<I_AD_PrinterTray_Matching> retrievePrinterTrayMatchings(I_AD_Printer_Matching matching);

	/**
	 * Retrieves a printer config by host key or user ID. One of both has to be set.
	 * If no printer config exists, the method returns <code>null</code>.
	 *
	 * @param hostKey the hostKey to retrieve the config for. May be <code>null</code> or empty string.
	 * @param userToPrintId if the given <code>hostKey</code> is <code>null</code> or empty then this parameter must be <code>> 0</code>. Used to retrieve the config by its
	 *            {@link I_AD_Printer_Config#COLUMNNAME_CreatedBy CreatedBy} value
	 */
	I_AD_Printer_Config retrievePrinterConfig(String hostKey, UserId userToPrintId);

	I_AD_Printer_Config retrievePrinterConfig(String hostKey, UserId userToPrintId, WorkplaceId workplaceId);

	/**
	 * @return empty list if the given queue item has no recipients or if {@link I_C_Printing_Queue#COLUMN_IsPrintoutForOtherUser} <code>='N'</code>.
	 */
	List<UserId> retrievePrintingQueueRecipientIDs(I_C_Printing_Queue item);

	/**
	 * Delete all existing recipients of given item.
	 * <p/>
	 * NOTE: this method will prevent updating the item's aggregation key.
	 */
	void deletePrintingQueueRecipients(I_C_Printing_Queue item);

	/** Retrieve all recipients */
	List<I_C_Printing_Queue_Recipient> retrieveAllPrintingQueueRecipients(I_C_Printing_Queue item);

	/** @return true if the {@link I_C_Printing_Queue}'s AggregationKey shall not be updated automatically for given recipient; it is assumed that a BL will take care of it */
	boolean isUpdatePrintingQueueAggregationKey(I_C_Printing_Queue_Recipient recipient);

	/**
	 * Ensure that the aggregation key of the given {@code recipients} printing queue item won't be updated via model interceptor when the given recipient is saved.
	 */
	void setDisableAggregationKeyUpdate(I_C_Printing_Queue_Recipient recipient);

	/**
	 * Retrieve next {@link I_C_Print_Job_Instructions} available for processing. Following context values are considered:
	 * <ul>
	 * <li>logged/session user; only those instructions for logged user are fetched
	 * <li>hostkey; if available only those instructions for hostkey are fetched or those whom does not have a hostkey set
	 * </ul>
	 *
	 * NOTE: this method locks the returned object
	 */
	I_C_Print_Job_Instructions retrieveAndLockNextPrintJobInstructions(Properties ctx, String trxName);

	/**
	 * Retrieve all (active and inactive) {@link I_AD_PrinterHW_MediaSize}.
	 */
	List<I_AD_PrinterHW_MediaSize> retrieveMediaSizes(I_AD_PrinterHW printerHW);

	/**
	 * @param createIfNotExists if <code>true</code> then the record is created on the fly if necessary. Background is that currently we don't handle different mediasizes anyways. We just need a
	 *            record for A4 to be present. So if an exotic printer does not have it, just create it on the fly and make no fuss about it. See task 08458
	 */
	I_AD_PrinterHW_MediaSize retrieveMediaSize(I_AD_PrinterHW hwPrinter, MediaSize mediaSize, boolean createIfNotExists);

	void removeMediaSizes(List<I_AD_PrinterHW_MediaSize> sizes);

	I_AD_PrinterHW_Calibration retrieveCalibration(I_AD_PrinterHW_MediaSize hwMediaSize, I_AD_PrinterHW_MediaTray hwTray);

	List<I_AD_PrinterHW_Calibration> retrieveCalibrations(Properties ctx, int printerID, String trxName);

	void removeCalibrations(List<I_AD_PrinterHW_Calibration> calibrations);

	I_AD_PrinterHW retrieveHardwarePrinter(@NonNull HardwarePrinterId hardwarePrinterId);

	List<I_AD_PrinterHW_MediaTray> retrieveMediaTrays(@NonNull HardwarePrinterId hardwarePrinterId);

	void removeMediaTrays(List<I_AD_PrinterHW_MediaTray> trays);

	/**
	 * Converts {@link IPrintingQueueQuery} to current database {@link IQuery} implementation.
	 *
	 * @return database's {@link IQuery}
	 */
	IQuery<I_C_Printing_Queue> createQuery(Properties ctx, IPrintingQueueQuery queueQuery, @Nullable String trxName);

	/**
	 * Returns all logical printers for the given org.
	 * <p>
	 * <b>IMPORTANT:</b> the printers are returned with internal TrxName=<code>null</code>!
	 */
	List<I_AD_Printer> retrievePrinters(Properties ctx, int adOrgId);

	List<I_AD_Printer_Matching> retrievePrinterMatchings(I_AD_PrinterHW printerHW);

	List<I_AD_Printer_Tray> retrieveTrays(LogicalPrinterId logicalPrinterId);

	/**
	 * @return {@link I_AD_Printer_Matching}; never returns null
	 */
	I_AD_Printer_Matching retrievePrinterMatching(String hostKey, UserId userToPrintId, I_AD_PrinterRouting routing);

	I_AD_Printer_Matching retrievePrinterMatchingOrNull(@Nullable String hostKey, @Nullable UserId userToPrintId, @NonNull de.metas.adempiere.model.I_AD_Printer printer);

	I_AD_Print_Clients retrievePrintClientsEntry(Properties ctx, String hostKey);

	/**
	 * Retrieve <b>the latest</b> printing queue for the given {@code archive}.
	 */
	I_C_Printing_Queue retrievePrintingQueue(I_AD_Archive archive);

	/**
	 * Get a list of printing package infos
	 */
	List<I_C_Print_PackageInfo> retrievePrintPackageInfos(I_C_Print_Package printPackage);

	/**
	 * retrieve package data
	 */
	I_C_PrintPackageData retrievePrintPackageData(I_C_Print_Package printPackage);

	/**
	 * retrieves a printer which has the output type PDF
	 * <ul> virtual printer because is not a real hardware printer
	 */
	I_AD_PrinterHW retrieveAttachToPrintPackagePrinter(final Properties ctx, String hostkey, final String trxName);
}
