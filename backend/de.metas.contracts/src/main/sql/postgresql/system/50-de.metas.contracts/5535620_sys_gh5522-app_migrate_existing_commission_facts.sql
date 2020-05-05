
UPDATE C_Commission_fact
SET commissionfacttimestamp = (EXTRACT(EPOCH FROM updated) || '.0')::character varying(255);
