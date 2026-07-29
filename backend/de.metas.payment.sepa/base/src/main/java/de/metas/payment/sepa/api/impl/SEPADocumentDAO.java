package de.metas.payment.sepa.api.impl;

/*
 * #%L
 * de.metas.payment.sepa
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

import de.metas.payment.sepa.api.ISEPADocumentDAO;
import de.metas.payment.sepa.interfaces.I_C_BP_BankAccount;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.payment.sepa.model.I_SEPA_Export_Line_Ref;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class SEPADocumentDAO implements ISEPADocumentDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public I_C_BP_BankAccount retrieveSEPABankAccount(I_C_BPartner bPartner)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(bPartner);
		final String trxName = InterfaceWrapperHelper.getTrxName(bPartner);

		final String whereClause = I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID + "=?";

		final List<Object> params = new ArrayList<>();
		params.add(bPartner.getC_BPartner_ID());

		return new Query(ctx, I_C_BP_BankAccount.Table_Name, whereClause, trxName)
				.setParameters(params)
				.setOrderBy(I_C_BP_BankAccount.COLUMNNAME_IsDefault + " DESC")
				.setOnlyActiveRecords(true)
				.first(I_C_BP_BankAccount.class);
	}

	@Override
	public List<I_SEPA_Export_Line> retrieveLines(@NonNull final I_SEPA_Export doc)
	{
		return queryBL.createQueryBuilder(I_SEPA_Export_Line.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_SEPA_Export_Line.COLUMNNAME_IsError, false)
				.addEqualsFilter(I_SEPA_Export_Line.COLUMNNAME_SEPA_Export_ID, doc.getSEPA_Export_ID())
				.orderBy()
				.addColumn(I_SEPA_Export_Line.COLUMNNAME_C_Currency_ID)
				.addColumn(I_SEPA_Export_Line.COLUMNNAME_SEPA_Export_Line_ID).endOrderBy()
				.create()
				.list();
	}

	@Override
	public List<I_SEPA_Export_Line> retrieveLinesChangeRule(Properties ctx, String trxName)
	{
		//
		// Placeholder for future functionality.
		return Collections.emptyList();
	}
	
	@NonNull
	public List<I_SEPA_Export_Line_Ref> retrieveLineReferences(@NonNull final I_SEPA_Export_Line line)
	{
		return toLineRefSqlQuery(line)
				.list();
	}
	
	@NonNull
	private IQuery<I_SEPA_Export_Line_Ref> toLineRefSqlQuery(@NonNull final I_SEPA_Export_Line line)
	{
		return queryBL.createQueryBuilder(I_SEPA_Export_Line_Ref.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_SEPA_Export_Line_Ref.COLUMNNAME_SEPA_Export_Line_ID, line.getSEPA_Export_Line_ID())
				.addEqualsFilter(I_SEPA_Export_Line_Ref.COLUMNNAME_SEPA_Export_ID, line.getSEPA_Export_ID())
				.create();
	}
}
