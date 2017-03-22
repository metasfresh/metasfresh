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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Pattern;

import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.print.MPrintFormatItem;
import org.compiere.print.MPrintTableFormat;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.Util;
import org.compiere.util.ValueNamePair;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.output.OutputException;

/**
 * Table Print Element. Maintains a logical cross page table, which is
 * "broken up" when printing
 * 
 * <pre>
 *  The table is 3 pages wide, 2 pages high
 * 		+-----+-----+-----+
 *      | 1.1 | 1.2 | 1.3 |
 * 		+-----+-----+-----+
 *      | 2.1 | 2.2 | 2.3 |
 * 		+-----+-----+-----+
 *  Printed
 * 		+-----+-----+-----+
 *      |  1  |  2  |  3  |
 * 		+-----+-----+-----+
 *      |  4  |  5  |  6  |
 * 		+-----+-----+-----+
 * </pre>
 * 
 * @author Jorg Janke
 * @version $Id: TableElement.java,v 1.2 2006/07/30 00:53:02 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL <li>FR [ 1803359 ] Migrate to barbecue 1.1
 */
public class TableElement extends PrintElement
{
	/**
	 * Constructor. Created in LayoutEngine. The rowCol.. maps are organized as
	 * follows - Point (row,col) row - data if 0..m - if -1 for the entire
	 * column column - data if 0..n - if -1 for the entire row i.e. Point (-1,
	 * -1) is the default for the table
	 *
	 * @param columnHeader
	 *            array with column headers (Key=ColumnName)
	 * @param columnMaxWidth
	 *            array with column max width - 0=no restrictions -
	 *            negative=supress if null
	 * @param columnMaxHeight
	 *            array with row max height for a column - 0=no restrictions;
	 *            -1=one row only
	 * @param columnJustification
	 *            field justification for column
	 *
	 * @param fixedWidth
	 *            array with column fixed width
	 * @param functionRows
	 *            list of function rows
	 * @param multiLineHeader
	 *            if true, the header is not truncated at maxWidth
	 *
	 * @param data
	 *            2D array with data to be printed [row][col]
	 * @param pk
	 *            array of primary keys
	 * @param pkColumnName
	 *            primary key name
	 *
	 * @param pageNoStart
	 *            page number of starting page
	 * @param firstPage
	 *            bounds on first page
	 * @param nextPages
	 *            bounds on following pages
	 * @param repeatedColumns
	 *            repeat first x columns on - X Axis follow pages
	 * @param additionalLines
	 *            map of old colum to below printed column
	 *
	 * @param rowColFont
	 *            HashMap with Point as key with Font overwrite
	 * @param rowColColor
	 *            HashMap with Point as key with foreground Color overwrite
	 * @param rowColBackground
	 *            HashMap with Point as key with background Color overwrite
	 * @param tFormat
	 *            table format
	 * @param pageBreak
	 *            Arraylist of rows with page break
	 */
	public TableElement(ValueNamePair[] columnHeader, int[] columnMaxWidth,
			int[] columnMaxHeight, String[] columnJustification,
			boolean[] fixedWidth, ArrayList<Integer> functionRows,
			boolean multiLineHeader, Object[][] data, KeyNamePair[] pk,
			String pkColumnName, int pageNoStart, Rectangle firstPage,
			Rectangle nextPages, int repeatedColumns,
			HashMap<Integer, Integer> additionalLines,
			HashMap<Point, Font> rowColFont, HashMap<Point, Color> rowColColor,
			HashMap<Point, Color> rowColBackground, MPrintTableFormat tFormat,
			ArrayList<Integer> pageBreak, boolean[] colSuppressRepeats)
	{
		super();
		log.debug("Cols=" + columnHeader.length + ", Rows=" + data.length);
		m_colSuppressRepeats = colSuppressRepeats;
		m_columnHeader = columnHeader;
		m_columnMaxWidth = columnMaxWidth;
		m_columnMaxHeight = columnMaxHeight;
		m_columnJustification = columnJustification;
		m_functionRows = functionRows;
		m_fixedWidth = fixedWidth;
		//
		m_multiLineHeader = multiLineHeader;
		m_data = data;
		m_pk = pk;
		m_pkColumnName = pkColumnName;
		//
		m_pageNoStart = pageNoStart;
		m_firstPage = firstPage;
		m_nextPages = nextPages;
		m_repeatedColumns = repeatedColumns;
		m_additionalLines = additionalLines;
		// Used Fonts,Colots
		Point pAll = new Point(ALL, ALL);
		m_rowColFont = rowColFont;
		m_baseFont = m_rowColFont.get(pAll);
		if (m_baseFont == null)
			m_baseFont = new Font(null);
		m_rowColColor = rowColColor;
		m_baseColor = m_rowColColor.get(pAll);
		if (m_baseColor == null)
			m_baseColor = Color.black;
		m_rowColBackground = rowColBackground;
		m_baseBackground = m_rowColBackground.get(pAll);
		if (m_baseBackground == null)
			m_baseBackground = Color.white;
		m_tFormat = tFormat;

		// Page Break - not two after each other
		m_pageBreak = pageBreak;
		for (int i = 0; i < m_pageBreak.size(); i++)
		{
			Integer row = m_pageBreak.get(i);
			while ((i + 1) < m_pageBreak.size())
			{
				Integer nextRow = m_pageBreak.get(i + 1);
				if ((row.intValue() + 1) == nextRow.intValue())
				{
					log.debug("- removing PageBreak row=" + row);
					m_pageBreak.remove(i);
					row = nextRow;
				}
				else
					break;
			}
		} // for all page breaks

		// Load Image
		waitForLoad(LayoutEngine.IMAGE_TRUE);
		waitForLoad(LayoutEngine.IMAGE_FALSE);
	} // TableElement

	/** Column Headers */
	private ValueNamePair[] m_columnHeader;
	/** Max column widths */
	private int[] m_columnMaxWidth;
	/** Max row height per column */
	private int[] m_columnMaxHeight;
	/** Field Justification for Column */
	private String[] m_columnJustification;
	/** True if column fixed length */
	private boolean[] m_fixedWidth;
	/** Create multiple header lines if required */
	private boolean m_multiLineHeader;
	/** List of Function Rows */
	private ArrayList<Integer> m_functionRows;
	/** The Data */
	private Object[][] m_data;
	/** Primary Keys */
	private KeyNamePair[] m_pk;
	/** Primary Key Column Name */
	private String m_pkColumnName;
	/** Starting page Number */
	private int m_pageNoStart;
	/** Bounds of first Page */
	private Rectangle m_firstPage;
	/** Bounds of next Pages */
	private Rectangle m_nextPages;

	private boolean[] m_colSuppressRepeats;

	/** repeat first x columns on - X Axis follow pages */
	private int m_repeatedColumns;

	/** base font for table */
	private Font m_baseFont;
	/** HashMap with Point as key with Font overwrite */
	private HashMap<Point, Font> m_rowColFont;
	/** base foreground color for table */
	private Color m_baseColor;
	/** HashMap with Point as key with foreground Color overwrite */
	private HashMap<Point, Color> m_rowColColor;
	/** base color for table */
	private Color m_baseBackground;
	/** HashMap with Point as key with background Color overwrite */
	private HashMap<Point, Color> m_rowColBackground;
	/** Format of Table */
	private MPrintTableFormat m_tFormat;
	/** Page Break Rows */
	private ArrayList<Integer> m_pageBreak;

	/** width of columns (float) */
	private ArrayList<Float> m_columnWidths = new ArrayList<Float>();
	/** height of rows (float) */
	private ArrayList<Float> m_rowHeights = new ArrayList<Float>();
	/** height of header */
	private int m_headerHeight = 0;

