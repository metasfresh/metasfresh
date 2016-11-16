package de.metas.javaclasses.process;

import org.adempiere.util.Services;

import de.metas.javaclasses.IJavaClassTypeBL;
import de.metas.javaclasses.model.I_AD_JavaClass_Type;
import de.metas.process.Process;
import de.metas.process.SvrProcess;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
/**
 * This process invokes {@link IJavaClassTypeBL#updateClassRecordsList(I_AD_JavaClass_Type)} with the currently selected {@link I_AD_JavaClass_Type}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Process(requiresCurrentRecordWhenCalledFromGear = true)
public class AD_JavaClass_Type_UpdateClassRecordsList extends SvrProcess
{
	private final IJavaClassTypeBL javaClassTypeBL = Services.get(IJavaClassTypeBL.class);

	@Override
	protected String doIt() throws Exception
	{
		final I_AD_JavaClass_Type javaClassType = getRecord(I_AD_JavaClass_Type.class);
		javaClassTypeBL.updateClassRecordsList(javaClassType);

		return "@Success@";
	}
}
