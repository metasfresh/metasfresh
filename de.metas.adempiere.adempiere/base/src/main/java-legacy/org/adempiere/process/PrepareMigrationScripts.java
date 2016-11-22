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

import java.io.File;
import java.io.FilenameFilter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Vector;
import org.compiere.Adempiere;
import org.compiere.model.MSequence;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

public class PrepareMigrationScripts extends JavaProcess {

	String path;

	// Charset found variable
	public static boolean found = false;

	@Override
	protected String doIt() throws Exception {
		String directory;
		if (path != null)
			directory = path;
		else
			return "ERROR - No path";
		File dir = new File(directory);

		// The list of files can also be retrieved as File objects
		File[] dirList = dir.listFiles();
		Vector<String> fileName = new Vector<String>();

		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".sql");
			}
		};
		dirList = dir.listFiles(filter);

		log.info("Searching for SQL files in the " + dir + " directory");

		String msg = "";

		// Get Filenames
		for (int i = 0; i < dirList.length; i++) {
			fileName.add(dirList[i].toString()
					.substring(directory.length() + 1));
			log
					.debug("Found file ["
							+ fileName.get(i)
							+ "]. Finding out if the script has or hasn't been applied yet...");
			try {
				// First of all, check if the script hasn't been applied yet...
				String checkScript = "select ad_migrationscript_id from ad_migrationscript where name = ?";
				PreparedStatement pstmt = DB.prepareStatement(checkScript, this
						.get_TrxName());
				pstmt.setString(1, fileName.get(i));
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					log.warn("Script " + fileName.get(i)
							+ " already in the database");
					pstmt.close();
					continue;
				}
				pstmt.close();
				// first use a Scanner to get each line
				Scanner scanner = new Scanner(dirList[i]);
				StringBuffer body = new StringBuffer();
				boolean blHeader = false;
				boolean blBody = false;
				boolean isFirstLine = true;
				boolean hasHeader = false;
				String Name = fileName.get(i);
				String Description = "Unknown";
				String ProjectName = "Adempiere";
				String tmp = Adempiere.getMainVersion();
				tmp = tmp.replace("Release ", "");
				tmp = tmp.replace(".", "");
				String ReleaseNo = tmp;
				String DeveloperName = "Not known";
				String Reference = "--";
				String Url = "http://www.sourceforge.net/projects/adempiere";
				Timestamp ts = new Timestamp(Calendar.getInstance()
						.getTimeInMillis());
				while (scanner.hasNextLine()) {
					String line = null;
					// if it's the first line check for the header
					if (isFirstLine)
						line = scanner.nextLine();
					if (isFirstLine && line.equals("--BEGINHEADER--"))
						hasHeader = true;
					if (hasHeader) {
						if (!isFirstLine)
							line = scanner.nextLine();
						if (line.equals("--ENDHEADER--")) {
							blHeader = false;
							blBody = false;
						}
						if (line.equals("--ENDMS--")) {
							blHeader = false;
							blBody = false;
						}
						if (blHeader) {
							if (line.startsWith("Name")) {
								Name = line.substring("Name".length() + 1).trim();
							} else if (line.startsWith("Description")) {
								Description = line.substring("Description"
										.length() + 1).trim();
							} else if (line.startsWith("ProjectName")) {
								ProjectName = line.substring("ProjectName"
										.length() + 1).trim();
							} else if (line.startsWith("ReleaseNo")) {
								ReleaseNo = line
										.substring("ReleaseNo".length() + 1).trim();
							} else if (line.startsWith("DeveloperName")) {
								DeveloperName = line.substring("DeveloperName"
										.length() + 1).trim();
							} else if (line.startsWith("Reference")) {
								Reference = line
										.substring("Reference".length() + 1).trim();
							} else if (line.startsWith("Url")) {
								Url = line.substring("Url".length() + 1).trim();
							}
						}
						if (blBody) {
							body.append(line + '\n');
						}
						if (line.equals("--BEGINHEADER--")) {
							blHeader = true;
							blBody = false;
						}
						if (line.equals("--BEGINMS--")) {
							blHeader = false;
							blBody = true;
						}
					} else {
						if (!isFirstLine)
							line = scanner.nextLine();
						body.append(line + '\n');
					}
					isFirstLine = false;
				}
				scanner.close();
				int seqID = MSequence.getNextID(0, "AD_MigrationScript", this
						.get_TrxName());
				String sql = "INSERT INTO ad_migrationscript (ad_client_id, ad_org_id, "
						+ "ad_migrationscript_id,  createdby, "
						+ "name, projectname, "
						+ "releaseno, status, "
						+ "url, updatedby, "
						+ "filename, description, "
						+ "developername, reference, "
						+ "isactive, isapply, "
						+ "created, updated) "
						+ "VALUES "
						+ "(0, 0, ?, ?, "
						+ "?, ?, ?, ?, "
						+ "?, ?, ?, ?, "
						+ "?, ?, ?, ?, "
						+ "?, ? )";
				pstmt = DB.prepareStatement(sql, this.get_TrxName());
				pstmt.setInt(1, seqID);
				pstmt.setInt(2, Env.getAD_User_ID(Env.getCtx()));
				pstmt.setString(3, Name);
				pstmt.setString(4, ProjectName);
				pstmt.setString(5, ReleaseNo);
				pstmt.setString(6, "IP");
				pstmt.setString(7, Url);
				pstmt.setInt(8, Env.getAD_User_ID(Env.getCtx()));
				pstmt.setString(9, path);
				pstmt.setString(10, Description);
				pstmt.setString(11, DeveloperName);
				pstmt.setString(12, Reference);
				pstmt.setString(13, "Y");
				pstmt.setString(14, "Y");
				pstmt.setTimestamp(15, ts);
				pstmt.setTimestamp(16, ts);
				int result = pstmt.executeUpdate();
				pstmt.close();
				if (result > 0)
					log.info("Header inserted. Now inserting the script body");
				else {
					log.error("Script " + fileName.get(i) + " failed!");
					msg = msg + "Script " + fileName.get(i) + " failed!";
					continue;
				}
				sql = "update AD_MigrationScript set script = ? where AD_MigrationScript_ID = ?";
				pstmt = DB.prepareStatement(sql, this.get_TrxName());
				pstmt.setBytes(1, body.toString().getBytes());
				pstmt.setInt(2, seqID);
				result = pstmt.executeUpdate();
				pstmt.close();
				if (result > 0)
					log.info("Script Body inserted.");
				else {
					log.error("Script Body " + fileName.get(i) + " failed!");
					msg = msg + "Script Body " + fileName.get(i) + " failed!";
					pstmt = DB
							.prepareStatement(
									"delete from ad_migrationscript = ad_migrationscript_id = ?",
									this.get_TrxName());
					pstmt.setInt(1, seqID);
					result = pstmt.executeUpdate();
					continue;
				}
			} catch (Exception ex) {
				log.error(ex.getMessage());
			}
		}
		return "Sucess";
	}

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("ScriptsPath"))
				path = (String) para[i].getParameter();
			else
				log.error("Unknown Parameter: " + name);
		}

	}
}