	/** first data row number per page */
	private ArrayList<Integer> m_firstRowOnPage = new ArrayList<Integer>();
	/** first column number per -> page */
	private ArrayList<Integer> m_firstColumnOnPage = new ArrayList<Integer>();
	/** Height of page */
	private ArrayList<Float> m_pageHeight = new ArrayList<Float>();

	/** Key: Point(row,col) - Value: NamePair */
	private HashMap<Point, NamePair> m_rowColDrillDown = new HashMap<Point, NamePair>();

	/** Key: Integer (original Column) - Value: Integer (below column) */
	private HashMap<Integer, Integer> m_additionalLines;

	/*************************************************************************/

	/** Header Row Indicator */
	public static final int HEADER_ROW = -2;
	/** Header Row Indicator */
	public static final int ALL = -1;

	/** Horizontal - GAP between text & line */
	private static final int H_GAP = 0;
	/** Vertical | GAP between text & line */
	private static final int V_GAP = 0;

	/** Debug Print Paint */
	private static final boolean DEBUG_PRINT = false;

	/**************************************************************************
	 * Layout and Calculate Size. Set p_width & p_height
	 * 
	 * @return true if calculated
	 */
	@Override
	protected boolean calculateSize()
	{
		if (p_sizeCalculated)
			return true;

		p_width = 0;
		m_printRows = new ArrayList<ArrayList<ArrayList<Object>>>(m_data.length); // reset

		// Max Column Width = 50% of available width (used if maxWidth not set)
		float dynMxColumnWidth = m_firstPage.width / 2;

		// Width caolculation
		int rows = m_data.length;
		int cols = m_columnHeader.length;
		// Data Sizes and Header Sizes
		Dimension2DImpl[][] dataSizes = new Dimension2DImpl[rows][cols];
		Dimension2DImpl[] headerSizes = new Dimension2DImpl[cols];
		FontRenderContext frc = new FontRenderContext(null, true, true);

		// data columns - rows
		for (int dataCol = 0; dataCol < cols; dataCol++)
		{
			int col = dataCol;
			// Print below existing column
			if (m_additionalLines.containsKey(new Integer(dataCol)))
			{
				col = m_additionalLines.get(new Integer(dataCol)).intValue();
				log.trace("DataColumn=" + dataCol + ", BelowColumn=" + col);
			}
			float colWidth = 0;
			for (int row = 0; row < rows; row++)
			{
				Object dataItem = m_data[row][dataCol];
				if (dataItem == null)
				{
					dataSizes[row][dataCol] = new Dimension2DImpl();
					continue;
				}
				String string = dataItem.toString();
				if (string.length() == 0)
				{
					dataSizes[row][dataCol] = new Dimension2DImpl();
					continue;
				}
				Font font = getFont(row, dataCol);

				// Print below existing column = (col != dataCol)
				addPrintLines(row, col, dataItem);
				dataSizes[row][dataCol] = new Dimension2DImpl(); // don't print

				if (dataItem instanceof Boolean)
				{
					dataSizes[row][col].addBelow(LayoutEngine.IMAGE_SIZE);
					continue;
				}
				else if (dataItem instanceof ImageElement)
				{
					dataSizes[row][col].addBelow(new Dimension(
							(int)((ImageElement)dataItem).getWidth(),
							(int)((ImageElement)dataItem).getHeight()));
					// Adjust the column width - teo_sarca, [ 1673620 ]
					float width = (float)Math.ceil(dataSizes[row][col]
							.getWidth());
					if (colWidth < width)
						colWidth = width;
					continue;
				}
				else if (dataItem instanceof BarcodeElement)
				{
					dataSizes[row][col].addBelow(new Dimension(
							(int)((BarcodeElement)dataItem).getWidth(),
							(int)((BarcodeElement)dataItem).getHeight()));
					// Check if the overflow is allowed - teo_sarca, [ 1673590 ]
					if (!((BarcodeElement)dataItem).isAllowOverflow())
					{
						float width = (float)Math.ceil(dataSizes[row][col]
								.getWidth());
						if (colWidth < width)
							colWidth = width;
					}

					continue;
				}
				// No Width Limitations
				if (m_columnMaxWidth[col] == 0 || m_columnMaxWidth[col] == -1)
				{
					// if (HTMLElement.isHTML(string))
					// log.trace( "HTML (no) r=" + row + ",c=" + dataCol);
					TextLayout layout = new TextLayout(string, font, frc);
					float width = layout.getAdvance() + 2; // buffer
					float height = layout.getAscent() + layout.getDescent()
							+ layout.getLeading();
					if (width > dynMxColumnWidth)
						m_columnMaxWidth[col] = (int)Math
								.ceil(dynMxColumnWidth);
					else if (colWidth < width)
						colWidth = width;
					if (dataSizes[row][col] == null)
					{
						dataSizes[row][col] = new Dimension2DImpl();
						log.warn("No Size for r=" + row + ",c="
								+ col);
					}
					dataSizes[row][col].addBelow(width, height);
				}
				// Width limitations
				if (m_columnMaxWidth[col] != 0 && m_columnMaxWidth[col] != -1)
				{
					float height = 0;
					//
					if (HTMLElement.isHTML(string))
					{
						// log.trace( "HTML (limit) r=" + row + ",c=" +
						// dataCol);
						HTMLRenderer renderer = HTMLRenderer.get(string);
						colWidth = renderer.getWidth();
						if (m_columnMaxHeight[col] == -1) // one line only
							height = renderer.getHeightOneLine();
						else
							height = renderer.getHeight();
						renderer.setAllocation((int)colWidth, (int)height);
						// log.trace( "calculateSize HTML - " +
						// renderer.getAllocation());
						m_data[row][dataCol] = renderer; // replace for printing
					}
					else
					{
						String[] lines = Pattern
								.compile("$", Pattern.MULTILINE).split(string);
						for (int lineNo = 0; lineNo < lines.length; lineNo++)
						{
							AttributedString aString = new AttributedString(
									lines[lineNo]);
							aString.addAttribute(TextAttribute.FONT, font);
							AttributedCharacterIterator iter = aString
									.getIterator();
							LineBreakMeasurer measurer = new LineBreakMeasurer(
									iter, frc);
							while (measurer.getPosition() < iter.getEndIndex())
							{
								TextLayout layout = measurer.nextLayout(Math
										.abs(m_columnMaxWidth[col]));
								float width = layout.getAdvance();
								if (colWidth < width)
									colWidth = width;
								float lineHeight = layout.getAscent()
										+ layout.getDescent()
										+ layout.getLeading();
								if (m_columnMaxHeight[col] == -1) // one line
																	// only
								{
									height = lineHeight;
									break;
								}
								else if (m_columnMaxHeight[col] == 0
										|| (height + lineHeight) <= m_columnMaxHeight[col])
									height += lineHeight;
							}
						} // for all lines
					}
					if (m_fixedWidth[col])
						colWidth = Math.abs(m_columnMaxWidth[col]);
					dataSizes[row][col].addBelow(colWidth, height);
				}
				dataSizes[row][col].roundUp();
				if (dataItem instanceof NamePair)
					m_rowColDrillDown.put(new Point(row, col),
							(NamePair)dataItem);
				//
				log.trace("Col=" + col + ", row=" + row + " => "
						+ dataSizes[row][col] + " - ColWidth=" + colWidth);
			} // for all data rows

			// Column Width for Header
			String string = "";
			if (m_columnHeader[dataCol] != null)
				string = m_columnHeader[dataCol].toString();

			// Print below existing column
			if (col != dataCol)
				headerSizes[dataCol] = new Dimension2DImpl();
			else if (colWidth == 0 && m_columnMaxWidth[dataCol] < 0 // suppress
																	// Null
					|| string.length() == 0)
				headerSizes[dataCol] = new Dimension2DImpl();
			else
			{
				Font font = getFont(HEADER_ROW, dataCol);
				if (!font.isBold())
					font = new Font(font.getName(), Font.BOLD, font.getSize());
				// No Width Limitations
				if (m_columnMaxWidth[dataCol] == 0
						|| m_columnMaxWidth[dataCol] == -1
						|| !m_multiLineHeader)
				{
					TextLayout layout = new TextLayout(string, font, frc);
					float width = layout.getAdvance() + 3; // buffer
					float height = layout.getAscent() + layout.getDescent()
							+ layout.getLeading();
					if (width > dynMxColumnWidth)
						m_columnMaxWidth[dataCol] = (int)Math
								.ceil(dynMxColumnWidth);
					else if (colWidth < width)
						colWidth = width;
					headerSizes[dataCol] = new Dimension2DImpl(width, height);
				}
				// Width limitations
				if (m_columnMaxWidth[dataCol] != 0
						&& m_columnMaxWidth[dataCol] != -1)
				{
					float height = 0;
					//
					String[] lines = Pattern.compile("$", Pattern.MULTILINE)
							.split(string);
					for (int lineNo = 0; lineNo < lines.length; lineNo++)
					{
						AttributedString aString = new AttributedString(
								lines[lineNo]);
						aString.addAttribute(TextAttribute.FONT, font);
						AttributedCharacterIterator iter = aString
								.getIterator();
						LineBreakMeasurer measurer = new LineBreakMeasurer(
								iter, frc);
						colWidth = Math.abs(m_columnMaxWidth[dataCol]);
						while (measurer.getPosition() < iter.getEndIndex())
						{
							TextLayout layout = measurer.nextLayout(colWidth);
							float lineHeight = layout.getAscent()
									+ layout.getDescent() + layout.getLeading();
							if (!m_multiLineHeader) // one line only
							{
								height = lineHeight;
								break;
							}
							else
								/*
								 * if (m_columnMaxHeight[dataCol] == 0 ||
								 * (height + lineHeight) <=
								 * m_columnMaxHeight[dataCol])
								 */
								height += lineHeight;
						}
					} // for all header lines
					headerSizes[dataCol] = new Dimension2DImpl(colWidth, height);
				}

			} // headerSize
			headerSizes[dataCol].roundUp();
			colWidth = (float)Math.ceil(colWidth);
			// System.out.println("Col=" + dataCol + " => " +
			// headerSizes[dataCol]);

			// Round Column Width
			if (dataCol == 0)
				colWidth += m_tFormat.getVLineStroke().floatValue();
			if (colWidth != 0)
				colWidth += (2 * H_GAP)
						+ m_tFormat.getVLineStroke().floatValue();

			// Print below existing column
			if (col != dataCol)
			{
				m_columnWidths.add(new Float(0.0)); // for the data column
				Float origWidth = m_columnWidths.get(col);
				if (origWidth == null)
					log.error("Column " + dataCol + " below " + col
							+ " - no value for orig width");
				else
				{
					if (origWidth.compareTo(new Float(colWidth)) >= 0)
					{
						log.trace("Same Width - Col=" + col + " - OrigWidth="
								+ origWidth + " - Width=" + colWidth
								+ " - Total=" + p_width);
					}
					else
					{
						m_columnWidths.set(col, new Float(colWidth));
						p_width += (colWidth - origWidth.floatValue());
						log.trace("New Width - Col=" + col + " - OrigWidth="
								+ origWidth + " - Width=" + colWidth
								+ " - Total=" + p_width);
					}
				}
			}
			// Add new Column
			else
			{
				m_columnWidths.add(new Float(colWidth));
				p_width += colWidth;
				log.trace("Width - Col=" + dataCol + " - Width=" + colWidth
						+ " - Total=" + p_width);
			}
		} // for all columns

		// Height **********
		p_height = 0;
		for (int row = 0; row < rows; row++)
		{
			float rowHeight = 0f;
			for (int col = 0; col < cols; col++)
			{
				if (dataSizes[row][col].height > rowHeight) // max
					rowHeight = (float)dataSizes[row][col].height;
			} // for all columns
			rowHeight += m_tFormat.getLineStroke().floatValue() + (2 * V_GAP);
			m_rowHeights.add(new Float(rowHeight));
			p_height += rowHeight;
		} // for all rows
			// HeaderRow
		m_headerHeight = 0;
		for (int col = 0; col < cols; col++)
		{
			if (headerSizes[col].height > m_headerHeight)
				m_headerHeight = (int)headerSizes[col].height;
		} // for all columns
		m_headerHeight += (4 * m_tFormat.getLineStroke().floatValue())
				+ (2 * V_GAP); // Thick lines
		p_height += m_headerHeight;

		// Last row Lines
		p_height += m_tFormat.getLineStroke().floatValue(); // last fat line

		// Page Layout *******************************************************

		log.debug("FirstPage=" + m_firstPage + ", NextPages=" + m_nextPages);
		// One Page on Y | Axis
		if (m_firstPage.height >= p_height && m_pageBreak.size() == 0)
		{
			log.trace("Page Y=1 - PageHeight=" + m_firstPage.height
					+ " - TableHeight=" + p_height);
			m_firstRowOnPage.add(new Integer(0)); // Y
			m_pageHeight.add(new Float(p_height)); // Y index only
		}
		// multiple pages on Y | Axis
		else
		{
			float availableHeight = 0f;
			float usedHeight = 0f;
			boolean firstPage = true;
			int addlRows = 0;
			// for all rows
			for (int dataRow = 0; dataRow < m_rowHeights.size(); dataRow++)
			{
				float rowHeight = m_rowHeights.get(dataRow).floatValue();
				// Y page break before
				boolean pageBreak = isPageBreak(dataRow);

				// adjust for lastrow
				if (dataRow + 1 == m_rowHeights.size())
				{
					availableHeight -= m_tFormat.getLineStroke().floatValue();
				}

				if (!pageBreak && availableHeight < rowHeight)
				{
					if (availableHeight > 40 && rowHeight > 40)
					{
						log.trace("- Split (leave on current) Row=" + dataRow
								+ " - Available=" + availableHeight
								+ ", RowHeight=" + rowHeight);
						// if (splitRow (dataRow))
						// addlRows += 1;
					}
					// else
					pageBreak = true;
				}
				if (pageBreak)
				{
					availableHeight = firstPage ? m_firstPage.height
							: m_nextPages.height;
					m_firstRowOnPage.add(new Integer(dataRow + addlRows)); // Y
					if (!firstPage)
					{
						m_pageHeight.add(new Float(usedHeight)); // Y index only
						log.trace("Page Y=" + m_pageHeight.size()
								+ " - PageHeight=" + usedHeight);
					}
					log.trace("Page Y=" + m_firstRowOnPage.size() + " - Row="
							+ dataRow + " - force=" + isPageBreak(dataRow));
					firstPage = false;
					//
					availableHeight -= m_headerHeight;
					usedHeight += m_headerHeight;
				}
				availableHeight -= rowHeight;
				usedHeight += rowHeight;
				if (availableHeight < 0)
				{
					log.trace("- Split (move to next) Row=" + dataRow
							+ " - Available=" + availableHeight
							+ ", RowHeight=" + rowHeight);

				}
				log.trace("Page Y=" + m_pageHeight.size() + ", Row=" + dataRow
						+ ",AddlRows=" + addlRows + ", Height=" + rowHeight
						+ " - Available=" + availableHeight + ", Used="
						+ usedHeight);
			} // for all rows
			m_pageHeight.add(new Float(usedHeight)); // Y index only
			log.trace("Page Y=" + m_pageHeight.size() + " - PageHeight="
					+ usedHeight);
		} // multiple Y | pages

		// One page on - X Axis
		if (m_firstPage.width >= p_width)
		{
			log.trace("Page X=1 - PageWidth=" + m_firstPage.width
					+ " - TableWidth=" + p_width);
			m_firstColumnOnPage.add(new Integer(0)); // X
			//
			distributeColumns(m_firstPage.width - (int)p_width, 0,
					m_columnWidths.size());
		}
		// multiple pages on - X Axis
		else
		{
			int availableWidth = 0;
			int lastStart = 0;
			for (int col = 0; col < m_columnWidths.size(); col++)
			{
				int columnWidth = m_columnWidths.get(col).intValue();
				// X page preak
				if (availableWidth < columnWidth)
				{
					if (col != 0)
						distributeColumns(availableWidth, lastStart, col);
					//
					m_firstColumnOnPage.add(new Integer(col)); // X
					log.trace("Page X=" + m_firstColumnOnPage.size()
							+ " - Col=" + col);
					lastStart = col;
					availableWidth = m_firstPage.width; // Width is the same on
														// all pages
					//
					for (int repCol = 0; repCol < m_repeatedColumns
							&& col > repCol; repCol++)
					{
						float repColumnWidth = m_columnWidths.get(repCol)
								.floatValue();
						// leave 50% of space available for non repeated columns
						if (availableWidth < m_firstPage.width * 0.5)
							break;
						availableWidth -= repColumnWidth;
					}
				} // pageBreak
				availableWidth -= columnWidth;
			} // for acc columns
		} // multiple - X pages

		log.debug("Pages=" + getPageCount() + " X=" + m_firstColumnOnPage.size()
				+ "/Y=" + m_firstRowOnPage.size() + " - Width=" + p_width
				+ ", Height=" + p_height);
		return true;
	} // calculateSize

