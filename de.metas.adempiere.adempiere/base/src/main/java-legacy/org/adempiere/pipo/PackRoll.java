/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *
 * Copyright (C) 2005 Robert Klein. robeklein@hotmail.com
 * Contributor(s): Carlos Ruiz - globalqss
 *****************************************************************************/
package org.adempiere.pipo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * Reverse Package Install.
 * 
 * @author Robert Klein
 * 
 */
public class PackRoll extends SvrProcess {
	/** Package from Record */
	private int m_AD_Package_Imp_ID = 0;
	private String m_Processing = null;
	StringBuffer sql = null;
	StringBuffer sqlB = null;
	String columnIDName = null;
	StringBuffer sqlC = null;
	StringBuffer sqlD = null;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	protected void prepare() {
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("Processing"))
				m_Processing = (String) para[i].getParameter();
			else
				log.error("prepare - Unknown Parameter: " + name);
		}
		m_AD_Package_Imp_ID = getRecord_ID();
	} // prepare

	/**
	 * Perform process.
	 * 
	 * @return Message (translated text)
	 * @throws Exception
	 *             if not successful
	 */
	protected String doIt() throws Exception {

		sqlB = new StringBuffer("UPDATE AD_Package_Imp "
				+ "SET PK_Status = 'Uninstalling' "
				+ "WHERE AD_Package_Imp_ID = " + m_AD_Package_Imp_ID);
		int no = DB.executeUpdate(sqlB.toString(), get_TrxName());

		log.info("Starting Package Reversal");
		// select all records that are new or have been updated by package
		// install
		sql = new StringBuffer("SELECT * " + "FROM AD_Package_Imp_Detail "
				+ "WHERE AD_Package_Imp_ID=" + m_AD_Package_Imp_ID
				+ " ORDER BY AD_Package_Imp_Detail_ID DESC");
		log.info(sql.toString());
		PreparedStatement pstmt = null;
		try {
			pstmt = DB.prepareStatement(sql.toString(), null);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				if (rs.getString("Type").compareTo("file") == 0) {

					sqlB = new StringBuffer("SELECT * "
							+ "FROM AD_Package_Imp_Backup "
							+ "WHERE AD_Package_Imp_Detail_ID="
							+ rs.getInt("AD_Package_Imp_Detail_ID")
							+ " AND AD_Package_Imp_ID="
							+ rs.getInt("AD_Package_Imp_ID"));
					PreparedStatement pstmt2 = null;
					try {
						pstmt2 = DB.prepareStatement(sqlB.toString(),
								get_TrxName());

						ResultSet rs2 = pstmt2.executeQuery();

						while (rs2.next()) {
							if (rs2.getString("AD_Package_Imp_Bck_Dir") != null
									&& rs2.getString("AD_Package_Imp_Org_Dir") != null) {
								copyFile(rs2
										.getString("AD_Package_Imp_Bck_Dir"),
										rs2.getString("AD_Package_Imp_Org_Dir"));
							}

							// Update uninstall field for column
							sqlD = new StringBuffer(
									"UPDATE AD_Package_Imp_Backup"
											+ " SET Uninstall = 'Y'"
											+ " WHERE AD_Package_Imp_Backup_ID = "
											+ rs2
													.getInt("AD_Package_Imp_Backup_ID"));
							no = DB.executeUpdate(sqlD.toString(), null);

							// Update uninstall field for record
							sqlD = new StringBuffer(
									"UPDATE AD_Package_Imp_Detail"
											+ " SET Uninstall = 'Y'"
											+ " WHERE AD_Package_Imp_Detail_ID = "
											+ rs
													.getInt("AD_Package_Imp_Detail_ID"));
							no = DB.executeUpdate(sqlD.toString(), null);
						}

						rs2.close();
						pstmt2.close();
						pstmt2 = null;
					} catch (Exception e) {
						log.error("doIt", e);
					} finally {
						try {
							if (pstmt2 != null)
								pstmt2.close();
						} catch (Exception e) {
						}
						pstmt2 = null;
					}
				} else {

					String tableName = rs.getString("TableName");

					int recordID = rs.getInt("AD_Original_ID");

					// determine if record is an update to the original
					// if record is an update then update record with backup
					// settings
					// else inactivate record
					if (rs.getString("ACTION").compareTo("Update") == 0) {
						// select all backed up columns for the record
						sqlB = new StringBuffer("SELECT * "
								+ "FROM AD_Package_Imp_Backup "
								+ "WHERE AD_Package_Imp_Detail_ID="
								+ rs.getInt("AD_Package_Imp_Detail_ID")
								+ " AND AD_Package_Imp_ID="
								+ rs.getInt("AD_Package_Imp_ID"));
						PreparedStatement pstmt2 = null;
						try {
							pstmt2 = DB.prepareStatement(sqlB.toString(),
									get_TrxName());

							ResultSet rs2 = pstmt2.executeQuery();

							while (rs2.next()) {

								sql = new StringBuffer(
										"SELECT IsKey FROM AD_Column WHERE AD_Column_ID = ?");
								String IsKey = DB.getSQLValueString(
										get_TrxName(), sql.toString(), rs2
												.getInt("AD_Column_ID"));

								// Get Table value
								sql = new StringBuffer(
										"SELECT TableName FROM AD_Table WHERE AD_Table_ID = ?");
								tableName = DB.getSQLValueString(get_TrxName(),
										sql.toString(), rs2
												.getInt("AD_Table_ID"));

								// Get Column Name
								sql = new StringBuffer(
										"SELECT ColumnName FROM AD_Column WHERE AD_Column_ID = ?");
								String columnName = DB.getSQLValueString(
										get_TrxName(), sql.toString(), rs2
												.getInt("AD_Column_ID"));
								// log.info(columnName);

								// Adjust for Column reference table
								if (tableName.equals("AD_Ref_Table")) {
									columnIDName = "AD_Reference_ID";
								} else if (tableName.equals("AD_TreeNodeMM")) {
									columnIDName = "Node_ID";
								} else {
									columnIDName = tableName + "_ID";
								}

								// Update columns for record
								// TODO make process more efficient!
								if (IsKey.equals("Y")
										|| columnName.startsWith("Created"))
									; // ignore is a Key Column or if it
										// references a Created(By) Column
								// Update "Updated" field with current date
								else if (columnName.equals("Updated")) {
									// Format Date
									sqlC = new StringBuffer("UPDATE "
											+ tableName + " SET " + columnName
											+ " = now() WHERE "
											+ columnIDName + " = " + recordID);

									no = DB.executeUpdate(sqlC.toString(), null);
									// Update uninstall field
									sqlD = new StringBuffer(
											"UPDATE AD_Package_Imp_Backup"
													+ " SET Uninstall = 'Y'"
													+ " WHERE AD_Package_Imp_Backup_ID = "
													+ rs2.getInt("AD_Package_Imp_Backup_ID"));
									no = DB.executeUpdate(sqlD.toString(), null);
								}
								// Update "UpdatedBy" field with current user
								else if (columnName.equals("UpdatedBy")) {
									sqlC = new StringBuffer("UPDATE "
											+ tableName + " SET " + columnName
											+ " = '"
											+ Env.getAD_User_ID(Env.getCtx())
											+ "' WHERE " + columnIDName + " = "
											+ recordID);
									no = DB
											.executeUpdate(sqlC.toString(),
													null);

									sqlD = new StringBuffer(
											"UPDATE AD_Package_Imp_Backup"
													+ " SET Uninstall = 'Y'"
													+ " WHERE AD_Package_Imp_Backup_ID = "
													+ rs2
															.getInt("AD_Package_Imp_Backup_ID"));
									no = DB
											.executeUpdate(sqlD.toString(),
													null);
								}
								// Update all other fields with backup
								// information
								else {

									int v_AD_Reference_ID = rs2
											.getInt("AD_Reference_ID");
									// Update columns that are Strings adjusting
									// for single quotes
									if (v_AD_Reference_ID == 10
											|| v_AD_Reference_ID == 14
											|| v_AD_Reference_ID == 34
											|| v_AD_Reference_ID == 17
											// Carlos Ruiz globalqss, special
											// treatment for EntityType
											// it's a Table reference but must
											// be treated as String
											|| (v_AD_Reference_ID == 18 && columnName
													.equalsIgnoreCase("EntityType")))
										if (rs2.getObject("ColValue")
												.toString().equals("null")) {
											;// Ignore null values
										} else {
											sqlC = new StringBuffer("UPDATE "
													+ tableName
													+ " SET "
													+ columnName
													+ " = "
													+ "'"
													+ rs2.getObject("ColValue")
															.toString()
															.replaceAll("'",
																	"''") + "'"
													+ " WHERE " + columnIDName
													+ " = " + recordID);
										}
									// Update true/false columns
									else if (v_AD_Reference_ID == 20
											|| v_AD_Reference_ID == 28) {
										sqlC = new StringBuffer("UPDATE "
												+ tableName
												+ " SET "
												+ columnName
												+ " = "
												+ (rs2.getObject("ColValue")
														.toString().equals(
																"true") ? "'Y'"
														: "'N'") + " WHERE "
												+ columnIDName + " = "
												+ recordID);
									}
									// Update columns that are Strings adjusting
									// for single quotes
									else if (v_AD_Reference_ID == 13
											|| v_AD_Reference_ID == 18
											|| v_AD_Reference_ID == 19
											|| v_AD_Reference_ID == 21
											|| v_AD_Reference_ID == 25
											|| v_AD_Reference_ID == 27
											|| v_AD_Reference_ID == 30
											|| v_AD_Reference_ID == 31
											|| v_AD_Reference_ID == 35)
										sqlC = new StringBuffer("UPDATE "
												+ tableName
												+ " SET "
												+ columnName
												+ " = "
												+ rs2.getObject("ColValue")
														.toString().replaceAll(
																"'", "''")
												+ " WHERE " + columnIDName
												+ " = " + recordID);
									// Update columns that are numbers
									else if (v_AD_Reference_ID == 11
											|| v_AD_Reference_ID == 12
											|| v_AD_Reference_ID == 22
											|| v_AD_Reference_ID == 29)
										sqlC = new StringBuffer("UPDATE "
												+ tableName
												+ " SET "
												+ columnName
												+ " = "
												+ rs2.getObject("ColValue")
														.toString().replaceAll(
																"'", "''")
												+ " WHERE " + columnIDName
												+ " = " + recordID);
									// Update columns that are dates
									else if (v_AD_Reference_ID == 15
											|| v_AD_Reference_ID == 16)
										// TODO Develop portable code to update
										// date columns
										;// ignore
									else
										// 23-Binary, 24-Radio, 26-RowID,
										// 32-Image not supported
										;// ignore
									// execute update
									no = DB
											.executeUpdate(sqlC.toString(),
													null);

									// Update uninstall field for column
									sqlD = new StringBuffer(
											"UPDATE AD_Package_Imp_Backup"
													+ " SET Uninstall = 'Y'"
													+ " WHERE AD_Package_Imp_Backup_ID = "
													+ rs2
															.getInt("AD_Package_Imp_Backup_ID"));
									no = DB
											.executeUpdate(sqlD.toString(),
													null);

									// Update uninstall field for record
									sqlD = new StringBuffer(
											"UPDATE AD_Package_Imp_Detail"
													+ " SET Uninstall = 'Y'"
													+ " WHERE AD_Package_Imp_Detail_ID = "
													+ rs
															.getInt("AD_Package_Imp_Detail_ID"));
									no = DB
											.executeUpdate(sqlD.toString(),
													null);

								}
							}
							rs2.close();
							pstmt2.close();
							pstmt2 = null;
						} catch (Exception e) {
							log.error("doIt", e);
						} finally {
							try {
								if (pstmt2 != null)
									pstmt2.close();
							} catch (Exception e) {
							}
							pstmt2 = null;
						}
					} // ********* Update Loop
					// Inactivate new records
					else if (rs.getString("ACTION").compareTo("New") == 0) {
						if (tableName.equals("AD_Ref_Table"))
							columnIDName = "AD_Reference_ID";
						else if (tableName.equals("AD_TreeNodeMM"))
							columnIDName = "Node_ID";
						else
							columnIDName = tableName + "_ID";
						sqlC = new StringBuffer("UPDATE " + tableName
								+ " SET IsActive = 'N'" + " WHERE "
								+ columnIDName + " = " + recordID);

						// execute update
						no = DB.executeUpdate(sqlC.toString(), null);

						// Update uninstall field for record
						sqlD = new StringBuffer("UPDATE AD_Package_Imp_Detail"
								+ " SET Uninstall = 'Y'"
								+ " WHERE AD_Package_Imp_Detail_ID = "
								+ rs.getInt("AD_Package_Imp_Detail_ID"));
						no = DB.executeUpdate(sqlD.toString(), null);
					}
				}

			}
			rs.close();
			pstmt.close();
			pstmt = null;
		} catch (Exception e) {
			log.error("doIt", e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
			}
			pstmt = null;
		}
		// Update uninstall field for package
		sqlD = new StringBuffer("UPDATE AD_Package_Imp"
				+ " SET Uninstall = 'Y'" + " WHERE AD_Package_Imp_ID = "
				+ m_AD_Package_Imp_ID);
		no = DB.executeUpdate(sqlD.toString(), get_TrxName());

		sqlB = new StringBuffer("UPDATE AD_Package_Imp "
				+ " SET PK_Status = 'Uninstalled'"
				+ " WHERE AD_Package_Imp_ID = " + m_AD_Package_Imp_ID);
		no = DB.executeUpdate(sqlB.toString(), get_TrxName());

		log.info("Package Reversal Completed");

		return "";
	} // doIt

	/**
	 * Open input file for processing
	 * 
	 * @param String
	 *            file with path
	 * 
	 */
	public FileInputStream OpenInputfile(String filePath) {

		FileInputStream fileTarget = null;

		try {
			fileTarget = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			System.out.println("Can't find file ");

			return null;
		}
		return fileTarget;
	}

	/**
	 * Open output file for processing
	 * 
	 * @param String
	 *            file with path
	 * 
	 */
	public OutputStream OpenOutputfile(String filePath) {

		OutputStream fileTarget = null;

		try {
			fileTarget = new FileOutputStream(filePath);
		} catch (FileNotFoundException e) {
			System.out.println("Can't find file ");

			return null;
		}
		return fileTarget;
	}

	/**
	 * Copyfile
	 * 
	 * @param String
	 *            file with path
	 * 
	 */
	public int copyFile(String sourceFile, String targetFile) {

		OutputStream target = OpenOutputfile(targetFile);
		InputStream source = OpenInputfile(sourceFile);

		int byteCount = 0;
		int success = 0;
		try {
			while (true) {
				int data = source.read();
				if (data < 0)
					break;
				target.write(data);
				byteCount++;
			}
			source.close();
			target.close();

			System.out.println("Successfully copied " + byteCount + " bytes.");
		} catch (Exception e) {
			System.out.println("Error occurred while copying.  " + byteCount
					+ " bytes copied.");
			System.out.println(e.toString());

			success = -1;
		}
		return success;
	}

} // PackRoll
