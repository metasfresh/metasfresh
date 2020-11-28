package de.metas.dimension;

import java.util.Optional;
import java.util.function.Supplier;

import org.adempiere.mm.attributes.AttributeId;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.material.event.commons.AttributesKey;
import de.metas.util.Services;
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
			() -> Services.get(IMsgBL.class).getTranslatableMsgText(DimensionConstants.MSG_NoneOrEmpty),
			AttributesKey.NONE,
			Optional.empty());

	public static DimensionSpecGroup OTHER_GROUP = new DimensionSpecGroup(
			() -> Services.get(IMsgBL.class).getTranslatableMsgText(AttributesKey.MSG_ATTRIBUTES_KEY_OTHER),
			AttributesKey.OTHER,
			Optional.empty());

	/** Note: we use a supplier because we want to make sure that Services.get(IMsgBL.class) is not called before the system is ready for either production or unit test mode. */
	@NonNull
	Supplier<ITranslatableString> groupNameSupplier;

	/**
	 * These {@code M_AttributeValue_ID}s belong to this group. Maybe empty, often has just one element.<br>
	 * Might later be abstracted away so that other kind of data (e.g. "BPartners") could also be mapped to a dimension group.
	 */
	@NonNull
	AttributesKey attributesKey;

	@NonNull
	Optional<AttributeId> attributeId;

	public boolean isEmptyGroup()
	{
		return AttributesKey.NONE.equals(attributesKey);
	}

	public boolean isOtherGroup()
	{
		return AttributesKey.OTHER.equals(attributesKey);
	}

	public ITranslatableString getGroupName()
	{
		return groupNameSupplier.get();
	}
}
