package de.metas.adempiere.process;

/*
 * #%L
 * de.metas.swat.base
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


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;

import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.rtf.RTFEditorKit;

import org.adempiere.util.time.SystemTime;
import org.compiere.model.MBPartner;
import org.compiere.model.Query;
import org.compiere.util.TimeUtil;

import de.metas.interfaces.I_C_BPartner;
import de.metas.process.SvrProcess;

public class ConvertBPartnerMemo extends SvrProcess {

	@Override
	protected String doIt() throws Exception {

		final String whereClause = "";

		final List<MBPartner> bPartners = new Query(getCtx(),
				I_C_BPartner.Table_Name, whereClause, get_TrxName())
				.setOnlyActiveRecords(true).setClient_ID().list();

		final Timestamp startDate = SystemTime.asTimestamp();
		int counter = 0;
		for (final MBPartner bPartner : bPartners) {

			final String memoInput = (String) bPartner.get_Value(I_C_BPartner.COLUMNNAME_Memo);

			if (memoInput == null) {
				continue;
			}

			if (memoInput.toUpperCase().startsWith("<HTML")) {
				// string is already converted
				continue;
			}

			final InputStream r = new ByteArrayInputStream(memoInput.getBytes());

			final DefaultStyledDocument doc = new DefaultStyledDocument();

			final EditorKit inputEditorKit;
			if (memoInput.startsWith("{\\rtf")) {
				inputEditorKit = new RTFEditorKit();
			} else {
				inputEditorKit = new DefaultEditorKit();
			}

			inputEditorKit.read(r, doc, 0);

			final HTMLEditorKit outputEditorKit = new HTMLEditorKit();

			final ByteArrayOutputStream outpOutputStream = new ByteArrayOutputStream();
			outputEditorKit.write(outpOutputStream, doc, 0, doc.getLength());

			final String memoHtml = outpOutputStream.toString().replace(
					"font-size: 8pt", "font-size: 16pt");

			bPartner.set_ValueOfColumn(I_C_BPartner.COLUMNNAME_Memo, memoHtml);
			bPartner.saveEx();
			counter++;
			
			if (counter % 1000 == 0) {
				commitEx();
			}
		}

		addLog("Updated " + counter + " bpartners in "
				+ TimeUtil.formatElapsed(startDate));
		return "@Success@";
	}

	@Override
	protected void prepare() {
		// nothing to do
	}

}
