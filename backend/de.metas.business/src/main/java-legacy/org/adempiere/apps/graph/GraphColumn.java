/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
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
package org.adempiere.apps.graph;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import de.metas.project.ProjectType;
import org.compiere.model.MAchievement;
import org.compiere.model.MGoal;
import org.compiere.model.MMeasure;
import org.compiere.model.MMeasureCalc;
import org.compiere.model.MQuery;
import org.compiere.model.MRequestType;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

/**
 *
 * @author hengsin
 *
 */
public class GraphColumn
{

	/**
	 * 	Base Constructor
	 *	@param label label
	 *	@param value value
	 */
	public GraphColumn (String label, double value)
	{
		m_label = label;
		setValue(value);
	}	//	BarGraphColumn

	/**
	 * 	Single Achievement Constructor
	 *	@param achievement achievement
	 */
	public GraphColumn (MAchievement achievement)
	{
		this (achievement.getName(), achievement.getManualActual().doubleValue());
		m_achievement = achievement;
	}	//	GraphColumn

	/**
	 * 	Achievement Goal Constructor
	 *	@param goal goal
	 *	@param data count
	 */
	public GraphColumn (MGoal goal, BigDecimal data)
	{
		this ("", data == null ? 0 : data.doubleValue());
		m_goal = goal;
	}	//	GraphColumn

	/**
	 * 	Measure Calc Constructor
	 *	@param mc MeasureCalc
	 */
	public GraphColumn (MMeasureCalc mc, BigDecimal data)
	{
		this ("", data == null ? 0 : data.doubleValue());
		m_mc = mc;
	}	//	GraphColumn

	/**
	 * 	Request Type Constructor
	 *	@param rt Request Type
	 */
	public GraphColumn (MRequestType rt, BigDecimal data, int id)
	{
		this ("", data == null ? 0 : data.doubleValue());
		m_rt = rt;
		m_id = id;
	}	//	GraphColumn

	/**
	 * 	Project Type Constructor
	 *	@param pt Project Type
	 */
	public GraphColumn (ProjectType pt, BigDecimal data, int id)
	{
		this ("", data == null ? 0 : data.doubleValue());
		m_pt = pt;
		m_id = id;
	}	//	BarGraphColumn

	/** Optional Achievement		*/
	private MAchievement	m_achievement = null;
	/** Measure Calc				*/
	private MMeasureCalc	m_mc = null;
	/** Goal				*/
	private MGoal			m_goal = null;

	private MRequestType	m_rt = null;
	private ProjectType	m_pt = null;
	private int				m_id = 0;

	/** Display						*/
	private String			m_measureDisplay = null;
	private Timestamp		m_date = null;

	/** Column Label				*/
	private String	m_label = null;
	/** Column Data Value			*/
	private double	m_value = 0;
	/** Column Label Value			*/
	private String	m_labelValue = "";
	/** Column Data Target Value	*/
	private double	m_targetValue = 0;
	/** Column Width in pixels		*/
	private double	m_width = 0;
	/** Column Height in pixels		*/
	private double	m_height = 0;

	/**	Logger	*/
	private static Logger log = LogManager.getLogger(GraphColumn.class);
	/** Integer Number Format		*/
	private static DecimalFormat	s_format = DisplayType.getNumberFormat(DisplayType.Integer);

	/**
	 * 	Get Achievement Goal
	 *	@return achievement or null
	 */
	public MGoal getGoal()
	{
		return m_goal;
	}	//	getGoal


	/**
	 * 	Get Single Achievement
	 *	@return achievement or null
	 */
	public MAchievement getAchievement()
	{
		return m_achievement;
	}	//	getAchievement

	/**
	 * 	Get MeasureCalc
	 *	@return measure
	 */
	public MMeasureCalc getMeasureCalc()
	{
		return m_mc;
	}	//	getMeasureCalc

	public MRequestType getRequestType()
	{
		return m_rt;
	}

	public ProjectType getProjectType()
	{
		return m_pt;
	}

	public String getMeasureDisplay()
	{
		return m_measureDisplay;
	}	//	getMeasureDisplay

	public Timestamp getDate()
	{
		return m_date;
	}	//	getDate

	public int getID()
	{
		return m_id;
	}

