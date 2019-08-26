package de.metas.ui.web.window.descriptor.factory.standard;

import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.GridWindowVO;
import org.compiere.util.Env;

import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-webui-api
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@UtilityClass
public class DocumentLoaderUtil
{
	public GridWindowVO createGridWindoVO(final AdWindowId adWindowId)
	{
		final GridWindowVO gridWindowVO = GridWindowVO.builder()
				.ctx(Env.getCtx())
				.windowNo(0) // TODO: get rid of WindowNo from GridWindowVO
				.adWindowId(adWindowId)
				.adMenuId(-1) // N/A
				.loadAllLanguages(true)
				.applyRolePermissions(false) // must be false, unless we know that we do have #AD_User_ID in the context (which oftentimes we don't)
				.build();
		return gridWindowVO;
	}
}
