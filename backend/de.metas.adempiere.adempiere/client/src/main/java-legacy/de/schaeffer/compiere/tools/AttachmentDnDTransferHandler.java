package de.schaeffer.compiere.tools;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.List;

import javax.swing.TransferHandler;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.apps.ADialog;
import org.compiere.apps.APanel;
import org.compiere.model.DataStatusEvent;
import org.compiere.model.GridTab;
import org.compiere.util.DisplayType;
import org.slf4j.Logger;

import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.attachments.AttachmentEntryService;
import de.metas.logging.LogManager;

public class AttachmentDnDTransferHandler extends TransferHandler
{

	/**
	 *
	 */
	private static final long serialVersionUID = -4482233564492129396L;

	/** Logger */
	private final Logger log = LogManager.getLogger(getClass());

	// private final GridTab gridTab;
	private final APanel panel;

	public AttachmentDnDTransferHandler(APanel panel)
	{
		this.panel = panel;
	}

	public GridTab getGridTab()
	{
		return panel.getCurrentTab();
	}

	public int getTableId()
	{
		GridTab gridTab = getGridTab();
		return gridTab == null ? -1 : gridTab.getAD_Table_ID();
	}

	public int getRecordId()
	{
		GridTab gridTab = getGridTab();
		return gridTab == null ? -1 : gridTab.getRecord_ID();
	}

	@Override
	public boolean canImport(TransferHandler.TransferSupport support)
	{
		final GridTab gridTab = getGridTab();
		if (gridTab == null)
			return false;

		// if (!support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)
		// && !support.isDataFlavorSupported(DataFlavor.getTextPlainUnicodeFlavor())) {
		// return false;
		// }
		return true;
	}

	@Override
	public boolean importData(TransferHandler.TransferSupport support)
	{
		final GridTab gridTab = getGridTab();
		if (gridTab == null)
		{
			return false;
		}
		if (!canImport(support))
		{
			return false;
		}

		Transferable t = support.getTransferable();

		DataFlavor[] flavors = t.getTransferDataFlavors();
		final TableRecordReference tableRecordReference =TableRecordReference.of(getTableId(), getRecordId());

		final AttachmentEntryService attachmentEntryService = Adempiere.getBean(AttachmentEntryService.class);

		for (int i = 0; i < flavors.length; i++)
		{
			DataFlavor flavor = flavors[i];
			try
			{
				if (flavor.equals(DataFlavor.javaFileListFlavor))
				{
					@SuppressWarnings("unchecked")
					List<File> files = (List<File>)t.getTransferData(DataFlavor.javaFileListFlavor);

					attachmentEntryService.createNewAttachments(tableRecordReference, AttachmentEntryCreateRequest.fromFiles(files));
				}
				else if (flavor.getMimeType().startsWith("text"))
				{
					final Object data = t.getTransferData(DataFlavor.stringFlavor);
					if (data == null)
						continue;
					final String text = data.toString();
					final DateFormat df = DisplayType.getDateFormat(DisplayType.DateTime);
					final String name = "Text " + df.format(new Timestamp(System.currentTimeMillis()));

					attachmentEntryService.createNewAttachment(tableRecordReference, name, text.getBytes(StandardCharsets.UTF_8));
				}
			}
			catch (Exception ex)
			{
				log.error(ex.getLocalizedMessage(), ex);
				ADialog.error(gridTab.getWindowNo(), null, "Error", ex.getLocalizedMessage());
				return false;
			}
		}
		// inform APanel/.. -> dataStatus with row updated
		gridTab.loadAttachments();

		final DataStatusEvent dataStatusEvent = DataStatusEvent.builder()
				.source(gridTab)
				.totalRows(gridTab.getRowCount())
				.changed(false)
				.autoSave(true)
				.inserting(false)
				.build();
		dataStatusEvent.setCurrentRow(gridTab.getCurrentRow());
		final String status = dataStatusEvent.getAD_Message();
		if (status == null || status.length() == 0)
			dataStatusEvent.setInfo("NavigateOrUpdate", null, false, false);
		gridTab.fireDataStatusChanged(dataStatusEvent);
		return true;
	}
}
