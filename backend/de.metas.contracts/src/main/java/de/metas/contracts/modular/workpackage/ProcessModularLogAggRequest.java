/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular.workpackage;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.log.LogEntryContractType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.util.lang.impl.TableRecordReference;

import java.util.List;

@Value
@Builder
@Jacksonized
public class ProcessModularLogAggRequest
{
	@NonNull
	List<ProcessRequest> requestList;

	@Value
	@Builder
	@Jacksonized
	public static class ProcessRequest
	{
		@NonNull TableRecordReference recordReference;
		@NonNull ModelAction action;
		@NonNull LogEntryContractType logEntryContractType;
		@NonNull FlatrateTermId flatrateTermId;
		@NonNull String handlerClassname;
	}
}
