package de.metas.request;

import com.google.common.collect.ImmutableSet;
import de.metas.copy_with_details.CopyRecordSupportTableInfo;
import de.metas.copy_with_details.GeneralCopyRecordSupport;
import org.compiere.model.I_R_RequestUpdates;
import org.compiere.model.PO;

import java.util.List;

/*
 * #%L
 * de.metas.swat.base
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

public class RequestPOCopyRecordSupport extends GeneralCopyRecordSupport
{
	@Override
	public List<CopyRecordSupportTableInfo> getSuggestedChildren(final PO po)
	{
		return getSuggestedChildren(
				po.getPOInfo(),
				ImmutableSet.of(I_R_RequestUpdates.Table_Name)
		);
	}
}
