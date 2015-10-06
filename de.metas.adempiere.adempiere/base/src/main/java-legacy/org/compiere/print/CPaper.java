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

import java.awt.Insets;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.util.Properties;

import javax.print.attribute.Attribute;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;

import org.compiere.util.CLogger;
import org.compiere.util.Language;
import org.compiere.util.Msg;

/**
 *	Adempiere Paper
 *
 *  Change log:
 *  <ul>
 *  <li>2009-02-10 - armen - [ 2580531 ] Custom Paper Support - https://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2580531&group_id=176962
 *  </ul>
 *   
 * 	@author 	Jorg Janke
 * 	@version 	$Id: CPaper.java,v 1.2 2006/07/30 00:53:02 jjanke Exp $
 */
public class CPaper extends Paper
{
	/**
	 * 	Constructor.
	 *  Derive Paper from PageForamt
	 * 	@param pf PageFormat
	 */
	public CPaper (PageFormat pf)
	{
		super();
		m_landscape = pf.getOrientation() != PageFormat.PORTRAIT;
		//	try to find MediaSize
		float x = (float)pf.getWidth();
		float y = (float)pf.getHeight();
		MediaSizeName msn = MediaSize.findMedia (x/72, y/72, MediaSize.INCH);
		MediaSize ms = null;
		if (msn == null)
			msn = MediaSize.findMedia (y/72, x/72, MediaSize.INCH);	//	flip it
		if (msn != null)
			ms = MediaSize.getMediaSizeForName(msn);
		setMediaSize(ms, m_landscape);
		//	set size directly
		setSize(pf.getWidth(), pf.getHeight());
		setImageableArea(pf.getImageableX(), pf.getImageableY(),
			pf.getImageableWidth(), pf.getImageableHeight());
	}	//	CPaper

	/**
	 * 	Constructor.
	 * 	Get Media Size from Default Language
	 *  @param landscape true if landscape, false if portrait
	 */
	public CPaper (boolean landscape)
	{
		this (Language.getLoginLanguage(), landscape);
	}	//	CPaper

	/**
	 * 	Constructor.
	 * 	Get Media Size from Language,
	 *  @param language language to derive media size
	 *  @param landscape true if landscape, false if portrait
	 */
	private CPaper (Language language, boolean landscape)
	{
		this (language.getMediaSize(), landscape);
	}	//	CPaper

	/**
	 * 	Detail Constructor 1/2 inch on all sides
	 * 	@param mediaSize media size
	 *  @param landscape true if landscape, false if portrait
	 */
	private CPaper (MediaSize mediaSize, boolean landscape)
	{
		this (mediaSize, landscape, 36, 36, 36, 36);
	}	//	CPaper

	/**
	 * 	Detail Constructor
	 * 	@param mediaSize media size
	 *  @param left x in 1/72 inch
	 *  @param top y in 1/72 inch
	 *  @param right right x in 1/72
	 *  @param bottom bottom y in 1/72
	 *  @param landscape true if landscape, false if portrait
	 */
	public CPaper (MediaSize mediaSize, boolean landscape,
		double left, double top, double right, double bottom)
	{
		super();
		setMediaSize (mediaSize, landscape);
		setImageableArea(left, top, getWidth()-left-right, getHeight()-top-bottom);
	}	//	CPaper

	/**	Media size						*/
	private MediaSize	m_mediaSize;
	/** Landscape flag					*/
	private boolean		m_landscape = false;
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(CPaper.class);

	/**
	 * 	Set Media Size
	 * 	@param mediaSize media size
	 *  @param landscape true if landscape, false if portrait
	 */
	public void setMediaSize (MediaSize mediaSize, boolean landscape)
	{
		if (mediaSize == null)
			throw new IllegalArgumentException("MediaSize is null");
		m_mediaSize = mediaSize;
		m_landscape = landscape;

		//	Get Sise in Inch * 72
		double width = m_mediaSize.getX (MediaSize.INCH) * 72;
		double height = m_mediaSize.getY (MediaSize.INCH) * 72;
		//	Set Size
		setSize (width, height);
		log.fine(mediaSize.getMediaSizeName() + ": " + m_mediaSize + " - Landscape=" + m_landscape);
	}	//	setMediaSize

	/**
	 * Get Media Size
	 * @return media size
	 */
	//AA Goodwill : Custom Paper Support
	public CPaper (double x, double y, int units,
			boolean landscape,
			double left, double top, double right, double bottom)
	{
		super();
		setMediaSize (x, y, units, landscape);
		setImageableArea(left, top, getWidth()-left-right, getHeight()-top-bottom);
	}
	
	/**
	 * Set Media Size
	 * @param x the value to which to set this <code>Paper</code> object's width
	 * @param y the value to which to set this <code>Paper</code> object's height
	 * @param units number of microns (see Size2DSyntax.INCH, Size2DSyntax.MM)
	 * @param landscape true if it's landscape format
	 * @see Paper#setSize(double, double)
	 */
	public void setMediaSize (double x, double y, int units, boolean landscape)
	{
		if (x == 0 || y == 0)
			throw new IllegalArgumentException("MediaSize is null");
		
		m_landscape = landscape;

		//	Get Sise in Inch * 72
		final double mult = (double)units / (double)Size2DSyntax.INCH * (double)72;
		final double width = x * mult;
		final double height = y * mult;
		//	Set Size
		setSize (width, height);
		log.fine("Width & Height" + ": " + x + "/" + y  + " - Landscape=" + m_landscape);
	}	//	setMediaSize
	//End Of AA Goodwill

