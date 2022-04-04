/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package org.adempiere.model.copy;

import lombok.NonNull;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.CopyRecordFactory;
import org.adempiere.model.CopyRecordSupport;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

@Service
public class CopyRecordService
{
	@NonNull
	public PO copyRecord(@NonNull final CopyRecordRequest copyRecordRequest)
	{
		final TableRecordReference fromRecordRef = copyRecordRequest.getTableRecordReference();

		final Object fromModel = fromRecordRef.getModel(PlainContextAware.newWithThreadInheritedTrx());
		final String tableName = InterfaceWrapperHelper.getModelTableName(fromModel);
		final PO fromPO = InterfaceWrapperHelper.getPO(fromModel);

		validateCloneIsEnabled(copyRecordRequest, tableName);

		final PO toPO = TableModelLoader.instance.newPO(Env.getCtx(), tableName, ITrx.TRXNAME_ThreadInherited);
		toPO.setDynAttribute(PO.DYNATTR_CopyRecordSupport, CopyRecordFactory.getCopyRecordSupport(tableName)); // set "getValueToCopy" advisor
		PO.copyValues(fromPO, toPO, true);
		InterfaceWrapperHelper.save(toPO);

		final CopyRecordSupport childCRS = CopyRecordFactory.getCopyRecordSupport(tableName);
		childCRS.setAdWindowId(copyRecordRequest.getFromAdWindowId());
		childCRS.setParentPO(toPO);
		childCRS.setBase(true);
		childCRS.copyRecord(fromPO, ITrx.TRXNAME_ThreadInherited);

		return toPO;
	}

	private static void validateCloneIsEnabled(@NonNull final CopyRecordRequest request, @NonNull final String tableName)
	{
		if (CopyRecordFactory.isEnabledForTableName(tableName))
		{
			return;
		}

		if (request.getCustomErrorIfCloneNotAllowed() != null)
		{
			throw new AdempiereException(request.getCustomErrorIfCloneNotAllowed());
		}

		throw new AdempiereException("Clone is not enabled for table=" + tableName);
	}
}