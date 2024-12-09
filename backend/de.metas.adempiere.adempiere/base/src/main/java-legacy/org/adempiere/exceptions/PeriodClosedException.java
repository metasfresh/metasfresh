<<<<<<< HEAD
/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2008 SC ARHIPAC SERVICE SRL. All Rights Reserved.            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.exceptions;

import java.sql.Timestamp;

import org.compiere.model.MRefList;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;

/**
 * Period Closed Exception.
 * This exception is throwed by
 * {@link org.compiere.model.MPeriod#testPeriodOpen(java.util.Properties, Timestamp, int, int)} and
 * {@link org.compiere.model.MPeriod#testPeriodOpen(java.util.Properties, Timestamp, String, int)} methods.
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 * 				<li>FR [ 2520591 ] Support multiples calendar for Org
 *				@see http://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id=176962
 *
 */
=======
package org.adempiere.exceptions;

import de.metas.ad_reference.ReferenceId;
import de.metas.document.DocBaseType;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import org.compiere.model.X_C_DocType;

import javax.annotation.Nullable;
import java.sql.Timestamp;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
public class PeriodClosedException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2798371272365454799L;

	/**
	 * 
	 */
<<<<<<< HEAD
	public PeriodClosedException(Timestamp dateAcct, String docBaseType)
	{
		super("@PeriodClosed@ @Date@="+dateAcct+", @DocBaseType@="
				+MRefList.getListName(Env.getCtx(), X_C_DocType.DOCBASETYPE_AD_Reference_ID, docBaseType)
		);
=======
	public PeriodClosedException(final Timestamp dateAcct, final DocBaseType docBaseType)
	{
		super(buildMsg(dateAcct, docBaseType));
	}

	private static ITranslatableString buildMsg(@Nullable final Timestamp dateAcct, @Nullable final DocBaseType docBaseType)
	{
		final ITranslatableString dateAcctTrl = dateAcct != null
				? TranslatableStrings.dateAndTime(dateAcct)
				: TranslatableStrings.anyLanguage("?");

		final ITranslatableString docBaseTypeTrl = docBaseType != null
				? TranslatableStrings.adRefList(ReferenceId.ofRepoId(X_C_DocType.DOCBASETYPE_AD_Reference_ID), docBaseType.getCode())
				: TranslatableStrings.anyLanguage("?");

		return TranslatableStrings.builder()
				.appendADMessage(AdMessageKey.of("PeriodClosed"))
				.append(" ").appendADElement("DateAcct").append(": ").append(dateAcctTrl)
				.append(", ").appendADElement("DocBaseType").append(": ").append(docBaseTypeTrl)
				.build();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
