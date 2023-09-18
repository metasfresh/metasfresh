package de.metas.acct.accounts;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.IAccountDAO;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.ValidCombinationQuery;
import de.metas.elementvalue.ElementValueService;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.event.impl.PlainEventBusFactory;
import de.metas.organization.IOrgDAO;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.Adempiere;
import org.compiere.model.I_C_SubAcct;
import org.compiere.model.I_C_ValidCombination;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ValidCombinationService
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IAccountDAO accountDAO = Services.get(IAccountDAO.class);
	private final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final ElementValueService elementValueService;
	private final IEventBusFactory eventBusFactory;

	private static final Topic EVENTBUS_TOPIC = Topic.local("de.metas.acct.accounts.ValidCombinationService.events");
	private static final String DYNATTR_AvoidUpdateDescriptionOnSave = "AvoidUpdateDescriptionOnSave";

	@PostConstruct
	public void postConstruct()
	{
		eventBusFactory.getEventBus(EVENTBUS_TOPIC).subscribeOn(ValidCombinationUpdateEvent.class, this::updateValidCombinationDescriptionNow);
	}

	public static ValidCombinationService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new ValidCombinationService(ElementValueService.newInstanceForUnitTesting(), PlainEventBusFactory.newInstance());
	}

	public void scheduleUpdateDescriptionAfterCommit(final ValidCombinationQuery query)
	{
		scheduleUpdateDescriptionAfterCommit(ImmutableSet.of(query));
	}

	public void scheduleUpdateDescriptionAfterCommit(final Collection<ValidCombinationQuery> queries)
	{
		trxManager.accumulateAndProcessAfterCommit(
				ValidCombinationService.class.getSimpleName() + "#scheduleUpdateDescriptionAfterCommit",
				ImmutableSet.copyOf(queries),
				this::postUpdateValidCombinationDescriptionEvent
		);
	}

	public void updateValueDescription(final I_C_ValidCombination account)
	{
		newValidCombinationDescriptionUpdater().update(account);
	}

	public static boolean isAvoidUpdatingValueDescriptionOnSave(final I_C_ValidCombination account)
	{
		return StringUtils.toBoolean(InterfaceWrapperHelper.getDynAttribute(account, DYNATTR_AvoidUpdateDescriptionOnSave));
	}

	private ValidCombinationDescriptionUpdater newValidCombinationDescriptionUpdater()
	{
		return new ValidCombinationDescriptionUpdater(acctSchemaDAO, orgDAO, elementValueService);
	}

	private void postUpdateValidCombinationDescriptionEvent(final List<ValidCombinationQuery> queriesList)
	{
		if (queriesList.isEmpty())
		{
			return;
		}

		eventBusFactory.getEventBus(EVENTBUS_TOPIC)
				.enqueueObject(ValidCombinationUpdateEvent.builder()
						.multiQuery(ImmutableSet.copyOf(queriesList))
						.build());
	}

	private void updateValidCombinationDescriptionNow(final ValidCombinationUpdateEvent event)
	{
		final Set<ValidCombinationQuery> multiQuery = event.getMultiQuery();
		if (multiQuery.isEmpty())
		{
			return;
		}

		final ValidCombinationDescriptionUpdater updater = newValidCombinationDescriptionUpdater();

		accountDAO.getByMultiQuery(multiQuery)
				.forEach(account -> {
					updater.update(account);
					try (IAutoCloseable ignored = InterfaceWrapperHelper.temporarySetDynAttribute(account, DYNATTR_AvoidUpdateDescriptionOnSave, true))
					{
						accountDAO.save(account);
					}
				});
	}

	public void updateValidCombinationDescriptionNow(final ValidCombinationQuery query)
	{
		updateValidCombinationDescriptionNow(ValidCombinationUpdateEvent.builder()
				.multiQuery(ImmutableSet.of(query))
				.build());
	}

	public void validate(@NonNull final I_C_ValidCombination account)
	{
		// Validate Sub-Account
		if (account.getC_SubAcct_ID() > 0)
		{
			I_C_SubAcct sa = account.getC_SubAcct();
			if (sa != null && sa.getC_ElementValue_ID() != account.getAccount_ID())
			{
				throw new AdempiereException("C_SubAcct.C_ElementValue_ID=" + sa.getC_ElementValue_ID() + "<>Account_ID=" + account.getAccount_ID());
			}
		}
	}

	public void createIfMissing(@NonNull final AccountDimension dimension)
	{
		accountDAO.getOrCreateAccountId(dimension);
	}

}
