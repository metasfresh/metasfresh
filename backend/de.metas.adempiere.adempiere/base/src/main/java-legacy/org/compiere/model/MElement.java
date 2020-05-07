/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;

import de.metas.i18n.IMsgBL;

/**
 *	Accounting Element Model.
 *	
 *  @author Jorg Janke
 *  @version $Id: MElement.java,v 1.3 2006/07/30 00:58:04 jjanke Exp $
 */
public class MElement extends X_C_Element
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7656092284157893366L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_Element_ID id
	 *	@param trxName transaction
	 */
	public MElement (Properties ctx, int C_Element_ID, String trxName)
	{
		super(ctx, C_Element_ID, trxName);
		if (C_Element_ID == 0)
		{
		//	setName (null);
		//	setAD_Tree_ID (0);
		//	setElementType (null);	// A
			setIsBalancing (false);
			setIsNaturalAccount (false);
		}
	}	//	MElement

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MElement (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MElement

	/**
	 * 	Full Constructor
	 *	@param client client
	 *	@param Name name
	 *	@param ElementType type
	 *	@param AD_Tree_ID tree
	 */
	public MElement (MClient client, String Name, String ElementType, int AD_Tree_ID)
	{
		this (client.getCtx(), 0, client.get_TrxName());
		setClientOrg(client);
		setName (Name);
		setElementType (ElementType);	// A
		setAD_Tree_ID (AD_Tree_ID);
		setIsNaturalAccount(ELEMENTTYPE_Account.equals(ElementType));
	}	//	MElement

	/** Tree Used			*/
	private X_AD_Tree	m_tree = null;
	
	/**
	 * 	Get Tree
	 *	@return tree
	 */
	public X_AD_Tree getTree ()
	{
		if (m_tree == null)
			m_tree = new X_AD_Tree (getCtx(), getAD_Tree_ID(), get_TrxName());
		return m_tree;
	}	//	getTree
	
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (final boolean newRecord)
	{
		if (getAD_Org_ID() != 0)
		{
			setAD_Org_ID(0);
		}
		final String elementType = getElementType();
		//	Natural Account
		if (ELEMENTTYPE_UserDefined.equals(elementType) && isNaturalAccount())
		{
			setIsNaturalAccount(false);
		}
		//	Tree validation
		final X_AD_Tree tree = getTree();
		if (tree == null)
		{
			return false;
		}
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		
		final String treeType = tree.getTreeType();
				
		if (ELEMENTTYPE_UserDefined.equals(elementType))
		{
			if (X_AD_Tree.TREETYPE_User1.equals(treeType) || X_AD_Tree.TREETYPE_User2.equals(treeType))
			{
				;
			}
			else
			{
				throw new AdempiereException("@TreeType@ <> @ElementType@ (U)");
			}
		}
		else
		{
			if (!X_AD_Tree.TREETYPE_ElementValue.equals(treeType))
			{
				throw new AdempiereException("@TreeType@ <> @ElementType@ (A)");
			}
		}
		return true;
	}	//	beforeSave
	
}	//	MElement
