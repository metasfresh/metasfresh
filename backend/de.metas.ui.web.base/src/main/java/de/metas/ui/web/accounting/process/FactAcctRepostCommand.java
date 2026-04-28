package de.metas.ui.web.accounting.process;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.DocumentPostMultiRequest;
import de.metas.acct.api.DocumentPostRequest;
import de.metas.acct.api.IPostingService;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.util.Set;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

final class FactAcctRepostCommand
{
	private final IPostingService postingService = Services.get(IPostingService.class);

	private final UserId onErrorNotifyUserId;
	private final boolean forcePosting;
	private final ImmutableSet<DocumentToRepost> documentsToRepost;

	@lombok.Value
	@lombok.Builder
	public static class DocumentToRepost
	{
		int adTableId;
		int recordId;
		ClientId clientId;

		public TableRecordReference getRecordRef()
		{
			return TableRecordReference.of(getAdTableId(), getRecordId());
		}
	}

	@Builder
	private FactAcctRepostCommand(
			final boolean forcePosting,
			@NonNull @Singular("documentToRepost") final Set<DocumentToRepost> documentsToRepost,
			@Nullable final UserId onErrorNotifyUserId)
	{
		Check.assumeNotEmpty(documentsToRepost, "documentsToRepost is not empty");

		this.forcePosting = forcePosting;
		this.documentsToRepost = ImmutableSet.copyOf(documentsToRepost);
		this.onErrorNotifyUserId = onErrorNotifyUserId;
	}

	public void execute()
	{
		documentsToRepost.stream()
				.map(this::toDocumentPostRequest)
				.collect(DocumentPostMultiRequest.collect())
				.ifPresent(postingService::schedule);
	}

	private DocumentPostRequest toDocumentPostRequest(final DocumentToRepost doc)
	{
		return DocumentPostRequest.builder()
				.record(doc.getRecordRef())
				.clientId(doc.getClientId())
				.force(forcePosting)
				.onErrorNotifyUserId(onErrorNotifyUserId)
				.build();
	}

}