	/**************************************************************************
	 * Distribute Columns to fill page
	 * 
	 * @param availableWidth
	 *            width to distribute
	 * @param fromCol
	 *            start column
	 * @param toCol
	 *            end column (not included)
	 */
	private void distributeColumns(int availableWidth, int fromCol, int toCol)
	{
		log.trace("Available=" + availableWidth + ", Columns " + fromCol
				+ "->" + toCol);
		int start = fromCol;
		if (fromCol == 0 && m_repeatedColumns > 0)
			start = m_repeatedColumns;
		// calculate total Width
		int totalWidth = availableWidth;
		for (int col = start; col < toCol; col++)
			totalWidth += m_columnWidths.get(col).floatValue();
		int remainingWidth = availableWidth;
		// distribute proportionally (does not increase zero width columns)
		for (int x = 0; remainingWidth > 0 && x < 5; x++) // max 4 iterations
		{
			log.trace("TotalWidth=" + totalWidth + ", Remaining="
					+ remainingWidth);
			for (int col = start; col < toCol && remainingWidth != 0; col++)
			{
				int columnWidth = m_columnWidths.get(col).intValue();
				if (columnWidth != 0)
				{
					int additionalPart = columnWidth * availableWidth
							/ totalWidth;
					if (remainingWidth < additionalPart)
					{
						m_columnWidths.set(col, new Float(columnWidth
								+ remainingWidth));
						remainingWidth = 0;
					}
					else
					{
						m_columnWidths.set(col, new Float(columnWidth
								+ additionalPart));
						remainingWidth -= additionalPart;
					}
					log.trace("  col=" + col + " - From " + columnWidth
							+ " to " + m_columnWidths.get(col));
				}
			}
		}
		// add remainder to last non 0 width column
		for (int c = toCol - 1; remainingWidth != 0 && c >= 0; c--)
		{
			int columnWidth = m_columnWidths.get(c).intValue();
			if (columnWidth > 0)
			{
				m_columnWidths.set(c, new Float(columnWidth + remainingWidth));
				log.trace("Final col=" + c + " - From " + columnWidth + " to "
						+ m_columnWidths.get(c));
				remainingWidth = 0;
			}
		}
	} // distribute Columns

