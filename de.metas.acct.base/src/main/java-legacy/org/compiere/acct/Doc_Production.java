/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.acct;

import java.math.BigDecimal;
import java.util.List;

import org.compiere.model.MAcctSchema;

/**
 * Post Invoice Documents.
 * 
 * <pre>
 *  Table:              M_Production (325)
 *  Document Types:     MMP
 * </pre>
 * 
 * @author Jorg Janke
 * @version $Id: Doc_Production.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
@Deprecated
public class Doc_Production extends Doc<DocLine<Doc_Production>>
{
	public Doc_Production(final IDocBuilder docBuilder)
	{
		super(docBuilder, DOCTYPE_MatProduction);
	}

	@Override
	protected void loadDocumentDetails()
	{
	}

	@Override
	public BigDecimal getBalance()
	{
		return BigDecimal.ZERO;
	}   // getBalance

	@Override
	public List<Fact> createFacts(final MAcctSchema as)
	{
		throw newPostingException().setDetailMessage("Posting production documents si no longer supported");
	}
}
