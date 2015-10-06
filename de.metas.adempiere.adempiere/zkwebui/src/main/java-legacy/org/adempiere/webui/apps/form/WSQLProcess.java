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

/**
 * 2007, Modified by Posterita Ltd.
 */

package org.adempiere.webui.apps.form;

import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.Textbox;
import org.adempiere.webui.panel.ADForm;
import org.compiere.apps.form.VSQLProcess;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;

/**
 * A Custom Form to specify and process SQL statements.
 *
 * The range of statement types that can be performed can be restricted
 * by allowing or disallowing DML statements.
 *
 * @author Andrew Kimball
 *
 */
public class WSQLProcess extends ADForm implements EventListener
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2038792517003449189L;

	/** Log. */
    private static CLogger  log = CLogger.getCLogger(VSQLProcess.class);

    /** Grid used to layout components. */
    private Grid m_grdMain = new Grid();
    /** SQL label. */
    private Label m_lblSql = new Label("SQL");
    /** SQL statement field. */
    private Textbox m_txbSqlField = new Textbox();
    /** Process button. */
    private Button m_btnSql = createProcessButton();
    /** Field to hold result of SQL statement execution. */
    private Textbox m_txbResultField = new Textbox();

    /**
     * Default constructor.
     */
    public WSQLProcess()
    {
        super();
    }

    @Override
    protected void initForm()
    {
        Row rwTop = new Row();
        Row rwBottom = new Row();
        Rows rows = new Rows();
        final int noColumns = 60;
        final int maxStatementLength = 9000;
        final int noStatementRows = 3;
        final int noResultRows = 20;

        m_grdMain.setWidth("80%");

        // create the top row of components
        m_txbSqlField.setMultiline(true);
        m_txbSqlField.setMaxlength(maxStatementLength);
        m_txbSqlField.setRows(noStatementRows);
        m_txbSqlField.setCols(noColumns);
        m_txbSqlField.setReadonly(false);

        m_btnSql.addEventListener(Events.ON_CLICK, this);

        rwTop.appendChild(m_lblSql);
        rwTop.appendChild(m_txbSqlField);
        rwTop.appendChild(m_btnSql);

        rows.appendChild(rwTop);

        // create the bottom row of components
        m_txbResultField.setCols(noColumns);
        m_txbResultField.setRows(noResultRows);
        m_txbResultField.setReadonly(true);

        rwBottom.appendChild(m_txbResultField);
        rwBottom.setSpans("3");
        rwBottom.setAlign("center");

        rows.appendChild(rwBottom);

        // put it all together
        m_grdMain.appendChild(rows);

        this.appendChild(m_grdMain);

        return;
    }

    /**
     *  Create Process Button.
     *  @return button
     */
    public static final Button createProcessButton()
    {
        Button btnProcess = new Button();

        btnProcess.setImage("/images/Process24.png");
        btnProcess.setName(Msg.getMsg(Env.getCtx(), "Process"));

        return btnProcess;
    }   //  createProcessButton

    /**
     *  Process a semicolon delimitted list of SQL Statements.
     *
     *  @param sqlStatements    one or more statements separated by a semicolon (';')
     *  @param allowDML         whether to allow DML statements
     *  @return a string summarising the results
     */
    public static String processStatements (String sqlStatements, boolean allowDML)
    {
        return VSQLProcess.processStatements(sqlStatements, allowDML);
    }

    /**
     *  Process SQL Statements.
     *
     *  @param sqlStatement a single SQL statement
     *  @param allowDML     whether to allow DML statements
     *  @return a string summarising the results
     */
    public static String processStatement (String sqlStatement, boolean allowDML)
    {
        return VSQLProcess.processStatement(sqlStatement, allowDML);
    }


    /*
     * (non-Javadoc)
     * @see org.adempiere.webui.panel.ADForm#onEvent(org.zkoss.zk.ui.event.Event)
     */
    public void onEvent(Event event) throws Exception
    {
        m_txbResultField.setText(processStatements (m_txbSqlField.getText(), false));
    }
}