	/**
	 * Check for for PageBreak
	 * 
	 * @param row
	 *            current row
	 * @return true if row should be on new page
	 */
	private boolean isPageBreak(int row)
	{
		for (int i = 0; i < m_pageBreak.size(); i++)
		{
			Integer rr = m_pageBreak.get(i);
			if (rr.intValue() + 1 == row)
				return true;
			else if (rr.intValue() > row)
				return false;
		}
		return false;
	} // isPageBreak

	/**
	 * For Multi-Page Tables, set Height to Height of last Page
	 */
	public void setHeightToLastPage()
	{
		int lastLayoutPage = getPageCount() + m_pageNoStart - 1;
		log.debug("PageCount - Table=" + getPageCount() + "(Start="
				+ m_pageNoStart + ") Layout=" + lastLayoutPage
				+ " - Old Height=" + p_height);
		p_height = getHeight(lastLayoutPage);
		log.debug("New Height=" + p_height);
	} // setHeightToLastPage

	/**************************************************************************
	 * Get Font. Based on Point (row,col).
	 * 
	 * <pre>
	 *  Examples:
	 * 	  From general to specific:
	 *      (-1,-1) => for entire table
	 *  	(-1, c) => for entire column c
	 *  	(r, -1) => for entire row r (overwrites column)
	 *      (r, c)  => for specific cell (highest priority)
	 *    Header is row -2
	 * 		(-2,-1) => for all header columns
	 * 		(-2, c) => for header column c
	 * </pre>
	 * 
	 * @param row
	 *            row
	 * @param col
	 *            column
	 * @return Font for row/col
	 */
	private Font getFont(int row, int col)
	{
		// First specific position
		Font font = m_rowColFont.get(new Point(row, col));
		if (font != null)
			return font;
		// Row Next
		font = m_rowColFont.get(new Point(row, ALL));
		if (font != null)
			return font;
		// Column then
		font = m_rowColFont.get(new Point(ALL, col));
		if (font != null)
			return font;
		// default
		return m_baseFont;
	} // getFont

	/**
	 * Get Foreground Color.
	 * 
	 * @param row
	 *            row
	 * @param col
	 *            column
	 * @return Color for row/col
	 */
	private Color getColor(int row, int col)
	{
		// First specific position
		Color color = m_rowColColor.get(new Point(row, col));
		if (color != null)
			return color;
		// Row Next
		color = m_rowColColor.get(new Point(row, ALL));
		if (color != null)
			return color;
		// Column then
		color = m_rowColColor.get(new Point(ALL, col));
		if (color != null)
			return color;
		// default
		return m_baseColor;
	} // getFont

	/**
	 * Get Foreground Color.
	 * 
	 * @param row
	 *            row
	 * @param col
	 *            column
	 * @return Color for row/col
	 */
	private Color getBackground(int row, int col)
	{
		// First specific position
		Color color = m_rowColBackground.get(new Point(row, col));
		if (color != null)
			return color;
		// Row Next
		color = m_rowColBackground.get(new Point(row, ALL));
		if (color != null)
			return color;
		// Column then
		color = m_rowColBackground.get(new Point(ALL, col));
		if (color != null)
			return color;
		// default
		return m_baseBackground;
	} // getFont

	/**************************************************************************
	 * Get Calculated Height on page
	 * 
	 * @param pageNo
	 *            layout page number
	 * @return Height
	 */
	@Override
	public float getHeight(int pageNo)
	{
		int pageIndex = getPageIndex(pageNo);
		int pageYindex = getPageYIndex(pageIndex);
		log.debug("Page=" + pageNo + " - PageIndex=" + pageIndex
				+ ", PageYindex=" + pageYindex);
		float pageHeight = m_pageHeight.get(pageYindex).floatValue();
		float pageHeightPrevious = 0f;
		if (pageYindex > 0)
			pageHeightPrevious = m_pageHeight.get(pageYindex - 1).floatValue();
		float retValue = pageHeight - pageHeightPrevious;
		log.debug("Page=" + pageNo + " - PageIndex=" + pageIndex
				+ ", PageYindex=" + pageYindex + ", Height="
				+ String.valueOf(retValue));
		return retValue;
	} // getHeight

