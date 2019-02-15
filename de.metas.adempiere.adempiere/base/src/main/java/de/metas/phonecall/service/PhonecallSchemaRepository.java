package de.metas.phonecall.service;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import org.compiere.model.I_C_Phonecall_Schema;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;

import de.metas.cache.CCache;
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

@Repository
public class PhonecallSchemaRepository
{
	private final CCache<PhonecallSchemaId, PhonecallSchema> schemas = CCache.<PhonecallSchemaId, PhonecallSchema> builder()
			.tableName(I_C_Phonecall_Schema.Table_Name)
			.build();

	public PhonecallSchema getById(@NonNull final PhonecallSchemaId id)
	{
		return schemas.getOrLoad(id, this::retrieveById);
	}

	public PhonecallSchema retrieveById(@NonNull final PhonecallSchemaId id)
	{
		final I_C_Phonecall_Schema schemaRecord = loadOutOfTrx(id, I_C_Phonecall_Schema.class);

		return PhonecallSchema.builder()
				.id(id)
				.name(schemaRecord.getName())
				.versions(retrieveSchemaVersions(id))
				.build();
	}

	private ImmutableList<PhonecallSchemaVersion> retrieveSchemaVersions(final PhonecallSchemaId id)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
