/**********************************************************************
 * This file is part of Adempiere ERP Bazaar                           *
 * http://www.adempiere.org                                            *
 *                                                                     *
 * Copyright (C) Fernando Lucktemberg - fer_luck                       *
 * Copyright (C) Contributors                                          *
 *                                                                     *
 * This program is free software; you can redistribute it and/or       *
 * modify it under the terms of the GNU General Public License         *
 * as published by the Free Software Foundation; either version 2      *
 * of the License, or (at your option) any later version.              *
 *                                                                     *
 * This program is distributed in the hope that it will be useful,     *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of      *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
 * GNU General Public License for more details.                        *
 *                                                                     *
 * You should have received a copy of the GNU General Public License   *
 * along with this program; if not, write to the Free Software         *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
 * MA 02110-1301, USA.                                                 *
 *                                                                     *
 * Contributors:                                                       *
 * - Fernando Lucktemberg - fer_luck                                   *
 *                                                                     *
 * Sponsors:                                                           *
 * - Company (http://www.faire.com.br)                                 *
 ***********************************************************************/
package org.adempiere.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.SvrProcess;

import org.compiere.util.DB;

public class ApplyMigrationScripts extends SvrProcess {

	/** Logger */
	private static Logger log = LogManager.getLogger(ApplyMigrationScripts.class);

	@Override
	protected String doIt() throws Exception {
		// TODO Auto-generated method stub
		log.info("Applying migrations scripts");
		String sql = "select ad_migrationscript_id, script, name from ad_migrationscript where isApply = 'Y' and status = 'IP' order by name, created";
		PreparedStatement pstmt = DB.prepareStatement(sql, this.get_TrxName());
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			byte[] scriptArray = rs.getBytes(2);
			int seqID = rs.getInt(1);
			boolean execOk = true;
			try {
				StringBuffer tmpSql = new StringBuffer();
				tmpSql = new StringBuffer(new String(scriptArray));

				if (tmpSql.length() > 0) {
					log.info("Executing script " + rs.getString(3));
					execOk = executeScript(tmpSql.toString(), rs.getString(3));
					System.out.println();
				}
			} catch (SQLException e) {
				execOk = false;
				log.error("Script: " + rs.getString(3) + " - " + e.getMessage(), e);
			} finally {
				sql = "UPDATE ad_migrationscript SET status = ? , isApply = 'N' WHERE ad_migrationscript_id = ? ";
				pstmt = DB.prepareStatement(sql, this.get_TrxName());
				if (execOk) {
					pstmt.setString(1, "CO");
					pstmt.setInt(2, seqID);
				} else {
					pstmt.setString(1, "ER");
					pstmt.setInt(2, seqID);
				}
				try {
					pstmt.executeUpdate();
					if (!execOk) {
						pstmt.close();
						return null;
					}
				} catch (SQLException e) {
					log.error("Script: " + rs.getString(3) + " - " + e.getMessage(), e);
				}
			}
		}
		rs.close();
		pstmt.close();
		return null;
	}

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	public boolean executeScript(String sql, String fileName) {
		BufferedReader reader = new BufferedReader(new StringReader(sql));
		StringBuffer sqlBuf = new StringBuffer();
		String line;
		boolean statementReady = false;
		boolean execOk = true;
		boolean longComment = false;
		try {
			while ((line = reader.readLine()) != null) {
				// different continuation for oracle and postgres
				line = line.trim();
				//Check if it's a comment
				if (line.startsWith("--") || line.length() == 0){
					continue;
				} else if (line.endsWith(";") && !longComment) {
					sqlBuf.append(' ');
					sqlBuf.append(line.substring(0, line.length() - 1));
					statementReady = true;
				} else if(line.startsWith("/*")){
					longComment = true;
				} else if(line.endsWith("*/")){
					longComment = false;
				} else {
					if(longComment)
						continue;
					sqlBuf.append(' ');
					sqlBuf.append(line);
					statementReady = false;
				}

				if (statementReady) {
					if (sqlBuf.length() == 0)
						continue;
					Connection conn = DB.getConnectionRW();
					conn.setAutoCommit(false);
					Statement stmt = null;
					try {
						stmt = conn.createStatement();
						stmt.execute(sqlBuf.toString());
						System.out.print(".");
					} catch (SQLException e) {
						e.printStackTrace();
						execOk = false;
						log.error("Script: " + fileName + " - " + e.getMessage() + ". The line that caused the error is the following ==> " + sqlBuf, e);
					} finally {
						stmt.close();
						if(execOk)
							conn.commit();
						else
							conn.rollback();
						conn.setAutoCommit(true);
						conn.close();
						if(!execOk)
							return false;
					}
					sqlBuf.setLength(0);					
				}
			}
		} catch(SQLException e){
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

}
