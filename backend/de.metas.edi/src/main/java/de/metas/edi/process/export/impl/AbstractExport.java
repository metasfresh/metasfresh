package de.metas.edi.process.export.impl;

/*
 * #%L
 * de.metas.edi
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.edi.model.I_EDI_Document;
import de.metas.edi.process.export.IExport;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.process.rpl.exp.CreateAttachmentRequest;
import org.adempiere.process.rpl.exp.ExportHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.MEXPFormat;
import org.compiere.model.MReplicationStrategy;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.model.X_AD_ReplicationTable;

import javax.annotation.Nullable;
import java.util.Properties;

/**
 *
 * Abstract base class to do the exporting. subclass this one to export data that is compatible with {@link I_EDI_Document} (such as {@link I_EDI_Desadv}).
 *
 * @param <T> type of the I_EDI_Document that shall be exported.
 */
public abstract class AbstractExport<T extends I_EDI_Document>
		implements IExport<I_EDI_Document>
{
	private final T document;
	private final String tableIdentifier;
	private final ClientId expClientId;

	public AbstractExport(
			@NonNull final T document,
			@NonNull final String tableIdentifier,
			@NonNull final ClientId expClientId)
	{
		this.document = document;

		Check.assumeNotEmpty(tableIdentifier, "Parameter 'tableIdentifier' may not be empty");
		this.tableIdentifier = tableIdentifier;

		Check.assume(expClientId.isRegular(), "Parameter 'expClientId' needs to be non-System (AD_Client_ID>0); expClientId={}", expClientId);
		this.expClientId = expClientId;
	}

	protected <DT> void exportEDI(final Class<DT> documentType, final String exportFormatName, final String tableName, final String columnName)
	{
		exportEDI(documentType, exportFormatName, tableName, columnName, null);
	}

	/**
	 * Sends given document to ESB/EDI bus
	 *
	 * @throws Exception on any error
	 */
	protected <DT> void exportEDI(
			final Class<DT> documentType,
			final String exportFormatName,
			final String tableName,
			final String columnName,
			@Nullable final CreateAttachmentRequest attachResultRequest)
	{
		final String whereClause = columnName + "=?";

		final Properties ctx = InterfaceWrapperHelper.getCtx(document);
		final String trxName = InterfaceWrapperHelper.getTrxName(document);
		final int recordId = InterfaceWrapperHelper.getId(document);
		final DT viewToExport = new Query(ctx, tableName, whereClause, trxName)
				.setParameters(recordId)
				.firstOnly(documentType);
		final PO viewToExportPO = InterfaceWrapperHelper.getPO(viewToExport);
		Check.errorIf(viewToExportPO == null, "View {} has no record for document {}", tableName, document);

		final MEXPFormat exportFormat = fetchExportFormat(ctx, exportFormatName, trxName);
		final ExportHelper exportHelper = new ExportHelper(ctx, expClientId);

		Check.errorIf(exportHelper.getAD_ReplicationStrategy() == null, "Client {} has no AD_ReplicationStrategy", expClientId);

		exportHelper.exportRecord(viewToExportPO,
				exportFormat,
				MReplicationStrategy.REPLICATION_DOCUMENT,
				X_AD_ReplicationTable.REPLICATIONTYPE_Merge,
				0, // ReplicationEvent = no event
				attachResultRequest
		);
	}

	private MEXPFormat fetchExportFormat(final Properties ctx, final String exportFormatName, final String trxName)
	{
		MEXPFormat expFormat = MEXPFormat.getFormatByValueAD_Client_IDAndVersion(ctx,
				exportFormatName,
				expClientId.getRepoId(),
				"*", // version
				trxName);

		if (expFormat == null)
		{
			expFormat = MEXPFormat.getFormatByValueAD_Client_IDAndVersion(ctx,
					exportFormatName,
					0, // AD_Client_ID
					"*", // version
					trxName);
		}

		if (expFormat == null)
		{
			throw new AdempiereException("@NotFound@ @EXP_Format_ID@ (@Value@: " + exportFormatName + ")");
		}

		return expFormat;
	}

	@Override
	public T getDocument()
	{
		return document;
	}

	@Override
	public final String getTableIdentifier()
	{
		return tableIdentifier;
	}
}
