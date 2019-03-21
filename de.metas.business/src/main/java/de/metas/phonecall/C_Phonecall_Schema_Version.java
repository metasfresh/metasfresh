package de.metas.phonecall;

import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.model.CopyRecordFactory;
import org.compiere.model.I_C_Phonecall_Schema_Version;
import org.springframework.stereotype.Component;

/*
 * #%L
 * de.metas.business
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

@Interceptor(I_C_Phonecall_Schema_Version.class)
@Component("de.metas.phonecall.C_Phonecall_Schema_Version")
public class C_Phonecall_Schema_Version
{

	@Init
	public void init(final IModelValidationEngine engine)
	{
		CopyRecordFactory.enableForTableName(I_C_Phonecall_Schema_Version.Table_Name);

		CopyRecordFactory.registerCopyRecordSupport(I_C_Phonecall_Schema_Version.Table_Name, PhonecallSchemaVersionPOCopyRecordSupport.class);
	}

}
