package de.metas.procurement.webui.sync;

import de.metas.common.procurement.sync.protocol.dto.SyncUser;
import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
public class SyncUserImportService extends AbstractSyncImportService
{
	@Autowired
	@Lazy
	private UserRepository usersRepo;

	public void importUsers(final BPartner bpartner, final List<SyncUser> syncUsers)
	{
		final Map<String, User> users = mapByUuid(usersRepo.findByBpartner(bpartner));
		final List<User> usersToSave = new ArrayList<>();
		for (final SyncUser syncUser : syncUsers)
		{
			if (syncUser.isDeleted())
			{
				continue;
			}

			User user = users.remove(syncUser.getUuid());
			user = importUserNoSave(bpartner, syncUser, user);
			if (user != null)
			{
				usersToSave.add(user);
			}
		}
		//
		// Delete remaining users
		for (final User user : users.values())
		{
			deleteUser(user);
		}
		//
		// Save users
		usersRepo.save(usersToSave);
	}

	private User importUserNoSave(final BPartner bpartner, final SyncUser syncUser, User user)
	{
		final String uuid = syncUser.getUuid();
		if (user != null && !Objects.equals(uuid, user.getUuid()))
		{
			user = null;
		}
		if (user == null)
		{
			user = new User();
			user.setUuid(uuid);
			user.setBpartner(bpartner);
		}

		user.markNotDeleted();
		user.setEmail(syncUser.getEmail().trim());
		// only sync the PW if not already set, e.g. by the forgot-password feature
		if (user.getPassword() == null || user.getPassword().trim().isEmpty())
		{
			user.setPassword(syncUser.getPassword());
		}
		user.setLanguage(syncUser.getLanguage());
		// usersRepo.save(user); // don't save it
		logger.debug("Imported: {} -> {}", syncUser, user);

		return user;
	}

	private void deleteUser(final User user)
	{
		if (user.isDeleted())
		{
			return;
		}
		user.markDeleted();
		usersRepo.save(user);

		logger.debug("Deleted: {}", user);
	}

}
