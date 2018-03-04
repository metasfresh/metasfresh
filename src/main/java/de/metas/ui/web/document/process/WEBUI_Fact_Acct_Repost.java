package de.metas.ui.web.document.process;

import org.adempiere.acct.api.IPostingRequestBuilder.PostImmediate;
import org.adempiere.acct.api.IPostingService;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.compiere.model.I_Fact_Acct;

import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IViewRow;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class WEBUI_Fact_Acct_Repost extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private final IPostingService postingService = Services.get(IPostingService.class);

	@Param(parameterName = "IsEnforcePosting", mandatory = true)
	private boolean enforce;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		getView().streamByIds(getSelectedRowIds())
				.map(this::extractDocumentToRepost)
				.distinct()
				.forEach(this::repost);
		return MSG_OK;
	}

	private DocumentToRepost extractDocumentToRepost(final IViewRow row)
	{
		final int adTableId = row.getFieldJsonValueAsInt(I_Fact_Acct.COLUMNNAME_AD_Table_ID, -1);
		final int recordId = row.getFieldJsonValueAsInt(I_Fact_Acct.COLUMNNAME_Record_ID, -1);
		final int adClientId = row.getFieldJsonValueAsInt(I_Fact_Acct.COLUMNNAME_AD_Client_ID, -1);
		return DocumentToRepost.builder()
				.adTableId(adTableId)
				.recordId(recordId)
				.adClientId(adClientId)
				.build();
	}

	private void repost(final DocumentToRepost doc)
	{
		postingService
				.newPostingRequest()
				.setContext(getCtx(), ITrx.TRXNAME_None)
				.setAD_Client_ID(doc.getAdClientId())
				.setDocument(doc.getAdTableId(), doc.getRecordId())
				.setForce(enforce)
				.setPostImmediate(PostImmediate.Yes)
				.setFailOnError(true) // yes, because we will display a pop-up to user in this case (see below)
				.postIt();
	}
	
	@Override
	protected void postProcess(boolean success)
	{
		getView().invalidateSelection();
	}

	@lombok.Value
	@lombok.Builder
	private static class DocumentToRepost
	{
		int adTableId;
		int recordId;
		int adClientId;
	}

}
