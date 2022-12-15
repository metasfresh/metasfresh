/*
 * #%L
 * marketing-base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.marketing.base;

import com.google.common.collect.ImmutableList;
import de.metas.marketing.base.model.ContactPerson;
import de.metas.marketing.base.model.DataRecord;
import de.metas.marketing.base.model.LocalToRemoteSyncResult;
import de.metas.marketing.base.model.PlatformId;
import de.metas.marketing.base.model.RemoteToLocalSyncResult;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.util.email.EmailValidator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class SyncUtil
{
	/**
	 * Note: if something is sorted out by this method, then that's probably some bug.
	 */
	@NonNull
	public <T extends DataRecord> List<T> filterForRecordsWithCorrectPlatformId(
			@NonNull final PlatformId platformId,
			@NonNull final List<T> records,
			@NonNull final ImmutableList.Builder<LocalToRemoteSyncResult> resultToAddErrorsTo)
	{
		final String errorMessage = StringUtils.formatMessage("Data record's platformId={} does not match this client's platFormId={}", platformId);

		final Map<Boolean, List<T>> okAndNotOkDataRecords = records
				.stream()
				.collect(Collectors.partitioningBy(record -> PlatformId.equals(record.getPlatformId(), platformId)));

		okAndNotOkDataRecords.get(false)
				.stream()
				.map(record -> LocalToRemoteSyncResult.error(record, errorMessage))
				.forEach(resultToAddErrorsTo::add);

		return okAndNotOkDataRecords.get(true);
	}

	@NonNull
	public List<ContactPerson> filterForPersonsWithEmailOrRemoteId(
			@NonNull final List<ContactPerson> contactPersons,
			@NonNull final ImmutableList.Builder<RemoteToLocalSyncResult> syncResultsToAddErrorsTo)
	{
		final Map<Boolean, List<ContactPerson>> personsWithAndWithoutEmailOrRemoteId = contactPersons
				.stream()
				.collect(Collectors.partitioningBy(ContactPerson::hasEmailAddress));

		personsWithAndWithoutEmailOrRemoteId.get(false)
				.stream()
				.map(contactPerson -> RemoteToLocalSyncResult.error(contactPerson, "contact person has no email address"))
				.forEach(syncResultsToAddErrorsTo::add);

		return personsWithAndWithoutEmailOrRemoteId.get(true);
	}

	/**
	 * @param resultToAddTo to each contactPerson without an email, and error result is added to this list builder.
	 * @return the persons that do have a non-empty email
	 */
	@NonNull
	public List<ContactPerson> filterForPersonsWithEmail(
			@NonNull final List<ContactPerson> contactPersons,
			@NonNull final ImmutableList.Builder<LocalToRemoteSyncResult> resultToAddErrorsTo)
	{
		final Map<Boolean, List<ContactPerson>> personsWithAndWithoutEmail = contactPersons
				.stream()
				.collect(Collectors.partitioningBy(contactPerson -> EmailValidator.isValid(contactPerson.getEmailAddressStringOrNull())));

		personsWithAndWithoutEmail.get(false)
				.stream()
				.map(contactPerson -> LocalToRemoteSyncResult.error(contactPerson, "Contact person has no (valid) email address"))
				.forEach(resultToAddErrorsTo::add);

		return personsWithAndWithoutEmail.get(true);
	}
}
