
update ad_table set ischangelog='Y', updated='2022-08-22 10:50', updatedby=99
where tablename in (
                    'C_ProjectLine','C_ProjectType','C_ProjectPhase','C_ProjectTask','C_ProjectIssue',
                    'C_Project_Repair_Task','C_Project_Repair_Consumption_Summary','C_Project_Repair_CostCollector','C_Project_Resource_Budget',
                    'C_Project_Resource_Budget_Simulation','C_Project_WO_Resource_Conflict','C_Project_WO_Step_Simulation','C_Project_WO_Resource','C_Project_WO_Resource_Simulation',
                    'C_Project_WO_Step'
    )
  and ischangelog='N';
