/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.bpartner.impexp.blockstatus.impl;

import de.metas.bpartner.blockstatus.file.BPartnerBlockFileId;
import de.metas.i18n.AdMessageKey;
import de.metas.impexp.format.ImportTableDescriptor;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_I_BPartner_BlockStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class IBPartnerBlockStatusDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final static AdMessageKey MSG_NO_UNPROCESSED_ROWS_FOUND = AdMessageKey.of("de.metas.bpartner.impexp.blockstatus.impl.NoUnprocessedRowsFound");

	@NonNull
	public PInstanceId getUnprocessedRowsSelectionId(@NonNull final BPartnerBlockFileId bPartnerBlockFileId)
	{
		return Optional.ofNullable(getUnprocessedActiveRowsQuery(bPartnerBlockFileId).createSelection())
				.orElseThrow(() -> new AdempiereException(MSG_NO_UNPROCESSED_ROWS_FOUND, bPartnerBlockFileId));
	}

	public boolean existUnprocessedRowsFor(@NonNull final BPartnerBlockFileId fileId)
	{
		return getUnprocessedActiveRowsQuery(fileId).firstId() > 0;
	}

	public boolean existRowsFor(@NonNull final BPartnerBlockFileId fileId)
	{
		return queryBL.createQueryBuilder(I_I_BPartner_BlockStatus.class)
				.addEqualsFilter(I_I_BPartner_BlockStatus.COLUMNNAME_C_BPartner_Block_File_ID, fileId)
				.orderBy(I_I_BPartner_BlockStatus.COLUMNNAME_I_BPartner_BlockStatus_ID)
				.create()
				.firstId() > 0;
	}

	/**
	 * Workaround to make the PO framework aware of the updated/inserted failed records and propagate the changes to UI.
	 * <p>
	 * Related to {@see de.metas.bpartner.impexp.blockstatus.BPartnerBlockStatusImportProcess#updateAndValidateImportRecords()}
	 */
	public void forceAcknowledgementForFailedRows(@NonNull final PInstanceId selectionId)
	{
		queryBL.createQueryBuilder(I_I_BPartner_BlockStatus.class)
				.addEqualsFilter(I_I_BPartner_BlockStatus.COLUMNNAME_I_IsImported, "E")
				.setOnlySelection(selectionId)
				.stream()
				.forEach(ibPartnerBlockStatusRecord -> {
					ibPartnerBlockStatusRecord.setI_ErrorMsg(ibPartnerBlockStatusRecord.getI_ErrorMsg() + "!");
					InterfaceWrapperHelper.save(ibPartnerBlockStatusRecord);
				});
	}

	@NonNull
	private IQuery<I_I_BPartner_BlockStatus> getUnprocessedActiveRowsQuery(@NonNull final BPartnerBlockFileId fileId)
	{
		return queryBL.createQueryBuilder(I_I_BPartner_BlockStatus.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_I_BPartner_BlockStatus.COLUMNNAME_C_BPartner_Block_File_ID, fileId)
				.addEqualsFilter(ImportTableDescriptor.COLUMNNAME_Processed, false)
				.orderBy(I_I_BPartner_BlockStatus.COLUMNNAME_I_BPartner_BlockStatus_ID)
				.create();
	}
}