	/**
	 * 	Get Media Size
	 * 	@return media size
	 */
	public MediaSizeName getMediaSizeName()
	{
		//AA Goodwill: Custom Paper Support
		if(m_mediaSize == null)
			return MediaSizeName.ISO_A4;
		//End Of AA Goodwill
		return m_mediaSize.getMediaSizeName();
	}	//	getMediaSizeName

	/**
	 * 	Get Media Size
	 * 	@return media size
	 */
	public MediaSize getMediaSize()
	{
		return m_mediaSize;
	}	//	getMediaSize

	/**
	 * 	Get Printable Media Area
	 * 	@return Printable Area
	 */
	public MediaPrintableArea getMediaPrintableArea()
	{
		MediaPrintableArea area = new MediaPrintableArea ((float)getImageableX()/72, (float)getImageableY()/72,
			(float)getImageableWidth()/72, (float)getImageableHeight()/72, MediaPrintableArea.INCH);
	//	log.fine( "CPaper.getMediaPrintableArea", area.toString(MediaPrintableArea.INCH, "\""));
		return area;
	}	//	getMediaPrintableArea

	/**
	 * 	Get Printable Media Area
	 * 	@param area Printable Area
	 */
	public void setMediaPrintableArea (MediaPrintableArea area)
	{
		int inch = MediaPrintableArea.INCH;
		log.fine(area.toString(inch, "\""));
		setImageableArea(area.getX(inch)*72, area.getY(inch)*72,
			area.getWidth(inch)*72, area.getHeight(inch)*72);
	}	//	setMediaPrintableArea

	/**
	 * 	Is Landscape
	 * 	@return true if landscape
	 */
	public boolean isLandscape()
	{
		return m_landscape;
	}	//	isLandscape

	/*************************************************************************/

	/**
	 * 	Show Dialog and Set Paper
	 *  @param job printer job
	 *  @return true if changed.
	 */
	public boolean pageSetupDialog(PrinterJob job)
	{
		PrintRequestAttributeSet prats = getPrintRequestAttributeSet();
		//	Page Dialog
		PageFormat pf = job.pageDialog(prats);
		setPrintRequestAttributeSet(prats);
		return true;
	}	//	pageSetupDialog

	/**
	 *  Return Print Request Attributes
	 *  @return PrintRequestAttributeSet
	 */
	public PrintRequestAttributeSet getPrintRequestAttributeSet()
	{
		PrintRequestAttributeSet pratts = new HashPrintRequestAttributeSet();
		//	media-printable-area = (25.4,25.4)->(165.1,228.6)mm - class javax.print.attribute.standard.MediaPrintableArea
		pratts.add(getMediaPrintableArea());
		//	orientation-requested = landscape - class javax.print.attribute.standard.OrientationRequested
		if (isLandscape())
			pratts.add(OrientationRequested.LANDSCAPE);
		else
			pratts.add(OrientationRequested.PORTRAIT);
		//	media = na-legal
		//Commented Lines By AA Goodwill: Custom Paper Support 
		//pratts.add(getMediaSizeName());
		//End Of AA Goodwill

		return pratts;
	}   //  getPrintRequestAttributes

	/**
	 *  Set Print Request Attributes
	 *  @param prats PrintRequestAttributeSet
	 */
	public void setPrintRequestAttributeSet (PrintRequestAttributeSet prats)
	{
		boolean landscape = m_landscape;
		MediaSize ms = m_mediaSize;
		MediaPrintableArea area = getMediaPrintableArea();

		Attribute[] atts = prats.toArray();
		for (int i = 0; i < atts.length; i++)
		{
			if (atts[i] instanceof OrientationRequested)
			{
				OrientationRequested or = (OrientationRequested)atts[i];
				if (or.equals(OrientationRequested.PORTRAIT))
					landscape = false;
				else
					landscape = true;
			}
			else if (atts[i] instanceof MediaSizeName)
			{
				MediaSizeName msn = (MediaSizeName)atts[i];
				ms = MediaSize.getMediaSizeForName(msn);
			}
			else if (atts[i] instanceof MediaPrintableArea)
			{
				area = (MediaPrintableArea)atts[i];
			}
			else	//	unhandeled
				System.out.println(atts[i].getName() + " = " + atts[i] + " - " + atts[i].getCategory());
		}
		//
		setMediaSize(ms, landscape);
		setMediaPrintableArea(area);
	}   //  getPrintRequestAttributes


	/*************************************************************************/

	/**
	 * 	Get the Page Format for the Papaer
	 * 	@return Page Format
	 */
	public PageFormat getPageFormat()
	{
		PageFormat pf = new PageFormat();
		pf.setPaper(this);
		int orient = PageFormat.PORTRAIT;
		if (m_landscape)
			orient = PageFormat.LANDSCAPE;
		pf.setOrientation(orient);
		return pf;
	}	//	getPageFormat

