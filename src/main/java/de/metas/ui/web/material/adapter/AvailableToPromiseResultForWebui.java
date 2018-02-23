package de.metas.ui.web.material.adapter;

import static org.adempiere.model.InterfaceWrapperHelper.getModelTranslationMap;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeValue;

import com.google.common.collect.ImmutableMap;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.material.event.commons.AttributesKey;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-dispo-commons
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
public class AvailableToPromiseResultForWebui
{
	private final List<Group> groups;

	@Builder
	private AvailableToPromiseResultForWebui(@Singular final List<Group> groups)
	{
		Check.assumeNotEmpty(groups, "groups is not empty");
		this.groups = groups;
	}

	private Group getSingleGroup()
	{
		if (groups.size() > 1)
		{
			throw new AdempiereException("Not a single group: " + this);
		}
		return groups.get(0);
	}

	public Quantity getSingleQuantity()
	{
		return getSingleGroup().getQty();
	}

	@Value
	public static class Group
	{
		public enum Type
		{
			ATTRIBUTE_SET, OTHER_STORAGE_KEYS, ALL_STORAGE_KEYS
		}

		int productId;

		Quantity qty;

		Type type;

		ImmutableMap<String, Object> lookupAttributesMap;
		ITranslatableString storageAttributesString;
		ITranslatableString uomSymbolStr;

		@Builder
		public Group(
				final int productId,
				@NonNull final Type type,
				@Nullable final List<I_M_AttributeValue> attributeValues,
				@NonNull final Quantity qty)
		{
			Check.assume(productId > 0, "productId > 0");
			this.type = type;
			this.productId = productId;

			if (type == Type.ATTRIBUTE_SET)
			{
				Check.errorIf(attributeValues == null,
						"The given type parameter is {}, therefore the given attribute set may not be null; productId={}; qty={}", type, productId, qty);
			}
			this.qty = qty;
			this.uomSymbolStr = createUomSymbolStr();
			this.lookupAttributesMap = createLookupAttributesMap(attributeValues);
			this.storageAttributesString = createStorageAttributesString(attributeValues);
		}

		private ITranslatableString createUomSymbolStr()
		{
			final I_C_UOM uom = qty.getUOM();
			final ITranslatableString uomSymbolStr = getModelTranslationMap(uom)
					.getColumnTrl(I_C_UOM.COLUMNNAME_UOMSymbol, uom.getUOMSymbol());
			return uomSymbolStr;
		}

		private ImmutableMap<String, Object> createLookupAttributesMap(
				@Nullable final List<I_M_AttributeValue> attributeValues)
		{
			final ImmutableMap.Builder<String, Object> attributeMapBuilder = ImmutableMap.builder();
			switch (getType())
			{
				case ATTRIBUTE_SET:
					for (final I_M_AttributeValue attributevalue : attributeValues)
					{
						attributeMapBuilder.put(Integer.toString(attributevalue.getM_Attribute_ID()), attributevalue.getValue());
					}
					break;
				case OTHER_STORAGE_KEYS:
					break; // nothing
				case ALL_STORAGE_KEYS:
					break; // nothing
				default:
					Check.errorIf(true, "Unexpected Group.Type={}; gtroup={}", getType(), this);
					break;
			}
			return attributeMapBuilder.build();
		}

		private ITranslatableString createStorageAttributesString(
				@Nullable final List<I_M_AttributeValue> attributeValues)
		{
			final IMsgBL msgBL = Services.get(IMsgBL.class);

			final ITranslatableString result;
			switch (getType())
			{
				case ATTRIBUTE_SET:
					final List<ITranslatableString> singleStrings = new ArrayList<>();
					for (final I_M_AttributeValue attributeValue : attributeValues)
					{
						final ITranslatableString attributeValueStr = getModelTranslationMap(attributeValue)
								.getColumnTrl(I_M_AttributeValue.COLUMNNAME_Name, attributeValue.getName());
						singleStrings.add(attributeValueStr);
					}
					result = ITranslatableString.compose(" ", singleStrings);

					break;
				case OTHER_STORAGE_KEYS:
					result = msgBL.getTranslatableMsgText(AttributesKey.MSG_ATTRIBUTES_KEY_OTHER);
					break;
				case ALL_STORAGE_KEYS:
					result = msgBL.getTranslatableMsgText(AttributesKey.MSG_ATTRIBUTES_KEY_ALL);
					break;
				default:
					Check.errorIf(true, "Unexpected type={}; gtroup={}", getType(), this);
					result = null;
					break;
			}
			return result;
		}
	}


}
