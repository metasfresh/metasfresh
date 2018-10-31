package de.metas.inbound.mail;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.inbound.mail.config.InboundEMailConfig;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.inbound.mail
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

@Service
public class InboundEMailService
{
	private final InboundEMailRepository emailRepo;
	private final CompositeInboundEMailListener listeners;

	public InboundEMailService(
			@NonNull final InboundEMailRepository emailRepo,
			@NonNull final Optional<List<InboundEMailListener>> listeners)
	{
		this.emailRepo = emailRepo;
		this.listeners = CompositeInboundEMailListener.of(listeners.orElseGet(ImmutableList::of));
	}

	public void onInboundEMailReceived(@NonNull final InboundEMailConfig config, @NonNull final InboundEMail email)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		try (final IAutoCloseable c = switchContext(config))
		{
			trxManager.runInThreadInheritedTrx(() -> onInboundEMailReceivedInTrx(config, email));
		}
	}

	private void onInboundEMailReceivedInTrx(@NonNull final InboundEMailConfig config, @NonNull final InboundEMail email)
	{
		final InboundEMail emailEffective = listeners.onInboundEMailReceived(config, email);
		emailRepo.save(emailEffective);
	}

	private static IAutoCloseable switchContext(@NonNull final InboundEMailConfig config)
	{
		final Properties ctx = Env.newTemporaryCtx();
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, config.getAdClientId().getRepoId());
		Env.setContext(ctx, Env.CTXNAME_AD_Org_ID, config.getOrgId().getRepoId());

		return Env.switchContext(ctx);
	}
}
