package de.metas.acct.posting.server;

import de.metas.Profiles;
import de.metas.acct.api.DocumentPostMultiRequest;
import de.metas.acct.api.DocumentPostRequest;
import de.metas.acct.api.IPostingService;
import de.metas.acct.posting.DocumentPostRequestHandler;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
@Profile(Profiles.PROFILE_AccountingService)
public class AccountingService implements DocumentPostRequestHandler
{
	private static final Logger logger = LogManager.getLogger(AccountingService.class);
	private final IPostingService postingService = Services.get(IPostingService.class);

	@Override
	public void handleRequest(@NonNull final DocumentPostRequest request)
	{
		logger.debug("Posting: {}", request);
		postingService.postAfterCommit(DocumentPostMultiRequest.of(request));
	}
}
