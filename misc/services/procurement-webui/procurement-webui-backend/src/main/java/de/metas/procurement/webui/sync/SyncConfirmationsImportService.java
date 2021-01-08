package de.metas.procurement.webui.sync;

import de.metas.common.procurement.sync.protocol.dto.SyncConfirmation;
import de.metas.procurement.webui.model.SyncConfirm;
import de.metas.procurement.webui.repository.SyncConfirmRepository;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/*
 * #%L
 * de.metas.procurement.webui
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Service
@Transactional
public class SyncConfirmationsImportService extends AbstractSyncImportService
{
	private static final transient Logger logger = LoggerFactory.getLogger(SyncConfirmationsImportService.class);

	@Autowired
	private SyncConfirmRepository syncConfirmRepo;

	/**
	 * Loads and updates the {@link SyncConfirm} record that is identified by the given <code>syncConfirmation</code>'s {@link SyncConfirmation#getConfirmId()}.
	 */
	@Transactional
	public void importConfirmation(@NonNull final SyncConfirmation syncConfirmation)
	{
		SyncConfirm confirmRecord = null;
		if (syncConfirmation.getConfirmId() > 0)
		{
			confirmRecord = syncConfirmRepo.getOne(syncConfirmation.getConfirmId());
		}

		if (confirmRecord == null)
		{
			// something is actually wrong. Either the given syncConfirm has no ID, or there is no record with this ID.
			// Since the whole syncConfirm thing is about stability and diagnosibility, we shall now try to make the best of the situation.
			confirmRecord = new SyncConfirm();
			confirmRecord.setEntryType("UNKNOWN ID " + syncConfirmation.getConfirmId());
			logger.error("Found no record for {}", syncConfirmation);

		}

		confirmRecord.setServerEventId(syncConfirmation.getServerEventId());
		confirmRecord.setDateConfirmed(syncConfirmation.getDateConfirmed());
		confirmRecord.setDateConfirmReceived(new Date());

		syncConfirmRepo.save(confirmRecord);
	}
}