	/**
	 * Get Calculated Height on page
	 * 
	 * @param pageNo
	 *            page number
	 * @return Height
	 */
	public float getWidth(int pageNo)
	{
		int pageIndex = getPageIndex(pageNo);
		if (pageIndex == 0)
			return m_firstPage.width;
		return m_nextPages.width;
	} // getHeight

	/**************************************************************************
	 * Get number of "real" pages.
	 * 
	 * @return page count
	 */
	@Override
	public int getPageCount()
	{
		return m_firstRowOnPage.size() * m_firstColumnOnPage.size();
	} // getPageCount

	/**
	 * Get zero based Page Index within Layout
	 * 
	 * @param pageNo
	 *            real page no
	 * @return page index
	 */
	protected int getPageIndex(int pageNo)
	{
		int index = pageNo - m_pageNoStart;
		if (index < 0)
			log.error("index=" + index, new Exception());
		return index;
	} // getPageIndex

	/**
	 * Get "real" page Number within Layout
	 * 
	 * @param pageIndex
	 *            zero based page index
	 * @return page number
	 */
	private int getPageNo(int pageIndex)
	{
		return pageIndex + m_pageNoStart;
	} // getPageNo

	/**************************************************************************
	 * Get X - Page Index. Zero Based; Page No is the "real" page No
	 * 
	 * <pre>
	 *  The table is 3 pages wide, 2 pages high - index
	 * 		+-----+-----+-----+
	 *      | 0.0 | 0.1 | 0.2 |
	 * 		+-----+-----+-----+
	 *      | 1.0 | 1.1 | 1.2 |
	 * 		+-----+-----+-----+
	 *  Page Index
	 * 		+-----+-----+-----+
	 *      |  0  |  1  |  2  |
	 * 		+-----+-----+-----+
	 *      |  3  |  4  |  5  |
	 * 		+-----+-----+-----+
	 * </pre>
	 * 
	 * @param pageIndex
	 *            zero based page index
	 * @return page index on X axis
	 */
	protected int getPageXIndex(int pageIndex)
	{
		int noXpages = m_firstColumnOnPage.size();
		// int noYpages = m_firstRowOnPage.size();
		int x = pageIndex % noXpages;
		return x;
	} // getPageXIndex

	/**
	 * Get X - Page Count
	 * 
	 * @return X page count
	 */
	protected int getPageXCount()
	{
		return m_firstColumnOnPage.size();
	} // getPageXCount

	/**
	 * Get Y | Page Index. Zero Based; Page No is the "real" page No
	 * 
	 * <pre>
	 *  The table is 3 pages wide, 2 pages high - index
	 * 		+-----+-----+-----+
	 *      | 0.0 | 0.1 | 0.2 |
	 * 		+-----+-----+-----+
	 *      | 1.0 | 1.1 | 1.2 |
	 * 		+-----+-----+-----+
	 *  Page Index
	 * 		+-----+-----+-----+
	 *      |  0  |  1  |  2  |
	 * 		+-----+-----+-----+
	 *      |  3  |  4  |  5  |
	 * 		+-----+-----+-----+
	 * </pre>
	 * 
	 * @param pageIndex
	 *            zero based page index
	 * @return page index on Y axis
	 */
	protected int getPageYIndex(int pageIndex)
	{
		int noXpages = m_firstColumnOnPage.size();
		// int noYpages = m_firstRowOnPage.size();
		int y = (pageIndex - (pageIndex % noXpages)) / noXpages;
		return y;
	} // getPageYIndex

	/**
	 * Get Y | Page Count
	 * 
	 * @return Y page count
	 */
	protected int getPageYCount()
	{
		return m_firstRowOnPage.size();
	} // getPageYCount

	/**************************************************************************
	 * Get Drill Down value
	 * 
	 * @param relativePoint
	 *            relative Point
	 * @param pageNo
	 *            page number
	 * @return if found Qyery or null
	 */
	@Override
	public MQuery getDrillDown(Point relativePoint, int pageNo)
	{
		if (m_rowColDrillDown.size() == 0)
			return null;
		if (!getBounds(pageNo).contains(relativePoint))
			return null;
		int row = getRow(relativePoint.y, pageNo);
		if (row == -1)
			return null;
		int col = getCol(relativePoint.x, pageNo);
		if (col == -1)
			return null;
		log.debug("Row=" + row + ", Col=" + col + ", PageNo=" + pageNo);
		//
		NamePair pp = m_rowColDrillDown.get(new Point(row, col));
		if (pp == null)
			return null;
		String columnName = MQuery.getZoomColumnName(m_columnHeader[col]
				.getID());
		String tableName = MQuery.getZoomTableName(columnName);
		Object code = pp.getID();
		if (pp instanceof KeyNamePair)
			code = new Integer(((KeyNamePair)pp).getKey());
		//
		MQuery query = new MQuery(tableName);
		query.addRestriction(columnName, Operator.EQUAL, code, null,
				pp.toString());
		return query;
	} // getDrillDown

	/**
	 * Get Drill Across value
	 * 
	 * @param relativePoint
	 *            relative Point
	 * @param pageNo
	 *            page number
	 * @return if found Query or null
	 */
	@Override
	public MQuery getDrillAcross(Point relativePoint, int pageNo)
	{
		if (!getBounds(pageNo).contains(relativePoint))
			return null;
		int row = getRow(relativePoint.y, pageNo);
		if (row == -1)
			return null;
		log.debug("Row=" + row + ", PageNo=" + pageNo);
		//
		if (m_pk[row] == null) // FunctionRows
			return null;
		return MQuery.getEqualQuery(m_pkColumnName, m_pk[row].getKey());
	} // getDrillAcross

	/**
	 * Get relative Bounds of Element. (entire page, not just used portion)
	 * 
	 * @param pageNo
	 *            pageNo
	 * @return bounds relative position on page
	 */
	public Rectangle getBounds(int pageNo)
	{
		int pageIndex = getPageIndex(pageNo);
		int pageYindex = getPageYIndex(pageIndex);
		if (pageYindex == 0)
			return m_firstPage;
		else
			return m_nextPages;
	} // getBounds

	/**
	 * Get Row for yPos
	 * 
	 * @param yPos
	 *            y position (page relative)
	 * @param pageNo
	 *            page number
	 * @return row index or -1
	 */
	private int getRow(int yPos, int pageNo)
	{
		int pageIndex = getPageIndex(pageNo);
		int pageYindex = getPageYIndex(pageIndex);
		//
		int curY = (pageYindex == 0 ? m_firstPage.y : m_nextPages.y)
				+ m_headerHeight;
		if (yPos < curY)
			return -1; // above
		//
		int firstRow = m_firstRowOnPage.get(pageYindex).intValue();
		int nextPageRow = m_data.length; // no of rows
		if (pageYindex + 1 < m_firstRowOnPage.size())
			nextPageRow = m_firstRowOnPage.get(pageYindex + 1).intValue();
		//
		for (int row = firstRow; row < nextPageRow; row++)
		{
			int rowHeight = m_rowHeights.get(row).intValue(); // includes
																// 2*Gaps+Line
			if (yPos >= curY && yPos < (curY + rowHeight))
				return row;
			curY += rowHeight;
		}
		// below
		return -1;
	} // getRow

