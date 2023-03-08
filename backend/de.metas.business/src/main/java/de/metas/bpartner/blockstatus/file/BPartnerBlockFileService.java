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

package de.metas.bpartner.blockstatus.file;

import de.metas.bpartner.impexp.blockstatus.impl.IBPartnerBlockStatusDAO;
import de.metas.process.PInstanceId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class BPartnerBlockFileService
{
	@NonNull
	private final BPartnerBlockFileRepository bPartnerBlockFileRepository;

	@NonNull
	private final IBPartnerBlockStatusDAO ibPartnerBlockStatusDAO;

	public BPartnerBlockFileService(
			@NonNull final BPartnerBlockFileRepository bPartnerBlockFileRepository,
			@NonNull final IBPartnerBlockStatusDAO ibPartnerBlockStatusDAO)
	{
		this.bPartnerBlockFileRepository = bPartnerBlockFileRepository;
		this.ibPartnerBlockStatusDAO = ibPartnerBlockStatusDAO;
	}

	@NonNull
	public BPartnerBlockStatusFile getById(@NonNull final BPartnerBlockFileId id)
	{
		return bPartnerBlockFileRepository.getById(id);
	}

	@NonNull
	public BPartnerBlockStatusFile save(@NonNull final BPartnerBlockStatusFile file)
	{
		return bPartnerBlockFileRepository.save(file);
	}

	@NonNull
	public PInstanceId getUnprocessedRowsSelectionId(@NonNull final BPartnerBlockFileId bPartnerBlockFileId)
	{
		return ibPartnerBlockStatusDAO.getUnprocessedRowsSelectionId(bPartnerBlockFileId);
	}

	public boolean isImported(@NonNull final BPartnerBlockFileId fileId)
	{
		return ibPartnerBlockStatusDAO.existRowsFor(fileId);
	}

	public boolean hasUnprocessedRows(@NonNull final BPartnerBlockFileId fileId)
	{
		return ibPartnerBlockStatusDAO.existUnprocessedRowsFor(fileId);
	}
}
