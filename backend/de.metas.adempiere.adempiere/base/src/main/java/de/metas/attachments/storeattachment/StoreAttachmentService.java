package de.metas.attachments.storeattachment;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import ch.qos.logback.classic.Level;
import de.metas.attachments.AttachmentEntry;
import de.metas.logging.LogManager;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import lombok.NonNull;

/*
 * #%L
 * de.metas.document.archive.base
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
public class StoreAttachmentService
{
	private static final Logger logger = LogManager.getLogger(StoreAttachmentService.class);

	private final List<StoreAttachmentServiceImpl> storeAttachmentServices;
	private final List<AttachmentStoredListener> attachmentStoredListeners;

	public StoreAttachmentService(
			@NonNull final Optional<List<StoreAttachmentServiceImpl>> storeAttachmentServices,
			@NonNull final Optional<List<AttachmentStoredListener>> attachmentStoredListeners)
	{
		this.storeAttachmentServices = storeAttachmentServices.orElse(ImmutableList.of());
		this.attachmentStoredListeners = attachmentStoredListeners.orElse(ImmutableList.of());
	}

	public boolean isAttachmentStorable(@NonNull final AttachmentEntry attachmentEntry)
	{
		final StoreAttachmentServiceImpl service = extractFor(attachmentEntry);
		if (service == null)
		{
			return false;
		}
		return service.isAttachmentStorable(attachmentEntry);
	}

	/**
	 * @return false if there is no StoreAttachmentService to take care of the given attachmentEntry.
	 */
	public boolean storeAttachment(@NonNull final AttachmentEntry attachmentEntry)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

		final StoreAttachmentServiceImpl service = extractFor(attachmentEntry);
		if (service == null)
		{
			loggable.addLog("StoreAttachmentService - no StoreAttachmentServiceImpl found for attachment={}", attachmentEntry);
			return false;
		}

		final URI storageIdentifier = service.storeAttachment(attachmentEntry);
		loggable.addLog("StoreAttachmentService - stored attachment to URI={}; storeAttachmentServiceImpl={}, attachment={}", storageIdentifier, service, attachmentEntry);

		for (final AttachmentStoredListener attachmentStoredListener : attachmentStoredListeners)
		{
			attachmentStoredListener.attachmentWasStored(attachmentEntry, storageIdentifier);
		}
		return true;
	}

	private StoreAttachmentServiceImpl extractFor(@NonNull final AttachmentEntry attachmentEntry)
	{
		for (final StoreAttachmentServiceImpl storeAttachmentService : storeAttachmentServices)
		{
			if (storeAttachmentService.appliesTo(attachmentEntry))
			{
				return storeAttachmentService;
			}
		}
		return null;
	}

}
