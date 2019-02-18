package de.metas.phonecall.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import de.metas.phonecall.PhonecallSchema;
import de.metas.phonecall.PhonecallSchemaId;
import de.metas.phonecall.PhonecallSchemaVersion;
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

@Service
public class PhonecallScheduleService
{
	private final PhonecallScheduleRepository schedulesRepo;
	private final PhonecallSchemaRepository schemaRepo;

	public PhonecallScheduleService(
			@NonNull final PhonecallScheduleRepository schedulesRepo,
			@NonNull final PhonecallSchemaRepository schemaRepo)
	{
		this.schedulesRepo = schedulesRepo;
		this.schemaRepo = schemaRepo;
	}

	public void generatePhonecallSchedule(
			@NonNull final PhonecallSchemaId schemaId,
			@NonNull final LocalDate date)
	{
		final PhonecallSchema schema = schemaRepo.getById(schemaId);
		final PhonecallSchemaVersion schemaVersion = schema.getVersion(date);

		throw new UnsupportedOperationException(); // TODO
	}
}
