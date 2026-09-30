-- SUPERSEDED (net no-op): M_Forwarder was an early port design (gh13942), later REPLACED by the
-- standard M_Shipper and DROPPED within this same, still-unpublished port -- see gh14215: 5671880
-- (replace with M_Shipper), 5671920 (drop window), 5671930 (drop M_Forwarder table). Retained only
-- for migration-chain continuity; M_Forwarder does not ship.

ALTER TABLE M_Forwarder
    DROP COLUMN IF EXISTS AD_Table_ID
;

ALTER TABLE M_Forwarder
    DROP COLUMN IF EXISTS CM_Template_ID
;

ALTER TABLE M_Forwarder
    DROP COLUMN IF EXISTS OtherClause
;

ALTER TABLE M_Forwarder
    DROP COLUMN IF EXISTS WhereClause
;


CREATE UNIQUE INDEX m_forwarder_unique_value
    ON m_forwarder (value)
;


