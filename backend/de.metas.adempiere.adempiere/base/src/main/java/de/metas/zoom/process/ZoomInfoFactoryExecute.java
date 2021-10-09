package de.metas.zoom.process;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.GenericPO;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;

import de.metas.document.references.related_documents.POZoomSource;
import de.metas.document.references.related_documents.RelatedDocumentsFactory;
import de.metas.document.references.related_documents.RelatedDocumentsPermissions;
import de.metas.document.references.related_documents.RelatedDocumentsPermissionsFactory;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.ILoggable;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * Excercise the {@link RelatedDocumentsFactory} with the currently selected record.
 * The relevant code contains {@link ILoggable#addLog(String, Object...)} calls that output timing info.
 * this way, we can use the process to diagnose zoom-to-performance-problems.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ZoomInfoFactoryExecute extends JavaProcess
{
	private final RelatedDocumentsFactory relatedDocumentsFactory = SpringContextHolder.instance.getBean(RelatedDocumentsFactory.class);

	@Param(mandatory = true, parameterName = "AD_Window_ID")
	private AdWindowId windowId;

	@Param(mandatory = true, parameterName = "Record_ID")
	private int recordId;

	@Param(mandatory = true, parameterName = "AD_Table_ID")
	private int tableId;

	@Override
	protected String doIt() throws Exception
	{
		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(tableId);
		final GenericPO po = new GenericPO(tableName, getCtx(), recordId);

		final POZoomSource zoomSource = POZoomSource.of(po, windowId);
		final RelatedDocumentsPermissions permissions = RelatedDocumentsPermissionsFactory.ofRolePermissions(Env.getUserRolePermissions());
		relatedDocumentsFactory.retrieveRelatedDocuments(zoomSource, permissions);

		return MSG_OK;
	}
}
