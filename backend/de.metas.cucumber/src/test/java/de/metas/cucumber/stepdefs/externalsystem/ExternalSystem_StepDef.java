/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.externalsystem;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.externalsystem.ExternalSystemCreateRequest;
import de.metas.externalsystem.ExternalSystemRepository;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.model.I_ExternalSystem;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.SpringContextHolder;

@RequiredArgsConstructor
public class ExternalSystem_StepDef
{
	private final ExternalSystemRepository externalSystemRepository = SpringContextHolder.instance.getBean(ExternalSystemRepository.class);

	@Then("metasfresh contains External System")
	public void containsExternalSystem(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createExternalSystem);
	}

	private void createExternalSystem(@NonNull final DataTableRow row)
	{
		final String name = row.getAsString(I_ExternalSystem.COLUMNNAME_Name);
		final String value = row.getAsString(I_ExternalSystem.COLUMNNAME_Value);

		externalSystemRepository.getOptionalByValue(value)
				.orElseGet(() -> externalSystemRepository.create(ExternalSystemCreateRequest.builder()
						.name(name)
						.type(ExternalSystemType.ofValue(value))
						.build()));
	}
}