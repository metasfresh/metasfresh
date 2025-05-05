package de.metas.letter;

import de.metas.i18n.Language;
import de.metas.letters.model.I_AD_BoilerPlate;
import lombok.NonNull;
import org.adempiere.ad.expression.api.impl.StringExpressionCompiler;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.translate;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Repository
public class BoilerPlateRepository
{
	@NonNull
	public BoilerPlate getByBoilerPlateId(
			@NonNull final BoilerPlateId boilerPlateId,
			@Nullable final Language language)
	{
		final I_AD_BoilerPlate boilerPlateRecord = loadOutOfTrx(boilerPlateId, I_AD_BoilerPlate.class);
		return toBoilerPlate(boilerPlateRecord, language);
	}

	private BoilerPlate toBoilerPlate(
			@NonNull final I_AD_BoilerPlate boilerPlateRecord,
			@Nullable final Language language)
	{
		final I_AD_BoilerPlate recordToUse;
		final Language languageEffective;
		if (language != null)
		{
			recordToUse = translate(boilerPlateRecord, I_AD_BoilerPlate.class, language.getAD_Language());
			languageEffective = language;
		}
		else
		{
			recordToUse = boilerPlateRecord;
			languageEffective = Language.getBaseLanguage();
		}

		return BoilerPlate.builder()
				.id(BoilerPlateId.ofRepoId(recordToUse.getAD_BoilerPlate_ID()))
				.language(languageEffective)
				.subject(StringExpressionCompiler.instance.compile(recordToUse.getSubject()))
				.textSnippet(StringExpressionCompiler.instance.compile(recordToUse.getTextSnippet()))
				.build();
	}
}
