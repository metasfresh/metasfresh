/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.acct.process;

import de.metas.acct.api.impl.ElementValueId;
import de.metas.elementvalue.ElementValueParentChangeRequest;
import de.metas.elementvalue.ElementValueService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_TreeNode;

public class C_ElementValue_Update extends JavaProcess implements IProcessPrecondition
{
	private final ElementValueService evService = SpringContextHolder.instance.getBean(ElementValueService.class);

	@Param(parameterName = I_AD_TreeNode.COLUMNNAME_Parent_ID, mandatory = true)
	private int p_parentId;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.accept();
		}

		return ProcessPreconditionsResolution.reject();
	}

	@Override
	protected String doIt()
	{
		evService.changeParentAndReorderByAccountNo(
				ElementValueParentChangeRequest.builder()
						.elementValueId(ElementValueId.ofRepoId(getRecord_ID()))
						.newParentId(ElementValueId.ofRepoId(p_parentId))
						.build());

		return MSG_OK;
	}
}
