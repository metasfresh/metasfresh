drop table if exists T_Query_Selection_ToDelete;

CREATE TABLE T_Query_Selection_ToDelete
(
  UUID character varying(60) NOT NULL,
  Executor_UUID character varying(60),
  Created timestamp with time zone NOT NULL DEFAULT now()
);

create unique index T_Query_Selection_ToDelete_Executor_UUID on T_Query_Selection_ToDelete (Executor_UUID);
