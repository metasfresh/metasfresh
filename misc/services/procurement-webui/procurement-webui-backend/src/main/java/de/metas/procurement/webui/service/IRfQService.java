package de.metas.procurement.webui.service;

/*
 * #%L
 * metasfresh-procurement-webui
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.Rfq;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.rest.rfq.JsonChangeRfqRequest;
import lombok.NonNull;

import javax.annotation.Nullable;
import javax.transaction.Transactional;
import java.util.List;

public interface IRfQService extends UserConfirmationHandler
{
	List<Rfq> getActiveRfqs(@Nullable User user);

	List<Rfq> getActiveRfqs(@Nullable BPartner bpartner);

	Rfq getUserActiveRfq(@NonNull User user, long rfqId);

	Rfq getRfqById(long rfq_id);

	@Nullable
	Rfq getRfqByUUID(@NonNull String rfq_uuid);

	void saveRecursively(@NonNull Rfq rfq);

	Rfq changeActiveRfq(final JsonChangeRfqRequest request, final User loggedUser);

	@Override
	long getCountUnconfirmed(User user);

	@Override
	void confirmUserEntries(User user);
}
