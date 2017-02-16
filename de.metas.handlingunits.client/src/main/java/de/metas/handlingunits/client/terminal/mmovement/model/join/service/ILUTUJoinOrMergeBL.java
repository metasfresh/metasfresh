package de.metas.handlingunits.client.terminal.mmovement.model.join.service;

/*
 * #%L
 * de.metas.handlingunits.client
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


import java.util.List;

import org.adempiere.util.ISingletonService;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.allocation.transfer.IHUJoinBL;
import de.metas.handlingunits.client.terminal.mmovement.model.join.ILUTUJoinKey;
import de.metas.handlingunits.model.I_M_HU;

public interface ILUTUJoinOrMergeBL extends ISingletonService
{
	/**
	 * Either join or merge the selected TUs in the LU using Join keys. For those {@code tuKeys} that represent "real" TUs, 
	 * invoke {@link IHUJoinBL}, for those that represent aggregate HUs, perform a merge.
	 *
	 * @param terminalCtx
	 * @param luKey
	 * @param tuKeys
	 * @return HUs that were processed
	 */
	List<I_M_HU> joinOrMergeHUs(ITerminalContext terminalCtx, ILUTUJoinKey luKey, List<? extends ILUTUJoinKey> tuKeys);
}
