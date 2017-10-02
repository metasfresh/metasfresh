package de.metas.document.archive.model.validator;

/*
 * #%L
 * de.metas.document.archive.base
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

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import de.metas.document.archive.api.IDocOutboundProducerService;
import de.metas.document.archive.api.impl.AbstractDocOutboundProducer;
import de.metas.document.archive.async.spi.impl.DocOutboundWorkpackageProcessor;
import de.metas.document.archive.model.I_C_Doc_Outbound_Config;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;

/**
 * Intercepter which listens to a a table specified in {@link I_C_Doc_Outbound_Config} and enqueues the documents to {@link DocOutboundWorkpackageProcessor}.
 *
 * @author tsa
 *
 */
/* package */class DocOutboundProducerValidator extends AbstractDocOutboundProducer implements ModelValidator
{
	private static final String COLUMNNAME_Processed = "Processed";

	private final ModelValidationEngine modelValidationEngine;
	private int m_AD_Client_ID = -1;

	public DocOutboundProducerValidator(final ModelValidationEngine modelValidationEngine, final I_C_Doc_Outbound_Config config)
	{
		super(config);

		Check.assumeNotNull(modelValidationEngine, "modelValidationEngine not null");
		this.modelValidationEngine = modelValidationEngine;
	}

	@Override
	public void init(final IDocOutboundProducerService producerService)
	{
		// Detect AD_Client to be used for registering
		final I_C_Doc_Outbound_Config config = getC_Doc_Outbound_Config();
		final int adClientId = config.getAD_Client_ID();

		final I_AD_Client client;
		if (adClientId > 0)
		{
			client = config.getAD_Client();
		}
		else
		{
			// register for all clients
			client = null;
		}

		modelValidationEngine.addModelValidator(this, client);
	}

	// NOTE: keep in sync with initialize method
	@Override
	public void destroy(final IDocOutboundProducerService producerService)
	{
		// Unregister from model validation engine
		final String tableName = getTableName();
		modelValidationEngine.removeModelChange(tableName, this);
		modelValidationEngine.removeDocValidate(tableName, this);
	}

	// NOTE: keep in sync with destroy method
	@Override
	public void initialize(final ModelValidationEngine engine, final MClient client)
	{
		if (client != null)
		{
			m_AD_Client_ID = client.getAD_Client_ID();
		}

		final String tableName = getTableName();

		if (isDocument())
		{
			engine.addDocValidate(tableName, this);
		}
		else
		{
			engine.addModelChange(tableName, this);
		}
	}

	@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		return null; // nothing
	}

	@Override
	public String modelChange(PO po, int type) throws Exception
	{
		if (type == TYPE_AFTER_NEW || type == TYPE_AFTER_CHANGE)
		{
			if (isDocument())
			{
				if (!acceptDocument(po))
				{
					return null;
				}

				if (po.is_ValueChanged(IDocument.DocStatus) && Services.get(IDocumentBL.class).isDocumentReversedOrVoided(po))
				{
					voidDocOutbound(po);
				}
			}

			if (isJustProcessed(po, type))
			{
				createDocOutbound(po);
			}
		}
		return null;
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		Check.assume(isDocument(), "PO '{}' is a document", po);

		if (!acceptDocument(po))
		{
			return null;
		}

		if (timing == ModelValidator.TIMING_AFTER_COMPLETE
				&& !Services.get(IDocumentBL.class).isReversalDocument(po))
		{
			createDocOutbound(po);
		}

		if (timing == ModelValidator.TIMING_AFTER_VOID
				|| timing == ModelValidator.TIMING_AFTER_REVERSEACCRUAL
				|| timing == ModelValidator.TIMING_AFTER_REVERSECORRECT)
		{
			voidDocOutbound(po);
		}

		return null;
	}

	/**
	 *
	 * @param po
	 * @param changeType
	 * @return true if the given PO was just processed
	 */
	private boolean isJustProcessed(final PO po, final int changeType)
	{
		final boolean isNew = changeType == ModelValidator.TYPE_BEFORE_NEW || changeType == ModelValidator.TYPE_AFTER_NEW;
		final int idxProcessed = po.get_ColumnIndex(DocOutboundProducerValidator.COLUMNNAME_Processed);
		final boolean processedColumnAvailable = idxProcessed > 0;
		final boolean processed = processedColumnAvailable ? po.get_ValueAsBoolean(idxProcessed) : true;

		if (processedColumnAvailable)
		{
			if (isNew)
			{
				return processed;
			}
			else if (po.is_ValueChanged(idxProcessed))
			{
				return processed;
			}
			else
			{
				return false;
			}
		}
		else
		// Processed column is not available
		{
			// If is not available, we always consider the record as processed right after it was created
			// This condition was introduced because we need to archive/print records which does not have such a column (e.g. letters)
			return isNew;
		}
	}
}
