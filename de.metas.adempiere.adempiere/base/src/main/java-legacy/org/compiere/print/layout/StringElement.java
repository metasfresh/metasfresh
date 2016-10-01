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
package org.compiere.print.layout;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.CharacterIterator;
import java.util.Properties;
import java.util.regex.Pattern;

import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.print.MPrintFormatItem;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.util.NamePair;
import org.compiere.util.Util;

/**
 *	String Form Print ELement.
 *  The input can be multiple lines. The first tab is expanded.
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: StringElement.java,v 1.2 2006/07/30 00:53:02 jjanke Exp $
 */
public class StringElement extends PrintElement
{
	/**
	 *	Standard Field Constructor.
	 *  Created in LayoutEngine
	 *  @param inText text
	 *  @param font font
	 *  @param paint paint
	 * 	@param ID optional ID (null if document)
	 *  @param translateText if true, check for optional text translation
	 */
	public StringElement (String inText, Font font, Paint paint, NamePair ID, boolean translateText)
	{
		super();
		log.trace("Text=" + inText + ", ID=" + ID + ", Translate=" + translateText);
		m_font = font;
		m_paint = paint;
		if (translateText)
		{
			int count = Util.getCount(inText, '@');
			if (count > 0 && count % 2 == 0)
			{
				m_originalString = inText;
				//	Translate it to get rough space (not correct context) = may be too small
				inText = Msg.parseTranslation(Env.getCtx(), m_originalString);
			}
		}
		m_ID = ID;
		String[] lines = Pattern.compile("$", Pattern.MULTILINE).split(inText);
		m_string_paper = new AttributedString[lines.length];
		m_string_view = new AttributedString[lines.length];
		for (int i = 0; i < lines.length; i++)
		{
			String line = Util.removeCRLF (lines[i]);
			m_string_paper[i] = new AttributedString(line);
			if (line.length() == 0)
				continue;
			log.trace(" - line=" + i + " - " + line);
			m_string_paper[i].addAttribute(TextAttribute.FONT, font);
			m_string_paper[i].addAttribute(TextAttribute.FOREGROUND, paint);
			if (m_ID != null && i == 0)		//	first line only - create special Attributed String
			{
				m_string_view[i] = new AttributedString(line);
				m_string_view[i].addAttribute(TextAttribute.FONT, font);
				int endIndex = line.length();
				m_string_view[i].addAttribute(TextAttribute.FOREGROUND, LINK_COLOR);
				m_string_view[i].addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_ONE_PIXEL, 0, endIndex);
			}
			else
				m_string_view[i] = m_string_paper[i];
		}
		//	Load Image
		waitForLoad(LayoutEngine.IMAGE_TRUE);
		waitForLoad(LayoutEngine.IMAGE_FALSE);
	}	//	StringElement

	/**
	 * 	Attributed String Constructor
	 * 	@param string attributed string
	 */
	public StringElement(AttributedString string)
	{
		super();
		m_string_paper = new AttributedString[] {string};
		m_string_view = m_string_paper;
	}	//	StringElement

	/**
	 *	Field Constructor.
	 *  Created in LayoutEngine
	 *  @param content text or boolean
	 *  @param font font
	 *  @param paint paint
	 * 	@param ID optional ID (null if document)
	 *  @param label optional label
	 * 	@param labelSuffix optional label suffix
	 */
	public StringElement (Object content, Font font, Paint paint, NamePair ID, String label, String labelSuffix)
	{
		super();
		log.trace("Label=" + label + "|" + labelSuffix
				+ ", Content=" + content + ", ID=" + ID);
		m_font = font;
		m_paint = paint;
		int startIndex = 0;
		int endOffset = 0;

		StringBuffer text = new StringBuffer();
		if (label != null && label.length() > 0)
		{
			text.append(label).append(" ");
			startIndex = label.length() + 1;
		}
		if (content instanceof Boolean)
			m_check = (Boolean)content;
		else
			text.append(content);
		if (labelSuffix != null && labelSuffix.length() > 0)
		{
			text.append(labelSuffix);
			endOffset = labelSuffix.length();
		}
		m_ID = ID;
		String[] lines = Pattern.compile("$", Pattern.MULTILINE).split(text);
		m_string_paper = new AttributedString[lines.length];
		m_string_view = new AttributedString[lines.length];
		for (int i = 0; i < lines.length; i++)
		{
			String line = Util.removeCRLF (lines[i]);
			m_string_paper[i] = new AttributedString(line);
			if (line.length() == 0)
				continue;
			log.trace(" - line=" + i + " - " + line);
			m_string_paper[i].addAttribute(TextAttribute.FONT, font);
			m_string_paper[i].addAttribute(TextAttribute.FOREGROUND, paint);
			if (m_ID != null && i == 0)		//	first line only - create special Attributed String
			{
				m_string_view[i] = new AttributedString(line);
				m_string_view[i].addAttribute(TextAttribute.FONT, font);
				m_string_view[i].addAttribute(TextAttribute.FOREGROUND, paint);
				int endIndex = line.length() - endOffset;
				if (endIndex > startIndex)
				{
					m_string_view[i].addAttribute (TextAttribute.FOREGROUND, LINK_COLOR, startIndex, endIndex);
					m_string_view[i].addAttribute (TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_ONE_PIXEL, startIndex, endIndex);
				}
			}
			else
				m_string_view[i] = m_string_paper[i];
		}
	}	//	StringElement


	/**	Actual Elements	- Viewer		*/
	private AttributedString[]	m_string_view = null;
	/** Actual Elements - Printer		*/
	private AttributedString[]	m_string_paper = null;
	/**	To be translated String			*/
	private String				m_originalString = null;
	/** Font used						*/
	private Font				m_font = null;
	/** Paint used						*/
	private Paint				m_paint = null;
	/**	Optional ID of String				*/
	private NamePair			m_ID = null;
	/** Optional CheckBox					*/
	private Boolean				m_check = null;

	/**
	 * 	Get optional ID
	 * 	@return ID or null
	 */
	public NamePair getID()
	{
		return m_ID;
	}	//	getID

	/**
	 * 	Get Original String
	 *	@return original (may be null)
	 */
	public String getOriginalString()
	{
		return m_originalString;
	}	//	getOrig

	/**
	 * 	Translate Context if required
	 *  If content is translated, the element needs to stay in the bounds
	 *  of the originally calculated size and need to align the field.
	 * 	@param ctx context
	 */
	@Override
	public void translate(Properties ctx)
	{
		if (m_originalString == null)
			return;
		String inText = Msg.parseTranslation(ctx, m_originalString);
	//	log.debug( "StringElement.translate", inText);
		String[] lines = Pattern.compile("$", Pattern.MULTILINE).split(inText);
		m_string_paper = new AttributedString[lines.length];
		for (int i = 0; i < lines.length; i++)
		{
			String line = Util.removeCRLF (lines[i]);
			m_string_paper[i] = new AttributedString(line);
			if (line.length() > 0)
			{
				m_string_paper[i].addAttribute(TextAttribute.FONT, m_font);
				m_string_paper[i].addAttribute(TextAttribute.FOREGROUND, m_paint);
			}
		}
		m_string_view = m_string_paper;
	}	//	translate


	/**************************************************************************
	 * 	Layout and Calculate Size.
	 * 	Set p_width & p_height
	 * 	@return Size
	 */
	@Override
	protected boolean calculateSize()
	{
		if (p_sizeCalculated)
			return true;
		//
		FontRenderContext frc = new FontRenderContext(null, true, true);
		TextLayout layout = null;
		p_height = 0f;
		p_width = 0f;

		//	No Limit
		if (p_maxWidth == 0f && p_maxHeight == 0f)
		{
			for (int i = 0; i < m_string_paper.length; i++)
			{
				AttributedCharacterIterator iter = m_string_paper[i].getIterator();
				if (iter.getBeginIndex() == iter.getEndIndex())
					continue;

				//	Check for Tab (just first)
				int tabPos = -1;
				for (char c = iter.first(); c != CharacterIterator.DONE && tabPos == -1; c = iter.next())
				{
					if (c == '\t')
						tabPos = iter.getIndex();
				}

				if (tabPos == -1)
				{
					layout = new TextLayout (iter, frc);
					p_height += layout.getAscent() + layout.getDescent() + layout.getLeading();
					if (p_width < layout.getAdvance())
						p_width = layout.getAdvance();
				}
				else	//	with tab
				{
					LineBreakMeasurer measurer = new LineBreakMeasurer(iter, frc);
					layout = measurer.nextLayout(9999, tabPos, false);
					p_height += layout.getAscent() + layout.getDescent() + layout.getLeading();
					float width = getTabPos (0, layout.getAdvance());
					layout = measurer.nextLayout(9999, iter.getEndIndex(), true);
					width += layout.getAdvance();
					if (p_width < width)
						p_width = width;
				}
			}	//	 for all strings

			//	Add CheckBox Size
			if (m_check != null)
			{
				p_width += LayoutEngine.IMAGE_SIZE.width;
				if (p_height < LayoutEngine.IMAGE_SIZE.height)
					p_height = LayoutEngine.IMAGE_SIZE.height;
			}
		}
		//	Size Limits
		else
		{
			p_width = p_maxWidth;
			for (int i = 0; i < m_string_paper.length; i++)
			{
				AttributedCharacterIterator iter = m_string_paper[i].getIterator();
				if (iter.getBeginIndex() == iter.getEndIndex())
					continue;

				LineBreakMeasurer measurer = new LineBreakMeasurer(iter, frc);
			//	System.out.println("StringLength=" + m_originalString.length() + " MaxWidth=" + p_maxWidth + " MaxHeight=" + p_maxHeight);
				while (measurer.getPosition() < iter.getEndIndex())
				{
					//	no need to expand tab space for limited space
					layout = measurer.nextLayout(p_maxWidth);
					float lineHeight = layout.getAscent() + layout.getDescent() + layout.getLeading();
				//	System.out.println("  LineWidth=" + layout.getAdvance() + "  LineHeight=" + lineHeight);
					if (p_maxHeight == -1f && i == 0)		//	one line only
						p_maxHeight = lineHeight;
					if (p_maxHeight == 0f || (p_height + lineHeight) <= p_maxHeight)
						p_height += lineHeight;
				}
			}	//	 for all strings

			//	Add CheckBox Size
			if (m_check != null)
			{
			//	p_width += LayoutEngine.IMAGE_SIZE.width;
				if (p_height < LayoutEngine.IMAGE_SIZE.height)
					p_height = LayoutEngine.IMAGE_SIZE.height;
			}
		//	System.out.println("  Width=" + p_width + "  Height=" + p_height);
		}
	//	System.out.println("StringElement.calculate size - Width="
	//		+ p_width + "(" + p_maxWidth + ") - Height=" + p_height + "(" + p_maxHeight + ")");

		//	Enlarge Size when aligned and max size is given
		if (p_FieldAlignmentType != null)
		{
			boolean changed = false;
			if (p_height < p_maxHeight)
			{
				p_height = p_maxHeight;
				changed = true;
			}
			if (p_width < p_maxWidth)
			{
				p_width = p_maxWidth;
				changed = true;
			}
		//	if (changed)
		//		System.out.println("StringElement.calculate size - Width="
		//			+ p_width + "(" + p_maxWidth + ") - Height=" + p_height + "(" + p_maxHeight + ")");
		}
		return true;
	}	//	calculateSize


	/**************************************************************************
	 * 	Get Drill Down value
	 * 	@param relativePoint relative Point
	 *  @param pageNo page number (ignored)
	 * 	@return if found query or null
	 */
	@Override
	public MQuery getDrillDown (Point relativePoint, int pageNo)
	{
		if (m_ID != null && getBounds().contains(relativePoint))
		{
			log.debug(toString());
			String columnName = MQuery.getZoomColumnName(m_ID.getName());
			String tableName = MQuery.getZoomTableName(columnName);
			Object code = m_ID.getID();
			if (m_ID instanceof KeyNamePair)
				code = new Integer(((KeyNamePair)m_ID).getKey());
			//
			MQuery query = new MQuery(tableName);
			query.addRestriction(columnName, Operator.EQUAL, code);
			return query;
		}
		return null;
	}	//	getDrillDown

	/**
	 * 	Get Drill Across value
	 * 	@param relativePoint relative Point
	 *  @param pageNo page number (ignored)
	 * 	@return null - not implemented
	 */
	@Override
	public MQuery getDrillAcross (Point relativePoint, int pageNo)
	{
	//	log.debug( "StringElement.getDrillAcross");
	//	if (getBounds().contains(relativePoint));
		return null;
	}	//	getDrillAcross


	/**************************************************************************
	 * 	Paint/Print.
	 *  Calculate actual Size.
	 *  The text is printed in the topmost left position - i.e. the leading is below the line
	 * 	@param g2D Graphics
	 *  @param pageStart top left Location of page
	 *  @param pageNo page number for multi page support (0 = header/footer) - ignored
	 *  @param ctx print context
	 *  @param isView true if online view (IDs are links)
	 */
	@Override
	public void paint (Graphics2D g2D, int pageNo, Point2D pageStart, Properties ctx, boolean isView)
	{
	//	log.trace( "StringElement.paint", "<" + m_originalString + "> " + p_pageLocation.x + "/" + p_pageLocation.y
	//		+ ", Clip=" + g2D.getClip()
	//		+ ", Translate=" + g2D.getTransform().getTranslateX() + "/" + g2D.getTransform().getTranslateY()
	//		+ ", Scale=" + g2D.getTransform().getScaleX() + "/" + g2D.getTransform().getScaleY()
	//		+ ", Shear=" + g2D.getTransform().getShearX() + "/" + g2D.getTransform().getShearY());
		Point2D.Double location = getAbsoluteLocation(pageStart);
		//
		if (m_originalString != null)
			translate(ctx);

		AttributedString aString = null;
		AttributedCharacterIterator iter = null;
		AttributedCharacterIterator iter2 = null;
		float xPos = (float)location.x;
		float yPos = (float)location.y;
		float yPen = 0f;
		float height = 0f;
		float width = 0f;
		//	for all lines
		for (int i = 0; i < m_string_paper.length; i++)
		{
			//	Get Text
			if (isView)
			{
				if (m_string_view[i] == null)
					continue;
				aString = m_string_view[i];
			}
			else
			{
				if (m_string_paper[i] == null)
					continue;
				aString = m_string_paper[i];
			}
			iter = aString.getIterator();
			//	Zero Length
			if (iter.getBeginIndex() == iter.getEndIndex())
				continue;


			//	Check for Tab (just first) and 16 bit characters
			int tabPos = -1;
			boolean is8Bit = true;
			for (char c = iter.first(); c != CharacterIterator.DONE; c = iter.next())
			{
				if (c == '\t' && tabPos == -1)
					tabPos = iter.getIndex();
				if (c > 255)
					is8Bit = false;
			}

			TextLayout layout = null;
			float xPen = xPos;

			//	No Limit
			if (p_maxWidth == 0f)
			{
				if (tabPos == -1)
				{
					layout = new TextLayout (iter, g2D.getFontRenderContext());
					yPen = yPos + layout.getAscent();
				//	layout.draw(g2D, xPen, yPen);
					g2D.setFont(m_font);
					g2D.setPaint(m_paint);
					g2D.drawString(iter, xPen, yPen);
					//
					yPos += layout.getAscent() + layout.getDescent() + layout.getLeading();
					if (width < layout.getAdvance())
						width = layout.getAdvance();
				}
				else	//	we have a tab
				{
					LineBreakMeasurer measurer = new LineBreakMeasurer(iter, g2D.getFontRenderContext());
					layout = measurer.nextLayout(9999, tabPos, false);
					float lineHeight_1 = layout.getAscent() + layout.getDescent() + layout.getLeading();
					yPen = yPos + layout.getAscent();
					layout.draw(g2D, xPen, yPen);		//	first part before tab
					xPen = getTabPos (xPos, layout.getAdvance());
					float lineWidth = xPen - xPos;
					layout = measurer.nextLayout(9999);//, iter.getEndIndex(), true);
					float lineHeight_2 = layout.getAscent() + layout.getDescent() + layout.getLeading();
					layout.draw(g2D, xPen, yPen);		//	second part after tab
					//
					yPos += Math.max(lineHeight_1, lineHeight_2);
					lineWidth += layout.getAdvance();
					if (width < lineWidth)
						width = lineWidth;
				}
				//	log.trace( "StringElement.paint - No Limit - " + location.x + "/" + yPos
				//		+ " w=" + layout.getAdvance() + ",h=" + lineHeight + ", Bounds=" + layout.getBounds());
			}
			//	Size Limits
			else
			{
				boolean fastDraw = LayoutEngine.s_FASTDRAW;
				if (fastDraw && !isView && !is8Bit)
					fastDraw = false;
				LineBreakMeasurer measurer = new LineBreakMeasurer(iter, g2D.getFontRenderContext());
				while (measurer.getPosition() < iter.getEndIndex())
				{
					if (tabPos == -1)
					{
						layout = measurer.nextLayout(p_maxWidth);
						if (measurer.getPosition() < iter.getEndIndex())
							fastDraw = false;
					}
					else	//	tab
					{
						fastDraw = false;
						layout = measurer.nextLayout(p_maxWidth, tabPos, false);
					}
					//	Line Height
					float lineHeight = layout.getAscent() + layout.getDescent() + layout.getLeading();
					if (p_maxHeight == -1f && i == 0)		//	one line only
						p_maxHeight = lineHeight;
					//	If we have hight left over
					if (p_maxHeight == 0f || (height + lineHeight) <= p_maxHeight)
					{
						yPen = (float)location.y + height + layout.getAscent();
						//	Tab in Text
						if (tabPos != -1)
						{
							layout.draw(g2D, xPen, yPen);	//	first part before tab
							xPen = getTabPos (xPos, layout.getAdvance());
							layout = measurer.nextLayout(p_width, iter.getEndIndex(), true);
							tabPos = -1;	//	reset (just one tab)
						}
						else if ((MPrintFormatItem.FIELDALIGNMENTTYPE_TrailingRight.equals(p_FieldAlignmentType) && layout.isLeftToRight())
							|| (MPrintFormatItem.FIELDALIGNMENTTYPE_LeadingLeft.equals(p_FieldAlignmentType) && !layout.isLeftToRight()))
							xPen += p_maxWidth - layout.getAdvance();
						else if (MPrintFormatItem.FIELDALIGNMENTTYPE_Center.equals(p_FieldAlignmentType))
							xPen += (p_maxWidth - layout.getAdvance()) / 2;
						else if (MPrintFormatItem.FIELDALIGNMENTTYPE_Block.equals(p_FieldAlignmentType) && measurer.getPosition() < iter.getEndIndex())
						{
							layout = layout.getJustifiedLayout(p_maxWidth);
							fastDraw = false;
						}
						if (fastDraw)
						{
							g2D.setFont(m_font);
							g2D.setPaint(m_paint);
							g2D.drawString(iter, xPen, yPen);
							height += lineHeight;
							break;
						}
						else
						{
							layout.draw(g2D, xPen, yPen);
						}
						height += lineHeight;
					//	log.trace( "StringElement.paint - Limit - " + xPen + "/" + yPen
					//		+ " w=" + layout.getAdvance() + ",h=" + lineHeight + ", Align=" + p_FieldAlignmentType + ", Max w=" + p_maxWidth + ",h=" + p_maxHeight + ", Bounds=" + layout.getBounds());
					}
				}
				width = p_maxWidth;
			}	//	size limits
		}	//	for all strings
		if (m_check != null)
		{
			int x = (int)(location.x + width + 1);
			int y = (int)(location.y);
			g2D.drawImage(m_check.booleanValue() ? LayoutEngine.IMAGE_TRUE : LayoutEngine.IMAGE_FALSE, x, y, this);
		}
	}	//	paint

	/**
	 * 	Get Tab Position.
	 *  The Tab position is relative to the string itself, not the absolute
	 *  position; i.e. to have the same tab position on a page, strings need
	 *  to start at the same position.
	 *  The Tab is rounded up to the next 30 dividable position.
	 * 	@param xPos starting x position
	 * 	@param length length of segment
	 * 	@return new x Position (xPos + length + tabSpace)
	 */
	private float getTabPos (float xPos, float length)
	{
		float retValue = xPos + length;
		int iLength = (int)Math.ceil(length);
		int tabSpace = iLength % 30;
		retValue += (30 - tabSpace);
		return retValue;
	}	//	getTabPos

	/**
	 * 	String Representation
	 * 	@return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("StringElement[");
		sb.append("Bounds=").append(getBounds())
			.append(",Height=").append(p_height).append("(").append(p_maxHeight)
			.append("),Width=").append(p_width).append("(").append(p_maxHeight)
			.append("),PageLocation=").append(p_pageLocation).append(" - ");
		for (int i = 0; i < m_string_paper.length; i++)
		{
			if (m_string_paper.length > 1)
				sb.append(Env.NL).append(i).append(":");
			AttributedCharacterIterator iter = m_string_paper[i].getIterator();
			for (char c = iter.first(); c != CharacterIterator.DONE; c = iter.next())
				sb.append(c);
		}
		if (m_ID != null)
			sb.append(",ID=(").append(m_ID.toStringX()).append(")");
		sb.append("]");
		return sb.toString();
	}	//	toString

}	//	StringElement
