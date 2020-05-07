package de.metas.inout.model.validator;

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.model.I_M_InOutLine;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * @author metas-dev <dev@metasfresh.com>
 * @task http://dewiki908/mediawiki/index.php/09548_Avoid_FK-constraint_violation_when_a_packaging-iol-is_deleted_%28106784154474%29
 */
@Interceptor(I_M_InOutLine.class)
public class M_InOutLine
{
	public static final M_InOutLine INSTANCE = new M_InOutLine();

	private M_InOutLine()
	{
	}

	/**
	 * Sets <code>M_PackingMaterial_InOutLine_ID</code> to <code>null</code> for all inOutLines that reference the given <code>packingMaterialLine</code>.
	 *
	 * Note: we don't even check if <code>packingMaterialLine</code> has <code>IsPackagingMaterial='Y'</code>,<br>
	 * because we want to make sure that the FK-constrain violation is avoided, even if due to whatever reason, the IsPackagingMaterial value was already set to 'N' earlier.
	 *
	 * @param packingMaterialLine
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void processorDeleted(final I_M_InOutLine packingMaterialLine)
	{
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
		final List<I_M_InOutLine> allReferencingLines = inOutDAO.retrieveAllReferencingLinesBuilder(packingMaterialLine)
				.create()
				.list(I_M_InOutLine.class);

		for (final I_M_InOutLine referencingIol : allReferencingLines)
		{
			referencingIol.setM_PackingMaterial_InOutLine(null);
			InterfaceWrapperHelper.save(referencingIol);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void handleInOutLineDelete(final I_M_InOutLine iol)
	{
		Services.get(IInOutBL.class).deleteMatchInvsForInOutLine(iol); // task 08627
	}
}
