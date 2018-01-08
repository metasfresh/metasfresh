package de.metas.dimension;

import org.adempiere.util.Services;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.material.event.commons.AttributesKey;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.dimension
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

@Value
public class DimensionSpecGroup
{
	public static DimensionSpecGroup EMPTY_GROUP = new DimensionSpecGroup(
			Services.get(IMsgBL.class).getTranslatableMsgText(DimensionConstants.MSG_NoneOrEmpty),
			AttributesKey.NONE);

	public static DimensionSpecGroup OTHER_GROUP = new DimensionSpecGroup(
			Services.get(IMsgBL.class).getTranslatableMsgText(AttributesKey.MSG_ATTRIBUTES_KEY_OTHER),
			AttributesKey.OTHER);

	@NonNull
	ITranslatableString groupName;

	/**
	 * These {@code M_AttributeValue_ID}s belong to this group. Maybe empty, often has just one element.<br>
	 * Might later be abstracted away so that other kind of data (e.g. "BPartners") could also be mapped to a dimension group.
	 */
	@NonNull
	AttributesKey attributesKey;

	public boolean isEmptyGroup()
	{
		return AttributesKey.NONE.equals(attributesKey);
	}

	public boolean isOtherGroup()
	{
		return AttributesKey.OTHER.equals(attributesKey);
	}
}
