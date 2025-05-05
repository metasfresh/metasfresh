/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.window.descriptor.decorator;

import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

@Service
public class ExternallyReferencedDocumentDecorator implements IDocumentDecorator
{
	private static final AdMessageKey EXTERNAL_REFERENCE_READ_ONLY_IN_METASFRESH_ERROR = AdMessageKey.of("CannotDeleteExternalReferenceReadOnlyInMetasfresh");

	private static final AdMessageKey EXTERNAL_REFERENCE_READ_ONLY_IN_METASFRESH_REASON = AdMessageKey.of("ExternalReferenceReadOnlyInMetasfreshReason");

	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private final ExternalReferenceRepository externalReferenceRepository;

	public ExternallyReferencedDocumentDecorator(@NonNull final ExternalReferenceRepository externalReferenceRepository)
	{
		this.externalReferenceRepository = externalReferenceRepository;
	}

	@Override
	@NonNull
	public ReadOnlyInfo isReadOnly(@NonNull final TableRecordReference recordReference)
	{
		final boolean isReadOnly = externalReferenceRepository.isReadOnlyInMetasfresh(recordReference);

		if (!isReadOnly)
		{
			return ReadOnlyInfo.of(BooleanWithReason.FALSE);
		}

		return ReadOnlyInfo.builder()
				.isReadOnly(BooleanWithReason.trueBecause(msgBL.getTranslatableMsgText(EXTERNAL_REFERENCE_READ_ONLY_IN_METASFRESH_REASON)))
				.forceReadOnlySubDocuments(true)
				.build();
	}

	@Override
	@NonNull
	public BooleanWithReason isDeleteForbidden(@NonNull final TableRecordReference recordReference)
	{
		if (!isReadOnly(recordReference).isReadOnly())
		{
			return BooleanWithReason.FALSE;
		}

		final ITranslatableString errorMessage = msgBL.getTranslatableMsgText(EXTERNAL_REFERENCE_READ_ONLY_IN_METASFRESH_ERROR, recordReference.getTableName() + "_" + recordReference.getRecord_ID());

		return BooleanWithReason.trueBecause(errorMessage);
	}

	@Override
	@NonNull
	public BooleanWithReason isDeleteSubDocumentsForbidden(final @NonNull TableRecordReference tableRecordReference)
	{
		return isDeleteForbidden(tableRecordReference);
	}
}
