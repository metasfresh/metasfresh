package org.adempiere.mm.attributes.interceptor;

<<<<<<< HEAD
import static org.adempiere.model.InterfaceWrapperHelper.createOld;

=======
import com.google.common.base.Objects;
import de.metas.util.Check;
import lombok.NonNull;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.ModelValidator;
<<<<<<< HEAD

import com.google.common.base.Objects;

import de.metas.util.Check;
import lombok.NonNull;
=======
import org.springframework.stereotype.Component;

import static org.adempiere.model.InterfaceWrapperHelper.createOld;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/*
 * #%L
 * de.metas.business
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

<<<<<<< HEAD
@Interceptor(I_M_AttributeValue.class)
public class M_AttributeValue
{
	public static final M_AttributeValue INSTANCE = new M_AttributeValue();

=======
@Component
@Interceptor(I_M_AttributeValue.class)
public class M_AttributeValue
{
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public M_AttributeValue()
	{
	}

<<<<<<< HEAD
=======
	/**
	 * Note: no user-friendly msg needed, because the column is not updatable.
	 */
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_M_AttributeValue.COLUMNNAME_Value)
	public void preventAttributeValueChange(@NonNull final I_M_AttributeValue attributeValue)
	{
		final String newValue = attributeValue.getValue();
		final String oldValue = createOld(attributeValue, I_M_AttributeValue.class).getValue();

		Check.errorUnless(
				Objects.equal(newValue, oldValue),
<<<<<<< HEAD
				"M_AttributeValue.Value may not be updated because it might be relevant for our storage; oldValue={}; newValue={}; attributevalue={}",
=======
				"M_AttributeValue.Value may not be updated because it's rendered into ASI-strings and might be relevant for our storage; oldValue={}; newValue={}; attributevalue={}",
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				oldValue, newValue, attributeValue);
	}

}
