package de.metas.acct.doc;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.acct.Doc;
import org.compiere.model.PO;
import org.compiere.util.Env;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import de.metas.acct.api.AcctSchema;
import lombok.NonNull;

/*
 * #%L
 * de.metas.acct.base
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

/**
 * Convenient {@link IAcctDocProvider} implementation.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public abstract class AcctDocProviderTemplate implements IAcctDocProvider
{
	private final ImmutableMap<String, AcctDocFactory> docFactoriesByTableName;

	protected AcctDocProviderTemplate(final Map<String, AcctDocFactory> docFactoriesByTableName)
	{
		this.docFactoriesByTableName = ImmutableMap.copyOf(docFactoriesByTableName);
	}

	@Override
	public final String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(getDocTableNames())
				.toString();
	}

	@Override
	public final Set<String> getDocTableNames()
	{
		return docFactoriesByTableName.keySet();
	}

	@Override
	public final Doc<?> getOrNull(
			@NonNull final AcctDocRequiredServicesFacade services,
			@NonNull final List<AcctSchema> acctSchemas,
			@NonNull final TableRecordReference documentRef)
	{
		final String tableName = documentRef.getTableName();

		final AcctDocFactory docFactory = docFactoriesByTableName.get(tableName);
		if (docFactory == null)
		{
			return null;
		}

		return docFactory.createAcctDoc(AcctDocContext.builder()
				.services(services)
				.acctSchemas(acctSchemas)
				.documentModel(retrieveDocumentModel(documentRef))
				.build());
	}

	private AcctDocModel retrieveDocumentModel(final TableRecordReference documentRef)
	{
		final PO po = TableModelLoader.instance.getPO(
				Env.getCtx(),
				documentRef.getTableName(),
				documentRef.getRecord_ID(),
				ITrx.TRXNAME_ThreadInherited);
		if (po == null)
		{
			throw new AdempiereException("No document found for " + documentRef);
		}
		return new POAcctDocModel(po);
	}

	@FunctionalInterface
	protected interface AcctDocFactory
	{
		Doc<?> createAcctDoc(AcctDocContext ctx);
	}

}
