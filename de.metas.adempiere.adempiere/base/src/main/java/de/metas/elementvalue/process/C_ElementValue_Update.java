package de.metas.elementvalue.process;

import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_TreeNode;
import org.compiere.model.I_C_ElementValue;

import de.metas.elementvalue.ElementValue;
import de.metas.elementvalue.ElementValueId;
import de.metas.elementvalue.ElementValueRepository;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class C_ElementValue_Update extends JavaProcess implements IProcessPrecondition
{

	@Param(parameterName = I_AD_TreeNode.COLUMNNAME_Parent_ID, mandatory = true)
	private int p_parentId;

	@Param(parameterName = I_AD_TreeNode.COLUMNNAME_SeqNo, mandatory = true)
	private int p_seqNo;

	final ElementValueRepository evRepo = SpringContextHolder.instance.getBean(ElementValueRepository.class);

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
		final I_C_ElementValue record = evRepo.getByIdInTrx(ElementValueId.ofRepoId(getRecord_ID()));
		final ElementValue elementValue = evRepo.toElementValue(record);
		evRepo.updateTreeNodeParentAndSeqNo(elementValue, p_parentId, p_seqNo);
		
		return "OK";
	}
	}