	/*************************************************************************/

	/**
	 * 	Get String Representation
	 * 	@return info
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer("CPaper[");
		sb.append(getWidth()/72).append("x").append(getHeight()/72).append('"')
			.append(m_landscape ? " Landscape " : " Portrait ")
			.append("x=").append(getImageableX())
			.append(",y=").append(getImageableY())
			.append(" w=").append(getImageableWidth())
			.append(",h=").append(getImageableHeight())
			.append("]");
		return sb.toString();
	}	//	toString

	/**
	 * 	Get "nice" String Representation
	 *  @param ctx context
	 * 	@return info
	 */
	public String toString (Properties ctx)
	{
		StringBuffer sb = new StringBuffer();
		//	Print Media size
		//AA Goodwill : Custom Paper Support
		if (m_mediaSize != null){
			sb.append(m_mediaSize.getMediaSizeName());
			//	Print dimension
			String name = m_mediaSize.getMediaSizeName().toString();
			if (!name.startsWith("iso"))
				sb.append(" - ").append(m_mediaSize.toString(MediaSize.INCH,"\""))
					.append(" (").append(getMediaPrintableArea().toString(MediaPrintableArea.INCH,"\""));
			if (!name.startsWith("na"))
				sb.append(" - ").append(m_mediaSize.toString(MediaSize.MM,"mm"))
					.append(" (").append(getMediaPrintableArea().toString(MediaPrintableArea.MM,"mm"));
			//	Print Orientation
			sb.append(") - ")
				.append(Msg.getMsg(ctx, m_landscape ? "Landscape" : "Portrait"));
		}		
		else sb.append("Custom - ").append(toString());
		//End Of AA Goodwill
		return sb.toString();
	}	//	toString

	/**
	 * 	Equals
	 * 	@param obj compare
	 * 	@return true if equal
	 */
	public boolean equals (Object obj)
	{
		if (obj instanceof CPaper)
		{
			CPaper cp = (CPaper)obj;
			if (cp.isLandscape() != m_landscape)
				return false;
			//	media size is more descriptive
			if (getImageableX() == cp.getImageableX() && getImageableY() == cp.getImageableY()
				&& getImageableWidth() == cp.getImageableWidth() && getImageableHeight() == cp.getImageableHeight())
				return true;
		}
		return false;
	}	//	equals

	/*************************************************************************/

	/**
	 * 	Get Width in 1/72 inch
	 * 	@param orientationCorrected correct for orientation
	 * 	@return width
	 */
	public double getWidth (boolean orientationCorrected)
	{
		if (orientationCorrected && m_landscape)
			return super.getHeight();
		return super.getWidth();
	}

	/**
	 * 	Get Height in 1/72 inch
	 * 	@param orientationCorrected correct for orientation
	 * 	@return height
	 */
	public double getHeight (boolean orientationCorrected)
	{
		if (orientationCorrected && m_landscape)
			return super.getWidth();
		return super.getHeight();
	}

	/**
	 * 	Get Image Y in 1/72 inch
	 * 	@param orientationCorrected correct for orientation
	 * 	@return imagable Y
	 */
	public double getImageableY (boolean orientationCorrected)
	{
		if (orientationCorrected && m_landscape)
			return super.getImageableX();
		return super.getImageableY();
	}

	/**
	 * 	Get Image X in 1/72 inch
	 * 	@param orientationCorrected correct for orientation
	 * 	@return imagable X
	 */
	public double getImageableX (boolean orientationCorrected)
	{
		if (orientationCorrected && m_landscape)
			return super.getImageableY();
		return super.getImageableX();
	}

	/**
	 * 	Get Image Height in 1/72 inch
	 * 	@param orientationCorrected correct for orientation
	 * 	@return imagable height
	 */
	public double getImageableHeight (boolean orientationCorrected)
	{
		if (orientationCorrected && m_landscape)
			return super.getImageableWidth();
		return super.getImageableHeight();
	}
	/**
	 * 	Get Image Width in 1/72 inch
	 * 	@param orientationCorrected correct for orientation
	 * 	@return imagable width
	 */
	public double getImageableWidth (boolean orientationCorrected)
	{
		if (orientationCorrected && m_landscape)
			return super.getImageableHeight();
		return super.getImageableWidth();
	}

	/**
	 * 	Get Margin
	 * 	@param orientationCorrected correct for orientation
	 * 	@return margin
	 */
	public Insets getMargin (boolean orientationCorrected)
	{
		return new Insets ((int)getImageableY(orientationCorrected),	//	top
			(int)getImageableX(orientationCorrected),					//	left
			(int)(getHeight(orientationCorrected)-getImageableY(orientationCorrected)-getImageableHeight(orientationCorrected)),	//	bottom
			(int)(getWidth(orientationCorrected)-getImageableX(orientationCorrected)-getImageableWidth(orientationCorrected)));	//	right
	}	//	getMargin

}	//	CPapaer
