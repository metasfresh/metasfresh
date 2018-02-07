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
import java.util.List;
import java.util.Properties;

import org.adempiere.acct.api.IAcctSchemaDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.report.MReportTree;
import org.compiere.util.KeyNamePair;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import de.metas.currency.ICurrencyDAO;

/**
 *  Accounting Schema Model (base)
 *
 *  @author 	Jorg Janke
 *  @author     victor.perez@e-evolution.com, www.e-evolution.com
 *    			<li>RF [ 2214883 ] Remove SQL code and Replace for Query http://sourceforge.net/tracker/index.php?func=detail&aid=2214883&group_id=176962&atid=879335  
 *  @version 	$Id: MAcctSchema.java,v 1.4 2006/07/30 00:58:04 jjanke Exp $
 */
public class MAcctSchema extends X_C_AcctSchema
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7228171623905614596L;


	/**
	 *  Get AccountSchema of Client
	 * 	@param ctx context
	 *  @param C_AcctSchema_ID schema id
	 *  @return Accounting schema
	 *  @deprecated please just load it directly because it's cached
	 *  @see org.adempiere.acct.model.validator.AcctModuleInterceptor
	 */
	@Deprecated
	public static MAcctSchema get (final Properties ctx, final int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID <= 0)
		{
			return null;
		}
		
		// NOTE: we assume the C_AcctSchema is cached (see org.adempiere.acct.model.validator.AcctModuleInterceptor.setupCaching(IModelCacheService) )
		final I_C_AcctSchema acctSchema = InterfaceWrapperHelper.loadOutOfTrx(C_AcctSchema_ID, I_C_AcctSchema.class);
		return LegacyAdapters.convertToPO(acctSchema);
	}	//	get

	/**
	 *  Get AccountSchema of Client
	 * 	@param ctx context
	 *  @param AD_Client_ID client or 0 for all
	 *  @return Array of AcctSchema of Client
	 *  @deprecated please use {@link IAcctSchemaDAO#retrieveClientAcctSchemas(Properties, int)}
	 */
	@Deprecated
	public static MAcctSchema[] getClientAcctSchema (final Properties ctx, final int AD_Client_ID)
	{
		final List<I_C_AcctSchema> clientAcctSchemas = Services.get(IAcctSchemaDAO.class).retrieveClientAcctSchemas(ctx, AD_Client_ID);
		return LegacyAdapters.convertToPOArray(clientAcctSchemas, MAcctSchema.class);
	}	//	getClientAcctSchema

	@Deprecated
	public static MAcctSchema[] getClientAcctSchema (final int AD_Client_ID)
	{
		final List<I_C_AcctSchema> clientAcctSchemas = Services.get(IAcctSchemaDAO.class).retrieveClientAcctSchemas(AD_Client_ID);
		return LegacyAdapters.convertToPOArray(clientAcctSchemas, MAcctSchema.class);
	}

	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_AcctSchema_ID id
	 *	@param trxName transaction
	 */
	public MAcctSchema (Properties ctx, int C_AcctSchema_ID, String trxName)
	{
		super (ctx, C_AcctSchema_ID, trxName);
		if (is_new())
		{
		//	setC_Currency_ID (0);
		//	setName (null);
			setAutoPeriodControl (true);
			setPeriod_OpenFuture(2);
			setPeriod_OpenHistory(2);
			setCostingMethod (COSTINGMETHOD_StandardCosting);
			setCostingLevel(COSTINGLEVEL_Client);
			setIsAdjustCOGS(false);
			setGAAP (GAAP_InternationalGAAP);
			setHasAlias (true);
			setHasCombination (false);
			setIsAccrual (true);	// Y
			setCommitmentType(COMMITMENTTYPE_None);
			setIsDiscountCorrectsTax (false);
			setTaxCorrectionType(TAXCORRECTIONTYPE_None);
			setIsTradeDiscountPosted (false);
			setIsPostServices(false);
			setIsExplicitCostAdjustment(false);
			setSeparator ("-");	// -
		}
	}	//	MAcctSchema
	
	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MAcctSchema (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MAcctSchema

	/**
	 * 	Parent Constructor
	 *	@param client client
	 *	@param currency currency
	 */
	public MAcctSchema (MClient client, KeyNamePair currency)
	{
		this (client.getCtx(), 0, client.get_TrxName());
		setClientOrg(client);
		setC_Currency_ID (currency.getKey());
		setName (client.getName() + " " + getGAAP() + "/" + get_ColumnCount() + " " + currency.getName());
	}	//	MAcctSchema


	/** Element List       */
	private MAcctSchemaElement[]	m_elements = null;
	/** GL Info				*/
	private I_C_AcctSchema_GL _acctSchemaGL = null;
	
	private MAccount				m_SuspenseError_Acct = null;
	private MAccount				m_CurrencyBalancing_Acct = null;
	private MAccount				m_DueTo_Acct = null;
	private MAccount				m_DueFrom_Acct = null;
	/** Accounting Currency Precision	*/
	private int						m_stdPrecision = -1;
	/** Costing Currency Precision		*/
	private int						m_costPrecision = -1;
	
	/** Only Post Org					*/
	private MOrg					m_onlyOrg = null;
	/** Only Post Org Childs			*/
	private Integer[] 				m_onlyOrgs = null; 

	/**************************************************************************
	 *  AcctSchema Elements
	 *  @return ArrayList of AcctSchemaElement
	 */
	public MAcctSchemaElement[] getAcctSchemaElements()
	{
		if (m_elements == null)
			m_elements = MAcctSchemaElement.getAcctSchemaElements(this);
		return m_elements;
	}   //  getAcctSchemaElements

	/**
	 *  Get AcctSchema Element
	 *  @param elementType segment type - AcctSchemaElement.ELEMENTTYPE_
	 *  @return AcctSchemaElement
	 *  @deprecated please use {@link IAcctSchemaDAO#retrieveFirstAcctSchemaElementOrNull(I_C_AcctSchema, String)}.
	 */
	@Deprecated
	public MAcctSchemaElement getAcctSchemaElement (String elementType)
	{
		return LegacyAdapters.convertToPO(
				Services.get(IAcctSchemaDAO.class).retrieveFirstAcctSchemaElementOrNull(this, elementType));
	}   //  getAcctSchemaElement

	/**
	 *  Has AcctSchema Element
	 *  @param segmentType segment type - AcctSchemaElement.SEGMENT_
	 *  @return true if schema has segment type
	 */
	public boolean isAcctSchemaElement (String segmentType)
	{
		return getAcctSchemaElement(segmentType) != null;
	}   //  isAcctSchemaElement

	/**
	 * 	Get AcctSchema GL info
	 *	@return GL info
	 */
	private final I_C_AcctSchema_GL getAcctSchemaGL()
	{
		if (_acctSchemaGL == null)
		{
			_acctSchemaGL = Services.get(IAcctSchemaDAO.class).retrieveAcctSchemaGL(getCtx(), getC_AcctSchema_ID());
		}
		return _acctSchemaGL;
	}	//	getAcctSchemaGL
	
	/**
	 * 	Get AcctSchema Defaults
	 *	@return defaults
	 */
	public final MAcctSchemaDefault getAcctSchemaDefault()
	{
		return acctSchemaDefaultSupplier.get();
	}

	private final Supplier<MAcctSchemaDefault> acctSchemaDefaultSupplier = Suppliers.memoize(new Supplier<MAcctSchemaDefault>()
	{
		@Override
		public MAcctSchemaDefault get()
		{
			final MAcctSchemaDefault acctSchemaDefault = MAcctSchemaDefault.get(getCtx(), getC_AcctSchema_ID());
			if(acctSchemaDefault == null)
			{
				throw new IllegalStateException("No Default Definition for C_AcctSchema_ID=" + getC_AcctSchema_ID());
			}
			return acctSchemaDefault;
		}
	});

	/**
	 *	String representation
	 *  @return String rep
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("AcctSchema[");
			sb.append(get_ID()).append("-").append(getName())
				.append("]");
		return sb.toString();
	}	//	toString

	/**
	 * 	Is Suspense Balancing active
	 *	@return suspense balancing
	 */
	public boolean isSuspenseBalancing()
	{
		final I_C_AcctSchema_GL m_gl = getAcctSchemaGL();
		return m_gl.isUseSuspenseBalancing() && m_gl.getSuspenseBalancing_Acct() > 0;
	}	//	isSuspenseBalancing

	/**
	 *	Get Suspense Error Account
	 *  @return suspense error account
	 */
	public MAccount getSuspenseBalancing_Acct()
	{
		if (m_SuspenseError_Acct != null)
			return m_SuspenseError_Acct;
		final I_C_AcctSchema_GL m_gl = getAcctSchemaGL();
		int C_ValidCombination_ID = m_gl.getSuspenseBalancing_Acct();
		m_SuspenseError_Acct = MAccount.get(getCtx(), C_ValidCombination_ID);
		return m_SuspenseError_Acct;
	}	//	getSuspenseBalancing_Acct

	/**
	 * 	Is Currency Balancing active
	 *	@return suspense balancing
	 */
	public boolean isCurrencyBalancing()
	{
		final I_C_AcctSchema_GL m_gl = getAcctSchemaGL();
		return m_gl.isUseCurrencyBalancing();
	}	//	isSuspenseBalancing

	/**
	 *	Get Currency Balancing Account
	 *  @return currency balancing account
	 */
	public MAccount getCurrencyBalancing_Acct()
	{
		if (m_CurrencyBalancing_Acct != null)
			return m_CurrencyBalancing_Acct;
		final I_C_AcctSchema_GL m_gl = getAcctSchemaGL();
		int C_ValidCombination_ID = m_gl.getCurrencyBalancing_Acct();
		m_CurrencyBalancing_Acct = MAccount.get(getCtx(), C_ValidCombination_ID);
		return m_CurrencyBalancing_Acct;
	}	//	getCurrencyBalancing_Acct


	/**
	 *	Get Due To Account for Segment
	 *  @param segment ignored
	 *  @return Account
	 */
	public MAccount getDueTo_Acct(String segment)
	{
		if (m_DueTo_Acct != null)
			return m_DueTo_Acct;
		final I_C_AcctSchema_GL m_gl = getAcctSchemaGL();
		int C_ValidCombination_ID = m_gl.getIntercompanyDueTo_Acct();
		m_DueTo_Acct = MAccount.get(getCtx(), C_ValidCombination_ID);
		return m_DueTo_Acct;
	}	//	getDueTo_Acct

	/**
	 *	Get Due From Account for Segment
	 *  @param segment ignored
	 *  @return Account
	 */
	public MAccount getDueFrom_Acct(String segment)
	{
		if (m_DueFrom_Acct != null)
			return m_DueFrom_Acct;
		final I_C_AcctSchema_GL m_gl = getAcctSchemaGL();
		int C_ValidCombination_ID = m_gl.getIntercompanyDueFrom_Acct();
		m_DueFrom_Acct = MAccount.get(getCtx(), C_ValidCombination_ID);
		return m_DueFrom_Acct;
	}	//	getDueFrom_Acct

	/**
	 * Set Only Org Childs
	 * @param orgs
	 * @deprecated only orgs are now fetched automatically
	 * @throws IllegalStateException every time when you call it 
	 */
	@Deprecated
	public void setOnlyOrgs (Integer[] orgs)
	{
//		m_onlyOrgs = orgs;
		throw new IllegalStateException("The OnlyOrgs are now fetched automatically");
	}	//	setOnlyOrgs
	
	/**
	 * Get Only Org Children
	 * @return array of AD_Org_ID
	 */
	public Integer[] getOnlyOrgs()
	{
		if (m_onlyOrgs == null)
		{
			m_onlyOrgs = MReportTree.getChildIDs(getCtx(), 
					0, MAcctSchemaElement.ELEMENTTYPE_Organization, 
					getAD_OrgOnly_ID());
		}
		return m_onlyOrgs;
	}	//	getOnlyOrgs

	/**
	 * 	Skip creating postings for this Org.
	 *	@param AD_Org_ID
	 *	@return true if to skip
	 */
	public boolean isSkipOrg (int AD_Org_ID)
	{
		if (getAD_OrgOnly_ID() == 0)
		{
			return false;
		}
		//	Only Organization
		if (getAD_OrgOnly_ID() == AD_Org_ID)
		{
			return false;
		}
		if (m_onlyOrg == null)
		{
			m_onlyOrg = MOrg.get(getCtx(), getAD_OrgOnly_ID());
		}
		//	Not Summary Only - i.e. skip it
		if (!m_onlyOrg.isSummary())
		{
			return true;
		}
		final Integer[] onlyOrgs = getOnlyOrgs();
		if (onlyOrgs == null)
		{
			return false;
		}
		for (int i = 0; i < onlyOrgs.length; i++)
		{
			if (AD_Org_ID == onlyOrgs[i].intValue())
			{
				return false;
			}
		}
		return true;
	}	//	isSkipOrg
	
	public boolean isAllowPostingForOrg(final int adOrgId)
	{
		return !isSkipOrg(adOrgId);
	}
	
	/**
	 * 	Get Std Precision of accounting Currency
	 *	@return precision
	 */
	public int getStdPrecision()
	{
		if (m_stdPrecision < 0)
		{
			final I_C_Currency cur = Services.get(ICurrencyDAO.class).retrieveCurrency(getCtx(), getC_Currency_ID());
			m_stdPrecision = cur.getStdPrecision();
			m_costPrecision = cur.getCostingPrecision();
		}
		return m_stdPrecision;
	}	//	getStdPrecision

	/**
	 * 	Get Costing Precision of accounting Currency
	 *	@return precision
	 */
	public int getCostingPrecision()
	{
		if (m_costPrecision < 0)
			getStdPrecision();
		return m_costPrecision;
	}	//	getCostingPrecision
	
	/**
	 * 	Create PO Commitment Accounting
	 *	@return true if creaet commitments
	 */
	public boolean isCreatePOCommitment()
	{
		String s = getCommitmentType();
		if (s == null)
			return false;
		return COMMITMENTTYPE_POCommitmentOnly.equals(s)
			|| COMMITMENTTYPE_POCommitmentReservation.equals(s)
			|| COMMITMENTTYPE_POSOCommitmentReservation.equals(s)
			|| COMMITMENTTYPE_POSOCommitment.equals(s);
	}	//	isCreateCommitment

	/**
	 * 	Create SO Commitment Accounting
	 *	@return true if creaet commitments
	 */
	public boolean isCreateSOCommitment()
	{
		String s = getCommitmentType();
		if (s == null)
			return false;
		return COMMITMENTTYPE_SOCommitmentOnly.equals(s)
			|| COMMITMENTTYPE_POSOCommitmentReservation.equals(s)
			|| COMMITMENTTYPE_POSOCommitment.equals(s);
	}	//	isCreateCommitment

	/**
	 * 	Create Commitment/Reservation Accounting
	 *	@return true if create reservations
	 */
	public boolean isCreateReservation()
	{
		String s = getCommitmentType();
		if (s == null)
			return false;
		return COMMITMENTTYPE_POCommitmentReservation.equals(s)
			|| COMMITMENTTYPE_POSOCommitmentReservation.equals(s);
	}	//	isCreateReservation

	/**
	 * 	Get Tax Correction Type
	 *	@return tax correction type
	 */
	@Override
	public String getTaxCorrectionType()
	{
		if (super.getTaxCorrectionType() == null)	//	created 07/23/06 2.5.3d
			setTaxCorrectionType(isDiscountCorrectsTax() 
				? TAXCORRECTIONTYPE_Write_OffAndDiscount : TAXCORRECTIONTYPE_None);
		return super.getTaxCorrectionType ();
	}	//	getTaxCorrectionType
	
	/**
	 * 	Tax Correction
	 *	@return true if not none
	 */
	public boolean isTaxCorrection()
	{
		return !getTaxCorrectionType().equals(TAXCORRECTIONTYPE_None);
	}	//	isTaxCorrection
	
	/**
	 * 	Tax Correction for Discount
	 *	@return true if tax is corrected for Discount 
	 */
	public boolean isTaxCorrectionDiscount()
	{
		return getTaxCorrectionType().equals(TAXCORRECTIONTYPE_DiscountOnly)
			|| getTaxCorrectionType().equals(TAXCORRECTIONTYPE_Write_OffAndDiscount);
	}	//	isTaxCorrectionDiscount

	/**
	 * 	Tax Correction for WriteOff
	 *	@return true if tax is corrected for WriteOff 
	 */
	public boolean isTaxCorrectionWriteOff()
	{
		return getTaxCorrectionType().equals(TAXCORRECTIONTYPE_Write_OffOnly)
			|| getTaxCorrectionType().equals(TAXCORRECTIONTYPE_Write_OffAndDiscount);
	}	//	isTaxCorrectionWriteOff

	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if (getAD_Org_ID() != 0)
		{
			setAD_Org_ID(0);
		}
		if (super.getTaxCorrectionType() == null)
		{
			setTaxCorrectionType(isDiscountCorrectsTax() ? TAXCORRECTIONTYPE_Write_OffAndDiscount : TAXCORRECTIONTYPE_None);
		}
		
		if (getGAAP() == null)
			setGAAP(GAAP_InternationalGAAP);

		//	Check Primary
		if (getAD_OrgOnly_ID() != 0)
		{
			final I_AD_ClientInfo info = Services.get(IClientDAO.class).retrieveClientInfo(getCtx(), getAD_Client_ID());
			if (info.getC_AcctSchema1_ID() == getC_AcctSchema_ID())
			{
				setAD_OrgOnly_ID(0);
			}
		}
		return true;
	}	//	beforeSave
	
}	//	MAcctSchema
