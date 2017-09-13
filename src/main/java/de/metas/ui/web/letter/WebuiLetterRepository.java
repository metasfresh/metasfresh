package de.metas.ui.web.letter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.UnaryOperator;

import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.window.datatypes.DocumentPath;
import lombok.NonNull;
import lombok.ToString;

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
public class WebuiLetterRepository
{
	private final AtomicInteger nextLetterId = new AtomicInteger(1);

	private final Cache<String, WebuiLetterEntry> lettersById = CacheBuilder.newBuilder()
			.expireAfterAccess(2, TimeUnit.HOURS)
			.build();

	public WebuiLetter createNewLetter(final int ownerUserId, final DocumentPath contextDocumentPath)
	{
		Preconditions.checkArgument(ownerUserId >= 0, "ownerUserId >= 0");

		final WebuiLetter letter = WebuiLetter.builder()
				.letterId(String.valueOf(nextLetterId.getAndIncrement()))
				.ownerUserId(ownerUserId)
				.contextDocumentPath(contextDocumentPath)
				.content(null)
				.build();

		lettersById.put(letter.getLetterId(), new WebuiLetterEntry(letter));

		return letter;
	}

	private WebuiLetterEntry getLetterEntry(final String letterId)
	{
		final WebuiLetterEntry letterEntry = lettersById.getIfPresent(letterId);
		if (letterEntry == null)
		{
			throw new EntityNotFoundException("Letter not found").setParameter("letterId", letterId);
		}
		return letterEntry;
	}

	public WebuiLetter getLetter(final String letterId)
	{
		return getLetterEntry(letterId).getLetter();
	}

	public WebuiLetterChangeResult changeLetter(final String letterId, @NonNull final UnaryOperator<WebuiLetter> letterModifier)
	{
		return getLetterEntry(letterId).compute(letterModifier);
	}

	public void removeLetterById(final String letterId)
	{
		lettersById.invalidate(letterId);
	}

	@ToString
	private static final class WebuiLetterEntry
	{
		private WebuiLetter letter;

		public WebuiLetterEntry(@NonNull final WebuiLetter letter)
		{
			this.letter = letter;
		}

		public synchronized WebuiLetter getLetter()
		{
			return letter;
		}

		public synchronized WebuiLetterChangeResult compute(final UnaryOperator<WebuiLetter> modifier)
		{
			final WebuiLetter letterOld = letter;
			final WebuiLetter letterNew = modifier.apply(letterOld);
			if (letterNew == null)
			{
				throw new NullPointerException("letter");
			}

			letter = letterNew;
			return WebuiLetterChangeResult.builder().letter(letterNew).originalLetter(letterOld).build();
		}

	}
}