	/**
	 * Get Column for xPos
	 * 
	 * @param xPos
	 *            x position (page relative)
	 * @param pageNo
	 *            page number
	 * @return column index or -1
	 */
	private int getCol(int xPos, int pageNo)
	{
		int pageIndex = getPageIndex(pageNo);
		int pageXindex = getPageXIndex(pageIndex);
		//
		int curX = pageXindex == 0 ? m_firstPage.x : m_nextPages.x;
		if (xPos < curX)
			return -1; // too left

		int firstColumn = m_firstColumnOnPage.get(pageXindex).intValue();
		int nextPageColumn = m_columnHeader.length; // no of cols
		if (pageXindex + 1 < m_firstColumnOnPage.size())
			nextPageColumn = m_firstColumnOnPage.get(pageXindex + 1).intValue();

		// fixed volumns
		int regularColumnStart = firstColumn;
		for (int col = 0; col < m_repeatedColumns; col++)
		{
			int colWidth = m_columnWidths.get(col).intValue(); // includes
																// 2*Gaps+Line
			if (xPos >= curX && xPos < (curX + colWidth))
				return col;
			curX += colWidth;
			if (regularColumnStart == col)
				regularColumnStart++;
		}
		// regular columns
		for (int col = regularColumnStart; col < nextPageColumn; col++)
		{
			int colWidth = m_columnWidths.get(col).intValue(); // includes
																// 2*Gaps+Line
			if (xPos >= curX && xPos < (curX + colWidth))
				return col;
			curX += colWidth;
		} // for all columns
			// too right
		return -1;
	} // getCol

	/**************************************************************************
	 * Paint/Print.
	 *
	 * @param g2D
	 *            Graphics
	 * @param pageNo
	 *            page number for multi page support (0 = header/footer)
	 * @param pageStart
	 *            top left Location of page
	 * @param ctx
	 *            context
	 * @param isView
	 *            true if online view (IDs are links)
	 */
	@Override
	public void paint(Graphics2D g2D, int pageNo, Point2D pageStart,
			Properties ctx, boolean isView)
	{
		int pageIndex = getPageIndex(pageNo);
		int pageXindex = getPageXIndex(pageIndex);
		int pageYindex = getPageYIndex(pageIndex);
		if (DEBUG_PRINT)
			log.info("Page=" + pageNo + " [x=" + pageXindex + ", y="
					+ pageYindex + "]");
		//
		int firstColumn = m_firstColumnOnPage.get(pageXindex).intValue();
		int nextPageColumn = m_columnHeader.length; // no of cols
		if (pageXindex + 1 < m_firstColumnOnPage.size())
			nextPageColumn = m_firstColumnOnPage.get(pageXindex + 1).intValue();
		//
		int firstRow = m_firstRowOnPage.get(pageYindex).intValue();
		int nextPageRow = m_data.length; // no of rows
		if (pageYindex + 1 < m_firstRowOnPage.size())
			nextPageRow = m_firstRowOnPage.get(pageYindex + 1).intValue();
		if (DEBUG_PRINT)
			log.trace("Col=" + firstColumn + "-" + (nextPageColumn - 1)
					+ ", Row=" + firstRow + "-" + (nextPageRow - 1));

		// Top Left
		int startX = (int)pageStart.getX();
		int startY = (int)pageStart.getY();
		// Table Start
		startX += pageXindex == 0 ? m_firstPage.x : m_nextPages.x;
		startY += pageYindex == 0 ? m_firstPage.y : m_nextPages.y;
		if (DEBUG_PRINT)
			log.trace("PageStart=" + pageStart + ", StartTable x=" + startX
					+ ", y=" + startY);

		// paint first fixed volumns
		boolean firstColumnPrint = true;
		int regularColumnStart = firstColumn;
		for (int col = 0; col < m_repeatedColumns
				&& col < m_columnWidths.size(); col++)
		{
			int colWidth = m_columnWidths.get(col).intValue(); // includes
																// 2*Gaps+Line
			if (colWidth != 0)
			{
				printColumn(g2D, col, startX, startY, firstColumnPrint,
						firstRow, nextPageRow, isView);
				startX += colWidth;
				firstColumnPrint = false;
			}
			if (regularColumnStart == col)
				regularColumnStart++;
		}

		// paint columns
		for (int col = regularColumnStart; col < nextPageColumn; col++)
		{
			int colWidth = m_columnWidths.get(col).intValue(); // includes
																// 2*Gaps+Line
			if (colWidth != 0)
			{
				printColumn(g2D, col, startX, startY, firstColumnPrint,
						firstRow, nextPageRow, isView);
				startX += colWidth;
				firstColumnPrint = false;
			}
		} // for all columns

	} // paint

