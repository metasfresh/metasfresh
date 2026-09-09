/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.ui.web.handlingunits.process;

import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.empties.IHUEmptiesService;
import de.metas.handlingunits.inout.ReceiptCorrectHUsProcessor;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.handlingunits.report.HUReceiptScheduleReportExecutor;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.NullDocumentChangesCollector;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOut;
import org.compiere.model.MImage;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * What a receipt-schedule action DOES, separated from which window it was started on, so the body exists once
 * and both the {@code WEBUI_M_ReceiptSchedule_*} process and its
 * {@code WEBUI_RV_ReceiptDisposition_DeliveryPlanning_*} adapter call it.
 * <p>
 * The adapters cannot be avoided: for a view row the platform resolves a process' record through
 * {@code IView#getTableRecordReferenceOrNull}, which on the new window yields
 * {@code RV_ReceiptDisposition_DeliveryPlanning}, while every {@code WEBUI_M_ReceiptSchedule_*} class asks for
 * its record as {@code M_ReceiptSchedule} - and {@code JavaProcess#getRecord} is {@code protected final}.
 */
@Service
@RequiredArgsConstructor
public class ReceiptScheduleActions
{
	@NonNull private final IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	@NonNull private final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);
	@NonNull private final IHUEmptiesService huEmptiesService = Services.get(IHUEmptiesService.class);
	@NonNull private final DocumentCollection documentsRepo;

	// ---------------------------------------------------------------------------------------------
	// "Foto"
	// ---------------------------------------------------------------------------------------------

	/**
	 * Attaches the given {@code AD_Image} to the receipt schedule, as "Foto" does on the receipt-schedule window.
	 */
	public void attachPhoto(
			@NonNull final Properties ctx,
			@NonNull final I_M_ReceiptSchedule receiptSchedule,
			final int adImageId)
	{
		final MImage adImage = MImage.get(ctx, adImageId);
		if (adImage == null || adImage.getAD_Image_ID() <= 0)
		{
			throw new EntityNotFoundException("@NotFound@ @AD_Image_ID@: " + adImageId);
		}

		final String name = adImage.getName();
		final byte[] data = adImage.getData();
		final BufferedImage image;
		try
		{
			image = ImageIO.read(new ByteArrayInputStream(data));
		}
		catch (final IOException ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}

		huReceiptScheduleBL.attachPhoto(receiptSchedule, name, image);
	}

	// ---------------------------------------------------------------------------------------------
	// "Drucken Produktanlieferung"
	// ---------------------------------------------------------------------------------------------

	/** Runs the material-receipt Jasper for the receipt schedule. */
	public void runMaterialReceiptJasper(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		HUReceiptScheduleReportExecutor
				.get(receiptSchedule)
				.executeHUReport();
	}

	// ---------------------------------------------------------------------------------------------
	// "Korrektur"
	// ---------------------------------------------------------------------------------------------

	public ProcessPreconditionsResolution checkHUsToReverseApplicable(@Nullable final I_M_ReceiptSchedule receiptSchedule)
	{
		if (receiptSchedule == null)
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (receiptScheduleBL.isClosed(receiptSchedule))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("already closed");
		}
		// Receipt schedule shall not be about packing materials
		if (receiptSchedule.isPackagingMaterial())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not applying for packing materials");
		}
		if (receiptSchedule.getQtyMoved().signum() <= 0)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no receipts to be reversed");
		}

		return ProcessPreconditionsResolution.accept();
	}

	/**
	 * Never empty - a selection that reaches this far and yields no HU is a hard error, as on the receipt-schedule window.
	 */
	public List<I_M_HU> getHUsToReverse(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		final List<I_M_HU> hus = ReceiptCorrectHUsProcessor.builder()
				.setM_ReceiptSchedule(receiptSchedule)
				.build()
				.getAvailableHUsToReverse();

		if (hus.isEmpty())
		{
			throw new AdempiereException("@NotFound@ @M_HU_ID@");
		}

		return hus;
	}

	// ---------------------------------------------------------------------------------------------
	// "Leergut Ausgabe" / "Leergut Rücknahme"
	// ---------------------------------------------------------------------------------------------

	/**
	 * Created FROM the receipt schedule when one is selected, or an empty draft when nothing is - both windows
	 * offer the action with no selection.
	 *
	 * @return the {@code M_InOut_ID} to open, or {@code -1} when there is nothing to open.
	 */
	public int createEmptiesReturns(
			@NonNull final Properties ctx,
			@Nullable final I_M_ReceiptSchedule receiptSchedule,
			@NonNull final String returnMovementType,
			@NonNull final AdWindowId targetWindowId)
	{
		if (receiptSchedule == null)
		{
			return createDraftEmptiesDocument(ctx, returnMovementType, targetWindowId);
		}

		final I_M_InOut emptiesInOut = huEmptiesService.createDraftEmptiesInOutFromReceiptSchedule(receiptSchedule, returnMovementType);
		return emptiesInOut == null ? -1 : emptiesInOut.getM_InOut_ID();
	}

	private int createDraftEmptiesDocument(
			@NonNull final Properties ctx,
			@NonNull final String returnMovementType,
			@NonNull final AdWindowId targetWindowId)
	{
		final DocumentPath documentPath = DocumentPath.builder()
				.setDocumentType(WindowId.of(targetWindowId))
				.setDocumentId(DocumentId.NEW_ID_STRING)
				.allowNewDocumentId()
				.build();

		final DocumentId documentId = documentsRepo.forDocumentWritable(documentPath, NullDocumentChangesCollector.instance, document -> {
			huEmptiesService.newReturnsInOutProducer(ctx)
					.setMovementType(returnMovementType)
					.setMovementDate(SystemTime.asDayTimestamp())
					.fillReturnsInOutHeader(InterfaceWrapperHelper.create(document, I_M_InOut.class));
			return document.getDocumentId();
		});

		return documentId.toInt();
	}
}
