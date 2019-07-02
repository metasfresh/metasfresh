package de.metas.bpartner.composite;

import static de.metas.util.Check.isEmpty;

import javax.annotation.Nullable;

import org.adempiere.ad.table.RecordChangeLog;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Language;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.rest.ExternalId;
import lombok.Builder;
import lombok.Data;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
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

@Data
public class BPartner
{
	/** May be null if the bpartner was not yet saved. */
	private BPartnerId id;

	private ExternalId externalId;

	private String value;

	private String name;

	/** non-empty value implies that the bpartner is also a company */
	private String companyName;

	/** This translates to `C_BPartner.BPartner_Parent_ID`. It's a this bpartner's central/parent company. */
	private BPartnerId parentId;

	/** This translates to `C_BPartner.Phone2`. It's this bpartner's central phone number. */
	private String phone;

	private Language language;

	private String url;

	private BPGroupId groupId;

	private final RecordChangeLog changeLog;

	@Builder(toBuilder = true)
	private BPartner(
			@Nullable final BPartnerId id,
			@Nullable final ExternalId externalId,
			@Nullable final String value,
			@Nullable final String name,
			@Nullable final String companyName,
			@Nullable final BPartnerId parentId,
			@Nullable final String phone,
			@Nullable final Language language,
			@Nullable final String url,
			@Nullable final BPGroupId groupId,
			@Nullable final RecordChangeLog changeLog)
	{
		this.id = id;
		this.externalId = externalId;
		this.value = value;
		this.name = name;
		this.companyName = companyName;
		this.parentId = parentId;
		this.phone = phone;
		this.language = language;
		this.url = url;
		this.groupId = groupId;

		this.changeLog = changeLog;
	}

	/** empty list means valid */
	public ImmutableList<ITranslatableString> validate()
	{
		final ImmutableList.Builder<ITranslatableString> result = ImmutableList.builder();
		if (isEmpty(value, true))
		{
			result.add(TranslatableStrings.constant("bpartner.value"));
		}
		if (isEmpty(name, true))
		{
			result.add(TranslatableStrings.constant("bpartner.name"));
		}
		if (groupId == null)
		{
			result.add(TranslatableStrings.constant("bpartner.groupId"));
		}
		return result.build();
	}
}
