package de.metas.procurement.webui.sync;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.metas.procurement.sync.protocol.SyncProductSupplyConfirm;
import de.metas.procurement.sync.protocol.SyncWeeklySupplyConfirm;
import de.metas.procurement.webui.model.SyncConfirm;
import de.metas.procurement.webui.repository.SyncConfirmRepository;

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
	@Autowired
	private SyncConfirmRepository syncConfirmRepo;

	@Transactional
	public void importProductSupplyConfirm(SyncProductSupplyConfirm syncProductSupplyConfirm)
	{
		final SyncConfirm confirm = new SyncConfirm();
		confirm.setEntry_type("product_supply");
		confirm.setEntry_uuid(syncProductSupplyConfirm.getProduct_supply_uuid());
		confirm.setServer_event_id(syncProductSupplyConfirm.getServer_event_id());
		confirm.setServerDateReceived(syncProductSupplyConfirm.getDateReceived());
		syncConfirmRepo.save(confirm);
	}

	@Transactional
	public void importWeeklySupplyConfirm(SyncWeeklySupplyConfirm syncWeeklySupplyConfirm)
	{
		final SyncConfirm confirm = new SyncConfirm();
		confirm.setEntry_type("week_supply");
		confirm.setEntry_uuid(syncWeeklySupplyConfirm.getWeek_supply_uuid());
		confirm.setServer_event_id(syncWeeklySupplyConfirm.getServer_event_id());
		confirm.setServerDateReceived(syncWeeklySupplyConfirm.getDateReceived());
		syncConfirmRepo.save(confirm);
	}

}
