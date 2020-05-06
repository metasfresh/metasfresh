/*
drop table if exists M_ShipmentSchedule_Lock;
*/

create table M_ShipmentSchedule_Lock (
	M_ShipmentSchedule_ID numeric(10,0) NOT NULL,
	LockedBy_User_ID numeric(10,0) NOT NULL,
	LockType character varying(1) NOT NULL,
	--
	Created timestamp with time zone NOT NULL DEFAULT now()
);

CREATE UNIQUE INDEX M_ShipmentSchedule_Lock_UQ ON M_ShipmentSchedule_Lock(M_ShipmentSchedule_ID);

