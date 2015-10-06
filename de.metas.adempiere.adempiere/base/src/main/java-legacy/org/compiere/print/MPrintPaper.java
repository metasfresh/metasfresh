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
package org.compiere.print;

import java.sql.ResultSet;
import java.util.Properties;

import javax.print.attribute.EnumSyntax;
import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.PO;
import org.compiere.model.X_AD_PrintPaper;
import org.compiere.util.CCache;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Language;

/**
 *	AD_PrintPaper Print Paper Model
 *
 *  Change log:
 *  <ul>
 *  <li>2009-02-10 - armen - [ 2580531 ] Custom Paper Support - https://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2580531&group_id=176962
 *  </ul>
 *  
 * 	@author 	Jorg Janke
 * 	@version 	$Id: MPrintPaper.java,v 1.3 2006/07/30 00:53:02 jjanke Exp $
 * 
 * @author Teo Sarca
 * 			<li>FR [ 2829019 ] Check PrintPaper on save
 * 			https://sourceforge.net/tracker/?func=detail&aid=2829019&group_id=176962&atid=879335
 */
public class MPrintPaper extends X_AD_PrintPaper
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8284757169766494111L;


	/**
	 * 	Get Paper
	 * 	@param AD_PrintPaper_ID id
	 * 	@return Paper
	 */
	static public MPrintPaper get (int AD_PrintPaper_ID)
	{
		Integer key = new Integer(AD_PrintPaper_ID);
		MPrintPaper pp = (MPrintPaper)s_papers.get(key);
		if (pp == null)
		{
			pp = new MPrintPaper (Env.getCtx(), AD_PrintPaper_ID, null);
			s_papers.put(key, pp);
		}
		else
			s_log.config("AD_PrintPaper_ID=" + AD_PrintPaper_ID);
		return pp;
	}	//	get

	/**
	 * 	Create Paper and save
	 * 	@param name name
	 * 	@param landscape landscape
	 * 	@return Paper
	 */
	static MPrintPaper create (String name, boolean landscape)
	{
		MPrintPaper pp = new MPrintPaper (Env.getCtx(), 0, null);
		pp.setName(name);
		pp.setIsLandscape(landscape);
		pp.save();
		return pp;
	}	//	create

	/**	Logger				*/
	private static CLogger s_log = CLogger.getCLogger(MPrintPaper.class);
	/** Cached Fonts						*/
	static private CCache<Integer,MPrintPaper> s_papers 
		= new CCache<Integer,MPrintPaper>("AD_PrintPaper", 5);
	
	
	/**************************************************************************
	 *	Constructor
	 *  @param ctx context
	 *  @param AD_PrintPaper_ID ID if 0 A4
	 *  @param trxName transaction
	 */
	public MPrintPaper(Properties ctx, int AD_PrintPaper_ID, String trxName)
	{
		super(ctx, AD_PrintPaper_ID, trxName);
		if (AD_PrintPaper_ID == 0)
		{
			setIsDefault (false);
			setIsLandscape (true);
			setCode ("iso-a4");
			setMarginTop (36);
			setMarginBottom (36);
			setMarginLeft (36);
			setMarginRight (36);
		}
	}	//	MPrintPaper
	
	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MPrintPaper (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MPrintPaper


	/** Media Size			*/
	private MediaSize		m_mediaSize = null;

	/**************************************************************************
	 * 	Get Media Size.
	 *  The search is hard coded as the javax.print.MediaSize* info is private
	 * 	@return MediaSize from Code
	 */
	public MediaSize getMediaSize()
	{
		if (m_mediaSize != null)
			return m_mediaSize;
		//
		String nameCode = getCode();
		if (nameCode != null)
		{
			//	Get Name
			MediaSizeName nameMedia = null;
			CMediaSizeName msn = new CMediaSizeName(4);
			String[] names = msn.getStringTable();
			for (int i = 0; i < names.length; i++)
			{
				String name = names[i];
				if (name.equalsIgnoreCase(nameCode))
				{
					nameMedia = (MediaSizeName)msn.getEnumValueTable()[i];
					log.finer("Name=" + nameMedia);
					break;
				}
			}
			if (nameMedia != null)
			{
				m_mediaSize = MediaSize.getMediaSizeForName(nameMedia);
				log.fine("Name->Size=" + m_mediaSize);
			}
		}
		//	Create New Media Size
		if (m_mediaSize == null)
		{
			float x = getSizeX().floatValue();
			float y = getSizeY().floatValue();
			if (x > 0 && y > 0)
			{
				m_mediaSize = new MediaSize(x, y, getUnitsInt(), MediaSizeName.A);
				log.fine("Size=" + m_mediaSize);
			}
		}
		//	Fallback
		if (m_mediaSize == null)
				m_mediaSize = getMediaSizeDefault();
		return m_mediaSize;
	}	//	getMediaSize

	/**
	 * 	Get Media Size
	 * 	@return Default Media Size based on Language
	 */
	public MediaSize getMediaSizeDefault()
	{
		m_mediaSize = Language.getLoginLanguage().getMediaSize();
		if (m_mediaSize == null)
			m_mediaSize = MediaSize.ISO.A4;
		log.fine("Size=" + m_mediaSize);
		return m_mediaSize;
	}	//	getMediaSizeDefault

	/**
	 * 	Get Units Int
	 *	@return units
	 */
	public int getUnitsInt()
	{
		String du = getDimensionUnits();
		if (du == null || DIMENSIONUNITS_MM.equals(du))
			return Size2DSyntax.MM;
		else if (DIMENSIONUNITS_Inch.equals(du))
			return Size2DSyntax.INCH; 
		else
			throw new AdempiereException("@NotSupported@ @DimensionUnit@ : "+du);
	}	//	getUnits
	
	/**
	 * 	Get CPaper
	 * 	@return CPaper
	 */
	public CPaper getCPaper()
	{
		//Modify Lines By AA Goodwill : Custom Paper Support 
		CPaper retValue;
		if (getCode().toLowerCase().startsWith("custom"))
		{
			retValue = new CPaper (getSizeX().doubleValue(), getSizeY().doubleValue(), getUnitsInt(),
					isLandscape(),
					getMarginLeft(), getMarginTop(), getMarginRight(), getMarginBottom());			
		}
		else
		{
			retValue = new CPaper (getMediaSize(), isLandscape(),
					getMarginLeft(), getMarginTop(), getMarginRight(), getMarginBottom());
		}
		//End Of AA Goodwill
		return retValue;
	}	//	getCPaper
	
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		// Check all settings are correct by reload all data
		m_mediaSize = null;
		getMediaSize();
		getCPaper();
		
		return true;
	}



	/**
	 * 	Media Size Name 
	 */
	class CMediaSizeName extends MediaSizeName
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 8561532175435930293L;

		/**
		 * 	CMediaSizeName
		 *	@param code
		 */
	    public CMediaSizeName(int code) 
	    {
	    	super (code);
	    }	//	CMediaSizeName

		/**
		 * 	Get String Table
		 *	@return string
		 */
		public String[] getStringTable ()
		{
			return super.getStringTable ();
		}
		
		/**
		 * 	Get Enum Value Table
		 *	@return Media Sizes
		 */
		public EnumSyntax[] getEnumValueTable ()
		{
			return super.getEnumValueTable ();
		}
	}	//	CMediaSizeName	
	
	/**************************************************************************
	 * 	Test
	 * 	@param args args
	 */
	public static void main(String[] args)
	{
		org.compiere.Adempiere.startupEnvironment(true);

	//	create ("Standard Landscape", true);
	//	create ("Standard Portrait", false);

		//	Read All Papers
		int[] IDs = PO.getAllIDs ("AD_PrintPaper", null, null);
		for (int i = 0; i < IDs.length; i++)
		{
			System.out.println("--");
			MPrintPaper pp = new MPrintPaper(Env.getCtx(), IDs[i], null);
			pp.dump();
		}

	}
}	//	MPrintPaper
