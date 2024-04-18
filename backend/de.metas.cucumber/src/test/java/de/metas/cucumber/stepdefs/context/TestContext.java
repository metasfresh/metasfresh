/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs.context;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.metas.cucumber.stepdefs.APIResponse;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.util.lang.RepoIdAware;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

@Data
public class TestContext
{
	private static final String ROW_FieldName = "REST.Context";

	private APIResponse apiResponse;
	private String requestPayload;
	private Map<String, String> httpHeaders;
	private String endpointPath;

	private final HashMap<String, String> variables = new HashMap<>();

	public String getApiResponseBodyAsString()
	{
		return apiResponse.getContent();
	}

	public <T> T getApiResponseBodyAs(@NonNull final Class<T> type) throws JsonProcessingException
	{
		return apiResponse.getContentAs(type);
	}

	public void setIntVariableFromRow(@NonNull final DataTableRow row, @NonNull final IntSupplier valueSupplier)
	{
		row.getAsOptionalString(ROW_FieldName).ifPresent(restVariableName -> setVariable(restVariableName, valueSupplier.getAsInt()));
	}

	public void setIdVariableFromRow(@NonNull final DataTableRow row, @NonNull final RepoIdAware id)
	{
		row.getAsOptionalString(ROW_FieldName).ifPresent(restVariableName -> setVariable(restVariableName, id.getRepoId()));
	}

	public void setIdVariableFromRow(@NonNull final DataTableRow row, @NonNull final Supplier<? extends RepoIdAware> idSupplier)
	{
		row.getAsOptionalString(ROW_FieldName).ifPresent(restVariableName -> {
			final RepoIdAware id = idSupplier.get();
			if (id != null)
			{
				setVariable(restVariableName, id.getRepoId());
			}
			else
			{
				setVariableToNull(restVariableName);
			}
		});
	}

	public void setStringVariableFromRow(@NonNull final DataTableRow row, @NonNull final Supplier<String> valueSupplier)
	{
		row.getAsOptionalString(ROW_FieldName).ifPresent(restVariableName -> setVariable(restVariableName, valueSupplier.get()));
	}

	public void setVariable(@NonNull String name, int valueInt)
	{
		setVariable(name, String.valueOf(valueInt));
	}

	public void setVariableToNull(@NonNull String name)
	{
		variables.put(name, null);
	}

	public void setVariable(@NonNull String name, @Nullable String value)
	{
		if (variables.containsKey(name))
		{
			throw new AdempiereException("Overriding REST context variable `" + name + "`=`" + variables.get(name) + "` with `" + value + "` is not allowed");
		}

		variables.put(name, value);
	}
}
