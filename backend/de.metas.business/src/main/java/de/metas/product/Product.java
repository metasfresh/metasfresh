package de.metas.product;

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.ITranslatableString;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/*
 * #%L
 * de.metas.business
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

@Value
@Builder
public class Product
{
	@NonNull
	ProductId id;

	@NonNull
	UomId uomId;

	@NonNull
	String productNo;

	@NonNull
	ITranslatableString name;

	@Nullable
	ITranslatableString description;

	@Nullable
	BPartnerId manufacturerId;

	@Nullable
	String packageSize;

	@Nullable
	BigDecimal weight;
}