	/**
	 * @return Returns the label.
	 */
	public String getLabel ()
	{
		return m_label;
	}	//	getLabel

	/**
	 * @param label The label to set.
	 */
	public void setLabel (String label)
	{
		m_label = label;
		if (m_label != null)
			m_labelValue = s_format.format(m_value) + " - " + m_label;
		else
			m_labelValue = s_format.format(m_value);
	}	//	setLabel

	/**
	 *	@param date for label.
	 * 	@param MeasureDisplay measure display
	 */
	public void setLabel (Timestamp date, String MeasureDisplay)
	{
		if (date == null)
			return;
		m_date = date;
		m_measureDisplay = MeasureDisplay;
		//
		SimpleDateFormat format = DisplayType.getDateFormat(DisplayType.Date);
		String text = format.format(date);
		//	Month only
		if (MGoal.MEASUREDISPLAY_Month.equals(MeasureDisplay)
			|| MGoal.MEASUREDISPLAY_Quarter.equals(MeasureDisplay))
		{
			String pattern = format.toPattern();
			String mmText = text;
			int index = pattern.indexOf("dd");
			if (index == 0)			//	dd.MM.yyyy
				mmText = text.substring(3);
			else if (index > 0)		//	MM/dd/yyyy
			{
				mmText = text.substring(0, index-1);
				if (text.length() > index+2)
					mmText += text.substring(index+2);
			}
			setLabel(mmText);
		}
		else	//	Day
			setLabel(text);
	}	//	setLabel

	/**
	 * @return Returns the targetValue.
	 */
	public double getTargetValue ()
	{
		return m_targetValue;
	}	//	getTargetValue

	/**
	 * @param targetValue The targetValue to set.
	 */
	public void setTargetValue (double targetValue)
	{
		m_targetValue = targetValue;
	}	//	setTargetValue

	/**
	 * @return Returns the data value.
	 */
	public double getValue ()
	{
		return m_value;
	}	//	getValue

	/**
	 * @param value The data value to set.
	 */
	public void setValue (double value)
	{
		m_value = value;
		if (m_label != null)
			m_labelValue = s_format.format(m_value) + " - " + m_label;
		else
			m_labelValue = s_format.format(m_value);
	}	//	setValue

	/**
	 * @return Returns the column width in pixels.
	 */
	public double getColWidth ()
	{
		return m_width;
	}	//	getColWidth

	/**
	 * @param width The column width in pixels.
	 */
	public void setColWidth (double width)
	{
		m_width = width;
	}	//	getColWidth

	/**
	 * @return Returns the height in pixels.
	 */
	public double getColHeight()
	{
		return m_height;
	}	//	getHeight

	/**
	 * @param height The height in pixels.
	 */
	public void setColHeight (double height)
	{
		m_height = height;
	}	//	setHeight

	public MQuery getMQuery(MGoal mGoal)
	{
		MQuery query = null;
		if (getAchievement() != null)	//	Single Achievement
		{
			MAchievement a = getAchievement();
			query = MQuery.getEqualQuery("PA_Measure_ID", a.getPA_Measure_ID());
		}
		else if (getGoal() != null)		//	Multiple Achievements
		{
			MGoal goal = getGoal();
			query = MQuery.getEqualQuery("PA_Measure_ID", goal.getPA_Measure_ID());
		}
		else if (getMeasureCalc() != null)	//	Document
		{
			MMeasureCalc mc = getMeasureCalc();
			query = mc.getQuery(mGoal.getRestrictions(false),
					getMeasureDisplay(), getDate(),
					Env.getUserRolePermissions());	// logged in role
		}
		else if (getProjectType() != null)	//	Document
		{
			ProjectType pt = getProjectType();
			query = MMeasure.getQuery(pt, mGoal.getRestrictions(false),
					getMeasureDisplay(), getDate(), getID(),
					Env.getUserRolePermissions());	// logged in role
		}
		else if (getRequestType() != null)	//	Document
		{
			MRequestType rt = getRequestType();
			query = rt.getQuery(mGoal.getRestrictions(false),
					getMeasureDisplay(), getDate(), getID(),
					Env.getUserRolePermissions());	// logged in role
		}
		return query;
	}
}
