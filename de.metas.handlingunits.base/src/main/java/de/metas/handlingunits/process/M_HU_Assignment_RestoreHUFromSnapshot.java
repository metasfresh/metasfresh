package de.metas.handlingunits.process;

import java.sql.Timestamp;

import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_Snapshot;
import de.metas.handlingunits.snapshot.IHUSnapshotDAO;
import de.metas.process.JavaProcess;
import de.metas.process.Param;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Quick workaround process for HUs that need to be restored to a snapshot version.
 * This process actually only makes sense is a troubleshooting scenario, so please only use it if you know what you are doing.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 * @task https://github.com/metasfresh/metasfresh/issues/2112
 */
public class M_HU_Assignment_RestoreHUFromSnapshot extends JavaProcess
{

	@Param(parameterName = I_M_HU_Snapshot.COLUMNNAME_Snapshot_UUID, mandatory = true)
	private String p_snapshotUUID;

	@Param(parameterName = "DateTrx", mandatory = true)
	private Timestamp dateTrx;

	@Override
	protected String doIt() throws Exception
	{
		final I_M_HU_Assignment huToRestore = getRecord(I_M_HU_Assignment.class);
		final TableRecordReference referencedModel = TableRecordReference.ofReferenced(huToRestore);

		final IHUSnapshotDAO huSnapshotDAO = Services.get(IHUSnapshotDAO.class);
		huSnapshotDAO.restoreHUs()
				.setContext(this)
				.setSnapshotId(p_snapshotUUID)
				.setDateTrx(dateTrx)
				.setReferencedModel(referencedModel)
				.addModelId(huToRestore.getM_HU_ID())
				.restoreFromSnapshot();

		return MSG_OK;
	}

}
