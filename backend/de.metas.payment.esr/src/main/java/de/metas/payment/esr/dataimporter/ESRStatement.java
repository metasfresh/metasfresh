package de.metas.payment.esr.dataimporter;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2017 metas GmbH
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
/**
 * Instances of this POJO are created by {@link IESRDataImporter}s and are supposed to be agnostic of the imported file's data format.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Data
@Builder
public class ESRStatement
{
	private final BigDecimal ctrlAmount;

	private final BigDecimal ctrlQty;

	@Singular
	private final List<ESRTransaction> transactions;

	@Singular
	private final List<String> errorMsgs;
}
