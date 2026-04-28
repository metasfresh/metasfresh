package org.adempiere.mm.attributes.api.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.lang.SOTrx;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.api.Attribute;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeExcludeBL;
import org.adempiere.mm.attributes.api.IAttributeExcludeDAO;
import org.adempiere.model.I_M_AttributeSetExcludeLine;
import org.compiere.model.I_M_AttributeSetExclude;

import java.util.List;

public class AttributeExcludeBL implements IAttributeExcludeBL
{
	private final IAttributeExcludeDAO attributeExcludeDAO = Services.get(IAttributeExcludeDAO.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	@Override
	public boolean isFullExclude(I_M_AttributeSetExclude attributeSetExclude)
	{
		if (null == attributeSetExclude)
		{
			return false;
		}
		final List<I_M_AttributeSetExcludeLine> list = attributeExcludeDAO.retrieveLines(attributeSetExclude);
		if ((null != list) && (!list.isEmpty()))
		{
			final AttributeSetId attributeSetId = AttributeSetId.ofRepoId(attributeSetExclude.getM_AttributeSet_ID());
			final List<Attribute> attributeList = attributeDAO.retrieveAttributes(attributeSetId, true);

			Check.assumeNotNull(attributeList, "We shouldn't have attribute exclude lines on attribute sets without attributes; attributeSetExclude=" + attributeSetExclude);
			if (list.size() == attributeList.size())
			{
				// Every attribute is marked to be excluded.
				return true;
			}

			// Only some attributes are excluded. Not full exclude.
			return false;
		}
		return true;

	}

	@Override
	public boolean isExcludedAttribute(Attribute attribute, AttributeSetId attributeSetId, int columnId, SOTrx soTrx)
	{
		final I_M_AttributeSetExclude attributeSetExclude = attributeExcludeDAO.retrieveAttributeSetExclude(attributeSetId, columnId, soTrx);
		if (null == attributeSetExclude)
		{
			return false;
		}
		final List<I_M_AttributeSetExcludeLine> list = attributeExcludeDAO.retrieveLines(attributeSetExclude);
		if ((null == list) || (list.isEmpty()))
		{
			// Full exclude.
			return true;
		}
		for (final I_M_AttributeSetExcludeLine line : list)
		{
			final AttributeId lineAttributeId = AttributeId.ofRepoIdOrNull(line.getM_Attribute_ID());
			if (AttributeId.equals(lineAttributeId, attribute.getAttributeId()))
			{
				// We have a match. Attribute excluded.
				return true;
			}
		}

		return false;

	}

	@Override
	public I_M_AttributeSetExclude getAttributeSetExclude(@NonNull final AttributeSetId attributeSetId, int columnId, @NonNull SOTrx soTrx)
	{
		return attributeExcludeDAO.retrieveAttributeSetExclude(attributeSetId, columnId, soTrx);
	}

}
