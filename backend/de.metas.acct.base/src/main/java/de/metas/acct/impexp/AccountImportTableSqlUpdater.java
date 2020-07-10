package de.metas.acct.impexp;

import static de.metas.impexp.format.ImportTableDescriptor.COLUMNNAME_I_ErrorMsg;
import static de.metas.impexp.format.ImportTableDescriptor.COLUMNNAME_I_IsImported;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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
 * A helper class for {@link AccountImportProcess} that performs SQL updates on the {@link I_ElementValue} table.
 * Those updates complements the data from existing metasfresh records and flag those import records that can't yet be imported.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
public class AccountImportTableSqlUpdater
{
	private static final transient Logger logger = LogManager.getLogger(AccountImportTableSqlUpdater.class);

	public void updateAccountImportTable(@NonNull final ImportRecordsSelection selection)
	{
		dbUpdateElement(selection);
		dbUpdateParentElementValue(selection);
		dbUpdateErrorMessages(selection);
	}

	private void dbUpdateElement(final ImportRecordsSelection selection)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_ElementValue i ")
				.append("SET C_Element_ID = (SELECT C_Element_ID FROM C_Element e WHERE e.Name = i.ElementName ")
				.append("AND e.AD_Client_ID IN (0, i.AD_Client_ID) ")
				.append("AND e.AD_Org_ID IN (0, i.AD_Org_ID) ) ")
				.append("WHERE ElementName IS NOT NULL AND C_Element_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_None);
		logger.debug("Set Element Default={} ",  no);
	}

	public void dbUpdateParentElementValue(final ImportRecordsSelection selection)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_ElementValue i ")
				.append("SET ParentElementValue_ID=(SELECT C_ElementValue_ID ")
				.append("FROM C_ElementValue ev WHERE i.C_Element_ID=ev.C_Element_ID ")
				.append("AND i.ParentValue=ev.Value AND i.AD_Client_ID=ev.AD_Client_ID ) ")
				.append("WHERE ParentElementValue_ID IS NULL ")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_None);
		logger.debug("Found Parent ElementValue={}",  no);
	}

	public void dbUpdateParentElementValueId(final int treeId)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE AD_TreeNode set Parent_ID = parentelementvalue_id, seqno = c_elementvalue_id ")
				.append(" from i_elementvalue ev where ev.C_ElementValue_ID = node_ID and ad_tree_ID = ? " )
				.append(" and parentelementvalue_id is not null " );
		no = DB.executeUpdateEx(sql.toString(), new Object[] {treeId}, ITrx.TRXNAME_None);
		logger.debug("Updated Parent ElementValue=" + no);

		sql = new StringBuilder("update AD_TreeNode set  seqno = c_elementvalue_id ")
				.append(" from i_elementvalue ev where ev.C_ElementValue_ID = node_ID and ad_tree_ID = ? " )
				.append(" and ev.IsSummary='Y' " );
		no = DB.executeUpdateEx(sql.toString(), new Object[] {treeId}, ITrx.TRXNAME_None);
		logger.debug("Updated Parent Seqno={}",  no);

	}

	public void dbUpdateTreeElementValue(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_ElementValue i ")
				.append("SET ParentElementValue_ID=(SELECT C_ElementValue_ID ")
				.append("FROM C_ElementValue ev WHERE i.C_Element_ID=ev.C_Element_ID ")
				.append("AND i.ParentValue=ev.Value AND i.AD_Client_ID=ev.AD_Client_ID ")
				.append("AND e.AD_Org_ID IN (0, i.AD_Org_ID) ) ")
				.append("WHERE ParentElementValue_ID IS NULL ")
				.append("AND " + COLUMNNAME_I_IsImported + "<>'Y' ")
				.append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_None);
		logger.debug("Found Parent ElementValue=" + no);
	}
	
	public void dbUpdateElementValueParentAndSeqNo(final int c_element_id)
	{
		final StringBuilder sql = new StringBuilder("update c_elementvalue set Parent_id = tn.parent_id, seqno = tn.seqno ")
				.append(" from AD_treenode tn where tn.node_id = c_elementvalue.c_elementvalue_id and tn.ad_org_id = c_elementvalue.ad_org_id")
				.append(" and exists ( select 1 from c_element e where e.c_element_id = c_elementvalue.c_element_id and e.ad_tree_id = tn.ad_tree_id) " )
				.append(" and c_element_id = ? " )
				.append("  " );
		int no = DB.executeUpdateEx(sql.toString(), new Object[] {c_element_id}, ITrx.TRXNAME_None);
		logger.debug("Updated Element values: {}",  no);

	}


	private void dbUpdateErrorMessages(final ImportRecordsSelection selection)
	{

		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_ElementValue ")
				.append("SET I_ErrorMsg=I_ErrorMsg||'Info=ParentNotFound, ' ")
				.append("WHERE ParentElementValue_ID IS NULL AND ParentValue IS NOT NULL ")
				.append("AND " + COLUMNNAME_I_IsImported + "<>'Y' ")
				.append(selection.toSqlWhereClause());
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_None);
		logger.info("Not Found Parent ElementValue=" + no);

		sql = new StringBuilder("UPDATE I_ElementValue "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Value is mandatory, ' "
				+ "WHERE Value IS NULL "
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(selection.toSqlWhereClause());
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_None);
		logger.info("Value is mandatory={}", no);
	}
}
