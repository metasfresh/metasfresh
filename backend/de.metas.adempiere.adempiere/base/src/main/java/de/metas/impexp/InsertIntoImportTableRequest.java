package de.metas.impexp;

import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.service.ClientId;

import de.metas.impexp.config.DataImportConfigId;
import de.metas.impexp.format.ImpFormat;
import de.metas.impexp.parser.ImpDataLine;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

@Value
@Builder
public class InsertIntoImportTableRequest
{
	@NonNull
	ImpFormat importFormat;

	@NonNull
	ClientId clientId;

	@NonNull
	OrgId orgId;

	@NonNull
	UserId userId;

	@NonNull
	DataImportRunId dataImportRunId;

	@Nullable
	DataImportConfigId dataImportConfigId;

	int insertBatchSize;

	@NonNull
	final Stream<ImpDataLine> stream;
}
