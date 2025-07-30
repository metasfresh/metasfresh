/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.sscc18.impl;

import de.metas.sscc18.ISSCC18CodeDAO;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;

public class SSCC18CodeDAO implements ISSCC18CodeDAO
{
	@Override
	public AttributeId retrieveSSCC18AttributeId()
	{
		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
		return attributesRepo.retrieveAttributeIdByValueOrNull(AttributeConstants.ATTR_SSCC18_Value);
	}

}
