
CREATE INDEX IF NOT EXISTS c_queue_workpackage_created ON c_queue_workpackage (created DESC NULLS LAST);
comment on index c_queue_workpackage_created is 'Needed because when filling T_WEBUI_ViewSelection, c_queue_workpackage is ordered by Created, latest first';

CREATE INDEX IF NOT EXISTS c_queue_workpackage_created ON c_queue_workpackage (updated DESC NULLS LAST);
