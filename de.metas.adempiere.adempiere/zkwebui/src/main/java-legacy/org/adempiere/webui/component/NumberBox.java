/******************************************************************************
 * Product: Posterita Ajax UI 												  *
 * Copyright (C) 2007 Posterita Ltd.  All Rights Reserved.                    *
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
 * Posterita Ltd., 3, Draper Avenue, Quatre Bornes, Mauritius                 *
 * or via info@posterita.org or http://www.posterita.org/                     *
 *****************************************************************************/

package org.adempiere.webui.component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;

import org.adempiere.webui.LayoutUtils;
import org.adempiere.webui.apps.AEnv;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.zkoss.zhtml.Table;
import org.zkoss.zhtml.Td;
import org.zkoss.zhtml.Tr;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Vbox;

/**
 *
 * @author  <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @date    Mar 11, 2007
 * @version $Revision: 0.10 $
 * 
 * @author Low Heng Sin
 */
public class NumberBox extends Div
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7089099079981906933L;

	private Textbox txtCalc = new Textbox();
    
    boolean integral = false;
    
    NumberFormat format = null;
    
    private Decimalbox decimalBox = null;
    private Button btn;
    
    private boolean btnEnabled = true;

	private Popup popup;
    
    /**
     * 
     * @param integral
     */
    public NumberBox(boolean integral)
    {
        super();
        this.integral = integral;
        init();
    }
    
    private void init()
    {
    	Table grid = new Table();
		appendChild(grid);
		grid.setStyle("border: none; padding: 0px; margin: 0px;");
		grid.setDynamicProperty("border", "0");
		grid.setDynamicProperty("cellpadding", "0");
		grid.setDynamicProperty("cellspacing", "0");
		
		Tr tr = new Tr();
		grid.appendChild(tr);
		tr.setStyle("border: none; padding: 0px; margin: 0px; white-space:nowrap; ");

		Td td = new Td();
		tr.appendChild(td);
		td.setStyle("border: none; padding: 0px; margin: 0px;");
		decimalBox = new Decimalbox();
    	if (integral)
    		decimalBox.setScale(0);
    	decimalBox.setStyle("display: inline;");
		td.appendChild(decimalBox);
		
		Td btnColumn = new Td();
		tr.appendChild(btnColumn);
		btnColumn.setStyle("border: none; padding: 0px; margin: 0px;");
		btnColumn.setSclass("editor-button");
		btn = new Button();
        btn.setImage("/images/Calculator10.png");
		btn.setTabindex(-1);
		LayoutUtils.addSclass("editor-button", btn);
		btnColumn.appendChild(btn);
        
        popup = getCalculatorPopup();
        LayoutUtils.addSclass("editor-button", btn);
        btn.setPopup(popup);
        btn.setStyle("text-align: center;");
        appendChild(popup);
     
        String style = AEnv.isFirefox2() ? "display: inline" : "display: inline-block"; 
        style = style + ";white-space:nowrap";
        this.setStyle(style);	     
    }
    
    /**
     * 
     * @param format
     */
    public void setFormat(NumberFormat format)
    {
    	this.format = format;
    }
    
    /**
     * 
     * @param value
     */
    public void setValue(Object value)
    {
    	if (value == null)
    		decimalBox.setValue(null);
    	else if (value instanceof BigDecimal)
    		decimalBox.setValue((BigDecimal) value);
    	else if (value instanceof Number)
    		decimalBox.setValue(new BigDecimal(((Number)value).doubleValue()));
    	else
    		decimalBox.setValue(new BigDecimal(value.toString()));
    }
    
    /**
     * 
     * @return BigDecimal
     */
    public BigDecimal getValue()
    {
    	return decimalBox.getValue();
    }
    
    /**
     * 
     * @return text
     */
    public String getText()
    {
    	BigDecimal value = decimalBox.getValue();
    	if (value == null) return null;
    	
    	if (format != null)
    		return format.format(value);
    	else
    		return value.toPlainString();
    }
    
    /**
     * 
     * @param value
     */
    public void setValue(String value)
    {
    	Number numberValue = null;
    	
    	if (format != null)
    	{
    		try
			{
    			numberValue = format.parse(value);
    			setValue(numberValue);
			}
			catch (ParseException e)
			{
			}
    	}
    	else
    	{
    		decimalBox.setValue(new BigDecimal(value));
    	}    	
    }
    
    private Popup getCalculatorPopup()
    {
        Popup popup = new Popup(); 

        Vbox vbox = new Vbox();

        char separatorChar = DisplayType.getNumberFormat(DisplayType.Number, Env.getLanguage(Env.getCtx())).getDecimalFormatSymbols().getDecimalSeparator();
        
        txtCalc = new Textbox();
        txtCalc.setAction("onKeyPress : return calc.validate('" + 
        		decimalBox.getId() + "','" + txtCalc.getId() 
                + "'," + integral + "," + (int)separatorChar + ", event);");
        txtCalc.setMaxlength(250);
        txtCalc.setCols(30);
        
        String txtCalcId = txtCalc.getId();

        vbox.appendChild(txtCalc);
        Hbox row1 = new Hbox();

        Button btnAC = new Button();
        btnAC.setWidth("40px");
        btnAC.setLabel("AC");
        btnAC.setAction("onClick : calc.clearAll('" + txtCalcId + "')");

        Button btn7 = new Button();
        btn7.setWidth("30px");
        btn7.setLabel("7");
        btn7.setAction("onClick : calc.append('" + txtCalcId + "', '7')");

        Button btn8 = new Button();
        btn8.setWidth("30px");
        btn8.setLabel("8");
        btn8.setAction("onClick : calc.append('" + txtCalcId + "', '8')");

        Button btn9 = new Button();
        btn9.setWidth("30px");
        btn9.setLabel("9");
        btn9.setAction("onClick : calc.append('" + txtCalcId + "', '9')");

        Button btnMultiply = new Button();
        btnMultiply.setWidth("30px");
        btnMultiply.setLabel("*");
        btnMultiply.setAction("onClick : calc.append('" + txtCalcId + "', ' * ')");

        row1.appendChild(btnAC);
        row1.appendChild(btn7);
        row1.appendChild(btn8);
        row1.appendChild(btn9);
        row1.appendChild(btnMultiply);

        Hbox row2 = new Hbox();

        Button btnC = new Button();
        btnC.setWidth("40px");
        btnC.setLabel("C");
        btnC.setAction("onClick : calc.clear('" + txtCalcId + "')");
        
        Button btn4 = new Button();
        btn4.setWidth("30px");
        btn4.setLabel("4");
        btn4.setAction("onClick : calc.append('" + txtCalcId + "', '4')");

        Button btn5 = new Button();
        btn5.setWidth("30px");
        btn5.setLabel("5");
        btn5.setAction("onClick : calc.append('" + txtCalcId + "', '5')");

        Button btn6 = new Button();
        btn6.setWidth("30px");
        btn6.setLabel("6");
        btn6.setAction("onClick : calc.append('" + txtCalcId + "', '6')");
        
        Button btnDivide = new Button();
        btnDivide.setWidth("30px");
        btnDivide.setLabel("/");
        btnDivide.setAction("onClick : calc.append('" + txtCalcId + "', ' / ')");

        row2.appendChild(btnC);
        row2.appendChild(btn4);
        row2.appendChild(btn5);
        row2.appendChild(btn6);
        row2.appendChild(btnDivide);

        Hbox row3 = new Hbox();

        Button btnModulo = new Button();
        btnModulo.setWidth("40px");
        btnModulo.setLabel("%");
        btnModulo.setAction("onClick : calc.append('" + txtCalcId + "', ' % ')");

        Button btn1 = new Button();
        btn1.setWidth("30px");
        btn1.setLabel("1");
        btn1.setAction("onClick : calc.append('" + txtCalcId + "', '1')");

        Button btn2 = new Button();
        btn2.setWidth("30px");
        btn2.setLabel("2");
        btn2.setAction("onClick : calc.append('" + txtCalcId + "', '2')");

        Button btn3 = new Button();
        btn3.setWidth("30px");
        btn3.setLabel("3");
        btn3.setAction("onClick : calc.append('" + txtCalcId + "', '3')");

        Button btnSubstract = new Button();
        btnSubstract.setWidth("30px");
        btnSubstract.setLabel("-");
        btnSubstract.setAction("onClick : calc.append('" + txtCalcId + "', ' - ')");

        row3.appendChild(btnModulo);
        row3.appendChild(btn1);
        row3.appendChild(btn2);
        row3.appendChild(btn3);
        row3.appendChild(btnSubstract);

        Hbox row4 = new Hbox();

        Button btnCurrency = new Button();
        btnCurrency.setWidth("40px");
        btnCurrency.setLabel("$");
        btnCurrency.setDisabled(true);

        Button btn0 = new Button();
        btn0.setWidth("30px");
        btn0.setLabel("0");
        btn0.setAction("onClick : calc.append('" + txtCalcId + "', '0')");

        String separator = Character.toString(separatorChar);
        Button btnDot = new Button();
        btnDot.setWidth("30px");
        btnDot.setLabel(separator);
        btnDot.setDisabled(integral);
        btnDot.setAction("onClick : calc.append('" + txtCalcId + "', '" + separator + "')");

        Button btnEqual = new Button();
        btnEqual.setWidth("30px");
        btnEqual.setLabel("=");
        btnEqual.setAction("onClick : calc.evaluate('" + decimalBox.getId() + "','" 
                + txtCalcId + "','" + separator + "')");
        
        Button btnAdd = new Button();
        btnAdd.setWidth("30px");
        btnAdd.setLabel("+");
        btnAdd.setAction("onClick : calc.append('" + txtCalcId + "', ' + ')");

        row4.appendChild(btnCurrency);
        row4.appendChild(btnDot);
        row4.appendChild(btn0);
        row4.appendChild(btnEqual);
        row4.appendChild(btnAdd);

        vbox.appendChild(row1);
        vbox.appendChild(row2);
        vbox.appendChild(row3);
        vbox.appendChild(row4);

        popup.appendChild(vbox);
        return popup;
    }

    /**
     * 
     * @return boolean
     */
	public boolean isIntegral() {
		return integral;
	}

	/**
	 * 
	 * @param integral
	 */
	public void setIntegral(boolean integral) {
		this.integral = integral;
		if (integral)
			decimalBox.setScale(0);
		else
			decimalBox.setScale(Decimalbox.AUTO);
	}
	
	/**
	 * 
	 * @param enabled
	 */
	public void setEnabled(boolean enabled)
	{
	     decimalBox.setReadonly(!enabled);
	     
	     boolean isCalculatorEnabled = btnEnabled && enabled;
	     btn.setEnabled(isCalculatorEnabled);
	     if (isCalculatorEnabled)
	    	 btn.setPopup(popup);
	     else 
	     {
	    	 Popup p = null;
	    	 btn.setPopup(p);
	     }
	}
	
	/**
	 * 
	 * @return boolean
	 */
	public boolean isEnabled()
	{
		 return decimalBox.isReadonly();
	}
	
	@Override
	public boolean addEventListener(String evtnm, EventListener listener)
	{
	     if(Events.ON_CLICK.equals(evtnm))
	     {
	       	 return btn.addEventListener(evtnm, listener);
	     }
	     else
	     {
	         return decimalBox.addEventListener(evtnm, listener);
	     }
	}
	
	@Override
	public void focus()
	{
		decimalBox.focus();
	}
	
	/**
	 * 
	 * @return decimalBox
	 */
	public Decimalbox getDecimalbox()
	{
		return decimalBox;
	}
	
	public void setCalculatorEnabled(boolean enabled)
	{
		btnEnabled = enabled;
		btn.setEnabled(btnEnabled);
		btn.setVisible(btnEnabled);
	}
	public boolean isCalculatorEnabled()
	{
		return this.btnEnabled;
	}
}
