package de.metas.commission.install;

/*
 * #%L
 * de.metas.commission.client
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


import static de.metas.commission.model.I_C_AdvCommissionInstance.COLUMNNAME_AD_Table_ID;
import static de.metas.commission.model.I_C_AdvCommissionInstance.COLUMNNAME_C_AdvCommissionTerm_ID;
import static de.metas.commission.model.I_C_AdvCommissionInstance.COLUMNNAME_LevelHierarchy;
import static de.metas.commission.model.I_C_AdvCommissionInstance.COLUMNNAME_Record_ID;
import static org.compiere.util.DisplayType.Amount;
import static org.compiere.util.DisplayType.Button;
import static org.compiere.util.DisplayType.DateTime;
import static org.compiere.util.DisplayType.Integer;
import static org.compiere.util.DisplayType.String;
import static org.compiere.util.DisplayType.Table;
import static org.compiere.util.DisplayType.TableDir;

import java.util.Properties;

import org.compiere.Adempiere;
import org.compiere.model.MColumn;
import org.compiere.model.X_AD_Table;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Env;
import org.compiere.util.Ini;

import de.metas.commission.model.I_C_AdvComSalesRepFact;
import de.metas.commission.model.I_C_AdvCommissionFact;
import de.metas.commission.model.I_C_AdvCommissionInstance;
import de.metas.commission.model.I_C_AdvCommissionRelevantPO;

public class InstallMain {

	private static final String ENTITY_TYPE = "de.metas.commission";

	private static final String C_ADV_COMMISSION_INSTANCE = I_C_AdvCommissionInstance.Table_Name;
	private static final String C_ADV_COMMISSION_FACT = I_C_AdvCommissionFact.Table_Name;
	private static final String C_INCIDENT_LINE = "C_IncidentLine";
	private static final String C_INCIDENT_LINE_FACT = "C_IncidentLineFact";

	private static final Logger logger = LogManager.getLogger(InstallMain.class);

	private static final String trxName = null;

	private static void doIt() {


		// commissionIncident();
		//
		// commissionIncidentFact();
		//
		// commissionInstance();
		//
		// commissionInstanceFact();
		//
		// commissionRelevantPO();

		// commissionfact2salesRepFact();

		// commissionFact_SalesRepV();

		specificSponsorParams();

		specificInstanceParams();
		
		
	}

	private static void specificSponsorParams() {

		final Properties ctx = Env.getCtx();
		final String tableName = "C_AdvComSponsorParam";

		TableInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName).setName("Spezifische sponsorbezogene Parameter")
				.setAccessLevel(X_AD_Table.ACCESSLEVEL_Organization).save()
				.standardColumns();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, "C_AdvCommissionTerm_ID").setReference(TableDir)
				.setUpdatable(false).setMandatory(true).setParent(true).save()
				.syncColumn();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, "Description").setReference(String).setFieldLength(
				1024).setUpdatable(true).setMandatory(false).save()
				.syncColumn();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, "Name").setReference(String).setFieldLength(255)
				.setUpdatable(true).setMandatory(true).save().syncColumn();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, "ParamName").setReference(String)
				.setFieldLength(255).setUpdatable(true).setMandatory(true)
				.save().syncColumn();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, "ParamValue").setReference(String).setFieldLength(
				255).setUpdatable(true).setMandatory(true).save().syncColumn();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, "SeqNo").setReference(String).setFieldLength(255)
				.setUpdatable(true).setMandatory(true).save().syncColumn();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, "AD_Reference_ID").setReference(TableDir)
				.setUpdatable(true).setMandatory(true).save().syncColumn();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, "AD_Reference_Value_ID").setReference(Table)
				.setReferenceValue(4).setUpdatable(true).setMandatory(true)
				.save().syncColumn();
		
		IndexTableInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, "Name_SponsorParam_Idx").setUnique(true).save().addIndexColumns(
				"Name", "C_AdvCommissionTerm_ID").syncIndexTable();

		IndexTableInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, "ParamName_SponsorParam_Idx").setUnique(true).save()
				.addIndexColumns("ParamName", "C_AdvCommissionTerm_ID")
				.syncIndexTable();
	}

	private static void specificInstanceParams() {

		final Properties ctx = Env.getCtx();
		final String tableName = "C_AdvComInstanceParam";

		TableInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName).setName("Spezifische vorgangsbezogene Parameter")
				.setAccessLevel(X_AD_Table.ACCESSLEVEL_Organization).save()
				.standardColumns();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, "C_AdvCommissionType_ID").setReference(TableDir)
				.setUpdatable(false).setMandatory(true).setParent(true).save()
				.syncColumn();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, "Description").setReference(String).setFieldLength(
				1024).setUpdatable(true).setMandatory(false).save()
				.syncColumn();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, "Name").setReference(String).setFieldLength(255)
				.setUpdatable(true).setMandatory(true).save().syncColumn();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, "ParamName").setReference(String)
				.setFieldLength(255).setUpdatable(true).setMandatory(true)
				.save().syncColumn();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, "ParamValue").setReference(String).setFieldLength(
				255).setUpdatable(true).setMandatory(true).save().syncColumn();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, "SeqNo").setReference(String).setFieldLength(255)
				.setUpdatable(true).setMandatory(true).save().syncColumn();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, "AD_Reference_ID").setReference(TableDir)
				.setUpdatable(true).setMandatory(true).save().syncColumn();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, "AD_Reference_Value_ID").setReference(Table)
				.setReferenceValue(4).setUpdatable(true).setMandatory(true)
				.save().syncColumn();

		IndexTableInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, "Name_InstanceParam_Idx").setUnique(true).save().addIndexColumns(
				"Name", "C_AdvCommissionType_ID").syncIndexTable();

		IndexTableInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, "ParamName_InstanceParam_Idx").setUnique(true).save()
				.addIndexColumns("ParamName", "C_AdvCommissionType_ID")
				.syncIndexTable();

	}

	private static void commissionFact_SalesRepV() {

		final Properties ctx = Env.getCtx();
		final String tableName = "C_AdvComFact_SalesRepFact_V";

		TableInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName).setName("Sponsor-Umsatzdatensatz").setAccessLevel(
				X_AD_Table.ACCESSLEVEL_ClientPlusOrganization).setView(true)
				.save().standardColumns();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, I_C_AdvCommissionFact.COLUMNNAME_AD_Table_ID)
				.setReference(TableDir).setMandatory(true).save();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, I_C_AdvCommissionFact.COLUMNNAME_Record_ID)
				.setReference(TableDir).setMandatory(true).save();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, I_C_AdvCommissionFact.COLUMNNAME_DateDoc)
				.setReference(TableDir).setMandatory(true).save();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, I_C_AdvCommissionFact.COLUMNNAME_Status)
				.setReference(TableDir).setMandatory(true).save();


		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName,
				I_C_AdvCommissionFact.COLUMNNAME_CommissionPointsBase)
				.setReference(TableDir).setMandatory(true).save();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, I_C_AdvCommissionFact.COLUMNNAME_Qty).setReference(
				TableDir).setMandatory(true).save();

		ColumnInstaller.mkInst(ctx, trxName)
				.createOrUpdate(ENTITY_TYPE, tableName,
						I_C_AdvCommissionFact.COLUMNNAME_CommissionPointsSum)
				.setReference(TableDir).setMandatory(true).save();
	}

	private static void commissionRelevantPO() {

		final Properties ctx = Env.getCtx();
		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				I_C_AdvCommissionRelevantPO.Table_Name,
				I_C_AdvCommissionRelevantPO.COLUMNNAME_DateDocColumn_ID)
				.setMandatory(true).save().syncColumn();
	}

	private static void commissionInstanceFact() {

		final Properties ctx = Env.getCtx();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				C_ADV_COMMISSION_FACT, "DateDoc").setReference(DateTime).save()
				.syncColumn().replacesOldCol("DateAcct");

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				C_ADV_COMMISSION_FACT, C_INCIDENT_LINE_FACT + "_ID")
				.setReference(TableDir).setParent(true).setUpdatable(false)
				.setMandatory(false).setDefaultValue("-1").save().syncColumn();
	}

	private static void commissionInstance() {

		final Properties ctx = Env.getCtx();

		logger.info("Column 'C_IncidentLine_ID'");
		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				C_ADV_COMMISSION_INSTANCE, C_INCIDENT_LINE + "_ID")
				.setReference(TableDir).setParent(true).setUpdatable(false)
				.setMandatory(false).setDefaultValue("-1").save().syncColumn();

		logger.info("Column 'LevelForecast'");
		final MColumn levelForecastCol = ColumnInstaller.mkInst(ctx, trxName)
				.createOrUpdate(ENTITY_TYPE, C_ADV_COMMISSION_INSTANCE,
						"LevelForecast").setReference(Integer).save()
				.syncColumn().getColumn();

		logger.info("Field for column 'LevelForecast'");
		FieldInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				levelForecastCol).setWindowAndTab("Buchauszug", "Vorgang")
				.setVisible(true).save();

		logger.info("Column 'LevelCalculation'");

		final MColumn levelCalculationCol = ColumnInstaller
				.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
						C_ADV_COMMISSION_INSTANCE, "LevelCalculation")
				.setReference(Integer).save().syncColumn().replacesOldCol(
						"LevelCommission").getColumn();

		logger.info("Field for column 'LevelCalculation'");
		FieldInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				levelCalculationCol).setWindowAndTab("Buchauszug", "Vorgang")
				.setVisible(true).save();

		logger.info("Column 'LevelHierarchy'");
		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				C_ADV_COMMISSION_INSTANCE, "LevelHierarchy").setReference(
				Integer).save().syncColumn();
		
		// there is a view c_advcommission_info_v that refers to 'level'
		// so in order to drop the level column, one needs to change that view
		// TODO create an installer that executes generic SQL


		// DROP VIEW c_advcommission_info_v;
		//		
		// CREATE OR REPLACE VIEW c_advcommission_info_v AS
		// SELECT max(i.c_advcommissioninstance_id) AS
		// c_advcommission_info_v_id, max(i.ad_client_id) AS ad_client_id,
		// max(i.ad_org_id) AS ad_org_id, max(i.created) AS created,
		// max(i.createdby) AS createdby, max(i.updated) AS updated,
		// max(i.updatedby) AS updatedby, ssr.c_bpartner_id,
		// i.c_advcommissionterm_id, i."levelforecast", p.startdate, p.enddate,
		// sum(pr.commissionpoints) AS points_predicted,
		// sum(ca.commissionpoints) AS points_calculated,
		// sum(pa.commissionpoints) AS points_paid
		// FROM c_advcommissioninstance i
		// LEFT JOIN c_sponsor_salesrep ssr ON i.c_sponsor_salesrep_id =
		// ssr.c_sponsor_id AND ssr.isactive = 'Y'::bpchar AND ssr.c_bpartner_id
		// > 0::numeric
		// LEFT JOIN ( SELECT p.startdate, p.enddate, t.c_advcommissionterm_id
		// FROM c_period p
		// LEFT JOIN c_year y ON y.c_year_id = p.c_year_id
		// LEFT JOIN c_advcommissioncondition c ON c.c_calendar_id =
		// y.c_calendar_id
		// LEFT JOIN c_advcommissionterm t ON t.c_advcommissioncondition_id =
		// c.c_advcommissioncondition_id) p ON p.c_advcommissionterm_id =
		// i.c_advcommissionterm_id
		// LEFT JOIN ( SELECT f.commissionpoints, f.c_advcommissioninstance_id,
		// f.datedoc
		// FROM c_advcommissionfact f
		// WHERE f.commissionpoints > 0::numeric AND f.status::text =
		// 'PR'::text) pr ON pr.c_advcommissioninstance_id =
		// i.c_advcommissioninstance_id AND ssr.validfrom <= pr.datedoc AND
		// ssr.validto >= pr.datedoc AND p.startdate <= pr.datedoc AND p.enddate
		// >= pr.datedoc
		// LEFT JOIN ( SELECT f.commissionpoints, f.c_advcommissioninstance_id,
		// f.datedoc
		// FROM c_advcommissionfact f
		// WHERE f.commissionpoints > 0::numeric AND f.status::text =
		// 'CA'::text) ca ON ca.c_advcommissioninstance_id =
		// i.c_advcommissioninstance_id AND ssr.validfrom <= ca.datedoc AND
		// ssr.validto >= ca.datedoc AND p.startdate <= ca.datedoc AND p.enddate
		// >= ca.datedoc
		// LEFT JOIN ( SELECT f.commissionpoints, f.c_advcommissioninstance_id,
		// f.datedoc
		// FROM c_advcommissionfact f
		// WHERE f.commissionpoints > 0::numeric AND f.status::text =
		// 'PA'::text) pa ON pa.c_advcommissioninstance_id =
		// i.c_advcommissioninstance_id AND ssr.validfrom <= pa.datedoc AND
		// ssr.validto >= pa.datedoc AND p.startdate <= pa.datedoc AND p.enddate
		// >= pa.datedoc
		// WHERE i.isactive = 'Y'::bpchar AND (pr.commissionpoints > 0::numeric
		// OR ca.commissionpoints > 0::numeric OR pa.commissionpoints >
		// 0::numeric)
		// GROUP BY ssr.c_bpartner_id, i.c_advcommissionterm_id,
		// i."levelforecast", p.startdate, p.enddate
		// ORDER BY ssr.c_bpartner_id, i.c_advcommissionterm_id,
		// i."levelforecast", p.startdate;
		//		
		// GRANT ALL ON TABLE c_advcommission_info_v TO adempiere;

		logger.info("Replacing column 'Level' with 'LevelHierarchy'");
		final MColumn levelHierarchyCol = ColumnInstaller.mkInst(ctx, trxName)
				.createOrUpdate(ENTITY_TYPE, C_ADV_COMMISSION_INSTANCE,
						"LevelHierarchy").save().replacesOldCol("Level")
				.getColumn();

		logger.info("Field for column 'LevelHierarchy'");
		FieldInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				levelHierarchyCol).setWindowAndTab("Buchauszug", "Vorgang")
				.setVisible(true).save();

		logger.info("Index for table " + C_ADV_COMMISSION_INSTANCE);
		IndexTableInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				C_ADV_COMMISSION_INSTANCE,
				levelHierarchyCol.getColumnName() + "_IDX").setUnique(true)
				.save().addIndexColumns(COLUMNNAME_Record_ID,
						COLUMNNAME_AD_Table_ID,
						COLUMNNAME_C_AdvCommissionTerm_ID,
						COLUMNNAME_LevelHierarchy).syncIndexTable();

		logger.info("Column 'Points_ToCalculate'");
		final MColumn PointsToCalcCol = ColumnInstaller
				.mkInst(ctx, trxName)
				.createOrUpdate(ENTITY_TYPE, C_ADV_COMMISSION_INSTANCE,
						"Points_ToCalculate")
				.setReference(Amount)
				.setColumnSQL(
						"(select sum(CommissionPoints) from C_AdvCommissionFact f where f.C_AdvCommissionInstance_ID=C_AdvCommissionInstance.C_AdvCommissionInstance_ID AND f.Status='CP')")
				.save().getColumn();

		logger.info("Field for column 'Points_ToCalculate'");
		FieldInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				PointsToCalcCol).setWindowAndTab("Buchauszug", "Vorgang")
				.setVisible(true).save();
	}

	private static void commissionIncident() {

		final Properties ctx = Env.getCtx();

		TableInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				C_INCIDENT_LINE).setName("Incident Line").setAccessLevel(
				X_AD_Table.ACCESSLEVEL_ClientPlusOrganization).save()
				.standardColumns();
	}

	private static void commissionIncidentFact() {

		final Properties ctx = Env.getCtx();

		TableInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				C_INCIDENT_LINE_FACT).setName("Commission Incident Fact")
				.setAccessLevel(X_AD_Table.ACCESSLEVEL_ClientPlusOrganization)
				.save().standardColumns();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				C_INCIDENT_LINE_FACT, C_INCIDENT_LINE + "_ID").setReference(
				TableDir).setParent(true).setMandatory(true)
				.setUpdatable(false).save().syncColumn();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				C_INCIDENT_LINE_FACT, "AD_Table_ID").setReference(TableDir)
				.setMandatory(true).setUpdatable(false).save().syncColumn();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				C_INCIDENT_LINE_FACT, "Record_ID").setReference(Button)
				.setFieldLength(20).setMandatory(true).setUpdatable(false)
				.save().syncColumn();

		IndexTableInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				C_INCIDENT_LINE_FACT, C_INCIDENT_LINE_FACT + "_T_R_IDX")
				.setUnique(true).save().addIndexColumns("AD_Table_ID",
						"Record_ID").syncIndexTable();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				C_INCIDENT_LINE_FACT, "C_BPartner_ID").setReference(TableDir)
				.setMandatory(true).save().syncColumn();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				C_INCIDENT_LINE_FACT, "DateDoc").setReference(DateTime)
				.setMandatory(true).save().syncColumn();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				C_INCIDENT_LINE_FACT, "C_AdvCommissionRelevantPO_ID")
				.setReference(TableDir).setMandatory(true).save().syncColumn();

	}

	private static void commissionfact2salesRepFact() {

		final Properties ctx = Env.getCtx();

		final String tableName = "C_AdvComFact_SalesRepFact";

		TableInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName).setName("Commissionfact to sales rep fact")
				.setAccessLevel(X_AD_Table.ACCESSLEVEL_ClientPlusOrganization)
				.save().standardColumns();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName, C_ADV_COMMISSION_FACT + "_ID")
				.setReference(TableDir).setMandatory(true).setUpdatable(false)
				.save().syncColumn();

		ColumnInstaller.mkInst(ctx, trxName).createOrUpdate(ENTITY_TYPE,
				tableName,
				I_C_AdvComSalesRepFact.COLUMNNAME_C_AdvComSalesRepFact_ID)
				.setReference(TableDir).setMandatory(true).setUpdatable(false)
				.save().syncColumn();
	}

	public static void main(final String[] args) {

		Adempiere.startupEnvironment(false);
		Ini.setMetasfreshHome(".");
		Ini.setProperty(Ini.P_LOGMIGRATIONSCRIPT, true);
		
		doIt();
	}
}
