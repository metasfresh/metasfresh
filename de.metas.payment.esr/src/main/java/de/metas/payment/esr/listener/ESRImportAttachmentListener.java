/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.payment.esr.listener;

import ch.qos.logback.classic.Level;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.listener.AttachmentListener;
import de.metas.logging.LogManager;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.api.RunESRImportRequest;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.util.Loggables;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;

import static de.metas.payment.esr.ESRConstants.ESR_ASYNC_BATCH_DESC;
import static de.metas.payment.esr.ESRConstants.ESR_ASYNC_BATCH_NAME;

public class ESRImportAttachmentListener implements AttachmentListener
{
	private static final Logger logger = LogManager.getLogger(ESRImportAttachmentListener.class);

	private final transient IESRImportBL esrImportBL = Services.get(IESRImportBL.class);

	@Override
	public void afterPersist(final AttachmentEntry attachmentEntry,
							final TableRecordReference tableRecordReference)
	{
		final I_ESR_Import esrImport = InterfaceWrapperHelper.load(tableRecordReference.getRecord_ID(), I_ESR_Import.class);

		final RunESRImportRequest runESRImportRequest = RunESRImportRequest.builder()
				.esrImport(esrImport)
				.attachmentEntryId(attachmentEntry.getId())
				.asyncBatchDescription(ESR_ASYNC_BATCH_DESC)
				.asyncBatchName(ESR_ASYNC_BATCH_NAME)
				.loggable(Loggables.withLogger(logger, Level.DEBUG))
				.build();

		esrImportBL.scheduleESRImportFor(runESRImportRequest);
	}
}
