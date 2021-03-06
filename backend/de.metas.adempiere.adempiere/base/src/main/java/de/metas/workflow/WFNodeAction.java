/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.workflow;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_AD_WF_Node;

public enum WFNodeAction implements ReferenceListAwareEnum
{
	WaitSleep(X_AD_WF_Node.ACTION_WaitSleep),
	UserChoice(X_AD_WF_Node.ACTION_UserChoice),
	SubWorkflow(X_AD_WF_Node.ACTION_SubWorkflow),
	SetVariable(X_AD_WF_Node.ACTION_SetVariable),
	UserWindow(X_AD_WF_Node.ACTION_UserWindow),
	UserForm(X_AD_WF_Node.ACTION_UserForm),
	AppsTask(X_AD_WF_Node.ACTION_AppsTask),
	AppsReport(X_AD_WF_Node.ACTION_AppsReport),
	AppsProcess(X_AD_WF_Node.ACTION_AppsProcess),
	DocumentAction(X_AD_WF_Node.ACTION_DocumentAction),
	EMail(X_AD_WF_Node.ACTION_EMail),
	UserWorkbench(X_AD_WF_Node.ACTION_UserWorkbench);

	private static final ReferenceListAwareEnums.ValuesIndex<WFNodeAction> typesByCode = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	WFNodeAction(@NonNull final String code)
	{
		this.code = code;
	}

	public static WFNodeAction ofCode(@NonNull final String code)
	{
		return typesByCode.ofCode(code);
	}

	public boolean isUserChoice()
	{
		return UserChoice.equals(this);
	}
}
