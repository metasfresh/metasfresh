package de.metas.printing.api;

/*
 * #%L
 * de.metas.printing.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Package;
import de.metas.util.ISingletonService;

public interface IPrintPackageBL extends ISingletonService
{
	IPrintPackageCtx createInitialCtx(Properties ctx);

	/**
	 * @param jobInstructions contains the information which print job with which line(s) shall be added to the package
	 * @return true if print package was created /updated
	 */
	boolean addPrintingDataToPrintPackage(I_C_Print_Package printPackage, 
			I_C_Print_Job_Instructions jobInstructions, 
			IPrintPackageCtx printPackageCtx);

	IPrintPackageCtx createEmptyInitialCtx();
}
