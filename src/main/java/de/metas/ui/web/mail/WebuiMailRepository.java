package de.metas.ui.web.mail;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.UnaryOperator;

import org.compiere.util.DisplayType;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.springframework.stereotype.Component;

import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider.LookupScope;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class WebuiMailRepository
{
	private final AtomicInteger nextEmailId = new AtomicInteger(1);
	private final ConcurrentHashMap<String, WebuiEmail> emailsById = new ConcurrentHashMap<>();

	private final LookupDataSource emailToLookup;

	public WebuiMailRepository()
	{
		final LookupDescriptor emailToLookupDescriptor = SqlLookupDescriptor.builder()
				.setColumnName(org.compiere.model.I_AD_User.COLUMNNAME_AD_User_ID)
				.setDisplayType(DisplayType.Search)
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.buildProvider()
				.provideForScope(LookupScope.DocumentField);

		emailToLookup = LookupDataSourceFactory.instance.getLookupDataSource(emailToLookupDescriptor);
	}

	public WebuiEmail createNewEmail(final LookupValue from)
	{
		final String emailId = String.valueOf(nextEmailId.getAndIncrement());
		final WebuiEmail email = WebuiEmail.builder()
				.emailId(emailId)
				.from(from)
				.build();

		emailsById.put(emailId, email);
		return email;
	}

	public WebuiEmail getEmail(final String emailId)
	{
		final WebuiEmail email = emailsById.get(emailId);
		if (email == null)
		{
			throw new EntityNotFoundException("Email not found");
		}
		return email;
	}

	public WebuiEmail changeEmail(final String emailId, final UnaryOperator<WebuiEmail> emailModifier)
	{
		return emailsById.compute(emailId, (id, emailOld) -> emailModifier.apply(emailOld));
	}

	public WebuiEmail changeEmailAndRemove(final String emailId, final UnaryOperator<WebuiEmail> emailModifier)
	{
		final WebuiEmail email = emailsById.compute(emailId, (id, emailOld) -> emailModifier.apply(emailOld));
		emailsById.remove(emailId);
		return email;
	}

	public LookupValuesList getToTypeahead(String emailId_NOTUSED, String query)
	{
		final Evaluatee ctx = Evaluatees.empty(); // TODO
		return emailToLookup.findEntities(ctx, query);
	}

}
