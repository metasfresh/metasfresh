/*
 * #%L
 * de.metas.shipper.gateway.dpd
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
package de.metas.payment.esr.dataimporter;

import de.metas.payment.esr.model.X_ESR_ImportLine;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;

public enum ESRType implements ReferenceListAwareEnum
{
	TYPE_QRR(X_ESR_ImportLine.TYPE_QRR),
	TYPE_ISRReference(X_ESR_ImportLine.TYPE_ISRReference);

	@Getter
	private final String code;
	
	
	ESRType(final String code)
	{
		this.code = code;
	}


}
