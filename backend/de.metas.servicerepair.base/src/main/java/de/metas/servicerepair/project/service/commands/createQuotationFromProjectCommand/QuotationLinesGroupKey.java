/*
 * #%L
 * de.metas.servicerepair.base
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

package de.metas.servicerepair.project.service.commands.createQuotationFromProjectCommand;

import de.metas.servicerepair.customerreturns.WarrantyCase;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
@Builder
class QuotationLinesGroupKey
{
	public static QuotationLinesGroupKey OTHERS = QuotationLinesGroupKey.builder().type(Type.OTHERS).build();

	public enum Type
	{
		REPAIRED_PRODUCT,
		OTHERS
	}

	@NonNull Type type;

	@Nullable WarrantyCase warrantyCase;
	@Nullable ServiceRepairProjectTaskId taskId;

	public static boolean equals(@Nullable final QuotationLinesGroupKey key1, @Nullable final QuotationLinesGroupKey key2)
	{
		return Objects.equals(key1, key2);
	}
}