	/**
	 * Print non zero width Column
	 * 
	 * @param g2D
	 *            graphics
	 * @param col
	 *            column index
	 * @param origX
	 *            start X
	 * @param origY
	 *            start Y
	 * @param leftVline
	 *            if true print left vertical line (for first column)
	 * @param firstRow
	 *            first row index
	 * @param nextPageRow
	 *            row index of next page
	 * @param isView
	 *            true if online view (IDs are links)
	 */
	private void printColumn(Graphics2D g2D, int col,
			final int origX, final int origY, boolean leftVline,
			final int firstRow, final int nextPageRow, boolean isView)
	{
		int curX = origX;
		int curY = origY;	// start from top
		//
		float colWidth = m_columnWidths.get(col).floatValue();		// includes 2*Gaps+Line
		float netWidth = colWidth - (2 * H_GAP) - m_tFormat.getVLineStroke().floatValue();
		if (leftVline)
			netWidth -= m_tFormat.getVLineStroke().floatValue();
		int rowHeight = m_headerHeight;
		float netHeight = rowHeight - (4 * m_tFormat.getLineStroke().floatValue()) + (2 * V_GAP);

		if (DEBUG_PRINT)
			log.trace("#" + col + " - x=" + curX + ", y=" + curY
					+ ", width=" + colWidth + "/" + netWidth + ", HeaderHeight=" + rowHeight + "/" + netHeight);
		String alignment = m_columnJustification[col];

		// paint header ***************************************************
		if (leftVline)			// draw left | line
		{
			g2D.setPaint(m_tFormat.getVLine_Color());
			g2D.setStroke(m_tFormat.getVLine_Stroke());
			if (m_tFormat.isPaintBoundaryLines())				// -> | (left)
				g2D.drawLine(origX, (int)(origY + m_tFormat.getLineStroke().floatValue()),
						origX, (int)(origY + rowHeight - (4 * m_tFormat.getLineStroke().floatValue())));
			curX += m_tFormat.getVLineStroke().floatValue();
		}
		// X - start line
		if (m_tFormat.isPaintHeaderLines())
		{
			g2D.setPaint(m_tFormat.getHeaderLine_Color());
			g2D.setStroke(m_tFormat.getHeader_Stroke());
			g2D.drawLine(origX, origY, 							// -> - (top)
					(int)(origX + colWidth - m_tFormat.getVLineStroke().floatValue()), origY);
		}
		curY += (2 * m_tFormat.getLineStroke().floatValue());	// thick
		// Background
		Color bg = getBackground(HEADER_ROW, col);
		if (!bg.equals(Color.white))
		{
			g2D.setPaint(bg);
			g2D.fillRect(curX,
					(int)(curY - m_tFormat.getLineStroke().floatValue()),
					(int)(colWidth - m_tFormat.getVLineStroke().floatValue()),
					(int)(rowHeight - (4 * m_tFormat.getLineStroke().floatValue())));
		}

		int tempCurY = curY;
		curX += H_GAP;		// upper left gap
		curY += V_GAP;
		// Header
		AttributedString aString = null;
		AttributedCharacterIterator iter = null;
		LineBreakMeasurer measurer = null;
		float usedHeight = 0;

		// Calculate column header height - teo_sarca [ 1673429 ]
		String headerString = m_columnHeader[col].toString();
		if (headerString.length() == 0)
			headerString = " ";
		// if (m_columnHeader[col].toString().length() > 0)
		{
			aString = new AttributedString(headerString);
			aString.addAttribute(TextAttribute.FONT, getFont(HEADER_ROW, col));
			aString.addAttribute(TextAttribute.FOREGROUND, getColor(HEADER_ROW, col));
			//
			boolean fastDraw = LayoutEngine.s_FASTDRAW;
			if (fastDraw && !isView && !Util.is8Bit(headerString))
				fastDraw = false;
			iter = aString.getIterator();
			measurer = new LineBreakMeasurer(iter, g2D.getFontRenderContext());
			while (measurer.getPosition() < iter.getEndIndex())		// print header
			{
				TextLayout layout = measurer.nextLayout(netWidth + 2);
				if (iter.getEndIndex() != measurer.getPosition())
					fastDraw = false;
				float lineHeight = layout.getAscent() + layout.getDescent() + layout.getLeading();

				if (alignment.equals(MPrintFormatItem.FIELDALIGNMENTTYPE_Block))
				{
					layout = layout.getJustifiedLayout(netWidth + 2);
					fastDraw = false;
				}
				curY += layout.getAscent();
				float penX = curX;
				if (alignment.equals(MPrintFormatItem.FIELDALIGNMENTTYPE_Center))
					penX += (netWidth - layout.getAdvance()) / 2;
				else if ((alignment.equals(MPrintFormatItem.FIELDALIGNMENTTYPE_TrailingRight) && layout.isLeftToRight())
						|| (alignment.equals(MPrintFormatItem.FIELDALIGNMENTTYPE_LeadingLeft) && !layout.isLeftToRight()))
					penX += netWidth - layout.getAdvance();
				//
				if (fastDraw)
				{	// Bug - set Font/Color explicitly
					g2D.setFont(getFont(HEADER_ROW, col));
					g2D.setColor(getColor(HEADER_ROW, col));
					g2D.drawString(iter, penX, curY);
				}
				else
					layout.draw(g2D, penX, curY);										// -> text
				curY += layout.getDescent() + layout.getLeading();
				usedHeight += layout.getAscent() + layout.getDescent();

				if (!m_multiLineHeader)			// one line only
					break;
			}
		}

		curX += netWidth + H_GAP;
		curY = tempCurY + (int)(rowHeight - (4 * m_tFormat.getLineStroke().floatValue()));
		// Y end line
		g2D.setPaint(m_tFormat.getVLine_Color());
		g2D.setStroke(m_tFormat.getVLine_Stroke());
		if (m_tFormat.isPaintVLines())					// -> | (right)
			g2D.drawLine(curX, (int)(origY + m_tFormat.getLineStroke().floatValue()),
					curX, (int)(origY + rowHeight - (4 * m_tFormat.getLineStroke().floatValue())));
		curX += m_tFormat.getVLineStroke().floatValue();
		// X end line
		if (m_tFormat.isPaintHeaderLines())
		{
			g2D.setPaint(m_tFormat.getHeaderLine_Color());
			g2D.setStroke(m_tFormat.getHeader_Stroke());
			g2D.drawLine(origX, curY, 					// -> - (button)
					(int)(origX + colWidth - m_tFormat.getVLineStroke().floatValue()), curY);
		}
		curY += (2 * m_tFormat.getLineStroke().floatValue());	// thick

		// paint Data ***************************************************
		for (int row = firstRow; row < nextPageRow; row++)
		{
			rowHeight = m_rowHeights.get(row).intValue();	// includes 2*Gaps+Line
			netHeight = rowHeight - (2 * V_GAP) - m_tFormat.getLineStroke().floatValue();
			int rowYstart = curY;

			curX = origX;
			if (leftVline)			// draw left | line
			{
				g2D.setPaint(m_tFormat.getVLine_Color());
				g2D.setStroke(m_tFormat.getVLine_Stroke());
				if (m_tFormat.isPaintBoundaryLines())
					g2D.drawLine(curX, rowYstart, 				// -> | (left)
							curX, (int)(rowYstart + rowHeight - m_tFormat.getLineStroke().floatValue()));
				curX += m_tFormat.getVLineStroke().floatValue();
			}
			// Background
			bg = getBackground(row, col);
			if (!bg.equals(Color.white))
			{
				g2D.setPaint(bg);
				g2D.fillRect(curX, curY,
						(int)(colWidth - m_tFormat.getVLineStroke().floatValue()),
						(int)(rowHeight - m_tFormat.getLineStroke().floatValue()));
			}
			curX += H_GAP;		// upper left gap
			curY += V_GAP;

			// actual data
			Object[] printItems = getPrintItems(row, col);

			float penY = curY;

			// suppress repeated values
			boolean suppress = false;
			if (m_colSuppressRepeats[col] && row > 0 && row != firstRow)
			{
				Object[] lastItems = {};
				lastItems = getPrintItems(row - 1, col);
				if (Arrays.equals(lastItems, printItems))
					suppress = true;
			}

			if (!suppress)
			{
				for (int index = 0; index < printItems.length; index++)
				{
					if (printItems[index] == null)
						;
					else if (printItems[index] instanceof ImageElement)
					{
						Image imageToDraw = ((ImageElement)printItems[index]).getImage();
						if (imageToDraw != null) // teo_sarca [ 1674706 ]
						{
							// Draw image using the scale factor - teo_sarca, [ 1673548 ] Image is not scaled in a report table cell
							double scale = ((ImageElement)printItems[index]).getScaleFactor();
							if (scale != 1.0)
							{
								AffineTransform transform = new AffineTransform();
								transform.translate(curX, penY);
								transform.scale(scale, scale);
								g2D.drawImage(imageToDraw, transform, this);
							}
							else
							{
								g2D.drawImage(imageToDraw, curX, (int)penY, this);
							}
						}
					}
					else if (printItems[index] instanceof BarcodeElement)
					{
						try
						{
							Barcode barcode = ((BarcodeElement)printItems[index]).getBarcode();
							if (barcode != null)
							{
								double scale = ((BarcodeElement)printItems[index]).getScaleFactor();
								if (scale != 1.0)
								{
									int w = barcode.getWidth();
									int h = barcode.getHeight();

									// draw barcode to buffer
									BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
									Graphics2D temp = (Graphics2D)image.getGraphics();
									barcode.draw(temp, 0, 0);

									// scale barcode and paint
									AffineTransform transform = new AffineTransform();
									transform.translate(curX, penY);
									transform.scale(scale, scale);
									g2D.drawImage(image, transform, this);
								}
								else
								{
									barcode.draw(g2D, curX, (int)penY);
								}
							}
						}
						catch (OutputException e)
						{
						}
					}

					else if (printItems[index] instanceof HTMLRenderer)
					{
						HTMLRenderer renderer = (HTMLRenderer)printItems[index];
						Rectangle allocation = new Rectangle((int)colWidth, (int)netHeight);
						// log.trace( "printColumn HTML - " + allocation);
						g2D.translate(curX, penY);
						renderer.paint(g2D, allocation);
						g2D.translate(-curX, -penY);
						penY += allocation.getHeight();
					}
					else
					{
						String str;

						// task 09359
						// Handle Boolean as String: x when true, empty when false
						if (printItems[index] instanceof Boolean)
						{
							if (((Boolean)printItems[index]).booleanValue())
							{
								str = "x";
							}
							else
							{

								str = " ";
							}
						}
						else
						{
							str = printItems[index].toString();
						}
						
						if (DEBUG_PRINT)
							log.debug("row=" + row + ",col=" + col + " - " + str + " 8Bit=" + Util.is8Bit(str));
						if (str.length() > 0)
						{
							usedHeight = 0;
							String[] lines = Pattern.compile("$", Pattern.MULTILINE).split(str);
							for (int lineNo = 0; lineNo < lines.length; lineNo++)
							{
								aString = new AttributedString(lines[lineNo]);
								aString.addAttribute(TextAttribute.FONT, getFont(row, col));
								if (isView && printItems[index] instanceof NamePair)	// ID
								{
									aString.addAttribute(TextAttribute.FOREGROUND, LINK_COLOR);
									aString.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_ONE_PIXEL, 0, str.length());
								}
								else
									aString.addAttribute(TextAttribute.FOREGROUND, getColor(row, col));
								//
								iter = aString.getIterator();
								boolean fastDraw = LayoutEngine.s_FASTDRAW;
								if (fastDraw && !isView && !Util.is8Bit(lines[lineNo]))
									fastDraw = false;
								measurer = new LineBreakMeasurer(iter, g2D.getFontRenderContext());
								while (measurer.getPosition() < iter.getEndIndex())		// print element
								{
									TextLayout layout = measurer.nextLayout(netWidth + 2);
									if (iter.getEndIndex() != measurer.getPosition())
										fastDraw = false;
									float lineHeight = layout.getAscent() + layout.getDescent() + layout.getLeading();
									if ((m_columnMaxHeight[col] <= 0
											|| (usedHeight + lineHeight) <= m_columnMaxHeight[col])
											&& (usedHeight + lineHeight) <= netHeight)
									{
										if (alignment.equals(MPrintFormatItem.FIELDALIGNMENTTYPE_Block) && measurer.getPosition() < iter.getEndIndex())
										{
											layout = layout.getJustifiedLayout(netWidth + 2);
											fastDraw = false;
										}
										penY += layout.getAscent();
										float penX = curX;
										if (alignment.equals(MPrintFormatItem.FIELDALIGNMENTTYPE_Center))
											penX += (netWidth - layout.getAdvance()) / 2;
										else if ((alignment.equals(MPrintFormatItem.FIELDALIGNMENTTYPE_TrailingRight) && layout.isLeftToRight())
												|| (alignment.equals(MPrintFormatItem.FIELDALIGNMENTTYPE_LeadingLeft) && !layout.isLeftToRight()))
											penX += netWidth - layout.getAdvance();
										//
										if (fastDraw)
										{	// Bug - set Font/Color explicitly
											g2D.setFont(getFont(row, col));
											if (isView && printItems[index] instanceof NamePair)	// ID
											{
												g2D.setColor(LINK_COLOR);
												// TextAttribute.UNDERLINE
											}
											else
												g2D.setColor(getColor(row, col));
											g2D.drawString(iter, penX, penY);
										}
										else
											layout.draw(g2D, penX, penY);										// -> text
										if (DEBUG_PRINT)
											log.debug("row=" + row + ",col=" + col + " - " + str + " - x=" + penX + ",y=" + penY);
										penY += layout.getDescent() + layout.getLeading();
										usedHeight += lineHeight;
										//
										if (m_columnMaxHeight[col] == -1)	// FirstLineOny
											break;
									}
								}	// print element
							}	// for all lines
						}	// length > 0
					}	// non boolean
				}	// for all print items
			} // not suppressed

			curY += netHeight + V_GAP;
			curX += netWidth + H_GAP;
			// Y end line
			g2D.setPaint(m_tFormat.getVLine_Color());
			g2D.setStroke(m_tFormat.getVLine_Stroke());
			if (m_tFormat.isPaintVLines())
				g2D.drawLine(curX, rowYstart, 				// -> | (right)
						curX, (int)(rowYstart + rowHeight - m_tFormat.getLineStroke().floatValue()));
			curX += m_tFormat.getVLineStroke().floatValue();

			// X end line
			if (row == m_data.length - 1)         // last Line
			{
				/**
				 * Bug fix - Bottom line was always displayed whether or not header lines was set to be visible
				 * 
				 * @author ashley
				 */
				if (m_tFormat.isPaintHeaderLines())
				{
					g2D.setPaint(m_tFormat.getHeaderLine_Color());
					g2D.setStroke(m_tFormat.getHeader_Stroke());
					g2D.drawLine(origX, curY,                   // -> - (last line)
							(int)(origX + colWidth - m_tFormat.getVLineStroke().floatValue()), curY);
					curY += (2 * m_tFormat.getLineStroke().floatValue());   // thick
				}
				else
				{
					curY += m_tFormat.getLineStroke().floatValue();
				}
			}
			else
			{
				// next line is a funcion column -> underline this
				boolean nextIsFunction = m_functionRows.contains(new Integer(row + 1));
				if (nextIsFunction && m_functionRows.contains(new Integer(row)))
					nextIsFunction = false;     // this is a function line too
				if (nextIsFunction)
				{
					g2D.setPaint(m_tFormat.getFunctFG_Color());
					g2D.setStroke(m_tFormat.getHLine_Stroke());
					g2D.drawLine(origX, curY,               // -> - (bottom)
							(int)(origX + colWidth - m_tFormat.getVLineStroke().floatValue()), curY);
				}
				else if (m_tFormat.isPaintHLines())
				{
					g2D.setPaint(m_tFormat.getHLine_Color());
					g2D.setStroke(m_tFormat.getHLine_Stroke());
					g2D.drawLine(origX, curY,               // -> - (bottom)
							(int)(origX + colWidth - m_tFormat.getVLineStroke().floatValue()), curY);
				}
				curY += m_tFormat.getLineStroke().floatValue();
			}
		}	// for all rows

	}	// printColumn

	/**
	 * Add Additional Lines to row/col
	 * 
	 * @param row
	 *            row
	 * @param col
	 *            col
	 * @param data
	 *            data
	 */
	private void addPrintLines(int row, int col, Object data)
	{
		while (m_printRows.size() <= row)
			m_printRows.add(null);
		ArrayList<ArrayList<Object>> columns = m_printRows.get(row);
		if (columns == null)
			columns = new ArrayList<ArrayList<Object>>(m_columnHeader.length);
		while (columns.size() <= col)
			columns.add(null);
		//
		ArrayList<Object> coordinate = columns.get(col);
		if (coordinate == null)
			coordinate = new ArrayList<Object>();
		coordinate.add(data);
		//
		columns.set(col, coordinate);
		m_printRows.set(row, columns);
		log.trace("row=" + row + ", col=" + col + " - Rows="
				+ m_printRows.size() + ", Cols=" + columns.size() + " - "
				+ data);
	} // addAdditionalLines

	/** Print Data */
	private ArrayList<ArrayList<ArrayList<Object>>> m_printRows = new ArrayList<ArrayList<ArrayList<Object>>>();

	/**
	 * Insert empty Row after current Row
	 *
	 * @param currentRow
	 */
	private void insertRow(int currentRow)
	{

	} // inserRow

	/**
	 * Get Print Data including additional Lines
	 * 
	 * @param row
	 *            row
	 * @param col
	 *            col
	 * @return non null array of print objects (may be empty)
	 */
	private Object[] getPrintItems(int row, int col)
	{
		ArrayList<ArrayList<Object>> columns = null;
		if (m_printRows.size() > row)
			columns = m_printRows.get(row);
		if (columns == null)
			return new Object[] {};
		ArrayList<Object> coordinate = null;
		if (columns.size() > col)
			coordinate = columns.get(col);
		if (coordinate == null)
			return new Object[] {};
		//
		return coordinate.toArray();
	} // getPrintItems

}
