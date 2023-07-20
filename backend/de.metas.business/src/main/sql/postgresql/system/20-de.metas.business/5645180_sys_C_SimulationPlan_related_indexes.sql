DROP INDEX IF EXISTS c_simulationplan_name
;

CREATE UNIQUE INDEX c_simulationplan_name ON c_simulationplan (name)
;

-- ----------------------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------------------------------
DROP INDEX IF EXISTS c_project_resource_budget_simulation_parent
;

DROP INDEX IF EXISTS c_project_resource_budget_simulation_uq
;

CREATE INDEX c_project_resource_budget_simulation_parent ON c_project_resource_budget_simulation (c_simulationplan_id)
;

CREATE UNIQUE INDEX c_project_resource_budget_simulation_uq ON c_project_resource_budget_simulation (c_simulationplan_id, c_project_resource_budget_id)
;

-- ----------------------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------------------------------


DROP INDEX IF EXISTS c_project_wo_step_simulation_parent
;

DROP INDEX IF EXISTS c_project_wo_step_simulation_uq
;

CREATE INDEX c_project_wo_step_simulation_parent ON c_project_wo_step_simulation (c_simulationplan_id)
;

CREATE UNIQUE INDEX c_project_wo_step_simulation_uq ON c_project_wo_step_simulation (c_simulationplan_id, c_project_wo_step_id)
;


-- ----------------------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------------------------------
-- ----------------------------------------------------------------------------------------------------------------------------------------


DROP INDEX IF EXISTS c_project_wo_resource_simulation_parent
;

DROP INDEX IF EXISTS c_project_wo_resource_simulation_uq
;

CREATE INDEX c_project_wo_resource_simulation_parent ON c_project_wo_resource_simulation (c_simulationplan_id)
;

CREATE UNIQUE INDEX c_project_wo_resource_simulation_uq ON c_project_wo_resource_simulation (c_simulationplan_id, c_project_wo_resource_id)
;

