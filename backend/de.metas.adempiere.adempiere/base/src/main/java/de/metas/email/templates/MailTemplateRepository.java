package de.metas.email.templates;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_R_MailText;
import org.springframework.stereotype.Repository;

import de.metas.cache.CCache;
import de.metas.i18n.IModelTranslationMap;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@Repository
public class MailTemplateRepository
{
	private final CCache<MailTemplateId, MailTemplate> templatesById = CCache.<MailTemplateId, MailTemplate> builder()
			.tableName(I_R_MailText.Table_Name)
			.build();

	public MailTemplate getById(@NonNull final MailTemplateId id)
	{
		return templatesById.getOrLoad(id, this::retrieveById);
	}

	public MailTemplate retrieveById(@NonNull final MailTemplateId id)
	{
		final I_R_MailText record = loadOutOfTrx(id, I_R_MailText.class);
		return toMailTemplate(record);
	}

	private static MailTemplate toMailTemplate(final I_R_MailText record)
	{
		final IModelTranslationMap trlMap = InterfaceWrapperHelper.getModelTranslationMap(record);

		return MailTemplate.builder()
				.id(MailTemplateId.ofRepoId(record.getR_MailText_ID()))
				.name(record.getName())
				.html(record.isHtml())
				.mailHeader(trlMap.getColumnTrl(I_R_MailText.COLUMNNAME_MailHeader, record.getMailHeader()))
				.mailText(trlMap.getColumnTrl(I_R_MailText.COLUMNNAME_MailText, record.getMailText()))
				.mailText2(trlMap.getColumnTrl(I_R_MailText.COLUMNNAME_MailText2, record.getMailText2()))
				.mailText3(trlMap.getColumnTrl(I_R_MailText.COLUMNNAME_MailText3, record.getMailText3()))
				.build();
	}
}
