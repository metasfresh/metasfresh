package de.metas.procurement.webui.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.metas.procurement.webui.model.Setting;
import de.metas.procurement.webui.repository.SettingsRepository;
import de.metas.procurement.webui.service.ISettingsService;

/*
 * #%L
 * de.metas.procurement.webui
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Service
public class SettingsService implements ISettingsService
{
	private static final String NAME_InfoMessage = "infoMessage";

	@Autowired
	SettingsRepository settingsRepo;

	@Override
	public String getValue(final String name)
	{
		final Setting setting = settingsRepo.findByName(name);
		if (setting == null)
		{
			return null;
		}

		return setting.getValue();
	}

	@Transactional
	@Override
	public void setValue(final String name, final String value)
	{
		Setting setting = settingsRepo.findByName(name);
		if (setting == null)
		{
			setting = new Setting();
			setting.setName(name);
		}

		setting.setValue(value);
		settingsRepo.saveAndFlush(setting);
	}

	@Override
	public String getInfoMessage()
	{
		return getValue(NAME_InfoMessage);
	}

	@Override
	public void setInfoMessage(String infoMessage)
	{
		setValue(NAME_InfoMessage, infoMessage);
	}
}
